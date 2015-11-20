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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
            resetCode,
            registerEmail;
    private final Label loginError,
            resetError,
            accessInstructions,
            resetInstructions1,
            resetInstructions2,
            resetPassStrengthLabel,
            registerPassStrengthLabel,
            registerError,
            registerInstructions;
    private final VP_PasswordField loginPass,
            resetNewPass,
            resetNewPassConfirm,
            registerPass,
            registerPassConfirm;
    private final HBox accessLine,
            loginButtonLine,
            resetNewPassLine,
            resetNewPassConfirmLine,
            resetCodeLine,
            resetButLine;
    private final VP_Button submitResetBtn;

    /*------------------------------------------------------------------------*
     * VP_Center()
     * - Constructor.
     * - Parameter controller stored as member to access its data manager and
     *   error function.
     *------------------------------------------------------------------------*/
    protected VP_Center(VP_GUIController controller) {
        //-------- Initialization Start ----------\\
        this.controller = controller;
        loginError = new Label();
        resetError = new Label();
        accessInstructions = new Label();
        resetInstructions1 = new Label();
        resetInstructions2 = new Label();
        resetPassStrengthLabel = new Label();
        registerPassStrengthLabel = new Label();
        registerError = new Label();
        registerInstructions = new Label();
        accessLine = new HBox();
        loginButtonLine = new HBox();
        resetNewPassLine = new HBox();
        resetNewPassConfirmLine = new HBox();
        resetCodeLine = new HBox();
        resetButLine = new HBox();
        submitResetBtn = new VP_Button("Submit", new SubmitResetAction());
        regLoginAccess = new VP_TextField(16, 16);
        resetCode = new VP_TextField(16, 16);
        loginEmail = new VP_TextField(32, 254);
        resetEmail = new VP_TextField(32, 254);
        registerEmail = new VP_TextField(32, 254);
        loginPass = new VP_PasswordField(32, 32, 0, null);
        resetNewPass = new VP_PasswordField(32, 32,
                controller.getUSER_PASSWORD_MINIMUM(), resetPassStrengthLabel);
        registerPass = new VP_PasswordField(32, 32,
                controller.getUSER_PASSWORD_MINIMUM(), registerPassStrengthLabel);
        resetNewPassConfirm = new VP_PasswordField(32, 32, 0, null);
        registerPassConfirm = new VP_PasswordField(32, 32, 0, null);
        //-------- Initialization End ------------\\

        this.setId("center");
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
        getChildren().addAll(
                buildLoginScreen(),
                buildResetPasswordScreen(),
                buildRegistrationScreen(),
                buildTestScreen());
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
     *   A.K.A Screen 0
     * - No parameters.
     * - Returns a scroller that gets applied to a center stackpane level.
     *------------------------------------------------------------------------*/
    private ScrollPane buildLoginScreen() {
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox();
        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-10));
        Label loginLabel = new Label("LOGIN"),
                loginEmailLabel = new Label("email:"),
                loginPassLabel = new Label("password:"),
                passForgotLabel = new Label("forgot your password?"),
                needAccountLabel = new Label("need an account?"),
                regCodeLabel = new Label("code:");
        VP_Button loginBtn = new VP_Button("Login", new LoginAction()),
                enterAccessBtn = new VP_Button("Submit", new SubmitAccessAction()),
                accessCancelBtn = new VP_Button("Cancel", new CancelAccessAction()),
                accessResendBtn = new VP_Button("Resend Code", new ResendAccessAction());
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
        loginEmailLabel.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        loginEmail.setAlignment(Pos.CENTER_LEFT);
        emailLine.getChildren().addAll(loginEmailLabel, loginEmail);
        HBox passLine = new HBox();
        passLine.setAlignment(Pos.CENTER_LEFT);
        passLine.setSpacing(10);
        loginPassLabel.getStyleClass().add("inputLabel");
        loginPassLabel.setPrefWidth(80);
        loginPassLabel.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        loginPassLabel.setAlignment(Pos.CENTER_RIGHT);
        loginPass.setAlignment(Pos.CENTER_LEFT);
        passLine.getChildren().addAll(loginPassLabel, loginPass);
        loginError.setWrapText(true);
        loginError.setPadding(new Insets(10, 50, 10, 50));
        loginError.getStyleClass().add("errorLabel");
        loginError.setAlignment(Pos.CENTER);
        loginError.setVisible(false);
        loginError.setManaged(false);
        loginButtonLine.setPadding(new Insets(0, 0, 0, 32));
        loginButtonLine.setAlignment(Pos.CENTER_LEFT);
        loginButtonLine.setSpacing(50);
        passForgotLabel.getStyleClass().add("clickable");
        passForgotLabel.setOnMouseClicked(new ForgotPassAction());
        needAccountLabel.getStyleClass().add("clickable");
        needAccountLabel.setOnMouseClicked(new NeedAccountAction());
        loginButtonLine.getChildren().addAll(loginBtn, needAccountLabel, passForgotLabel);
        accessInstructions.setText("Enter the access code that was "
                + "emailed to you when you registered below.");
        accessInstructions.setWrapText(true);
        accessInstructions.setPadding(new Insets(10, 50, 10, 50));
        accessInstructions.getStyleClass().add("paragraph");
        accessInstructions.setAlignment(Pos.CENTER);
        accessInstructions.setVisible(false);
        accessInstructions.setManaged(false);
        accessLine.setAlignment(Pos.CENTER_LEFT);
        accessLine.setSpacing(20);
        regCodeLabel.getStyleClass().add("inputLabel");
        regCodeLabel.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
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
     *   A.K.A Screen 1
     * - No parameters.
     * - Returns a scroller that gets applied to a center stackpane level.
     *------------------------------------------------------------------------*/
    private ScrollPane buildResetPasswordScreen() {
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox(),
                forgotPassBox = new VBox();
        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-10));
        Label resetLabel = new Label("RESET PASSWORD"),
                resetEmailLabel = new Label("email:"),
                resetNewPassLabel = new Label("new password:"),
                resetNewPassConfirmLabel = new Label("confirm\nnew password:"),
                resetCodeLabel = new Label("code:");
        VP_Button cancelResetBtn1 = new VP_Button("Cancel", new CancelResetAction()),
                submitResetPassCodeBtn = new VP_Button("Submit", new SubmitResetPassCode()),
                cancelResetBtn2 = new VP_Button("Cancel", new CancelResetAction());
        forgotPassBox.getStyleClass().add("formDivision");
        forgotPassBox.setSpacing(10);
        forgotPassBox.setPadding(new Insets(10, 10, 10, 10));
        resetLabel.getStyleClass().add("pageHeader");
        resetInstructions1.setText("Enter your email and submit. "
                + "An access code will be sent to your email.");
        resetInstructions1.setWrapText(true);
        resetInstructions1.setPadding(new Insets(10, 50, 10, 50));
        resetInstructions1.getStyleClass().add("paragraph");
        resetInstructions1.setAlignment(Pos.CENTER);
        HBox emailLine = new HBox();
        emailLine.setAlignment(Pos.CENTER_LEFT);
        emailLine.setSpacing(10);
        resetEmailLabel.getStyleClass().add("inputLabel");
        resetEmailLabel.setPrefWidth(120);
        resetEmailLabel.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        resetEmailLabel.setAlignment(Pos.CENTER_RIGHT);
        emailLine.getChildren().addAll(resetEmailLabel, resetEmail,
                submitResetBtn, cancelResetBtn1);
        resetError.setWrapText(true);
        resetError.setPadding(new Insets(10, 50, 10, 50));
        resetError.getStyleClass().add("errorLabel");
        resetError.setAlignment(Pos.CENTER);
        resetError.setVisible(false);
        resetError.setManaged(false);
        resetInstructions2.setText("Enter your new password, confirm it, "
                + "and enter the access code that was sent to you.");
        resetInstructions2.setWrapText(true);
        resetInstructions2.setPadding(new Insets(10, 50, 10, 50));
        resetInstructions2.getStyleClass().add("paragraph");
        resetInstructions2.setAlignment(Pos.CENTER);
        resetInstructions2.setVisible(false);
        resetInstructions2.setManaged(false);
        resetNewPassLine.setAlignment(Pos.CENTER_LEFT);
        resetNewPassLine.setSpacing(10);
        resetNewPassLabel.getStyleClass().add("inputLabel");
        resetNewPassLabel.setPrefWidth(120);
        resetNewPassLabel.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        resetNewPassLabel.setAlignment(Pos.CENTER_RIGHT);
        resetNewPassLine.getChildren().addAll(resetNewPassLabel, resetNewPass);
        resetNewPassLine.setVisible(false);
        resetNewPassLine.setManaged(false);
        resetNewPassConfirmLine.setAlignment(Pos.CENTER_LEFT);
        resetNewPassConfirmLine.setSpacing(10);
        resetPassStrengthLabel.setAlignment(Pos.CENTER_LEFT);
        resetPassStrengthLabel.getStyleClass().add("inputLabel");
        resetPassStrengthLabel.setVisible(false);
        resetPassStrengthLabel.setManaged(false);
        resetNewPassConfirmLabel.getStyleClass().add("inputLabel");
        resetNewPassConfirmLabel.setPrefWidth(120);
        resetNewPassConfirmLabel.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        resetNewPassConfirmLabel.setAlignment(Pos.CENTER_RIGHT);
        resetNewPassConfirmLine.getChildren().addAll(resetNewPassConfirmLabel,
                resetNewPassConfirm);
        resetNewPassConfirmLine.setVisible(false);
        resetNewPassConfirmLine.setManaged(false);
        resetCodeLine.setAlignment(Pos.CENTER_LEFT);
        resetCodeLine.setSpacing(10);
        resetCodeLabel.getStyleClass().add("inputLabel");
        resetCodeLabel.setPrefWidth(120);
        resetCodeLabel.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        resetCodeLabel.setAlignment(Pos.CENTER_RIGHT);
        resetCodeLine.getChildren().addAll(resetCodeLabel, resetCode);
        resetCodeLine.setVisible(false);
        resetCodeLine.setManaged(false);
        resetButLine.setPadding(new Insets(0, 0, 0, 32));
        resetButLine.setAlignment(Pos.CENTER_LEFT);
        resetButLine.setSpacing(100);
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
     * buildRegistrationScreen()
     * - Creates the user registration screen. Called by buildCenter().
     *   A.K.A Screen 2
     * - No parameters.
     * - Returns a scroller that gets applied to a center stackpane level.
     *------------------------------------------------------------------------*/
    private ScrollPane buildRegistrationScreen() {
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox();
        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-10));
        Label registerLabel = new Label("REGISTER NEW ACCOUNT"),
                registerEmailLabel = new Label("email:"),
                registerPassLabel = new Label("password:"),
                registerPassConfirmLabel = new Label("confirm\npassword:");
        VP_Button registerBtn = new VP_Button("Register", new RegisterSubmitAction()),
                registerCancelBtn = new VP_Button("Cancel", new CancelRegisterAction());
        VBox registerBox = new VBox();
        registerBox.setSpacing(10);
        registerBox.getStyleClass().add("formDivision");
        registerBox.setPadding(new Insets(10, 10, 10, 10));
        registerLabel.getStyleClass().add("pageHeader");
        HBox emailLine = new HBox();
        emailLine.setAlignment(Pos.CENTER_LEFT);
        emailLine.setSpacing(10);
        registerEmailLabel.getStyleClass().add("inputLabel");
        registerEmailLabel.setPrefWidth(100);
        registerEmailLabel.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        registerEmailLabel.setAlignment(Pos.CENTER_RIGHT);
        registerEmail.setAlignment(Pos.CENTER_LEFT);
        emailLine.getChildren().addAll(registerEmailLabel, registerEmail);
        HBox passLine = new HBox();
        passLine.setAlignment(Pos.CENTER_LEFT);
        passLine.setSpacing(10);
        registerPassLabel.getStyleClass().add("inputLabel");
        registerPassLabel.setPrefWidth(100);
        registerPassLabel.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        registerPassLabel.setAlignment(Pos.CENTER_RIGHT);
        registerPass.setAlignment(Pos.CENTER_LEFT);
        passLine.getChildren().addAll(registerPassLabel, registerPass);
        registerPassStrengthLabel.setAlignment(Pos.CENTER_LEFT);
        registerPassStrengthLabel.getStyleClass().add("inputLabel");
        registerPassStrengthLabel.setPadding(new Insets(0, 50, 0, 50));
        HBox registerConfirmLine = new HBox();
        registerConfirmLine.setAlignment(Pos.CENTER_LEFT);
        registerConfirmLine.setSpacing(10);
        registerPassConfirmLabel.getStyleClass().add("inputLabel");
        registerPassConfirmLabel.setPrefWidth(100);
        registerPassConfirmLabel.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        registerPassConfirmLabel.setAlignment(Pos.CENTER_RIGHT);
        registerConfirmLine.getChildren().addAll(registerPassConfirmLabel,
                registerPassConfirm);
        registerBox.getChildren().addAll(registerLabel, emailLine, passLine,
                registerPassStrengthLabel, registerConfirmLine);
        registerError.setWrapText(true);
        registerError.setPadding(new Insets(10, 50, 10, 50));
        registerError.getStyleClass().add("errorLabel");
        registerError.setAlignment(Pos.CENTER);
        registerError.setVisible(false);
        registerError.setManaged(false);
        registerInstructions.setWrapText(true);
        registerInstructions.setPadding(new Insets(10, 50, 10, 50));
        registerInstructions.getStyleClass().add("paragraph");
        registerInstructions.setAlignment(Pos.CENTER);
        registerInstructions.setText("Enter your email and password twice. When "
                + "you submit, an email will be sent to you with a registration "
                + "access code. Login with the email and password you provided "
                + "and you will be prompted to enter in the access code. Access "
                + "codes expire within one hour.");
        HBox registerButtonLine = new HBox();
        registerButtonLine.setPadding(new Insets(0, 0, 0, 32));
        registerButtonLine.setAlignment(Pos.CENTER_LEFT);
        registerButtonLine.setSpacing(50);
        registerButtonLine.getChildren().addAll(registerBtn, registerCancelBtn);
        registerBox.getChildren().addAll(registerError,
                registerInstructions, registerButtonLine);
        screenContent.getChildren().addAll(registerBox);
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
        //-------- Initialization Start ----------\\
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox();
        //-------- Initialization End ------------\\
        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-10));
        screenContent.setSpacing(30);
        screenContent.setPadding(new Insets(20, 20, 20, 20));
        screen.setContent(screenContent);
        screen.setPannable(true);
        return screen;
    }

    /*------------------------------------------------------------------------*
     * resetLoginRegForms()
     * - Restores the login page back to its original state.
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
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

    /*------------------------------------------------------------------------*
     * resetResetPasswordForms()
     * - Restores the reset password page back to its original state.
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
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
     * resetRegisterForms()
     * - Restores the registration page back to its original state.
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    private void resetRegisterForms() {
        registerEmail.setText("");
        registerPass.setText("");
        registerPassConfirm.setText("");
        registerError.setText("");
        registerError.setVisible(false);
        registerError.setManaged(false);
    }

    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*------------------------------------------------------------------------*
     * Subclass LoginAction
     * - Action event for the 'forgot password?' link on page 0. Switches the
     *   center stackpane to show level 1.
     *------------------------------------------------------------------------*/
    private class ForgotPassAction implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            VP_Sounds.play(0);
            resetLoginRegForms();
            showScreen(1);
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass NeedAccountAction
     * - Action event for the 'need an account?' link on page 0. Switches the
     *   center stackpane to show level 2.
     *------------------------------------------------------------------------*/
    private class NeedAccountAction implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            VP_Sounds.play(0);
            resetLoginRegForms();
            showScreen(2);
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass LoginAction
     * - Action event for the login button on page 0.
     *------------------------------------------------------------------------*/
    private class LoginAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            //-------- Initialization Start ----------\\
            String cred[] = new String[]{loginEmail.getText().toLowerCase(),
                loginPass.getText()};
            int loginStatus;
            //-------- Initialization End ------------\\
            VP_Sounds.play(0);

            accessInstructions.setText("Enter the access code that was emailed "
                    + "to you when you registered below.");
            try {
                loginStatus = controller.getDataM().userLogin(cred);
                if (loginStatus == -1) {
                    // user does not exist in database
                    loginError.setText("The email address and/or password is "
                            + "incorrect. Please try again.");
                    loginError.setManaged(true);
                    loginError.setVisible(true);
                    loginEmail.showInvalid();
                    loginPass.showInvalid();
                    VP_Sounds.play(-1);
                } else if (loginStatus == -2) {
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
                } else {
                    // user login successful
                    resetLoginRegForms();
                    showScreen(3);
                } 
            } catch (SQLException ex) {
                controller.errorAlert(1407, ex.getMessage());
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                controller.errorAlert(1202, ex.getMessage());
            }
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass ResendAccessAction
     * - Action event for the resend code button on page 0 to assign a new
     *   access code for the user and send it to the user's email.
     *------------------------------------------------------------------------*/
    private class ResendAccessAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            //-------- Initialization Start ----------\\
            String cred[] = new String[]{loginEmail.getText().toLowerCase(),
                loginPass.getText(), regLoginAccess.getText()};
            //-------- Initialization End ------------\\

            VP_Sounds.play(0);
            try {
                controller.getDataM().resendAccess(cred);
                accessInstructions.setText("A new access code has been emailed and "
                        + "you should receive it shortly. Enter the code below.\n"
                        + "If you do not receive an email, verify that you have "
                        + "entered in the correct email address.");
            } catch (SQLException ex) {
                controller.errorAlert(1410, ex.getMessage());
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                controller.errorAlert(1205, ex.getMessage());
            }
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass SubmitAccessAction
     * - Action event for the submit button on page 0 which submits the
     *   registration access code.
     *------------------------------------------------------------------------*/
    private class SubmitAccessAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            //-------- Initialization Start ----------\\
            String cred[] = new String[]{loginEmail.getText().toLowerCase(),
                loginPass.getText(), regLoginAccess.getText()};
            boolean accessStatus;
            //-------- Initialization End ------------\\

            VP_Sounds.play(0);
            try {
                accessStatus = controller.getDataM().verifyRegAccess(cred);
                if (accessStatus) {
                    resetLoginRegForms();
                    showScreen(3);
                } else {
                    loginError.setText("The registration code is incorrect. Please try again.");
                    accessInstructions.setText("Enter the access code that was emailed to you below.");
                    loginError.setVisible(true);
                    loginError.setManaged(true);
                    regLoginAccess.showInvalid();
                    VP_Sounds.play(-1);
                }
            } catch (SQLException ex) {
                controller.errorAlert(1408, ex.getMessage());
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                controller.errorAlert(1203, ex.getMessage());
            }
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass CancelAccessAction
     * - Action event for the cancel button on page 0. Calls 
     *   resetLoginRegForms() to reset the forms and view.
     *------------------------------------------------------------------------*/
    private class CancelAccessAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            resetLoginRegForms();
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass SubmitResetAction
     * - Action event for the submit button on page 1 that is for submitting the
     *   email to the database manager to check if the user is eligible for
     *   password resetting.
     *------------------------------------------------------------------------*/
    private class SubmitResetAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            //-------- Initialization Start ----------\\
            int userStatus;
            //-------- Initialization End ------------\\

            VP_Sounds.play(0);
            try {
                userStatus = controller.getDataM().findUserForReset(resetEmail.getText().toLowerCase());
                if (userStatus == 0) {
                    resetEmail.showInvalid();
                    resetError.setText("The provided user does not exists in VaqPack. "
                            + "If the email is correct, you need to register a new account.");
                    resetError.setVisible(true);
                    resetError.setManaged(true);
                    VP_Sounds.play(-1);
                } else if (userStatus == 1) {
                    resetEmail.showInvalid();
                    resetError.setText("The password for this email has recently been reset. "
                            + "Passwords may only be reset once every 24 hours.");
                    resetError.setVisible(true);
                    resetError.setManaged(true);
                    VP_Sounds.play(-1);
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
                controller.errorAlert(1411, ex.getMessage());
            }
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass SubmitResetPassCode
     * - Action event for the submit button on page 1 to submit the code for
     *   resetting a user password.
     *------------------------------------------------------------------------*/
    private class SubmitResetPassCode implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            //-------- Initialization Start ----------\\
            int resetStatus = 0;
            String[] cred = {resetEmail.getText().toLowerCase(), resetNewPass.getText(),
                resetNewPassConfirm.getText(), resetCode.getText()};
            //-------- Initialization End ------------\\

            VP_Sounds.play(0);
            if (cred[1].length() < controller.getUSER_PASSWORD_MINIMUM()) {
                resetNewPass.showInvalid();
                resetNewPassConfirm.showInvalid();
                resetError.setText("The new password is not long enough.");
                resetError.setVisible(true);
                resetError.setManaged(true);
                VP_Sounds.play(-1);
            } else if (!cred[1].equals(cred[2])) {
                resetNewPass.showInvalid();
                resetNewPassConfirm.showInvalid();
                resetError.setText("The passwords do not match.");
                resetError.setVisible(true);
                resetError.setManaged(true);
                VP_Sounds.play(-1);
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
                        VP_Sounds.play(-1);
                    } else if (resetStatus == 0) {
                        resetCode.showInvalid();
                        resetError.setText("The code is incorrect. Please try again.");
                        resetError.setVisible(true);
                        resetError.setManaged(true);
                        VP_Sounds.play(-1);
                    }
                } catch (SQLException ex) {
                    controller.errorAlert(1409, ex.getMessage());
                } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                    controller.errorAlert(1204, ex.getMessage());
                }
            }
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass CancelResetAction
     * - Action event for the cancel buttons on page 1. Calls
     *   resetResetPasswordForms() to restore the page to its original state.
     *------------------------------------------------------------------------*/
    private class CancelResetAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            resetResetPasswordForms();
            showScreen(0);
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass RegisterSubmitAction
     * - Action event for the submit buttons on page 2.
     *------------------------------------------------------------------------*/
    private class RegisterSubmitAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            //-------- Initialization Start ----------\\
            String[] cred = {registerEmail.getText().toLowerCase(), registerPass.getText(),
                registerPassConfirm.getText()};
            int registerStatus;
            //-------- Initialization End ------------\\

            VP_Sounds.play(0);
            if (cred[1].length() < controller.getUSER_PASSWORD_MINIMUM()) {
                registerPass.showInvalid();
                registerPassConfirm.showInvalid();
                registerError.setText("The password is not long enough.");
                registerError.setVisible(true);
                registerError.setManaged(true);
                VP_Sounds.play(-1);
            } else if (!cred[1].equals(cred[2])) {
                registerPass.showInvalid();
                registerPassConfirm.showInvalid();
                registerError.setText("The passwords do not match.");
                registerError.setVisible(true);
                registerError.setManaged(true);
                VP_Sounds.play(-1);
            } else {
                try {
                    registerStatus = controller.getDataM().regUser(cred);
                    if (registerStatus == 2) {
                        accessInstructions.setText("Login with your new account.");
                        accessInstructions.setVisible(true);
                        accessInstructions.setManaged(true);
                        resetRegisterForms();
                        showScreen(0);
                    } else if (registerStatus == 1) {
                        registerEmail.showInvalid();
                        registerError.setText("This email is already associated "
                                + "with a VaqPack user.");
                        registerError.setVisible(true);
                        registerError.setManaged(true);
                        VP_Sounds.play(-1);
                    } else if (registerStatus == 0) {
                        registerEmail.showInvalid();
                        registerError.setText("This email is already associated "
                                + "with a VaqPack user who recently registered "
                                + "but has not yet enetered in the access code. "
                                + "Login and you will be prompted to enter in "
                                + "this code. If you did not receive the code, "
                                + "there will be an option to resend one.");
                        registerError.setVisible(true);
                        registerError.setManaged(true);
                        VP_Sounds.play(-1);
                    }
                } catch (SQLException ex) {
                    controller.errorAlert(1412, ex.getMessage());
                } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                    controller.errorAlert(1206, ex.getMessage());
                }
            }
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass CancelRegisterAction
     * - Action event for the cancel buttons on page 2. Calls
     *   resetRegisterForms() to restore the page to its original state.
     *------------------------------------------------------------------------*/
    private class CancelRegisterAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            resetRegisterForms();
            showScreen(0);
        }
    }

    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/
}
