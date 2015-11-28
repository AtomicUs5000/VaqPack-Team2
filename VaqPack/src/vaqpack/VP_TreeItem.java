package vaqpack;

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

    protected int getWizardNumber() {
        return wizardNumber;
    }

    protected DoubleProperty getPositionProp() {
        return positionProp;
    }
}
