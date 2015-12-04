/**
 * VP_Footer.java - Everything involving the footer of the GUI. FILE ID 1500
 */
package vaqpack;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 * The footer is the bottom of the main BorderPane layout. The footer extends
 * HBox and consists of images on the right, and a dynamic field on the left
 * that shows the email address of the currently logged in user.
 *
 * @author William Dewald (Project Manager, Team-02)
 * @author Fernando Bazan
 * @author Erik Lopez
 * @author Raul Saavedra
 * @author Nathanael Carr
 * @version 1.0
 * @since 1.0
 */
public class VP_Footer extends HBox {

    private final VP_GUIController controller;
    private final Pane footerLogo;
    private final Label footerCaption;

    /**
     * Adds empty panes for the footer information and images.
     *
     * @param controller Stores the GUI controller for convenience in accessing 
     * controller functions or classes accessed by the controller.
     * @since 1.0
     */
    protected VP_Footer(VP_GUIController controller) {
        //-------- Initialization Start ----------\\
        this.controller = controller;
        footerLogo = new Pane();
        footerCaption = new Label();
        //-------- Initialization End ------------\\
    }

    /**
     * Builds the contents of the empty GUI footer. Called in a task to build in
     * the background.
     *
     * @since 1.0
     */
    protected void build() {
        //-------- Initialization Start ----------\\
        HBox leftFooterBox = new HBox();
        Label userLoggedInLabel = new Label();
        //-------- Initialization End ------------\\

        setId("footer");
        footerLogo.setId("footerLogo");
        footerCaption.setId("footerCaption");
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
