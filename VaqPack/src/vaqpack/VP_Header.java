/**
 * VP_Loader.java  - Everything involving the header of the GUI.
 * FILE ID 1400
 */

package vaqpack;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
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
import javafx.stage.Stage;
import vaqpack.components.VP_Dialog;

/**
 * 
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
    private final Stage primaryStage;
    private final MenuBar menuBar;
    private final HBox headerBar;
    private final Pane headerLogo;
    private final Label headerCaption;
    private final Menu adminMenu;
    private final MenuItem userLogout, changePass;

    /*------------------------------------------------------------------------*
     * VP_Header()
     * - Constructor. Adds an empty menubar and header section to itself.
     * - Parameter 
     *------------------------------------------------------------------------*/
    protected VP_Header(VP_GUIController controller, Stage primaryStage) {
        //-------- Initialization Start ----------\\
        this.controller = controller;
        this.primaryStage = primaryStage;
        menuBar = new MenuBar();
        headerBar = new HBox();
        headerLogo = new Pane();
        headerCaption = new Label();
        adminMenu = new Menu("Admin");
        userLogout = new MenuItem("Logout");
        changePass = new MenuItem("Change Your Password");
        //-------- Initialization End ------------\\
        menuBar.setId("menuBar");
        headerBar.setId("headerBar");
        headerLogo.setId("headerLogo");
        headerCaption.setId("headerCaption");
        this.getChildren().addAll(menuBar, headerBar);
    }

    /*------------------------------------------------------------------------*
     * build()
     * - Builds the gui header, including the menu.
     *   Called in a task to build in the background.
     * - No Paramters
     * - No Return
     *------------------------------------------------------------------------*/
    protected void build() {
        //-------- Initialization Start ----------\\
        Menu homeMenu = new Menu("Home"),
                optionsMenu = new Menu("Options"),
                helpMenu = new Menu("Help");
        MenuItem exitVP = new MenuItem("Exit VaqPack"),
                toggleFull = new MenuItem("Toggle Full Screen"),
                adminItem = new MenuItem("Something adminny"),
                gettingStarted = new MenuItem("Getting Started with VaqPack"),
                aboutHelp = new MenuItem("About VaqPack");
        //-------- Initialization End ------------\\

        // Menu actions
        userLogout.setOnAction((e) -> {controller.logoutUser();});
        exitVP.setOnAction(controller.new ClosingSequence());
        toggleFull.setOnAction(new FullScreenToggle());
        changePass.setOnAction(controller.getCenter().new WizardMainAction(22));
        gettingStarted.setOnAction(new HelpAction());
        aboutHelp.setOnAction(new AboutAction());

        // Menu building
        homeMenu.getItems().addAll(userLogout,
                new SeparatorMenuItem(), exitVP);
        optionsMenu.getItems().addAll(toggleFull, changePass);
        adminMenu.getItems().addAll(adminItem);
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
    /*------------------------------------------------------------------------*
     * Subclass FullScreenToggle
     * - Allows the user to enter or exit fullscreen mode
     *------------------------------------------------------------------------*/
    private class FullScreenToggle implements EventHandler {
        @Override
        public void handle(Event event) {
            primaryStage.setFullScreen(!primaryStage.isFullScreen());
        }
    }
    
    private class HelpAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            VP_Dialog helpDialog = new VP_Dialog("Getting Started with VaqPack");
            helpDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
            helpDialog.show();
        }
    }
    
    private class AboutAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            VP_Dialog aboutDialog = new VP_Dialog("About VaqPack");
            aboutDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
            aboutDialog.showAndWait();
        }
    }

    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/
    protected Menu getAdminMenu() {
        return adminMenu;
    }

    protected MenuItem getUserLogout() {
        return userLogout;
    }

    protected MenuBar getMenuBar() {
        return menuBar;
    }

    protected MenuItem getChangePass() {
        return changePass;
    }
}
