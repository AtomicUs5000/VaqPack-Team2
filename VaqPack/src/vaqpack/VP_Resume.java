/*-----------------------------------------------------------------------------*
 * VP_Resume.java
 * - Resume object
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 2300
 *-----------------------------------------------------------------------------*/
package vaqpack;

public class VP_Resume {
    private int themeId = -1;
    private final VP_User owner;
    private boolean startedResume,
            completedResume;
    private String xsl;
    
    /*------------------------------------------------------------------------*
     * VP_Resume()
     * - Constructor.
     * Parameter owner is the logged in user who owns this.
     *------------------------------------------------------------------------*/
    protected VP_Resume(VP_User owner) {
        this.owner = owner;
        
        startedResume = false;
        completedResume = false;
    }
    
    /*------------------------------------------------------------------------*
     * revert()
     * - Reverts bound properties to the last stored value
     * - No parameters
     * - No return
     *------------------------------------------------------------------------*/
    protected void revert() {
    }
    
    /*------------------------------------------------------------------------*
     * save()
     * - Stored values are set to the current bound properties.
     * - No parameters
     * - No return
     *------------------------------------------------------------------------*/
    protected void save() {
        generateXLS();
    }
    
    /*------------------------------------------------------------------------*
     * clear()
     * - Clears all data in the resume for the next user
     * - No parameters
     * - No return
     *------------------------------------------------------------------------*/
    protected void clear() {
        themeId = -1;
        xsl = null;
        completedResume = false;
        startedResume = false;
    }
    
    /*------------------------------------------------------------------------*
     * generateXLS()
     * - Creates the xml stylesheet to be passed to html conversion.
     * - No parameters
     * - No return
     *------------------------------------------------------------------------*/
    private void generateXLS() {
        
    }

    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/

    protected boolean hasStartedResume() {
        return startedResume;
    }

    protected boolean hasCompletedResume() {
        return completedResume;
    }
}
