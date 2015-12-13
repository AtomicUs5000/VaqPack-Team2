/**
 * VP_Paragraph.java - Defines a paragraph within a page division. FILE ID 2500
 */
package vaqpack.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;

/**
 * Defines a paragraph section within a page division. This extends Label and 
 * consists of overloaded constructors.
 *
 * @author William Dewald (Project Manager, Team-02)
 * @author Fernando Bazan
 * @author Erik Lopez
 * @author Raul Saavedra
 * @author Nathanael Carr
 * @version 1.0
 * @since 1.0
 */
public class VP_Paragraph extends Label {

    /**
     * Constructor.
     * 
     * @since 1.0
     */
    public VP_Paragraph() {
        this.setWrapText(true);
        this.setPadding(new Insets(10, 50, 10, 50));
        this.getStyleClass().add("paragraph");
    }

    /**
     * Constructor.
     * 
     * @param text String sets the text for the paragraph.
     * @since 1.0
     */
    public VP_Paragraph(String text) {
        this.setWrapText(true);
        this.setPadding(new Insets(10, 50, 10, 50));
        this.setText("\t" + text);
        this.getStyleClass().add("paragraph");
    }
    
    /**
     * Constructor. 
     * 
     * @param text Sets the text for the paragraph.
     * @param isError Applies error style to paragraph.
     * @since 1.0
     */
    public VP_Paragraph(String text, boolean isError) {
        this.setWrapText(true);
        this.setPadding(new Insets(10, 50, 10, 50));
        this.setText("\t" + text);
        if (isError) {
            this.getStyleClass().add("errorLabel");
        } else {
            this.getStyleClass().add("paragraph");
        }
    }

    /**
     * Adds an indent to the first line of paragraph text.
     * 
     * @param text Sets the text for the paragraph.
     * @since 1.0
     */
    public void setParaText(String text) {
        this.setText("\t" + text);
    }
    
    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/
}
