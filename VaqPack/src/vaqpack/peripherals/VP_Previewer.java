package vaqpack.peripherals;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class VP_Previewer {

   public WebView buildPreview()
   {
      WebView preview = new WebView();
      WebEngine webEngine = preview.getEngine();
      webEngine.load("http://www.google.com/");
      preview.setScaleX(0.75);
      preview.setScaleY(0.75);
      return preview;
   }
}
