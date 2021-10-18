package chatroom;

import chatroom.client.EnterRoomCommand;
import chatroom.client.LeaveRoomCommand;
import chatroom.client.SendMessageCommand;
import chatroom.service.RoomService;
import chatroom.core.Client;
import chatroom.exeptions.IncorrectCommandExpression;
import chatroom.util.Commands;
import chatroom.util.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ChatRoomApp {
    private static RoomService roomService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatRoomApp.class);

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        LOGGER.info("Please Enter Your Name: ");
        String username = input.nextLine();

        while (!Validate.validUserName(username)) {
            if (username.equals(Commands.CANCEL_LOGGING_IN)) break;
            LOGGER.warn("Please Enter Valid Name: ");
            username = input.nextLine();
        }
        LOGGER.info("Welcome {}", username);
        Client client = new Client();
        client.setClientName(username);
        client.setClientId(UUID.randomUUID().toString());

        listAllRooms();
        LOGGER.info("Please Enter Command: ");
        Scanner scanner = new Scanner(System.in);
        String cmdString;
        while (true) {
            cmdString = scanner.nextLine();
            if (cmdString.equals(Commands.CANCEL_LOGGING_IN)) {
                System.exit(0);
                break;
            } else if (Validate.validateCommand(cmdString)) {
                LOGGER.warn("validated");
                break;
            }
            LOGGER.warn("Please Enter Valid Command: ");
        }

        System.out.println("===============================================================================");

        /*while (true) {
            LOGGER.info("<<{}>>", cmdString);

            if (Commands.isEnterIntoRoom(cmdString)) {
                String cmdVal = Commands.value(cmdString);

                try {
                    if (!roomService.existsRoom(cmdVal)) {
                        LOGGER.warn("Room {} not found", cmdVal);
                    } else {
                        new EnterRoomCommand(client, cmdVal).execute();
                    }
                    cmdString = scanner.nextLine();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (Commands.isLeavingTheRoom(cmdString)) {
                LOGGER.info("leaving...");
                new LeaveRoomCommand(client).execute();
                cmdString = scanner.nextLine();
            } else if (Commands.isSendMessage(cmdString)) {
                new SendMessageCommand(client, cmdString).execute();
                cmdString = scanner.nextLine();
            } else {
                LOGGER.info("--- None ---");
                scanner.close();
                break;
            }
        }*/

        try {
            nexDecision(client, cmdString);
        } catch (IncorrectCommandExpression | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void nexDecision(Client client, String cmd) throws IncorrectCommandExpression, SQLException {
        if (cmd.equals(Commands.CANCEL_LOGGING_IN)) return;
        //LOGGER.info("Command '{}' Received", cmd);
        //System.out.println("Thread Active Count = " + Thread.activeCount());

        Scanner scanner = null;
        if (Commands.isEnterIntoRoom(cmd)) {
            String cmdVal = Commands.value(cmd);

            boolean existsRoom = roomService.existsRoom(cmdVal);
            if (!existsRoom) {
                LOGGER.warn("Room {} not found", cmdVal);
                scanner = new Scanner(System.in);
                nexDecision(client, scanner.nextLine());
                scanner.close();
                return;
            }

            EnterRoomCommand command = new EnterRoomCommand(client, cmdVal);
            command.execute();

            scanner = new Scanner(System.in);
            nexDecision(client, scanner.nextLine());
        } else if (Commands.isLeavingTheRoom(cmd)) {
            System.out.println("leaving...");
            LeaveRoomCommand command = new LeaveRoomCommand(client);
            command.execute();

            scanner = new Scanner(System.in);
            nexDecision(client, scanner.nextLine());
        } else if (Commands.isSendMessage(cmd)) {
            SendMessageCommand command = new SendMessageCommand(client, cmd);
            command.execute();

            scanner = new Scanner(System.in);
            nexDecision(client, scanner.nextLine());
        } else if (Commands.isExit(cmd)) {
            System.exit(0);
        } else if (Commands.isShowRooms(cmd)) {
            listAllRooms();

            scanner = new Scanner(System.in);
            nexDecision(client, scanner.nextLine());
        } else {
            System.out.println("--- None ---");
            //id start with -cmd "please enter valid command", otherwise send message if client is in room

            scanner = new Scanner(System.in);
            nexDecision(client, scanner.nextLine());
        }
        if (scanner != null) scanner.close();
        return;
    }

    private static void listAllRooms() {
        roomService = RoomService.getInstance();
        List<String> rooms = roomService.rooms();
        LOGGER.info("Available Room(s): {}", rooms);
    }
}
