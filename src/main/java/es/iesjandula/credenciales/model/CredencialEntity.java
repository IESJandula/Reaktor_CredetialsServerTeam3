package es.iesjandula.credenciales.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "credencial")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class CredencialEntity 
{
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column 
    private String usuario;

    @Column 
    private String descripcion;

    @Column 
    private String ubicacion;

    @Column 
    private String url;

    @Column 
    private String password;
}
