/*-----------------------------------------------------------------------------*
* VP_NetworkManager.java
* - Controls connections such as sending email.
*   Might need to work with VP_DatabaseManager (unknown at this time)
* Authors:
* - Team-02
* -- William Dewald (Project Manager)
* -- Fernando Bazan
* -- Nathanael Carr
* -- Erik Lopez
* -- Raul Saavedra
*-----------------------------------------------------------------------------*/
package vaqpack;

public class VP_NetworkManager {
    private final VP_GUIEvents   GE;
    private final VP_FileManager FM;
    
    /*------------------------------------------------------------------------*
     * VP_NetworkManager()
     * - Constructor.
     *------------------------------------------------------------------------*/
    protected VP_NetworkManager(VP_GUIEvents GE) {
        this.GE = GE;
        FM = GE.getFM();
    }
    
    /*------------------------------------------------------------------------*
     * Setters and Getters
     *------------------------------------------------------------------------*/
}
