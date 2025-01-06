package upeu.edu.pe.mspagos.service;

import upeu.edu.pe.mspagos.entity.Boleta;
import upeu.edu.pe.mspagos.entity.Factura;

public interface PdfService {
    public void generarPdfBoleta(Boleta boleta) throws Exception;
    public void generarPdfFactura(Factura factura) throws Exception;
}
