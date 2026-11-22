package es.iesjandula.credenciales.util;

// Constantes de la aplicacion
public class Constant 
{
    // Crear Credencial
    public static final Long EROR_OBJECT_NULL = 400L;
    public static final Long ERROR_USER_NULL_OR_EMPTY = 401L;
    public static final Long ERROR_DESCRIPTION_NULL_OR_EMPTY = 402L;
    public static final Long ERROR_UBICATION_AND_URL_NULL_OR_EMPTY = 403L;
    public static final Long ERROR_UBICATION_NOT_EXIST = 404L;
    public static final Long ERROR_PASSWORD_NULL_OR_EMPTY = 405L;
    public static final Long ERROR_PASSWORD_MALFORMED = 406L;


    // Listar Credenciales
    public static final Long ERROR_LIST_CREDENTIALS_INTERNAL_SERVER = 500L;

    // Eliminar Credenciales
    public static final Long ERROR_DELETE_USER_NULL_OR_EMPTY = 400L;
    public static final Long ERROR_DELETE_UBICATION_AND_URL_NULL_OR_EMPTY = 401L;
    public static final Long ERROR_DELETE_UBICATION_NOT_EXIST = 402L;
    public static final Long ERROR_DELETE_CREDENTIALS_INTERNAL_SERVER = 500L;

    // Actualizar Funcionamiento
    public static final Long ERROR_UPDATE_USER_NULL_OR_EMPTY = 400L;
    public static final Long ERROR_UPDATE_UBICATION_AND_URL_NULL_OR_EMPTY = 401L;
    public static final Long ERROR_UPDATE_UBICATION_NOT_EXIST = 402L;
    public static final Long ERROR_UPDATE_WORKING_STATUS_INTERNAL_SERVER = 500L;

    // Obtener Password
    public static final Long ERROR_GET_PASSWORD_USER_NULL_OR_EMPTY = 400L;
    public static final Long ERROR_GET_PASSWORD_UBICATION_AND_URL_NULL_OR_EMPTY = 401L;
    public static final Long ERROR_GET_PASSWORD_UBICATION_NOT_EXIST = 402L;
    public static final Long ERROR_GET_PASSWORD_MULTIPLE_MATCHES = 403L;
    public static final Long ERROR_GET_PASSWORD_INTERNAL_SERVER = 500L;
    public static final Long ERROR_CREATE_CREDENTIALS_INTERNAL_SERVER = 500L;
}
