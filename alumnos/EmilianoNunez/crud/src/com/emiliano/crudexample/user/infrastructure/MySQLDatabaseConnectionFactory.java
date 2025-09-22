package com.emiliano.crudexample.user.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDatabaseConnectionFactory implements IDatabaseConnectionFactory {
    @Override
    public Connection createConnection() throws SQLException {
        String url = "jdbc:mysql://45.55.169.185:3306/cursojava";
        String usuario = "tallerjava";
        String password = "Newen2024!";

        return DriverManager.getConnection(url, usuario, password);
    }
}

