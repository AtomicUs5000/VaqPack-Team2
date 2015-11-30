/*-----------------------------------------------------------------------------*
 * VP_DatabaseManager.java
 * - Handles connection and queries to a database
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 1400
 *-----------------------------------------------------------------------------*/
package vaqpack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

public class VP_DatabaseManager {

    private final VP_GUIController controller; // temporary for testing
    private Connection con;
    private Statement stm;
    private ResultSet rts;
    private final String dbName;
    private String url,
            port,
            fullURL,
            adminUserName,
            adminPassword;

    /*------------------------------------------------------------------------*
     * VP_DatabaseManager()
     * - Constructor. Initialiazes the connection, statement, and result set.
     * No Parameters.
     *------------------------------------------------------------------------*/
    protected VP_DatabaseManager(VP_GUIController controller) {
        //-------- Initialization Start ----------\\
        // url, port, dbadmin user and pass kept here just for refernce. These values
        // may or not be used. These were the original values before the program
        // allowed the entry of different values.
        this.controller = controller; // temporary for testing
        dbName = "vaqpack_db";
        port = "3306";
        url = "localhost";
        fullURL = "jdbc:mysql://" + url + ":" + port + "/";
        adminUserName = "vpAdmin";
        adminPassword = "vpTeam2Pa$$";
        con = null;
        stm = null;
        rts = null;
        //-------- Initialization End ------------\\
    }

