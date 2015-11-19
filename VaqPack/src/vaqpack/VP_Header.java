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
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class VP_Header extends VBox {

    private final VP_GUIController controller;
    private final MenuBar menuBar;
    private final HBox headerBar;
    private final Pane headerLogo;
    private final Label headerCaption;

    /*------------------------------------------------------------------------*
     * VP_Header()
     * - Constructor. Adds an empty menubar and header section to itself.
     * - No parameters.
     *------------------------------------------------------------------------*/
    protected VP_Header(VP_GUIController controller) {
        this.controller = controller;
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
     * Subclasses
     *------------------------------------------------------------------------*/
    /*------------------------------------------------------------------------*
     * Subclass FullScreenToggle
     * - Allows the user to enter or exit fullscreen mode
     *------------------------------------------------------------------------*/
    protected class FullScreenToggle implements EventHandler {

        @Override
        public void handle(Event event) {
            controller.getPrimaryStage().setFullScreen(!controller.getPrimaryStage().isFullScreen());
        }
    }

    /*------------------------------------------------------------------------*
     * Setters and Getters
     *------------------------------------------------------------------------*/
    protected MenuBar getMenuBar() {
        return menuBar;
    }

    protected HBox getHeaderBar() {
        return headerBar;
    }

    protected Pane getHeaderLogo() {
        return headerLogo;
    }

    protected Label getHeaderCaption() {
        return headerCaption;
    }

}
