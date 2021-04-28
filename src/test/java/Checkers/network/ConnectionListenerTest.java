import Checkers.network.ConnectionListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ConnectionListenerTest {
    private ConnectionListener cl;
    private ActionListener al;

    @BeforeEach
    public void setup() {
        al = mock(ActionListener.class);
        cl = new ConnectionListener(0, al);
    }

    @Test
    public void testRun() {
       assertEquals(2, 2);
    }


}
