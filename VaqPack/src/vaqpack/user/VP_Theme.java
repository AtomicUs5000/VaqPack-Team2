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
 * FILE ID 5500
 *-----------------------------------------------------------------------------*/
package vaqpack.user;

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
        CL_NAME_1(" style = \"text-align:center; font-family: 'Times New Roman', Times, serif; font-weight: bold; font-size: 14pt; display: block;\""),
        CL_ADDRESS1_1(" style = \"text-align:center; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\""),
        CL_ADDRESS2_1(" style = \"text-align:center; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\""),
        CL_CITY_STATE_ZIP_1(" style = \"text-align:center; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\""),
        CL_COMMUNICATION_1(" style = \"text-align:center; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\""),
        CL_AD_1(" style = \"float:right; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\""),
        CL_DATE_1(" style = \"font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\""),
        CL_CONTACT_NAME_1(" style = \"font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\""),
        CL_CONTACT_INFO_1(" style = \"font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\""),
        CL_SALUTATION_1(" style = \"font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\""),
        CL_CLOSING_1(" style = \"font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\""),
        CL_PARAGRAPH_1(" style = \"text-indent:1cm; padding:0 0 6px 0; margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\""),
        CL_SIGNATURE_1(" style = \"font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\""),
        RES_NAME_1(" style=\"text-align:center; font-family: 'Times New Roman', Times, serif; font-weight: bold; font-size: 14pt; display: block;\""),
        RES_ADDRESS1_1(" style=\"text-align:center; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\""),
        RES_ADDRESS2_1(" style=\"text-align:center; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\""),
        RES_CITY_STATE_ZIP_1(" style=\"text-align:center; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\""),
        RES_COMMUNICATION_1(" style=\"text-align:center; font-family: 'Times New Roman', Times, serif; font-size: 12pt; display: block;\""),
        RES_PARAGRAPHS_1(" style=\"text-indent:1cm;padding:0 0 6px 0;margin:0; font-family: 'Times New Roman', Times, serif; font-size: 12pt;\""),
        RES_TITLES_1(" style=\"font-family: 'Times New Roman', Times, serif; font-weight: bold; font-size: 13pt; display: block;\""),
        RES_LISTS_1(" style=\"font-family: 'Times New Roman', Times, serif; font-size: 12pt;\""),
        RES_CONTAINERS_1(" style = \"margin-bottom: 14pt\""),
        RES_DIVIDERS_1(" style =\"margin-top: 3px\""),
        
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
        CL_NAME_2(""),
        CL_ADDRESS1_2(""),
        CL_ADDRESS2_2(""),
        CL_CITY_STATE_ZIP_2(""),
        CL_COMMUNICATION_2(""),
        CL_AD_2(""),
        CL_DATE_2(""),
        CL_CONTACT_NAME_2(""),
        CL_CONTACT_INFO_2(""),
        CL_SALUTATION_2(""),
        CL_CLOSING_2(""),
        CL_PARAGRAPH_2(""),
        CL_SIGNATURE_2(""),
        RES_NAME_2(""),
        RES_ADDRESS1_2(""),
        RES_ADDRESS2_2(""),
        RES_CITY_STATE_ZIP_2(""),
        RES_COMMUNICATION_2(""),
        RES_PARAGRAPHS_2(""),
        RES_TITLES_2(""),
        RES_LISTS_2(""),
        RES_CONTAINERS_2(""),
        RES_DIVIDERS_2(""),
        
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
        CL_NAME_3(""),
        CL_ADDRESS1_3(""),
        CL_ADDRESS2_3(""),
        CL_CITY_STATE_ZIP_3(""),
        CL_COMMUNICATION_3(""),
        CL_AD_3(""),
        CL_DATE_3(""),
        CL_CONTACT_NAME_3(""),
        CL_CONTACT_INFO_3(""),
        CL_SALUTATION_3(""),
        CL_CLOSING_3(""),
        CL_PARAGRAPH_3(""),
        CL_SIGNATURE_3(""),
        RES_NAME_3(""),
        RES_ADDRESS1_3(""),
        RES_ADDRESS2_3(""),
        RES_CITY_STATE_ZIP_3(""),
        RES_COMMUNICATION_3(""),
        RES_PARAGRAPHS_3(""),
        RES_TITLES_3(""),
        RES_LISTS_3(""),
        RES_CONTAINERS_3(""),
        RES_DIVIDERS_3(""),
        
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
        BC_WEB_4(""),
        CL_NAME_4(""),
        CL_ADDRESS1_4(""),
        CL_ADDRESS2_4(""),
        CL_CITY_STATE_ZIP_4(""),
        CL_COMMUNICATION_4(""),
        CL_AD_4(""),
        CL_DATE_4(""),
        CL_CONTACT_NAME_4(""),
        CL_CONTACT_INFO_4(""),
        CL_SALUTATION_4(""),
        CL_CLOSING_4(""),
        CL_PARAGRAPH_4(""),
        CL_SIGNATURE_4(""),
        RES_NAME_4(""),
        RES_ADDRESS1_4(""),
        RES_ADDRESS2_4(""),
        RES_CITY_STATE_ZIP_4(""),
        RES_COMMUNICATION_4(""),
        RES_PARAGRAPHS_4(""),
        RES_TITLES_4(""),
        RES_LISTS_4(""),
        RES_CONTAINERS_4(""),
        RES_DIVIDERS_4("");

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
