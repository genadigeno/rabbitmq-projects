package chatroom.util;

import chatroom.exeptions.IncorrectCommandExpression;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public final class Commands {
    private Commands() {}

    public static final String CANCEL_LOGGING_IN = "cancel";

    public static final String LEAVE_ROOM = "-cmd leave room";//
    public static final String ENTER_ROOM = "-cmd enter [roomName]";//
    public static final String SHOW_ROOMS = "-cmd show rooms";//
    public static final String SHOW_ROOM  = "-cmd show room [roomName]";
    public static final String REFRESH    = "-cmd refresh";
    public static final String EXIT_CHAT  = "-cmd exit";//

    private static final List<String> list = new ArrayList<>();

    public static List<String> commands() {
        list.add(LEAVE_ROOM);
        list.add(ENTER_ROOM);
        list.add(SHOW_ROOMS);
        list.add(SHOW_ROOM);
        list.add(REFRESH);
        list.add(EXIT_CHAT);
        return list;
    }

    public static String value(String cmd) throws IncorrectCommandExpression {
        if (!Validate.validateCommand(cmd) || !cmd.startsWith("-cmd ")) { //?
            throw new IncorrectCommandExpression(cmd+" command is invalid");
        } else if (isEnterIntoRoom(cmd)) {
            return of("-cmd enter", cmd);
        } else if (isLeavingTheRoom(cmd)) {
            return "";//?
        } else {
            throw new IncorrectCommandExpression(cmd+" command is invalid");
        }
    }

    private static String of(String carcass, String cmd) throws IncorrectCommandExpression {
        int count = StringUtils.countMatches(cmd, "\"");
        if (count == 0) {
            return cmd.replace(carcass, "").trim();
        } else if (count % 2 == 0) {
            return cmd.replace(carcass, "").replaceAll("\"", "").trim();
        } else {
            throw new IncorrectCommandExpression(cmd+" command is invalid");
        }
    }

    public static boolean isEnterIntoRoom(String cmd) {
        if (!Validate.validateCommand(cmd) || !cmd.startsWith("-cmd ")) { //?
            //throw new IncorrectCommandExpression(cmd+" command is invalid"); //it stops program execution.
            //System.out.println("command is invalid '"+cmd+"'");
            return false;
        }
        return cmd.startsWith(ENTER_ROOM.substring(0, ENTER_ROOM.indexOf('[')));
    }

    public static boolean isLeavingTheRoom(String cmd) {
        if (!Validate.validateCommand(cmd) || !cmd.startsWith("-cmd ")) { //?
            //throw new IncorrectCommandExpression(cmd+" command is invalid"); //it stops program execution.
            //System.out.println("command is invalid '"+cmd+"'");
            return false;
        }
        return cmd.trim().equals(LEAVE_ROOM.trim());
    }

    public static boolean isSendMessage(String cmd) {
        return !StringUtils.isBlank(cmd) && !cmd.startsWith("-cmd");
    }

    public static boolean isExit(String cmd) {
        return !StringUtils.isBlank(cmd) && cmd.trim().equals(EXIT_CHAT);
    }

    public static boolean isShowRooms(String cmd) {
        return !StringUtils.isBlank(cmd) && cmd.trim().equals(SHOW_ROOMS);
    }
}
