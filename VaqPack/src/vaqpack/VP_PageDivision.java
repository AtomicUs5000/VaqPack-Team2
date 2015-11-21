/*-----------------------------------------------------------------------------*
 * VP_PageDivision.java
 * - Custom VBox page section with a header title
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 4200
 *-----------------------------------------------------------------------------*/
package vaqpack;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class VP_PageDivision extends VBox {
    
    /*------------------------------------------------------------------------*
     * VP_PageDivision()
     * - Constructor.
     * - Parameter string title is the title at the top of this component
     *------------------------------------------------------------------------*/
    public VP_PageDivision(String title) {
        this.setSpacing(10);
        this.getStyleClass().add("formDivision");
        this.setPadding(new Insets(10, 10, 10, 10));
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("pageHeader");
        titleLabel.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        titleLabel.setMaxSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        titleLabel.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().add(titleLabel);
    }
}
