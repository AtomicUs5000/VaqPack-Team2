/**
 * VP_Button.java - Custom button class. FILE ID 2000
 */
package vaqpack.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * Custom button class for GUI button nodes. This extends Button
 * and consists of overloaded constructors.
 *
 * @author William Dewald (Project Manager, Team-02)
 * @author Fernando Bazan
 * @author Erik Lopez
 * @author Raul Saavedra
 * @author Nathanael Carr
 * @version 1.0
 * @since 1.0
 */
public class VP_Button extends Button {
    
    /**
     * Constructor.
     *
     * @since 1.0
     */
    public VP_Button() {
        this.getStyleClass().add("genButton");
        this.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        this.setMaxSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
    }

    /**
     * Constructor.
     *
     * @param label The string to set the text label of the button.
     * @since 1.0
     */
    public VP_Button(String label) {
        this.getStyleClass().add("genButton");
        this.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        this.setMaxSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        this.setText(label);
    }

    /**
     * Constructor.
     *
     * @param label The string to set the text label of the button.
     * @param e The EventHandler action event associated with the button.
     * @since 1.0
     */
    public VP_Button(String label, EventHandler<ActionEvent> e) {
        this.getStyleClass().add("genButton");
        this.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        this.setMaxSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        this.setText(label);
        this.setOnAction(e);
    }
    
    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/
}
