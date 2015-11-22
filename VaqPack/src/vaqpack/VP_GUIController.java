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
import java.util.Timer;
import java.util.TimerTask;
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

    private final Scene primaryScene;
    private final String title;
    private final StackPane mainLayout;
    private final VP_Loader loader;
    private final VP_DataManager dataM;
    private final VP_Header header;
    private final VP_Tree leftTree;
    private final VP_Center center;
    private final VP_Footer footer;
    private ArrayList<Runnable> guiTasks,
            dbTasks;
    private ArrayList<String> guiTaskLabels,
            dbTaskLabels;
    private final int USER_PASSWORD_MINIMUM,
            USER_INACTIVITY_LIMIT,
            sceneWidth,
            sceneHeight;
    private final VP_User currentUser;
    private final Alert warnLogOut;
    private Timer timer;
    private int sessionSeconds;

    /*------------------------------------------------------------------------*
     * VP_GUIController()
     * - Constructor. Creates the empty scene and sets the title for it. 
     *   Creates a VP_Loader and sets it as visible, while the
     *   empty GUI is not visible. Creates the Data Manager.
     * - Parameter primaryStage sent to header object for fullscreen control.
     * - Calls load().
     *------------------------------------------------------------------------*/
    protected VP_GUIController(Stage primaryStage) {
        //-------- Initialization Start ----------\\
        USER_PASSWORD_MINIMUM = 12;
        USER_INACTIVITY_LIMIT = 300;
        sceneWidth = 1000;
        sceneHeight = 600;
        mainLayout = new StackPane();
        loader = new VP_Loader(sceneWidth, sceneHeight);
        dataM = new VP_DataManager(this);
        header = new VP_Header(this, primaryStage);
        leftTree = new VP_Tree();
        center = new VP_Center(this);
        footer = new VP_Footer(this);
        primaryScene = new Scene(mainLayout, sceneWidth, sceneHeight);
        sessionSeconds = USER_INACTIVITY_LIMIT;
        timer = new java.util.Timer();
        warnLogOut = new Alert(AlertType.WARNING);
        currentUser = new VP_User();
        title = "VaqPack";
        //-------- Initialization End ------------\\

        mainLayout.getChildren().addAll(loader, createShell());
        mainLayout.getChildren().get(1).setVisible(false);
        mainLayout.getChildren().get(0).setVisible(true);
        mainLayout.setAlignment(Pos.TOP_LEFT);
        primaryScene.getStylesheets().add(this.getClass().getResource("/vpStyle.css").toExternalForm());
        primaryScene.setOnMouseMoved(new UpdateActivity());
        primaryScene.setOnKeyTyped(new UpdateActivity());
        primaryStage.setTitle(title);
        primaryStage.setScene(primaryScene);
        primaryStage.setOnCloseRequest(new ClosingSequence());
        warnLogOut.setContentText("You will be logged out for inactivity in 30 seconds.");
        warnLogOut.getDialogPane().setOnMouseMoved(new UpdateActivity());
        warnLogOut.getDialogPane().setOnKeyTyped(new UpdateActivity());
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
        //-------- Initialization Start ----------\\
        VP_ErrorHandler eh = new VP_ErrorHandler(errorCode, exceptionString);
        VPErrorAlert errorAlert = new VPErrorAlert(AlertType.ERROR);
        //-------- Initialization End ------------\\
        VP_Sounds.play(-2);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText(eh.getHeader());
        errorAlert.setContentText(eh.getContent());
        errorAlert.setResizable(true);
        errorAlert.getDialogPane().setPrefSize(sceneWidth * 0.5, sceneHeight * 0.75);
        errorAlert.showAndWait();
        if (eh.isCritical()) {
            System.exit(-1);
        }
    }
    
    /*------------------------------------------------------------------------*
     * logoutUser()
     * - Calls logout() for the user to clear bound values and then calls
     *   newUserSet() which will detect that there is no user and will adjust
     *   the GUI accordingly.
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void logoutUser() {
        timer.cancel();
        timer.purge();
        currentUser.logout();
        newUserSet();
    }
    
    /*------------------------------------------------------------------------*
     * newUserSet()
     * - Makes changes throughout the GUI, triggered when the current user
     *   has changed. If the user is logged in, the inactivity timer is set.
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void newUserSet() {
        if (header.getMenuBar().getMenus().size() > 3) {
            header.getMenuBar().getMenus().remove(3);
        }
        if (currentUser.getAccessLevel() == -1) {
            header.getAdminMenu().setVisible(false);
            header.getUserLogout().setVisible(false);
            center.showScreen(0);
        } else {
            header.getUserLogout().setVisible(true);
            center.showScreen(3);
            if (currentUser.getAccessLevel() > 0) {
                header.getMenuBar().getMenus().add(header.getAdminMenu());
            }
            sessionSeconds = USER_INACTIVITY_LIMIT;
            timer = new java.util.Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        sessionSeconds -= 1;
                        if (sessionSeconds == 30) {
                            warnLogOut.show();
                        }
                        if (sessionSeconds < 0) {
                            sessionSeconds = 0;
                            if (currentUser.getAccessLevel() != -1) {
                                logoutUser();
                                if (warnLogOut.isShowing()) {
                                    warnLogOut.close();
                                }
                                this.cancel();
                            }
                        }
                    });
                }
            }, 0, 1000);
        }
    }

    /*------------------------------------------------------------------------*
     * createShell()
     * - Creates an empty BorderPane and its children to be built after the
     *   stage is showing and while the dtatabase is being checked.
     * - No paramters.
     * - Returns the empty GUI BorderPane.
     *------------------------------------------------------------------------*/
    private BorderPane createShell() {
        //-------- Initialization Start ----------\\
        BorderPane guiShell = new BorderPane();
        //-------- Initialization End ------------\\

        guiShell.setTop(header);
        guiShell.setLeft(leftTree);
        guiShell.setCenter(center);
        guiShell.setBottom(footer);
        return guiShell;
    }

    /*------------------------------------------------------------------------*
     * load()
     * - Configures and/or checks the database and builds the gui components
     *   as background tasks/services. 
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    private void load() {
        //-------- Initialization Start ----------\\
        LoadingThread loadDB;
        LoadingThread loadGUI;
        dbTasks = new ArrayList();
        guiTasks = new ArrayList();
        dbTaskLabels = new ArrayList();
        guiTaskLabels = new ArrayList();
        //-------- Initialization End ------------\\

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
            guiTasks.add(new LoadGUITask(i, this));
        }
        for (int i = 0; i < dbTaskLabels.size(); i++) {
            dbTasks.add(new LoadDBTask(i));
        }
        loader.setTotalTasks(dbTaskLabels.size() + guiTaskLabels.size());
        loadDB = new LoadingThread(dbTasks);
        loadGUI = new LoadingThread(guiTasks);
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
        //-------- Initialization Start ----------\\
        Optional result;
        VP_Dialog requestLoc = new VP_Dialog("MySQL Database Server Location");
        VP_TextField urlField = new VP_TextField(50, 0),
                portField = new VP_TextField(5, 5);
        String[] loc = dataM.getCurrentLocation();
        Label urlLabel = new Label("URL: "),
                portLabel = new Label("PORT: ");
        //-------- Initialization End ------------\\

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
        //-------- Initialization Start ----------\\
        Optional result;
        VP_Dialog requestCred = new VP_Dialog("MySQL Database Admin Credentials");
        VP_TextField userField = new VP_TextField(16, 16);
        VP_PasswordField passField = new VP_PasswordField(32, 32, 0, null);
        String[] cred = {"", ""};
        Label userLabel = new Label("ADMIN USER: "),
                passLabel = new Label("ADMIN PASSWORD: ");
        String headerString = "The MySQL admin account must already be created "
                + "for the MyDQL server, with privileges to create a database and tables.";
        //-------- Initialization End ------------\\

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
        //-------- Initialization Start ----------\\
        Optional result;
        boolean passwordsOK = false,
                emailOK = false,
                lengthOK = false;
        VP_Dialog requestCred = new VP_Dialog("VaqPack Admin User Setup");
        VP_TextField userField = new VP_TextField(16, USER_PASSWORD_MINIMUM),
                emailField = new VP_TextField(32, 254);
        String[] cred = {"", "", "", ""};
        Label userLabel = new Label("MySQL Admin User: "),
                passLabel = new Label("MySQL Admin Password: "),
                emailLabel = new Label("VaqPack Admin Email: "),
                passLabel2 = new Label("VaqPack Admin Password: "),
                passLabel3 = new Label("Reenter VP Admin Password:"),
                passStrengthLabel = new Label("(Password must be at least "
                        + USER_PASSWORD_MINIMUM + " characters in length)"),
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
        //-------- Initialization End ------------\\

        if (type == 0) {
            requestCred.setHeaderText(headerString);
        } else {
            requestCred.setHeaderText(headerString + "\n\nThe provided database "
                    + "admin credentials were incorrect.\nPlease try again.");
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
                    requestCred.setHeaderText(headerString + "\n\nThe entered "
                            + "VaqPack admin email address is not in valid email"
                            + " form.\nPlease try again.");
                    emailField.showInvalid();
                } else if (cred[3].length() < USER_PASSWORD_MINIMUM) {
                    lengthOK = false;
                    requestCred.setHeaderText(headerString + "\n\nThe VaqPack "
                            + "admin user password is not long enough.\nPlease try again.");
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

    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*------------------------------------------------------------------------*
     * Subclass ClosingSequence
     * - Prevents the window from closing before the user saves things.
     *------------------------------------------------------------------------*/
    protected class ClosingSequence implements EventHandler {

        @Override
        public void handle(Event event) {
            //-------- Initialization Start ----------\\
            boolean exitCancelled = false;
            //-------- Initialization End ------------\\

            event.consume();
            // insert code alert user to save things. if necessary
            // let the alert return a value
            // exitCancelled = blahblahblah
            if (!exitCancelled) {
                logoutUser();
                System.exit(0);
            }
        }
    }

    /*------------------------------------------------------------------------*
     * Subclass LoadDBTasks
     * - Defines the runnable wrappers for the datatbase tasks.
     *------------------------------------------------------------------------*/
    private class LoadDBTask implements Runnable {

        private final int stage;
        private boolean adminCheck;
        private CountDownLatch adminlatch,
                latch;

        public LoadDBTask(int stage) {
            this.stage = stage;
        }

        @Override
        public void run() {
            //-------- Initialization Start ----------\\
            boolean complete = false,
                    retrieveComplete = false,
                    adminExists = false;
            //-------- Initialization End ------------\\

            Platform.runLater(() -> (loader.setActivity1(dbTaskLabels.get(stage))));
            if (stage == 0 || stage == 1) {
                while (!complete) {
                    retrieveComplete = false;
                    while (!retrieveComplete) {
                        try {
                            if (stage == 0) {
                                dataM.retrieveDBLocation();
                            } else if (stage == 1) {
                                dataM.retrieveAdmin();
                            }
                            retrieveComplete = true;
                        } catch (FileNotFoundException ex) {
                            latch = new CountDownLatch(1);
                            Platform.runLater(() -> {
                                try {
                                    if (stage == 0) {
                                        dataM.storeDBLocation(requestDBLocation(0));
                                        latch.countDown();
                                    } else if (stage == 1) {
                                        dataM.storeAdminCred(requestAdminCred(0));
                                        latch.countDown();
                                    }
                                } catch (IOException ex2) {
                                    Platform.runLater(() -> errorAlert(1301 + stage, ex2.getMessage()));
                                } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                                        InvalidKeyException | InvalidAlgorithmParameterException |
                                        IllegalBlockSizeException | BadPaddingException ex2) {
                                    Platform.runLater(() -> errorAlert(1303 + stage, ex2.getMessage()));
                                }
                            });
                            try {
                                latch.await();
                            } catch (InterruptedException ex1) {
                                Platform.runLater(() -> errorAlert(1101 + stage, ex1.getMessage()));
                            } finally {
                                latch.countDown();
                            }
                        } catch (IOException ex) {
                            Platform.runLater(() -> errorAlert(1301 + stage, ex.getMessage()));
                        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                                InvalidKeyException | InvalidAlgorithmParameterException |
                                IllegalBlockSizeException | BadPaddingException ex) {
                            Platform.runLater(() -> errorAlert(1303 + stage, ex.getMessage()));
                        }
                    }
                    try {
                        if (stage == 0) {
                            dataM.checkDBLocation();
                            complete = true;
                        } else if (stage == 1) {
                            dataM.checkDBSchemaExists();
                        }

                    } catch (SQLException ex) {
                        if ((ex.getErrorCode() == 0 && stage == 0) || (ex.getErrorCode() == 1045 && stage == 1)) {
                            latch = new CountDownLatch(1);
                            Platform.runLater(() -> {
                                try {
                                    if (stage == 0) {
                                        dataM.storeDBLocation(requestDBLocation(1));
                                        latch.countDown();
                                    } else if (stage == 1) {
                                        dataM.storeAdminCred(requestAdminCred(1));
                                        latch.countDown();
                                    }
                                } catch (IOException ex2) {
                                    Platform.runLater(() -> errorAlert(1301 + stage, ex2.getMessage()));
                                } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                                        InvalidKeyException | InvalidAlgorithmParameterException |
                                        IllegalBlockSizeException | BadPaddingException ex2) {
                                    Platform.runLater(() -> errorAlert(1303 + stage, ex2.getMessage()));
                                } finally {
                                    latch.countDown();
                                }
                            });
                            try {
                                latch.await();
                            } catch (InterruptedException ex1) {
                                errorAlert(1103 + stage, ex.getMessage());
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
                        dataM.checkDBTable(stage - 2);
                    } else if (stage == 23) {
                        dataM.contructUserAccess();
                    }
                } catch (SQLException ex) {
                    if (ex.getErrorCode() != 1050 && stage != 3) {
                        Platform.runLater(() -> errorAlert(1403, ex.getMessage()));
                    } else if (ex.getErrorCode() != 1050 && stage == 3) {
                        Platform.runLater(() -> errorAlert(1404, ex.getMessage()));
                    }
                }
            } else if (stage == 24) {
                adminExists = false;
                while (!adminExists && !adminCheck) {
                    adminCheck = false;
                    adminlatch = new CountDownLatch(1);
                    try {
                        adminExists = dataM.searchForVPAdmin();
                    } catch (SQLException ex) {
                        Platform.runLater(() -> errorAlert(1405, ex.getMessage()));
                    }
                    if (!adminExists) {
                        Platform.runLater(() -> {
                            try {
                                adminCheck = dataM.createVPAdmin(requestVPAdmin(0));
                                adminlatch.countDown();
                            } catch (SQLException ex) {
                                Platform.runLater(() -> errorAlert(1406, ex.getMessage()));
                            } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                                Platform.runLater(() -> errorAlert(1201, ex.getMessage()));
                            }
                        });
                    } else {
                        adminlatch.countDown();
                    }
                    try {
                        adminlatch.await();
                    } catch (InterruptedException ex) {
                        errorAlert(1105, ex.getMessage());
                    }
                    if (!adminExists && !adminCheck) {
                        while (!adminCheck) {
                            adminlatch = new CountDownLatch(1);
                            Platform.runLater(() -> {
                                try {
                                    adminCheck = dataM.createVPAdmin(requestVPAdmin(1));
                                    adminlatch.countDown();
                                } catch (SQLException ex) {
                                    Platform.runLater(() -> errorAlert(1306, ex.getMessage()));
                                } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                                    Platform.runLater(() -> errorAlert(1201, ex.getMessage()));
                                }
                            });
                            try {
                                adminlatch.await();
                            } catch (InterruptedException ex) {
                                Platform.runLater(() -> errorAlert(1106, ex.getMessage()));
                            } finally {
                                adminlatch.countDown();
                            }
                        }
                    }
                }
            } else {
                latch = new CountDownLatch(1);
                Platform.runLater(() -> {
                    loader.incrementCompletedTasks();
                    latch.countDown();
                });
                try {
                    latch.await();
                    Thread.sleep(500);
                    exposeGUI();
                } catch (InterruptedException ex) {
                    Platform.runLater(() -> errorAlert(1107, ex.getMessage()));
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

        private final VP_GUIController controller;
        private final int stage;

        public LoadGUITask(int stage, VP_GUIController controller) {
            this.stage = stage;
            this.controller = controller;
        }

        @Override
        public void run() {
            Platform.runLater(() -> (loader.setActivity2(guiTaskLabels.get(stage))));
            if (stage == 0) {
                Platform.runLater(() -> header.build());
            } else if (stage == 1) {
                Platform.runLater(() -> leftTree.build());
            } else if (stage == 2) {
                Platform.runLater(() -> center.build());
            } else if (stage == 3) {
                Platform.runLater(() -> footer.build());
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
                    LoadingThread.sleep(3000 / tasks.size());
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
            //-------- Initialization Start ----------\\
            VBox shellPad = new VBox();
            //-------- Initialization End ------------\\

            this.getDialogPane().getStylesheets().add(this.getClass().getResource("/vpStyle.css").toExternalForm());
            this.setTitle(title);
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
     * Subclass UpdateActivity
     * - Resets the inactivity timer. If a warning is showing, it closes.
     *------------------------------------------------------------------------*/
    private class UpdateActivity implements EventHandler {

        @Override
        public void handle(Event event) {
            sessionSeconds = USER_INACTIVITY_LIMIT;
            if (warnLogOut.isShowing()) {
                warnLogOut.close();
            }
        }
    }

    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/
    protected int getUSER_PASSWORD_MINIMUM() {
        return USER_PASSWORD_MINIMUM;
    }

    protected VP_DataManager getDataM() {
        return dataM;
    }

    protected VP_User getCurrentUser() {
        return currentUser;
    }
}
