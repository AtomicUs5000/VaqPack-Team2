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
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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
    private ArrayList<Service> dbservices,
            guiservices;
    private ArrayList<String> dbserviceLabels,
            guiserviceLabels;
    private final int sceneWidth = 1000, // width is temporary
            sceneHeight = 600;           // height is temporary

    /*------------------------------------------------------------------------*
     * VP_GUIController()
     * - Constructor. Stores primaryStage and creates the empty scene and sets 
     *   the title for it. Creates a VP_Loader and sets it as visible, while the
     *   empty GUI is not visible. Creates the Data Manager.
     * - Calls load().
     * - Parameter primaryStage stored for window control.
     *------------------------------------------------------------------------*/
    protected VP_GUIController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        mainLayout = new StackPane();
        guiBuilder = new VP_GUIBuilder();
        loader = new VP_Loader(sceneWidth, sceneHeight);
        dataM = new VP_DataManager(this, loader);
        guiLayout = guiBuilder.createShell();
        mainLayout.getChildren().addAll(loader, guiLayout);
        mainLayout.getChildren().get(1).setVisible(false);
        mainLayout.getChildren().get(0).setVisible(true);
        primaryScene = new Scene(mainLayout, sceneWidth, sceneHeight);
        primaryScene.getStylesheets().add(this.getClass().getResource("/vpStyle.css").toExternalForm());
        primaryStage.setTitle(title);
        primaryStage.setScene(primaryScene);
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
        dbserviceLabels = new ArrayList();
        guiserviceLabels = new ArrayList();
        dbserviceLabels.add("Retrieving MySQL Location");
        dbserviceLabels.add("Checking MySQL Connectivity");
        dbserviceLabels.add("Retrieving MySQL Admin Credentials");
        dbserviceLabels.add("Checking MySQL Admin Credentials");
        dbserviceLabels.add("Checking User Table");
        dbserviceLabels.add("Checking Access Level Table");
        dbserviceLabels.add("Checking Business Card Table");
        dbserviceLabels.add("Checking Contact Table");
        dbserviceLabels.add("Checking Cover Letter Table");
        dbserviceLabels.add("Checking Resume Table");
        dbserviceLabels.add("Checking User Data Table");
        dbserviceLabels.add("Checking Custom Theme Table");
        dbserviceLabels.add("Checking Default Theme Table");
        dbserviceLabels.add("Checking Business Card Has Custom Theme Table");
        dbserviceLabels.add("Checking Business Card Has Default Theme Table");
        dbserviceLabels.add("Checking Business Card PDF Table");
        dbserviceLabels.add("Checking Cover Letter Has Custom Theme Table");
        dbserviceLabels.add("Checking Cover Letter Has Default Theme Table");
        dbserviceLabels.add("Checking Cover Letter PDF Table");
        dbserviceLabels.add("Checking Resume Has Custom Theme Table");
        dbserviceLabels.add("Checking Resume Has Default Theme Table");
        dbserviceLabels.add("Checking Resume PDF Table");
        dbserviceLabels.add("Checking Resume HTML Table");
        dbserviceLabels.add("Checking User Access Levels");
        dbserviceLabels.add("Checking VaqPack Admin User");
        guiserviceLabels.add("Building Menu and Header");
        guiserviceLabels.add("Building Tree Viewer");
        guiserviceLabels.add("Building Center Panes");
        dbservices = new ArrayList();
        guiservices = new ArrayList();
        dbservices.add(new DBFileService(1));
        dbservices.add(new DBService(1, 0));
        dbservices.add(new DBFileService(2));
        dbservices.add(new DBService(2, 0));
        for (int i = 0; i < 19; i++) {
            dbservices.add(new DBService(3, i));
        }
        dbservices.add(new DBService(4, 0));
        dbservices.add(new DBService(5, 0));
        dbservices.add(new DBService(6, 0));
        dbservices.add(new DBService(7, 0));
        for (int i = 0; i < 3; i++) {
            guiservices.add(new GUIService(i));
        }
        loader.setTotalTasks(dbservices.size() + guiservices.size() - 2);
        loader.setActivity1(dbserviceLabels.get(0));
        loader.setActivity2(guiserviceLabels.get(0));
        dbservices.get(0).start();
        guiservices.get(0).start();
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
        VPDialog requestLoc = new VPDialog("MySQL Database Server Location");
        Optional result;
        TextField urlField = new TextField(),
                portField = new TextField();
        String[] loc = dataM.getCurrentLocation();
        Label urlLabel = new Label("URL: "),
                portLabel = new Label("PORT: ");
        if (type == 0) {
            requestLoc.setHeaderText("MySQL Database Server Location");
        } else if (type == 1) {
            requestLoc.setHeaderText("Either the provided MySQL url or port is invalid, \n"
                    + "or the MySQL server is offline.");
        }
        urlField.setText(loc[0]);
        urlField.setPrefColumnCount(50);
        urlLabel.setPrefWidth(50);
        portField.setText(loc[1]);
        portField.setPrefColumnCount(5);
        portField.setOnKeyTyped(new TextFieldLimiter(portField, 5));
        portField.setOnKeyPressed(new TextFieldLimiter(portField, 5));
        portField.setOnKeyReleased(new TextFieldLimiter(portField, 5));
        portLabel.setPrefWidth(50);
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
        VPDialog requestCred = new VPDialog("MySQL Database Admin Credentials");
        Optional result;
        TextField userField = new TextField();
        PasswordField passField = new PasswordField();
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
        }
        userField.setPrefColumnCount(16);
        userField.setOnKeyTyped(new TextFieldLimiter(userField, 16));
        userField.setOnKeyPressed(new TextFieldLimiter(userField, 16));
        userField.setOnKeyReleased(new TextFieldLimiter(userField, 16));
        userLabel.setPrefWidth(120);
        passField.setPrefColumnCount(32);
        passField.setOnKeyTyped(new TextFieldLimiter(userField, 32));
        passField.setOnKeyPressed(new TextFieldLimiter(userField, 32));
        passField.setOnKeyReleased(new TextFieldLimiter(userField, 32));
        passLabel.setPrefWidth(120);
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
        boolean passwordsOK = false;
        VPDialog requestCred = new VPDialog("VaqPack Admin User Setup");
        Optional result;
        TextField userField = new TextField(),
                emailField = new TextField();
        PasswordField passField = new PasswordField(),
                passField2 = new PasswordField(),
                passField3 = new PasswordField();
        String[] cred = {"", "", "", ""};
        Label userLabel = new Label("MySQL Admin User: "),
                passLabel = new Label("MySQL Admin Password: "),
                emailLabel = new Label("VaqPack Admin Email: "),
                passLabel2 = new Label("VaqPack Admin Password: "),
                passLabel3 = new Label("Reenter VP Admin Password:");
        String cred4 = "",
                headerString = "VaqPack requires at least one admin user "
                + "of the application.\nThis prompt will appear whenever an "
                + "admin user does not exist upon loading.\nThe MySQL database "
                + "admin must login in order to create a VaqPack admin user.\n"
                + "Canceling will force the program to close.";
        if (type == 0) {
            requestCred.setHeaderText(headerString);
        } else {
            requestCred.setHeaderText("The provided database admin credentials "
                    + "were incorrect.\nPlease try again.\n\n" + headerString);
        }
        userField.setPrefColumnCount(16);
        userField.setOnKeyTyped(new TextFieldLimiter(userField, 16));
        userField.setOnKeyPressed(new TextFieldLimiter(userField, 16));
        userField.setOnKeyReleased(new TextFieldLimiter(userField, 16));
        userLabel.setPrefWidth(160);
        passField.setPrefColumnCount(32);
        passField.setOnKeyTyped(new TextFieldLimiter(userField, 32));
        passField.setOnKeyPressed(new TextFieldLimiter(userField, 32));
        passField.setOnKeyReleased(new TextFieldLimiter(userField, 32));
        passLabel.setPrefWidth(160);
        emailField.setPrefColumnCount(32);
        emailField.setOnKeyTyped(new TextFieldLimiter(userField, 254));
        emailField.setOnKeyPressed(new TextFieldLimiter(userField, 254));
        emailField.setOnKeyReleased(new TextFieldLimiter(userField, 254));
        emailLabel.setPrefWidth(160);
        passField2.setPrefColumnCount(32);
        passField2.setOnKeyTyped(new TextFieldLimiter(userField, 32));
        passField2.setOnKeyPressed(new TextFieldLimiter(userField, 32));
        passField2.setOnKeyReleased(new TextFieldLimiter(userField, 32));
        passLabel2.setPrefWidth(160);
        passField3.setPrefColumnCount(32);
        passField3.setOnKeyTyped(new TextFieldLimiter(userField, 32));
        passField3.setOnKeyPressed(new TextFieldLimiter(userField, 32));
        passField3.setOnKeyReleased(new TextFieldLimiter(userField, 32));
        passLabel3.setPrefWidth(160);
        requestCred.dialogShell.add(userLabel, 0, 0);
        requestCred.dialogShell.add(userField, 1, 0);
        requestCred.dialogShell.add(passLabel, 0, 1);
        requestCred.dialogShell.add(passField, 1, 1);
        requestCred.dialogShell.add(emailLabel, 0, 2);
        requestCred.dialogShell.add(emailField, 1, 2);
        requestCred.dialogShell.add(passLabel2, 0, 3);
        requestCred.dialogShell.add(passField2, 1, 3);
        requestCred.dialogShell.add(passLabel3, 0, 4);
        requestCred.dialogShell.add(passField3, 1, 4);
        requestCred.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        while (!passwordsOK) {
            result = requestCred.showAndWait();
            if (result.get() == ButtonType.OK) {
                cred[0] = userField.getText();
                cred[1] = passField.getText();
                cred[2] = emailField.getText();
                cred[3] = passField2.getText();
                cred4 = passField3.getText();
            } else {
                System.exit(-1);
            }
            if (cred[3].equals(cred4)) {
                passwordsOK = true;
            } else {
                passField2.setText("");
                passField3.setText("");
                requestCred.setHeaderText("The VaqPack admin user passwords do "
                        + "not match.\nPlease try again.\n\n" + headerString);
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
     * Subclass DBFileService
     * - Defines the tasks that involve the File Manager.
     *------------------------------------------------------------------------*/
    private class DBFileService extends Service<Void> {

        private final int stage;

        public DBFileService(int stage) {
            this.stage = stage;
            // checking stored connectivity properties
            if (stage == 1) {
                this.setOnSucceeded((WorkerStateEvent event) -> {
                    loader.incrementCompletedTasks();
                    loader.setActivity1(dbserviceLabels.get(1));
                    dbservices.get(1).reset();
                    dbservices.get(1).start();
                });
                this.setOnFailed((WorkerStateEvent event) -> {
                    if (this.exceptionProperty().getValue().toString().contains("FileNotFoundException")) {
                        try {
                            dataM.storeDBLocation(requestDBLocation(0));
                            loader.incrementCompletedTasks();
                            loader.setActivity1(dbserviceLabels.get(1));
                            dbservices.get(1).reset();
                            dbservices.get(1).start();
                        } catch (IOException ex) {
                            errorAlert(1302, ex.getMessage());
                        }
                    } else {
                        errorAlert(1301, this.getException().getMessage());
                    }
                });
            } // checking encrypted stored mysql admin credentials
            else if (stage == 2) {
                this.setOnSucceeded((WorkerStateEvent event) -> {
                    loader.incrementCompletedTasks();
                    loader.setActivity1(dbserviceLabels.get(3));
                    dbservices.get(3).reset();
                    dbservices.get(3).start();
                });
                this.setOnFailed((WorkerStateEvent event) -> {
                    if (!this.exceptionProperty().getValue().toString().contains("FileNotFoundException")) {
                        errorAlert(1304, this.getException().getMessage());
                    }
                    try {
                        dataM.storeAdminCred(requestAdminCred(0));
                        loader.incrementCompletedTasks();
                        loader.setActivity1(dbserviceLabels.get(3));
                        dbservices.get(3).reset();
                        dbservices.get(3).start();
                    } catch (IOException ex) {
                        errorAlert(1305, ex.getMessage());
                    } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                            InvalidKeyException | InvalidAlgorithmParameterException |
                            IllegalBlockSizeException | BadPaddingException ex) {
                        errorAlert(1303, ex.getMessage());
                    }
                });
            }
            this.setOnScheduled((WorkerStateEvent event) -> {
                try {
                    Thread.sleep(180);
                } catch (InterruptedException ex) {
                    errorAlert(1101, ex.getMessage());
                }
            });
        }

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                public Void call() throws FileNotFoundException,
                        IOException, NoSuchAlgorithmException,
                        IllegalBlockSizeException, BadPaddingException,
                        NoSuchPaddingException, InvalidKeyException,
                        InvalidAlgorithmParameterException, InterruptedException {
                    if (stage == 1) {
                        dataM.retrieveDBLocation();
                    } else if (stage == 2) {
                        dataM.retrieveAdmin();
                    }
                    Thread.sleep(250);
                    return null;
                }
            };
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass DBService
     * - Defines the tasks that involve the Database Manager.
     *------------------------------------------------------------------------*/
    private class DBService extends Service<Void> {

        private final int stage, subStage;
        private int vpAdminType = 0;
        private boolean adminCheck = false,
                adminExists = false;

        public DBService(int stage, int subStage) {
            this.stage = stage;
            this.subStage = subStage;
            // connectivity checking
            if (stage == 1) {
                this.setOnSucceeded((WorkerStateEvent event) -> {
                    loader.incrementCompletedTasks();
                    loader.setActivity1(dbserviceLabels.get(2));
                    dbservices.get(2).reset();
                    dbservices.get(2).start();
                });
                this.setOnFailed((WorkerStateEvent event) -> {
                    if (((SQLException) (this.getException())).getErrorCode() == 0) {
                        try {
                            dataM.storeDBLocation(requestDBLocation(1));
                            loader.setActivity1(dbserviceLabels.get(0));
                            dbservices.get(0).reset();
                            dbservices.get(0).start();
                        } catch (IOException ex) {
                            errorAlert(1302, ex.getMessage());
                        }
                    } else {
                        errorAlert(1401, ((SQLException) (this.getException())).getMessage());
                    }
                });
            } // mysql admin checking and main databse schema
            else if (stage == 2) {
                this.setOnSucceeded((WorkerStateEvent event) -> {
                    loader.incrementCompletedTasks();
                    loader.setActivity1(dbserviceLabels.get(4));
                    dbservices.get(4).reset();
                    dbservices.get(4).start();
                });
                this.setOnFailed((WorkerStateEvent event) -> {
                    // The database already exists, this is fine
                    if (((SQLException) (this.getException())).getErrorCode() == 1007) {
                        loader.incrementCompletedTasks();
                        loader.setActivity1(dbserviceLabels.get(4));
                        dbservices.get(4).reset();
                        dbservices.get(4).start();
                    } // The admin username or password is incorrect
                    else if (((SQLException) (this.getException())).getErrorCode() == 1045) {
                        try {
                            dataM.storeAdminCred(requestAdminCred(0));
                            loader.setActivity1(dbserviceLabels.get(2));
                            dbservices.get(2).reset();
                            dbservices.get(2).start();
                        } catch (IOException ex) {
                            errorAlert(1305, ex.getMessage());
                        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
                            errorAlert(1303, ex.getMessage());
                        }
                    } else {
                        errorAlert(1402, ((SQLException) (this.getException())).getMessage());
                    }
                });
            } // database table checking
            else if (stage == 3) {
                int temp = 5 + subStage;
                this.setOnSucceeded((WorkerStateEvent event) -> {
                    loader.incrementCompletedTasks();
                    loader.setActivity1(dbserviceLabels.get(temp));
                    dbservices.get(temp).reset();
                    dbservices.get(temp).start();
                });
                this.setOnFailed((WorkerStateEvent event) -> {
                    if (((SQLException) (this.getException())).getErrorCode() == 1050) {
                        loader.incrementCompletedTasks();
                        loader.setActivity1(dbserviceLabels.get(temp));
                        dbservices.get(temp).reset();
                        dbservices.get(temp).start();
                    } else {
                        errorAlert(1403, ((SQLException) (this.getException())).getMessage());
                    }
                });
            } // vaqpack user level definitions
            else if (stage == 4) {
                this.setOnSucceeded((WorkerStateEvent event) -> {
                    loader.incrementCompletedTasks();
                    loader.setActivity1(dbserviceLabels.get(24));
                    dbservices.get(24).reset();
                    dbservices.get(24).start();
                });
                this.setOnFailed((WorkerStateEvent event) -> {
                    errorAlert(1404, ((SQLException) (this.getException())).getMessage());
                });
            } // check if vaqpack admin user exists
            else if (stage == 5) {
                this.setOnSucceeded((WorkerStateEvent event) -> {
                    if (!adminExists) {
                        try {
                            adminCheck = dataM.createVPAdmin(requestVPAdmin(vpAdminType));
                        } catch (SQLException | NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                            errorAlert(1306, ex.getMessage());
                        }
                        dbservices.get(25).reset();
                        dbservices.get(25).start();
                    } else {
                        loader.incrementCompletedTasks();
                        loader.setActivity2("Database Initialization Complete");
                        dbservices.get(26).reset();
                        dbservices.get(26).start();
                    }
                });
                this.setOnFailed((WorkerStateEvent event) -> {
                    errorAlert(1307, this.getException().getMessage());
                });
            } // try to store the data
            else if (stage == 6) {
                this.setOnSucceeded((WorkerStateEvent event) -> {
                    if (!adminCheck) {
                        ((DBService) (dbservices.get(24))).setVPAdminType(1);
                        dbservices.get(24).reset();
                        dbservices.get(24).start();
                    }
                });
                this.setOnFailed((WorkerStateEvent event) -> {
                    errorAlert(1308, this.getException().getMessage());
                });
            } else if (stage == 7) {
                this.setOnSucceeded((WorkerStateEvent event) -> {
                    try {
                        Thread.sleep(360);
                    } catch (InterruptedException ex) {
                        errorAlert(1101, ex.getMessage());
                    }
                });
            }
            this.setOnScheduled((WorkerStateEvent event) -> {
                try {
                    Thread.sleep(180);
                } catch (InterruptedException ex) {
                    errorAlert(1101, ex.getMessage());
                }
            });
        }

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                public Void call() throws SQLException, InterruptedException,
                        NoSuchAlgorithmException, UnsupportedEncodingException {
                    if (stage == 1) {
                        dataM.checkDBLocation();
                    } else if (stage == 2) {
                        dataM.checkDBSchemaExists();
                    } else if (stage == 3) {
                        dataM.checkDBTable(subStage);
                    } else if (stage == 4) {
                        dataM.contructUserAccess();
                    } else if (stage == 5) {
                        adminExists = dataM.searchForVPAdmin();
                    } else if (stage == 7) {
                        Platform.runLater(() -> exposeGUI());
                    }
                    return null;
                }
            };
        }

        public void setVPAdminType(int type) {
            this.vpAdminType = type;
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass GUIService
     * - Defines the tasks that involve building the GUI.
     *------------------------------------------------------------------------*/
    private class GUIService extends Service<Void> {

        private final int stage;

        public GUIService(int stage) {
            this.stage = stage;
            this.setOnSucceeded((WorkerStateEvent event) -> {
                loader.incrementCompletedTasks();
                if (this.stage == guiservices.size() - 1) {
                    loader.setActivity2("Application Build Complete");
                } else {
                    loader.setActivity2(guiserviceLabels.get(stage + 1));
                    guiservices.get(this.stage + 1).reset();
                    guiservices.get(this.stage + 1).start();
                }
            });
            this.setOnFailed((WorkerStateEvent event) -> {
                errorAlert(1101, this.getException().getMessage());
                loader.incrementCompletedTasks();
            });
        }

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                public Void call() throws Exception {
                    Thread.sleep((int) (4000 / guiservices.size()));
                    if (stage == 0) {
                        Platform.runLater(() -> guiBuilder.buildTop());
                    } else if (stage == 1) {
                        Platform.runLater(() -> guiBuilder.buildLeft());
                    } else if (stage == 2) {
                        Platform.runLater(() -> guiBuilder.buildCenter());
                    }
                    return null;
                }
            };
        }
    }


    /*------------------------------------------------------------------------*
     * Subclass VPDialog
     * - Custom-styled JavaFX Dialog.
     *------------------------------------------------------------------------*/
    private class VPDialog extends Dialog {

        private final GridPane dialogShell;
        /*---------------------------------------------------------------------*
         * VPDialog()
         * - Constructor.
         * - Parameter title is the string title of the window.
         *---------------------------------------------------------------------*/

        public VPDialog(String title) {
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
     * Subclass TextFieldLimiter
     * - Prevents the user from adding characters into a textfield component
     *   beyond a specific character count.
     *------------------------------------------------------------------------*/
    private class TextFieldLimiter implements EventHandler<KeyEvent> {

        private final int limit;
        private final TextField thisNode;
        /*---------------------------------------------------------------------*
         * TextFieldLimiter()
         * - Constructor.
         * - Parameter thisNode is the TextField using this EventHandler
         * - Parameter limit is the allowed character limit for this TextField.
         *---------------------------------------------------------------------*/

        public TextFieldLimiter(TextField thisNode, int limit) {
            this.limit = limit;
            this.thisNode = thisNode;
        }

        @Override
        public void handle(KeyEvent event) {
            if (event.getEventType() == KeyEvent.KEY_PRESSED
                    || event.getEventType() == KeyEvent.KEY_RELEASED
                    || event.getEventType() == KeyEvent.KEY_TYPED) {
                String text = thisNode.getText();
                if (text.length() > limit) {
                    thisNode.setText(text.substring(0, limit));
                    thisNode.positionCaret(limit);
                }
            }
        }
    }

    /*------------------------------------------------------------------------*
     * Setters and Getters
     *------------------------------------------------------------------------*/
}
