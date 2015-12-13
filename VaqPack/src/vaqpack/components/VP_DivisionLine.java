/**
 * VP_DivisionLine.java - Custom HBox that represents a line of page division. 
 * FILE ID 2100
 */
package vaqpack.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * The division line divides a page. This extends HBox and consists of 
 * overloaded constructors and methods to show and hide the division line.
 *
 * @author William Dewald (Project Manager, Team-02)
 * @author Fernando Bazan
 * @author Erik Lopez
 * @author Raul Saavedra
 * @author Nathanael Carr
 * @version 1.0
 * @since 1.0
 */
public class VP_DivisionLine extends HBox {
    
    /**
     * Constructor.
     * 
     * @since 1.0
     */
    public VP_DivisionLine() {
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(20);
        this.setPadding(new Insets(4, 0, 4, 4));
    }
    
    /**
     * Constructor.
     * 
     * @param nodes An array of nodes which allows the addition of nodes to this
     * component at the time of its construction.
     * @since 1.0
     */
    public VP_DivisionLine(Node[] nodes) {
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(20);
        this.getChildren().addAll(nodes);
        this.setPadding(new Insets(4, 0, 4, 4));
    }
    
    /**
     * Constructor.
     * 
     * @param nodes An array of nodes which allows the addition of nodes to this
     * component at the time of its construction.
     * @param padLeft Sets the left padding of the line.
     * @since 1.0
     */
    public VP_DivisionLine(Node[] nodes, int padLeft) {
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(20);
        this.getChildren().addAll(nodes);
        this.setPadding(new Insets(4, 0, 4, padLeft));
    }
    
    /**
     * Hides the line and collapses it.
     * 
     * @since 1.0
     */
    public void hide() {
        this.setVisible(false);
        this.setManaged(false);
    }
    
    /**
     * Shows the line and expands it.
     * 
     * @since 1.0
     */
    public void show() {
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
