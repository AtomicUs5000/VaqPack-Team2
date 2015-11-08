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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class VP_GUIController {
    private final String            title = "VaqPack";
    private final int               sceneWidth = 1000,     // width is temporary
                                    sceneHeight = 600;    // height is temporary
    private final Scene             primaryScene;
    private final Stage             primaryStage;
    private final StackPane         mainLayout;
    private final BorderPane        guiLayout;
    private final VP_Loader         loader;
    private final VP_DataManager    dataM;
    private final VP_GUIBuilder     guiBuilder;
    private ArrayList<Service>      dbservices;
    private ArrayList<String>       dbserviceLabels;

    /*------------------------------------------------------------------------*
     * VP_GUIController
     * - Constructor. Stores primaryStage and creates the scene for it.
     * - parameter primaryStage stored for window control
     *------------------------------------------------------------------------*/
    protected VP_GUIController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(title);
        mainLayout = new StackPane();
        guiBuilder = new VP_GUIBuilder();
        loader = new VP_Loader(sceneWidth, sceneHeight);
        dataM = new VP_DataManager(this, loader);
        guiLayout = guiBuilder.createShell();
        mainLayout.getChildren().addAll(loader, guiLayout);
        mainLayout.getChildren().get(1).setVisible(false);
        mainLayout.getChildren().get(0).setVisible(true);
        primaryScene = new Scene(mainLayout, sceneWidth, sceneHeight);
        primaryStage.setScene(primaryScene);
        load();
    }
    
    /*------------------------------------------------------------------------*
     * load
     * - Configures and/or checks the database and builds the gui components
     * -- as background tasks/services. 
     * - No parameters
     * - No return
     *------------------------------------------------------------------------*/
    private void load() {
        dbserviceLabels = new ArrayList();
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
        dbservices = new ArrayList();
        dbservices.add(new DBFileService(1));
        dbservices.add(new DBService(1, 0));
        dbservices.add(new DBFileService(2));
        dbservices.add(new DBService(2, 0));
        for (int i = 0; i < 19; i++)
            dbservices.add(new DBService(3, i));
        dbservices.add(new DBService(4, 0));
        dbservices.add(new DBService(5, 0));
        dbservices.add(new DBService(6, 0));
        loader.setActivity(dbserviceLabels.get(0));
        dbservices.get(0).start();
        Task guitask = new Task<Void>() {
            @Override public Void call(){
                for (int i = 0; i < 3; i++) {
                    switch (i) {
                        case 0: guiBuilder.buildTop(); break;
                        case 1: guiBuilder.buildLeft(); break;
                        case 2: guiBuilder.buildCenter(); break;
                    }
                }
                return null;
            }
        };
        new Thread(guitask).start();
    }
    
    private String[] requestDBLocation(int type) {
        // initialize the dialog
        Dialog      requestLoc = new Dialog();
        Optional    result;
        VBox        dialogShell = new VBox();
        HBox        line1 = new HBox(),
                    line2 = new HBox();
        TextField   urlField = new TextField(),
                    portField = new TextField();
        String[]    loc = dataM.getCurrentLocation();
        Label       urlLabel = new Label("URL: "),
                    portLabel = new Label("PORT: ");
        
        requestLoc.setTitle("MySQL Database Server Location");
        if (type == 0)
            requestLoc.setHeaderText("MySQL Database Server Location");
        else if (type == 1)
            requestLoc.setHeaderText("Either the provided MySQL url or port is invalid, \n"
                    + "or the MySQL server is offline.");
        dialogShell.setSpacing(12);
        line1.setSpacing(10);
        line1.setAlignment(Pos.CENTER_LEFT);
        urlField.setText(loc[0]);
        urlField.setPrefColumnCount(50);
        urlLabel.setPrefWidth(50);
        line1.getChildren().addAll(urlLabel, urlField);
        line2.setSpacing(10);
        line2.setAlignment(Pos.CENTER_LEFT);
        portField.setText(loc[1]);
        portField.setPrefColumnCount(5);
        portField.setOnKeyTyped(new TextFieldLimiter(portField, 5));
        portField.setOnKeyPressed(new TextFieldLimiter(portField, 5));
        portField.setOnKeyReleased(new TextFieldLimiter(portField, 5));
        portLabel.setPrefWidth(50);
        line2.getChildren().addAll(portLabel, portField);
        dialogShell.getChildren().addAll(line1, line2);
        requestLoc.getDialogPane().setContent(dialogShell);
        requestLoc.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        result = requestLoc.showAndWait();
        if (result.get() == ButtonType.OK) {
            loc[0] = urlField.getText();
            loc[1] = portField.getText();
        }
        else
            System.exit(-1);
        return loc;
    }
    
    private void exposeGUI() {
        mainLayout.getChildren().get(0).setVisible(false);
        mainLayout.getChildren().get(1).setVisible(true);
    }
    
    private String[] requestAdminCred(int type) {
        // initialize the dialog
        Dialog        requestCred = new Dialog();
        Optional      result;
        VBox          dialogShell = new VBox();
        HBox          line1 = new HBox(),
                      line2 = new HBox();
        TextField     userField = new TextField();
        PasswordField passField = new PasswordField();
        String[]      cred = {"", ""};
        Label         userLabel = new Label("ADMIN USER: "),
                      passLabel = new Label("ADMIN PASSWORD: ");
        
        requestCred.setTitle("MySQL Database Admin Credentials");
        if (type == 0)
            requestCred.setHeaderText("Set up the MySQL admin account.\n"
                    + "The MySQL admin account must already be created for the \n"
                    + "MyDQL server, with privileges to create a database and tables.");
        else if (type == 1)
            requestCred.setHeaderText("Either the provided MySQL amdin username or password is invalid.\n"
                    + "Please reenter the correct admin username and password.\n"
                    + "The MySQL admin account must already be created for the \n"
                    + "MyDQL server, with privileges to create a database and tables.");
        dialogShell.setSpacing(12);
        line1.setSpacing(10);
        line1.setAlignment(Pos.CENTER_LEFT);
        userField.setPrefColumnCount(16);
        userField.setOnKeyTyped(new TextFieldLimiter(userField, 16));
        userField.setOnKeyPressed(new TextFieldLimiter(userField, 16));
        userField.setOnKeyReleased(new TextFieldLimiter(userField, 16));
        userLabel.setPrefWidth(120);
        line1.getChildren().addAll(userLabel, userField);
        line2.setSpacing(10);
        line2.setAlignment(Pos.CENTER_LEFT);
        passField.setPrefColumnCount(32);
        passField.setOnKeyTyped(new TextFieldLimiter(userField, 32));
        passField.setOnKeyPressed(new TextFieldLimiter(userField, 32));
        passField.setOnKeyReleased(new TextFieldLimiter(userField, 32));
        passLabel.setPrefWidth(120);
        line2.getChildren().addAll(passLabel, passField);
        dialogShell.getChildren().addAll(line1, line2);
        requestCred.getDialogPane().setContent(dialogShell);
        requestCred.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        result = requestCred.showAndWait();
        if (result.get() == ButtonType.OK) {
            cred[0] = userField.getText();
            cred[1] = passField.getText();
        }
        else
            System.exit(-1);
        return cred;
    }
    
    private String[] requestVPAdmin() {
        // initialize the dialog
        Dialog        requestCred = new Dialog();
        Optional      result;
        VBox          dialogShell = new VBox();
        HBox          line1 = new HBox(),
                      line2 = new HBox(),
                      line3 = new HBox(),
                      line4 = new HBox();
        TextField     userField = new TextField(),
                      emailField = new TextField();
        PasswordField passField = new PasswordField(),
                      passField2 = new PasswordField();
        String[]      cred = {"", "", "", ""};
        Label         userLabel = new Label("MySQL Admin User: "),
                      passLabel = new Label("MySQL Admin Password: "),
                      emailLabel = new Label("VaqPack Admin Email: "),
                      passLabel2 = new Label("VaqPack Admin Password: ");   
        requestCred.setTitle("Admin User Setup");
        requestCred.setHeaderText("VaqPack requires at least one admin user of the application.\n"
                + "This prompt will appear whenever an admin user does not exist upon loading.\n"
                + "The MySQL database admin must login in order to create a VaqPack admin user.\n"
                + "Canceling will force the program to close.");
        
        System.out.println("in requestVPAdmin");
        
        dialogShell.setSpacing(12);
        line1.setSpacing(10);
        line1.setAlignment(Pos.CENTER_LEFT);
        userField.setPrefColumnCount(16);
        userField.setOnKeyTyped(new TextFieldLimiter(userField, 16));
        userField.setOnKeyPressed(new TextFieldLimiter(userField, 16));
        userField.setOnKeyReleased(new TextFieldLimiter(userField, 16));
        userLabel.setPrefWidth(160);
        line1.getChildren().addAll(userLabel, userField);
        line2.setSpacing(10);
        line2.setAlignment(Pos.CENTER_LEFT);
        passField.setPrefColumnCount(32);
        passField.setOnKeyTyped(new TextFieldLimiter(userField, 32));
        passField.setOnKeyPressed(new TextFieldLimiter(userField, 32));
        passField.setOnKeyReleased(new TextFieldLimiter(userField, 32));
        passLabel.setPrefWidth(160);
        line2.getChildren().addAll(passLabel, passField);
        line3.setSpacing(10);
        line3.setAlignment(Pos.CENTER_LEFT);
        emailField.setPrefColumnCount(32);
        emailField.setOnKeyTyped(new TextFieldLimiter(userField, 254));
        emailField.setOnKeyPressed(new TextFieldLimiter(userField, 254));
        emailField.setOnKeyReleased(new TextFieldLimiter(userField, 254));
        emailLabel.setPrefWidth(160);
        line3.getChildren().addAll(emailLabel, emailField);
        line4.setSpacing(10);
        line4.setAlignment(Pos.CENTER_LEFT);
        passField2.setPrefColumnCount(32);
        passField2.setOnKeyTyped(new TextFieldLimiter(userField, 32));
        passField2.setOnKeyPressed(new TextFieldLimiter(userField, 32));
        passField2.setOnKeyReleased(new TextFieldLimiter(userField, 32));
        passLabel2.setPrefWidth(160);
        line4.getChildren().addAll(passLabel2, passField2);
        dialogShell.getChildren().addAll(line1, line2, line3, line4);
        requestCred.getDialogPane().setContent(dialogShell);
        requestCred.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        result = requestCred.showAndWait();
        if (result.get() == ButtonType.OK) {
            cred[0] = userField.getText();
            cred[1] = passField.getText();
            cred[2] = emailField.getText();
            cred[3] = passField2.getText();
        }
        else
            System.exit(-1);
        return cred;
    }

    /*------------------------------------------------------------------------*
     * errorAlert()
     * - loads an error into a new VP_ErrorHandler and then shows an alert with
     * the header and content determined by VP_ErrorHandler.
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
     * Subclasses
     *------------------------------------------------------------------------*/
    private class DBFileService extends Service<Void> {
        private final int stage;
        public DBFileService(int stage) {
            this.stage = stage;
            // checking stored connectivity properties
            if (stage == 1) {
                this.setOnSucceeded((WorkerStateEvent event) -> {
                    loader.setActivity(dbserviceLabels.get(1));
                    dbservices.get(1).reset();
                    dbservices.get(1).start();
                });
                this.setOnFailed((WorkerStateEvent event) -> {
                    if (this.exceptionProperty().getValue().toString().contains("FileNotFoundException")) {
                        try {
                            dataM.storeDBLocation(requestDBLocation(0));
                            loader.setActivity(dbserviceLabels.get(1));
                            dbservices.get(1).reset();
                            dbservices.get(1).start();
                        } catch (IOException ex) {
                            errorAlert(1302, ex.getMessage());
                        }
                    }
                    else
                        errorAlert(1301, this.getException().getMessage());
                });
            }
            // checking encrypted stored mysql admin credentials
            else if (stage == 2) {
                this.setOnSucceeded((WorkerStateEvent event) -> {
                    loader.setActivity(dbserviceLabels.get(3));
                    dbservices.get(3).reset();
                    dbservices.get(3).start();
                });
                this.setOnFailed((WorkerStateEvent event) -> {
                    if (this.exceptionProperty().getValue().toString().contains("FileNotFoundException")) {
                        try {
                            dataM.storeAdminCred(requestAdminCred(0));
                            loader.setActivity(dbserviceLabels.get(3));
                            dbservices.get(3).reset();
                            dbservices.get(3).start();
                        } catch (IOException ex) {
                            errorAlert(1305, ex.getMessage());
                        } catch (NoSuchAlgorithmException | NoSuchPaddingException | 
                                InvalidKeyException | InvalidAlgorithmParameterException | 
                                IllegalBlockSizeException | BadPaddingException ex) {
                            errorAlert(1303, ex.getMessage());
                        }
                    }
                    else {
                        errorAlert(1304, this.getException().getMessage());
                        try {
                            dataM.storeAdminCred(requestAdminCred(0));
                            loader.setActivity(dbserviceLabels.get(3));
                            dbservices.get(3).reset();
                            dbservices.get(3).start();
                        } catch (IOException ex) {
                            errorAlert(1305, ex.getMessage());
                        } catch (NoSuchAlgorithmException | NoSuchPaddingException | 
                                InvalidKeyException | InvalidAlgorithmParameterException | 
                                IllegalBlockSizeException | BadPaddingException ex) {
                            errorAlert(1303, ex.getMessage());
                        }
                    }
                });
            }
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
                    if (stage == 1)
                        dataM.retrieveDBLocation();
                    else if (stage == 2)
                        dataM.retrieveAdmin();
                    Thread.sleep(250);
                    return null;
                }
            };
        }
    }
    
    private class DBService extends Service<Void> {
        private final int stage;
        private final int subStage;
        private boolean adminCheck = false,
                adminExists = false;
        public DBService(int stage, int subStage) {
            this.stage = stage;
            this.subStage = subStage;
            // connectivity checking
            if (stage == 1) {
                this.setOnSucceeded((WorkerStateEvent event) -> {
                    loader.setActivity(dbserviceLabels.get(2));
                    dbservices.get(2).reset();
                    dbservices.get(2).start();
                });
                this.setOnFailed((WorkerStateEvent event) -> {
                    if (((SQLException)(this.getException())).getErrorCode() == 0) {
                        try {
                            dataM.storeDBLocation(requestDBLocation(1));
                            loader.setActivity(dbserviceLabels.get(0));
                            dbservices.get(0).reset();
                            dbservices.get(0).start();
                        } catch (IOException ex) {
                            errorAlert(1302, ex.getMessage());
                        }
                    }
                    else
                        errorAlert(1401, ((SQLException)(this.getException())).getMessage());
                });
            }
            // mysql admin checking and main databse schema
            else if (stage == 2) {
                this.setOnSucceeded((WorkerStateEvent event) -> {
                    loader.setActivity(dbserviceLabels.get(4));
                    dbservices.get(4).reset();
                    dbservices.get(4).start();
                });
                this.setOnFailed((WorkerStateEvent event) -> {
                    // The database already exists, this is fine
                    if (((SQLException)(this.getException())).getErrorCode() == 1007) {
                        loader.setActivity(dbserviceLabels.get(4));
                        dbservices.get(4).reset();
                        dbservices.get(4).start();
                    }
                    // The admin username or password is incorrect
                    else if (((SQLException)(this.getException())).getErrorCode() == 1045) {
                        try {
                            dataM.storeAdminCred(requestAdminCred(0));
                            loader.setActivity(dbserviceLabels.get(2));
                            dbservices.get(2).reset();
                            dbservices.get(2).start();
                        } catch (IOException ex) {
                            errorAlert(1305, ex.getMessage());
                        } catch (NoSuchAlgorithmException | NoSuchPaddingException 
                                | InvalidKeyException | InvalidAlgorithmParameterException 
                                | IllegalBlockSizeException | BadPaddingException ex) {
                            errorAlert(1303, ex.getMessage());
                        }
                    }
                    else
                        errorAlert(1402, ((SQLException)(this.getException())).getMessage());
                });
            }
            // database table checking
            else if (stage == 3) {
                int temp = 5 + subStage;
                this.setOnSucceeded((WorkerStateEvent event) -> {
                        loader.setActivity(dbserviceLabels.get(temp));
                        dbservices.get(temp).reset();
                        dbservices.get(temp).start();
                });
                this.setOnFailed((WorkerStateEvent event) -> {
                    if (((SQLException)(this.getException())).getErrorCode() == 1050) {
                        loader.setActivity(dbserviceLabels.get(temp));
                        dbservices.get(temp).reset();
                        dbservices.get(temp).start();
                    }
                    else
                        errorAlert(1403, ((SQLException)(this.getException())).getMessage());
                });
            }
            // vaqpack user level definitions
            else if (stage == 4) {
                this.setOnSucceeded((WorkerStateEvent event) -> {
                        loader.setActivity(dbserviceLabels.get(24));
                        dbservices.get(24).reset();
                        dbservices.get(24).start();
                });
                this.setOnFailed((WorkerStateEvent event) -> {
                    errorAlert(1404, ((SQLException)(this.getException())).getMessage());
                });
            }
            // check if vaqpack admin user exists
            else if (stage == 5) {
                this.setOnSucceeded((WorkerStateEvent event) -> {
                    if (!adminExists) {
                        try {
                            adminCheck = dataM.createVPAdmin(requestVPAdmin());
                        } catch (SQLException | NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                            errorAlert(1306, ex.getMessage());
                        }
                        dbservices.get(25).reset();
                        dbservices.get(25).start();
                    }
                    else  {
                        exposeGUI();
                    }
                });
                this.setOnFailed((WorkerStateEvent event) -> {
                    errorAlert(1307, this.getException().getMessage());
                });
            }
            // try to store the data
            else if (stage == 6) {
                this.setOnSucceeded((WorkerStateEvent event) -> {
                    if (!adminCheck) {
                        dbservices.get(24).reset();
                        dbservices.get(24).start();
                    }
                });
                this.setOnFailed((WorkerStateEvent event) -> {
                    errorAlert(1308, this.getException().getMessage());
                });
            }
        }
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override 
                public Void call() throws SQLException, InterruptedException, 
                        NoSuchAlgorithmException, UnsupportedEncodingException {
                    if (stage == 1)
                        dataM.checkDBLocation();
                    else if (stage == 2)
                        dataM.checkDBSchemaExists();
                    else if (stage == 3)
                        dataM.checkDBTable(subStage);
                    else if (stage == 4)
                        dataM.contructUserAccess();
                    else if (stage == 5)
                        adminExists = dataM.searchForVPAdmin();
                    Thread.sleep(250);
                    return null;
                }
            };
        }
    }
    
    private class TextFieldLimiter implements EventHandler<KeyEvent> {
        private final int limit;
        private final TextField thisNode;
        public TextFieldLimiter(TextField thisNode, int limit) {
            this.limit = limit;
            this.thisNode = thisNode;
        }
        @Override
        public void handle(KeyEvent event){
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
}
