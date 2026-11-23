package es.iesjandula.credenciales.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredencialPassDto extends CredencialDto
{
    private String password;
}
