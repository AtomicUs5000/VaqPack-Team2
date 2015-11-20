/*-----------------------------------------------------------------------------*
 * VaqPack.java
 * - Main class for VaqPack project
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 1000
 *-----------------------------------------------------------------------------*/
package vaqpack;

import javafx.application.Application;
import javafx.stage.Stage;

public class VaqPack extends Application {

    @Override
    public void start(Stage primaryStage) {
        VP_GUIController controller = new VP_GUIController(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
}
