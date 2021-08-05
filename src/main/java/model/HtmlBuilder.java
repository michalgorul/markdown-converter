package model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Pattern;

public class HtmlBuilder {

    public static final String headerFirst = """
            <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Markdown to HTML</title>
                    <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
                """;

    public static final String headerSecond = """
                 </head>
                 <body>
                  """;


            public static final String footer = """
                </body>
            </html>""";

    /**
     * This method saves string to file in html format
     *
     * @param htmlPath path to file we want to save
     * @param s    string to put into file
     * @throws IOException basic exception when path does not exist
     */
    public static void saveHtmlFile(String htmlPath, String s, String cssPath) throws IOException {

        String markDownFileContains = ReadingFromFile.getFileInString("src/main/resources/test.txt");

        String style = HtmlBuilder.styleFromFile(cssPath);

        Path resultPath = Paths.get(htmlPath);
        s = headerFirst + style + headerSecond + s + footer;
        byte[] strToBytes = s.getBytes();

        Files.write(resultPath, strToBytes);

    }

    /**
     * This method saves string to file in html format
     *
     * @param html html file in string
     * @param path path to save file
     * @throws IOException basic exception when path does not exist
     */
    public static void saveHtmlFileFromString(String html, String path) throws IOException {
        Files.write(Paths.get(path), html.getBytes());
    }



    public static String getHtmlString (String markdown, String css) throws IOException {
        String html = toHtmlFormat(markdown);
        return headerFirst + css + headerSecond + html + footer;
    }

