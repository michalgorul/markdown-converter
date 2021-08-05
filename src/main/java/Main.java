import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import controller.MarkDownConverterController;
import view.MainPage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

//        model.PdfCreator.savePdfAndHtml(model.HtmlBuilder.toHtmlFormat(model.ReadingFromFile.getFileInString()),"index.html",
//                "styles.css","sample.pdf");

        MainPage mainPage = new MainPage("Main Page");
        MarkDownConverterController markDownConverterController = new MarkDownConverterController(mainPage);
        mainPage.setVisible(true);
    }
}
