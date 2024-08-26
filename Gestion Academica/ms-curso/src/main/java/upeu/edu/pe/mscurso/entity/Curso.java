package upeu.edu.pe.mscurso.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data

public class Curso {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long idCurso;

    private String nombre;
    private String codigo;
    private String descripcion;
    private int creditos;
    private String tipo;
    private String nivel;

}
