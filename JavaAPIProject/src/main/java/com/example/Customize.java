package com.example;

public class Customize {
    /*
     * Simple class to update border in App.java
     */

    // Static variable to store border
    private static String border;
    
    /*
     * Initializes border to default
     */
    public Customize(){
        border = "════════════════════════════════════";
    }

    /*
     * Returns border string
     */
    public static String getBorder() {
        return border;
    }

    /*
     * Changes border to parameter
     */
    public static void setBorder(String newBorder) {
        border = newBorder;
    }
}
