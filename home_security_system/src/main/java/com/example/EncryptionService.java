package com.example;

import org.mindrot.jbcrypt.BCrypt;

public class EncryptionService {

    // Hash the PIN using BCrypt
    public String hashPin(String plainPin) {
        // Generates a hashed PIN
        return BCrypt.hashpw(plainPin, BCrypt.gensalt());
    }

    // Verify if the input PIN matches the stored hash
    public boolean verifyPin(String inputPin, String storedHash) {
        // Compares the input PIN with the stored hash
        return BCrypt.checkpw(inputPin, storedHash);
    }

    public static void main(String[] args)
    {
        
    }
}
