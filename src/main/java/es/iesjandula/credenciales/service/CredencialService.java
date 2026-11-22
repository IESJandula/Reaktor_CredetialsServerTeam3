package es.iesjandula.credenciales.service;

import java.util.ArrayList;
import java.util.List;

import es.iesjandula.credenciales.util.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.iesjandula.credenciales.dto.CredencialDto;
import es.iesjandula.credenciales.dto.CredencialPassDto;
import es.iesjandula.credenciales.model.CredencialEntity;
import es.iesjandula.credenciales.repository.CredencialRepository;
import es.iesjandula.credenciales.util.Constant;
import es.iesjandula.credenciales.util.CredencialError;
import lombok.extern.slf4j.Slf4j;

@Service 
@Slf4j 
public class CredencialService 
{
    // Importamos el repositorio de credenciales para poder realizar operaciones con la base de datos
    @Autowired
    private CredencialRepository credencialRepository;

    @Autowired
    private PasswordEncryptor passwordEncryptor;
    
    // Método para crear una nueva credencial en la base de datos
    public void crearCredencial(CredencialPassDto credencialPassDto) throws CredencialError
    {
        // Validamos que el objeto no sea nulo y que los campos obligatorios no estén vacíos
        if (credencialPassDto == null)
        {
            String error = "El objeto es nulo";

            log.error(error);
            throw new CredencialError(Constant.EROR_OBJECT_NULL, error);
        }

        // Validamos que los campos obligatorios no estén vacíos
        if (credencialPassDto.getUsuario() == null || credencialPassDto.getUsuario().isEmpty())
        {
            String error = "El usuario es nulo o vacío";

            log.error(error);
            throw new CredencialError(Constant.ERROR_USER_NULL_OR_EMPTY, error);
        }

        // Validamos que la descripción no esté vacía
        if (credencialPassDto.getDescripcion() == null || credencialPassDto.getDescripcion().isEmpty())
        {
            String error = "La descripción es nula o vacía";

            log.error(error);
            throw new CredencialError(Constant.ERROR_DESCRIPTION_NULL_OR_EMPTY, error);
        }

        // Validamos que la ubicación y la URL no estén vacías
        if ((credencialPassDto.getUbicacion() == null || credencialPassDto.getUbicacion().isEmpty()) && (credencialPassDto.getUrl() == null || credencialPassDto.getUrl().isEmpty()))
        {
            String error = "La ubicación y la URL son nulas o vacías";

            log.error(error);
            throw new CredencialError(Constant.ERROR_UBICATION_AND_URL_NULL_OR_EMPTY, error);
        }

        // validar que la ubicación exista en el sistema

        // Validamos que la contraseña no esté vacía y cumpla con el formato requerido
        if (credencialPassDto.getPassword() == null || credencialPassDto.getPassword().isEmpty())
        {
            String error = "La contraseña es nula o vacía";

            log.error(error);
            throw new CredencialError(Constant.ERROR_PASSWORD_NULL_OR_EMPTY, error);
        }

        // Validamos que la contraseña cumpla con el formato requerido
        if (!credencialPassDto.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"))
        {
            String error = "La contraseña no cumple con el formato requerido";

            log.error(error);
            throw new CredencialError(Constant.ERROR_PASSWORD_MALFORMED, error);
        }

        // Creamos un objeto CredencialEntity a partir del objeto CredencialPassDto y lo guardamos en la base de datos
        CredencialEntity credencialEntity = new CredencialEntity();
        try
        {
            String passwordEncriptada = this.passwordEncryptor.encriptar(credencialPassDto.getPassword());
            credencialEntity.setPassword(passwordEncriptada);
        }
        catch (Exception exception)
        {
            log.error("Error al encriptar la contraseña", exception);
            throw new CredencialError(Constant.ERROR_CREATE_CREDENTIALS_INTERNAL_SERVER, "Error al encriptar la contraseña", exception);
        }
        credencialEntity.setUsuario(credencialPassDto.getUsuario());
        credencialEntity.setDescripcion(credencialPassDto.getDescripcion());
        credencialEntity.setUbicacion(credencialPassDto.getUbicacion());
        credencialEntity.setUrl(credencialPassDto.getUrl());
        credencialEntity.setPassword(credencialPassDto.getPassword());

        // Guardamos la credencial en la base de datos y registramos un mensaje de éxito en el log
        log.info("La credencial se ha creado correctamente");
        this.credencialRepository.saveAndFlush(credencialEntity);
    }

    // Método para actualizar una credencial existente en la base de datos
    public List<CredencialDto> listarCredenciales()
    {
        /*
        Creamos primero una lista entity ya que la llamada al repostitory
        solo devuelve entity y luego creamos una lista con dto que rellenaremos
        con los datos de la lista entity para devolverla al controlador
        */
        List<CredencialEntity> credenciales = this.credencialRepository.findAll();
        List<CredencialDto> credencialesDto = new ArrayList<>();

        for (int i = 0; i < credenciales.size(); i++)
        {
            CredencialDto credencialDto = new CredencialDto();
            credencialDto.setUsuario(credenciales.get(i).getUsuario());
            credencialDto.setDescripcion(credenciales.get(i).getDescripcion());
            credencialDto.setUbicacion(credenciales.get(i).getUbicacion());
            credencialDto.setUrl(credenciales.get(i).getUrl());
            credencialesDto.add(credencialDto);
        }

        return credencialesDto;
    }

    // Método para eliminar una credencial de la base de datos
    public void eliminarCredenciales(CredencialDto credencialDto) throws CredencialError
    {

        // Validamos que el objeto y el usuario no sean nulos ni vacíos (400)
        if (credencialDto == null || credencialDto.getUsuario() == null || credencialDto.getUsuario().isEmpty())
        {
            String error = "El usuario es nulo o vacío";

            log.error(error);
            throw new CredencialError(Constant.ERROR_DELETE_USER_NULL_OR_EMPTY, error);
        }

        // Validamos que al menos ubicación o URL vengan rellenas (401)
        if (credencialDto.getUbicacion() == null && credencialDto.getUbicacion().isEmpty() && credencialDto.getUrl() == null && credencialDto.getUrl().isEmpty())
        {
            String error = "La ubicación y la URL son nulas o vacías. Una de las dos debe estar rellena";

            log.error(error);
            throw new CredencialError(Constant.ERROR_DELETE_UBICATION_AND_URL_NULL_OR_EMPTY, error);
        }

        // Validar que la ubicación exista en el sistema (402)
        if (credencialDto.getUbicacion() != null && !credencialDto.getUbicacion().isEmpty())
        {
            // TODO: comprobar contra tu repositorio/lista/servicio de ubicaciones.
            String error = "La ubicación no existe";
            log.error(error);
            throw new CredencialError(Constant.ERROR_DELETE_UBICATION_NOT_EXIST, error);
        }

        // Buscamos todos los registros que coincidan con el filtro
        List<CredencialEntity> credencialesEncontradas;

        if (credencialDto.getUbicacion() != null && !credencialDto.getUbicacion().isEmpty() && credencialDto.getUrl() != null && !credencialDto.getUrl().isEmpty())
        {
            credencialesEncontradas = this.credencialRepository.findByUsuarioAndUbicacionAndUrl(
                    credencialDto.getUsuario(),
                    credencialDto.getUbicacion(),
                    credencialDto.getUrl());
        }
        else if (credencialDto.getUbicacion() != null && !credencialDto.getUbicacion().isEmpty())
        {
            credencialesEncontradas = this.credencialRepository.findByUsuarioAndUbicacion(
                    credencialDto.getUsuario(),
                    credencialDto.getUbicacion());
        }
        else
        {
            credencialesEncontradas = this.credencialRepository.findByUsuarioAndUrl(
                    credencialDto.getUsuario(),
                    credencialDto.getUrl());
        }

        // Eliminamos todos los registros encontrados
        this.credencialRepository.deleteAll(credencialesEncontradas);
        this.credencialRepository.flush();
        log.info("Se han eliminado {} credenciales del usuario {}", credencialesEncontradas.size(), credencialDto.getUsuario());
    }
    
    public void actualizarFuncionamiento(CredencialDto credencialDto, Boolean funciona) throws CredencialError
    {
        // Validamos que el objeto no sea nulo y que los campos obligatorios no estén vacíos
        if (credencialDto.getUsuario() == null || credencialDto.getUsuario().isEmpty())
        {
            String error = "El usuario es nulo o vacío";

            log.error(error);
            throw new CredencialError(Constant.ERROR_UPDATE_USER_NULL_OR_EMPTY, error);
        }

        // Validamos que la ubicación y la URL no estén vacías
        if ((credencialDto.getUbicacion() == null || credencialDto.getUbicacion().isEmpty()) && (credencialDto.getUrl() == null || credencialDto.getUrl().isEmpty()))
        {
            String error = "La ubicación y la URL son nulas o vacías";

            log.error(error);
            throw new CredencialError(Constant.ERROR_UPDATE_UBICATION_AND_URL_NULL_OR_EMPTY, error);
        }

        CredencialEntity credencialEntity = this.credencialRepository.findByUsuario(credencialDto.getUsuario());
        credencialEntity.setUbicacion(credencialDto.getUbicacion());
        credencialEntity.setDescripcion(credencialDto.getDescripcion());
        credencialEntity.setUrl(credencialDto.getUrl());

        this.credencialRepository.saveAndFlush(credencialEntity);
        log.info("Se ha actualizado correctamente la credencial con usuario: " + credencialDto.getUsuario());
    }

    // Método para obtener la contraseña de una credencial en la base de datos
    public String obtenerPassword(CredencialDto credencialDto) throws CredencialError
    {
        // Validamos que el objeto y el usuario no sean nulos ni vacíos (400)
        if (credencialDto == null || credencialDto.getUsuario() == null || credencialDto.getUsuario().isEmpty())
        {
            String error = "El usuario es nulo o vacío";

            log.error(error);
            throw new CredencialError(Constant.ERROR_GET_PASSWORD_USER_NULL_OR_EMPTY, error);
        }

        boolean tieneUbicacion = credencialDto.getUbicacion() != null && !credencialDto.getUbicacion().isEmpty();
        boolean tieneUrl = credencialDto.getUrl() != null && !credencialDto.getUrl().isEmpty();

        // Validamos que al menos ubicación o URL vengan rellenas (401)
        if (!tieneUbicacion && !tieneUrl)
        {
            String error = "La ubicación y la URL son nulas o vacías. Una de las dos debe estar rellena";

            log.error(error);
            throw new CredencialError(Constant.ERROR_GET_PASSWORD_UBICATION_AND_URL_NULL_OR_EMPTY, error);
        }

        // Validar que la ubicación exista en el sistema (402)
        if (tieneUbicacion)
        {
            String error = "La ubicación no existe";
            log.error(error);
            throw new CredencialError(Constant.ERROR_GET_PASSWORD_UBICATION_NOT_EXIST, error);
        }

        // Buscamos los registros que coincidan con el filtro
        List<CredencialEntity> credencialesEncontradas;

        if (tieneUbicacion && tieneUrl)
        {
            credencialesEncontradas = this.credencialRepository.findByUsuarioAndUbicacionAndUrl(
                    credencialDto.getUsuario(),
                    credencialDto.getUbicacion(),
                    credencialDto.getUrl());
        }
        else if (tieneUbicacion)
        {
            credencialesEncontradas = this.credencialRepository.findByUsuarioAndUbicacion(
                    credencialDto.getUsuario(),
                    credencialDto.getUbicacion());
        }
        else
        {
            credencialesEncontradas = this.credencialRepository.findByUsuarioAndUrl(
                    credencialDto.getUsuario(),
                    credencialDto.getUrl());
        }

        // Si hay más de una coincidencia, lanzamos el error 403
        if (credencialesEncontradas.size() > 1)
        {
            String error = "La búsqueda coincide con más de una credencial";

            log.error(error);
            throw new CredencialError(Constant.ERROR_GET_PASSWORD_MULTIPLE_MATCHES, error);
        }

        // Si no existe ninguna coincidencia, evitamos un IndexOutOfBoundsException
        if (credencialesEncontradas.isEmpty())
        {
            log.info("No se encontró ninguna credencial para el usuario {}", credencialDto.getUsuario());
            return "";
        }


        log.info("Contraseña obtenida correctamente para el usuario {}", credencialDto.getUsuario());
        try
        {
            String passwordDesencriptada = this.passwordEncryptor.desencriptar(credencialesEncontradas.get(0).getPassword());
            log.info("Contraseña obtenida correctamente para el usuario {}", credencialDto.getUsuario());
            return passwordDesencriptada;
        }
        catch (Exception exception)
        {
            log.error("Error al desencriptar la contraseña", exception);
            throw new CredencialError(Constant.ERROR_GET_PASSWORD_INTERNAL_SERVER, "Error al desencriptar la contraseña", exception);
        }
    }
}
