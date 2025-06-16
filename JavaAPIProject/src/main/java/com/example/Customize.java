package com.example;
 
public class Customize {
    // Static variable to store border
    private static String border;
    
    /*
     * Initializes border to default
     */
    public Customize(){
        border = "════════════════════════════════════";
    }

    /**
     * This method is a getter method,
     * @return border String 
     */
    public static String getBorder() {
        return border;
    }

    /**
     * This method is a setter method, it changes border to given parameter 
     * @param newBorder , a String that the user inputs to set the new border String 
     */
    public static void setBorder(String newBorder) {
        border = newBorder;
    }

// COLOR FEATURE NOT YET IMPLEMENTED, IN PROGRESS

    // Found on https://www.w3schools.blog/ansi-colors-java

    // public static final String RESET = "\033[0m";  // Text Reset
    // public static final String RED = "\033[0;31m";     // RED, 1
    // public static final String GREEN = "\033[0;32m";   // GREEN, 2
    // public static final String YELLOW = "\033[0;33m";  // YELLOW, 3
    // public static final String BLUE = "\033[0;34m";    // BLUE, 4
    // public static final String PURPLE = "\033[0;35m";  // PURPLE, 5
    // public static final String CYAN = "\033[0;36m";    // CYAN, 6
    // public static final String WHITE = "\033[0;37m";   // WHITE, default

    // public static String color = "";
    // public static void setColor(int num) {
    //     if (num == 1) {
    //         color = RED;
    //     } else if (num == 2) {
    //         color = GREEN;
    //     } else if (num == 3) {
    //         color = YELLOW;
    //     } else if (num == 4) {
    //         color = BLUE;
    //     } else if (num == 5) {
    //         color = PURPLE;
    //     } else if (num == 6) {
    //         color = CYAN;
    //     } else {
    //         color = WHITE;
    //     }
    // }

}
