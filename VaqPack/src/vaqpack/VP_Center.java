/*-----------------------------------------------------------------------------*
 * VP_Center.java
 * - Everything involving the center view of the GUI (wizard)
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 1900
 *-----------------------------------------------------------------------------*/
package vaqpack;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class VP_Center extends StackPane {

    private final VP_GUIController controller;
    private final VP_TextField loginEmail,
            resetEmail,
            regLoginAccess,
            resetCode;
    private final Label loginError,
            resetError,
            accessInstructions,
            resetInstructions1,
            resetInstructions2,
            resetPassStrengthLabel;
    private final VP_PasswordField loginPass,
            resetNewPass,
            resetNewPassConfirm;
    private final HBox accessLine,
            loginButtonLine,
            resetNewPassLine,
            resetNewPassConfirmLine,
            resetCodeLine,
            resetButLine;
    private final Button submitResetBtn;

    /*------------------------------------------------------------------------*
     * VP_Center()
     * - Constructor.
     *------------------------------------------------------------------------*/
    protected VP_Center(VP_GUIController controller) {
        this.controller = controller;
        this.setId("center");
        loginError = new Label();
        resetError = new Label();
        accessInstructions = new Label();
        resetInstructions1 = new Label();
        resetInstructions2 = new Label();
        resetPassStrengthLabel = new Label();
        accessLine = new HBox();
        loginButtonLine = new HBox();
        resetNewPassLine = new HBox();
        resetNewPassConfirmLine = new HBox();
        resetCodeLine = new HBox();
        resetButLine = new HBox();
        submitResetBtn = new Button();
        regLoginAccess = new VP_TextField(16, 16);
        resetCode = new VP_TextField(16, 16);
        loginEmail = new VP_TextField(32, 254);
        resetEmail = new VP_TextField(32, 254);
        loginPass = new VP_PasswordField(32, 32, 0, null);
        resetNewPass = new VP_PasswordField(32, 32, 12, resetPassStrengthLabel);
        resetNewPassConfirm = new VP_PasswordField(32, 32, 0, null);
    }

    /*------------------------------------------------------------------------*
     * showScreen()
     * - Makes all center stackpane levels invisible except for the desired one.
     * - Parameter screenNumber is the desired stackpane level to show.
     * - No Return
     *------------------------------------------------------------------------*/
    protected void showScreen(int screenNumber) {
        for (int i = 0; i < getChildren().size(); i++) {
            getChildren().get(i).setVisible(false);
        }
        getChildren().get(screenNumber).setVisible(true);
    }

    private void resetLoginRegForms() {
        loginEmail.setText("");
        loginEmail.setDisable(false);
        loginEmail.setEditable(true);
        loginPass.setText("");
        loginPass.setDisable(false);
        loginPass.setEditable(true);
        loginButtonLine.setVisible(true);
        loginButtonLine.setManaged(true);
        loginError.setText("");
        loginError.setVisible(false);
        loginError.setManaged(false);
        accessInstructions.setText("Enter the access code that was emailed to you when you registered below.");
        accessInstructions.setVisible(false);
        accessInstructions.setManaged(false);
        accessLine.setVisible(false);
        accessLine.setManaged(false);
    }

    private void resetResetPasswordForms() {
        resetEmail.setText("");
        resetEmail.setDisable(false);
        resetEmail.setEditable(true);
        submitResetBtn.setDisable(false);
        resetError.setText("");
        resetError.setVisible(false);
        resetError.setManaged(false);
        resetInstructions1.setVisible(true);
        resetInstructions1.setManaged(true);
        resetInstructions2.setVisible(false);
        resetInstructions2.setManaged(false);
        resetNewPassLine.setVisible(false);
        resetNewPassLine.setManaged(false);
        resetPassStrengthLabel.setText("");
        resetPassStrengthLabel.setVisible(false);
        resetPassStrengthLabel.setManaged(false);
        resetNewPassConfirmLine.setVisible(false);
        resetNewPassConfirmLine.setManaged(false);
        resetCodeLine.setVisible(false);
        resetCodeLine.setManaged(false);
        resetButLine.setVisible(false);
        resetButLine.setManaged(false);
        resetCode.setText("");
        resetNewPass.setText("");
        resetNewPassConfirm.setText("");
    }

    /*------------------------------------------------------------------------*
     * Subclasses
     *------------------------------------------------------------------------*/
    /*------------------------------------------------------------------------*
     * Subclass LoginAction
     *------------------------------------------------------------------------*/
    protected class ForgotPassAction implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            resetLoginRegForms();
            showScreen(1);
        }
    }

    protected class LoginAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String cred[] = new String[]{loginEmail.getText().toLowerCase(),
                loginPass.getText()};
            accessInstructions.setText("Enter the access code that was emailed to you when you registered below.");
            try {
                int loginStatus = controller.getDataM().userLogin(cred);
                if (loginStatus == 0) {
                    // user does not exist in database
                    loginError.setText("The email address and/or password is incorrect. Please try again.");
                    loginError.setManaged(true);
                    loginError.setVisible(true);
                    loginEmail.showInvalid();
                    loginPass.showInvalid();
                } else if (loginStatus == 1) {
                    // user login successful
                    resetLoginRegForms();
                    showScreen(2);
                } else if (loginStatus == 2) {
                    // user needs to enter registration code
                    loginEmail.setDisable(true);
                    loginEmail.setEditable(false);
                    loginPass.setDisable(true);
                    loginPass.setEditable(false);
                    loginError.setText("");
                    loginError.setVisible(false);
                    loginError.setManaged(false);
                    loginButtonLine.setVisible(false);
                    loginButtonLine.setManaged(false);
                    accessInstructions.setVisible(true);
                    accessInstructions.setManaged(true);
                    accessLine.setVisible(true);
                    accessLine.setManaged(true);
                }
            } catch (SQLException ex) {
                Logger.getLogger(VP_Center.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(VP_Center.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(VP_Center.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass SubmitAccessAction
     *------------------------------------------------------------------------*/
    protected class SubmitAccessAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String cred[] = new String[]{loginEmail.getText().toLowerCase(),
                loginPass.getText(), regLoginAccess.getText()};
            try {
                boolean accessStatus = controller.getDataM().verifyRegAccess(cred);
                if (accessStatus) {
                    resetLoginRegForms();
                    showScreen(2);
                } else {
                    loginError.setText("The registration code is incorrect. Please try again.");
                    accessInstructions.setText("Enter the access code that was emailed to you below.");
                    loginError.setVisible(true);
                    loginError.setManaged(true);
                    regLoginAccess.showInvalid();
                }
            } catch (SQLException ex) {
                Logger.getLogger(VP_Center.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(VP_Center.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(VP_Center.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass CancelAccessAction
     *------------------------------------------------------------------------*/
    protected class CancelAccessAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            resetLoginRegForms();
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass SubmitResetPassCode
     *------------------------------------------------------------------------*/
    protected class SubmitResetPassCode implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            int resetStatus = 0;
            String[] cred = {resetEmail.getText(), resetNewPass.getText(),
                resetNewPassConfirm.getText(), resetCode.getText()};
            if (cred[1].length() < 12) {
                resetNewPass.showInvalid();
                resetNewPassConfirm.showInvalid();
                resetError.setText("The new password is not long enough.");
                resetError.setVisible(true);
                resetError.setManaged(true);
            } else if (!cred[1].equals(cred[2])) {
                resetNewPass.showInvalid();
                resetNewPassConfirm.showInvalid();
                resetError.setText("The passwords do not match.");
                resetError.setVisible(true);
                resetError.setManaged(true);
            } else {
                try {
                    resetStatus = controller.getDataM().resetPass(cred);
                    if (resetStatus == 2) {
                        accessInstructions.setText("Your password was reset. Login with your new password.");
                        accessInstructions.setVisible(true);
                        accessInstructions.setManaged(true);
                        resetResetPasswordForms();
                        showScreen(0);
                    } else if (resetStatus == 1) {
                        resetCode.showInvalid();
                        resetError.setText("The code has expired. Cancel and start the reset process over again.");
                        resetError.setVisible(true);
                        resetError.setManaged(true);
                    } else if (resetStatus == 0) {
                        resetCode.showInvalid();
                        resetError.setText("The code is incorrect. Please try again.");
                        resetError.setVisible(true);
                        resetError.setManaged(true);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(VP_Center.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(VP_Center.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(VP_Center.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass SubmitResetAction
     *------------------------------------------------------------------------*/
    protected class SubmitResetAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            int userStatus;
            try {
                userStatus = controller.getDataM().findUser(resetEmail.getText());
                if (userStatus == 0) {
                    resetEmail.showInvalid();
                    resetError.setText("The provided user does not exists in VaqPack. "
                            + "If the email is correct, you need to register a new account.");
                    resetError.setVisible(true);
                    resetError.setManaged(true);
                } else if (userStatus == 1) {
                    resetEmail.showInvalid();
                    resetError.setText("The password for this email has recently been reset. "
                            + "Passwords may only be reset once every 24 hours.");
                    resetError.setVisible(true);
                    resetError.setManaged(true);
                } else {
                    submitResetBtn.setDisable(true);
                    resetEmail.setDisable(true);
                    resetEmail.setEditable(false);
                    resetInstructions1.setVisible(false);
                    resetInstructions1.setManaged(false);
                    resetInstructions2.setVisible(true);
                    resetInstructions2.setManaged(true);
                    resetNewPassLine.setVisible(true);
                    resetNewPassLine.setManaged(true);
                    resetPassStrengthLabel.setText("");
                    resetPassStrengthLabel.setVisible(true);
                    resetPassStrengthLabel.setManaged(true);
                    resetNewPassConfirmLine.setVisible(true);
                    resetNewPassConfirmLine.setManaged(true);
                    resetCodeLine.setVisible(true);
                    resetCodeLine.setManaged(true);
                    resetButLine.setVisible(true);
                    resetButLine.setManaged(true);
                }
            } catch (SQLException ex) {
                Logger.getLogger(VP_Center.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass CancelResetAction
     *------------------------------------------------------------------------*/
    protected class CancelResetAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            resetResetPasswordForms();
            showScreen(0);
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass ResendAccessAction
     *------------------------------------------------------------------------*/
    protected class ResendAccessAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String cred[] = new String[]{loginEmail.getText().toLowerCase(),
                loginPass.getText(), regLoginAccess.getText()};
            try {
                controller.getDataM().resendAccess(cred);
                accessInstructions.setText("A new access code has been emailed and "
                        + "you should receive it shortly. Enter the code below.\n"
                        + "If you do not receive an email, verify that you have "
                        + "entered in the correct email address.");
            } catch (SQLException ex) {
                Logger.getLogger(VP_Center.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(VP_Center.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(VP_Center.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /*------------------------------------------------------------------------*
     * Setters and Getters
     *------------------------------------------------------------------------*/
    protected VP_TextField getLoginEmail() {
        return loginEmail;
    }

    protected VP_PasswordField getLoginPass() {
        return loginPass;
    }

    protected VP_TextField getRegLoginAccess() {
        return regLoginAccess;
    }

    protected Label getAccessInstructions() {
        return accessInstructions;
    }

    protected Label getLoginError() {
        return loginError;
    }

    protected HBox getAccessLine() {
        return accessLine;
    }

    protected HBox getLoginButtonLine() {
        return loginButtonLine;
    }

    protected VP_TextField getResetEmail() {
        return resetEmail;
    }

    protected Button getSubmitResetBtn() {
        return submitResetBtn;
    }

    protected Label getResetError() {
        return resetError;
    }

    protected Label getResetInstructions1() {
        return resetInstructions1;
    }

    protected Label getResetInstructions2() {
        return resetInstructions2;
    }

    protected VP_TextField getResetCode() {
        return resetCode;
    }

    protected VP_PasswordField getResetNewPass() {
        return resetNewPass;
    }

    protected VP_PasswordField getResetNewPassConfirm() {
        return resetNewPassConfirm;
    }

    protected HBox getResetNewPassLine() {
        return resetNewPassLine;
    }

    protected HBox getResetNewPassConfirmLine() {
        return resetNewPassConfirmLine;
    }

    protected HBox getResetCodeLine() {
        return resetCodeLine;
    }

    protected HBox getResetButLine() {
        return resetButLine;
    }

    protected Label getResetPassStrengthLabel() {
        return resetPassStrengthLabel;
    }

}
