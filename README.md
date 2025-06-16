***Project Overview – Basic Spotify Stats***

For this project, I used a Spotify API in order to access different levels of information about artists on Spotify given a search query and give the user a chance to customize the way the information is presented. The information the user can see is the artist's following, popularity, genres associated with the artist, their top tracks, and trivia from the artists too.

--------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------

***Code Breakdown***

**App.java**

  *main(String[] args)*

The main method is the entry point of the program. It begins by asking the user to input an option through the use of number prompts. The menu goes as follows: 1. Artist Information    2. User Information    3. Customize    4. Trivia    5. Exit".

If the user types 1, they will be redirected to a search bar where they can type in the name of an artist. After typing this name, they will be provided with information about the artists following, their top 5 most popular songs, their popularity level calculated by Spotify out of a 100 point scale, and an array of their listed genres. The Spotify API provided by Spotify itself is not fully functional, however, so some genre arrays are listed as empty. If a genre array is found to be empty, the user will have the option to add a genre of their own, and this will be saved with the rest of the artist's information with a separate method and if their name is called again the new modified genre will be displayed instead of an empty list. The user then gets the option of adding a track from the top 5 tracks into their favorite tracks, given a y/n prompt, and if they type y they will be asked to provide an index. The same thing happens after for favorite artists, and the currently searched artist will be added to their favorites list. Both of these can be accessed if the user picks User Information. They will be looped back to the original menu.

If the user picks User Information, they will then be shown another menu. The menu consists of 1. Favorite Artists    2. Favorite Tracks    3. Exit. If the user picks 1, they will be shown a formatted list of their favorite artists, before being shown this mini menu again. If they pick 2, they will be shown a formatted list of their favorite tracks, before being shown the mini menu again. They will be redirected to the main menu if they pick 3.

If the user picks 3, they will be asked to type a new border they want before being redirected to the main menu again. The border they inputted will be used for future displays of information, such as the information displayed when you pick 1 in the main menu for Artists Information. They will then be redirected to the main menu.

If the user picks 4, they will be presented with a prompt to enter an artist's name to see trivia. Once they have entered an artist's name, they will be presented with the total number of tracks they have, and the total duration of all of those tracks added up. They will then be redirected to the main menu.

If the user picks 5, they will exit the program and be presented with a goodbye message!

--------------------------------------------------------------------------------------------------------------

**SpotifyAPI.java**

  *getAccessToken()*
  
This is a provided method which calls an API request to obtain an access token every time it runs. It returns the access token in the form of a String.

  *getArtistInfoFromAPI(String artist)*
  
This method utilizes getAccessToken() in order to obtain a JSON object from the API containing all of the possible search results with the provided artist name. The method appends different aspects of an URL together using the token provided previously and the artist name as a query to establish a full URL link to obtain the JSON information from the API. It returns the JSON object as a String.

  *getOneArtistInfo(String artist)*
  
This method provides the user with the first search result of getArtistInfoFromAPI(String artist). It does this by calling getArtistInfoFromAPI(String artist) and parsing through the section of “artists” and obtaining the first index of that section, or index 0. The return value of this method is a JSON file containing everything related to the artist, such as followers, popularity levels, images, and even genres.

  *getArtistFollowing(String artist)*
  
This method utilizes getOneArtistInfo(String artist) to gather all of the information of the search artist. It then parses through the JSON object in order to obtain the information under the section “followers”, which it then returns as a String with "Followers: (number)".

  *getArtistPopularity(String artist)*
  
This method also uses getOneArtistInfo(String artist) to obtain all of the information regarding the searched artist. It then parses through the JSON object to obtain information under the “popularity” section, which it returns as a String with “Popularity: (number) out of a 100”.

  *getArtistGenre(String artist)*
  
This method also uses getOneArtistInfo(String artist) to obtain all of the information regarding the searched artist. It then parses through the JSON object to obtain information under the “genres” section, which it returns as a String "Genres: " followed by an appended String containing all the listed genres provided by the API.
	
  *getModifiedArtistGenre(String artist)*
  
This is a getter method. This method will return a String of genres associated with the given artists that has been stored in modGenre. 

To fully understand this, we need to look at modGenre, a private static String[][] which has a grid 1000 x 1000. This variable stores the artist name in the first column, or column index 0, and in the following columns of that specific row the saved genres will be stored. 

This method takes the artists name we want to find the genres for and it will begin iterating through each value in the modGenre 2D Array with a nested for loop, and when it finds a row with a column at index 0 that has an item that matches the artist String in the parameter, it will return a list, formatted, with the genres that are stored in the following columns with the associated artist name (which always resides in the first column of the row).
	
  *setModifiedArtistGenre(String artist, String newGenre)*
  
This is a setter method, it does not return anything. This method will add the artist's name and the desired new genre into modGenre, our 2D array. 

