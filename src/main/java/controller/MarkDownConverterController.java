package controller;

import com.itextpdf.html2pdf.HtmlConverter;
import model.HtmlBuilder;
import model.PdfCreator;
import model.ReadingFromFile;
import view.MainPage;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class MarkDownConverterController {

    /**
     * The view object from MVC model
     */
    private final MainPage mainPage;

    public MarkDownConverterController(MainPage mainPage) {
        this.mainPage = mainPage;

        // Tell the View that when ever the GetMarkDownFromFile button is clicked to execute
        // the actionPerformed method in the GetMDFromFileListener inner class
        this.mainPage.addGetMarkDownFromFileListener(new GetMDFromFileListener());

        // Tell the View that when ever the GetCssFromFile button is clicked to execute
        // the actionPerformed method in the GetCSSFromFileListener inner class
        this.mainPage.addGetCssFromFileListener(new GetCSSFromFileListener());

        // Tell the View that when ever the GetHtmlFromFile button is clicked to execute
        // the actionPerformed method in the GetHtmlFromFileListener inner class
        this.mainPage.addGetHtmlFromFileListener(new GetHtmlFromFileListener());

        // Tell the View that when ever the CheckPreview button is clicked to execute
        // the actionPerformed method in the CheckPreviewListener inner class
        this.mainPage.addCheckPreviewListener(new CheckPreviewListener());

        // Tell the View that when ever the GetHtmlFromFile button is clicked to execute
        // the actionPerformed method in the GetHtmlFromFileListener inner class
        this.mainPage.addOpenInBrowserListener(new OpenInBrowserListener());

        // Tell the View that when ever the SaveHtmlFile button is clicked to execute
        // the actionPerformed method in the SaveHtmlFileListener inner class
        this.mainPage.addSaveToHTMLListener(new SaveHtmlFileListener());

        // Tell the View that when ever the SaveHtmlFile button is clicked to execute
        // the actionPerformed method in the SaveHtmlFileListener inner class
        this.mainPage.addSaveToPdfListener(new SavePdfFileListener());
    }

    public class GetMDFromFileListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                JFileChooser c = new JFileChooser();
                String path = "";
                // Demonstrate "Open" dialog:
                int rVal = c.showOpenDialog(null);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    path = c.getCurrentDirectory().toString() + "\\" + c.getSelectedFile().getName();
                }
                mainPage.setMarkdownTextAreaText(ReadingFromFile.getFileInString(path));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    public class GetCSSFromFileListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                JFileChooser c = new JFileChooser("user.home");
                c.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.css", "css");
                c.addChoosableFileFilter(filter);
                String path = "";
                // Demonstrate "Open" dialog:
                int rVal = c.showOpenDialog(null);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    path = c.getCurrentDirectory().toString() + "\\" + c.getSelectedFile().getName();
                }
                mainPage.setCssTextArea(ReadingFromFile.getFileInString(path));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    public class GetHtmlFromFileListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                JFileChooser c = new JFileChooser("user.home");
                c.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.html", "html");
                c.addChoosableFileFilter(filter);
                String path = "";
                // Demonstrate "Open" dialog:
                int rVal = c.showOpenDialog(null);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    path = c.getCurrentDirectory().toString() + "\\" + c.getSelectedFile().getName();
                }
                mainPage.setHtmlPreviewEditorPane(ReadingFromFile.getFileInString(path));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public class CheckPreviewListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                mainPage.setHtmlPreviewEditorPane(htmlToPreview());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    public class OpenInBrowserListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Path resultPath = Paths.get("src/main/resources/temp.html");

            byte[] strToBytes = mainPage.getHtmlPreviewEditorPane().getBytes();

            try {
                Files.write(resultPath, strToBytes);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            File htmlFile = new File(resultPath.toString());
            try {
                Desktop.getDesktop().browse(htmlFile.toURI());
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            try {
                Files.deleteIfExists(resultPath);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    public class SaveHtmlFileListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser c = new JFileChooser("user.home");
            String path = "";
            // Demonstrate "Save" dialog:
            int rVal = c.showSaveDialog(null);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                path = c.getCurrentDirectory().toString() + "\\" + c.getSelectedFile().getName();
                try {
                    HtmlBuilder.saveHtmlFileFromString(mainPage.getHtmlPreviewEditorPane(), path);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        }
    }

    public class SavePdfFileListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser c = new JFileChooser("user.home");
            String path = "";
            // Demonstrate "Save" dialog:
            int rVal = c.showSaveDialog(null);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                path = c.getCurrentDirectory().toString() + "\\" + c.getSelectedFile().getName();
                try {
                    mainPage.setHtmlPreviewEditorPane(htmlToPreview());
                    PdfCreator.savePdf(mainPage.getHtmlPreviewEditorPane(), path);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        }
    }

    private String htmlToPreview() throws IOException {
        String markdown = mainPage.getMarkdownTextAreaText();
        String cssStyle = "\n<style>\n" + mainPage.getCssTextAreaText() + "\n</style>\n";
        return HtmlBuilder.getHtmlString(markdown, cssStyle);
    }
}
