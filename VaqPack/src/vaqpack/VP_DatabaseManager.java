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

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.bind.DatatypeConverter;

public class VP_DatabaseManager {

    private Connection con;
    private Statement stm;
    private ResultSet rts;
    private final String dbName = "vaqpack_db";
    private String port = "3306",
            url = "localhost",
            fullURL = "jdbc:mysql://" + url + ":" + port + "/",
            adminUserName = "vpAdmin",
            adminPassword = "vpTeam2Pa$$";

    /*------------------------------------------------------------------------*
     * VP_DatabaseManager()
     * - Constructor. Initialiazes the connection, statement, and result set.
     * No Parameters.
     *------------------------------------------------------------------------*/
    protected VP_DatabaseManager() {
        con = null;
        stm = null;
        rts = null;
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
        connect("");
        String sql = "CREATE DATABASE " + dbName;
        stm.executeUpdate(sql);
        close();
    }

    /*------------------------------------------------------------------------*
     * checkAcessLevelTable()
     * - Defines the SQL statement to create the 'access_level' table and then
     *   calls checkTable().
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void checkAcessLevelTable() throws SQLException {
        String sql = "CREATE TABLE access_level ("
                + "  id int(10) unsigned NOT NULL AUTO_INCREMENT,"
                + "  level int(1) unsigned NOT NULL,"
                + "  change_credentials bit(1) NOT NULL DEFAULT 0,"
                + "  create_admin bit(1) NOT NULL DEFAULT 0,"
                + "  move_db bit(1) NOT NULL DEFAULT 0,"
                + "  PRIMARY KEY (id, level),"
                + "  UNIQUE KEY id_UNIQUE (id),"
                + "  UNIQUE KEY level_UNIQUE (level)"
                + ")";
        checkTable(sql);
    }

    /*------------------------------------------------------------------------*
     * checkUserTable()
     * - Defines the SQL statement to create the 'user' table and then
     *   calls checkTable().
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void checkUserTable() throws SQLException {
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
        String sql = "CREATE TABLE custom_theme ("
                + "  id int(10) unsigned NOT NULL AUTO_INCREMENT,"
                + "  user_id int(10) unsigned NOT NULL,"
                + "  PRIMARY KEY (id,user_id),"
                + "  UNIQUE KEY id_UNIQUE (id),"
                + "  UNIQUE KEY user_id_UNIQUE (user_id),"
                + "  CONSTRAINT CTUserID FOREIGN KEY (user_id) REFERENCES user (id)"
                + "  ON DELETE CASCADE ON UPDATE NO ACTION"
                + ")";
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
        String sql = "CREATE TABLE default_theme ("
                + "  id int(10) unsigned NOT NULL AUTO_INCREMENT,"
                + "  PRIMARY KEY (id),"
                + "  UNIQUE KEY id_UNIQUE (id)"
                + ")";
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
        checkTable(sql);
    }

    /*------------------------------------------------------------------------*
     * findAdminUser()
     * - Attempts to locate at least one VaqPack admin user account.
     * - No parameters.
     * - Returns a boolean value indicating whether or not a VaqPack admin user
     *   has been created and exists in the database user table. This is
     *   necessary in case the user cancels or the program exists in the middle
     *   of first-run initialization.
     *------------------------------------------------------------------------*/
    protected boolean findAdminUser() throws SQLException {
        boolean adminExists = false;
        connect(dbName);
        String sql = "SELECT * FROM user WHERE access_level = 1";
        rts = stm.executeQuery(sql);
        if (rts.next()) {
            adminExists = true;
        }
        close();
        return adminExists;
    }

    /*------------------------------------------------------------------------*
     * createVaqPackAdmin()
     * - Inserts an admin VaqPack user.
     * - Parameters cred is a string array of the database admin user
     *   credentials and the VaqPack admin user crfedentials.
     * - Returns a boolean value indicating if that the database admin user is
     *   valid, allowing the creation of this VP admin user account.
     *------------------------------------------------------------------------*/
    protected boolean createVaqPackAdmin(String[] cred) throws SQLException,
            NoSuchAlgorithmException, UnsupportedEncodingException {
        boolean adminCredChecked = false;
        if (cred[0].equals(adminUserName) && cred[1].equals(adminPassword)) {
            adminCredChecked = true;
            connect(dbName);
            String sql = "INSERT INTO user (email, password, access_level)"
                    + " VALUES ('" + cred[2] + "', '" + hashPassword(cred[3]) + "', 1)";
            stm.executeUpdate(sql);
            close();
        }
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
        connect(dbName);
        String sql = "SELECT COUNT(*) FROM access_level";
        rts = stm.executeQuery(sql);
        rts.next();
        int count = rts.getInt(1);
        if (count != 2) {
            if (count != 0) {
                sql = "TRUNCATE access_level";
                stm.executeUpdate(sql);
            }
            sql = "INSERT INTO access_level (level, change_credentials, create_admin, move_db)"
                    + " VALUES (0, 0, 0, 0)";
            stm.executeUpdate(sql);
            sql = "INSERT INTO access_level (level, change_credentials, create_admin, move_db)"
                    + " VALUES (1, 1, 1, 1);";
            stm.executeUpdate(sql);
        }
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
    
    /*------------------------------------------------------------------------*
     * hashPassword()
     * - Hashes a password.
     * - Parameter pass is the string to be hashed.
     * - Return a string of the hashed password.
     *------------------------------------------------------------------------*/
    private String hashPassword(String pass) throws NoSuchAlgorithmException,
            UnsupportedEncodingException {
        MessageDigest mDig = MessageDigest.getInstance("SHA-256");
        byte[] passHash = mDig.digest(pass.getBytes("UTF-8"));
        return DatatypeConverter.printHexBinary(passHash);
    }
    
    /*------------------------------------------------------------------------*
     * Setters and Getters
     *------------------------------------------------------------------------*/
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