package com.example;


public class Employee {
    private int id;
    private String name;
    private String role;

    public String getName(){
        return name;
    }

    public void setName(String N){
        this.name = N;
    }
    public int  getId(){
        return id;
    }

    public void setId(int Id){
        this.id = Id;
    }
    public String getRole(){
        return role;
    }

    public void setRole(String R){
        this.role = R;
    }
}
