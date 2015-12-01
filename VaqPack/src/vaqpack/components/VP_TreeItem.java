/*-----------------------------------------------------------------------------*
 * VP_TreeItem.java
 * - Custom TreeItem
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 2900
 *-----------------------------------------------------------------------------*/
package vaqpack.components;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class VP_TreeItem extends TreeItem {
    private final int wizardNumber;
    private final DoubleProperty positionProp;

    public VP_TreeItem(String value, int wizardNumber) {
        positionProp = new SimpleDoubleProperty();
        this.positionProp.setValue(0);
        this.wizardNumber = wizardNumber;
        this.setValue(value);
        if (wizardNumber == 4) {
            this.setGraphic(new ImageView(new Image("personal-icon.png")));
        }
    }

    public int getWizardNumber() {
        return wizardNumber;
    }

    public DoubleProperty getPositionProp() {
        return positionProp;
    }
}
