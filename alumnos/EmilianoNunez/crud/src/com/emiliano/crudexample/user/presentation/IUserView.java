package com.emiliano.crudexample.user.presentation;

import java.util.List;

import com.emiliano.crudexample.user.domain.User;

public interface IUserView {
    void mostrarUsuario(User usuario);
    void mostrarMensaje(String mensaje);
    void mostrarError(String message);
    void setPresenter(UserPresenter presenter);
    void mostrarListaUsuarios(List<User> usuarios);
}
