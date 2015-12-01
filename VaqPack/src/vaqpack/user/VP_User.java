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
 * FILE ID 5000
 *-----------------------------------------------------------------------------*/
package vaqpack.user;

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
    public VP_User() {
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
    public void logout() {
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
    public void revert() {
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
    public void save() {
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
    public boolean hasCompletedPersonalInfo() {
        return completedPersonalInfo;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public StringProperty getEmail() {
        return email;
    }

    public StringProperty getFirstName() {
        return firstName;
    }

    public StringProperty getMiddleName() {
        return middleName;
    }

    public StringProperty getLastName() {
        return lastName;
    }

    public StringProperty getAddress1() {
        return address1;
    }

    public StringProperty getAddress2() {
        return address2;
    }

    public StringProperty getCity() {
        return city;
    }

    public StringProperty getState() {
        return state;
    }

    public StringProperty getZip() {
        return zip;
    }

    public StringProperty getPhone() {
        return phone;
    }

    public StringProperty getCell() {
        return cell;
    }

    public StringProperty getDocEmail() {
        return docEmail;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public VP_BusinessCard getBcard() {
        return bcard;
    }

    public VP_Resume getResume() {
        return resume;
    }

    public VP_CoverLetter getCovlet() {
        return covlet;
    }

    public int[] getCoverLetterIds() {
        return coverLetterIds;
    }

    public int getCurrentCoverLetterIndex() {
        return currentCoverLetterIndex;
    }

    public void setCurrentCoverLetterIndex(int currentCoverLetterIndex) {
        this.currentCoverLetterIndex = currentCoverLetterIndex;
    }
}