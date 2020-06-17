package tracking;

//package com.tracking;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.SQLException; 
  
// This class can be used to initialize the database connection 
public class DBConn { 
    public static Connection initializeDatabase() 
        throws SQLException, ClassNotFoundException 
    { 
        String dbURL = "jdbc:mariadb://circinus-10.ics.uci.edu:3306/"; 
        // Database name to access 
        String dbName = "inf124"; 
        String dbUsername = "root"; 
        String dbPassword = "ENlittenl#aw5"; 
  
        Class.forName("org.mariadb.jdbc.Driver"); 
        Connection con = DriverManager.getConnection(
            String.format("%s%s?user=%s&password=%s", dbURL, dbName, dbUsername, dbPassword)); 
        return con; 
    } 
} 
