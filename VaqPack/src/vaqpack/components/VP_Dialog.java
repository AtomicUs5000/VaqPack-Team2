/**
 * VP_Dialog.java - Custom dialog box. FILE ID 2950
 */
package vaqpack.components;

import javafx.geometry.Pos;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * Custom dialog box class. This extends Dialog and consists of a constructor
 * to set dialog box style and title.
 *
 * @author William Dewald (Project Manager, Team-02)
 * @author Fernando Bazan
 * @author Erik Lopez
 * @author Raul Saavedra
 * @author Nathanael Carr
 * @version 1.0
 * @since 1.0
 */
public class VP_Dialog extends Dialog {

    private final GridPane dialogShell;

    /**
     * Constructor.
     * 
     * @param title The string to set the title text of the dialog box.
     * @since 1.0
     */
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

    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/
    /**
     * Accessor method.
     * 
     * @return The constructed GridPane dialog box.
     * @since 1.0
     */
    public GridPane getDialogShell() {
        return dialogShell;
    }
}
