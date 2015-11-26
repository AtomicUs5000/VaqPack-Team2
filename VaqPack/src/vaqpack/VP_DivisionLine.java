/*-----------------------------------------------------------------------------*
 * VP_DivisionLine.java
 * - Custom HBox that represents a line of a page division
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 4300
 *-----------------------------------------------------------------------------*/
package vaqpack;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class VP_DivisionLine extends HBox {
    
    /*------------------------------------------------------------------------*
     * VP_DivisionLine
     * - Default Constructor.
     * - No parameters
     *------------------------------------------------------------------------*/
    public VP_DivisionLine() {
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(20);
        this.setPadding(new Insets(2, 0, 2, 2));
    }
    
    /*------------------------------------------------------------------------*
     * VP_DivisionLine
     * - Constructor.
     * - Parameter nodes is an array of nodes, allowing the addition of nodes
     *   to this component at the time of its construction.
     *------------------------------------------------------------------------*/
    public VP_DivisionLine(Node[] nodes) {
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(20);
        this.getChildren().addAll(nodes);
        this.setPadding(new Insets(2, 0, 2, 2));
    }
    
    /*------------------------------------------------------------------------*
     * hide()
     * - Hides this line and collapses it.
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void hide() {
        this.setVisible(false);
        this.setManaged(false);
    }
    
    /*------------------------------------------------------------------------*
     * show()
     * - Shows this line and expands it.
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void show() {
        this.setVisible(true);
        this.setManaged(true);
    }
    
    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/
}
