/*-----------------------------------------------------------------------------*
 * VP_FieldLabel.java
 * - Custom label class
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 4100
 *-----------------------------------------------------------------------------*/
package vaqpack;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class VP_FieldLabel extends Label{
    
    /*------------------------------------------------------------------------*
     * VP_FieldLabel()
     * - Default Constructor.
     * - No Parameters
     *------------------------------------------------------------------------*/
    public VP_FieldLabel() {
        this.getStyleClass().add("inputLabel");
        this.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        this.setMaxSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        this.setAlignment(Pos.CENTER_RIGHT);
    }
    
    /*------------------------------------------------------------------------*
     * VP_FieldLabel()
     * - Constructor.
     * - Parameter text is the text for the label.
     *------------------------------------------------------------------------*/
    public VP_FieldLabel(String text) {
        this.getStyleClass().add("inputLabel");
        this.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        this.setMaxSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        this.setAlignment(Pos.CENTER_RIGHT);
        this.setText(text);
    }
    
    /*------------------------------------------------------------------------*
     * VP_FieldLabel()
     * - Constructor.
     * - Parameter text is the text for the label.
     * - Parameter width is the preferred width of the label.
     *------------------------------------------------------------------------*/
    public VP_FieldLabel(String text, double width) {
        this.getStyleClass().add("inputLabel");
        this.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        this.setMaxSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        this.setAlignment(Pos.CENTER_RIGHT);
        this.setText(text);
        this.setPrefWidth(width);
    }
}
