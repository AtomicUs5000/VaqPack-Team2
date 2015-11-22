/*-----------------------------------------------------------------------------*
 * VP_HtmlToPdf.java
 * - Converts HTML to PDF format
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 3000
 *-----------------------------------------------------------------------------*/
package vaqpack;

import com.lowagie.text.DocumentException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.xhtmlrenderer.pdf.ITextRenderer;

public final class VP_HtmlToPdf {

    /*------------------------------------------------------------------------*
     * VP_HtmlToPdf()
     * - Private constructor. Cannot be instantiated
     * No Parameters.
     *------------------------------------------------------------------------*/
    private VP_HtmlToPdf() {
    }

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
