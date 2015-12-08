/**
 * VP_Contact.java - Contact object, each user may have several
 * ID 5100
 */
package vaqpack.user;

import javafx.beans.property.SimpleStringProperty;

/**
 * Holds contact information for a single contact.
 *
 * @author William Dewald (Project Manager, Team-02)
 * @author Fernando Bazan
 * @author Erik Lopez
 * @author Raul Saavedra
 * @author Nathanael Carr
 * @version 1.0
 * @since 1.0
 */
public class VP_Contact {

    private final SimpleStringProperty
            email,
            name;

    /**
     * Constructor.
     * 
     * @param email The contact's email address.
     * @param name  The contact's name or label.
     * @since 1.0
     */
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
    /**
     * Accessor method.
     * 
     * @return The contact's name or label.
     * @since 1.0
     */
    public String getName() {
        return this.name.get();
    }
    
    /**
     * Mutator method.
     * 
     * @param name Sets the contact's name or label.
     * @since 1.0
     */
    public void setName(String name) {
        this.name.set(name);
    }
    
    /**
     * Accessor method.
     * 
     * @return The contact's email address.
     * @since 1.0
     */
    public String getEmail() {
        return this.email.get();
    }
    
    /**
     * Mutator method.
     * 
     * @param email Sets the contact's email address.
     * @since 1.0
     */
    public void setEmail(String email) {
        this.email.set(email);
    }
}