    /*------------------------------------------------------------------------*
     * checkDBRunning()
     * - Verifies that a MySQL database is running on the provided url and port.
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void checkDBRunning() throws SQLException {
        connect("");
        close();
    }

    /*------------------------------------------------------------------------*
     * checkSchema()
     * - Creates the vaqpack_db schema if necessary.
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void checkSchema() throws SQLException {
        //-------- Initialization Start ----------\\
        String sql = "CREATE DATABASE " + dbName;
        //-------- Initialization End ------------\\

        connect("");
        stm.executeUpdate(sql);
        close();
    }

    /*------------------------------------------------------------------------*
     * checkUserTable()
     * - Defines the SQL statement to create the 'user' table and then
     *   calls checkTable().
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void checkUserTable() throws SQLException {
        //-------- Initialization Start ----------\\
        String sql = "CREATE TABLE user ("
                + "  id int(10) unsigned NOT NULL AUTO_INCREMENT,"
                + "  email varchar(254) NOT NULL,"
                + "  password char(64) NOT NULL,"
                + "  access_level tinyint(1) unsigned NOT NULL DEFAULT 0,"
                + "  last_access datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                + "  PRIMARY KEY (id, email),"
                + "  UNIQUE KEY id_UNIQUE (id),"
                + "  UNIQUE KEY email_UNIQUE (email)"
                + ")";
        //-------- Initialization End ------------\\

        checkTable(sql);
    }

    /*------------------------------------------------------------------------*
     * checkAcessLevelTable()
     * - Defines the SQL statement to create the 'access_level' table and then
     *   calls checkTable().
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void checkAcessLevelTable() throws SQLException {
        //-------- Initialization Start ----------\\
        String sql = "CREATE TABLE access_level ("
                + "  id int(10) unsigned NOT NULL AUTO_INCREMENT,"
                + "  level tinyint(1) unsigned NOT NULL,"
                + "  change_credentials bit(1) NOT NULL DEFAULT 0,"
                + "  create_manager bit(1) NOT NULL DEFAULT 0,"
                + "  move_db bit(1) NOT NULL DEFAULT 0,"
                + "  PRIMARY KEY (id, level),"
                + "  UNIQUE KEY id_UNIQUE (id),"
                + "  UNIQUE KEY level_UNIQUE (level)"
                + ")";
        //-------- Initialization End ------------\\

        checkTable(sql);
    }

    /*------------------------------------------------------------------------*
     * checkRegisteringUserTable()
     * - Defines the SQL statement to create the 'registering_user' table and 
     *   then calls checkTable().
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void checkRegisteringUserTable() throws SQLException {
        //-------- Initialization Start ----------\\
        String sql = "CREATE TABLE registering_user ("
                + "  id int(10) unsigned NOT NULL AUTO_INCREMENT,"
                + "  email varchar(254) NOT NULL,"
                + "  password char(64) NOT NULL,"
                + "  access_level tinyint(1) unsigned NOT NULL DEFAULT 0,"
                + "  reg_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                + "  access_code varchar(16) NOT NULL,"
                + "  PRIMARY KEY (id, email),"
                + "  UNIQUE KEY id_UNIQUE (id),"
                + "  UNIQUE KEY email_UNIQUE (email)"
                + ")";
        //-------- Initialization End ------------\\

        checkTable(sql);
    }

    /*------------------------------------------------------------------------*
     * checkResetCodeTable()
     * - Defines the SQL statement to create the 'reset_code' table and 
     *   then calls checkTable().
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void checkResetCodeTable() throws SQLException {
        //-------- Initialization Start ----------\\
        String sql = "CREATE TABLE reset_code ("
                + "  id int(10) unsigned NOT NULL AUTO_INCREMENT,"
                + "  email varchar(254) NOT NULL,"
                + "  sent_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                + "  confirm_time datetime,"
                + "  access_code varchar(16) NOT NULL,"
                + "  PRIMARY KEY (id, email),"
                + "  UNIQUE KEY id_UNIQUE (id),"
                + "  UNIQUE KEY email_UNIQUE (email)"
                + ")";
        //-------- Initialization End ------------\\

        checkTable(sql);
    }

    /*------------------------------------------------------------------------*
     * checkBusinessCardTable()
     * - Defines the SQL statement to create the 'business_card' table and then
     *   calls checkTable().
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void checkBusinessCardTable() throws SQLException {
        //-------- Initialization Start ----------\\
        String sql = "CREATE TABLE business_card ("
                + "  id int(10) unsigned NOT NULL AUTO_INCREMENT,"
                + "  user_id int(10) unsigned NOT NULL,"
                + "  profession varchar(48) DEFAULT NULL,"
                + "  company_name varchar(48) DEFAULT NULL,"
                + "  company_slogan varchar(128) DEFAULT NULL,"
                + "  webpage varchar(48) DEFAULT NULL,"
                + "  theme tinyint(2) DEFAULT -1,"
                + "  PRIMARY KEY (id),"
                + "  UNIQUE KEY id_UNIQUE (id),"
                + "  UNIQUE KEY user_id_UNIQUE (user_id),"
                + "  CONSTRAINT BusUserID FOREIGN KEY (user_id) REFERENCES user (id)"
                + "  ON DELETE CASCADE ON UPDATE NO ACTION"
                + ")";
        //-------- Initialization End ------------\\

        checkTable(sql);
    }

    /*------------------------------------------------------------------------*
     * checkContactTable()
     * - Defines the SQL statement to create the 'contact' table and then
     *   calls checkTable().
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void checkContactTable() throws SQLException {
        //-------- Initialization Start ----------\\
        String sql = "CREATE TABLE contact ("
                + "  id int(10) unsigned NOT NULL AUTO_INCREMENT,"
                + "  user_id int(10) unsigned NOT NULL,"
                + "  name varchar(128) NOT NULL,"
                + "  email varchar(254) NOT NULL,"
                + "  PRIMARY KEY (id),"
                + "  UNIQUE KEY id_UNIQUE (id),"
                + "  KEY conUID_idx (user_id),"
                + "  CONSTRAINT conUID FOREIGN KEY (user_id) REFERENCES user (id)"
                + "  ON DELETE CASCADE ON UPDATE NO ACTION"
                + ")";
        //-------- Initialization End ------------\\

        checkTable(sql);
    }

    /*------------------------------------------------------------------------*
     * checkCoverLetterTable()
     * - Defines the SQL statement to create the 'cover_letter' table and then
     *   calls checkTable().
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void checkCoverLetterTable() throws SQLException {
        //-------- Initialization Start ----------\\
        String sql = "CREATE TABLE cover_letter ("
                + "  id int(10) unsigned NOT NULL AUTO_INCREMENT,"
                + "  user_id int(10) unsigned NOT NULL,"
                + "  adsource varchar(128) DEFAULT NULL,"
                + "  job_title varchar(128) DEFAULT NULL,"
                + "  reference_number varchar(128) DEFAULT NULL,"
                + "  date varchar(32) NOT NULL,"
                + "  contact_first_name varchar(45) DEFAULT NULL,"
                + "  contact_middle_name varchar(45) DEFAULT NULL,"
                + "  contact_last_name varchar(45) DEFAULT NULL,"
                + "  contact_title varchar(48) DEFAULT NULL,"
                + "  contact_company_name varchar(48) DEFAULT NULL,"
                + "  contact_address_line1 varchar(254) DEFAULT NULL,"
                + "  contact_address_line2 varchar(254) DEFAULT NULL,"
                + "  contact_city varchar(45) DEFAULT NULL,"
                + "  contact_state varchar(2) DEFAULT NULL,"
                + "  contact_zipcode varchar(10) DEFAULT NULL,"
                + "  salutation varchar(128) DEFAULT NULL,"
                + "  numb_paragraphs tinyint(1) unsigned DEFAULT 1,"
                + "  text longtext,"
                + "  closing varchar(128) DEFAULT NULL,"
                + "  theme tinyint(2) DEFAULT -1,"
                + "  PRIMARY KEY (id),"
                + "  UNIQUE KEY id_UNIQUE (id),"
                + "  UNIQUE KEY user_id_UNIQUE (id, user_id),"
                + "  CONSTRAINT CovUserID FOREIGN KEY (user_id) REFERENCES user (id)"
                + "  ON DELETE CASCADE ON UPDATE NO ACTION"
                + ")";
        //-------- Initialization End ------------\\

        checkTable(sql);
    }

    /*------------------------------------------------------------------------*
     * checkResumeTable()
     * - Defines the SQL statement to create the 'resume' table and then
     *   calls checkTable().
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void checkResumeTable() throws SQLException {
        //-------- Initialization Start ----------\\
        String sql = "CREATE TABLE resume ("
                + "  id int(10) unsigned NOT NULL AUTO_INCREMENT,"
                + "  user_id int(10) unsigned NOT NULL,"
                + "  objective text DEFAULT NULL,"
                + "  numb_education tinyint(1) unsigned DEFAULT 1,"
                + "  ed_names text DEFAULT NULL,"
                + "  ed_locs text DEFAULT NULL,"
                + "  ed_earned text DEFAULT NULL,"
                + "  ed_gpa text DEFAULT NULL,"
                + "  ed_start text DEFAULT NULL,"
                + "  ed_end text DEFAULT NULL,"
                + "  numb_experience tinyint(1) unsigned DEFAULT 1,"
                + "  ex_names text DEFAULT NULL,"
                + "  ex_locs text DEFAULT NULL,"
                + "  ex_positions text DEFAULT NULL,"
                + "  ex_start text DEFAULT NULL,"
                + "  ex_end text DEFAULT NULL,"
                + "  numb_achievements tinyint(1) unsigned DEFAULT 0,"
                + "  ac_names text DEFAULT NULL,"
                + "  ac_institutions text DEFAULT NULL,"
                + "  ac_dates text DEFAULT NULL,"
                + "  numb_community tinyint(1) unsigned DEFAULT 0,"
                + "  ev_names text DEFAULT NULL,"
                + "  ev_locs text DEFAULT NULL,"
                + "  ev_dates text DEFAULT NULL,"
                + "  numb_qualifications tinyint(1) unsigned DEFAULT 1,"
                + "  qualifications text DEFAULT NULL,"
                + "  numb_highlights tinyint(1) unsigned DEFAULT 1,"
                + "  highlights text DEFAULT NULL,"
                + "  numb_languages tinyint(1) unsigned DEFAULT 1,"
                + "  languages text DEFAULT NULL,"
                + "  numb_software tinyint(1) unsigned DEFAULT 1,"
                + "  software text DEFAULT NULL,"
                + "  numb_references tinyint(1) unsigned DEFAULT 0,"
                + "  ref_first_names text DEFAULT NULL,"
                + "  ref_middle_names text DEFAULT NULL,"
                + "  ref_last_names text DEFAULT NULL,"
                + "  ref_company text DEFAULT NULL,"
                + "  ref_phone text DEFAULT NULL,"
                + "  ref_email text DEFAULT NULL,"
                + "  theme tinyint(2) DEFAULT -1,"
                + "  PRIMARY KEY (id),"
                + "  UNIQUE KEY id_UNIQUE (id),"
                + "  UNIQUE KEY user_id_UNIQUE (user_id),"
                + "  CONSTRAINT ResUserID FOREIGN KEY (user_id) REFERENCES user (id)"
                + "  ON DELETE CASCADE ON UPDATE NO ACTION"
                + ")";
        //-------- Initialization End ------------\\

        checkTable(sql);
    }

    /*------------------------------------------------------------------------*
     * checkUserDataTable()
     * - Defines the SQL statement to create the 'user_data' table and then
     *   calls checkTable().
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void checkUserDataTable() throws SQLException {
        //-------- Initialization Start ----------\\
        String sql = "CREATE TABLE user_data ("
                + "  user_id int(10) unsigned NOT NULL,"
                + "  first_name varchar(45) NOT NULL,"
                + "  middle_name varchar(45) DEFAULT NULL,"
                + "  last_name varchar(45) NOT NULL,"
                + "  address_line1 varchar(254) NOT NULL,"
                + "  address_line2 varchar(254) DEFAULT NULL,"
                + "  city varchar(45) NOT NULL,"
                + "  state varchar(2) NOT NULL,"
                + "  zipcode varchar(10) NOT NULL,"
                + "  phone varchar(13) NOT NULL,"
                + "  cell varchar(13) DEFAULT NULL,"
                + "  email varchar(254) DEFAULT NULL,"
                + "  PRIMARY KEY (user_id),"
                + "  UNIQUE KEY user_id_UNIQUE (user_id),"
                + "  CONSTRAINT userID FOREIGN KEY (user_id) REFERENCES user (id)"
                + "  ON DELETE CASCADE ON UPDATE NO ACTION"
                + ")";
        //-------- Initialization End ------------\\

        checkTable(sql);
    }

    /*------------------------------------------------------------------------*
     * checkCustomThemeTable()
     * - Defines the SQL statement to create the 'custom_theme' table and then
     *   calls checkTable().
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void checkCustomThemeTable() throws SQLException {
        //-------- Initialization Start ----------\\
        String sql = "CREATE TABLE custom_theme ("
                + "  id int(10) unsigned NOT NULL AUTO_INCREMENT,"
                + "  user_id int(10) unsigned NOT NULL,"
                + "  user_theme_id tinyint(2) unsigned NOT NULL,"
                + "  PRIMARY KEY (id,user_id),"
                + "  UNIQUE KEY id_UNIQUE (id),"
                + "  UNIQUE KEY user_id_UNIQUE (user_id),"
                + "  CONSTRAINT CTUserID FOREIGN KEY (user_id) REFERENCES user (id)"
                + "  ON DELETE CASCADE ON UPDATE NO ACTION"
                + ")";
        //-------- Initialization End ------------\\

        checkTable(sql);
    }

    /*------------------------------------------------------------------------*
     * checkBusinessCardPDFTable()
     * - Defines the SQL statement to create the 'business_card_pdf' table and 
     *   then calls checkTable().
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void checkBusinessCardPDFTable() throws SQLException {
        //-------- Initialization Start ----------\\
        String sql = "CREATE TABLE business_card_pdf ("
                + "  user_id int(10) unsigned NOT NULL,"
                + "  pdf blob,"
                + "  PRIMARY KEY (user_id),"
                + "  UNIQUE KEY user_id_UNIQUE (user_id),"
                + "  CONSTRAINT userIDbcpdf FOREIGN KEY (user_id) REFERENCES user (id)"
                + "  ON DELETE CASCADE ON UPDATE NO ACTION"
                + ")";
        //-------- Initialization End ------------\\

        checkTable(sql);
    }

    /*------------------------------------------------------------------------*
     * checkCoverLetterPDFTable()
     * - Defines the SQL statement to create the 'cover_letter_pdf' table and
     *   then calls checkTable().
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void checkCoverLetterPDFTable() throws SQLException {
        //-------- Initialization Start ----------\\
        String sql = "CREATE TABLE cover_letter_pdf ("
                + "  id int(10) unsigned NOT NULL AUTO_INCREMENT,"
                + "  cover_letter_id int(10) unsigned NOT NULL,"
                + "  pdf blob,"
                + "  PRIMARY KEY (id,cover_letter_id),"
                + "  UNIQUE KEY id_UNIQUE (id),"
                + "  UNIQUE KEY cover_letter_id_UNIQUE (cover_letter_id),"
                + "  CONSTRAINT covPID FOREIGN KEY (cover_letter_id) "
                + "  REFERENCES cover_letter (id) ON DELETE CASCADE ON UPDATE NO ACTION"
                + ")";
        //-------- Initialization End ------------\\

        checkTable(sql);
    }

    /*------------------------------------------------------------------------*
     * checkResPDFTable()
     * - Defines the SQL statement to create the 'resume_pdf' table and then
     *   calls checkTable().
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void checkResPDFTable() throws SQLException {
        //-------- Initialization Start ----------\\
        String sql = "CREATE TABLE resume_pdf ("
                + "  user_id int(10) unsigned NOT NULL,"
                + "  pdf blob,"
                + "  PRIMARY KEY (user_id),"
                + "  UNIQUE KEY user_id_UNIQUE (user_id),"
                + "  CONSTRAINT userIDrespdf FOREIGN KEY (user_id) REFERENCES user (id)"
                + "  ON DELETE CASCADE ON UPDATE NO ACTION"
                + ")";
        //-------- Initialization End ------------\\

        checkTable(sql);
    }

    /*------------------------------------------------------------------------*
     * checkResHTMLTable()
     * - Defines the SQL statement to create the 'resume_html' table and then
     *   calls checkTable().
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void checkResHTMLTable() throws SQLException {
        //-------- Initialization Start ----------\\
        String sql = "CREATE TABLE resume_html ("
                + "  user_id int(10) unsigned NOT NULL,"
                + "  pdf blob,"
                + "  PRIMARY KEY (user_id),"
                + "  UNIQUE KEY user_id_UNIQUE (user_id),"
                + "  CONSTRAINT userIDresH FOREIGN KEY (user_id) REFERENCES user (id)"
                + "  ON DELETE CASCADE ON UPDATE NO ACTION"
                + ")";
        //-------- Initialization End ------------\\

        checkTable(sql);
    }

    /*------------------------------------------------------------------------*
     * findAdminUser()
     * - Attempts to locate at least one VaqPack admin user account.
     * - Also cleans the dead registration codes.
     * - Also cleans the dead reset codes.
     * - No parameters.
     * - Returns a boolean value indicating whether or not a VaqPack admin user
     *   has been created and exists in the database user table. This is
     *   necessary in case the user cancels or the program exists in the middle
     *   of first-run initialization.
     *------------------------------------------------------------------------*/
    protected boolean findAdminUser() throws SQLException {
        //-------- Initialization Start ----------\\
        boolean adminExists = false;
        Date dt = new java.util.Date();
        String sql = "SELECT * FROM user WHERE access_level = 1";
        Timestamp regTime;
        int remId;
        long difference;
        //-------- Initialization End ------------\\

        connect(dbName);
        rts = stm.executeQuery(sql);
        if (rts.next()) {
            adminExists = true;
        } else {
            sql = "SELECT * FROM registering_user WHERE access_level = 1";
            rts = stm.executeQuery(sql);
            if (rts.next()) {
                regTime = rts.getTimestamp("reg_time");
                difference = dt.getTime() - regTime.getTime();
                if (difference < 3600000) {
                    adminExists = true;
                } else {
                    remId = rts.getInt("id");
                    sql = "DELETE FROM registering_user WHERE id = " + remId;
                    stm.executeUpdate(sql);
                }
            }
            sql = "SELECT * FROM registering_user WHERE access_level = 2";
            rts = stm.executeQuery(sql);
            while (rts.next()) {
                regTime = rts.getTimestamp("reg_time");
                difference = dt.getTime() - regTime.getTime();
                if (difference >= 3600000) {
                    remId = rts.getInt("id");
                    sql = "DELETE FROM registering_user WHERE id = " + remId;
                    stm.executeUpdate(sql);
                }
            }
            sql = "SELECT * FROM registering_user WHERE access_level = 0";
            rts = stm.executeQuery(sql);
            while (rts.next()) {
                regTime = rts.getTimestamp("reg_time");
                difference = dt.getTime() - regTime.getTime();
                if (difference >= 3600000) {
                    remId = rts.getInt("id");
                    sql = "DELETE FROM registering_user WHERE id = " + remId;
                    stm.executeUpdate(sql);
                }
            }
        }
        // clean reset_code table
        sql = "SELECT * FROM reset_code";
        rts = stm.executeQuery(sql);
        while (rts.next()) {
            regTime = rts.getTimestamp("sent_time");
            difference = dt.getTime() - regTime.getTime();
            if (difference > 86400000) {
                remId = rts.getInt("id");
                sql = "DELETE FROM reset_code WHERE id = " + remId;
                stm.executeUpdate(sql);
            }
        }
        close();
        return adminExists;
    }

