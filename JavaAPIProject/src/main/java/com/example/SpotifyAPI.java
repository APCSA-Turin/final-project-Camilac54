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
 * Most of the information regarding Spotify API was found here: https://developer.spotify.com/documentation/web-api
 * For future reference on learning how to add user login, go here: https://medium.com/@alex.ginsberg/beginners-guide-to-the-spotify-web-api-bade6aa2d47c
 * Coded by Camila C.
 */

public class SpotifyAPI {
    private static String[][] modGenre = new String[1000][1000]; // A 2D array meant to hold all modified genres
    private static int totalDurationMs = 0; // int variable meant to keep total duration of selected tracks
    private static int totalTracks = 0; // int variable meant to keep the total number of tracks from selected albums 
    
    public static void main( String[] args ) throws IOException {}
    
    /**
     * Main access method, calls api request every time it runs
     * @return a Strng token used for other methods to access information
     * @throws IOException
     */
    public static String getAccessToken() throws IOException { // WORKS
        String auth = "76760c2f393042f5a9f5941b5df26890" + ":" + "1ac1b639a03f46bba96435969b854c40"; 
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes()); 
        URL url = new URL("https://accounts.spotify.com/api/token"); // Search query
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

    /**
     * A general method, used mainly for reference to obtain all possible search results from artist name
     * @param artist , a given String that the user will input as a search query
     * @return all search results related to the given artist String, from most likely to match to least likely
     * @throws IOException
     */
    public static String getArtistInfoFromAPI(String artist) throws IOException { // WORKS
        String token = getAccessToken(); // Uses previous method getAccessToken()
        // System.out.println(token); // testing
        String query = artist; 
        URL url = new URL("https://api.spotify.com/v1/search?q=" + URLEncoder.encode(query, "UTF-8") + "&type=artist"); // Search query 
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

    /**
     * This method will return only the first and most relevant information "pack" given the artist
     * @param artist , a given String that the user will input as a search query
     * @return the information of the first result given the artist name
     * @throws IOException
     */
    public static JSONObject getOneArtistInfo(String artist) throws IOException { // WORKS
        String everything = getArtistInfoFromAPI(artist); // Obtaining the String from the search query using getArtistInfoFromAPI(String artist)
        JSONObject mainObj = new JSONObject(everything); // Converts it into a JSON file
        JSONObject artists = mainObj.getJSONObject("artists"); // First parse through the JSON object to find "artists"
        JSONObject firstResult = artists.getJSONArray("items").getJSONObject(0); // Second parse through the JSON object to find items in the first index
        return firstResult; 
    }

    /**
     * This method will parse through the information provided by getOneArtistInfo(String artist) and find the artists folowing
     * @param artist , a given String that the user will input as a search query
     * @return a String with the total follower count of the artist, formatted with "Total followers: " + integer of followers 
     * @throws IOException
     */
    public static String getArtistFollowing(String artist) throws IOException { // WORKS
        String token = getAccessToken(); // Uses previous method getAccessToken() 
        // System.out.println(token); // testing
        String query = artist; 
        URL url = new URL("https://api.spotify.com/v1/search?q=" + URLEncoder.encode(query, "UTF-8") + "&type=artist"); // Search query
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
        conn.setRequestProperty("Authorization", "Bearer " + token); 
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
        String inputLine; 
        StringBuilder response = new StringBuilder(); 
        while ((inputLine = in.readLine()) != null) response.append(inputLine); 
        in.close();
        String json = response.toString(); // JSON object from search query, converted into a String
        JSONObject mainObj = new JSONObject(json); // JSON object that will be parsed through
        JSONObject artists = mainObj.getJSONObject("artists"); // First parse, finding "artists"
        JSONObject firstResult = artists.getJSONArray("items").getJSONObject(0); // Second parse, finding "items" at index 0
        JSONObject followers = firstResult.getJSONObject("followers"); // Third parse, finding "followers"
        int total = followers.getInt("total"); // Final parse, finding "total" int of followers
        return "Total followers: " + total;
    }
    
    /**
     * This method will parse through the information given by getOneArtistInfo(String artist) and return the artists popularity 
     * @param artist , a given String that the user will input as a search query
     * @return a String with the calculated popularity of the artist, a number given by Spotify, formatted with "Popularity Level: (int) out of a 100"
     * @throws IOException
     */
    public static String getArtistPopularity(String artist) throws IOException { // WORKS
        JSONObject everything = getOneArtistInfo(artist); // Making a JSON Object with information from getOneArtistInfo(String artist)
        int popularity = everything.getInt("popularity"); // Parsing through it to find the section of "popularity", which contains an int
        return "Popularity Level: " + popularity + " out of 100";
    }

    /**
     * This method will parse through the information given by getOneArtistInfo(String artist) and return a list of all the given genres
     * Note, some genres cannot be found, even for big artists, and this is an issue from Spotify API
     * @param artist , a given String that the user will input as a search query
     * @return A string that contains a list of all the genres the given artist has listed on Spotify
     * @throws IOException
     */
    public static String getArtistGenre(String artist) throws IOException { // WORKS
        JSONObject everything = getOneArtistInfo(artist); // Making a JSON Object with information from getOneArtistInfo(String artist)
        JSONArray genreList = everything.getJSONArray("genres"); // Parsing through the JSON Object to find "genres"
        StringBuilder genres = new StringBuilder("Genres: "); // A StringBuilder to format everything 
        for (int i = 0; i < genreList.length(); i++) { // Iterates through all the items of genreList to format them
            genres.append(genreList.getString(i)); // Appends each genre to index
            if (i < genreList.length() - 1) { // checks if it's the last item yet to add a space for the list
                genres.append(" ");
            }
        }
        return genres.toString();
    }

    /**
     * This method will return the saved genre from the given artist
     * @param artist , a given String that the user will input as a search query
     * @return A string that contains a list of the genres that were saved into the modGenre 2d array
     */
    public static String getModifiedArtistGenre(String artist) { // WORKS
        String genreString = "";
        for (int r = 0; r < modGenre.length; r ++) { // iterates through the rows of modGenre
            if ((artist.equals(modGenre[r][0]))) { // Checks if the first index (index 0) of the given row matches the artist provided
                for (int c = 1; c < modGenre[0].length; c ++) { // If true, iterates through the columns of the row
                    if (modGenre[r][c] != null) { // If the column isn't empty, it adds the item into a string, formatted with a space
                        genreString += modGenre[r][c] + " ";
                        // System.out.println(genreString); // testing 
                    } else {
                        break; // If it is empty, break the loop
                    }
                } 
            }
        }
        return "Genres: " + genreString;
    }

    /**
     * This method adds/updates the modified genre list based off the users input
     * @param artist , a given String that the user will input as a search query
     * @param newGenre , a given String that represents the new genre the user wants to add
     */
    public static void setModifiedArtistGenre(String artist, String newGenre) { // WORKS
        for (int r = 0; r < modGenre.length; r ++) { // iterates through the modGenre list to find empty column so we can input the artist and genre
            if (modGenre[r][0] == null) { // adds artist to the first column (column at index 0) if it's null
                modGenre[r][0] = artist; 
                for (int c = 1; c < 2; c ++) { // adds the new genre into the second column (column at index 1)
                    modGenre[r][c] = newGenre;
                }
                break; // breaks loop
            }
        }
    }

    /**
     * This method returns the top 5 tracks from the given artist
     * Lack of formatting is needed for the favorite feature found in SpotifyUser.java and App.java
     * @param artist , a given String that the user will input as a search query
     * @return An ArrayList with unformatted track names
     * @throws IOException
     */
    public static ArrayList<String> getArtistTopTracksRaw(String artist) throws IOException {
        String token = getAccessToken();
        JSONObject artistInfo = getOneArtistInfo(artist);
        String artistId = artistInfo.getString("id");
        URL url = new URL("https://api.spotify.com/v1/artists/" + artistId + "/top-tracks?market=US"); // Search query, looking for tracks
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Authorization", "Bearer " + token);
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) response.append(inputLine);
        in.close();    
        JSONObject obj = new JSONObject(response.toString());
        JSONArray tracks = obj.getJSONArray("tracks"); // Parsing to find tracks 
        ArrayList<String> trackList = new ArrayList<>();
        for (int i = 0; i < Math.min(5, tracks.length()); i++) { // Obtaining the first 5 tracks, or top 5 tracks
            JSONObject track = tracks.getJSONObject(i);
            trackList.add(track.getString("name")); // In the tracks, it gets specifically their name and adds it to the arrayList
        }
        return trackList;
    }

