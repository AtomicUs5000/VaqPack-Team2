/**
 * VP_GUIController.java - Events triggered by or altering the VaqPak GUI. FILE
 * ID 1100
 */
package vaqpack;

import vaqpack.peripherals.VP_Sounds;
import vaqpack.peripherals.VP_Loader;
import vaqpack.peripherals.VP_ErrorHandler;
import vaqpack.data.VP_DataManager;
import vaqpack.user.VP_User;
import vaqpack.components.VP_PageDivision;
import vaqpack.components.VP_PasswordField;
import vaqpack.components.VP_TreeItem;
import vaqpack.components.VP_TextField;
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
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import vaqpack.components.VP_Dialog;
import vaqpack.components.VP_PageSubdivision;
import vaqpack.user.VP_Resume;

/**
 * Events triggered by or altering the VaqPak GUI. This class links the GUI to
 * the data and to user events, and provides a centralized location where
 * components can reference each other.
 *
 * @author William Dewald (Project Manager, Team-02)
 * @author Fernando Bazan
 * @author Erik Lopez
 * @author Raul Saavedra
 * @author Nathanael Carr
 * @version 1.0
 * @since 1.0
 */
public class VP_GUIController {

    private final int USER_PASSWORD_MINIMUM = 12,
            USER_INACTIVITY_LIMIT = 300,
            SCENE_WIDTH = 1000,
            SCENE_HEIGHT = 600;
    private final String TITLE = "VaqPack";
    private final StackPane mainLayout = new StackPane();
    private final VP_Loader loader = new VP_Loader(SCENE_WIDTH, SCENE_HEIGHT);
    private final Stage primaryStage;
    private final Scene primaryScene = new Scene(mainLayout, SCENE_WIDTH, SCENE_HEIGHT);
    private final Alert warnLogOut = new Alert(AlertType.WARNING);
    private final VP_User currentUser = new VP_User();
    private final VP_DataManager dataM;
    private final VP_Header header;
    private final VP_Left leftTree;
    private final VP_Center center;
    private final VP_Footer footer;
    private ArrayList<Runnable> guiTasks,
            dbTasks;
    private ArrayList<String> guiTaskLabels,
            dbTaskLabels;
    private Timer timer;
    private int sessionSeconds;
    private boolean changes;

    /**
     * Constructor. Creates the empty scene and sets the TITLE for it. Creates a
     * VP_Loader and sets it as visible, while the empty GUI is not visible.
     *
     * @param primaryStage Sent to header object for full-screen control.
     * @since 1.0
     */
    protected VP_GUIController(Stage primaryStage) {
        //-------- Initialization Start ----------\\
        this.primaryStage = primaryStage;
        dataM = new VP_DataManager(this);
        header = new VP_Header(this);
        leftTree = new VP_Left(this);
        center = new VP_Center(this);
        footer = new VP_Footer(this);
        timer = new java.util.Timer();
        sessionSeconds = USER_INACTIVITY_LIMIT;
        ObservableList<Node> mainChildren = mainLayout.getChildren();
        //-------- Initialization End ------------\\

        mainChildren.addAll(loader, createShell());
        mainChildren.get(1).setVisible(false);
        mainChildren.get(0).setVisible(true);
        mainLayout.setAlignment(Pos.TOP_LEFT);
        primaryScene.getStylesheets().add(this.getClass()
                .getResource("/vpStyle.css").toExternalForm());
        primaryScene.setOnMouseMoved(new UpdateActivity());
        primaryScene.setOnKeyTyped(new UpdateActivity());
        primaryStage.setTitle(TITLE);
        primaryStage.setScene(primaryScene);
        primaryStage.setOnCloseRequest(new ClosingSequence());
        warnLogOut.setContentText("You will be logged out for inactivity in 30 seconds.");
        warnLogOut.getDialogPane().setOnMouseMoved(new UpdateActivity());
        warnLogOut.getDialogPane().setOnKeyTyped(new UpdateActivity());
        load();
    }

    /**
     * Loads an error into a new VP_ErrorHandler and then shows an alert with
     * the header and content determined by VP_ErrorHandler.
     *
     * @param errorCode Sent to VP_ErrorHandler to determine the message.
     * @param exceptionString The exception converted to string, if the error
     * happens to call an exception. Can be an empty string.
     * @since 1.0
     */
    public void errorAlert(int errorCode, String exceptionString) {
        //-------- Initialization Start ----------\\
        VP_ErrorHandler eh = new VP_ErrorHandler(errorCode, exceptionString);
        VPErrorAlert errorAlert = new VPErrorAlert(AlertType.ERROR);
        //-------- Initialization End ------------\\

        VP_Sounds.play(-2);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText(eh.getHeader());
        errorAlert.setContentText(eh.getContent());
        errorAlert.setResizable(true);
        errorAlert.getDialogPane().setPrefSize(SCENE_WIDTH * 0.5, SCENE_HEIGHT * 0.75);
        errorAlert.showAndWait();
        if (eh.isCritical()) {
            System.exit(-1);
        }
    }

    /**
     * Series of actions that must occur when a user logs out. Stops the timer
     * that logs users out for inactivity. Calls logout() for the user to clear
     * bound values and then calls newUserSet() which will detect that there is
     * no user and will adjust the GUI accordingly. Finally, this function calls
     * updateTree() to make the tree invisible.
     *
     * @since 1.0
     */
    protected void logoutUser() {
        timer.cancel();
        timer.purge();
        currentUser.logout();
        newUserSet();
        updateTree(0);
    }

