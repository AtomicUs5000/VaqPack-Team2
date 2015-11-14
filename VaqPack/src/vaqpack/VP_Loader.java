/*-----------------------------------------------------------------------------*
 * VP_Loader.java
 * - Everything involving the loading screen
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 1500
 *-----------------------------------------------------------------------------*/
package vaqpack;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class VP_Loader extends VBox {
    private final StringProperty
            loadPercentProp = new SimpleStringProperty(),
            activity1 = new SimpleStringProperty(),
            activity2 = new SimpleStringProperty();
    private final Label
            percentLabel,
            activityLabel1,
            activityLabel2;
    private int
            totalTasks = 0,
            completedTasks = 0;
    
    /*------------------------------------------------------------------------*
     * VP_Loader()
     * - Constructor.
     * - Parameter sceneWidth used for the preferred width
     * - Parameter sceneHeight used for the preferred height
     *------------------------------------------------------------------------*/
    protected VP_Loader(int sceneWidth, int sceneHeight) {
        this.setPrefSize(sceneWidth, sceneHeight);
        this.setAlignment(Pos.CENTER);
        this.setId("loader");
        Pane loadLogoPane = new Pane();
        loadLogoPane.setPrefSize(300, 300);
        loadLogoPane.setId("loadLogo");
        percentLabel = new Label();
        percentLabel.getStyleClass().add("loadText");
        activityLabel1 = new Label();
        activityLabel1.getStyleClass().add("loadText");
        activityLabel2 = new Label();
        activityLabel2.getStyleClass().add("loadText");
        percentLabel.textProperty().bind(loadPercentProp);
        activityLabel1.textProperty().bind(activity1);
        activityLabel2.textProperty().bind(activity2);
        this.getChildren().addAll(loadLogoPane, percentLabel, activityLabel1, activityLabel2);
    }
    
    protected void incrementCompletedTasks() {
        completedTasks++;
        double percentDouble = (((double)completedTasks / (double)totalTasks) * 100);
        int percent = (int)percentDouble;
        loadPercentProp.set("Loading : " + percent + "%");
    }

    /*------------------------------------------------------------------------*
     * Setters and Getters
     *------------------------------------------------------------------------*/
    public final String getActivity1() {
        return activity1.get();
    }

    public final void setActivity1(String value) {
        activity1.set(value);
    }
    
    public final String getActivity2() {
        return activity2.get();
    }

    public final void setActivity2(String value) {
        activity2.set(value);
    }

    protected int getTotalTasks() {
        return totalTasks;
    }

    protected void setTotalTasks(int totalTasks) {
        this.totalTasks = totalTasks;
    }
}
