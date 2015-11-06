/*-----------------------------------------------------------------------------*
 * VP_GUIController.java
 * - Events triggered by or altering the VaqPak GUI
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 *-----------------------------------------------------------------------------*/
package vaqpack;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VP_GUIController {

    private final Scene
            primaryScene;
    private final Stage
            primaryStage;
    private final VP_DataManager dataM;
    private final VP_GUIBuilder builder;

    /*------------------------------------------------------------------------*
     * VP_GUIController
     * - Constructor. Stores primaryStage and creates the scene for it
     * by creating a new VP_GUIBuiler object and applying its mainLayout.
     * - parameter primaryStage stored for window control
     *------------------------------------------------------------------------*/
    protected VP_GUIController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        builder = new VP_GUIBuilder(this);
        primaryScene = new Scene(builder.getMainLayout(),
                builder.getSceneWidth(),
                builder.getSceneHeight());
        dataM = new VP_DataManager(this);
        primaryStage.setScene(primaryScene);
    }

    /*------------------------------------------------------------------------*
     * errorAlert()
     * - loads an error into VP_ErrorHandler and then shows an alert with the
     * header and content determined by VP_ErrorHandler.
     * - parameter errorCode sent to VP_ErrorHandler to determine the message.
     * - parameter exceptionString is the exception converted to string, if the
     * error happens to call an exception. Can be an empty string.
     * - No return
     *------------------------------------------------------------------------*/
    protected void errorAlert(int errorCode, String exceptionString) {
        VP_ErrorHandler eh = new VP_ErrorHandler(errorCode, exceptionString);
        Alert errorAlert = new Alert(AlertType.ERROR);
        
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText(eh.getHeader());
        errorAlert.setContentText(eh.getContent());
        errorAlert.showAndWait();
        if (eh.isCritical()) {
            System.exit(-1);
        }
    }

    /*------------------------------------------------------------------------*
     * Action Event Subclasses
     *------------------------------------------------------------------------*/
    /*------------------------------------------------------------------------*
     * TestEvent Subclass
     * - Just a test
     *------------------------------------------------------------------------*/
    protected class TestEvent implements EventHandler<ActionEvent> {

        private final int testType;

        protected TestEvent(int testType) {
            this.testType = testType;
        }

        @Override
        public void handle(ActionEvent event) {
            int test = 0;
            try {
                test = Integer.parseInt("not an integer");
            } catch (Exception e) {
                errorAlert(testType, e.toString());
            }
        }
    }

    /*------------------------------------------------------------------------*
     * Setters and Getters
     *------------------------------------------------------------------------*/
}
