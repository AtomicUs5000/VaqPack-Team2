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

import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;

public class VP_Header extends VBox {

    private MenuBar menuBar;

    /*------------------------------------------------------------------------*
     * VP_Header()
     * - Constructor. Adds an empty menubar to itself.
     * - No parameters.
     *------------------------------------------------------------------------*/
    protected VP_Header() {
        menuBar = new MenuBar();
        this.getChildren().addAll(menuBar);
    }

    /*------------------------------------------------------------------------*
     * Setters and Getters
     *------------------------------------------------------------------------*/
    protected MenuBar getMenuBar() {
        return menuBar;
    }

    protected void setMenuBar(MenuBar menuBar) {
        this.menuBar = menuBar;
    }

}
