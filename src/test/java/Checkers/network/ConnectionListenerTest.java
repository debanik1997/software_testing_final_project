import Checkers.network.ConnectionListener;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.awt.event.ActionListener;
import java.net.ServerSocket;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConnectionListenerTest {
    private ConnectionListener cl;
    private ActionListener al;
    private ServerSocket socket;

    @BeforeEach
    public void setup() {
        al = mock(ActionListener.class);
        cl = mock(ConnectionListener.class);
        socket = mock(ServerSocket.class);
        cl.setServerSocket(socket);
    }

    @AfterEach
    public void closeConnection() {
        cl.stopListening();
    }

    @Test
    public void testConstructorWithPortAndActionListenerMock() {
        int port = 128;
        ConnectionListener cl = new ConnectionListener(port, al);
        assertEquals(port, cl.getPort());
    }

    @Test
    public void testRunClosedSocket() {
        when(socket.isClosed()).thenReturn(true);
        when(socket.getLocalPort()).thenReturn(0);
        cl.run();
        verify(socket, times(1)).isClosed();
        verify(socket, times(1)).getLocalPort();
    }

    @Test
    public void testStopListeningNullSocket() {
        cl.setServerSocket(null);
        assertTrue(cl.stopListening());
    }
}
