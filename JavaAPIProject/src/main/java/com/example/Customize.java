package com.example;

public class Customize {
    private static String border;
    
    public Customize(){
        border = "════════════════════════════════════";
    }
    
    public static String getBorder() {
        return border;
    }

    public static void setBorder(String newBorder) {
        border = newBorder;
    }
}
