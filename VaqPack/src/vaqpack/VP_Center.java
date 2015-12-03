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
 * FILE ID 1200
 *-----------------------------------------------------------------------------*/
package vaqpack;

import vaqpack.peripherals.VP_Sounds;
import vaqpack.user.VP_User;
import vaqpack.user.VP_Resume;
import vaqpack.components.VP_Button;
import vaqpack.components.VP_DivisionLine;
import vaqpack.components.VP_Paragraph;
import vaqpack.components.VP_PageDivision;
import vaqpack.components.VP_PageSubdivision;
import vaqpack.components.VP_PasswordField;
import vaqpack.components.VP_TextArea;
import vaqpack.components.VP_FieldLabel;
import vaqpack.components.VP_TextField;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import vaqpack.components.VP_Dialog;

public class VP_Center extends StackPane {

    private final VP_GUIController controller;
    private final VP_TextField loginEmail, resetEmail, regLoginAccess,
            resetCode, registerEmail;
    private final Label resetPassStrengthLabel, registerPassStrengthLabel,
            changePassStrengthLabel, infoProgress, resumeProgress, bcardProgress,
            covletProgress, resObjProgress, resEduProgress, resExpProgress,
            resQualProgress, resHighProgress, resLangProgress, resSWProgress;
    private final VP_PasswordField loginPass,
            resetNewPass, resetNewPassConfirm, registerPass, registerPassConfirm,
            oldPass, newPass, newPassConfirm;
    private final VP_DivisionLine accessInstructionsLine, loginButtonLine,
            accessLine, resetInstructions1Line, resetInstructions2Line,
            resetNewPassLine, resetNewPassConfirmLine, resetCodeLine, resetButLine,
            resetPassStrengthLine, personalInfoErrorLine, selectCoverLetterLine,
            addParagraphLine, loginErrorLine, resetErrorLine, registerErrorLine,
            bcardErrorLine, covletEditErrorLine, objectiveErrorLine, educationErrorLine,
            experienceErrorLine, achievementsErrorLine, communityErrorLine,
            qualificationsErrorLine, highlightsErrorLine, languagesErrorLine,
            softwareErrorLine, referencesErrorLine, changePassErrorLine;
    private final VP_Paragraph accessInstructions, resetInstructions1, resetInstructions2,
            overviewInfo, coverLetterDetails, loginError, resetError, registerError,
            personalInfoError, bcardError, covletEditError, objectiveError,
            educationError, experienceError, achievementsError, communityError,
            qualificationsError, highlightsError, languagesError, softwareError,
            referencesError, changePassError;
    private final VP_Button submitResetBtn, startNewBtn, addEducationBtn, addExperienceBtn,
            addAchievementBtn, addCommunityBtn, addQualificationBtn, addHighlightBtn,
            addLanguageBtn, addSoftwareBtn, addReferenceBtn;
    private final ComboBox coverLetterSelect;
    private final ArrayList<VP_Button> wizardMainButtons;
    private final ArrayList<VP_PageSubdivision> bcNodes, clNodes;
    private final ArrayList<Node> coverLetterEditFields;
    private final ArrayList<VP_TextField> educationFields, experienceFields, achievementsFields,
            communityFields, qualificationsFields, highlightsFields, languagesFields,
            softwareFields, referencesFields;
    private final VP_FieldLabel dateValueLabel;
    private final VP_PageSubdivision dynamicBody;
    private final VP_PageDivision resumeEducationBox, resumeExperienceBox, resumeAchievementsBox,
            resumeCommunityBox, resumeQualificationsBox, resumeHighlightsBox,
            resumeLanguagesBox, resumeSoftwareBox, resumeReferencesBox;

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
        educationErrorLine = new VP_DivisionLine();
        experienceErrorLine = new VP_DivisionLine();
        achievementsErrorLine = new VP_DivisionLine();
        communityErrorLine = new VP_DivisionLine();
        qualificationsErrorLine = new VP_DivisionLine();
        highlightsErrorLine = new VP_DivisionLine();
        languagesErrorLine = new VP_DivisionLine();
        softwareErrorLine = new VP_DivisionLine();
        referencesErrorLine = new VP_DivisionLine();
        changePassErrorLine = new VP_DivisionLine();
        loginError = new VP_Paragraph("", true);
        resetError = new VP_Paragraph("", true);
        registerError = new VP_Paragraph("", true);
        personalInfoError = new VP_Paragraph("", true);
        bcardError = new VP_Paragraph("", true);
        covletEditError = new VP_Paragraph("", true);
        objectiveError = new VP_Paragraph("", true);
        educationError = new VP_Paragraph("", true);
        experienceError = new VP_Paragraph("", true);
        achievementsError = new VP_Paragraph("", true);
        communityError = new VP_Paragraph("", true);
        qualificationsError = new VP_Paragraph("", true);
        highlightsError = new VP_Paragraph("", true);
        languagesError = new VP_Paragraph("", true);
        softwareError = new VP_Paragraph("", true);
        referencesError = new VP_Paragraph("", true);
        changePassError = new VP_Paragraph("", true);
        coverLetterDetails = new VP_Paragraph("", false);
        accessInstructions = new VP_Paragraph();
        resetInstructions1 = new VP_Paragraph();
        resetInstructions2 = new VP_Paragraph();
        overviewInfo = new VP_Paragraph();
        resetPassStrengthLabel = new Label();
        registerPassStrengthLabel = new Label();
        changePassStrengthLabel = new Label();
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
        oldPass = new VP_PasswordField(32, 32, 0, null);
        newPass = new VP_PasswordField(32, 32,
                controller.getUSER_PASSWORD_MINIMUM(), changePassStrengthLabel);
        newPassConfirm = new VP_PasswordField(32, 32, 0, null);
        wizardMainButtons = new ArrayList();
        bcNodes = new ArrayList();
        clNodes = new ArrayList();
        coverLetterEditFields = new ArrayList();
        educationFields = new ArrayList();
        experienceFields = new ArrayList();
        achievementsFields = new ArrayList();
        communityFields = new ArrayList();
        qualificationsFields = new ArrayList();
        highlightsFields = new ArrayList();
        languagesFields = new ArrayList();
        softwareFields = new ArrayList();
        referencesFields = new ArrayList();
        dateValueLabel = new VP_FieldLabel("", 200);
        dynamicBody = new VP_PageSubdivision("BODY", false);
        coverLetterSelect = new ComboBox();
        submitResetBtn = new VP_Button("Submit", new SubmitResetAction());
        startNewBtn = new VP_Button("Start New Cover Letter", new StartNewCoverLetter());
        addEducationBtn = new VP_Button("Add Education Entry", new AddEducationAction());
        addExperienceBtn = new VP_Button("Add Experience", new AddExperienceAction());
        addAchievementBtn = new VP_Button("Add Achievement", new AddAchievementAction());
        addCommunityBtn = new VP_Button("Add Event Entry", new AddCommunityAction());
        addQualificationBtn = new VP_Button("Add Qualification", new AddQualificationAction());
        addHighlightBtn = new VP_Button("Add Highlight", new AddHighlightAction());
        addLanguageBtn = new VP_Button("Add Language", new AddLanguageAction());
        addSoftwareBtn = new VP_Button("Add Software", new AddSoftwareAction());
        addReferenceBtn = new VP_Button("Add Reference", new AddReferenceAction());
        resumeEducationBox = new VP_PageDivision("RESUME -- EDUCATION");
        resumeExperienceBox = new VP_PageDivision("RESUME -- WORK EXPERIENCE");
        resumeAchievementsBox = new VP_PageDivision("RESUME -- AWARDS AND ACHIEVEMENTS");
        resumeCommunityBox = new VP_PageDivision("RESUME -- COMMUNITY");
        resumeQualificationsBox = new VP_PageDivision("RESUME -- QUALIFICATIONS");
        resumeHighlightsBox = new VP_PageDivision("RESUME -- HIGHLIGHTS");
        resumeLanguagesBox = new VP_PageDivision("RESUME -- LANGUAGES");
        resumeSoftwareBox = new VP_PageDivision("RESUME -- SOFTWARE");
        resumeReferencesBox = new VP_PageDivision("RESUME -- REFERENCES");
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
                buildResumeHighlightsScreen(), //.....screen 18
                buildResumeLanguagesScreen(), //......screen 19
                buildResumeSoftwareScreen(), //.......screen 20
                buildResumeReferencesScreen(), //.....screen 21
                buildChangePasswordScreen() // .......screen 22
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
        } else if (screenNumber == 6) {
            ((TreeView) (controller.getLeftTree().getChildren().get(0))).getSelectionModel().clearAndSelect(4);
        } else if (screenNumber == 8) {
            ((TreeView) (controller.getLeftTree().getChildren().get(0))).getSelectionModel().clearAndSelect(5);
        } else if (screenNumber == 10) {
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
        coverLetterEditFields.add(new VP_TextArea());          // bind this to cover letter
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
        VP_PageSubdivision educationDiv = new VP_PageSubdivision("EDUCATION ENTRY #1", false);
        VP_FieldLabel label0 = new VP_FieldLabel("institution name:", 130),
                label1 = new VP_FieldLabel("institution location:", 130),
                label2 = new VP_FieldLabel("degree, certificate, or\ntraining earned:", 130),
                label3 = new VP_FieldLabel("*GPA:", 130),
                label4 = new VP_FieldLabel("start date:", 130),
                label5 = new VP_FieldLabel("end date:", 130);
        educationFields.add(new VP_TextField(32, 128));
        educationFields.add(new VP_TextField(32, 128));
        educationFields.add(new VP_TextField(32, 128));
        educationFields.add(new VP_TextField(32, 128));
        educationFields.add(new VP_TextField(32, 128));
        educationFields.add(new VP_TextField(32, 128));
        VP_Button submitBtn = new VP_Button("Submit", new SubmitEducationAction()),
                cancelBtn = new VP_Button("Cancel", new CancelAction(11));
        VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{label0, educationFields.get(0)}),
                line1 = new VP_DivisionLine(new Node[]{label1, educationFields.get(1)}),
                line2 = new VP_DivisionLine(new Node[]{label2, educationFields.get(2)}),
                line3 = new VP_DivisionLine(new Node[]{label3, educationFields.get(3)}),
                line4 = new VP_DivisionLine(new Node[]{label4, educationFields.get(4)}),
                line5 = new VP_DivisionLine(new Node[]{label5, educationFields.get(5)}),
                buttonLine = new VP_DivisionLine(new Node[]{submitBtn, addEducationBtn, cancelBtn});
        VP_Paragraph notes1 = new VP_Paragraph("(*) denotes an optional field."),
                notes2 = new VP_Paragraph("At least one education entry must exist for your resume. "
                        + "The entries can be schools, colleges, or training centers. The GPA field is "
                        + "optional. You may include up to 9 total education entries in your resume.");
        //-------- Initialization End ------------\\
        for (int i = 0; i < educationFields.size(); i++) {
            educationFields.get(i).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getEducation().get(0).get(i));
        }
        educationDiv.getChildren().addAll(line0, line1, line2, line3, line4, line5);
        educationErrorLine.getChildren().addAll(educationError);
        educationErrorLine.hide();
        resumeEducationBox.getChildren().addAll(notes2, educationDiv, notes1, educationErrorLine, buttonLine);
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
        VP_PageSubdivision experienceDiv = new VP_PageSubdivision("EXPERIENCE ENTRY #1", false);
        VP_FieldLabel label0 = new VP_FieldLabel("institution name:", 130),
                label1 = new VP_FieldLabel("institution location:", 130),
                label2 = new VP_FieldLabel("position held:", 130),
                label3 = new VP_FieldLabel("start date:", 130),
                label4 = new VP_FieldLabel("end date:", 130);
        VP_Button submitBtn = new VP_Button("Submit", new SubmitExperienceAction()),
                cancelBtn = new VP_Button("Cancel", new CancelAction(11));
        experienceFields.add(new VP_TextField(32, 128));
        experienceFields.add(new VP_TextField(32, 128));
        experienceFields.add(new VP_TextField(32, 128));
        experienceFields.add(new VP_TextField(32, 128));
        experienceFields.add(new VP_TextField(32, 128));
        VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{label0, experienceFields.get(0)}),
                line1 = new VP_DivisionLine(new Node[]{label1, experienceFields.get(1)}),
                line2 = new VP_DivisionLine(new Node[]{label2, experienceFields.get(2)}),
                line3 = new VP_DivisionLine(new Node[]{label3, experienceFields.get(3)}),
                line4 = new VP_DivisionLine(new Node[]{label4, experienceFields.get(4)}),
                buttonLine = new VP_DivisionLine(new Node[]{submitBtn, addExperienceBtn, cancelBtn});
        VP_Paragraph notes1 = new VP_Paragraph(""),
                notes2 = new VP_Paragraph("At least one experience entry must exist for your resume. "
                        + "The entries are not limited to employers. Entries may also be internships or research studies, "
                        + "for example. You may include up to 9 total experience entries in your resume.");
        //-------- Initialization End ------------\\

        for (int i = 0; i < experienceFields.size(); i++) {
            experienceFields.get(i).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getExperience().get(0).get(i));
        }
        experienceDiv.getChildren().addAll(line0, line1, line2, line3, line4);
        experienceErrorLine.getChildren().addAll(experienceError);
        experienceErrorLine.hide();
        resumeExperienceBox.getChildren().addAll(notes2, experienceDiv, notes1, experienceErrorLine, buttonLine);
        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-20));
        screenContent.getChildren().addAll(resumeExperienceBox);
        screenContent.setSpacing(30);
        screenContent.setPadding(new Insets(20, 20, 20, 20));
        screen.setContent(screenContent);
        screen.setPannable(true);
        return screen;
    }

    /*------------------------------------------------------------------------*
     * buildResumeAchievementsScreen()
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
        VP_PageSubdivision achievementsDiv = new VP_PageSubdivision("AWARD/ACHIEVEMENT ENTRY #1", false);
        VP_FieldLabel label0 = new VP_FieldLabel("name of award\nor achievement:", 130),
                label1 = new VP_FieldLabel("given by:", 130),
                label2 = new VP_FieldLabel("date:", 130);
        VP_Button submitBtn = new VP_Button("Submit", new SubmitAchievementsAction()),
                cancelBtn = new VP_Button("Cancel", new CancelAction(11));
        achievementsFields.add(new VP_TextField(32, 128));
        achievementsFields.add(new VP_TextField(32, 128));
        achievementsFields.add(new VP_TextField(32, 128));
        VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{label0, achievementsFields.get(0)}),
                line1 = new VP_DivisionLine(new Node[]{label1, achievementsFields.get(1)}),
                line2 = new VP_DivisionLine(new Node[]{label2, achievementsFields.get(2)}),
                buttonLine = new VP_DivisionLine(new Node[]{submitBtn, addAchievementBtn, cancelBtn});
        VP_Paragraph notes1 = new VP_Paragraph(""),
                notes2 = new VP_Paragraph("This section of your resume is optional. "
                        + "Each entry can be an award that you have won, induction into a society, or some other "
                        + "achievement. You may include up to 9 total achievements in your resume.");
        //-------- Initialization End ------------\\

        for (int i = 0; i < achievementsFields.size(); i++) {
            achievementsFields.get(i).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getAchievements().get(0).get(i));
        }
        achievementsDiv.getChildren().addAll(line0, line1, line2);
        achievementsErrorLine.getChildren().addAll(achievementsError);
        achievementsErrorLine.hide();
        resumeAchievementsBox.getChildren().addAll(notes2, achievementsDiv, notes1, achievementsErrorLine, buttonLine);
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
        VP_PageSubdivision communityDiv = new VP_PageSubdivision("EVENT ENTRY #1", false);
        VP_FieldLabel label0 = new VP_FieldLabel("event name:", 130),
                label1 = new VP_FieldLabel("event location:", 130),
                label2 = new VP_FieldLabel("date:", 130);
        VP_Button submitBtn = new VP_Button("Submit", new SubmitCommunityAction()),
                cancelBtn = new VP_Button("Cancel", new CancelAction(11));
        communityFields.add(new VP_TextField(32, 128));
        communityFields.add(new VP_TextField(32, 128));
        communityFields.add(new VP_TextField(32, 128));
        VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{label0, communityFields.get(0)}),
                line1 = new VP_DivisionLine(new Node[]{label1, communityFields.get(1)}),
                line2 = new VP_DivisionLine(new Node[]{label2, communityFields.get(2)}),
                buttonLine = new VP_DivisionLine(new Node[]{submitBtn, addCommunityBtn, cancelBtn});
        VP_Paragraph notes1 = new VP_Paragraph(""),
                notes2 = new VP_Paragraph("This section of your resume is optional. "
                        + "Each entry represents a community service event or volunteer work that you "
                        + "have participated in. You may include up to 9 total events in your resume.");
        //-------- Initialization End ------------\\

        for (int i = 0; i < communityFields.size(); i++) {
            communityFields.get(i).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getCommunity().get(0).get(i));
        }
        communityDiv.getChildren().addAll(line0, line1, line2);
        communityErrorLine.getChildren().addAll(communityError);
        communityErrorLine.hide();
        resumeCommunityBox.getChildren().addAll(notes2, communityDiv, notes1, communityErrorLine, buttonLine);
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
        VP_PageSubdivision qualificationsDiv = new VP_PageSubdivision("QUALIFICATION #1", false);
        qualificationsFields.add(new VP_TextField(50, 128));
        VP_Button submitBtn = new VP_Button("Submit", new SubmitQualificationsAction()),
                cancelBtn = new VP_Button("Cancel", new CancelAction(11));
        VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{qualificationsFields.get(0)}),
                buttonLine = new VP_DivisionLine(new Node[]{submitBtn, addQualificationBtn, cancelBtn});
        VP_Paragraph notes1 = new VP_Paragraph(""),
                notes2 = new VP_Paragraph("List your skills that are relevant to the job position "
                        + "you are applying for. At least one qualification must exist for your resume, although "
                        + "you really should have more. You may include up to 9 total qualifications in your resume.");
        //-------- Initialization End ------------\\

        for (int i = 0; i < qualificationsFields.size(); i++) {
            qualificationsFields.get(i).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getQualifications().get(i));
        }
        qualificationsDiv.getChildren().addAll(line0);
        qualificationsErrorLine.getChildren().addAll(qualificationsError);
        qualificationsErrorLine.hide();
        resumeQualificationsBox.getChildren().addAll(notes2, qualificationsDiv, notes1, qualificationsErrorLine, buttonLine);
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
        VP_PageSubdivision highlightsDiv = new VP_PageSubdivision("PERSONAL QUALITY #1", false);
        highlightsFields.add(new VP_TextField(50, 128));
        VP_Button submitBtn = new VP_Button("Submit", new SubmitHighlightsAction()),
                cancelBtn = new VP_Button("Cancel", new CancelAction(11));
        VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{highlightsFields.get(0)}),
                buttonLine = new VP_DivisionLine(new Node[]{submitBtn, addHighlightBtn, cancelBtn});
        VP_Paragraph notes1 = new VP_Paragraph(""),
                notes2 = new VP_Paragraph("List some personal qualities or personality traits "
                        + "that you feel would be beneficial to an employer. At least one highlight must "
                        + "exist for your resume, although you really should have more. "
                        + "You may include up to 9 total highlights in your resume.");
        //-------- Initialization End ------------\\

        for (int i = 0; i < highlightsFields.size(); i++) {
            highlightsFields.get(i).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getHighlights().get(i));
        }
        highlightsDiv.getChildren().addAll(line0);
        highlightsErrorLine.getChildren().addAll(highlightsError);
        highlightsErrorLine.hide();
        resumeHighlightsBox.getChildren().addAll(notes2, highlightsDiv, notes1, highlightsErrorLine, buttonLine);
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
        VP_PageSubdivision languagesDiv = new VP_PageSubdivision("PRIMARY LANGUAGE", false);
        languagesFields.add(new VP_TextField(50, 128));
        VP_Button submitBtn = new VP_Button("Submit", new SubmitLanguagesAction()),
                cancelBtn = new VP_Button("Cancel", new CancelAction(11));
        VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{languagesFields.get(0)}),
                buttonLine = new VP_DivisionLine(new Node[]{submitBtn, addLanguageBtn, cancelBtn});
        VP_Paragraph notes1 = new VP_Paragraph(""),
                notes2 = new VP_Paragraph("List the languages that you know. "
                        + "The primary language must exist for your resume. "
                        + "You may include up to 8 additional secondary languages in your resume.");
        //-------- Initialization End ------------\\

        for (int i = 0; i < languagesFields.size(); i++) {
            languagesFields.get(i).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getLanguages().get(i));
        }
        languagesDiv.getChildren().addAll(line0);
        languagesErrorLine.getChildren().addAll(languagesError);
        languagesErrorLine.hide();
        resumeLanguagesBox.getChildren().addAll(notes2, languagesDiv, notes1, languagesErrorLine, buttonLine);
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
        VP_PageSubdivision softwareDiv = new VP_PageSubdivision("SOFTWARE PRODUCT #1", false);
        softwareFields.add(new VP_TextField(50, 128));
        VP_Button submitBtn = new VP_Button("Submit", new SubmitSoftwareAction()),
                cancelBtn = new VP_Button("Cancel", new CancelAction(11));
        VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{softwareFields.get(0)}),
                buttonLine = new VP_DivisionLine(new Node[]{submitBtn, addSoftwareBtn, cancelBtn});
        VP_Paragraph notes1 = new VP_Paragraph(""),
                notes2 = new VP_Paragraph("List the software products that you "
                        + "are familiar with and are relevant to the job you are applying for. "
                        + "At least one software product must exist for your resume. "
                        + "You may include up to 9 total products in your resume.");
        //-------- Initialization End ------------\\

        for (int i = 0; i < softwareFields.size(); i++) {
            softwareFields.get(i).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getSoftware().get(i));
        }
        softwareDiv.getChildren().addAll(line0);
        softwareErrorLine.getChildren().addAll(softwareError);
        softwareErrorLine.hide();
        resumeSoftwareBox.getChildren().addAll(notes2, softwareDiv, notes1, softwareErrorLine, buttonLine);
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
        VP_PageSubdivision referenceDiv = new VP_PageSubdivision("REFERENCE #1", false);
        VP_FieldLabel label0 = new VP_FieldLabel("first name:", 130),
                label1 = new VP_FieldLabel("*middle name:", 130),
                label2 = new VP_FieldLabel("last name:", 130),
                label3 = new VP_FieldLabel("company or\ninstitution:", 130),
                label4 = new VP_FieldLabel("phone:", 130),
                label5 = new VP_FieldLabel("*email:", 130);
        VP_Button submitBtn = new VP_Button("Submit", new SubmitReferencesAction()),
                cancelBtn = new VP_Button("Cancel", new CancelAction(11));
        referencesFields.add(new VP_TextField(32, 45));
        referencesFields.add(new VP_TextField(32, 45));
        referencesFields.add(new VP_TextField(32, 45));
        referencesFields.add(new VP_TextField(32, 128));
        referencesFields.add(new VP_TextField(13, 13));
        referencesFields.add(new VP_TextField(32, 254));
        VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{label0, referencesFields.get(0)}),
                line1 = new VP_DivisionLine(new Node[]{label1, referencesFields.get(1)}),
                line2 = new VP_DivisionLine(new Node[]{label2, referencesFields.get(2)}),
                line3 = new VP_DivisionLine(new Node[]{label3, referencesFields.get(3)}),
                line4 = new VP_DivisionLine(new Node[]{label4, referencesFields.get(4)}),
                line5 = new VP_DivisionLine(new Node[]{label5, referencesFields.get(5)}),
                buttonLine = new VP_DivisionLine(new Node[]{submitBtn, addReferenceBtn, cancelBtn});
        VP_Paragraph notes1 = new VP_Paragraph("(*) denotes an optional field."),
                notes2 = new VP_Paragraph("This section of your resume is optional. "
                        + "When you do not include any references, the resume will be generated with "
                        + "text stating that \"References are available upon request.\" You may"
                        + "include up to 9 total references in your resume.");
        //-------- Initialization End ------------\\

        for (int i = 0; i < referencesFields.size(); i++) {
            referencesFields.get(i).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getReferences().get(0).get(i));
        }
        referenceDiv.getChildren().addAll(line0, line1, line2, line3, line4, line5);
        referencesErrorLine.getChildren().addAll(referencesError);
        referencesErrorLine.hide();
        resumeReferencesBox.getChildren().addAll(notes2, referenceDiv, notes1, referencesErrorLine, buttonLine);
        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-20));
        screenContent.getChildren().addAll(resumeReferencesBox);
        screenContent.setSpacing(30);
        screenContent.setPadding(new Insets(20, 20, 20, 20));
        screen.setContent(screenContent);
        screen.setPannable(true);
        return screen;
    }
    
    private ScrollPane buildChangePasswordScreen() {
        //-------- Initialization Start ----------\\
        ScrollPane screen = new ScrollPane();
        VBox screenContent = new VBox();
        VP_PageDivision changePassBox = new VP_PageDivision("CHANGE YOUR PASSWORD");
        VP_FieldLabel oldPassLabel = new VP_FieldLabel("old password:", 100),
                newPassLabel = new VP_FieldLabel("new password:", 100),
                newPassConfirmLabel = new VP_FieldLabel("confirm new\npassword:", 100);
        VP_Button submitBtn = new VP_Button("Submit", new ChangePassAction()),
                cancelBtn = new VP_Button("Cancel", new CancelAction(22));
        VP_DivisionLine
                oldPassLine = new VP_DivisionLine(new Node[]{oldPassLabel, oldPass}),
                newPassLine = new VP_DivisionLine(new Node[]{newPassLabel, newPass}),
                changePassStrengthLine = new VP_DivisionLine(new Node[]{changePassStrengthLabel}),
                newPassConfirmLine = new VP_DivisionLine(new Node[]{newPassConfirmLabel, newPassConfirm}),
                buttonLine = new VP_DivisionLine(new Node[]{submitBtn, cancelBtn});
        //-------- Initialization End ------------\\

        screenContent.prefWidthProperty().bind(screen.widthProperty().add(-20));
        changePassStrengthLabel.getStyleClass().add("inputLabel");
        changePassErrorLine.getChildren().addAll(changePassError);
        changePassBox.getChildren().addAll(oldPassLine, newPassLine, changePassStrengthLine,
                newPassConfirmLine, changePassErrorLine, buttonLine);
        screenContent.getChildren().addAll(changePassBox);
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
        covletEditError.setParaText("");
        covletEditErrorLine.hide();
        objectiveError.setParaText("");
        objectiveErrorLine.hide();
        educationError.setParaText("");
        educationErrorLine.hide();
        experienceError.setParaText("");
        experienceErrorLine.hide();
        achievementsError.setParaText("");
        achievementsErrorLine.hide();
        communityError.setParaText("");
        communityErrorLine.hide();
        qualificationsError.setParaText("");
        qualificationsErrorLine.hide();
        highlightsError.setParaText("");
        highlightsErrorLine.hide();
        languagesError.setParaText("");
        languagesErrorLine.hide();
        softwareError.setParaText("");
        softwareErrorLine.hide();
        referencesError.setParaText("");
        referencesErrorLine.hide();
        personalInfoError.setParaText("");
        personalInfoErrorLine.hide();
        changePassError.setParaText("");
        changePassErrorLine.hide();
        controller.getCurrentUser().getResume().revert();
        controller.getCurrentUser().getCovlet().revert();
        controller.getCurrentUser().getBcard().revert();
        controller.getCurrentUser().revert();
        changePassStrengthLabel.setText("");
        oldPass.showValid();
        newPass.showValid();
        newPassConfirm.showValid();
        oldPass.setText("");
        newPass.setText("");
        newPassConfirm.setText("");
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
        VP_Resume thisRes = controller.getCurrentUser().getResume();
        startNewBtn.setVisible(true);
        addEducationBtn.setVisible(true);
        addExperienceBtn.setVisible(true);
        addAchievementBtn.setVisible(true);
        addCommunityBtn.setVisible(true);
        addQualificationBtn.setVisible(true);
        addHighlightBtn.setVisible(true);
        addLanguageBtn.setVisible(true);
        addSoftwareBtn.setVisible(true);
        addReferenceBtn.setVisible(true);
        startNewBtn.setManaged(true);
        addEducationBtn.setManaged(true);
        addExperienceBtn.setManaged(true);
        addAchievementBtn.setManaged(true);
        addCommunityBtn.setManaged(true);
        addQualificationBtn.setManaged(true);
        addHighlightBtn.setManaged(true);
        addLanguageBtn.setManaged(true);
        addSoftwareBtn.setManaged(true);
        addReferenceBtn.setManaged(true);
        selectCoverLetterLine.show();
        if (thisRes.getNumbEducation() == 9) {
            addEducationBtn.setVisible(false);
            addEducationBtn.setManaged(false);
        }
        if (thisRes.getNumbExperience() == 9) {
            addExperienceBtn.setVisible(false);
            addExperienceBtn.setManaged(false);
        }
        if (thisRes.getNumbAchievements() == 9) {
            addAchievementBtn.setVisible(false);
            addAchievementBtn.setManaged(false);
        }
        if (thisRes.getNumbCommunity() == 9) {
            addCommunityBtn.setVisible(false);
            addCommunityBtn.setManaged(false);
        }
        if (thisRes.getNumbQualification() == 9) {
            addQualificationBtn.setVisible(false);
            addQualificationBtn.setManaged(false);
        }
        if (thisRes.getNumbHighlights() == 9) {
            addHighlightBtn.setVisible(false);
            addHighlightBtn.setManaged(false);
        }
        if (thisRes.getNumbLanguages() == 9) {
            addLanguageBtn.setVisible(false);
            addLanguageBtn.setManaged(false);
        }
        if (thisRes.getNumbSoftware() == 9) {
            addSoftwareBtn.setVisible(false);
            addSoftwareBtn.setManaged(false);
        }
        if (thisRes.getNumbReferences() == 9) {
            addReferenceBtn.setVisible(false);
            addReferenceBtn.setManaged(false);
        }
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
        // education
        if ((resumeEducationBox.getChildren().size() - 5) > thisRes.getNumbEducation()) {
            while ((resumeEducationBox.getChildren().size() - 5) > thisRes.getNumbEducation()) {
                resumeEducationBox.getChildren().remove(3);
                for (int i = 0; i < 6; i++) {
                    educationFields.remove(thisRes.getNumbEducation() * 6);
                }
            }
        } else {
            while ((resumeEducationBox.getChildren().size() - 5) < thisRes.getNumbEducation()) {
                int newNumb = resumeEducationBox.getChildren().size() - 4;
                VP_PageSubdivision educationDiv = new VP_PageSubdivision("EDUCATION ENTRY #" + newNumb, false);
                VP_FieldLabel label0 = new VP_FieldLabel("institution name:", 130),
                        label1 = new VP_FieldLabel("institution location:", 130),
                        label2 = new VP_FieldLabel("degree, certificate, or\ntraining earned:", 130),
                        label3 = new VP_FieldLabel("*GPA:", 130),
                        label4 = new VP_FieldLabel("start date:", 130),
                        label5 = new VP_FieldLabel("end date:", 130);
                educationFields.add(new VP_TextField(32, 128));
                educationFields.add(new VP_TextField(32, 128));
                educationFields.add(new VP_TextField(32, 128));
                educationFields.add(new VP_TextField(32, 128));
                educationFields.add(new VP_TextField(32, 128));
                educationFields.add(new VP_TextField(32, 128));
                VP_Button delBtn = new VP_Button("Delete", new DeleteEducationAction(newNumb));
                VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{label0, educationFields.get(0 + (6 * (newNumb - 1)))}),
                        line1 = new VP_DivisionLine(new Node[]{label1, educationFields.get(1 + (6 * (newNumb - 1)))}),
                        line2 = new VP_DivisionLine(new Node[]{label2, educationFields.get(2 + (6 * (newNumb - 1)))}),
                        line3 = new VP_DivisionLine(new Node[]{label3, educationFields.get(3 + (6 * (newNumb - 1)))}),
                        line4 = new VP_DivisionLine(new Node[]{label4, educationFields.get(4 + (6 * (newNumb - 1)))}),
                        line5 = new VP_DivisionLine(new Node[]{label5, educationFields.get(5 + (6 * (newNumb - 1)))}),
                        delline = new VP_DivisionLine(new Node[]{delBtn});
                educationDiv.getChildren().addAll(line0, line1, line2, line3, line4, line5, delline);
                resumeEducationBox.getChildren().add(resumeEducationBox.getChildren().size() - 3, educationDiv);
            }
            for (int i = 0; i < thisRes.getNumbEducation(); i++) {
                for (int ii = 0; ii < 6; ii++) {
                    educationFields.get((6 * i) + ii).textProperty().bindBidirectional(thisRes.getEducation().get(i).get(ii));
                }
            }
        }
        // experience
        if ((resumeExperienceBox.getChildren().size() - 5) > thisRes.getNumbExperience()) {
            while ((resumeExperienceBox.getChildren().size() - 5) > thisRes.getNumbExperience()) {
                resumeExperienceBox.getChildren().remove(3);
                for (int i = 0; i < 5; i++) {
                    experienceFields.remove(thisRes.getNumbExperience() * 5);
                }
            }
        } else {
            while ((resumeExperienceBox.getChildren().size() - 5) < thisRes.getNumbExperience()) {
                int newNumb = resumeExperienceBox.getChildren().size() - 4;
                VP_PageSubdivision experienceDiv = new VP_PageSubdivision("EXPERIENCE ENTRY #" + newNumb, false);
                VP_FieldLabel label0 = new VP_FieldLabel("institution name:", 130),
                        label1 = new VP_FieldLabel("institution location:", 130),
                        label2 = new VP_FieldLabel("position held:", 130),
                        label3 = new VP_FieldLabel("start date:", 130),
                        label4 = new VP_FieldLabel("end date:", 130);
                experienceFields.add(new VP_TextField(32, 128));
                experienceFields.add(new VP_TextField(32, 128));
                experienceFields.add(new VP_TextField(32, 128));
                experienceFields.add(new VP_TextField(32, 128));
                experienceFields.add(new VP_TextField(32, 128));
                VP_Button delBtn = new VP_Button("Delete", new DeleteExperienceAction(newNumb));
                VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{label0, experienceFields.get(0 + (5 * (newNumb - 1)))}),
                        line1 = new VP_DivisionLine(new Node[]{label1, experienceFields.get(1 + (5 * (newNumb - 1)))}),
                        line2 = new VP_DivisionLine(new Node[]{label2, experienceFields.get(2 + (5 * (newNumb - 1)))}),
                        line3 = new VP_DivisionLine(new Node[]{label3, experienceFields.get(3 + (5 * (newNumb - 1)))}),
                        line4 = new VP_DivisionLine(new Node[]{label4, experienceFields.get(4 + (5 * (newNumb - 1)))}),
                        delline = new VP_DivisionLine(new Node[]{delBtn});
                experienceDiv.getChildren().addAll(line0, line1, line2, line3, line4, delline);
                resumeExperienceBox.getChildren().add(resumeExperienceBox.getChildren().size() - 3, experienceDiv);
            }
            for (int i = 0; i < thisRes.getNumbExperience(); i++) {
                for (int ii = 0; ii < 5; ii++) {
                    experienceFields.get((5 * i) + ii).textProperty().bindBidirectional(thisRes.getExperience().get(i).get(ii));
                }
            }
        }
        // references
        if ((resumeReferencesBox.getChildren().size() - 5) > thisRes.getNumbReferences()) {
            while ((resumeReferencesBox.getChildren().size() - 5) > thisRes.getNumbReferences()) {
                resumeReferencesBox.getChildren().remove(3);
                for (int i = 0; i < 6; i++) {
                    referencesFields.remove(thisRes.getNumbReferences() * 6);
                }
            }
        } else {
            while ((resumeReferencesBox.getChildren().size() - 5) < thisRes.getNumbReferences()) {
                int newNumb = resumeReferencesBox.getChildren().size() - 4;
                VP_PageSubdivision referenceDiv = new VP_PageSubdivision("REFERENCE #" + newNumb, false);
                VP_FieldLabel label0 = new VP_FieldLabel("first name:", 130),
                        label1 = new VP_FieldLabel("*middle name:", 130),
                        label2 = new VP_FieldLabel("last name:", 130),
                        label3 = new VP_FieldLabel("company or\ninstitution:", 130),
                        label4 = new VP_FieldLabel("phone:", 130),
                        label5 = new VP_FieldLabel("*email:", 130);
                referencesFields.add(new VP_TextField(32, 45));
                referencesFields.add(new VP_TextField(32, 45));
                referencesFields.add(new VP_TextField(32, 45));
                referencesFields.add(new VP_TextField(32, 128));
                referencesFields.add(new VP_TextField(13, 13));
                referencesFields.add(new VP_TextField(32, 254));
                VP_Button delBtn = new VP_Button("Delete", new DeleteReferenceAction(newNumb));
                VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{label0, referencesFields.get(0 + (6 * (newNumb - 1)))}),
                        line1 = new VP_DivisionLine(new Node[]{label1, referencesFields.get(1 + (6 * (newNumb - 1)))}),
                        line2 = new VP_DivisionLine(new Node[]{label2, referencesFields.get(2 + (6 * (newNumb - 1)))}),
                        line3 = new VP_DivisionLine(new Node[]{label3, referencesFields.get(3 + (6 * (newNumb - 1)))}),
                        line4 = new VP_DivisionLine(new Node[]{label4, referencesFields.get(4 + (6 * (newNumb - 1)))}),
                        line5 = new VP_DivisionLine(new Node[]{label5, referencesFields.get(5 + (6 * (newNumb - 1)))}),
                        delline = new VP_DivisionLine(new Node[]{delBtn});
                referenceDiv.getChildren().addAll(line0, line1, line2, line3, line4, line5, delline);
                resumeReferencesBox.getChildren().add(resumeReferencesBox.getChildren().size() - 3, referenceDiv);
            }
            for (int i = 0; i < thisRes.getNumbReferences(); i++) {
                for (int ii = 0; ii < 6; ii++) {
                    referencesFields.get((6 * i) + ii).textProperty().bindBidirectional(thisRes.getReferences().get(i).get(ii));
                }
            }
        }
        // achievements
        if ((resumeAchievementsBox.getChildren().size() - 5) > thisRes.getNumbAchievements()) {
            while ((resumeAchievementsBox.getChildren().size() - 5) > thisRes.getNumbAchievements()) {
                resumeAchievementsBox.getChildren().remove(3);
                for (int i = 0; i < 3; i++) {
                    achievementsFields.remove(thisRes.getNumbAchievements() * 3);
                }
            }
        } else {
            while ((resumeAchievementsBox.getChildren().size() - 5) < thisRes.getNumbAchievements()) {
                int newNumb = resumeAchievementsBox.getChildren().size() - 4;
                VP_PageSubdivision achievementDiv = new VP_PageSubdivision("AWARD/ACHIEVEMENT ENTRY #" + newNumb, false);
                VP_FieldLabel label0 = new VP_FieldLabel("name of award\nor achievement:", 130),
                        label1 = new VP_FieldLabel("given by:", 130),
                        label2 = new VP_FieldLabel("date:", 130);
                achievementsFields.add(new VP_TextField(32, 128));
                achievementsFields.add(new VP_TextField(32, 128));
                achievementsFields.add(new VP_TextField(32, 128));
                VP_Button delBtn = new VP_Button("Delete", new DeleteAchievementAction(newNumb));
                VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{label0, achievementsFields.get(0 + (3 * (newNumb - 1)))}),
                        line1 = new VP_DivisionLine(new Node[]{label1, achievementsFields.get(1 + (3 * (newNumb - 1)))}),
                        line2 = new VP_DivisionLine(new Node[]{label2, achievementsFields.get(2 + (3 * (newNumb - 1)))}),
                        delline = new VP_DivisionLine(new Node[]{delBtn});
                achievementDiv.getChildren().addAll(line0, line1, line2, delline);
                resumeAchievementsBox.getChildren().add(resumeAchievementsBox.getChildren().size() - 3, achievementDiv);
            }
            for (int i = 0; i < thisRes.getNumbAchievements(); i++) {
                for (int ii = 0; ii < 3; ii++) {
                    achievementsFields.get((3 * i) + ii).textProperty().bindBidirectional(thisRes.getAchievements().get(i).get(ii));
                }
            }
        }
        // community
        if ((resumeCommunityBox.getChildren().size() - 5) > thisRes.getNumbCommunity()) {
            while ((resumeCommunityBox.getChildren().size() - 5) > thisRes.getNumbCommunity()) {
                resumeCommunityBox.getChildren().remove(3);
                for (int i = 0; i < 3; i++) {
                    communityFields.remove(thisRes.getNumbCommunity() * 3);
                }
            }
        } else {
            while ((resumeCommunityBox.getChildren().size() - 5) < thisRes.getNumbCommunity()) {
                int newNumb = resumeCommunityBox.getChildren().size() - 4;
                VP_PageSubdivision communityDiv = new VP_PageSubdivision("EVENT ENTRY #" + newNumb, false);
                VP_FieldLabel label0 = new VP_FieldLabel("event name:", 130),
                        label1 = new VP_FieldLabel("event location:", 130),
                        label2 = new VP_FieldLabel("date:", 130);
                communityFields.add(new VP_TextField(32, 128));
                communityFields.add(new VP_TextField(32, 128));
                communityFields.add(new VP_TextField(32, 128));
                VP_Button delBtn = new VP_Button("Delete", new DeleteCommunityAction(newNumb));
                VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{label0, communityFields.get(0 + (3 * (newNumb - 1)))}),
                        line1 = new VP_DivisionLine(new Node[]{label1, communityFields.get(1 + (3 * (newNumb - 1)))}),
                        line2 = new VP_DivisionLine(new Node[]{label2, communityFields.get(2 + (3 * (newNumb - 1)))}),
                        delline = new VP_DivisionLine(new Node[]{delBtn});
                communityDiv.getChildren().addAll(line0, line1, line2, delline);
                resumeCommunityBox.getChildren().add(resumeCommunityBox.getChildren().size() - 3, communityDiv);
            }
            for (int i = 0; i < thisRes.getNumbCommunity(); i++) {
                for (int ii = 0; ii < 3; ii++) {
                    communityFields.get((3 * i) + ii).textProperty().bindBidirectional(thisRes.getCommunity().get(i).get(ii));
                }
            }
        }
        // qualifications
        if ((resumeQualificationsBox.getChildren().size() - 5) > thisRes.getNumbQualification()) {
            while ((resumeQualificationsBox.getChildren().size() - 5) > thisRes.getNumbQualification()) {
                resumeQualificationsBox.getChildren().remove(3);
                qualificationsFields.remove(thisRes.getNumbQualification());
            }
        } else {
            while ((resumeQualificationsBox.getChildren().size() - 5) < thisRes.getNumbQualification()) {
                int newNumb = resumeQualificationsBox.getChildren().size() - 4;
                VP_PageSubdivision qualificationDiv = new VP_PageSubdivision("QUALIFICATION #" + newNumb, false);
                qualificationsFields.add(new VP_TextField(50, 128));
                VP_Button delBtn = new VP_Button("Delete", new DeleteQualificationAction(newNumb));
                VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{qualificationsFields.get(newNumb - 1)}),
                        delline = new VP_DivisionLine(new Node[]{delBtn});
                qualificationDiv.getChildren().addAll(line0, delline);
                resumeQualificationsBox.getChildren().add(resumeQualificationsBox.getChildren().size() - 3, qualificationDiv);
            }
            for (int i = 0; i < thisRes.getNumbQualification(); i++) {
                qualificationsFields.get(i).textProperty().bindBidirectional(thisRes.getQualifications().get(i));
            }
        }
        // highlights
        if ((resumeHighlightsBox.getChildren().size() - 5) > thisRes.getNumbHighlights()) {
            while ((resumeHighlightsBox.getChildren().size() - 5) > thisRes.getNumbHighlights()) {
                resumeHighlightsBox.getChildren().remove(3);
                highlightsFields.remove(thisRes.getNumbHighlights());
            }
        } else {
            while ((resumeHighlightsBox.getChildren().size() - 5) < thisRes.getNumbHighlights()) {
                int newNumb = resumeHighlightsBox.getChildren().size() - 4;
                VP_PageSubdivision highlightDiv = new VP_PageSubdivision("PERSONAL QUALITY #" + newNumb, false);
                highlightsFields.add(new VP_TextField(50, 128));
                VP_Button delBtn = new VP_Button("Delete", new DeleteHighlightAction(newNumb));
                VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{highlightsFields.get(newNumb - 1)}),
                        delline = new VP_DivisionLine(new Node[]{delBtn});
                highlightDiv.getChildren().addAll(line0, delline);
                resumeHighlightsBox.getChildren().add(resumeHighlightsBox.getChildren().size() - 3, highlightDiv);
            }
            for (int i = 0; i < thisRes.getNumbHighlights(); i++) {
                highlightsFields.get(i).textProperty().bindBidirectional(thisRes.getHighlights().get(i));
            }
        }
        // languages
        if ((resumeLanguagesBox.getChildren().size() - 5) > thisRes.getNumbLanguages()) {
            while ((resumeLanguagesBox.getChildren().size() - 5) > thisRes.getNumbLanguages()) {
                resumeLanguagesBox.getChildren().remove(3);
                languagesFields.remove(thisRes.getNumbLanguages());
            }
        } else {
            while ((resumeLanguagesBox.getChildren().size() - 5) < thisRes.getNumbLanguages()) {
                int newNumb = resumeLanguagesBox.getChildren().size() - 4;
                VP_PageSubdivision languageDiv = new VP_PageSubdivision("SECONDARY LANGUAGE #" + (newNumb - 1), false);
                languagesFields.add(new VP_TextField(50, 128));
                VP_Button delBtn = new VP_Button("Delete", new DeleteLanguageAction(newNumb));
                VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{languagesFields.get(newNumb - 1)}),
                        delline = new VP_DivisionLine(new Node[]{delBtn});
                languageDiv.getChildren().addAll(line0, delline);
                resumeLanguagesBox.getChildren().add(resumeLanguagesBox.getChildren().size() - 3, languageDiv);
            }
            for (int i = 0; i < thisRes.getNumbLanguages(); i++) {
                languagesFields.get(i).textProperty().bindBidirectional(thisRes.getLanguages().get(i));
            }
        }
        // software
        if ((resumeHighlightsBox.getChildren().size() - 5) > thisRes.getNumbHighlights()) {
            while ((resumeHighlightsBox.getChildren().size() - 5) > thisRes.getNumbHighlights()) {
                resumeHighlightsBox.getChildren().remove(3);
                highlightsFields.remove(thisRes.getNumbHighlights());
            }
        } else {
            while ((resumeSoftwareBox.getChildren().size() - 5) < thisRes.getNumbSoftware()) {
                int newNumb = resumeSoftwareBox.getChildren().size() - 4;
                VP_PageSubdivision softwareDiv = new VP_PageSubdivision("SOFTWARE PRODUCT #" + newNumb, false);
                softwareFields.add(new VP_TextField(50, 128));
                VP_Button delBtn = new VP_Button("Delete", new DeleteSoftwareAction(newNumb));
                VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{softwareFields.get(newNumb - 1)}),
                        delline = new VP_DivisionLine(new Node[]{delBtn});
                softwareDiv.getChildren().addAll(line0, delline);
                resumeSoftwareBox.getChildren().add(resumeSoftwareBox.getChildren().size() - 3, softwareDiv);
            }
            for (int i = 0; i < thisRes.getNumbSoftware(); i++) {
                softwareFields.get(i).textProperty().bindBidirectional(thisRes.getSoftware().get(i));
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
                    controller.errorAlert(3115, ex.getMessage());
                } catch (TransformerException | ParserConfigurationException | IOException | DocumentException ex) {
                    controller.errorAlert(3302, ex.getMessage());
                }
            }
            showScreen(3, 0);
        }
    }

    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    private class DeleteEducationAction implements EventHandler<ActionEvent> {

        private final int entryNumber;

        public DeleteEducationAction(int entryNumber) {
            this.entryNumber = entryNumber;
        }

        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            for (int i = 0; i < educationFields.size(); i++) {
                educationFields.get(i).textProperty().unbind();
            }
            controller.getCurrentUser().getResume().setNumbEducation(controller.getCurrentUser().getResume().getNumbEducation() - 1);
            for (int i = 0; i < 6; i++) {
                educationFields.remove((entryNumber - 1) * 6);
            }
            resumeEducationBox.getChildren().remove(entryNumber + 1);
            controller.getCurrentUser().getResume().getEducation().remove(entryNumber - 1);
            controller.getCurrentUser().getResume().getEducation().add(new ArrayList());
            for (int i = 0; i < 6; i++) {
                controller.getCurrentUser().getResume().getEducation().get(8).add(new SimpleStringProperty());
            }
            for (int i = 0; i < controller.getCurrentUser().getResume().getNumbEducation(); i++) {
                if (i > 0) {
                    ((Label) (((VP_PageSubdivision) (resumeEducationBox.getChildren().get(i + 2))).getChildren().get(0))).setText("EDUCATION ENTRY #" + (i + 1));
                    ((VP_Button) (((VP_DivisionLine) ((VP_PageSubdivision) (resumeEducationBox.getChildren().get(i + 2))).getChildren().get(7))).getChildren().get(0)).setOnAction(new DeleteEducationAction(i + 1));
                }
                for (int ii = 0; ii < 6; ii++) {
                    educationFields.get((6 * i) + ii).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getEducation().get(i).get(ii));
                }
            }
            addEducationBtn.setVisible(true);
            addEducationBtn.setManaged(true);
        }
    }

    private class AddEducationAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            int newNumb = controller.getCurrentUser().getResume().getNumbEducation() + 1;
            VP_PageSubdivision educationDiv = new VP_PageSubdivision("EDUCATION ENTRY #" + newNumb, false);
            VP_FieldLabel label0 = new VP_FieldLabel("institution name:", 130),
                    label1 = new VP_FieldLabel("institution location:", 130),
                    label2 = new VP_FieldLabel("degree, certificate, or\ntraining earned:", 130),
                    label3 = new VP_FieldLabel("*GPA:", 130),
                    label4 = new VP_FieldLabel("start date", 130),
                    label5 = new VP_FieldLabel("end date", 130);
            educationFields.add(new VP_TextField(32, 128));
            educationFields.add(new VP_TextField(32, 128));
            educationFields.add(new VP_TextField(32, 128));
            educationFields.add(new VP_TextField(32, 128));
            educationFields.add(new VP_TextField(32, 128));
            educationFields.add(new VP_TextField(32, 128));
            VP_Button delBtn = new VP_Button("Delete", new DeleteEducationAction(newNumb));
            VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{label0, educationFields.get(0 + (6 * controller.getCurrentUser().getResume().getNumbEducation()))}),
                    line1 = new VP_DivisionLine(new Node[]{label1, educationFields.get(1 + (6 * controller.getCurrentUser().getResume().getNumbEducation()))}),
                    line2 = new VP_DivisionLine(new Node[]{label2, educationFields.get(2 + (6 * controller.getCurrentUser().getResume().getNumbEducation()))}),
                    line3 = new VP_DivisionLine(new Node[]{label3, educationFields.get(3 + (6 * controller.getCurrentUser().getResume().getNumbEducation()))}),
                    line4 = new VP_DivisionLine(new Node[]{label4, educationFields.get(4 + (6 * controller.getCurrentUser().getResume().getNumbEducation()))}),
                    line5 = new VP_DivisionLine(new Node[]{label5, educationFields.get(5 + (6 * controller.getCurrentUser().getResume().getNumbEducation()))}),
                    delline = new VP_DivisionLine(new Node[]{delBtn});
            educationDiv.getChildren().addAll(line0, line1, line2, line3, line4, line5, delline);
            resumeEducationBox.getChildren().add(resumeEducationBox.getChildren().size() - 3, educationDiv);
            int ii = 0;
            for (int i = 6 * controller.getCurrentUser().getResume().getNumbEducation(); i < educationFields.size(); i++) {
                educationFields.get(i).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getEducation().get(controller.getCurrentUser().getResume().getNumbEducation()).get(ii));
                ii += 1;
            }
            controller.getCurrentUser().getResume().setNumbEducation(newNumb);
            if (controller.getCurrentUser().getResume().getNumbEducation() == 9) {
                addEducationBtn.setVisible(false);
                addEducationBtn.setManaged(false);
            }
        }
    }

    private class SubmitEducationAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            boolean hasError = false;
            VP_Sounds.play(0);
            for (int i = 0; i < educationFields.size(); i++) {
                educationFields.get(i).textProperty().setValue(educationFields.get(i).textProperty().getValueSafe().trim());
                if (educationFields.get(i).textProperty().getValueSafe().equals("")) {
                    if (i % 6 == 0) {
                        educationError.setParaText("The institution name cannot be blank in education entry #" + (((int) (i / 6)) + 1) + ".");
                        hasError = true;
                    } else if (i % 6 == 1) {
                        educationError.setParaText("The institution location cannot be blank in education entry #" + (((int) (i / 6)) + 1) + ".");
                        hasError = true;
                    } else if (i % 6 == 2) {
                        educationError.setParaText("The degree, certification, or training field cannot be blank in education entry #" + (((int) (i / 6)) + 1) + ".");
                        hasError = true;
                    } else if (i % 6 == 4) {
                        educationError.setParaText("The start date cannot be blank in education entry #" + (((int) (i / 6)) + 1) + ".");
                        hasError = true;
                    } else if (i % 6 == 5) {
                        educationError.setParaText("The end date cannot be blank in education entry #" + (((int) (i / 6)) + 1) + ".");
                        hasError = true;
                    }
                    if (hasError) {
                        educationFields.get(i).showInvalid();
                        break;
                    }
                }
            }
            if (hasError) {
                VP_Sounds.play(-1);
                educationErrorLine.show();
            } else {
                educationError.setParaText("");
                educationErrorLine.hide();
                controller.getCurrentUser().getResume().save();
                if (controller.getCurrentUser().getResume().hasChanges()) {
                    updateDynamicFields();
                    try {
                        controller.getDataM().saveResume(1);
                    } catch (SQLException ex) {
                        controller.errorAlert(3118, ex.getMessage());
                    } catch (TransformerException | ParserConfigurationException | IOException | DocumentException ex) {
                        controller.errorAlert(3303, ex.getMessage());
                    }
                }
                showScreen(11, 0);
            }
        }
    }

    private class DeleteExperienceAction implements EventHandler<ActionEvent> {

        private final int entryNumber;

        public DeleteExperienceAction(int entryNumber) {
            this.entryNumber = entryNumber;
        }

        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            for (int i = 0; i < experienceFields.size(); i++) {
                experienceFields.get(i).textProperty().unbind();
            }
            controller.getCurrentUser().getResume().setNumbExperience(controller.getCurrentUser().getResume().getNumbExperience() - 1);
            for (int i = 0; i < 5; i++) {
                experienceFields.remove((entryNumber - 1) * 5);
            }
            resumeExperienceBox.getChildren().remove(entryNumber + 1);
            controller.getCurrentUser().getResume().getExperience().remove(entryNumber - 1);
            controller.getCurrentUser().getResume().getExperience().add(new ArrayList());
            for (int i = 0; i < 5; i++) {
                controller.getCurrentUser().getResume().getExperience().get(8).add(new SimpleStringProperty());
            }
            for (int i = 0; i < controller.getCurrentUser().getResume().getNumbExperience(); i++) {
                if (i > 0) {
                    ((Label) (((VP_PageSubdivision) (resumeExperienceBox.getChildren().get(i + 2))).getChildren().get(0))).setText("EXPERIENCE ENTRY #" + (i + 1));
                    ((VP_Button) (((VP_DivisionLine) ((VP_PageSubdivision) (resumeExperienceBox.getChildren().get(i + 2))).getChildren().get(6))).getChildren().get(0)).setOnAction(new DeleteExperienceAction(i + 1));
                }
                for (int ii = 0; ii < 5; ii++) {
                    experienceFields.get((5 * i) + ii).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getExperience().get(i).get(ii));
                }
            }
            addExperienceBtn.setVisible(true);
            addExperienceBtn.setManaged(true);
        }
    }

    private class AddExperienceAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            int newNumb = controller.getCurrentUser().getResume().getNumbExperience() + 1;
            VP_PageSubdivision experienceDiv = new VP_PageSubdivision("EXPERIENCE ENTRY #" + newNumb, false);
            VP_FieldLabel label0 = new VP_FieldLabel("institution name:", 130),
                    label1 = new VP_FieldLabel("institution location:", 130),
                    label2 = new VP_FieldLabel("position held:", 130),
                    label3 = new VP_FieldLabel("start date", 130),
                    label4 = new VP_FieldLabel("end date", 130);
            experienceFields.add(new VP_TextField(32, 128));
            experienceFields.add(new VP_TextField(32, 128));
            experienceFields.add(new VP_TextField(32, 128));
            experienceFields.add(new VP_TextField(32, 128));
            experienceFields.add(new VP_TextField(32, 128));
            VP_Button delBtn = new VP_Button("Delete", new DeleteExperienceAction(newNumb));
            VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{label0, experienceFields.get(0 + (5 * controller.getCurrentUser().getResume().getNumbExperience()))}),
                    line1 = new VP_DivisionLine(new Node[]{label1, experienceFields.get(1 + (5 * controller.getCurrentUser().getResume().getNumbExperience()))}),
                    line2 = new VP_DivisionLine(new Node[]{label2, experienceFields.get(2 + (5 * controller.getCurrentUser().getResume().getNumbExperience()))}),
                    line3 = new VP_DivisionLine(new Node[]{label3, experienceFields.get(3 + (5 * controller.getCurrentUser().getResume().getNumbExperience()))}),
                    line4 = new VP_DivisionLine(new Node[]{label4, experienceFields.get(4 + (5 * controller.getCurrentUser().getResume().getNumbExperience()))}),
                    delline = new VP_DivisionLine(new Node[]{delBtn});
            experienceDiv.getChildren().addAll(line0, line1, line2, line3, line4, delline);
            resumeExperienceBox.getChildren().add(resumeExperienceBox.getChildren().size() - 3, experienceDiv);
            int ii = 0;
            for (int i = 5 * controller.getCurrentUser().getResume().getNumbExperience(); i < experienceFields.size(); i++) {
                experienceFields.get(i).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getExperience().get(controller.getCurrentUser().getResume().getNumbExperience()).get(ii));
                ii += 1;
            }
            controller.getCurrentUser().getResume().setNumbExperience(newNumb);
            if (controller.getCurrentUser().getResume().getNumbExperience() == 9) {
                addExperienceBtn.setVisible(false);
                addExperienceBtn.setManaged(false);
            }
        }
    }

    private class SubmitExperienceAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            boolean hasError = false;
            VP_Sounds.play(0);
            for (int i = 0; i < experienceFields.size(); i++) {
                experienceFields.get(i).textProperty().setValue(experienceFields.get(i).textProperty().getValueSafe().trim());
                if (experienceFields.get(i).textProperty().getValueSafe().equals("")) {
                    if (i % 5 == 0) {
                        experienceError.setParaText("The institution name cannot be blank in experience entry #" + (((int) (i / 5)) + 1) + ".");
                        hasError = true;
                    } else if (i % 5 == 1) {
                        experienceError.setParaText("The institution location cannot be blank in experience entry #" + (((int) (i / 5)) + 1) + ".");
                        hasError = true;
                    } else if (i % 5 == 2) {
                        experienceError.setParaText("The position held cannot be blank in experience entry #" + (((int) (i / 5)) + 1) + ".");
                        hasError = true;
                    } else if (i % 5 == 3) {
                        experienceError.setParaText("The start date cannot be blank in experience entry #" + (((int) (i / 5)) + 1) + ".");
                        hasError = true;
                    } else if (i % 5 == 4) {
                        experienceError.setParaText("The end date cannot be blank in experience entry #" + (((int) (i / 5)) + 1) + ".");
                        hasError = true;
                    }
                    if (hasError) {
                        experienceFields.get(i).showInvalid();
                        break;
                    }
                }
            }
            if (hasError) {
                VP_Sounds.play(-1);
                experienceErrorLine.show();
            } else {
                experienceError.setParaText("");
                experienceErrorLine.hide();
                controller.getCurrentUser().getResume().save();
                if (controller.getCurrentUser().getResume().hasChanges()) {
                    updateDynamicFields();
                    try {
                        controller.getDataM().saveResume(2);
                    } catch (SQLException ex) {
                        controller.errorAlert(3119, ex.getMessage());
                    } catch (TransformerException | ParserConfigurationException | IOException | DocumentException ex) {
                        controller.errorAlert(3303, ex.getMessage());
                    }
                }
                showScreen(11, 0);
            }
        }
    }

    private class DeleteAchievementAction implements EventHandler<ActionEvent> {

        private final int entryNumber;

        public DeleteAchievementAction(int entryNumber) {
            this.entryNumber = entryNumber;
        }

        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            for (int i = 0; i < achievementsFields.size(); i++) {
                achievementsFields.get(i).textProperty().unbind();
            }
            controller.getCurrentUser().getResume().setNumbAchievements(controller.getCurrentUser().getResume().getNumbAchievements() - 1);
            for (int i = 0; i < 3; i++) {
                achievementsFields.remove((entryNumber - 1) * 3);
            }
            resumeAchievementsBox.getChildren().remove(entryNumber + 1);
            controller.getCurrentUser().getResume().getAchievements().remove(entryNumber - 1);
            controller.getCurrentUser().getResume().getAchievements().add(new ArrayList());
            for (int i = 0; i < 3; i++) {
                controller.getCurrentUser().getResume().getAchievements().get(8).add(new SimpleStringProperty());
            }
            for (int i = 0; i < controller.getCurrentUser().getResume().getNumbAchievements(); i++) {
                if (i > 0) {
                    ((Label) (((VP_PageSubdivision) (resumeAchievementsBox.getChildren().get(i + 2))).getChildren().get(0))).setText("AWARD/ACHIEVEMENT ENTRY #" + (i + 1));
                    ((VP_Button) (((VP_DivisionLine) ((VP_PageSubdivision) (resumeAchievementsBox.getChildren().get(i + 2))).getChildren().get(4))).getChildren().get(0)).setOnAction(new DeleteAchievementAction(i + 1));
                }
                for (int ii = 0; ii < 3; ii++) {
                    achievementsFields.get((3 * i) + ii).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getAchievements().get(i).get(ii));
                }
            }
            addAchievementBtn.setVisible(true);
            addAchievementBtn.setManaged(true);
        }
    }

    private class AddAchievementAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            int newNumb = controller.getCurrentUser().getResume().getNumbAchievements() + 1;
            VP_PageSubdivision achievementDiv = new VP_PageSubdivision("AWARD/ACHIEVEMENT ENTRY #" + newNumb, false);
            VP_FieldLabel label0 = new VP_FieldLabel("name of award\nor achievement:", 130),
                    label1 = new VP_FieldLabel("given by:", 130),
                    label2 = new VP_FieldLabel("date:", 130);
            achievementsFields.add(new VP_TextField(32, 128));
            achievementsFields.add(new VP_TextField(32, 128));
            achievementsFields.add(new VP_TextField(32, 128));
            VP_Button delBtn = new VP_Button("Delete", new DeleteAchievementAction(newNumb));
            VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{label0, achievementsFields.get(0 + (3 * controller.getCurrentUser().getResume().getNumbAchievements()))}),
                    line1 = new VP_DivisionLine(new Node[]{label1, achievementsFields.get(1 + (3 * controller.getCurrentUser().getResume().getNumbAchievements()))}),
                    line2 = new VP_DivisionLine(new Node[]{label2, achievementsFields.get(2 + (3 * controller.getCurrentUser().getResume().getNumbAchievements()))}),
                    delline = new VP_DivisionLine(new Node[]{delBtn});
            achievementDiv.getChildren().addAll(line0, line1, line2, delline);
            resumeAchievementsBox.getChildren().add(resumeAchievementsBox.getChildren().size() - 3, achievementDiv);
            int ii = 0;
            for (int i = 3 * controller.getCurrentUser().getResume().getNumbAchievements(); i < achievementsFields.size(); i++) {
                achievementsFields.get(i).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getAchievements().get(controller.getCurrentUser().getResume().getNumbAchievements()).get(ii));
                ii += 1;
            }
            controller.getCurrentUser().getResume().setNumbAchievements(newNumb);
            if (controller.getCurrentUser().getResume().getNumbAchievements() == 9) {
                addAchievementBtn.setVisible(false);
                addAchievementBtn.setManaged(false);
            }
        }
    }

    private class SubmitAchievementsAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            boolean hasError = false;
            VP_Sounds.play(0);
            for (int i = 0; i < achievementsFields.size(); i++) {
                achievementsFields.get(i).textProperty().setValue(achievementsFields.get(i).textProperty().getValueSafe().trim());
            }
            boolean endReached = false;
            for (int ii = 0; ii < controller.getCurrentUser().getResume().getNumbAchievements(); ii++) {
                int count = 0;
                for (int i = 0 + (ii * 3); i < 3 + (ii * 3); i++) {
                    if (!achievementsFields.get(i).textProperty().getValueSafe().equals("")) {
                        count += 1;
                    }
                }
                if (count == 0 && !endReached) {
                    endReached = true;
                } else if (endReached && count > 0) {
                    achievementsError.setParaText("You cannot have blank achievements in between entries. See achievement #" + (ii));
                    hasError = true;
                    break;
                } else if (count < 3 && count > 0) {
                    achievementsError.setParaText("Not all mandatory fields for an achievement are complete for entry #" + (ii + 1));
                    hasError = true;
                    break;
                }
            }
            if (hasError) {
                VP_Sounds.play(-1);
                achievementsErrorLine.show();
            } else {
                achievementsError.setParaText("");
                achievementsErrorLine.hide();
                controller.getCurrentUser().getResume().save();
                if (controller.getCurrentUser().getResume().hasChanges()) {
                    updateDynamicFields();
                    try {
                        controller.getDataM().saveResume(3);
                    } catch (SQLException ex) {
                        controller.errorAlert(3121, ex.getMessage());
                    } catch (TransformerException | ParserConfigurationException | IOException | DocumentException ex) {
                        controller.errorAlert(3303, ex.getMessage());
                    }
                }
                showScreen(11, 0);
            }
        }
    }

    private class DeleteCommunityAction implements EventHandler<ActionEvent> {

        private final int entryNumber;

        public DeleteCommunityAction(int entryNumber) {
            this.entryNumber = entryNumber;
        }

        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            for (int i = 0; i < communityFields.size(); i++) {
                communityFields.get(i).textProperty().unbind();
            }
            controller.getCurrentUser().getResume().setNumbCommunity(controller.getCurrentUser().getResume().getNumbCommunity() - 1);
            for (int i = 0; i < 3; i++) {
                communityFields.remove((entryNumber - 1) * 3);
            }
            resumeCommunityBox.getChildren().remove(entryNumber + 1);
            controller.getCurrentUser().getResume().getCommunity().remove(entryNumber - 1);
            controller.getCurrentUser().getResume().getCommunity().add(new ArrayList());
            for (int i = 0; i < 3; i++) {
                controller.getCurrentUser().getResume().getCommunity().get(8).add(new SimpleStringProperty());
            }
            for (int i = 0; i < controller.getCurrentUser().getResume().getNumbCommunity(); i++) {
                if (i > 0) {
                    ((Label) (((VP_PageSubdivision) (resumeCommunityBox.getChildren().get(i + 2))).getChildren().get(0))).setText("EVENT ENTRY #" + (i + 1));
                    ((VP_Button) (((VP_DivisionLine) ((VP_PageSubdivision) (resumeCommunityBox.getChildren().get(i + 2))).getChildren().get(4))).getChildren().get(0)).setOnAction(new DeleteCommunityAction(i + 1));
                }
                for (int ii = 0; ii < 3; ii++) {
                    communityFields.get((3 * i) + ii).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getCommunity().get(i).get(ii));
                }
            }
            addCommunityBtn.setVisible(true);
            addCommunityBtn.setManaged(true);
        }
    }

    private class AddCommunityAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            int newNumb = controller.getCurrentUser().getResume().getNumbCommunity() + 1;
            VP_PageSubdivision communityDiv = new VP_PageSubdivision("EVENT ENTRY #" + newNumb, false);
            VP_FieldLabel label0 = new VP_FieldLabel("event name:", 130),
                    label1 = new VP_FieldLabel("event location:", 130),
                    label2 = new VP_FieldLabel("date:", 130);
            communityFields.add(new VP_TextField(32, 128));
            communityFields.add(new VP_TextField(32, 128));
            communityFields.add(new VP_TextField(32, 128));
            VP_Button delBtn = new VP_Button("Delete", new DeleteCommunityAction(newNumb));
            VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{label0, communityFields.get(0 + (3 * controller.getCurrentUser().getResume().getNumbCommunity()))}),
                    line1 = new VP_DivisionLine(new Node[]{label1, communityFields.get(1 + (3 * controller.getCurrentUser().getResume().getNumbCommunity()))}),
                    line2 = new VP_DivisionLine(new Node[]{label2, communityFields.get(2 + (3 * controller.getCurrentUser().getResume().getNumbCommunity()))}),
                    delline = new VP_DivisionLine(new Node[]{delBtn});
            communityDiv.getChildren().addAll(line0, line1, line2, delline);
            resumeCommunityBox.getChildren().add(resumeCommunityBox.getChildren().size() - 3, communityDiv);
            int ii = 0;
            for (int i = 3 * controller.getCurrentUser().getResume().getNumbCommunity(); i < communityFields.size(); i++) {
                communityFields.get(i).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getCommunity().get(controller.getCurrentUser().getResume().getNumbCommunity()).get(ii));
                ii += 1;
            }
            controller.getCurrentUser().getResume().setNumbCommunity(newNumb);
            if (controller.getCurrentUser().getResume().getNumbCommunity() == 9) {
                addCommunityBtn.setVisible(false);
                addCommunityBtn.setManaged(false);
            }
        }
    }

    private class SubmitCommunityAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            boolean hasError = false;
            VP_Sounds.play(0);
            for (int i = 0; i < communityFields.size(); i++) {
                communityFields.get(i).textProperty().setValue(communityFields.get(i).textProperty().getValueSafe().trim());
            }
            boolean endReached = false;
            for (int ii = 0; ii < controller.getCurrentUser().getResume().getNumbCommunity(); ii++) {
                int count = 0;
                for (int i = 0 + (ii * 3); i < 3 + (ii * 3); i++) {
                    if (!communityFields.get(i).textProperty().getValueSafe().equals("")) {
                        count += 1;
                    }
                }
                if (count == 0 && !endReached) {
                    endReached = true;
                } else if (endReached && count > 0) {
                    communityError.setParaText("You cannot have blank events in between entries. See entry #" + (ii));
                    hasError = true;
                    break;
                } else if (count < 3 && count > 0) {
                    communityError.setParaText("Not all mandatory fields for an event are complete for entry #" + (ii + 1));
                    hasError = true;
                    break;
                }
            }
            if (hasError) {
                VP_Sounds.play(-1);
                communityErrorLine.show();
            } else {
                communityError.setParaText("");
                communityErrorLine.hide();
                controller.getCurrentUser().getResume().save();
                if (controller.getCurrentUser().getResume().hasChanges()) {
                    updateDynamicFields();
                    try {
                        controller.getDataM().saveResume(4);
                    } catch (SQLException ex) {
                        controller.errorAlert(3122, ex.getMessage());
                    } catch (TransformerException | ParserConfigurationException | IOException | DocumentException ex) {
                        controller.errorAlert(3303, ex.getMessage());
                    }
                }
                showScreen(11, 0);
            }
        }
    }

    private class DeleteQualificationAction implements EventHandler<ActionEvent> {

        private final int entryNumber;

        public DeleteQualificationAction(int entryNumber) {
            this.entryNumber = entryNumber;
        }

        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            for (int i = 0; i < qualificationsFields.size(); i++) {
                qualificationsFields.get(i).textProperty().unbind();
            }
            controller.getCurrentUser().getResume().setNumbQualification(controller.getCurrentUser().getResume().getNumbQualification() - 1);
            qualificationsFields.remove(entryNumber - 1);
            resumeQualificationsBox.getChildren().remove(entryNumber + 1);
            controller.getCurrentUser().getResume().getQualifications().remove(entryNumber - 1);
            controller.getCurrentUser().getResume().getQualifications().add(new SimpleStringProperty());
            for (int i = 0; i < controller.getCurrentUser().getResume().getNumbQualification(); i++) {
                if (i > 0) {
                    ((Label) (((VP_PageSubdivision) (resumeQualificationsBox.getChildren().get(i + 2))).getChildren().get(0))).setText("QUALIFICATION #" + (i + 1));
                    ((VP_Button) (((VP_DivisionLine) ((VP_PageSubdivision) (resumeQualificationsBox.getChildren().get(i + 2))).getChildren().get(2))).getChildren().get(0)).setOnAction(new DeleteQualificationAction(i + 1));
                }
                qualificationsFields.get(i).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getQualifications().get(i));
            }
            addQualificationBtn.setVisible(true);
            addQualificationBtn.setManaged(true);
        }
    }

    private class AddQualificationAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            int newNumb = controller.getCurrentUser().getResume().getNumbQualification() + 1;
            VP_PageSubdivision qualificationDiv = new VP_PageSubdivision("QUALIFICATION #" + newNumb, false);
            qualificationsFields.add(new VP_TextField(50, 128));
            VP_Button delBtn = new VP_Button("Delete", new DeleteQualificationAction(newNumb));
            VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{qualificationsFields.get(controller.getCurrentUser().getResume().getNumbQualification())}),
                    delline = new VP_DivisionLine(new Node[]{delBtn});
            qualificationDiv.getChildren().addAll(line0, delline);
            resumeQualificationsBox.getChildren().add(resumeQualificationsBox.getChildren().size() - 3, qualificationDiv);
            qualificationsFields.get(qualificationsFields.size() - 1).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getQualifications().get(qualificationsFields.size() - 1));
            controller.getCurrentUser().getResume().setNumbQualification(newNumb);
            if (controller.getCurrentUser().getResume().getNumbQualification() == 9) {
                addQualificationBtn.setVisible(false);
                addQualificationBtn.setManaged(false);
            }
        }
    }

    private class SubmitQualificationsAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            boolean hasError = false;
            VP_Sounds.play(0);
            for (int i = 0; i < qualificationsFields.size(); i++) {
                qualificationsFields.get(i).textProperty().setValue(qualificationsFields.get(i).textProperty().getValueSafe().trim());
                if (qualificationsFields.get(i).textProperty().getValueSafe().equals("")) {
                    qualificationsError.setParaText("Qualification #" + (i + 1) + " cannot be blank.");
                    hasError = true;
                    qualificationsFields.get(i).showInvalid();
                    break;
                }
            }
            if (hasError) {
                VP_Sounds.play(-1);
                qualificationsErrorLine.show();
            } else {
                qualificationsError.setParaText("");
                qualificationsErrorLine.hide();
                controller.getCurrentUser().getResume().save();
                if (controller.getCurrentUser().getResume().hasChanges()) {
                    updateDynamicFields();
                    try {
                        controller.getDataM().saveResume(5);
                    } catch (SQLException ex) {
                        controller.errorAlert(3123, ex.getMessage());
                    } catch (TransformerException | ParserConfigurationException | IOException | DocumentException ex) {
                        controller.errorAlert(3303, ex.getMessage());
                    }
                }
                showScreen(11, 0);
            }
        }
    }

    private class DeleteHighlightAction implements EventHandler<ActionEvent> {

        private final int entryNumber;

        public DeleteHighlightAction(int entryNumber) {
            this.entryNumber = entryNumber;
        }

        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            for (int i = 0; i < highlightsFields.size(); i++) {
                highlightsFields.get(i).textProperty().unbind();
            }
            controller.getCurrentUser().getResume().setNumbHighlights(controller.getCurrentUser().getResume().getNumbHighlights() - 1);
            highlightsFields.remove(entryNumber - 1);
            resumeHighlightsBox.getChildren().remove(entryNumber + 1);
            controller.getCurrentUser().getResume().getHighlights().remove(entryNumber - 1);
            controller.getCurrentUser().getResume().getHighlights().add(new SimpleStringProperty());
            for (int i = 0; i < controller.getCurrentUser().getResume().getNumbHighlights(); i++) {
                if (i > 0) {
                    ((Label) (((VP_PageSubdivision) (resumeHighlightsBox.getChildren().get(i + 2))).getChildren().get(0))).setText("PERSONAL QUALITY #" + (i + 1));
                    ((VP_Button) (((VP_DivisionLine) ((VP_PageSubdivision) (resumeHighlightsBox.getChildren().get(i + 2))).getChildren().get(2))).getChildren().get(0)).setOnAction(new DeleteHighlightAction(i + 1));
                }
                highlightsFields.get(i).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getHighlights().get(i));
            }
            addHighlightBtn.setVisible(true);
            addHighlightBtn.setManaged(true);
        }
    }

    private class AddHighlightAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            int newNumb = controller.getCurrentUser().getResume().getNumbHighlights() + 1;
            VP_PageSubdivision highlightDiv = new VP_PageSubdivision("PERSONAL QUALITY #" + newNumb, false);
            highlightsFields.add(new VP_TextField(50, 128));
            VP_Button delBtn = new VP_Button("Delete", new DeleteHighlightAction(newNumb));
            VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{highlightsFields.get(controller.getCurrentUser().getResume().getNumbHighlights())}),
                    delline = new VP_DivisionLine(new Node[]{delBtn});
            highlightDiv.getChildren().addAll(line0, delline);
            resumeHighlightsBox.getChildren().add(resumeHighlightsBox.getChildren().size() - 3, highlightDiv);
            highlightsFields.get(highlightsFields.size() - 1).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getHighlights().get(highlightsFields.size() - 1));
            controller.getCurrentUser().getResume().setNumbHighlights(newNumb);
            if (controller.getCurrentUser().getResume().getNumbHighlights() == 9) {
                addHighlightBtn.setVisible(false);
                addHighlightBtn.setManaged(false);
            }
        }
    }

    private class SubmitHighlightsAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            boolean hasError = false;
            VP_Sounds.play(0);
            for (int i = 0; i < highlightsFields.size(); i++) {
                highlightsFields.get(i).textProperty().setValue(highlightsFields.get(i).textProperty().getValueSafe().trim());
                if (highlightsFields.get(i).textProperty().getValueSafe().equals("")) {
                    highlightsError.setParaText("Highlight #" + (i + 1) + " cannot be blank.");
                    hasError = true;
                    highlightsFields.get(i).showInvalid();
                    break;
                }
            }
            if (hasError) {
                VP_Sounds.play(-1);
                highlightsErrorLine.show();
            } else {
                highlightsError.setParaText("");
                highlightsErrorLine.hide();
                controller.getCurrentUser().getResume().save();
                if (controller.getCurrentUser().getResume().hasChanges()) {
                    updateDynamicFields();
                    try {
                        controller.getDataM().saveResume(6);
                    } catch (SQLException ex) {
                        controller.errorAlert(3124, ex.getMessage());
                    } catch (TransformerException | ParserConfigurationException | IOException | DocumentException ex) {
                        controller.errorAlert(3303, ex.getMessage());
                    }
                }
                showScreen(11, 0);
            }
        }
    }

    private class DeleteLanguageAction implements EventHandler<ActionEvent> {

        private final int entryNumber;

        public DeleteLanguageAction(int entryNumber) {
            this.entryNumber = entryNumber;
        }

        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            for (int i = 0; i < languagesFields.size(); i++) {
                languagesFields.get(i).textProperty().unbind();
            }
            controller.getCurrentUser().getResume().setNumbLanguages(controller.getCurrentUser().getResume().getNumbLanguages() - 1);
            languagesFields.remove(entryNumber - 1);
            resumeLanguagesBox.getChildren().remove(entryNumber + 1);
            controller.getCurrentUser().getResume().getLanguages().remove(entryNumber - 1);
            controller.getCurrentUser().getResume().getLanguages().add(new SimpleStringProperty());
            for (int i = 0; i < controller.getCurrentUser().getResume().getNumbLanguages(); i++) {
                if (i > 0) {
                    ((Label) (((VP_PageSubdivision) (resumeLanguagesBox.getChildren().get(i + 2))).getChildren().get(0))).setText("SECONDARY LANGUAGE #" + i);
                    ((VP_Button) (((VP_DivisionLine) ((VP_PageSubdivision) (resumeLanguagesBox.getChildren().get(i + 2))).getChildren().get(2))).getChildren().get(0)).setOnAction(new DeleteLanguageAction(i + 1));
                }
                languagesFields.get(i).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getLanguages().get(i));
            }
            addLanguageBtn.setVisible(true);
            addLanguageBtn.setManaged(true);
        }
    }

    private class AddLanguageAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            int newNumb = controller.getCurrentUser().getResume().getNumbLanguages() + 1;
            VP_PageSubdivision languageDiv = new VP_PageSubdivision("SECONDARY LANGUAGE #" + controller.getCurrentUser().getResume().getNumbLanguages(), false);
            languagesFields.add(new VP_TextField(50, 128));
            VP_Button delBtn = new VP_Button("Delete", new DeleteLanguageAction(newNumb));
            VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{languagesFields.get(controller.getCurrentUser().getResume().getNumbLanguages())}),
                    delline = new VP_DivisionLine(new Node[]{delBtn});
            languageDiv.getChildren().addAll(line0, delline);
            resumeLanguagesBox.getChildren().add(resumeLanguagesBox.getChildren().size() - 3, languageDiv);
            languagesFields.get(languagesFields.size() - 1).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getLanguages().get(languagesFields.size() - 1));
            controller.getCurrentUser().getResume().setNumbLanguages(newNumb);
            if (controller.getCurrentUser().getResume().getNumbLanguages() == 9) {
                addLanguageBtn.setVisible(false);
                addLanguageBtn.setManaged(false);
            }
        }
    }

    private class SubmitLanguagesAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            boolean hasError = false;
            VP_Sounds.play(0);
            for (int i = 0; i < languagesFields.size(); i++) {
                languagesFields.get(i).textProperty().setValue(languagesFields.get(i).textProperty().getValueSafe().trim());
                if (languagesFields.get(i).textProperty().getValueSafe().equals("")) {
                    if (i == 0) {
                        languagesError.setParaText("Primary Language cannot be blank.");
                    } else {
                        languagesError.setParaText("Secondary Language #" + i + " cannot be blank.");
                    }
                    hasError = true;
                    languagesFields.get(i).showInvalid();
                    break;
                }
            }
            if (hasError) {
                VP_Sounds.play(-1);
                languagesErrorLine.show();
            } else {
                languagesError.setParaText("");
                languagesErrorLine.hide();
                controller.getCurrentUser().getResume().save();
                if (controller.getCurrentUser().getResume().hasChanges()) {
                    updateDynamicFields();
                    try {
                        controller.getDataM().saveResume(7);
                    } catch (SQLException ex) {
                        controller.errorAlert(3125, ex.getMessage());
                    } catch (TransformerException | ParserConfigurationException | IOException | DocumentException ex) {
                        controller.errorAlert(3303, ex.getMessage());
                    }
                }
                showScreen(11, 0);
            }
        }
    }

    private class DeleteSoftwareAction implements EventHandler<ActionEvent> {

        private final int entryNumber;

        public DeleteSoftwareAction(int entryNumber) {
            this.entryNumber = entryNumber;
        }

        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            for (int i = 0; i < softwareFields.size(); i++) {
                softwareFields.get(i).textProperty().unbind();
            }
            controller.getCurrentUser().getResume().setNumbSoftware(controller.getCurrentUser().getResume().getNumbSoftware() - 1);
            softwareFields.remove(entryNumber - 1);
            resumeSoftwareBox.getChildren().remove(entryNumber + 1);
            controller.getCurrentUser().getResume().getSoftware().remove(entryNumber - 1);
            controller.getCurrentUser().getResume().getSoftware().add(new SimpleStringProperty());
            for (int i = 0; i < controller.getCurrentUser().getResume().getNumbSoftware(); i++) {
                if (i > 0) {
                    ((Label) (((VP_PageSubdivision) (resumeSoftwareBox.getChildren().get(i + 2))).getChildren().get(0))).setText("SOFTWARE PRODUCT #" + (i + 1));
                    ((VP_Button) (((VP_DivisionLine) ((VP_PageSubdivision) (resumeSoftwareBox.getChildren().get(i + 2))).getChildren().get(2))).getChildren().get(0)).setOnAction(new DeleteSoftwareAction(i + 1));
                }
                softwareFields.get(i).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getSoftware().get(i));
            }
            addSoftwareBtn.setVisible(true);
            addSoftwareBtn.setManaged(true);
        }
    }

    private class AddSoftwareAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            int newNumb = controller.getCurrentUser().getResume().getNumbSoftware() + 1;
            VP_PageSubdivision softwareDiv = new VP_PageSubdivision("SOFTWARE PRODUCT #" + newNumb, false);
            softwareFields.add(new VP_TextField(50, 128));
            VP_Button delBtn = new VP_Button("Delete", new DeleteSoftwareAction(newNumb));
            VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{softwareFields.get(controller.getCurrentUser().getResume().getNumbSoftware())}),
                    delline = new VP_DivisionLine(new Node[]{delBtn});
            softwareDiv.getChildren().addAll(line0, delline);
            resumeSoftwareBox.getChildren().add(resumeSoftwareBox.getChildren().size() - 3, softwareDiv);
            softwareFields.get(softwareFields.size() - 1).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getSoftware().get(softwareFields.size() - 1));
            controller.getCurrentUser().getResume().setNumbSoftware(newNumb);
            if (controller.getCurrentUser().getResume().getNumbSoftware() == 9) {
                addSoftwareBtn.setVisible(false);
                addSoftwareBtn.setManaged(false);
            }
        }
    }

    private class SubmitSoftwareAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            boolean hasError = false;
            VP_Sounds.play(0);
            for (int i = 0; i < softwareFields.size(); i++) {
                softwareFields.get(i).textProperty().setValue(softwareFields.get(i).textProperty().getValueSafe().trim());
                if (softwareFields.get(i).textProperty().getValueSafe().equals("")) {
                    softwareError.setParaText("Software Product #" + (i + 1) + " cannot be blank.");
                    hasError = true;
                    softwareFields.get(i).showInvalid();
                    break;
                }
            }
            if (hasError) {
                VP_Sounds.play(-1);
                softwareErrorLine.show();
            } else {
                softwareError.setParaText("");
                softwareErrorLine.hide();
                controller.getCurrentUser().getResume().save();
                if (controller.getCurrentUser().getResume().hasChanges()) {
                    updateDynamicFields();
                    try {
                        controller.getDataM().saveResume(8);
                    } catch (SQLException ex) {
                        controller.errorAlert(3126, ex.getMessage());
                    } catch (TransformerException | ParserConfigurationException | IOException | DocumentException ex) {
                        controller.errorAlert(3303, ex.getMessage());
                    }
                }
                showScreen(11, 0);
            }
        }
    }

    private class DeleteReferenceAction implements EventHandler<ActionEvent> {

        private final int entryNumber;

        public DeleteReferenceAction(int entryNumber) {
            this.entryNumber = entryNumber;
        }

        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            for (int i = 0; i < referencesFields.size(); i++) {
                referencesFields.get(i).textProperty().unbind();
            }
            controller.getCurrentUser().getResume().setNumbReferences(controller.getCurrentUser().getResume().getNumbReferences() - 1);
            for (int i = 0; i < 6; i++) {
                referencesFields.remove((entryNumber - 1) * 6);
            }
            resumeReferencesBox.getChildren().remove(entryNumber + 1);
            controller.getCurrentUser().getResume().getReferences().remove(entryNumber - 1);
            controller.getCurrentUser().getResume().getReferences().add(new ArrayList());
            for (int i = 0; i < 6; i++) {
                controller.getCurrentUser().getResume().getReferences().get(8).add(new SimpleStringProperty());
            }
            for (int i = 0; i < controller.getCurrentUser().getResume().getNumbReferences(); i++) {
                if (i > 0) {
                    ((Label) (((VP_PageSubdivision) (resumeReferencesBox.getChildren().get(i + 2))).getChildren().get(0))).setText("REFERENCE #" + (i + 1));
                    ((VP_Button) (((VP_DivisionLine) ((VP_PageSubdivision) (resumeReferencesBox.getChildren().get(i + 2))).getChildren().get(7))).getChildren().get(0)).setOnAction(new DeleteReferenceAction(i + 1));
                }
                for (int ii = 0; ii < 6; ii++) {
                    referencesFields.get((6 * i) + ii).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getReferences().get(i).get(ii));
                }
            }
            addReferenceBtn.setVisible(true);
            addReferenceBtn.setManaged(true);
        }
    }

    private class AddReferenceAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            int newNumb = controller.getCurrentUser().getResume().getNumbReferences() + 1;
            VP_PageSubdivision referenceDiv = new VP_PageSubdivision("REFERENCE #" + newNumb, false);
            VP_FieldLabel label0 = new VP_FieldLabel("first name:", 130),
                    label1 = new VP_FieldLabel("*middle name:", 130),
                    label2 = new VP_FieldLabel("last name:", 130),
                    label3 = new VP_FieldLabel("company or\ninstitution:", 130),
                    label4 = new VP_FieldLabel("phone:", 130),
                    label5 = new VP_FieldLabel("*email:", 130);
            referencesFields.add(new VP_TextField(32, 45));
            referencesFields.add(new VP_TextField(32, 45));
            referencesFields.add(new VP_TextField(32, 45));
            referencesFields.add(new VP_TextField(32, 128));
            referencesFields.add(new VP_TextField(13, 13));
            referencesFields.add(new VP_TextField(32, 254));
            VP_Button delBtn = new VP_Button("Delete", new DeleteReferenceAction(newNumb));
            VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{label0, referencesFields.get(0 + (6 * controller.getCurrentUser().getResume().getNumbReferences()))}),
                    line1 = new VP_DivisionLine(new Node[]{label1, referencesFields.get(1 + (6 * controller.getCurrentUser().getResume().getNumbReferences()))}),
                    line2 = new VP_DivisionLine(new Node[]{label2, referencesFields.get(2 + (6 * controller.getCurrentUser().getResume().getNumbReferences()))}),
                    line3 = new VP_DivisionLine(new Node[]{label3, referencesFields.get(3 + (6 * controller.getCurrentUser().getResume().getNumbReferences()))}),
                    line4 = new VP_DivisionLine(new Node[]{label4, referencesFields.get(4 + (6 * controller.getCurrentUser().getResume().getNumbReferences()))}),
                    line5 = new VP_DivisionLine(new Node[]{label5, referencesFields.get(5 + (6 * controller.getCurrentUser().getResume().getNumbReferences()))}),
                    delline = new VP_DivisionLine(new Node[]{delBtn});
            referenceDiv.getChildren().addAll(line0, line1, line2, line3, line4, line5, delline);
            resumeReferencesBox.getChildren().add(resumeReferencesBox.getChildren().size() - 3, referenceDiv);
            int ii = 0;
            for (int i = 6 * controller.getCurrentUser().getResume().getNumbReferences(); i < referencesFields.size(); i++) {
                referencesFields.get(i).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getReferences().get(controller.getCurrentUser().getResume().getNumbReferences()).get(ii));
                ii += 1;
            }
            controller.getCurrentUser().getResume().setNumbReferences(newNumb);
            if (controller.getCurrentUser().getResume().getNumbReferences() == 9) {
                addReferenceBtn.setVisible(false);
                addReferenceBtn.setManaged(false);
            }
        }
    }

    private class SubmitReferencesAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            boolean hasError = false;
            String phoneRegex = "\\([0-9]{3}\\)[0-9]{3}\\-[0-9]{4}$";
            Pattern phonePattern = Pattern.compile(phoneRegex);
            Matcher matcher;
            VP_Sounds.play(0);
            for (int i = 0; i < referencesFields.size(); i++) {
                referencesFields.get(i).textProperty().setValue(referencesFields.get(i).textProperty().getValueSafe().trim());
            }
            boolean endReached = false;
            for (int ii = 0; ii < controller.getCurrentUser().getResume().getNumbReferences(); ii++) {
                int count = 0, optionalCount = 0;
                for (int i = 0 + (ii * 6); i < 6 + (ii * 6); i++) {
                    if (!referencesFields.get(i).textProperty().getValueSafe().equals("")) {
                        if (i % 6 == 1 || i % 6 == 5) {
                            optionalCount += 1;
                            if (i % 6 == 5) {
                                hasError = (!controller.getDataM().checkEmail(referencesFields.get(i).textProperty().getValueSafe()));
                                if (hasError) {
                                    referencesFields.get(i).showInvalid();
                                    referencesError.setParaText("Email is not in valid form in reference #" + (ii + 1) + ".");
                                }
                            }
                        } else {
                            count += 1;
                            if (i % 6 == 4) {
                                matcher = phonePattern.matcher(referencesFields.get(i).textProperty().getValueSafe());
                                if (!matcher.matches()) {
                                    hasError = true;
                                    referencesFields.get(i).showInvalid();
                                    referencesError.setParaText("Phone numbers must be in form "
                                            + "(xxx)xxx-xxxx for reference #" + (ii + 1) + ".");
                                    break;
                                }
                            }
                        }
                    }
                }
                if (!hasError) {
                    if (count == 0 && optionalCount == 0 && !endReached) {
                        endReached = true;
                    } else if (endReached && (count > 0 || optionalCount > 0)) {
                        referencesError.setParaText("You cannot have blank references in between entries. See reference #" + (ii));
                        hasError = true;
                        break;
                    } else if ((count < 4 && count > 0) || (count == 0 && optionalCount > 0)) {
                        referencesError.setParaText("Not all mandatory fields for a reference are complete for entry #" + (ii + 1));
                        hasError = true;
                        break;
                    }
                } else {
                    break;
                }
            }
            if (hasError) {
                VP_Sounds.play(-1);
                referencesErrorLine.show();
            } else {
                referencesError.setParaText("");
                referencesErrorLine.hide();
                controller.getCurrentUser().getResume().save();
                if (controller.getCurrentUser().getResume().hasChanges()) {
                    updateDynamicFields();
                    showScreen(11, 0);
                    try {
                        controller.getDataM().saveResume(9);
                    } catch (SQLException ex) {
                        controller.errorAlert(3120, ex.getMessage());
                    } catch (TransformerException | ParserConfigurationException | IOException | DocumentException ex) {
                        controller.errorAlert(3303, ex.getMessage());
                    }
                } else {
                    showScreen(11, 0);
                }
            }
        }
    }

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
                    showScreen(11, 0);
                    try {
                        controller.getDataM().saveResume(0);
                    } catch (SQLException ex) {
                        controller.errorAlert(3117, ex.getMessage());
                    } catch (TransformerException | ParserConfigurationException | IOException | DocumentException ex) {
                        controller.errorAlert(3303, ex.getMessage());
                    }
                } else {
                    showScreen(11, 0);
                }
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
                controller.errorAlert(3116, ex.getMessage());
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
                    showScreen(3, 0);
                    try {
                        controller.getDataM().saveBCardData();
                    } catch (SQLException ex) {
                        controller.errorAlert(3114, ex.getMessage());
                    } catch (TransformerException | ParserConfigurationException | IOException | DocumentException ex) {
                        controller.errorAlert(3301, ex.getMessage());
                    }
                } else {
                    showScreen(3, 0);
                }
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
                showScreen(3, 0);
                try {
                    controller.getDataM().saveUserData();
                    controller.getCurrentUser().getCovlet().save();
                    controller.getCurrentUser().getResume().save();
                    controller.getCurrentUser().getBcard().save();
                } catch (SQLException ex) {
                    controller.errorAlert(3113, ex.getMessage());
                }
            }
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass WizardMainAction
     * - Brings the user to one of the main wizard pages
     *------------------------------------------------------------------------*/
    protected class WizardMainAction implements EventHandler<ActionEvent> {

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
                    controller.errorAlert(3107, ex.getMessage());
                } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                    controller.errorAlert(3002, ex.getMessage());
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
                controller.errorAlert(3110, ex.getMessage());
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                controller.errorAlert(3005, ex.getMessage());
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
                controller.errorAlert(3108, ex.getMessage());
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                controller.errorAlert(3003, ex.getMessage());
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
                    controller.errorAlert(3111, ex.getMessage());
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
                    controller.errorAlert(3109, ex.getMessage());
                } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                    controller.errorAlert(3004, ex.getMessage());
                }
            }
        }
    }
    
    private class ChangePassAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            //-------- Initialization Start ----------\\
            String[] cred = {oldPass.getText(), newPass.getText(), newPassConfirm.getText()};
            //-------- Initialization End ------------\\
            VP_Sounds.play(0);
            changePassError.setText("");
            changePassErrorLine.hide();
            if (cred[0].length() < controller.getUSER_PASSWORD_MINIMUM()) {
                oldPass.showInvalid();
                changePassError.setText("The old password is incorrect. Please try again.");
                changePassErrorLine.show();
                VP_Sounds.play(-1);
            } else if (cred[1].length() < controller.getUSER_PASSWORD_MINIMUM())
                    {
                newPass.showInvalid();
                newPassConfirm.showInvalid();
                changePassError.setText("The new password is not long enough. Please try again.");
                changePassErrorLine.show();
                VP_Sounds.play(-1);
            } else {
                try {
                    if (controller.getDataM().checkCurrentPassword(cred[0])) {
                        if (cred[1].equals(cred[2])) {
                            controller.getDataM().changePass(cred[1]);
                            // showandwait an alert then switch the screen
                            VP_Dialog passChanged = new VP_Dialog("PASSWORD CHANGED");
                            passChanged.setHeaderText("Your password has been changed successfully.");
                            passChanged.getDialogShell().add(new Label("Use your new password the next time you log in."), 0, 0);
                            passChanged.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
                            passChanged.showAndWait();
                            cancelActionFunction();
                            showScreen(3, 0);
                        } else {
                            newPass.showInvalid();
                            newPassConfirm.showInvalid();
                            changePassError.setText("The new passwords do not match each other.");
                            changePassErrorLine.show();
                            VP_Sounds.play(-1);
                        }
                    } else {
                        oldPass.showInvalid();
                        changePassError.setText("The old password is incorrect. Please try again.");
                        changePassErrorLine.show();
                        VP_Sounds.play(-1);
                    }
                } catch (SQLException ex) {
                    controller.errorAlert(3127, ex.getMessage());
                } catch (NoSuchAlgorithmException |UnsupportedEncodingException ex) {
                    controller.errorAlert(3007, ex.getMessage());
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
                        controller.errorAlert(3112, ex.getMessage());
                    } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                        controller.errorAlert(3006, ex.getMessage());
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
