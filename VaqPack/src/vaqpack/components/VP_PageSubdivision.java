/**
 * VP_PageSubdivision.java - Custom VBox page subsection with a caption title.
 * FILE ID 2400
 */
package vaqpack.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * The page subdivision defines page subsections. This extends VBox and consists
 * of a constructor.
 *
 * @author William Dewald (Project Manager, Team-02)
 * @author Fernando Bazan
 * @author Erik Lopez
 * @author Raul Saavedra
 * @author Nathanael Carr
 * @version 1.0
 * @since 1.0
 */
public class VP_PageSubdivision extends VBox {

    /**
     * Constructor.
     * 
     * @param caption The string that sets the caption text of the subsection.
     * @param isContainer Determines if component is a container or subdivision
     * @since 1.0
     */
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