    /**
     * Must be called whenever the user has logged in, has successfully
     * completed a major task, or has logged out. This functions determines
     * which branches of the tree are visible. Calls updateDynamicTree which
     * will either create or delete leaves of the tree depending on the addition
     * or deletion of particular entries by the user.
     *
     * @param fromPage From which page of the wizard this function has been
     * called. In the case where all tree branches should be checked, fromPage
     * should be 0.
     * @since 1.0
     */
    protected void updateTree(int fromPage) {
        ObservableList<TreeItem<String>> falseRootChildren = leftTree.getFalseRoot().getChildren();
        if (currentUser.getUserID() != -1) {
            if (falseRootChildren.size() == 0) {
                falseRootChildren.add(new VP_TreeItem("OVERVIEW", 3));
                falseRootChildren.add(new VP_TreeItem("Personal Information", 4));
            }
            if (currentUser.hasCompletedPersonalInfo()) {
                if (falseRootChildren.size() == 2) {
                    falseRootChildren.add(new VP_TreeItem("Resume", 11));
                    VP_TreeItem res = (VP_TreeItem) falseRootChildren.get(2);
                    ArrayList<VP_TreeItem> RES_Nodes = new ArrayList();
                    RES_Nodes.add(new VP_TreeItem("Heading", 12));
                    ArrayList<VP_TreeItem> RES_He_Nodes = new ArrayList();
                    RES_He_Nodes.add(new VP_TreeItem("Name", 12));
                    ArrayList<VP_TreeItem> RES_N_Nodes = new ArrayList();
                    RES_N_Nodes.add(new VP_TreeItem("First Name", 4));
                    RES_N_Nodes.add(new VP_TreeItem("Middle Name", 4));
                    RES_N_Nodes.add(new VP_TreeItem("Last Name", 4));
                    RES_He_Nodes.get(0).getChildren().addAll(RES_N_Nodes);
                    RES_He_Nodes.add(new VP_TreeItem("Address", 12));
                    ArrayList<VP_TreeItem> RES_HEA_Nodes = new ArrayList();
                    RES_HEA_Nodes.add(new VP_TreeItem("Address Line 1", 4));
                    RES_HEA_Nodes.add(new VP_TreeItem("Address Line 2", 4));
                    RES_HEA_Nodes.add(new VP_TreeItem("City", 4));
                    RES_HEA_Nodes.add(new VP_TreeItem("State", 4));
                    RES_HEA_Nodes.add(new VP_TreeItem("Zip", 4));
                    RES_He_Nodes.get(1).getChildren().addAll(RES_HEA_Nodes);
                    RES_He_Nodes.add(new VP_TreeItem("Communication", 12));
                    ArrayList<VP_TreeItem> RES_HEC_Nodes = new ArrayList();
                    RES_HEC_Nodes.add(new VP_TreeItem("Phone", 4));
                    RES_HEC_Nodes.add(new VP_TreeItem("Cell", 4));
                    RES_HEC_Nodes.add(new VP_TreeItem("Email", 4));
                    RES_He_Nodes.get(2).getChildren().addAll(RES_HEC_Nodes);
                    RES_Nodes.get(0).getChildren().addAll(RES_He_Nodes);
                    RES_Nodes.add(new VP_TreeItem("Objective", 12));
                    RES_Nodes.add(new VP_TreeItem("Education", 13));
                    RES_Nodes.add(new VP_TreeItem("Work Experience", 14));
                    RES_Nodes.add(new VP_TreeItem("Achievements", 15));
                    RES_Nodes.add(new VP_TreeItem("Community", 16));
                    RES_Nodes.add(new VP_TreeItem("Qualifications", 17));
                    RES_Nodes.add(new VP_TreeItem("Highlights", 18));
                    RES_Nodes.add(new VP_TreeItem("Languages", 19));
                    RES_Nodes.add(new VP_TreeItem("Software", 20));
                    RES_Nodes.add(new VP_TreeItem("References", 21));
                    res.getChildren().addAll(RES_Nodes);
                    falseRootChildren.add(new VP_TreeItem("Business Card", 5));
                    VP_TreeItem bc = (VP_TreeItem) falseRootChildren.get(3);
                    ArrayList<VP_TreeItem> BC_Nodes = new ArrayList();
                    BC_Nodes.add(new VP_TreeItem("Name", 5));
                    BC_Nodes.get(0).getPositionProp().bind(center.getBcNodes()
                            .get(0).layoutYProperty().subtract(20)
                            .divide(((VP_PageDivision) (((VBox) (((ScrollPane) (center.getChildren().get(5))).getContent()))
                                    .getChildren().get(0))).heightProperty()
                                    .subtract(center.heightProperty())));
                    ArrayList<VP_TreeItem> BC_N_Nodes = new ArrayList();
                    BC_N_Nodes.add(new VP_TreeItem("First Name", 4));
                    BC_N_Nodes.add(new VP_TreeItem("Middle Name", 4));
                    BC_N_Nodes.add(new VP_TreeItem("Last Name", 4));
                    BC_Nodes.get(0).getChildren().addAll(BC_N_Nodes);
                    BC_Nodes.add(new VP_TreeItem("Company", 5));
                    BC_Nodes.get(1).getPositionProp().bind(center.getBcNodes().get(1).layoutYProperty()
                            .subtract(20)
                            .divide(((VP_PageDivision) (((VBox) (((ScrollPane) (center.getChildren().get(5))).getContent()))
                                    .getChildren().get(0))).heightProperty()
                                    .subtract(center.heightProperty())));
                    ArrayList<VP_TreeItem> BC_CY_Nodes = new ArrayList();
                    BC_CY_Nodes.add(new VP_TreeItem("Profession", 5));
                    BC_CY_Nodes.get(0).getPositionProp().bind(center.getBcNodes().get(1).layoutYProperty()
                            .subtract(20)
                            .divide(((VP_PageDivision) (((VBox) (((ScrollPane) (center.getChildren().get(5))).getContent()))
                                    .getChildren().get(0))).heightProperty()
                                    .subtract(center.heightProperty())));
                    BC_CY_Nodes.add(new VP_TreeItem("Company Name", 5));
                    BC_CY_Nodes.get(1).getPositionProp().bind(center.getBcNodes().get(1).layoutYProperty()
                            .subtract(20)
                            .divide(((VP_PageDivision) (((VBox) (((ScrollPane) (center.getChildren().get(5))).getContent()))
                                    .getChildren().get(0))).heightProperty()
                                    .subtract(center.heightProperty())));
                    BC_CY_Nodes.add(new VP_TreeItem("Slogan", 5));
                    BC_CY_Nodes.get(2).getPositionProp().bind(center.getBcNodes().get(1).layoutYProperty()
                            .subtract(20)
                            .divide(((VP_PageDivision) (((VBox) (((ScrollPane) (center.getChildren().get(5))).getContent()))
                                    .getChildren().get(0))).heightProperty()
                                    .subtract(center.heightProperty())));
                    BC_Nodes.get(1).getChildren().addAll(BC_CY_Nodes);
                    BC_Nodes.add(new VP_TreeItem("Address", 5));
                    BC_Nodes.get(2).getPositionProp().bind(center.getBcNodes().get(2).layoutYProperty()
                            .subtract(20)
                            .divide(((VP_PageDivision) (((VBox) (((ScrollPane) (center.getChildren().get(5))).getContent()))
                                    .getChildren().get(0))).heightProperty()
                                    .subtract(center.heightProperty())));
                    ArrayList<VP_TreeItem> BC_A_Nodes = new ArrayList();
                    BC_A_Nodes.add(new VP_TreeItem("Address Line 1", 4));
                    BC_A_Nodes.add(new VP_TreeItem("Address Line 2", 4));
                    BC_A_Nodes.add(new VP_TreeItem("City", 4));
                    BC_A_Nodes.add(new VP_TreeItem("State", 4));
                    BC_A_Nodes.add(new VP_TreeItem("Zip", 4));
                    BC_Nodes.get(2).getChildren().addAll(BC_A_Nodes);
                    BC_Nodes.add(new VP_TreeItem("Communication", 5));
                    BC_Nodes.get(3).getPositionProp().bind(center.getBcNodes().get(3).layoutYProperty()
                            .subtract(20)
                            .divide(((VP_PageDivision) (((VBox) (((ScrollPane) (center.getChildren().get(5))).getContent()))
                                    .getChildren().get(0))).heightProperty()
                                    .subtract(center.heightProperty())));
                    ArrayList<VP_TreeItem> BC_CN_Nodes = new ArrayList();
                    BC_CN_Nodes.add(new VP_TreeItem("Phone", 4));
                    BC_CN_Nodes.add(new VP_TreeItem("Cell", 4));
                    BC_CN_Nodes.add(new VP_TreeItem("Email", 4));
                    BC_CN_Nodes.add(new VP_TreeItem("Web Page", 5));
                    BC_CN_Nodes.get(3).getPositionProp().bind(center.getBcNodes().get(3).layoutYProperty()
                            .subtract(20)
                            .divide(((VP_PageDivision) (((VBox) (((ScrollPane) (center.getChildren().get(5))).getContent()))
                                    .getChildren().get(0))).heightProperty()
                                    .subtract(center.heightProperty())));
                    BC_Nodes.get(3).getChildren().addAll(BC_CN_Nodes);
                    bc.getChildren().addAll(BC_Nodes);
                    falseRootChildren.add(new VP_TreeItem("Cover Letter", 6));
                    VP_TreeItem cl = (VP_TreeItem) falseRootChildren.get(4);
                    ArrayList<VP_TreeItem> CL_Nodes = new ArrayList();
                    CL_Nodes.add(new VP_TreeItem("Heading", 7));
                    CL_Nodes.get(0).getPositionProp().bind(center.getClNodes().get(0).layoutYProperty()
                            .subtract(20)
                            .divide(((VP_PageDivision) (((VBox) (((ScrollPane) (center.getChildren().get(7))).getContent()))
                                    .getChildren().get(0))).heightProperty()
                                    .subtract(center.heightProperty())));
                    ArrayList<VP_TreeItem> CL_H_Nodes = new ArrayList();
                    CL_H_Nodes.add(new VP_TreeItem("Name", 7));
                    CL_H_Nodes.get(0).getPositionProp().bind(center.getClNodes().get(1).layoutYProperty()
                            .subtract(20)
                            .divide(((VP_PageDivision) (((VBox) (((ScrollPane) (center.getChildren().get(7))).getContent()))
                                    .getChildren().get(0))).heightProperty()
                                    .subtract(center.heightProperty())));
                    ArrayList<VP_TreeItem> CL_HN_Nodes = new ArrayList();
                    CL_HN_Nodes.add(new VP_TreeItem("First Name", 4));
                    CL_HN_Nodes.add(new VP_TreeItem("Middle name", 4));
                    CL_HN_Nodes.add(new VP_TreeItem("Last Name", 4));
                    CL_H_Nodes.get(0).getChildren().addAll(CL_HN_Nodes);
                    CL_H_Nodes.add(new VP_TreeItem("Address", 7));
                    CL_H_Nodes.get(1).getPositionProp().bind(center.getClNodes().get(2).layoutYProperty()
                            .subtract(20)
                            .divide(((VP_PageDivision) (((VBox) (((ScrollPane) (center.getChildren().get(7))).getContent()))
                                    .getChildren().get(0))).heightProperty()
                                    .subtract(center.heightProperty())));
                    ArrayList<VP_TreeItem> CL_HA_Nodes = new ArrayList();
                    CL_HA_Nodes.add(new VP_TreeItem("Address Line 1", 4));
                    CL_HA_Nodes.add(new VP_TreeItem("Address Line 2", 4));
                    CL_HA_Nodes.add(new VP_TreeItem("City", 4));
                    CL_HA_Nodes.add(new VP_TreeItem("State", 4));
                    CL_HA_Nodes.add(new VP_TreeItem("Zip", 4));
                    CL_H_Nodes.get(1).getChildren().addAll(CL_HA_Nodes);
                    CL_H_Nodes.add(new VP_TreeItem("Communication", 7));
                    CL_H_Nodes.get(2).getPositionProp().bind(center.getClNodes().get(3).layoutYProperty()
                            .subtract(20)
                            .divide(((VP_PageDivision) (((VBox) (((ScrollPane) (center.getChildren().get(7))).getContent()))
                                    .getChildren().get(0))).heightProperty()
                                    .subtract(center.heightProperty())));
                    ArrayList<VP_TreeItem> CL_HC_Nodes = new ArrayList();
                    CL_HC_Nodes.add(new VP_TreeItem("Phone", 4));
                    CL_HC_Nodes.add(new VP_TreeItem("Cell", 4));
                    CL_HC_Nodes.add(new VP_TreeItem("Email", 4));
                    CL_H_Nodes.get(2).getChildren().addAll(CL_HC_Nodes);
                    CL_Nodes.get(0).getChildren().addAll(CL_H_Nodes);
                    CL_Nodes.add(new VP_TreeItem("Date", 7));
                    CL_Nodes.get(1).getPositionProp().bind(center.getClNodes().get(4).layoutYProperty()
                            .subtract(20)
                            .divide(((VP_PageDivision) (((VBox) (((ScrollPane) (center.getChildren().get(7))).getContent()))
                                    .getChildren().get(0))).heightProperty()
                                    .subtract(center.heightProperty())));
                    CL_Nodes.add(new VP_TreeItem("Ad Reference", 7));
                    CL_Nodes.get(2).getPositionProp().bind(center.getClNodes().get(5).layoutYProperty()
                            .subtract(20)
                            .divide(((VP_PageDivision) (((VBox) (((ScrollPane) (center.getChildren().get(7))).getContent()))
                                    .getChildren().get(0))).heightProperty()
                                    .subtract(center.heightProperty())));
                    ArrayList<VP_TreeItem> CL_A_Nodes = new ArrayList();
                    CL_A_Nodes.add(new VP_TreeItem("Source", 7));
                    CL_A_Nodes.add(new VP_TreeItem("Job Position", 7));
                    CL_A_Nodes.add(new VP_TreeItem("Reference Number", 7));
                    CL_Nodes.get(2).getChildren().addAll(CL_A_Nodes);
                    CL_Nodes.add(new VP_TreeItem("Contact Information", 7));
                    CL_Nodes.get(3).getPositionProp().bind(center.getClNodes().get(6).layoutYProperty()
                            .subtract(20)
                            .divide(((VP_PageDivision) (((VBox) (((ScrollPane) (center.getChildren().get(7))).getContent()))
                                    .getChildren().get(0))).heightProperty()
                                    .subtract(center.heightProperty())));
                    ArrayList<VP_TreeItem> CL_C_Nodes = new ArrayList();
                    CL_C_Nodes.add(new VP_TreeItem("Name", 7));
                    CL_C_Nodes.get(0).getPositionProp().bind(center.getClNodes().get(7).layoutYProperty()
                            .subtract(20)
                            .divide(((VP_PageDivision) (((VBox) (((ScrollPane) (center.getChildren().get(7))).getContent()))
                                    .getChildren().get(0))).heightProperty()
                                    .subtract(center.heightProperty())));
                    ArrayList<VP_TreeItem> CL_CN_Nodes = new ArrayList();
                    CL_CN_Nodes.add(new VP_TreeItem("First Name", 7));
                    CL_CN_Nodes.add(new VP_TreeItem("Middle name", 7));
                    CL_CN_Nodes.add(new VP_TreeItem("Last Name", 7));
                    CL_C_Nodes.get(0).getChildren().addAll(CL_CN_Nodes);
                    CL_C_Nodes.add(new VP_TreeItem("Company", 7));
                    CL_C_Nodes.get(1).getPositionProp().bind(center.getClNodes().get(8).layoutYProperty()
                            .subtract(20)
                            .divide(((VP_PageDivision) (((VBox) (((ScrollPane) (center.getChildren().get(7))).getContent()))
                                    .getChildren().get(0))).heightProperty()
                                    .subtract(center.heightProperty())));
                    ArrayList<VP_TreeItem> CL_CC_Nodes = new ArrayList();
                    CL_CC_Nodes.add(new VP_TreeItem("Contact Title", 7));
                    CL_CC_Nodes.add(new VP_TreeItem("Company Name", 7));
                    CL_C_Nodes.get(1).getChildren().addAll(CL_CC_Nodes);
                    CL_C_Nodes.add(new VP_TreeItem("Address", 7));
                    CL_C_Nodes.get(2).getPositionProp().bind(center.getClNodes().get(9).layoutYProperty()
                            .subtract(20)
                            .divide(((VP_PageDivision) (((VBox) (((ScrollPane) (center.getChildren().get(7))).getContent()))
                                    .getChildren().get(0))).heightProperty()
                                    .subtract(center.heightProperty())));
                    ArrayList<VP_TreeItem> CL_CA_Nodes = new ArrayList();
                    CL_CA_Nodes.add(new VP_TreeItem("Address Line 1", 7));
                    CL_CA_Nodes.add(new VP_TreeItem("Address Line 2", 7));
                    CL_CA_Nodes.add(new VP_TreeItem("City", 7));
                    CL_CA_Nodes.add(new VP_TreeItem("State", 7));
                    CL_CA_Nodes.add(new VP_TreeItem("Zip", 7));
                    CL_C_Nodes.get(2).getChildren().addAll(CL_CA_Nodes);
                    CL_Nodes.get(3).getChildren().addAll(CL_C_Nodes);
                    CL_Nodes.add(new VP_TreeItem("Salutation", 7));
                    CL_Nodes.get(4).getPositionProp().bind(center.getClNodes().get(10).layoutYProperty()
                            .subtract(20)
                            .divide(((VP_PageDivision) (((VBox) (((ScrollPane) (center.getChildren().get(7))).getContent()))
                                    .getChildren().get(0))).heightProperty()
                                    .subtract(center.heightProperty())));
                    CL_Nodes.add(new VP_TreeItem("Body", 7));
                    CL_Nodes.get(5).getPositionProp().bind(center.getClNodes().get(11).layoutYProperty()
                            .subtract(20)
                            .divide(((VP_PageDivision) (((VBox) (((ScrollPane) (center.getChildren().get(7))).getContent()))
                                    .getChildren().get(0))).heightProperty()
                                    .subtract(center.heightProperty())));
                    CL_Nodes.add(new VP_TreeItem("Closing", 7));
                    CL_Nodes.get(6).getPositionProp().bind(center.getClNodes().get(12).layoutYProperty()
                            .subtract(20)
                            .divide(((VP_PageDivision) (((VBox) (((ScrollPane) (center.getChildren().get(7))).getContent()))
                                    .getChildren().get(0))).heightProperty()
                                    .subtract(center.heightProperty())));
                    CL_Nodes.add(new VP_TreeItem("Signature", 7));
                    CL_Nodes.get(7).getPositionProp().bind(center.getClNodes().get(13).layoutYProperty()
                            .subtract(20)
                            .divide(((VP_PageDivision) (((VBox) (((ScrollPane) (center.getChildren().get(7))).getContent()))
                                    .getChildren().get(0))).heightProperty()
                                    .subtract(center.heightProperty())));
                    ArrayList<VP_TreeItem> CL_S_Nodes = new ArrayList();
                    CL_S_Nodes.add(new VP_TreeItem("First Name", 4));
                    CL_S_Nodes.add(new VP_TreeItem("Middle Name", 4));
                    CL_S_Nodes.add(new VP_TreeItem("Last Name", 4));
                    CL_Nodes.get(7).getChildren().addAll(CL_S_Nodes);
                    cl.getChildren().addAll(CL_Nodes);
                    updateTree(fromPage);
                } else if (falseRootChildren.size() > 2) {
                    // update the dynamically added custom nodes
                    if (fromPage == 0) {
                        for (int i = 7; i < 22; i++) {
                            updateDynamicTree(i);
                        }
                    } else {
                        updateDynamicTree(fromPage);
                    }
                }
                if (falseRootChildren.size() <= 5 && (currentUser.getBcard().hasCompletedBusinessCard()
                        || currentUser.getCovlet().hasCompletedCoverLetter()
                        || currentUser.getResume().hasCompletedResume())) {
                    falseRootChildren.add(new VP_TreeItem("Document Themes", 8));
                    falseRootChildren.add(new VP_TreeItem("Distribute Documents", 10));
                }
            }
        } else {
            falseRootChildren.clear();
        }
    }

