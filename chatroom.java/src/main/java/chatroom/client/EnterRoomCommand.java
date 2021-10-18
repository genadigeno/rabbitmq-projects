package chatroom.client;

import chatroom.core.Client;

public class EnterRoomCommand implements Command {
    private Client client;
    private String roomName;

    public EnterRoomCommand(Client client, String roomName) {
        this.client = client;
        this.roomName = roomName;
    }

    @Override
    public void execute() {
        client.enterRoom(roomName);
    }
}
