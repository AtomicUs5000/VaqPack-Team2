/**
 * VP_HtmlToPdf.java - Converts HTML to PDF format. FILE ID 3400
 */
package vaqpack.data;

import com.lowagie.text.DocumentException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 * Converts a file from HTML to PDF format.
 *
 * @author William Dewald (Project Manager, Team-02)
 * @author Fernando Bazan
 * @author Erik Lopez
 * @author Raul Saavedra
 * @author Nathanael Carr
 * @version 1.0
 * @since 1.0
 */
public final class VP_HtmlToPdf {

    /**
     * Private constructor. Cannot be instantiated.
     */
    private VP_HtmlToPdf() {
    }

    /**
     * Method to convert HTML file to PDF.
     * 
     * @param htmlFile The File object containing HTML data.
     * @param pdfFile The File object containing PDF data.
     * @throws FileNotFoundException
     * @throws IOException
     * @throws DocumentException 
     * @since 1.0
     */
    public static void convert(File htmlFile, File pdfFile) 
            throws FileNotFoundException, IOException, DocumentException {
        String url = htmlFile.toURI().toURL().toString();
        try (OutputStream outputStream = new FileOutputStream(pdfFile)) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(url);
            renderer.layout();
            renderer.createPDF(outputStream);
            outputStream.flush();
            outputStream.close();
        }
    }
    
    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/
}
