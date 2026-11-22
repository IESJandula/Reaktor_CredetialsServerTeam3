package es.iesjandula.credenciales.util;

import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.HashMap;


public class CredencialError extends Exception 
{
    private Long code;

    public CredencialError(Long code, String message) {
        super(message);
        this.code = code;
    }

    public CredencialError(Long code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }

    public Long getCode() {
        return code;
    }

    /**
	 * mapa para devolver la excepcion
	 * @return mapa con mensaje, error y en caso de tener excepcion tambien devuelve la excepcion
	 */
	public Map<String,String> getBodyExceptionMessage()
	{
		Map<String,String> messageMap = new HashMap<String,String>() ;
		
		messageMap.put("code", String.valueOf(this.code)) ;
		messageMap.put("message", this.getMessage()) ;
		
		if (this.getCause() != null)
		{
			String stackTrace = ExceptionUtils.getStackTrace(this.getCause()) ;
			messageMap.put("exception", stackTrace) ;
		}
		
		return messageMap ;
    }    
}
