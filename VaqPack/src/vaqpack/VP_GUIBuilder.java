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
        leftTree.setPrefWidth(200);
        leftTree.setPadding(new Insets(10, 10, 10, 10));
        
        TreeItem<String> coverLetterRoot = new TreeItem("Cover Letter");
        coverLetterRoot.setExpanded(true);
        TreeItem<String> covLetHeading = new TreeItem("Heading");
        covLetHeading.setExpanded(true);
        TreeItem<String> insideAddy = new TreeItem("Inside Address");   
        insideAddy.setExpanded(true);     
        coverLetterRoot.getChildren().addAll(covLetHeading, insideAddy);
        
        TreeItem<String> resumeRoot = new TreeItem("Resume");
        resumeRoot.setExpanded(true);
        TreeItem<String> resHeading = new TreeItem("Heading");
        resHeading.setExpanded(true);
        TreeItem<String> objective = new TreeItem("Objective");
        objective.setExpanded(true);
        resumeRoot.getChildren().addAll(resHeading, objective);
        
        TreeItem<String> bcardRoot = new TreeItem("Business Card");
        bcardRoot.setExpanded(true);
        TreeItem<String> bcName = new TreeItem("Name");
        bcName.setExpanded(true);
        bcName.setExpanded(true);     
        bcardRoot.getChildren().addAll(bcName);
        
        TreeView<String> tree1 = new TreeView (coverLetterRoot);
        TreeView<String> tree2 = new TreeView (resumeRoot);
        TreeView<String> tree3 = new TreeView (bcardRoot);
        leftTree.getChildren().addAll(tree1, tree2, tree3);
        
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
