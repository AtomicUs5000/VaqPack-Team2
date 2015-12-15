/**
 * VP_DataToHtml.java - HTML convertor. FILE ID 3300
 */
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

/**
 * Converts data nodes to HTML format. Consists of private constructor and
 * static convert() method.
 *
 * @author William Dewald (Project Manager, Team-02)
 * @author Fernando Bazan
 * @author Erik Lopez
 * @author Raul Saavedra
 * @author Nathanael Carr
 * @version 1.0
 * @since 1.0
 */
public final class VP_DataToHtml {

    /**
     * Constructor. Private constructor. Cannot be instantiated.
     * 
     * @since 1.0
     */
    private VP_DataToHtml() {
    }
    
    /**
     * Converts XML/XSL files to HTML.
     * 
     * @param xmlFile XML file to be converted.
     * @param xslFile XSL file to be converted.
     * @param htmlFile HTML file to store converted XML/XSL data.
     * @throws FileNotFoundException
     * @throws TransformerException
     * @throws IOException 
     * @since 1.0
     */
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
