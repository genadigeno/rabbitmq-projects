package eno.practice.rabbitmqspringboot;

import org.junit.jupiter.api.Test;

public class UsualJunitTest {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BETTER_GREEN = "\u001B[38;5;46m";
    public static final String ANSI_BETTER_RED   = "\u001B[38;5;196m";

    @Test
    public void test() {
        System.out.println(ANSI_BETTER_GREEN+"\u23F6 34.98$"+ANSI_RESET);
        System.out.println(ANSI_BETTER_RED+"\u23F7 32.18$"+ANSI_RESET);
    }
}
