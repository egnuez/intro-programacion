package com.emiliano.crudexample.user.application;

import java.util.List;

import com.emiliano.crudexample.user.domain.IUserRepository;
import com.emiliano.crudexample.user.domain.User;

public class ListUsers {
    private IUserRepository usuarioRepositorio;

    public ListUsers(IUserRepository usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public List<User> ejecutar() {
        return usuarioRepositorio.list();
    }
}
