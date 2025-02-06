package com.example;

public class Pin {
    private String id;
    private int userId;
    private String encryptedPin;

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }
    public int getUserId(){
        return userId;
    }

    public void setUserId(int UI){
        this.userId = UI;
    }
    public String getEncryptedPin(){
        return encryptedPin;
    }

    public void setEncryptedPin(String EP){
        this.encryptedPin = EP;
    }

}
