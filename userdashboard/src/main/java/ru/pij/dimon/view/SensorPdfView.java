package ru.pij.dimon.view;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import ru.pij.dimon.dto.SensorDto;
import ru.pij.dimon.dto.SensorValueDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component("reportSensorView")
public class SensorPdfView extends AbstractView {
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {

        SensorDto sensor = (SensorDto) model.get("sensor");
        response.setHeader("Content-Disposition", "attachment; filename="+sensor.getType()+" report.pdf");

        PdfWriter pdfWriter = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(pdfWriter);
        Document pdfDocument = new Document(pdf);

        Table table = new Table(3);
        table.setWidth(500.0f);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.addCell("Number");
        table.addCell("Value, "+sensor.getUnits());
        table.addCell("Date");

        for (SensorValueDto sensorValues:sensor.getSensorValueList()) {
            table.addCell(sensorValues.getId().toString());
            table.addCell(sensorValues.getValue().toString());
            table.addCell(sensorValues.getDate());
        }

        pdfDocument.add(table);
        pdfDocument.close();
    }
}
