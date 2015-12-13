/**
 * VP_FieldLabel.java - Custom label class. FILE ID 2200
 */
package vaqpack.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

/**
 * Custom label class for GUI label nodes. This extends label and consists of 
 * overloaded constructors.
 *
 * @author William Dewald (Project Manager, Team-02)
 * @author Fernando Bazan
 * @author Erik Lopez
 * @author Raul Saavedra
 * @author Nathanael Carr
 * @version 1.0
 * @since 1.0
 */
public class VP_FieldLabel extends Label{
    
    /**
     * Constructor.
     * 
     * @since 1.0
     */
    public VP_FieldLabel() {
        this.getStyleClass().add("inputLabel");
        this.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        this.setMaxSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        this.setAlignment(Pos.CENTER_RIGHT);
    }
    
    /**
     * Constructor.
     * 
     * @param text The string to set the text for the label.
     * @since 1.0
     */
    public VP_FieldLabel(String text) {
        this.getStyleClass().add("inputLabel");
        this.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        this.setMaxSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        this.setAlignment(Pos.CENTER_RIGHT);
        this.setText(text);
    }
    
    /**
     * Constructor.
     * 
     * @param text The string to set the text for the label.
     * @param width The preferred width of the label.
     * @since 1.0
     */
    public VP_FieldLabel(String text, double width) {
        this.getStyleClass().add("inputLabel");
        this.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        this.setMaxSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        this.setAlignment(Pos.CENTER_RIGHT);
        this.setText(text);
        this.setPrefWidth(width);
    }
}
