package com;

import static spark.Spark.*;
import com.google.gson.Gson;
import java.util.*;
import spark.Request;
import spark.Response;
import spark.Route;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HerosApi {

    private static Gson gson = new Gson();
    private static String jdbcUrl = "jdbc:mysql://146.190.118.241:3306/heroes-tp";
    private static String username = "heroestp";
    private static String password = "oficios2025!";

    public static void main(String[] args) {

        port(4567);

        get("/heroes", new Route() {
            @Override
            public Object handle(Request req, Response res) throws Exception {
                res.type("application/json");
                
                // Se conecta a la Base
                
                Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

                // Ejecutar la consulta

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from heroes");
                
                ArrayList<Hero> heroes = new ArrayList<>();
                
                while(resultSet.next()){
                    Integer id = resultSet.getInt("id");
                    String nombre = resultSet.getString("nombre");
                    String poderes = resultSet.getString("poderes");
                    String debilidades = resultSet.getString("debilidades");
                    
                    Hero hero = new Hero(id, nombre, poderes, debilidades);
                    heroes.add(hero);
                }

                return gson.toJson(heroes);
            }
        });

        
        get("/heroes/:id", new Route() {
            @Override
            public Object handle(Request req, Response res) throws Exception {

                res.type("application/json");
                int id = Integer.parseInt(req.params("id"));

                
                // Se conecta a la Base
                
                Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

                // Ejecutar la consulta

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from heroes where id = " + id);
                
                if(resultSet.next()){
                    
                    Integer _id = resultSet.getInt("id");
                    String nombre = resultSet.getString("nombre");
                    String poderes = resultSet.getString("poderes");
                    String debilidades = resultSet.getString("debilidades");
                    
                    Hero hero = new Hero(_id, nombre, poderes, debilidades);
                    
                    return gson.toJson(hero);

                } else {
                    res.status(404);
                    return "";
                }

            }
        });

    
        post("/heroes", new Route() {
            @Override
            public Object handle(Request req, Response res) throws Exception {
                res.type("application/json");

                Hero hero = gson.fromJson(req.body(), Hero.class);

                // Se conecta a la Base
                
                Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

                // Ejecutar la consulta

                PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into heroes (nombre, poderes, debilidades) values (?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
                );

                // Bindear los campos

                preparedStatement.setString(1, hero.getNombre());
                preparedStatement.setString(2, hero.getPoderes());
                preparedStatement.setString(3, hero.getDebilidades());

                // Ejecutar el INSERT

                preparedStatement.executeUpdate();

                // Obtener el ID generado por la DB

                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

                if (generatedKeys.next()) {
                    int lastInsertedId = generatedKeys.getInt(1);
                    hero.setId(lastInsertedId);
                    System.out.println("Last inserted ID: " + lastInsertedId);
                } else {
                    System.out.println("Error ejecutando el insert");
                }

                // Devolver el heroe
                
                return gson.toJson(hero);
            }
        });
    
        delete("/heroes/:id", new Route() {
            @Override
            public Object handle(Request req, Response res) throws Exception {
                int id = Integer.parseInt(req.params(":id"));
                res.type("application/json");
                
                // Se conecta a la Base
                
                Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

                // Prepara consulta

                PreparedStatement preparedStatement = connection.prepareStatement("delete from heroes where id = ?");
                preparedStatement.setInt(1, id); 

                // Ejecutar consulta

                int rowsAffected = preparedStatement.executeUpdate();
                System.out.println(rowsAffected + " row(s) deleted successfully.");

                if (rowsAffected > 0){
                    res.status(200);
                    return rowsAffected + " filas(s) borradas";
                }else{
                    res.status(404);
                    return "No existe un registro con ese id";
                }
            }
        });
    }
}