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

    /*
     * Adds the parameter (which is meant to be an input from the user)
     * To the favorite array list
     * returns true if addition successful
     */
    public static boolean addFavArtist(String fav) { 
        favorite.add(fav);
        return true;
    }

    /*
     * Returns artists list with formatting (bullet points)
     */
    public static String getFavArtistList() {
        if (favorite.isEmpty()) {
            return "You have no favorite artists saved.";
        }
        String everything = "Your Favorite Artists:\n";
        for (int i = 0; i < favorite.size(); i++) {
            everything += "  " + (i + 1) + ". " + favorite.get(i) + "\n";
        }
        return everything;
    }

    /*
     * Adds the parameter (which is meant to be an input from the user)
     * To the favoriteTracks array list
     * returns true if addition is successful
     */
    public static boolean addFavTrack(String fav) {
        favoriteTracks.add(fav);
        return true;
    }

    /*
     * Returns the favorite track list formatted (with bullet points)
     */
    public static String getFavoriteTrackList() {
        if (favorite.isEmpty()) {
            return "You have no favorite tracks saved.";
        }
        String everything = "Your Favorite Tracks:\n";
        for (int i = 0; i < favoriteTracks.size(); i++) {
            everything += "  " + (i + 1) + ". " + favoriteTracks.get(i) + "\n";
        }
        return everything;
    }
}
