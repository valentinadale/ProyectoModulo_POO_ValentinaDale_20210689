package ValentinaDale.ProyectoModuloPOO.Services;

import ValentinaDale.ProyectoModuloPOO.Entities.ProveedorEntity;
import ValentinaDale.ProyectoModuloPOO.Exceptions.ExceptionProveedorNoEncontrado;
import ValentinaDale.ProyectoModuloPOO.Exceptions.ExceptionProveedorNoRegistrado;
import ValentinaDale.ProyectoModuloPOO.Models.DTO.ProveedorDTO;
import ValentinaDale.ProyectoModuloPOO.Repositories.ProveedorRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProveedorService {

    @Autowired
    ProveedorRepository repo;


    //metodo para buscar registros
    public List<ProveedorDTO> obtenerProveedores() {

        //convierte a dto los datos que vienen de tipo entity de la base
        List<ProveedorEntity> Lista = repo.findAll();
        return Lista.stream().map(this::convertirADTO).collect(Collectors.toList());

    }

    //metodo que inserta los datos a la base
    public ProveedorDTO insertarProveedores(@Valid ProveedorDTO json) {

        //si no se envian datos muestra un mensaje de advertencia
        if (json == null){
            throw new IllegalArgumentException("Tienen que ingresarse todos los datos");
        }
        try {
            //los datos que ingrese el usuario se convierten a entity para ingresarlos a la base de datos
            ProveedorEntity entity = convertirAEntity(json);
            //los datos que regrese la base (el registro que se ingreso) los convierte a dto para poder mostrarselos al usuario
            ProveedorEntity respuesta = repo.save(entity);
            return convertirADTO(respuesta);
        }catch (Exception e){
            //si hubo un error en el proceso muestra un mensaje
            log.error("Error interno del servidor" + e.getMessage());
            throw new ExceptionProveedorNoRegistrado("No se pudo registrar el Proveedor");
        }

    }

    ////metodo para actualizar los registros, se tiene que recibir el id del registro que se actuliza
    public ProveedorDTO actualizarProveedor(Long id, @Valid ProveedorDTO json) {

        //busca en la base el registro con ede id
        ProveedorEntity proveedorExiste = repo.findById(id).orElseThrow(
                ()-> new ExceptionProveedorNoEncontrado("No se pudo encontrar al proveedor en la base de datos")
        );
        //los datos que ingrese el usuario se convierten a entity para ingresarlos a la base de datos
        proveedorExiste.setProviderName(json.getProviderName());
        proveedorExiste.setProviderPhone(json.getProviderPhone());
        proveedorExiste.setProviderAddress(json.getProviderAddress());
        proveedorExiste.setProviderEmail(json.getProviderEmail());
        proveedorExiste.setProviderCode(json.getProviderCode());
        proveedorExiste.setProviderStatus(json.getProviderStatus());
        proveedorExiste.setProviderComments(json.getProviderComments());
        ProveedorEntity proveedorActualizado = repo.save(proveedorExiste);
        //los datos que regrese la base (datos actualizados) los convierte a dto para poder mostrarselos al usuario
        return convertirADTO(proveedorActualizado);
    }


//metodo para elimianr un usuario, se tiene que brindr en la url el id del registro que se quiera eliminar
    public boolean eliminarProveedor(Long id) {
        if(id == null){
            //si no se envia ningun id se muestra un mensaje de error
            throw new IllegalArgumentException("Se tiene que proveer el id del proveedor a eliminar");
        }
        try {
            //se busca en la bae si existe ese registro
            Optional<ProveedorEntity> existingTraining = repo.findById(id);
            if(!existingTraining.isPresent()){
                throw new RuntimeException("El proveedor se encontro exitosamente");
            }
            //se elimina
            repo.deleteById(id);
            return true;
        }catch (Exception e){
            System.out.println("Error al eliminar el proveedor " + e.getMessage());
            return false;
        }
    }

    //Metodo para buscar en la base el registro mediante un id
    public ProveedorDTO buscarPorID(Long id) {

        //si no se da ningun id se muestra un mensjae de error
        if(id == null){
            throw new IllegalArgumentException("Se tiene que proveer el id del proveedor para buscar");
        }
        //busca en la base si existe el registro
        ProveedorEntity proveedorExiste = repo.findById(id).orElseThrow(
                ()-> new ExceptionProveedorNoEncontrado("No se pudo encontrar al proveedor en la base de datos")
        );
        //si existe lo convierte a dto para mostrarselo al usuario
        return convertirADTO(proveedorExiste);

    }

    //metodo para convertir los datos a Entity para poder ingrsarlos a la base de datos
    private ProveedorEntity convertirAEntity(@Valid ProveedorDTO json) {

        ProveedorEntity entity = new ProveedorEntity();
        entity.setProviderID(json.getProviderID());
        entity.setProviderName(json.getProviderName());
        entity.setProviderPhone(json.getProviderPhone());
        entity.setProviderAddress(json.getProviderAddress());
        entity.setProviderEmail(json.getProviderEmail());
        entity.setProviderCode(json.getProviderCode());
        entity.setProviderStatus(json.getProviderStatus());
        entity.setProviderComments(json.getProviderComments());
        return entity;
    }

    //metodo para convertir los datos a dto para poder mostrarselos al usuario
    private ProveedorDTO convertirADTO(ProveedorEntity entity) {

        ProveedorDTO dto = new ProveedorDTO();
        dto.setProviderID(entity.getProviderID());
        dto.setProviderName(entity.getProviderName());
        dto.setProviderPhone(entity.getProviderPhone());
        dto.setProviderAddress(entity.getProviderAddress());
        dto.setProviderEmail(entity.getProviderEmail());
        dto.setProviderCode(entity.getProviderCode());
        dto.setProviderStatus(entity.getProviderStatus());
        dto.setProviderComments(entity.getProviderComments());
        return dto;

    }


}