    /*------------------------------------------------------------------------*
     * createVaqPackAdmin()
     * - Inserts an admin VaqPack user.
     * - Parameters cred is a string array of the database admin user
     *   credentials and the VaqPack admin user credentials.
     * - Returns a boolean value indicating if that the database admin user is
     *   valid, allowing the creation of this VP admin user account.
     *------------------------------------------------------------------------*/
    protected boolean createVaqPackAdmin(String[] cred, String code, String hashed)
            throws SQLException {
        //-------- Initialization Start ----------\\
        boolean adminCredChecked = false;
        String sql;
        //-------- Initialization End ------------\\

        if (cred[0].equals(adminUserName) && cred[1].equals(adminPassword)) {
            adminCredChecked = true;
            connect(dbName);
            sql = "INSERT INTO registering_user (email, password, access_level, access_code)"
                    + " VALUES ('" + cred[2] + "', '" + hashed + "', 1, '" + code + "')";
            stm.executeUpdate(sql);
        }
        close();
        return adminCredChecked;
    }

    /*------------------------------------------------------------------------*
     * buildAccessLevels()
     * - Verifies the integrity of the 'access_level' table. If there are not
     *   exactly 2 user levels in the table, the table gets truncated and the
     *   user access levels are recreated.
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void buildAccessLevels() throws SQLException {
        //-------- Initialization Start ----------\\
        int count;
        String sql = "SELECT COUNT(*) FROM access_level";
        //-------- Initialization End ------------\\

        connect(dbName);
        rts = stm.executeQuery(sql);
        rts.next();
        count = rts.getInt(1);
        if (count != 3) {
            if (count != 0) {
                sql = "TRUNCATE access_level";
                stm.executeUpdate(sql);
            }
            // regular user
            sql = "INSERT INTO access_level (level, change_credentials, create_manager, move_db)"
                    + " VALUES (0, 0, 0, 0)";
            stm.executeUpdate(sql);
            // admin user, can create managers
            sql = "INSERT INTO access_level (level, change_credentials, create_manager, move_db)"
                    + " VALUES (1, 1, 1, 1);";
            // manager user
            sql = "INSERT INTO access_level (level, change_credentials, create_manager, move_db)"
                    + " VALUES (2, 1, 0, 1);";
            stm.executeUpdate(sql);
        }
        close();
    }

    protected void loadCovLetData(VP_User thisUser, int clID) throws SQLException {
        int userID = thisUser.getUserID();
        String sql = "SELECT * FROM cover_letter WHERE user_id = " + userID + " AND id = " + clID;
        connect(dbName);
        rts = stm.executeQuery(sql);
        if (rts.next()) {
            thisUser.getCovlet().setId(rts.getInt("id"));
            thisUser.getCovlet().getAdSource().setValue(rts.getString("adsource"));
            thisUser.getCovlet().getAdJobTitle().setValue(rts.getString("job_title"));
            thisUser.getCovlet().getAdRefNumber().setValue(rts.getString("reference_number"));
            thisUser.getCovlet().getDate().setValue(rts.getString("date"));
            thisUser.getCovlet().getContactFirstName().setValue(rts.getString("contact_first_name"));
            thisUser.getCovlet().getContactMiddleName().setValue(rts.getString("contact_middle_name"));
            thisUser.getCovlet().getContactLastName().setValue(rts.getString("contact_last_name"));
            thisUser.getCovlet().getContactTitle().setValue(rts.getString("contact_title"));
            thisUser.getCovlet().getContactCompany().setValue(rts.getString("contact_company_name"));
            thisUser.getCovlet().getContactAddress1().setValue(rts.getString("contact_address_line1"));
            thisUser.getCovlet().getContactAddress2().setValue(rts.getString("contact_address_line2"));
            thisUser.getCovlet().getContactCity().setValue(rts.getString("contact_city"));
            thisUser.getCovlet().getContactState().setValue(rts.getString("contact_state"));
            thisUser.getCovlet().getContactZip().setValue(rts.getString("contact_zipcode"));
            thisUser.getCovlet().getSalutation().setValue(rts.getString("salutation"));
            thisUser.getCovlet().setNumbParagraphs(rts.getInt("numb_paragraphs"));
            String[] paraText = rts.getString("text").split("\\@\\#\\$");
            for (int i = 0; i < thisUser.getCovlet().getNumbParagraphs(); i++) {
                thisUser.getCovlet().getParagraphs().get(i).setValue(paraText[i]);
            }
            thisUser.getCovlet().getClosing().setValue(rts.getString("closing"));
            thisUser.getCovlet().setThemeId(rts.getInt("theme"));
            thisUser.getCovlet().save();
        }
        close();
    }

    /*------------------------------------------------------------------------*
     * attemptUserLogin()
     * - May or may not log in the user, depending on where the user is found in
     *   the database.
     * - Parameter cred is a string array of user credentials.
     * - Returns -1 if user does not exist, returns the user's access level if
     *   the user exists in the user table, and returns -2 if the user is found
     *   in registering_user table.
     *------------------------------------------------------------------------*/
    protected int attemptUserLogin(String[] cred, VP_User thisUser)
            throws SQLException {
        //-------- Initialization Start ----------\\
        int loginStatus = -1,
                userID = -1,
                id;
        Timestamp regTime;
        long newMS;
        Date dt;
        String sql = "SELECT * FROM user WHERE email = '" + cred[0] + "' AND "
                + "password = '" + cred[1] + "'", temp;
        VP_BusinessCard bc = thisUser.getBcard();
        VP_CoverLetter cl = thisUser.getCovlet();
        VP_Resume res = thisUser.getResume();
        //-------- Initialization End ------------\\
        connect(dbName);
        rts = stm.executeQuery(sql);
        if (rts.next()) {
            userID = rts.getInt("id");
            thisUser.setUserID(userID);
            loginStatus = rts.getInt("access_level");
            dt = new java.util.Date();
            sql = "UPDATE user SET last_access = '" + new Timestamp(dt.getTime()) + "' WHERE id = " + userID;
            stm.executeUpdate(sql);
            thisUser.getEmail().setValue(cred[0]);
            thisUser.setAccessLevel(loginStatus);
            sql = "SELECT * FROM user_data WHERE user_id = " + userID;
            rts = stm.executeQuery(sql);
            if (rts.next()) {
                thisUser.getFirstName().setValue(rts.getString("first_name"));
                thisUser.getMiddleName().setValue(rts.getString("middle_name"));
                thisUser.getLastName().setValue(rts.getString("last_name"));
                thisUser.getAddress1().setValue(rts.getString("address_line1"));
                thisUser.getAddress2().setValue(rts.getString("address_line2"));
                thisUser.getCity().setValue(rts.getString("city"));
                thisUser.getState().setValue(rts.getString("state"));
                thisUser.getZip().setValue(rts.getString("zipcode"));
                thisUser.getPhone().setValue(rts.getString("phone"));
                thisUser.getCell().setValue(rts.getString("cell"));
                thisUser.getDocEmail().setValue(rts.getString("email"));
                thisUser.save();
                sql = "SELECT * FROM business_card WHERE user_id = " + userID;
                rts = stm.executeQuery(sql);
                if (rts.next()) {
                    bc.getProfession().setValue(rts.getString("profession"));
                    bc.getCompanyName().setValue(rts.getString("company_name"));
                    bc.getCompanySlogan().setValue(rts.getString("company_slogan"));
                    bc.getWebPage().setValue(rts.getString("webpage"));
                    bc.setThemeId(rts.getInt("theme"));
                    bc.save();
                }
                sql = "SELECT * FROM cover_letter WHERE user_id = " + userID;
                rts = stm.executeQuery(sql);
                if (rts.next()) {
                    thisUser.getCoverLetterIds()[0] = rts.getInt("id");
                    cl.setId(rts.getInt("id"));
                    cl.getAdSource().setValue(rts.getString("adsource"));
                    cl.getAdJobTitle().setValue(rts.getString("job_title"));
                    cl.getAdRefNumber().setValue(rts.getString("reference_number"));
                    cl.getDate().setValue(rts.getString("date"));
                    cl.getContactFirstName().setValue(rts.getString("contact_first_name"));
                    cl.getContactMiddleName().setValue(rts.getString("contact_middle_name"));
                    cl.getContactLastName().setValue(rts.getString("contact_last_name"));
                    cl.getContactTitle().setValue(rts.getString("contact_title"));
                    cl.getContactCompany().setValue(rts.getString("contact_company_name"));
                    cl.getContactAddress1().setValue(rts.getString("contact_address_line1"));
                    cl.getContactAddress2().setValue(rts.getString("contact_address_line2"));
                    cl.getContactCity().setValue(rts.getString("contact_city"));
                    cl.getContactState().setValue(rts.getString("contact_state"));
                    cl.getContactZip().setValue(rts.getString("contact_zipcode"));
                    cl.getSalutation().setValue(rts.getString("salutation"));
                    cl.setNumbParagraphs(rts.getInt("numb_paragraphs"));
                    String[] paraText = rts.getString("text").split("\\@\\#\\$");
                    for (int i = 0; i < cl.getNumbParagraphs(); i++) {
                        cl.getParagraphs().get(i).setValue(paraText[i]);
                    }
                    cl.getClosing().setValue(rts.getString("closing"));
                    cl.setThemeId(rts.getInt("theme"));
                    cl.save();
                    if (rts.next()) {
                        thisUser.getCoverLetterIds()[1] = rts.getInt("id");
                        if (rts.next()) {
                            thisUser.getCoverLetterIds()[2] = rts.getInt("id");
                        }
                    }
                }
                sql = "SELECT * FROM resume WHERE user_id = " + userID;
                rts = stm.executeQuery(sql);
                if (rts.next()) {
                    res.getObjective().setValue(rts.getString("objective"));
                    res.setNumbEducation(rts.getInt("numb_education"));
                    res.setNumbExperience(rts.getInt("numb_experience"));
                    res.setNumbAchievements(rts.getInt("numb_achievements"));
                    res.setNumbCommunity(rts.getInt("numb_community"));
                    res.setNumbQualification(rts.getInt("numb_qualifications"));
                    res.setNumbHighlights(rts.getInt("numb_highlights"));
                    res.setNumbLanguages(rts.getInt("numb_languages"));
                    res.setNumbSoftware(rts.getInt("numb_software"));
                    res.setNumbReferences(rts.getInt("numb_references"));
                    String[] paraText0 = new String[9];
                    String[] paraText1 = new String[9];
                    String[] paraText2 = new String[9];
                    String[] paraText3 = new String[9];
                    String[] paraText4 = new String[9];
                    String[] paraText5 = new String[9];
                    // education
                    if (rts.getString("ed_names") != null) {
                        paraText0 = rts.getString("ed_names").split("\\@\\#\\$");
                    }
                    if (rts.getString("ed_locs") != null) {
                        paraText1 = rts.getString("ed_locs").split("\\@\\#\\$");
                    }
                    if (rts.getString("ed_earned") != null) {
                        paraText2 = rts.getString("ed_earned").split("\\@\\#\\$");
                    }
                    if (rts.getString("ed_gpa") != null) {
                        paraText3 = rts.getString("ed_gpa").split("\\@\\#\\$");
                    }
                    if (rts.getString("ed_start") != null) {
                        paraText4 = rts.getString("ed_start").split("\\@\\#\\$");
                    }
                    if (rts.getString("ed_end") != null) {
                        paraText5 = rts.getString("ed_end").split("\\@\\#\\$");
                    }
                    for (int i = 0; i < res.getNumbEducation(); i++) {
                        res.getEducation().get(i).get(0).setValue(paraText0[i]);
                        res.getEducation().get(i).get(1).setValue(paraText1[i]);
                        res.getEducation().get(i).get(2).setValue(paraText2[i]);
                        res.getEducation().get(i).get(3).setValue(paraText3[i]);
                        res.getEducation().get(i).get(4).setValue(paraText4[i]);
                        res.getEducation().get(i).get(5).setValue(paraText5[i]);
                    }
                    paraText0 = new String[9];
                    paraText1 = new String[9];
                    paraText2 = new String[9];
                    paraText3 = new String[9];
                    paraText4 = new String[9];
                    paraText5 = new String[9];
                    // work experience
                    if (rts.getString("ex_names") != null) {
                        paraText0 = rts.getString("ex_names").split("\\@\\#\\$");
                    }
                    if (rts.getString("ex_locs") != null) {
                        paraText1 = rts.getString("ex_locs").split("\\@\\#\\$");
                    }
                    if (rts.getString("ex_positions") != null) {
                        paraText2 = rts.getString("ex_positions").split("\\@\\#\\$");
                    }
                    if (rts.getString("ex_start") != null) {
                        paraText3 = rts.getString("ex_start").split("\\@\\#\\$");
                    }
                    if (rts.getString("ex_end") != null) {
                        paraText4 = rts.getString("ex_end").split("\\@\\#\\$");
                    }
                    for (int i = 0; i < res.getNumbExperience(); i++) {
                        res.getExperience().get(i).get(0).setValue(paraText0[i]);
                        res.getExperience().get(i).get(1).setValue(paraText1[i]);
                        res.getExperience().get(i).get(2).setValue(paraText2[i]);
                        res.getExperience().get(i).get(3).setValue(paraText3[i]);
                        res.getExperience().get(i).get(4).setValue(paraText4[i]);
                    }
                    paraText0 = new String[9];
                    paraText1 = new String[9];
                    paraText2 = new String[9];
                    paraText3 = new String[9];
                    paraText4 = new String[9];
                    // achievements and awards
                    if (rts.getString("ac_names") != null) {
                        paraText0 = rts.getString("ac_names").split("\\@\\#\\$");
                    }
                    if (rts.getString("ac_institutions") != null) {
                        paraText1 = rts.getString("ac_institutions").split("\\@\\#\\$");
                    }
                    if (rts.getString("ac_dates") != null) {
                        paraText2 = rts.getString("ac_dates").split("\\@\\#\\$");
                    }
                    for (int i = 0; i < res.getNumbAchievements(); i++) {
                        res.getAchievements().get(i).get(0).setValue(paraText0[i]);
                        res.getAchievements().get(i).get(1).setValue(paraText1[i]);
                        res.getAchievements().get(i).get(2).setValue(paraText2[i]);
                    }
                    paraText0 = new String[9];
                    paraText1 = new String[9];
                    paraText2 = new String[9];
                    // community services or volunteer work
                    if (rts.getString("ev_names") != null) {
                        paraText0 = rts.getString("ev_names").split("\\@\\#\\$");
                    }
                    if (rts.getString("ev_locs") != null) {
                        paraText1 = rts.getString("ev_locs").split("\\@\\#\\$");
                    }
                    if (rts.getString("ev_dates") != null) {
                        paraText2 = rts.getString("ev_dates").split("\\@\\#\\$");
                    }
                    for (int i = 0; i < res.getNumbCommunity(); i++) {
                        res.getCommunity().get(i).get(0).setValue(paraText0[i]);
                        res.getCommunity().get(i).get(1).setValue(paraText1[i]);
                        res.getCommunity().get(i).get(2).setValue(paraText2[i]);
                    }
                    paraText0 = new String[9];
                    paraText1 = new String[9];
                    paraText2 = new String[9];
                    //qualifications
                    if (rts.getString("qualifications") != null) {
                        paraText0 = rts.getString("qualifications").split("\\@\\#\\$");
                    }
                    for (int i = 0; i < res.getNumbQualification(); i++) {
                        res.getQualifications().get(i).setValue(paraText0[i]);
                    }
                    paraText0 = new String[9];
                    // highlights
                    if (rts.getString("highlights") != null) {
                        paraText0 = rts.getString("highlights").split("\\@\\#\\$");
                    }
                    for (int i = 0; i < res.getNumbHighlights(); i++) {
                        res.getHighlights().get(i).setValue(paraText0[i]);
                    }
                    paraText0 = new String[9];
                    // languages
                    if (rts.getString("languages") != null) {
                        paraText0 = rts.getString("languages").split("\\@\\#\\$");
                    }
                    for (int i = 0; i < res.getNumbLanguages(); i++) {
                        res.getLanguages().get(i).setValue(paraText0[i]);
                    }
                    paraText0 = new String[9];
                    // software
                    if (rts.getString("software") != null) {
                        paraText0 = rts.getString("software").split("\\@\\#\\$");
                    }
                    for (int i = 0; i < res.getNumbSoftware(); i++) {
                        res.getSoftware().get(i).setValue(paraText0[i]);
                    }
                    paraText0 = new String[9];
                    // references
                    if (rts.getString("ref_first_names") != null) {
                        paraText0 = rts.getString("ref_first_names").split("\\@\\#\\$");
                    }
                    if (rts.getString("ref_middle_names") != null) {
                        paraText1 = rts.getString("ref_middle_names").split("\\@\\#\\$");
                    }
                    if (rts.getString("ref_last_names") != null) {
                        paraText2 = rts.getString("ref_last_names").split("\\@\\#\\$");
                    }
                    if (rts.getString("ref_company") != null) {
                        paraText3 = rts.getString("ref_company").split("\\@\\#\\$");
                    }
                    if (rts.getString("ref_phone") != null) {
                        paraText4 = rts.getString("ref_phone").split("\\@\\#\\$");
                    }
                    if (rts.getString("ref_email") != null) {
                        paraText5 = rts.getString("ref_email").split("\\@\\#\\$");
                    }
                    for (int i = 0; i < res.getNumbReferences(); i++) {
                        res.getReferences().get(i).get(0).setValue(paraText0[i]);
                        res.getReferences().get(i).get(1).setValue(paraText1[i]);
                        res.getReferences().get(i).get(2).setValue(paraText2[i]);
                        res.getReferences().get(i).get(3).setValue(paraText3[i]);
                        res.getReferences().get(i).get(4).setValue(paraText4[i]);
                        res.getReferences().get(i).get(5).setValue(paraText5[i]);
                    }
                    res.setThemeId(rts.getInt("theme"));
                    res.save();
                }
            }

        } else {
            sql = "SELECT * FROM registering_user WHERE email = '" + cred[0] + "' AND "
                    + "password = '" + cred[1] + "'";
            rts = stm.executeQuery(sql);
            if (rts.next()) {
                loginStatus = -2;
                // extend the lifetime of the registration code by five minutes
                id = rts.getInt("id");
                regTime = rts.getTimestamp("reg_time");
                newMS = regTime.getTime() + 300000;
                regTime = new Timestamp(newMS);
                sql = "UPDATE registering_user SET reg_time = '" + regTime + "' WHERE id = " + id;
                stm.executeUpdate(sql);
            }
        }
        close();
        return loginStatus;
    }

