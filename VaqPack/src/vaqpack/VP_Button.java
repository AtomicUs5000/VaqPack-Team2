/*-----------------------------------------------------------------------------*
 * VP_Button.java
 * - Custom button class
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 4000
 *-----------------------------------------------------------------------------*/
package vaqpack;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class VP_Button extends Button {

    /*------------------------------------------------------------------------*
     * VP_Button()
     * - Default Constructor.
     * - No Parameters
     *------------------------------------------------------------------------*/
    public VP_Button() {
        this.getStyleClass().add("genButton");
        this.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        this.setMaxSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
    }

    /*------------------------------------------------------------------------*
     * VP_Button()
     * - Constructor.
     * - Parameter string label is the text label of the button.
     *------------------------------------------------------------------------*/
    public VP_Button(String label) {
        this.getStyleClass().add("genButton");
        this.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        this.setMaxSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        this.setText(label);
    }

    /*------------------------------------------------------------------------*
     * VP_Button()
     * - Constructor.
     * - Parameter string label is the text label of the button.
     * - Parameter event handler e is the action event associated with
     *   the button.
     *------------------------------------------------------------------------*/
    public VP_Button(String label, EventHandler<ActionEvent> e) {
        this.getStyleClass().add("genButton");
        this.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        this.setMaxSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        this.setText(label);
        this.setOnAction(e);
    }
}
