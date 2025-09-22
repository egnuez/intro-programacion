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

        /*
        post("/heroes", new Route() {
            @Override
            public Object handle(Request req, Response res) throws Exception {
                res.type("application/json");
                Hero hero = gson.fromJson(req.body(), Hero.class);
                hero = new Hero(nextId++, hero.getName(), hero.getPower(), hero.getAvatar());
                heroes.add(hero);
                return gson.toJson(hero);
            }
        });
    
        delete("/heroes/:id", new Route() {
            @Override
            public Object handle(Request req, Response res) throws Exception {
                int id = Integer.parseInt(req.params(":id"));
                res.type("application/json");
                for (Hero h : heroes) {
                    if (h.getId() == id) {
                        heroes.remove(h);
                        return gson.toJson(h);
                    }
                }

                // No existe
                
                res.status(404);
                return "";
            }
        });
        */
    }
}