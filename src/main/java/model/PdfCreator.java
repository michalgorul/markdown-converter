package model;

import com.itextpdf.html2pdf.HtmlConverter;

import java.io.FileOutputStream;
import java.io.IOException;

public class PdfCreator {

    public static void savePdfAndHtml(String s, String htmlPath, String cssPath, String pdfPath) throws IOException {
        HtmlBuilder.saveHtmlFile(htmlPath, s, cssPath);

        HtmlConverter.convertToPdf(s, new FileOutputStream(pdfPath));

    }

    public static void savePdf(String s, String pdfPath) throws IOException {

        HtmlConverter.convertToPdf(s, new FileOutputStream(pdfPath));

    }

}
