import Checkers.model.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;

    @BeforeEach
    void initializeBoard() {
        board = new Board();
    }

    @Test
    void testInitialBoardConfigurationOfBlackAndWhitePieces() {
        for (int i = 0; i < 32; i++) {
            if (i >= 0 && i < 12) {
                assertEquals(Board.BLACK_CHECKER, board.get(i));
            } else if (i >= 12 && i < 20) {
                assertEquals(Board.EMPTY, board.get(i));
            } else {
                assertEquals(Board.WHITE_CHECKER, board.get(i));
            }
        }
    }

    @Test
    void testFindAllBlackPiecesInitialBoardConfiguration() {
        int[][] initialBlackPieceLocations = {
                {1, 0},
                {3, 0},
                {5, 0},
                {7, 0},
                {0, 1},
                {2, 1},
                {4, 1},
                {6, 1},
                {1, 2},
                {3, 2},
                {5, 2},
                {7, 2},
        };

        List<Point> list = board.find(Board.BLACK_CHECKER);
        for (int i = 0; i < list.size(); i++) {
            assertEquals(initialBlackPieceLocations[i][0], list.get(i).x);
            assertEquals(initialBlackPieceLocations[i][1], list.get(i).y);
        }
    }

    @Test
    void testFindAllWhitePiecesInitialBoardConfiguration() {
        int[][] initialBlackPieceLocations = {
                {0, 5},
                {2, 5},
                {4, 5},
                {6, 5},
                {1, 6},
                {3, 6},
                {5, 6},
                {7, 6},
                {0, 7},
                {2, 7},
                {4, 7},
                {6, 7},
        };

        List<Point> list = board.find(Board.WHITE_CHECKER);
        for (int i = 0; i < list.size(); i++) {
            assertEquals(initialBlackPieceLocations[i][0], list.get(i).x);
            assertEquals(initialBlackPieceLocations[i][1], list.get(i).y);
        }
    }

    @Test
    void testFindAllEmptyPiecesInitialBoardConfiguration() {
        int[][] initialBlackPieceLocations = {
                {0, 3},
                {2, 3},
                {4, 3},
                {6, 3},
                {1, 4},
                {3, 4},
                {5, 4},
                {7, 4},
        };

        List<Point> list = board.find(Board.EMPTY);
        for (int i = 0; i < list.size(); i++) {
            assertEquals(initialBlackPieceLocations[i][0], list.get(i).x);
            assertEquals(initialBlackPieceLocations[i][1], list.get(i).y);
        }
    }

    @Test
    void testNoInvalidPiecesInitialBoardConfiguration() {
        List<Point> list = board.find(Board.INVALID);
        assertEquals(0, list.size());
    }
}
