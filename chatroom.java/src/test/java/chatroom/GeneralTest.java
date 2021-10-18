package chatroom;

import chatroom.util.Commands;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GeneralTest {
    private String command = "-cmd leave";
    private String command2 = "-cmd kfl";

    @Test
    public void testTrue() {
        AtomicBoolean isValid = new AtomicBoolean(false);
        int indexOfTypedCommand = command.indexOf('[');
        if (indexOfTypedCommand > -1) {
            command = command.substring(0, indexOfTypedCommand);
        }

        Commands.commands().stream()/*.takeWhile(cmd -> isValid.get())*/.forEach(cmd -> {
            int indexOfCommand = cmd.indexOf('[');
            if (indexOfCommand > -1) {
                cmd = cmd.substring(0, indexOfCommand);
            }

            if (cmd.trim().equals(command.trim())) {
                isValid.set(true);
                return;
            }
        });
        assertTrue(isValid.get());
    }

    @Test
    public void testFalse() {
        AtomicBoolean isValid = new AtomicBoolean(false);
        int indexOfTypedCommand = command2.indexOf('[');
        if (indexOfTypedCommand > -1) {
            command2 = command2.substring(0, indexOfTypedCommand);
        }

        Commands.commands().stream()/*.takeWhile(cmd -> isValid.get())*/.forEach(cmd -> {
            int indexOfCommand = cmd.indexOf('[');
            if (indexOfCommand > -1) {
                cmd = cmd.substring(0, indexOfCommand);
            }

            if (cmd.trim().equals(command2.trim())) {
                isValid.set(true);
                return;
            }
        });
        assertFalse(isValid.get());
    }
}
