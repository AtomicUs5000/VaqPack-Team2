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

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class VP_Tree extends VBox {
    
    /*------------------------------------------------------------------------*
     * VP_Tree()
     * - Constructor.
     *------------------------------------------------------------------------*/
    protected VP_Tree() {
        this.setId("treeContainer");
    }

    /*------------------------------------------------------------------------*
     * build()
     * - Builds the tree view. Called in a task, to build in the background
     * - No Paramters
     * - No Return
     *------------------------------------------------------------------------*/
    protected void build() {
        VBox.setVgrow(this, Priority.ALWAYS);
        setPrefWidth(200);
        TreeItem<String> falseRoot = new TreeItem();
        falseRoot.setExpanded(true);
        ArrayList<TreeItem> ROOT_Nodes = new ArrayList();

        // COVER LETTER
        ROOT_Nodes.add(new TreeItem("Cover Letter"));
        ArrayList<TreeItem> CL_Nodes = new ArrayList();
        CL_Nodes.add(new TreeItem("Heading"));
        ArrayList<TreeItem> CL_H_Nodes = new ArrayList();
        CL_H_Nodes.add(new TreeItem("Name"));
        CL_H_Nodes.add(new TreeItem("Street Address"));
        CL_H_Nodes.add(new TreeItem("City-State-Zip"));
        CL_H_Nodes.add(new TreeItem("Phone-Email"));
        CL_Nodes.get(0).getChildren().addAll(CL_H_Nodes);
        CL_Nodes.add(new TreeItem("Inside-Address"));
        ArrayList<TreeItem> CL_I_Nodes = new ArrayList();
        CL_I_Nodes.add(new TreeItem("Contact Name"));
        CL_I_Nodes.add(new TreeItem("Company Name"));
        CL_I_Nodes.add(new TreeItem("Street Address"));
        CL_I_Nodes.add(new TreeItem("City-State-Zip"));
        CL_Nodes.get(1).getChildren().addAll(CL_I_Nodes);
        CL_Nodes.add(new TreeItem("Date"));
        CL_Nodes.add(new TreeItem("Salutation"));
        CL_Nodes.add(new TreeItem("Body"));
        ArrayList<TreeItem> CL_B_Nodes = new ArrayList();
        for (int i = 0; i < 3; i++) {
            CL_B_Nodes.add(new TreeItem("Paragraph"));
        }
        CL_Nodes.get(4).getChildren().addAll(CL_B_Nodes);
        CL_Nodes.add(new TreeItem("Closing"));
        CL_Nodes.add(new TreeItem("Signature"));
        ROOT_Nodes.get(0).getChildren().addAll(CL_Nodes);

        // RESUME
        ROOT_Nodes.add(new TreeItem("Resume"));
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
        RES_Nodes.add(new TreeItem("Qualifications"));
        ArrayList<TreeItem> RES_Q_Nodes = new ArrayList();
        for (int i = 0; i < 3; i++) {
            RES_Q_Nodes.add(new TreeItem("Skill"));
        }
        RES_Nodes.get(5).getChildren().addAll(RES_Q_Nodes);
        RES_Nodes.add(new TreeItem("Highlights"));
        ArrayList<TreeItem> RES_Hi_Nodes = new ArrayList();
        for (int i = 0; i < 3; i++) {
            RES_Hi_Nodes.add(new TreeItem("Quality"));
        }
        RES_Nodes.get(6).getChildren().addAll(RES_Hi_Nodes);
        RES_Nodes.add(new TreeItem("Languages"));
        ArrayList<TreeItem> RES_L_Nodes = new ArrayList();
        for (int i = 0; i < 3; i++) {
            RES_L_Nodes.add(new TreeItem("Language"));
        }
        RES_Nodes.get(7).getChildren().addAll(RES_L_Nodes);
        RES_Nodes.add(new TreeItem("Software"));
        ArrayList<TreeItem> RES_S_Nodes = new ArrayList();
        for (int i = 0; i < 3; i++) {
            RES_S_Nodes.add(new TreeItem("Product"));
        }
        RES_Nodes.get(8).getChildren().addAll(RES_S_Nodes);
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
        RES_Nodes.get(9).getChildren().addAll(RES_R_Nodes);
        ROOT_Nodes.get(1).getChildren().addAll(RES_Nodes);

        // BUSINESS CARD
        ROOT_Nodes.add(new TreeItem("Business Card"));
        ArrayList<TreeItem> BC_Nodes = new ArrayList();
        BC_Nodes.add(new TreeItem("Name"));
        BC_Nodes.add(new TreeItem("Profession"));
        BC_Nodes.add(new TreeItem("Company"));
        ArrayList<TreeItem> BC_C_Nodes = new ArrayList();
        BC_C_Nodes.add(new TreeItem("Company Name"));
        BC_C_Nodes.add(new TreeItem("Slogan"));
        BC_Nodes.get(2).getChildren().addAll(BC_C_Nodes);
        BC_Nodes.add(new TreeItem("Web Address"));
        BC_Nodes.add(new TreeItem("Street Address"));
        BC_Nodes.add(new TreeItem("City-State-Zip"));
        BC_Nodes.add(new TreeItem("Primary Phone"));
        BC_Nodes.add(new TreeItem("Secondary Phone"));
        BC_Nodes.add(new TreeItem("Fax Number"));
        BC_Nodes.add(new TreeItem("Email Address"));
        ROOT_Nodes.get(2).getChildren().addAll(BC_Nodes);

        for (int i = 0; i < ROOT_Nodes.size(); i++) {
            falseRoot.getChildren().add(ROOT_Nodes.get(i));
        }
        TreeView<String> tree = new TreeView(falseRoot);
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
}
