package upeu.edu.pe.mspagos.service.impl;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import org.springframework.stereotype.Service;
import upeu.edu.pe.mspagos.entity.Boleta;
import upeu.edu.pe.mspagos.entity.Factura;
import upeu.edu.pe.mspagos.service.PdfService;

import java.io.FileOutputStream;

@Service
public class PdfServiceImpl implements PdfService {

    @Override
    public void generarPdfBoleta(Boleta boleta) throws Exception {
        String destino = "src/main/resources/static/files/boleta_" + boleta.getNumeroBoleta() + ".pdf";
        PdfWriter writer = new PdfWriter(new FileOutputStream(destino));
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Ruta al logo
        String logoPath = "src/main/resources/static/images/logo_upeu.png"; // Asegúrate de tener el logo aquí.
        Image logo = new Image(com.itextpdf.io.image.ImageDataFactory.create(logoPath));
        logo.setWidth(100);
        logo.setHeight(100);

        // Crear tabla para el encabezado
        Table encabezado = new Table(UnitValue.createPercentArray(new float[]{1, 2, 1}));
        encabezado.setWidth(UnitValue.createPercentValue(100));

        // Primera celda: Logo
        encabezado.addCell(logo);

        // Segunda celda: Información de la universidad
        Paragraph infoUniversidad = new Paragraph()
                .add("UNIVERSIDAD PERUANA UNION\n")
                .add("Carretera Arequipa Km 6 - Chullunquia, Puno - San Román - Juliaca\n")
                .add("Teléfono: (051) 51-\n")
                .setFontSize(10)
                .setTextAlignment(TextAlignment.CENTER);
        encabezado.addCell(infoUniversidad);

        // Tercera celda: Información de la boleta
        Paragraph infoBoleta = new Paragraph()
                .add("R.U.C. 20138122256\n")
                .add("BOLETA DE VENTA ELECTRÓNICA\n")
                .add(boleta.getNumeroBoleta() + "\n")
                .setFontSize(10)
                .setTextAlignment(TextAlignment.RIGHT)
                .setFontColor(new DeviceRgb(0, 0, 0));
        encabezado.addCell(infoBoleta);

        // Agregar encabezado al documento
        document.add(encabezado);

        // Cerrar el documento
        document.close();

        System.out.println("PDF generado exitosamente en: " + destino);
    }

    public void generarPdfFactura(Factura factura) throws Exception {
        String destino = "src/main/resources/static/files/factura_" + factura.getNumeroFactura() + ".pdf";
        PdfWriter writer = new PdfWriter(new FileOutputStream(destino));
        Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer));

        // Título
        document.add(new Paragraph("Factura").setBold().setFontSize(16));
        document.add(new Paragraph("Número de Factura: " + factura.getNumeroFactura()));
        document.add(new Paragraph("Fecha de Emisión: " + factura.getFechaEmision()));

        // Tabla con detalles
        Table table = new Table(2);
        table.addCell("Descripción");
        //table.addCell(factura.getDescripcion());
        table.addCell("Subtotal");
        //table.addCell(factura.getSubtotal().toString());
        table.addCell("Impuestos");
        //table.addCell(factura.getImpuestos().toString());
        table.addCell("Total");
        //table.addCell(factura.getTotal().toString());

        document.add(table);

        document.close();
    }
}