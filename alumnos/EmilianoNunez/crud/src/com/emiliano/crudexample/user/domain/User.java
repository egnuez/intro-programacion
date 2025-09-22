package com.emiliano.crudexample.user.domain;

public class User {
    private int id;
    private String nombre;
    private String email;

    public User(int id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}