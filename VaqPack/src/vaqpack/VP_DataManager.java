/*-----------------------------------------------------------------------------*
 * VP_DataManager.java
 * - Stores the data nodes (XML) as built or loaded by the user
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 *-----------------------------------------------------------------------------*/
package vaqpack;

public class VP_DataManager {
    private final VP_GUIController controller;
    private final VP_DatabaseManager dbManager;
    private final VP_DataToHtml data2html;
    private final VP_HtmlToPdf   html2pdf;
    private final VP_FileManager fileM;
    
    /*------------------------------------------------------------------------*
     * VP_DataManager()
     * - Constructor.
     * - Instantiates the datatbase manager.
     * - parameter VP_GUIController stored to access events
     *------------------------------------------------------------------------*/
    protected VP_DataManager(VP_GUIController controller) {
        this.controller = controller;
        dbManager = new VP_DatabaseManager();
        data2html = new VP_DataToHtml();
        html2pdf = new VP_HtmlToPdf();
        fileM = new VP_FileManager();
    }
    
    /*------------------------------------------------------------------------*
     * Setters and Getters
     *------------------------------------------------------------------------*/
}
