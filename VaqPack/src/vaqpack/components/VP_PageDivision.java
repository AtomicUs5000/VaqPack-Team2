/**
 * VP_PageDivision.java - Custom VBox that represents a page section with title. 
 * FILE ID 2300
 */
package vaqpack.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * The page division defines page sections. This extends VBox and consists of 
 * overloaded constructors.
 *
 * @author William Dewald (Project Manager, Team-02)
 * @author Fernando Bazan
 * @author Erik Lopez
 * @author Raul Saavedra
 * @author Nathanael Carr
 * @version 1.0
 * @since 1.0
 */
public class VP_PageDivision extends VBox {
    
    /**
     * Constructor.
     * 
     * @param title The string which sets the title at the top of this component.
     * @since 1.0
     */
    public VP_PageDivision(String title) {
        this.setSpacing(6);
        this.getStyleClass().add("formDivision");
        this.setPadding(new Insets(10, 10, 10, 16));
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("pageHeader");
        titleLabel.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        titleLabel.setMaxSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        titleLabel.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().add(titleLabel);
    }
    
    /**
     * Constructor.
     * 
     * @param title The string which sets the title at the top of this component.
     * @param icon The name of the desired header icon.
     * @param width The preferred width of the component.
     * @since 1.0
     */
    public VP_PageDivision(String title, String icon, double width) {
        this.setSpacing(10);
        this.getStyleClass().add("formDivision");
        this.setPadding(new Insets(10, 10, 10, 16));
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("pageHeader");
        titleLabel.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        titleLabel.setMaxSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        titleLabel.setAlignment(Pos.CENTER_RIGHT);
        titleLabel.setPrefHeight(32);
        titleLabel.setPrefWidth(width);
        titleLabel.setStyle("-fx-background-image: url(\"" + icon + "\");"
                + "-fx-background-size: 32px 32px;" +
                "-fx-background-position: left center;"
                + "-fx-background-repeat: no-repeat;");
        this.getChildren().add(titleLabel);
    }
}
