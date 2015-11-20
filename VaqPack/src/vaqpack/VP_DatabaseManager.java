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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

public class VP_DatabaseManager {

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
    protected VP_DatabaseManager() {
        //-------- Initialization Start ----------\\
        // url, port, dbadmin user and pass kept here just for refernce. These values
        // may or not be used. These were the original values before the program
        // allowed the entry of different values.
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
                + "  access_level int(1) unsigned NOT NULL DEFAULT 0,"
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
                + "  level int(1) unsigned NOT NULL,"
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
                + "  access_level int(1) unsigned NOT NULL DEFAULT 0,"
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
                + "  last_modified datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                + "  data longtext,"
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
                + "  last_modified datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                + "  source varchar(128) DEFAULT NULL,"
                + "  date datetime DEFAULT NULL,"
                + "  job_title varchar(128) DEFAULT NULL,"
                + "  reference_number varchar(128) DEFAULT NULL,"
                + "  text longtext,"
                + "  PRIMARY KEY (id),"
                + "  UNIQUE KEY id_UNIQUE (id),"
                + "  UNIQUE KEY user_id_UNIQUE (user_id),"
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
                + "  user_id int(10) unsigned NOT NULL,\n"
                + "  last_modified datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                + "  data longtext,"
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
                + "  street_numb int(11) unsigned NOT NULL,"
                + "  street varchar(128) NOT NULL,"
                + "  apartment varchar(45) DEFAULT NULL,"
                + "  city varchar(45) NOT NULL,"
                + "  state varchar(2) NOT NULL,"
                + "  zipcode varchar(5) NOT NULL,"
                + "  zip_plus_4 varchar(4) DEFAULT NULL,"
                + "  address_line2 varchar(256) DEFAULT NULL,"
                + "  phone varchar(10) NOT NULL,"
                + "  cell varchar(10) DEFAULT NULL,"
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
     * checkDefaultThemeTable()
     * - Defines the SQL statement to create the 'default_theme' table and then
     *   calls checkTable().
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void checkDefaultThemeTable() throws SQLException {
        //-------- Initialization Start ----------\\
        String sql = "CREATE TABLE default_theme ("
                + "  id int(10) unsigned NOT NULL AUTO_INCREMENT,"
                + "  PRIMARY KEY (id),"
                + "  UNIQUE KEY id_UNIQUE (id)"
                + ")";
        //-------- Initialization End ------------\\

        checkTable(sql);
    }

    /*------------------------------------------------------------------------*
     * checkBCHasCustomThemeTable()
     * - Defines the SQL statement to create the 
     *   'business_card_has_custom_theme' table and then calls checkTable().
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void checkBCHasCustomThemeTable() throws SQLException {
        //-------- Initialization Start ----------\\
        String sql = "CREATE TABLE business_card_has_custom_theme ("
                + "  business_card_id int(10) unsigned NOT NULL,"
                + "  custom_theme_id int(10) unsigned NOT NULL,"
                + "  PRIMARY KEY (business_card_id,custom_theme_id),"
                + "  UNIQUE KEY business_card_id_UNIQUE (business_card_id),"
                + "  UNIQUE KEY custom_theme_id_UNIQUE (custom_theme_id),"
                + "  CONSTRAINT bccHID FOREIGN KEY (business_card_id) "
                + "  REFERENCES business_card (id) ON DELETE CASCADE ON UPDATE NO ACTION,"
                + "  CONSTRAINT ctbHID FOREIGN KEY (custom_theme_id) "
                + "  REFERENCES custom_theme (id) ON DELETE CASCADE ON UPDATE NO ACTION"
                + ")";
        //-------- Initialization End ------------\\

        checkTable(sql);
    }

    /*------------------------------------------------------------------------*
     * checkBCHasDefaultThemeTable()
     * - Defines the SQL statement to create the 
     *   'business_card_has_default_theme' table and then calls checkTable().
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void checkBCHasDefaultThemeTable() throws SQLException {
        //-------- Initialization Start ----------\\
        String sql = "CREATE TABLE business_card_has_default_theme ("
                + "  business_card_id int(10) unsigned NOT NULL,"
                + "  default_theme_id int(10) unsigned NOT NULL,"
                + "  PRIMARY KEY (business_card_id,default_theme_id),"
                + "  UNIQUE KEY business_card_id_UNIQUE (business_card_id),"
                + "  UNIQUE KEY default_theme_id_UNIQUE (default_theme_id),"
                + "  CONSTRAINT bcdHID FOREIGN KEY (business_card_id) "
                + "  REFERENCES business_card (id) ON DELETE CASCADE ON UPDATE NO ACTION,"
                + "  CONSTRAINT dtHID FOREIGN KEY (default_theme_id) "
                + "  REFERENCES default_theme (id) ON DELETE CASCADE ON UPDATE NO ACTION"
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
                + "  id int(10) unsigned NOT NULL AUTO_INCREMENT,"
                + "  business_card_id int(10) unsigned NOT NULL,"
                + "  pdf longtext,"
                + "  PRIMARY KEY (id,business_card_id),"
                + "  UNIQUE KEY id_UNIQUE (id),"
                + "  UNIQUE KEY business_card_id_UNIQUE (business_card_id),"
                + "  CONSTRAINT busPID FOREIGN KEY (business_card_id) "
                + "  REFERENCES business_card (id) ON DELETE CASCADE ON UPDATE NO ACTION"
                + ")";
        //-------- Initialization End ------------\\

        checkTable(sql);
    }

    /*------------------------------------------------------------------------*
     * checkCLHasCustomThemeTable()
     * - Defines the SQL statement to create the 'cover_letter_has_custom_theme'
     *   table and then calls checkTable().
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void checkCLHasCustomThemeTable() throws SQLException {
        //-------- Initialization Start ----------\\
        String sql = "CREATE TABLE cover_letter_has_custom_theme ("
                + "  cover_letter_id int(10) unsigned NOT NULL,"
                + "  custom_theme_id int(10) unsigned NOT NULL,"
                + "  PRIMARY KEY (cover_letter_id,custom_theme_id),"
                + "  UNIQUE KEY cover_letter_id_UNIQUE (cover_letter_id),"
                + "  UNIQUE KEY custom_theme_id_UNIQUE (custom_theme_id),"
                + "  CONSTRAINT clcHID FOREIGN KEY (cover_letter_id) "
                + "  REFERENCES cover_letter (id) ON DELETE CASCADE ON UPDATE NO ACTION,"
                + "  CONSTRAINT ctclHID FOREIGN KEY (custom_theme_id) "
                + "  REFERENCES custom_theme (id) ON DELETE CASCADE ON UPDATE NO ACTION"
                + ")";
        //-------- Initialization End ------------\\

        checkTable(sql);
    }

    /*------------------------------------------------------------------------*
     * checkCLHasDefaultThemeTable()
     * - Defines the SQL statement to create the
     *   'cover_letter_has_default_theme' table and then calls checkTable().
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void checkCLHasDefaultThemeTable() throws SQLException {
        //-------- Initialization Start ----------\\
        String sql = "CREATE TABLE cover_letter_has_default_theme ("
                + "  cover_letter_id int(10) unsigned NOT NULL,"
                + "  default_theme_id int(10) unsigned NOT NULL,"
                + "  PRIMARY KEY (cover_letter_id,default_theme_id),"
                + "  UNIQUE KEY cover_letter_id_UNIQUE (cover_letter_id),"
                + "  UNIQUE KEY default_theme_id_UNIQUE (default_theme_id),"
                + "  CONSTRAINT cldHID FOREIGN KEY (cover_letter_id) "
                + "  REFERENCES cover_letter (id) ON DELETE CASCADE ON UPDATE NO ACTION,"
                + "  CONSTRAINT dtclHID FOREIGN KEY (default_theme_id) "
                + "  REFERENCES default_theme (id) ON DELETE CASCADE ON UPDATE NO ACTION"
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
                + "  pdf longtext,"
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
     * checkResHasCustomThemeTable()
     * - Defines the SQL statement to create the 'resume_has_custom_theme' table
     *   and then calls checkTable().
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void checkResHasCustomThemeTable() throws SQLException {
        //-------- Initialization Start ----------\\
        String sql = "CREATE TABLE resume_has_custom_theme ("
                + "  resume_id int(10) unsigned NOT NULL,"
                + "  custom_theme_id int(10) unsigned NOT NULL,"
                + "  PRIMARY KEY (resume_id,custom_theme_id),"
                + "  UNIQUE KEY resume_id_UNIQUE (resume_id),"
                + "  UNIQUE KEY custom_theme_id_UNIQUE (custom_theme_id),"
                + "  CONSTRAINT c2ID FOREIGN KEY (custom_theme_id) "
                + "  REFERENCES custom_theme (id) ON DELETE CASCADE ON UPDATE NO ACTION,"
                + "  CONSTRAINT r2ID FOREIGN KEY (resume_id) "
                + "  REFERENCES resume (id) ON DELETE CASCADE ON UPDATE NO ACTION"
                + ")";
        //-------- Initialization End ------------\\

        checkTable(sql);
    }

    /*------------------------------------------------------------------------*
     * checkResHasDefaultThemeTable()
     * - Defines the SQL statement to create the 'resume_has_default_theme'
     *   table and then calls checkTable().
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void checkResHasDefaultThemeTable() throws SQLException {
        //-------- Initialization Start ----------\\
        String sql = "CREATE TABLE resume_has_default_theme ("
                + "  resume_id int(10) unsigned NOT NULL,"
                + "  default_theme_id int(10) unsigned NOT NULL,"
                + "  PRIMARY KEY (resume_id, default_theme_id),"
                + "  UNIQUE KEY resume_id_UNIQUE (resume_id),"
                + "  UNIQUE KEY default_theme_id_UNIQUE (default_theme_id),"
                + "  CONSTRAINT dID FOREIGN KEY (default_theme_id) "
                + "  REFERENCES default_theme (id) ON DELETE CASCADE ON UPDATE NO ACTION,"
                + "  CONSTRAINT rID FOREIGN KEY (resume_id) "
                + "  REFERENCES resume (id) ON DELETE CASCADE ON UPDATE NO ACTION"
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
                + "  id int(10) unsigned NOT NULL AUTO_INCREMENT,"
                + "  resume_id int(10) unsigned NOT NULL,"
                + "  pdf longtext,"
                + "  PRIMARY KEY (id,resume_id),"
                + "  UNIQUE KEY id_UNIQUE (id),"
                + "  UNIQUE KEY resume_id_UNIQUE (resume_id),"
                + "  CONSTRAINT resPID FOREIGN KEY (resume_id) "
                + "  REFERENCES resume (id) ON DELETE CASCADE ON UPDATE NO ACTION"
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
                + "  id int(10) unsigned NOT NULL AUTO_INCREMENT,"
                + "  resume_id int(10) unsigned NOT NULL,"
                + "  html longtext,"
                + "  PRIMARY KEY (id,resume_id),"
                + "  UNIQUE KEY id_UNIQUE (id),"
                + "  UNIQUE KEY resume_id_UNIQUE (resume_id),"
                + "  CONSTRAINT resHID FOREIGN KEY (resume_id) "
                + "  REFERENCES resume (id) ON DELETE CASCADE ON UPDATE NO ACTION"
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
                System.out.println("time difference is " + difference);
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

    /*------------------------------------------------------------------------*
     * attemptUserLogin()
     * - May or may not log in the user, depending on where the user is found in
     *   the databse.
     * - Parameter cred is a string array of user credentials.
     * - Returns -1 if user does not exist, returns the user's access level if
     *   the user exists in the user table, and returns -2 if the user is found
     *   in registering_user table.
     *------------------------------------------------------------------------*/
    protected int attemptUserLogin(String[] cred)
            throws SQLException {
        //-------- Initialization Start ----------\\
        int loginStatus = -1,
                id;
        Timestamp regTime;
        long newMS;
        Date dt;
        String sql = "SELECT * FROM user WHERE email = '" + cred[0] + "' AND "
                + "password = '" + cred[1] + "'";
        //-------- Initialization End ------------\\

        connect(dbName);
        rts = stm.executeQuery(sql);
        if (rts.next()) {
            loginStatus = rts.getInt("access_level");
            dt = new java.util.Date();
            sql = "UPDATE user SET last_access = '" + new Timestamp(dt.getTime()) + "' WHERE id = " + rts.getInt("id");
                stm.executeUpdate(sql);
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
