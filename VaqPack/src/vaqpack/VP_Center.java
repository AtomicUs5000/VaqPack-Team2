/**
 * VP_Center.java - Everything involving the center view of the GUI (wizard).
 * FILE ID 1200
 */
package vaqpack;

import vaqpack.peripherals.VP_Sounds;
import vaqpack.user.VP_User;
import vaqpack.user.VP_Resume;
import vaqpack.components.*;
import com.lowagie.text.DocumentException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import vaqpack.peripherals.VP_Previewer;
import vaqpack.user.VP_Contact;
import vaqpack.user.VP_Employer;
import vaqpack.user.VP_Theme;

/**
 * The center is the center of the main BorderPane layout. The center extends
 * StackPane, where each layer of the StackPane corresponds to a wizard page.
 * Some wizard pages may only be accessible under certain conditions.
 *
 * @author William Dewald (Project Manager, Team-02)
 * @author Fernando Bazan
 * @author Erik Lopez
 * @author Raul Saavedra
 * @author Nathanael Carr
 * @version 1.0
 * @since 1.0
 */
public class VP_Center extends StackPane {

    private final VP_GUIController controller;
    private final VP_TextField loginEmail, resetEmail, regLoginAccess,
            resetCode, registerEmail, newMysqlURL, newMysqlPort;
    private final VP_SimpleProgress infoProgress, resumeProgress, bcardProgress,
            covletProgress, resObjProgress, resEduProgress, resExpProgress,
            resQualProgress, resHighProgress, resLangProgress, resSWProgress;
    private final Label resetPassStrengthLabel, registerPassStrengthLabel,
            changePassStrengthLabel;
    private final VP_PasswordField loginPass,
            resetNewPass, resetNewPassConfirm, registerPass, registerPassConfirm,
            oldPass, newPass, newPassConfirm, adminPassMysql;
    private final VP_DivisionLine accessInstructionsLine, loginButtonLine,
            accessLine, resetInstructions1Line, resetInstructions2Line,
            resetNewPassLine, resetNewPassConfirmLine, resetCodeLine, resetButLine,
            resetPassStrengthLine, selectCoverLetterLine, addParagraphLine;
    private final VP_ErrorLine loginErrorLine, resetErrorLine, registerErrorLine,
            bcardErrorLine, covletEditErrorLine, objectiveErrorLine, educationErrorLine,
            experienceErrorLine, achievementsErrorLine, communityErrorLine,
            qualificationsErrorLine, highlightsErrorLine, languagesErrorLine,
            softwareErrorLine, referencesErrorLine, changePassErrorLine, 
            addContactErrorLine, sendAttachErrorLine, changeMysqlErrorLine, 
            personalInfoErrorLine, addEmployerErrorLine;
    private final VP_Paragraph accessInstructions, resetInstructions1, resetInstructions2,
            overviewInfo, coverLetterDetails;
    private final VP_Button submitResetBtn, startNewBtn, addEducationBtn, addExperienceBtn,
            addAchievementBtn, addCommunityBtn, addQualificationBtn, addHighlightBtn,
            addLanguageBtn, addSoftwareBtn, addReferenceBtn, printResBtn, printCLBtn,
            printBCBtn, deleteCovletBtn;
    private final ComboBox coverLetterSelect;
    private final ArrayList<VP_Button> wizardMainButtons;
    private final ArrayList<VP_PageSubdivision> bcNodes, clNodes;
    private final ArrayList<Node> coverLetterEditFields;
    private final ArrayList<VP_TextField> educationFields, experienceFields, achievementsFields,
            communityFields, qualificationsFields, highlightsFields, languagesFields,
            softwareFields, referencesFields;
    private final VP_FieldLabel dateValueLabel;
    private final VP_PageSubdivision dynamicBody;
    private final VP_PageDivision covLetEditBox, resumeEducationBox, resumeExperienceBox,
            resumeAchievementsBox, resumeCommunityBox, resumeQualificationsBox,
            resumeHighlightsBox, resumeLanguagesBox, resumeSoftwareBox, resumeReferencesBox;
    private final CheckBox resHTMLcb, resPDFcb, clHTMLcb, clPDFcb, bcHTMLcb, bcPDFcb;
    private final VBox resLeftContainer, bcLeftContainer, clLeftContainer;
    private final ToggleGroup groupRes, groupBC, groupCL;

    /**
     * Constructor. Initializes all of the components that require a reference
     * to them in the future for dynamic page handling.
     *
     * @param controller Stores the GUI controller for convenience in accessing
     * controller functions or classes accessed by the controller.
     * @since 1.0
     */
    protected VP_Center(VP_GUIController controller) {
        //-------- Initialization Start ----------\\
        this.controller = controller;
        loginButtonLine = new VP_DivisionLine();
        accessInstructionsLine = new VP_DivisionLine();
        resetInstructions1Line = new VP_DivisionLine();
        resetInstructions2Line = new VP_DivisionLine();
        resetNewPassLine = new VP_DivisionLine();
        resetNewPassConfirmLine = new VP_DivisionLine();
        resetCodeLine = new VP_DivisionLine();
        accessLine = new VP_DivisionLine();
        resetButLine = new VP_DivisionLine();
        resetPassStrengthLine = new VP_DivisionLine();
        addParagraphLine = new VP_DivisionLine();
        selectCoverLetterLine = new VP_DivisionLine();
        loginErrorLine = new VP_ErrorLine();
        resetErrorLine = new VP_ErrorLine();
        registerErrorLine = new VP_ErrorLine();
        personalInfoErrorLine = new VP_ErrorLine();
        bcardErrorLine = new VP_ErrorLine();
        covletEditErrorLine = new VP_ErrorLine();
        objectiveErrorLine = new VP_ErrorLine();
        educationErrorLine = new VP_ErrorLine();
        experienceErrorLine = new VP_ErrorLine();
        achievementsErrorLine = new VP_ErrorLine();
        communityErrorLine = new VP_ErrorLine();
        qualificationsErrorLine = new VP_ErrorLine();
        highlightsErrorLine = new VP_ErrorLine();
        languagesErrorLine = new VP_ErrorLine();
        softwareErrorLine = new VP_ErrorLine();
        referencesErrorLine = new VP_ErrorLine();
        changePassErrorLine = new VP_ErrorLine();
        addContactErrorLine = new VP_ErrorLine();
        sendAttachErrorLine = new VP_ErrorLine();
        changeMysqlErrorLine = new VP_ErrorLine();
        addEmployerErrorLine = new VP_ErrorLine();
        coverLetterDetails = new VP_Paragraph();
        accessInstructions = new VP_Paragraph();
        resetInstructions1 = new VP_Paragraph();
        resetInstructions2 = new VP_Paragraph();
        overviewInfo = new VP_Paragraph();
        resetPassStrengthLabel = new Label();
        registerPassStrengthLabel = new Label();
        changePassStrengthLabel = new Label();
        infoProgress = new VP_SimpleProgress();
        resumeProgress = new VP_SimpleProgress();
        bcardProgress = new VP_SimpleProgress();
        covletProgress = new VP_SimpleProgress();
        resObjProgress = new VP_SimpleProgress();
        resEduProgress = new VP_SimpleProgress();
        resExpProgress = new VP_SimpleProgress();
        resQualProgress = new VP_SimpleProgress();
        resHighProgress = new VP_SimpleProgress();
        resLangProgress = new VP_SimpleProgress();
        resSWProgress = new VP_SimpleProgress();
        regLoginAccess = new VP_TextField(16, 16);
        resetCode = new VP_TextField(16, 16);
        loginEmail = new VP_TextField(32, 254);
        resetEmail = new VP_TextField(32, 254);
        registerEmail = new VP_TextField(32, 254);
        newMysqlURL = new VP_TextField(50, 0);
        newMysqlPort = new VP_TextField(5, 5);
        adminPassMysql = new VP_PasswordField(32, 32, 0, null);
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
        printResBtn = new VP_Button("Print Resume", new PrintAction(2));
        printCLBtn = new VP_Button("Print Cover Letter", new PrintAction(6));
        printBCBtn = new VP_Button("Print Business Card", new PrintAction(4));
        deleteCovletBtn = new VP_Button("Delete", new DeleteCovletAction());
        covLetEditBox = new VP_PageDivision("EDIT COVER LETTER");
        resumeEducationBox = new VP_PageDivision("RESUME -- EDUCATION");
        resumeExperienceBox = new VP_PageDivision("RESUME -- WORK EXPERIENCE");
        resumeAchievementsBox = new VP_PageDivision("RESUME -- AWARDS AND ACHIEVEMENTS");
        resumeCommunityBox = new VP_PageDivision("RESUME -- COMMUNITY");
        resumeQualificationsBox = new VP_PageDivision("RESUME -- QUALIFICATIONS");
        resumeHighlightsBox = new VP_PageDivision("RESUME -- HIGHLIGHTS");
        resumeLanguagesBox = new VP_PageDivision("RESUME -- LANGUAGES");
        resumeSoftwareBox = new VP_PageDivision("RESUME -- SOFTWARE");
        resumeReferencesBox = new VP_PageDivision("RESUME -- REFERENCES");
        resHTMLcb = new CheckBox("Resume (HTML File)");
        resPDFcb = new CheckBox("Resume (PDF File)");
        clHTMLcb = new CheckBox("Cover Letter (HTML File)");
        clPDFcb = new CheckBox("Cover Letter (PDF File)");
        bcHTMLcb = new CheckBox("Business Card (HTML File)");
        bcPDFcb = new CheckBox("Business Card (PDF File)");
        resLeftContainer = new VBox();
        bcLeftContainer = new VBox();
        clLeftContainer = new VBox();
        groupRes = new ToggleGroup();
        groupBC = new ToggleGroup();
        groupCL  = new ToggleGroup();
        //-------- Initialization End ------------\\
    }

