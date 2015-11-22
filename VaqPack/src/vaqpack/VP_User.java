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

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VP_User {

    private int accessLevel,
            userID;
    private boolean completedPersonalInfo,
            completedResume,
            completedBusinessCard,
            completedCoverLetter,
            startedResume,
            startedBusinessCard,
            startedCoverLetter;
    private final StringProperty email,
            firstName,
            middleName,
            lastName,
            address1,
            address2,
            city,
            state,
            zip,
            phone,
            cell,
            docEmail; // This can be different from VaqPack user email.
    private String firstNameStored,
            middleNameStored,
            lastNameStored,
            address1Stored,
            address2Stored,
            cityStored,
            stateStored,
            zipStored,
            phoneStored,
            cellStored,
            docEmailStored;

    /*------------------------------------------------------------------------*
     * VP_User()
     * - Constructor.
     * - No parameters
     *------------------------------------------------------------------------*/
    protected VP_User() {
        email = new SimpleStringProperty();
        firstName = new SimpleStringProperty();
        middleName = new SimpleStringProperty();
        lastName = new SimpleStringProperty();
        address1 = new SimpleStringProperty();
        address2 = new SimpleStringProperty();
        city = new SimpleStringProperty();
        state = new SimpleStringProperty();
        zip = new SimpleStringProperty();
        phone = new SimpleStringProperty();
        cell = new SimpleStringProperty();
        docEmail = new SimpleStringProperty();
        userID = -1;
        accessLevel = -1;
        completedPersonalInfo = false;
        completedResume = false;
        completedBusinessCard = false;
        completedCoverLetter = false;
        startedResume = false;
        startedBusinessCard = false;
        startedCoverLetter = false;
    }

    /*------------------------------------------------------------------------*
     * logout()
     * - Clears all user data for the next user
     * - No parameters
     * - No return
     *------------------------------------------------------------------------*/
    protected void logout() {
        firstNameStored = null;
        middleNameStored = null;
        lastNameStored = null;
        address1Stored = null;
        address2Stored = null;
        cityStored = null;
        stateStored = null;
        zipStored = null;
        phoneStored = null;
        cellStored = null;
        docEmailStored = null;
        email.setValue(null);
        firstName.setValue(null);
        middleName.setValue(null);
        lastName.setValue(null);
        address1.setValue(null);
        address2.setValue(null);
        city.setValue(null);
        state.setValue(null);
        zip.setValue(null);
        phone.setValue(null);
        cell.setValue(null);
        docEmail.setValue(null);
        accessLevel = -1;
        userID = -1;
        completedPersonalInfo = false;
        completedResume = false;
        completedBusinessCard = false;
        completedCoverLetter = false;
        startedResume = false;
        startedBusinessCard = false;
        startedCoverLetter = false;
    }
    
    /*------------------------------------------------------------------------*
     * revert()
     * - Reverts bound properties to the last stored value
     * - No parameters
     * - No return
     *------------------------------------------------------------------------*/
    protected void revert() {
        firstName.setValue(firstNameStored);
        middleName.setValue(middleNameStored);
        lastName.setValue(lastNameStored);
        address1.setValue(address1Stored);
        address2.setValue(address2Stored);
        city.setValue(cityStored);
        state.setValue(stateStored);
        zip.setValue(zipStored);
        phone.setValue(phoneStored);
        cell.setValue(cellStored);
        docEmail.setValue(docEmailStored);
    }
    
    /*------------------------------------------------------------------------*
     * revert()
     * - Stored values are set to the current bound properties.
     * - No parameters
     * - No return
     *------------------------------------------------------------------------*/
    protected void save() {
        firstNameStored = firstName.getValue();
        middleNameStored = middleName.getValue();
        lastNameStored = lastName.getValue();
        address1Stored = address1.getValue();
        address2Stored = address2.getValue();
        cityStored = city.getValue();
        stateStored = state.getValue();
        zipStored = zip.getValue();
        phoneStored = phone.getValue();
        cellStored = cell.getValue();
        docEmailStored = docEmail.getValue();
        completedPersonalInfo = true;
        if (firstNameStored == null || lastNameStored == null || address1Stored == null ||
                cityStored == null || stateStored == null || zipStored == null) {
            completedPersonalInfo = false;
        }
    }

    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/
    protected boolean hasCompletedPersonalInfo() {
        return completedPersonalInfo;
    }
    
    protected boolean hasCompletedResume() {
        return completedResume;
    }

    protected boolean hasCompletedBusinessCard() {
        return completedBusinessCard;
    }

    protected boolean hasCompletedCoverLetter() {
        return completedCoverLetter;
    }
    
    protected boolean hasStartedResume() {
        return startedResume;
    }

    protected boolean hasStartedBusinessCard() {
        return startedBusinessCard;
    }

    protected boolean hasStartedCoverLetter() {
        return startedCoverLetter;
    }

    protected int getAccessLevel() {
        return accessLevel;
    }

    protected void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    protected StringProperty getEmail() {
        return email;
    }

    protected StringProperty getFirstName() {
        return firstName;
    }

    protected StringProperty getMiddleName() {
        return middleName;
    }

    protected StringProperty getLastName() {
        return lastName;
    }

    protected StringProperty getAddress1() {
        return address1;
    }

    protected StringProperty getAddress2() {
        return address2;
    }

    protected StringProperty getCity() {
        return city;
    }

    protected StringProperty getState() {
        return state;
    }

    protected StringProperty getZip() {
        return zip;
    }

    protected StringProperty getPhone() {
        return phone;
    }

    protected StringProperty getCell() {
        return cell;
    }

    protected StringProperty getDocEmail() {
        return docEmail;
    }

    protected int getUserID() {
        return userID;
    }

    protected void setUserID(int userID) {
        this.userID = userID;
    }
}
