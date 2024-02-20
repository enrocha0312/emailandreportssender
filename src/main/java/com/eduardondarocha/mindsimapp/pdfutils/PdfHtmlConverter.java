package com.eduardondarocha.mindsimapp.pdfutils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PdfHtmlConverter {
    private static Document criarDocumentoAPartirDeHTML(String nomeArquivo){
        try {
            String pathname = "D:/Codigos_VSCODE/Programas_didaticos_Java/Projetos_Batch/mindsimapp/src/main/java/com/eduardondarocha/mindsimapp/files/" + nomeArquivo + ".html";
            File inputHTML = new File(pathname);
            Document document = Jsoup.parse(inputHTML, "UTF-8");
            document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
            return document;
        }catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }
    public static String converterHTMLparaPDF(String nomeArquivo){
        Document xhtml = criarDocumentoAPartirDeHTML(nomeArquivo);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String outputpdf = "D:/Codigos_VSCODE/Programas_didaticos_Java/Projetos_Batch/mindsimapp/src/main/java/com/eduardondarocha/mindsimapp/pdfs/" +
                nomeArquivo + dateTimeFormatter.format(LocalDateTime.now()).substring(0,11) + ".pdf";
        try (OutputStream outputStream = new FileOutputStream(outputpdf)) {
            ITextRenderer renderer = new ITextRenderer();
            SharedContext sharedContext = renderer.getSharedContext();
            sharedContext.setPrint(true);
            sharedContext.setInteractive(false);
            renderer.setDocumentFromString(xhtml.html());
            renderer.layout();
            renderer.createPDF(outputStream);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return outputpdf;
    }

}
