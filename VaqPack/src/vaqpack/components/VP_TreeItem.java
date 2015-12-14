/**
 * VP_TreeItem.java - Custom TreeItem. FILE ID 2900
 */
package vaqpack.components;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Custom tree item class. This extends TreeItem and consists of an overloaded
 * constructor.
 *
 * @author William Dewald (Project Manager, Team-02)
 * @author Fernando Bazan
 * @author Erik Lopez
 * @author Raul Saavedra
 * @author Nathanael Carr
 * @version 1.0
 * @since 1.0
 */
public class VP_TreeItem extends TreeItem {
    private final int wizardNumber;
    private final DoubleProperty positionProp;

    /**
     * Constructor. 
     * 
     * @param value Text value of the tree item.
     * @param wizardNumber Defines component icon.
     * @since 1.0
     */
    public VP_TreeItem(String value, int wizardNumber) {
        positionProp = new SimpleDoubleProperty();
        this.positionProp.setValue(0);
        this.wizardNumber = wizardNumber;
        this.setValue(value);
        if (wizardNumber == 4) {
            this.setGraphic(new ImageView(new Image("personal-icon.png")));
        }
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
     * @return The value defining the component icon.
     * @since 1.0
     */
    public int getWizardNumber() {
        return wizardNumber;
    }

    /**
     * Accessor method.
     * 
     * @return Tree item position value.
     */
    public DoubleProperty getPositionProp() {
        return positionProp;
    }
}
