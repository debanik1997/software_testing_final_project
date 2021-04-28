import Checkers.model.Player;
import Checkers.model.NetworkPlayer;
import Checkers.model.ComputerPlayer;
import Checkers.model.HumanPlayer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    void testNetworkPlayerIsNotHuman() {
        NetworkPlayer np = new NetworkPlayer();
        assertFalse(np.isHuman());
    }

    @Test
    void testComputerPlayerIsNotHuman() {
        ComputerPlayer cp = new ComputerPlayer();
        assertFalse(cp.isHuman());
    }

    @Test
    void testHumanPlayerIsHuman() {
        HumanPlayer hp = new HumanPlayer();
        assertTrue(hp.isHuman());
    }

    @Test
    void testNetworkPlayerToString() {
        NetworkPlayer np = new NetworkPlayer();
        assertEquals("NetworkPlayer[isHuman=false]", np.toString());
    }

    @Test
    void testComputerPlayerToString() {
        ComputerPlayer cp = new ComputerPlayer();
        assertEquals("ComputerPlayer[isHuman=false]", cp.toString());
    }

    @Test
    void testHumanPlayerToString() {
        HumanPlayer hp = new HumanPlayer();
        assertEquals("HumanPlayer[isHuman=true]", hp.toString());
    }
}
