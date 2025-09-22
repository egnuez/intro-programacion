package com.emiliano.crudexample.core;

import java.sql.SQLException;

import javax.swing.JFrame;

import com.emiliano.crudexample.user.application.CreateUser;
import com.emiliano.crudexample.user.application.DeleteUser;
import com.emiliano.crudexample.user.application.ListUsers;
import com.emiliano.crudexample.user.application.UpdateUser;
import com.emiliano.crudexample.user.domain.IUserRepository;
import com.emiliano.crudexample.user.infrastructure.MySQLDatabaseConnectionFactory;
import com.emiliano.crudexample.user.infrastructure.UserRepositoryFactory;
import com.emiliano.crudexample.user.presentation.UserPresenter;
import com.emiliano.crudexample.user.view.SwingUserView;

public class Main {
    public static void main(String[] args) {

        UserRepositoryFactory userRepositoryFactory = new UserRepositoryFactory(new MySQLDatabaseConnectionFactory());

        try {

            IUserRepository userRepository = userRepositoryFactory.createUserRepository();

            CreateUser crearUsuario = new CreateUser(userRepository);
            ListUsers listUsers = new ListUsers(userRepository);
            DeleteUser deleteUser = new DeleteUser(userRepository);
            UpdateUser updateUser = new UpdateUser(userRepository);
            
            SwingUserView vista = new SwingUserView();
            UserPresenter presenter = new UserPresenter(vista, crearUsuario, listUsers, deleteUser, updateUser);
            vista.setPresenter(presenter);
            vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            vista.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    
    }
}