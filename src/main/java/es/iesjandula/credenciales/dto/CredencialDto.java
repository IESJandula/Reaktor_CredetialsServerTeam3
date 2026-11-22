package es.iesjandula.credenciales.dto;

import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredencialDto
{
	private Long id;
	private String usuario;
	private String descripcion;
	private String ubicacion;
	private String url;
}
