package com;

public class Hero {
    private int id;
    private String name;
    private String power;
    private String avatar;
    

    public Hero(int id, String name, String power, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;

    }

    public int getId() { return id; }

    public String getName() { return name; }
    public String getPower() { return power; }
    public String getAvatar() { return avatar; }
    

    public void setName(String name) { this.name = name; }
    public void setPower(String power) { this.power = power; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    
}
