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
 * FILE ID 1100
 *-----------------------------------------------------------------------------*/
package vaqpack;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class VP_GUIController {

    private final String title = "VaqPack";
    private final Scene primaryScene;
    private final Stage primaryStage;
    private final StackPane mainLayout;
    private final BorderPane guiLayout;
    private final VP_Loader loader;
    private final VP_DataManager dataM;
    private final VP_GUIBuilder guiBuilder;
    private ArrayList<Runnable> guiTasks,
            dbTasks;
    private ArrayList<String> guiTaskLabels,
            dbTaskLabels;
    private final int USER_PASSWORD_MINIMUM = 12,
            sceneWidth = 1000, // width is temporary
            sceneHeight = 600;           // height is temporary

    /*------------------------------------------------------------------------*
     * VP_GUIController()
     * - Constructor. Stores primaryStage and creates the empty scene and sets 
     *   the title for it. Creates a VP_Loader and sets it as visible, while the
     *   empty GUI is not visible. Creates the Data Manager.
     * - Parameter primaryStage stored for window control.
     * - Calls load().
     *------------------------------------------------------------------------*/
    protected VP_GUIController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        mainLayout = new StackPane();
        guiBuilder = new VP_GUIBuilder(this);
        loader = new VP_Loader(sceneWidth, sceneHeight);
        dataM = new VP_DataManager(this);
        guiLayout = guiBuilder.createShell();
        mainLayout.getChildren().addAll(loader, guiLayout);
        mainLayout.getChildren().get(1).setVisible(false);
        mainLayout.getChildren().get(0).setVisible(true);
        mainLayout.setAlignment(Pos.TOP_LEFT);
        primaryScene = new Scene(mainLayout, sceneWidth, sceneHeight);
        primaryScene.getStylesheets().add(this.getClass().getResource("/vpStyle.css").toExternalForm());
        primaryStage.setTitle(title);
        primaryStage.setScene(primaryScene);
        primaryStage.setOnCloseRequest(new ClosingSequence());
        load();
    }

    /*------------------------------------------------------------------------*
     * errorAlert()
     * - Loads an error into a new VP_ErrorHandler and then shows an alert with
     *   the header and content determined by VP_ErrorHandler.
     * - Parameter errorCode sent to VP_ErrorHandler to determine the message.
     * - Parameter exceptionString is the exception converted to string, if the
     *   error happens to call an exception. Can be an empty string.
     * - No Return.
     *------------------------------------------------------------------------*/
    protected void errorAlert(int errorCode, String exceptionString) {
        VP_ErrorHandler eh = new VP_ErrorHandler(errorCode, exceptionString);
        VPErrorAlert errorAlert = new VPErrorAlert(AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText(eh.getHeader());
        errorAlert.setContentText(eh.getContent());
        errorAlert.showAndWait();
        if (eh.isCritical()) {
            System.exit(-1);
        }
    }

    /*------------------------------------------------------------------------*
     * load()
     * - Configures and/or checks the database and builds the gui components
     *   as background tasks/services. 
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    private void load() {
        dbTasks = new ArrayList();
        guiTasks = new ArrayList();
        dbTaskLabels = new ArrayList();
        guiTaskLabels = new ArrayList();
        dbTaskLabels.add("Retrieving MySQL Location");
        dbTaskLabels.add("Retrieving MySQL Admin Credentials");
        dbTaskLabels.add("Checking User Table");
        dbTaskLabels.add("Checking Access Level Table");
        dbTaskLabels.add("Checking Registering User Table");
        dbTaskLabels.add("Checking Reset Code Table");
        dbTaskLabels.add("Checking Business Card Table");
        dbTaskLabels.add("Checking Contact Table");
        dbTaskLabels.add("Checking Cover Letter Table");
        dbTaskLabels.add("Checking Resume Table");
        dbTaskLabels.add("Checking User Data Table");
        dbTaskLabels.add("Checking Custom Theme Table");
        dbTaskLabels.add("Checking Default Theme Table");
        dbTaskLabels.add("Checking Business Card Has Custom Theme Table");
        dbTaskLabels.add("Checking Business Card Has Default Theme Table");
        dbTaskLabels.add("Checking Business Card PDF Table");
        dbTaskLabels.add("Checking Cover Letter Has Custom Theme Table");
        dbTaskLabels.add("Checking Cover Letter Has Default Theme Table");
        dbTaskLabels.add("Checking Cover Letter PDF Table");
        dbTaskLabels.add("Checking Resume Has Custom Theme Table");
        dbTaskLabels.add("Checking Resume Has Default Theme Table");
        dbTaskLabels.add("Checking Resume PDF Table");
        dbTaskLabels.add("Checking Resume HTML Table");
        dbTaskLabels.add("Verifying User Access Levels");
        dbTaskLabels.add("Verifying VaqPack Admin User Existence");
        dbTaskLabels.add("Database Initialization Complete");
        guiTaskLabels.add("Building Menu and Header");
        guiTaskLabels.add("Building Tree Viewer");
        guiTaskLabels.add("Building Center Panes");
        guiTaskLabels.add("Building Footer");
        guiTaskLabels.add("Application Build Complete");
        for (int i = 0; i < guiTaskLabels.size(); i++) {
            guiTasks.add(new LoadGUITask(i));
        }
        for (int i = 0; i < dbTaskLabels.size(); i++) {
            dbTasks.add(new LoadDBTask(i));
        }
        loader.setTotalTasks(dbTaskLabels.size() + guiTaskLabels.size());
        LoadingThread loadDB = new LoadingThread(dbTasks);
        LoadingThread loadGUI = new LoadingThread(guiTasks);
        loadGUI.setDaemon(true);
        loadDB.start();
        loadGUI.start();
    }

    /*------------------------------------------------------------------------*
     * requestDBLocation()
     * - Displays a dialog requesting the database server location. If the user
     *   cancels this, the program exits.
     * - Parameter type. type = 0 indicates first time showing this dialog.
     *   type = 1 indicates that the dialog is being shown again.
     * - Returns a string array of the database server url and port.
     *------------------------------------------------------------------------*/
    private String[] requestDBLocation(int type) {
        VP_Dialog requestLoc = new VP_Dialog("MySQL Database Server Location");
        Optional result;
        VP_TextField urlField = new VP_TextField(50, 0),
                portField = new VP_TextField(5, 5);
        String[] loc = dataM.getCurrentLocation();
        Label urlLabel = new Label("URL: "),
                portLabel = new Label("PORT: ");
        if (type == 0) {
            requestLoc.setHeaderText("MySQL Database Server Location");
        } else if (type == 1) {
            requestLoc.setHeaderText("Either the provided MySQL url or port is invalid, \n"
                    + "or the MySQL server is offline.");
            urlField.showInvalid();
            portField.showInvalid();
        }
        urlField.setText(loc[0]);
        portField.setText(loc[1]);
        requestLoc.dialogShell.add(urlLabel, 0, 0);
        requestLoc.dialogShell.add(urlField, 1, 0);
        requestLoc.dialogShell.add(portLabel, 0, 1);
        requestLoc.dialogShell.add(portField, 1, 1);
        requestLoc.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        result = requestLoc.showAndWait();
        if (result.get() == ButtonType.OK) {
            loc[0] = urlField.getText();
            loc[1] = portField.getText();
        } else {
            System.exit(-1);
        }
        return loc;
    }

    /*------------------------------------------------------------------------*
     * requestAdminCred()
     * - Displays a dialog requesting the database admin user credentials.
     *   If the user cancels this, the program exits.
     * - Parameter type. type = 0 indicates first time showing this dialog.
     *   type = 1 indicates that the dialog is being shown again.
     * - Returns a string array of the database admin username and password.
     *------------------------------------------------------------------------*/
    private String[] requestAdminCred(int type) {
        VP_Dialog requestCred = new VP_Dialog("MySQL Database Admin Credentials");
        Optional result;
        VP_TextField userField = new VP_TextField(16, 16);
        VP_PasswordField passField = new VP_PasswordField(32, 32, 0, null);
        String[] cred = {"", ""};
        Label userLabel = new Label("ADMIN USER: "),
                passLabel = new Label("ADMIN PASSWORD: ");
        String headerString = "The MySQL admin account must already be created "
                + "for the MyDQL server, with privileges to create a database and tables.";
        if (type == 0) {
            requestCred.setHeaderText("Set up the MySQL admin account.\n" + headerString);
        } else if (type == 1) {
            requestCred.setHeaderText("Either the provided MySQL amdin username or password is invalid.\n"
                    + "Please reenter the correct admin username and password.\n"
                    + headerString);
            userField.showInvalid();
            passField.showInvalid();
        }
        requestCred.dialogShell.add(userLabel, 0, 0);
        requestCred.dialogShell.add(userField, 1, 0);
        requestCred.dialogShell.add(passLabel, 0, 1);
        requestCred.dialogShell.add(passField, 1, 1);
        requestCred.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        result = requestCred.showAndWait();
        if (result.get() == ButtonType.OK) {
            cred[0] = userField.getText();
            cred[1] = passField.getText();
        } else {
            System.exit(-1);
        }
        return cred;
    }

    /*------------------------------------------------------------------------*
     * requestVPAdmin()
     * - Displays a dialog requesting the VaqPack admin user credentials, as
     *   well as confirmation that of database server admin credentials.
     *   If the user cancels this, the program exits.
     * - Parameter type. type = 0 indicates first time showing this dialog.
     *   type = 1 indicates that the dialog is being shown again.
     * - Returns a string array of the database admin username and password and
     *   the VaqPack admin user email and password.
     *------------------------------------------------------------------------*/
    private String[] requestVPAdmin(int type) {
        boolean passwordsOK = false,
                emailOK = false,
                lengthOK = false;
        VP_Dialog requestCred = new VP_Dialog("VaqPack Admin User Setup");
        Optional result;
        VP_TextField userField = new VP_TextField(16, USER_PASSWORD_MINIMUM),
                emailField = new VP_TextField(32, 254);
        String[] cred = {"", "", "", ""};
        Label userLabel = new Label("MySQL Admin User: "),
                passLabel = new Label("MySQL Admin Password: "),
                emailLabel = new Label("VaqPack Admin Email: "),
                passLabel2 = new Label("VaqPack Admin Password: "),
                passLabel3 = new Label("Reenter VP Admin Password:"),
                passStrengthLabel = new Label("(Password must be at least " + USER_PASSWORD_MINIMUM + " characters in length)"),
                passStrengthLevel = new Label("Srength: Unacceptable");
        VP_PasswordField passField = new VP_PasswordField(32, 32, 0, null),
                passField2 = new VP_PasswordField(32, 32, USER_PASSWORD_MINIMUM, passStrengthLevel),
                passField3 = new VP_PasswordField(32, 32, 0, null);
        String cred4 = "",
                headerString = "VaqPack requires at least one admin user "
                + "of the application.\nThis prompt will appear whenever an "
                + "admin user does not exist upon loading.\nThe MySQL database "
                + "admin must login in order to create a VaqPack admin user.\n"
                + "Canceling will force the program to close.";
        if (type == 0) {
            requestCred.setHeaderText(headerString);
        } else {
            requestCred.setHeaderText(headerString + "\n\nThe provided database admin credentials "
                    + "were incorrect.\nPlease try again.");
            userField.showInvalid();
            passField.showInvalid();
        }
        requestCred.dialogShell.add(userLabel, 0, 0);
        requestCred.dialogShell.add(userField, 1, 0);
        requestCred.dialogShell.add(passLabel, 0, 1);
        requestCred.dialogShell.add(passField, 1, 1);
        requestCred.dialogShell.add(emailLabel, 0, 2);
        requestCred.dialogShell.add(emailField, 1, 2);
        requestCred.dialogShell.add(passLabel2, 0, 3);
        requestCred.dialogShell.add(passField2, 1, 3);
        requestCred.dialogShell.add(passStrengthLabel, 0, 4);
        requestCred.dialogShell.add(passStrengthLevel, 1, 4);
        requestCred.dialogShell.add(passLabel3, 0, 5);
        requestCred.dialogShell.add(passField3, 1, 5);
        requestCred.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        while (!passwordsOK || !emailOK || !lengthOK) {
            result = requestCred.showAndWait();
            if (result.get() == ButtonType.OK) {
                cred[0] = userField.getText();
                cred[1] = passField.getText();
                cred[2] = emailField.getText().toLowerCase();
                cred[3] = passField2.getText();
                cred4 = passField3.getText();
            } else {
                System.exit(-1);
            }
            if (cred[3].equals(cred4)) {
                passwordsOK = true;
                emailOK = dataM.checkEmail(cred[2]);
                lengthOK = true;
                if (!emailOK) {
                    requestCred.setHeaderText(headerString + "\n\nThe entered VaqPack admin email address "
                            + "is not in valid email form.\nPlease try again.");
                    emailField.showInvalid();
                } else if (cred[3].length() < USER_PASSWORD_MINIMUM) {
                    lengthOK = false;
                    requestCred.setHeaderText(headerString + "\n\nThe VaqPack admin user password is "
                            + "not long enough.\nPlease try again.");
                    passField2.showInvalid();
                    passField3.showInvalid();
                }
            } else {
                passField2.setText("");
                passField3.setText("");
                requestCred.setHeaderText(headerString + "\n\nThe VaqPack admin user passwords do "
                        + "not match.\nPlease try again.");
                passField2.showInvalid();
                passField3.showInvalid();
            }
        }
        return cred;
    }

    /*------------------------------------------------------------------------*
     * exposeGUI()
     * - Hides the loader and makes the GUI visible
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    private void exposeGUI() {
        mainLayout.getChildren().get(0).setVisible(false);
        mainLayout.getChildren().get(1).setVisible(true);
    }

    /*------------------------------------------------------------------------*
     * Subclasses
     *------------------------------------------------------------------------*/

    /*------------------------------------------------------------------------*
     * Subclass LoadDBTasks
     * - Defines the runnablw wrappers for the datatbase tasks.
     *------------------------------------------------------------------------*/
    private class LoadDBTask implements Runnable {

        private final int stage;
        private boolean adminCheck;
        private CountDownLatch adminlatch;

        public LoadDBTask(int stage) {
            this.stage = stage;
        }

        @Override
        public void run() {
            Platform.runLater(() -> (loader.setActivity1(dbTaskLabels.get(stage))));
            if (stage == 0 || stage == 1) {
                boolean complete = false;
                while (!complete) {
                    boolean retrieveComplete = false;
                    while (!retrieveComplete) {
                        try {
                            if (stage == 0) {
                                getDataM().retrieveDBLocation();
                            } else if (stage == 1) {
                                getDataM().retrieveAdmin();
                            }
                            retrieveComplete = true;
                        } catch (FileNotFoundException ex) {
                            CountDownLatch latch = new CountDownLatch(1);
                            Platform.runLater(() -> {
                                try {
                                    if (stage == 0) {
                                        getDataM().storeDBLocation(requestDBLocation(0));
                                        latch.countDown();
                                    } else if (stage == 1) {
                                        getDataM().storeAdminCred(requestAdminCred(0));
                                        latch.countDown();
                                    }
                                } catch (IOException ex2) {
                                    Platform.runLater(() -> errorAlert(1302 + (3 * stage), ex2.getMessage()));
                                } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                                        InvalidKeyException | InvalidAlgorithmParameterException |
                                        IllegalBlockSizeException | BadPaddingException ex2) {
                                    Platform.runLater(() -> errorAlert(1303 + (3 * stage), ex2.getMessage()));
                                }
                            });
                            try {
                                latch.await();
                            } catch (InterruptedException ex1) {
                                Platform.runLater(() -> errorAlert(1101, ex1.getMessage()));
                            } finally {
                                latch.countDown();
                            }
                        } catch (IOException ex) {
                            Platform.runLater(() -> errorAlert(1301 + (3 * stage), ex.getMessage()));
                        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                                InvalidKeyException | InvalidAlgorithmParameterException |
                                IllegalBlockSizeException | BadPaddingException ex) {
                            Platform.runLater(() -> errorAlert(1303 + (3 * stage), ex.getMessage()));
                        }
                    }
                    try {
                        if (stage == 0) {
                            getDataM().checkDBLocation();
                            complete = true;
                        } else if (stage == 1) {
                            getDataM().checkDBSchemaExists();
                        }

                    } catch (SQLException ex) {
                        if ((ex.getErrorCode() == 0 && stage == 0) || (ex.getErrorCode() == 1045 && stage == 1)) {
                            CountDownLatch latch = new CountDownLatch(1);
                            Platform.runLater(() -> {
                                try {
                                    if (stage == 0) {
                                        getDataM().storeDBLocation(requestDBLocation(1));
                                        latch.countDown();
                                    } else if (stage == 1) {
                                        getDataM().storeAdminCred(requestAdminCred(1));
                                        latch.countDown();
                                    }
                                } catch (IOException ex2) {
                                    Platform.runLater(() -> errorAlert(1302 + (3 * stage), ex2.getMessage()));
                                } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                                        InvalidKeyException | InvalidAlgorithmParameterException |
                                        IllegalBlockSizeException | BadPaddingException ex2) {
                                    Platform.runLater(() -> errorAlert(1303 + (3 * stage), ex2.getMessage()));
                                } finally {
                                    latch.countDown();
                                }
                            });
                            try {
                                latch.await();
                            } catch (InterruptedException ex1) {
                                errorAlert(1102, ex.getMessage());
                            } finally {
                                latch.countDown();
                            }
                        } else if (stage == 1 && ex.getErrorCode() == 1007) {
                            complete = true;
                        } else {
                            Platform.runLater(() -> errorAlert(1401 + stage, ex.getMessage()));
                        }
                    }
                }
            } else if (stage > 1 && stage < 24) {
                try {
                    if (stage < 23) {
                        getDataM().checkDBTable(stage - 2);
                    } else if (stage == 23) {
                        getDataM().contructUserAccess();
                    }
                } catch (SQLException ex) {
                    if (ex.getErrorCode() != 1050 && stage < 23) {
                        Platform.runLater(() -> errorAlert(1403, ex.getMessage()));
                    } else if (stage == 23) {
                        Platform.runLater(() -> errorAlert(1404, ex.getMessage()));
                    }
                }
            } else if (stage == 24) {
                boolean adminExists = false;
                while (!adminExists && !adminCheck) {
                    adminCheck = false;
                    adminlatch = new CountDownLatch(1);
                    try {
                        adminExists = getDataM().searchForVPAdmin();
                    } catch (SQLException ex) {
                        Platform.runLater(() -> errorAlert(1307, ex.getMessage()));
                    }
                    if (!adminExists) {
                        Platform.runLater(() -> {
                            try {
                                adminCheck = getDataM().createVPAdmin(requestVPAdmin(0));
                                adminlatch.countDown();
                            } catch (SQLException | NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                                Platform.runLater(() -> errorAlert(1306, ex.getMessage()));
                            }
                        });
                    } else {
                        adminlatch.countDown();
                    }
                    try {
                        adminlatch.await();
                    } catch (InterruptedException ex) {
                        errorAlert(1102, ex.getMessage());
                    }
                    if (!adminExists && !adminCheck) {
                        while (!adminCheck) {
                            adminlatch = new CountDownLatch(1);
                            Platform.runLater(() -> {
                                try {
                                    adminCheck = getDataM().createVPAdmin(requestVPAdmin(1));
                                    adminlatch.countDown();
                                } catch (SQLException | NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                                    Platform.runLater(() -> errorAlert(1306, ex.getMessage()));
                                }
                            });
                            try {
                                adminlatch.await();
                            } catch (InterruptedException ex) {
                                Platform.runLater(() -> errorAlert(1102, ex.getMessage()));
                            } finally {
                                adminlatch.countDown();
                            }
                        }
                    }
                }
            } else {
                CountDownLatch latch = new CountDownLatch(1);
                Platform.runLater(() -> {
                    loader.incrementCompletedTasks();
                    latch.countDown();
                });
                try {
                    latch.await();
                    Thread.sleep(500);
                    exposeGUI();
                } catch (InterruptedException ex) {
                    Platform.runLater(() -> errorAlert(1102, ex.getMessage()));
                }
            }
            if (stage != dbTaskLabels.size() - 1) {
                Platform.runLater(() -> loader.incrementCompletedTasks());
            }
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass LoadGUITask
     * - Defines the runnable wrappers for the gui tasks.
     *------------------------------------------------------------------------*/
    private class LoadGUITask implements Runnable {

        private final int stage;

        public LoadGUITask(int stage) {
            this.stage = stage;
        }

        @Override
        public void run() {
            Platform.runLater(() -> (loader.setActivity2(guiTaskLabels.get(stage))));
            if (stage == 0) {
                Platform.runLater(() -> guiBuilder.buildTop());
            } else if (stage == 1) {
                Platform.runLater(() -> guiBuilder.buildLeft());
            } else if (stage == 2) {
                Platform.runLater(() -> guiBuilder.buildCenter());
            } else if (stage == 3) {
                Platform.runLater(() -> guiBuilder.buildBottom());
            }
            Platform.runLater(() -> loader.incrementCompletedTasks());
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass LoadingThread
     * - Runs the tasks during loading.
     *------------------------------------------------------------------------*/
    private class LoadingThread extends Thread {

        private final ArrayList<Runnable> tasks;

        public LoadingThread(ArrayList<Runnable> tasks) {
            this.tasks = tasks;
        }

        @Override
        public void run() {
            for (int i = 0; i < tasks.size(); i++) {
                try {
                    tasks.get(i).run();
                    LoadingThread.sleep(4000 / tasks.size());
                } catch (InterruptedException ex) {
                    Platform.runLater(() -> errorAlert(1101, ex.getMessage()));
                }
            }
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass VP_Dialog
     * - Custom-styled JavaFX Dialog.
     *------------------------------------------------------------------------*/
    private class VP_Dialog extends Dialog {

        private final GridPane dialogShell;
        /*---------------------------------------------------------------------*
         * VPDialog()
         * - Constructor.
         * - Parameter title is the string title of the window.
         *---------------------------------------------------------------------*/

        public VP_Dialog(String title) {
            this.getDialogPane().getStylesheets().add(this.getClass().getResource("/vpStyle.css").toExternalForm());
            this.setTitle(title);
            VBox shellPad = new VBox();
            this.dialogShell = new GridPane();
            this.dialogShell.setAlignment(Pos.CENTER_LEFT);
            this.dialogShell.setVgap(20);
            this.dialogShell.setHgap(20);
            this.dialogShell.getStyleClass().add("dialogGrid");
            shellPad.getChildren().add(dialogShell);
            shellPad.getStyleClass().add("dialogPad");
            this.getDialogPane().setContent(shellPad);
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass VPErrorAlert
     * - Custom-styled JavaFX Alert for errors.
     *------------------------------------------------------------------------*/
    private class VPErrorAlert extends Alert {

        public VPErrorAlert(AlertType alertType) {
            super(alertType);
            this.getDialogPane().getStylesheets().add(this.getClass().getResource("/vpStyle.css").toExternalForm());
            this.getDialogPane().getStyleClass().add("errorAlert");
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass ClosingSequence
     * - Prevents the window from closing before the user saves things.
     *------------------------------------------------------------------------*/
    protected class ClosingSequence implements EventHandler {

        @Override
        public void handle(Event event) {
            event.consume();
            boolean exitCancelled = false;
            // insert code alert user to save things. if necessary
            // let the alert return a value
            // exitCancelled = blahblahblah
            if (!exitCancelled) {
                System.exit(0);
            }
        }
    }

    /*------------------------------------------------------------------------*
     * Setters and Getters
     *------------------------------------------------------------------------*/
    protected Stage getPrimaryStage() {
        return primaryStage;
    }

    protected int getUSER_PASSWORD_MINIMUM() {
        return USER_PASSWORD_MINIMUM;
    }

    protected VP_DataManager getDataM() {
        return dataM;
    }
}
