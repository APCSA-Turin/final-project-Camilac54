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
    private static ArrayList<String> favorite = new ArrayList<String>();
    private static ArrayList<String> favoriteTracks = new ArrayList<String>();

    public static boolean addFavArtist(String fav) {
        favorite.add(fav);
        return true;
    }

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

    public static boolean addFavTrack(String fav) {
        favoriteTracks.add(fav);
        return true;
    }

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
