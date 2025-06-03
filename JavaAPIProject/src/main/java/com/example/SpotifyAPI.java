package com.example;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
/**
 * Hello world!
 *
 */
public class SpotifyAPI {
    private static String[][] modGenre = new String[100][100];
    private static int totalDurationMs = 0;
    private static int totalTracks = 0;

    
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

    /**
     * Returns a string with the total follower count of the artist
     **/
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
    
    /**
     * Returns popularity level of the given artist
     **/
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

    /**
     * Returns the genre string of the modified artist
     **/
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

    /**
     * This is meant to keep track of who is modified and to also set the new genre to the artist
     **/
    public static void setModifiedArtistGenre(String artist, String newGenre) { // WORKS
        for (int r = 0; r < modGenre.length; r ++) { // iterates through the 
            if (modGenre[r][0] == null) {
                modGenre[r][0] = artist;
                for (int c = 1; c < 2; c ++) {
                    modGenre[r][c] = newGenre;
                }
                break; // hopefully breaks loop
            }
        }
    }

    /**
     * Returns a nice list of the given artists top 5 tracks, without any formatting
     * The lack of formatting is needed for a searching feature in App.java
    **/
    public static ArrayList<String> getArtistTopTracksRaw(String artist) throws IOException {
        String token = getAccessToken();
        JSONObject artistInfo = getOneArtistInfo(artist);
        String artistId = artistInfo.getString("id");
        URL url = new URL("https://api.spotify.com/v1/artists/" + artistId + "/top-tracks?market=US");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Authorization", "Bearer " + token);
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) response.append(inputLine);
        in.close();    
        JSONObject obj = new JSONObject(response.toString());
        JSONArray tracks = obj.getJSONArray("tracks");  
        ArrayList<String> trackList = new ArrayList<>();
        for (int i = 0; i < Math.min(5, tracks.length()); i++) {
            JSONObject track = tracks.getJSONObject(i);
            trackList.add(track.getString("name"));
        }
        return trackList;
    }

    /**
     * Used to format the track list given getArtistTopTracksRaw(String artist)
     * Returns with nicely formatted numbers
     */
    public static String formatTopTracks(ArrayList<String> tracks) {
        String result = "Top Tracks:\n";
        for (int i = 0; i < tracks.size(); i++) {
            result += "      " + (i + 1) + ". " + tracks.get(i) + "\n";
        }
        return result;
    }

    public static void getArtistAlbums(String artistName) throws IOException {
        totalDurationMs = 0;
        totalTracks = 0;
        String token = getAccessToken();
        JSONObject artistInfo = getOneArtistInfo(artistName);
        String artistId = artistInfo.getString("id");
        URL url = new URL("https://api.spotify.com/v1/artists/" + artistId + "/albums?include_groups=album&limit=5");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Authorization", "Bearer " + token);
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) response.append(inputLine);
        in.close();

        JSONObject json = new JSONObject(response.toString());
        JSONArray items = json.getJSONArray("items");

        for (int i = 0; i < items.length(); i++) {
            JSONObject album = items.getJSONObject(i);
            String albumId = album.getString("id");

            // Get tracks in this album
            URL tracksUrl = new URL("https://api.spotify.com/v1/albums/" + albumId + "/tracks");
            HttpURLConnection trackConn = (HttpURLConnection) tracksUrl.openConnection();
            trackConn.setRequestProperty("Authorization", "Bearer " + token);
            BufferedReader trackIn = new BufferedReader(new InputStreamReader(trackConn.getInputStream()));
            StringBuilder trackResponse = new StringBuilder();
            while ((inputLine = trackIn.readLine()) != null) trackResponse.append(inputLine);
            trackIn.close();

            JSONArray tracks = new JSONObject(trackResponse.toString()).getJSONArray("items");
            totalTracks += tracks.length();

            for (int j = 0; j < tracks.length(); j++) {
                JSONObject track = tracks.getJSONObject(j);
                totalDurationMs += track.getInt("duration_ms");
            }
        }
    }

    public static String getAlbumStats() {
        int minutes = totalDurationMs / (1000 * 60);
        return "They have " + totalTracks + " tracks across albums, totaling about " + minutes + " minutes of music!";
    }
}