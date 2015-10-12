/*-----------------------------------------------------------------------------*
 * VP_GUIEvents.java
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

public class VP_GUIEvents {

    private final Scene primaryScene;
    private final Stage primaryStage;
    private final VP_DatabaseManager DB;
    private final VP_DataManager DM;
    private final VP_DataToHtml D2H;
    private final VP_FileManager FM;
    private final VP_GUIBuild GB;
    private final VP_HtmlToPdf H2P;
    private final VP_NetworkManager NM;
    private final VP_TemplateManager TM;

    /*------------------------------------------------------------------------*
     * VP_GUIEvents
     * - Constructor. Stores primaryStage and creates the scene for it
     * by creating a new VP_GUIBuiler object and applying its mainLayout.
     * - parameter primaryStage stored for window control
     *------------------------------------------------------------------------*/
    protected VP_GUIEvents(Stage primaryStage) {
        this.primaryStage = primaryStage;
        GB = new VP_GUIBuild(this);
        primaryScene = new Scene(GB.getMainLayout(),
                GB.getSceneWidth(),
                GB.getSceneHeight());
        DB = new VP_DatabaseManager(this);
        DM = new VP_DataManager();
        D2H = new VP_DataToHtml();
        H2P = new VP_HtmlToPdf();
        FM = new VP_FileManager();
        NM = new VP_NetworkManager(this);
        TM = new VP_TemplateManager(this);
        
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
    protected VP_DatabaseManager getDB() {
        return DB;
    }

    protected VP_DataManager getDM() {
        return DM;
    }

    protected VP_FileManager getFM() {
        return FM;
    }

    protected VP_GUIBuild getGB() {
        return GB;
    }
}
