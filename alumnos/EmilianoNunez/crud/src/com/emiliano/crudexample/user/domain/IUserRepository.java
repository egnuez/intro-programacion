package com.emiliano.crudexample.user.domain;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    void add(User user);
    User findById(int id);
    Optional<User> findByEmail(String email);
    void update(User user);
    void delete(int id);
    List<User> list();
}
