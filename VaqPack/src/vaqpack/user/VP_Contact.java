/*-----------------------------------------------------------------------------*
 * VP_Contact.java
 * - Contact object, each user may have several
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 5400
 *-----------------------------------------------------------------------------*/
package vaqpack.user;

import javafx.beans.property.SimpleStringProperty;

public class VP_Contact {

    private final SimpleStringProperty
            email,
            name;

    /*------------------------------------------------------------------------*
     * VP_Contact()
     * - Constructor.
     * - Parameter email sets the contact's email.
     *------------------------------------------------------------------------*/
    public VP_Contact(String email, String name) {
        //-------- Initialization Start ----------\\
        this.email = new SimpleStringProperty(email);
        this.name = new SimpleStringProperty(name);
        //-------- Initialization End ------------\\
    }

    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/
        
    public String getName() {
        return this.name.get();
    }
    public void setName(String name) {
        this.name.set(name);
    }
    
    public String getEmail() {
        return this.email.get();
    }
    public void setEmail(String email) {
        this.email.set(email);
    }
}
