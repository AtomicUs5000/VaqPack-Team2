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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
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
            covletProgress,
            resObjProgress,
            resEduProgress,
            resExpProgress,
            resQualProgress,
            resHighProgress,
            resLangProgress,
            resSWProgress;
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
            addParagraphLine,
            selectCoverLetterLine,
            objectiveErrorLine;
    private final VP_Paragraph loginError,
            accessInstructions,
            resetError,
            resetInstructions1,
            resetInstructions2,
            registerError,
            overviewInfo,
            personalInfoError,
            bcardError,
            covletEditError,
            coverLetterDetails,
            objectiveError;
    private final VP_Button submitResetBtn,
            startNewBtn;
    private final ComboBox coverLetterSelect;
    private final ArrayList<VP_Button> wizardMainButtons;
    private final ArrayList<VP_PageSubdivision> bcNodes,
            clNodes;
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
        selectCoverLetterLine = new VP_DivisionLine();
        objectiveErrorLine = new VP_DivisionLine();
        loginError = new VP_Paragraph("", true);
        resetError = new VP_Paragraph("", true);
        registerError = new VP_Paragraph("", true);
        personalInfoError = new VP_Paragraph("", true);
        bcardError = new VP_Paragraph("", true);
        covletEditError = new VP_Paragraph("", true);
        objectiveError = new VP_Paragraph("", true);
        coverLetterDetails = new VP_Paragraph("", false);
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
        resObjProgress = new Label();
        resEduProgress = new Label();
        resExpProgress = new Label();
        resQualProgress = new Label();
        resHighProgress = new Label();
        resLangProgress = new Label();
        resSWProgress = new Label();
        submitResetBtn = new VP_Button("Submit", new SubmitResetAction());
        startNewBtn = new VP_Button("Start New Cover Letter", new StartNewCoverLetter());
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
        clNodes = new ArrayList();
        coverLetterEditFields = new ArrayList();
        dateValueLabel = new VP_FieldLabel("", 200);
        dynamicBody = new VP_PageSubdivision("BODY", false);
        coverLetterSelect = new ComboBox();
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
                buildLoginScreen(), //................screen 0
                buildResetPasswordScreen(), //........screen 1
                buildRegistrationScreen(), //.........screen 2
                buildOverviewScreen(), //.............screen 3
                buildPersonalInfoScreen(), //.........screen 4
                buildBusinessCardScreen(), //.........screen 5
                buildCoverLettersStartScreen(), //....screen 6
                buildCoverLettersEditScreen(), //.....screen 7
                buildThemesStartScreen(), //..........screen 8
                buildThemesEditScreen(), //...........screen 9
                buildDistributeScreen(), //...........screen 10
                buildResumeStartScreen(), //..........screen 11
                buildResumeObjectiveScreen(), //......screen 12
                buildResumeEducationScreen(), //......screen 13
                buildResumeExperienceScreen(), //.....screen 14
                buildResumeAchievementsScreen(), //...screen 15
                buildResumeCommunityScreen(), //......screen 16
                buildResumeQualificationsScreen(), //.screen 17
                buildResumeHighlightsScreen(), //......screen 18
                buildResumeLanguagesScreen(), //......screen 19
                buildResumeSoftwareScreen(), //.......screen 20
                buildResumeReferencesScreen() //......screen 21
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
            ((TreeView) (controller.getLeftTree().getChildren().get(0))).getSelectionModel().clearAndSelect(0);
            updateOverview();
        } else if (screenNumber == 4) {
            ((TreeView) (controller.getLeftTree().getChildren().get(0))).getSelectionModel().clearAndSelect(1);
        } else if (screenNumber == 5) {
            ((TreeView) (controller.getLeftTree().getChildren().get(0))).getSelectionModel().clearAndSelect(3);
        }  else if (screenNumber == 6) {
            ((TreeView) (controller.getLeftTree().getChildren().get(0))).getSelectionModel().clearAndSelect(4);
        }  else if (screenNumber == 8) {
            ((TreeView) (controller.getLeftTree().getChildren().get(0))).getSelectionModel().clearAndSelect(5);
        }  else if (screenNumber == 10) {
            ((TreeView) (controller.getLeftTree().getChildren().get(0))).getSelectionModel().clearAndSelect(6);
        } else if (screenNumber == 11) {
            ((TreeView) (controller.getLeftTree().getChildren().get(0))).getSelectionModel().clearAndSelect(2);
            updateResumeStatus();
        }
        getChildren().get(screenNumber).setVisible(true);
        ((ScrollPane) (getChildren().get(screenNumber))).setVvalue(position);
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
        VP_PageSubdivision tasks = new VP_PageSubdivision("TASKS", false);
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
        tasks.getChildren().addAll(step1Line, step2Line, step3Line, step4Line,
                step5Line, step6Line);
        overviewBox.getChildren().addAll(overviewInfo, tasks);
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
        VP_PageSubdivision name = new VP_PageSubdivision("NAME", false),
                address = new VP_PageSubdivision("ADDRESS", false),
                communication = new VP_PageSubdivision("COMMUNICATION", false);
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
        name.getChildren().addAll(firstNameLine, middleNameLine, lastNameLine);
        address.getChildren().addAll(address1Line, address2Line, cityLine, stateLine, zipLine);
        communication.getChildren().addAll(phoneLine, cellLine, emailLine);
        personalInfoBox.getChildren().addAll(name, address, communication, notesLine, personalInfoErrorLine, buttonsLine);
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
        VP_DivisionLine buttonLine = new VP_DivisionLine();
        VP_Button selectCoverLetterButton = new VP_Button("Load", new LoadCoverLetterAction()),
                cancelBtn = new VP_Button("Cancel", new CancelAction());
        VP_PageSubdivision select = new VP_PageSubdivision("SELECT", false);
        //-------- Initialization End ------------\\
        coverLetterDetails.setParaText("You are currently using 0 out of 3 cover letters available to you."
                + "\nClick \"Start New Cover Letter\" to begin working on a new cover letter.");
        selectCoverLetterLine.setPadding(new Insets(30, 0, 30, 100));
        selectCoverLetterLine.getChildren().addAll(coverLetterSelect, selectCoverLetterButton);
        selectCoverLetterLine.hide();
        
        buttonLine.getChildren().addAll(startNewBtn, cancelBtn);
        covLetListBox.getChildren().addAll(coverLetterDetails, selectCoverLetterLine, buttonLine);
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
                + "Locked fields can be edited by updating your personal info. You may create up to 9 paragraphs.");
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
        clNodes.add(heading);
        clNodes.add(name);
        clNodes.add(address);
        clNodes.add(communication);
        clNodes.add(dateDivision);
        clNodes.add(adref);
        clNodes.add(contact);
        clNodes.add(contactName);
        clNodes.add(contactCompany);
        clNodes.add(contactAddress);
        clNodes.add(salutation);
        clNodes.add(dynamicBody);
        clNodes.add(closing);
        clNodes.add(signature);
        clNodes.add(sigName);
        VP_Button submitBtn = new VP_Button("Submit", new SubmitCovLetEditAction()),
                cancelBtn = new VP_Button("Cancel", new CancelAction(7)),
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
        ((VP_TextField) (coverLetterEditFields.get(0))).textProperty().bindBidirectional(controller.getCurrentUser().getFirstName());
        ((VP_TextField) (coverLetterEditFields.get(1))).textProperty().bindBidirectional(controller.getCurrentUser().getMiddleName());
        ((VP_TextField) (coverLetterEditFields.get(2))).textProperty().bindBidirectional(controller.getCurrentUser().getLastName());
        ((VP_TextField) (coverLetterEditFields.get(3))).textProperty().bindBidirectional(controller.getCurrentUser().getAddress1());
        ((VP_TextField) (coverLetterEditFields.get(4))).textProperty().bindBidirectional(controller.getCurrentUser().getAddress2());
        ((VP_TextField) (coverLetterEditFields.get(5))).textProperty().bindBidirectional(controller.getCurrentUser().getCity());
        ((VP_TextField) (coverLetterEditFields.get(6))).textProperty().bindBidirectional(controller.getCurrentUser().getState());
        ((VP_TextField) (coverLetterEditFields.get(7))).textProperty().bindBidirectional(controller.getCurrentUser().getZip());
        ((VP_TextField) (coverLetterEditFields.get(8))).textProperty().bindBidirectional(controller.getCurrentUser().getPhone());
        ((VP_TextField) (coverLetterEditFields.get(9))).textProperty().bindBidirectional(controller.getCurrentUser().getCell());
        ((VP_TextField) (coverLetterEditFields.get(10))).textProperty().bindBidirectional(controller.getCurrentUser().getDocEmail());
        dateValueLabel.textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getDate());
        ((VP_TextField) (coverLetterEditFields.get(11))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getAdSource());
        ((VP_TextField) (coverLetterEditFields.get(12))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getAdJobTitle());
        ((VP_TextField) (coverLetterEditFields.get(13))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getAdRefNumber());
        ((VP_TextField) (coverLetterEditFields.get(14))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getContactFirstName());
        ((VP_TextField) (coverLetterEditFields.get(15))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getContactMiddleName());
        ((VP_TextField) (coverLetterEditFields.get(16))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getContactLastName());
        ((VP_TextField) (coverLetterEditFields.get(17))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getContactTitle());
        ((VP_TextField) (coverLetterEditFields.get(18))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getContactCompany());
        ((VP_TextField) (coverLetterEditFields.get(19))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getContactAddress1());
        ((VP_TextField) (coverLetterEditFields.get(20))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getContactAddress2());
        ((VP_TextField) (coverLetterEditFields.get(21))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getContactCity());
        ((VP_TextField) (coverLetterEditFields.get(22))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getContactState());
        ((VP_TextField) (coverLetterEditFields.get(23))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getContactZip());
        ((VP_TextField) (coverLetterEditFields.get(24))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getSalutation());
        ((VP_TextArea) (coverLetterEditFields.get(25))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getParagraphs().get(0));
        ((VP_TextField) (coverLetterEditFields.get(26))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getClosing());
        ((VP_TextField) (coverLetterEditFields.get(27))).textProperty().bindBidirectional(controller.getCurrentUser().getFirstName());
        ((VP_TextField) (coverLetterEditFields.get(28))).textProperty().bindBidirectional(controller.getCurrentUser().getMiddleName());
        ((VP_TextField) (coverLetterEditFields.get(29))).textProperty().bindBidirectional(controller.getCurrentUser().getLastName());

        for (int i = 0; i < coverLetterEditFields.size(); i++) {
            if (i < 11 || i > 26) {
                ((VP_TextField) (coverLetterEditFields.get(i))).setEditable(false);
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
        //-------- Initialization Start ----------\\
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox();
        VP_PageDivision resumeStatusBox = new VP_PageDivision("RESUME");
        VP_Button cancelBtn = new VP_Button("Cancel", new CancelAction());
        ArrayList<Label> progressList = new ArrayList();
        progressList.add(resObjProgress);
        progressList.add(resEduProgress);
        progressList.add(resExpProgress);
        progressList.add(resQualProgress);
        progressList.add(resHighProgress);
        progressList.add(resLangProgress);
        progressList.add(resSWProgress);
        ArrayList<VP_Button> resUpdateBtns = new ArrayList();
        resUpdateBtns.add(new VP_Button("Update Objective", new WizardMainAction(12)));
        resUpdateBtns.add(new VP_Button("Update Education", new WizardMainAction(13)));
        resUpdateBtns.add(new VP_Button("Update Work Experience", new WizardMainAction(14)));
        resUpdateBtns.add(new VP_Button("Update Achievements / Awards", new WizardMainAction(15)));
        resUpdateBtns.add(new VP_Button("Update Community Involvement", new WizardMainAction(16)));
        resUpdateBtns.add(new VP_Button("Update Your Qualifications", new WizardMainAction(17)));
        resUpdateBtns.add(new VP_Button("Update Your Highlights", new WizardMainAction(18)));
        resUpdateBtns.add(new VP_Button("Update Languages", new WizardMainAction(19)));
        resUpdateBtns.add(new VP_Button("Update Software", new WizardMainAction(20)));
        resUpdateBtns.add(new VP_Button("Update Your References", new WizardMainAction(21)));
        VP_DivisionLine step1Line = new VP_DivisionLine(new Node[]{resUpdateBtns.get(0), resObjProgress}),
                step2Line = new VP_DivisionLine(new Node[]{resUpdateBtns.get(1), resEduProgress}),
                step3Line = new VP_DivisionLine(new Node[]{resUpdateBtns.get(2), resExpProgress}),
                step4Line = new VP_DivisionLine(new Node[]{resUpdateBtns.get(3), new VP_FieldLabel("optional")}),
                step5Line = new VP_DivisionLine(new Node[]{resUpdateBtns.get(4), new VP_FieldLabel("optional")}),
                step6Line = new VP_DivisionLine(new Node[]{resUpdateBtns.get(5), resQualProgress}),
                step7Line = new VP_DivisionLine(new Node[]{resUpdateBtns.get(6), resHighProgress}),
                step8Line = new VP_DivisionLine(new Node[]{resUpdateBtns.get(7), resLangProgress}),
                step9Line = new VP_DivisionLine(new Node[]{resUpdateBtns.get(8), resSWProgress}),
                step10Line = new VP_DivisionLine(new Node[]{resUpdateBtns.get(9), new VP_FieldLabel("optional")}),
                buttonLine = new VP_DivisionLine(new Node[]{cancelBtn});
        VP_PageSubdivision tasks = new VP_PageSubdivision("RESUME TASKS", false);
        //-------- Initialization End ------------\\
        for (int i = 0; i < resUpdateBtns.size(); i++) {
            resUpdateBtns.get(i).setPrefWidth(250);
        }
        for (int i = 0; i < progressList.size(); i++) {
            progressList.get(i).getStyleClass().add("notStarted");
            progressList.get(i).setMinWidth(250);
            progressList.get(i).setAlignment(Pos.CENTER_RIGHT);
        }
        updateResumeStatus();
        tasks.getChildren().addAll(step1Line, step2Line,
                step3Line, step4Line, step5Line, step6Line, step7Line, step8Line, step9Line, step10Line);
        resumeStatusBox.getChildren().addAll(tasks, buttonLine);
        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-20));
        screenContent.getChildren().addAll(resumeStatusBox);
        screenContent.setSpacing(30);
        screenContent.setPadding(new Insets(20, 20, 20, 20));
        screen.setContent(screenContent);
        screen.setPannable(true);
        return screen;
    }
    
    /*------------------------------------------------------------------------*
     * buildResumeObjectiveScreen()
     * - Builds the screen displaying the Heading and Objective sections of the
     *   resume.
     *   A.K.A. screen 12
     * - No parameters.
     * - Returns a scroller that gets applied to a center stackpane level.
     *------------------------------------------------------------------------*/
    private ScrollPane buildResumeObjectiveScreen() {
        //-------- Initialization Start ----------\\
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox();
        VP_PageDivision resumeHOBox = new VP_PageDivision("RESUME -- HEADING and OBJECTIVE");
        VP_PageSubdivision heading = new VP_PageSubdivision("HEADING", true),
                objective = new VP_PageSubdivision("OBJECTIVE", false);
        VP_TextArea objectiveParagraph = new VP_TextArea();
        VP_FieldLabel firstNameLabel = new VP_FieldLabel("first name:", 110),       
                middleNameLabel = new VP_FieldLabel("*middle name:", 110),
                lastNameLabel = new VP_FieldLabel("last name:", 110),
                address1Label = new VP_FieldLabel("address line 1:", 110),
                address2Label = new VP_FieldLabel("*address line 2:", 110),
                cityLabel = new VP_FieldLabel("city:", 110),
                stateLabel = new VP_FieldLabel("state:", 110),
                zipLabel = new VP_FieldLabel("zipcode:", 110),
                phoneLabel = new VP_FieldLabel("phone:", 110),
                cellLabel = new VP_FieldLabel("*cell:", 110),
                emailLabel = new VP_FieldLabel("email:", 110);
        ArrayList<VP_TextField> headingFields = new ArrayList();
        headingFields.add(new VP_TextField(32, 45));   // bind this to user
        headingFields.add(new VP_TextField(32, 45));   // bind this to user
        headingFields.add(new VP_TextField(32, 45));   // bind this to user
        headingFields.add(new VP_TextField(32, 254));  // bind this to user
        headingFields.add(new VP_TextField(32, 254));  // bind this to user
        headingFields.add(new VP_TextField(32, 45));   // bind this to user
        headingFields.add(new VP_TextField(2, 2));     // bind this to user
        headingFields.add(new VP_TextField(10, 10));   // bind this to suer
        headingFields.add(new VP_TextField(13, 13));   // bind this to user
        headingFields.add(new VP_TextField(13, 13));   // bind this to user
        headingFields.add(new VP_TextField(32, 254));  // bind this to user
        VP_Button submitBtn = new VP_Button("Submit", new SubmitObjectiveAction(objectiveParagraph)),
                cancelBtn = new VP_Button("Cancel", new CancelAction(11));
        VP_DivisionLine firstNameLine = new VP_DivisionLine(new Node[]{firstNameLabel, headingFields.get(0)}),
                middleNameLine = new VP_DivisionLine(new Node[]{middleNameLabel, headingFields.get(1)}),
                lastNameLine = new VP_DivisionLine(new Node[]{lastNameLabel, headingFields.get(2)}),
                address1Line = new VP_DivisionLine(new Node[]{address1Label, headingFields.get(3)}),
                address2Line = new VP_DivisionLine(new Node[]{address2Label, headingFields.get(4)}),
                cityLine = new VP_DivisionLine(new Node[]{cityLabel, headingFields.get(5)}),
                stateLine = new VP_DivisionLine(new Node[]{stateLabel, headingFields.get(6)}),
                zipLine = new VP_DivisionLine(new Node[]{zipLabel, headingFields.get(7)}),
                phoneLine = new VP_DivisionLine(new Node[]{phoneLabel, headingFields.get(8)}),
                cellLine = new VP_DivisionLine(new Node[]{cellLabel, headingFields.get(9)}),
                emailLine = new VP_DivisionLine(new Node[]{emailLabel, headingFields.get(10)}),
                objectiveLine = new VP_DivisionLine(new Node[]{objectiveParagraph}),
                buttonsLine = new VP_DivisionLine(new Node[]{submitBtn, cancelBtn});
        VP_PageSubdivision name = new VP_PageSubdivision("NAME", false),
                address = new VP_PageSubdivision("ADDRESS", false),
                communication = new VP_PageSubdivision("COMMUNICATION", false);
        VP_Paragraph hNotes = new VP_Paragraph("Locked fields can be edited by updating your personal info."),
                oNotes = new VP_Paragraph("Write a short statement that clearly defines your career goals and direction. "
                        + "The objective section of your resume should be tailored to fit what a specific employer is looking for. "
                        + "Keep this is mind when sending your resume to multiple employers.");
        //-------- Initialization End ------------\\
        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-20));
        headingFields.get(0).textProperty().bindBidirectional(controller.getCurrentUser().getFirstName());
        headingFields.get(1).textProperty().bindBidirectional(controller.getCurrentUser().getMiddleName());
        headingFields.get(2).textProperty().bindBidirectional(controller.getCurrentUser().getLastName());
        headingFields.get(3).textProperty().bindBidirectional(controller.getCurrentUser().getAddress1());
        headingFields.get(4).textProperty().bindBidirectional(controller.getCurrentUser().getAddress2());
        headingFields.get(5).textProperty().bindBidirectional(controller.getCurrentUser().getCity());
        headingFields.get(6).textProperty().bindBidirectional(controller.getCurrentUser().getState());
        headingFields.get(7).textProperty().bindBidirectional(controller.getCurrentUser().getZip());
        headingFields.get(8).textProperty().bindBidirectional(controller.getCurrentUser().getPhone());
        headingFields.get(9).textProperty().bindBidirectional(controller.getCurrentUser().getCell());
        headingFields.get(10).textProperty().bindBidirectional(controller.getCurrentUser().getDocEmail());
        objectiveParagraph.textProperty().bindBidirectional(controller.getCurrentUser().getResume().getObjective());
        for (int i = 0; i < headingFields.size(); i++) {
            headingFields.get(i).setEditable(false);
            headingFields.get(i).setDisable(true);
        }
        name.getChildren().addAll(firstNameLine, middleNameLine, lastNameLine);
        address.getChildren().addAll(address1Line, address2Line, cityLine, stateLine, zipLine);
        communication.getChildren().addAll(phoneLine, cellLine, emailLine);
        heading.getChildren().addAll(name, address, communication, hNotes);
        objectiveErrorLine.getChildren().addAll(objectiveError);
        objectiveErrorLine.hide();
        objective.getChildren().addAll(oNotes, objectiveLine, objectiveErrorLine);
        resumeHOBox.getChildren().addAll(heading, objective, buttonsLine);
        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-20));
        screenContent.getChildren().addAll(resumeHOBox);
        screenContent.setSpacing(30);
        screenContent.setPadding(new Insets(20, 20, 20, 20));
        screen.setContent(screenContent);
        screen.setPannable(true);
        return screen;
    }
    
    /*------------------------------------------------------------------------*
     * buildResumeEducationScreen()
     * - Builds the screen displaying the Education section of the
     *   resume.
     *   A.K.A. screen 13
     * - No parameters.
     * - Returns a scroller that gets applied to a center stackpane level.
     *------------------------------------------------------------------------*/
    private ScrollPane buildResumeEducationScreen() {
        //-------- Initialization Start ----------\\
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox();
        VP_PageDivision resumeEducationBox = new VP_PageDivision("RESUME -- EDUCATION");
        
        //-------- Initialization End ------------\\

        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-20));
        screenContent.getChildren().addAll(resumeEducationBox);
        screenContent.setSpacing(30);
        screenContent.setPadding(new Insets(20, 20, 20, 20));
        screen.setContent(screenContent);
        screen.setPannable(true);
        return screen;
    }
    
    /*------------------------------------------------------------------------*
     * buildResumeExperienceScreen()
     * - Builds the screen displaying the Work Experience section of the
     *   resume.
     *   A.K.A. screen 14
     * - No parameters.
     * - Returns a scroller that gets applied to a center stackpane level.
     *------------------------------------------------------------------------*/
    private ScrollPane buildResumeExperienceScreen() {
        //-------- Initialization Start ----------\\
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox();
        VP_PageDivision resumeExperienceBox = new VP_PageDivision("RESUME -- WORK EXPERIENCE");
        
        //-------- Initialization End ------------\\

        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-20));
        screenContent.getChildren().addAll(resumeExperienceBox);
        screenContent.setSpacing(30);
        screenContent.setPadding(new Insets(20, 20, 20, 20));
        screen.setContent(screenContent);
        screen.setPannable(true);
        return screen;
    }
    
    /*------------------------------------------------------------------------*
     * buildResumeExperienceScreen()
     * - Builds the screen displaying the Awards and Achievements section of the
     *   resume.
     *   A.K.A. screen 15
     * - No parameters.
     * - Returns a scroller that gets applied to a center stackpane level.
     *------------------------------------------------------------------------*/
    private ScrollPane buildResumeAchievementsScreen() {
        //-------- Initialization Start ----------\\
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox();
        VP_PageDivision resumeAchievementsBox = new VP_PageDivision("RESUME -- AWARDS AND ACHIEVEMENTS");
        
        //-------- Initialization End ------------\\

        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-20));
        screenContent.getChildren().addAll(resumeAchievementsBox);
        screenContent.setSpacing(30);
        screenContent.setPadding(new Insets(20, 20, 20, 20));
        screen.setContent(screenContent);
        screen.setPannable(true);
        return screen;
    }
    
    /*------------------------------------------------------------------------*
     * buildResumeCommunityScreen()
     * - Builds the screen displaying the Community section of the
     *   resume.
     *   A.K.A. screen 16
     * - No parameters.
     * - Returns a scroller that gets applied to a center stackpane level.
     *------------------------------------------------------------------------*/
    private ScrollPane buildResumeCommunityScreen() {
        //-------- Initialization Start ----------\\
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox();
        VP_PageDivision resumeCommunityBox = new VP_PageDivision("RESUME -- COMMUNITY");
        
        //-------- Initialization End ------------\\

        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-20));
        screenContent.getChildren().addAll(resumeCommunityBox);
        screenContent.setSpacing(30);
        screenContent.setPadding(new Insets(20, 20, 20, 20));
        screen.setContent(screenContent);
        screen.setPannable(true);
        return screen;
    }
    
    /*------------------------------------------------------------------------*
     * buildResumeQualificationsScreen()
     * - Builds the screen displaying the Qualifications section of the
     *   resume.
     *   A.K.A. screen 17
     * - No parameters.
     * - Returns a scroller that gets applied to a center stackpane level.
     *------------------------------------------------------------------------*/
    private ScrollPane buildResumeQualificationsScreen() {
        //-------- Initialization Start ----------\\
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox();
        VP_PageDivision resumeQualificationsBox = new VP_PageDivision("RESUME -- QUALIFICATIONS");
        
        //-------- Initialization End ------------\\

        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-20));
        screenContent.getChildren().addAll(resumeQualificationsBox);
        screenContent.setSpacing(30);
        screenContent.setPadding(new Insets(20, 20, 20, 20));
        screen.setContent(screenContent);
        screen.setPannable(true);
        return screen;
    }
    
    /*------------------------------------------------------------------------*
     * buildResumeHighlightsScreen()
     * - Builds the screen displaying the Highlights section of the
     *   resume.
     *   A.K.A. screen 18
     * - No parameters.
     * - Returns a scroller that gets applied to a center stackpane level.
     *------------------------------------------------------------------------*/
    private ScrollPane buildResumeHighlightsScreen() {
        //-------- Initialization Start ----------\\
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox();
        VP_PageDivision resumeHighlightsBox = new VP_PageDivision("RESUME -- HIGHLIGHTS");
        
        //-------- Initialization End ------------\\

        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-20));
        screenContent.getChildren().addAll(resumeHighlightsBox);
        screenContent.setSpacing(30);
        screenContent.setPadding(new Insets(20, 20, 20, 20));
        screen.setContent(screenContent);
        screen.setPannable(true);
        return screen;
    }
    
    /*------------------------------------------------------------------------*
     * buildResumeLanguagesScreen()
     * - Builds the screen displaying the Languages section of the
     *   resume.
     *   A.K.A. screen 19
     * - No parameters.
     * - Returns a scroller that gets applied to a center stackpane level.
     *------------------------------------------------------------------------*/
    private ScrollPane buildResumeLanguagesScreen() {
        //-------- Initialization Start ----------\\
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox();
        VP_PageDivision resumeLanguagesBox = new VP_PageDivision("RESUME -- LANGUAGES");
        
        //-------- Initialization End ------------\\

        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-20));
        screenContent.getChildren().addAll(resumeLanguagesBox);
        screenContent.setSpacing(30);
        screenContent.setPadding(new Insets(20, 20, 20, 20));
        screen.setContent(screenContent);
        screen.setPannable(true);
        return screen;
    }
    
    /*------------------------------------------------------------------------*
     * buildResumeSoftwareScreen()
     * - Builds the screen displaying the Software section of the
     *   resume.
     *   A.K.A. screen 20
     * - No parameters.
     * - Returns a scroller that gets applied to a center stackpane level.
     *------------------------------------------------------------------------*/
    private ScrollPane buildResumeSoftwareScreen() {
        //-------- Initialization Start ----------\\
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox();
        VP_PageDivision resumeSoftwareBox = new VP_PageDivision("RESUME -- SOFTWARE");
        
        //-------- Initialization End ------------\\

        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-20));
        screenContent.getChildren().addAll(resumeSoftwareBox);
        screenContent.setSpacing(30);
        screenContent.setPadding(new Insets(20, 20, 20, 20));
        screen.setContent(screenContent);
        screen.setPannable(true);
        return screen;
    }
    
    /*------------------------------------------------------------------------*
     * buildResumeReferencesScreen()
     * - Builds the screen displaying the References section of the
     *   resume.
     *   A.K.A. screen 21
     * - No parameters.
     * - Returns a scroller that gets applied to a center stackpane level.
     *------------------------------------------------------------------------*/
    private ScrollPane buildResumeReferencesScreen() {
        //-------- Initialization Start ----------\\
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox();
        VP_PageDivision resumeReferencesBox = new VP_PageDivision("RESUME -- REFERENCES");
        
        //-------- Initialization End ------------\\

        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-20));
        screenContent.getChildren().addAll(resumeReferencesBox);
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
     * updateResumeStatus()
     * - Adjust the resume status page depending on what the user has completed.
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    private void updateResumeStatus() {
        //-------- Initialization Start ----------\\
        VP_Resume thisRes = controller.getCurrentUser().getResume();
        //-------- Initialization End ------------\\
        resObjProgress.getStyleClass().remove(infoProgress.getStyleClass().size() - 1);
        resEduProgress.getStyleClass().remove(infoProgress.getStyleClass().size() - 1);
        resExpProgress.getStyleClass().remove(infoProgress.getStyleClass().size() - 1);
        resQualProgress.getStyleClass().remove(infoProgress.getStyleClass().size() - 1);
        resHighProgress.getStyleClass().remove(infoProgress.getStyleClass().size() - 1);
        resLangProgress.getStyleClass().remove(infoProgress.getStyleClass().size() - 1);
        resSWProgress.getStyleClass().remove(infoProgress.getStyleClass().size() - 1);
        if (thisRes.hasCompletedObjective()) {
            resObjProgress.getStyleClass().add("complete");
            resObjProgress.setText("Complete");
        } else {
            resObjProgress.getStyleClass().add("notStarted");
            resObjProgress.setText("Not started");
        }
        if (thisRes.hasCompletedEducation()) {
            resEduProgress.getStyleClass().add("complete");
            resEduProgress.setText("Complete");
        } else {
            resEduProgress.getStyleClass().add("notStarted");
            resEduProgress.setText("Not started");
        }
        if (thisRes.hasCompletedWorkExperience()) {
            resExpProgress.getStyleClass().add("complete");
            resExpProgress.setText("Complete");
        } else {
            resExpProgress.getStyleClass().add("notStarted");
            resExpProgress.setText("Not started");
        }
        if (thisRes.hasCompletedQualifications()) {
            resQualProgress.getStyleClass().add("complete");
            resQualProgress.setText("Complete");
        } else {
            resQualProgress.getStyleClass().add("notStarted");
            resQualProgress.setText("Not started");
        }
        if (thisRes.hasCompletedHighlights()) {
            resHighProgress.getStyleClass().add("complete");
            resHighProgress.setText("Complete");
        } else {
            resHighProgress.getStyleClass().add("notStarted");
            resHighProgress.setText("Not started");
        }
        if (thisRes.hasCompletedLanguages()) {
            resLangProgress.getStyleClass().add("complete");
            resLangProgress.setText("Complete");
        } else {
            resLangProgress.getStyleClass().add("notStarted");
            resLangProgress.setText("Not started");
        }
        if (thisRes.hasCompletedSoftware()) {
            resSWProgress.getStyleClass().add("complete");
            resSWProgress.setText("Complete");
        } else {
            resSWProgress.getStyleClass().add("notStarted");
            resSWProgress.setText("Not started");
        }
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
        
        objectiveError.setParaText("");
        objectiveErrorLine.hide();
        
        controller.getCurrentUser().getResume().revert();
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
        int numbLetters = 0;
        startNewBtn.setVisible(true);
        startNewBtn.setManaged(true);
        selectCoverLetterLine.show();
        coverLetterSelect.getItems().clear();
        List<String> clChoices = new ArrayList<>();
        clChoices.add("Cover Letter #1");
        clChoices.add("Cover Letter #2");
        clChoices.add("Cover Letter #3");
        for (int i = 0; i < 3; i++) {
            if (controller.getCurrentUser().getCoverLetterIds()[i] > 0) {
                numbLetters += 1;
                coverLetterSelect.getItems().add(clChoices.get(i));
                if (controller.getCurrentUser().getCurrentCoverLetterIndex() == i) {
                    coverLetterSelect.setValue(clChoices.get(i));
                }
            }
        }
        if (numbLetters == 0) {
            coverLetterDetails.setParaText("You are currently using 0 out of 3 cover letters available to you. "
                    + "\nClick \"Start New Cover Letter\" to begin working on a new cover letter.");
            selectCoverLetterLine.hide();
        } else if (numbLetters < 3) {
            coverLetterDetails.setParaText("You are currently using " + numbLetters + " out of 3 cover letters available to you. "
                    + "\nClick \"Start New Cover Letter\" to begin working on a new cover letter or choose a cover letter "
                    + "to load and/or edit.");
        } else {
            coverLetterDetails.setParaText("You are currently using 3 out of 3 cover letters available to you. "
                    + "\nChoose a cover letter to load and/or edit.");
            startNewBtn.setVisible(false);
            startNewBtn.setManaged(false);
        }
        if ((dynamicBody.getChildren().size() - 2) != controller.getCurrentUser().getCovlet().getNumbParagraphs()) {
            if ((dynamicBody.getChildren().size() - 2) > controller.getCurrentUser().getCovlet().getNumbParagraphs()) {
                while ((dynamicBody.getChildren().size() - 2) > controller.getCurrentUser().getCovlet().getNumbParagraphs()) {
                    dynamicBody.getChildren().remove(dynamicBody.getChildren().size() - 2);
                    coverLetterEditFields.remove(25 + controller.getCurrentUser().getCovlet().getNumbParagraphs());
                }
            } else {
                while ((dynamicBody.getChildren().size() - 2) < controller.getCurrentUser().getCovlet().getNumbParagraphs()) {
                    VP_FieldLabel newParaLabel = new VP_FieldLabel("paragraph " + (dynamicBody.getChildren().size() - 1) + ":", 140);
                    VP_Button delParaBtn = new VP_Button("Delete", new DeleteParagraphAction(dynamicBody.getChildren().size() - 1));
                    coverLetterEditFields.add(23 + dynamicBody.getChildren().size(), new VP_TextArea());
                    VP_DivisionLine newParaLine = new VP_DivisionLine(new Node[]{newParaLabel, coverLetterEditFields.get(23 + dynamicBody.getChildren().size()), delParaBtn});
                    dynamicBody.getChildren().add(dynamicBody.getChildren().size() - 1, newParaLine);
                }
                for (int i = 0; i < controller.getCurrentUser().getCovlet().getNumbParagraphs(); i++) {
                    ((VP_FieldLabel) ((VP_DivisionLine) (dynamicBody.getChildren().get(i + 1))).getChildren().get(0)).setText("paragraph " + (i + 1) + ":");
                    ((VP_Button) ((VP_DivisionLine) (dynamicBody.getChildren().get(i + 1))).getChildren().get(2)).setOnAction(new DeleteParagraphAction(i + 1));
                    ((VP_TextArea) (coverLetterEditFields.get(25 + i))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getParagraphs().get(i));
                }
            }
            addParagraphLine.show();
            if (controller.getCurrentUser().getCovlet().getNumbParagraphs() == 9) {
                addParagraphLine.hide();
            }
        }
        controller.updateTree();
    }

    private void saveCovLetFunction(int type) {
        //-------- Initialization Start ----------\\
        boolean hasError = false;
        String zipRegex = "^[0-9]{5}(?:-[0-9]{4})?$";
        Pattern zipPattern = Pattern.compile(zipRegex);
        Matcher matcher;
        //-------- Initialization End ------------\\
        VP_Sounds.play(0);
        for (int i = 11; i < 25; i++) {
            ((VP_TextField) (coverLetterEditFields.get(i))).textProperty().setValue(((VP_TextField) (coverLetterEditFields.get(i))).textProperty().getValueSafe().trim());
        }
        for (int i = 25; i < 25 + controller.getCurrentUser().getCovlet().getNumbParagraphs(); i++) {
            ((VP_TextArea) (coverLetterEditFields.get(i))).textProperty().setValue(((VP_TextArea) (coverLetterEditFields.get(i))).textProperty().getValueSafe().trim());
        }
        ((VP_TextField) (coverLetterEditFields.get(25 + controller.getCurrentUser().getCovlet().getNumbParagraphs()))).textProperty().setValue(((VP_TextField) (coverLetterEditFields.get(25 + controller.getCurrentUser().getCovlet().getNumbParagraphs()))).textProperty().getValueSafe().trim());
        if (type == 1) {
            matcher = zipPattern.matcher(((VP_TextField) (coverLetterEditFields.get(23))).textProperty().getValueSafe());
            if (!matcher.matches()) {
                hasError = true;
                ((VP_TextField) (coverLetterEditFields.get(23))).showInvalid();
                covletEditError.setParaText("Contact Zipcode is not in proper form. "
                        + "Zipcodes can only be in form xxxxx or xxxxx-xxxx");
            } else if (((VP_TextArea) (coverLetterEditFields.get(25))).textProperty().getValueSafe().equals("")) {
                hasError = true;
                ((VP_TextArea) (coverLetterEditFields.get(25))).showInvalid();
                covletEditError.setParaText("The first paragraph of the body cannot be blank.");
            } else if (controller.getCurrentUser().getCovlet().getNumbParagraphs() > 1) {
                for (int i = 0; i < controller.getCurrentUser().getCovlet().getNumbParagraphs(); i++) {
                    if (((VP_TextArea) (coverLetterEditFields.get(25 + i))).textProperty().getValueSafe().equals("")) {
                        hasError = true;
                        ((VP_TextArea) (coverLetterEditFields.get(25 + i))).showInvalid();
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
                updateDynamicFields();
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

    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    private class SubmitObjectiveAction implements EventHandler<ActionEvent> {
        private final VP_TextArea objectiveParagraph;
        public SubmitObjectiveAction(VP_TextArea objectiveParagraph) {
            this.objectiveParagraph = objectiveParagraph;
        }
        @Override
        public void handle(ActionEvent event) {
            boolean hasError = false;
            VP_Sounds.play(0);
            objectiveParagraph.textProperty().setValue(objectiveParagraph.textProperty().getValueSafe().trim());
            if (objectiveParagraph.textProperty().getValueSafe().equals("")) {
                hasError = true;
                objectiveParagraph.showInvalid();
                objectiveError.setParaText("The objective cannot be blank in your resume.");
            }
            if (hasError) {
                VP_Sounds.play(-1);
                objectiveErrorLine.show();
            } else {
                objectiveError.setParaText("");
                objectiveErrorLine.hide();
                controller.getCurrentUser().getResume().save();
                if (controller.getCurrentUser().getResume().hasChanges()) {
                    updateDynamicFields();
                    try {
                        controller.getDataM().saveResume(0);
                    } catch (SQLException ex) {
                        controller.errorAlert(1417, ex.getMessage());
                    }
                }
                showScreen(11, 0);
            }
        }
    }
    
    private class LoadCoverLetterAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            int selectedLetter;
            int clID;
            if (coverLetterSelect.getValue() == "Cover Letter #1") {
                selectedLetter = 0;
            } else if (coverLetterSelect.getValue() == "Cover Letter #2") {
                selectedLetter = 1;
            } else {
                selectedLetter = 2;
            }
            controller.getCurrentUser().getCovlet().clear();
            clID = controller.getCurrentUser().getCoverLetterIds()[selectedLetter];
            try {
                controller.getDataM().loadCovLet(clID);
                controller.getCurrentUser().setCurrentCoverLetterIndex(selectedLetter);
                updateDynamicFields();
                showScreen(7, 0);

            } catch (SQLException ex) {
                controller.errorAlert(1416, ex.getMessage());
            }
        }
    }

    private class StartNewCoverLetter implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            if (controller.getCurrentUser().getCoverLetterIds()[0] == 0) {
                controller.getCurrentUser().getCovlet().clear();
                controller.getCurrentUser().setCurrentCoverLetterIndex(0);
            } else if (controller.getCurrentUser().getCoverLetterIds()[1] == 0) {
                controller.getCurrentUser().getCovlet().clear();
                controller.getCurrentUser().setCurrentCoverLetterIndex(1);
            } else {
                controller.getCurrentUser().getCovlet().clear();
                controller.getCurrentUser().setCurrentCoverLetterIndex(2);
            }
            updateDynamicFields();
            saveCovLetFunction(0);
            showScreen(7, 0);
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass CancelAction
     * - Reverts any information changed in post-login forms back to the 
     *   user's saved values and brings the user back to the Overview
     *   page.
     *------------------------------------------------------------------------*/
    private class CancelAction implements EventHandler<ActionEvent> {

        private final int fromPage;

        public CancelAction() {
            this.fromPage = 3;
        }

        public CancelAction(int fromPage) {
            this.fromPage = fromPage;
        }

        @Override
        public void handle(ActionEvent event) {
            cancelActionFunction();
            if (fromPage == 7) {
                showScreen(6, 0);
            } else if (fromPage == 11) {
                showScreen(11, 0);
            } else {
                showScreen(3, 0);
            }
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass AddParagraphAction
     * - 
     *------------------------------------------------------------------------*/
    private class AddParagraphAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            for (int i = 0; i < controller.getCurrentUser().getCovlet().getNumbParagraphs(); i++) {
                ((VP_TextArea) (coverLetterEditFields.get(25 + i))).textProperty().unbind();
            }
            int newParaNumb = controller.getCurrentUser().getCovlet().getNumbParagraphs() + 1;
            VP_FieldLabel newParaLabel = new VP_FieldLabel("paragraph " + newParaNumb + ":", 140);
            VP_Button delParaBtn = new VP_Button("Delete", new DeleteParagraphAction(newParaNumb));
            coverLetterEditFields.add(24 + newParaNumb, new VP_TextArea());
            VP_DivisionLine newParaLine = new VP_DivisionLine(new Node[]{newParaLabel, coverLetterEditFields.get(24 + newParaNumb), delParaBtn});
            dynamicBody.getChildren().add(dynamicBody.getChildren().size() - 1, newParaLine);
            controller.getCurrentUser().getCovlet().setNumbParagraphs(newParaNumb);
            for (int i = 0; i < controller.getCurrentUser().getCovlet().getNumbParagraphs(); i++) {
                ((VP_TextArea) (coverLetterEditFields.get(25 + i))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getParagraphs().get(i));
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

        public DeleteParagraphAction(int paragraphNumber) {
            this.paragraphNumber = paragraphNumber;
        }

        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            for (int i = 0; i < controller.getCurrentUser().getCovlet().getNumbParagraphs(); i++) {
                ((VP_TextArea) (coverLetterEditFields.get(25 + i))).textProperty().unbind();
            }
            if (controller.getCurrentUser().getCovlet().getNumbParagraphs() > 1) {
                controller.getCurrentUser().getCovlet().setNumbParagraphs(controller.getCurrentUser().getCovlet().getNumbParagraphs() - 1);
                coverLetterEditFields.remove(24 + paragraphNumber);
                dynamicBody.getChildren().remove(paragraphNumber);
                controller.getCurrentUser().getCovlet().getParagraphs().remove(paragraphNumber - 1);
                controller.getCurrentUser().getCovlet().getParagraphs().add(new SimpleStringProperty());
                for (int i = 0; i < controller.getCurrentUser().getCovlet().getNumbParagraphs(); i++) {
                    ((VP_FieldLabel) ((VP_DivisionLine) (dynamicBody.getChildren().get(i + 1))).getChildren().get(0)).setText("paragraph " + (i + 1) + ":");
                    ((VP_Button) ((VP_DivisionLine) (dynamicBody.getChildren().get(i + 1))).getChildren().get(2)).setOnAction(new DeleteParagraphAction(i + 1));

                    ((VP_TextArea) (coverLetterEditFields.get(25 + i))).textProperty().bindBidirectional(controller.getCurrentUser().getCovlet().getParagraphs().get(i));
                }
            } else {
                ((VP_TextArea) (coverLetterEditFields.get(25))).setText("");
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
            saveCovLetFunction(1);
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
            if (!businessCardFields.get(14).textProperty().getValueSafe().equals("")) {
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
                    updateDynamicFields();
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
                updateDynamicFields();
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
                        updateDynamicFields();
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
                    updateDynamicFields();
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

    protected ArrayList<VP_PageSubdivision> getClNodes() {
        return clNodes;
    }
}
