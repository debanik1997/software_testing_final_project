import static org.junit.jupiter.api.Assertions.*;

import Checkers.network.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SessionTest {

    private String sid = "test_sid";
    private int sourcePort = 123;
    private String destinationHost = "test_destination_host";
    private int destinationPort = 321;

    private Session session;

    @BeforeEach
    void initializeSession() {
        session = new Session(sid, sourcePort, destinationHost, destinationPort);
    }

    @Test
    void testSessionGetSid() {
        assertEquals(sid, session.getSid());
    }

    @Test
    void testSessionSetSid() {
        session.setSid("new_sid");
        assertEquals("new_sid", session.getSid());
    }

    @Test
    void testSessionGetDestinationHost() {
        assertEquals(destinationHost, session.getDestinationHost());
    }

    @Test
    void testSessionSetDestinationHost() {
        session.setDestinationHost("new_dh");
        assertEquals("new_dh", session.getDestinationHost());
    }

    @Test
    void testSessionGetDestinationPort() {
        assertEquals(destinationPort, session.getDestinationPort());
    }

    @Test
    void testSessionSetDestinationPort() {
        session.setDestinationPort(4300);
        assertEquals(4300, session.getDestinationPort());
    }

    @Test
    void testSessionGetSourcePort() {
        assertEquals(sourcePort, session.getSourcePort());
    }
}
