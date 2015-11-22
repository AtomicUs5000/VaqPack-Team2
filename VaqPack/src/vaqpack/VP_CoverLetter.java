/*-----------------------------------------------------------------------------*
 * VP_CoverLetter.java
 * - Cover letter object, each user may have several
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 2200
 *-----------------------------------------------------------------------------*/
package vaqpack;

public class VP_CoverLetter {
    private int themeId = -1;
    private final VP_User owner;
    private boolean startedCoverLetter,
            completedCoverLetter;
    private String xsl;
    
    /*------------------------------------------------------------------------*
     * VP_CoverLetter()
     * - Constructor.
     * Parameter owner is the logged in user who owns this.
     *------------------------------------------------------------------------*/
    protected VP_CoverLetter(VP_User owner) {
        this.owner = owner;
        
        startedCoverLetter = false;
        completedCoverLetter = false;
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
        completedCoverLetter = false;
        startedCoverLetter = false;
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

    protected boolean hasStartedCoverLetter() {
        return startedCoverLetter;
    }

    protected boolean hasCompletedCoverLetter() {
        return completedCoverLetter;
    }
}
