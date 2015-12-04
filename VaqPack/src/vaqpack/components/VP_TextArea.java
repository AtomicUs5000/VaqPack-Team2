/*-----------------------------------------------------------------------------*
 * VP_TextField.java
 * - Custom TextField with built in KeyEvent handling
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 2700
 *-----------------------------------------------------------------------------*/
package vaqpack.components;

import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import vaqpack.VP_GUIController;

public class VP_TextArea extends TextArea implements EventHandler<KeyEvent> {
    private final VP_GUIController controller;
    
    public VP_TextArea() {
        this.controller = null;
        this.setStyle("-fx-control-inner-background: white");
        setPrefRowCount(4);
        setWrapText(true);
        assignEvents();
    }
    
    public VP_TextArea(VP_GUIController controller) {
        this.controller = controller;
        this.setStyle("-fx-control-inner-background: white");
        setPrefRowCount(4);
        setWrapText(true);
        assignEvents();
    }
    
    /*---------------------------------------------------------------------*
     * handle()
     * - Handles the KeyEvent, enforcing the limit, if any.
     *   Also clears the red tint that was applied when invalid.
     *---------------------------------------------------------------------*/
    @Override
    public void handle(KeyEvent event) {
        this.showValid();
        if (controller != null) {
            controller.setChanges(true);
        }
    }
    
    /*------------------------------------------------------------------------*
     * showInvalid()
     * - Applies a red tint to the field when the caller deems it as invalid.
     * - No Paramters
     * - No Return
     *------------------------------------------------------------------------*/
    public void showInvalid() {
        this.setStyle("-fx-control-inner-background: rgb(255, 210, 210);");
    }
    
    /*------------------------------------------------------------------------*
     * showValid()
     * - Removes the red tint when the caller deems it as valid.
     * - No Paramters
     * - No Return
     *------------------------------------------------------------------------*/
    protected void showValid() {
        this.setStyle("-fx-control-inner-background: white");
    }
    
    /*------------------------------------------------------------------------*
     * assignEvents()
     * - Applies any event handlers needed. This is done outside of the
     *   constructor to avoid potential problems.
     * - No Paramters
     * - No Return
     *------------------------------------------------------------------------*/
    private void assignEvents() {
        this.setOnKeyReleased(this);
    }
}
