package vaqpack.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class VP_ErrorLine extends HBox {
    
    private final VP_Paragraph errorText;
    
    public VP_ErrorLine() {
        errorText = new VP_Paragraph("", true);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(20);
        this.setPadding(new Insets(2, 0, 2, 2));
        this.getChildren().add(errorText);
        this.setVisible(false);
        this.setManaged(false);
    }
    
    public void setText(String text) {
        errorText.setParaText(text);
    }
    
    public void hide() {
        this.setVisible(false);
        this.setManaged(false);
        errorText.setParaText("");
    }
    
    public void show() {
        this.setVisible(true);
        this.setManaged(true);
    }
}
