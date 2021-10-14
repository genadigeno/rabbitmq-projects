package eno.practice.rabbitmqspringboot.util;

public class Text {

    /** https://en.wikipedia.org/wiki/ANSI_escape_code#8-bit */
    public static final String ANSI_RESET  = "\u001B[0m";
    public static final String ANSI_BLACK  = "\u001B[30m";
    public static final String ANSI_RED    = "\u001B[31m";
    public static final String ANSI_GREEN  = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE   = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN   = "\u001B[36m";
    public static final String ANSI_WHITE  = "\u001B[37m";

    public static final String ANSI_BETTER_GREEN = "\u001B[38;5;46m";
    public static final String ANSI_BETTER_RED   = "\u001B[38;5;196m";

    public static final String ANSI_UP   = "\u23F6";//⏶
    public static final String ANSI_DOWN = "\u23F7";//⏷

    public static String red(String text) {
        return ANSI_BETTER_RED+text+ANSI_RESET;
    }

    public static String purple(String text) {
        return ANSI_PURPLE+text+ANSI_RESET;
    }

    public static String green(String text) {
        return ANSI_BETTER_GREEN+text+ANSI_RESET;
    }
}
