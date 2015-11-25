/**
 * VaqPack.java - Main class for VaqPack project. FILE ID 1000
 */
package vaqpack;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class. Extends Application.
 *
 * @author William Dewald (Project Manager, Team-02)
 * @author Fernando Bazan
 * @author Erik Lopez
 * @author Raul Saavedra
 * @author Nathanael Carr
 * @version 1.0
 * @since 1.0
 */
public class VaqPack extends Application {

    /**
     * The main entry point for this JavaFX program. VP_GUIController controller
     * is instantiated, passing the top-level Stage container. The primary Stage
     * is shown.
     *
     * @param primaryStage The top-level Stage container,
     * @since 1.0
     */
    @Override
    public void start(Stage primaryStage) {
        VP_GUIController controller = new VP_GUIController(primaryStage);
        primaryStage.show();
    }

    /**
     * @param args The array of command line arguments. Currently not
     * implemented in this project.
     * @since 1.0
     */
    public static void main(String[] args) {
        launch(args);
    }

    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
}
