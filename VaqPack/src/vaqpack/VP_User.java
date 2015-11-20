/*-----------------------------------------------------------------------------*
 * VP_User.java
 * - User object
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 2400
 *-----------------------------------------------------------------------------*/
package vaqpack;

public class VP_User {
    private final int accessLevel;
    private String email;
    
    /*------------------------------------------------------------------------*
     * VP_User()
     * - Constructor.
     *------------------------------------------------------------------------*/
    protected VP_User(String email, int accessLevel) {
        this.email = email;
        this.accessLevel = accessLevel;
    }

    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/

    protected String getEmail() {
        return email;
    }

    protected void setEmail(String email) {
        this.email = email;
    }
    
    protected int getAccessLevel() {
        return accessLevel;
    }
}
