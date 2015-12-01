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
 * FILE ID 4100
 *-----------------------------------------------------------------------------*/
package vaqpack.peripherals;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class VP_Loader extends VBox {

    private final StringProperty loadPercentProp,
            activity1,
            activity2;
    private final Label percentLabel,
            activityLabel1,
            activityLabel2;
    private int totalTasks,
            completedTasks;

    /*------------------------------------------------------------------------*
     * VP_Loader()
     * - Constructor.
     * - Parameter sceneWidth used for the preferred width
     * - Parameter sceneHeight used for the preferred height
     *------------------------------------------------------------------------*/
    public VP_Loader(int sceneWidth, int sceneHeight) {
        //-------- Initialization Start ----------\\
        loadPercentProp = new SimpleStringProperty();
        activity1 = new SimpleStringProperty();
        activity2 = new SimpleStringProperty();
        percentLabel = new Label();
        activityLabel1 = new Label();
        activityLabel2 = new Label();
        totalTasks = 0;
        completedTasks = 0;
        //-------- Initialization End ------------\\

        this.setPrefSize(sceneWidth, sceneHeight);
        this.setAlignment(Pos.CENTER);
        this.setId("loader");
        Pane loadLogoPane = new Pane();
        loadLogoPane.setPrefSize(300, 300);
        loadLogoPane.setId("loadLogo");
        percentLabel.getStyleClass().add("loadText");
        activityLabel1.getStyleClass().add("loadText");
        activityLabel2.getStyleClass().add("loadText");
        percentLabel.textProperty().bind(loadPercentProp);
        activityLabel1.textProperty().bind(activity1);
        activityLabel2.textProperty().bind(activity2);
        this.getChildren().addAll(loadLogoPane, percentLabel, activityLabel1, activityLabel2);
    }

    /*------------------------------------------------------------------------*
     * incrementCompletedTasks()
     * - Calculates the percentage of loading completion.
     * - No Paramters
     * - No Return
     *------------------------------------------------------------------------*/
    public void incrementCompletedTasks() {
        //-------- Initialization Start ----------\\
        double percentDouble;
        int percent;
        //-------- Initialization End ------------\\

        completedTasks++;
        percentDouble = (((double) completedTasks / (double) totalTasks) * 100);
        percent = (int) percentDouble;
        loadPercentProp.set("Loading : " + percent + "%");
    }

    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/
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

    public void setTotalTasks(int totalTasks) {
        this.totalTasks = totalTasks;
    }
}
