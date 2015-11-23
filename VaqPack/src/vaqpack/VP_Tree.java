/*-----------------------------------------------------------------------------*
 * VP_Tree.java
 * - Everything involving the tree view (left side of GUI) of the wizard
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 1800
 *-----------------------------------------------------------------------------*/
package vaqpack;

import com.sun.javafx.scene.control.skin.LabeledText;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class VP_Tree extends VBox {
    private final VP_GUIController controller;
    private final TreeItem<String> falseRoot;
    
    /*------------------------------------------------------------------------*
     * VP_Tree()
     * - Constructor.
     *------------------------------------------------------------------------*/
    protected VP_Tree(VP_GUIController controller) {
        this.controller = controller;
        this.falseRoot = new TreeItem();
    }

    
    private class TreeClick implements EventHandler<MouseEvent> {
        private final TreeView<String> treeView;

        private TreeClick(TreeView<String> treeView) {
            this.treeView = treeView;
        }
        
        @Override
        public void handle(MouseEvent event) {
            Node node = event.getPickResult().getIntersectedNode();
            //if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
            if ((!(node instanceof StackPane)) && (node instanceof LabeledText || (node instanceof TreeCell && ((TreeCell) node).getText() != null)))
            {
                try {
                    VP_Sounds.play(0);
                    int wizardNumber = (int) ((VP_TreeItem)treeView.getSelectionModel().getSelectedItem()).getWizardNumber();
                    System.out.println("GOOD Node click: " + wizardNumber);
                    controller.quickJump(wizardNumber);
                    
                    // adds a paragrapg the the cover letter
                    /*
                    treeView.getRoot().getChildren().get(3).getChildren().get(4).getChildren().add(new VP_TreeItem(
                            "Paragraph " + 
                           (treeView.getRoot().getChildren().get(3).getChildren().get(4).getChildren().size() + 1),
                            3));
                    */
                }
                catch (Exception e) {
                    // just east it, shouldn't tak you anywhere anyway
                }
            }
        }
    }

    
    /*------------------------------------------------------------------------*
     * build()
     * - Builds the tree view. Called in a task, to build in the background
     * - No Paramters
     * - No Return
     *------------------------------------------------------------------------*/
    protected void build() {

        this.setId("treeContainer");
        VBox.setVgrow(this, Priority.ALWAYS);
        setPrefWidth(200);
        falseRoot.setExpanded(true);
        
        /*
        
        // OVERVIEW
        VP_TreeItem overviewTI = new VP_TreeItem("Overview", 3);
        ROOT_Nodes.add(overviewTI);
        
        // RESUME
        ROOT_Nodes.add(new VP_TreeItem("Resume", 11));
        ArrayList<TreeItem> RES_Nodes = new ArrayList();
        RES_Nodes.add(new TreeItem("Heading"));
        ArrayList<TreeItem> RES_He_Nodes = new ArrayList();
        RES_He_Nodes.add(new TreeItem("Name"));
        RES_He_Nodes.add(new TreeItem("Street Address"));
        RES_He_Nodes.add(new TreeItem("City-State-Zip"));
        RES_He_Nodes.add(new TreeItem("Phone-Email"));
        RES_Nodes.get(0).getChildren().addAll(RES_He_Nodes);
        RES_Nodes.add(new TreeItem("Objective"));
        ArrayList<TreeItem> RES_O_Nodes = new ArrayList();
        RES_O_Nodes.add(new TreeItem("Paragraph"));
        RES_Nodes.get(1).getChildren().addAll(RES_O_Nodes);
        RES_Nodes.add(new TreeItem("Education"));
        ArrayList<TreeItem> RES_E_Nodes = new ArrayList();
        for (int i = 0; i < 3; i++) {
            RES_E_Nodes.add(new TreeItem("Institution"));
            ArrayList<TreeItem> RES_E_I_Nodes = new ArrayList();
            RES_E_I_Nodes.add(new TreeItem("Name"));
            RES_E_I_Nodes.add(new TreeItem("Degree"));
            RES_E_I_Nodes.add(new TreeItem("GPA"));
            RES_E_I_Nodes.add(new TreeItem("Location"));
            RES_E_I_Nodes.add(new TreeItem("Duration"));
            ArrayList<TreeItem> RES_EID_Nodes = new ArrayList();
            RES_EID_Nodes.add(new TreeItem("Start Date"));
            RES_EID_Nodes.add(new TreeItem("End Date"));
            RES_E_I_Nodes.get(4).getChildren().addAll(RES_EID_Nodes);
            RES_E_Nodes.get(i).getChildren().addAll(RES_E_I_Nodes);
        }
        RES_Nodes.get(2).getChildren().addAll(RES_E_Nodes);
        RES_Nodes.add(new TreeItem("Work Experience"));
        ArrayList<TreeItem> RES_W_Nodes = new ArrayList();
        for (int i = 0; i < 3; i++) {
            RES_W_Nodes.add(new TreeItem("Employer"));
            ArrayList<TreeItem> RES_W_E_Nodes = new ArrayList();
            RES_W_E_Nodes.add(new TreeItem("Name"));
            RES_W_E_Nodes.add(new TreeItem("Location"));
            for (int ii = 2; ii < 4; ii++) {
                RES_W_E_Nodes.add(new TreeItem("Position"));
                ArrayList<TreeItem> RES_WEP_Nodes = new ArrayList();
                RES_WEP_Nodes.add(new TreeItem("Title"));
                RES_WEP_Nodes.add(new TreeItem("Duration"));
                ArrayList<TreeItem> RES_WEPD_Nodes = new ArrayList();
                RES_WEPD_Nodes.add(new TreeItem("Start Date"));
                RES_WEPD_Nodes.add(new TreeItem("End Date"));
                RES_WEP_Nodes.get(1).getChildren().addAll(RES_WEPD_Nodes);
                RES_W_E_Nodes.get(ii).getChildren().addAll(RES_WEP_Nodes);
            }
            RES_W_Nodes.get(i).getChildren().addAll(RES_W_E_Nodes);
        }
        RES_Nodes.get(3).getChildren().addAll(RES_W_Nodes);
        
        RES_Nodes.add(new TreeItem("Achievements"));
        ArrayList<TreeItem> RES_A_Nodes = new ArrayList();
        for (int i = 0; i < 3; i++) {
            RES_A_Nodes.add(new TreeItem("Achievement"));
            ArrayList<TreeItem> RES_AA_Nodes = new ArrayList();
            RES_AA_Nodes.add(new TreeItem("Title"));
            RES_AA_Nodes.add(new TreeItem("Date"));
            RES_A_Nodes.get(i).getChildren().addAll(RES_AA_Nodes);
        }
        RES_Nodes.get(4).getChildren().addAll(RES_A_Nodes); 
        RES_Nodes.add(new TreeItem("Community"));
        ArrayList<TreeItem> RES_C_Nodes = new ArrayList();
        for (int i = 0; i < 3; i++) {
            RES_C_Nodes.add(new TreeItem("Event"));
            ArrayList<TreeItem> RES_CN_Nodes = new ArrayList();
            RES_CN_Nodes.add(new TreeItem("Name"));
            RES_CN_Nodes.add(new TreeItem("Date"));
            RES_C_Nodes.get(i).getChildren().addAll(RES_CN_Nodes);
        }
        RES_Nodes.get(5).getChildren().addAll(RES_C_Nodes);
        RES_Nodes.add(new TreeItem("Qualifications"));
        ArrayList<TreeItem> RES_Q_Nodes = new ArrayList();
        for (int i = 0; i < 3; i++) {
            RES_Q_Nodes.add(new TreeItem("Skill"));
        }
        RES_Nodes.get(6).getChildren().addAll(RES_Q_Nodes);
        RES_Nodes.add(new TreeItem("Highlights"));
        ArrayList<TreeItem> RES_Hi_Nodes = new ArrayList();
        for (int i = 0; i < 3; i++) {
            RES_Hi_Nodes.add(new TreeItem("Quality"));
        }
        RES_Nodes.get(7).getChildren().addAll(RES_Hi_Nodes);
        RES_Nodes.add(new TreeItem("Languages"));
        ArrayList<TreeItem> RES_L_Nodes = new ArrayList();
        for (int i = 0; i < 3; i++) {
            RES_L_Nodes.add(new TreeItem("Language"));
        }
        RES_Nodes.get(8).getChildren().addAll(RES_L_Nodes);
        RES_Nodes.add(new TreeItem("Software"));
        ArrayList<TreeItem> RES_S_Nodes = new ArrayList();
        for (int i = 0; i < 3; i++) {
            RES_S_Nodes.add(new TreeItem("Product"));
        }
        RES_Nodes.get(9).getChildren().addAll(RES_S_Nodes);
        RES_Nodes.add(new TreeItem("References"));
        ArrayList<TreeItem> RES_R_Nodes = new ArrayList();
        for (int i = 0; i < 3; i++) {
            RES_R_Nodes.add(new TreeItem("Reference"));
            ArrayList<TreeItem> RES_RR_Nodes = new ArrayList();
            RES_RR_Nodes.add(new TreeItem("Name"));
            RES_RR_Nodes.add(new TreeItem("Company"));
            RES_RR_Nodes.add(new TreeItem("Phone"));
            RES_RR_Nodes.add(new TreeItem("Email"));
            RES_R_Nodes.get(i).getChildren().addAll(RES_RR_Nodes);
        }
        RES_Nodes.get(10).getChildren().addAll(RES_R_Nodes);
        ROOT_Nodes.get(1).getChildren().addAll(RES_Nodes);

        */
        
        TreeView<String> tree = new TreeView(falseRoot);
        tree.setOnMouseClicked(new TreeClick(tree));
        tree.setShowRoot(false);
        tree.prefWidthProperty().bind(widthProperty());
        tree.prefHeightProperty().bind(heightProperty());
        tree.getStyleClass().add("treeStyle");
        setPadding(new Insets(1, 1, 1, 1));
        getChildren().addAll(tree);
    }
    
    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/

    protected TreeItem<String> getFalseRoot() {
        return falseRoot;
    }
}