    /**
     * Builds the GUI center which holds most of the content. Called in a task,
     * to build in the background. The center extends StackPane, where each
     * layer is an individual page.
     * <ul>
     * <li> Layer 0 = Login Screen </li>
     * <li> Layer 1 = Reset Password Screen </li>
     * <li> Layer 2 = Registration Screen </li>
     * <li> Layer 3 = Overview Screen </li>
     * <li> Layer 4 = Personal Information Screen </li>
     * <li> Layer 5 = Business Card Screen </li>
     * <li> Layer 6 = Cover Letters List Screen </li>
     * <li> Layer 7 = Cover Letter Edit Screen </li>
     * <li> Layer 8 = Themes List Screen Screen </li>
     * <li> Layer 9 = Custom Theme Screen </li>
     * <li> Layer 10 = Distribute Screen </li>
     * <li> Layer 11 = Resume Status Screen </li>
     * <li> Layer 12 = Resume Objective Screen </li>
     * <li> Layer 13 = Resume Education Screen </li>
     * <li> Layer 14 = Resume Experience Screen </li>
     * <li> Layer 15 = Resume Achievements Screen </li>
     * <li> Layer 16 = Resume Community Screen </li>
     * <li> Layer 17 = Resume Qualifications Screen </li>
     * <li> Layer 18 = Resume Highlights Screen </li>
     * <li> Layer 19 = Resume Languages Screen </li>
     * <li> Layer 20 = Resume Software Screen </li>
     * <li> Layer 21 = Resume References Screen </li>
     * <li> Layer 22 = Change Password Screen </li>
     * </ul>
     *
     * @since 1.0
     */
    protected void build() {
        setId("center");
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
                buildChangePasswordScreen(), //.......screen 22
                buildChangeMysqlScreen(), //..........screen 23
                buildPrintScreen(), //................screen 24
                buildEmployersScreen() //.............screen 25
        );
        showScreen(0, 0);
    }

    /**
     * Makes all center StackPane levels invisible except for the desired one.
     *
     * @param screenNumber The desired StackPane level to show.
     * @param position The scroll position on the selected screen.
     * @since 1.0
     */
    protected void showScreen(int screenNumber, double position) {
        controller.setChanges(false);
        for (int i = 0; i < getChildren().size(); i++) {
            getChildren().get(i).setVisible(false);
        }
        TreeView thisTView = (TreeView)controller.getLeftTree().getChildren().get(0);
        if (screenNumber == 3) {
            thisTView.getSelectionModel().clearAndSelect(0);
            updateOverview();
        } else if (screenNumber == 4) {
            thisTView.getSelectionModel().clearAndSelect(1);
        } else if (screenNumber == 5) {
            thisTView.getSelectionModel().clearAndSelect(3);
        } else if (screenNumber == 6) {
            thisTView.getSelectionModel().clearAndSelect(4);
        } else if (screenNumber == 8) {
            thisTView.getSelectionModel().clearAndSelect(5);
        } else if (screenNumber == 10) {
            thisTView.getSelectionModel().clearAndSelect(6);
            updateSelectableDocs();
        } else if (screenNumber == 11) {
            thisTView.getSelectionModel().clearAndSelect(2);
            updateResumeStatus();
        }
        getChildren().get(screenNumber).setVisible(true);
        ((VP_Screen) (getChildren().get(screenNumber))).setVvalue(position);
    }
    
    /**
     * Opens a confirmation dialog, asking the user if it is desired to remain on the 
     * page to save changes or leave the page;
     * 
     * @return Whether or not the user wants to remain on the page.
     * @since 1.0
     */
    protected boolean confirmLeavePage() {
        //-------- Initialization Start ----------\\
        boolean staying = false;
        Optional result;
        VP_Dialog changesDialog = new VP_Dialog("Save Changes?");
        Label changesLabel = new Label("Would you like to stay on the page to submit "
                + "changes or continue with the desired action?");
        ButtonType saveBtn = new ButtonType("Stay Here", ButtonBar.ButtonData.YES),
                continueBtn = new ButtonType("Continue", ButtonBar.ButtonData.NO);
        //-------- Initialization End ------------\\
        
        changesDialog.setHeaderText("You have not submitted your changes.");
        changesLabel.setPadding(new Insets(50, 20, 50, 20));
        changesDialog.getDialogShell().add(changesLabel, 0, 0);
        changesDialog.getDialogPane().getButtonTypes().addAll(saveBtn, continueBtn);
        result = changesDialog.showAndWait();
        if (result.get() == saveBtn) {
            staying = true;
        }
        return staying;
    }

    /**
     * Creates the user login screen. Called by buildCenter(). A.K.A Screen 0
     *
     * @return A VP_Screen that gets applied to a center StackPane level.
     * @since 1.0
     */
    private VP_Screen buildLoginScreen() {
        //-------- Initialization Start ----------\\
        VP_Screen screen = new VP_Screen("LOGIN");
        VP_Paragraph loginNotes = new VP_Paragraph("Enter your login information below to begin using VaqPack.");
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

        passForgotLabel.getStyleClass().add("clickable");
        passForgotLabel.setOnMouseClicked(new ForgotPassAction());
        needAccountLabel.getStyleClass().add("clickable");
        needAccountLabel.setOnMouseClicked(new NeedAccountAction());
        loginButtonLine.getChildren().addAll(loginBtn, needAccountLabel, passForgotLabel);
        accessInstructionsLine.getChildren().addAll(accessInstructions);
        accessLine.getChildren().addAll(regCodeLabel, regLoginAccess,
                enterAccessBtn, accessCancelBtn, accessResendBtn);
        screen.addNodes(new Node[]{loginNotes, emailLine, passLine, loginErrorLine,
                loginButtonLine, accessInstructionsLine, accessLine});
        resetLoginRegForms();
        return screen;
    }

    /**
     * Creates the reset password screen. Called by buildCenter(). A.K.A Screen
     * 1
     *
     * @return A VP_Screen that gets applied to a center StackPane level.
     * @since 1.0
     */
    private VP_Screen buildResetPasswordScreen() {
        //-------- Initialization Start ----------\\
        VP_Screen screen = new VP_Screen("RESET PASSWORD");
        VP_FieldLabel resetNewPassLabel = new VP_FieldLabel("new password:", 100),
                resetNewPassConfirmLabel = new VP_FieldLabel("confirm\nnew password:", 100),
                resetCodeLabel = new VP_FieldLabel("code:", 100);
        VP_Button submitResetPassCodeBtn = new VP_Button("Submit", new SubmitResetPassCode()),
                cancelResetBtn2 = new VP_Button("Cancel", new CancelActionPrelogin());
        VP_DivisionLine emailLine = new VP_DivisionLine(new Node[]{ new VP_FieldLabel("email:", 100), 
            resetEmail, submitResetBtn, new VP_Button("Cancel", new CancelActionPrelogin())}),
                passStrengthLine = new VP_DivisionLine(new Node[]{resetPassStrengthLabel});
        //-------- Initialization End ------------\\

        resetInstructions1Line.getChildren().addAll(resetInstructions1);
        resetInstructions2Line.getChildren().addAll(resetInstructions2);
        resetNewPassLine.getChildren().addAll(resetNewPassLabel, resetNewPass);
        resetPassStrengthLabel.getStyleClass().add("inputLabel");
        resetPassStrengthLine.getChildren().addAll(resetPassStrengthLabel);
        resetNewPassConfirmLine.getChildren().addAll(resetNewPassConfirmLabel,
                resetNewPassConfirm);
        resetCodeLine.getChildren().addAll(resetCodeLabel, resetCode);
        resetButLine.getChildren().addAll(submitResetPassCodeBtn, cancelResetBtn2);
        screen.addNodes(new Node[]{resetInstructions1Line,
                emailLine, resetErrorLine, resetInstructions2Line, resetNewPassLine,
                passStrengthLine, resetNewPassConfirmLine, resetCodeLine, resetButLine});
        resetResetPasswordForms();
        return screen;
    }

    /**
     * Creates the user registration screen. Called by buildCenter(). A.K.A
     * Screen 2
     *
     * @return A VP_Screen that gets applied to a center StackPane level.
     * @since 1.0
     */
    private VP_Screen buildRegistrationScreen() {
        //-------- Initialization Start ----------\\
        VP_Screen screen = new VP_Screen("REGISTER NEW ACCOUNT");
        VP_DivisionLine emailLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("email:", 100), registerEmail}),
                passLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("password:", 100), registerPass}),
                registerPassStrengthLine = new VP_DivisionLine(new Node[]{registerPassStrengthLabel}),
                registerConfirmLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("confirm\npassword:", 100), registerPassConfirm}),
                registerInstructionsLine = new VP_DivisionLine(new Node[]{new VP_Paragraph(""
                        + "Enter your email and password twice. When you submit, an email will be sent to you with a "
                        + "registration access code. Login with the email and password you provided and you will be "
                        + "prompted to enter in the access code. Access codes expire within one hour.")}),
                registerButtonLine = new VP_DivisionLine(new Node[]{new VP_Button("Register", new RegisterSubmitAction()), 
                    new VP_Button("Cancel", new CancelActionPrelogin())});
        //-------- Initialization End ------------\\

        registerPassStrengthLabel.getStyleClass().add("inputLabel");
        screen.addNodes(new Node[]{registerInstructionsLine, emailLine, passLine, registerPassStrengthLine,
            registerConfirmLine, registerErrorLine, registerButtonLine});
        resetRegisterForms();
        return screen;
    }

    /**
     * Builds the Overview screen which provides access to the main
     * functionality of the program and lists the completion status of some of
     * these functions for the logged in user. A.K.A Screen 3
     *
     * @return A VP_Screen that gets applied to a center StackPane level.
     * @since 1.0
     */
    private VP_Screen buildOverviewScreen() {
        //-------- Initialization Start ----------\\
        VP_Screen screen = new VP_Screen("OVERVIEW");
        VP_Button updateInfoBtn = new VP_Button("Update Personal Information", new WizardMainAction(4)),
                updateResumeBtn = new VP_Button("Update Your Resume", new WizardMainAction(11)),
                updateBcardBtn = new VP_Button("Update Your Business Card", new WizardMainAction(5)),
                updateCovLetBtn = new VP_Button("Update Your Cover Letters", new WizardMainAction(6)),
                applyThemesBtn = new VP_Button("Apply Themes to Your Docs", new WizardMainAction(8)),
                distributeBtn = new VP_Button("Distribute Your Docs", new WizardMainAction(10)),
                printDocsBtn = new VP_Button("Print Your Docs", new WizardMainAction(24));
        VP_PageSubdivision tasks = new VP_PageSubdivision("TASKS", true);
        VP_PageSubdivision step1 = new VP_PageSubdivision("STEP ONE", false),
                step2 = new VP_PageSubdivision("STEP TWO", false),
                step3 = new VP_PageSubdivision("STEP THREE", false),
                step4 = new VP_PageSubdivision("STEP FOUR", false);
        VP_DivisionLine step1Line = new VP_DivisionLine(new Node[]{updateInfoBtn, infoProgress}, 20),
                step2aLine = new VP_DivisionLine(new Node[]{updateResumeBtn, resumeProgress}, 20),
                step2bLine = new VP_DivisionLine(new Node[]{updateBcardBtn, bcardProgress}, 20),
                step2cLine = new VP_DivisionLine(new Node[]{updateCovLetBtn, covletProgress}, 20),
                step3Line = new VP_DivisionLine(new Node[]{applyThemesBtn}, 20),
                step4aLine = new VP_DivisionLine(new Node[]{distributeBtn}, 20),
                step4bLine = new VP_DivisionLine(new Node[]{printDocsBtn}, 20);
        
        //-------- Initialization End ------------\\

        wizardMainButtons.add(updateInfoBtn);
        wizardMainButtons.add(updateResumeBtn);
        wizardMainButtons.add(updateBcardBtn);
        wizardMainButtons.add(updateCovLetBtn);
        wizardMainButtons.add(applyThemesBtn);
        wizardMainButtons.add(distributeBtn);
        wizardMainButtons.add(printDocsBtn);
        for (int i = 0; i < wizardMainButtons.size(); i++) {
            wizardMainButtons.get(i).setPrefWidth(200);
        }
        step1.getChildren().addAll(step1Line);
        step2.getChildren().addAll(step2aLine, step2bLine, step2cLine);
        step3.getChildren().addAll(step3Line);
        step4.getChildren().addAll(step4aLine, step4bLine);
        tasks.getChildren().addAll(step1, step2, step3, step4);
        screen.addNodes(new Node[]{overviewInfo, tasks});
        return screen;
    }

    /**
     * Builds the screen where the user inputs personal information. A.K.A
     * Screen 4
     *
     * @return A VP_Screen that gets applied to a center StackPane level.
     * @since 1.0
     */
    private VP_Screen buildPersonalInfoScreen() {
        //-------- Initialization Start ----------\\
        VP_Screen screen = new VP_Screen("PERSONAL INFO", "personal-icon-larger.png", 160);
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
        personalInfoFields.add(new VP_TextField(32, 45, controller));
        personalInfoFields.add(new VP_TextField(32, 45, controller));
        personalInfoFields.add(new VP_TextField(32, 45, controller));
        personalInfoFields.add(new VP_TextField(32, 254, controller));
        personalInfoFields.add(new VP_TextField(32, 254, controller));
        personalInfoFields.add(new VP_TextField(32, 45, controller));
        personalInfoFields.add(new VP_TextField(2, 2, controller));
        personalInfoFields.add(new VP_TextField(10, 10, controller));
        personalInfoFields.add(new VP_TextField(13, 13, controller));
        personalInfoFields.add(new VP_TextField(13, 13, controller));
        personalInfoFields.add(new VP_TextField(32, 254, controller));
        VP_Paragraph notes = new VP_Paragraph("(*) denotes an optional field. "
                + "Leave email blank to use your VaqPack account login email address.");
        VP_Button submitBtn = new VP_Button("Submit", new SubmitPersonalInfoAction(personalInfoFields)),
                cancelBtn = new VP_Button("Cancel", new CancelAction(4));
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
        name.getChildren().addAll(firstNameLine, middleNameLine, lastNameLine);
        address.getChildren().addAll(address1Line, address2Line, cityLine, stateLine, zipLine);
        communication.getChildren().addAll(phoneLine, cellLine, emailLine);
        screen.addNodes(new Node[]{name, address, communication, notesLine, personalInfoErrorLine, buttonsLine});
        return screen;
    }

    /**
     * Builds the screen where the user edits the business card. A.K.A Screen 5
     *
     * @return A VP_Screen that gets applied to a center StackPane level.
     * @since 1.0
     */
    private VP_Screen buildBusinessCardScreen() {
        //-------- Initialization Start ----------\\
        VP_Screen screen = new VP_Screen("BUSINESS CARD");
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
        businessCardFields.add(new VP_TextField(32, 48, controller));   // bind this to card
        VP_Paragraph notes = new VP_Paragraph("(*) denotes an optional field. "
                + "Locked fields can be edited by updating your personal info.");
        VP_Button submitBtn = new VP_Button("Submit", new SubmitBCardAction(businessCardFields)),
                cancelBtn = new VP_Button("Cancel", new CancelAction());
        VP_DivisionLine firstNameLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("first name:", 110), businessCardFields.get(0)}),
                middleNameLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("*middle name:", 110), businessCardFields.get(1)}),
                lastNameLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("last name:", 110), businessCardFields.get(2)}),
                professionLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("profession:", 110), businessCardFields.get(3)}),
                companyNameLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("*company name:", 110), businessCardFields.get(4)}),
                companySloganLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("*company slogan:", 110), businessCardFields.get(5)}),
                address1Line = new VP_DivisionLine(new Node[]{new VP_FieldLabel("address line 1:", 110), businessCardFields.get(6)}),
                address2Line = new VP_DivisionLine(new Node[]{new VP_FieldLabel("*address line 2:", 110), businessCardFields.get(7)}),
                cityLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("city:", 110), businessCardFields.get(8)}),
                stateLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("state:", 110), businessCardFields.get(9)}),
                zipLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("zipcode:", 110), businessCardFields.get(10)}),
                phoneLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("phone:", 110), businessCardFields.get(11)}),
                cellLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("*cell:", 110), businessCardFields.get(12)}),
                emailLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("email:", 110), businessCardFields.get(13)}),
                webpageLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("*web page:", 110), businessCardFields.get(14)}),
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
        screen.addNodes(new Node[]{name, company, address, communication,
                notesLine, bcardErrorLine, buttonsLine});
        return screen;
    }

    /**
     * Builds the screen where the user sees the list of existing cover letters
     * or begins a new one. A.K.A Screen 6
     *
     * @return A VP_Screen that gets applied to a center StackPane level.
     * @since 1.0
     */
    private VP_Screen buildCoverLettersStartScreen() {
        //-------- Initialization Start ----------\\
        VP_Screen screen = new VP_Screen("COVER LETTERS");
        VP_Button selectCoverLetterButton = new VP_Button("Load", new LoadCoverLetterAction());
        VP_DivisionLine buttonLine = new VP_DivisionLine(new Node[]{startNewBtn, new VP_Button("Cancel", new CancelAction())});
        //-------- Initialization End ------------\\
        coverLetterDetails.setParaText("You currently do not have any cover letters started. "
                + "Start a new cover letter by clicking the 'Start New Cover Letter' button below.");
        selectCoverLetterLine.setPadding(new Insets(30, 0, 30, 100));
        selectCoverLetterLine.getChildren().addAll(coverLetterSelect, selectCoverLetterButton, deleteCovletBtn);
        selectCoverLetterLine.hide();
        screen.addNodes(new Node[]{coverLetterDetails, selectCoverLetterLine, buttonLine});
        return screen;
    }

    /**
     * Builds the screen where the user edits a selected cover letter. A.K.A
     * Screen 7
     *
     * @return A VP_Screen that gets applied to a center StackPane level.
     * @since 1.0
     */
    private VP_Screen buildCoverLettersEditScreen() {
        //-------- Initialization Start ----------\\
        VP_Screen screen = new VP_Screen(covLetEditBox);
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
        coverLetterEditFields.add(new VP_TextField(32, 128, controller));  // bind this to cover letter
        coverLetterEditFields.add(new VP_TextField(32, 128, controller));  // bind this to cover letter
        coverLetterEditFields.add(new VP_TextField(32, 128, controller));  // bind this to cover letter
        coverLetterEditFields.add(new VP_TextField(32, 45, controller));   // bind this to cover letter
        coverLetterEditFields.add(new VP_TextField(32, 45, controller));   // bind this to cover letter
        coverLetterEditFields.add(new VP_TextField(32, 45, controller));   // bind this to cover letter
        coverLetterEditFields.add(new VP_TextField(32, 48, controller));   // bind this tocover letter
        coverLetterEditFields.add(new VP_TextField(32, 48, controller));   // bind this to cover letter
        coverLetterEditFields.add(new VP_TextField(32, 254, controller));  // bind this to cover letter
        coverLetterEditFields.add(new VP_TextField(32, 254, controller));  // bind this to cover letter
        coverLetterEditFields.add(new VP_TextField(32, 45, controller));   // bind this to cover letter
        coverLetterEditFields.add(new VP_TextField(2, 2, controller));     // bind this to cover letter
        coverLetterEditFields.add(new VP_TextField(10, 10, controller));   // bind this to cover letter
        coverLetterEditFields.add(new VP_TextField(32, 128, controller));  // bind this to cover letter
        coverLetterEditFields.add(new VP_TextArea(controller));          // bind this to cover letter
        coverLetterEditFields.add(new VP_TextField(32, 128, controller));  // bind this to cover letter
        coverLetterEditFields.add(new VP_TextField(32, 45));   // bind this to user
        coverLetterEditFields.add(new VP_TextField(32, 45));   // bind this to user
        coverLetterEditFields.add(new VP_TextField(32, 45));   // bind this to user
        VP_Paragraph notes = new VP_Paragraph("(*) denotes an optional field. "
                + "Locked fields can be edited by updating your personal info. You may create up to 9 paragraphs.");
        VP_PageSubdivision heading = new VP_PageSubdivision("HEADING", true),
                name = new VP_PageSubdivision("NAME", false),
                address = new VP_PageSubdivision("ADDRESS", false),
                communication = new VP_PageSubdivision("COMMUNICATION", false),
                dateDivision = new VP_PageSubdivision("AUTO-FILL OPTIONS", false),
                adref = new VP_PageSubdivision("AD REFERENCE", false),
                contact = new VP_PageSubdivision("EMPLOYER INFORMATION", true),
                contactName = new VP_PageSubdivision("EMPLOYER NAME", false),
                contactCompany = new VP_PageSubdivision("EMPLOYER COMPANY", false),
                contactAddress = new VP_PageSubdivision("EMPLOYER ADDRESS", false),
                salutation = new VP_PageSubdivision("", false),
                closing = new VP_PageSubdivision("", false),
                signature = new VP_PageSubdivision("SIGNATURE", true),
                sigName = new VP_PageSubdivision("NAME", false);
        VP_FieldLabel tableHeading = new VP_FieldLabel("Load Ad Reference and Employer Information from VaqPack Employer List:");
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
                loadDataBtn = new VP_Button("Load Employer Ad Data"),
                delPara1Btn = new VP_Button("Delete", new DeleteParagraphAction(1)),
                addParaBtn = new VP_Button("Add a New Paragraph", new AddParagraphAction());
        VP_DivisionLine firstNameLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("first name:", 140), coverLetterEditFields.get(0)}),
                middleNameLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("*middle name:", 140), coverLetterEditFields.get(1)}),
                lastNameLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("last name:", 140), coverLetterEditFields.get(2)}),
                address1Line = new VP_DivisionLine(new Node[]{new VP_FieldLabel("address line 1:", 140), coverLetterEditFields.get(3)}),
                address2Line = new VP_DivisionLine(new Node[]{new VP_FieldLabel("*address line 2:", 140), coverLetterEditFields.get(4)}),
                cityLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("city:", 140), coverLetterEditFields.get(5)}),
                stateLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("state:", 140), coverLetterEditFields.get(6)}),
                zipLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("zipcode:", 140), coverLetterEditFields.get(7)}),
                phoneLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("phone:", 140), coverLetterEditFields.get(8)}),
                cellLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("*cell:", 140), coverLetterEditFields.get(9)}),
                emailLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("email:", 140), coverLetterEditFields.get(10)}),
                dateLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("date:", 140), dateValueLabel, dateBtn}),
                adSourceLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("*job ad source:", 140), coverLetterEditFields.get(11)}),
                adJobLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("*job position:", 140), coverLetterEditFields.get(12)}),
                adRefLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("*ad reference no:", 140), coverLetterEditFields.get(13)}),
                contactFirstNameLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("employer first name:", 140), coverLetterEditFields.get(14)}),
                contactMiddleNameLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("*employer middle name:", 140), coverLetterEditFields.get(15)}),
                contactLastNameLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("employer last name:", 140), coverLetterEditFields.get(16)}),
                contactTitleLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("*employer title:", 140), coverLetterEditFields.get(17)}),
                contactCompanyLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("*company name:", 140), coverLetterEditFields.get(18)}),
                contactAddress1Line = new VP_DivisionLine(new Node[]{new VP_FieldLabel("employer address line 1:", 140), coverLetterEditFields.get(19)}),
                contactAddress2Line = new VP_DivisionLine(new Node[]{new VP_FieldLabel("*employer address line 2:", 140), coverLetterEditFields.get(20)}),
                contactCityLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("employer city:", 140), coverLetterEditFields.get(21)}),
                contactStateLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("employer state:", 140), coverLetterEditFields.get(22)}),
                contactZipLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("employer zipcode:", 140), coverLetterEditFields.get(23)}),
                salutationLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("salutation:", 140), coverLetterEditFields.get(24)}),
                paragraph1Line = new VP_DivisionLine(new Node[]{new VP_FieldLabel("paragraph 1:", 140), coverLetterEditFields.get(25), delPara1Btn}),
                closingLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("closing:", 140), coverLetterEditFields.get(26)}),
                sigFirstNameLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("first name:", 140), coverLetterEditFields.get(27)}),
                sigMiddleNameLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("*middle name:", 140), coverLetterEditFields.get(28)}),
                sigLastNameLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("last name:", 140), coverLetterEditFields.get(29)}),
                buttonsLine = new VP_DivisionLine(new Node[]{submitBtn, cancelBtn});
        TableView table2 = new TableView();
        TableColumn emailCol2 = new TableColumn("Email"),
                companyCol = new TableColumn("Company"),
                adRefNumbCol = new TableColumn("Ad Reference Number");

        //-------- Initialization End ------------\\
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
        addParagraphLine.getChildren().addAll(addParaBtn);
        name.getChildren().addAll(firstNameLine, middleNameLine, lastNameLine);
        address.getChildren().addAll(address1Line, address2Line, cityLine, stateLine, zipLine);
        communication.getChildren().addAll(phoneLine, cellLine, emailLine);
        heading.getChildren().addAll(name, address, communication);
        emailCol2.setCellValueFactory(new PropertyValueFactory<>("email"));
        companyCol.setCellValueFactory(new PropertyValueFactory<>("company"));
        adRefNumbCol.setCellValueFactory(new PropertyValueFactory<>("adRefNumb"));
        table2.setItems(controller.getCurrentUser().getEmployers());
        table2.setEditable(true);
        table2.getColumns().addAll(emailCol2, companyCol, adRefNumbCol);
        table2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table2.setFixedCellSize(25);
        table2.prefHeightProperty().bind(Bindings.size(table2.getItems()).multiply(table2.getFixedCellSize()).add(30));
        table2.getSelectionModel().clearSelection();
        table2.setMaxHeight(100);
        table2.setPlaceholder(new Label("There are no employers available at this time. Check back later."));
        tableHeading.setPadding(new Insets(30, 0, 0, 50));
        dateDivision.getChildren().addAll(dateLine, tableHeading, table2, loadDataBtn);
        loadDataBtn.setOnAction((e)->{
            VP_Employer thisEmployer = (VP_Employer)(table2.getSelectionModel().getSelectedItem());
            if (thisEmployer != null) {
                VP_Sounds.play(0);
                ((VP_TextField) (coverLetterEditFields.get(11))).setText(thisEmployer.getAdSource());
                ((VP_TextField) (coverLetterEditFields.get(12))).setText(thisEmployer.getAdPosition());
                ((VP_TextField) (coverLetterEditFields.get(13))).setText(thisEmployer.getAdRefNumb());
                ((VP_TextField) (coverLetterEditFields.get(14))).setText(thisEmployer.getFirstName());
                ((VP_TextField) (coverLetterEditFields.get(15))).setText(thisEmployer.getMiddleName());
                ((VP_TextField) (coverLetterEditFields.get(16))).setText(thisEmployer.getLastName());
                ((VP_TextField) (coverLetterEditFields.get(17))).setText(thisEmployer.getTitle());
                ((VP_TextField) (coverLetterEditFields.get(18))).setText(thisEmployer.getCompany());
                ((VP_TextField) (coverLetterEditFields.get(19))).setText(thisEmployer.getAddress1());
                ((VP_TextField) (coverLetterEditFields.get(20))).setText(thisEmployer.getAddress2());
                ((VP_TextField) (coverLetterEditFields.get(21))).setText(thisEmployer.getCity());
                ((VP_TextField) (coverLetterEditFields.get(22))).setText(thisEmployer.getState());
                ((VP_TextField) (coverLetterEditFields.get(23))).setText(thisEmployer.getZip());
                controller.setChanges(true);
            }
        });
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
        screen.addNodes(new Node[]{heading, dateDivision, adref, contact,
                salutation, dynamicBody, closing, signature, notes, covletEditErrorLine, buttonsLine});
        return screen;
    }

    /**
     * Builds the screen where the user sees a list of available themes and
     * applies them to documents. From here, a user may select to build a custom
     * theme in a future version. A.K.A Screen 8
     *
     * @return A VP_Screen that gets applied to a center StackPane level.
     * @since 1.0
     */
    private VP_Screen buildThemesStartScreen() {
        VP_Screen screen = new VP_Screen("DOCUMENT THEMES");
        VP_Paragraph notes = new VP_Paragraph("Select a theme from the list of themes for each document. "
                + "Although you are free to choose individual themes for each document, it is "
                + "recommended that you apply the same theme for a combined look and style "
                + "that has uniformity.");
        VP_PageSubdivision resDiv = new VP_PageSubdivision("RESUME THEME", false),
                bcDiv = new VP_PageSubdivision("BUSINESS CARD THEME", false),
                clDiv = new VP_PageSubdivision("COVER LETTER THEME", false);
        int numbDefaultThemes = Integer.parseInt(VP_Theme.Default.COUNT.toString()); 
        VP_DivisionLine resContainer = new VP_DivisionLine(),
                bcContainer = new VP_DivisionLine(),
                clContainer = new VP_DivisionLine(),
                buttonLine = new VP_DivisionLine(new Node[]{new VP_Button("Return to Overview", new CancelAction())});
        VP_PageSubdivision resRightContainer = new VP_PageSubdivision("SAMPLE PREVIEW", true),
                bcRightContainer = new VP_PageSubdivision("SAMPLE PREVIEW", true),
                clRightContainer = new VP_PageSubdivision("SAMPLE PREVIEW", true);
        for (int i = 0; i < numbDefaultThemes; i++) {
            VP_FieldLabel thisResLabel = new VP_FieldLabel("", 100),
                    thisBCLabel = new VP_FieldLabel("", 100),
                    thisCLLabel = new VP_FieldLabel("", 100);
            VP_DivisionLine resLine = new VP_DivisionLine(),
                    bcLine = new VP_DivisionLine(),
                    clLine = new VP_DivisionLine();
            RadioButton btnRes = new RadioButton(VP_Theme.Default.valueOf("NAME_" + (i + 1)).toString()),
                    btnBC = new RadioButton(VP_Theme.Default.valueOf("NAME_" + (i + 1)).toString()),
                    btnCL = new RadioButton(VP_Theme.Default.valueOf("NAME_" + (i + 1)).toString());
            btnRes.setToggleGroup(groupRes);
            btnBC.setToggleGroup(groupBC);
            btnCL.setToggleGroup(groupCL);
            btnRes.setUserData(i + 1);
            btnBC.setUserData(i + 1);
            btnCL.setUserData(i + 1);
            resLine.getChildren().addAll(thisResLabel, btnRes);
            bcLine.getChildren().addAll(thisBCLabel, btnBC);
            clLine.getChildren().addAll(thisCLLabel, btnCL);
            resLeftContainer.getChildren().add(resLine);
            bcLeftContainer.getChildren().add(bcLine);
            clLeftContainer.getChildren().add(clLine);
            if (i == 0){
                btnRes.setSelected(true);
                btnBC.setSelected(true);
                btnCL.setSelected(true);
                thisResLabel.setText("Current Theme");
                thisBCLabel.setText("Current Theme");
                thisCLLabel.setText("Current Theme");
            }
        }
        resLeftContainer.getChildren().add(new VP_Button("Apply Theme", new ApplyTheme(groupRes, 0, resLeftContainer)));
        bcLeftContainer.getChildren().add(new VP_Button("Apply Theme", new ApplyTheme(groupBC, 1, bcLeftContainer)));
        clLeftContainer.getChildren().add(new VP_Button("Apply Theme", new ApplyTheme(groupCL, 2, clLeftContainer)));
        resContainer.setSpacing(40);
        bcContainer.setSpacing(40);
        clContainer.setSpacing(40);
        resRightContainer.setAlignment(Pos.TOP_CENTER);
        bcRightContainer.setAlignment(Pos.TOP_CENTER);
        clRightContainer.setAlignment(Pos.TOP_CENTER);
        resContainer.getChildren().addAll(resLeftContainer, resRightContainer);
        bcContainer.getChildren().addAll(bcLeftContainer, bcRightContainer);
        clContainer.getChildren().addAll(clLeftContainer, clRightContainer);
        resDiv.getChildren().addAll(resContainer);
        bcDiv.getChildren().addAll(bcContainer);
        clDiv.getChildren().addAll(clContainer);
        screen.addNodes(new Node[]{notes, resDiv, bcDiv, clDiv, buttonLine});
        WebView previewRes = new WebView(),
                previewBC = new WebView(),
                previewCL = new WebView();
        previewRes.setPrefSize(400, 200);
        previewBC.setPrefSize(400, 200);
        previewCL.setPrefSize(400, 200);
        resRightContainer.getChildren().add(previewRes);
        bcRightContainer.getChildren().add(previewBC);
        clRightContainer.getChildren().add(previewCL);
        groupRes.selectedToggleProperty().addListener(new ThemeToggle(groupRes, 0, resRightContainer));
        groupBC.selectedToggleProperty().addListener(new ThemeToggle(groupBC, 1, bcRightContainer));
        groupCL.selectedToggleProperty().addListener(new ThemeToggle(groupCL, 2, clRightContainer));

        return screen;
    }

    /**
     * Builds the screen where the user edits a selected custom theme. A.K.A
     * Screen 9. Currently, this screen is not implemented.
     *
     * @return A VP_Screen that gets applied to a center StackPane level.
     * @since 1.0
     */
    private VP_Screen buildThemesEditScreen() {
        VP_Screen screen = new VP_Screen("EDIT CUSTOM THEME");
        return screen;
    }

    /**
     * Builds the screen where the user selects which documents to send as
     * attachments to a selected contact. User may also edit the list of stored
     * contacts. A.K.A Screen 10
     *
     * @return A VP_Screen that gets applied to a center StackPane level.
     * @since 1.0
     */
    private VP_Screen buildDistributeScreen() {
        //-------- Initialization Start ----------\\
        VP_Screen screen = new VP_Screen("DISTRIBUTE DOCUMENTS");
        VP_PageSubdivision documentsDiv = new VP_PageSubdivision("DOCUMENTS", false),
                contactsDiv = new VP_PageSubdivision("CONTACTS", false),
                sendDiv = new VP_PageSubdivision("SEND", false);
        VP_Paragraph docInfo = new VP_Paragraph("Choose which documents you would like to send below. "
                + "If a specific document is unselectable, this means you need to complete "
                + "something in that document before you can select it for distribution."),
                contactInfo = new VP_Paragraph("Choose a contact to send to from your list of contacts "
                        + "or from the system list of available employers. "
                        + "If you do not have any contacts or if you you need to send to someone who "
                        + "is not in the list of contacts, add a new contact first.");
        VP_TextField addEmail = new VP_TextField(20, 254),
                addName = new VP_TextField(20, 140);
        VP_Button addContactBtn = new VP_Button("Add"),
                deleteBtn = new VP_Button("Delete Selected Contact"),
                sendBtn = new VP_Button("Send Attachments"),
                cancelBtn = new VP_Button("Cancel",  new CancelAction(10));
        VP_DivisionLine doc1Line = new VP_DivisionLine(new Node[]{resHTMLcb}, 40),
                doc2Line = new VP_DivisionLine(new Node[]{resPDFcb}, 40),
                doc3Line = new VP_DivisionLine(new Node[]{clHTMLcb}, 40),
                doc4Line = new VP_DivisionLine(new Node[]{clPDFcb}, 40),
                doc5Line = new VP_DivisionLine(new Node[]{bcHTMLcb}, 40),
                doc6Line = new VP_DivisionLine(new Node[]{bcPDFcb}, 40),
                deleteLine = new VP_DivisionLine(new Node[]{deleteBtn}),
                addContactLine = new VP_DivisionLine(new Node[]{addEmail, addName, addContactBtn}),
                sendLine = new VP_DivisionLine(new Node[]{sendBtn, cancelBtn});
        TableView table = new TableView(),
                table2 = new TableView();
        TableColumn emailCol = new TableColumn("Email"),
                nameCol = new TableColumn("Name"),
                emailCol2 = new TableColumn("Email"),
                companyCol = new TableColumn("Company"),
                adRefNumbCol = new TableColumn("Ad Reference Number");
        
        VP_FieldLabel tableHeader1 = new VP_FieldLabel("PERSONAL CONTACT LIST"),
                tableHeader2 = new VP_FieldLabel("VAQPACK EMPLOYER LIST");

        //-------- Initialization End ------------\\
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailCol2.setCellValueFactory(new PropertyValueFactory<>("email"));
        companyCol.setCellValueFactory(new PropertyValueFactory<>("company"));
        adRefNumbCol.setCellValueFactory(new PropertyValueFactory<>("adRefNumb"));

        table.setItems(controller.getCurrentUser().getContacts());
        table.setEditable(true);
        table.getColumns().addAll(emailCol, nameCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setFixedCellSize(25);
        table.prefHeightProperty().bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize()).add(30));
        table.getSelectionModel().clearSelection();
        table.setMaxHeight(200);
        table.setPlaceholder(new Label("You have no contacts. Add some contact below."));

        table2.setItems(controller.getCurrentUser().getEmployers());
        table2.setEditable(true);
        table2.getColumns().addAll(emailCol2, companyCol, adRefNumbCol);
        table2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table2.setFixedCellSize(25);
        table2.prefHeightProperty().bind(Bindings.size(table2.getItems()).multiply(table2.getFixedCellSize()).add(30));
        table2.getSelectionModel().clearSelection();
        table2.setMaxHeight(100);
        table2.setPlaceholder(new Label("There are no employers available at this time. Check back later."));
        
        tableHeader1.setPadding(new Insets(30, 0, 0, 50));
        tableHeader2.setPadding(new Insets(10, 0, 0, 50));
        addEmail.setPromptText("Email Address");
        addName.setPromptText("Contact Name");
        table.selectionModelProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
            table2.getSelectionModel().clearSelection();
        });
        table2.selectionModelProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
            table.getSelectionModel().clearSelection();
        });
        addContactBtn.setOnAction((e) -> {
            boolean hasError = false;
            String thisEmail = addEmail.getText().trim().toLowerCase(),
                    thisName = addName.getText().trim();
            VP_Sounds.play(0);
            if (thisEmail == null || thisEmail.equals("")) {
                hasError = true;
                addEmail.showInvalid();
                addContactErrorLine.setText("New contact email address cannot be blank.");
            } else if (thisName == null || thisName.equals("")) {
                hasError = true;
                addName.showInvalid();
                addContactErrorLine.setText("New contact name cannot be blank.");
            } else if (!controller.getDataM().checkEmail(thisEmail)) {
                hasError = true;
                addEmail.showInvalid();
                addContactErrorLine.setText("New contact email is invalid.");
            } else {
                for (int i = 0; i < controller.getCurrentUser().getContacts().size(); i++) {
                    if (((VP_Contact)controller.getCurrentUser().getContacts().get(i)).getEmail().equalsIgnoreCase(thisEmail)) {
                        hasError = true;
                        addEmail.showInvalid();
                        addContactErrorLine.setText("A contact with this email already exists.");
                        break;
                    }
                }
            }
            if (!hasError) {
                addContactErrorLine.hide();
                VP_Contact newContact = new VP_Contact(thisEmail, thisName);
                try {
                    controller.getDataM().addContact(newContact.getEmail(), newContact.getName());
                    controller.getCurrentUser().getContacts().add(newContact);
                    table.getSelectionModel().clearSelection();
                    addName.clear();
                    addEmail.clear();
                    table.scrollTo(controller.getCurrentUser().getContacts().size() - 1);
                } catch (SQLException ex) {
                    controller.errorAlert(3128, ex.getMessage());
                }
            } else {
                VP_Sounds.play(-1);
                addContactErrorLine.show();
            }
        });
        deleteBtn.setOnAction((e) -> {
            VP_Contact deletedContact = (VP_Contact)(table.getSelectionModel().getSelectedItem());
            if (deletedContact != null) {
                VP_Sounds.play(0);
                try {
                    controller.getDataM().deleteContact(deletedContact.getEmail(), deletedContact.getName());
                    controller.getCurrentUser().getContacts().remove(deletedContact);
                    table.getSelectionModel().clearSelection();
                } catch (SQLException ex) {
                    controller.errorAlert(3129, ex.getMessage());
                }
            }
        });
        
        sendBtn.setOnAction((e) -> {
            Boolean hasError = false, 
                    sendResHTML = resHTMLcb.isSelected(),
                    sendResPDF = resPDFcb.isSelected(),
                    sendBCHTML = bcHTMLcb.isSelected(),
                    sendBCPDF = bcPDFcb.isSelected(),
                    sendCLHTML = clHTMLcb.isSelected(),
                    sendCLPDF = clPDFcb.isSelected();
            String email = "";
            VP_Contact sendContact = (VP_Contact)(table.getSelectionModel().getSelectedItem());
            VP_Employer sendEmployer = (VP_Employer)(table2.getSelectionModel().getSelectedItem());
            VP_Sounds.play(0);
            if (sendContact != null) {
                email = sendContact.getEmail();
            } else if (sendEmployer != null) {
                email = sendEmployer.getEmail();
            }
            if (!(sendResHTML || sendResPDF || sendBCHTML || sendBCPDF || sendCLPDF || sendCLHTML)) {
                hasError = true;
                sendAttachErrorLine.setText("You have not selected any files to send.");
            } else if (email.equals("")) {
                hasError = true;
                sendAttachErrorLine.setText("You have not selected a contact or employer to send documents to.");
            }
            if (hasError) {
                VP_Sounds.play(-1);
                sendAttachErrorLine.show();
            } else {
                sendAttachErrorLine.hide();
                addContactErrorLine.hide();
                try {
                    controller.getDataM().sendAttachments(email, sendResHTML, sendResPDF, 
                            sendBCHTML, sendBCPDF, sendCLHTML, sendCLPDF);
                    table.getSelectionModel().clearSelection();
                    table2.getSelectionModel().clearSelection();
                    resHTMLcb.setSelected(false);
                    resPDFcb.setSelected(false);
                    bcHTMLcb.setSelected(false);
                    bcPDFcb.setSelected(false);
                    clHTMLcb.setSelected(false);
                    clPDFcb.setSelected(false);
                    VP_Dialog emailSent = new VP_Dialog("EMAIL SENT");
                    emailSent.setHeaderText("Your attachments have been sent out to the selected contact.");
                    Label emailSentLabel = new Label("You should verify with the contact that the email has been received.");
                    emailSentLabel.setPadding(new Insets(50, 20, 50, 20));
                    emailSent.getDialogShell().add(emailSentLabel, 0, 0);
                    emailSent.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
                            emailSent.showAndWait();
                } catch (SQLException | IOException ex) {
                    controller.errorAlert(3130, ex.getMessage());
                }
            }
        });
        documentsDiv.getChildren().addAll(docInfo, doc1Line, doc2Line, doc3Line, doc4Line, doc5Line, doc6Line);
        contactsDiv.getChildren().addAll(contactInfo, tableHeader2, table2, tableHeader1, table, deleteLine, addContactErrorLine, addContactLine);
        sendDiv.getChildren().addAll(sendAttachErrorLine, sendLine);
        screen.addNodes(new Node[]{documentsDiv, contactsDiv, sendDiv});
        return screen;
    }

    /**
     * Builds the screen displaying the completion status of the various resume
     * sections. From here, the user navigates to these sections to edit them,
     * or may choose to create a custom section. A.K.A Screen 11
     *
     * @return A VP_Screen that gets applied to a center StackPane level.
     * @since 1.0
     */
    private VP_Screen buildResumeStartScreen() {
        //-------- Initialization Start ----------\\
        VP_Screen screen = new VP_Screen("RESUME");
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
                buttonLine = new VP_DivisionLine(new Node[]{new VP_Button("Cancel", new CancelAction())});
        VP_PageSubdivision tasks = new VP_PageSubdivision("RESUME TASKS", false);
        //-------- Initialization End ------------\\
        for (int i = 0; i < resUpdateBtns.size(); i++) {
            resUpdateBtns.get(i).setPrefWidth(250);
        }
        tasks.getChildren().addAll(step1Line, step2Line,
                step3Line, step4Line, step5Line, step6Line, step7Line, step8Line, step9Line, step10Line);
        updateResumeStatus();
        screen.addNodes(new Node[]{tasks, buttonLine});
        return screen;
    }

    /**
     * Builds the screen displaying the Heading and Objective sections of the
     * resume. A.K.A. screen 12
     *
     * @return A VP_Screen that gets applied to a center StackPane level.
     * @since 1.0
     */
    private VP_Screen buildResumeObjectiveScreen() {
        //-------- Initialization Start ----------\\
        VP_Screen screen = new VP_Screen("RESUME -- HEADING and OBJECTIVE");
        VP_PageSubdivision heading = new VP_PageSubdivision("HEADING", true),
                objective = new VP_PageSubdivision("OBJECTIVE", false);
        VP_TextArea objectiveParagraph = new VP_TextArea();
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
        VP_DivisionLine firstNameLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("first name:", 110), headingFields.get(0)}),
                middleNameLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("*middle name:", 110), headingFields.get(1)}),
                lastNameLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("last name:", 110), headingFields.get(2)}),
                address1Line = new VP_DivisionLine(new Node[]{new VP_FieldLabel("address line 1:", 110), headingFields.get(3)}),
                address2Line = new VP_DivisionLine(new Node[]{new VP_FieldLabel("*address line 2:", 110), headingFields.get(4)}),
                cityLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("city:", 110), headingFields.get(5)}),
                stateLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("state:", 110), headingFields.get(6)}),
                zipLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("zipcode:", 110), headingFields.get(7)}),
                phoneLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("phone:", 110), headingFields.get(8)}),
                cellLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("*cell:", 110), headingFields.get(9)}),
                emailLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("email:", 110), headingFields.get(10)}),
                objectiveLine = new VP_DivisionLine(new Node[]{objectiveParagraph}),
                buttonsLine = new VP_DivisionLine(new Node[]{new VP_Button("Submit", new SubmitObjectiveAction(objectiveParagraph)), 
                    new VP_Button("Cancel", new CancelAction(11))});
        VP_PageSubdivision name = new VP_PageSubdivision("NAME", false),
                address = new VP_PageSubdivision("ADDRESS", false),
                communication = new VP_PageSubdivision("COMMUNICATION", false);
        VP_Paragraph hNotes = new VP_Paragraph("Locked fields can be edited by updating your personal info."),
                oNotes = new VP_Paragraph("Write a short statement that clearly defines your career goals and direction. "
                        + "The objective section of your resume should be tailored to fit what a specific employer is looking for. "
                        + "Keep this is mind when sending your resume to multiple employers.");
        //-------- Initialization End ------------\\
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
        objective.getChildren().addAll(oNotes, objectiveLine, objectiveErrorLine);
        screen.addNodes(new Node[]{heading, objective, buttonsLine});
        return screen;
    }

    /**
     * Builds the screen displaying the Education section of the resume. A.K.A.
     * screen 13
     *
     * @return A VP_Screen that gets applied to a center StackPane level.
     * @since 1.0
     */
    private VP_Screen buildResumeEducationScreen() {
        //-------- Initialization Start ----------\\
        VP_Screen screen = new VP_Screen(resumeEducationBox);
        VP_PageSubdivision educationDiv = new VP_PageSubdivision("EDUCATION ENTRY #1", false);
        educationFields.add(new VP_TextField(32, 128, controller));
        educationFields.add(new VP_TextField(32, 128, controller));
        educationFields.add(new VP_TextField(32, 128, controller));
        educationFields.add(new VP_TextField(32, 128, controller));
        educationFields.add(new VP_TextField(32, 128, controller));
        educationFields.add(new VP_TextField(32, 128, controller));
        VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{new VP_FieldLabel("institution name:", 130), educationFields.get(0)}),
                line1 = new VP_DivisionLine(new Node[]{new VP_FieldLabel("institution location:", 130), educationFields.get(1)}),
                line2 = new VP_DivisionLine(new Node[]{new VP_FieldLabel("degree, certificate, or\ntraining earned:", 130), educationFields.get(2)}),
                line3 = new VP_DivisionLine(new Node[]{new VP_FieldLabel("*GPA:", 130), educationFields.get(3)}),
                line4 = new VP_DivisionLine(new Node[]{new VP_FieldLabel("start date:", 130), educationFields.get(4)}),
                line5 = new VP_DivisionLine(new Node[]{new VP_FieldLabel("end date:", 130), educationFields.get(5)}),
                buttonLine = new VP_DivisionLine(new Node[]{new VP_Button("Submit", new SubmitEducationAction()), addEducationBtn,
                    new VP_Button("Cancel", new CancelAction(11))});
        VP_Paragraph notes1 = new VP_Paragraph("(*) denotes an optional field."),
                notes2 = new VP_Paragraph("At least one education entry must exist for your resume. "
                        + "The entries can be schools, colleges, or training centers. The GPA field is "
                        + "optional. You may include up to 9 total education entries in your resume.");
        //-------- Initialization End ------------\\
        for (int i = 0; i < educationFields.size(); i++) {
            educationFields.get(i).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getEducation().get(0).get(i));
        }
        educationDiv.getChildren().addAll(line0, line1, line2, line3, line4, line5);
        screen.addNodes(new Node[]{notes2, educationDiv, notes1, educationErrorLine, buttonLine});
        return screen;
    }

    /**
     * Builds the screen displaying the Work Experience section of the resume.
     * A.K.A. screen 14
     *
     * @return A VP_Screen that gets applied to a center StackPane level.
     * @since 1.0
     */
    private VP_Screen buildResumeExperienceScreen() {
        //-------- Initialization Start ----------\\
        VP_Screen screen = new VP_Screen(resumeExperienceBox);
        VP_PageSubdivision experienceDiv = new VP_PageSubdivision("EXPERIENCE ENTRY #1", false);
        experienceFields.add(new VP_TextField(32, 128, controller));
        experienceFields.add(new VP_TextField(32, 128, controller));
        experienceFields.add(new VP_TextField(32, 128, controller));
        experienceFields.add(new VP_TextField(32, 128, controller));
        experienceFields.add(new VP_TextField(32, 128, controller));
        VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{new VP_FieldLabel("institution name:", 130), experienceFields.get(0)}),
                line1 = new VP_DivisionLine(new Node[]{new VP_FieldLabel("institution location:", 130), experienceFields.get(1)}),
                line2 = new VP_DivisionLine(new Node[]{new VP_FieldLabel("position held:", 130), experienceFields.get(2)}),
                line3 = new VP_DivisionLine(new Node[]{new VP_FieldLabel("start date:", 130), experienceFields.get(3)}),
                line4 = new VP_DivisionLine(new Node[]{new VP_FieldLabel("end date:", 130), experienceFields.get(4)}),
                buttonLine = new VP_DivisionLine(new Node[]{new VP_Button("Submit", new SubmitExperienceAction()),
                    addExperienceBtn, new VP_Button("Cancel", new CancelAction(11))});
        VP_Paragraph notes1 = new VP_Paragraph(""),
                notes2 = new VP_Paragraph("At least one experience entry must exist for your resume. "
                        + "The entries are not limited to employers. Entries may also be internships or research studies, "
                        + "for example. You may include up to 9 total experience entries in your resume.");
        //-------- Initialization End ------------\\

        for (int i = 0; i < experienceFields.size(); i++) {
            experienceFields.get(i).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getExperience().get(0).get(i));
        }
        experienceDiv.getChildren().addAll(line0, line1, line2, line3, line4);
        screen.addNodes(new Node[]{notes2, experienceDiv, notes1, experienceErrorLine, buttonLine});
        return screen;
    }

    /**
     * Builds the screen displaying the Awards and Achievements section of the
     * resume. A.K.A. screen 15
     *
     * @return A VP_Screen that gets applied to a center StackPane level.
     * @since 1.0
     */
    private VP_Screen buildResumeAchievementsScreen() {
        //-------- Initialization Start ----------\\
        VP_Screen screen = new VP_Screen(resumeAchievementsBox);
        VP_PageSubdivision achievementsDiv = new VP_PageSubdivision("AWARD/ACHIEVEMENT ENTRY #1", false);
        achievementsFields.add(new VP_TextField(32, 128, controller));
        achievementsFields.add(new VP_TextField(32, 128, controller));
        achievementsFields.add(new VP_TextField(32, 128, controller));
        VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{new VP_FieldLabel("name of award\nor achievement:", 130), achievementsFields.get(0)}),
                line1 = new VP_DivisionLine(new Node[]{new VP_FieldLabel("given by:", 130), achievementsFields.get(1)}),
                line2 = new VP_DivisionLine(new Node[]{new VP_FieldLabel("date:", 130), achievementsFields.get(2)}),
                buttonLine = new VP_DivisionLine(new Node[]{new VP_Button("Submit", new SubmitAchievementsAction()), 
                    addAchievementBtn, new VP_Button("Cancel", new CancelAction(11))});
        VP_Paragraph notes1 = new VP_Paragraph(""),
                notes2 = new VP_Paragraph("This section of your resume is optional. "
                        + "Each entry can be an award that you have won, induction into a society, or some other "
                        + "achievement. You may include up to 9 total achievements in your resume.");
        //-------- Initialization End ------------\\

        for (int i = 0; i < achievementsFields.size(); i++) {
            achievementsFields.get(i).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getAchievements().get(0).get(i));
        }
        achievementsDiv.getChildren().addAll(line0, line1, line2);
        screen.addNodes(new Node[]{notes2, achievementsDiv, notes1, achievementsErrorLine, buttonLine});
        return screen;
    }

    /**
     * Builds the screen displaying the Community section of the resume. A.K.A.
     * screen 16
     *
     * @return A VP_Screen that gets applied to a center StackPane level.
     * @since 1.0
     */
    private VP_Screen buildResumeCommunityScreen() {
        //-------- Initialization Start ----------\\
        VP_Screen screen = new VP_Screen(resumeCommunityBox);
        VP_PageSubdivision communityDiv = new VP_PageSubdivision("EVENT ENTRY #1", false);
        communityFields.add(new VP_TextField(32, 128, controller));
        communityFields.add(new VP_TextField(32, 128, controller));
        communityFields.add(new VP_TextField(32, 128, controller));
        VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{new VP_FieldLabel("event name:", 130), communityFields.get(0)}),
                line1 = new VP_DivisionLine(new Node[]{new VP_FieldLabel("event location:", 130), communityFields.get(1)}),
                line2 = new VP_DivisionLine(new Node[]{new VP_FieldLabel("date:", 130), communityFields.get(2)}),
                buttonLine = new VP_DivisionLine(new Node[]{new VP_Button("Submit", new SubmitCommunityAction()),
                    addCommunityBtn, new VP_Button("Cancel", new CancelAction(11))});
        VP_Paragraph notes1 = new VP_Paragraph(""),
                notes2 = new VP_Paragraph("This section of your resume is optional. "
                        + "Each entry represents a community service event or volunteer work that you "
                        + "have participated in. You may include up to 9 total events in your resume.");
        //-------- Initialization End ------------\\

        for (int i = 0; i < communityFields.size(); i++) {
            communityFields.get(i).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getCommunity().get(0).get(i));
        }
        communityDiv.getChildren().addAll(line0, line1, line2);
        screen.addNodes(new Node[]{notes2, communityDiv, notes1, communityErrorLine, buttonLine});
        return screen;
    }

    /**
     * Builds the screen displaying the Qualifications section of the resume.
     * A.K.A. screen 17
     *
     * @return A VP_Screen that gets applied to a center StackPane level.
     * @since 1.0
     */
    private VP_Screen buildResumeQualificationsScreen() {
        //-------- Initialization Start ----------\\
        VP_Screen screen = new VP_Screen(resumeQualificationsBox);
        VP_PageSubdivision qualificationsDiv = new VP_PageSubdivision("QUALIFICATION #1", false);
        qualificationsFields.add(new VP_TextField(50, 128, controller));
        VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{qualificationsFields.get(0)}),
                buttonLine = new VP_DivisionLine(new Node[]{new VP_Button("Submit", new SubmitQualificationsAction()),
                    addQualificationBtn, new VP_Button("Cancel", new CancelAction(11))});
        VP_Paragraph notes1 = new VP_Paragraph(""),
                notes2 = new VP_Paragraph("List your skills that are relevant to the job position "
                        + "you are applying for. At least one qualification must exist for your resume, although "
                        + "you really should have more. You may include up to 9 total qualifications in your resume.");
        //-------- Initialization End ------------\\

        for (int i = 0; i < qualificationsFields.size(); i++) {
            qualificationsFields.get(i).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getQualifications().get(i));
        }
        qualificationsDiv.getChildren().addAll(line0);
        screen.addNodes(new Node[]{notes2, qualificationsDiv, notes1, qualificationsErrorLine, buttonLine});
        return screen;
    }

    /**
     * Builds the screen displaying the Highlights section of the resume. A.K.A.
     * screen 18
     *
     * @return A VP_Screen that gets applied to a center StackPane level.
     * @since 1.0
     */
    private VP_Screen buildResumeHighlightsScreen() {
        //-------- Initialization Start ----------\\
        VP_Screen screen = new VP_Screen(resumeHighlightsBox);
        VP_PageSubdivision highlightsDiv = new VP_PageSubdivision("PERSONAL QUALITY #1", false);
        highlightsFields.add(new VP_TextField(50, 128, controller));
        VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{highlightsFields.get(0)}),
                buttonLine = new VP_DivisionLine(new Node[]{new VP_Button("Submit", new SubmitHighlightsAction()),
                    addHighlightBtn, new VP_Button("Cancel", new CancelAction(11))});
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
        screen.addNodes(new Node[]{notes2, highlightsDiv, notes1, highlightsErrorLine, buttonLine});
        return screen;
    }

    /**
     * Builds the screen displaying the Languages section of the resume. A.K.A.
     * screen 19
     *
     * @return A VP_Screen that gets applied to a center StackPane level.
     * @since 1.0
     */
    private VP_Screen buildResumeLanguagesScreen() {
        //-------- Initialization Start ----------\\
        VP_Screen screen = new VP_Screen(resumeLanguagesBox);
        VP_PageSubdivision languagesDiv = new VP_PageSubdivision("PRIMARY LANGUAGE", false);
        languagesFields.add(new VP_TextField(50, 128, controller));
        VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{languagesFields.get(0)}),
                buttonLine = new VP_DivisionLine(new Node[]{new VP_Button("Submit", new SubmitLanguagesAction()),
                    addLanguageBtn, new VP_Button("Cancel", new CancelAction(11))});
        VP_Paragraph notes1 = new VP_Paragraph(""),
                notes2 = new VP_Paragraph("List the languages that you know. "
                        + "The primary language must exist for your resume. "
                        + "You may include up to 8 additional secondary languages in your resume.");
        //-------- Initialization End ------------\\

        for (int i = 0; i < languagesFields.size(); i++) {
            languagesFields.get(i).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getLanguages().get(i));
        }
        languagesDiv.getChildren().addAll(line0);
        screen.addNodes(new Node[]{notes2, languagesDiv, notes1, languagesErrorLine, buttonLine});
        return screen;
    }

    /**
     * Builds the screen displaying the Software section of the resume. A.K.A.
     * screen 20
     *
     * @return A VP_Screen that gets applied to a center StackPane level.
     * @since 1.0
     */
    private VP_Screen buildResumeSoftwareScreen() {
        //-------- Initialization Start ----------\\
        VP_Screen screen = new VP_Screen(resumeSoftwareBox);
        VP_PageSubdivision softwareDiv = new VP_PageSubdivision("SOFTWARE PRODUCT #1", false);
        softwareFields.add(new VP_TextField(50, 128, controller));
        VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{softwareFields.get(0)}),
                buttonLine = new VP_DivisionLine(new Node[]{new VP_Button("Submit", new SubmitSoftwareAction()), 
                    addSoftwareBtn, new VP_Button("Cancel", new CancelAction(11))});
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
        screen.addNodes(new Node[]{notes2, softwareDiv, notes1, softwareErrorLine, buttonLine});
        return screen;
    }

    /**
     * Builds the screen displaying the References section of the resume. A.K.A.
     * screen 21
     *
     * @return A VP_Screen that gets applied to a center StackPane level.
     * @since 1.0
     */
    private VP_Screen buildResumeReferencesScreen() {
        //-------- Initialization Start ----------\\
        VP_Screen screen = new VP_Screen(resumeReferencesBox);
        VP_PageSubdivision referenceDiv = new VP_PageSubdivision("REFERENCE #1", false);
        referencesFields.add(new VP_TextField(32, 45, controller));
        referencesFields.add(new VP_TextField(32, 45, controller));
        referencesFields.add(new VP_TextField(32, 45, controller));
        referencesFields.add(new VP_TextField(32, 128, controller));
        referencesFields.add(new VP_TextField(13, 13, controller));
        referencesFields.add(new VP_TextField(32, 254, controller));
        VP_DivisionLine line0 = new VP_DivisionLine(new Node[]{new VP_FieldLabel("first name:", 130), referencesFields.get(0)}),
                line1 = new VP_DivisionLine(new Node[]{new VP_FieldLabel("*middle name:", 130), referencesFields.get(1)}),
                line2 = new VP_DivisionLine(new Node[]{new VP_FieldLabel("last name:", 130), referencesFields.get(2)}),
                line3 = new VP_DivisionLine(new Node[]{new VP_FieldLabel("company or\ninstitution:", 130), referencesFields.get(3)}),
                line4 = new VP_DivisionLine(new Node[]{new VP_FieldLabel("phone:", 130), referencesFields.get(4)}),
                line5 = new VP_DivisionLine(new Node[]{new VP_FieldLabel("*email:", 130), referencesFields.get(5)}),
                buttonLine = new VP_DivisionLine(new Node[]{new VP_Button("Submit", new SubmitReferencesAction()),
                    addReferenceBtn, new VP_Button("Cancel", new CancelAction(11))});
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
        screen.addNodes(new Node[]{notes2, referenceDiv, notes1, referencesErrorLine, buttonLine});
        return screen;
    }
    

    /**
     * Builds the screen displaying the fields for password changing. A.K.A.
     * screen 22
     *
     * @return A VP_Screen that gets applied to a center StackPane level.
     * @since 1.0
     */
    private VP_Screen buildChangePasswordScreen() {
        //-------- Initialization Start ----------\\
        VP_Screen screen = new VP_Screen("CHANGE YOUR PASSWORD");
        VP_FieldLabel oldPassLabel = new VP_FieldLabel("old password:", 100),
                newPassLabel = new VP_FieldLabel("new password:", 100),
                newPassConfirmLabel = new VP_FieldLabel("confirm new\npassword:", 100);
        VP_DivisionLine oldPassLine = new VP_DivisionLine(new Node[]{oldPassLabel, oldPass}),
                newPassLine = new VP_DivisionLine(new Node[]{newPassLabel, newPass}),
                changePassStrengthLine = new VP_DivisionLine(new Node[]{changePassStrengthLabel}),
                newPassConfirmLine = new VP_DivisionLine(new Node[]{newPassConfirmLabel, newPassConfirm}),
                buttonLine = new VP_DivisionLine(new Node[]{new VP_Button("Submit", new ChangePassAction()), 
                    new VP_Button("Cancel", new CancelAction(22))});
        //-------- Initialization End ------------\\

        changePassStrengthLabel.getStyleClass().add("inputLabel");
        screen.addNodes(new Node[]{oldPassLine, newPassLine, changePassStrengthLine,
                newPassConfirmLine, changePassErrorLine, buttonLine});
        return screen;
    }
    
    /**
     * Builds the screen displaying the fields for changing the MySQL location.
     * A.K.A. screen 23. Only accessible by a VaqPack administrator.
     *
     * @return A VP_Screen that gets applied to a center StackPane level.
     * @since 1.0
     */
    private VP_Screen buildChangeMysqlScreen() {
        //-------- Initialization Start ----------\\
        VP_Screen screen = new VP_Screen("CHANGE MYSQL LOCATION");
        VP_Paragraph mysqlNotes = new VP_Paragraph("Enter in your password, the new url, and the new port for the VaqPack database. "
                + "VaqPack will build a new database at the new location if one is not already present. "
                + "Data must still be moved by the database administrator, unless this is a fresh installation. "
                + "When you submit, VaqPack will close and will have to be restarted. "
                + "The database admninistrator should be present in the event of problems or if "
                + "new database credentials need to be entered. VaqPack does not check if the url and port is valid "
                + "until it is restarted.");
        VP_DivisionLine adminPassLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("your password:", 100), adminPassMysql}),
                newMysqlUrlLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("new MySQL url:", 100), newMysqlURL}),
                newMysqlPortLine = new VP_DivisionLine(new Node[]{new VP_FieldLabel("new MySQL port:", 100), newMysqlPort}),
                buttonLine = new VP_DivisionLine(new Node[]{new VP_Button("Submit", new ChangeMySQLAction()),
                    new VP_Button("Cancel", new CancelAction(23))
                });
        //-------- Initialization End ------------\\
        
        screen.addNodes(new Node[]{mysqlNotes, adminPassLine, newMysqlUrlLine, newMysqlPortLine, 
                changeMysqlErrorLine, buttonLine});
        return screen;
    }
    
    /**
     * Builds the screen displaying the printing options.
     * A.K.A. screen 24.
     *
     * @return A VP_Screen that gets applied to a center StackPane level.
     * @since 1.0
     */
    private VP_Screen buildPrintScreen() {
        //-------- Initialization Start ----------\\
        VP_Screen screen = new VP_Screen("PRINT DOCUMENTS");
        VP_Paragraph printNotes = new VP_Paragraph("Choose a document to print by clicking one of the buttons below. "
                + "If a button is not active, this means that the document is not complete and connot be printed yet.");
        VP_DivisionLine printLine = new VP_DivisionLine(new Node[]{printResBtn, printCLBtn, printBCBtn}),
                cancelLine = new VP_DivisionLine(new Node[]{new VP_Button("Cancel", new CancelAction(24))});
        //-------- Initialization End ------------\\
        screen.addNodes(new Node[]{printNotes, printLine, cancelLine});
        return screen;
    }
    
    /**
     * Builds the screen where the administrator updates the list of employers 
     * available to all users. A.K.A Screen 25
     *
     * @return A VP_Screen that gets applied to a center StackPane level.
     * @since 1.0
     */
    private VP_Screen buildEmployersScreen() {
        //-------- Initialization Start ----------\\
        VP_Screen screen = new VP_Screen("EMPLOYERS LIST");
        VP_PageSubdivision employersDiv = new VP_PageSubdivision("Employer List", false),
                addEmployerDiv = new VP_PageSubdivision("Add Employer", false);
        VP_Button addBtn = new VP_Button("Add"),
                deleteBtn = new VP_Button("Delete Selected Employer"),
                cancelBtn = new VP_Button("Cancel",  new CancelAction(25));
        TableView table = new TableView();
        TableColumn emailCol = new TableColumn("Email"),
                firstNameCol = new TableColumn("First Name"),
                middleNameCol = new TableColumn("Middle Name"), 
                lastNameCol = new TableColumn("Last Name"), 
                titleCol = new TableColumn("Title"), 
                companyCol = new TableColumn("Company"), 
                address1Col = new TableColumn("Address Line1"), 
                address2Col = new TableColumn("Address Line2"), 
                cityCol = new TableColumn("City"), 
                stateCol = new TableColumn("State"), 
                zipCol = new TableColumn("Zipcode"), 
                adSourceCol = new TableColumn("Ad Source"), 
                adPositionCol = new TableColumn("Job Position"), 
                adRefNumbCol = new TableColumn("Ad Reference Number");
        VP_TextField addEmail = new VP_TextField(32, 254),
                addFirstName = new VP_TextField(32, 45),
                addMiddleName = new VP_TextField(32, 45),
                addLastName = new VP_TextField(32, 45),
                addTitle = new VP_TextField(32, 48),
                addCompany = new VP_TextField(32, 48),
                addAddress1 = new VP_TextField(32, 254),
                addAddress2 = new VP_TextField(32, 254),
                addCity = new VP_TextField(32, 45),
                addState = new VP_TextField(32, 2),
                addZip = new VP_TextField(32, 10),
                addAdSource = new VP_TextField(32, 128),
                addAdPosition = new VP_TextField(32, 128),
                addAdRefNumb = new VP_TextField(32, 128);
        //-------- Initialization End ------------\\
        
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        middleNameCol.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        companyCol.setCellValueFactory(new PropertyValueFactory<>("company"));
        address1Col.setCellValueFactory(new PropertyValueFactory<>("address1"));
        address2Col.setCellValueFactory(new PropertyValueFactory<>("address2"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        stateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        zipCol.setCellValueFactory(new PropertyValueFactory<>("zip"));
        adSourceCol.setCellValueFactory(new PropertyValueFactory<>("adSource"));
        adPositionCol.setCellValueFactory(new PropertyValueFactory<>("adPosition"));
        adRefNumbCol.setCellValueFactory(new PropertyValueFactory<>("adRefNumb"));
        table.setItems(controller.getCurrentUser().getEmployers());
        table.setEditable(true);
        table.getColumns().addAll(emailCol, firstNameCol, middleNameCol, lastNameCol,
                titleCol, companyCol, address1Col, address2Col, cityCol, stateCol,
                zipCol, adSourceCol, adPositionCol, adRefNumbCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setFixedCellSize(25);
        table.prefHeightProperty().bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize()).add(30));
        table.getSelectionModel().clearSelection();
        table.setMaxHeight(400);
        table.setPlaceholder(new Label("There are no employers. Add some empoyers below."));
        addEmail.setPromptText("Email Address");
        addFirstName.setPromptText("First Name");
        addMiddleName.setPromptText("Middle Name (optional)");
        addLastName.setPromptText("Last Name");
        addTitle.setPromptText("Employer Title (optional)");
        addCompany.setPromptText("Employer Company");
        addAddress1.setPromptText("Address Line 1");
        addAddress2.setPromptText("Address Line 2");
        addCity.setPromptText("City");
        addState.setPromptText("State");
        addZip.setPromptText("Zipcode");
        addAdSource.setPromptText("Ad Source");
        addAdPosition.setPromptText("Job Position");
        addAdRefNumb.setPromptText("Ad Reference Number");
        employersDiv.getChildren().addAll(table, deleteBtn);
        addEmployerDiv.getChildren().addAll(addEmail, addFirstName, addMiddleName,
                addLastName, addTitle, addCompany, addAddress1, addAddress2,
                addCity, addState, addZip, addAdSource, addAdPosition, addAdRefNumb, 
                addEmployerErrorLine, addBtn);
        addBtn.setOnAction((e) -> {
            String zipRegex = "^[0-9]{5}(?:-[0-9]{4})?$";
            Pattern zipPattern = Pattern.compile(zipRegex);
            Matcher matcher;
            boolean hasError = false;
            String thisEmail = addEmail.getText().trim().toLowerCase(),
                    thisFirstName = addFirstName.getText().trim(),
                    thisMiddleName = addMiddleName.getText().trim(),
                    thisLastName = addLastName.getText().trim(),
                    thisTitle = addTitle.getText().trim(),
                    thisCompany = addCompany.getText().trim(),
                    thisAddress1 = addAddress1.getText().trim(),
                    thisAddress2 = addAddress2.getText().trim(),
                    thisCity = addCity.getText().trim(),
                    thisState = addState.getText().trim().toUpperCase(),
                    thisZip = addZip.getText().trim(),
                    thisAdSource = addAdSource.getText().trim(),
                    thisAdPosition = addAdPosition.getText().trim(),
                    thisAdRefNumb = addAdRefNumb.getText().trim();
            VP_Sounds.play(0);
            if (thisEmail == null || thisEmail.equals("")) {
                hasError = true;
                addEmail.showInvalid();
                addEmployerErrorLine.setText("New employer email address cannot be blank.");
            } else if (!controller.getDataM().checkEmail(thisEmail)) {
                hasError = true;
                addEmail.showInvalid();
                addEmployerErrorLine.setText("New employer email is invalid.");
            } else if (thisFirstName == null || thisFirstName.equals("")) {
                hasError = true;
                addFirstName.showInvalid();
                addEmployerErrorLine.setText("New employer first name cannot be blank.");
            } else if (thisLastName == null || thisLastName.equals("")) {
                hasError = true;
                addLastName.showInvalid();
                addEmployerErrorLine.setText("New employer last name cannot be blank.");
            } else if (thisCompany == null || thisCompany.equals("")) {
                hasError = true;
                addCompany.showInvalid();
                addEmployerErrorLine.setText("New employer company name cannot be blank.");
            } else if (thisAddress1 == null || thisAddress1.equals("")) {
                hasError = true;
                addAddress1.showInvalid();
                addEmployerErrorLine.setText("New employer address line 1 cannot be blank.");
            } else if (thisCity == null || thisCity.equals("")) {
                hasError = true;
                addCity.showInvalid();
                addEmployerErrorLine.setText("New employer city cannot be blank.");
            } else if (thisState == null || thisState.equals("")) {
                hasError = true;
                addState.showInvalid();
                addEmployerErrorLine.setText("New employer state cannot be blank.");
            } else if (thisZip == null || thisZip.equals("")) {
                hasError = true;
                addZip.showInvalid();
                addEmployerErrorLine.setText("New employer zipcode cannot be blank.");
            } else if (thisAdSource == null || thisAdSource.equals("")) {
                hasError = true;
                addAdSource.showInvalid();
                addEmployerErrorLine.setText("The ad source cannot be blank.");
            } else if (thisAdPosition == null || thisAdPosition.equals("")) {
                hasError = true;
                addAdPosition.showInvalid();
                addEmployerErrorLine.setText("The ad job position cannot be blank.");
            } else if (thisAdRefNumb == null || thisAdRefNumb.equals("")) {
                hasError = true;
                addAdRefNumb.showInvalid();
                addEmployerErrorLine.setText("The ad reference number cannot be blank.");
            } else {
                for (int i = 0; i < controller.getCurrentUser().getEmployers().size(); i++) {
                    if (((VP_Employer)controller.getCurrentUser().getEmployers().get(i)).getEmail().equalsIgnoreCase(thisEmail)) {
                        hasError = true;
                        addEmail.showInvalid();
                        addEmployerErrorLine.setText("An employer with this email already exists.");
                        break;
                    }
                }
                if (!hasError) {
                    matcher = zipPattern.matcher(thisZip);
                    if (!matcher.matches()) {
                        hasError = true;
                        addZip.showValid();
                        addEmployerErrorLine.setText("Employer Zipcode is not in proper form. "
                        + "Zipcodes can only be in form xxxxx or xxxxx-xxxx");
                    }
                }
            }
            if (!hasError) {
                addEmployerErrorLine.hide();
                VP_Employer newEmployer = new VP_Employer(thisEmail, thisFirstName, 
                        thisMiddleName, thisLastName, thisTitle, thisCompany, thisAddress1,
                        thisAddress2, thisCity, thisState, thisZip, thisAdSource,
                        thisAdPosition, thisAdRefNumb);
                try {
                    controller.getDataM().addEmployer(newEmployer);
                    controller.getCurrentUser().getEmployers().add(newEmployer);
                    table.getSelectionModel().clearSelection();
                    addEmail.clear();
                    addFirstName.clear();
                    addMiddleName.clear();
                    addLastName.clear();
                    addTitle.clear();
                    addCompany.clear();
                    addAddress1.clear();
                    addAddress2.clear();
                    addCity.clear();
                    addState.clear();
                    addZip.clear();
                    addAdSource.clear();
                    addAdPosition.clear();
                    addAdRefNumb.clear();
                    table.scrollTo(controller.getCurrentUser().getEmployers().size() - 1);
                } catch (SQLException ex) {
                    controller.errorAlert(3135, ex.getMessage());
                }
            } else {
                VP_Sounds.play(-1);
                addEmployerErrorLine.show();
            }
        });
        deleteBtn.setOnAction((e) -> {
            VP_Employer deletedEmployer = (VP_Employer)(table.getSelectionModel().getSelectedItem());
            if (deletedEmployer != null) {
                VP_Sounds.play(0);
                try {
                    controller.getDataM().deleteEmployer(deletedEmployer);
                    controller.getCurrentUser().getEmployers().remove(deletedEmployer);
                    table.getSelectionModel().clearSelection();
                } catch (SQLException ex) {
                    controller.errorAlert(3136, ex.getMessage());
                }
            }
        });
        screen.addNodes(new Node[]{employersDiv, addEmployerDiv, cancelBtn});
        return screen;
    }

    /**
     * Restores the login page back to its original state.
     *
     * @since 1.0
     */
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
        loginErrorLine.hide();
        accessInstructions.setParaText("Enter the access code that was emailed to "
                + "you when you registered below.");
        accessInstructionsLine.hide();
        accessLine.hide();
    }

    /**
     * Restores the reset password page back to its original state.
     *
     * @since 1.0
     */
    private void resetResetPasswordForms() {
        resetEmail.setText("");
        resetEmail.setDisable(false);
        resetEmail.setEditable(true);
        resetEmail.showValid();
        submitResetBtn.setDisable(false);
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

    /**
     * Restores the registration page back to its original state.
     *
     * @since 1.0
     */
    private void resetRegisterForms() {
        registerEmail.setText("");
        registerEmail.showValid();
        registerPass.setText("");
        registerPass.showValid();
        registerPassConfirm.setText("");
        registerPassConfirm.showValid();
        registerErrorLine.hide();
    }

    /**
     * Updates which documents are selectable for sending based or for printing 
     * based on the completion status of those documents.
     *
     * @since 1.0
     */
    private void updateSelectableDocs() {
        resHTMLcb.setDisable(true);
        resPDFcb.setDisable(true);
        bcHTMLcb.setDisable(true);
        bcPDFcb.setDisable(true);
        clHTMLcb.setDisable(true);
        clPDFcb.setDisable(true);
        printResBtn.setDisable(true);
        printCLBtn.setDisable(true);
        printBCBtn.setDisable(true);
        VP_User thisUser = controller.getCurrentUser();
        if (thisUser.getBcard().hasCompletedBusinessCard()) {
            bcHTMLcb.setDisable(false);
            bcPDFcb.setDisable(false);
            printBCBtn.setDisable(false);
        }
        if (thisUser.getCovlet().hasCompletedCoverLetter()) {
            clHTMLcb.setDisable(false);
            clPDFcb.setDisable(false);
            printCLBtn.setDisable(false);
        }
        if (thisUser.getResume().hasCompletedResume()) {
            resHTMLcb.setDisable(false);
            resPDFcb.setDisable(false);
            printResBtn.setDisable(false);
        }
    }
    
    /**
     * Adjusts the resume status page depending on what the user has completed.
     *
     * @since 1.0
     */
    private void updateResumeStatus() {
        //-------- Initialization Start ----------\\
        VP_Resume thisRes = controller.getCurrentUser().getResume();
        //-------- Initialization End ------------\\
        if (thisRes.hasCompletedObjective()) {
            resObjProgress.setStage(2);
        } else {
            resObjProgress.setStage(0);
        }
        if (thisRes.hasCompletedEducation()) {
            resEduProgress.setStage(2);
        } else {
            resEduProgress.setStage(0);
        }
        if (thisRes.hasCompletedWorkExperience()) {
            resExpProgress.setStage(2);
        } else {
            resExpProgress.setStage(0);
        }
        if (thisRes.hasCompletedQualifications()) {
            resQualProgress.setStage(2);
        } else {
            resQualProgress.setStage(0);
        }
        if (thisRes.hasCompletedHighlights()) {
            resHighProgress.setStage(2);
        } else {
            resHighProgress.setStage(0);
        }
        if (thisRes.hasCompletedLanguages()) {
            resLangProgress.setStage(2);
        } else {
            resLangProgress.setStage(0);
        }
        if (thisRes.hasCompletedSoftware()) {
            resSWProgress.setStage(2);
        } else {
            resSWProgress.setStage(0);
        }
    }

    /**
     * Adjusts the overview page depending on what the user has completed.
     *
     * @since 1.0
     */
    private void updateOverview() {
        //-------- Initialization Start ----------\\
        VP_User thisUser = controller.getCurrentUser();
        //-------- Initialization End ------------\\

        if (thisUser != null) {
            if (!thisUser.hasCompletedPersonalInfo()) {
                infoProgress.setStage(0);
                overviewInfo.setParaText("Welcome to VaqPack! Before you can begin building "
                        + "your resume, business card, and cover letters, we need to gather "
                        + "your personal information. This information remains private to "
                        + "you and is stored for the sole purpose of automatically filling in "
                        + "text in your documents. Click \"Update Personal Information\" below "
                        + "in to get started.");
                for (int i = 1; i < wizardMainButtons.size(); i++) {
                    wizardMainButtons.get(i).setDisable(true);
                }
            } else {
                infoProgress.setStage(2);
                wizardMainButtons.get(1).setDisable(false);
                wizardMainButtons.get(2).setDisable(false);
                wizardMainButtons.get(3).setDisable(false);
                if (thisUser.getResume().hasCompletedResume() || thisUser.getBcard().hasCompletedBusinessCard()
                        || thisUser.getCovlet().hasCompletedCoverLetter()) {
                    overviewInfo.setParaText("You have completed updating your personal information "
                            + "and at least one document. You may update any document or information at "
                            + "any time. You may apply a theme to your completed documents, "
                            + "you may send them via email, or you may print them.");
                    wizardMainButtons.get(4).setDisable(false);
                    wizardMainButtons.get(5).setDisable(false);
                    wizardMainButtons.get(6).setDisable(false);
                } else {

                    overviewInfo.setParaText("You have completed updating your personal information. "
                            + "You may go back to edit this information at any time. Your next step is to "
                            + "complete any document of your choice.");
                }
            }
            if (thisUser.getResume().hasCompletedResume()) {
                resumeProgress.setStage(2);
            } else if (thisUser.getResume().hasStartedResume()) {
                resumeProgress.setStage(1);
            } else {
                resumeProgress.setStage(0);
            }
            if (thisUser.getBcard().hasCompletedBusinessCard()) {
                bcardProgress.setStage(2);
            } else if (thisUser.getBcard().hasStartedBusinessCard()) {
                bcardProgress.setStage(1);
            } else {
                bcardProgress.setStage(0);
            }
            if (thisUser.getCovlet().hasCompletedCoverLetter()) {
                covletProgress.setStage(2);
            } else if (thisUser.getCovlet().hasStartedCoverLetter()) {
                covletProgress.setStage(1);
            } else {
                covletProgress.setStage(0);
            }
        }
    }

    /**
     * Resets fields and bound data when a user cancels.
     *
     * @since 1.0
     */
    protected void cancelActionFunction() {
        VP_Sounds.play(0);
        controller.setChanges(false);
        bcardErrorLine.hide();
        covletEditErrorLine.hide();
        objectiveErrorLine.hide();
        educationErrorLine.hide();
        experienceErrorLine.hide();
        achievementsErrorLine.hide();
        communityErrorLine.hide();
        qualificationsErrorLine.hide();
        highlightsErrorLine.hide();
        languagesErrorLine.hide();
        softwareErrorLine.hide();
        referencesErrorLine.hide();
        personalInfoErrorLine.hide();
        changePassErrorLine.hide();
        addContactErrorLine.hide();
        sendAttachErrorLine.hide();
        changeMysqlErrorLine.hide();
        addEmployerErrorLine.hide();
        resHTMLcb.setSelected(false);
        resPDFcb.setSelected(false);
        bcHTMLcb.setSelected(false);
        bcPDFcb.setSelected(false);
        clHTMLcb.setSelected(false);
        clPDFcb.setSelected(false);
        controller.getCurrentUser().getResume().revert();
        controller.getCurrentUser().getCovlet().revert();
        controller.getCurrentUser().getBcard().revert();
        controller.getCurrentUser().revert();
        Thread backgroundThread = new Thread(new UpdateDynamicTask(0));
        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }

    /**
     * Changes visibility and text values of various components base don recent
     * user activity. Also, this function adds or removes components from
     * various pages depending on what has been added or removed by the user.
     * This function must be called whenever a user logs out, a new user logs
     * in, or a user loads a different cover letter to work on. This function
     * does not update the tree view.
     *
     * @since 1.0
     */
    private void updateDynamicContent(int fromPage) {
        int numbThemes = Integer.parseInt(VP_Theme.Default.COUNT.toString());
        for (int i = 0; i < numbThemes; i++) {
            ((VP_FieldLabel)(((VP_DivisionLine)(resLeftContainer.getChildren().get(i))).getChildren().get(0))).setText("");
            ((VP_FieldLabel)(((VP_DivisionLine)(bcLeftContainer.getChildren().get(i))).getChildren().get(0))).setText("");
            ((VP_FieldLabel)(((VP_DivisionLine)(clLeftContainer.getChildren().get(i))).getChildren().get(0))).setText("");
            if (controller.getCurrentUser().getResume().getThemeId() == ((i + 1) * - 1)) {
                ((VP_FieldLabel)(((VP_DivisionLine)(resLeftContainer.getChildren().get(i))).getChildren().get(0))).setText("Current Theme");
                groupRes.getToggles().get(i).setSelected(true);
            }
            if (controller.getCurrentUser().getBcard().getThemeId() == ((i + 1) * - 1)) {
                ((VP_FieldLabel)(((VP_DivisionLine)(bcLeftContainer.getChildren().get(i))).getChildren().get(0))).setText("Current Theme");
                groupBC.getToggles().get(i).setSelected(true);
            }
            if (controller.getCurrentUser().getCovlet().getThemeId() == ((i + 1) * - 1)) {
                ((VP_FieldLabel)(((VP_DivisionLine)(clLeftContainer.getChildren().get(i))).getChildren().get(0))).setText("Current Theme");
                groupCL.getToggles().get(i).setSelected(true);
            }
        }
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
        deleteCovletBtn.setVisible(false);
        selectCoverLetterLine.hide();
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
        int count = 0;
        SimpleDateFormat oldFormatter = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        for (int i = 0; i < controller.getCurrentUser().getCoverLetterIds().size(); i++) {
            if ((int)(controller.getCurrentUser().getCoverLetterIds().get(i)) > 0) {
                count++;
                String oldDateStr = (String)controller.getCurrentUser().getCoverLetterDates().get(i);
                Date date;
                try {
                    date = oldFormatter.parse(oldDateStr);
                    String simpleDate = formatter.format(date);
                    clChoices.add("#" + count + " " + simpleDate + " ... Employer: " + (String)controller.getCurrentUser().getCoverLetterNames().get(i));
                    coverLetterSelect.getItems().add(clChoices.get(i));
                    if (controller.getCurrentUser().getCurrentCoverLetterIndex() == i) {
                        coverLetterSelect.getSelectionModel().select(i);
                    }
                } catch (ParseException ex) {
                }
            }
        }
        if (count > 0) {
            selectCoverLetterLine.show();
            if (count > 1) {
                deleteCovletBtn.setVisible(true);
            }
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
                    coverLetterEditFields.add(23 + dynamicBody.getChildren().size(), new VP_TextArea(controller));
                    VP_DivisionLine newParaLine = new VP_DivisionLine(new Node[]{newParaLabel, coverLetterEditFields.get(23 + dynamicBody.getChildren().size()), delParaBtn});
                    dynamicBody.getChildren().add(dynamicBody.getChildren().size() - 1, newParaLine);
                    dynamicBody.layout();
                    dynamicBody.getParent().layout();
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
                resumeEducationBox.getChildren().remove(resumeEducationBox.getChildren().size() - 4);
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
                educationFields.add(new VP_TextField(32, 128, controller));
                educationFields.add(new VP_TextField(32, 128, controller));
                educationFields.add(new VP_TextField(32, 128, controller));
                educationFields.add(new VP_TextField(32, 128, controller));
                educationFields.add(new VP_TextField(32, 128, controller));
                educationFields.add(new VP_TextField(32, 128, controller));
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
                resumeExperienceBox.getChildren().remove(resumeExperienceBox.getChildren().size() - 4);
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
                experienceFields.add(new VP_TextField(32, 128, controller));
                experienceFields.add(new VP_TextField(32, 128, controller));
                experienceFields.add(new VP_TextField(32, 128, controller));
                experienceFields.add(new VP_TextField(32, 128, controller));
                experienceFields.add(new VP_TextField(32, 128, controller));
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
                resumeReferencesBox.getChildren().remove(resumeReferencesBox.getChildren().size() - 4);
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
                referencesFields.add(new VP_TextField(32, 45, controller));
                referencesFields.add(new VP_TextField(32, 45, controller));
                referencesFields.add(new VP_TextField(32, 45, controller));
                referencesFields.add(new VP_TextField(32, 128, controller));
                referencesFields.add(new VP_TextField(13, 13, controller));
                referencesFields.add(new VP_TextField(32, 254, controller));
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
                resumeAchievementsBox.getChildren().remove(resumeAchievementsBox.getChildren().size() - 4);
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
                achievementsFields.add(new VP_TextField(32, 128, controller));
                achievementsFields.add(new VP_TextField(32, 128, controller));
                achievementsFields.add(new VP_TextField(32, 128, controller));
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
                resumeCommunityBox.getChildren().remove(resumeCommunityBox.getChildren().size() - 4);
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
                communityFields.add(new VP_TextField(32, 128, controller));
                communityFields.add(new VP_TextField(32, 128, controller));
                communityFields.add(new VP_TextField(32, 128, controller));
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
                resumeQualificationsBox.getChildren().remove(resumeQualificationsBox.getChildren().size() - 4);
                qualificationsFields.remove(thisRes.getNumbQualification());
            }
        } else {
            while ((resumeQualificationsBox.getChildren().size() - 5) < thisRes.getNumbQualification()) {
                int newNumb = resumeQualificationsBox.getChildren().size() - 4;
                VP_PageSubdivision qualificationDiv = new VP_PageSubdivision("QUALIFICATION #" + newNumb, false);
                qualificationsFields.add(new VP_TextField(50, 128, controller));
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
                resumeHighlightsBox.getChildren().remove(resumeHighlightsBox.getChildren().size() - 4);
                highlightsFields.remove(thisRes.getNumbHighlights());
            }
        } else {
            while ((resumeHighlightsBox.getChildren().size() - 5) < thisRes.getNumbHighlights()) {
                int newNumb = resumeHighlightsBox.getChildren().size() - 4;
                VP_PageSubdivision highlightDiv = new VP_PageSubdivision("PERSONAL QUALITY #" + newNumb, false);
                highlightsFields.add(new VP_TextField(50, 128, controller));
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
                resumeLanguagesBox.getChildren().remove(resumeLanguagesBox.getChildren().size() - 4);
                languagesFields.remove(thisRes.getNumbLanguages());
            }
        } else {
            while ((resumeLanguagesBox.getChildren().size() - 5) < thisRes.getNumbLanguages()) {
                int newNumb = resumeLanguagesBox.getChildren().size() - 4;
                VP_PageSubdivision languageDiv = new VP_PageSubdivision("SECONDARY LANGUAGE #" + (newNumb - 1), false);
                languagesFields.add(new VP_TextField(50, 128, controller));
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
        if ((resumeSoftwareBox.getChildren().size() - 5) > thisRes.getNumbSoftware()) {
            while ((resumeSoftwareBox.getChildren().size() - 5) > thisRes.getNumbSoftware()) {
                resumeSoftwareBox.getChildren().remove(resumeSoftwareBox.getChildren().size() - 4);
                softwareFields.remove(thisRes.getNumbSoftware());
            }
        } else {
            while ((resumeSoftwareBox.getChildren().size() - 5) < thisRes.getNumbSoftware()) {
                int newNumb = resumeSoftwareBox.getChildren().size() - 4;
                VP_PageSubdivision softwareDiv = new VP_PageSubdivision("SOFTWARE PRODUCT #" + newNumb, false);
                softwareFields.add(new VP_TextField(50, 128, controller));
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
        controller.updateTree(fromPage);
    }

    /**
     * Saves the currently loaded cover letter. Called when a user submits
     * changes to a cover letter or called when the user selects to add a new
     * cover letter.
     *
     * @param type If type is 1, this means that changes to a cover letter have
     * been made and this function should use field input verification.
     * @since 1.0
     */
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
                covletEditErrorLine.setText("Contact Zipcode is not in proper form. "
                        + "Zipcodes can only be in form xxxxx or xxxxx-xxxx");
            } else if (((VP_TextArea) (coverLetterEditFields.get(25))).textProperty().getValueSafe().equals("")) {
                hasError = true;
                ((VP_TextArea) (coverLetterEditFields.get(25))).showInvalid();
                covletEditErrorLine.setText("The first paragraph of the body cannot be blank.");
            } else if (controller.getCurrentUser().getCovlet().getNumbParagraphs() > 1) {
                for (int i = 0; i < controller.getCurrentUser().getCovlet().getNumbParagraphs(); i++) {
                    if (((VP_TextArea) (coverLetterEditFields.get(25 + i))).textProperty().getValueSafe().equals("")) {
                        hasError = true;
                        ((VP_TextArea) (coverLetterEditFields.get(25 + i))).showInvalid();
                        covletEditErrorLine.setText("Please delete blank paragraphs before submitting.");
                        break;
                    }
                }
            }
        }
        if (hasError) {
            VP_Sounds.play(-1);
            covletEditErrorLine.show();
        } else {
            covletEditErrorLine.hide();
            controller.getCurrentUser().getCovlet().save();
            if (controller.getCurrentUser().getCovlet().hasChanges()) {
                Thread backgroundThread1 = new Thread(new UpdateDynamicTask(7));
                backgroundThread1.setDaemon(true);
                backgroundThread1.start();
                Thread backgroundThread2 = new Thread(new SaveCovLetTask());
                backgroundThread2.setDaemon(true);
                backgroundThread2.start();
            }
            showScreen(3, 0);
        }
    }

    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /**
     * Brings the user to one of the main wizard pages.
     *
     * @since 1.0
     */
    protected class WizardMainAction implements EventHandler<ActionEvent> {

        private final int wizardPage;

        /**
         * Constructor. Stores the page number to send the suer to.
         *
         * @param wizardPage The Screen number or StackPane layer to be viewed.
         * @since 1.0
         */
        public WizardMainAction(int wizardPage) {
            this.wizardPage = wizardPage;
        }

        /**
         * @param event An ActionEvent triggered by a button.
         * @since 1.0
         */
        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            showScreen(wizardPage, 0);
        }
    }

    /**
     * Action event for the cancel button on pages before login or involving
     * login. Calls functions that reset the forms involving login or
     * registration.
     *
     * @since 1.0
     */
    private class CancelActionPrelogin implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent triggered by a cancel button.
         * @since 1.0
         */
        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            resetLoginRegForms();
            resetResetPasswordForms();
            resetRegisterForms();
            showScreen(0, 0);
        }
    }

    /**
     * Reverts any information changed in post-login forms back to the user's
     * saved values and brings the user back to the Overview page.
     *
     * @since 1.0
     */
    private class CancelAction implements EventHandler<ActionEvent> {

        private final int fromPage;

        /**
         * Default Constructor. Sets the fromPage member to 3, representing the
         * overview page of the wizard.
         *
         * @since 1.0
         */
        public CancelAction() {
            this.fromPage = 3;
        }

        /**
         * Constructor. Sets the fromPage member.
         *
         * @param fromPage Represents the current wizard page of the user.
         * @since 1.0
         */
        public CancelAction(int fromPage) {
            this.fromPage = fromPage;
        }

        /**
         * @param event An ActionEvent triggered by a cancel button.
         * @since 1.0
         */
        @Override
        public void handle(ActionEvent event) {
            boolean saving = false;
            // check for changes
            if (controller.hasChanges()) {
                saving = confirmLeavePage();
            }
            if (!saving) {
                cancelActionFunction();
                if (fromPage == 7) {
                    showScreen(6, 0);
                } else if (fromPage == 11) {
                    showScreen(11, 0);
                } else {
                    showScreen(3, 0);
                }
            } else {
                if (fromPage == 4) {

                }
            }
        }
    }

    /**
     * Action event for the 'forgot password?' link on page 0. Switches the
     * center StackPane to show level 1.
     *
     * @since 1.0
     */
    private class ForgotPassAction implements EventHandler<MouseEvent> {

        /**
         * @param event MouseEvent triggered by a clickable label.
         * @since 1.0
         */
        @Override
        public void handle(MouseEvent event) {
            VP_Sounds.play(0);
            resetLoginRegForms();
            showScreen(1, 0);
        }
    }

    /**
     * Action event for the 'need an account?' link on page 0. Switches the
     * center StackPane to show level 2, user registration.
     *
     * @since 1.0
     */
    private class NeedAccountAction implements EventHandler<MouseEvent> {

        /**
         * @param event MouseEvent triggered by a clickable label.
         * @since 1.0
         */
        @Override
        public void handle(MouseEvent event) {
            VP_Sounds.play(0);
            resetLoginRegForms();
            showScreen(2, 0);
        }
    }

    /**
     * Action event for the login button on wizard page 0.
     *
     * @since 1.0
     */
    private class LoginAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent for the login button.
         * @since 1.0
         */
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
                        loginErrorLine.setText("The email address and/or password is "
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
                        loginErrorLine.hide();
                        loginButtonLine.hide();
                        accessInstructionsLine.show();
                        accessLine.show();
                    } else {
                        // user login successful
                        resetLoginRegForms();
                        Thread backgroundThread = new Thread(new UpdateDynamicTask(0));
                        backgroundThread.setDaemon(true);
                        backgroundThread.start();
                        showScreen(3, 0);
                    }
                } catch (SQLException ex) {
                    controller.errorAlert(3107, ex.getMessage());
                } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                    controller.errorAlert(3002, ex.getMessage());
                }
            } else {
                loginErrorLine.setText("The email provided is invalid. Please try again.");
                loginErrorLine.show();
                loginEmail.showInvalid();
            }
        }
    }

    /**
     * Action event for the submit button on wizard page 2 to save user
     * registration credentials. Checks if the provided email is not already
     * associated with an existing account.
     *
     * @since 1.0
     */
    private class RegisterSubmitAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent, triggered by a submit button.
         * @since 1.0
         */
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
                    registerErrorLine.setText("The password is not long enough.");
                    registerErrorLine.show();
                    VP_Sounds.play(-1);
                } else if (!cred[1].equals(cred[2])) {
                    registerPass.showInvalid();
                    registerPassConfirm.showInvalid();
                    registerErrorLine.setText("The passwords do not match.");
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
                            registerErrorLine.setText("This email is already associated "
                                    + "with a VaqPack user.");
                            registerErrorLine.show();
                            VP_Sounds.play(-1);
                        } else if (registerStatus == 0) {
                            registerEmail.showInvalid();
                            registerErrorLine.setText("This email is already associated "
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
                registerErrorLine.setText("The email provided is invalid. Please try again.");
                registerErrorLine.show();
                VP_Sounds.play(-1);
            }
        }
    }

    /**
     * Action event for the resend code button on page 0 to assign a new access
     * code for the user and send it to the user's email.
     *
     * @since 1.0
     */
    private class ResendAccessAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent triggered by the resend button.
         * @since 1.0
         */
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

    /**
     * Action event for the submit button on page 0 which submits the
     * registration access code.
     *
     * @since 1.0
     */
    private class SubmitAccessAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent for a submit button.
         * @since 1.0
         */
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
                    Thread backgroundThread = new Thread(new UpdateDynamicTask(0));
                    backgroundThread.setDaemon(true);
                    backgroundThread.start();
                    showScreen(3, 0);
                } else {
                    loginErrorLine.setText("The registration code is incorrect. Please try again.");
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

    /**
     * Action event for the submit button on page 1 that is for submitting the
     * email to the database manager to check if the user is eligible for
     * password resetting.
     *
     * @since 1.0
     */
    private class SubmitResetAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent, triggered by a submit button.
         * @since 1.0
         */
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
                        resetErrorLine.setText("The provided user does not exists in VaqPack. "
                                + "If the email is correct, you need to register a new account.");
                        resetErrorLine.show();
                        VP_Sounds.play(-1);
                    } else if (userStatus == 1) {
                        resetEmail.showInvalid();
                        resetErrorLine.setText("The password for this email has recently been reset. "
                                + "Passwords may only be reset once every 24 hours.");
                        resetErrorLine.show();
                        VP_Sounds.play(-1);
                    } else {
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
                resetErrorLine.setText("The email provided is invalid. Please try again.");
                resetErrorLine.show();
                resetEmail.showInvalid();
                VP_Sounds.play(-1);
            }
        }
    }

    /**
     * Action event for the submit button on page 1 to submit the code for
     * resetting a user password. This is typically used in the case that the
     * user has forgotten the password and cannot log in.
     *
     * @since 1.0
     */
    private class SubmitResetPassCode implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent, triggered by a submit button.
         * @since 1.0
         */
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
                resetErrorLine.setText("The new password is not long enough.");
                resetErrorLine.show();
                VP_Sounds.play(-1);
            } else if (!cred[1].equals(cred[2])) {
                resetNewPass.showInvalid();
                resetNewPassConfirm.showInvalid();
                resetErrorLine.setText("The passwords do not match.");
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
                        resetErrorLine.setText("The code has expired. Cancel and start the reset process over again.");
                        resetErrorLine.show();
                        VP_Sounds.play(-1);
                    } else if (resetStatus == 0) {
                        resetCode.showInvalid();
                        resetErrorLine.setText("The code is incorrect. Please try again.");
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

    /**
     * Saves any information changed in the Personal Information page and brings
     * the user back to the Overview page.
     *
     * @since 1.0
     */
    private class SubmitPersonalInfoAction implements EventHandler<ActionEvent> {

        private final ArrayList<VP_TextField> personalInfoFields;

        /**
         * Constructor. Loads the input fields for personal information for
         * reference.
         *
         * @param personalInfoFields
         * @since 1.0
         */
        public SubmitPersonalInfoAction(ArrayList<VP_TextField> personalInfoFields) {
            this.personalInfoFields = personalInfoFields;
        }

        /**
         * @param event An ActionEvent, triggered by a submit button.
         * @since 1.0
         */
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
                personalInfoErrorLine.setText("First Name cannot be blank.");
            } else if (personalInfoFields.get(2).textProperty().getValueSafe().equals("")) {
                hasError = true;
                personalInfoFields.get(2).showInvalid();
                personalInfoErrorLine.setText("Last Name cannot be blank.");
            } else if (personalInfoFields.get(3).textProperty().getValueSafe().equals("")) {
                hasError = true;
                personalInfoFields.get(3).showInvalid();
                personalInfoErrorLine.setText("Address Line 1 cannot be blank.");
            } else if (personalInfoFields.get(5).textProperty().getValueSafe().equals("")) {
                hasError = true;
                personalInfoFields.get(5).showInvalid();
                personalInfoErrorLine.setText("City cannot be blank.");
            } else if (personalInfoFields.get(6).textProperty().getValueSafe().equals("")) {
                hasError = true;
                personalInfoFields.get(6).showInvalid();
                personalInfoErrorLine.setText("State cannot be blank.");
            } else if (personalInfoFields.get(6).textProperty().getValueSafe().length() != 2) {
                hasError = true;
                personalInfoFields.get(6).showInvalid();
                personalInfoErrorLine.setText("State must be two characters.");
            } else if (personalInfoFields.get(7).textProperty().getValueSafe().equals("")) {
                hasError = true;
                personalInfoFields.get(7).showInvalid();
                personalInfoErrorLine.setText("Zipcode cannot be blank.");
            } else if (personalInfoFields.get(8).textProperty().getValueSafe().equals("")) {
                hasError = true;
                personalInfoFields.get(8).showInvalid();
                personalInfoErrorLine.setText("Phone cannot be blank.");
            } else {
                matcher = zipPattern.matcher(personalInfoFields.get(7).textProperty().getValueSafe());
                if (!matcher.matches()) {
                    hasError = true;
                    personalInfoFields.get(7).showInvalid();
                    personalInfoErrorLine.setText("Zipcode is not in proper form. "
                            + "Zipcodes can only be in form xxxxx or xxxxx-xxxx");
                } else {
                    matcher = phonePattern.matcher(personalInfoFields.get(8).textProperty().getValueSafe());
                    if (!matcher.matches()) {
                        hasError = true;
                        personalInfoFields.get(8).showInvalid();
                        personalInfoErrorLine.setText("Phone numbers must be in form "
                                + "(xxx)xxx-xxxx");
                    } else {
                        if (personalInfoFields.get(9).textProperty().getValueSafe().length() > 0) {
                            matcher = phonePattern.matcher(personalInfoFields.get(9).textProperty().getValueSafe());
                            if (!matcher.matches()) {
                                hasError = true;
                                personalInfoFields.get(9).showInvalid();
                                personalInfoErrorLine.setText("Phone numbers must be in  form "
                                        + "(xxx)xxx-xxxx");
                            }
                        }
                        if ((!hasError) && (!personalInfoFields.get(10).textProperty().getValueSafe().equals(""))) {
                            hasError = (!controller.getDataM().checkEmail(personalInfoFields.get(10).textProperty().getValueSafe()));
                            if (hasError) {
                                personalInfoFields.get(10).showInvalid();
                                personalInfoErrorLine.setText("Email is not in valid form.");
                            }
                        }
                    }
                }
            }
            if (hasError) {
                VP_Sounds.play(-1);
                personalInfoErrorLine.show();
            } else {
                personalInfoErrorLine.hide();
                controller.getCurrentUser().save();
                Thread backgroundThread1 = new Thread(new UpdateDynamicTask(4));
                backgroundThread1.setDaemon(true);
                backgroundThread1.start();
                showScreen(3, 0);
                if (controller.getCurrentUser().hasChanges()) {
                    Thread backgroundThread2 = new Thread(new SaveUserTask());
                    backgroundThread2.setDaemon(true);
                    backgroundThread2.start();
                }
            }
        }
    }

    /**
     * Saves the user's personal information, entered in wizard page 4.
     *
     * @since 1.0
     */
    private class SubmitObjectiveAction implements EventHandler<ActionEvent> {

        private final VP_TextArea objectiveParagraph;

        /**
         * Constructor. Stores the objective paragraph field for reference;
         *
         * @param objectiveParagraph The cover letter objective paragraph field.
         * @since 1.0
         */
        public SubmitObjectiveAction(VP_TextArea objectiveParagraph) {
            this.objectiveParagraph = objectiveParagraph;
        }

        /**
         * @param event An ActionEvent, triggered by the cover letter submit
         * button.
         * @since 1.0
         */
        @Override
        public void handle(ActionEvent event) {
            boolean hasError = false;
            VP_Sounds.play(0);
            objectiveParagraph.textProperty().setValue(objectiveParagraph.textProperty().getValueSafe().trim());
            if (objectiveParagraph.textProperty().getValueSafe().equals("")) {
                hasError = true;
                objectiveParagraph.showInvalid();
                objectiveErrorLine.setText("The objective cannot be blank in your resume.");
            }
            if (hasError) {
                VP_Sounds.play(-1);
                objectiveErrorLine.show();
            } else {
                objectiveErrorLine.hide();
                controller.getCurrentUser().getResume().save();
                if (controller.getCurrentUser().getResume().hasChanges()) {
                    Thread backgroundThread1 = new Thread(new UpdateDynamicTask(12));
                    backgroundThread1.setDaemon(true);
                    backgroundThread1.start();
                    showScreen(11, 0);
                    Thread backgroundThread2 = new Thread(new SaveResTask(0));
                    backgroundThread2.setDaemon(true);
                    backgroundThread2.start();
                } else {
                    showScreen(11, 0);
                }
            }
        }
    }
    
    protected class UpdateDynamicTask implements Runnable {
        private final int fromPage;
        
        public UpdateDynamicTask(int fromPage) {
            this.fromPage = fromPage;
        }
        @Override
        public void run() {
            Platform.runLater(() -> {updateDynamicContent(fromPage);});
        }
    }
    
    private class SaveUserTask implements Runnable {
        @Override
        public void run() {
            try {
                controller.getDataM().getDbBusy().await();
                controller.getDataM().setDbBusy(new CountDownLatch(1));
                controller.getDataM().saveUserData();
                controller.getCurrentUser().getResume().save();
                controller.getCurrentUser().getBcard().save();
                if (controller.getCurrentUser().getCovlet().getId() != 0) {
                    controller.getCurrentUser().getCovlet().save();
                    Thread backgroundThread1 = new Thread(new SaveCovLetTask());
                    backgroundThread1.setDaemon(true);
                    controller.getDataM().setDbBusy(new CountDownLatch(1));
                    backgroundThread1.start();
                }
                Thread backgroundThread2 = new Thread(new SaveBCardTask());
                backgroundThread2.setDaemon(true);
                Thread backgroundThread3 = new Thread(new SaveResTask(0));
                backgroundThread3.setDaemon(true);
                controller.getDataM().getDbBusy().await();
                controller.getDataM().setDbBusy(new CountDownLatch(1));
                backgroundThread2.start();
                controller.getDataM().getDbBusy().await();
                controller.getDataM().setDbBusy(new CountDownLatch(1));
                backgroundThread3.start();
            } catch (SQLException ex) {
                Platform.runLater(() -> {controller.errorAlert(3113, ex.getMessage());});
            } catch (InterruptedException ex) {
                Platform.runLater(() -> {controller.errorAlert(1204, ex.getMessage());});
            } finally {
                controller.getDataM().getDbBusy().countDown();
            }
        }
    }
    
    private class SaveBCardTask implements Runnable {
        @Override
        public void run() {
            try {
                controller.getDataM().saveBCardData();
            } catch (SQLException ex) {
                Platform.runLater(() -> {controller.errorAlert(3114, ex.getMessage());});
            } catch (TransformerException | ParserConfigurationException | IOException | DocumentException ex) {
                Platform.runLater(() -> {controller.errorAlert(3301, ex.getMessage());});
            }
        }
    }
    
    private class SaveCovLetTask implements Runnable {
        @Override
        public void run() {
        try {
            controller.getDataM().saveCovLetData();
            } catch (SQLException ex) {
                Platform.runLater(() -> {controller.errorAlert(3115, ex.getMessage());});
            } catch (TransformerException | ParserConfigurationException | IOException | DocumentException ex) {
                Platform.runLater(() -> {controller.errorAlert(3302, ex.getMessage());});
            }
        }
    }
    
    private class SaveResTask implements Runnable {
        private final int resumeSection; 
        public SaveResTask(int resumeSection) {
            this.resumeSection = resumeSection;
        }
        @Override
        public void run() {
            try {
                controller.getDataM().saveResume(resumeSection);
            } catch (SQLException ex) {
                Platform.runLater(() -> {controller.errorAlert(3117 + resumeSection, ex.getMessage());});
            } catch (TransformerException | ParserConfigurationException | IOException | DocumentException ex) {
                Platform.runLater(() -> {controller.errorAlert(3303, ex.getMessage());});
            }
        }
    }

    /**
     * Deletes a field from the resume education section.
     *
     * @since 1.0
     */
    private class DeleteEducationAction implements EventHandler<ActionEvent> {

        private final int entryNumber;

        /**
         * Constructor. Stores the entry to be deleted for reference.
         *
         * @param entryNumber
         * @since 1.0
         */
        public DeleteEducationAction(int entryNumber) {
            this.entryNumber = entryNumber;
        }

        /**
         * @param event An ActionEvent, triggered by a delete button.
         * @since 1.0
         */
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
            getResumeEducationBox().getChildren().remove(entryNumber + 1);
            controller.getCurrentUser().getResume().getEducation().remove(entryNumber - 1);
            controller.getCurrentUser().getResume().getEducation().add(new ArrayList());
            for (int i = 0; i < 6; i++) {
                controller.getCurrentUser().getResume().getEducation().get(8).add(new SimpleStringProperty());
            }
            for (int i = 0; i < controller.getCurrentUser().getResume().getNumbEducation(); i++) {
                if (i > 0) {
                    ((Label) (((VP_PageSubdivision) (getResumeEducationBox().getChildren().get(i + 2))).getChildren().get(0))).setText("EDUCATION ENTRY #" + (i + 1));
                    ((VP_Button) (((VP_DivisionLine) ((VP_PageSubdivision) (getResumeEducationBox().getChildren().get(i + 2))).getChildren().get(7))).getChildren().get(0)).setOnAction(new DeleteEducationAction(i + 1));
                }
                for (int ii = 0; ii < 6; ii++) {
                    educationFields.get((6 * i) + ii).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getEducation().get(i).get(ii));
                }
            }
            controller.setChanges(true);
            addEducationBtn.setVisible(true);
            addEducationBtn.setManaged(true);
        }
    }

    /**
     * Adds a field to the resume education section.
     *
     * @since 1.0
     */
    private class AddEducationAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent, triggered by an add entry button.
         * @since 1.0
         */
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
            getResumeEducationBox().getChildren().add(getResumeEducationBox().getChildren().size() - 3, educationDiv);
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

    /**
     * Submits the data in the resume education section.
     *
     * @since 1.0
     */
    private class SubmitEducationAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent, triggered by a submit button.
         * @since 1.0
         */
        @Override
        public void handle(ActionEvent event) {
            boolean hasError = false;
            VP_Sounds.play(0);
            for (int i = 0; i < educationFields.size(); i++) {
                educationFields.get(i).textProperty().setValue(educationFields.get(i).textProperty().getValueSafe().trim());
                if (educationFields.get(i).textProperty().getValueSafe().equals("")) {
                    if (i % 6 == 0) {
                        educationErrorLine.setText("The institution name cannot be blank in education entry #" + (((int) (i / 6)) + 1) + ".");
                        hasError = true;
                    } else if (i % 6 == 1) {
                        educationErrorLine.setText("The institution location cannot be blank in education entry #" + (((int) (i / 6)) + 1) + ".");
                        hasError = true;
                    } else if (i % 6 == 2) {
                        educationErrorLine.setText("The degree, certification, or training field cannot be blank in education entry #" + (((int) (i / 6)) + 1) + ".");
                        hasError = true;
                    } else if (i % 6 == 4) {
                        educationErrorLine.setText("The start date cannot be blank in education entry #" + (((int) (i / 6)) + 1) + ".");
                        hasError = true;
                    } else if (i % 6 == 5) {
                        educationErrorLine.setText("The end date cannot be blank in education entry #" + (((int) (i / 6)) + 1) + ".");
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
                educationErrorLine.hide();
                controller.getCurrentUser().getResume().save();
                if (controller.getCurrentUser().getResume().hasChanges()) {
                    Thread backgroundThread1 = new Thread(new UpdateDynamicTask(13));
                    backgroundThread1.setDaemon(true);
                    backgroundThread1.start();
                    Thread backgroundThread2 = new Thread(new SaveResTask(1));
                    backgroundThread2.setDaemon(true);
                    backgroundThread2.start();
                }
                showScreen(11, 0);
            }
        }
    }

    /**
     * Deletes a field from the resume experience section.
     *
     * @since 1.0
     */
    private class DeleteExperienceAction implements EventHandler<ActionEvent> {

        private final int entryNumber;

        /**
         * Constructor. Stores the entry to be deleted for reference.
         *
         * @param entryNumber
         * @since 1.0
         */
        public DeleteExperienceAction(int entryNumber) {
            this.entryNumber = entryNumber;
        }

        /**
         * @param event An ActionEvent, triggered by a delete button.
         * @since 1.0
         */
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
            getResumeExperienceBox().getChildren().remove(entryNumber + 1);
            controller.getCurrentUser().getResume().getExperience().remove(entryNumber - 1);
            controller.getCurrentUser().getResume().getExperience().add(new ArrayList());
            for (int i = 0; i < 5; i++) {
                controller.getCurrentUser().getResume().getExperience().get(8).add(new SimpleStringProperty());
            }
            for (int i = 0; i < controller.getCurrentUser().getResume().getNumbExperience(); i++) {
                if (i > 0) {
                    ((Label) (((VP_PageSubdivision) (getResumeExperienceBox().getChildren().get(i + 2))).getChildren().get(0))).setText("EXPERIENCE ENTRY #" + (i + 1));
                    ((VP_Button) (((VP_DivisionLine) ((VP_PageSubdivision) (getResumeExperienceBox().getChildren().get(i + 2))).getChildren().get(6))).getChildren().get(0)).setOnAction(new DeleteExperienceAction(i + 1));
                }
                for (int ii = 0; ii < 5; ii++) {
                    experienceFields.get((5 * i) + ii).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getExperience().get(i).get(ii));
                }
            }
            controller.setChanges(true);
            addExperienceBtn.setVisible(true);
            addExperienceBtn.setManaged(true);
        }
    }

    /**
     * Adds a field to the resume experience section.
     *
     * @since 1.0
     */
    private class AddExperienceAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent, triggered by an add entry button.
         * @since 1.0
         */
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
            getResumeExperienceBox().getChildren().add(getResumeExperienceBox().getChildren().size() - 3, experienceDiv);
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

    /**
     * Submits the data in the resume experience section.
     *
     * @since 1.0
     */
    private class SubmitExperienceAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent, triggered by a submit button.
         * @since 1.0
         */
        @Override
        public void handle(ActionEvent event) {
            boolean hasError = false;
            VP_Sounds.play(0);
            for (int i = 0; i < experienceFields.size(); i++) {
                experienceFields.get(i).textProperty().setValue(experienceFields.get(i).textProperty().getValueSafe().trim());
                if (experienceFields.get(i).textProperty().getValueSafe().equals("")) {
                    if (i % 5 == 0) {
                        experienceErrorLine.setText("The institution name cannot be blank in experience entry #" + (((int) (i / 5)) + 1) + ".");
                        hasError = true;
                    } else if (i % 5 == 1) {
                        experienceErrorLine.setText("The institution location cannot be blank in experience entry #" + (((int) (i / 5)) + 1) + ".");
                        hasError = true;
                    } else if (i % 5 == 2) {
                        experienceErrorLine.setText("The position held cannot be blank in experience entry #" + (((int) (i / 5)) + 1) + ".");
                        hasError = true;
                    } else if (i % 5 == 3) {
                        experienceErrorLine.setText("The start date cannot be blank in experience entry #" + (((int) (i / 5)) + 1) + ".");
                        hasError = true;
                    } else if (i % 5 == 4) {
                        experienceErrorLine.setText("The end date cannot be blank in experience entry #" + (((int) (i / 5)) + 1) + ".");
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
                experienceErrorLine.hide();
                controller.getCurrentUser().getResume().save();
                if (controller.getCurrentUser().getResume().hasChanges()) {
                    Thread backgroundThread1 = new Thread(new UpdateDynamicTask(14));
                    backgroundThread1.setDaemon(true);
                    backgroundThread1.start();
                    Thread backgroundThread2 = new Thread(new SaveResTask(2));
                    backgroundThread2.setDaemon(true);
                    backgroundThread2.start();
                }
                showScreen(11, 0);
            }
        }
    }

    /**
     * Deletes a field from the resume achievements section.
     *
     * @since 1.0
     */
    private class DeleteAchievementAction implements EventHandler<ActionEvent> {

        private final int entryNumber;

        /**
         * Constructor. Stores the entry to be deleted for reference.
         *
         * @param entryNumber
         * @since 1.0
         */
        public DeleteAchievementAction(int entryNumber) {
            this.entryNumber = entryNumber;
        }

        /**
         * @param event An ActionEvent, triggered by a delete button.
         * @since 1.0
         */
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
            getResumeAchievementsBox().getChildren().remove(entryNumber + 1);
            controller.getCurrentUser().getResume().getAchievements().remove(entryNumber - 1);
            controller.getCurrentUser().getResume().getAchievements().add(new ArrayList());
            for (int i = 0; i < 3; i++) {
                controller.getCurrentUser().getResume().getAchievements().get(8).add(new SimpleStringProperty());
            }
            for (int i = 0; i < controller.getCurrentUser().getResume().getNumbAchievements(); i++) {
                if (i > 0) {
                    ((Label) (((VP_PageSubdivision) (getResumeAchievementsBox().getChildren().get(i + 2))).getChildren().get(0))).setText("AWARD/ACHIEVEMENT ENTRY #" + (i + 1));
                    ((VP_Button) (((VP_DivisionLine) ((VP_PageSubdivision) (getResumeAchievementsBox().getChildren().get(i + 2))).getChildren().get(4))).getChildren().get(0)).setOnAction(new DeleteAchievementAction(i + 1));
                }
                for (int ii = 0; ii < 3; ii++) {
                    achievementsFields.get((3 * i) + ii).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getAchievements().get(i).get(ii));
                }
            }
            controller.setChanges(true);
            addAchievementBtn.setVisible(true);
            addAchievementBtn.setManaged(true);
        }
    }

    /**
     * Adds a field to the resume achievements section.
     *
     * @since 1.0
     */
    private class AddAchievementAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent, triggered by an add entry button.
         * @since 1.0
         */
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
            getResumeAchievementsBox().getChildren().add(getResumeAchievementsBox().getChildren().size() - 3, achievementDiv);
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

    /**
     * Submits the data in the resume achievements section.
     *
     * @since 1.0
     */
    private class SubmitAchievementsAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent, triggered by a submit button.
         * @since 1.0
         */
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
                    achievementsErrorLine.setText("You cannot have blank achievements in between entries. See achievement #" + (ii));
                    hasError = true;
                    break;
                } else if (count < 3 && count > 0) {
                    achievementsErrorLine.setText("Not all mandatory fields for an achievement are complete for entry #" + (ii + 1));
                    hasError = true;
                    break;
                }
            }
            if (hasError) {
                VP_Sounds.play(-1);
                achievementsErrorLine.show();
            } else {
                achievementsErrorLine.hide();
                controller.getCurrentUser().getResume().save();
                if (controller.getCurrentUser().getResume().hasChanges()) {
                    Thread backgroundThread1 = new Thread(new UpdateDynamicTask(15));
                    backgroundThread1.setDaemon(true);
                    backgroundThread1.start();
                    Thread backgroundThread2 = new Thread(new SaveResTask(3));
                    backgroundThread2.setDaemon(true);
                    backgroundThread2.start();
                }
                showScreen(11, 0);
            }
        }
    }

    /**
     * Deletes a field from the resume community section.
     *
     * @since 1.0
     */
    private class DeleteCommunityAction implements EventHandler<ActionEvent> {

        private final int entryNumber;

        /**
         * Constructor. Stores the entry to be deleted for reference.
         *
         * @param entryNumber
         * @since 1.0
         */
        public DeleteCommunityAction(int entryNumber) {
            this.entryNumber = entryNumber;
        }

        /**
         * @param event An ActionEvent, triggered by a delete button.
         * @since 1.0
         */
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
            getResumeCommunityBox().getChildren().remove(entryNumber + 1);
            controller.getCurrentUser().getResume().getCommunity().remove(entryNumber - 1);
            controller.getCurrentUser().getResume().getCommunity().add(new ArrayList());
            for (int i = 0; i < 3; i++) {
                controller.getCurrentUser().getResume().getCommunity().get(8).add(new SimpleStringProperty());
            }
            for (int i = 0; i < controller.getCurrentUser().getResume().getNumbCommunity(); i++) {
                if (i > 0) {
                    ((Label) (((VP_PageSubdivision) (getResumeCommunityBox().getChildren().get(i + 2))).getChildren().get(0))).setText("EVENT ENTRY #" + (i + 1));
                    ((VP_Button) (((VP_DivisionLine) ((VP_PageSubdivision) (getResumeCommunityBox().getChildren().get(i + 2))).getChildren().get(4))).getChildren().get(0)).setOnAction(new DeleteCommunityAction(i + 1));
                }
                for (int ii = 0; ii < 3; ii++) {
                    communityFields.get((3 * i) + ii).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getCommunity().get(i).get(ii));
                }
            }
            controller.setChanges(true);
            addCommunityBtn.setVisible(true);
            addCommunityBtn.setManaged(true);
        }
    }

    /**
     * Adds a field to the resume community section.
     *
     * @since 1.0
     */
    private class AddCommunityAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent, triggered by an add entry button.
         * @since 1.0
         */
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
            getResumeCommunityBox().getChildren().add(getResumeCommunityBox().getChildren().size() - 3, communityDiv);
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

    /**
     * Submits the data in the resume community section.
     *
     * @since 1.0
     */
    private class SubmitCommunityAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent, triggered by a submit button.
         * @since 1.0
         */
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
                    communityErrorLine.setText("You cannot have blank events in between entries. See entry #" + (ii));
                    hasError = true;
                    break;
                } else if (count < 3 && count > 0) {
                    communityErrorLine.setText("Not all mandatory fields for an event are complete for entry #" + (ii + 1));
                    hasError = true;
                    break;
                }
            }
            if (hasError) {
                VP_Sounds.play(-1);
                communityErrorLine.show();
            } else {
                communityErrorLine.hide();
                controller.getCurrentUser().getResume().save();
                if (controller.getCurrentUser().getResume().hasChanges()) {
                    Thread backgroundThread1 = new Thread(new UpdateDynamicTask(16));
                    backgroundThread1.setDaemon(true);
                    backgroundThread1.start();
                    Thread backgroundThread2 = new Thread(new SaveResTask(4));
                    backgroundThread2.setDaemon(true);
                    backgroundThread2.start();
                }
                showScreen(11, 0);
            }
        }
    }

    /**
     * Deletes a field from the resume qualifications section.
     *
     * @since 1.0
     */
    private class DeleteQualificationAction implements EventHandler<ActionEvent> {

        private final int entryNumber;

        /**
         * Constructor. Stores the entry to be deleted for reference.
         *
         * @param entryNumber
         * @since 1.0
         */
        public DeleteQualificationAction(int entryNumber) {
            this.entryNumber = entryNumber;
        }

        /**
         * @param event An ActionEvent, triggered by a delete button.
         * @since 1.0
         */
        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            for (int i = 0; i < qualificationsFields.size(); i++) {
                qualificationsFields.get(i).textProperty().unbind();
            }
            controller.getCurrentUser().getResume().setNumbQualification(controller.getCurrentUser().getResume().getNumbQualification() - 1);
            qualificationsFields.remove(entryNumber - 1);
            getResumeQualificationsBox().getChildren().remove(entryNumber + 1);
            controller.getCurrentUser().getResume().getQualifications().remove(entryNumber - 1);
            controller.getCurrentUser().getResume().getQualifications().add(new SimpleStringProperty());
            for (int i = 0; i < controller.getCurrentUser().getResume().getNumbQualification(); i++) {
                if (i > 0) {
                    ((Label) (((VP_PageSubdivision) (getResumeQualificationsBox().getChildren().get(i + 2))).getChildren().get(0))).setText("QUALIFICATION #" + (i + 1));
                    ((VP_Button) (((VP_DivisionLine) ((VP_PageSubdivision) (getResumeQualificationsBox().getChildren().get(i + 2))).getChildren().get(2))).getChildren().get(0)).setOnAction(new DeleteQualificationAction(i + 1));
                }
                qualificationsFields.get(i).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getQualifications().get(i));
            }
            controller.setChanges(true);
            addQualificationBtn.setVisible(true);
            addQualificationBtn.setManaged(true);
        }
    }

    /**
     * Adds a field to the resume qualifications section.
     *
     * @since 1.0
     */
    private class AddQualificationAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent, triggered by an add entry button.
         * @since 1.0
         */
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
            getResumeQualificationsBox().getChildren().add(getResumeQualificationsBox().getChildren().size() - 3, qualificationDiv);
            qualificationsFields.get(qualificationsFields.size() - 1).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getQualifications().get(qualificationsFields.size() - 1));
            controller.getCurrentUser().getResume().setNumbQualification(newNumb);
            if (controller.getCurrentUser().getResume().getNumbQualification() == 9) {
                addQualificationBtn.setVisible(false);
                addQualificationBtn.setManaged(false);
            }
        }
    }

    /**
     * Submits the data in the resume qualifications section.
     *
     * @since 1.0
     */
    private class SubmitQualificationsAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent, triggered by a submit button.
         * @since 1.0
         */
        @Override
        public void handle(ActionEvent event) {
            boolean hasError = false;
            VP_Sounds.play(0);
            for (int i = 0; i < qualificationsFields.size(); i++) {
                qualificationsFields.get(i).textProperty().setValue(qualificationsFields.get(i).textProperty().getValueSafe().trim());
                if (qualificationsFields.get(i).textProperty().getValueSafe().equals("")) {
                    qualificationsErrorLine.setText("Qualification #" + (i + 1) + " cannot be blank.");
                    hasError = true;
                    qualificationsFields.get(i).showInvalid();
                    break;
                }
            }
            if (hasError) {
                VP_Sounds.play(-1);
                qualificationsErrorLine.show();
            } else {
                qualificationsErrorLine.hide();
                controller.getCurrentUser().getResume().save();
                if (controller.getCurrentUser().getResume().hasChanges()) {
                    Thread backgroundThread1 = new Thread(new UpdateDynamicTask(17));
                    backgroundThread1.setDaemon(true);
                    backgroundThread1.start();
                    Thread backgroundThread2 = new Thread(new SaveResTask(5));
                    backgroundThread2.setDaemon(true);
                    backgroundThread2.start();
                }
                showScreen(11, 0);
            }
        }
    }

    /**
     * Deletes a field from the resume highlights section.
     *
     * @since 1.0
     */
    private class DeleteHighlightAction implements EventHandler<ActionEvent> {

        private final int entryNumber;

        /**
         * Constructor. Stores the entry to be deleted for reference.
         *
         * @param entryNumber
         * @since 1.0
         */
        public DeleteHighlightAction(int entryNumber) {
            this.entryNumber = entryNumber;
        }

        /**
         * @param event An ActionEvent, triggered by a delete button.
         * @since 1.0
         */
        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            for (int i = 0; i < highlightsFields.size(); i++) {
                highlightsFields.get(i).textProperty().unbind();
            }
            controller.getCurrentUser().getResume().setNumbHighlights(controller.getCurrentUser().getResume().getNumbHighlights() - 1);
            highlightsFields.remove(entryNumber - 1);
            getResumeHighlightsBox().getChildren().remove(entryNumber + 1);
            controller.getCurrentUser().getResume().getHighlights().remove(entryNumber - 1);
            controller.getCurrentUser().getResume().getHighlights().add(new SimpleStringProperty());
            for (int i = 0; i < controller.getCurrentUser().getResume().getNumbHighlights(); i++) {
                if (i > 0) {
                    ((Label) (((VP_PageSubdivision) (getResumeHighlightsBox().getChildren().get(i + 2))).getChildren().get(0))).setText("PERSONAL QUALITY #" + (i + 1));
                    ((VP_Button) (((VP_DivisionLine) ((VP_PageSubdivision) (getResumeHighlightsBox().getChildren().get(i + 2))).getChildren().get(2))).getChildren().get(0)).setOnAction(new DeleteHighlightAction(i + 1));
                }
                highlightsFields.get(i).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getHighlights().get(i));
            }
            controller.setChanges(true);
            addHighlightBtn.setVisible(true);
            addHighlightBtn.setManaged(true);
        }
    }

    /**
     * Adds a field to the resume highlights section.
     *
     * @since 1.0
     */
    private class AddHighlightAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent, triggered by an add entry button.
         * @since 1.0
         */
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
            getResumeHighlightsBox().getChildren().add(getResumeHighlightsBox().getChildren().size() - 3, highlightDiv);
            highlightsFields.get(highlightsFields.size() - 1).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getHighlights().get(highlightsFields.size() - 1));
            controller.getCurrentUser().getResume().setNumbHighlights(newNumb);
            if (controller.getCurrentUser().getResume().getNumbHighlights() == 9) {
                addHighlightBtn.setVisible(false);
                addHighlightBtn.setManaged(false);
            }
        }
    }

    /**
     * Submits the data in the resume highlights section.
     *
     * @since 1.0
     */
    private class SubmitHighlightsAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent, triggered by a submit button.
         * @since 1.0
         */
        @Override
        public void handle(ActionEvent event) {
            boolean hasError = false;
            VP_Sounds.play(0);
            for (int i = 0; i < highlightsFields.size(); i++) {
                highlightsFields.get(i).textProperty().setValue(highlightsFields.get(i).textProperty().getValueSafe().trim());
                if (highlightsFields.get(i).textProperty().getValueSafe().equals("")) {
                    highlightsErrorLine.setText("Highlight #" + (i + 1) + " cannot be blank.");
                    hasError = true;
                    highlightsFields.get(i).showInvalid();
                    break;
                }
            }
            if (hasError) {
                VP_Sounds.play(-1);
                highlightsErrorLine.show();
            } else {
                highlightsErrorLine.hide();
                controller.getCurrentUser().getResume().save();
                if (controller.getCurrentUser().getResume().hasChanges()) {
                    Thread backgroundThread1 = new Thread(new UpdateDynamicTask(18));
                    backgroundThread1.setDaemon(true);
                    backgroundThread1.start();
                    Thread backgroundThread2 = new Thread(new SaveResTask(6));
                    backgroundThread2.setDaemon(true);
                    backgroundThread2.start();
                }
                showScreen(11, 0);
            }
        }
    }

    /**
     * Deletes a field from the resume languages section.
     *
     * @since 1.0
     */
    private class DeleteLanguageAction implements EventHandler<ActionEvent> {

        private final int entryNumber;

        /**
         * Constructor. Stores the entry to be deleted for reference.
         *
         * @param entryNumber
         * @since 1.0
         */
        public DeleteLanguageAction(int entryNumber) {
            this.entryNumber = entryNumber;
        }

        /**
         * @param event An ActionEvent, triggered by a delete button.
         * @since 1.0
         */
        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            for (int i = 0; i < languagesFields.size(); i++) {
                languagesFields.get(i).textProperty().unbind();
            }
            controller.getCurrentUser().getResume().setNumbLanguages(controller.getCurrentUser().getResume().getNumbLanguages() - 1);
            languagesFields.remove(entryNumber - 1);
            getResumeLanguagesBox().getChildren().remove(entryNumber + 1);
            controller.getCurrentUser().getResume().getLanguages().remove(entryNumber - 1);
            controller.getCurrentUser().getResume().getLanguages().add(new SimpleStringProperty());
            for (int i = 0; i < controller.getCurrentUser().getResume().getNumbLanguages(); i++) {
                if (i > 0) {
                    ((Label) (((VP_PageSubdivision) (getResumeLanguagesBox().getChildren().get(i + 2))).getChildren().get(0))).setText("SECONDARY LANGUAGE #" + i);
                    ((VP_Button) (((VP_DivisionLine) ((VP_PageSubdivision) (getResumeLanguagesBox().getChildren().get(i + 2))).getChildren().get(2))).getChildren().get(0)).setOnAction(new DeleteLanguageAction(i + 1));
                }
                languagesFields.get(i).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getLanguages().get(i));
            }
            controller.setChanges(true);
            addLanguageBtn.setVisible(true);
            addLanguageBtn.setManaged(true);
        }
    }

    /**
     * Adds a field to the resume languages section.
     *
     * @since 1.0
     */
    private class AddLanguageAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent, triggered by an add entry button.
         * @since 1.0
         */
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
            getResumeLanguagesBox().getChildren().add(getResumeLanguagesBox().getChildren().size() - 3, languageDiv);
            languagesFields.get(languagesFields.size() - 1).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getLanguages().get(languagesFields.size() - 1));
            controller.getCurrentUser().getResume().setNumbLanguages(newNumb);
            if (controller.getCurrentUser().getResume().getNumbLanguages() == 9) {
                addLanguageBtn.setVisible(false);
                addLanguageBtn.setManaged(false);
            }
        }
    }

    /**
     * Submits the data in the resume languages section.
     *
     * @since 1.0
     */
    private class SubmitLanguagesAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent, triggered by a submit button.
         * @since 1.0
         */
        @Override
        public void handle(ActionEvent event) {
            boolean hasError = false;
            VP_Sounds.play(0);
            for (int i = 0; i < languagesFields.size(); i++) {
                languagesFields.get(i).textProperty().setValue(languagesFields.get(i).textProperty().getValueSafe().trim());
                if (languagesFields.get(i).textProperty().getValueSafe().equals("")) {
                    if (i == 0) {
                        languagesErrorLine.setText("Primary Language cannot be blank.");
                    } else {
                        languagesErrorLine.setText("Secondary Language #" + i + " cannot be blank.");
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
                languagesErrorLine.hide();
                controller.getCurrentUser().getResume().save();
                if (controller.getCurrentUser().getResume().hasChanges()) {
                    Thread backgroundThread1 = new Thread(new UpdateDynamicTask(19));
                    backgroundThread1.setDaemon(true);
                    backgroundThread1.start();
                    Thread backgroundThread2 = new Thread(new SaveResTask(7));
                    backgroundThread2.setDaemon(true);
                    backgroundThread2.start();
                }
                showScreen(11, 0);
            }
        }
    }

    /**
     * Deletes a field from the resume software section.
     *
     * @since 1.0
     */
    private class DeleteSoftwareAction implements EventHandler<ActionEvent> {

        private final int entryNumber;

        /**
         * Constructor. Stores the entry to be deleted for reference.
         *
         * @param entryNumber
         * @since 1.0
         */
        public DeleteSoftwareAction(int entryNumber) {
            this.entryNumber = entryNumber;
        }

        /**
         * @param event An ActionEvent, triggered by a delete button.
         * @since 1.0
         */
        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            for (int i = 0; i < softwareFields.size(); i++) {
                softwareFields.get(i).textProperty().unbind();
            }
            controller.getCurrentUser().getResume().setNumbSoftware(controller.getCurrentUser().getResume().getNumbSoftware() - 1);
            softwareFields.remove(entryNumber - 1);
            getResumeSoftwareBox().getChildren().remove(entryNumber + 1);
            controller.getCurrentUser().getResume().getSoftware().remove(entryNumber - 1);
            controller.getCurrentUser().getResume().getSoftware().add(new SimpleStringProperty());
            for (int i = 0; i < controller.getCurrentUser().getResume().getNumbSoftware(); i++) {
                if (i > 0) {
                    ((Label) (((VP_PageSubdivision) (getResumeSoftwareBox().getChildren().get(i + 2))).getChildren().get(0))).setText("SOFTWARE PRODUCT #" + (i + 1));
                    ((VP_Button) (((VP_DivisionLine) ((VP_PageSubdivision) (getResumeSoftwareBox().getChildren().get(i + 2))).getChildren().get(2))).getChildren().get(0)).setOnAction(new DeleteSoftwareAction(i + 1));
                }
                softwareFields.get(i).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getSoftware().get(i));
            }
            controller.setChanges(true);
            addSoftwareBtn.setVisible(true);
            addSoftwareBtn.setManaged(true);
        }
    }

    /**
     * Adds a field to the resume software section.
     *
     * @since 1.0
     */
    private class AddSoftwareAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent, triggered by an add entry button.
         * @since 1.0
         */
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
            getResumeSoftwareBox().getChildren().add(getResumeSoftwareBox().getChildren().size() - 3, softwareDiv);
            softwareFields.get(softwareFields.size() - 1).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getSoftware().get(softwareFields.size() - 1));
            controller.getCurrentUser().getResume().setNumbSoftware(newNumb);
            if (controller.getCurrentUser().getResume().getNumbSoftware() == 9) {
                addSoftwareBtn.setVisible(false);
                addSoftwareBtn.setManaged(false);
            }
        }
    }

    /**
     * Submits the data in the resume software section.
     *
     * @since 1.0
     */
    private class SubmitSoftwareAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent, triggered by a submit button.
         * @since 1.0
         */
        @Override
        public void handle(ActionEvent event) {
            boolean hasError = false;
            VP_Sounds.play(0);
            for (int i = 0; i < softwareFields.size(); i++) {
                softwareFields.get(i).textProperty().setValue(softwareFields.get(i).textProperty().getValueSafe().trim());
                if (softwareFields.get(i).textProperty().getValueSafe().equals("")) {
                    softwareErrorLine.setText("Software Product #" + (i + 1) + " cannot be blank.");
                    hasError = true;
                    softwareFields.get(i).showInvalid();
                    break;
                }
            }
            if (hasError) {
                VP_Sounds.play(-1);
                softwareErrorLine.show();
            } else {
                softwareErrorLine.hide();
                controller.getCurrentUser().getResume().save();
                if (controller.getCurrentUser().getResume().hasChanges()) {
                    Thread backgroundThread1 = new Thread(new UpdateDynamicTask(20));
                    backgroundThread1.setDaemon(true);
                    backgroundThread1.start();
                    Thread backgroundThread2 = new Thread(new SaveResTask(8));
                    backgroundThread2.setDaemon(true);
                    backgroundThread2.start();
                }
                showScreen(11, 0);
            }
        }
    }

    /**
     * Deletes a field from the resume references section.
     *
     * @since 1.0
     */
    private class DeleteReferenceAction implements EventHandler<ActionEvent> {

        private final int entryNumber;

        /**
         * Constructor. Stores the entry to be deleted for reference.
         *
         * @param entryNumber
         * @since 1.0
         */
        public DeleteReferenceAction(int entryNumber) {
            this.entryNumber = entryNumber;
        }

        /**
         * @param event An ActionEvent, triggered by a delete button.
         * @since 1.0
         */
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
            getResumeReferencesBox().getChildren().remove(entryNumber + 1);
            controller.getCurrentUser().getResume().getReferences().remove(entryNumber - 1);
            controller.getCurrentUser().getResume().getReferences().add(new ArrayList());
            for (int i = 0; i < 6; i++) {
                controller.getCurrentUser().getResume().getReferences().get(8).add(new SimpleStringProperty());
            }
            for (int i = 0; i < controller.getCurrentUser().getResume().getNumbReferences(); i++) {
                if (i > 0) {
                    ((Label) (((VP_PageSubdivision) (getResumeReferencesBox().getChildren().get(i + 2))).getChildren().get(0))).setText("REFERENCE #" + (i + 1));
                    ((VP_Button) (((VP_DivisionLine) ((VP_PageSubdivision) (getResumeReferencesBox().getChildren().get(i + 2))).getChildren().get(7))).getChildren().get(0)).setOnAction(new DeleteReferenceAction(i + 1));
                }
                for (int ii = 0; ii < 6; ii++) {
                    referencesFields.get((6 * i) + ii).textProperty().bindBidirectional(controller.getCurrentUser().getResume().getReferences().get(i).get(ii));
                }
            }
            controller.setChanges(true);
            addReferenceBtn.setVisible(true);
            addReferenceBtn.setManaged(true);
        }
    }

    /**
     * Adds a field to the resume references section.
     *
     * @since 1.0
     */
    private class AddReferenceAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent, triggered by an add entry button.
         * @since 1.0
         */
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
            getResumeReferencesBox().getChildren().add(getResumeReferencesBox().getChildren().size() - 3, referenceDiv);
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

    /**
     * Submits the data in the resume references section.
     *
     * @since 1.0
     */
    private class SubmitReferencesAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent, triggered by a submit button.
         * @since 1.0
         */
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
                                    referencesErrorLine.setText("Email is not in valid form in reference #" + (ii + 1) + ".");
                                }
                            }
                        } else {
                            count += 1;
                            if (i % 6 == 4) {
                                matcher = phonePattern.matcher(referencesFields.get(i).textProperty().getValueSafe());
                                if (!matcher.matches()) {
                                    hasError = true;
                                    referencesFields.get(i).showInvalid();
                                    referencesErrorLine.setText("Phone numbers must be in form "
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
                        referencesErrorLine.setText("You cannot have blank references in between entries. See reference #" + (ii));
                        hasError = true;
                        break;
                    } else if ((count < 4 && count > 0) || (count == 0 && optionalCount > 0)) {
                        referencesErrorLine.setText("Not all mandatory fields for a reference are complete for entry #" + (ii + 1));
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
                referencesErrorLine.hide();
                controller.getCurrentUser().getResume().save();
                if (controller.getCurrentUser().getResume().hasChanges()) {
                    Thread backgroundThread1 = new Thread(new UpdateDynamicTask(21));
                    backgroundThread1.setDaemon(true);
                    backgroundThread1.start();
                    Thread backgroundThread2 = new Thread(new SaveResTask(9));
                    backgroundThread2.setDaemon(true);
                    backgroundThread2.start();
                } else {
                    showScreen(11, 0);
                }
            }
        }
    }

    /**
     * Clears the currently loaded cover letter and imports the selected one.
     *
     * @since 1.0
     */
    private class LoadCoverLetterAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent, triggered by the load cover letter
         * button.
         * @since 1.0
         */
        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            int clID;
            int selectedLetter = coverLetterSelect.getSelectionModel().getSelectedIndex();
            controller.getCurrentUser().getCovlet().clear();
            clID = (int)controller.getCurrentUser().getCoverLetterIds().get(selectedLetter);
            controller.getCurrentUser().setCurrentCoverLetterIndex(selectedLetter);
            try {
                controller.getDataM().loadCovLet(clID);
                Thread backgroundThread = new Thread(new UpdateDynamicTask(6));
                backgroundThread.setDaemon(true);
                backgroundThread.start();
                showScreen(7, 0);

            } catch (SQLException ex) {
                controller.errorAlert(3116, ex.getMessage());
            }
        }
    }

    /**
     * Stars a new, empty cover letter for the user.
     *
     * @since 1.0
     */
    private class StartNewCoverLetter implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent, triggered by the add new cover letter
         * button.
         * @since 1.0
         */
        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            if ((int)controller.getCurrentUser().getCoverLetterIds().get(0) == 0
                    && controller.getCurrentUser().getCoverLetterIds().size() == 1) {
                controller.getCurrentUser().getCovlet().clear();
                controller.getCurrentUser().setCurrentCoverLetterIndex(0);
            } else {
                controller.getCurrentUser().getCovlet().clear();
                controller.getCurrentUser().getCoverLetterIds().add(0);
                SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM d, yyyy");
                String formattedDate = formatter.format(new Date());
                controller.getCurrentUser().getCoverLetterDates().add(formattedDate);
                controller.getCurrentUser().getCoverLetterNames().add("");
                controller.getCurrentUser().setCurrentCoverLetterIndex(controller.getCurrentUser().getCoverLetterIds().size() - 1);
                controller.getCurrentUser().getCovlet().save();
            }
            Thread backgroundThread = new Thread(new UpdateDynamicTask(6));
            backgroundThread.setDaemon(true);
            backgroundThread.start();
            saveCovLetFunction(0);
            showScreen(7, 0);
        }
    }

    /**
     * Adds a paragraph to the resume cover letter section.
     *
     * @since 1.0
     */
    private class AddParagraphAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent, triggered by an add paragraph button.
         * @since 1.0
         */
        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            for (int i = 0; i < controller.getCurrentUser().getCovlet().getNumbParagraphs(); i++) {
                ((VP_TextArea) (coverLetterEditFields.get(25 + i))).textProperty().unbind();
            }
            int newParaNumb = controller.getCurrentUser().getCovlet().getNumbParagraphs() + 1;
            VP_FieldLabel newParaLabel = new VP_FieldLabel("paragraph " + newParaNumb + ":", 140);
            VP_Button delParaBtn = new VP_Button("Delete", new DeleteParagraphAction(newParaNumb));
            coverLetterEditFields.add(24 + newParaNumb, new VP_TextArea(controller));
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

    /**
     * Deletes a paragraph from the resume cover letter section.
     *
     * @since 1.0
     */
    private class DeleteParagraphAction implements EventHandler<ActionEvent> {

        private final int paragraphNumber;

        /**
         * Constructor. Stores the paragraph field number for reference.
         *
         * @param paragraphNumber
         * @since 1.0
         */
        public DeleteParagraphAction(int paragraphNumber) {
            this.paragraphNumber = paragraphNumber;
        }

        /**
         * @param event An ActionEvent, triggered by a delete paragraph button.
         * @since 1.0
         */
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
            controller.setChanges(true);
            addParagraphLine.show();
        }
    }

    /**
     * Saves any information changed in the Business Card page and brings the
     * user back to the Overview page.
     *
     * @since 1.0
     */
    private class SubmitCovLetEditAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent, triggered by the submit cover letter
         * button.
         * @since 1.0
         */
        @Override
        public void handle(ActionEvent event) {
            saveCovLetFunction(1);
        }
    }

    /**
     * Updates the date on the cover letter from its previously stored value to
     * a new value representing the current date.
     *
     * @since 1.0
     */
    private class UpdateDateAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent, triggered by the update button.
         * @since 1.0
         */
        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            SimpleDateFormat formatter1 = new SimpleDateFormat("EEEE, MMMM d, yyyy");
            String formattedDate1 = formatter1.format(new Date());
            dateValueLabel.setText(formattedDate1);
            controller.setChanges(true);
        }
    }

    /**
     * Saves any information changed in the Business Card page and brings the
     * user back to the Overview page.
     *
     * @since 1.0
     */
    private class SubmitBCardAction implements EventHandler<ActionEvent> {

        private final ArrayList<VP_TextField> businessCardFields;

        /**
         * Constructor. Stores the business card fields for reference.
         *
         * @param businessCardFields The list of fields of the business card.
         * @since 1.0
         */
        public SubmitBCardAction(ArrayList<VP_TextField> businessCardFields) {
            this.businessCardFields = businessCardFields;
        }

        /**
         * @param event An ActionEvent, triggered by the submit business card
         * button.
         * @since 1.0
         */
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
                    bcardErrorLine.setText("The web address is not in valid form. Be sure to include whether "
                            + "the address is http or https");
                }
            }
            if (hasError) {
                VP_Sounds.play(-1);
                bcardErrorLine.show();
            } else {
                bcardErrorLine.hide();
                controller.getCurrentUser().getBcard().save();
                if (controller.getCurrentUser().getBcard().hasChanges()) {
                    Thread backgroundThread1 = new Thread(new UpdateDynamicTask(5));
                    backgroundThread1.setDaemon(true);
                    backgroundThread1.start();
                    showScreen(3, 0);
                    Thread backgroundThread2 = new Thread(new SaveBCardTask());
                    backgroundThread2.setDaemon(true);
                    backgroundThread2.start();
                } else {
                    showScreen(3, 0);
                }
            }
        }
    }

    /**
     * Action event for the submit button on wizard page 22 to save a user's new
     * password. This is different than resetting a password. In this case, the
     * user is already logged in but would like to change the password.
     * Therefore, only verification that the new password meets requirements is
     * needed. The user must still enter in the old password to avoid the
     * problem of other people attempting to change the logged in user's
     * password if that user happens to step away.
     *
     * @since 1.0
     */
    private class ChangePassAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent, triggered by a submit button.
         * @since 1.0
         */
        @Override
        public void handle(ActionEvent event) {
            //-------- Initialization Start ----------\\
            String[] cred = {oldPass.getText(), newPass.getText(), newPassConfirm.getText()};
            //-------- Initialization End ------------\\
            VP_Sounds.play(0);
            changePassErrorLine.setText("");
            changePassErrorLine.hide();
            if (cred[0].length() < controller.getUSER_PASSWORD_MINIMUM()) {
                oldPass.showInvalid();
                changePassErrorLine.setText("The old password is incorrect. Please try again.");
                changePassErrorLine.show();
                VP_Sounds.play(-1);
            } else if (cred[1].length() < controller.getUSER_PASSWORD_MINIMUM()) {
                newPass.showInvalid();
                newPassConfirm.showInvalid();
                changePassErrorLine.setText("The new password is not long enough. Please try again.");
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
                            Label passChangedLabel = new Label("Use your new password the next time you log in.");
                            passChangedLabel.setPadding(new Insets(50, 20, 50, 20));
                            passChanged.getDialogShell().add(passChangedLabel, 0, 0);
                            passChanged.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
                            passChanged.showAndWait();
                            cancelActionFunction();
                            showScreen(3, 0);
                        } else {
                            newPass.showInvalid();
                            newPassConfirm.showInvalid();
                            changePassErrorLine.setText("The new passwords do not match each other.");
                            changePassErrorLine.show();
                            VP_Sounds.play(-1);
                        }
                    } else {
                        oldPass.showInvalid();
                        changePassErrorLine.setText("The old password is incorrect. Please try again.");
                        changePassErrorLine.show();
                        VP_Sounds.play(-1);
                    }
                } catch (SQLException ex) {
                    controller.errorAlert(3127, ex.getMessage());
                } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                    controller.errorAlert(3007, ex.getMessage());
                }
            }
        }
    }
    
    private class ChangeMySQLAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent, triggered by a submit button.
         * @since 1.0
         */
        @Override
        public void handle(ActionEvent event) {
            //-------- Initialization Start ----------\\
            String[] cred = {adminPassMysql.getText(), newMysqlURL.getText(), newMysqlPort.getText()};
            //-------- Initialization End ------------\\
            VP_Sounds.play(0);
            changeMysqlErrorLine.hide();
            if (cred[0].length() < controller.getUSER_PASSWORD_MINIMUM()) {
                adminPassMysql.showInvalid();
                changeMysqlErrorLine.setText("Your password is incorrect. Please try again.");
                changeMysqlErrorLine.show();
                VP_Sounds.play(-1);
            } else if (cred[1] == null || cred[1].equals("")) {
                newMysqlURL.showInvalid();
                changeMysqlErrorLine.setText("New MySQL URL cannot be blank.");
                changeMysqlErrorLine.show();
                VP_Sounds.play(-1);
            } else if (cred[2] == null || cred[1].equals("")) {
                newMysqlPort.showInvalid();
                changeMysqlErrorLine.setText("New MySQL Port cannot be blank.");
                changeMysqlErrorLine.show();
                VP_Sounds.play(-1);
            } else  {
                try {
                    if (controller.getDataM().checkCurrentPassword(cred[0])) {
                        controller.getDataM().storeDBLocation(new String[]{cred[1], cred[2]});
                        System.exit(2);
                    } else {
                        adminPassMysql.showInvalid();
                        changeMysqlErrorLine.setText("Your password is incorrect. Please try again.");
                        changeMysqlErrorLine.show();
                        VP_Sounds.play(-1);
                    }
                } catch (SQLException ex) {
                    controller.errorAlert(3131, ex.getMessage());
                } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                    controller.errorAlert(3132, ex.getMessage());
                } catch (IOException ex) {
                    controller.errorAlert(3207, ex.getMessage());
                }
            }
        }
    }
    
    private class ApplyTheme implements EventHandler<ActionEvent> {
        private final ToggleGroup group;
        private final int type;
        private final VBox container;
        
        public ApplyTheme(ToggleGroup group, int type, VBox container) {
            this.group = group;
            this.type = type;
            this.container = container;
        }

        @Override
        public void handle(ActionEvent event) {
            if (group.getSelectedToggle() != null) {
                if (type == 0) {
                    controller.getCurrentUser().getResume().setThemeId((int)(group.getSelectedToggle().getUserData()) * -1);
                    controller.getCurrentUser().getResume().save();
                    Thread backgroundThread = new Thread(new SaveResTask(10));
                    backgroundThread.setDaemon(true);
                    backgroundThread.start();
                } else if (type == 1) {
                    controller.getCurrentUser().getBcard().setThemeId((int)(group.getSelectedToggle().getUserData()) * -1);
                    controller.getCurrentUser().getBcard().save();
                    Thread backgroundThread = new Thread(new SaveBCardTask());
                    backgroundThread.setDaemon(true);
                    backgroundThread.start();
                    
                } else if (type == 2) {
                    controller.getCurrentUser().getCovlet().setThemeId((int)(group.getSelectedToggle().getUserData()) * -1);
                    controller.getCurrentUser().getCovlet().save();
                    Thread backgroundThread = new Thread(new SaveCovLetTask());
                    backgroundThread.setDaemon(true);
                    backgroundThread.start();
                }
                int numbThemes = Integer.parseInt(VP_Theme.Default.COUNT.toString());
                for (int i = 0; i < numbThemes; i++) {
                    ((VP_FieldLabel)(((VP_DivisionLine)(container.getChildren().get(i))).getChildren().get(0))).setText("");
                    if (i == ((int)(group.getSelectedToggle().getUserData())) - 1) {
                        ((VP_FieldLabel)(((VP_DivisionLine)(container.getChildren().get(i))).getChildren().get(0))).setText("Current Theme");
                    }
                }
            }
        }
    }
    
    private class ThemeToggle implements ChangeListener<Toggle> {
        private final ToggleGroup group;
        private final int type;
        private final VP_PageSubdivision container;
        
        public ThemeToggle(ToggleGroup group, int type, VP_PageSubdivision container) {
            this.group = group;
            this.type = type;
            this.container = container;
        }
        
        @Override
        public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
            if (group.getSelectedToggle() != null) {
                VP_Previewer previewer = new VP_Previewer();
                WebView preview;
                try {
                    preview = previewer.buildPreview(type, (int)(group.getSelectedToggle().getUserData()) * -1);
                    preview.setPrefSize(400, 200);
                    container.getChildren().set(1, preview);

                } catch (IOException ex) {
                    // just eat it.
                }
            }
        }
    }
    
    private class PrintAction implements EventHandler<ActionEvent> {
        private final Thread backgroundThread;
        
        public PrintAction(int type) {
            backgroundThread = new Thread(new PrintTask(type));
        }
        
        /**
         * @param event An ActionEvent, triggered by a print button.
         * @since 1.0
         */
        @Override
        public void handle(ActionEvent event) {
            this.backgroundThread.setDaemon(false);
            this.backgroundThread.start();
            VP_Dialog printDialog = new VP_Dialog("Printing");
            printDialog.setHeaderText("");
            VP_Paragraph printContent = new VP_Paragraph("VaqPack is attempting to print your "
                    + "document. This may take a few moments.");
            printContent.setPadding(new Insets(50, 20, 50, 20));
            printDialog.getDialogShell().add(printContent, 0, 0);
            printDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
            printDialog.showAndWait();
        }
    }
    
    private class PrintTask implements Runnable {
        private final int type;
        public PrintTask(int type) {
            this.type = type;
        }
        @Override
        public void run() {
            try {
                controller.getDataM().getDbBusy().await();
                controller.getDataM().setDbBusy(new CountDownLatch(1));
                controller.getDataM().printDocument(type);
            } catch (SQLException ex) {
                Platform.runLater(() -> {controller.errorAlert(3114, ex.getMessage());});
            } catch (IOException ex) {
                Platform.runLater(() -> {controller.errorAlert(3304, ex.getMessage());});
            } catch (InterruptedException ex) {
                Platform.runLater(() -> {controller.errorAlert(1205, ex.getMessage());});
            } finally {
                controller.getDataM().getDbBusy().countDown();
            }
        }
    }
    
    private class DeleteCovletAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            VP_Sounds.play(0);
            deleteCovletBtn.setVisible(false);
            int index = coverLetterSelect.getSelectionModel().getSelectedIndex();
            int clId = (int)controller.getCurrentUser().getCoverLetterIds().get(index);
            int newId = 0;
            controller.getCurrentUser().getCoverLetterIds().remove(index);
            controller.getCurrentUser().getCoverLetterDates().remove(index);
            controller.getCurrentUser().getCoverLetterNames().remove(index);
            if (index <= controller.getCurrentUser().getCurrentCoverLetterIndex()) {
                controller.getCurrentUser().setCurrentCoverLetterIndex(controller.getCurrentUser().getCurrentCoverLetterIndex() - 1);
                if (controller.getCurrentUser().getCurrentCoverLetterIndex() < 0) {
                    controller.getCurrentUser().setCurrentCoverLetterIndex(0);
                }
            }
            newId = (int)controller.getCurrentUser().getCoverLetterIds().get(controller.getCurrentUser().getCurrentCoverLetterIndex());
            if (clId > 0) {
                try {
                    controller.getDataM().deleteCovlet(clId);
                } catch (SQLException ex) {
                    controller.errorAlert(3134, ex.getMessage());
                }
            }
            try {
                controller.getDataM().loadCovLet(newId);
                Thread backgroundThread = new Thread(new UpdateDynamicTask(6));
                backgroundThread.setDaemon(true);
                backgroundThread.start();
            } catch (SQLException ex) {
                controller.errorAlert(3116, ex.getMessage());
            }
        }
    }

    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/
    /**
     * Accessor method.
     *
     * @return The list of Business Card fields.
     * @since 1.0
     */
    protected ArrayList<VP_PageSubdivision> getBcNodes() {
        return bcNodes;
    }

    /**
     * Accessor method.
     *
     * @return The list of Cover letter fields.
     * @since 1.0
     */
    protected ArrayList<VP_PageSubdivision> getClNodes() {
        return clNodes;
    }

    /**
     * Accessor method.
     *
     * @return The container object for the resume education fields.
     * @since 1.0
     */
    protected VP_PageDivision getResumeEducationBox() {
        return resumeEducationBox;
    }

    /**
     * Accessor method.
     *
     * @return The container object for the resume experiences fields.
     * @since 1.0
     */
    protected VP_PageDivision getResumeExperienceBox() {
        return resumeExperienceBox;
    }

    /**
     * Accessor method.
     *
     * @return The container object for the resume achievements fields.
     * @since 1.0
     */
    protected VP_PageDivision getResumeAchievementsBox() {
        return resumeAchievementsBox;
    }

    /**
     * Accessor method.
     *
     * @return The container object for the resume community fields.
     * @since 1.0
     */
    protected VP_PageDivision getResumeCommunityBox() {
        return resumeCommunityBox;
    }

    /**
     * Accessor method.
     *
     * @return The container object for the resume qualifications fields.
     * @since 1.0
     */
    protected VP_PageDivision getResumeQualificationsBox() {
        return resumeQualificationsBox;
    }

    /**
     * Accessor method.
     *
     * @return The container object for the resume highlights fields.
     * @since 1.0
     */
    protected VP_PageDivision getResumeHighlightsBox() {
        return resumeHighlightsBox;
    }

    /**
     * Accessor method.
     *
     * @return The container object for the resume languages fields.
     * @since 1.0
     */
    protected VP_PageDivision getResumeLanguagesBox() {
        return resumeLanguagesBox;
    }

    /**
     * Accessor method.
     *
     * @return The container object for the resume software fields.
     * @since 1.0
     */
    protected VP_PageDivision getResumeSoftwareBox() {
        return resumeSoftwareBox;
    }

    /**
     * Accessor method.
     *
     * @return The container object for the resume references fields.
     * @since 1.0
     */
    protected VP_PageDivision getResumeReferencesBox() {
        return resumeReferencesBox;
    }

    /**
     * Accessor method.
     *
     * @return The container object for the cover letter fields.
     * @since 1.0
     */
    protected VP_PageDivision getCovLetEditBox() {
        return covLetEditBox;
    }
}
