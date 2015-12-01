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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VP_CoverLetter {
    private int themeId = -1,
            numbParagraphs = 1,
            numbParagraphsStored = 1,
            id = 0;
    private final VP_User owner;
    private boolean startedCoverLetter,
            completedCoverLetter,
            changes;
    private final StringProperty
            date,
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
            closing;
    private final ArrayList<StringProperty> paragraphs;
    private final ArrayList<String> paragraphsStored;
    private String 
            dateStored,
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
            xsl;
    
    /*------------------------------------------------------------------------*
     * VP_CoverLetter()
     * - Constructor.
     * Parameter owner is the logged in user who owns this.
     *------------------------------------------------------------------------*/
    protected VP_CoverLetter(VP_User owner) {
        this.owner = owner;
        date = new SimpleStringProperty();
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
        paragraphsStored = new ArrayList();
        paragraphs = new ArrayList();
        for (int i = 0; i < 9; i ++) {
            paragraphsStored.add("");
            paragraphs.add(new SimpleStringProperty());
        }
        startedCoverLetter = false;
        completedCoverLetter = false;
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        String formattedDate = formatter.format(new Date());
        date.setValue(formattedDate);
        dateStored = formattedDate;
    }
    
    /*------------------------------------------------------------------------*
     * revert()
     * - Reverts bound properties to the last stored value
     * - No parameters
     * - No return
     *------------------------------------------------------------------------*/
    protected void revert() {
        date.setValue(dateStored);
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
        for (int i = 0; i < 9; i++) {
            paragraphs.get(i).setValue(paragraphsStored.get(i));
        }
        numbParagraphs = numbParagraphsStored;
    }
    
    /*------------------------------------------------------------------------*
     * save()
     * - Stored values are set to the current bound properties.
     * - No parameters
     * - No return
     *------------------------------------------------------------------------*/
    protected void save() {
        changes = false;
        completedCoverLetter = false;
        startedCoverLetter = false;

        if (numbParagraphs != numbParagraphsStored) {
            numbParagraphsStored = numbParagraphs;
            changes = true;
        }
        for (int i = 0; i < 9; i++) {
            if (!paragraphsStored.get(i).equals(paragraphs.get(i).getValueSafe())) {
                paragraphsStored.set(i, paragraphs.get(i).getValueSafe());
                changes = true;
            }
        }
        if ((dateStored != null && !dateStored.equals(date.getValue())) || 
                (dateStored == null && date.getValue() != null) ) {
            dateStored = date.getValue();
            changes = true;
        }
        if ((adSourceStored != null && !adSourceStored.equals(adSource.getValue())) || 
                (adSourceStored == null && adSource.getValue() != null) ) {
            adSourceStored = adSource.getValue();
            changes = true;
        }
        if ((adJobTitleStored != null && !adJobTitleStored.equals(adJobTitle.getValue())) || 
                (adJobTitleStored == null && adJobTitle.getValue() != null) ) {
            adJobTitleStored = adJobTitle.getValue();
            changes = true;
        }
        if ((adRefNumberStored != null && !adRefNumberStored.equals(adRefNumber.getValue())) || 
                (adRefNumberStored == null && adRefNumber.getValue() != null) ) {
            adRefNumberStored = adRefNumber.getValue();
            changes = true;
        }
        if ((contactFirstNameStored != null && !contactFirstNameStored.equals(contactFirstName.getValue())) || 
                (contactFirstNameStored == null && contactFirstName.getValue() != null) ) {
            contactFirstNameStored = contactFirstName.getValue();
            changes = true;
        }
        if ((contactMiddleNameStored != null && !contactMiddleNameStored.equals(contactMiddleName.getValue())) || 
                (contactMiddleNameStored == null && contactMiddleName.getValue() != null) ) {
            contactMiddleNameStored = contactMiddleName.getValue();
            changes = true;
        }
        if ((contactLastNameStored != null && !contactLastNameStored.equals(contactLastName.getValue())) || 
                (contactLastNameStored == null && contactLastName.getValue() != null) ) {
            contactLastNameStored = contactLastName.getValue();
            changes = true;
        }
        if ((contactTitleStored != null && !contactTitleStored.equals(contactTitle.getValue())) || 
                (contactTitleStored == null && contactTitle.getValue() != null) ) {
            contactTitleStored = contactTitle.getValue();
            changes = true;
        }
        if ((contactCompanyStored != null && !contactCompanyStored.equals(contactCompany.getValue())) || 
                (contactCompanyStored == null && contactCompany.getValue() != null) ) {
            contactCompanyStored = contactCompany.getValue();
            changes = true;
        }
        if ((contactAddress1Stored != null && !contactAddress1Stored.equals(contactAddress1.getValue())) || 
                (contactAddress1Stored == null && contactAddress1.getValue() != null) ) {
            contactAddress1Stored = contactAddress1.getValue();
            changes = true;
        }
        if ((contactAddress2Stored != null && !contactAddress2Stored.equals(contactAddress2.getValue())) || 
                (contactAddress2Stored == null && contactAddress2.getValue() != null) ) {
            contactAddress2Stored = contactAddress2.getValue();
            changes = true;
        }
        if ((contactCityStored != null && !contactCityStored.equals(contactCity.getValue())) || 
                (contactCityStored == null && contactCity.getValue() != null) ) {
            contactCityStored = contactCity.getValue();
            changes = true;
        }
        if ((contactStateStored != null && !contactStateStored.equals(contactState.getValue())) || 
                (contactStateStored == null && contactState.getValue() != null) ) {
            contactStateStored = contactState.getValue();
            changes = true;
        }
        if ((contactZipStored != null && !contactZipStored.equals(contactZip.getValue())) || 
                (contactZipStored == null && contactZip.getValue() != null) ) {
            contactZipStored = contactZip.getValue();
            changes = true;
        }
        if ((salutationStored != null && !salutationStored.equals(salutation.getValue())) || 
                (salutationStored == null && salutation.getValue() != null) ) {
            salutationStored = salutation.getValue();
            changes = true;
        }
        if ((closingStored != null && !closingStored.equals(closing.getValue())) || 
                (closingStored == null && closing.getValue() != null) ) {
            closingStored = closing.getValue();
            changes = true;
        }
        // check for completeness
        if (contactFirstNameStored != null && !contactFirstNameStored.equals("") &&
                contactLastNameStored != null && !contactLastNameStored.equals("") &&
                contactAddress1Stored != null && !contactAddress1Stored.equals("") &&
                contactCityStored != null && !contactCityStored.equals("") &&
                contactStateStored != null && !contactStateStored.equals("") &&
                contactZipStored != null && !contactZipStored.equals("") &&
                salutationStored != null && !salutationStored.equals("") &&
                closingStored != null && !closingStored.equals("") &&
                paragraphsStored.get(0) != null && !paragraphsStored.get(0).equals("")) {
            startedCoverLetter = true;
            completedCoverLetter = true;
        } else if (adSource != null ||
                adJobTitle != null ||
                adRefNumber != null ||
                contactFirstNameStored != null ||
                contactLastNameStored != null ||
                contactCompanyStored != null ||
                contactTitleStored != null ||
                contactAddress1Stored != null ||
                contactAddress2Stored != null ||
                contactCityStored != null ||
                contactStateStored != null ||
                contactZipStored != null ||
                salutationStored != null ||
                closingStored != null ||
                paragraphsStored.get(0) != null) {
            startedCoverLetter = true;
        }
        if (changes && completedCoverLetter)
            generateXSL();
    }
    
    /*------------------------------------------------------------------------*
     * clear()
     * - Clears all data in the cover letter for the next user
     * - No parameters
     * - No return
     *------------------------------------------------------------------------*/
    protected void clear() {
        themeId = -1;
        numbParagraphs = 1;
        numbParagraphsStored = 1;
        for (int i = 0; i < 9; i ++) {
            paragraphsStored.set(i, "");
        }
        for (int i = 0; i < 9; i ++) {
            paragraphs.get(i).setValue("");
        }
        xsl = null;
        completedCoverLetter = false;
        startedCoverLetter = false;
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        String formattedDate = formatter.format(new Date());
        date.setValue(formattedDate);
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
        dateStored = null;
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
        id = 0;
    }
    
    /*------------------------------------------------------------------------*
     * generateXSL()
     * - Creates the xml stylesheet to be passed to html conversion.
     * - No parameters
     * - No return
     *------------------------------------------------------------------------*/
    private void generateXSL() {
        xsl = "<?xml version=\"1.0\"?>\n"
                + "<xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" "
                + "version=\"1.0\">\n"
                + "<xsl:output method=\"xml\" indent=\"yes\"/>\n"
                + "<xsl:template match=\"/\">\n"
                + "<html>\n<head>\n<title>Cover Letter</title>\n</head>\n<body style=\"padding:20px;\">\n<div";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("CL_NAME_" + (themeId * -1));
        }
        xsl += "><span xml:space=\"preserve\"><xsl:value-of select=\"coverletter/heading/name/firstname\"/> ";
        if (owner.getMiddleName().getValueSafe()!= null) {
            xsl += " <xsl:value-of select=\"coverletter/heading/name/middlename\"/> ";
        }
        xsl += " <xsl:value-of select=\"coverletter/heading/name/lastname\"/></span></div>\n<div";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("CL_ADDRESS1_" + (themeId * -1));
        }
        xsl += "><xsl:value-of select=\"coverletter/heading/address/line1\"/></div>\n<div";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("CL_ADDRESS2_" + (themeId * -1));
        }
        xsl += "><xsl:value-of select=\"coverletter/heading/address/line2\"/></div>\n<div";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("CL_CITY_STATE_ZIP_" + (themeId * -1));
        }
        xsl += "><span xml:space=\"preserve\"><xsl:value-of select=\"coverletter/heading/address/city\"/>, "
                + "  <xsl:value-of select=\"coverletter/heading/address/state\"/> "
                + "<xsl:value-of select=\"coverletter/heading/address/zip\"/></span></div>\n<div";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("CL_CITY_STATE_ZIP_" + (themeId * -1));
        }
        xsl += "><span xml:space=\"preserve\">phone: <xsl:value-of select=\"coverletter/heading/communication/phone\"/> "
                + " <span style=\"padding-left:8pt;\">cell: <xsl:value-of select=\"coverletter/heading/communication/cell\"/></span> "
                + " <span style=\"padding-left:8pt;\"><xsl:value-of select=\"coverletter/heading/communication/email\"/> </span></span></div>\n"
                + "<hr />\n<br />\n<div";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("CL_DATE_" + (themeId * -1));
        }
        xsl += "><xsl:value-of select=\"coverletter/date\"/></div>\n<br />\n<br />\n<div";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("CL_AD_" + (themeId * -1));
        }
        xsl += "><div><xsl:value-of select=\"coverletter/adreference/source\"/></div>"
                + "<div><xsl:value-of select=\"coverletter/adreference/position\"/></div>"
                + "<div><xsl:value-of select=\"coverletter/adreference/refnumber\"/></div></div>\n<div";
        
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("CL_CONTACT_NAME_" + (themeId * -1));
        }
        xsl += "><span xml:space=\"preserve\"><xsl:value-of select=\"coverletter/contactinformation/name/firstname\"/> ";
        if (contactMiddleNameStored != null) {
            xsl += " <xsl:value-of select=\"coverletter/contactinformation/name/middlename\"/> ";
        }
        xsl += " <xsl:value-of select=\"coverletter/contactinformation/name/lastname\"/></span></div>\n";
        if (contactTitleStored != null) {
            xsl += "<div";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("CL_CONTACT_INFO_" + (themeId * -1));
            }
            xsl += "><xsl:value-of select=\"coverletter/contactinformation/company/title\"/></div>\n";
        }
        if (contactCompanyStored != null) {
            xsl += "<div";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("CL_CONTACT_INFO_" + (themeId * -1));
            }
            xsl += "><xsl:value-of select=\"coverletter/contactinformation/company/companyname\"/></div>\n";
        }
        xsl += "<div";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("CL_CONTACT_INFO_" + (themeId * -1));
        }
        xsl += "><xsl:value-of select=\"coverletter/contactinformation/address/line1\"/></div>\n";
        if (contactAddress2Stored != null) {
            xsl += "<div";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("CL_CONTACT_INFO_" + (themeId * -1));
            }
            xsl += "><xsl:value-of select=\"coverletter/contactinformation/address/line2\"/></div>\n";
        }
        xsl += "<div";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("CL_CONTACT_INFO_" + (themeId * -1));
        }
        xsl += "><span xml:space=\"preserve\"><xsl:value-of select=\"coverletter/contactinformation/address/city\"/>, "
                + "  <xsl:value-of select=\"coverletter/contactinformation/address/state\"/> "
                + "<xsl:value-of select=\"coverletter/contactinformation/address/zip\"/></span></div>\n<br />\n<div";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("CL_SALUTATION_" + (themeId * -1));
        }
        xsl += "><xsl:value-of select=\"coverletter/salutation\"/>,</div>\n<br />\n";
        for (int i = 0; i < numbParagraphs; i++) {
            xsl += "<p";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("CL_PARAGRAPH_" + (themeId * -1));
            }
            xsl += "><xsl:value-of select=\"coverletter/body/paragraph" + (i + 1) + "\"/></p>\n";
        }
        xsl += "<br />\n<div";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("CL_CLOSING_" + (themeId * -1));
        }
        xsl += "><xsl:value-of select=\"coverletter/closing\"/>,</div>\n<br />\n<div";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("CL_SIGNATURE_" + (themeId * -1));
        }
        xsl += "><span xml:space=\"preserve\"><xsl:value-of select=\"coverletter/signature/firstname\"/> ";
        if (owner.getMiddleName().getValueSafe()!= null) {
            xsl += " <xsl:value-of select=\"coverletter/heading/name/middlename\"/> ";
        }
        xsl += " <xsl:value-of select=\"coverletter/heading/name/lastname\"/></span></div>"
                + "\n</body>\n</html>\n</xsl:template>\n</xsl:stylesheet>";
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

    protected int getThemeId() {
        return themeId;
    }

    protected void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    protected StringProperty getAdSource() {
        return adSource;
    }

    protected StringProperty getAdJobTitle() {
        return adJobTitle;
    }

    protected StringProperty getAdRefNumber() {
        return adRefNumber;
    }

    protected StringProperty getContactFirstName() {
        return contactFirstName;
    }

    protected StringProperty getContactMiddleName() {
        return contactMiddleName;
    }

    protected StringProperty getContactLastName() {
        return contactLastName;
    }

    protected StringProperty getContactTitle() {
        return contactTitle;
    }

    protected StringProperty getContactCompany() {
        return contactCompany;
    }

    protected StringProperty getContactAddress1() {
        return contactAddress1;
    }

    protected StringProperty getContactAddress2() {
        return contactAddress2;
    }

    protected StringProperty getContactCity() {
        return contactCity;
    }

    protected StringProperty getContactState() {
        return contactState;
    }

    protected StringProperty getContactZip() {
        return contactZip;
    }

    protected StringProperty getSalutation() {
        return salutation;
    }

    protected StringProperty getClosing() {
        return closing;
    }

    protected ArrayList<StringProperty> getParagraphs() {
        return paragraphs;
    }
    
    protected boolean hasChanges() {
        return changes;
    }

    protected int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    protected StringProperty getDate() {
        return date;
    }
    
    protected String getXsl() {
        return xsl;
    }
}
