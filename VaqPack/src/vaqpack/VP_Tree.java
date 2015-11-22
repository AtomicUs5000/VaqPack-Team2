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

        // BUSINESS CARD
        ROOT_Nodes.add(new VP_TreeItem("Business Card", 5));
        ArrayList<TreeItem> BC_Nodes = new ArrayList();
            BC_Nodes.add(new VP_TreeItem("Name", 5));
            ArrayList<TreeItem> BC_N_Nodes = new ArrayList();
                BC_N_Nodes.add(new VP_TreeItem("First Name", 4));
                BC_N_Nodes.add(new VP_TreeItem("Middle Name", 4));
                BC_N_Nodes.add(new VP_TreeItem("Last Name", 4));
            BC_Nodes.get(0).getChildren().addAll(BC_N_Nodes); 

            BC_Nodes.add(new VP_TreeItem("Company", 5));
            ArrayList<TreeItem> BC_CY_Nodes = new ArrayList();
                BC_CY_Nodes.add(new VP_TreeItem("Profession", 5));
                BC_CY_Nodes.add(new VP_TreeItem("Company Name", 5));
                BC_CY_Nodes.add(new VP_TreeItem("Slogan", 5));
            BC_Nodes.get(1).getChildren().addAll(BC_CY_Nodes);

            BC_Nodes.add(new VP_TreeItem("Address", 5));
            ArrayList<TreeItem> BC_A_Nodes = new ArrayList();
                BC_A_Nodes.add(new VP_TreeItem("Address Line 1", 4));
                BC_A_Nodes.add(new VP_TreeItem("Address Line 2", 4));
                BC_A_Nodes.add(new VP_TreeItem("City", 4));
                BC_A_Nodes.add(new VP_TreeItem("State", 4));
                BC_A_Nodes.add(new VP_TreeItem("Zip", 4));
            BC_Nodes.get(2).getChildren().addAll(BC_A_Nodes);

            BC_Nodes.add(new VP_TreeItem("Communication", 5));
            ArrayList<TreeItem> BC_CN_Nodes = new ArrayList();
                BC_CN_Nodes.add(new VP_TreeItem("Phone", 4));
                BC_CN_Nodes.add(new VP_TreeItem("Cell", 4));
                BC_CN_Nodes.add(new VP_TreeItem("Email", 4));
                BC_CN_Nodes.add(new VP_TreeItem("Web Page", 5));
            BC_Nodes.get(3).getChildren().addAll(BC_CN_Nodes);
        ROOT_Nodes.get(2).getChildren().addAll(BC_Nodes);
        
        // COVER LETTER
        ROOT_Nodes.add(new VP_TreeItem("Cover Letter", 6));
        ArrayList<TreeItem> CL_Nodes = new ArrayList();
        
            CL_Nodes.add(new VP_TreeItem("Heading", 7));
            ArrayList<TreeItem> CL_H_Nodes = new ArrayList();
                CL_H_Nodes.add(new VP_TreeItem("Name", 7));
                ArrayList<TreeItem> CL_HN_Nodes = new ArrayList();
                    CL_HN_Nodes.add(new VP_TreeItem("First Name", 4));
                    CL_HN_Nodes.add(new VP_TreeItem("Middle name", 4));
                    CL_HN_Nodes.add(new VP_TreeItem("Last Name", 4));
                CL_H_Nodes.get(0).getChildren().addAll(CL_HN_Nodes);
                
                CL_H_Nodes.add(new VP_TreeItem("Address", 7));
                ArrayList<TreeItem> CL_HA_Nodes = new ArrayList();
                    CL_HA_Nodes.add(new VP_TreeItem("Address Line 1", 4));
                    CL_HA_Nodes.add(new VP_TreeItem("Address Line 2", 4));
                    CL_HA_Nodes.add(new VP_TreeItem("City", 4));
                    CL_HA_Nodes.add(new VP_TreeItem("State", 4));
                    CL_HA_Nodes.add(new VP_TreeItem("Zip", 4));
                CL_H_Nodes.get(1).getChildren().addAll(CL_HA_Nodes);
                
                CL_H_Nodes.add(new VP_TreeItem("Communication", 7));
                ArrayList<TreeItem> CL_HC_Nodes = new ArrayList();
                    CL_HC_Nodes.add(new VP_TreeItem("Phone", 4));
                    CL_HC_Nodes.add(new VP_TreeItem("Cell", 4));
                    CL_HC_Nodes.add(new VP_TreeItem("Email", 4));
                CL_H_Nodes.get(2).getChildren().addAll(CL_HC_Nodes);
            CL_Nodes.get(0).getChildren().addAll(CL_H_Nodes);
            
            CL_Nodes.add(new VP_TreeItem("Ad Reference", 7));
            ArrayList<TreeItem> CL_A_Nodes = new ArrayList();
                CL_A_Nodes.add(new VP_TreeItem("Source", 7));
                CL_A_Nodes.add(new VP_TreeItem("Job Position", 7));
                CL_A_Nodes.add(new VP_TreeItem("Reference Number", 7));
            CL_Nodes.get(1).getChildren().addAll(CL_A_Nodes);
            
            CL_Nodes.add(new VP_TreeItem("Contact Information", 7));
            ArrayList<TreeItem> CL_C_Nodes = new ArrayList();
                CL_C_Nodes.add(new VP_TreeItem("Name", 7));
                ArrayList<TreeItem> CL_CN_Nodes = new ArrayList();
                    CL_CN_Nodes.add(new VP_TreeItem("First Name", 7));
                    CL_CN_Nodes.add(new VP_TreeItem("Middle name", 7));
                    CL_CN_Nodes.add(new VP_TreeItem("Last Name", 7));
                CL_C_Nodes.get(0).getChildren().addAll(CL_CN_Nodes);
                
                CL_C_Nodes.add(new VP_TreeItem("Company", 7));
                ArrayList<TreeItem> CL_CC_Nodes = new ArrayList();
                    CL_CC_Nodes.add(new VP_TreeItem("Contact Title", 7));
                    CL_CC_Nodes.add(new VP_TreeItem("Company Name", 7));
                CL_C_Nodes.get(1).getChildren().addAll(CL_CC_Nodes);
                
                CL_C_Nodes.add(new VP_TreeItem("Address", 7));
                ArrayList<TreeItem> CL_CA_Nodes = new ArrayList();
                    CL_CA_Nodes.add(new VP_TreeItem("Address Line 1", 7));
                    CL_CA_Nodes.add(new VP_TreeItem("Address Line 2", 7));
                    CL_CA_Nodes.add(new VP_TreeItem("City", 7));
                    CL_CA_Nodes.add(new VP_TreeItem("State", 7));
                    CL_CA_Nodes.add(new VP_TreeItem("Zip", 7));
                CL_C_Nodes.get(2).getChildren().addAll(CL_CA_Nodes);
            CL_Nodes.get(2).getChildren().addAll(CL_C_Nodes);
            
            CL_Nodes.add(new VP_TreeItem("Salutation", 7));
            
            CL_Nodes.add(new VP_TreeItem("Body", 7));
            ArrayList<TreeItem> CL_B_Nodes = new ArrayList();
                for (int i = 1; i < 4; i++) {
                    CL_B_Nodes.add(new VP_TreeItem("Paragraph " + i, 7));
                }
            CL_Nodes.get(4).getChildren().addAll(CL_B_Nodes);
            
            CL_Nodes.add(new VP_TreeItem("Closing", 7));
            
            CL_Nodes.add(new VP_TreeItem("Signature", 7));
            
        ROOT_Nodes.get(3).getChildren().addAll(CL_Nodes);

        for (int i = 0; i < ROOT_Nodes.size(); i++) {
            falseRoot.getChildren().add(ROOT_Nodes.get(i));
        }
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