package com.example;

public class XYZTests {
    public static void main(String[] args) {
        
        // TESTING TESTING ----------------------------------------------------------------------------------------------------------

        // System.out.println(SpotifyAPI.getArtistTopTracks("Atta Boy"));
        // // SpotifyAPI.setModifiedArtistGenre("Atta Boy", "indie");
        // // System.out.println(SpotifyAPI.getModifiedArtistGenre("Atta Boy"));
        // // System.out.println(SpotifyAPI.getAccessToken());
        // // System.out.println(SpotifyAPI.getArtistInfoFromAPI("Atta Boy"));
        // // System.out.println(SpotifyAPI.getOneArtistInfo("Atta Boy"));
        // System.out.println(SpotifyAPI.getArtistFollowing("Atta Boy"));
        // System.out.println(SpotifyAPI.getArtistPopularity("Atta Boy"));
        // System.out.println(SpotifyAPI.getArtistGenre("Atta Boy"));

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
}
