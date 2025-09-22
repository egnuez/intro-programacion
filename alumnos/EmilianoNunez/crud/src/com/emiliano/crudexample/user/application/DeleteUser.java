package com.emiliano.crudexample.user.application;

import com.emiliano.crudexample.user.domain.IUserRepository;

public class DeleteUser {
    private IUserRepository usuarioRepositorio;

    public DeleteUser(IUserRepository usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public void ejecutar(int id) {
        usuarioRepositorio.delete(id);
    }
}
