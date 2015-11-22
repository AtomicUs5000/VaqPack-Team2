/*-----------------------------------------------------------------------------*
 * VP_Theme.java
 * - Contains the default theme and stores custom themes
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 2500
 *-----------------------------------------------------------------------------*/
package vaqpack;

public final class VP_Theme {

    /*------------------------------------------------------------------------*
     * VP_Theme()
     * - Private constructor. Cannot be instantiated
     * No Parameters.
     *------------------------------------------------------------------------*/
    private VP_Theme() {
    }
    
    public enum Default {
        NAME_1("Super Simple"),
        BC_BACKGROUND_1(" style = \"background-color:#FFF;\""),
        BC_NAME_1(" style = \"font-family: 'Times New Roman', Times, serif; font-weight: bold; font-size: 12pt; display: block;\""),
        BC_PROFESSION_1(" style=\"font-family:'Times New Roman', Times, serif;font-size: 10pt; display: block;\""),
        BC_COMPANY_NAME_1(" style=\"font-family:'Times New Roman', Times, serif;font-size: 11pt; display: block;\""),
        BC_COMPANY_SLOGAN_1(" style=\"font-family:'Times New Roman', Times, serif;padding-left:20pt; font-style: italic; font-size: 10pt; display: block;\""),
        BC_ADDRESS1_1(" style=\"font-family:'Times New Roman', Times, serif;padding-top:10pt; font-size: 10pt;display: block;\""),
        BC_ADDRESS2_1(" style=\"font-family:'Times New Roman', Times, serif;font-size: 10pt; display: block;\""),
        BC_CITY_STATE_ZIP_1(" style=\"font-family:'Times New Roman', Times, serif;font-size: 10pt; display: block;\""),
        BC_PHONE_CELL_1(" style=\"font-family:'Times New Roman', Times, serif;\""),
        BC_EMAIL_1(" style=\"font-family:'Times New Roman', Times, serif;font-size: 11pt; display: block;\""),
        BC_WEB_1(" style=\"font-family:'Times New Roman', Times, serif;font-size: 9pt; display: block;\""),
        
        
        NAME_2(""),
        BC_BACKGROUND_2(""),
        BC_NAME_2(""),
        BC_PROFESSION_2(""),
        BC_COMPANY_NAME_2(""),
        BC_COMPANY_SLOGAN_2(""),
        BC_ADDRESS1_2(""),
        BC_ADDRESS2_2(""),
        BC_CITY_STATE_ZIP_2(""),
        BC_PHONE_CELL_2(""),
        BC_EMAIL_2(""),
        BC_WEB_2(""),
        
        
        NAME_3(""),
        BC_BACKGROUND_3(""),
        BC_NAME_3(""),
        BC_PROFESSION_3(""),
        BC_COMPANY_NAME_3(""),
        BC_COMPANY_SLOGAN_3(""),
        BC_ADDRESS1_3(""),
        BC_ADDRESS2_3(""),
        BC_CITY_STATE_ZIP_3(""),
        BC_PHONE_CELL_3(""),
        BC_EMAIL_3(""),
        BC_WEB_3(""),
        
        NAME_4(""),
        BC_BACKGROUND_4(""),
        BC_NAME_4(""),
        BC_PROFESSION_4(""),
        BC_COMPANY_NAME_4(""),
        BC_COMPANY_SLOGAN_4(""),
        BC_ADDRESS1_4(""),
        BC_ADDRESS2_4(""),
        BC_CITY_STATE_ZIP_4(""),
        BC_PHONE_CELL_4(""),
        BC_EMAIL_4(""),
        BC_WEB_4("");
        
        
        private final String value;
        
        private Default(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
    



    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/
}
