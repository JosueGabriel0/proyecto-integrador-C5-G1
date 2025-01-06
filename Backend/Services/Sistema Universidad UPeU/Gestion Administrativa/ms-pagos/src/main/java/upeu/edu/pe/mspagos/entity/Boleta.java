package upeu.edu.pe.mspagos.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import upeu.edu.pe.mspagos.dto.Estudiante;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Boleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBoleta;

    @OneToOne
    @JoinColumn(name = "pago_id")
    @JsonBackReference
    private Pago pago; // Relación con el pago asociado

    private String nombreCliente;
    private String documentoDeIdentidad;
    private String direccion;

    private String numeroBoleta;
    private LocalDate fechaEmision;
    private String descripcionBoleta;
    private String tipoDocumento;
    private String sucursal;
    private String organizacionDeVentas;
    private String tipoMoneda;

    private String codigoProductoServicio;
    private String descripcionProductoServicio;
    private String unidadDeMedida;
    private BigDecimal cantidad;
    private BigDecimal valorUnitario;
    private BigDecimal valorDescuento;
    private BigDecimal valorTotal;

    private BigDecimal operacionGravada;
    private BigDecimal operacionInafecta;
    private BigDecimal operacionExonerada;
    private BigDecimal operacionGratuita;
    private BigDecimal descuentosTotales;
    private BigDecimal igv;
    private BigDecimal precioVentaTotal;

    private String boletaUrl;
}