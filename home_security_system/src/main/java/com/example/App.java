package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            // Prompt the user for the image path
            System.out.println("Enter the path to the image file:");
            String testImagePath = scanner.nextLine();
            System.out.println("Test Image Path: " + testImagePath); // Debug print

            // Command to run the Python script with the image path as an argument
            String[] command = {"python", "C:\\Users\\mahih\\Desktop\\IRP\\home_security_system\\face_recognition\\recognize.py", testImagePath};

            // Run the Python script
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.directory(new File("C:\\Users\\mahih\\Desktop\\IRP\\home_security_system\\face_recognition")); // Set working directory

            // Start the process
            Process process = pb.start();

            // Capture the standard output of the Python script
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            List<String> outputLines = stdInput.lines().collect(Collectors.toList());

            // Parse the recognized faces from the output
            String recognizedFacesLine = outputLines.stream()
                    .filter(line -> line.startsWith("Recognized face is of "))
                    .findFirst()
                    .orElse("Recognized face is of []");
            String recognizedFacesString = recognizedFacesLine.replace("Recognized face is of ", "").replace("[", "").replace("]", "").replace("'", "").trim();
            List<String> recognizedFaces = Arrays.asList(recognizedFacesString.split(", "));

            if (!recognizedFaces.isEmpty() && !recognizedFacesString.equals("Unknown")) {
                System.out.println("Recognized faces: " + recognizedFaces);

                // Use the recognized face's name as the username
                String username = recognizedFaces.get(0); // Assume the first recognized face is the username

                // Prompt for PIN
                System.out.println("Enter PIN for user " + username + ":");
                String inputPin = scanner.nextLine();

                // Verify PIN
                PinService pinService = new PinService();
                pinService.matchPin(username, inputPin);
            } else {
                System.out.println("No faces recognized. Access denied.");
            }

            // Wait for the process to complete
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
