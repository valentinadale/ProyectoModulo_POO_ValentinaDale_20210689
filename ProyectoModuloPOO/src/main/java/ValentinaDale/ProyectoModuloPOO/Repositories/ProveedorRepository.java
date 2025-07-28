package ValentinaDale.ProyectoModuloPOO.Repositories;

import ValentinaDale.ProyectoModuloPOO.Entities.ProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<ProveedorEntity, Long>{

//interface que contiene todos los metodos/solicitudes que interactuan con la base de datos

}
