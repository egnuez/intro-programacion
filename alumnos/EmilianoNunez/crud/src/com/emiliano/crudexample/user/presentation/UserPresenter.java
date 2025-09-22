package com.emiliano.crudexample.user.presentation;

import java.util.List;

import com.emiliano.crudexample.user.application.CreateUser;
import com.emiliano.crudexample.user.application.DeleteUser;
import com.emiliano.crudexample.user.application.ListUsers;
import com.emiliano.crudexample.user.application.UpdateUser;
import com.emiliano.crudexample.user.domain.User;

public class UserPresenter {
    private IUserView vista;
    private CreateUser createUserUseCase;
    private ListUsers listUsersUseCase;
    private DeleteUser deleteUserUseCase;
    private UpdateUser updateUserUseCase;

    public UserPresenter(IUserView vista, 
    CreateUser createUserUseCase, 
    ListUsers listUsersUseCase, 
    DeleteUser deleteUserUseCase,
    UpdateUser updateUserUseCase) {
        this.vista = vista;
        this.createUserUseCase = createUserUseCase;
        this.listUsersUseCase = listUsersUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
    }

    public void agregarUsuario(String nombre, String email) {

        if (nombre == null || nombre.length() < 5 || !Character.isUpperCase(nombre.charAt(0))) {
            vista.mostrarError("El nombre debe tener al menos 5 caracteres y comenzar con mayuscula.");
            return;
        }
    
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            vista.mostrarError("El correo electrónico no es válido.");
            return;
        }

        User usuario = new User(0, nombre, email);
        try{
            createUserUseCase.ejecutar(usuario);
        } catch (IllegalArgumentException e) {
            vista.mostrarError(e.getMessage());
            return;
        }
        vista.mostrarMensaje("Usuario agregado correctamente");
    }

    public void mostrarUsuarios() {
        List<User> usuarios = listUsersUseCase.ejecutar();
        vista.mostrarListaUsuarios(usuarios);
    }

    public void borrarUsuario(int id) {
        System.out.println("borrar, " + id);
        deleteUserUseCase.ejecutar(id);
    }

    public void actualizarUsuario(int id, String nombre, String email) {

        if (nombre == null || nombre.length() < 5 || !Character.isUpperCase(nombre.charAt(0))) {
            vista.mostrarError("El nombre debe tener al menos 5 caracteres y comenzar con mayuscula.");
            return;
        }
    
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            vista.mostrarError("El correo electrónico no es válido.");
            return;
        }
        
        User usuario = new User(id, nombre, email);
        try{
            updateUserUseCase.ejecutar(usuario);
        } catch (IllegalArgumentException e) {
            vista.mostrarError(e.getMessage());
            return;
        }
        vista.mostrarMensaje("Usuario modificado correctamente");
    }
}
