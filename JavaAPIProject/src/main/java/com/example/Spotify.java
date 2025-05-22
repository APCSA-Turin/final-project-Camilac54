package com.example;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;
/**
 * Hello world!
 *
 */
public class Spotify {
        public static void main( String[] args ) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("♡   ∩_∩ \r\n" +
                        "╭ („•֊•„)♡  ୨୧ ┈┈ • ┈┈ • ┈┈ • ┈╮\r\n" +
                        "    U U \r\n" + "");
        System.out.print("• Welcome to Spotify Basic Stats.\n• Here you can look at the basic \n  stats of your favorite artists.");
        boolean leave = false;
        while (leave == false) {
            System.out.print("\n\nPlease type an artists name: ");
            String name = scanner.nextLine();
            System.out.println(getArtistFollowing(name));
            System.out.println(getArtistPopularity(name));
            System.out.println(getArtistGenre(name));
            if (getArtistGenre(name).equals("Genres: ")) {
                System.out.print("\nIt seems there is no saved genre for this artist.\nWould you like to add one?\nY/N: ");
                String answer = scanner.nextLine();
                if (answer.equals("Y") || answer.equals("y")) {
                    answer = scanner.nextLine();
                    System.out.println("Genres: ");
                }
            }
            System.out.print("Would you like to look for another artist?\nY/N: ");
            String answer = scanner.nextLine();
            if (answer.equals("Y") || answer.equals("y")) {
                continue;
            } else {
                leave = true;
            }
        }
        System.out.println("\nThank you so much for using my program!\nSee you later!\n╰┈ • ┈┈ • ┈┈ • ୨୧ • ┈┈ • ┈┈ • ┈╯\r\n" + "");
        // System.out.println(getAccessToken());
        // System.out.println(getArtistInfoFromAPI("Atta Boy"));
        // System.out.println(getOneArtistInfo("Atta Boy"));
        // System.out.println(getArtistFollowing("Atta Boy"));
        // System.out.println(getArtistPopularity("Atta Boy"));
        // System.out.println(getArtistGenre("Atta Boy"));
    }
    
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

    public static String getArtistGenre(String artist) throws IOException {
        JSONObject everything = getOneArtistInfo(artist);
        JSONArray genreList = everything.getJSONArray("genres");
        StringBuilder genres = new StringBuilder("Genres: ");
        for (int i = 0; i < genreList.length(); i++) {
            genres.append(genreList.getString(i));
            if (i < genreList.length() - 1) genres.append(", ");
        }
        return genres.toString();
    }

    // // Returns users top tracsk on spotify
    // // Helpful link from Spotify website: https://developer.spotify.com/documentation/web-api/reference/get-users-top-artists-and-tracks
    // // url I need to use https://api.spotify.com/v1/me/top/tracks
    // // Given authorization sample information from the developer website: Authorization: Bearer 1POdFZRZbvb...qqillRxMr2z, basically I need to use the token and have it be a bearer
    // public static String getUsersTopTracks() throws IOException {
    //     String token = getAccessToken();
    //     // System.out.println(getAccessToken() + "\n");
    //     URL url = new URL("https://api.spotify.com/v1/me/top/tracks?limit=10&offset=5"); 
    //     HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
    //     conn.setRequestProperty("Authorization", "Bearer " + token); 
    //     BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
    //     String inputLine; 
    //     StringBuilder response = new StringBuilder(); 
    //     while ((inputLine = in.readLine()) != null) response.append(inputLine); 
    //     in.close();
    //     String json = response.toString();
    //     JSONObject mainObj = new JSONObject(json);
    //     // JSONOBject firstResult = mainObj.getJSONObject(0);
    //     return json;
    // }

    // public static String getUsersTopArtist() throws IOException {
    //     String token = getAccessToken();
    //     URL url = new URL("http://api.spotify.com/v1/me/top/artists");
    //     HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
    //     conn.setRequestProperty("Authorization", "Bearer " + token); 
    //     BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
    //     String inputLine; 
    //     StringBuilder response = new StringBuilder(); 
    //     while ((inputLine = in.readLine()) != null) response.append(inputLine); 
    //     in.close();
    //     String json = response.toString();
    //     return json;
    // }
}