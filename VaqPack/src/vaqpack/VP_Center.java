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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

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
     * build()
     * - Builds the GUI center which holds most of the content.
     *   Called in a task, to build in the background.
     *   The center is a stackpane, where each layer is an individual page
     *   Layer 0 = Login Screen
     *   Layer 1 = Reset Password Screen
     *   Layer 2 = Temporary Testing grounds
     * - No Paramters
     * - No Return
     *------------------------------------------------------------------------*/
    protected void build() {
        getChildren().addAll(buildLoginScreen(),
                buildResetPasswordScreen(), buildTestScreen());
        showScreen(0);
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

    /*------------------------------------------------------------------------*
     * buildLoginScreen()
     * - Creates the user login screen. Called by buildCenter().
     * - No parameters.
     * - Returns a scroller that gets applied to a center stackpane level.
     *------------------------------------------------------------------------*/
    private ScrollPane buildLoginScreen() {
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox();
        Label loginLabel = new Label("LOGIN"),
                loginEmailLabel = new Label("email:"),
                loginPassLabel = new Label("password:"),
                passForgotLabel = new Label("forgot your password?"),
                regCodeLabel = new Label("code:");
        Button loginBtn = new Button("Login"),
                enterAccessBtn = new Button("Submit"),
                accessCancelBtn = new Button("Cancel"),
                accessResendBtn = new Button("Resend Code");
        VBox loginBox = new VBox();
        loginBox.setSpacing(10);
        loginBox.getStyleClass().add("formDivision");
        loginBox.setPadding(new Insets(10, 10, 10, 10));
        loginLabel.getStyleClass().add("pageHeader");
        HBox emailLine = new HBox();
        emailLine.setAlignment(Pos.CENTER_LEFT);
        emailLine.setSpacing(10);
        loginEmailLabel.getStyleClass().add("inputLabel");
        loginEmailLabel.setPrefWidth(80);
        loginEmailLabel.setAlignment(Pos.CENTER_RIGHT);
        loginEmail.setAlignment(Pos.CENTER_LEFT);
        emailLine.getChildren().addAll(loginEmailLabel, loginEmail);
        HBox passLine = new HBox();
        passLine.setAlignment(Pos.CENTER_LEFT);
        passLine.setSpacing(10);
        loginPassLabel.getStyleClass().add("inputLabel");
        loginPassLabel.setPrefWidth(80);
        loginPassLabel.setAlignment(Pos.CENTER_RIGHT);
        loginPass.setAlignment(Pos.CENTER_LEFT);
        passLine.getChildren().addAll(loginPassLabel, loginPass);
        loginError.setWrapText(true);
        loginError.getStyleClass().add("errorLabel");
        loginError.setAlignment(Pos.CENTER);
        loginError.prefWidthProperty().bind(loginBox.prefWidthProperty());
        loginError.setVisible(false);
        loginError.setManaged(false);
        loginButtonLine.setPadding(new Insets(0, 0, 0, 32));
        loginButtonLine.setAlignment(Pos.CENTER_LEFT);
        loginButtonLine.setSpacing(50);
        loginBtn.getStyleClass().add("genButton");
        loginBtn.setOnAction(new LoginAction());
        passForgotLabel.getStyleClass().add("clickable");
        passForgotLabel.setOnMouseClicked(new ForgotPassAction());
        loginButtonLine.getChildren().addAll(loginBtn, passForgotLabel);
        accessInstructions.setText("Enter the access code that was "
                + "emailed to you when you registered below.");
        accessInstructions.setWrapText(true);
        accessInstructions.getStyleClass().add("inputLabel");
        accessInstructions.setAlignment(Pos.CENTER);
        accessInstructions.prefWidthProperty().bind(loginBox.prefWidthProperty());
        accessInstructions.setVisible(false);
        accessInstructions.setManaged(false);
        accessLine.setAlignment(Pos.CENTER_LEFT);
        accessLine.setSpacing(20);
        regCodeLabel.getStyleClass().add("inputLabel");
        enterAccessBtn.getStyleClass().add("genButton");
        enterAccessBtn.setOnAction(new SubmitAccessAction());
        accessCancelBtn.getStyleClass().add("genButton");
        accessCancelBtn.setOnAction(new CancelAccessAction());
        accessResendBtn.getStyleClass().add("genButton");
        accessResendBtn.setOnAction(new ResendAccessAction());
        accessLine.getChildren().addAll(regCodeLabel, regLoginAccess,
                enterAccessBtn, accessCancelBtn, accessResendBtn);
        accessLine.setVisible(false);
        accessLine.setManaged(false);
        loginBox.getChildren().addAll(loginLabel, emailLine, passLine, loginError,
                loginButtonLine, accessInstructions, accessLine);
        screenContent.getChildren().addAll(loginBox);
        screenContent.setSpacing(30);
        screenContent.setPadding(new Insets(20, 20, 20, 20));
        screen.setContent(screenContent);
        screen.setPannable(true);
        return screen;
    }

    /*------------------------------------------------------------------------*
     * buildResetPasswordScreen()
     * - Creates the reset password screen. Called by buildCenter().
     * - No parameters.
     * - Returns a scroller that gets applied to a center stackpane level.
     *------------------------------------------------------------------------*/
    private ScrollPane buildResetPasswordScreen() {
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox(),
                forgotPassBox = new VBox();
        Label resetLabel = new Label("RESET PASSWORD"),
                resetEmailLabel = new Label("email:"),
                resetNewPassLabel = new Label("new password:"),
                resetNewPassConfirmLabel = new Label("confirm\nnew password:"),
                resetCodeLabel = new Label("code:");
        Button cancelResetBtn1 = new Button("Cancel"),
                submitResetPassCodeBtn = new Button("Submit"),
                cancelResetBtn2 = new Button("Cancel");
        forgotPassBox.getStyleClass().add("formDivision");
        forgotPassBox.setSpacing(10);
        forgotPassBox.setPadding(new Insets(10, 10, 10, 10));
        resetLabel.getStyleClass().add("pageHeader");
        resetInstructions1.setText("Enter your email and submit. "
                + "An access code will be sent to your email.");
        resetInstructions1.setWrapText(true);
        resetInstructions1.getStyleClass().add("inputLabel");
        resetInstructions1.setAlignment(Pos.CENTER);
        resetInstructions1.prefWidthProperty().bind(forgotPassBox.prefWidthProperty());
        HBox emailLine = new HBox();
        emailLine.setAlignment(Pos.CENTER_LEFT);
        emailLine.setSpacing(10);
        resetEmailLabel.getStyleClass().add("inputLabel");
        resetEmailLabel.setPrefWidth(120);
        resetEmailLabel.setAlignment(Pos.CENTER_RIGHT);
        submitResetBtn.setText("Submit");
        submitResetBtn.getStyleClass().add("genButton");
        submitResetBtn.setOnAction(new SubmitResetAction());
        cancelResetBtn1.getStyleClass().add("genButton");
        cancelResetBtn1.setOnAction(new CancelResetAction());
        emailLine.getChildren().addAll(resetEmailLabel, resetEmail,
                submitResetBtn, cancelResetBtn1);
        resetError.setWrapText(true);
        resetError.getStyleClass().add("errorLabel");
        resetError.setAlignment(Pos.CENTER);
        resetError.prefWidthProperty().bind(forgotPassBox.prefWidthProperty());
        resetError.setVisible(false);
        resetError.setManaged(false);
        resetInstructions2.setText("Enter your new password, confirm it, "
                + "and enter the access code that was sent to you.");
        resetInstructions2.setWrapText(true);
        resetInstructions2.getStyleClass().add("inputLabel");
        resetInstructions2.setAlignment(Pos.CENTER);
        resetInstructions2.prefWidthProperty().bind(forgotPassBox.prefWidthProperty());
        resetInstructions2.setVisible(false);
        resetInstructions2.setManaged(false);
        resetNewPassLine.setAlignment(Pos.CENTER_LEFT);
        resetNewPassLine.setSpacing(10);
        resetNewPassLabel.getStyleClass().add("inputLabel");
        resetNewPassLabel.setPrefWidth(120);
        resetNewPassLabel.setAlignment(Pos.CENTER_RIGHT);
        resetNewPassLine.getChildren().addAll(resetNewPassLabel, resetNewPass);
        resetNewPassLine.setVisible(false);
        resetNewPassLine.setManaged(false);
        resetNewPassConfirmLine.setAlignment(Pos.CENTER_LEFT);
        resetNewPassConfirmLine.setSpacing(10);
        resetPassStrengthLabel.setAlignment(Pos.CENTER_LEFT);
        resetPassStrengthLabel.getStyleClass().add("inputLabel");
        resetPassStrengthLabel.prefWidthProperty().bind(forgotPassBox.prefWidthProperty());
        resetPassStrengthLabel.setVisible(false);
        resetPassStrengthLabel.setManaged(false);
        resetNewPassConfirmLabel.getStyleClass().add("inputLabel");
        resetNewPassConfirmLabel.setPrefWidth(120);
        resetNewPassConfirmLabel.setAlignment(Pos.CENTER_RIGHT);
        resetNewPassConfirmLine.getChildren().addAll(resetNewPassConfirmLabel,
                resetNewPassConfirm);
        resetNewPassConfirmLine.setVisible(false);
        resetNewPassConfirmLine.setManaged(false);
        resetCodeLine.setAlignment(Pos.CENTER_LEFT);
        resetCodeLine.setSpacing(10);
        resetCodeLabel.getStyleClass().add("inputLabel");
        resetCodeLabel.setPrefWidth(120);
        resetCodeLabel.setAlignment(Pos.CENTER_RIGHT);
        resetCodeLine.getChildren().addAll(resetCodeLabel, resetCode);
        resetCodeLine.setVisible(false);
        resetCodeLine.setManaged(false);
        resetButLine.setPadding(new Insets(0, 0, 0, 32));
        resetButLine.setAlignment(Pos.CENTER_LEFT);
        resetButLine.setSpacing(100);
        submitResetPassCodeBtn.getStyleClass().add("genButton");
        submitResetPassCodeBtn.setOnAction(new SubmitResetPassCode());
        cancelResetBtn2.getStyleClass().add("genButton");
        cancelResetBtn2.setOnAction(new CancelResetAction());
        resetButLine.getChildren().addAll(submitResetPassCodeBtn, cancelResetBtn2);
        resetButLine.setVisible(false);
        resetButLine.setManaged(false);
        forgotPassBox.getChildren().addAll(resetLabel, resetInstructions1,
                emailLine, resetError, resetInstructions2,
                resetNewPassLine, resetPassStrengthLabel,
                resetNewPassConfirmLine, resetCodeLine, resetButLine);
        screenContent.getChildren().addAll(forgotPassBox);
        screenContent.setSpacing(30);
        screenContent.setPadding(new Insets(20, 20, 20, 20));
        screen.setContent(screenContent);
        screen.setPannable(true);
        return screen;
    }

    /*------------------------------------------------------------------------*
     * buildTestScreen()
     * - TEMPORARY
     * - Testing grounds
     *------------------------------------------------------------------------*/
    private ScrollPane buildTestScreen() {
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox();

        screenContent.setSpacing(30);
        screenContent.setPadding(new Insets(20, 20, 20, 20));
        screen.setContent(screenContent);
        screen.setPannable(true);
        return screen;
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
        accessInstructions.setText("Enter the access code that was emailed to "
                + "you when you registered below.");
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
            accessInstructions.setText("Enter the access code that was emailed "
                    + "to you when you registered below.");
            try {
                int loginStatus = controller.getDataM().userLogin(cred);
                if (loginStatus == 0) {
                    // user does not exist in database
                    loginError.setText("The email address and/or password is "
                            + "incorrect. Please try again.");
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
}
