package com.example;

import org.mindrot.jbcrypt.BCrypt;
public class PinGeneration {

    MongoDBConnection dbc;

    public PinGeneration(){
        dbc = new MongoDBConnection();
    }
    public static String hashPIN(String pin) {
        return BCrypt.hashpw(pin, BCrypt.gensalt(12)); // 12 rounds of salting
    }

    public static void main(String[] args) {
        String[] pins = new String[14];

        
        pins[0]="749984";
        pins[1]="843929";
        pins[2]="743474";
        pins[3]="880730";
        pins[4]="652914";
        pins[5]="714277";
        pins[6]="483454";
        pins[7]="411528";
        pins[8]="482026";
        pins[9]="526881";
        pins[10]="439601";
        pins[11]="615446";
        pins[12]="632293";
        pins[13]="069217";



        for (int i = 0; i < 14; i++) {

            System.out.println("Encrypted PINs: " + hashPIN(pins[i])+" ");
            
        }
    }
}