    /**
     * Called by updateTree(), this function either adds or deletes tree
     * branches representing the dynamic fields added to or deleted from
     * document forms as desired by the user.
     *
     * @param fromPage Integer value representing the page of the wizard from
     * which a call to updateTree() originates. The purpose of fromPage is to
     * limit tree updates only to relevant branches.
     * @since 1.0
     */
    private void updateDynamicTree(int fromPage) {
        //-------- Initialization Start ----------\\
        VP_Resume resume = currentUser.getResume();
        ObservableList<TreeItem<String>> falseRootChildren = leftTree.getFalseRoot().getChildren();
        VP_PageDivision div;
        int count;
        //-------- Initialization End ------------\\

        // cover letter paragraphs
        if (fromPage == 6 || fromPage == 7) {
            div = center.getCovLetEditBox();
            VP_TreeItem clBody = (VP_TreeItem) falseRootChildren.get(4).getChildren().get(5);
            while (clBody.getChildren().size() > currentUser.getCovlet().getNumbParagraphs()) {
                ((VP_TreeItem) (clBody.getChildren().get(clBody.getChildren().size() - 1))).getPositionProp().unbind();
                clBody.getChildren().remove(clBody.getChildren().size() - 1);
            }
            while (clBody.getChildren().size() < currentUser.getCovlet().getNumbParagraphs()) {
                clBody.getChildren().add(new VP_TreeItem("Paragraph " + (clBody.getChildren().size() + 1), 7));
                ((VP_TreeItem) (clBody.getChildren().get(clBody.getChildren().size() - 1))).getPositionProp().bind(
                        ((VP_PageSubdivision) (div.getChildren().get(6))).getChildren().get(clBody.getChildren().size()).layoutYProperty()
                        .add(div.layoutYProperty().add(div.getChildren().get(6).layoutYProperty()))
                        .divide(div.heightProperty().add(div.layoutYProperty()).subtract(center.heightProperty())));
            }
        } else if (fromPage > 12 && fromPage < 22) {
            // resume
            VP_TreeItem resItem = (VP_TreeItem) falseRootChildren.get(2).getChildren().get(fromPage - 11);
            switch (fromPage) {
                case 13:
                    div = center.getResumeEducationBox();
                    count = resume.getNumbEducation();
                    break;
                case 14:
                    div = center.getResumeExperienceBox();
                    count = resume.getNumbExperience();
                    break;
                case 15:
                    div = center.getResumeAchievementsBox();
                    count = resume.getNumbAchievements();
                    break;
                case 16:
                    div = center.getResumeCommunityBox();
                    count = resume.getNumbCommunity();
                    break;
                case 17:
                    div = center.getResumeQualificationsBox();
                    count = resume.getNumbQualification();
                    break;
                case 18:
                    div = center.getResumeHighlightsBox();
                    count = resume.getNumbHighlights();
                    break;
                case 19:
                    div = center.getResumeLanguagesBox();
                    count = resume.getNumbLanguages();
                    break;
                case 20:
                    div = center.getResumeSoftwareBox();
                    count = resume.getNumbSoftware();
                    break;
                case 21:
                    div = center.getResumeReferencesBox();
                    count = resume.getNumbReferences();
                    break;
                default:
                    div = center.getResumeEducationBox();
                    count = resItem.getChildren().size();
                    break;
            }
            while (resItem.getChildren().size() > count) {
                ((VP_TreeItem) (resItem.getChildren().get(resItem.getChildren().size() - 1))).getPositionProp().unbind();
                resItem.getChildren().remove(resItem.getChildren().size() - 1);
            }
            while (resItem.getChildren().size() < count) {
                resItem.getChildren().add(new VP_TreeItem("Entry " + (resItem.getChildren().size() + 1), fromPage));
                ((VP_TreeItem) (resItem.getChildren().get(resItem.getChildren().size() - 1))).getPositionProp()
                        .bind(div.layoutYProperty().add(div.getChildren().get(resItem.getChildren().size() + 1)
                                        .layoutYProperty().subtract(8 * resItem.getChildren().size())).divide(div.heightProperty()
                                        .add(div.layoutYProperty()).subtract(center.heightProperty())));
            }
        }
    }

