/*-----------------------------------------------------------------------------*
 * VP_BusinessCard.java
 * - Business card object
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 2100
 *-----------------------------------------------------------------------------*/
package vaqpack;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VP_BusinessCard {
    private int themeId = -1;
    private final VP_User owner;
    private final StringProperty profession,
            companyName,
            companySlogan,
            webPage;
    private String xsl,
            professionStored,
            companyNameStored,
            companySloganStored,
            webPageStored;
    private boolean startedBusinessCard,
            completedBusinessCard,
            changes;
    
    /*------------------------------------------------------------------------*
     * VP_BusinessCard()
     * - Constructor.
     * Parameter owner is the logged in user who owns this.
     *------------------------------------------------------------------------*/
    protected VP_BusinessCard(VP_User owner) {
        this.owner = owner;
        profession = new SimpleStringProperty();
        companyName = new SimpleStringProperty();
        companySlogan = new SimpleStringProperty();
        webPage = new SimpleStringProperty();
        startedBusinessCard = false;
        completedBusinessCard = false;
    }
    
    /*------------------------------------------------------------------------*
     * revert()
     * - Reverts bound properties to the last stored value
     * - No parameters
     * - No return
     *------------------------------------------------------------------------*/
    protected void revert() {
        profession.setValue(professionStored);
        companyName.setValue(companyNameStored);
        companySlogan.setValue(companySloganStored);
        webPage.setValue(webPageStored);
    }
    
    /*------------------------------------------------------------------------*
     * save()
     * - Stored values are set to the current bound properties.
     * - No parameters
     * - No return
     *------------------------------------------------------------------------*/
    protected void save() {
        changes = false;
        completedBusinessCard = false;
        startedBusinessCard = false;
        
        if (!professionStored.equals(profession.getValueSafe())) {
            professionStored = profession.getValue();
            changes = true;
        }
        if (!companyNameStored.equals(companyName.getValueSafe())) {
            companyNameStored = companyName.getValue();
            changes = true;
        }
        if (!companySloganStored.equals(companySlogan.getValueSafe())) {
            companySloganStored = companySlogan.getValue();
            changes = true;
        }
        if (!webPageStored.equals(webPage.getValueSafe())) {
            webPageStored = webPage.getValue();
            changes = true;
        }
        
        // check for completeness
        if (professionStored != null && !professionStored.equals("")) {
            startedBusinessCard = true;
            completedBusinessCard = true;
        } else if(companyNameStored != null || companySloganStored != null || webPageStored != null) {
            startedBusinessCard = true;
        }
        if (changes) {
            generateXLS();
        }
    }
    
    /*------------------------------------------------------------------------*
     * clear()
     * - Clears all data in the business card for the next user
     * - No parameters
     * - No return
     *------------------------------------------------------------------------*/
    protected void clear() {
        themeId = -1;
        xsl = null;
        professionStored = null;
        companyNameStored = null;
        companySloganStored = null;
        webPageStored = null;
        companyName.setValue(null);
        companySlogan.setValue(null);
        webPage.setValue(null);
        completedBusinessCard = false;
        startedBusinessCard = false;
    }
    
    /*------------------------------------------------------------------------*
     * generateXLS()
     * - Creates the xml stylesheet to be passed to html conversion.
     * - No parameters
     * - No return
     *------------------------------------------------------------------------*/
    private void generateXLS() {
        xsl = "<?xml version=\"1.0\"?>\n"
                + "<xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" "
                + "version=\"1.0\">\n"
                + "<xsl:output method=\"xml\" indent=\"yes\"/>\n"
                + "<xsl:template match=\"/\">\n"
                + "<html>\n<head>\n<title>Business Card</title>\n</head>\n<body>\n"
                + "<div style=\"border-collapse:collapse;margin:0;padding:0;width:3.5in;"
                + "height:2in;text-align:center;vertical-align:middle;border:1px solid #000;\">\n"
                + "<table style=\"margin:4pt;border-collapse:collapse;margin:0;padding:0;width:3.5in;"
                + "height:2in;text-align:center;vertical-align:middle;\">\n"
                + "<tr";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("BC_BACKGROUND_" + (themeId * -1));
        }
        xsl += ">\n<td>\n<span";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("BC_NAME_" + (themeId * -1));
        }
        xsl += " xml:space=\"preserve\"><xsl:value-of select=\"businesscard/name/firstname\"/> ";
        if (owner.getMiddleName().getValueSafe()!= null) {
            xsl += "  <xsl:value-of select=\"businesscard/name/middlename\"/> ";
        }
        xsl += "<xsl:value-of select=\"businesscard/name/lastname\"/></span>";
        if (professionStored != null) {
            xsl += "\n<span";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("BC_PROFESSION_" + (themeId * -1));
            }
            xsl += "><xsl:value-of select=\"businesscard/company/profession\"/></span>";
        }
        if (companyNameStored != null) {
            xsl += "\n<span";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("BC_COMPANY_NAME_" + (themeId * -1));
            }
            xsl += "><xsl:value-of select=\"businesscard/company/companyname\"/></span>";
        }
        if (companySloganStored != null) {
            xsl += "\n<span";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("BC_COMPANY_SLOGAN_" + (themeId * -1));
            }
            xsl += ">\"<xsl:value-of select=\"businesscard/company/slogan\"/>\"</span>";
        }
        xsl += "\n<span";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("BC_ADDRESS1_" + (themeId * -1));
        }
        xsl += "><xsl:value-of select=\"businesscard/address/line1\"/></span>";
        if (owner.getAddress2().getValueSafe() != null) {
            xsl += "\n<span";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("BC_ADDRESS2_" + (themeId * -1));
            }
            xsl += "><xsl:value-of select=\"businesscard/address/line2\"/></span>";
        }
        xsl += "\n<span";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("BC_CITY_STATE_ZIP_" + (themeId * -1));
        }
        xsl += "  xml:space=\"preserve\"><xsl:value-of select=\"businesscard/address/city\"/>, "
                + "<xsl:value-of select=\"businesscard/address/state\"/> "
                + "<xsl:value-of select=\"businesscard/address/zip\"/></span>";
        xsl += "\n<span";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("BC_PHONE_CELL_" + (themeId * -1));
        }
        xsl += "  xml:space=\"preserve\"><xsl:value-of select=\"businesscard/communication/phone\"/>";
        if (owner.getCell().getValueSafe() != null) {
            xsl += "<span style=\"padding-left:8pt;\">cell: <xsl:value-of select=\"businesscard/communication/cell\"/></span>";
        }
        xsl += "\n</span><span";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("BC_EMAIL_" + (themeId * -1));
        }
        xsl += "><xsl:value-of select=\"businesscard/communication/email\"/></span>";
        if (webPageStored != null) {
            xsl += "\n<span";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("BC_WEB_" + (themeId * -1));
            }
            xsl += "><xsl:value-of select=\"businesscard/communication/web\"/></span>";
        }
        xsl += "\n</td>\n</tr>\n</table>\n</div>\n</body>\n</html>\n</xsl:template>\n</xsl:stylesheet>";
    }

    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/
    protected StringProperty getProfession() {
        return profession;
    }
    
    protected StringProperty getCompanyName() {
        return companyName;
    }

    protected StringProperty getCompanySlogan() {
        return companySlogan;
    }

    protected StringProperty getWebPage() {
        return webPage;
    }
    
    protected boolean hasCompletedBusinessCard() {
        return completedBusinessCard;
    }
    
    protected boolean hasStartedBusinessCard() {
        return completedBusinessCard;
    }

    protected int getThemeId() {
        return themeId;
    }

    protected void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    protected String getXsl() {
        return xsl;
    }

    protected boolean hasChanges() {
        return changes;
    }
}
