/**
 * VP_TextField.java - Custom TextField. FILE ID 2800
 */
package vaqpack.components;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import vaqpack.VP_GUIController;

/**
 * Defines a custom text field with KeyEvent handling. This extends TextField 
 * and implements EventHandler. Consists of overloaded constructors and methods 
 * to show valid or invalid entries.
 *
 * @author William Dewald (Project Manager, Team-02)
 * @author Fernando Bazan
 * @author Erik Lopez
 * @author Raul Saavedra
 * @author Nathanael Carr
 * @version 1.0
 * @since 1.0
 */
public class VP_TextField extends TextField implements EventHandler<KeyEvent> {
    private final VP_GUIController controller;
    private int limit;

    /**
     * Constructor.
     * 
     * @param columns Sets the preferred width, in columns, of the field.
     * @param limit limit Allowed character limit of the field. 
     * <ul>
     * <li>&lt;= 0 - No text limit</li>
     * </ul>
     * @since 1.0
     */
    public VP_TextField(int columns, int limit) {
        //-------- Initialization Start ----------\\
        this.controller = null;
        this.limit = limit;
        //-------- Initialization End ------------\\
        this.setMinSize(TextField.USE_PREF_SIZE, TextField.USE_PREF_SIZE);
        this.setMaxSize(TextField.USE_PREF_SIZE, TextField.USE_PREF_SIZE);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setStyle("-fx-control-inner-background: white");
        this.setPrefColumnCount(columns);
        this.assignEvents();
    }

    /**
     * Constructor.
     * 
     * @param columns Sets the preferred width, in columns, of the field.
     * @param limit limit Allowed character limit of the field. 
     * <ul>
     * <li>&lt;= 0 - No text limit</li>
     * </ul>
     * @param controller Stores the GUI controller for convenience in accessing
     * controller functions or classes accessed by the controller.
     * @since 1.0
     */
    public VP_TextField(int columns, int limit, VP_GUIController controller) {
        //-------- Initialization Start ----------\\
        this.controller = controller;
        this.limit = limit;
        //-------- Initialization End ------------\\
        this.setMinSize(TextField.USE_PREF_SIZE, TextField.USE_PREF_SIZE);
        this.setMaxSize(TextField.USE_PREF_SIZE, TextField.USE_PREF_SIZE);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setStyle("-fx-control-inner-background: white");
        this.setPrefColumnCount(columns);
        this.assignEvents();
    }

    /**
     * Handles the KeyEvent. Enforces the limit, if any. Also clears the red 
     * tint that was applied when invalid.
     * 
     * @param event 
     * @since 1.0
     */
    @Override
    public void handle(KeyEvent event) {
        this.showValid();
        if (controller != null) {
            controller.setChanges(true);
        }
        if (limit > 0) {
            String text = this.getText();
            try {
                if (text.length() > limit) {
                    this.setText(text.substring(0, limit));
                    this.positionCaret(limit);
                }
            }
            catch (Exception e) {
                // tab or enter pressed, just ignore
            }
        }
    }

    /**
     * Applies a red tint to the field when the caller deems it as invalid.
     * 
     * @since 1.0
     */
    public void showInvalid() {
        this.setStyle("-fx-control-inner-background: rgb(255, 210, 210);");
    }
    
    /**
     * Removes the red tint when the caller deems it as valid.
     * 
     * @since 1.0
     */
    public void showValid() {
        this.setStyle("-fx-control-inner-background: white");
    }

    /**
     * Applies any event handlers needed. Done outside of the constructor to 
     * avoid potential problems.
     * 
     * @since 1.0
     */
    private void assignEvents() {
        this.setOnKeyReleased(this);
    }

    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/
    /**
     * Accessor method.
     * 
     * @return The text field limit.
     * @since 1.0
     */
    protected int getLimit() {
        return limit;
    }

    /**
     * Mutator method.
     * 
     * @param limit The text field limit.
     * @since 1.0
     */
    protected void setLimit(int limit) {
        this.limit = limit;
    }
}
