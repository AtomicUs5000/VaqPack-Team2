/**
 * VP_PasswordField.java - Everything involving the password field GUI nodes.
 * FILE ID 2600
 */
package vaqpack.components;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import vaqpack.VP_GUIController;

/**
 * Defines a custom password field. This extends PasswordField and consists of
 * overloaded constructors and implements EventHandler.
 *
 * @author William Dewald (Project Manager, Team-02)
 * @author Fernando Bazan
 * @author Erik Lopez
 * @author Raul Saavedra
 * @author Nathanael Carr
 * @version 1.0
 * @since 1.0
 */
public class VP_PasswordField extends PasswordField implements EventHandler<KeyEvent> {
    private final VP_GUIController controller;
    private final int limit,
            minimum;
    private final Label strengthLabel;

    /**
     * Constructor.
     * 
     * @param columns Sets the preferred width, in columns, of the field.
     * @param limit Allowed character limit of the field. 
     * <ul>
     * <li>&lt;= 0 - No text limit</li>
     * </ul>
     * @param minimum Defines password strength requirements.
     * <ul>
     * <li>&lt;= 0 - No minimum requirement and no password strength checking.</li>
     * </ul>
     * @param strengthLabel Label that notes the strength if label is not null.
     * @since 1.0
     */
    public VP_PasswordField(int columns, int limit, int minimum, Label strengthLabel) {
        //-------- Initialization Start ----------\\
        this.controller = null;
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
    
    /**
     * Constructor.
     * 
     * @param columns Sets the preferred width, in columns, of the field.
     * @param limit Allowed character limit of the field. 
     * <ul>
     * <li>&lt;= 0 - No text limit</li>
     * </ul>
     * @param minimum Defines password strength requirements.
     * <ul>
     * <li>&lt;= 0 - No minimum requirement and no password strength checking.</li>
     * </ul>
     * @param strengthLabel Label that notes the strength if label is not null.
     * @param controller Stores the GUI controller for convenience in accessing
     * controller functions or classes accessed by the controller.
     * @since 1.0
     */
    public VP_PasswordField(int columns, int limit, int minimum, 
            Label strengthLabel, VP_GUIController controller) {
        //-------- Initialization Start ----------\\
        this.controller = controller;
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

    /**
     * Handles the KeyEvent. Enforces the limit, if any. Calculates the strength
     * of the password if minimum &gt; 0 and if strenthLabel exists. Clears red
     * tint if applied due to invalid entry.
     * 
     * @param event The KeyEvent to be handled
     * @since 1.0
     */
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
        if (controller != null) {
            controller.setChanges(true);
        }
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
                    strengthLabel.setText("Strength: Unacceptable");
                    break;
                case 1:
                    strengthLabel.setText("Strength: Weak");
                    break;
                case 2:
                    strengthLabel.setText("Strength: Mediocre");
                    break;
                case 3:
                    strengthLabel.setText("Strength: Good");
                    break;
                case 4:
                    strengthLabel.setText("Strength: Strong");
                    break;
                case 5:
                    strengthLabel.setText("Strength: Very Strong");
                    break;
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
}
