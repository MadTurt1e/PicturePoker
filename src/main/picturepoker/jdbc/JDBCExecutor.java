package main.picturepoker.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExecutor {
    public static void main (String[]args){
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db", "picturepoker", "postgres", "password");
    }
}
