package es.iesjandula.credenciales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.iesjandula.credenciales.model.CredencialEntity;

import java.util.List;

// Repositorio para hacer las query con la base de datos
@Repository 
public interface CredencialRepository extends JpaRepository<CredencialEntity, Long>
{
    CredencialEntity findByUsuario(String usuario);
    List<CredencialEntity> findByUsuarioAndUbicacionAndUrl(String usuario, String ubicacion, String url);
    List<CredencialEntity> findByUsuarioAndUbicacion(String usuario, String ubicacion);
    List<CredencialEntity> findByUsuarioAndUrl(String usuario, String url);

}
