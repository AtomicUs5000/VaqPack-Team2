/*-----------------------------------------------------------------------------*
 * VP_Footer.java
 * - Everything involving the footer of the gui
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 1500
 *-----------------------------------------------------------------------------*/
package vaqpack;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class VP_Footer extends HBox {

    private final VP_GUIController controller;
    private final Pane footerLogo;
    private final Label footerCaption;

    /*------------------------------------------------------------------------*
     * VP_Footer()
     * - Constructor. Adds empty panes for the footer information
     * - No parameters.
     *------------------------------------------------------------------------*/
    protected VP_Footer(VP_GUIController controller) {
        //-------- Initialization Start ----------\\
        this.controller = controller;
        footerLogo = new Pane();
        footerCaption = new Label();
        //-------- Initialization End ------------\\

        this.setId("footer");
        footerLogo.setId("footerLogo");
        footerCaption.setId("footerCaption");
    }

    /*------------------------------------------------------------------------*
     * build()
     * - Builds the gui footer. Called in a task to build in the background.
     * - No Paramters
     * - No Return
     *------------------------------------------------------------------------*/
    protected void build() {
        //-------- Initialization Start ----------\\
        HBox leftFooterBox = new HBox();
        Label userLoggedInLabel = new Label();
        //-------- Initialization End ------------\\
        
        setAlignment(Pos.CENTER_RIGHT);
        setPrefHeight(40);
        setFillHeight(true);
        setSpacing(20);
        setPadding(new Insets(0, 20, 0, 20));
        footerCaption.setText("The University of Texas Rio Grande Valley");
        footerLogo.setPrefSize(100, 20);
        footerLogo.setMinSize(100, 20);
        leftFooterBox.setAlignment(Pos.CENTER_LEFT);
        leftFooterBox.setFillHeight(true);
        HBox.setHgrow(leftFooterBox, Priority.ALWAYS);
        userLoggedInLabel.setId("footerUser");
        userLoggedInLabel.setAlignment(Pos.CENTER_LEFT);
        userLoggedInLabel.textProperty().bind(controller.getCurrentUser().getEmail());
        leftFooterBox.getChildren().addAll(userLoggedInLabel);
        getChildren().addAll(leftFooterBox, footerLogo, footerCaption);
    }

    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/
}
