package chatroom.client;

import chatroom.core.Client;

public class SendMessageCommand implements Command {
    private Client client;
    private String message;

    public SendMessageCommand(Client client, String message) {
        this.client = client;
        this.message = message;
    }

    @Override
    public void execute() {
        client.sendMessage(message);
    }
}
