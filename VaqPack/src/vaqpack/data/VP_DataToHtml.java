/*-----------------------------------------------------------------------------*
 * VP_DataToHtml.java
 * - Converts data nodes to HTML format
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 3300
 *-----------------------------------------------------------------------------*/
package vaqpack.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public final class VP_DataToHtml {

    /*------------------------------------------------------------------------*
     * VP_DataToHtml()
     * - Private constructor. Cannot be instantiated
     * No Parameters.
     *------------------------------------------------------------------------*/
    private VP_DataToHtml() {
    }
    
    public static void convert(File xmlFile, File xslFile, File htmlFile) 
            throws FileNotFoundException, TransformerException, IOException {
        //-------- Initialization Start ----------\\
        TransformerFactory transformFactory = TransformerFactory.newInstance();
        StreamSource xslStream = new StreamSource(xslFile),
                xmlStream = new StreamSource(xmlFile);
        //-------- Initialization End ------------\\
        
        try (FileOutputStream outputStream = new FileOutputStream(htmlFile)) {
            StreamResult htmlStream = new StreamResult(outputStream);
            Transformer transformer = transformFactory.newTransformer(xslStream);
            
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(xmlStream, htmlStream);
            outputStream.flush();
        }
    }

    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/
}
