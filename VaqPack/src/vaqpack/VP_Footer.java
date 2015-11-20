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
 * FILE ID 2000
 *-----------------------------------------------------------------------------*/
package vaqpack;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class VP_Footer extends HBox {

    private final Pane footerLogo;
    private final Label footerCaption;
    private final Label userLoggedInLabel;

    /*------------------------------------------------------------------------*
     * VP_Footer()
     * - Constructor. Adds empty panes for the footer information
     * - No parameters.
     *------------------------------------------------------------------------*/
    protected VP_Footer() {
        //-------- Initialization Start ----------\\
        footerLogo = new Pane();
        footerCaption = new Label();
        userLoggedInLabel = new Label();
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
        leftFooterBox.getChildren().addAll(userLoggedInLabel);
        userLoggedInLabel.setAlignment(Pos.CENTER_LEFT);
        getChildren().addAll(leftFooterBox, footerLogo, footerCaption);
    }

    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/

    protected Label getUserLoggedInLabel() {
        return userLoggedInLabel;
    }
}
