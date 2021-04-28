import static org.junit.jupiter.api.Assertions.*;

import Checkers.network.Command;
import org.junit.jupiter.api.Test;

public class CommandTest {

    @Test
    void testCommandGetCommand() {
        Command c = new Command(Command.COMMAND_CONNECT, new String[]{"data"});
        assertEquals(Command.COMMAND_CONNECT, c.getCommand());
    }

    @Test
    void testCommandSetCommand() {
        Command c = new Command(Command.COMMAND_CONNECT, new String[]{"data"});
        c.setCommand(Command.COMMAND_GET);
        assertEquals(Command.COMMAND_GET, c.getCommand());
    }

    @Test
    void testCommandGetData() {
        Command c = new Command(Command.COMMAND_CONNECT, new String[]{"data"});
        assertArrayEquals(new String[]{"data"}, c.getData());
    }

    @Test
    void testCommandSetData() {
        Command c = new Command(Command.COMMAND_CONNECT, new String[]{"data"});
        c.setData(new String[]{"data", "data2"});
        assertArrayEquals(new String[]{"data", "data2"}, c.getData());
    }

    @Test
    void testCommandGetOutput() {
        Command c = new Command(Command.COMMAND_CONNECT, new String[]{"data", "data2"});
        assertEquals("CONNECT\n" + "data\n" + "data2", c.getOutput());
    }
}