This method will iterate through modGenre, find a row that has an empty first column (column index 0), and add the artist's name to that location. Then, it will add the provided genre into the following column of that same row. 

  *getArtistTopTracksRaw(String artist)*
  
This is a getter method. This method will return an ArrayList<String> of the top 5 tracks of the given artist. It uses getOneArtistInfo(String artist) and getAccessToken() to make a search query.

A search query using the artist's name will be made and a JSON Object will be returned, then it will be parsed through to find a section labeled "tracks". From there, we iterate through the items in the object section and get the first 5 names by parsing through the object and finding the section "name", which gets added to the ArrayList and returned. No formatting will be made, as we need the "raw" version for a feature found in App.java and SpotifyUser.java.

  *formatTopTracks(ArrayList<String> tracks)*
  
This method will return a String of  the top tracks from the given artist found in getArtistTopTracksRaw(String artist), formatted in a list. This method will iterate for the tracks length to add them into a String all formatted with bullet points 1-5.

  *getArtistAlbums(String artistName)*
  
This method will retrieve all the albums of the given artist and update the number of total tracks and the number of total duration of the tracks too, adding them all up. It uses getOneArtistInfo(String artist) and getAccessToken() in order to make a search query for albums, then it will parse through the JSON Object returned from that search to find items. From there, it makes a search query for tracks in said album and parses through the tracks for individual items before parsing through that object to find the duration_ms for each track in a given album.

Spotify only allows 50 results per "page" of search results, so because some artists have more than 50 albums,we have an offset and limit variables too. Our limit starts off at 50 and our offset at 0. If we reach the end of parsing through all albums on the first page and there are more albums left, we add 50 to offset and repeat all the parsing.

  *getAlbumStats()*
  
This method formats the album stats we find using the previous method and returns a String. A mathematical operation is made here, as we convert totalDurationMs into minutes, and then return a String listing the total tracks across albums and the total amount of minutes.
SpotifyUser.java

  *addFavArtist(String fav)*
  
This method returns a boolean value when the addition is complete. It will add the given parameter into an ArrayList<String> for favorite artists.

  *getFavArtistList()*
  
This method returns a String of all the items in favorite ArrayList<String>, which is an array list of all the current user's favorite artists.

If the ArrayList<String> is empty, the user will receive a message indicating so. If not, then the method will iterate through all the items of the favorite artists list and format them with numbered bullet points before returning it to the user.

  *addFavTrack(String fav)*
  
This method returns a boolean value when the addition is complete. It will add the given parameter into an ArrayList<String> for favorite tracks.

  *getFavoriteTrackList()*
  
This method returns a String of all the items in favoriteTracks ArrayList<String>, which is an array list of all the current user's favorite tracks.

If the ArrayList<String> is empty, the user will receive a message indicating so. If not, then the method will iterate through all the items of the favorite tracks list and format them with numbered bullet points before returning it to the user.

--------------------------------------------------------------------------------------------------------------

**Customize.java**
  
  *Customize()*
  
This method initializes the String variable border to the default border "look", "════════════════════════════════════".

  *getBorder()*
  
This method returns a String of the border.

  *setBorder(String newBorder)*
  
This method is a setter method, it changes the border to a given String parameter.


--------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------

***Features Implemented***

In this project, I implemented:
Base Project (+88%)
Statistics/ Machine Learning/Basic Computation (+6%)
Filter/Sort Data (+2%)
Written Walk Through (+5%)

My Base Project category is complete because I took information from an API and made it into accessible information for the user.

My Basic Computation category is complete because I performed basic computations in order to convert the total duration of the tracks that comes in ms into minutes in getAlbumStats().

My Filter/Sort Data category is complete because I parse through a bunch of information to find different things for the user, such as top tracks, popularity, all of the tracks, etc.

--------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------

***Output Example***


![Screenshot 2025-06-15 224036](https://github.com/user-attachments/assets/2a299cc7-0f47-4c1c-b25d-03bdb9b12030)

If option is 1:

![Screenshot 2025-06-15 224105](https://github.com/user-attachments/assets/780e56ea-a669-43a5-a2f3-e32f241cad12)

If option is 2:

![Screenshot 2025-06-15 224213](https://github.com/user-attachments/assets/ba2ea83e-f563-4629-a50e-1455ae381811)

  -If option is 1:


  ![Screenshot 2025-06-15 224238](https://github.com/user-attachments/assets/1a449cf7-5d70-4ee0-8004-b9cb91b7fe87)

	
  -If option is 2:
	
  ![Screenshot 2025-06-15 224310](https://github.com/user-attachments/assets/103d9ad9-8ab3-4212-b0c2-443ca18575b3)


If option is 4:

![Screenshot 2025-06-15 224414](https://github.com/user-attachments/assets/c373b891-96a7-4eab-a4c5-17e05cd9588c)

--------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------

***What I learned***
For this project, I learned:
- How to request information from API's
- How to parse through JSON Files, especially layered files
- How to make a system with multiple pathways/options for the user to take them to different functions
