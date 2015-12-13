/**
 * VP_Loader.java - Everything involving the header of the GUI. FILE ID 1400
 */
package vaqpack;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import vaqpack.components.VP_Dialog;
import vaqpack.components.VP_FieldLabel;
import vaqpack.components.VP_Paragraph;

/**
 * The header is the top of the main BorderPane layout. The header extends VBox
 * and consists of a menu bar section and a logo section.
 *
 * @author William Dewald (Project Manager, Team-02)
 * @author Fernando Bazan
 * @author Erik Lopez
 * @author Raul Saavedra
 * @author Nathanael Carr
 * @version 1.0
 * @since 1.0
 */
public class VP_Header extends VBox {

    private final VP_GUIController controller;
    private final MenuBar menuBar;
    private final HBox headerBar;
    private final Pane headerLogo;
    private final Label headerCaption;
    private final Menu adminMenu;
    private final MenuItem userLogout, changePass;
    private final VP_Dialog helpDialog,
            aboutDialog;

    /**
     * Constructor. Adds an empty menu bar and header logo section to itself.
     *
     * @param controller Stores the GUI controller for convenience in accessing
     * controller functions or classes accessed by the controller.
     * @since 1.0
     */
    protected VP_Header(VP_GUIController controller) {
        //-------- Initialization Start ----------\\
        this.controller = controller;
        menuBar = new MenuBar();
        headerBar = new HBox();
        headerLogo = new Pane();
        headerCaption = new Label();
        adminMenu = new Menu("Admin");
        userLogout = new MenuItem("Logout");
        changePass = new MenuItem("Change Your Password");
        helpDialog = new VP_Dialog("Getting Started with VaqPack");
        aboutDialog = new VP_Dialog("About VaqPack");
        //-------- Initialization End ------------\\

        this.getChildren().addAll(menuBar, headerBar);
    }

