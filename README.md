# MarkDown Converter
MarkDown to html and pdf converter. Simple GUI was written using java swing

## GUI design

![image](https://user-images.githubusercontent.com/43811151/128382880-741836ef-117b-4d8c-ab1f-483741959276.png)

## Sample ActionListener class

```java
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
```

## Sample code snippet responsible for conversions

```java
public static String images(String s) {
    return s.replaceAll("!\\[(\\S*)]\\((\\S*)\\)", "<p><img src=\"$2\" alt=\"$1\"></p>\n");
}
```
