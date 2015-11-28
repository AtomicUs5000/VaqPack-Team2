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

import com.lowagie.text.DocumentException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class VP_Center extends StackPane {

    private final VP_GUIController controller;
    private final VP_TextField loginEmail,
            resetEmail,
            regLoginAccess,
            resetCode,
            registerEmail;
    private final Label resetPassStrengthLabel,
            registerPassStrengthLabel,
            infoProgress,
            resumeProgress,
            bcardProgress,
            covletProgress;
    private final VP_PasswordField loginPass,
            resetNewPass,
            resetNewPassConfirm,
            registerPass,
            registerPassConfirm;
    private final VP_DivisionLine loginErrorLine,
            accessInstructionsLine,
            loginButtonLine,
            accessLine,
            resetErrorLine,
            resetInstructions1Line,
            resetInstructions2Line,
            resetNewPassLine,
            resetNewPassConfirmLine,
            resetCodeLine,
            resetButLine,
            resetPassStrengthLine,
            registerErrorLine,
            personalInfoErrorLine,
            bcardErrorLine,
            covletEditErrorLine,
            addParagraphLine;
    private final VP_Paragraph loginError,
            accessInstructions,
            resetError,
            resetInstructions1,
            resetInstructions2,
            registerError,
            overviewInfo,
            personalInfoError,
            bcardError,
            covletEditError;
    private final VP_Button submitResetBtn;
    private final ArrayList<VP_Button> wizardMainButtons;
    private final ArrayList<VP_PageSubdivision> bcNodes;
    private final ArrayList<Node> coverLetterEditFields;
    private final VP_FieldLabel dateValueLabel;
    private final VP_PageSubdivision dynamicBody;

    /*------------------------------------------------------------------------*
     * VP_Center()
     * - Constructor.
     * - Parameter controller stored as member to access its data manager and
     *   error function.
     *------------------------------------------------------------------------*/
    protected VP_Center(VP_GUIController controller) {
        //-------- Initialization Start ----------\\
        this.controller = controller;
        loginErrorLine = new VP_DivisionLine();
        loginButtonLine = new VP_DivisionLine();
        accessInstructionsLine = new VP_DivisionLine();
        resetErrorLine = new VP_DivisionLine();
        resetInstructions1Line = new VP_DivisionLine();
        resetInstructions2Line = new VP_DivisionLine();
        resetNewPassLine = new VP_DivisionLine();
        resetNewPassConfirmLine = new VP_DivisionLine();
        resetCodeLine = new VP_DivisionLine();
        accessLine = new VP_DivisionLine();
        resetButLine = new VP_DivisionLine();
        resetPassStrengthLine = new VP_DivisionLine();
        registerErrorLine = new VP_DivisionLine();
        personalInfoErrorLine = new VP_DivisionLine();
        bcardErrorLine = new VP_DivisionLine();
        covletEditErrorLine = new VP_DivisionLine();
        addParagraphLine = new VP_DivisionLine();
        loginError = new VP_Paragraph("", true);
        resetError = new VP_Paragraph("", true);
        registerError = new VP_Paragraph("", true);
        personalInfoError = new VP_Paragraph("", true);
        bcardError = new VP_Paragraph("", true);
        covletEditError = new VP_Paragraph("", true);
        accessInstructions = new VP_Paragraph();
        resetInstructions1 = new VP_Paragraph();
        resetInstructions2 = new VP_Paragraph();
        overviewInfo = new VP_Paragraph();
        resetPassStrengthLabel = new Label();
        registerPassStrengthLabel = new Label();
        infoProgress = new Label();
        resumeProgress = new Label();
        bcardProgress = new Label();
        covletProgress = new Label();
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
        wizardMainButtons = new ArrayList();
        bcNodes = new ArrayList();
        coverLetterEditFields = new ArrayList();
        dateValueLabel = new VP_FieldLabel("", 200);
        dynamicBody = new VP_PageSubdivision("BODY", false);
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
                buildLoginScreen(), // screen 0
                buildResetPasswordScreen(), // screen 1
                buildRegistrationScreen(), // screen 2
                buildOverviewScreen(), // screen 3
                buildPersonalInfoScreen(), // screen 4
                buildBusinessCardScreen(), // screen 5
                buildCoverLettersStartScreen(), // screen 6
                buildCoverLettersEditScreen(), // screen 7
                buildThemesStartScreen(), // screen 8
                buildThemesEditScreen(), // screen 9
                buildDistributeScreen(), // screen 10
                buildResumeStartScreen() // screen 11
        );
        showScreen(0, 0);
    }

    /*------------------------------------------------------------------------*
     * showScreen()
     * - Makes all center stackpane levels invisible except for the desired one.
     * - Parameter screenNumber is the desired stackpane level to show.
     * - No Return
     *------------------------------------------------------------------------*/
    protected void showScreen(int screenNumber, double position) {
        for (int i = 0; i < getChildren().size(); i++) {
            getChildren().get(i).setVisible(false);
        }
        if (screenNumber == 3) {
            ((TreeView)(controller.getLeftTree().getChildren().get(0))).getSelectionModel().clearSelection();
            ((TreeView)(controller.getLeftTree().getChildren().get(0))).getSelectionModel().select(0);
        }
        
        updateOverview();
        getChildren().get(screenNumber).setVisible(true);
        ((ScrollPane)(getChildren().get(screenNumber))).setVvalue(position);
    }

    /*------------------------------------------------------------------------*
     * buildLoginScreen()
     * - Creates the user login screen. Called by buildCenter().
     *   A.K.A Screen 0
     * - No parameters.
     * - Returns a scroller that gets applied to a center stackpane level.
     *------------------------------------------------------------------------*/
    private ScrollPane buildLoginScreen() {
        //-------- Initialization Start ----------\\
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox();
        VP_PageDivision loginBox = new VP_PageDivision("LOGIN");
        VP_FieldLabel loginEmailLabel = new VP_FieldLabel("email:", 60),
                loginPassLabel = new VP_FieldLabel("password:", 60),
                regCodeLabel = new VP_FieldLabel("code:", 60);
        Label passForgotLabel = new Label("forgot your password?"),
                needAccountLabel = new Label("need an account?");
        VP_Button loginBtn = new VP_Button("Login", new LoginAction()),
                enterAccessBtn = new VP_Button("Submit", new SubmitAccessAction()),
                accessCancelBtn = new VP_Button("Cancel", new CancelActionPrelogin()),
                accessResendBtn = new VP_Button("Resend Code", new ResendAccessAction());
        VP_DivisionLine emailLine = new VP_DivisionLine(new Node[]{loginEmailLabel, loginEmail}),
                passLine = new VP_DivisionLine(new Node[]{loginPassLabel, loginPass});
        //-------- Initialization End ------------\\

        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-20));
        loginErrorLine.getChildren().addAll(loginError);
        passForgotLabel.getStyleClass().add("clickable");
        passForgotLabel.setOnMouseClicked(new ForgotPassAction());
        needAccountLabel.getStyleClass().add("clickable");
        needAccountLabel.setOnMouseClicked(new NeedAccountAction());
        loginButtonLine.getChildren().addAll(loginBtn, needAccountLabel, passForgotLabel);
        accessInstructionsLine.getChildren().addAll(accessInstructions);
        accessLine.getChildren().addAll(regCodeLabel, regLoginAccess,
                enterAccessBtn, accessCancelBtn, accessResendBtn);
        loginBox.getChildren().addAll(emailLine, passLine, loginErrorLine,
                loginButtonLine, accessInstructionsLine, accessLine);
        resetLoginRegForms();
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
        //-------- Initialization Start ----------\\
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox();
        VP_PageDivision forgotPassBox = new VP_PageDivision("RESET PASSWORD");
        VP_FieldLabel resetEmailLabel = new VP_FieldLabel("email:", 100),
                resetNewPassLabel = new VP_FieldLabel("new password:", 100),
                resetNewPassConfirmLabel = new VP_FieldLabel("confirm\nnew password:", 100),
                resetCodeLabel = new VP_FieldLabel("code:", 100);
        VP_Button cancelResetBtn1 = new VP_Button("Cancel", new CancelActionPrelogin()),
                submitResetPassCodeBtn = new VP_Button("Submit", new SubmitResetPassCode()),
                cancelResetBtn2 = new VP_Button("Cancel", new CancelActionPrelogin());
        VP_DivisionLine emailLine = new VP_DivisionLine(new Node[]{resetEmailLabel, resetEmail,
            submitResetBtn, cancelResetBtn1});
        //-------- Initialization End ------------\\

        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-20));
        resetInstructions1Line.getChildren().addAll(resetInstructions1);
        resetErrorLine.getChildren().addAll(resetError);
        resetInstructions2Line.getChildren().addAll(resetInstructions2);
        resetNewPassLine.getChildren().addAll(resetNewPassLabel, resetNewPass);
        resetPassStrengthLabel.getStyleClass().add("inputLabel");
        resetPassStrengthLine.getChildren().addAll(resetPassStrengthLabel);
        resetNewPassConfirmLine.getChildren().addAll(resetNewPassConfirmLabel,
                resetNewPassConfirm);
        resetCodeLine.getChildren().addAll(resetCodeLabel, resetCode);
        resetButLine.getChildren().addAll(submitResetPassCodeBtn, cancelResetBtn2);
        forgotPassBox.getChildren().addAll(resetInstructions1Line,
                emailLine, resetErrorLine, resetInstructions2Line,
                resetNewPassLine, resetPassStrengthLabel,
                resetNewPassConfirmLine, resetCodeLine, resetButLine);
        resetResetPasswordForms();
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
        //-------- Initialization Start ----------\\
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox();
        VP_PageDivision registerBox = new VP_PageDivision("REGISTER NEW ACCOUNT");
        VP_FieldLabel registerEmailLabel = new VP_FieldLabel("email:", 100),
                registerPassLabel = new VP_FieldLabel("password:", 100),
                registerPassConfirmLabel = new VP_FieldLabel("confirm\npassword:", 100);
        VP_Button registerBtn = new VP_Button("Register", new RegisterSubmitAction()),
                registerCancelBtn = new VP_Button("Cancel", new CancelActionPrelogin());
        VP_Paragraph registerInstructions = new VP_Paragraph("Enter your email and password twice. When "
                + "you submit, an email will be sent to you with a registration "
                + "access code. Login with the email and password you provided "
                + "and you will be prompted to enter in the access code. Access "
                + "codes expire within one hour.");
        VP_DivisionLine emailLine = new VP_DivisionLine(new Node[]{registerEmailLabel, registerEmail}),
                passLine = new VP_DivisionLine(new Node[]{registerPassLabel, registerPass}),
                registerPassStrengthLine = new VP_DivisionLine(new Node[]{registerPassStrengthLabel}),
                registerConfirmLine = new VP_DivisionLine(new Node[]{registerPassConfirmLabel, registerPassConfirm}),
                registerInstructionsLine = new VP_DivisionLine(new Node[]{registerInstructions}),
                registerButtonLine = new VP_DivisionLine(new Node[]{registerBtn, registerCancelBtn});
        //-------- Initialization End ------------\\

        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-20));
        registerPassStrengthLabel.getStyleClass().add("inputLabel");
        registerErrorLine.getChildren().addAll(registerError);
        registerBox.getChildren().addAll(emailLine, passLine,
                registerPassStrengthLine, registerConfirmLine, registerErrorLine,
                registerInstructionsLine, registerButtonLine);
        resetRegisterForms();
        screenContent.getChildren().addAll(registerBox);
        screenContent.setSpacing(30);
        screenContent.setPadding(new Insets(20, 20, 20, 20));
        screen.setContent(screenContent);
        screen.setPannable(true);
        return screen;
    }

    /*------------------------------------------------------------------------*
     * buildOverviewScreen()
     * - Builds the Overview screen which provides access to the main
     *   functionality of the program and lists the completion status of some of 
     *   these functions for the logged in user.
     *   A.K.A Screen 3
     * - No parameters.
     * - Returns a scroller that gets applied to a center stackpane level.
     *------------------------------------------------------------------------*/
    private ScrollPane buildOverviewScreen() {
        //-------- Initialization Start ----------\\
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox();
        VP_PageDivision overviewBox = new VP_PageDivision("OVERVIEW");
        VP_Button updateInfoBtn = new VP_Button("Update Personal Information", new WizardMainAction(4)),
                updateResumeBtn = new VP_Button("Update Your Resume", new WizardMainAction(11)),
                updateBcardBtn = new VP_Button("Update Your Business Card", new WizardMainAction(5)),
                updateCovLetBtn = new VP_Button("Update Your Cover Letters", new WizardMainAction(6)),
                applyThemesBtn = new VP_Button("Apply Themes to Your Docs", new WizardMainAction(8)),
                distributeBtn = new VP_Button("Distribute Your Docs", new WizardMainAction(10));
        VP_DivisionLine step1Line = new VP_DivisionLine(new Node[]{updateInfoBtn, infoProgress}),
                step2Line = new VP_DivisionLine(new Node[]{updateResumeBtn, resumeProgress}),
                step3Line = new VP_DivisionLine(new Node[]{updateBcardBtn, bcardProgress}),
                step4Line = new VP_DivisionLine(new Node[]{updateCovLetBtn, covletProgress}),
                step5Line = new VP_DivisionLine(new Node[]{applyThemesBtn}),
                step6Line = new VP_DivisionLine(new Node[]{distributeBtn});
        //-------- Initialization End ------------\\

        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-20));
        wizardMainButtons.add(updateInfoBtn);
        wizardMainButtons.add(updateResumeBtn);
        wizardMainButtons.add(updateBcardBtn);
        wizardMainButtons.add(updateCovLetBtn);
        wizardMainButtons.add(applyThemesBtn);
        wizardMainButtons.add(distributeBtn);
        for (int i = 0; i < wizardMainButtons.size(); i++) {
            wizardMainButtons.get(i).setPrefWidth(200);
        }
        infoProgress.getStyleClass().add("notStarted");
        infoProgress.setMinWidth(250);
        infoProgress.setAlignment(Pos.CENTER_RIGHT);
        resumeProgress.getStyleClass().add("notStarted");
        resumeProgress.setMinWidth(250);
        resumeProgress.setAlignment(Pos.CENTER_RIGHT);
        bcardProgress.getStyleClass().add("notStarted");
        bcardProgress.setMinWidth(250);
        bcardProgress.setAlignment(Pos.CENTER_RIGHT);
        covletProgress.getStyleClass().add("notStarted");
        covletProgress.setMinWidth(250);
        covletProgress.setAlignment(Pos.CENTER_RIGHT);

        overviewBox.getChildren().addAll(overviewInfo, step1Line, step2Line,
                step3Line, step4Line, step5Line, step6Line);
        updateOverview();
        screenContent.getChildren().addAll(overviewBox);
        screenContent.setSpacing(30);
        screenContent.setPadding(new Insets(20, 20, 20, 20));
        screen.setContent(screenContent);
        screen.setPannable(true);
        return screen;
    }

    /*------------------------------------------------------------------------*
     * buildPersonalInfoScreen()
     * - Builds the screen where the user inputs personal information.
     *   A.K.A Screen 4
     * - No parameters.
     * - Returns a scroller that gets applied to a center stackpane level.
     *------------------------------------------------------------------------*/
    private ScrollPane buildPersonalInfoScreen() {
        //-------- Initialization Start ----------\\
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox();
        VP_PageDivision personalInfoBox = new VP_PageDivision("PERSONAL INFO", "personal-icon-larger.png", 160);
        VP_FieldLabel firstNameLabel = new VP_FieldLabel("first name:", 100),
                middleNameLabel = new VP_FieldLabel("*middle name:", 100),
                lastNameLabel = new VP_FieldLabel("last name:", 100),
                address1Label = new VP_FieldLabel("address line 1:", 100),
                address2Label = new VP_FieldLabel("*address line 2:", 100),
                cityLabel = new VP_FieldLabel("city:", 100),
                stateLabel = new VP_FieldLabel("state:", 100),
                zipLabel = new VP_FieldLabel("zipcode:", 100),
                phoneLabel = new VP_FieldLabel("phone:", 100),
                cellLabel = new VP_FieldLabel("*cell:", 100),
                emailLabel = new VP_FieldLabel("*email:", 100);
        ArrayList<VP_TextField> personalInfoFields = new ArrayList();
        personalInfoFields.add(new VP_TextField(32, 45));
        personalInfoFields.add(new VP_TextField(32, 45));
        personalInfoFields.add(new VP_TextField(32, 45));
        personalInfoFields.add(new VP_TextField(32, 254));
        personalInfoFields.add(new VP_TextField(32, 254));
        personalInfoFields.add(new VP_TextField(32, 45));
        personalInfoFields.add(new VP_TextField(2, 2));
        personalInfoFields.add(new VP_TextField(10, 10));
        personalInfoFields.add(new VP_TextField(13, 13));
        personalInfoFields.add(new VP_TextField(13, 13));
        personalInfoFields.add(new VP_TextField(32, 254));
        VP_Paragraph notes = new VP_Paragraph("(*) denotes an optional field. "
                + "Leave email blank to use your VaqPack account login email address.");
        VP_Button submitBtn = new VP_Button("Submit", new SubmitPersonalInfoAction(personalInfoFields)),
                cancelBtn = new VP_Button("Cancel", new CancelAction());
        VP_DivisionLine firstNameLine = new VP_DivisionLine(new Node[]{firstNameLabel, personalInfoFields.get(0)}),
                middleNameLine = new VP_DivisionLine(new Node[]{middleNameLabel, personalInfoFields.get(1)}),
                lastNameLine = new VP_DivisionLine(new Node[]{lastNameLabel, personalInfoFields.get(2)}),
                address1Line = new VP_DivisionLine(new Node[]{address1Label, personalInfoFields.get(3)}),
                address2Line = new VP_DivisionLine(new Node[]{address2Label, personalInfoFields.get(4)}),
                cityLine = new VP_DivisionLine(new Node[]{cityLabel, personalInfoFields.get(5)}),
                stateLine = new VP_DivisionLine(new Node[]{stateLabel, personalInfoFields.get(6)}),
                zipLine = new VP_DivisionLine(new Node[]{zipLabel, personalInfoFields.get(7)}),
                phoneLine = new VP_DivisionLine(new Node[]{phoneLabel, personalInfoFields.get(8)}),
                cellLine = new VP_DivisionLine(new Node[]{cellLabel, personalInfoFields.get(9)}),
                emailLine = new VP_DivisionLine(new Node[]{emailLabel, personalInfoFields.get(10)}),
                notesLine = new VP_DivisionLine(new Node[]{notes}),
                buttonsLine = new VP_DivisionLine(new Node[]{submitBtn, cancelBtn});
        //-------- Initialization End ------------\\

        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-20));
        personalInfoFields.get(0).textProperty().bindBidirectional(controller.getCurrentUser().getFirstName());
        personalInfoFields.get(1).textProperty().bindBidirectional(controller.getCurrentUser().getMiddleName());
        personalInfoFields.get(2).textProperty().bindBidirectional(controller.getCurrentUser().getLastName());
        personalInfoFields.get(3).textProperty().bindBidirectional(controller.getCurrentUser().getAddress1());
        personalInfoFields.get(4).textProperty().bindBidirectional(controller.getCurrentUser().getAddress2());
        personalInfoFields.get(5).textProperty().bindBidirectional(controller.getCurrentUser().getCity());
        personalInfoFields.get(6).textProperty().bindBidirectional(controller.getCurrentUser().getState());
        personalInfoFields.get(7).textProperty().bindBidirectional(controller.getCurrentUser().getZip());
        personalInfoFields.get(8).textProperty().bindBidirectional(controller.getCurrentUser().getPhone());
        personalInfoFields.get(9).textProperty().bindBidirectional(controller.getCurrentUser().getCell());
        personalInfoFields.get(10).textProperty().bindBidirectional(controller.getCurrentUser().getDocEmail());
        personalInfoErrorLine.getChildren().addAll(personalInfoError);
        personalInfoErrorLine.hide();
        personalInfoBox.getChildren().addAll(firstNameLine, middleNameLine, lastNameLine,
                address1Line, address2Line, cityLine, stateLine, zipLine, phoneLine, cellLine,
                emailLine, notesLine, personalInfoErrorLine, buttonsLine);
        screenContent.getChildren().addAll(personalInfoBox);
        screenContent.setSpacing(30);
        screenContent.setPadding(new Insets(20, 20, 20, 20));
        screen.setContent(screenContent);
        screen.setPannable(true);
        return screen;
    }

    /*------------------------------------------------------------------------*
     * buildBusinessCardScreen()
     * - Builds the screen where the user edits the business card.
     *   A.K.A Screen 5
     * - No parameters.
     * - Returns a scroller that gets applied to a center stackpane level.
     *------------------------------------------------------------------------*/
    private ScrollPane buildBusinessCardScreen() {
        //-------- Initialization Start ----------\\
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox();
        VP_PageDivision bcardPageBox = new VP_PageDivision("BUSINESS CARD");
        VP_FieldLabel firstNameLabel = new VP_FieldLabel("first name:", 110),
                middleNameLabel = new VP_FieldLabel("*middle name:", 110),
                lastNameLabel = new VP_FieldLabel("last name:", 110),
                professionLabel = new VP_FieldLabel("profession:", 110),
                companyNameLabel = new VP_FieldLabel("*company name:", 110),
                companySloganLabel = new VP_FieldLabel("*company slogan:", 110),
                address1Label = new VP_FieldLabel("address line 1:", 110),
                address2Label = new VP_FieldLabel("*address line 2:", 110),
                cityLabel = new VP_FieldLabel("city:", 110),
                stateLabel = new VP_FieldLabel("state:", 110),
                zipLabel = new VP_FieldLabel("zipcode:", 110),
                phoneLabel = new VP_FieldLabel("phone:", 110),
                cellLabel = new VP_FieldLabel("*cell:", 110),
                emailLabel = new VP_FieldLabel("email:", 110),
                webpageLabel = new VP_FieldLabel("*web page:", 110);
        ArrayList<VP_TextField> businessCardFields = new ArrayList();
        businessCardFields.add(new VP_TextField(32, 45));   // bind this to user
        businessCardFields.add(new VP_TextField(32, 45));   // bind this to user
        businessCardFields.add(new VP_TextField(32, 45));   // bind this to user
        businessCardFields.add(new VP_TextField(32, 48));   // bind this to card
        businessCardFields.add(new VP_TextField(32, 48));   // bind this to card
        businessCardFields.add(new VP_TextField(32, 128));  // bind this to card
        businessCardFields.add(new VP_TextField(32, 254));  // bind this to user
        businessCardFields.add(new VP_TextField(32, 254));  // bind this to user
        businessCardFields.add(new VP_TextField(32, 45));   // bind this to user
        businessCardFields.add(new VP_TextField(2, 2));     // bind this to user
        businessCardFields.add(new VP_TextField(10, 10));   // bind this to suer
        businessCardFields.add(new VP_TextField(13, 13));   // bind this to user
        businessCardFields.add(new VP_TextField(13, 13));   // bind this to user
        businessCardFields.add(new VP_TextField(32, 254));  // bind this to user
        businessCardFields.add(new VP_TextField(32, 48));   // bind this to card
        VP_Paragraph notes = new VP_Paragraph("(*) denotes an optional field. "
                + "Locked fields can be edited by updating your personal info.");
        VP_Button submitBtn = new VP_Button("Submit", new SubmitBCardAction(businessCardFields)),
                cancelBtn = new VP_Button("Cancel", new CancelAction());
        VP_DivisionLine firstNameLine = new VP_DivisionLine(new Node[]{firstNameLabel, businessCardFields.get(0)}),
                middleNameLine = new VP_DivisionLine(new Node[]{middleNameLabel, businessCardFields.get(1)}),
                lastNameLine = new VP_DivisionLine(new Node[]{lastNameLabel, businessCardFields.get(2)}),
                professionLine = new VP_DivisionLine(new Node[]{professionLabel, businessCardFields.get(3)}),
                companyNameLine = new VP_DivisionLine(new Node[]{companyNameLabel, businessCardFields.get(4)}),
                companySloganLine = new VP_DivisionLine(new Node[]{companySloganLabel, businessCardFields.get(5)}),
                address1Line = new VP_DivisionLine(new Node[]{address1Label, businessCardFields.get(6)}),
                address2Line = new VP_DivisionLine(new Node[]{address2Label, businessCardFields.get(7)}),
                cityLine = new VP_DivisionLine(new Node[]{cityLabel, businessCardFields.get(8)}),
                stateLine = new VP_DivisionLine(new Node[]{stateLabel, businessCardFields.get(9)}),
                zipLine = new VP_DivisionLine(new Node[]{zipLabel, businessCardFields.get(10)}),
                phoneLine = new VP_DivisionLine(new Node[]{phoneLabel, businessCardFields.get(11)}),
                cellLine = new VP_DivisionLine(new Node[]{cellLabel, businessCardFields.get(12)}),
                emailLine = new VP_DivisionLine(new Node[]{emailLabel, businessCardFields.get(13)}),
                webpageLine = new VP_DivisionLine(new Node[]{webpageLabel, businessCardFields.get(14)}),
                notesLine = new VP_DivisionLine(new Node[]{notes}),
                buttonsLine = new VP_DivisionLine(new Node[]{submitBtn, cancelBtn});
        VP_PageSubdivision name = new VP_PageSubdivision("NAME", false),
                company = new VP_PageSubdivision("COMPANY", false),
                address = new VP_PageSubdivision("ADDRESS", false),
                communication = new VP_PageSubdivision("COMMUNICATION", false);
        //-------- Initialization End ------------\\
        bcNodes.add(name);
        bcNodes.add(company);
        bcNodes.add(address);
        bcNodes.add(communication);
        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-20));
        businessCardFields.get(0).textProperty().bindBidirectional(controller.getCurrentUser().getFirstName());
        businessCardFields.get(1).textProperty().bindBidirectional(controller.getCurrentUser().getMiddleName());
        businessCardFields.get(2).textProperty().bindBidirectional(controller.getCurrentUser().getLastName());
        businessCardFields.get(3).textProperty().bindBidirectional(controller.getCurrentUser().getBcard().getProfession());
        businessCardFields.get(4).textProperty().bindBidirectional(controller.getCurrentUser().getBcard().getCompanyName());
        businessCardFields.get(5).textProperty().bindBidirectional(controller.getCurrentUser().getBcard().getCompanySlogan());
        businessCardFields.get(6).textProperty().bindBidirectional(controller.getCurrentUser().getAddress1());
        businessCardFields.get(7).textProperty().bindBidirectional(controller.getCurrentUser().getAddress2());
        businessCardFields.get(8).textProperty().bindBidirectional(controller.getCurrentUser().getCity());
        businessCardFields.get(9).textProperty().bindBidirectional(controller.getCurrentUser().getState());
        businessCardFields.get(10).textProperty().bindBidirectional(controller.getCurrentUser().getZip());
        businessCardFields.get(11).textProperty().bindBidirectional(controller.getCurrentUser().getPhone());
        businessCardFields.get(12).textProperty().bindBidirectional(controller.getCurrentUser().getCell());
        businessCardFields.get(13).textProperty().bindBidirectional(controller.getCurrentUser().getDocEmail());
        businessCardFields.get(14).textProperty().bindBidirectional(controller.getCurrentUser().getBcard().getWebPage());
        for (int i = 0; i < businessCardFields.size(); i++) {
            if (i != 3 && i != 4 && i != 5 && i != 14) {
                businessCardFields.get(i).setEditable(false);
                businessCardFields.get(i).setDisable(true);
            }
        }
        name.getChildren().addAll(firstNameLine, middleNameLine, lastNameLine);
        company.getChildren().addAll(professionLine, companyNameLine, companySloganLine);
        address.getChildren().addAll(address1Line, address2Line, cityLine, stateLine, zipLine);
        communication.getChildren().addAll(phoneLine, cellLine, emailLine, webpageLine);
        bcardErrorLine.getChildren().addAll(bcardError);
        bcardErrorLine.hide();
        bcardPageBox.getChildren().addAll(name, company, address, communication,
                notesLine, bcardErrorLine, buttonsLine);
        
        screenContent.getChildren().addAll(bcardPageBox);
        screenContent.setSpacing(30);
        screenContent.setPadding(new Insets(20, 20, 20, 20));
        screen.setContent(screenContent);
        screen.setPannable(true);
        return screen;
    }

    /*------------------------------------------------------------------------*
     * buildCoverLettersStartScreen()
     * - Builds the screen where the user sees the list of existing cover
     *   letters or begins a new one.
     *   A.K.A Screen 6
     * - No parameters.
     * - Returns a scroller that gets applied to a center stackpane level.
     *------------------------------------------------------------------------*/
    private ScrollPane buildCoverLettersStartScreen() {
        //-------- Initialization Start ----------\\
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox();
        VP_PageDivision covLetListBox = new VP_PageDivision("COVER LETTERS");
        //-------- Initialization End ------------\\

        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-20));

        screenContent.getChildren().addAll(covLetListBox);
        screenContent.setSpacing(30);
        screenContent.setPadding(new Insets(20, 20, 20, 20));
        screen.setContent(screenContent);
        screen.setPannable(true);
        return screen;
    }

    /*------------------------------------------------------------------------*
     * buildCoverLettersEditScreen()
     * - Builds the screen where the user edits a selected cover letter. 
     *   A.K.A Screen 7
     * - No parameters.
     * - Returns a scroller that gets applied to a center stackpane level.
     *------------------------------------------------------------------------*/
    private ScrollPane buildCoverLettersEditScreen() {
        //-------- Initialization Start ----------\\
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox();
        VP_PageDivision covLetEditBox = new VP_PageDivision("EDIT COVER LETTER");
        VP_FieldLabel firstNameLabel = new VP_FieldLabel("first name:", 140),
                middleNameLabel = new VP_FieldLabel("*middle name:", 140),
                lastNameLabel = new VP_FieldLabel("last name:", 140),
                address1Label = new VP_FieldLabel("address line 1:", 140),
                address2Label = new VP_FieldLabel("*address line 2:", 140),
                cityLabel = new VP_FieldLabel("city:", 140),
                stateLabel = new VP_FieldLabel("state:", 140),
                zipLabel = new VP_FieldLabel("zipcode:", 140),
                phoneLabel = new VP_FieldLabel("phone:", 140),
                cellLabel = new VP_FieldLabel("*cell:", 140),
                emailLabel = new VP_FieldLabel("email:", 140),
                dateLabel = new VP_FieldLabel("date:", 140),
                adSourceLabel = new VP_FieldLabel("*job ad source:", 140),
                adJobLabel = new VP_FieldLabel("*job position:", 140),
                adRefLabel = new VP_FieldLabel("*ad reference no:", 140),
                contactFirstNameLabel = new VP_FieldLabel("contact first name:", 140),
                contactMiddleNameLabel = new VP_FieldLabel("*contact middle name:", 140),
                contactLastNameLabel = new VP_FieldLabel("contact last name:", 140),
                contactTitleLabel = new VP_FieldLabel("*contact title:", 140),
                contactCompanyLabel = new VP_FieldLabel("*company name:", 140),
                contactAddress1Label = new VP_FieldLabel("contact address line 1:", 140),
                contactAddress2Label = new VP_FieldLabel("*contact address line 2:", 140),
                contactCityLabel = new VP_FieldLabel("contact city:", 140),
                contactStateLabel = new VP_FieldLabel("conact state:", 140),
                contactZipLabel = new VP_FieldLabel("contact zipcode:", 140),
                salutationLabel = new VP_FieldLabel("salutation:", 140),
                paragraph1Label = new VP_FieldLabel("paragraph 1:", 140),
                closingLabel = new VP_FieldLabel("closing:", 140),
                sigFirstNameLabel = new VP_FieldLabel("first name:", 140),
                sigMiddleNameLabel = new VP_FieldLabel("*middle name:", 140),
                sigLastNameLabel = new VP_FieldLabel("last name:", 140);

        coverLetterEditFields.add(new VP_TextField(32, 45));   // bind this to user
        coverLetterEditFields.add(new VP_TextField(32, 45));   // bind this to user
        coverLetterEditFields.add(new VP_TextField(32, 45));   // bind this to user
        coverLetterEditFields.add(new VP_TextField(32, 254));  // bind this to user
        coverLetterEditFields.add(new VP_TextField(32, 254));  // bind this to user
        coverLetterEditFields.add(new VP_TextField(32, 45));   // bind this to user
        coverLetterEditFields.add(new VP_TextField(2, 2));     // bind this to user
        coverLetterEditFields.add(new VP_TextField(10, 10));   // bind this to user
        coverLetterEditFields.add(new VP_TextField(13, 13));   // bind this to user
        coverLetterEditFields.add(new VP_TextField(13, 13));   // bind this to user
        coverLetterEditFields.add(new VP_TextField(32, 254));  // bind this to user
        coverLetterEditFields.add(new VP_TextField(32, 128));  // bind this to cover letter
        coverLetterEditFields.add(new VP_TextField(32, 128));  // bind this to cover letter
        coverLetterEditFields.add(new VP_TextField(32, 128));  // bind this to cover letter
        coverLetterEditFields.add(new VP_TextField(32, 45));   // bind this to cover letter
        coverLetterEditFields.add(new VP_TextField(32, 45));   // bind this to cover letter
        coverLetterEditFields.add(new VP_TextField(32, 45));   // bind this to cover letter
        coverLetterEditFields.add(new VP_TextField(32, 48));   // bind this tocover letter
        coverLetterEditFields.add(new VP_TextField(32, 48));   // bind this to cover letter
        coverLetterEditFields.add(new VP_TextField(32, 254));  // bind this to cover letter
        coverLetterEditFields.add(new VP_TextField(32, 254));  // bind this to cover letter
        coverLetterEditFields.add(new VP_TextField(32, 45));   // bind this to cover letter
        coverLetterEditFields.add(new VP_TextField(2, 2));     // bind this to cover letter
        coverLetterEditFields.add(new VP_TextField(10, 10));   // bind this to cover letter
        coverLetterEditFields.add(new VP_TextField(32, 128));  // bind this to cover letter
        coverLetterEditFields.add(new VP_TextArea());  // bind this to cover letter
        coverLetterEditFields.add(new VP_TextField(32, 128));  // bind this to cover letter
        coverLetterEditFields.add(new VP_TextField(32, 45));   // bind this to user
        coverLetterEditFields.add(new VP_TextField(32, 45));   // bind this to user
        coverLetterEditFields.add(new VP_TextField(32, 45));   // bind this to user
        VP_Paragraph notes = new VP_Paragraph("(*) denotes an optional field. "
                + "Locked fields can be edited by updating your personal info.");
        VP_PageSubdivision heading = new VP_PageSubdivision("HEADING", true),
                name = new VP_PageSubdivision("NAME", false),
                address = new VP_PageSubdivision("ADDRESS", false),
                communication = new VP_PageSubdivision("COMMUNICATION", false),
                dateDivision = new VP_PageSubdivision("", false),
                adref = new VP_PageSubdivision("AD REFERENCE", false),
                contact = new VP_PageSubdivision("CONTACT INFORMATION", true),
                contactName = new VP_PageSubdivision("CONTACT NAME", false),
                contactCompany = new VP_PageSubdivision("CONTACT COMPANY", false),
                contactAddress = new VP_PageSubdivision("CONTACT ADDRESS", false),
                salutation = new VP_PageSubdivision("", false),
                closing = new VP_PageSubdivision("", false),
                signature = new VP_PageSubdivision("SIGNATURE", true),
                sigName = new VP_PageSubdivision("NAME", false);
        VP_Button submitBtn = new VP_Button("Submit", new SubmitCovLetEditAction()),
                cancelBtn = new VP_Button("Cancel", new CancelAction()),
                dateBtn = new VP_Button("Update", new UpdateDateAction()),
                delPara1Btn = new VP_Button("Delete", new DeleteParagraphAction(1)),
                addParaBtn = new VP_Button("Add a New Paragraph", new AddParagraphAction());
        VP_DivisionLine firstNameLine = new VP_DivisionLine(new Node[]{firstNameLabel, coverLetterEditFields.get(0)}),
                middleNameLine = new VP_DivisionLine(new Node[]{middleNameLabel, coverLetterEditFields.get(1)}),
                lastNameLine = new VP_DivisionLine(new Node[]{lastNameLabel, coverLetterEditFields.get(2)}),
                address1Line = new VP_DivisionLine(new Node[]{address1Label, coverLetterEditFields.get(3)}),
                address2Line = new VP_DivisionLine(new Node[]{address2Label, coverLetterEditFields.get(4)}),
                cityLine = new VP_DivisionLine(new Node[]{cityLabel, coverLetterEditFields.get(5)}),
                stateLine = new VP_DivisionLine(new Node[]{stateLabel, coverLetterEditFields.get(6)}),
                zipLine = new VP_DivisionLine(new Node[]{zipLabel, coverLetterEditFields.get(7)}),
                phoneLine = new VP_DivisionLine(new Node[]{phoneLabel, coverLetterEditFields.get(8)}),
                cellLine = new VP_DivisionLine(new Node[]{cellLabel, coverLetterEditFields.get(9)}),
                emailLine = new VP_DivisionLine(new Node[]{emailLabel, coverLetterEditFields.get(10)}),
                dateLine = new VP_DivisionLine(new Node[]{dateLabel, dateValueLabel, dateBtn}),
                adSourceLine = new VP_DivisionLine(new Node[]{adSourceLabel, coverLetterEditFields.get(11)}),
                adJobLine = new VP_DivisionLine(new Node[]{adJobLabel, coverLetterEditFields.get(12)}),
                adRefLine = new VP_DivisionLine(new Node[]{adRefLabel, coverLetterEditFields.get(13)}),
                contactFirstNameLine = new VP_DivisionLine(new Node[]{contactFirstNameLabel, coverLetterEditFields.get(14)}),
                contactMiddleNameLine = new VP_DivisionLine(new Node[]{contactMiddleNameLabel, coverLetterEditFields.get(15)}),
                contactLastNameLine = new VP_DivisionLine(new Node[]{contactLastNameLabel, coverLetterEditFields.get(16)}),
                contactTitleLine = new VP_DivisionLine(new Node[]{contactTitleLabel, coverLetterEditFields.get(17)}),
                contactCompanyLine = new VP_DivisionLine(new Node[]{contactCompanyLabel, coverLetterEditFields.get(18)}),
                contactAddress1Line = new VP_DivisionLine(new Node[]{contactAddress1Label, coverLetterEditFields.get(19)}),
                contactAddress2Line = new VP_DivisionLine(new Node[]{contactAddress2Label, coverLetterEditFields.get(20)}),
                contactCityLine = new VP_DivisionLine(new Node[]{contactCityLabel, coverLetterEditFields.get(21)}),
                contactStateLine = new VP_DivisionLine(new Node[]{contactStateLabel, coverLetterEditFields.get(22)}),
                contactZipLine = new VP_DivisionLine(new Node[]{contactZipLabel, coverLetterEditFields.get(23)}),
                salutationLine = new VP_DivisionLine(new Node[]{salutationLabel, coverLetterEditFields.get(24)}),
                paragraph1Line = new VP_DivisionLine(new Node[]{paragraph1Label, coverLetterEditFields.get(25), delPara1Btn}),
                closingLine = new VP_DivisionLine(new Node[]{closingLabel, coverLetterEditFields.get(26)}),
                sigFirstNameLine = new VP_DivisionLine(new Node[]{sigFirstNameLabel, coverLetterEditFields.get(27)}),
                sigMiddleNameLine = new VP_DivisionLine(new Node[]{sigMiddleNameLabel, coverLetterEditFields.get(28)}),
                sigLastNameLine = new VP_DivisionLine(new Node[]{sigLastNameLabel, coverLetterEditFields.get(29)}),
                buttonsLine = new VP_DivisionLine(new Node[]{submitBtn, cancelBtn});
        
        //-------- Initialization End ------------\\

        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-20));
        ((VP_TextField)(coverLetterEditFields.get(0))).textProperty().bindBidirectional(controller.getCurrentUser().getFirstName());
        ((VP_TextField)(coverLetterEditFields.get(1))).textProperty().bindBidirectional(controller.getCurrentUser().getMiddleName());
        ((VP_TextField)(coverLetterEditFields.get(2))).textProperty().bindBidirectional(controller.getCurrentUser().getLastName());
        ((VP_TextField)(coverLetterEditFields.get(3))).textProperty().bindBidirectional(controller.getCurrentUser().getAddress1());
        ((VP_TextField)(coverLetterEditFields.get(4))).textProperty().bindBidirectional(controller.getCurrentUser().getAddress2());
        ((VP_TextField)(coverLetterEditFields.get(5))).textProperty().bindBidirectional(controller.getCurrentUser().getCity());
        ((VP_TextField)(coverLetterEditFields.get(6))).textProperty().bindBidirectional(controller.getCurrentUser().getState());
        ((VP_TextField)(coverLetterEditFields.get(7))).textProperty().bindBidirectional(controller.getCurrentUser().getZip());
        ((VP_TextField)(coverLetterEditFields.get(8))).textProperty().bindBidirectional(controller.getCurrentUser().getPhone());
        ((VP_TextField)(coverLetterEditFields.get(9))).textProperty().bindBidirectional(controller.getCurrentUser().getCell());
        ((VP_TextField)(coverLetterEditFields.get(10))).textProperty().bindBidirectional(controller.getCurrentUser().getDocEmail());
        dateValueLabel.textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getDate());
        ((VP_TextField)(coverLetterEditFields.get(11))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getAdSource());
        ((VP_TextField)(coverLetterEditFields.get(12))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getAdJobTitle());
        ((VP_TextField)(coverLetterEditFields.get(13))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getAdRefNumber());
        ((VP_TextField)(coverLetterEditFields.get(14))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getContactFirstName());
        ((VP_TextField)(coverLetterEditFields.get(15))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getContactMiddleName());
        ((VP_TextField)(coverLetterEditFields.get(16))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getContactLastName());
        ((VP_TextField)(coverLetterEditFields.get(17))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getContactTitle());
        ((VP_TextField)(coverLetterEditFields.get(18))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getContactCompany());
        ((VP_TextField)(coverLetterEditFields.get(19))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getContactAddress1());
        ((VP_TextField)(coverLetterEditFields.get(20))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getContactAddress2());
        ((VP_TextField)(coverLetterEditFields.get(21))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getContactCity());
        ((VP_TextField)(coverLetterEditFields.get(22))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getContactState());
        ((VP_TextField)(coverLetterEditFields.get(23))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getContactZip());
        ((VP_TextField)(coverLetterEditFields.get(24))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getSalutation());
        ((VP_TextArea)(coverLetterEditFields.get(25))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getParagraphs().get(0));
        ((VP_TextField)(coverLetterEditFields.get(26))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getClosing());
        ((VP_TextField)(coverLetterEditFields.get(27))).textProperty().bindBidirectional(controller.getCurrentUser().getFirstName());
        ((VP_TextField)(coverLetterEditFields.get(28))).textProperty().bindBidirectional(controller.getCurrentUser().getMiddleName());
        ((VP_TextField)(coverLetterEditFields.get(29))).textProperty().bindBidirectional(controller.getCurrentUser().getLastName());
        
        for (int i = 0; i < coverLetterEditFields.size(); i++) {
            if (i < 11 || i > 25) {
                ((VP_TextField)(coverLetterEditFields.get(i))).setEditable(false);
                coverLetterEditFields.get(i).setDisable(true);
            }
        }
        covletEditErrorLine.getChildren().addAll(covletEditError);
        addParagraphLine.getChildren().addAll(addParaBtn);
        covletEditErrorLine.hide();
        name.getChildren().addAll(firstNameLine, middleNameLine, lastNameLine);
        address.getChildren().addAll(address1Line, address2Line, cityLine, stateLine, zipLine);
        communication.getChildren().addAll(phoneLine, cellLine, emailLine);
        heading.getChildren().addAll(name, address, communication);
        dateDivision.getChildren().addAll(dateLine);
        adref.getChildren().addAll(adSourceLine, adJobLine, adRefLine);
        contactName.getChildren().addAll(contactFirstNameLine, contactMiddleNameLine, contactLastNameLine);
        contactCompany.getChildren().addAll(contactTitleLine, contactCompanyLine);
        contactAddress.getChildren().addAll(contactAddress1Line, contactAddress2Line, contactCityLine, contactStateLine, contactZipLine);
        contact.getChildren().addAll(contactName, contactCompany, contactAddress);
        salutation.getChildren().addAll(salutationLine);
        dynamicBody.getChildren().addAll(paragraph1Line, addParagraphLine);
        closing.getChildren().addAll(closingLine);
        sigName.getChildren().addAll(sigFirstNameLine, sigMiddleNameLine, sigLastNameLine);
        signature.getChildren().addAll(sigName);
        covLetEditBox.getChildren().addAll(heading, dateDivision, adref, contact, 
                salutation, dynamicBody, closing, signature, notes, covletEditErrorLine, buttonsLine);
        screenContent.getChildren().addAll(covLetEditBox);
        screenContent.setSpacing(30);
        screenContent.setPadding(new Insets(20, 20, 20, 20));
        screen.setContent(screenContent);
        screen.setPannable(true);
        return screen;
    }

    /*------------------------------------------------------------------------*
     * buildThemesStartScreen()
     * - Builds the screen where the user sees a list of available themes and 
     *   applies them to documents. From here, a user may select to build a
     *   custom theme. 
     *   A.K.A Screen 8
     * - No parameters.
     * - Returns a scroller that gets applied to a center stackpane level.
     *------------------------------------------------------------------------*/
    private ScrollPane buildThemesStartScreen() {
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox();
        VP_PageDivision themesListBox = new VP_PageDivision("DOCUMENT THEMES");

        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-20));

        screenContent.getChildren().addAll(themesListBox);
        screenContent.setSpacing(30);
        screenContent.setPadding(new Insets(20, 20, 20, 20));
        screen.setContent(screenContent);
        screen.setPannable(true);
        return screen;
    }

    /*------------------------------------------------------------------------*
     * buildThemesEditScreen()
     * - Builds the screen where the user edits a selected custom theme. 
     *   A.K.A Screen 9
     * - No parameters.
     * - Returns a scroller that gets applied to a center stackpane level.
     *------------------------------------------------------------------------*/
    private ScrollPane buildThemesEditScreen() {
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox();
        VP_PageDivision themeEditBox = new VP_PageDivision("EDIT CUSTOM THEME");

        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-20));

        screenContent.getChildren().addAll(themeEditBox);
        screenContent.setSpacing(30);
        screenContent.setPadding(new Insets(20, 20, 20, 20));
        screen.setContent(screenContent);
        screen.setPannable(true);
        return screen;
    }

    /*------------------------------------------------------------------------*
     * buildDistributeScreen()
     * - Builds the screen where the user selects which documents to send as
     *   attachments to a selected contact. User may also edit the list of
     *   stored contacts.
     *   A.K.A Screen 10
     * - No parameters.
     * - Returns a scroller that gets applied to a center stackpane level.
     *------------------------------------------------------------------------*/
    private ScrollPane buildDistributeScreen() {
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox();
        VP_PageDivision distributeBox = new VP_PageDivision("DISTRIBUTE DOCUMENTS");

        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-20));

        screenContent.getChildren().addAll(distributeBox);
        screenContent.setSpacing(30);
        screenContent.setPadding(new Insets(20, 20, 20, 20));
        screen.setContent(screenContent);
        screen.setPannable(true);
        return screen;
    }

    /*------------------------------------------------------------------------*
     * buildResumeStartScreen()
     * - Builds the screen displaying the completion status of the various 
     *   resume sections. From here, the user navigates to these sections to
     *   edit them, or may choose to create a custom section.
     *   A.K.A Screen 11
     * - No parameters.
     * - Returns a scroller that gets applied to a center stackpane level.
     *------------------------------------------------------------------------*/
    private ScrollPane buildResumeStartScreen() {
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox();
        VP_PageDivision resumeStatusBox = new VP_PageDivision("RESUME");

        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-20));

        screenContent.getChildren().addAll(resumeStatusBox);
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
        loginEmail.showValid();
        loginPass.setText("");
        loginPass.setDisable(false);
        loginPass.setEditable(true);
        loginPass.showValid();
        loginButtonLine.show();
        loginError.setParaText("");
        loginErrorLine.hide();
        accessInstructions.setParaText("Enter the access code that was emailed to "
                + "you when you registered below.");
        accessInstructionsLine.hide();
        accessLine.hide();
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
        resetEmail.showValid();
        submitResetBtn.setDisable(false);
        resetError.setParaText("");
        resetErrorLine.hide();
        resetInstructions1.setParaText("Enter your email and submit. "
                + "An access code will be sent to your email.");
        resetInstructions2.setParaText("Enter your new password, confirm it, "
                + "and enter the access code that was sent to you.");
        resetInstructions1Line.show();
        resetInstructions2Line.hide();
        resetNewPassLine.hide();
        resetPassStrengthLabel.setText("");
        resetPassStrengthLine.hide();
        resetNewPassConfirmLine.hide();
        resetCodeLine.hide();
        resetButLine.hide();
        resetCode.setText("");
        resetCode.showValid();
        resetNewPass.setText("");
        resetNewPass.showValid();
        resetNewPassConfirm.setText("");
        resetNewPassConfirm.showValid();
    }

    /*------------------------------------------------------------------------*
     * resetRegisterForms()
     * - Restores the registration page back to its original state.
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    private void resetRegisterForms() {
        registerEmail.setText("");
        registerEmail.showValid();
        registerPass.setText("");
        registerPass.showValid();
        registerPassConfirm.setText("");
        registerPassConfirm.showValid();
        registerError.setText("");
        registerErrorLine.hide();
    }

    /*------------------------------------------------------------------------*
     * updateOverview()
     * - Adjust the overview page depending on what the user has completed.
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    private void updateOverview() {
        //-------- Initialization Start ----------\\
        VP_User thisUser = controller.getCurrentUser();
        //-------- Initialization End ------------\\

        if (thisUser != null) {
            infoProgress.getStyleClass().remove(infoProgress.getStyleClass().size() - 1);
            resumeProgress.getStyleClass().remove(resumeProgress.getStyleClass().size() - 1);
            bcardProgress.getStyleClass().remove(bcardProgress.getStyleClass().size() - 1);
            covletProgress.getStyleClass().remove(covletProgress.getStyleClass().size() - 1);
            if (!thisUser.hasCompletedPersonalInfo()) {
                infoProgress.getStyleClass().add("notStarted");
                infoProgress.setText("Not started");
                overviewInfo.setParaText("Welcome to VaqPack! Before you can begin building "
                        + "your resume, business card, and cover letters, we need to gather "
                        + "your personal information. This information remains private to "
                        + "you and is stored for the sole purpose of automatically filling in "
                        + "text in your documents. Click \"Update Personal Information\" below "
                        + "to get started.");
                for (int i = 1; i < wizardMainButtons.size(); i++) {
                    wizardMainButtons.get(i).setDisable(true);
                }
            } else {
                infoProgress.getStyleClass().add("complete");
                infoProgress.setText("Complete");
                wizardMainButtons.get(1).setDisable(false);
                wizardMainButtons.get(2).setDisable(false);
                wizardMainButtons.get(3).setDisable(false);
                if (thisUser.getResume().hasCompletedResume() || thisUser.getBcard().hasCompletedBusinessCard()
                        || thisUser.getCovlet().hasCompletedCoverLetter()) {
                    overviewInfo.setParaText("You have completed updating your personal information "
                            + "and at least one document. You may update any document or information at "
                            + "any time. You may now also apply a theme to your completed documents and "
                            + "send them via email.");
                    wizardMainButtons.get(4).setDisable(false);
                    wizardMainButtons.get(5).setDisable(false);
                } else {

                    overviewInfo.setParaText("You have completed updating your personal information. "
                            + "You may go back to edit this information at any time. Your next step is to "
                            + "complete any document of your choice.");
                }
            }
            if (thisUser.getResume().hasCompletedResume()) {
                resumeProgress.getStyleClass().add("complete");
                resumeProgress.setText("Complete");
            } else if (thisUser.getResume().hasStartedResume()) {
                resumeProgress.getStyleClass().add("inProgress");
                resumeProgress.setText("In progress");
            } else {
                resumeProgress.getStyleClass().add("notStarted");
                resumeProgress.setText("Not started");
            }
            if (thisUser.getBcard().hasCompletedBusinessCard()) {
                bcardProgress.getStyleClass().add("complete");
                bcardProgress.setText("Complete");
            } else if (thisUser.getBcard().hasStartedBusinessCard()) {
                bcardProgress.getStyleClass().add("inProgress");
                bcardProgress.setText("In progress");
            } else {
                bcardProgress.getStyleClass().add("notStarted");
                bcardProgress.setText("Not started");
            }
            if (thisUser.getCovlet().hasCompletedCoverLetter()) {
                covletProgress.getStyleClass().add("complete");
                covletProgress.setText("Complete");
            } else if (thisUser.getCovlet().hasStartedCoverLetter()) {
                covletProgress.getStyleClass().add("inProgress");
                covletProgress.setText("In progress");
            } else {
                covletProgress.getStyleClass().add("notStarted");
                covletProgress.setText("Not started");
            }
        }
    }

    protected void cancelActionFunction() {
        VP_Sounds.play(0);
        bcardError.setParaText("");
        bcardErrorLine.hide();
        controller.getCurrentUser().getBcard().revert();
        covletEditError.setParaText("");
        covletEditErrorLine.hide();
        controller.getCurrentUser().getCovlet().revert();
        personalInfoError.setParaText("");
        personalInfoErrorLine.hide();
        controller.getCurrentUser().revert();
        updateDynamicFields();
    }

    protected void cancelActionPreloginFunction() {
        VP_Sounds.play(0);
        resetLoginRegForms();
        resetResetPasswordForms();
        resetRegisterForms();
    }

    
    protected void updateDynamicFields() {
        if ((dynamicBody.getChildren().size() - 2) != controller.getCurrentUser().getCovlet().getNumbParagraphs()) {
            if ((dynamicBody.getChildren().size() - 2) > controller.getCurrentUser().getCovlet().getNumbParagraphs()) {
                while ((dynamicBody.getChildren().size() - 2) > controller.getCurrentUser().getCovlet().getNumbParagraphs()) {
                    dynamicBody.getChildren().remove(dynamicBody.getChildren().size() - 2);
                    coverLetterEditFields.remove(25 + controller.getCurrentUser().getCovlet().getNumbParagraphs());
                }
            }
            else {
                while ((dynamicBody.getChildren().size() - 2) < controller.getCurrentUser().getCovlet().getNumbParagraphs()) {
                    VP_FieldLabel newParaLabel = new VP_FieldLabel("paragraph " + (dynamicBody.getChildren().size() - 1) + ":", 140);
                    VP_Button delParaBtn = new VP_Button("Delete", new DeleteParagraphAction(dynamicBody.getChildren().size() - 1));
                    coverLetterEditFields.add(23 + dynamicBody.getChildren().size(), new VP_TextArea());
                    VP_DivisionLine newParaLine = new VP_DivisionLine(new Node[]{newParaLabel, coverLetterEditFields.get(23 + dynamicBody.getChildren().size()), delParaBtn});
                    dynamicBody.getChildren().add(dynamicBody.getChildren().size() - 1, newParaLine);
                }
                for (int i = 0; i < controller.getCurrentUser().getCovlet().getNumbParagraphs(); i++) {
                    ((VP_FieldLabel)((VP_DivisionLine)(dynamicBody.getChildren().get(i + 1))).getChildren().get(0)).setText("paragraph " + (i + 1) + ":");
                    ((VP_Button)((VP_DivisionLine)(dynamicBody.getChildren().get(i + 1))).getChildren().get(2)).setOnAction(new DeleteParagraphAction(i + 1));
                    ((VP_TextArea)(coverLetterEditFields.get(25 + i))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getParagraphs().get(i));
                }
            }
            addParagraphLine.show();
            if (controller.getCurrentUser().getCovlet().getNumbParagraphs() == 9) {
                addParagraphLine.hide();
            }
            controller.updateTree();
        }
    }

    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*------------------------------------------------------------------------*
     * Subclass CancelAction
     * - Reverts any information changed in post-login forms back to the 
     *   user's saved values and brings the user back to the Overview
     *   page.
     *------------------------------------------------------------------------*/
    private class CancelAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            cancelActionFunction();
            showScreen(3, 0);
        }
    }
    
    /*------------------------------------------------------------------------*
     * Subclass DeleteParagraphAction
     * - 
     *------------------------------------------------------------------------*/
    private class AddParagraphAction implements EventHandler<ActionEvent> {
        
        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            for (int i = 0; i < controller.getCurrentUser().getCovlet().getNumbParagraphs(); i++) {
                ((VP_TextArea)(coverLetterEditFields.get(25 + i))).textProperty().unbind();
            }
            int newParaNumb = controller.getCurrentUser().getCovlet().getNumbParagraphs() + 1;
            VP_FieldLabel newParaLabel = new VP_FieldLabel("paragraph " + newParaNumb + ":", 140);
            VP_Button delParaBtn = new VP_Button("Delete", new DeleteParagraphAction(newParaNumb));
            coverLetterEditFields.add(24 + newParaNumb, new VP_TextArea());
            VP_DivisionLine newParaLine = new VP_DivisionLine(new Node[]{newParaLabel, coverLetterEditFields.get(24 + newParaNumb), delParaBtn});
            dynamicBody.getChildren().add(dynamicBody.getChildren().size() - 1, newParaLine);
            controller.getCurrentUser().getCovlet().setNumbParagraphs(newParaNumb);
            for (int i = 0; i < controller.getCurrentUser().getCovlet().getNumbParagraphs(); i++) {
                ((VP_TextArea)(coverLetterEditFields.get(25 + i))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getParagraphs().get(i));
            }
            if (controller.getCurrentUser().getCovlet().getNumbParagraphs() == 9) {
                addParagraphLine.hide();
            }
        }
    }
    
    /*------------------------------------------------------------------------*
     * Subclass DeleteParagraphAction
     * - 
     *------------------------------------------------------------------------*/
    private class DeleteParagraphAction implements EventHandler<ActionEvent> {
        private final int paragraphNumber;
        
        public DeleteParagraphAction(int paragraphNumber){
            this.paragraphNumber = paragraphNumber;
        }
        
        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            for (int i = 0; i < controller.getCurrentUser().getCovlet().getNumbParagraphs(); i++) {
                ((VP_TextArea)(coverLetterEditFields.get(25 + i))).textProperty().unbind();
            }
            if (controller.getCurrentUser().getCovlet().getNumbParagraphs() > 1) {
                controller.getCurrentUser().getCovlet().setNumbParagraphs(controller.getCurrentUser().getCovlet().getNumbParagraphs() - 1);
                coverLetterEditFields.remove(24 + paragraphNumber);
                dynamicBody.getChildren().remove(paragraphNumber);
                controller.getCurrentUser().getCovlet().getParagraphs().remove(paragraphNumber - 1);
                controller.getCurrentUser().getCovlet().getParagraphs().add(new SimpleStringProperty());
                for (int i = 0; i < controller.getCurrentUser().getCovlet().getNumbParagraphs(); i++) {
                    ((VP_FieldLabel)((VP_DivisionLine)(dynamicBody.getChildren().get(i + 1))).getChildren().get(0)).setText("paragraph " + (i + 1) + ":");
                    ((VP_Button)((VP_DivisionLine)(dynamicBody.getChildren().get(i + 1))).getChildren().get(2)).setOnAction(new DeleteParagraphAction(i + 1));
                    
                    ((VP_TextArea)(coverLetterEditFields.get(25 + i))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getParagraphs().get(i));
                }
            } else {
                ((VP_TextArea)(coverLetterEditFields.get(25))).setText("");
            }
            addParagraphLine.show();
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass SubmitCovLetEditAction
     * - Saves any information changed in the Business Card  page and brings the
     *   user back to the Overview page.
     *------------------------------------------------------------------------*/
    private class SubmitCovLetEditAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            //-------- Initialization Start ----------\\
            boolean hasError = false;
            String zipRegex = "^[0-9]{5}(?:-[0-9]{4})?$";
            Pattern zipPattern = Pattern.compile(zipRegex);
            Matcher matcher;
            //-------- Initialization End ------------\\
            VP_Sounds.play(0);
            for (int i = 11; i < 25; i++) {
                ((VP_TextField)(coverLetterEditFields.get(i))).textProperty().setValue(((VP_TextField)(coverLetterEditFields.get(i))).textProperty().getValueSafe().trim());
            }
            for (int i = 25; i < 25 + controller.getCurrentUser().getCovlet().getNumbParagraphs(); i++) {
                ((VP_TextArea)(coverLetterEditFields.get(i))).textProperty().setValue(((VP_TextArea)(coverLetterEditFields.get(i))).textProperty().getValueSafe().trim());
            }
            ((VP_TextField)(coverLetterEditFields.get(25 + controller.getCurrentUser().getCovlet().getNumbParagraphs()))).textProperty().setValue(((VP_TextField)(coverLetterEditFields.get(25 + controller.getCurrentUser().getCovlet().getNumbParagraphs()))).textProperty().getValueSafe().trim());
            if (((VP_TextField)(coverLetterEditFields.get(14))).textProperty().getValueSafe().equals("")) {
                hasError = true;
                ((VP_TextField)(coverLetterEditFields.get(14))).showInvalid();
                covletEditError.setParaText("Contact First Name cannot be blank.");
            } else if (((VP_TextField)(coverLetterEditFields.get(16))).textProperty().getValueSafe().equals("")) {
                hasError = true;
                ((VP_TextField)(coverLetterEditFields.get(16))).showInvalid();
                covletEditError.setParaText("Contact Last Name cannot be blank.");
            } else if (((VP_TextField)(coverLetterEditFields.get(19))).textProperty().getValueSafe().equals("")) {
                hasError = true;
                ((VP_TextField)(coverLetterEditFields.get(19))).showInvalid();
                covletEditError.setParaText("Contact Address Line 1 cannot be blank.");
            } else if (((VP_TextField)(coverLetterEditFields.get(21))).textProperty().getValueSafe().equals("")) {
                hasError = true;
                ((VP_TextField)(coverLetterEditFields.get(21))).showInvalid();
                covletEditError.setParaText("Contact City cannot be blank.");
            } else if (((VP_TextField)(coverLetterEditFields.get(22))).textProperty().getValueSafe().equals("")) {
                hasError = true;
                ((VP_TextField)(coverLetterEditFields.get(22))).showInvalid();
                covletEditError.setParaText("Contact State cannot be blank.");
            }  else if (((VP_TextField)(coverLetterEditFields.get(22))).textProperty().getValueSafe().length() != 2) {
                hasError = true;
                ((VP_TextField)(coverLetterEditFields.get(22))).showInvalid();
                covletEditError.setParaText("Contact State must be two characters.");
            } else if (((VP_TextField)(coverLetterEditFields.get(23))).textProperty().getValueSafe().equals("")) {
                hasError = true;
                ((VP_TextField)(coverLetterEditFields.get(23))).showInvalid();
                covletEditError.setParaText("Contact Zipcode cannot be blank.");
            } else if (((VP_TextField)(coverLetterEditFields.get(24))).textProperty().getValueSafe().equals("")) {
                hasError = true;
                ((VP_TextField)(coverLetterEditFields.get(24))).showInvalid();
                covletEditError.setParaText("Salutation cannot be blank.");
            }  else if (((VP_TextField)(coverLetterEditFields.get(25 + controller.getCurrentUser().getCovlet().getNumbParagraphs()))).textProperty().getValueSafe().equals("")) {
                hasError = true;
                ((VP_TextField)(coverLetterEditFields.get(25 + controller.getCurrentUser().getCovlet().getNumbParagraphs()))).showInvalid();
                covletEditError.setParaText("Closing cannot be blank.");
            } else {
                matcher = zipPattern.matcher(((VP_TextField)(coverLetterEditFields.get(23))).textProperty().getValueSafe());
                if (!matcher.matches()) {
                    hasError = true;
                    ((VP_TextField)(coverLetterEditFields.get(23))).showInvalid();
                    covletEditError.setParaText("Contact Zipcode is not in proper form. "
                            + "Zipcodes can only be in form xxxxx or xxxxx-xxxx");
                } else if (((VP_TextArea)(coverLetterEditFields.get(25))).textProperty().getValueSafe().equals("")) {
                    hasError = true;
                    ((VP_TextArea)(coverLetterEditFields.get(25))).showInvalid();
                    covletEditError.setParaText("The first paragraph of the body cannot be blank.");
                } else if (controller.getCurrentUser().getCovlet().getNumbParagraphs() > 1) {
                    for (int i = 0; i < controller.getCurrentUser().getCovlet().getNumbParagraphs(); i++) {
                        if (((VP_TextArea)(coverLetterEditFields.get(25 + i))).textProperty().getValueSafe().equals("")) {
                            hasError = true;
                            ((VP_TextArea)(coverLetterEditFields.get(25 + i))).showInvalid();
                            covletEditError.setParaText("Please delete blank paragraphs before submitting.");
                            break;
                        }
                    }
                }
            }
            if (hasError) {
                VP_Sounds.play(-1);
                covletEditErrorLine.show();
            } else {
                covletEditError.setParaText("");
                covletEditErrorLine.hide();
                controller.getCurrentUser().getCovlet().save();
                if (controller.getCurrentUser().getCovlet().hasChanges()) {
                    controller.updateTree();
                    try {
                        controller.getDataM().saveCovLetData();
                    } catch (SQLException ex) {
                        controller.errorAlert(1415, ex.getMessage());
                    } catch (TransformerException | ParserConfigurationException | IOException | DocumentException ex) {
                        controller.errorAlert(2901, ex.getMessage());
                    }
                }
                showScreen(3, 0);
            }
        }
    }
    
    /*------------------------------------------------------------------------*
     * Subclass UpdateDateAction
     * - Updates the date on the cover letter from its perviously stored value
     *   to a new value representing the current date.
     *------------------------------------------------------------------------*/
    private class UpdateDateAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM d, yyyy");
            String formattedDate = formatter.format(new Date());
            dateValueLabel.setText(formattedDate);
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass SubmitBCardAction
     * - Saves any information changed in the Business Card  page and brings the
     *   user back to the Overview page.
     *------------------------------------------------------------------------*/
    private class SubmitBCardAction implements EventHandler<ActionEvent> {

        private final ArrayList<VP_TextField> businessCardFields;

        public SubmitBCardAction(ArrayList<VP_TextField> businessCardFields) {
            this.businessCardFields = businessCardFields;
        }

        @Override
        public void handle(ActionEvent event) {
            //-------- Initialization Start ----------\\
            boolean hasError = false;
            String webRegex = "^https?://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
            Pattern webPattern = Pattern.compile(webRegex);
            Matcher matcher;
            //-------- Initialization End ------------\\

            VP_Sounds.play(0);
            businessCardFields.get(3).textProperty().setValue(businessCardFields.get(3).textProperty().getValueSafe().trim());
            businessCardFields.get(4).textProperty().setValue(businessCardFields.get(4).textProperty().getValueSafe().trim());
            businessCardFields.get(5).textProperty().setValue(businessCardFields.get(5).textProperty().getValueSafe().trim());
            businessCardFields.get(14).textProperty().setValue(businessCardFields.get(14).textProperty().getValueSafe().trim());
            if (businessCardFields.get(3).textProperty().getValueSafe() == null) {
                hasError = true;
                businessCardFields.get(3).showInvalid();
                bcardError.setParaText("Profession cannot be blank");
            } else if (!businessCardFields.get(14).textProperty().getValueSafe().equals("")) {
                matcher = webPattern.matcher(businessCardFields.get(14).textProperty().getValueSafe());
                if (!matcher.matches()) {
                    hasError = true;
                    businessCardFields.get(14).showInvalid();
                    bcardError.setParaText("The web address is not in valid form. Be sure to include whether "
                            + "the address is http or https");
                }
            }
            if (hasError) {
                VP_Sounds.play(-1);
                bcardErrorLine.show();
            } else {
                bcardError.setParaText("");
                bcardErrorLine.hide();
                controller.getCurrentUser().getBcard().save();
                if (controller.getCurrentUser().getBcard().hasChanges()) {
                    try {
                        controller.getDataM().saveBCardData();
                    } catch (SQLException ex) {
                        controller.errorAlert(1414, ex.getMessage());
                    } catch (TransformerException | ParserConfigurationException | IOException | DocumentException ex) {
                        controller.errorAlert(2901, ex.getMessage());
                    }
                }
                showScreen(3, 0);
            }
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass SubmitPersonalInfoAction
     * - Saves any information changed in the Personal Information page and 
     *   brings the user back to the Overview page.
     *------------------------------------------------------------------------*/
    private class SubmitPersonalInfoAction implements EventHandler<ActionEvent> {

        private final ArrayList<VP_TextField> personalInfoFields;

        public SubmitPersonalInfoAction(ArrayList<VP_TextField> personalInfoFields) {
            this.personalInfoFields = personalInfoFields;
        }

        @Override
        public void handle(ActionEvent event) {
            //-------- Initialization Start ----------\\
            boolean hasError = false;
            String zipRegex = "^[0-9]{5}(?:-[0-9]{4})?$",
                    phoneRegex = "\\([0-9]{3}\\)[0-9]{3}\\-[0-9]{4}$";
            Pattern zipPattern = Pattern.compile(zipRegex),
                    phonePattern = Pattern.compile(phoneRegex);
            Matcher matcher;
            //-------- Initialization End ------------\\

            VP_Sounds.play(0);
            for (int i = 0; i < personalInfoFields.size(); i++) {
                personalInfoFields.get(i).textProperty().setValue(personalInfoFields.get(i).textProperty().getValueSafe().trim());
            }
            personalInfoFields.get(6).textProperty().setValue(personalInfoFields.get(6).textProperty().getValueSafe().toUpperCase());
            personalInfoFields.get(10).textProperty().setValue(personalInfoFields.get(10).textProperty().getValueSafe().toLowerCase());
            if (personalInfoFields.get(10).textProperty().getValueSafe().equals("")) {
                personalInfoFields.get(10).textProperty().setValue(controller.getCurrentUser().getEmail().getValue());
            }
            if (personalInfoFields.get(0).textProperty().getValueSafe().equals("")) {
                hasError = true;
                personalInfoFields.get(0).showInvalid();
                personalInfoError.setParaText("First Name cannot be blank.");
            } else if (personalInfoFields.get(2).textProperty().getValueSafe().equals("")) {
                hasError = true;
                personalInfoFields.get(2).showInvalid();
                personalInfoError.setParaText("Last Name cannot be blank.");
            } else if (personalInfoFields.get(3).textProperty().getValueSafe().equals("")) {
                hasError = true;
                personalInfoFields.get(3).showInvalid();
                personalInfoError.setParaText("Address Line 1 cannot be blank.");
            } else if (personalInfoFields.get(5).textProperty().getValueSafe().equals("")) {
                hasError = true;
                personalInfoFields.get(5).showInvalid();
                personalInfoError.setParaText("City cannot be blank.");
            } else if (personalInfoFields.get(6).textProperty().getValueSafe().equals("")) {
                hasError = true;
                personalInfoFields.get(6).showInvalid();
                personalInfoError.setParaText("State cannot be blank.");
            } else if (personalInfoFields.get(6).textProperty().getValueSafe().length() != 2) {
                hasError = true;
                personalInfoFields.get(6).showInvalid();
                personalInfoError.setParaText("State must be two characters.");
            } else if (personalInfoFields.get(7).textProperty().getValueSafe().equals("")) {
                hasError = true;
                personalInfoFields.get(7).showInvalid();
                personalInfoError.setParaText("Zipcode cannot be blank.");
            } else if (personalInfoFields.get(8).textProperty().getValueSafe().equals("")) {
                hasError = true;
                personalInfoFields.get(8).showInvalid();
                personalInfoError.setParaText("Phone cannot be blank.");
            } else {
                matcher = zipPattern.matcher(personalInfoFields.get(7).textProperty().getValueSafe());
                if (!matcher.matches()) {
                    hasError = true;
                    personalInfoFields.get(7).showInvalid();
                    personalInfoError.setParaText("Zipcode is not in proper form. "
                            + "Zipcodes can only be in form xxxxx or xxxxx-xxxx");
                } else {
                    matcher = phonePattern.matcher(personalInfoFields.get(8).textProperty().getValueSafe());
                    if (!matcher.matches()) {
                        hasError = true;
                        personalInfoFields.get(8).showInvalid();
                        personalInfoError.setParaText("Phone numbers must be in form "
                                + "(xxx)xxx-xxxx");
                    } else {
                        if (personalInfoFields.get(9).textProperty().getValueSafe().length() > 0) {
                            matcher = phonePattern.matcher(personalInfoFields.get(9).textProperty().getValueSafe());
                            if (!matcher.matches()) {
                                hasError = true;
                                personalInfoFields.get(9).showInvalid();
                                personalInfoError.setParaText("Phone numbers must be in  form "
                                        + "(xxx)xxx-xxxx");
                            }
                        }
                        if ((!hasError) && (!personalInfoFields.get(10).textProperty().getValueSafe().equals(""))) {
                            hasError = (!controller.getDataM().checkEmail(personalInfoFields.get(10).textProperty().getValueSafe()));
                            if (hasError) {
                                personalInfoFields.get(10).showInvalid();
                                personalInfoError.setParaText("Email is not in valid form.");
                            }
                        }
                    }
                }
            }
            if (hasError) {
                VP_Sounds.play(-1);
                personalInfoErrorLine.show();
            } else {
                personalInfoError.setParaText("");
                personalInfoErrorLine.hide();
                controller.getCurrentUser().save();
                try {
                    controller.getDataM().saveUserData();
                } catch (SQLException ex) {
                    controller.errorAlert(1413, ex.getMessage());
                } finally {
                    showScreen(3, 0);
                }
            }
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass WizardMainAction
     * - Brings the user to one of the main wizard pages
     *------------------------------------------------------------------------*/
    private class WizardMainAction implements EventHandler<ActionEvent> {

        private final int wizardPage;

        public WizardMainAction(int wizardPage) {
            this.wizardPage = wizardPage;
        }

        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            showScreen(wizardPage, 0);
        }
    }

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
            showScreen(1, 0);
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
            showScreen(2, 0);
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
            accessInstructions.setParaText("Enter the access code that was emailed "
                    + "to you when you registered below.");
            if (controller.getDataM().checkEmail(cred[0])) {
                try {
                    loginStatus = controller.getDataM().userLogin(cred);
                    if (loginStatus == -1) {
                        // user does not exist in database
                        loginError.setParaText("The email address and/or password is "
                                + "incorrect. Please try again.");
                        loginErrorLine.show();
                        loginEmail.showInvalid();
                        loginPass.showInvalid();
                        VP_Sounds.play(-1);
                    } else if (loginStatus == -2) {
                        // user needs to enter registration code
                        loginEmail.setDisable(true);
                        loginEmail.setEditable(false);
                        loginPass.setDisable(true);
                        loginPass.setEditable(false);
                        loginError.setParaText("");
                        loginErrorLine.hide();
                        loginButtonLine.hide();
                        accessInstructionsLine.show();
                        accessLine.show();
                    } else {
                        // user login successful
                        resetLoginRegForms();
                        controller.updateTree();
                        showScreen(3, 0);
                    }
                } catch (SQLException ex) {
                    controller.errorAlert(1407, ex.getMessage());
                } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                    controller.errorAlert(1202, ex.getMessage());
                }
            } else {
                loginError.setParaText("The email provided is invalid. Please try again.");
                loginErrorLine.show();
                loginEmail.showInvalid();
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
                accessInstructions.setParaText("A new access code has been emailed and "
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
                    cred[1] = loginPass.getText();
                    controller.getDataM().userLogin(cred);
                    resetLoginRegForms();
                    controller.updateTree();
                    showScreen(3, 0);
                } else {
                    loginError.setText("The registration code is incorrect. Please try again.");
                    accessInstructions.setParaText("Enter the access code that was emailed to you below.");
                    loginErrorLine.show();
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
     * Subclass CancelActionPrelogin
     * - Action event for the cancel button on pages before login or involving 
     *   login. Calls cancelActionPreloginFunction().
     *------------------------------------------------------------------------*/
    private class CancelActionPrelogin implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            cancelActionPreloginFunction();
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
            if (controller.getDataM().checkEmail(resetEmail.getText())) {
                try {
                    userStatus = controller.getDataM().findUserForReset(resetEmail.getText().toLowerCase());
                    if (userStatus == 0) {
                        resetEmail.showInvalid();
                        resetError.setParaText("The provided user does not exists in VaqPack. "
                                + "If the email is correct, you need to register a new account.");
                        resetErrorLine.show();
                        VP_Sounds.play(-1);
                    } else if (userStatus == 1) {
                        resetEmail.showInvalid();
                        resetError.setParaText("The password for this email has recently been reset. "
                                + "Passwords may only be reset once every 24 hours.");
                        resetErrorLine.show();
                        VP_Sounds.play(-1);
                    } else {
                        resetError.setParaText("");
                        resetErrorLine.hide();
                        submitResetBtn.setDisable(true);
                        resetEmail.setDisable(true);
                        resetEmail.setEditable(false);
                        resetInstructions1Line.hide();
                        resetInstructions2Line.show();
                        resetNewPassLine.show();
                        resetPassStrengthLabel.setText("");
                        resetPassStrengthLine.show();
                        resetNewPassConfirmLine.show();
                        resetCodeLine.show();
                        resetButLine.show();
                    }
                } catch (SQLException ex) {
                    controller.errorAlert(1411, ex.getMessage());
                }
            } else {
                resetError.setParaText("The email provided is invalid. Please try again.");
                resetErrorLine.show();
                resetEmail.showInvalid();
                VP_Sounds.play(-1);
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
                resetError.setParaText("The new password is not long enough.");
                resetErrorLine.show();
                VP_Sounds.play(-1);
            } else if (!cred[1].equals(cred[2])) {
                resetNewPass.showInvalid();
                resetNewPassConfirm.showInvalid();
                resetError.setParaText("The passwords do not match.");
                resetErrorLine.show();
                VP_Sounds.play(-1);
            } else {
                try {
                    resetStatus = controller.getDataM().resetPass(cred);
                    if (resetStatus == 2) {
                        accessInstructions.setParaText("Your password was reset. Login with your new password.");
                        accessInstructionsLine.show();
                        resetResetPasswordForms();
                        showScreen(0, 0);
                    } else if (resetStatus == 1) {
                        resetCode.showInvalid();
                        resetError.setParaText("The code has expired. Cancel and start the reset process over again.");
                        resetErrorLine.show();
                        VP_Sounds.play(-1);
                    } else if (resetStatus == 0) {
                        resetCode.showInvalid();
                        resetError.setParaText("The code is incorrect. Please try again.");
                        resetErrorLine.show();
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
            if (controller.getDataM().checkEmail(cred[0])) {
                if (cred[1].length() < controller.getUSER_PASSWORD_MINIMUM()) {
                    registerPass.showInvalid();
                    registerPassConfirm.showInvalid();
                    registerError.setText("The password is not long enough.");
                    registerErrorLine.show();
                    VP_Sounds.play(-1);
                } else if (!cred[1].equals(cred[2])) {
                    registerPass.showInvalid();
                    registerPassConfirm.showInvalid();
                    registerError.setText("The passwords do not match.");
                    registerErrorLine.show();
                    VP_Sounds.play(-1);
                } else {
                    try {
                        registerStatus = controller.getDataM().regUser(cred);
                        if (registerStatus == 2) {
                            accessInstructions.setParaText("Login with your new account.");
                            accessInstructionsLine.show();
                            resetRegisterForms();
                            showScreen(0, 0);
                        } else if (registerStatus == 1) {
                            registerEmail.showInvalid();
                            registerError.setText("This email is already associated "
                                    + "with a VaqPack user.");
                            registerErrorLine.show();
                            VP_Sounds.play(-1);
                        } else if (registerStatus == 0) {
                            registerEmail.showInvalid();
                            registerError.setText("This email is already associated "
                                    + "with a VaqPack user who recently registered "
                                    + "but has not yet enetered in the access code. "
                                    + "Login and you will be prompted to enter in "
                                    + "this code. If you did not receive the code, "
                                    + "there will be an option to resend one.");
                            registerErrorLine.show();
                            VP_Sounds.play(-1);
                        }
                    } catch (SQLException ex) {
                        controller.errorAlert(1412, ex.getMessage());
                    } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                        controller.errorAlert(1206, ex.getMessage());
                    }
                }
            } else {
                registerEmail.showInvalid();
                registerError.setText("The email provided is invalid. Please try again.");
                registerErrorLine.show();
                VP_Sounds.play(-1);
            }
        }
    }

    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/
    
    protected ArrayList<VP_PageSubdivision> getBcNodes() {
        return bcNodes;
    }
}