    /**
     * Makes changes throughout the GUI, triggered when the current user has
     * changed. If the user is logged in, the inactivity timer is set.
     *
     * @since 1.0
     */
    public void newUserSet() {
        header.getAdminMenu().setDisable(true);
        header.getAdminMenu().setText("");
        if (currentUser.getAccessLevel() == -1) {
            header.getUserLogout().setVisible(false);
            header.getChangePass().setVisible(false);
            center.showScreen(0, 0);
        } else {
            header.getUserLogout().setVisible(true);
            header.getChangePass().setVisible(true);
            center.showScreen(3, 0);
            if (currentUser.getAccessLevel() > 0) {
                header.getAdminMenu().setDisable(false);
                header.getAdminMenu().setText("Admin");
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

    /**
     * Creates an empty BorderPane and its children to be built after the stage
     * is showing and while the database is being checked.
     *
     * @return The empty GUI BorderPane.
     * @since 1.0
     */
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

    /**
     * Configures and/or checks the database and builds the GUI components as
     * background tasks/services.
     *
     * @since 1.0
     */
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
        dbTaskLabels.add("Checking Business Card PDF Table");
        dbTaskLabels.add("Checking Business Card HTML Table");
        dbTaskLabels.add("Checking Cover Letter PDF Table");
        dbTaskLabels.add("Checking Cover Letter HTML Table");
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
        loadDB = new LoadingThread(dbTasks);
        loadGUI = new LoadingThread(guiTasks);
        loadGUI.setDaemon(true);
        loadDB.start();
        loadGUI.start();
    }

    /**
     * Displays a dialog requesting the database server location. If the user
     * cancels this, the program exits.
     *
     * @param type When type is 0, this indicates the first time showing this
     * dialog. When type is 1, this indicates that the dialog is being shown
     * again.
     * @return A string array of the database server URL and port.
     * @since 1.0
     */
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
        requestLoc.getDialogShell().add(urlLabel, 0, 0);
        requestLoc.getDialogShell().add(urlField, 1, 0);
        requestLoc.getDialogShell().add(portLabel, 0, 1);
        requestLoc.getDialogShell().add(portField, 1, 1);
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

    /**
     * Displays a dialog requesting the database administrator user credentials.
     * If the user cancels this, the program exits.
     *
     * @param type When type is 0, this indicates the first time showing this
     * dialog. When type is 1, this indicates that the dialog is being shown
     * again.
     * @return A string array of the database administrator username and
     * password.
     * @since 1.0
     */
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
        requestCred.getDialogShell().add(userLabel, 0, 0);
        requestCred.getDialogShell().add(userField, 1, 0);
        requestCred.getDialogShell().add(passLabel, 0, 1);
        requestCred.getDialogShell().add(passField, 1, 1);
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

    /**
     * Displays a dialog requesting the VaqPack administrator  user credentials, 
     * as well as confirmation of database server administrator credentials.
     * If the user cancels this, the program exits.
     * 
     * @param type When type is 0, this indicates first time showing this dialog.
     * @return A string array of the database administrator username and password 
     * and the VaqPack administrator user email and password.
     * @since 1.0
     */
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
        requestCred.getDialogShell().add(userLabel, 0, 0);
        requestCred.getDialogShell().add(userField, 1, 0);
        requestCred.getDialogShell().add(passLabel, 0, 1);
        requestCred.getDialogShell().add(passField, 1, 1);
        requestCred.getDialogShell().add(emailLabel, 0, 2);
        requestCred.getDialogShell().add(emailField, 1, 2);
        requestCred.getDialogShell().add(passLabel2, 0, 3);
        requestCred.getDialogShell().add(passField2, 1, 3);
        requestCred.getDialogShell().add(passStrengthLabel, 0, 4);
        requestCred.getDialogShell().add(passStrengthLevel, 1, 4);
        requestCred.getDialogShell().add(passLabel3, 0, 5);
        requestCred.getDialogShell().add(passField3, 1, 5);
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

    /**
     * Hides the loader and makes the GUI visible
     * 
     * @since 1.0
     */
    private void exposeGUI() {
        mainLayout.getChildren().get(0).setVisible(false);
        mainLayout.getChildren().get(1).setVisible(true);
    }

    /*#########################################################################/
     * SUBCLASSES                                                     /
     *########################################################################*/
    /**
     * Prevents the window from closing before the user saves things.
     *
     * @since 1.0
     */
    protected class ClosingSequence implements EventHandler {

        /**
         * @param event A general event of no specific type.
         * @since 1.0
         */
        @Override
        public void handle(Event event) {
            //-------- Initialization Start ----------\\
            boolean saving = false;
            //-------- Initialization End ------------\\
            event.consume();
            if (hasChanges()) {
                saving = center.confirmLeavePage();
            }
            if (!saving) {
                logoutUser();
                System.exit(0);
            }
        }
    }

    /**
     * Defines the runnable wrappers for the database tasks.
     * 
     * @since 1.0
     */
    private class LoadDBTask implements Runnable {

        private final int stage;
        private boolean adminCheck;
        private CountDownLatch adminlatch, latch;

        /**
         * @param stage The current stage of database checking. Depending on the 
         * stage, the database task is run differently.
         * @since 1.0
         */
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
            if (stage <= 1) {
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
                                    Platform.runLater(() -> errorAlert(3201 + stage, ex2.getMessage()));
                                } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                                        InvalidKeyException | InvalidAlgorithmParameterException |
                                        IllegalBlockSizeException | BadPaddingException ex2) {
                                    Platform.runLater(() -> errorAlert(3203 + stage, ex2.getMessage()));
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
                            Platform.runLater(() -> errorAlert(3201 + stage, ex.getMessage()));
                        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                                InvalidKeyException | InvalidAlgorithmParameterException |
                                IllegalBlockSizeException | BadPaddingException ex) {
                            Platform.runLater(() -> errorAlert(3203 + stage, ex.getMessage()));
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
                                    Platform.runLater(() -> errorAlert(3201 + stage, ex2.getMessage()));
                                } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                                        InvalidKeyException | InvalidAlgorithmParameterException |
                                        IllegalBlockSizeException | BadPaddingException ex2) {
                                    Platform.runLater(() -> errorAlert(3203 + stage, ex2.getMessage()));
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
                            Platform.runLater(() -> errorAlert(3101 + stage, ex.getMessage()));
                        }
                    }
                }
            } else if (stage <= 18) {
                try {
                    if (stage < 18) {
                        dataM.checkDBTable(stage - 2);
                    } else if (stage == 18) {
                        dataM.contructUserAccess();
                    }
                } catch (SQLException ex) {
                    if (ex.getErrorCode() != 1050 && stage != 3) {
                        Platform.runLater(() -> errorAlert(3103, ex.getMessage()));
                    } else if (ex.getErrorCode() != 1050 && stage == 3) {
                        Platform.runLater(() -> errorAlert(3104, ex.getMessage()));
                    }
                }
            } else if (stage == 19) {
                adminExists = false;
                while (!adminExists && !adminCheck) {
                    adminCheck = false;
                    adminlatch = new CountDownLatch(1);
                    try {
                        adminExists = dataM.searchForVPAdmin();
                    } catch (SQLException ex) {
                        Platform.runLater(() -> errorAlert(3105, ex.getMessage()));
                    }
                    if (!adminExists) {
                        Platform.runLater(() -> {
                            try {
                                adminCheck = dataM.createVPAdmin(requestVPAdmin(0));
                                adminlatch.countDown();
                            } catch (SQLException ex) {
                                Platform.runLater(() -> errorAlert(3106, ex.getMessage()));
                            } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                                Platform.runLater(() -> errorAlert(3001, ex.getMessage()));
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
                                    Platform.runLater(() -> errorAlert(3206, ex.getMessage()));
                                } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                                    Platform.runLater(() -> errorAlert(3001, ex.getMessage()));
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

    /**
     * Defines the runnable wrappers for the GUI tasks.
     * 
     * @since 1.0
     */
    private class LoadGUITask implements Runnable {

        private final int stage;
        /**
         * @param stage  The current stage of GUI building. Depending on the 
         * stage, a different portion of the GUI is constructed.
         * @since 1.0
         */
        public LoadGUITask(int stage) {
            this.stage = stage;
        }
        
        /**
         * Overridden run() sets the task to be completed depending on stage number.
         */
        @Override
        public void run() {
            Platform.runLater(() -> (loader.setActivity2(guiTaskLabels.get(stage))));
            if (stage == 0) {
                Platform.runLater(() -> header.build());
            } else if (stage == 1) {
                Platform.runLater(() -> getLeftTree().build());
            } else if (stage == 2) {
                Platform.runLater(() -> getCenter().build());
            } else if (stage == 3) {
                Platform.runLater(() -> footer.build());
            }
            Platform.runLater(() -> loader.incrementCompletedTasks());
        }
    }

    /**
     * Runs the tasks during loading.
     * 
     * @since 1.0
     */
    private class LoadingThread extends Thread {

        private final ArrayList<Runnable> tasks;

        /**
         * @param tasks The list of runnable tasks to be run.
         * @since 1.0
         */
        public LoadingThread(ArrayList<Runnable> tasks) {
            this.tasks = tasks;
        }
        
        /**
         * Overridden run() calls run run() for the desired task within this thread.
         * Provides a delay in between tasks to give a chance for the loading progress
         * to update while providing loading information to the user.
         */
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

    /**
     * Custom-styled JavaFX Alert for errors.
     * @since 1.0
     */
    public class VPErrorAlert extends Alert {

        /**
         * @param alertType Typically set to error type.
         * @since 1.0
         */
        public VPErrorAlert(AlertType alertType) {
            super(alertType);
            this.getDialogPane().getStylesheets().add(this.getClass()
                    .getResource("/vpStyle.css").toExternalForm());
            this.getDialogPane().getStyleClass().add("errorAlert");
        }
    }

    /**
     * Resets the inactivity timer. If a warning is showing, it closes.
     * @since 1.0
     */
    private class UpdateActivity implements EventHandler {

        /**
         * @param event
         * @since 1.0
         */
        @Override
        public void handle(Event event) {
            sessionSeconds = USER_INACTIVITY_LIMIT;
            if (warnLogOut.isShowing()) {
                warnLogOut.close();
            }
        }
    }

    /*#########################################################################/
     * SETTERS AND GETTERS                                                     /
     *########################################################################*/
    /**
     * Accessor method.
     *
     * @return The minimum length allowed for a password systemwide.
     * @since 1.0
     */
    protected int getUSER_PASSWORD_MINIMUM() {
        return USER_PASSWORD_MINIMUM;
    }

    /**
     * Accessor method.
     *
     * @return The VP_DataManager object.
     * @since 1.0
     */
    protected VP_DataManager getDataM() {
        return dataM;
    }

    /**
     * Accessor method.
     *
     * @return The current VP_User object.
     * @since 1.0
     */
    public VP_User getCurrentUser() {
        return currentUser;
    }

    /**
     * Accessor method.
     *
     * @return The VP_Left GUI object.
     * @since 1.0
     */
    protected VP_Left getLeftTree() {
        return leftTree;
    }

    /**
     * Accessor method.
     * 
     * @return The VP_Center GUI object.
     * @since 1.0
     */
    protected VP_Center getCenter() {
        return center;
    }

    /**
     * Accessor method.
     * 
     * @return The primary stage of the JavaFX GUI.
     * @since 1.0
     */
    protected Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Accessor method.
     * 
     * @return Whether or not the user has changed some field input.
     * @since 1.0
     */
    protected boolean hasChanges() {
        return changes;
    }

    /**
     * Mutator method.
     * 
     * @param changes The boolean value representing if the user has changed some field input.
     * @since 1.0
     */
    public void setChanges(boolean changes) {
        this.changes = changes;
    }

}
