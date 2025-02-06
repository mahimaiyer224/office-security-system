package com.example;

import org.bson.Document;
import org.mindrot.jbcrypt.BCrypt;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

public class PinService {
    MongoDBConnection dbc;
    EncryptionService ens;  // Use EncryptionService instead of HashingService

    public PinService() {
        dbc = new MongoDBConnection();
        ens = new EncryptionService();  // Correctly instantiate EncryptionService
    }

    public void matchPin(String userId, String inputPin) {
    MongoCollection<Document> pinsCollection = dbc.getCollection("pins");
    Document pinDocument = pinsCollection.find(Filters.eq("userId", userId)).first();

    if (pinDocument != null) {
        String encryptedPin = pinDocument.getString("encryptedPin");
        System.out.println("Fetched Encrypted PIN from DB: " + encryptedPin);  // Debug print
        System.out.println("Input PIN: " + inputPin);

        boolean isMatch = BCrypt.checkpw(inputPin, encryptedPin);
        System.out.println("BCrypt Match Result: " + isMatch);

        if (isMatch) {
            System.out.println("PIN Matched! Access Granted");
        } else {
            System.out.println("Wrong Pin");
        }
    } else {
        System.out.println("No PIN found for the given User ID");
    }
}

}
