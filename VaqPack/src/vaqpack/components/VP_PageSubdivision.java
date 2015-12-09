/*-----------------------------------------------------------------------------*
 * VP_PageDivision.java
 * - Custom VBox page subsection with a caption title
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 2400
 *-----------------------------------------------------------------------------*/
package vaqpack.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 *
 * @author atomi_000
 */
public class VP_PageSubdivision extends VBox {

    public VP_PageSubdivision(String caption, boolean isContainer) {
        this.setSpacing(6);
        this.setPadding(new Insets(6, 6, 6, 12));
        if (isContainer) {
            getStyleClass().add("formContainer");
        } else {
            this.getStyleClass().add("formSubdivision");
        }
        Label titleLabel = new Label(caption);
        if (isContainer) {
            titleLabel.getStyleClass().add("pageSubheader");
        } else {
            this.getStyleClass().add("pageSubSubheader");
        }
        titleLabel.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        titleLabel.setMaxSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        titleLabel.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().add(titleLabel);
    }
}
