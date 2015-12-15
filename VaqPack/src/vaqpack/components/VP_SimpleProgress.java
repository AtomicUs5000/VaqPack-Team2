/**
 * VP_SimpleProgress.java - Custom Label Class.
 */
package vaqpack.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

/**
 * Defines a custom Label class. This extends Label and consists of
 * a default constructor and a method to apply a style indicating user progress.
 *
 * @author William Dewald (Project Manager, Team-02)
 * @author Fernando Bazan
 * @author Erik Lopez
 * @author Raul Saavedra
 * @author Nathanael Carr
 * @version 1.0
 * @since 1.0
 */
public class VP_SimpleProgress extends Label {
    
    /**
     * Constructor.
     * 
     * @since 1.0
     */
    public VP_SimpleProgress() {
        this.getStyleClass().add("notStarted");
        this.setMinWidth(250);
        this.setAlignment(Pos.CENTER_RIGHT);
    }
    
    /**
     * Applies a style indicating user progress.
     * 
     * @param stage Defines the current progress status
     * <ul>
     * <li>0 = Not Started</li>
     * <li>1 = In Progress</li>
     * <li>2 = Complete</li>
     * </ul>
     */
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
