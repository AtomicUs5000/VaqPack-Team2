/**
 * VP_ErrorLine.java - Custom HBox class.
 */
package vaqpack.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

/**
 * Defines a custom HBox class. This extends HBox and consists of
 * a default constructor and a method to show or hide the component.
 *
 * @author William Dewald (Project Manager, Team-02)
 * @author Fernando Bazan
 * @author Erik Lopez
 * @author Raul Saavedra
 * @author Nathanael Carr
 * @version 1.0
 * @since 1.0
 */
public class VP_ErrorLine extends HBox {
    
    private final VP_Paragraph errorText;
    
    /**
     * Constructor.
     * 
     * @since 1.0
     */
    public VP_ErrorLine() {
        errorText = new VP_Paragraph("", true);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(20);
        this.setPadding(new Insets(2, 0, 2, 2));
        this.getChildren().add(errorText);
        this.setVisible(false);
        this.setManaged(false);
    }
    
    /**
     * Hides the line and collapses it. Resets component text.
     * 
     * @since 1.0
     */
    public void hide() {
        this.setVisible(false);
        this.setManaged(false);
        errorText.setParaText("");
    }
    
    /**
     * Shows the line and expands it.
     * 
     * @since 1.0
     */
    public void show() {
        this.setVisible(true);
        this.setManaged(true);
    }
    
    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/
    
    /**
     * Mutator method.
     * 
     * @param text The text displayed for the component.
     * @since 1.0
     */
    public void setText(String text) {
        errorText.setParaText(text);
    }

}
