/*-----------------------------------------------------------------------------*
 * VP_Loader.java
 * - Everything involving the header of the gui
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 1700
 *-----------------------------------------------------------------------------*/
package vaqpack;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VP_Header extends VBox {

    private final VP_GUIController controller;
    private final Stage primaryStage;
    private final MenuBar menuBar;
    private final HBox headerBar;
    private final Pane headerLogo;
    private final Label headerCaption;

    /*------------------------------------------------------------------------*
     * VP_Header()
     * - Constructor. Adds an empty menubar and header section to itself.
     * - Parameter 
     *------------------------------------------------------------------------*/
    protected VP_Header(VP_GUIController controller, Stage primaryStage) {
        this.controller = controller;
        this.primaryStage = primaryStage;
        menuBar = new MenuBar();
        headerBar = new HBox();
        headerLogo = new Pane();
        headerCaption = new Label();
        menuBar.setId("menuBar");
        headerBar.setId("headerBar");
        headerLogo.setId("headerLogo");
        headerCaption.setId("headerCaption");
        this.getChildren().addAll(menuBar, headerBar);
    }

    /*------------------------------------------------------------------------*
     * build()
     * - Builds the gui header, including the menu.
     *   Called in a task, to build in the background
     * - No Paramters
     * - No Return
     *------------------------------------------------------------------------*/
    protected void build() {
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
        toggleFull.setOnAction(new FullScreenToggle());
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
        menuBar.getMenus().addAll(homeMenu, optionsMenu, helpMenu);

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

    /*------------------------------------------------------------------------*
     * Subclasses
     *------------------------------------------------------------------------*/
    /*------------------------------------------------------------------------*
     * Subclass FullScreenToggle
     * - Allows the user to enter or exit fullscreen mode
     *------------------------------------------------------------------------*/
    protected class FullScreenToggle implements EventHandler {

        @Override
        public void handle(Event event) {
            primaryStage.setFullScreen(primaryStage.isFullScreen());
        }
    }

    /*------------------------------------------------------------------------*
     * Setters and Getters
     *------------------------------------------------------------------------*/
}
