package upeu.edu.pe.mscuentafinancierauniversitaria.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Boleta {
    private Long idBoleta;

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