    /*------------------------------------------------------------------------*
     * verifyUserAccessCode()
     * - May or may not complete the registration of a user. This is determined 
     *   by whether or not the access code and user credentials match up. If
     *   everything matches, the user is moved from the registering_user table
     *   to the user table.
     * - Parameter cred is a string array of user credentials.
     * - Returns a boolean value indicating whether or not the verification of 
     *   user's registration was successful.
     *------------------------------------------------------------------------*/
    protected boolean verifyUserAccessCode(String[] cred)
            throws SQLException {
        //-------- Initialization Start ----------\\
        boolean success = false;
        int remId,
                remAccessLevel;
        String sql = "SELECT * FROM registering_user WHERE email = '" + cred[0] + "' AND "
                + "password = '" + cred[1] + "' AND access_code = '" + cred[2] + "'";
        //-------- Initialization End ------------\\

        connect(dbName);
        rts = stm.executeQuery(sql);
        if (rts.next()) {
            success = true;
            remId = rts.getInt("id");
            remAccessLevel = rts.getInt("access_level");
            sql = "DELETE FROM registering_user WHERE id = " + remId;
            stm.executeUpdate(sql);
            sql = "INSERT INTO user (email, password, access_level)"
                    + " VALUES ('" + cred[0] + "', '" + cred[1] + "', " + remAccessLevel + ")";
            stm.executeUpdate(sql);
        }
        close();
        return success;
    }

