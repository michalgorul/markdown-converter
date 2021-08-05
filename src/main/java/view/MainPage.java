package view;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainPage extends JFrame{

    private JPanel mainPane;
    private JButton getMarkDownFromFileButton;
    private JButton getHTMLFromFileButton;
    private JButton getCSSFromFileButton;
    private JTextArea markdownTextArea;
    private JEditorPane htmlPreviewEditorPane;
    private JLabel markDownTextLabel;
    private JLabel cssStylesLabel;
    private JLabel htmlPreviewLabel;
    private JTextArea cssTextArea;
    private JButton saveToHTMLButton;
    private JButton saveToPDFButton;
    private JButton checkPreviewButton;
    private JButton openInBrowserButton;
    private final HTMLEditorKit kit = new HTMLEditorKit();
    StyleSheet styleSheet = kit.getStyleSheet();

    public MainPage(String title) {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPane);

        this.setTitle(title);
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height - 32);
        this.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height - 32));
        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width/2)-(this.getSize().width/2),
                (Toolkit.getDefaultToolkit().getScreenSize().height/2)-(this.getSize().height/2));

        this.htmlPreviewEditorPane.setEditable(false);
        // add an html editor kit
        this.htmlPreviewEditorPane.setEditorKit(this.kit);
        // create a document, set it on the pane, then add the html
        Document doc = this.kit.createDefaultDocument();
        this.htmlPreviewEditorPane.setDocument(doc);
        // create some simple html as a string
        String htmlEntryString = """
                <html>
                <body>
                <h1>Welcome!</h1>
                <h2>This is an H2 header</h2>
                <p>This is some sample text</p>
                </body>
                """;
        this.htmlPreviewEditorPane.setText(htmlEntryString);
        this.setLocationRelativeTo(null);
        this.pack();
    }


    public void setMarkdownTextAreaText(String s){
        this.markdownTextArea.setText(s);
    }

    public String getMarkdownTextAreaText(){
        return this.markdownTextArea.getText();
    }

    public String getCssTextAreaText(){
        return this.cssTextArea.getText();
    }

    public void setHtmlPreviewEditorPane(String s){
        this.htmlPreviewEditorPane.setText(s);
    }

    public String getHtmlPreviewEditorPane(){
        return this.htmlPreviewEditorPane.getText();
    }

    public void setCssTextArea(String s){
        this.cssTextArea.setText(s);

    }


    /**
     * This method will execute a method in the Controller named actionPerformed
     * if the getMarkDownFromFileButton is clicked
     * @param listenForMarkDownFromFileButton object handling getMDFromFileButton clicked
     */
    public void addGetMarkDownFromFileListener(ActionListener listenForMarkDownFromFileButton){

        getMarkDownFromFileButton.addActionListener(listenForMarkDownFromFileButton);
    }

    /**
     * This method will execute a method in the Controller named actionPerformed
     * if the getCssFromFileButton is clicked
     * @param listenForGetCSSFromFileButton object handling getCSSFromFileButton clicked
     */
    public void addGetCssFromFileListener(ActionListener listenForGetCSSFromFileButton){

        getCSSFromFileButton.addActionListener(listenForGetCSSFromFileButton);
    }

    /**
     * This method will execute a method in the Controller named actionPerformed
     * if the getHtmlFromFileButton is clicked
     * @param listenForGetHtmlFromFileButton object handling getHtmlFromFileButton clicked
     */
    public void addGetHtmlFromFileListener(ActionListener listenForGetHtmlFromFileButton){

        getHTMLFromFileButton.addActionListener(listenForGetHtmlFromFileButton);
    }

    /**
     * This method will execute a method in the Controller named actionPerformed
     * if the CheckPreviewButton is clicked
     * @param listenForCheckPreviewButton object handling CheckPreviewButton clicked
     */
    public void addCheckPreviewListener(ActionListener listenForCheckPreviewButton){

        checkPreviewButton.addActionListener(listenForCheckPreviewButton);
    }

    /**
     * This method will execute a method in the Controller named actionPerformed
     * if the openInBrowserButton is clicked
     * @param listenForOpenInBrowserButton object handling openInBrowserButton clicked
     */
    public void addOpenInBrowserListener(ActionListener listenForOpenInBrowserButton){

        openInBrowserButton.addActionListener(listenForOpenInBrowserButton);
    }

    /**
     * This method will execute a method in the Controller named actionPerformed
     * if the saveToHTMLButton is clicked
     * @param listenForSaveToHTMLButton object handling saveToHTMLButton clicked
     */
    public void addSaveToHTMLListener(ActionListener listenForSaveToHTMLButton){

        saveToHTMLButton.addActionListener(listenForSaveToHTMLButton);
    }

    /**
     * This method will execute a method in the Controller named actionPerformed
     * if the saveToPdfButton is clicked
     * @param listenForSaveToPdfButton object handling saveToPdfButton clicked
     */
    public void addSaveToPdfListener(ActionListener listenForSaveToPdfButton){

        saveToPDFButton.addActionListener(listenForSaveToPdfButton);
    }

}
