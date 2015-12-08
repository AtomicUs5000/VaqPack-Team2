/**
 * VP_BusinessCard.java - One per VP_User
 * ID 5100
 */
package vaqpack.user;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Holds all of the data unique to a business card. Decides when to build or 
 * rebuild the XSL for file generation.
 *
 * @author William Dewald (Project Manager, Team-02)
 * @author Fernando Bazan
 * @author Erik Lopez
 * @author Raul Saavedra
 * @author Nathanael Carr
 * @version 1.0
 * @since 1.0
 */
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
    
    /**
     * Constructor.
     * 
     * @param owner The logged in VP_User who owns this business card.
     * @since 1.0
     */
    protected VP_BusinessCard(VP_User owner) {
        this.owner = owner;
        profession = new SimpleStringProperty();
        companyName = new SimpleStringProperty();
        companySlogan = new SimpleStringProperty();
        webPage = new SimpleStringProperty();
        startedBusinessCard = false;
        completedBusinessCard = false;
    }
    
    /**
     * Reverts bound properties to the last stored value
     * 
     * @since 1.0
     */
    public void revert() {
        profession.setValue(professionStored);
        companyName.setValue(companyNameStored);
        companySlogan.setValue(companySloganStored);
        webPage.setValue(webPageStored);
    }

    /**
     * Stored values are set to the current bound properties. Checks are to done
     * to determine the completeness of the business card. If the the card is
     * complete, and there have been changes, then the XSL for the card is generated.
     * 
     * @since 1.0
     */
    public void save() {
        changes = false;
        completedBusinessCard = false;
        startedBusinessCard = false;
        
        // check for changes
        if ((professionStored != null && !professionStored.equals(profession.getValue())) || 
                (professionStored == null && profession.getValue() != null) ) {
            professionStored = profession.getValue();
            changes = true;
        }
        if ((companyNameStored != null && !companyNameStored.equals(companyName.getValue())) || 
                (companyNameStored == null && companyName.getValue() != null) ) {
            companyNameStored = companyName.getValue();
            changes = true;
        }
        if ((companySloganStored != null && !companySloganStored.equals(companySlogan.getValue())) || 
                (companySloganStored == null && companySlogan.getValue() != null) ) {
            companySloganStored = companySlogan.getValue();
            changes = true;
        }
        if ((webPageStored != null && !webPageStored.equals(webPage.getValue())) || 
                (webPageStored == null && webPage.getValue() != null) ) {
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
        if (completedBusinessCard) {
            generateXSL();
        }
    }

    /**
     * Clears all data in the business card for the next user.
     * 
     * @since 1.0
     */
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
    
    /**
     * Creates and stores the XML style-sheet to be used in HTML conversion.
     * 
     * @since 1.0
     */
    private void generateXSL() {
        String currentTheme = String.valueOf(themeId * -1);
        xsl = "<?xml version=\"1.0\"?>\n"
                + "<xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" "
                + "version=\"1.0\">\n"
                + "<xsl:output method=\"xml\" indent=\"yes\"/>\n"
                + "<xsl:template match=\"/\">\n"
                + "<html>\n<head>\n<title>Business Card -- <xsl:value-of select=\"businesscard/name/lastname\"/></title>\n</head>\n<body>\n"
                + "<div style=\"border-collapse:collapse;margin:0;padding:0;width:3.5in;"
                + "height:2in;text-align:center;vertical-align:middle;border:1px solid #000;\">\n"
                + "<table style=\"margin:4pt;border-collapse:collapse;margin:0;padding:0;width:3.5in;"
                + "height:2in;text-align:center;vertical-align:middle;\">\n"
                + "<tr";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("BC_BACKGROUND_" + currentTheme);
        }
        xsl += ">\n<td>\n<span";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("BC_NAME_" + currentTheme);
        }
        xsl += " xml:space=\"preserve\"><xsl:value-of select=\"businesscard/name/firstname\"/> ";
        if (owner.getMiddleName().getValueSafe()!= null) {
            xsl += "  <xsl:value-of select=\"businesscard/name/middlename\"/> ";
        }
        xsl += "<xsl:value-of select=\"businesscard/name/lastname\"/></span>";
        if (professionStored != null) {
            xsl += "\n<span";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("BC_PROFESSION_" + currentTheme);
            }
            xsl += "><xsl:value-of select=\"businesscard/company/profession\"/></span>";
        }
        if (companyNameStored != null) {
            xsl += "\n<span";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("BC_COMPANY_NAME_" + currentTheme);
            }
            xsl += "><xsl:value-of select=\"businesscard/company/companyname\"/></span>";
        }
        if (companySloganStored != null) {
            xsl += "\n<span";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("BC_COMPANY_SLOGAN_" + currentTheme);
            }
            xsl += ">\"<xsl:value-of select=\"businesscard/company/slogan\"/>\"</span>";
        }
        xsl += "\n<span";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("BC_ADDRESS1_" + currentTheme);
        }
        xsl += "><xsl:value-of select=\"businesscard/address/line1\"/></span>";
        if (owner.getAddress2().getValueSafe() != null) {
            xsl += "\n<span";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("BC_ADDRESS2_" + currentTheme);
            }
            xsl += "><xsl:value-of select=\"businesscard/address/line2\"/></span>";
        }
        xsl += "\n<span";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("BC_CITY_STATE_ZIP_" + currentTheme);
        }
        xsl += "  xml:space=\"preserve\"><xsl:value-of select=\"businesscard/address/city\"/>, "
                + "<xsl:value-of select=\"businesscard/address/state\"/> "
                + "<xsl:value-of select=\"businesscard/address/zip\"/></span>";
        xsl += "\n<span";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("BC_PHONE_CELL_" + currentTheme);
        }
        xsl += "  xml:space=\"preserve\"><xsl:value-of select=\"businesscard/communication/phone\"/>";
        if (owner.getCell().getValueSafe() != null) {
            xsl += "<span style=\"padding-left:8pt;\">cell: <xsl:value-of select=\"businesscard/communication/cell\"/></span>";
        }
        xsl += "\n</span><span";
        if (themeId < 0) {
            xsl += VP_Theme.Default.valueOf("BC_EMAIL_" + currentTheme);
        }
        xsl += "><xsl:value-of select=\"businesscard/communication/email\"/></span>";
        if (webPageStored != null) {
            xsl += "\n<span";
            if (themeId < 0) {
                xsl += VP_Theme.Default.valueOf("BC_WEB_" + currentTheme);
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
    /**
     * Accessor method.
     *
     * @return The property for the business card profession field.
     * @since 1.0
     */
    public StringProperty getProfession() {
        return profession;
    }
    
    /**
     * Accessor method.
     *
     * @return The property for the business card company field.
     * @since 1.0
     */
    public StringProperty getCompanyName() {
        return companyName;
    }

    /**
     * Accessor method.
     *
     * @return The property for the business card company slogan field.
     * @since 1.0
     */
    public StringProperty getCompanySlogan() {
        return companySlogan;
    }

    /**
     * Accessor method.
     *
     * @return The property for the business card web page field.
     * @since 1.0
     */
    public StringProperty getWebPage() {
        return webPage;
    }
    
    /**
     * Accessor method.
     *
     * @return Whether or not the business card is complete.
     * @since 1.0
     */
    public boolean hasCompletedBusinessCard() {
        return completedBusinessCard;
    }
    
    /**
     * Accessor method.
     *
     * @return Whether or not the business card has been started.
     * @since 1.0
     */
    public boolean hasStartedBusinessCard() {
        return startedBusinessCard;
    }

    /**
     * Accessor method.
     *
     * @return The currently selected theme for this business card.
     * @since 1.0
     */
    public int getThemeId() {
        return themeId;
    }

    /**
     * Mutator method.
     *
     * @param themeId Sets the theme for this business card
     * @since 1.0
     */
    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    /**
     * Accessor method.
     *
     * @return The most recently generated XSL.
     * @since 1.0
     */
    public String getXsl() {
        return xsl;
    }

    /**
     * Accessor method.
     *
     * @return Whether or not changes have been made.
     * @since 1.0
     */
    public boolean hasChanges() {
        return changes;
    }
}
