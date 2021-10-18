package chatroom.util;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

public final class Validate {
    private static final Pattern USERNAME_REGEX = Pattern.compile("[a-zA-Z]");
    private static final Pattern COMMAND_REGEX  = Pattern.compile("[a-zA-Z]");

    public static boolean validUserName(String username) {
        if (!validString(username)) return false;
        else return USERNAME_REGEX.matcher(Character.toString(username.charAt(0))).matches();
    }

    private static boolean validString(String s) {
        if (Objects.isNull(s)) return false;
        else return !s.isBlank();
    }

    public static boolean validateCommand(String command) {
        return validateCommand(command, false);
    }

    public static boolean validateCommand(String command, boolean debug) {
        if (!validString(command)) return false;

        AtomicBoolean isValid = new AtomicBoolean(false);

        /*int indexOfTypedCommand = command.indexOf('[');
        if (indexOfTypedCommand > -1) {
            command = command.substring(0, indexOfTypedCommand);
        }*/

        final String finalCommand = command;
        if (debug) System.out.println("requested command = " + finalCommand);
        Commands.commands().stream().forEach(cmd -> {
            int indexOfCommand = cmd.indexOf('[');
            if (indexOfCommand > -1) {
                cmd = cmd.substring(0, indexOfCommand);
            }

            if (debug) System.out.println("cmd = " + cmd);

            if (finalCommand.trim().startsWith(cmd.trim())) {
                isValid.set(true);
                return;
            }
        });
        return isValid.get();
    }
}
