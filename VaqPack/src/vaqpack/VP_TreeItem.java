package vaqpack;

import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class VP_TreeItem extends TreeItem {
        private final int wizardNumber;
        
        public VP_TreeItem(String value, int wizardNumber) {
            this.wizardNumber = wizardNumber;
            this.setValue(value);
            if (wizardNumber == 4) {
                this.setGraphic(new ImageView(new Image("personal-icon.png")));
            }
        }

        protected int getWizardNumber() {
            return wizardNumber;
        }
        
    }
