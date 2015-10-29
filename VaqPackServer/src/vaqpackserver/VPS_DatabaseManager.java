/*-----------------------------------------------------------------------------*
 * VPS_DatabaseManager.java
 * - Handles connection and queries to a database
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 *-----------------------------------------------------------------------------*/
package vaqpackserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VPS_DatabaseManager {

    private Connection con = null;
    private final int port = 3306;
    private final String dbName = "vaqpack_db",
            url = "jdbc:mysql://localhost:" + port + "/",
            adminUserName = "vpAdmin",
            adminPassword = "vpTeam2Pa$$";
    private PreparedStatement createDB = null;

    /*------------------------------------------------------------------------*
     * VPS_DatabaseManager()
     * - Constructor. Calls checkDatabase() and checkTables()
     * - No paramaters
     *------------------------------------------------------------------------*/
    protected VPS_DatabaseManager() {
        System.out.println("######### DATABASE INITIALIZATION START ########\n");
        if (checkDatabase()) {
            checkTables();
        }
        System.out.println("########## DATABASE INITIALIZATION END #########\n");
    }

    /*------------------------------------------------------------------------*
     * checkDatabase()
     * - Checks for a MySQL server at the location and port. Checks
     * the credentials and permissions for the admin user. Checks the existence
     * of the VacPack database, creating it if it does not exist.
     * - No paramaters.
     * - Return true if no exceptions.
     *------------------------------------------------------------------------*/
    private boolean checkDatabase() {
        boolean dbCheck = true;
        System.out.println("************* CHECK DATABASE START *************\n");
        try {
            con = DriverManager.getConnection(url, adminUserName, adminPassword);
            createDB = con.prepareStatement("CREATE DATABASE " + dbName);
            createDB.executeUpdate();
            System.out.println("MySQL server is running on port " + port + ".");
            System.out.println("User " + adminUserName
                    + " has correct credentials.");
            System.out.println("Database " + dbName + " created.");
            con.close();
        } catch (SQLException ex) {
            dbCheck = false;
            int eCode = ex.getErrorCode();
            if (eCode == 0) {
                // MySQL server is not running
                System.out.println("ERROR: The MySQL server is not currently "
                        + "running on port " + port);
            } else {
                System.out.println("The MySQL server is currently running "
                        + "on port " + port + ".");
                if (eCode == 1045) {
                    // incorrect username or password
                    System.out.println("ERROR: Incorrect admin user credentials "
                            + "for " + adminUserName);
                } else {
                    System.out.println("User " + adminUserName
                            + " has correct credentials.");
                    if (eCode == 1044) {
                        // user does not have the privelege
                        System.out.println("ERROR: User " + adminUserName + " does "
                                + "not have the privelege to create the database");
                    } else if (eCode == 1007) {
                        // Database already exists
                        System.out.println("Database " + dbName + " exists.");
                        dbCheck = true;
                    }
                }
            }
            if (!dbCheck) {
                System.out.println(eCode + ":" + ex.toString());
            }
        }
        System.out.println("************** CHECK DATABASE END **************\n");
        return dbCheck;
    }

    /*------------------------------------------------------------------------*
     * checkTables()
     * - Calls the various functions to check each database table.
     * - No paramaters.
     * - Return true if no exceptions.
     *------------------------------------------------------------------------*/
    private boolean checkTables() {
        boolean tablesCheck = true;
        System.out.println("************** CHECK TABLES START **************\n");
        // checkUserTable();
        // checkPrivelegeTable();
        // checkUserDataTable();
        // checkSavedResumeTable();
        // checkSavedBusinessCardTable();
        // checkSavedCoverSheetTable();
        // checkContactTable();
        System.out.println("We need to design the tables first");
        System.out.println("*************** CHECK TABLES END ***************\n");
        return tablesCheck;
    }

    /*------------------------------------------------------------------------*
     * Setters and Getters
     *------------------------------------------------------------------------*/
}
