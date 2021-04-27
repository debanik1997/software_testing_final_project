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
        int[][] initialWhitePieceLocations = {
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
            assertEquals(initialWhitePieceLocations[i][0], list.get(i).x);
            assertEquals(initialWhitePieceLocations[i][1], list.get(i).y);
        }
    }

    @Test
    void testFindAllEmptyPiecesInitialBoardConfiguration() {
        int[][] initialEmptyLocations = {
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
            assertEquals(initialEmptyLocations[i][0], list.get(i).x);
            assertEquals(initialEmptyLocations[i][1], list.get(i).y);
        }
    }

    @Test
    void testNoInvalidPiecesInitialBoardConfiguration() {
        List<Point> list = board.find(Board.INVALID);
        assertEquals(0, list.size());
    }

    @Test
    void testFindPiecesWithRandomIDInitialBoardConfiguration() {
        List<Point> list = board.find(999);
        assertEquals(0, list.size());
    }

    @Test
    void testSetSquareToIDIndexInRangeUsingSetWithIndex() {
        board.set(15, Board.WHITE_KING);
        assertEquals(Board.WHITE_KING, board.get(15));
    }

    @Test
    void testSetSquareToIDIndexInRangeMinValUsingSetWithIndex() {
        board.set(0, Board.WHITE_KING);
        assertEquals(Board.WHITE_KING, board.get(0));
    }

    @Test
    void testSetSquareToIDIndexInRangeMaxValUsingSetWithIndex() {
        board.set(31, Board.WHITE_KING);
        assertEquals(Board.WHITE_KING, board.get(31));
    }

    @Test
    void testSetSquareToIDIndexBelowRangeUsingSetWithIndex() {
        board.set(-1, Board.WHITE_KING);
        List<Point> list = board.find(Board.WHITE_KING);
        assertEquals(0, list.size());
    }

    @Test
    void testSetSquareToIDIndexAboveRangeUsingSetWithIndex() {
        board.set(32, Board.WHITE_KING);
        List<Point> list = board.find(Board.WHITE_KING);
        assertEquals(0, list.size());
    }

    @Test
    void testSetSquareToIDIndexIntegerMinValUsingSetWithIndex() {
        board.set(Integer.MIN_VALUE, Board.WHITE_KING);
        List<Point> list = board.find(Board.WHITE_KING);
        assertEquals(0, list.size());
    }

    @Test
    void testSetSquareToIDIndexIntegerMaxValUsingSetWithIndex() {
        board.set(Integer.MAX_VALUE, Board.WHITE_KING);
        List<Point> list = board.find(Board.WHITE_KING);
        assertEquals(0, list.size());
    }

    @Test
    void testSetSquareToIDIndexInRangeUsingSetWithXAndY() {
        board.set(2, 3, Board.WHITE_KING);
        assertEquals(Board.WHITE_KING, board.get(2, 3));
    }

    @Test
    void testSetSquareToIDIndexBelowRangeUsingSetWithXAndY() {
        board.set(-1, -1, Board.WHITE_KING);
        List<Point> list = board.find(Board.WHITE_KING);
        assertEquals(0, list.size());
    }

    @Test
    void testSetSquareToIDIndexAboveRangeUsingSetWithXAndY() {
        board.set(8, 8, Board.WHITE_KING);
        List<Point> list = board.find(Board.WHITE_KING);
        assertEquals(0, list.size());
    }

    @Test
    void testSetSquareToIDIndexIntegerMinValUsingSetWithXAndY() {
        board.set(Integer.MIN_VALUE, Integer.MIN_VALUE, Board.WHITE_KING);
        List<Point> list = board.find(Board.WHITE_KING);
        assertEquals(0, list.size());
    }

    @Test
    void testSetSquareToIDIndexIntegerMaxValUsingSetWithXAndY() {
        board.set(Integer.MAX_VALUE, Integer.MAX_VALUE, Board.WHITE_KING);
        List<Point> list = board.find(Board.WHITE_KING);
        assertEquals(0, list.size());
    }

    @Test
    void testToPointCorrectlyConvertsBlackTileIndexToPointIndexInRange() {
        assertEquals(new Point(3, 0), Board.toPoint(1));
    }

    @Test
    void testToPointCorrectlyConvertsBlackTileIndexToPointIndexInRangeIndex0() {
        assertEquals(new Point(1, 0), Board.toPoint(0));
    }

    @Test
    void testToPointCorrectlyConvertsBlackTileIndexToPointIndexInRangeIndex31() {
        assertEquals(new Point(7, 7), Board.toPoint(31));
    }

    @Test
    void testToPointConvertsToInvalidPointBlackTileIndexToPointIndexLessThanRange() {
        assertEquals(new Point(-1, -1), Board.toPoint(-1));
    }

    @Test
    void testToPointConvertsToInvalidPointBlackTileIndexToPointIndexGreaterThanRange() {
        assertEquals(new Point(-1, -1), Board.toPoint(32));
    }

    @Test
    void testToIndexCorrectlyConvertsXAndYToBlackTileIndexXAndYInRange() {
        assertEquals(1, Board.toIndex(new Point(3, 0)));
    }

    @Test
    void testToIndexReturnsNegative1ForInvalidPoint() {
        assertEquals(-1, Board.toIndex(new Point(-1, -1)));
    }

    @Test
    void testToIndexReturnsNegative1ForNullPoint() {
        assertEquals(-1, Board.toIndex(null));
    }

}
