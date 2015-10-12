/*-----------------------------------------------------------------------------*
* VP_TemplateManager.java
* - Contains the default templates and stores custom templates
* Authors:
* - Team-02
* -- William Dewald (Project Manager)
* -- Fernando Bazan
* -- Nathanael Carr
* -- Erik Lopez
* -- Raul Saavedra
*-----------------------------------------------------------------------------*/
package vaqpack;

public class VP_TemplateManager {
    private final VP_DatabaseManager DB;
    private final VP_DataManager     DM;
    private final VP_GUIBuild        GB;
    private final VP_GUIEvents       GE;
    
    /*------------------------------------------------------------------------*
     * VP_TemplateManager()
     * - Constructor.
     *------------------------------------------------------------------------*/
    protected VP_TemplateManager(VP_GUIEvents GE) {
        this.GE = GE;
        GB = GE.getGB();
        DB = GE.getDB();
        DM = GE.getDM();
    }
    
    /*------------------------------------------------------------------------*
     * Setters and Getters
     *------------------------------------------------------------------------*/
}
