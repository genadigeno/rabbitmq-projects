package chatroom.core;

import chatroom.client.Position;
import chatroom.messaging.RabbitTemplate;
import com.rabbitmq.client.Channel;

import java.io.IOException;

public class Client {
    private String consumerTag;//for quite the room
    private Channel receivingChannel;

    private RabbitTemplate rabbitTemplate;
    private String clientName;
    private String clientId;
    private Position position = Position.MAIN_ROOM;

    private String currentRoom;

    public void setClientName(String clientName) { this.clientName = clientName; }

    public void setClientId(String clientId) { this.clientId = clientId; }

    public String getClientId() { return clientId; }

    public String getClientName() { return clientName; }

    public Channel getReceivingChannel() {
        return receivingChannel;
    }

    public void setReceivingChannel(Channel receivingChannel) {
        this.receivingChannel = receivingChannel;
    }

    public void enterRoom(String roomName) {
        if (rabbitTemplate == null) rabbitTemplate = new RabbitTemplate();

        if (position == Position.MAIN_ROOM){
            /*roomName is exchange, clientName is queue*/
            try {
                consumerTag = rabbitTemplate.receive(roomName, clientName, this);
                position = Position.CHAT_ROOM;
                this.currentRoom = roomName;
                System.out.println(clientName + " Entered into room: " + roomName);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("You are already entered in room: " + roomName);
        }
    }

    public void leaveRoom() {
        if (rabbitTemplate == null) rabbitTemplate = new RabbitTemplate();

        if (position == Position.CHAT_ROOM) {
            try {
                rabbitTemplate.unsubscribe(consumerTag, this);// clientName is consumerTag
                position = Position.MAIN_ROOM;
                System.out.println(clientName + " Left the room");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("You are in main room");
        }
    }

    public void sendMessage(String message) {
        if (rabbitTemplate == null) rabbitTemplate = new RabbitTemplate();

        if (position == Position.CHAT_ROOM) {
            try {
                rabbitTemplate.send(this, currentRoom, message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
