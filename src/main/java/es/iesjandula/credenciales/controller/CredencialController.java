package es.iesjandula.credenciales.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.iesjandula.credenciales.dto.CredencialDto;
import es.iesjandula.credenciales.dto.CredencialPassDto;
import es.iesjandula.credenciales.service.CredencialService;
import es.iesjandula.credenciales.util.Constant;
import es.iesjandula.credenciales.util.CredencialError;
import lombok.extern.slf4j.Slf4j;

@RestController 
@RequestMapping("/")
@Slf4j 
public class CredencialController 
{
    // Llamamos al servicio de credenciales para poder realizar operaciones con la base de datos
    @Autowired 
    private CredencialService credencialService;

    // Método para crear una nueva credencial en la base de datos
    @PostMapping()
    public ResponseEntity<?> crearCredencial(@RequestBody CredencialPassDto credencialPassDto)
    {
        try
        {
            this.credencialService.crearCredencial(credencialPassDto);
            return ResponseEntity.status(200).build();
        }
        catch (CredencialError credencialError)
        {
            if (credencialError.getCode() == Constant.EROR_OBJECT_NULL)
            {
                return ResponseEntity.status(400).body(credencialError.getBodyExceptionMessage());
            }
            if (credencialError.getCode() == Constant.ERROR_USER_NULL_OR_EMPTY)
            {
                return ResponseEntity.status(401).body(credencialError.getBodyExceptionMessage());
            }
            if (credencialError.getCode() == Constant.ERROR_DESCRIPTION_NULL_OR_EMPTY)
            {
                return ResponseEntity.status(402).body(credencialError.getBodyExceptionMessage());
            }
            if (credencialError.getCode() == Constant.ERROR_UBICATION_NOT_EXIST)
            {
                return ResponseEntity.status(403).body(credencialError.getBodyExceptionMessage());
            }
            if (credencialError.getCode() == Constant.ERROR_UBICATION_AND_URL_NULL_OR_EMPTY)
            {
                return ResponseEntity.status(404).body(credencialError.getBodyExceptionMessage());
            }
            if (credencialError.getCode() == Constant.ERROR_PASSWORD_NULL_OR_EMPTY)
            {
                return ResponseEntity.status(405).body(credencialError.getBodyExceptionMessage());
            }
            if (credencialError.getCode() == Constant.ERROR_PASSWORD_MALFORMED)
            {
                return ResponseEntity.status(406).body(credencialError.getBodyExceptionMessage());
            }
        }
        catch (Exception exception)
        {
            CredencialError credencialError = new CredencialError(Constant.ERROR_CREATE_CREDENTIALS_INTERNAL_SERVER, "Error interno del servidor", exception);
            log.error("Error interno del servidor", exception);
            return ResponseEntity.status(500).body(credencialError.getBodyExceptionMessage());
        }
        return null;
    }

    // Método para obtener una lista de credenciales de la base de datos
    @GetMapping()
    public ResponseEntity<?> listarCredenciales()
    {
        try
        {
            return ResponseEntity.status(200).body(this.credencialService.listarCredenciales());
        }
        catch (Exception exception)
        {
            CredencialError credencialError = new CredencialError(Constant.ERROR_LIST_CREDENTIALS_INTERNAL_SERVER, "Error interno del servidor", exception);
            return ResponseEntity.status(500).body(credencialError.getBodyExceptionMessage());
        }
    }

    // Método para eliminar una credencial de la base de datos
    @DeleteMapping()
    public ResponseEntity<?> eliminarCredenciales(@RequestBody CredencialDto credencialDto)
    {
        try
        {
            this.credencialService.eliminarCredenciales(credencialDto);
            return ResponseEntity.status(200).build();
        }
        catch (CredencialError credencialError)
        {
            if (Constant.ERROR_DELETE_USER_NULL_OR_EMPTY.equals(credencialError.getCode()))
            {
                return ResponseEntity.status(400).body(credencialError.getBodyExceptionMessage());
            }
            if (Constant.ERROR_DELETE_UBICATION_AND_URL_NULL_OR_EMPTY.equals(credencialError.getCode()))
            {
                return ResponseEntity.status(401).body(credencialError.getBodyExceptionMessage());
            }
            if (Constant.ERROR_DELETE_UBICATION_NOT_EXIST.equals(credencialError.getCode()))
            {
                return ResponseEntity.status(402).body(credencialError.getBodyExceptionMessage());
            }
            return ResponseEntity.status(500).body(credencialError.getBodyExceptionMessage());
        }
        catch (Exception exception)
        {
            CredencialError credencialError = new CredencialError(
                    Constant.ERROR_DELETE_CREDENTIALS_INTERNAL_SERVER,
                    "Error interno del servidor",
                    exception);
            log.error("Error interno del servidor", exception);
            return ResponseEntity.status(500).body(credencialError.getBodyExceptionMessage());
        }
    }

