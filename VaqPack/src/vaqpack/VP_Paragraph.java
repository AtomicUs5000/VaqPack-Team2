/*-----------------------------------------------------------------------------*
 * VP_Paragraph.java
 * - Custom Label to be used as a paragraph within Page Divisions
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 4400
 *-----------------------------------------------------------------------------*/
package vaqpack;

import javafx.geometry.Insets;
import javafx.scene.control.Label;

public class VP_Paragraph extends Label {

    /*------------------------------------------------------------------------*
     * VP_Paragraph()
     * - Default Constructor.
     * - No parameters
     *------------------------------------------------------------------------*/
    public VP_Paragraph() {
        this.setWrapText(true);
        this.setPadding(new Insets(10, 50, 10, 50));
        this.getStyleClass().add("paragraph");
    }

    /*------------------------------------------------------------------------*
     * VP_Paragraph()
     * - Constructor.
     * - Parameter string text sets the text for the paragraph.
     *------------------------------------------------------------------------*/
    public VP_Paragraph(String text) {
        this.setWrapText(true);
        this.setPadding(new Insets(10, 50, 10, 50));
        this.setText("\t" + text);
        this.getStyleClass().add("paragraph");
    }
    
    /*------------------------------------------------------------------------*
     * VP_Paragraph()
     * - Constructor.
     * - Parameter string text sets the text for the paragraph.
     * - Parameter boolean isError sets the style to bold red if true.
     *------------------------------------------------------------------------*/
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

    /*------------------------------------------------------------------------*
     * setParaText()
     * - Separate from setText(), this add an indent on the first line.
     * - Parameter string text sets the text for the paragraph.
     * - No return
     *------------------------------------------------------------------------*/
    protected void setParaText(String text) {
        this.setText("\t" + text);
    }
    
    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/
}
