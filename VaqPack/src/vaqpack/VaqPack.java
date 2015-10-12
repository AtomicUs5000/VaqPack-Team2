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
 *-----------------------------------------------------------------------------*/
package vaqpack;

import javafx.application.Application;
import javafx.stage.Stage;

public class VaqPack extends Application {

    @Override
    public void start(Stage primaryStage) {
        VP_GUIEvents GE = new VP_GUIEvents(primaryStage);
        
        primaryStage.setTitle("VaqPack");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
