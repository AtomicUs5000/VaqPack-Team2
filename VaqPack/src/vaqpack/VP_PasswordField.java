/*-----------------------------------------------------------------------------*
 * VP_PasswordField.java
 * - Custom PasswordField with built in KeyEvent handling
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 2800
 *-----------------------------------------------------------------------------*/
package vaqpack;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;

public class VP_PasswordField extends PasswordField implements EventHandler<KeyEvent> {

    private final int limit,
            minimum;
    private final Label strengthLabel;

    /*---------------------------------------------------------------------*
     * VP_PasswordField()
     * - Constructor.
     * - Parameter limit is the allowed character limit for this TextField.
     *   A limit of 0 or less means to not limit the text.
     *   A minimum of 0 or less means no minimum requirement and no strength
     *   checking for this pawwrod field.
     * - Parameter strengthLabel is the label that notes the strength if this
     *   label is not null.
     *---------------------------------------------------------------------*/
    protected VP_PasswordField(int columns, int limit, int minimum, Label strengthLabel) {
        //-------- Initialization Start ----------\\
        this.limit = limit;
        this.minimum = minimum;
        this.strengthLabel = strengthLabel;
        //-------- Initialization End ------------\\

        this.setMinSize(PasswordField.USE_PREF_SIZE, PasswordField.USE_PREF_SIZE);
        this.setMaxSize(PasswordField.USE_PREF_SIZE, PasswordField.USE_PREF_SIZE);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setStyle("-fx-control-inner-background: white");
        this.setPrefColumnCount(columns);
        this.assignEvents();
    }

    /*---------------------------------------------------------------------*
     * handle()
     * - Handles the KeyEvent, enforcing the limit, if any.
     *   Calculates the strength of the password if minimum > 0 and if the
     *   strengthLabel exists.
     *   Also clears the red tint that was applied when invalid.
     *---------------------------------------------------------------------*/
    @Override
    public void handle(KeyEvent event) {
        //-------- Initialization Start ----------\\
        String text = this.getText();
        int strength = 0, length = text.length();
        boolean hasUpper = false,
                hasLower = false,
                hasAlpha = false,
                hasNumb = false,
                hasSpecial = false;
        //-------- Initialization End ------------\\

        this.showValid();
        if (limit > 0) {
            if (length > limit) {
                text = text.substring(0, limit);
                this.setText(text);
                this.positionCaret(limit);
                length = limit;
            }
        }
        if (minimum > 0 && strengthLabel != null) {
            if (length >= minimum) {
                for (int i = 0; i < length; i++) {
                    char thisChar = text.charAt(i);
                    if (Character.isUpperCase(thisChar)) {
                        hasUpper = true;
                        hasAlpha = true;
                    } else if (Character.isLowerCase(thisChar)) {
                        hasLower = true;
                        hasAlpha = true;
                    } else if (Character.isDigit(thisChar)) {
                        hasNumb = true;
                    } else if (Character.isLetter(thisChar)) {
                        hasAlpha = true;
                    } else {
                        hasSpecial = true;
                    }
                }
                strength += 1;
                if (hasUpper && hasLower) {
                    strength += 1;
                }
                if (hasNumb && hasAlpha) {
                    strength += 1;
                }
                if (hasSpecial) {
                    strength += 1;
                }
                if (length >= (int) ((limit + minimum) / 2)) {
                    strength += 1;
                }
                if (length == limit) {
                    strength += 1;
                }
            }
            switch (strength) {
                case 0:
                    strengthLabel.setText("Srength: Unacceptable");
                    break;
                case 1:
                    strengthLabel.setText("Srength: Weak");
                    break;
                case 2:
                    strengthLabel.setText("Srength: Mediocre");
                    break;
                case 3:
                    strengthLabel.setText("Srength: Good");
                    break;
                case 4:
                    strengthLabel.setText("Srength: Strong");
                    break;
                case 5:
                    strengthLabel.setText("Srength: Very Strong");
                    break;
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
     * - Removies the red tint when the caller deems it as valid.
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
}
