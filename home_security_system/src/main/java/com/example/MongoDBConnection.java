package com.example;


import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {
   private static MongoDatabase database = null;

    public static MongoDatabase establishConnection(){
        try{
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            database = mongoClient.getDatabase("office_security_name");
            System.out.println("Connection Established Successfully");
        }
        catch(MongoException e){
            System.out.println("Connection Establishment Failed");
            System.out.println(e);
        }

        return database;
    }
    
    public static MongoCollection<Document> getCollection(String collectionName)
    {
        if(database==null)
        {
            establishConnection();
        }
        return database.getCollection(collectionName);
    }

}
