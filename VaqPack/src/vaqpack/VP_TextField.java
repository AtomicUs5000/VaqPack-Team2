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
package vaqpack;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class VP_TextField extends TextField implements EventHandler<KeyEvent> {

    private int limit;

    /*---------------------------------------------------------------------*
     * VP_TextField()
     * - Constructor.
     * - Parameter limit is the allowed character limit for this TextField.
     *   A limit of 0 or less means to not limit the text.
     *---------------------------------------------------------------------*/
    protected VP_TextField(int columns, int limit) {
        //-------- Initialization Start ----------\\
        this.limit = limit;
        //-------- Initialization End ------------\\
        this.setMinSize(TextField.USE_PREF_SIZE, TextField.USE_PREF_SIZE);
        this.setMaxSize(TextField.USE_PREF_SIZE, TextField.USE_PREF_SIZE);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setStyle("-fx-control-inner-background: white");
        this.setPrefColumnCount(columns);
        this.assignEvents();
    }

    /*---------------------------------------------------------------------*
     * handle()
     * - Handles the KeyEvent, enforcing the limit, if any.
     *   Also clears the red tint that was applied when invalid.
     *---------------------------------------------------------------------*/
    @Override
    public void handle(KeyEvent event) {
        this.showValid();
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

    /*------------------------------------------------------------------------*
     * showInvalid()
     * - Applies a red tint to the field when the caller deems it as invalid.
     * - No Paramters
     * - No Return
     *------------------------------------------------------------------------*/
    protected void showInvalid() {
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

    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/
    protected int getLimit() {
        return limit;
    }

    protected void setLimit(int limit) {
        this.limit = limit;
    }
}
