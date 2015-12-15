/**
 * VP_Screen.java - Custom ScrollPane class. FILE ID 2850
 */
package vaqpack.components;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * Defines a custom ScrollPane class. This extends ScrollPane and consists of
 * overloaded constructors and method to add Nodes to a page section.
 *
 * @author William Dewald (Project Manager, Team-02)
 * @author Fernando Bazan
 * @author Erik Lopez
 * @author Raul Saavedra
 * @author Nathanael Carr
 * @version 1.0
 * @since 1.0
 */
public class VP_Screen extends ScrollPane{
    private final VP_PageDivision div;
    
    /**
     * Constructor.
     * 
     * @param divStr The title of the VP_PageDivision to add to this component.
     * @since 1.0
     */
    public VP_Screen(String divStr) {
        VBox screenContent = new VBox();
        screenContent.prefWidthProperty().bind(this.widthProperty().add(-20));
        screenContent.setSpacing(30);
        screenContent.setPadding(new Insets(20, 20, 20, 20));
        this.setContent(screenContent);
        this.setPannable(true);
        this.div = new VP_PageDivision(divStr);
        screenContent.getChildren().addAll(div);
    }
    
    /**
     * Constructor.
     * 
     * @param div The VP_PageDivision to be added to this component.
     * @since 1.0
     */
    public VP_Screen(VP_PageDivision div) {
        VBox screenContent = new VBox();
        screenContent.prefWidthProperty().bind(this.widthProperty().add(-20));
        screenContent.setSpacing(30);
        screenContent.setPadding(new Insets(20, 20, 20, 20));
        this.setContent(screenContent);
        this.setPannable(true);
        this.div = div;
        screenContent.getChildren().addAll(div);
    }
    
    /**
     * Constructor. 
     * 
     * @param divStr The title of the VP_PageDivision to add to this component.
     * @param imgStr The name of the desired header icon of the VP_PageDivision
     * to add to this component.
     * @param width The preferred width of the VP_PageDivision to add to this 
     * component.
     * @since 1.0
     */
    public VP_Screen(String divStr, String imgStr, double width) {
        VBox screenContent = new VBox();
        screenContent.prefWidthProperty().bind(this.widthProperty().add(-20));
        screenContent.setSpacing(30);
        screenContent.setPadding(new Insets(20, 20, 20, 20));
        this.setContent(screenContent);
        this.setPannable(true);
        div = new VP_PageDivision(divStr, imgStr, width);
        screenContent.getChildren().addAll(div);
    }
    
    /**
     * 
     * @param nodes Add child nodes to this component.
     * @since 1.0
     */
    public void addNodes(Node[] nodes) {
        div.getChildren().addAll(nodes);
    }
}
