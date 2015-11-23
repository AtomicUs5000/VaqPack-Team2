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

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VP_CoverLetter {
    private int themeId = -1,
            numbParagraphs = 1;
    private final VP_User owner;
    private boolean startedCoverLetter,
            completedCoverLetter;
    private final StringProperty 
            adSource,
            adJobTitle,
            adRefNumber,
            contactFirstName,
            contactMiddleName,
            contactLastName,
            contactTitle,
            contactCompany,
            contactAddress1,
            contactAddress2,
            contactCity,
            contactState,
            contactZip,
            salutation,
            closing,
            signature;
    private final ArrayList<StringProperty> paragraphs;
    private final ArrayList<String> paragraphsStored;
    private String 
            adSourceStored,
            adJobTitleStored,
            adRefNumberStored,
            contactFirstNameStored,
            contactMiddleNameStored,
            contactLastNameStored,
            contactTitleStored,
            contactCompanyStored,
            contactAddress1Stored,
            contactAddress2Stored,
            contactCityStored,
            contactStateStored,
            contactZipStored,
            salutationStored,
            closingStored,
            signatureStored,
            xsl;
    
    /*------------------------------------------------------------------------*
     * VP_CoverLetter()
     * - Constructor.
     * Parameter owner is the logged in user who owns this.
     *------------------------------------------------------------------------*/
    protected VP_CoverLetter(VP_User owner) {
        this.owner = owner;
        adSource  = new SimpleStringProperty();
        adJobTitle  = new SimpleStringProperty();
        adRefNumber  = new SimpleStringProperty();
        contactFirstName  = new SimpleStringProperty();
        contactMiddleName  = new SimpleStringProperty();
        contactLastName  = new SimpleStringProperty();
        contactTitle  = new SimpleStringProperty();
        contactCompany  = new SimpleStringProperty();
        contactAddress1  = new SimpleStringProperty();
        contactAddress2  = new SimpleStringProperty();
        contactCity  = new SimpleStringProperty();
        contactState  = new SimpleStringProperty();
        contactZip  = new SimpleStringProperty();
        salutation  = new SimpleStringProperty();
        closing  = new SimpleStringProperty();
        signature  = new SimpleStringProperty();
        paragraphsStored = new ArrayList();
        paragraphs = new ArrayList();
        StringProperty paragraph = new SimpleStringProperty();
        paragraphs.add(paragraph);
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
        adSource.setValue(adSourceStored);
        adJobTitle.setValue(adJobTitleStored);
        adRefNumber.setValue(adRefNumberStored);
        contactFirstName.setValue(contactFirstNameStored);
        contactMiddleName.setValue(contactMiddleNameStored);
        contactLastName.setValue(contactLastNameStored);
        contactTitle.setValue(contactTitleStored);
        contactCompany.setValue(contactCompanyStored);
        contactAddress1.setValue(contactAddress1Stored);
        contactAddress2.setValue(contactAddress2Stored);
        contactCity.setValue(contactCityStored);
        contactState.setValue(contactStateStored);
        contactZip.setValue(contactZipStored);
        salutation.setValue(salutationStored);
        closing.setValue(closingStored);
        signature.setValue(signatureStored);
        for (int i = 0; i < numbParagraphs; i++) {
            paragraphs.get(i).setValue(paragraphsStored.get(i));
        }
    }
    
    /*------------------------------------------------------------------------*
     * save()
     * - Stored values are set to the current bound properties.
     * - No parameters
     * - No return
     *------------------------------------------------------------------------*/
    protected void save() {
        adSourceStored = adSource.getValue();
        adJobTitleStored = adJobTitle.getValue();
        adRefNumberStored = adRefNumber.getValue();
        
        contactFirstNameStored = contactFirstName.getValue();
        contactMiddleNameStored = contactMiddleName.getValue();
        contactLastNameStored = contactLastName.getValue();
        
        contactTitleStored = contactTitle.getValue();
        contactCompanyStored = contactCompany.getValue();
        
        contactAddress1Stored = contactAddress1.getValue();
        
        contactAddress2Stored = contactAddress2.getValue();
        
        contactCityStored = contactCity.getValue();
        contactStateStored = contactState.getValue();
        contactZipStored = contactZip.getValue();
        
        salutationStored = salutation.getValue();
        closingStored = closing.getValue();
        signatureStored = signature.getValue();
        for (int i = 0; i < numbParagraphs; i++) {
            paragraphsStored.set(i, paragraphs.get(i).getValueSafe());
        }
        // check for completeness
        
        
        generateXLS();
    }
    
    /*------------------------------------------------------------------------*
     * clear()
     * - Clears all data in the cover letter for the next user
     * - No parameters
     * - No return
     *------------------------------------------------------------------------*/
    protected void clear() {
        themeId = -1;
        paragraphs.clear();
        paragraphsStored.clear();
        numbParagraphs = 1;
        StringProperty paragraph = new SimpleStringProperty();
        paragraphs.add(paragraph);
        xsl = null;
        completedCoverLetter = false;
        startedCoverLetter = false;
        adSource.setValue(null);
        adJobTitle.setValue(null);
        adRefNumber.setValue(null);
        contactFirstName.setValue(null);
        contactMiddleName.setValue(null);
        contactLastName.setValue(null);
        contactTitle.setValue(null);
        contactCompany.setValue(null);
        contactAddress1.setValue(null);
        contactAddress2.setValue(null);
        contactCity.setValue(null);
        contactState.setValue(null);
        contactZip.setValue(null);
        salutation.setValue(null);
        closing.setValue(null);
        signature.setValue(null);
        adSourceStored = null;
        adJobTitleStored = null;
        adRefNumberStored = null;
        contactFirstNameStored = null;
        contactMiddleNameStored = null;
        contactLastNameStored = null;
        contactTitleStored = null;
        contactCompanyStored = null;
        contactAddress1Stored = null;
        contactAddress2Stored = null;
        contactCityStored = null;
        contactStateStored = null;
        contactZipStored = null;
        salutationStored = null;
        closingStored = null;
        signatureStored = null;
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

    protected int getNumbParagraphs() {
        return numbParagraphs;
    }

    protected void setNumbParagraphs(int numbParagraphs) {
        this.numbParagraphs = numbParagraphs;
    }
}
