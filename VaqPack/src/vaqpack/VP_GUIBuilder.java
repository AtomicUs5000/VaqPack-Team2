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

import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class VP_GUIBuilder {

    private final VP_Header header;
    private final VP_Tree leftTree;
    private final VP_Center center;

    /*------------------------------------------------------------------------*
     * VP_GUIBuilder()
     * - Constructor. Initialiazes the header, leftTree, and center of the GUI.
     * No Parameters.
     *------------------------------------------------------------------------*/
    protected VP_GUIBuilder() {
        header = new VP_Header();
        leftTree = new VP_Tree();
        center = new VP_Center();
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
        Menu homeMenu = new Menu("Home");
        Menu helpMenu = new Menu("Help");
        MenuItem userLogin = new MenuItem("Login"),
                userLogout = new MenuItem("Logout"),
                exitVP = new MenuItem("Exit"),
                gettingStarted = new MenuItem("Getting Started with VaqPack"),
                aboutHelp = new MenuItem("About");
        homeMenu.getItems().addAll(
                userLogin,
                userLogout,
                exitVP);
        //userLogin.setOnAction(...);
        //userLogout.setOnAction(...);
        //exitVP.setOnAction(...);
        //gettingStarted.setOnAction(...);
        //aboutHelp.setOnAction(...);
        helpMenu.getItems().addAll(gettingStarted, aboutHelp);
        header.getMenuBar().getMenus().addAll(homeMenu, helpMenu);
    }

    /*------------------------------------------------------------------------*
     * buildLeft
     * - Builds the left side. Called in task, to build in the background
     *------------------------------------------------------------------------*/
    protected void buildLeft() {
        Label testLabel = new Label("Left");
        leftTree.getChildren().addAll(testLabel);
    }

    /*------------------------------------------------------------------------*
     * buildCenter
     * - Builds the gui center. Called in task, to build in the background
     *------------------------------------------------------------------------*/
    protected void buildCenter() {
        Label testLabel = new Label("CENTER");
        center.getChildren().addAll(testLabel);
    }

    /*------------------------------------------------------------------------*
     * Setters and Getters
     *------------------------------------------------------------------------*/
}
