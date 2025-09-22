package com.emiliano.crudexample.user.infrastructure;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.emiliano.crudexample.user.domain.IUserRepository;
import com.emiliano.crudexample.user.domain.User;

public class MysqlUserRepository implements IUserRepository {
    private Connection conexion;

    public MysqlUserRepository(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void add(User usuario) {
        String query = "INSERT INTO Habilidades (nombre_habilidad, descripcion_habilidad) VALUES (?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findById(int id) {
        String query = "SELECT * FROM Habilidades WHERE id_habilidad = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id_habilidad"), rs.getString("nombre_habilidad"), rs.getString("descripcion_habilidad"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(User usuario) {
        String query = "UPDATE Habilidades SET nombre_habilidad = ?, descripcion_habilidad = ? WHERE id_habilidad = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setInt(3, usuario.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM Habilidades WHERE id_habilidad = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> list() {
        List<User> usuarios = new ArrayList<>();
        String query = "SELECT * FROM Habilidades";
        try (Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                usuarios.add(new User(rs.getInt("id_habilidad"), rs.getString("nombre_habilidad"), rs.getString("descripcion_habilidad")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    @Override
    public Optional<User> findByEmail(String email){
        String query = "SELECT * FROM Habilidades WHERE descripcion_habilidad = ?";
        Optional<User> user = Optional.empty();
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = Optional.ofNullable(
                    new User(rs.getInt("id_habilidad"), rs.getString("nombre_habilidad"), rs.getString("descripcion_habilidad")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
