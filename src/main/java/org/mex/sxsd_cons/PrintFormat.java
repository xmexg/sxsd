package org.mex.sxsd_cons;

public class PrintFormat {

    private static String COLOR;

    public static String
    NONE         = "\033[0m",
    DARK         = "\033[0;30m",
    LIGHT_DARK   = "\033[1;30m",
    RED          = "\033[0;31m",
    LIGHT_RED    = "\033[1;31m",
    GREEN        = "\033[0;32m",
    LIGHT_GREEN  = "\033[1;32m",
    YELLOW       = "\033[0;33m",
    LIGHT_YELLOW = "\033[1;33m",
    BLUE         = "\033[0;34m",
    LIGHT_BLUE   = "\033[1;34m",
    PURPLE       = "\033[0;35m",
    LIGHT_PURPLE = "\033[1;35m",
    CYAN         = "\033[0;36m",
    LIGHT_CYAN   = "\033[1;36m",
    WHITE        = "\033[0;37m",
    LIGHT_WHITE  = "\033[1;37m",
    HELP         = "\033[0;32m",
    OUT          = "\033[0;33m",
    ERROR        = "\033[0;31m",
    INPUT        = "\033[0;34m",
    OK           = "\033[0;32m";

    public PrintFormat(String color) {
        this.COLOR = color;
    }

    public static void print(String s, String color) {
        System.out.print(color + s + NONE);
    }

    public void print(String s) {
        print(s, COLOR);
    }

    public static void Sprint(String s) { // Sprint = System.out.print
        System.out.print(s);
    }

    public static void println(String s, String color) {
        print(s + "\n", color);
    }

    public void println(String s) {
        println(s, COLOR);
    }

    public static void Sprintln(String s) { // Sprintln = System.out.println
        System.out.println(s);
    }
    
    public static void setColor(String color) {
    	System.out.print(color);
    }
    
    public static void clearColor() {
    	setColor(NONE);
    }

}
