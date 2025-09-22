package com.emiliano.crudexample.user.infrastructure;

import java.sql.SQLException;

public interface IUserRepositoryFactory {
    MysqlUserRepository createUserRepository() throws SQLException;
}