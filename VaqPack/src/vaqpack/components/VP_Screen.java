/*-----------------------------------------------------------------------------*
 * VP_Screen.java
 * - Custom ScrollPane class
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 2850
 *-----------------------------------------------------------------------------*/
package vaqpack.components;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class VP_Screen extends ScrollPane{
    private final VP_PageDivision div;
    
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
    
    public void addNodes(Node[] nodes) {
        div.getChildren().addAll(nodes);
    }
}
