/**
 * VP_TextArea.java - Custom TextArea. FILE ID 2700
 */
package vaqpack.components;

import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import vaqpack.VP_GUIController;

/**
 * Defines a custom TextArea class with KeyEvent handling. This extends TextArea and implements
 * EventHandler. 
 *
 * @author William Dewald (Project Manager, Team-02)
 * @author Fernando Bazan
 * @author Erik Lopez
 * @author Raul Saavedra
 * @author Nathanael Carr
 * @version 1.0
 * @since 1.0
 */
public class VP_TextArea extends TextArea implements EventHandler<KeyEvent> {
    private final VP_GUIController controller;
    
    /**
     * Constructor.
     * 
     * @since 1.0
     */
    public VP_TextArea() {
        this.controller = null;
        this.setStyle("-fx-control-inner-background: white");
        setPrefRowCount(4);
        setWrapText(true);
        assignEvents();
    }
    
    /**
     * Constructor.
     * 
     * @param controller Stores the GUI controller for convenience in accessing
     * controller functions or classes accessed by the controller.
     * @since 1.0
     */
    public VP_TextArea(VP_GUIController controller) {
        this.controller = controller;
        this.setStyle("-fx-control-inner-background: white");
        setPrefRowCount(4);
        setWrapText(true);
        assignEvents();
    }
    
    /**
     * Handles the KeyEvent, enforcing the limit, if any. Also clears the red 
     * tint that was applied when invalid.
     * 
     * @param event The key press event
     * @since 1.0
     */
    @Override
    public void handle(KeyEvent event) {
        this.showValid();
        if (controller != null) {
            controller.setChanges(true);
        }
    }
    
    /**
     * Applies a red tint to the field when the caller deems it as invalid.
     * @since 1.0
     */
    public void showInvalid() {
        this.setStyle("-fx-control-inner-background: rgb(255, 210, 210);");
    }
    
    /**
     * Removes the red tint when the caller deems it as valid.
     * @since 1.0
     */
    protected void showValid() {
        this.setStyle("-fx-control-inner-background: white");
    }
    
    /**
     * Applies any event handlers needed. This is done outside of the 
     * constructor to avoid potential problems.
     * @since 1.0
     */
    private void assignEvents() {
        this.setOnKeyReleased(this);
    }
}
