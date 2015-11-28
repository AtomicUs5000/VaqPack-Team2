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

    private final VP_BusinessCard bcard;
    private final VP_Resume resume;
    private final VP_CoverLetter covlet;
    private int accessLevel,
            userID,
            currentCoverLetterIndex;
    private boolean completedPersonalInfo;
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
    private final int[] coverLetterIds;

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
        bcard = new VP_BusinessCard(this);
        resume = new VP_Resume(this);
        covlet = new VP_CoverLetter(this);
        coverLetterIds = new int[3];
        coverLetterIds[0] = 0;
        coverLetterIds[1] = 0;
        coverLetterIds[2] = 0;
        currentCoverLetterIndex = 0;
        covlet.setId(coverLetterIds[currentCoverLetterIndex]);
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
        coverLetterIds[0] = 0;
        coverLetterIds[1] = 0;
        coverLetterIds[2] = 0;
        currentCoverLetterIndex = 0;
        covlet.setId(coverLetterIds[currentCoverLetterIndex]);
        bcard.clear();
        resume.clear();
        covlet.clear();
        completedPersonalInfo = false;
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
     * save()
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

    protected VP_BusinessCard getBcard() {
        return bcard;
    }

    protected VP_Resume getResume() {
        return resume;
    }

    protected VP_CoverLetter getCovlet() {
        return covlet;
    }

    protected int[] getCoverLetterIds() {
        return coverLetterIds;
    }

    protected int getCurrentCoverLetterIndex() {
        return currentCoverLetterIndex;
    }

    protected void setCurrentCoverLetterIndex(int currentCoverLetterIndex) {
        this.currentCoverLetterIndex = currentCoverLetterIndex;
    }
}
