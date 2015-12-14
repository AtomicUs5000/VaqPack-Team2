/**
 * VP_DatabaseManager.java - Handles connections and queries to a database.
 * FILE ID 3100
 */
package vaqpack.data;

import com.lowagie.tools.Executable;
import vaqpack.user.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Manages connections and queries for the database.
 *
 * @author William Dewald (Project Manager, Team-02)
 * @author Fernando Bazan
 * @author Erik Lopez
 * @author Raul Saavedra
 * @author Nathanael Carr
 * @version 1.0
 * @since 1.0
 */
public class VP_DatabaseManager {

    private final VP_DataManager dataM;
    private final String dbName;
    private Connection con;
    private Statement stm;
    private ResultSet rts;
    private String url,
            port,
            fullURL,
            adminUserName,
            adminPassword;

    /**
     * Constructor. Initializes the connection, statement, and result set.
     * 
     * @param dataM Data Manager object.
     * @since 1.0
     */
    protected VP_DatabaseManager(VP_DataManager dataM) {
        //-------- Initialization Start ----------\\
        // url, port, dbadmin user and pass kept here just for refernce. These values
        // may or not be used. These were the original values before the program
        // allowed the entry of different values.
        this.dataM = dataM;
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

    /**
     * Verifies that a MySQL database is running on the provided URL and port.
     * 
     * @throws SQLException 
     * @since 1.0
     */
    protected void checkDBRunning() throws SQLException {
        connect("");
        close();
    }

    /**
     * Creates the vaqpack_db schema if necessary.
     * 
     * @throws SQLException 
     * @since 1.0
     */
    protected void checkSchema() throws SQLException {
        //-------- Initialization Start ----------\\
        String sql = "CREATE DATABASE " + dbName;
        //-------- Initialization End ------------\\

        connect("");
        stm.executeUpdate(sql);
        close();
    }

    /**
     * Defines the SQL statement to create the 'user' table and then
     * calls checkTable().
     * 
     * @throws SQLException 
     * @since 1.0
     */
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

    /**
     * Defines the SQL statement to create the access_level table and then
     * calls checkTable().
     * 
     * @throws SQLException 
     * @since 1.0
     */
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

    /**
     * Defines the SQL statement to create the registering_user table and 
     * then calls checkTable().
     * 
     * @throws SQLException 
     * @since 1.0
     */
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

    /**
     * Defines the SQL statement to create the reset_code table and 
     * then calls checkTable().
     * 
     * @throws SQLException 
     * @since 1.0
     */
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

    /**
     * Defines the SQL statement to create the business_card table and then
     * calls checkTable().
     * 
     * @throws SQLException 
     * @since 1.0
     */
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

    /**
     * Defines the SQL statement to create the 'contact' table and then
     * calls checkTable().
     * 
     * @throws SQLException 
     * @since 1.0
     */
    protected void checkContactTable() throws SQLException {
        //-------- Initialization Start ----------\\
        String sql = "CREATE TABLE contact ("
                + "  user_id int(10) unsigned NOT NULL,"
                + "  name varchar(140) NOT NULL,"
                + "  email varchar(254) NOT NULL,"
                + "  PRIMARY KEY (user_id, email),"
                + "  UNIQUE KEY id_UNIQUE (user_id, email),"
                + "  CONSTRAINT conUID FOREIGN KEY (user_id) REFERENCES user (id)"
                + "  ON DELETE CASCADE ON UPDATE NO ACTION"
                + ")";
        //-------- Initialization End ------------\\

        checkTable(sql);
    }

    /**
     * Defines the SQL statement to create the 'cover_letter' table and then
     * calls checkTable().
     * 
     * @throws SQLException 
     * @since 1.0
     */
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

    /**
     * Defines the SQL statement to create the 'resume' table and then
     * calls checkTable().
     * 
     * @throws SQLException 
     * @since 1.0
     */
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
                + "  numb_achievements tinyint(1) unsigned DEFAULT 1,"
                + "  ac_names text DEFAULT NULL,"
                + "  ac_institutions text DEFAULT NULL,"
                + "  ac_dates text DEFAULT NULL,"
                + "  numb_community tinyint(1) unsigned DEFAULT 1,"
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
                + "  numb_references tinyint(1) unsigned DEFAULT 1,"
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

    /**
     * Defines the SQL statement to create the 'user_data' table and then
     * calls checkTable().
     * 
     * @throws SQLException 
     * @since 1.0
     */
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

    /**
     * Defines the SQL statement to create the 'custom_theme' table and then
     * calls checkTable().
     * 
     * @throws SQLException 
     * @since 1.0
     */
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

    /**
     * Defines the SQL statement to create the 'business_card_pdf' table and 
     * then calls checkTable().
     * 
     * @throws SQLException 
     * @since 1.0
     */
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
    
    /**
     * Defines the SQL statement to create the 'business_card_pdf' table and 
     * then calls checkTable().
     * 
     * @throws SQLException 
     * @since 1.0
     */
    protected void checkBusinessCardHTMLTable() throws SQLException {
        //-------- Initialization Start ----------\\
        String sql = "CREATE TABLE business_card_html ("
                + "  user_id int(10) unsigned NOT NULL,"
                + "  html blob,"
                + "  PRIMARY KEY (user_id),"
                + "  UNIQUE KEY user_id_UNIQUE (user_id),"
                + "  CONSTRAINT userIDbchtml FOREIGN KEY (user_id) REFERENCES user (id)"
                + "  ON DELETE CASCADE ON UPDATE NO ACTION"
                + ")";
        //-------- Initialization End ------------\\

        checkTable(sql);
    }

    /**
     * Defines the SQL statement to create the 'cover_letter_pdf' table and
     * then calls checkTable().
     * 
     * @throws SQLException 
     * @since 1.0
     */
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
    
    /**
     * Defines the SQL statement to create the 'cover_letter_html' table and
     * then calls checkTable().
     * 
     * @throws SQLException 
     * @since 1.0
     */
    protected void checkCoverLetterHTMLTable() throws SQLException {
        //-------- Initialization Start ----------\\
        String sql = "CREATE TABLE cover_letter_html ("
                + "  id int(10) unsigned NOT NULL AUTO_INCREMENT,"
                + "  cover_letter_id int(10) unsigned NOT NULL,"
                + "  html blob,"
                + "  PRIMARY KEY (id,cover_letter_id),"
                + "  UNIQUE KEY id_UNIQUE (id),"
                + "  UNIQUE KEY cover_letter_id_UNIQUE (cover_letter_id),"
                + "  CONSTRAINT covHID FOREIGN KEY (cover_letter_id) "
                + "  REFERENCES cover_letter (id) ON DELETE CASCADE ON UPDATE NO ACTION"
                + ")";
        //-------- Initialization End ------------\\

        checkTable(sql);
    }

    /**
     * Defines the SQL statement to create the 'resume_pdf' table and then
     * calls checkTable().
     * 
     * @throws SQLException 
     * @since 1.0
     */
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

    /**
     * Defines the SQL statement to create the 'resume_html' table and then
     * calls checkTable().
     * 
     * @throws SQLException 
     * @since 1.0
     */
    protected void checkResHTMLTable() throws SQLException {
        //-------- Initialization Start ----------\\
        String sql = "CREATE TABLE resume_html ("
                + "  user_id int(10) unsigned NOT NULL,"
                + "  html blob,"
                + "  PRIMARY KEY (user_id),"
                + "  UNIQUE KEY user_id_UNIQUE (user_id),"
                + "  CONSTRAINT userIDresH FOREIGN KEY (user_id) REFERENCES user (id)"
                + "  ON DELETE CASCADE ON UPDATE NO ACTION"
                + ")";
        //-------- Initialization End ------------\\

        checkTable(sql);
    }

    /**
     * Attempts to locate at least one VaqPack administrator user account. 
     * Cleans the dead registration codes.Cleans the dead reset codes.
     * 
     * @return Boolean value indicating whether or not a VaqPack administrator 
     * user has been created and exists in the database user table. This is
     * necessary in case the user cancels or the program exists in the middle
     * of first-run initialization.
     * @throws SQLException 
     * @since 1.0
     */
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

    /**
     * Inserts an administrator VaqPack user.
     * 
     * @param cred String array of the database administrator user credentials 
     * and the VaqPack administrator user credentials.
     * @param code String of user registration code.
     * @param hashed String of hashed password.
     * @return Boolean value indicating if that the database administrator user 
     * is valid, allowing the creation of this VP administrator user account.
     * @throws SQLException 
     * @since 1.0
     */
    protected boolean createVaqPackAdmin(String[] cred, String code, String hashed)
            throws SQLException {
        //-------- Initialization Start ----------\\
        boolean adminCredChecked = false;
        //-------- Initialization End ------------\\

        if (cred[0].equals(adminUserName) && cred[1].equals(adminPassword)) {
            adminCredChecked = true;
            connect(dbName);
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO "
                    + "registering_user (email, password, access_level, access_code) values(?,?,?,?)")) {
                ps.setString(1, cred[2]);
                ps.setString(2, hashed);
                ps.setInt(3, 1);
                ps.setString(4, code);
                ps.executeUpdate();
            }
        }
        close();
        return adminCredChecked;
    }

    /**
     * Verifies the integrity of the 'access_level' table. If there are not
     * exactly 2 user levels in the table, the table gets truncated and the
     * user access levels are recreated.
     * 
     * @throws SQLException 
     * @since 1.0
     */
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

    /**
     * Loads a user cover letter from the database.
     * 
     * @param clID Integer indicating which cover letter to load.
     * @throws SQLException 
     * @since 1.0
     */
    protected void loadCovLetData(int clID) throws SQLException {
        VP_User thisUser = dataM.getController().getCurrentUser();
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

    /**
     * May or may not log in the user, depending on where the user is found in
     * the database.
     * 
     * @param cred String array of user credentials.
     * @return -1 if user does not exist, returns the user's access level if
     * the user exists in the user table, and returns -2 if the user is found
     * in registering_user table.
     * @throws SQLException 
     * @since 1.0
     */
    protected int attemptUserLogin(String[] cred) throws SQLException {
        //-------- Initialization Start ----------\\
        VP_User thisUser = dataM.getController().getCurrentUser();
        int loginStatus = -1,
                userID = -1,
                id;
        Timestamp regTime;
        long newMS;
        Date dt;
        VP_BusinessCard bc = thisUser.getBcard();
        VP_CoverLetter cl = thisUser.getCovlet();
        VP_Resume res = thisUser.getResume();
        //-------- Initialization End ------------\\
        connect(dbName);
        try (PreparedStatement ps1 = con.prepareStatement("SELECT * FROM user WHERE email = ? AND "
                + "password = ?")) {
                ps1.setString(1, cred[0]);
                ps1.setString(2, cred[1]);
                ps1.executeQuery();
                rts = ps1.getResultSet();
                if (rts.next()) {
                    userID = rts.getInt("id");
                    thisUser.setUserID(userID);
                    loginStatus = rts.getInt("access_level");
                    dt = new java.util.Date();
                    try (PreparedStatement ps2 = con.prepareStatement("UPDATE user SET last_access = ? WHERE id = ?")) {
                        ps2.setTimestamp(1, new Timestamp(dt.getTime()));
                        ps2.setInt(2, userID);
                        ps2.executeUpdate();
                        thisUser.getEmail().setValue(cred[0]);
                        thisUser.setAccessLevel(loginStatus);
                        try (PreparedStatement ps3 = con.prepareStatement("SELECT * FROM user_data WHERE user_id = ?")) {
                            ps3.setInt(1, userID);
                            ps3.executeQuery();
                            rts = ps3.getResultSet();
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
                                try (PreparedStatement ps4 = con.prepareStatement("SELECT * FROM business_card WHERE user_id = ?")) {
                                    ps4.setInt(1, userID);
                                    ps4.executeQuery();
                                    rts = ps4.getResultSet();
                                    if (rts.next()) {
                                        bc.getProfession().setValue(rts.getString("profession"));
                                        bc.getCompanyName().setValue(rts.getString("company_name"));
                                        bc.getCompanySlogan().setValue(rts.getString("company_slogan"));
                                        bc.getWebPage().setValue(rts.getString("webpage"));
                                        bc.setThemeId(rts.getInt("theme"));
                                        bc.save();
                                    }
                                    try (PreparedStatement ps5 = con.prepareStatement("SELECT * FROM cover_letter WHERE user_id = ?")) {
                                        ps5.setInt(1, userID);
                                        ps5.executeQuery();
                                        rts = ps5.getResultSet();
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
                                                if (paraText[i] != null) {
                                                    cl.getParagraphs().get(i).setValue(paraText[i]);
                                                } else {
                                                    cl.getParagraphs().get(i).setValue("");
                                                }
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
                                        try (PreparedStatement ps6 = con.prepareStatement("SELECT * FROM resume WHERE user_id = ?")) {
                                            ps6.setInt(1, userID);
                                            ps6.executeQuery();
                                            rts = ps6.getResultSet();
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
                                                    if (paraText0[i] != null)
                                                        res.getEducation().get(i).get(0).setValue(paraText0[i]);
                                                    else
                                                        res.getEducation().get(i).get(0).setValue("");
                                                    if (paraText1[i] != null)
                                                        res.getEducation().get(i).get(1).setValue(paraText1[i]);
                                                    else
                                                        res.getEducation().get(i).get(1).setValue("");
                                                    if (paraText2[i] != null)
                                                        res.getEducation().get(i).get(2).setValue(paraText2[i]);
                                                    else
                                                        res.getEducation().get(i).get(2).setValue("");
                                                    if (paraText3[i] != null)
                                                        res.getEducation().get(i).get(3).setValue(paraText3[i]);
                                                    else
                                                        res.getEducation().get(i).get(3).setValue("");
                                                    if (paraText4[i] != null)
                                                        res.getEducation().get(i).get(4).setValue(paraText4[i]);
                                                    else
                                                        res.getEducation().get(i).get(4).setValue("");
                                                    if (paraText5[i] != null)
                                                        res.getEducation().get(i).get(5).setValue(paraText5[i]);
                                                    else
                                                        res.getEducation().get(i).get(5).setValue("");
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
                                                    if (paraText0[i] != null)
                                                        res.getExperience().get(i).get(0).setValue(paraText0[i]);
                                                    else
                                                        res.getExperience().get(i).get(0).setValue("");
                                                    if (paraText1[i] != null)
                                                        res.getExperience().get(i).get(1).setValue(paraText1[i]);
                                                    else
                                                        res.getExperience().get(i).get(1).setValue("");
                                                    if (paraText2[i] != null)
                                                        res.getExperience().get(i).get(2).setValue(paraText2[i]);
                                                    else
                                                        res.getExperience().get(i).get(2).setValue("");
                                                    if (paraText3[i] != null)
                                                        res.getExperience().get(i).get(3).setValue(paraText3[i]);
                                                    else
                                                        res.getExperience().get(i).get(3).setValue("");
                                                    if (paraText4[i] != null)
                                                        res.getExperience().get(i).get(4).setValue(paraText4[i]);
                                                    else
                                                        res.getExperience().get(i).get(4).setValue("");
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
                                                    if (paraText0[i] != null)
                                                        res.getAchievements().get(i).get(0).setValue(paraText0[i]);
                                                    else
                                                        res.getAchievements().get(i).get(0).setValue("");
                                                    if (paraText1[i] != null)
                                                        res.getAchievements().get(i).get(1).setValue(paraText1[i]);
                                                    else
                                                        res.getAchievements().get(i).get(1).setValue("");
                                                    if (paraText2[i] != null)
                                                        res.getAchievements().get(i).get(2).setValue(paraText2[i]);
                                                    else
                                                        res.getAchievements().get(i).get(2).setValue("");
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
                                            try (PreparedStatement ps7 = con.prepareStatement("SELECT * FROM contact WHERE user_id = ?")) {
                                                ps7.setInt(1, userID);
                                                ps7.executeQuery();
                                                rts = ps7.getResultSet();
                                                while (rts.next()) {
                                                    VP_Contact thisContact = new VP_Contact(rts.getString("email"), rts.getString("name"));
                                                    thisUser.getContacts().add(thisContact);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    try (PreparedStatement ps7 = con.prepareStatement("SELECT * FROM "
                            + "registering_user WHERE email = ? AND password = ?")) {
                        ps7.setString(1, cred[0]);
                        ps7.setString(2, cred[1]);
                        ps7.executeQuery();
                        rts = ps7.getResultSet();
                        if (rts.next()) {
                            loginStatus = -2;
                            // extend the lifetime of the registration code by five minutes
                            id = rts.getInt("id");
                            regTime = rts.getTimestamp("reg_time");
                            newMS = regTime.getTime() + 300000;
                            regTime = new Timestamp(newMS);
                            try (PreparedStatement ps8 = con.prepareStatement("UPDATE "
                                    + "registering_user SET reg_time = ? WHERE id = ?")) {
                                ps8.setTimestamp(1, regTime);
                                ps8.setInt(2, id);
                                ps8.executeUpdate();
                            }
                        }
                    }
                }
            }
        close();
        return loginStatus;
    }

    /**
     * May or may not complete the registration of a user. This is determined 
     * by whether or not the access code and user credentials match up. If
     * everything matches, the user is moved from the registering_user table
     * to the user table.
     * 
     * @param cred String array of user credentials.
     * @return Boolean value indicating whether or not the verification of 
     * user's registration was successful.
     * @throws SQLException 
     * @since 1.0
     */
    protected boolean verifyUserAccessCode(String[] cred)
            throws SQLException {
        //-------- Initialization Start ----------\\
        boolean success = false;
        int remId, remAccessLevel;
        //-------- Initialization End ------------\\
        connect(dbName);
        try (PreparedStatement ps1 = con.prepareStatement("SELECT * FROM registering_user WHERE email = ? AND "
                + "password = ? AND access_code = ?")) {
            ps1.setString(1, cred[0]);
            ps1.setString(2, cred[1]);
            ps1.setString(3, cred[2]);
            ps1.executeQuery();
            rts = ps1.getResultSet();
            if (rts.next()) {
                success = true;
                remId = rts.getInt("id");
                remAccessLevel = rts.getInt("access_level");
                try (PreparedStatement ps2 = con.prepareStatement("DELETE FROM registering_user WHERE id = ?")) {
                    ps2.setInt(1, remId);
                    ps2.executeUpdate();
                    
                    try (PreparedStatement ps3 = con.prepareStatement("INSERT INTO user (email, password, access_level)"
                                + " VALUES (?, ?, ?)")) {
                        ps3.setString(1, cred[0]);
                        ps3.setString(2, cred[1]);
                        ps3.setInt(3, remAccessLevel);
                        ps3.executeUpdate();
                    }
                }
            }
        }
        close();
        return success;
    }

    /**
     * May or may not reset a user's password. This is determined by the
     * matching of credentials and access code, and only occurs if the code has
     * not expired. Users cannot reset a password if they already have within
     * a 24-hour period, but this is determined in a separate function before
     * function can be called. However, if the reset can happen, the reset
     * time is stored to prevent resetting again within the next 24 hours.
     * 
     * @param cred String array of user credentials.
     * @return 0 if credentials do not match, returns a 1 if the
     * credentials match but the code has expired, and returns a 2 if the
     * password reset was a success.
     * @throws SQLException 
     * @since 1.0
     */
    protected int resetPassword(String[] cred) throws SQLException {
        //-------- Initialization Start ----------\\
        int resetStatus = 0, remID;
        long difference;
        Timestamp sentTime;
        Date dt;
        //-------- Initialization End ------------\\
        connect(dbName);
        try (PreparedStatement ps1 = con.prepareStatement("SELECT * FROM "
                + "reset_code WHERE email = ? AND access_code = ?")) {
            ps1.setString(1, cred[0]);
            ps1.setString(2, cred[3]);
            ps1.executeQuery();
            rts = ps1.getResultSet();
            if (rts.next()) {
                resetStatus = 1;
                dt = new java.util.Date();
                remID = rts.getInt("id");
                sentTime = rts.getTimestamp("sent_time");
                difference = dt.getTime() - sentTime.getTime();
                if (difference < 3600000) {
                    try (PreparedStatement ps2 = con.prepareStatement("UPDATE "
                            + "reset_code SET confirm_time = ? WHERE id = ?")) {
                        ps2.setTimestamp(1, new Timestamp(dt.getTime()));
                        ps2.setInt(2, remID);
                        ps2.executeUpdate();
                        try (PreparedStatement ps3 = con.prepareStatement("UPDATE "
                                + "user SET password = ? WHERE email = ?")) {
                            ps3.setString(1, cred[1]);
                            ps3.setString(2, cred[0]);
                            ps3.executeUpdate();
                            resetStatus = 2;
                        }
                    }
                }
            }
        }
        close();
        return resetStatus;
    }
    
    /**
     * Checks database for hashed password.
     * 
     * @param hashedPass Hashed string to be searched for in database.
     * @return Boolean indicating if password was found.
     * @throws SQLException 
     * @since 1.0
     */
    protected boolean checkPassword(String hashedPass) throws SQLException {
        //-------- Initialization Start ----------\\
        boolean success = false;
        //-------- Initialization End ------------\\
        connect(dbName);
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM user "
                + "WHERE id = ? AND password = ?")) {
            ps.setInt(1, dataM.getController().getCurrentUser().getUserID());
            ps.setString(2, hashedPass);
            ps.executeQuery();
            rts = ps.getResultSet();
            if (rts.next()) {
                success = true;
            }
        }
        close();
        return success;
    }
    
    /**
     * Updates user password.
     * 
     * @param hashedPass Hashed string to replace existing string.
     * @throws SQLException 
     * @since 1.0
     */
    protected void changePassword(String hashedPass) throws SQLException {
        connect(dbName);
        try (PreparedStatement ps = con.prepareStatement("UPDATE user SET "
                + "password = ? WHERE id = ?")) {
            ps.setString(1, hashedPass);
            ps.setInt(2, dataM.getController().getCurrentUser().getUserID());
            ps.executeUpdate();
        }
        close();
    }

    /**
     * This function is called when a user submits the email in the reset
     * password page. This first step verifies that the email exists in the
     * system. If it does, a check is done to see if the user has reset the 
     * password already within the past 24 hours.
     * 
     * @param email String of the user email.
     * @param code String of the registration code that is stored in the 
     * database and sent to user via email.
     * @return 0 if the user does not exist, returns a 1 if the
     * user exists but has already reset within the past 24 hours, and returns
     * a 2 if resetting is allowed and a new code is stored.
     * @throws SQLException 
     * @since 1.0
     */
    protected int findUserOrRegUserForReset(String email, String code)
            throws SQLException {
        //-------- Initialization Start ----------\\
        Timestamp sentTime, confirmTime;
        Date dt;
        int userStatus = 0, remID;
        long difference;
        //-------- Initialization End ------------\\
        connect(dbName);
        try (PreparedStatement ps1 = con.prepareStatement("SELECT * FROM user "
                + "WHERE email = ?")) {
            ps1.setString(1, email);
            ps1.executeQuery();
            rts = ps1.getResultSet();
            if (rts.next()) {
                userStatus = 2;
            }
            try (PreparedStatement ps2 = con.prepareStatement("SELECT * FROM reset_code "
                    + "WHERE email = ?")) {
                ps2.setString(1, email);
                ps2.executeQuery();
                rts = ps2.getResultSet();
                if (rts.next()) {
                    remID = rts.getInt("id");
                    if (rts.getDate("confirm_time") == null) {
                        try (PreparedStatement ps3 = con.prepareStatement("DELETE "
                                + "FROM reset_code WHERE id = ?")) {
                            ps3.setInt(1, remID);
                            ps3.executeUpdate();
                        }
                    } else {
                        sentTime = rts.getTimestamp("sent_time");
                        confirmTime = rts.getTimestamp("confirm_time");
                        difference = confirmTime.getTime() - sentTime.getTime();
                        if (difference < 86400000) {
                            userStatus = 1;
                        } else {
                            try (PreparedStatement ps3 = con.prepareStatement("DELETE "
                                    + "FROM reset_code WHERE id = ?")) {
                                ps3.setInt(1, remID);
                                ps3.executeUpdate();
                            }
                        }
                    }
                }
                if (userStatus == 2) {
                    dt = new java.util.Date();
                    try (PreparedStatement ps3 = con.prepareStatement("INSERT INTO "
                            + "reset_code (email, access_code, sent_time) "
                            + "VALUES (?, ?, ?)")) {
                        ps3.setString(1, email);
                        ps3.setString(2, code);
                        ps3.setTimestamp(3, new Timestamp(dt.getTime()));
                        ps3.executeUpdate();
                    }
                }
            }
        }
        close();
        return userStatus;
    }

    /**
     * This function stores a brand new registration code for a user if the 
     * credentials match. This is typically called for when a user does not 
     * receive the registration email or failed to enter in the code before
     * its expiration time.
     * 
     * @param cred String array of user credentials.
     * @param code String of the registration code that the user has entered.
     * @return Boolean value indicating whether the user exists.
     * @throws SQLException 
     * @since 1.0
     */
    protected boolean resendUserAccessCode(String[] cred, String code)
            throws SQLException {
        //-------- Initialization Start ----------\\
        boolean userExists = false;
        int id;
        Date dt;
        //-------- Initialization End ------------\\
        connect(dbName);
        try (PreparedStatement ps1 = con.prepareStatement("SELECT * FROM registering_user "
                + "WHERE email = ? AND password = ?")) {
            ps1.setString(1, cred[0]);
            ps1.setString(2, cred[1]);
            ps1.executeQuery();
            rts = ps1.getResultSet();
            if (rts.next()) {
                userExists = true;
                dt = new java.util.Date();
                id = rts.getInt("id");
                try (PreparedStatement ps2 = con.prepareStatement("UPDATE registering_user "
                        + "SET reg_time = ?, access_code = ? WHERE id = ?")) {
                    ps2.setTimestamp(1, new Timestamp(dt.getTime()));
                    ps2.setString(2, code);
                    ps2.setInt(3, id);
                    ps2.executeUpdate();
                }
            }
        }
        close();
        return userExists;
    }

    /**
     * This function stores a registration code for a new user.
     * 
     * @param cred String array of user credentials.
     * @param code String of the registration code that the user has entered.
     * @return Integer value indicating whether the user is new or not.
     * @throws SQLException 
     * @since 1.0
     */
    protected int registerUser(String[] cred, String code)
            throws SQLException {
        //-------- Initialization Start ----------\\
        int registerStatus = 1;
        Date dt;
        //-------- Initialization End ------------\\
        connect(dbName);
        try (PreparedStatement ps1 = con.prepareStatement("SELECT * FROM user "
                + "WHERE email = ?")) {
            ps1.setString(1, cred[0]);
            ps1.executeQuery();
            rts = ps1.getResultSet();
            if (!rts.next()) {
                try (PreparedStatement ps2 = con.prepareStatement("SELECT * FROM registering_user "
                        + "WHERE email = ?")) {
                    ps2.setString(1, cred[0]);
                    ps2.executeQuery();
                    rts = ps2.getResultSet();
                    if (rts.next()) {
                        registerStatus = 0;
                    } else {
                        registerStatus = 2;
                        dt = new java.util.Date();
                        try (PreparedStatement ps3 = con.prepareStatement("INSERT INTO "
                                + "registering_user (email, password, access_code)"
                                + " VALUES (?, ?, ?)")) {
                            ps3.setString(1, cred[0]);
                            ps3.setString(2, cred[1]);
                            ps3.setString(3, code);
                            ps3.executeUpdate();
                        }
                    }
                }
            }
        }
        close();
        return registerStatus;
    }

    /**
     * This function stores user personal information.
     * 
     * @throws SQLException 
     * @since 1.0
     */
    protected void storeUserData() throws SQLException {
        //-------- Initialization Start ----------\\
        VP_User thisUser = dataM.getController().getCurrentUser();
        int userID = thisUser.getUserID();
        //-------- Initialization End ------------\\
        connect(dbName);
        try (PreparedStatement ps1 = con.prepareStatement("SELECT * FROM "
                + "user_data WHERE user_id = ?")) {
            ps1.setInt(1, userID);
            ps1.executeQuery();
            rts = ps1.getResultSet();
            if (rts.next()) {
                try (PreparedStatement ps2 = con.prepareStatement("UPDATE user_data SET "
                        + "first_name = ?, middle_name = ?, last_name = ?, "
                        + "address_line1 = ?, address_line2 = ?, city = ?, "
                        + "state = ?, zipcode = ?, phone = ?, cell = ?, "
                        + "email = ? WHERE user_id = ?")) {
                    ps2.setString(1, thisUser.getFirstName().getValueSafe());
                    ps2.setString(2, thisUser.getMiddleName().getValueSafe());
                    ps2.setString(3, thisUser.getLastName().getValueSafe());
                    ps2.setString(4, thisUser.getAddress1().getValueSafe());
                    ps2.setString(5, thisUser.getAddress2().getValueSafe());
                    ps2.setString(6, thisUser.getCity().getValueSafe());
                    ps2.setString(7, thisUser.getState().getValueSafe());
                    ps2.setString(8, thisUser.getZip().getValueSafe());
                    ps2.setString(9, thisUser.getPhone().getValueSafe());
                    ps2.setString(10, thisUser.getCell().getValueSafe());
                    ps2.setString(11, thisUser.getDocEmail().getValueSafe());
                    ps2.setInt(12, userID);
                    ps2.executeUpdate();
                }
            } else {
                try (PreparedStatement ps2 = con.prepareStatement("INSERT INTO "
                        + "user_data (user_id, first_name, middle_name, last_name,  "
                        + "address_line1, address_line2, city, state, zipcode, phone, "
                        + "cell, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                    ps2.setInt(1, thisUser.getUserID());
                    ps2.setString(2, thisUser.getFirstName().getValueSafe());
                    ps2.setString(3, thisUser.getMiddleName().getValueSafe());
                    ps2.setString(4, thisUser.getLastName().getValueSafe());
                    ps2.setString(5, thisUser.getAddress1().getValueSafe());
                    ps2.setString(6, thisUser.getAddress2().getValueSafe());
                    ps2.setString(7, thisUser.getCity().getValueSafe());
                    ps2.setString(8, thisUser.getState().getValueSafe());
                    ps2.setString(9, thisUser.getZip().getValueSafe());
                    ps2.setString(10, thisUser.getPhone().getValueSafe());
                    ps2.setString(11, thisUser.getCell().getValueSafe());
                    ps2.setString(12, thisUser.getDocEmail().getValueSafe());
                    ps2.executeUpdate();
                }
            }
        }
        close();
        dataM.getDbBusy().countDown();
    }

    /**
     * This function stores business card data and its PDF file.
     * 
     * @param bcpdf File object to contain PDF data.
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws IOException 
     * @since 1.0
     */
    protected void storeBCardPDF(File bcpdf) throws SQLException,
            FileNotFoundException, IOException {
        //-------- Initialization Start ----------\\
        VP_User thisUser = dataM.getController().getCurrentUser();
        int userID = thisUser.getUserID();
        String sql = "";
        FileInputStream inputStream;
        VP_BusinessCard bc = thisUser.getBcard();
        //-------- Initialization End ------------\\
        connect(dbName);
        try (PreparedStatement ps1 = con.prepareStatement("SELECT * FROM business_card WHERE user_id = ?")) {
            ps1.setInt(1, userID);
            ps1.executeQuery();
            rts = ps1.getResultSet();
            if (rts.next()) {
                try (PreparedStatement ps2 = con.prepareStatement("UPDATE business_card SET "
                        + "profession = ?, "
                        + "company_name = ?, "
                        + "company_slogan = ?, "
                        + "webpage = ?, "
                        + "theme = ? "
                        + "WHERE user_id = ?")) {
                    ps2.setString(1, bc.getProfession().getValueSafe());
                    ps2.setString(2, bc.getCompanyName().getValueSafe());
                    ps2.setString(3, bc.getCompanySlogan().getValueSafe());
                    ps2.setString(4, bc.getWebPage().getValueSafe());
                    ps2.setInt(5, bc.getThemeId());
                    ps2.setInt(6, userID);
                    ps2.executeUpdate();
                }
            } else {
                try (PreparedStatement ps2 = con.prepareStatement("INSERT INTO business_card "
                        + "(user_id, profession, company_name, "
                        + "company_slogan, webpage, theme) VALUES (?, ?, ?, ?, ?, ?)")) {
                    ps2.setInt(1, userID);
                    ps2.setString(2, bc.getProfession().getValueSafe());
                    ps2.setString(3, bc.getCompanyName().getValueSafe());
                    ps2.setString(4, bc.getCompanySlogan().getValueSafe());
                    ps2.setString(5, bc.getWebPage().getValueSafe());
                    ps2.setInt(6, bc.getThemeId());
                    ps2.executeUpdate();
                }
            }
            if (bcpdf != null) {
                inputStream = new FileInputStream(bcpdf);
                sql = "SELECT * FROM business_card_pdf WHERE user_id = " + userID;
                rts = stm.executeQuery(sql);
                if (rts.next()) {
                    try (PreparedStatement ps3 = con.prepareStatement("UPDATE business_card_pdf SET pdf = ? WHERE user_id = ?")) {
                        ps3.setBinaryStream(1, (InputStream) inputStream, (int) bcpdf.length());
                        ps3.setInt(2, userID);
                        ps3.executeUpdate();
                        inputStream.close();
                    }
                } else {
                    try (PreparedStatement ps3 = con.prepareStatement("INSERT INTO business_card_pdf (user_id, pdf) values(?,?)")) {
                        ps3.setInt(1, userID);
                        ps3.setBinaryStream(2, (InputStream) inputStream, (int) bcpdf.length());
                        ps3.executeUpdate();
                        inputStream.close();
                    }
                }
                System.out.println("business card pdf Stored");
            }
        }
        close();
        dataM.getDbBusy().countDown();
    }
    
    /**
     * This function stores business card data and its HTML file.
     * 
     * @param bchtml File object to contain the HTML data.
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws IOException 
     * @since 1.0
     */
    protected void storeBCardHTML(File bchtml) throws SQLException,
            FileNotFoundException, IOException {
        VP_User thisUser = dataM.getController().getCurrentUser();
        int userID = thisUser.getUserID();
        FileInputStream inputStream;
        String sql;
        connect(dbName);
        if (bchtml != null) {
            inputStream = new FileInputStream(bchtml);
            sql = "SELECT * FROM business_card_html WHERE user_id = " + userID;
            rts = stm.executeQuery(sql);
            if (rts.next()) {
                try (PreparedStatement ps = con.prepareStatement("UPDATE business_card_html SET html = ? WHERE user_id = ?")) {
                    ps.setBinaryStream(1, (InputStream) inputStream, (int) bchtml.length());
                    ps.setInt(2, userID);
                    ps.executeUpdate();
                    inputStream.close();
                }
            } else {
                try (PreparedStatement ps = con.prepareStatement("INSERT INTO business_card_html (user_id, html) VALUES (?, ?)")) {
                    ps.setInt(1, userID);
                    ps.setBinaryStream(2, (InputStream) inputStream, (int) bchtml.length());
                    ps.executeUpdate();
                    inputStream.close();
                }
            }
            System.out.println("Business card html Stored");
        }
        close();
    }

    /**
     * This function stores cover letter data and its PDF file.
     * 
     * @param clpdf File object containing the PDF data.
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws IOException 
     * @since 1.0
     */
    protected void storeCovLetPDF(File clpdf) throws SQLException,
            FileNotFoundException, IOException {
        //-------- Initialization Start ----------\\
        VP_User thisUser = dataM.getController().getCurrentUser();
        int userID = thisUser.getUserID(),
                remId = thisUser.getCovlet().getId();
        String sql = "", paraText = "";
        FileInputStream inputStream;
        VP_CoverLetter cl = thisUser.getCovlet();
        //-------- Initialization End ------------\\
        for (int i = 0; i < cl.getNumbParagraphs(); i++) {
            paraText += cl.getParagraphs().get(i).getValueSafe();
            if (i != cl.getNumbParagraphs() - 1) {
                paraText += "@#$";
            }
        }
        connect(dbName);
        try (PreparedStatement ps1 = con.prepareStatement("SELECT * FROM cover_letter WHERE user_id = ? AND id = ?")) {
            ps1.setInt(1, userID);
            ps1.setInt(2, remId);
            ps1.executeQuery();
            rts = ps1.getResultSet();
            if (rts.next()) {
                try (PreparedStatement ps2 = con.prepareStatement("UPDATE cover_letter SET "
                            + "adsource = ?, job_title = ?, reference_number = ?, date = ?, "
                            + "contact_first_name = ?, contact_middle_name = ?, contact_last_name = ?, "
                            + "contact_title = ?, contact_company_name = ?, contact_address_line1 = ?, "
                            + "contact_address_line2 = ?, contact_city = ?, contact_state = ?, "
                            + "contact_zipcode = ?, salutation = ?, numb_paragraphs = ?, "
                            + "text = ?, closing = ?, theme = ? WHERE user_id = ? AND id = ?")) {
                    ps2.setString(1, cl.getAdSource().getValueSafe());
                    ps2.setString(2, cl.getAdJobTitle().getValueSafe());
                    ps2.setString(3, cl.getAdRefNumber().getValueSafe());
                    ps2.setString(4, cl.getDate().getValueSafe());
                    ps2.setString(5, cl.getContactFirstName().getValueSafe());
                    ps2.setString(6, cl.getContactMiddleName().getValueSafe());
                    ps2.setString(7, cl.getContactLastName().getValueSafe());
                    ps2.setString(8, cl.getContactTitle().getValueSafe());
                    ps2.setString(9, cl.getContactCompany().getValueSafe());
                    ps2.setString(10, cl.getContactAddress1().getValueSafe());
                    ps2.setString(11, cl.getContactAddress2().getValueSafe());
                    ps2.setString(12, cl.getContactCity().getValueSafe());
                    ps2.setString(13, cl.getContactState().getValueSafe());
                    ps2.setString(14, cl.getContactZip().getValueSafe());
                    ps2.setString(15, cl.getSalutation().getValueSafe());
                    ps2.setInt(16, cl.getNumbParagraphs());
                    ps2.setString(17, paraText);
                    ps2.setString(18, cl.getClosing().getValueSafe());
                    ps2.setInt(19, cl.getThemeId());
                    ps2.setInt(20, userID);
                    ps2.setInt(21, cl.getId());
                    ps2.executeUpdate();
                }
            } else {
                sql = "INSERT INTO "
                        + "cover_letter (user_id, adsource, job_title, reference_number, date, "
                        + "contact_first_name, contact_middle_name, contact_last_name, contact_title, "
                        + "contact_company_name, contact_address_line1, contact_address_line2, contact_city, "
                        + "contact_state, contact_zipcode, salutation, numb_paragraphs, text, "
                        + "closing, theme) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                        + "?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement ps2 = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    ps2.setInt(1, userID);
                    ps2.setString(2, cl.getAdSource().getValueSafe());
                    ps2.setString(3, cl.getAdJobTitle().getValueSafe());
                    ps2.setString(4, cl.getAdRefNumber().getValueSafe());
                    ps2.setString(5, cl.getDate().getValueSafe());
                    ps2.setString(6, cl.getContactFirstName().getValueSafe());
                    ps2.setString(7, cl.getContactMiddleName().getValueSafe());
                    ps2.setString(8, cl.getContactLastName().getValueSafe());
                    ps2.setString(9, cl.getContactTitle().getValueSafe());
                    ps2.setString(10, cl.getContactCompany().getValueSafe());
                    ps2.setString(11, cl.getContactAddress1().getValueSafe());
                    ps2.setString(12, cl.getContactAddress2().getValueSafe());
                    ps2.setString(13, cl.getContactCity().getValueSafe());
                    ps2.setString(14, cl.getContactState().getValueSafe());
                    ps2.setString(15, cl.getContactZip().getValueSafe());
                    ps2.setString(16, cl.getSalutation().getValueSafe());
                    ps2.setInt(17, cl.getNumbParagraphs());
                    ps2.setString(18, paraText);
                    ps2.setString(19, cl.getClosing().getValueSafe());
                    ps2.setInt(20, cl.getThemeId());
                    remId = ps2.executeUpdate();
                }
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
                System.out.println("cover letter pdf Stored");
            }
        }
        close();
        dataM.getDbBusy().countDown();
    }
    
    /**
     * This function stores cover letter data and its HTML file.
     * 
     * @param clhtml File object containing the HTML data.
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws IOException 
     * @since 1.0
     */
    protected void storeCovLetHTML(File clhtml) throws SQLException,
            FileNotFoundException, IOException {
        VP_User thisUser = dataM.getController().getCurrentUser();
        int remId = thisUser.getCovlet().getId();
        FileInputStream inputStream;
        String sql;
        connect(dbName);
        if (clhtml != null) {
            inputStream = new FileInputStream(clhtml);
            sql = "SELECT * FROM cover_letter_html WHERE cover_letter_id = " + remId;
            rts = stm.executeQuery(sql);
            if (rts.next()) {
                try (PreparedStatement ps = con.prepareStatement("UPDATE cover_letter_html SET html = ? WHERE cover_letter_id = ?")) {
                    ps.setBinaryStream(1, (InputStream) inputStream, (int) clhtml.length());
                    ps.setInt(2, remId);
                    ps.executeUpdate();
                    inputStream.close();
                }
            } else {
                try (PreparedStatement ps = con.prepareStatement("INSERT INTO cover_letter_html (cover_letter_id, html) values(?,?)")) {
                    ps.setInt(1, remId);
                    ps.setBinaryStream(2, (InputStream) inputStream, (int) clhtml.length());
                    ps.executeUpdate();
                    inputStream.close();
                }
            }
            System.out.println("Cover letter html Stored");
        }
        close();
    }
    
    /**
     * Stores Resume data and its HTML file.
     * 
     * @param reshtml File object containing HTML data.
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws IOException 
     * @since 1.0
     */
    protected void storeResHTML(File reshtml) throws SQLException,
            FileNotFoundException, IOException {
        VP_User thisUser = dataM.getController().getCurrentUser();
        int userID = thisUser.getUserID();
        FileInputStream inputStream;
        String sql;
        connect(dbName);
        if (reshtml != null) {
            inputStream = new FileInputStream(reshtml);
            sql = "SELECT * FROM resume_html WHERE user_id = " + userID;
            rts = stm.executeQuery(sql);
            if (rts.next()) {
                try (PreparedStatement ps = con.prepareStatement("UPDATE resume_html SET html = ? WHERE user_id = ?")) {
                    ps.setBinaryStream(1, (InputStream) inputStream, (int) reshtml.length());
                    ps.setInt(2, userID);
                    ps.executeUpdate();
                    inputStream.close();
                }
            } else {
                try (PreparedStatement ps = con.prepareStatement("INSERT INTO resume_html (user_id, html) VALUES (?, ?)")) {
                    ps.setInt(1, userID);
                    ps.setBinaryStream(2, (InputStream) inputStream, (int) reshtml.length());
                    ps.executeUpdate();
                    inputStream.close();
                }
            }
            System.out.println("Resume html Stored");
        }
        close();
    }
    
    /**
     * Stores Resume data and it's PDF file.
     * 
     * @param respdf File object containing the PDF data.
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws IOException 
     * @since 1.0
     */
    protected void storeResPDF(File respdf) throws SQLException,
            FileNotFoundException, IOException {
        VP_User thisUser = dataM.getController().getCurrentUser();
        int userID = thisUser.getUserID();
        FileInputStream inputStream;
        String sql;
        connect(dbName);
        if (respdf != null) {
            inputStream = new FileInputStream(respdf);
            sql = "SELECT * FROM resume_pdf WHERE user_id = " + userID;
            rts = stm.executeQuery(sql);
            if (rts.next()) {
                try (PreparedStatement ps = con.prepareStatement("UPDATE resume_pdf SET pdf = ? WHERE user_id = ?")) {
                    ps.setBinaryStream(1, (InputStream) inputStream, (int) respdf.length());
                    ps.setInt(2, userID);
                    ps.executeUpdate();
                    inputStream.close();
                }
            } else {
                try (PreparedStatement ps = con.prepareStatement("INSERT INTO resume_pdf (user_id, pdf) values(?,?)")) {
                    ps.setInt(1, userID);
                    ps.setBinaryStream(2, (InputStream) inputStream, (int) respdf.length());
                    ps.executeUpdate();
                    inputStream.close();
                }
            }
            System.out.println("Resume pdf Stored");
        }
        close();
    }
    
    /**
     * Adds user contact to the database.
     * 
     * @param email String of contact email to be added.
     * @param name String of contact name to be added.
     * @throws SQLException 
     * @since 1.0
     */
    protected void addUserContact(String email, String name) throws SQLException {
        //-------- Initialization Start ----------\\
        VP_User thisUser = dataM.getController().getCurrentUser();
        int userID = thisUser.getUserID();
        //-------- Initialization End ------------\\
        
        connect(dbName);
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO contact (user_id, name, email) values(?, ?, ?)")) {
            ps.setInt(1, userID);
            ps.setString(2, name);
            ps.setString(3, email);
            ps.executeUpdate();
        }
        close();
    }
    
    /**
     * Deletes a user contact from the database.
     * 
     * @param email String of contact email to be deleted.
     * @param name String of contact name to be deleted.
     * @throws SQLException 
     * @since 1.0
     */
    protected void deleteUserContact(String email, String name) throws SQLException {
        //-------- Initialization Start ----------\\
        VP_User thisUser = dataM.getController().getCurrentUser();
        int userID = thisUser.getUserID();
        //-------- Initialization End ------------\\
        
        connect(dbName);
        try (PreparedStatement ps = con.prepareStatement("DELETE FROM contact WHERE user_id = ? AND name = ? AND email = ?")) {
            ps.setInt(1, userID);
            ps.setString(2, name);
            ps.setString(3, email);
            ps.executeUpdate();
        }
        close();
    }
    
    /**
     * Stores resume data to the database.
     * 
     * @param section Integer indicating which resume section data to store.
     * @throws SQLException 
     * @since 1.0
     */
    protected void storeResumeData(int section) throws SQLException {
        //-------- Initialization Start ----------\\
        VP_User thisUser = dataM.getController().getCurrentUser();
        int userID = thisUser.getUserID();
        VP_Resume res = thisUser.getResume();
        String[] formattedStrings = new String[6];
        for (int i = 0; i < 6; i++) {
            formattedStrings[i] = "";
        }
        //-------- Initialization End ------------\\
        connect(dbName);
        try (PreparedStatement ps1 = con.prepareStatement("SELECT id FROM resume WHERE user_id = ?")) {
            ps1.setInt(1, userID);
            ps1.executeQuery();
            rts = ps1.getResultSet();
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
                    PreparedStatement ps2 = con.prepareStatement("UPDATE resume "
                            + "SET objective = ? WHERE user_id = ?");
                    ps2.setString(1, res.getObjective().getValueSafe());
                    ps2.setInt(2, userID);
                    ps2.executeUpdate();
                } else if (section == 1) {
                    PreparedStatement ps2 = con.prepareStatement("UPDATE resume "
                            + "SET numb_education = ?, ed_names = ?, "
                            + "ed_locs = ?, ed_earned = ?, ed_gpa = ?, "
                            + "ed_start = ?, ed_end = ? WHERE user_id = ?");
                    ps2.setInt(1, res.getNumbEducation());
                    ps2.setString(2, formattedStrings[0]);
                    ps2.setString(3, formattedStrings[1]);
                    ps2.setString(4, formattedStrings[2]);
                    ps2.setString(5, formattedStrings[3]);
                    ps2.setString(6, formattedStrings[4]);
                    ps2.setString(7, formattedStrings[5]);
                    ps2.setInt(8, userID);
                    ps2.executeUpdate();
                } else if (section == 2) {
                    PreparedStatement ps2 = con.prepareStatement("UPDATE resume "
                            + "SET numb_experience = ?, ex_names = ?, ex_locs = ?, "
                            + "ex_positions = ?, ex_start = ?, ex_end = ? WHERE user_id = ?");
                    ps2.setInt(1, res.getNumbExperience());
                    ps2.setString(2, formattedStrings[0]);
                    ps2.setString(3, formattedStrings[1]);
                    ps2.setString(4, formattedStrings[2]);
                    ps2.setString(5, formattedStrings[3]);
                    ps2.setString(6, formattedStrings[4]);
                    ps2.setInt(7, userID);
                    ps2.executeUpdate();
                } else if (section == 3) {
                    PreparedStatement ps2 = con.prepareStatement("UPDATE resume "
                            + "SET numb_achievements = ?, ac_names = ?, "
                            + "ac_institutions = ?, ac_dates = ? WHERE user_id = ?");
                    ps2.setInt(1, res.getNumbAchievements());
                    ps2.setString(2, formattedStrings[0]);
                    ps2.setString(3, formattedStrings[1]);
                    ps2.setString(4, formattedStrings[2]);
                    ps2.setInt(5, userID);
                    ps2.executeUpdate();
                } else if (section == 4) {
                    PreparedStatement ps2 = con.prepareStatement("UPDATE resume "
                            + "SET numb_community = ?, ev_names = ?, "
                            + "ev_locs = ?, ev_dates = ? WHERE user_id = ?");
                    ps2.setInt(1, res.getNumbCommunity());
                    ps2.setString(2, formattedStrings[0]);
                    ps2.setString(3, formattedStrings[1]);
                    ps2.setString(4, formattedStrings[2]);
                    ps2.setInt(5, userID);
                    ps2.executeUpdate();
                } else if (section == 5) {
                    PreparedStatement ps2 = con.prepareStatement("UPDATE resume "
                            + "SET numb_qualifications = ?, qualifications = ? WHERE user_id = ?");
                    ps2.setInt(1, res.getNumbQualification());
                    ps2.setString(2, formattedStrings[0]);
                    ps2.setInt(3, userID);
                    ps2.executeUpdate();
                } else if (section == 6) {
                    PreparedStatement ps2 = con.prepareStatement("UPDATE resume "
                            + "SET numb_highlights = ?, highlights = ? WHERE user_id = ?");
                    ps2.setInt(1, res.getNumbHighlights());
                    ps2.setString(2, formattedStrings[0]);
                    ps2.setInt(3, userID);
                    ps2.executeUpdate();
                } else if (section == 7) {
                    PreparedStatement ps2 = con.prepareStatement("UPDATE resume "
                            + "SET numb_languages = ?, languages = ? WHERE user_id = ?");
                    ps2.setInt(1, res.getNumbLanguages());
                    ps2.setString(2, formattedStrings[0]);
                    ps2.setInt(3, userID);
                    ps2.executeUpdate();
                } else if (section == 8) {
                    PreparedStatement ps2 = con.prepareStatement("UPDATE resume "
                            + "SET numb_software = ?, software = ? WHERE user_id = ?");
                    ps2.setInt(1, res.getNumbSoftware());
                    ps2.setString(2, formattedStrings[0]);
                    ps2.setInt(3, userID);
                    ps2.executeUpdate();
                } else if (section == 9) {
                    PreparedStatement ps2 = con.prepareStatement("UPDATE resume "
                            + "SET numb_references = ?, ref_first_names = ?, "
                            + "ref_middle_names = ?, ref_last_names = ?, ref_company = ?, "
                            + "ref_phone = ?, ref_email = ? WHERE user_id = ?");
                    ps2.setInt(1, res.getNumbReferences());
                    ps2.setString(2, formattedStrings[0]);
                    ps2.setString(3, formattedStrings[1]);
                    ps2.setString(4, formattedStrings[2]);
                    ps2.setString(5, formattedStrings[3]);
                    ps2.setString(6, formattedStrings[4]);
                    ps2.setString(7, formattedStrings[5]);
                    ps2.setInt(8, userID);
                    ps2.executeUpdate();
                } else if (section == 10) {
                    PreparedStatement ps2 = con.prepareStatement("UPDATE resume "
                            + "SET theme = ? WHERE  user_id = ?");
                    ps2.setInt(1, res.getThemeId());
                    ps2.setInt(2, userID);
                    ps2.executeUpdate();
                } 
            } else {
                if (section == 0) {
                    PreparedStatement ps2 = con.prepareStatement("INSERT INTO resume "
                            + "(user_id, objective) VALUES (?, ?)");
                    ps2.setInt(1, userID);
                    ps2.setString(2, res.getObjective().getValueSafe());
                    ps2.executeUpdate();
                } else if (section == 1) {
                    PreparedStatement ps2 = con.prepareStatement("INSERT INTO resume "
                            + "(user_id, numb_education, ed_names, "
                            + "ed_locs, ed_earned, ed_gpa, ed_start, ed_end) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                    ps2.setInt(1, userID);
                    ps2.setInt(2, res.getNumbEducation());
                    ps2.setString(3, formattedStrings[0]);
                    ps2.setString(4, formattedStrings[1]);
                    ps2.setString(5, formattedStrings[2]);
                    ps2.setString(6, formattedStrings[3]);
                    ps2.setString(7, formattedStrings[4]);
                    ps2.setString(8, formattedStrings[5]);
                    ps2.executeUpdate();
                } else if (section == 2) {
                    PreparedStatement ps2 = con.prepareStatement("INSERT INTO resume "
                            + "(user_id, numb_experience, ex_names, "
                            + "ex_locs, ex_positions, ex_start, ex_end) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?)");
                    ps2.setInt(1, userID);
                    ps2.setInt(2, res.getNumbExperience());
                    ps2.setString(3, formattedStrings[0]);
                    ps2.setString(4, formattedStrings[1]);
                    ps2.setString(5, formattedStrings[2]);
                    ps2.setString(6, formattedStrings[3]);
                    ps2.setString(7, formattedStrings[4]);
                    ps2.executeUpdate();
                } else if (section == 3) {
                    PreparedStatement ps2 = con.prepareStatement("INSERT INTO resume "
                            + "(user_id, numb_achievements, ac_names, "
                            + "ac_institutions, ac_dates) "
                            + "VALUES (?, ?, ?, ?, ?)");
                    ps2.setInt(1, userID);
                    ps2.setInt(2, res.getNumbAchievements());
                    ps2.setString(3, formattedStrings[0]);
                    ps2.setString(4, formattedStrings[1]);
                    ps2.setString(5, formattedStrings[2]);
                    ps2.executeUpdate();
                } else if (section == 4) {
                    PreparedStatement ps2 = con.prepareStatement("INSERT INTO resume "
                            + "(user_id, numb_community, ev_names, "
                            + "ev_locs, ev_dates) "
                            + "VALUES (?, ?, ?, ?, ?)");
                    ps2.setInt(1, userID);
                    ps2.setInt(2, res.getNumbCommunity());
                    ps2.setString(3, formattedStrings[0]);
                    ps2.setString(4, formattedStrings[1]);
                    ps2.setString(5, formattedStrings[2]);
                    ps2.executeUpdate();
                } else if (section == 5) {
                    PreparedStatement ps2 = con.prepareStatement("INSERT INTO resume "
                            + "(user_id, numb_qualifications, qualifications) "
                            + "VALUES (?, ?, ?)");
                    ps2.setInt(1, userID);
                    ps2.setInt(2, res.getNumbQualification());
                    ps2.setString(3, formattedStrings[0]);
                    ps2.executeUpdate();
                } else if (section == 6) {
                    PreparedStatement ps2 = con.prepareStatement("INSERT INTO resume "
                            + "(user_id, numb_highlights, highlights) "
                            + "VALUES (?, ?, ?)");
                    ps2.setInt(1, userID);
                    ps2.setInt(2, res.getNumbHighlights());
                    ps2.setString(3, formattedStrings[0]);
                    ps2.executeUpdate();
                } else if (section == 7) {
                    PreparedStatement ps2 = con.prepareStatement("INSERT INTO resume "
                            + "(user_id, numb_languages, languages) "
                            + "VALUES (?, ?, ?)");
                    ps2.setInt(1, userID);
                    ps2.setInt(2, res.getNumbLanguages());
                    ps2.setString(3, formattedStrings[0]);
                    ps2.executeUpdate();
                } else if (section == 8) {
                    PreparedStatement ps2 = con.prepareStatement("INSERT INTO resume "
                            + "(user_id, numb_software, software)"
                            + "VALUES (?, ?, ?)");
                    ps2.setInt(1, userID);
                    ps2.setInt(2, res.getNumbSoftware());
                    ps2.setString(3, formattedStrings[0]);
                    ps2.executeUpdate();
                } else if (section == 9) {
                    PreparedStatement ps2 = con.prepareStatement("INSERT INTO resume "
                            + "(user_id, numb_references, ref_first_names, "
                            + "ref_middle_names, ref_last_names, ref_company, "
                            + "ref_phone, ref_email) VALUES "
                            + "(?, ?, ?, ?, ?, ?, ?, ?)");
                    ps2.setInt(1, userID);
                    ps2.setInt(2, res.getNumbReferences());
                    ps2.setString(3, formattedStrings[0]);
                    ps2.setString(4, formattedStrings[1]);
                    ps2.setString(5, formattedStrings[2]);
                    ps2.setString(6, formattedStrings[3]);
                    ps2.setString(7, formattedStrings[4]);
                    ps2.setString(8, formattedStrings[5]);
                    ps2.executeUpdate();
                } else if (section == 10) {
                    PreparedStatement ps2 = con.prepareStatement("INSERT INTO resume "
                            + "(user_id, theme) VALUES (?, ?)");
                    ps2.setInt(1, userID);
                    ps2.setInt(2, res.getThemeId());
                    ps2.executeUpdate();
                } 
            }
        }
        close();
        dataM.getDbBusy().countDown();
    }
    
    /**
     * Retrieves a VaqPack user file from the database.
     * 
     * @param type Integer indicating which document and file type to retrieve.
     * @return File object containing the retrieved document.
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws IOException 
     * @since 1.0
     */
    protected File retrieveFile(int type) throws SQLException,
            FileNotFoundException, IOException {
        //-------- Initialization Start ----------\\
        VP_User thisUser = dataM.getController().getCurrentUser();
        int userID = thisUser.getUserID();
        String filecategory = "resume",
                filetype = "pdf",
                sql = "",
                condition = " WHERE user_id = " + userID;
        File thisFile = null;
        //-------- Initialization End ------------\\
        if (type == 1) {
            filetype = "html";
        } else if (type == 3) {
            filetype = "html";
            filecategory = "business_card";
        } else if (type == 4) {
            filetype = "pdf";
            filecategory = "business_card";
        }else if (type == 5) {
            filetype = "html";
            filecategory = "cover_letter";
            condition = " WHERE cover_letter_id = " + thisUser.getCovlet().getId();
        } else if (type == 6) {
            filetype = "pdf";
            filecategory = "cover_letter";
            condition = " WHERE cover_letter_id = " + thisUser.getCovlet().getId();
        }
        sql = "SELECT " +
                filetype + " FROM " + filecategory + "_" + filetype + condition;
        connect(dbName);
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            rts = ps.executeQuery();
            if (rts.next()) {
                thisFile = new File(thisUser.getLastName().getValueSafe() + "_" + filecategory + "." + filetype);
                try (InputStream inputStream = rts.getBinaryStream(filetype);
                        FileOutputStream outputStream = new FileOutputStream(thisFile)) {
                    int current;
                    while ((current = inputStream.read()) > -1) {
                        outputStream.write(current);
                    }
                    outputStream.flush();
                }
            }
        }
        close();
        return thisFile;
    }
    
    /**
     * Prints the indicated VaqPack user file.
     * 
     * @param type Integer indicating file type and document to print.
     * @param printThis Boolean indicating whether to print file or not.
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws IOException 
     * @since 1.0
     */
    protected void printFile(int type, boolean printThis) throws SQLException,
            FileNotFoundException, IOException {
        //-------- Initialization Start ----------\\
        VP_User thisUser = dataM.getController().getCurrentUser();
        int userID = thisUser.getUserID();
        String filecategory = "resume",
                filetype = "pdf",
                sql = "",
                condition = " WHERE user_id = " + userID;
        File thisFile = null;
        Process printJob = null;
        //-------- Initialization End ------------\\
        if (type == 1) {
            filetype = "html";
        } else if (type == 3) {
            filetype = "html";
            filecategory = "business_card";
        } else if (type == 4) {
            filetype = "pdf";
            filecategory = "business_card";
        }else if (type == 5) {
            filetype = "html";
            filecategory = "cover_letter";
            condition = " WHERE cover_letter_id = " + thisUser.getCovlet().getId();
        } else if (type == 6) {
            filetype = "pdf";
            filecategory = "cover_letter";
            condition = " WHERE cover_letter_id = " + thisUser.getCovlet().getId();
        }
        sql = "SELECT " +
                filetype + " FROM " + filecategory + "_" + filetype + condition;
        connect(dbName);
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            rts = ps.executeQuery();
            if (rts.next()) {
                thisFile = new File(thisUser.getLastName().getValueSafe() + "_" + filecategory + "." + filetype);
                try (InputStream inputStream = rts.getBinaryStream(filetype);
                        FileOutputStream outputStream = new FileOutputStream(thisFile)) {
                    int current;
                    while ((current = inputStream.read()) > -1) {
                        outputStream.write(current);
                    }
                    outputStream.flush();
                }
                thisFile.deleteOnExit();
                Executable.printDocumentSilent(thisFile, true);
            }
        }
        close();
    }

    /**
     * Creates the connection and initializes the statement.
     * 
     * @param db Database to append to the URL for connect. This db
     * string is null when checking connection and creating the schema.
     * @throws SQLException 
     * @since 1.0
     */
    private void connect(String db) throws SQLException {
        con = DriverManager.getConnection(fullURL + db, adminUserName, adminPassword);
        stm = con.createStatement();
    }

    /**
     * Closes the result set, the statement, and the connection.
     * 
     * @throws SQLException 
     * @since 1.0
     */
    private void close() throws SQLException {
        if (rts != null) {
            rts.close();
        }
        if (stm != null) {
            stm.close();
        }
        con.close();
    }

    /**
     * Connects, executes the create table update, and closes.
     * 
     * @param sql The string SQL statement.
     * @throws SQLException 
     * @since 1.0
     */
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
    /**
     * Mutator method.
     * 
     * @param adminPassword String setting the administrator password.
     * @since 1.0
     */
    protected void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    /**
     * Mutator method.
     * 
     * @param adminUserName String setting the administrator user name.
     * @since 1.0
     */
    protected void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }

    /**
     * Mutator method.
     * 
     * @param port String setting the port of the database connection.
     * @since 1.0
     */
    protected void setPort(String port) {
        this.port = port;
        fullURL = "jdbc:mysql://" + url + ":" + port + "/";
    }

    /**
     * Mutator method.
     * 
     * @param url String setting the URL of the database connection.
     * @since 1.0
     */
    protected void setUrl(String url) {
        this.url = url;
        fullURL = "jdbc:mysql://" + this.url + ":" + port + "/";
    }

    /**
     * Accessor method.
     * 
     * @return String storing the port of the database connection.
     * @since 1.0
     */
    protected String getPort() {
        return port;
    }

    /**
     * Accessor method.
     * 
     * @return String storing the URL of the database connection.
     * @since 1.0
     */
    protected String getUrl() {
        return url;
    }
}
