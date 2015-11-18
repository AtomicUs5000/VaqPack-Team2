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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
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
     * No Parameters.
     *------------------------------------------------------------------------*/
    protected VP_GUIBuilder(VP_GUIController controller) {
        this.controller = controller;
        header = new VP_Header();
        leftTree = new VP_Tree();
        center = new VP_Center();
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
        Menu    homeMenu = new Menu("Home"),
                optionsMenu = new Menu("Options"),
                helpMenu = new Menu("Help");
        MenuItem userLogin = new MenuItem("Login"),
                userLogout = new MenuItem("Logout"),
                exitVP = new MenuItem("Exit"),
                toggleTree = new MenuItem("Toggle Tree View"),
                toggleFull = new MenuItem("Toggle Full Screen"),
                gettingStarted = new MenuItem("Getting Started with VaqPack"),
                aboutHelp = new MenuItem("About");
        homeMenu.getItems().addAll(
                userLogin,
                userLogout,
                new SeparatorMenuItem(),
                exitVP);
        //userLogin.setOnAction(...);
        //exitVP.setOnAction(...);
        //gettingStarted.setOnAction(...);
        //aboutHelp.setOnAction(...);
        //fullScreen.setOnAction(...fullScreenToggle());
        optionsMenu.getItems().addAll(toggleTree, toggleFull);
        helpMenu.getItems().addAll(gettingStarted, aboutHelp);
        
        header.getMenuBar().getMenus().addAll(homeMenu, optionsMenu, helpMenu);
        header.getHeaderBar().setPrefHeight(50);
        header.getHeaderBar().setAlignment(Pos.CENTER_LEFT);
        header.getHeaderBar().setFillHeight(true);
        header.getHeaderBar().setSpacing(20);
        header.getHeaderBar().getChildren().addAll(header.getHeaderLogo(), header.getHeaderCaption());
        header.getHeaderLogo().setPrefSize(200, 50);
        header.getHeaderCaption().setText("Graduate-to-Professional Aid Pack");
    }

    /*------------------------------------------------------------------------*
     * buildLeft
     * - Builds the left side. Called in task, to build in the background
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
                for (int i = 0; i < 3; i ++)
                    CL_B_Nodes.add(new TreeItem("Paragraph"));
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
                for (int i = 0; i < 3; i++)
                    RES_Q_Nodes.add(new TreeItem("Skill"));
            RES_Nodes.get(5).getChildren().addAll(RES_Q_Nodes);
            RES_Nodes.add(new TreeItem("Highlights"));
                ArrayList<TreeItem> RES_Hi_Nodes = new ArrayList();
                for (int i = 0; i < 3; i++)
                    RES_Hi_Nodes.add(new TreeItem("Quality"));
            RES_Nodes.get(6).getChildren().addAll(RES_Hi_Nodes);
            RES_Nodes.add(new TreeItem("Languages"));
                ArrayList<TreeItem> RES_L_Nodes = new ArrayList();
                for (int i = 0; i < 3; i++)
                    RES_L_Nodes.add(new TreeItem("Language"));
            RES_Nodes.get(7).getChildren().addAll(RES_L_Nodes);
            RES_Nodes.add(new TreeItem("Software"));
                ArrayList<TreeItem> RES_S_Nodes = new ArrayList();
                for (int i = 0; i < 3; i++)
                    RES_S_Nodes.add(new TreeItem("Product"));
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
        
        for (int i = 0; i < ROOT_Nodes.size(); i++)
            falseRoot.getChildren().add(ROOT_Nodes.get(i));
        TreeView<String> tree = new TreeView(falseRoot);
        tree.setShowRoot(false);
        tree.prefWidthProperty().bind(leftTree.widthProperty());
        tree.prefHeightProperty().bind(leftTree.heightProperty());
        tree.getStyleClass().add("treeStyle");
        tree.setPadding(new Insets(20, 5, 20, 5));
        leftTree.getChildren().addAll(tree);
    }


    /*------------------------------------------------------------------------*
     * buildCenter
     * - Builds the gui center. Called in task, to build in the background
     *------------------------------------------------------------------------*/
    protected void buildCenter() {
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
            String msg =  "Test number 2. Trying to send to all recipients at "
                    + "the same time.\n\n"
                    + "This is a test email from the VaqPack software.\n"
                    + "This is the email that will include a randomly generated "
                    + "code that the new user must enter to verify the email "
                    + "address that they entered in when registering.\n"
                    + "If this is a success, I can finish the user login "
                    + "system and we can move forward to the wizard.";
            VP_Mail testEmail = new VP_Mail(controller, "william.dewald01@utrgv.edu", ccMail, "VaqPack Test Email", msg);
            testEmail.start();
        });
        center.getChildren().addAll(btnEmail);
        center.setPadding(new Insets(10, 10, 10, 10));
    }
    
    /*------------------------------------------------------------------------*
     * buildCenter
     * - Builds the gui center. Called in task, to build in the background
     *------------------------------------------------------------------------*/
    protected void buildBottom() {
        footer.setAlignment(Pos.CENTER_RIGHT);
        footer.setPrefHeight(40);
        footer.setFillHeight(true);
        footer.setSpacing(20);
        footer.setPadding(new Insets(0, 20, 0, 20));
        footer.getFooterCaption().setText("The University of Texas Rio Grande Valley");
        footer.getFooterLogo().setPrefSize(100, 20);
        footer.getChildren().addAll(footer.getFooterLogo(), footer.getFooterCaption());
    }

    /*------------------------------------------------------------------------*
     * Setters and Getters
     *------------------------------------------------------------------------*/
}
