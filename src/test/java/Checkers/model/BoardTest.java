import Checkers.model.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardTest {

    private Board board;

    @BeforeEach
    void initializeBoard() {
        board = new Board();
    }
    @Test
    void testInitialBoardState() {
        for (int x = 0; x <= 7; x++) {
            for (int y = 0; y <= 7; y++) {

            }
        }
    }

}
