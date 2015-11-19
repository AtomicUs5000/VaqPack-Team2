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
 *-----------------------------------------------------------------------------*/
package vaqpack;

import javafx.event.EventHandler;
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
        this.limit = limit;
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
        this.setStyle("-fx-control-inner-background: white");
        if (limit > 0) {
            String text = this.getText();
            if (text.length() > limit) {
                this.setText(text.substring(0, limit));
                this.positionCaret(limit);
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
     * assignEvents()
     * - Applies any event handlers needed. This is done outside of the
     *   constructor to avoid potential problems.
     * - No Paramters
     * - No Return
     *------------------------------------------------------------------------*/
    private void assignEvents() {
        this.setOnKeyReleased(this);
    }

    /*------------------------------------------------------------------------*
     * Setters and Getters
     *------------------------------------------------------------------------*/
    protected int getLimit() {
        return limit;
    }

    protected void setLimit(int limit) {
        this.limit = limit;
    }
}
