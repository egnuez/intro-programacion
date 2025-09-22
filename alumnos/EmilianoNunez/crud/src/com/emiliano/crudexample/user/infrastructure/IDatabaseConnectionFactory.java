package com.emiliano.crudexample.user.infrastructure;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDatabaseConnectionFactory {
    Connection createConnection() throws SQLException;
}
