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
 * FILE ID 1700
 *-----------------------------------------------------------------------------*/
package vaqpack;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class VP_Footer extends HBox {
    private final Pane footerLogo;
    private final Label footerCaption;
    
    /*------------------------------------------------------------------------*
     * VP_Footer()
     * - Constructor. Adds empty panes for the footer information
     * - No parameters.
     *------------------------------------------------------------------------*/
    protected VP_Footer() {
        footerLogo = new Pane();
        footerCaption = new Label();
        this.setId("footer");
        footerLogo.setId("footerLogo");
        footerCaption.setId("footerCaption");
    }

    /*------------------------------------------------------------------------*
     * Setters and Getters
     *------------------------------------------------------------------------*/

    protected Pane getFooterLogo() {
        return footerLogo;
    }

    protected Label getFooterCaption() {
        return footerCaption;
    }
}
