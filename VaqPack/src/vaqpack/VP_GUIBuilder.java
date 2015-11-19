/*-----------------------------------------------------------------------------*
 * VP_GUIBuilder.java
 * - Constructs the GUI and maintains references to key components
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 1600
 *-----------------------------------------------------------------------------*/
package vaqpack;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class VP_GUIBuilder {

    private final VP_GUIController controller;
    private final VP_Header header;
    private final VP_Tree leftTree;
    private final VP_Center center;
    private final VP_Footer footer;

    /*------------------------------------------------------------------------*
     * VP_GUIBuilder()
     * - Constructor. Initialiazes the header, leftTree, and center of the GUI.
     * - Parameter controller saves the reference to VP_GUIController to access
     *   the primary stage.
     *------------------------------------------------------------------------*/
    protected VP_GUIBuilder(VP_GUIController controller) {
        this.controller = controller;
        header = new VP_Header(controller);
        leftTree = new VP_Tree();
        center = new VP_Center(controller);
        footer = new VP_Footer();
    }

    /*------------------------------------------------------------------------*
     * createShell()
     * - Creates an empty BorderPane and its children to be built after the
     *   stage is showing and while the dtatabase is being checked.
     * - No paramters.
     * - Returns the empty GUI BorderPane.
     *------------------------------------------------------------------------*/
    protected BorderPane createShell() {
        BorderPane guiShell = new BorderPane();
        guiShell.setTop(header);
        guiShell.setLeft(leftTree);
        guiShell.setCenter(center);
        guiShell.setBottom(footer);
        return guiShell;
    }

    /*------------------------------------------------------------------------*
     * buildTop()
     * - Builds the header. Called in task, to build in the background
     * - Constrcusts the menubar for the header.
     * - No Paramters
     * - No Return
     *------------------------------------------------------------------------*/
    protected void buildTop() {
        // Menu initialization
        Menu homeMenu = new Menu("Home"),
                optionsMenu = new Menu("Options"),
                helpMenu = new Menu("Help");
        MenuItem userLogin = new MenuItem("Login"),
                userLogout = new MenuItem("Logout"),
                exitVP = new MenuItem("Exit"),
                toggleTree = new MenuItem("Toggle Tree View"),
                toggleFull = new MenuItem("Toggle Full Screen"),
                gettingStarted = new MenuItem("Getting Started with VaqPack"),
                aboutHelp = new MenuItem("About");

        // Menu actions
        exitVP.setOnAction(controller.new ClosingSequence());
        toggleFull.setOnAction(header.new FullScreenToggle());
        //userLogin.setOnAction(...);
        //exitVP.setOnAction(...);
        //gettingStarted.setOnAction(...);
        //aboutHelp.setOnAction(...);
        //fullScreen.setOnAction(...fullScreenToggle());

        // Menu building
        homeMenu.getItems().addAll(userLogin, userLogout,
                new SeparatorMenuItem(), exitVP);
        optionsMenu.getItems().addAll(toggleTree, toggleFull);
        helpMenu.getItems().addAll(gettingStarted, aboutHelp);
        header.getMenuBar().getMenus().addAll(homeMenu, optionsMenu, helpMenu);

        // Logo header construction
        header.getHeaderBar().setPrefHeight(50);
        header.getHeaderBar().setAlignment(Pos.CENTER_LEFT);
        header.getHeaderBar().setFillHeight(true);
        header.getHeaderBar().setSpacing(20);
        header.getHeaderBar().getChildren().addAll(header.getHeaderLogo(), header.getHeaderCaption());
        header.getHeaderLogo().setPrefSize(200, 50);
        header.getHeaderLogo().setMinSize(200, 50);
        header.getHeaderCaption().setText("Graduate-to-Professional Aid Pack");
    }

    /*------------------------------------------------------------------------*
     * buildLeft()
     * - Builds the left side. Called in a task, to build in the background.
     * - No Paramters
     * - No Return
     *------------------------------------------------------------------------*/
    protected void buildLeft() {
        VBox.setVgrow(leftTree, Priority.ALWAYS);
        leftTree.setPrefWidth(200);
        TreeItem<String> falseRoot = new TreeItem();
        falseRoot.setExpanded(true);
        ArrayList<TreeItem> ROOT_Nodes = new ArrayList();

        // COVER LETTER
        ROOT_Nodes.add(new TreeItem("Cover Letter"));
        ArrayList<TreeItem> CL_Nodes = new ArrayList();
        CL_Nodes.add(new TreeItem("Heading"));
        ArrayList<TreeItem> CL_H_Nodes = new ArrayList();
        CL_H_Nodes.add(new TreeItem("Name"));
        CL_H_Nodes.add(new TreeItem("Street Address"));
        CL_H_Nodes.add(new TreeItem("City-State-Zip"));
        CL_H_Nodes.add(new TreeItem("Phone-Email"));
        CL_Nodes.get(0).getChildren().addAll(CL_H_Nodes);
        CL_Nodes.add(new TreeItem("Inside-Address"));
        ArrayList<TreeItem> CL_I_Nodes = new ArrayList();
        CL_I_Nodes.add(new TreeItem("Contact Name"));
        CL_I_Nodes.add(new TreeItem("Company Name"));
        CL_I_Nodes.add(new TreeItem("Street Address"));
        CL_I_Nodes.add(new TreeItem("City-State-Zip"));
        CL_Nodes.get(1).getChildren().addAll(CL_I_Nodes);
        CL_Nodes.add(new TreeItem("Date"));
        CL_Nodes.add(new TreeItem("Salutation"));
        CL_Nodes.add(new TreeItem("Body"));
        ArrayList<TreeItem> CL_B_Nodes = new ArrayList();
        for (int i = 0; i < 3; i++) {
            CL_B_Nodes.add(new TreeItem("Paragraph"));
        }
        CL_Nodes.get(4).getChildren().addAll(CL_B_Nodes);
        CL_Nodes.add(new TreeItem("Closing"));
        CL_Nodes.add(new TreeItem("Signature"));
        ROOT_Nodes.get(0).getChildren().addAll(CL_Nodes);

        // RESUME
        ROOT_Nodes.add(new TreeItem("Resume"));
        ArrayList<TreeItem> RES_Nodes = new ArrayList();
        RES_Nodes.add(new TreeItem("Heading"));
        ArrayList<TreeItem> RES_He_Nodes = new ArrayList();
        RES_He_Nodes.add(new TreeItem("Name"));
        RES_He_Nodes.add(new TreeItem("Street Address"));
        RES_He_Nodes.add(new TreeItem("City-State-Zip"));
        RES_He_Nodes.add(new TreeItem("Phone-Email"));
        RES_Nodes.get(0).getChildren().addAll(RES_He_Nodes);
        RES_Nodes.add(new TreeItem("Objective"));
        ArrayList<TreeItem> RES_O_Nodes = new ArrayList();
        RES_O_Nodes.add(new TreeItem("Paragraph"));
        RES_Nodes.get(1).getChildren().addAll(RES_O_Nodes);
        RES_Nodes.add(new TreeItem("Education"));
        ArrayList<TreeItem> RES_E_Nodes = new ArrayList();
        for (int i = 0; i < 3; i++) {
            RES_E_Nodes.add(new TreeItem("Institution"));
            ArrayList<TreeItem> RES_E_I_Nodes = new ArrayList();
            RES_E_I_Nodes.add(new TreeItem("Name"));
            RES_E_I_Nodes.add(new TreeItem("Degree"));
            RES_E_I_Nodes.add(new TreeItem("GPA"));
            RES_E_I_Nodes.add(new TreeItem("Location"));
            RES_E_I_Nodes.add(new TreeItem("Duration"));
            ArrayList<TreeItem> RES_EID_Nodes = new ArrayList();
            RES_EID_Nodes.add(new TreeItem("Start Date"));
            RES_EID_Nodes.add(new TreeItem("End Date"));
            RES_E_I_Nodes.get(4).getChildren().addAll(RES_EID_Nodes);
            RES_E_Nodes.get(i).getChildren().addAll(RES_E_I_Nodes);
        }
        RES_Nodes.get(2).getChildren().addAll(RES_E_Nodes);
        RES_Nodes.add(new TreeItem("Work Experience"));
        ArrayList<TreeItem> RES_W_Nodes = new ArrayList();
        for (int i = 0; i < 3; i++) {
            RES_W_Nodes.add(new TreeItem("Employer"));
            ArrayList<TreeItem> RES_W_E_Nodes = new ArrayList();
            RES_W_E_Nodes.add(new TreeItem("Name"));
            RES_W_E_Nodes.add(new TreeItem("Location"));
            for (int ii = 2; ii < 4; ii++) {
                RES_W_E_Nodes.add(new TreeItem("Position"));
                ArrayList<TreeItem> RES_WEP_Nodes = new ArrayList();
                RES_WEP_Nodes.add(new TreeItem("Title"));
                RES_WEP_Nodes.add(new TreeItem("Duration"));
                ArrayList<TreeItem> RES_WEPD_Nodes = new ArrayList();
                RES_WEPD_Nodes.add(new TreeItem("Start Date"));
                RES_WEPD_Nodes.add(new TreeItem("End Date"));
                RES_WEP_Nodes.get(1).getChildren().addAll(RES_WEPD_Nodes);
                RES_W_E_Nodes.get(ii).getChildren().addAll(RES_WEP_Nodes);
            }
            RES_W_Nodes.get(i).getChildren().addAll(RES_W_E_Nodes);
        }
        RES_Nodes.get(3).getChildren().addAll(RES_W_Nodes);
        RES_Nodes.add(new TreeItem("Achievements"));
        ArrayList<TreeItem> RES_A_Nodes = new ArrayList();
        for (int i = 0; i < 3; i++) {
            RES_A_Nodes.add(new TreeItem("Achievement"));
            ArrayList<TreeItem> RES_AA_Nodes = new ArrayList();
            RES_AA_Nodes.add(new TreeItem("Title"));
            RES_AA_Nodes.add(new TreeItem("Date"));
            RES_A_Nodes.get(i).getChildren().addAll(RES_AA_Nodes);
        }
        RES_Nodes.get(4).getChildren().addAll(RES_A_Nodes);
        RES_Nodes.add(new TreeItem("Qualifications"));
        ArrayList<TreeItem> RES_Q_Nodes = new ArrayList();
        for (int i = 0; i < 3; i++) {
            RES_Q_Nodes.add(new TreeItem("Skill"));
        }
        RES_Nodes.get(5).getChildren().addAll(RES_Q_Nodes);
        RES_Nodes.add(new TreeItem("Highlights"));
        ArrayList<TreeItem> RES_Hi_Nodes = new ArrayList();
        for (int i = 0; i < 3; i++) {
            RES_Hi_Nodes.add(new TreeItem("Quality"));
        }
        RES_Nodes.get(6).getChildren().addAll(RES_Hi_Nodes);
        RES_Nodes.add(new TreeItem("Languages"));
        ArrayList<TreeItem> RES_L_Nodes = new ArrayList();
        for (int i = 0; i < 3; i++) {
            RES_L_Nodes.add(new TreeItem("Language"));
        }
        RES_Nodes.get(7).getChildren().addAll(RES_L_Nodes);
        RES_Nodes.add(new TreeItem("Software"));
        ArrayList<TreeItem> RES_S_Nodes = new ArrayList();
        for (int i = 0; i < 3; i++) {
            RES_S_Nodes.add(new TreeItem("Product"));
        }
        RES_Nodes.get(8).getChildren().addAll(RES_S_Nodes);
        RES_Nodes.add(new TreeItem("References"));
        ArrayList<TreeItem> RES_R_Nodes = new ArrayList();
        for (int i = 0; i < 3; i++) {
            RES_R_Nodes.add(new TreeItem("Reference"));
            ArrayList<TreeItem> RES_RR_Nodes = new ArrayList();
            RES_RR_Nodes.add(new TreeItem("Name"));
            RES_RR_Nodes.add(new TreeItem("Company"));
            RES_RR_Nodes.add(new TreeItem("Phone"));
            RES_RR_Nodes.add(new TreeItem("Email"));
            RES_R_Nodes.get(i).getChildren().addAll(RES_RR_Nodes);
        }
        RES_Nodes.get(9).getChildren().addAll(RES_R_Nodes);
        ROOT_Nodes.get(1).getChildren().addAll(RES_Nodes);

        // BUSINESS CARD
        ROOT_Nodes.add(new TreeItem("Business Card"));
        ArrayList<TreeItem> BC_Nodes = new ArrayList();
        BC_Nodes.add(new TreeItem("Name"));
        BC_Nodes.add(new TreeItem("Profession"));
        BC_Nodes.add(new TreeItem("Company"));
        ArrayList<TreeItem> BC_C_Nodes = new ArrayList();
        BC_C_Nodes.add(new TreeItem("Company Name"));
        BC_C_Nodes.add(new TreeItem("Slogan"));
        BC_Nodes.get(2).getChildren().addAll(BC_C_Nodes);
        BC_Nodes.add(new TreeItem("Web Address"));
        BC_Nodes.add(new TreeItem("Street Address"));
        BC_Nodes.add(new TreeItem("City-State-Zip"));
        BC_Nodes.add(new TreeItem("Primary Phone"));
        BC_Nodes.add(new TreeItem("Secondary Phone"));
        BC_Nodes.add(new TreeItem("Fax Number"));
        BC_Nodes.add(new TreeItem("Email Address"));
        ROOT_Nodes.get(2).getChildren().addAll(BC_Nodes);

        for (int i = 0; i < ROOT_Nodes.size(); i++) {
            falseRoot.getChildren().add(ROOT_Nodes.get(i));
        }
        TreeView<String> tree = new TreeView(falseRoot);
        tree.setShowRoot(false);
        tree.prefWidthProperty().bind(leftTree.widthProperty());
        tree.prefHeightProperty().bind(leftTree.heightProperty());
        tree.getStyleClass().add("treeStyle");
        leftTree.setPadding(new Insets(1, 1, 1, 1));
        leftTree.getChildren().addAll(tree);
    }

    /*------------------------------------------------------------------------*
     * buildCenter()
     * - Builds the gui center. Called in a task, to build in the background
     * - No Paramters
     * - No Return
     *------------------------------------------------------------------------*/
    protected void buildCenter() {
        center.getChildren().addAll(buildLoginScreen(),
                buildResetPasswordScreen(),
                buildTestScreen());
        center.showScreen(0);
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
        center.getLoginEmail().setAlignment(Pos.CENTER_LEFT);
        emailLine.getChildren().addAll(loginEmailLabel, center.getLoginEmail());
        HBox passLine = new HBox();
        passLine.setAlignment(Pos.CENTER_LEFT);
        passLine.setSpacing(10);
        loginPassLabel.getStyleClass().add("inputLabel");
        loginPassLabel.setPrefWidth(80);
        loginPassLabel.setAlignment(Pos.CENTER_RIGHT);
        center.getLoginPass().setAlignment(Pos.CENTER_LEFT);
        passLine.getChildren().addAll(loginPassLabel, center.getLoginPass());
        center.getLoginError().setWrapText(true);
        center.getLoginError().getStyleClass().add("errorLabel");
        center.getLoginError().setAlignment(Pos.CENTER);
        center.getLoginError().prefWidthProperty().bind(loginBox.prefWidthProperty());
        center.getLoginError().setVisible(false);
        center.getLoginError().setManaged(false);
        center.getLoginButtonLine().setPadding(new Insets(0, 0, 0, 32));
        center.getLoginButtonLine().setAlignment(Pos.CENTER_LEFT);
        center.getLoginButtonLine().setSpacing(50);
        loginBtn.getStyleClass().add("genButton");
        loginBtn.setOnAction(center.new LoginAction());
        passForgotLabel.getStyleClass().add("clickable");
        passForgotLabel.setOnMouseClicked(center.new ForgotPassAction());
        center.getLoginButtonLine().getChildren().addAll(loginBtn, passForgotLabel);
        center.getAccessInstructions().setText("Enter the access code that was emailed to you when you registered below.");
        center.getAccessInstructions().setWrapText(true);
        center.getAccessInstructions().getStyleClass().add("inputLabel");
        center.getAccessInstructions().setAlignment(Pos.CENTER);
        center.getAccessInstructions().prefWidthProperty().bind(loginBox.prefWidthProperty());
        center.getAccessInstructions().setVisible(false);
        center.getAccessInstructions().setManaged(false);
        center.getAccessLine().setAlignment(Pos.CENTER_LEFT);
        center.getAccessLine().setSpacing(20);
        regCodeLabel.getStyleClass().add("inputLabel");
        enterAccessBtn.getStyleClass().add("genButton");
        enterAccessBtn.setOnAction(center.new SubmitAccessAction());
        accessCancelBtn.getStyleClass().add("genButton");
        accessCancelBtn.setOnAction(center.new CancelAccessAction());
        accessResendBtn.getStyleClass().add("genButton");
        accessResendBtn.setOnAction(center.new ResendAccessAction());
        center.getAccessLine().getChildren().addAll(regCodeLabel, center.getRegLoginAccess(),
                enterAccessBtn, accessCancelBtn, accessResendBtn);
        center.getAccessLine().setVisible(false);
        center.getAccessLine().setManaged(false);
        loginBox.getChildren().addAll(loginLabel, emailLine, passLine, center.getLoginError(),
                center.getLoginButtonLine(), center.getAccessInstructions(), center.getAccessLine());
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
        center.getResetInstructions1().setText("Enter your email and submit. An access code will be sent to your email.");
        center.getResetInstructions1().setWrapText(true);
        center.getResetInstructions1().getStyleClass().add("inputLabel");
        center.getResetInstructions1().setAlignment(Pos.CENTER);
        center.getResetInstructions1().prefWidthProperty().bind(forgotPassBox.prefWidthProperty());
        HBox emailLine = new HBox();
        emailLine.setAlignment(Pos.CENTER_LEFT);
        emailLine.setSpacing(10);
        resetEmailLabel.getStyleClass().add("inputLabel");
        resetEmailLabel.setPrefWidth(120);
        resetEmailLabel.setAlignment(Pos.CENTER_RIGHT);
        center.getSubmitResetBtn().setText("Submit");
        center.getSubmitResetBtn().getStyleClass().add("genButton");
        center.getSubmitResetBtn().setOnAction(center.new SubmitResetAction());
        cancelResetBtn1.getStyleClass().add("genButton");
        cancelResetBtn1.setOnAction(center.new CancelResetAction());
        emailLine.getChildren().addAll(resetEmailLabel, center.getResetEmail(), center.getSubmitResetBtn(), cancelResetBtn1);
        center.getResetError().setWrapText(true);
        center.getResetError().getStyleClass().add("errorLabel");
        center.getResetError().setAlignment(Pos.CENTER);
        center.getResetError().prefWidthProperty().bind(forgotPassBox.prefWidthProperty());
        center.getResetError().setVisible(false);
        center.getResetError().setManaged(false);
        center.getResetInstructions2().setText("Enter your new password, confirm it, "
                + "and enter the access code that was sent to you.");
        center.getResetInstructions2().setWrapText(true);
        center.getResetInstructions2().getStyleClass().add("inputLabel");
        center.getResetInstructions2().setAlignment(Pos.CENTER);
        center.getResetInstructions2().prefWidthProperty().bind(forgotPassBox.prefWidthProperty());
        center.getResetInstructions2().setVisible(false);
        center.getResetInstructions2().setManaged(false);
        center.getResetNewPassLine().setAlignment(Pos.CENTER_LEFT);
        center.getResetNewPassLine().setSpacing(10);
        resetNewPassLabel.getStyleClass().add("inputLabel");
        resetNewPassLabel.setPrefWidth(120);
        resetNewPassLabel.setAlignment(Pos.CENTER_RIGHT);
        center.getResetNewPassLine().getChildren().addAll(resetNewPassLabel, center.getResetNewPass());
        center.getResetNewPassLine().setVisible(false);
        center.getResetNewPassLine().setManaged(false);
        center.getResetNewPassConfirmLine().setAlignment(Pos.CENTER_LEFT);
        center.getResetNewPassConfirmLine().setSpacing(10);
        center.getResetPassStrengthLabel().setAlignment(Pos.CENTER_LEFT);
        center.getResetPassStrengthLabel().getStyleClass().add("inputLabel");
        center.getResetPassStrengthLabel().prefWidthProperty().bind(forgotPassBox.prefWidthProperty());
        center.getResetPassStrengthLabel().setVisible(false);
        center.getResetPassStrengthLabel().setManaged(false);
        resetNewPassConfirmLabel.getStyleClass().add("inputLabel");
        resetNewPassConfirmLabel.setPrefWidth(120);
        resetNewPassConfirmLabel.setAlignment(Pos.CENTER_RIGHT);
        center.getResetNewPassConfirmLine().getChildren().addAll(resetNewPassConfirmLabel, center.getResetNewPassConfirm());
        center.getResetNewPassConfirmLine().setVisible(false);
        center.getResetNewPassConfirmLine().setManaged(false);
        center.getResetCodeLine().setAlignment(Pos.CENTER_LEFT);
        center.getResetCodeLine().setSpacing(10);
        resetCodeLabel.getStyleClass().add("inputLabel");
        resetCodeLabel.setPrefWidth(120);
        resetCodeLabel.setAlignment(Pos.CENTER_RIGHT);
        center.getResetCodeLine().getChildren().addAll(resetCodeLabel, center.getResetCode());
        center.getResetCodeLine().setVisible(false);
        center.getResetCodeLine().setManaged(false);
        center.getResetButLine().setPadding(new Insets(0, 0, 0, 32));
        center.getResetButLine().setAlignment(Pos.CENTER_LEFT);
        center.getResetButLine().setSpacing(100);
        submitResetPassCodeBtn.getStyleClass().add("genButton");
        submitResetPassCodeBtn.setOnAction(center.new SubmitResetPassCode());
        cancelResetBtn2.getStyleClass().add("genButton");
        cancelResetBtn2.setOnAction(center.new CancelResetAction());
        center.getResetButLine().getChildren().addAll(submitResetPassCodeBtn, cancelResetBtn2);
        center.getResetButLine().setVisible(false);
        center.getResetButLine().setManaged(false);
        forgotPassBox.getChildren().addAll(resetLabel, center.getResetInstructions1(),
                emailLine, center.getResetError(), center.getResetInstructions2(),
                center.getResetNewPassLine(), center.getResetPassStrengthLabel(),
                center.getResetNewPassConfirmLine(), center.getResetCodeLine(),
                center.getResetButLine());
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
    private VBox buildTestScreen() {
        VBox screen = new VBox();
        screen.setPadding(new Insets(20, 20, 20, 20));
        Button btnEmail = new Button("Send the test email!");
        btnEmail.setOnAction((ActionEvent event) -> {
            String[] ccMail = {
                "shiro_aurion@yahoo.com",
                "atomicus@gmail.com",
                "shiro_aurion@yahoo.com",
                "mr.fernandob3@gmail.com",
                "erik.a.lopez01@utrgv.edu",
                "scorpioncarr1@gmail.com"
            };
            String msg = "Test number 2. Trying to send to all recipients at "
                    + "the same time.\n\n"
                    + "This is a test email from the VaqPack software.\n"
                    + "This is the email that will include a randomly generated "
                    + "code that the new user must enter to verify the email "
                    + "address that they entered in when registering.\n"
                    + "If this is a success, I can finish the user login "
                    + "system and we can move forward to the wizard.";
            VP_Mail testEmail = new VP_Mail(controller, "william.dewald01@utrgv.edu", ccMail, "VaqPack Test Email", msg);
            testEmail.setDaemon(true);
            testEmail.start();
        });
        screen.getChildren().addAll(btnEmail);
        return screen;
    }

    /*------------------------------------------------------------------------*
     * buildBottom()
     * - Builds the gui footer. Called in a task, to build in the background
     * - No Paramters
     * - No Return
     *------------------------------------------------------------------------*/
    protected void buildBottom() {
        footer.setAlignment(Pos.CENTER_RIGHT);
        footer.setPrefHeight(40);
        footer.setFillHeight(true);
        footer.setSpacing(20);
        footer.setPadding(new Insets(0, 20, 0, 20));
        footer.getFooterCaption().setText("The University of Texas Rio Grande Valley");
        footer.getFooterLogo().setPrefSize(100, 20);
        footer.getFooterLogo().setMinSize(100, 20);
        footer.getChildren().addAll(footer.getFooterLogo(), footer.getFooterCaption());
    }

    /*------------------------------------------------------------------------*
     * Setters and Getters
     *------------------------------------------------------------------------*/
}
