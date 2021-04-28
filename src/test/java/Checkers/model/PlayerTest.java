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
}