     /**
      * This method formats the top tracks from a given artist found in getArtistTopTracksRaw(String artist)
      * This is more of a helper method, as it is needed to format and present the tracks to the user
      * @param tracks , an array returned by getArtistTopTracksRaw(String artist)
      * @return a String of formatted tracks (top 5) in a list counting from 1-5
      */
    public static String formatTopTracks(ArrayList<String> tracks) {
        String result = "Top Tracks:\n";
        for (int i = 0; i < tracks.size(); i++) { // iterates through track size to put them in a list
            result += "      " + (i + 1) + ". " + tracks.get(i) + "\n";
        }
        return result;
    }

    /**
     * This method retrives all the albums from a given artist, updates totalTracks number and totalDurationMs
     * Meaning, this looks through all the albums, adds up the total number of tracks and the total duration of everything too
     * Does not return anything
     * @param artistName , a given String that the user will input as a search query
     * @throws IOException
     */
    public static void getArtistAlbums(String artistName) throws IOException {
        totalDurationMs = 0; // Initialize to 0
        totalTracks = 0; // Initialize to 0
        String token = getAccessToken();
        JSONObject artistInfo = getOneArtistInfo(artistName);
        String artistId = artistInfo.getString("id");
        int offset = 0; // Used to switch "pages" with Spotifys information and get more albums 
        int limit = 50; // Spotify limits 50 results per page, so this is our starting limit that can later be changed by offset
        boolean moreAlbums = true; // A boolean for an inifite loop of search through albums
        while (moreAlbums) {
            URL url = new URL("https://api.spotify.com/v1/artists/" + artistId + "/albums?include_groups=album&limit=" + limit + "&offset=" + offset); // Search query, looking for albums
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "Bearer " + token);
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) response.append(inputLine);
            in.close();
            JSONObject json = new JSONObject(response.toString());
            JSONArray items = json.getJSONArray("items"); // First parse through the JSON Object to find items (albums) returned in current "page"
            if (items.length() == 0) { // Checking if our infinite loop should break yet based off items length
                moreAlbums = false;
                break;
            }
            for (int i = 0; i < items.length(); i++) { // Processing albums by iterating through each of them and their songs on current "page"
                JSONObject album = items.getJSONObject(i);
                String albumId = album.getString("id");
                URL tracksUrl = new URL("https://api.spotify.com/v1/albums/" + albumId + "/tracks"); // Second search query, looking at tracks
                HttpURLConnection trackConn = (HttpURLConnection) tracksUrl.openConnection();
                trackConn.setRequestProperty("Authorization", "Bearer " + token);
                BufferedReader trackIn = new BufferedReader(new InputStreamReader(trackConn.getInputStream()));
                StringBuilder trackResponse = new StringBuilder();
                while ((inputLine = trackIn.readLine()) != null) trackResponse.append(inputLine);
                trackIn.close();
                JSONArray tracks = new JSONObject(trackResponse.toString()).getJSONArray("items"); // Parsing through the given index of our first parsed JSON Object, looking for items
                totalTracks += tracks.length(); // Based on the amount of tracks from the given item, to be used next
                for (int j = 0; j < tracks.length(); j++) { // Based of totalTracks, loop through them to find duration of each
                    JSONObject track = tracks.getJSONObject(j);
                    totalDurationMs += track.getInt("duration_ms"); // Add the duration of each by parsing through to find "duration_ms"
                }
            }
            offset += limit; // Update offset to move onto the "next page" of Spotify API search results
        }
    }

    /**
     * This method provided a formatted String of how many minutes the artist has across albums and their track numbers
     * Note, this number is not accurate because of issues with Spotify API itself
     * @return A String that contains the number of tracks an artist has and their total duration overall
     */
    public static String getAlbumStats() {
        int minutes = totalDurationMs / (1000 * 60);
        return "They have " + totalTracks + " tracks across albums, totaling about " + minutes + " minutes of music!";
    }
}