package vaqpack.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class VP_SimpleProgress extends Label {
    
    public VP_SimpleProgress() {
        this.getStyleClass().add("notStarted");
        this.setMinWidth(250);
        this.setAlignment(Pos.CENTER_RIGHT);
    }
    
    public void setStage(int stage) {
        if (stage == 0) {
            this.getStyleClass().set(this.getStyleClass().size() - 1, "notStarted");
            this.setText("Not started");
        }
        else if (stage == 1) {
            this.getStyleClass().set(this.getStyleClass().size() - 1, "inProgress");
            this.setText("In progress");
        }
        else if (stage == 2) {
            this.getStyleClass().set(this.getStyleClass().size() - 1, "complete");
            this.setText("Complete");
        }
    }
}
