package com.emiliano.crudexample.user.infrastructure;

import java.sql.Connection;
import java.sql.SQLException;

public class UserRepositoryFactory implements IUserRepositoryFactory {
    private IDatabaseConnectionFactory connectionFactory;

    public UserRepositoryFactory(IDatabaseConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public MysqlUserRepository createUserRepository() throws SQLException {
        Connection connection = connectionFactory.createConnection();
        return new MysqlUserRepository(connection);
    }
}