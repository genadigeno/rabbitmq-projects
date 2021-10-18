package chatroom.exeptions;

public class IncorrectCommandExpression extends RuntimeException {
    public IncorrectCommandExpression(String message) {
        super(message);
    }
}
