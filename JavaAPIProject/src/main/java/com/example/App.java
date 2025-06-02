package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Thread;

public class App {
        public static void main( String[] args ) throws IOException, InterruptedException {
        ArrayList <String> updater = new ArrayList();
        Scanner scanner = new Scanner(System.in);
        System.out.println("♡   ∩_∩ \r\n" +
                        "╭ („•֊•„)♡  ୨୧ ┈┈ • ┈┈ • ┈┈ • ┈╮\r\n" +
                        "    U U \r\n" + "");
        System.out.print("• Welcome to Spotify Basic Stats.\n• Here you can look at the basic \n  stats of your favorite artists.\n What would you like to search for? (Please type 1 or 2)\n    1. Artist Information\n    2. User Information\n");
        int option = scanner.nextInt();
        // System.out.println("\n╰┈ • ┈┈ • ┈┈ • ୨୧ • ┈┈ • ┈┈ • ┈╯");
        if (option == 1) {
        boolean leave = false;
        boolean change = false;
                while (leave == false) {
                    System.out.print("\n\n--Please type an artists name: ");
                    String name = scanner.nextLine();
                    Thread.sleep(1000);
                    System.out.println("\n     ════════════════════════════════════");
                    System.out.print("    〢" + SpotifyAPI.getArtistTopTracks(name));
                    Thread.sleep(1000);
                    System.out.println("    〢" + SpotifyAPI.getArtistFollowing(name));
                    Thread.sleep(1000);
                    System.out.println("    〢" +SpotifyAPI.getArtistPopularity(name));

                    if (updater.indexOf(name) > -1) {
                        // new genre method
                        System.out.println("    〢" + SpotifyAPI.getModifiedArtistGenre(name));
                    } else {
                        System.out.println("    〢" + SpotifyAPI.getArtistGenre(name));
                    }         
                    System.out.println("     ════════════════════════════════════\n");

                    if (SpotifyAPI.getArtistGenre(name).equals("Genres: ") && updater.indexOf(name) == -1) {
                        System.out.print("\n•It seems there is no saved genre for this artist.\n --Would you like to add one?\nY/N: ");
                        String answer = scanner.nextLine();
                        if (answer.equals("Y") || answer.equals("y")) {
                            // answer = scanner.nextLine();
                            // System.out.println("Genres: " + answer);
                            System.out.print("\n\n--Please input the desired genre: ");
                            answer = scanner.nextLine();
                            SpotifyAPI.setModifiedArtistGenre(name, answer);
                            // System.out.println("\n" + SpotifyAPI.getModifiedArtistGenre(name) + "\n"); // no longer needed but I'll keep it for now
                            updater.add(name);
                        }
                    }
                    System.out.print("Menu: \n    1. Search\n    2. User Information\n    3. Exit\n  Please type a number: ");
                    int answer = scanner.nextInt();
                    if (answer == 1) {
                        continue;
                    } else if (answer == 2) {
                        change = true;
                        leave = true;
                    } else {
                        leave = true;
                    }
                }
                
                if (change) {
                    System.out.println("Let's take a look at your favorites list!");

                }
        } 
        
        System.out.print("\n♡   ∩_∩ \r\n" +
                "╭ („•֊•„)♡  ୨୧ ┈┈ • ┈┈ • ┈┈ • ┈╮\r\n" +
                "    U U \r\n" + "");
        System.out.println("Thank you so much for using my program!\nSee you later!\n╰┈ • ┈┈ • ┈┈ • ୨୧ • ┈┈ • ┈┈ • ┈╯\r\n" + "");

        // --------------------------------------------------------------------------------------------------------------------------------
        // System.out.println(SpotifyAPI.getArtistTopTracks("Atta Boy"));
        // // SpotifyAPI.setModifiedArtistGenre("Atta Boy", "indie");
        // // System.out.println(SpotifyAPI.getModifiedArtistGenre("Atta Boy"));
        // // System.out.println(SpotifyAPI.getAccessToken());
        // // System.out.println(SpotifyAPI.getArtistInfoFromAPI("Atta Boy"));
        // // System.out.println(SpotifyAPI.getOneArtistInfo("Atta Boy"));
        // System.out.println(SpotifyAPI.getArtistFollowing("Atta Boy"));
        // System.out.println(SpotifyAPI.getArtistPopularity("Atta Boy"));
        // System.out.println(SpotifyAPI.getArtistGenre("Atta Boy"));
    }
}