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
import javafx.scene.layout.VBox;

public class VP_Loader extends VBox {
    private final StringProperty activity = new SimpleStringProperty();
    private final Label activityLabel;
    
    /*------------------------------------------------------------------------*
     * VP_Loader()
     * - Constructor.
     *------------------------------------------------------------------------*/
    protected VP_Loader(int sceneWidth, int sceneHeight) {
        this.setPrefSize(sceneWidth, sceneHeight);
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: rgb(200, 100, 100);");
        Label testLabel1 = new Label("VaqPack Title");
        Label testLabel2 = new Label("VaqPack Logo");
        Label testLabel3 = new Label("Loading percent");
        activityLabel = new Label();
        activityLabel.textProperty().bind(activity);
        this.getChildren().addAll(testLabel1, testLabel2, testLabel3, activityLabel);
    }

    /*------------------------------------------------------------------------*
     * Setters and Getters
     *------------------------------------------------------------------------*/
    public final String getActivity() {
        return activity.get();
    }

    public final void setActivity(String value) {
        activity.set(value);
    }
}