    /*------------------------------------------------------------------------*
     * resetPassword()
     * - May or may not reset a user's password. This is determined by the
     *   matching of credentials and acces code, and only occurs if the code has
     *   not expired. Users cannot reset a password if they already have within
     *   a 24-hour period, but this is determined in a separate function before
     *   function can be called. However, if the reset can happen, the reset
     *   time is stored to prevent resetting again within the next 24 hours.
     * - Parameter cred is a string array of user credentials.
     * - Returns a value of 0 if credentials do not match, returns a 1 if the
     *   credentials match but the code has expired, and returns a 2 if the
     *   password reset was a success.
     *------------------------------------------------------------------------*/
    protected int resetPassword(String[] cred)
            throws SQLException {
        //-------- Initialization Start ----------\\
        int resetStatus = 0,
                remID;
        long difference;
        Timestamp sentTime;
        Date dt;
        String sql = "SELECT * FROM reset_code WHERE email = '" + cred[0]
                + "' AND access_code = '" + cred[3] + "'";
        //-------- Initialization End ------------\\

        connect(dbName);
        rts = stm.executeQuery(sql);
        if (rts.next()) {
            resetStatus = 1;
            dt = new java.util.Date();
            remID = rts.getInt("id");
            sentTime = rts.getTimestamp("sent_time");
            difference = dt.getTime() - sentTime.getTime();
            if (difference < 3600000) {
                sql = "UPDATE reset_code SET confirm_time = '" + new Timestamp(dt.getTime()) + "' WHERE id = " + remID;
                stm.executeUpdate(sql);
                sql = "UPDATE user SET password = '" + cred[1] + "' WHERE email = '" + cred[0] + "'";
                stm.executeUpdate(sql);
                resetStatus = 2;
            }
        }
        close();
        return resetStatus;
    }

    /*------------------------------------------------------------------------*
     * findUserOrRegUserForReset()
     * - This function is called when a user submits the email in the reset
     *   password page. This first step verifies that the email exists in the
     *   system. If it does, a check is done to see if the user has reset the 
     *   password already within the past 24 hours.
     * - Parameter email is the string user email.
     * - Parameter code is the code that is stored in the database and sent to 
     *   user via email.
     * - Returns a value of 0 if the user does not exist, returns a 1 if the
     *   user exists but has already reset within the past 24 hours, and returns
     *   a 2 if resetting is allowed and a new code is stored.
     *------------------------------------------------------------------------*/
    protected int findUserOrRegUserForReset(String email, String code)
            throws SQLException {
        //-------- Initialization Start ----------\\
        Timestamp sentTime,
                confirmTime;
        Date dt;
        int userStatus = 0,
                remID;
        long difference;
        String sql = "SELECT * FROM user WHERE email = '" + email + "'";
        //-------- Initialization End ------------\\

        connect(dbName);
        rts = stm.executeQuery(sql);
        if (rts.next()) {
            userStatus = 2;
        }
        sql = "SELECT * FROM reset_code WHERE email = '" + email + "'";
        rts = stm.executeQuery(sql);
        if (rts.next()) {
            remID = rts.getInt("id");
            if (rts.getDate("confirm_time") == null) {
                sql = "DELETE FROM reset_code WHERE id = " + remID;
                stm.executeUpdate(sql);
            } else {
                sentTime = rts.getTimestamp("sent_time");
                confirmTime = rts.getTimestamp("confirm_time");
                difference = confirmTime.getTime() - sentTime.getTime();
                if (difference < 86400000) {
                    userStatus = 1;
                } else {
                    sql = "DELETE FROM reset_code WHERE id = " + remID;
                    stm.executeUpdate(sql);
                }
            }
        }
        if (userStatus == 2) {
            dt = new java.util.Date();
            sql = "INSERT INTO reset_code (email, access_code, sent_time) "
                    + "VALUES ('" + email + "', '" + code + "', '" + new Timestamp(dt.getTime()) + "')";
            stm.executeUpdate(sql);
        }
        close();
        return userStatus;
    }

    /*------------------------------------------------------------------------*
     * resendUserAccessCode()
     * - This function stores a brand new registration code for a user if the 
     *   credentials match. This is typically called for when a user does not 
     *   receive the registration email or failed to enter in the code before
     *   its expiration time.
     * - Parameter cred is the string array of user credentials.
     * - Parameter code is the registration code that the user has entered.
     * - Returns a boolean value indicating whether the user exists.
     *------------------------------------------------------------------------*/
    protected boolean resendUserAccessCode(String[] cred, String code)
            throws SQLException {
        //-------- Initialization Start ----------\\
        boolean userExists = false;
        String sql = "SELECT * FROM registering_user WHERE email = '" + cred[0] + "' AND "
                + "password = '" + cred[1] + "'";
        int id;
        Date dt;
        //-------- Initialization End ------------\\

        connect(dbName);
        rts = stm.executeQuery(sql);
        if (rts.next()) {
            userExists = true;
            dt = new java.util.Date();
            id = rts.getInt("id");
            sql = "UPDATE registering_user SET reg_time = '" + new Timestamp(dt.getTime()) + "', "
                    + "access_code = '" + code + "' WHERE id = " + id;
            stm.executeUpdate(sql);
        }
        close();
        return userExists;
    }