    /**
     * Builds the components for the empty GUI header, including the menu.
     * Called in a task to build in the background.
     *
     * @since 1.0
     */
    protected void build() {
        //-------- Initialization Start ----------\\
        Menu homeMenu = new Menu("Home"),
                optionsMenu = new Menu("Options"),
                helpMenu = new Menu("Help");
        MenuItem exitVP = new MenuItem("Exit VaqPack"),
                toggleFull = new MenuItem("Toggle Full Screen"),
                updateEmployers = new MenuItem("Update Employers List"),
                changeDB = new MenuItem("Change MySQL Location"),
                gettingStarted = new MenuItem("Getting Started with VaqPack"),
                aboutHelp = new MenuItem("About VaqPack");
        //-------- Initialization End ------------\\

        // styles
        menuBar.setId("menuBar");
        headerBar.setId("headerBar");
        headerLogo.setId("headerLogo");
        headerCaption.setId("headerCaption");
        // Menu actions
        userLogout.setOnAction((e) -> {
            boolean saving = false;
            if (controller.hasChanges()) {
                saving = controller.getCenter().confirmLeavePage();
            }
            if (!saving) {
                controller.logoutUser();
            }
        });
        exitVP.setOnAction(controller.new ClosingSequence());
        toggleFull.setOnAction(new FullScreenToggle());
        changePass.setOnAction((e) -> {
            boolean saving = false;
            if (controller.hasChanges()) {
                saving = controller.getCenter().confirmLeavePage();
            }
            if (!saving) {
                controller.getCenter().cancelActionFunction();
                controller.getCenter().showScreen(22, 0);
            }    
        });
        updateEmployers.setOnAction((e) -> {
            boolean saving = false;
            if (controller.hasChanges()) {
                saving = controller.getCenter().confirmLeavePage();
            }
            if (!saving) {
                controller.getCenter().cancelActionFunction();
                controller.getCenter().showScreen(25, 0);
            }
        });
        changeDB.setOnAction((e) -> {
            boolean saving = false;
            if (controller.hasChanges()) {
                saving = controller.getCenter().confirmLeavePage();
            }
            if (!saving) {
                controller.getCenter().cancelActionFunction();
                controller.getCenter().showScreen(23, 0);
            }    
        });
        gettingStarted.setOnAction((e)->{
            if (helpDialog.isShowing()) {
                    helpDialog.hide();
                }
                helpDialog.show();
        });
        aboutHelp.setOnAction((e)->{
            if (aboutDialog.isShowing()) {
                aboutDialog.hide();
            }
            aboutDialog.show();
        });
        // Menu building
        homeMenu.getItems().addAll(userLogout,
                new SeparatorMenuItem(), exitVP);
        optionsMenu.getItems().addAll(toggleFull, changePass);
        adminMenu.getItems().addAll(updateEmployers, changeDB);
        helpMenu.getItems().addAll(gettingStarted, aboutHelp);
        menuBar.getMenus().addAll(homeMenu, optionsMenu, helpMenu, adminMenu);
        adminMenu.setDisable(true);
        adminMenu.setText("");
        userLogout.setVisible(false);
        changePass.setVisible(false);
        // Logo header construction
        headerBar.setPrefHeight(50);
        headerBar.setAlignment(Pos.CENTER_LEFT);
        headerBar.setFillHeight(true);
        headerBar.setSpacing(20);
        headerBar.getChildren().addAll(headerLogo, headerCaption);
        headerLogo.setPrefSize(200, 50);
        headerLogo.setMinSize(200, 50);
        headerCaption.setText("Graduate-to-Professional Aid Pack");
        helpDialog.initModality(Modality.NONE);
        helpDialog.setHeaderText("Version 1.0");
        ScrollPane helpScroller = new ScrollPane();
        helpScroller.setPrefWidth(450);
        VBox helpContent = new VBox();
        helpContent.prefWidthProperty().bind(helpScroller.widthProperty().add(-20));
        VP_FieldLabel loginHelpLabel = new VP_FieldLabel("Account Help:");
        VP_Paragraph acctPara1 = new VP_Paragraph("You must have a registered account in order to use VaqPack. "
                + "If you do not have an account, select the 'need an account?' link on the login page. "
                + "After you submit your information on the registration screen, an email will be sent to the "
                + "email address that you used to register with. This email contains a registration code that you "
                + "must enter in within one hour. If your code expires, you can always attempt to login and then click "
                + "the button to send a new code.");
        VP_Paragraph acctPara2 = new VP_Paragraph("If you cannot login because you have forgotten your password, click the "
                + "'forgot your password?' link on the login page. An email with confirmation code will be sent to email "
                + "address to prevent account theft.");
        VP_Paragraph acctPara3 = new VP_Paragraph("Once you are logged in, you may change your password at any time. "
                + "An email is sent to the email address associated with the currently logged-in account juat as a notifcation. "
                + "For security reasons, you must provide your current password along with the new password. This method is in "
                + "place to protect your account in the event that you step away without loggin out.");
        VP_FieldLabel privacyHelpLabel = new VP_FieldLabel("Privacy Infomation:");
        VP_Paragraph privPara = new VP_Paragraph("The information you provide to VaqPack is for the sole use of "
                + "generating the documents you choose. This information is not shared with anyone. "
                + "Although your password is hashed and safeguards have been provided to ensure that you must enter a "
                + "password that is at least somewhat difficult to crack, it is recommended that you still change your "
                + "password every few months. VaqPack will automatically log out any user after some time of inactivity. However, "
                + "you should always log out of VaqPack before stepping away from the computer running it.");
        VP_FieldLabel faqLabel = new VP_FieldLabel("Frequently Asked Questions:");
        VP_FieldLabel faq1 = new VP_FieldLabel("Why can't I edit some of the fields?");
        faq1.setPadding(new Insets(10, 0, 0, 40));
        VP_Paragraph ans1 = new VP_Paragraph("Every document has some fields that can only be edited in the 'Personal "
                + "Information' section. These fields are shown to you in the document editors just to let you know exactly "
                + "what information is used in each document.");
        VP_FieldLabel faq2 = new VP_FieldLabel("What is the tree for on the left side of VaqPack?");
        faq2.setPadding(new Insets(10, 0, 0, 40));
        VP_Paragraph ans2 = new VP_Paragraph("The tree view on the left is a convenient way to navigate between documents quickly "
                + "outside of the main wizard. Like the steps in the wizard overview page, parts of the tree because accessible "
                + "once you complete some steps.");
        VP_FieldLabel faq3 = new VP_FieldLabel("I found a serious bug in VaqPack. Who should I contact?");
        faq3.setPadding(new Insets(10, 0, 0, 40));
        VP_Paragraph ans3 = new VP_Paragraph("Send an email describing the problem to the VaqPack project manager, William DeWald vacpackt2@gmail.com");
        helpContent.getChildren().addAll(loginHelpLabel, acctPara1, acctPara2, acctPara3, privacyHelpLabel, 
                privPara, faqLabel, faq1, ans1, faq2, ans2, faq3, ans3);
        helpScroller.setContent(helpContent);
        helpDialog.getDialogPane().setPrefSize(480, 480);
        helpDialog.getDialogShell().add(helpScroller, 0, 0);
        helpDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        aboutDialog.initModality(Modality.NONE);
        aboutDialog.setHeaderText("Version 1.0");
        VP_Paragraph aboutContent = new VP_Paragraph("VaqPack was created by Team-02 \n"
                + "of Software Engineering course CSCI-3340-02 \n"
                + "during the Fall 2015 semester at UTRGV.\n\n"
                + "\t William DeWald (Project Manager)\n"
                + "\t\t Fernando Bazan\n"
                + "\t\t Nathanael Carr\n"
                + "\t\t Erik Lopez\n"
                + "\t\t Raul Saavedra\n");
        aboutContent.setPadding(new Insets(50, 20, 50, 20));
        aboutDialog.getDialogShell().add(aboutContent, 0, 0);
        aboutDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
    }

    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /**
     * Allows the user to enter or exit full-screen mode
     *
     * @since 1.0
     */
    private class FullScreenToggle implements EventHandler {

        /**
         * @param event A general event of no specific type. Although this is
         * currently triggered by a menu item, this is left to be a simple event
         * in case a shortcut command for this is added in future versions.
         * @since 1.0
         */
        @Override
        public void handle(Event event) {
            controller.getPrimaryStage().setFullScreen(!controller.getPrimaryStage().isFullScreen());
        }
    }

    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/
    /**
     * Accessor method.
     *
     * @return The 'Admin' Menu pull-down object.
     * @since 1.0
     */
    protected Menu getAdminMenu() {
        return adminMenu;
    }

    /**
     * Accessor method.
     *
     * @return The inner menu item allowing a user to log out.
     * @since 1.0
     */
    protected MenuItem getUserLogout() {
        return userLogout;
    }

    /**
     * Accessor method.
     *
     * @return The inner menu item allowing a user to access the page in the
     * wizard to change a password.
     * @since 1.0
     */
    protected MenuItem getChangePass() {
        return changePass;
    }
}
