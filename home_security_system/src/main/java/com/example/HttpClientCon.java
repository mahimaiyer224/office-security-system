package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpClientCon {
    public static void main(String[] args) throws Exception {
        // Path to the file containing image paths
        Path filePath = Paths.get("C:\\Users\\mahih\\Desktop\\IRP\\Images\\Test\\imageoscar.txt");

        // Read image paths from the file
        List<String> imagePaths = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                imagePaths.add(line.trim());
            }
        }

        // Create HTTP client
        CloseableHttpClient client = HttpClients.createDefault();

        for (String imagePath : imagePaths) {
            // Create POST request
            HttpPost post = new HttpPost("http://127.0.0.1:5000/recognize");

            // Build multipart request
            HttpEntity entity = MultipartEntityBuilder.create()
                    .addBinaryBody("image", new File(imagePath), ContentType.DEFAULT_BINARY, new File(imagePath).getName())
                    .build();
            post.setEntity(entity);

            // Execute request and get response
            try (CloseableHttpResponse response = client.execute(post)) {
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, Object> result = objectMapper.readValue(responseEntity.getContent(), new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {});
                    System.out.println("Image: " + imagePath);
                    System.out.println("Recognized faces: " + result.get("recognized_faces"));
                }
            }
        }
    }

    public List<String> recognizeFaces(String imagePath) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // Create POST request
            HttpPost post = new HttpPost("http://127.0.0.1:5000/recognize");

            // Build multipart request
            HttpEntity entity = MultipartEntityBuilder.create()
                    .addBinaryBody("image", new File(imagePath), ContentType.DEFAULT_BINARY, new File(imagePath).getName())
                    .build();
            post.setEntity(entity);

            // Execute request and get response
            try (CloseableHttpResponse response = client.execute(post)) {
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, Object> result = objectMapper.readValue(responseEntity.getContent(), new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {});
                    return (List<String>) result.get("recognized_faces");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of(); // Return an empty list if no faces are recognized or an error occurs
    }
}