    /*------------------------------------------------------------------------*
     * registerUser()
     * - This function stores a registration code for a new user.
     * - Parameter cred is the string array of user credentials.
     * - Parameter code is the registration code that the user has entered.
     * - Returns an integer value indicating whether the user is new or not.
     *------------------------------------------------------------------------*/
    protected int registerUser(String[] cred, String code)
            throws SQLException {
        //-------- Initialization Start ----------\\
        int registerStatus = 1;
        String sql = "SELECT * FROM user WHERE email = '" + cred[0] + "'";
        Date dt;
        //-------- Initialization End ------------\\

        connect(dbName);
        rts = stm.executeQuery(sql);
        if (!rts.next()) {
            sql = "SELECT * FROM registering_user WHERE email = '" + cred[0] + "'";
            rts = stm.executeQuery(sql);
            if (rts.next()) {
                registerStatus = 0;
            } else {
                registerStatus = 2;
                dt = new java.util.Date();
                sql = "INSERT INTO registering_user (email, password, access_code)"
                        + " VALUES ('" + cred[0] + "', '" + cred[1] + "', '" + code + "')";
                stm.executeUpdate(sql);
            }
        }
        close();
        return registerStatus;
    }

    /*------------------------------------------------------------------------*
     * storeUserData()
     * - This function stores user personal information.
     * - Parameter thisUser is the currently logged in user.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void storeUserData(VP_User thisUser) throws SQLException {
        //-------- Initialization Start ----------\\
        int userID = thisUser.getUserID();
        String sql = "SELECT * FROM user_data WHERE user_id = " + userID;
        //-------- Initialization End ------------\\

        connect(dbName);
        rts = stm.executeQuery(sql);
        if (rts.next()) {
            sql = "UPDATE user_data SET "
                    + "first_name = '" + thisUser.getFirstName().getValueSafe() + "', "
                    + "middle_name = '" + thisUser.getMiddleName().getValueSafe() + "', "
                    + "last_name = '" + thisUser.getLastName().getValueSafe() + "', "
                    + "address_line1 = '" + thisUser.getAddress1().getValueSafe() + "', "
                    + "address_line2 = '" + thisUser.getAddress2().getValueSafe() + "', "
                    + "city = '" + thisUser.getCity().getValueSafe() + "', "
                    + "state = '" + thisUser.getState().getValueSafe() + "', "
                    + "zipcode = '" + thisUser.getZip().getValueSafe() + "', "
                    + "phone = '" + thisUser.getPhone().getValueSafe() + "', "
                    + "cell = '" + thisUser.getCell().getValueSafe() + "', "
                    + "email = '" + thisUser.getDocEmail().getValueSafe() + "' "
                    + "WHERE user_id = " + userID;
        } else {
            sql = "INSERT INTO user_data (user_id, first_name, middle_name, last_name,  "
                    + "address_line1, address_line2, city, state, zipcode, phone, "
                    + "cell, email) VALUES (" + thisUser.getUserID() + " ,"
                    + "'" + thisUser.getFirstName().getValueSafe() + "', "
                    + "'" + thisUser.getMiddleName().getValueSafe() + "', "
                    + "'" + thisUser.getLastName().getValueSafe() + "', "
                    + "'" + thisUser.getAddress1().getValueSafe() + "', "
                    + "'" + thisUser.getAddress2().getValueSafe() + "', "
                    + "'" + thisUser.getCity().getValueSafe() + "', "
                    + "'" + thisUser.getState().getValueSafe() + "', "
                    + "'" + thisUser.getZip().getValueSafe() + "', "
                    + "'" + thisUser.getPhone().getValueSafe() + "', "
                    + "'" + thisUser.getCell().getValueSafe() + "', "
                    + "'" + thisUser.getDocEmail().getValueSafe() + "')";
        }
        stm.executeUpdate(sql);
        close();
    }

    /*------------------------------------------------------------------------*
     * storeBCardData()
     * - This function stores business card data and its pdf file.
     * - Parameter thisUser is the currently logged in user.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void storeBCardData(VP_User thisUser, File bcpdf) throws SQLException,
            FileNotFoundException, IOException {
        //-------- Initialization Start ----------\\
        int userID = thisUser.getUserID();
        String sql = "SELECT * FROM business_card WHERE user_id = " + userID;
        FileInputStream inputStream;
        //-------- Initialization End ------------\\

        connect(dbName);
        rts = stm.executeQuery(sql);
        if (rts.next()) {
            sql = "UPDATE business_card SET "
                    + "profession = '" + thisUser.getBcard().getProfession().getValueSafe() + "', "
                    + "company_name = '" + thisUser.getBcard().getCompanyName().getValueSafe() + "', "
                    + "company_slogan = '" + thisUser.getBcard().getCompanySlogan().getValueSafe() + "', "
                    + "webpage = '" + thisUser.getBcard().getWebPage().getValueSafe() + "', "
                    + "theme = " + thisUser.getBcard().getThemeId() + " "
                    + "WHERE user_id = " + userID;
        } else {
            sql = "INSERT INTO business_card (user_id, profession, company_name, "
                    + "company_slogan, webpage, theme) VALUES (" + thisUser.getUserID() + " ,"
                    + "'" + thisUser.getBcard().getProfession().getValueSafe() + "', "
                    + "'" + thisUser.getBcard().getCompanyName().getValueSafe() + "', "
                    + "'" + thisUser.getBcard().getCompanySlogan().getValueSafe() + "', "
                    + "'" + thisUser.getBcard().getWebPage().getValueSafe() + "', "
                    + thisUser.getBcard().getThemeId() + ")";
        }
        stm.executeUpdate(sql);
        if (bcpdf != null) {
            inputStream = new FileInputStream(bcpdf);
            sql = "SELECT * FROM business_card_pdf WHERE user_id = " + userID;
            rts = stm.executeQuery(sql);
            if (rts.next()) {
                try (PreparedStatement ps = con.prepareStatement("UPDATE business_card_pdf SET pdf = ? WHERE user_id = ?")) {
                    ps.setBinaryStream(1, (InputStream) inputStream, (int) bcpdf.length());
                    ps.setInt(2, userID);
                    ps.executeUpdate();
                    inputStream.close();
                }
            } else {
                try (PreparedStatement ps = con.prepareStatement("INSERT INTO business_card_pdf (user_id, pdf) values(?,?)")) {
                    ps.setInt(1, userID);
                    ps.setBinaryStream(2, (InputStream) inputStream, (int) bcpdf.length());
                    ps.executeUpdate();
                    inputStream.close();
                }
            }
            // TEMPORARY JUST TESTTIIINNGGGGG            UHGUIHDDH asparagus
            sql = "SELECT * FROM business_card_pdf WHERE user_id = " + userID;
            rts = stm.executeQuery(sql);
            if (rts.next()) {
                Blob pdfBlob = rts.getBlob("pdf");
                File pdf2 = new File("bcpdfLoadedFromDatabaseTest.pdf");
                InputStream inputStream2 = rts.getBinaryStream("pdf");
                try (FileOutputStream outputStream = new FileOutputStream(pdf2)) {
                    int current;
                    while ((current = inputStream2.read()) > -1) {
                        outputStream.write(current);
                    }
                    inputStream.close();
                    outputStream.flush();
                    outputStream.close();

                    String[] ccMail = {};
                    VP_Mail bcpdfEmail;
                    String msg = "Pdf send file test.\n\n"
                            + "This is an automated message from the VaqPack software. Please do not reply.";
                    bcpdfEmail = new VP_Mail(controller, thisUser.getEmail().getValueSafe(), ccMail, "VaqPack Testing", msg, "bcpdfLoadedFromDatabaseTest.pdf", pdf2);
                    bcpdfEmail.setDaemon(true);
                    bcpdfEmail.start();
                }
            }
            // TEMPORARY JUST TESTTIIINNGGGGG            UHGUIHDDH asparagus
        }
        close();
    }

    /*------------------------------------------------------------------------*
     * storeCovLetData()
     * - This function stores cover letter data and its pdf file.
     * - Parameter thisUser is the currently logged in user.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void storeCovLetData(VP_User thisUser, File clpdf) throws SQLException,
            FileNotFoundException, IOException {
        //-------- Initialization Start ----------\\
        int userID = thisUser.getUserID(),
                remId = thisUser.getCovlet().getId();
        String sql = "SELECT * FROM cover_letter WHERE user_id = " + userID + " AND id = " + remId;
        String paraText = "";
        FileInputStream inputStream;
        //-------- Initialization End ------------\\

        for (int i = 0; i < thisUser.getCovlet().getNumbParagraphs(); i++) {
            paraText += thisUser.getCovlet().getParagraphs().get(i).getValueSafe();
            if (i != thisUser.getCovlet().getNumbParagraphs() - 1) {
                paraText += "@#$";
            }
        }
        connect(dbName);
        rts = stm.executeQuery(sql);
        if (rts.next()) {
            sql = "UPDATE cover_letter SET "
                    + "adsource = '" + thisUser.getCovlet().getAdSource().getValueSafe() + "', "
                    + "job_title = '" + thisUser.getCovlet().getAdJobTitle().getValueSafe() + "', "
                    + "reference_number = '" + thisUser.getCovlet().getAdRefNumber().getValueSafe() + "', "
                    + "date = '" + thisUser.getCovlet().getDate().getValueSafe() + "', "
                    + "contact_first_name = '" + thisUser.getCovlet().getContactFirstName().getValueSafe() + "', "
                    + "contact_middle_name = '" + thisUser.getCovlet().getContactMiddleName().getValueSafe() + "', "
                    + "contact_last_name = '" + thisUser.getCovlet().getContactLastName().getValueSafe() + "', "
                    + "contact_title = '" + thisUser.getCovlet().getContactTitle().getValueSafe() + "', "
                    + "contact_company_name = '" + thisUser.getCovlet().getContactCompany().getValueSafe() + "', "
                    + "contact_address_line1 = '" + thisUser.getCovlet().getContactAddress1().getValueSafe() + "', "
                    + "contact_address_line2 = '" + thisUser.getCovlet().getContactAddress2().getValueSafe() + "', "
                    + "contact_city = '" + thisUser.getCovlet().getContactCity().getValueSafe() + "', "
                    + "contact_state = '" + thisUser.getCovlet().getContactState().getValueSafe() + "', "
                    + "contact_zipcode = '" + thisUser.getCovlet().getContactZip().getValueSafe() + "', "
                    + "salutation = '" + thisUser.getCovlet().getSalutation().getValueSafe() + "', "
                    + "numb_paragraphs = " + thisUser.getCovlet().getNumbParagraphs() + ", "
                    + "text = '" + paraText + "', "
                    + "closing = '" + thisUser.getCovlet().getClosing().getValueSafe() + "', "
                    + "theme = " + thisUser.getCovlet().getThemeId() + " "
                    + "WHERE user_id = " + userID + " AND id = " + thisUser.getCovlet().getId();
            stm.executeUpdate(sql);
        } else {
            sql = "INSERT INTO cover_letter (user_id, adsource, job_title, reference_number, date, "
                    + "contact_first_name, contact_middle_name, contact_last_name, contact_title, "
                    + "contact_company_name, contact_address_line1, contact_address_line2, contact_city, "
                    + "contact_state, contact_zipcode, salutation, numb_paragraphs, text, "
                    + "closing, theme) VALUES (" + thisUser.getUserID() + " ,"
                    + "'" + thisUser.getCovlet().getAdSource().getValueSafe() + "', "
                    + "'" + thisUser.getCovlet().getAdJobTitle().getValueSafe() + "', "
                    + "'" + thisUser.getCovlet().getAdRefNumber().getValueSafe() + "', "
                    + "'" + thisUser.getCovlet().getDate().getValueSafe() + "', "
                    + "'" + thisUser.getCovlet().getContactFirstName().getValueSafe() + "', "
                    + "'" + thisUser.getCovlet().getContactMiddleName().getValueSafe() + "', "
                    + "'" + thisUser.getCovlet().getContactLastName().getValueSafe() + "', "
                    + "'" + thisUser.getCovlet().getContactTitle().getValueSafe() + "', "
                    + "'" + thisUser.getCovlet().getContactCompany().getValueSafe() + "', "
                    + "'" + thisUser.getCovlet().getContactAddress1().getValueSafe() + "', "
                    + "'" + thisUser.getCovlet().getContactAddress2().getValueSafe() + "', "
                    + "'" + thisUser.getCovlet().getContactCity().getValueSafe() + "', "
                    + "'" + thisUser.getCovlet().getContactState().getValueSafe() + "', "
                    + "'" + thisUser.getCovlet().getContactZip().getValueSafe() + "', "
                    + "'" + thisUser.getCovlet().getSalutation().getValueSafe() + "', "
                    + thisUser.getCovlet().getNumbParagraphs() + ", "
                    + "'" + paraText + "', "
                    + "'" + thisUser.getCovlet().getClosing().getValueSafe() + "', "
                    + thisUser.getCovlet().getThemeId() + ")";
            remId = stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        }
        thisUser.getCoverLetterIds()[thisUser.getCurrentCoverLetterIndex()] = remId;
        thisUser.getCovlet().setId(remId);
        if (clpdf != null) {
            inputStream = new FileInputStream(clpdf);
            sql = "SELECT * FROM cover_letter_pdf WHERE cover_letter_id = " + remId;
            rts = stm.executeQuery(sql);
            if (rts.next()) {
                try (PreparedStatement ps = con.prepareStatement("UPDATE cover_letter_pdf SET pdf = ? WHERE cover_letter_id = ?")) {
                    ps.setBinaryStream(1, (InputStream) inputStream, (int) clpdf.length());
                    ps.setInt(2, remId);
                    ps.executeUpdate();
                    inputStream.close();
                }
            } else {
                try (PreparedStatement ps = con.prepareStatement("INSERT INTO cover_letter_pdf (cover_letter_id, pdf) values(?,?)")) {
                    ps.setInt(1, remId);
                    ps.setBinaryStream(2, (InputStream) inputStream, (int) clpdf.length());
                    ps.executeUpdate();
                    inputStream.close();
                }
            }
            // TEMPORARY JUST TESTTIIINNGGGGG            UHGUIHDDH asparagus
            sql = "SELECT * FROM cover_letter_pdf WHERE cover_letter_id = " + remId;
            rts = stm.executeQuery(sql);
            if (rts.next()) {
                Blob pdfBlob = rts.getBlob("pdf");
                File pdf2 = new File("clpdfLoadedFromDatabaseTest.pdf");
                InputStream inputStream2 = rts.getBinaryStream("pdf");
                try (FileOutputStream outputStream = new FileOutputStream(pdf2)) {
                    int current;
                    while ((current = inputStream2.read()) > -1) {
                        outputStream.write(current);
                    }
                    inputStream.close();
                    outputStream.flush();
                    outputStream.close();

                    String[] ccMail = {};
                    VP_Mail clpdfEmail;
                    String msg = "Pdf send file test.\n\n"
                            + "This is an automated message from the VaqPack software. Please do not reply.";
                    clpdfEmail = new VP_Mail(controller, thisUser.getEmail().getValueSafe(), ccMail, "VaqPack Testing", msg, "clpdfLoadedFromDatabaseTest.pdf", pdf2);
                    clpdfEmail.setDaemon(true);
                    clpdfEmail.start();
                }
            }
            // TEMPORARY JUST TESTTIIINNGGGGG            UHGUIHDDH asparagus
        }
        close();
    }

    protected void storeResumeData(VP_User thisUser, int section) throws SQLException {
        //-------- Initialization Start ----------\\
        int userID = thisUser.getUserID();
        VP_Resume res = thisUser.getResume();
        String sql = "SELECT id FROM resume WHERE user_id = " + userID;
        String[] formattedStrings = new String[6];
        for (int i = 0; i < 6; i++) {
            formattedStrings[i] = "";
        }
        //-------- Initialization End ------------\\
        connect(dbName);
        rts = stm.executeQuery(sql);
        if (section == 1) {
            for (int i = 0; i < res.getNumbEducation(); i++) {
                for (int ii = 0; ii < 6; ii++) {
                    formattedStrings[ii] += res.getEducation().get(i).get(ii).getValueSafe();
                    if (i != (res.getNumbEducation() - 1)) {
                        formattedStrings[ii] += "@#$";
                    }
                }
            }
        } else if (section == 2) {
            for (int i = 0; i < res.getNumbExperience(); i++) {
                for (int ii = 0; ii < 5; ii++) {
                    formattedStrings[ii] += res.getExperience().get(i).get(ii).getValueSafe();
                    if (i != (res.getNumbExperience() - 1)) {
                        formattedStrings[ii] += "@#$";
                    }
                }
            }
        } else if (section == 3) {
            for (int i = 0; i < res.getNumbAchievements(); i++) {
                for (int ii = 0; ii < 3; ii++) {
                    formattedStrings[ii] += res.getAchievements().get(i).get(ii).getValueSafe();
                    if (i != (res.getNumbAchievements() - 1)) {
                        formattedStrings[ii] += "@#$";
                    }
                }
            }
        } else if (section == 4) {
            for (int i = 0; i < res.getNumbCommunity(); i++) {
                for (int ii = 0; ii < 3; ii++) {
                    formattedStrings[ii] += res.getCommunity().get(i).get(ii).getValueSafe();
                    if (i != (res.getNumbCommunity() - 1)) {
                        formattedStrings[ii] += "@#$";
                    }
                }
            }
        } else if (section == 5) {
            for (int i = 0; i < res.getNumbQualification(); i++) {
                formattedStrings[0] += res.getQualifications().get(i).getValueSafe();
                if (i != res.getNumbQualification() - 1) {
                    formattedStrings[0] += "@#$";
                }
            }
        } else if (section == 6) {
            for (int i = 0; i < res.getNumbHighlights(); i++) {
                formattedStrings[0] += res.getHighlights().get(i).getValueSafe();
                if (i != res.getNumbHighlights() - 1) {
                    formattedStrings[0] += "@#$";
                }
            }
        } else if (section == 7) {
            for (int i = 0; i < res.getNumbLanguages(); i++) {
                formattedStrings[0] += res.getLanguages().get(i).getValueSafe();
                if (i != res.getNumbLanguages() - 1) {
                    formattedStrings[0] += "@#$";
                }
            }
        } else if (section == 8) {
            for (int i = 0; i < res.getNumbSoftware(); i++) {
                formattedStrings[0] += res.getSoftware().get(i).getValueSafe();
                if (i != res.getNumbSoftware() - 1) {
                    formattedStrings[0] += "@#$";
                }
            }
        } else if (section == 9) {
            for (int i = 0; i < res.getNumbReferences(); i++) {
                for (int ii = 0; ii < 6; ii++) {
                    formattedStrings[ii] += res.getReferences().get(i).get(ii).getValueSafe();
                    if (i != (res.getNumbReferences() - 1)) {
                        formattedStrings[ii] += "@#$";
                    }
                }
            }
        }
        if (rts.next()) {
            if (section == 0) {
                sql = "UPDATE resume SET objective = '" + res.getObjective().getValueSafe() + "' WHERE  user_id = " + userID;
            } else if (section == 1) {
                sql = "UPDATE resume SET numb_education = " + res.getNumbEducation() + ", "
                        + "ed_names = '" + formattedStrings[0] + "', "
                        + "ed_locs = '" + formattedStrings[1] + "', "
                        + "ed_earned = '" + formattedStrings[2] + "', "
                        + "ed_gpa = '" + formattedStrings[3] + "', "
                        + "ed_start = '" + formattedStrings[4] + "', "
                        + "ed_end = '" + formattedStrings[5] + "' "
                        + "WHERE  user_id = " + userID;
            } else if (section == 2) {
                sql = "UPDATE resume SET numb_experience = " + res.getNumbExperience() + ", "
                        + "ex_names = '" + formattedStrings[0] + "', "
                        + "ex_locs = '" + formattedStrings[1] + "', "
                        + "ex_positions = '" + formattedStrings[2] + "', "
                        + "ex_start = '" + formattedStrings[3] + "', "
                        + "ex_end = '" + formattedStrings[4] + "', "
                        + "WHERE  user_id = " + userID;
            } else if (section == 3) {
                sql = "UPDATE resume SET numb_achievements = " + res.getNumbAchievements() + ", "
                        + "ac_names = '" + formattedStrings[0] + "', "
                        + "ac_institutions = '" + formattedStrings[1] + "', "
                        + "ac_dates = '" + formattedStrings[2] + "', "
                        + "WHERE  user_id = " + userID;
            } else if (section == 4) {
                sql = "UPDATE resume SET numb_community = " + res.getNumbCommunity() + ", "
                        + "ev_names = '" + formattedStrings[0] + "', "
                        + "ev_locs = '" + formattedStrings[1] + "', "
                        + "ev_dates = '" + formattedStrings[2] + "', "
                        + "WHERE  user_id = " + userID;
            } else if (section == 5) {
                sql = "UPDATE resume SET numb_qualifications = " + res.getNumbQualification() + ", "
                        + "qualifications = '" + formattedStrings[0] + "' WHERE  user_id = " + userID;
            } else if (section == 6) {
                sql = "UPDATE resume SET numb_highlights = " + res.getNumbHighlights() + ", "
                        + "highlights = '" + formattedStrings[0] + "' WHERE  user_id = " + userID;
            } else if (section == 7) {
                sql = "UPDATE resume SET numb_languages = " + res.getNumbLanguages() + ", "
                        + "languages = '" + formattedStrings[0] + "' WHERE  user_id = " + userID;
            } else if (section == 8) {
                sql = "UPDATE resume SET numb_software = " + res.getNumbSoftware() + ", "
                        + "software = '" + formattedStrings[0] + "' WHERE  user_id = " + userID;
            } else if (section == 9) {
                sql = "UPDATE resume SET numb_references = " + res.getNumbReferences() + ", "
                        + "ref_first_names = '" + formattedStrings[0] + "', "
                        + "ref_middle_names = '" + formattedStrings[1] + "', "
                        + "ref_last_names = '" + formattedStrings[2] + "', "
                        + "ref_company = '" + formattedStrings[3] + "', "
                        + "ref_phone = '" + formattedStrings[4] + "', "
                        + "ref_email = '" + formattedStrings[5] + "' "
                        + "WHERE  user_id = " + userID;
            }
        } else {
            if (section == 0) {
                sql = "INSERT INTO resume (user_id, objective) VALUES (" + userID + ", '"
                        + res.getObjective().getValueSafe() + "')";
            } else if (section == 1) {
                sql = "INSERT INTO resume (user_id, numb_education, ed_names, "
                        + "ed_locs, ed_earned, ed_gpa, ed_start, ed_end) VALUES (" + userID
                        + ", " + res.getNumbEducation()
                        + ", '" + formattedStrings[0] + "'"
                        + ", '" + formattedStrings[1] + "'"
                        + ", '" + formattedStrings[2] + "'"
                        + ", '" + formattedStrings[3] + "'"
                        + ", '" + formattedStrings[4] + "'"
                        + ", '" + formattedStrings[5] + "')";
            } else if (section == 2) {
                sql = "INSERT INTO resume (user_id, numb_experience, ex_names, "
                        + "ex_locs, ex_positions, ex_start, ex_end) VALUES (" + userID
                        + ", " + res.getNumbExperience()
                        + ", '" + formattedStrings[0] + "'"
                        + ", '" + formattedStrings[1] + "'"
                        + ", '" + formattedStrings[2] + "'"
                        + ", '" + formattedStrings[3] + "'"
                        + ", '" + formattedStrings[4] + "')";
            } else if (section == 3) {
                sql = "INSERT INTO resume (user_id, numb_achievements, ac_names, "
                        + "ac_institutions, ac_dates) VALUES (" + userID
                        + ", " + res.getNumbAchievements()
                        + ", '" + formattedStrings[0] + "'"
                        + ", '" + formattedStrings[1] + "'"
                        + ", '" + formattedStrings[2] + "')";
            } else if (section == 4) {
                sql = "INSERT INTO resume (user_id, numb_community, ev_names, "
                        + "ev_locs, ev_dates) VALUES (" + userID
                        + ", " + res.getNumbCommunity()
                        + ", '" + formattedStrings[0] + "'"
                        + ", '" + formattedStrings[1] + "'"
                        + ", '" + formattedStrings[2] + "')";
            } else if (section == 5) {
                sql = "INSERT INTO resume (user_id, numb_qualifications, qualifications) VALUES (" + userID
                        + ", " + res.getNumbQualification()
                        + ", '" + formattedStrings[0] + "')";
            } else if (section == 6) {
                sql = "INSERT INTO resume (user_id, numb_highlights, highlights) VALUES (" + userID
                        + ", " + res.getNumbHighlights()
                        + ", '" + formattedStrings[0] + "')";
            } else if (section == 7) {
                sql = "INSERT INTO resume (user_id, numb_languages, languages) VALUES (" + userID
                        + ", " + res.getNumbLanguages()
                        + ", '" + formattedStrings[0] + "')";
            } else if (section == 8) {
                sql = "INSERT INTO resume (user_id, numb_software, software) VALUES (" + userID
                        + ", " + res.getNumbSoftware()
                        + ", '" + formattedStrings[0] + "')";
            } else if (section == 9) {
                sql = "INSERT INTO resume (user_id, numb_references, ref_first_names, "
                        + "ref_middle_names, ref_last_names, ref_company, ref_phone, ref_email) VALUES (" + userID
                        + ", " + res.getNumbReferences()
                        + ", '" + formattedStrings[0] + "'"
                        + ", '" + formattedStrings[1] + "'"
                        + ", '" + formattedStrings[2] + "'"
                        + ", '" + formattedStrings[3] + "'"
                        + ", '" + formattedStrings[4] + "'"
                        + ", '" + formattedStrings[5] + "')";
            }
        }
        stm.executeUpdate(sql);
        close();
    }

    /*------------------------------------------------------------------------*
     * connect()
     * - Creates the connection and initializes the statement.
     * - Parameter db is the database to append to the url for connect. This db
     *   string is null when checking connection and creating trhe schema.
     * - No return.
     *------------------------------------------------------------------------*/
    private void connect(String db) throws SQLException {
        con = DriverManager.getConnection(fullURL + db, adminUserName, adminPassword);
        stm = con.createStatement();
    }

    /*------------------------------------------------------------------------*
     * close()
     * - Closes the result set, the statement, and the connection.
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    private void close() throws SQLException {
        if (rts != null) {
            rts.close();
        }
        if (stm != null) {
            stm.close();
        }
        con.close();
    }

    /*------------------------------------------------------------------------*
     * checkTable()
     * - Connects, executes the create table update, and closes.
     * - Parameter sql is the string SQL statement.
     * - No return.
     *------------------------------------------------------------------------*/
    private void checkTable(String sql) throws SQLException {
        connect(dbName);
        stm.executeUpdate(sql);
        close();
    }

    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/
    protected void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    protected void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }

    protected void setPort(String port) {
        this.port = port;
        fullURL = "jdbc:mysql://" + url + ":" + port + "/";
    }

    protected void setUrl(String url) {
        this.url = url;
        fullURL = "jdbc:mysql://" + this.url + ":" + port + "/";
    }

    protected String getPort() {
        return port;
    }

    protected String getUrl() {
        return url;
    }
}