    /**
     * This method takes whole line and modifies it to html format
     *
     * @param s line to modify
     * @return modified line in html format
     */
    public static String headingsAndHorizontal(String s) throws IOException {

        Scanner scan = new Scanner(s);
        StringBuilder modified = new StringBuilder();

        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if (line.startsWith("# "))
                modified.append("<h1>").append(line.substring(2)).append("</h1>\n");

            else if (line.startsWith("## "))
                modified.append("<h2>").append(line.substring(3)).append("</h2>\n");

            else if (line.startsWith("### "))
                modified.append("<h3>").append(line.substring(4)).append("</h3>\n");

            else if (line.startsWith("#### "))
                modified.append("<h4>").append(line.substring(5)).append("</h4>\n");

            else if (line.startsWith("##### "))
                modified.append("<h5>").append(line.substring(6)).append("</h5>\n");

            else if (line.startsWith("###### "))
                modified.append("<h6>").append(line.substring(7)).append("</h6>\n");

            else if (line.startsWith("***") || line.startsWith("___") || line.startsWith("---"))
                modified.append("<hr/> \n");
            else
                modified.append(line).append('\n');
        }
        return modified.toString();
    }

    /**
     * This method will modify string adding some emphasis in html
     *
     * @param s string to put bold, italic etc
     * @return modified string
     */
    public static String emphasis(String s) {

        return s.replaceAll("[*]{3}([\\S|\\s]*)[*]{3}", "<strong><em>$1</em></strong>")
                .replaceAll("[_]{3}([\\S|\\s]*)[_]{3}", "<strong><em>$1</em></strong>")
                .replaceAll("[*]{2}([\\S|\\s]*)[*]{2}", "<strong>$1</strong>")
                .replaceAll("[_]{2}([\\S|\\s]*)[_]{2}", "<strong>$1</strong>")
                .replaceAll("[~]{2}([\\S|\\s]*)[~]{2}", "<del>$1</del>")
                .replaceAll("[*]([\\S|\\s]*)[*]", "<em>$1</em>")
                .replaceAll("[_]([\\S|\\s]*)[_]", "<em>$1</em>");


    }

    /**
     * This method takes string and modifies it to blockquote in html format
     *
     * @param s string to modify
     * @return modified string in html format
     */
    public static String blockquote(String s) {

        String replacement = "<blockquote>\n<p>\n$1\n</p>\n</blockquote>\n";
        Pattern p = Pattern.compile("> ([\\S|\\s][^\\n]*)[\\n]");
        String buf = s;

        while (true) {

            s = s.replaceAll(p.pattern(), replacement);
            if (s.equals(buf))
                break;
            else
                buf = s;
        }
        return s;
    }

    /**
     * This method takes string and modifies it to unordered list in html format
     *
     * @param s string to modify
     * @return modified string in html format
     */
    public static String unorderedList(String s) {
        return s.replaceAll("([+] |[*] |[-] )([\\S|\\s][^\\n]*)[\\n]", "<liu>\n$2</liu>\n");
    }

    /**
     * This method takes string and modifies it to ordered list in html format
     *
     * @param s string to modify
     * @return modified string in html format
     */
    public static String orderedList(String s) {
        return s.replaceAll("(\\d. )([\\S|\\s][^\\n]*)[\\n]", "<lio>\n$2</lio>\n");
    }

    /**
     * This method takes string and modifies <lio> to <li> it to ordered list in html format
     *
     * @param s string to modify
     * @return modified string in html format
     */
    public static String correctOrderedLists(String s) {

        Scanner scan = new Scanner(s);
        StringBuilder modified = new StringBuilder();
        boolean foundBeginningOfOrdered = false;
        boolean orderedMayEnd = false;

        while (scan.hasNextLine()) {
            String line = scan.nextLine();

            if (line.startsWith("<lio>") && !foundBeginningOfOrdered) {
                foundBeginningOfOrdered = true;
                modified.append("<ol>\n").append(line).append('\n');
            } else if (foundBeginningOfOrdered && line.startsWith("</lio>")) {
                orderedMayEnd = true;
                modified.append(line).append("\n");
            } else if (foundBeginningOfOrdered && orderedMayEnd && !line.startsWith("<lio>")
            ) {
                orderedMayEnd = false;
                foundBeginningOfOrdered = false;

                modified.append("</ol>\n").append(line).append("\n");

            } else if (foundBeginningOfOrdered && line.equals("")) {
                modified.append("</ol>").append("\n").append("\n");
            } else if (!line.startsWith("</lio>")) {
                orderedMayEnd = false;
                modified.append(line).append("\n");
            } else
                modified.append(line).append('\n');

        }
        s = modified.toString();
        s = s.replaceAll("<lio>", "<li>");
        s = s.replaceAll("</lio>", "</li>");
        return s;
    }

    /**
     * This method takes string and modifies <liu> to <li> it to ordered list in html format
     *
     * @param s string to modify
     * @return modified string in html format
     */
    public static String correctUnorderedLists(String s) {

        Scanner scan = new Scanner(s);
        StringBuilder modified = new StringBuilder();
        boolean foundBeginningOfUnordered = false;
        boolean unorderedMayEnd = false;
        boolean lastPosition = false;


        while (scan.hasNextLine()) {
            String line = scan.nextLine();

            if (line.startsWith("<liu>") && !foundBeginningOfUnordered) {
                foundBeginningOfUnordered = true;
                modified.append("<ul>\n").append(line).append('\n');
            } else if (foundBeginningOfUnordered && line.startsWith("</liu>")) {
                unorderedMayEnd = true;
                modified.append(line).append("\n");
            } else if (foundBeginningOfUnordered && unorderedMayEnd && !line.startsWith("<liu>")
                    && !line.startsWith("<lio>") && !line.startsWith("<ol>") && !line.startsWith("</ol>")) {

                unorderedMayEnd = false;
                foundBeginningOfUnordered = false;
                modified.append(line).append("</ul>").append("\n");
            } else if (foundBeginningOfUnordered && line.equals("")) {
                foundBeginningOfUnordered = false;
                modified.append("</ul>").append("\n");
            } else if (!line.startsWith("</liu>") && !line.startsWith("</li>")) {
                unorderedMayEnd = false;
                modified.append(line).append("\n");
            } else if (line.startsWith("</ol>")) {
                lastPosition = true;
                modified.append(line).append('\n');
            } else if (line.startsWith("\n") && lastPosition) {
                lastPosition = false;
                modified.append("</ul>").append('\n');
            } else
                modified.append(line).append('\n');

        }

        s = modified.toString();
        s = s.replaceAll("<liu>", "<li>");
        s = s.replaceAll("</liu>", "</li>");
        return s;
    }

    /**
     * This method will modify string adding code blocks in html
     *
     * @param s string to modify
     * @return modified string
     */
    public static String inlineAndBlockCode(String s) {
        return s.replaceAll("```\\n([\\s|\\S]*)\\n```", "<pre><code>$1</code></pre>")
                .replaceAll("`([\\s|\\S]*)`", "<code>$1</code>");
    }

    /**
     * This method will modify string adding image in html
     *
     * @param s string to modify
     * @return modified string
     */
    public static String images(String s) {
        return s.replaceAll("!\\[(\\S*)]\\((\\S*)\\)", "<p><img src=\"$2\" alt=\"$1\"></p>\n");
    }

    public static String links(String s) {
        return s.replaceAll("\\[(.*?)]\\((\\S*)", "<p><a href=\"$2\">$1</a></p>\n");
    }

    public static String styleFromFile(String path) {
        StringBuilder style = new StringBuilder("\n<style>");

        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                style.append(myReader.nextLine()).append('\n');
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        style.append("</style>\n");

        return  style.toString();
    }

    public static String styleFromString(String s) {

        return "\n<style>" + s + "</style>\n";
    }

    public static String toHtmlFormat(String s) throws IOException {

        s = HtmlBuilder.blockquote(s);
        s = HtmlBuilder.unorderedList(s);
        s = HtmlBuilder.orderedList(s);
        s = HtmlBuilder.headingsAndHorizontal(s);
        s = HtmlBuilder.emphasis(s);
        s = HtmlBuilder.correctOrderedLists(s);
        s = HtmlBuilder.correctUnorderedLists(s);
        s = HtmlBuilder.inlineAndBlockCode(s);
        s = HtmlBuilder.images(s);
        s = HtmlBuilder.links(s);

        return s;
    }

}
