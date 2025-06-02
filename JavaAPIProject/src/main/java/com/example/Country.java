// package com.example;

// import java.io.BufferedReader;
// import java.io.InputStreamReader;
// import java.net.HttpURLConnection;
// import java.util.Base64;
// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStreamReader;
// import java.net.HttpURLConnection;
// import java.net.MalformedURLException;
// import java.net.URL;
// import java.net.URLEncoder;
// import java.util.ArrayList;
// import java.util.Base64;
// import java.util.Scanner;
// import org.json.JSONArray;
// import org.json.JSONObject;

// import org.json.JSONObject;
// // https://public.opendatasoft.com/explore/dataset/countries-codes/table/?flg=en-us
// public class Country {
//     public static String getAPI() throw IOException { // /api/explore/v2.1/catalog/datasets/countries-codes/records?limit=20

//         String auth = "76760c2f393042f5a9f5941b5df26890" + ":" + "1ac1b639a03f46bba96435969b854c40"; 
//         String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes()); 
//         URL url = new URL("https://public.opendatasoft.com/api/explore/v2.1/catalog/datasets/countries-codes/records?limit=20"); 
//         HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
//         conn.setRequestMethod("POST"); 
//         conn.setDoOutput(true); 
//         conn.setRequestProperty("Authorization", "Basic " + encodedAuth); 
//         conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
//         String body = "grant_type=client_credentials"; 
//         conn.getOutputStream().write(body.getBytes()); 
//         BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
//         StringBuilder response = new StringBuilder(); 
//         String inputLine; 
//         while ((inputLine = in.readLine()) != null) response.append(inputLine); 
//         in.close(); // Parse access_token from JSON response 
//         String json = response.toString();
//         // System.out.println(json);
//         JSONObject obj = new JSONObject(json);
//         String token = obj.getString("access_token");
//         return token;
//     }
// }