    // Método para actualizar una credencial existente en la base de datos
    @PutMapping()
    public ResponseEntity<?> actualizarFuncionamiento(
            @RequestBody CredencialDto credencialDto,
            @RequestHeader(value = "funciona", required = true) Boolean funciona)
    {
        try
        {
            this.credencialService.actualizarFuncionamiento(credencialDto, funciona);
            return ResponseEntity.status(200).build();
        }
        catch (CredencialError credencialError)
        {
            if (Constant.ERROR_UPDATE_USER_NULL_OR_EMPTY.equals(credencialError.getCode()))
            {
                return ResponseEntity.status(400).body(credencialError.getBodyExceptionMessage());
            }
            if (Constant.ERROR_UPDATE_UBICATION_AND_URL_NULL_OR_EMPTY.equals(credencialError.getCode()))
            {
                return ResponseEntity.status(401).body(credencialError.getBodyExceptionMessage());
            }
            if (Constant.ERROR_UPDATE_UBICATION_NOT_EXIST.equals(credencialError.getCode()))
            {
                return ResponseEntity.status(402).body(credencialError.getBodyExceptionMessage());
            }
            return ResponseEntity.status(500).body(credencialError.getBodyExceptionMessage());
        }
        catch (Exception exception)
        {
            CredencialError credencialError = new CredencialError(
                    Constant.ERROR_UPDATE_WORKING_STATUS_INTERNAL_SERVER,
                    "Error interno del servidor",
                    exception);
            log.error("Error interno del servidor", exception);
            return ResponseEntity.status(500).body(credencialError.getBodyExceptionMessage());
        }
    }

    // Método para obtener la contraseña de una credencial en la base de datos
    @GetMapping("/password")
    public ResponseEntity<?> obtenerPassword(@RequestBody CredencialDto credencialDto)
    {
        try
        {
            String password = this.credencialService.obtenerPassword(credencialDto);
            return ResponseEntity.status(200).body(password);
        }
        catch (CredencialError credencialError)
        {
            if (Constant.ERROR_GET_PASSWORD_USER_NULL_OR_EMPTY.equals(credencialError.getCode()))
            {
                return ResponseEntity.status(400).body(credencialError.getBodyExceptionMessage());
            }
            if (Constant.ERROR_GET_PASSWORD_UBICATION_AND_URL_NULL_OR_EMPTY.equals(credencialError.getCode()))
            {
                return ResponseEntity.status(401).body(credencialError.getBodyExceptionMessage());
            }
            if (Constant.ERROR_GET_PASSWORD_UBICATION_NOT_EXIST.equals(credencialError.getCode()))
            {
                return ResponseEntity.status(402).body(credencialError.getBodyExceptionMessage());
            }
            if (Constant.ERROR_GET_PASSWORD_MULTIPLE_MATCHES.equals(credencialError.getCode()))
            {
                return ResponseEntity.status(403).body(credencialError.getBodyExceptionMessage());
            }
            return ResponseEntity.status(500).body(credencialError.getBodyExceptionMessage());
        }
        catch (Exception exception)
        {
            CredencialError credencialError = new CredencialError(
                    Constant.ERROR_GET_PASSWORD_INTERNAL_SERVER,
                    "Error interno del servidor",
                    exception);
            log.error("Error interno del servidor", exception);
            return ResponseEntity.status(500).body(credencialError.getBodyExceptionMessage());
        }
    }
}