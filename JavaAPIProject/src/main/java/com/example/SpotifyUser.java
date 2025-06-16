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
 
public class SpotifyUser { 
    /*
     * Two variables that will store information that is inputted in App.java
     * All from the user
     */
    private static ArrayList<String> favorite = new ArrayList<String>();
    private static ArrayList<String> favoriteTracks = new ArrayList<String>();

    /**
     * This method adds the parameter into the ArrayList for favorite artists
     * @param fav , a String (name) that the user wants added to their favorites list
     * @return true if addition is successful
     */
    public static boolean addFavArtist(String fav) { 
        favorite.add(fav);
        return true;
    }

     /**
      * This method shows the user all their favorited artists so far
      * @return A String containing all the items in favorite artists list, with bullet points (formatted)
      */
    public static String getFavArtistList() {
        if (favorite.isEmpty()) { // If there are no artists saved so far, it returns a message indicating so
            return "You have no favorite artists saved.";
        }
        String everything = "Your Favorite Artists:\n"; // Formatting string starter
        for (int i = 0; i < favorite.size(); i++) { // Iterating through every item in favorite arrayList, and formats it 
            everything += "  " + (i + 1) + ". " + favorite.get(i) + "\n";
        }
        return everything;
    }

    /**
     * This method adds the parameter into the ArrayList for favorite tracks
     * @param fav , a String (name) that the user wants added to their favorites list
     * @return true if addition is successful
     */
    public static boolean addFavTrack(String fav) {
        favoriteTracks.add(fav);
        return true;
    }


    /**
     * This method shows the user all their favorited tracks so far
     * @return A String containing all the items in favorite tracks list, with bullet points (formatted)
     */
    public static String getFavoriteTrackList() {
        if (favorite.isEmpty()) { // If there are no tracks saved so far, it returns a message indicating so
            return "You have no favorite tracks saved.";
        }
        String everything = "Your Favorite Tracks:\n"; // Formatting string starter
        for (int i = 0; i < favoriteTracks.size(); i++) { // Iterating through every item in favorite tracks arrayList, and formats it 
            everything += "  " + (i + 1) + ". " + favoriteTracks.get(i) + "\n";
        }
        return everything;
    }
}
