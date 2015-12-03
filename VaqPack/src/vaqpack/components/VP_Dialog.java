/*-----------------------------------------------------------------------------*
 * VP_TreeItem.java
 * - Custom Dialog Box
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 2950
 *-----------------------------------------------------------------------------*/
package vaqpack.components;

import javafx.geometry.Pos;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class VP_Dialog extends Dialog {

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

    public GridPane getDialogShell() {
        return dialogShell;
    }
}
