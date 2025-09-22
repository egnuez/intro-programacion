package com.emiliano.crudexample.user.application;

import com.emiliano.crudexample.user.domain.IUserRepository;
import com.emiliano.crudexample.user.domain.User;


public class UpdateUser {
    private IUserRepository usuarioRepositorio;

    public UpdateUser(IUserRepository usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public void ejecutar(User usuario) {
        if (usuarioRepositorio.findByEmail(usuario.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo electrónico.");
        }
        usuarioRepositorio.update(usuario);
    }
}
