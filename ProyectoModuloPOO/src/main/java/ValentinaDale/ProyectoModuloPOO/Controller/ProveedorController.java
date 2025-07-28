package ValentinaDale.ProyectoModuloPOO.Controller;

import ValentinaDale.ProyectoModuloPOO.Exceptions.ExceptionProveedorDuplicado;
import ValentinaDale.ProyectoModuloPOO.Models.DTO.ProveedorDTO;
import ValentinaDale.ProyectoModuloPOO.Services.ProveedorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apiPropietarios")
public class ProveedorController {

    @Autowired
    ProveedorService service;

    //Endpoint/metodo para obtener proveedores
    @GetMapping("/consultarProveedores")
    public List<ProveedorDTO> consultarProveedores(){
        //manda a llamar el metodo del service
        return service.obtenerProveedores();
    }

    //Endpoint/metodo para ingresar el proveedor
    @PostMapping("/ingresarProveedores")
    public ResponseEntity<?> ingresarProveedores(
            @Valid
            //se crea un objeto dto de los datos que ingreso el usuario llamado json
            @RequestBody ProveedorDTO json,
            HttpServletRequest request
    ){
        try {
            //se almacena en la variable respuesta los datos ya actualizados que se consiguen del metodo en el service y se envia el objeto dto/json
            ProveedorDTO respuesta = service.insertarProveedores(json);
            //si no se almacena nada en la variable respuesta (no se pudo insertar) mostrara un mensaje de error
            if (respuesta == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción fallida",
                        "errorType", "VALIDARION ERROR",
                        "message", "Los datos no pueden ser registrados"
                ));
            }
            //si se almaceno algo en respuesta muestra un mensaje de exito y se muestra los datos que se almacenaron en respuesta (los datos que se ingresaron)
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
               "status", "Succes",
               "data", respuesta
            ));
        }catch (Exception e){
            //si no inicia el proceso o no funciona muestra un error de servidor
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
               "status", "Error",
               "message", "Error no controlado al registrar",
               "detail", e.getMessage()
            ));
        }
    }

    //metodo/endpoint al que se accede para actualizar un registro, en la url tiene que ir el id del registro que se quiere actualizar
    @PutMapping("/actualizarProveedores/{id}")
    public ResponseEntity<?> actualizarProveedores(
            @PathVariable Long id,
            @Valid
            @RequestBody ProveedorDTO json,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            //se crea un objeto dto que sera el que se mostrara del registro ya actualizado en la base, esta respuesta vendra del metodo en el service y le daremos el id del registro que se quiere actualizar y los nuevos datos que brindo el usuario como dto
            ProveedorDTO dto = service.actualizarProveedor(id, json);
            return ResponseEntity.ok(dto);
        }catch (ExceptionProveedorDuplicado e){
            //si se encuentra ya duplicado datos del nuevo registro que tienen que ser unicos mostrara n mensaje de error
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
               "Error", "Datos duplicados",
               "Campo", e.getCampoDupliacdo()
            ));
        }
    }

    //metodo/endpoint al que se accede para eliminar, en la url tiene que ir el id del registro que se quiere eliminar
    @DeleteMapping("/eliminarProveedor/{id}")
    public ResponseEntity<Map<String, String>> eliminarProveedor(
            @PathVariable Long id
    ){
        //se almacena en la variable la respuesta del metodo en el service para eliminar al cual se le pasa el id
        boolean respuesta = service.eliminarProveedor(id);
        //si se logra eliminar correctamente se muestra un mensaje de exito
        if (respuesta == true){
            return ResponseEntity.ok().body(Map.of(
                    "Status", "Succes",
                    "Message", "Se elimino exitosamente"
            ));
            //si no muestra un mensaje de error
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "Status", "ERROR",
                    "Message", "ERROR interno del servidor al eliminar el proveedor"
            ));
        }
    }

    //metodo/endpoint al que se accede cuando se quiere buscar un registro por su id
    @GetMapping("/buscarporID/{id}")
    public ProveedorDTO buscarporID(
            @PathVariable Long id
    ){
        //Mustra el json con el usuario encontrado
        return service.buscarPorID(id);
    }

}
