package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
        public static void clearConsole() {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
        
        public static void main( String[] args ) throws IOException, InterruptedException {
        ArrayList <String> updater = new ArrayList();
        Scanner scanner = new Scanner(System.in);
        System.out.println("♡   ∩_∩ \r\n" +
                        "╭ („•֊•„)♡  ୨୧ ┈┈ • ┈┈ • ┈┈ • ┈╮\r\n" +
                        "    U U \r\n" + "");
        System.out.print("• Welcome to Basic Spotify Stats.\n• Here you can look at the basic \n  stats of your favorite artists.\n What would you like to search for?\n");
        System.out.print("Menu: \n    1. Artist Information\n    2. User Information\n    3. Customize\n    4. Trivia\n    5. Exit\n  Please type a number: ");
        int option = scanner.nextInt();
        scanner.nextLine();
        while (option != 5) {

            if (option == 1) {
                clearConsole();
                System.out.print("\n\n    • Please type an artists name: ");
                String name = scanner.nextLine();
                Thread.sleep(1000);
                System.out.println("\n     ════════════════════════════════════");
                ArrayList<String> topTracks = SpotifyAPI.getArtistTopTracksRaw(name);
                System.out.print("    〢" + SpotifyAPI.formatTopTracks(topTracks));
                Thread.sleep(1000);
                System.out.println("    〢" + SpotifyAPI.getArtistFollowing(name));
                Thread.sleep(1000);
                System.out.println("    〢" +SpotifyAPI.getArtistPopularity(name));
                if (updater.indexOf(name) > -1) {
                    System.out.println("    〢" + SpotifyAPI.getModifiedArtistGenre(name));
                } else {
                    System.out.println("    〢" + SpotifyAPI.getArtistGenre(name));
                }         
                System.out.println("     ════════════════════════════════════\n");
                if (SpotifyAPI.getArtistGenre(name).equals("Genres: ") && updater.indexOf(name) == -1) {
                    System.out.print("\n\n    • It seems there is no saved genre for this artist.\n    • Would you like to add one? (Y/N)  ");
                    String answer = scanner.nextLine();
                    if (answer.equals("Y") || answer.equals("y")) {
                        System.out.print("    • Please input the desired genre: ");
                        answer = scanner.nextLine();
                        SpotifyAPI.setModifiedArtistGenre(name, answer);
                        updater.add(name);
                    }
                }
                System.out.print("\n    • Would you like to add one of their tracks onto your favorites list? (Y/N)  ");
                String answer = scanner.nextLine();
                if (answer.equals("Y") || answer.equals("y") ) {
                    System.out.print("    • Please input the desired index to add to your list: ");
                    int trackIdx = scanner.nextInt();
                    scanner.nextLine();
                    if (trackIdx > 0 && trackIdx <= topTracks.size()) {
                        String track = topTracks.get(trackIdx - 1);
                        SpotifyUser.addFavTrack(track);
                        System.out.println("    • Successfully added!");
                    } else {
                        System.out.println("    • Invalid index!");
                    }
                }
                System.out.print("\n\n    • Would you like to add this artist to your favorites list? (Y/N)  ");
                answer = scanner.nextLine();
                if (answer.equals("Y") || answer.equals("y") ) {
                    SpotifyUser.addFavArtist(name);
                    System.out.println("    • Successfully added!");
                }
                Thread.sleep(3000);
                clearConsole();
                    System.out.print("Menu: \n    1. Artist Information\n    2. User Information\n    3. Customize\n    4. Trivia\n    5. Exit\n  Please type a number: ");
                option = scanner.nextInt();
                scanner.nextLine();
                clearConsole();
            }

            if (option == 2) {
                System.out.print("What would you like to access?\n    1. Favorite Artists\n    2. Favorite Tracks\n    3. Exit\n  Please type a number: ");
                int access = scanner.nextInt();
                scanner.nextLine();
                if (access == 1) {
                    clearConsole();
                    System.out.println("\n" + SpotifyUser.getFavArtistList() + "\n");
                } else if (access == 2) {
                    clearConsole();
                    System.out.println("\n" + SpotifyUser.getFavoriteTrackList() + "\n");
                } else {
                    clearConsole();
                    System.out.print("Menu: \n    1. Artist Information\n    2. User Information\n    3. Customize\n    4. Trivia\n    5. Exit\n  Please type a number: ");
                    option = scanner.nextInt();
                    scanner.nextLine();
                }
            }

            if (option == 3) {

            }

            if (option == 4) {
            System.out.print("You made it to fun trivia!\nPlease enter an artist's name to learn about their albums: ");
            String triviaName = scanner.nextLine();
            SpotifyAPI.getArtistAlbums(triviaName);
            System.out.println("\n" + SpotifyAPI.getAlbumStats() + "\n");
            Thread.sleep(3000);
            clearConsole();
            System.out.print("Menu: \n    1. Artist Information\n    2. User Information\n    3. Customize\n    4. Trivia\n    5. Exit\n  Please type a number: ");
            option = scanner.nextInt();
            scanner.nextLine();
            }
        }

        // System.out.println("\n╰┈ • ┈┈ • ┈┈ • ୨୧ • ┈┈ • ┈┈ • ┈╯");
        
        System.out.print("\n♡   ∩_∩ \r\n" +
                "╭ („•֊•„)♡  ୨୧ ┈┈ • ┈┈ • ┈┈ • ┈╮\r\n" +
                "    U U \r\n" + "");
        System.out.println("Thank you so much for using my program!\nSee you later!\n╰┈ • ┈┈ • ┈┈ • ୨୧ • ┈┈ • ┈┈ • ┈╯\r\n" + "");
    }
}