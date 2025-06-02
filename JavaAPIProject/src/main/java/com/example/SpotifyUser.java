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
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Scanner;

public class SpotifyUser { // None of this works yet... :(
    private static ArrayList<String> favorite = new ArrayList<String>();

    public static boolean addFavArtist(String fav) {
        favorite.add(fav);
        return true;
    }

    public static String getFavArtistIndex(int index) {
        return favorite.get(index - 1);
    }

    public static String getFavArtistName(String artistName) {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < favorite.size() - 1; i ++) {
            if (favorite.get(i).equals(artistName)) {
                return favorite.get(i);
            }
        }
        System.out.print("I am sorry, I couldn't find that artist in your favorite list, would you like to add them? Y/N: ");
        String answer = scanner.nextLine();
        if (answer.equals("Y") || answer.equals("y")) {
            addFavArtist(artistName);
        }

        return artistName;
    }
    
    // // Returns users top tracsk on spotify
    // // Helpful link from Spotify website: https://developer.spotify.com/documentation/web-api/reference/get-users-top-artists-and-tracks
    // // url I need to use https://api.spotify.com/v1/me/top/tracks
    // // Given authorization sample information from the developer website: Authorization: Bearer 1POdFZRZbvb...qqillRxMr2z, basically I need to use the token and have it be a bearer
    // public static String getUsersTopTracks() throws IOException {
    //     String token = SpotifyAPI.getAccessToken();
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
