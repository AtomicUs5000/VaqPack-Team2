/**
 * VP_Loader.java - Everything involving the header of the GUI. FILE ID 1400
 */
package vaqpack;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import vaqpack.components.VP_Dialog;
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
        gettingStarted.setOnAction(new HelpAction());
        aboutHelp.setOnAction(new AboutAction());

        // Menu building
        homeMenu.getItems().addAll(userLogout,
                new SeparatorMenuItem(), exitVP);
        optionsMenu.getItems().addAll(toggleFull, changePass);
        adminMenu.getItems().addAll(changeDB);
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

    /**
     * Opens the 'Getting Started with VaqPack' Dialog Box.
     *
     * @since 1.0
     */
    private class HelpAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent, triggered by a menu item selection.
         * @since 1.0
         */
        @Override
        public void handle(ActionEvent event) {
            VP_Dialog helpDialog = new VP_Dialog("Getting Started with VaqPack");
            helpDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
            helpDialog.show();
        }
    }

    /**
     * Opens the 'About VaqPack' Dialog Box.
     *
     * @since 1.0
     */
    private class AboutAction implements EventHandler<ActionEvent> {

        /**
         * @param event An ActionEvent, triggered by a menu item selection.
         * @since 1.0
         */
        @Override
        public void handle(ActionEvent event) {
            VP_Dialog aboutDialog = new VP_Dialog("About VaqPack");
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
            aboutDialog.showAndWait();
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
