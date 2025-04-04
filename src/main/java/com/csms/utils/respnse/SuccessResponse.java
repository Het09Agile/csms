package com.csms.utils.respnse;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponse{
    private String message="Success";
    private HttpStatus httpStatus;

    private Object data;

    public static ResponseEntity<Object> success(String message, Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", HttpStatus.OK.value());
        map.put("data", data);
        map.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));

        // Set headers for JSON response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        return new ResponseEntity<Object>(map, headers, HttpStatus.OK);

    }

    public static ResponseEntity<Object> success(Object data, HttpStatus status) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Success");
        map.put("status", status);
        map.put("data", data);
        map.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));

        // Set headers for JSON response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<Object>(map, headers, HttpStatus.OK);
    }

    public static ResponseEntity<?> dataOnly(Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Success");
        map.put("status", HttpStatus.OK.value());
        map.put("data", data);
        map.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));

        // Set headers for JSON response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<Object>(map, headers, HttpStatus.OK);
    }
    public static ResponseEntity<Object> messageOnly(String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", HttpStatus.OK.value());
        map.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));

        // Set headers for JSON response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<Object>(map, headers, HttpStatus.OK);
    }

    public static ResponseEntity<Object> errorResponse(String message,HttpStatus status) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));

        // Set headers for JSON response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<Object>(map, headers, status);
    }
}

