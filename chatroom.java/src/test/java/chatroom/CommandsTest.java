package chatroom;

import chatroom.exeptions.IncorrectCommandExpression;
import chatroom.service.RoomService;
import chatroom.util.Commands;
import chatroom.util.Validate;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class CommandsTest {
    private String cmd1 = "-cmd enter room.1";
    private String cmd2 = "-cmd enter \"room.1\"";
    private String cmd3 = "-cmd enter \"room.1";
    private String cmd4 = "-cmd enter room";
    private String cmd5 = "-cmd entr room.1";

    private RoomService roomService = RoomService.getInstance();

    @Test
    public void cmd1_is_valid() throws IncorrectCommandExpression, SQLException {
        assertTrue(Validate.validateCommand(cmd1, true));
        assertTrue(Commands.isEnterIntoRoom(cmd1));
        assertTrue(roomService.existsRoom("room.1"));
    }

    @Test
    public void cmd2_is_valid() throws IncorrectCommandExpression, SQLException {
        assertTrue(Validate.validateCommand(cmd2, true));
        assertTrue(Commands.isEnterIntoRoom(cmd2));
        assertEquals("room.1", Commands.value(cmd2));
        assertTrue(roomService.existsRoom(Commands.value(cmd2)));
    }

    @Test
    public void cmd3_is_not_valid() throws IncorrectCommandExpression {
        assertTrue(Validate.validateCommand(cmd3, true));
        assertTrue(Commands.isEnterIntoRoom(cmd3));
        assertThrows(IncorrectCommandExpression.class, () -> {
            assertNotEquals("room.1", Commands.value(cmd3));
        });
    }

    @Test
    public void cmd4_not_found() throws IncorrectCommandExpression, SQLException {
        assertTrue(Validate.validateCommand(cmd4, true));
        assertTrue(Commands.isEnterIntoRoom(cmd4));
        assertEquals("room", Commands.value(cmd4));
        assertFalse(roomService.existsRoom(Commands.value(cmd4)));
    }

    @Test
    public void cmd5_is_not_valid() {
        assertFalse(Validate.validateCommand(cmd5));
    }
}
