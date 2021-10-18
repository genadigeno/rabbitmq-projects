package chatroom.client;

import chatroom.core.Client;

public class LeaveRoomCommand implements Command {
    private Client client;

    public LeaveRoomCommand(Client client) {
        this.client = client;
    }

    @Override
    public void execute() {
        client.leaveRoom();
    }
}
