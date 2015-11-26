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
 * FILE ID 4400
 *-----------------------------------------------------------------------------*/
package vaqpack;

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
        this.getStyleClass().add("formSubdivision");
        this.setPadding(new Insets(6, 6, 6, 6));
        Label titleLabel = new Label(caption);
        titleLabel.getStyleClass().add("pageSubheader");
        if (isContainer)
            getStyleClass().add("formContainer");
        titleLabel.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        titleLabel.setMaxSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        titleLabel.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().add(titleLabel);
    }
}
