package com.example;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;
/**
 * Hello world!
 *
 */
public class SpotifyAPI {
    private static String[][] modGenre = new String[100][100];
    
    public static void main( String[] args ) throws IOException {}
    
    // Main access method, calls api request every time it runs
    public static String getAccessToken() throws IOException { // WORKS
        String auth = "76760c2f393042f5a9f5941b5df26890" + ":" + "1ac1b639a03f46bba96435969b854c40"; 
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes()); 
        URL url = new URL("https://accounts.spotify.com/api/token"); 
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
        conn.setRequestMethod("POST"); 
        conn.setDoOutput(true); 
        conn.setRequestProperty("Authorization", "Basic " + encodedAuth); 
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
        String body = "grant_type=client_credentials"; 
        conn.getOutputStream().write(body.getBytes()); 
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
        StringBuilder response = new StringBuilder(); 
        String inputLine; 
        while ((inputLine = in.readLine()) != null) response.append(inputLine); 
        in.close(); // Parse access_token from JSON response 
        String json = response.toString();
        // System.out.println(json);
        JSONObject obj = new JSONObject(json);
        String token = obj.getString("access_token");
        return token;
    }

    // Kind of a general method, used mainly for reference
    // Returns all the artists containing any form of section from the search query, from most likely to match to least likely
    public static String getArtistInfoFromAPI(String artist) throws IOException { // WORKS
        String token = getAccessToken(); 
        // System.out.println(token);
        String query = artist; 
        URL url = new URL("https://api.spotify.com/v1/search?q=" + URLEncoder.encode(query, "UTF-8") + "&type=artist"); 
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
        conn.setRequestProperty("Authorization", "Bearer " + token); 
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
        String inputLine; 
        StringBuilder response = new StringBuilder(); 
        while ((inputLine = in.readLine()) != null) response.append(inputLine); 
        in.close();
        String json = response.toString();
        return json;
    }

    // Returns the information of the first result given the artist name
    public static JSONObject getOneArtistInfo(String artist) throws IOException { // WORKS
        String everything = getArtistInfoFromAPI(artist);
        JSONObject mainObj = new JSONObject(everything);
        JSONObject artists = mainObj.getJSONObject("artists");
        JSONObject firstResult = artists.getJSONArray("items").getJSONObject(0);
        return firstResult; 
    }

    // Returns a string with the total follower count of the artist
    public static String getArtistFollowing(String artist) throws IOException { // WORKS
        String token = getAccessToken(); 
        // System.out.println(token); // testing
        String query = artist; 
        URL url = new URL("https://api.spotify.com/v1/search?q=" + URLEncoder.encode(query, "UTF-8") + "&type=artist"); 
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
        conn.setRequestProperty("Authorization", "Bearer " + token); 
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
        String inputLine; 
        StringBuilder response = new StringBuilder(); 
        while ((inputLine = in.readLine()) != null) response.append(inputLine); 
        in.close();
        String json = response.toString();
        JSONObject mainObj = new JSONObject(json);
        JSONObject artists = mainObj.getJSONObject("artists");
        JSONObject firstResult = artists.getJSONArray("items").getJSONObject(0);
        JSONObject followers = firstResult.getJSONObject("followers");
        int total = followers.getInt("total");
        return "Total followers: " + total;
    }

    // Returns popularity level of the given artist
    public static String getArtistPopularity(String artist) throws IOException { // WORKS
        JSONObject everything = getOneArtistInfo(artist);
        int popularity = everything.getInt("popularity");
        return "Popularity Level: " + popularity + " out of 100";
    }

    public static String getArtistGenre(String artist) throws IOException { // WORKS
        JSONObject everything = getOneArtistInfo(artist);
        JSONArray genreList = everything.getJSONArray("genres");
        StringBuilder genres = new StringBuilder("Genres: ");
        for (int i = 0; i < genreList.length(); i++) {
            genres.append(genreList.getString(i));
            if (i < genreList.length() - 1) genres.append(" ");
        }
        return genres.toString();
    }

    // Returns the genre string of the modified artist
    public static String getModifiedArtistGenre(String artist) { // WORKS
        String genreString = "";
        for (int r = 0; r < modGenre.length; r ++) {
            if ((artist.equals(modGenre[r][0]))) {
                for (int c = 1; c < modGenre[0].length; c ++) {
                    if (modGenre[r][c] != null) {
                        genreString += modGenre[r][c] + " ";
                        // System.out.println(genreString);
                    } else {
                        break;
                    }
                } 
            }
        }
        return "Genres: " + genreString;
    }

    // This is meant to keep track of who is modified and to also set the new genre to the artist
    public static void setModifiedArtistGenre(String artist, String newGenre) { // WORKS
        boolean alreadyIn = false;

// this part checks if the artist we are updating the infromation for is already in our list, might scrap this idea for logic issues

        //     if (artist.equals(modGenre[row][0])) {
        // for (int row = 0; row < modGenre.length; row ++) { 
        //         for (int col = 1; col < modGenre[0].length; col ++) {
        //             if (modGenre[row][col] == null) {
        //                 modGenre[row][col] = newGenre;
        //                 alreadyIn = true;
        //                 break;
        //             }
        //         }
        //     }
        // }

        if (!alreadyIn) { // 
            for (int r = 0; r < modGenre.length; r ++) {
                if (modGenre[r][0] == null) {
                    modGenre[r][0] = artist;
                    for (int c = 1; c < 2; c ++) {
                        modGenre[r][c] = newGenre;
                    }
                    break; // hopefully breaks loop
                }
            }
        }
    }

    // Returns a nice list of the given artists top 5 tracks
    public static String getArtistTopTracks (String artist) throws IOException { // WORKS
        String token = getAccessToken();
        JSONObject artistInfo = getOneArtistInfo(artist);
        String artistId = artistInfo.getString("id");
        URL url = new URL("https://api.spotify.com/v1/artists/" + artistId + "/top-tracks?market=" + "US");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Authorization", "Bearer " + token);
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) response.append(inputLine);
        in.close();
        JSONObject obj = new JSONObject(response.toString());
        JSONArray tracks = obj.getJSONArray("tracks");
        // System.out.println(tracks); // testing 
        StringBuilder topTracks = new StringBuilder("Top Tracks:\n");
        for (int i = 0; i < Math.min(5, tracks.length()); i++) {
            JSONObject track = tracks.getJSONObject(i);
            topTracks.append("      ").append( + i + 1).append(". ").append(track.getString("name")).append("\n");
        }
        return topTracks.toString();
    }

    public static String getTrack(String artist) throws IOException {
        
        return "";
    }
}