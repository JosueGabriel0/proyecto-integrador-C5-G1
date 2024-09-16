package upeu.edu.pe.msadministracion.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Entity
@Data
public class Aministracion {

    //Atributos Basicos de Administracion
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAdministracion;

    private String nombreDepartamento;
    private String responsable;
    private String correoContacto;
    private String telefonoContacto;

    //Atributos Relacionados Con El Personal
    @ElementCollection
    @CollectionTable(name = "empleados")
    @Column
    private List<Long> empleados;
    private int numeroEmpleados;
    private Map<String, String> rolesEmpleados;
    private List<Long> supervisores;

    //Atributos de Gestion Financiera
    private BigDecimal presupuestoAnual;
    private BigDecimal gastoMensual;
    private int transaccionesRecientes;
    private LocalDate fechaCierreFiscal;
    private String cuentaBancaria;
    private String bancoAsociado;

    //Atributos de Gestion de infraestuctura
    private List<Long> instalaciones;
    private String ubicacionOficina;
    private Map<String, String> recursoInfraestuctura;
    private List<String> contratosVigentes;
    private List<LocalDate> fechasMantenimiento;

    //Atributos de Gestion Academica
    private List<Long> cursosAdministrados;
    private int numeroEstudiantesInscritos;
    private Map<Long, String> programasAcademicos;
    private List<Long> conveniosColaboracion;

    //Atributos de Gestion Documental
    private List<String> documentosOficiales;
    private LocalDate fechaUltimaActualizacionDocumentos;
    private String responsableDocumentacion;
    private Map<String, String> permisoAccesoDocumentos;

    //Atributos de Comunicacion
    private String canalComunicacionProncipal;
    private List<String> canalesSecundarios;
    private List<String> notificacionesPendientes;
    private Map<LocalDate, String> recordatoriosImportantes;

    //Atributos Avanzados
    private boolean estadoActivo;
    private LocalDate fechaCreacion;
    private LocalDate fechaUltimaEvaluacion;
    private List<String> indicadoresDesempeno;
    private String politicaInterna;
    private List<String> riesgosIdentificados;
    private Map<String, String> estrategiasMitigacion;

}
