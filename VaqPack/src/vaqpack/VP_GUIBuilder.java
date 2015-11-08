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
import javafx.scene.layout.BorderPane;

public class VP_GUIBuilder {
    private final VP_Header header = new VP_Header();
    private final VP_Tree leftTree = new VP_Tree();
    private final VP_Center center = new VP_Center();
    
    /*------------------------------------------------------------------------*
     * createShell
     * - Creates an empty BorderPane and its children to be built after the
     *   stage is showing and while the dtatabase is being checked.
     *------------------------------------------------------------------------*/
    protected BorderPane createShell() {
        BorderPane guiShell = new BorderPane();
        guiShell.setTop(header);
        guiShell.setLeft(leftTree);
        guiShell.setCenter(center);
        return guiShell;
    }
    
    /*------------------------------------------------------------------------*
     * buildTop
     * - Builds the header. Called in task, to build in the background
     *------------------------------------------------------------------------*/
    protected void buildTop() {
        Label testLabel = new Label("TOP");
        header.getChildren().addAll(testLabel);
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
