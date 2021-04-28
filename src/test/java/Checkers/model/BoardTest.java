import Checkers.model.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
In this set of tests, we are conducting white-box testing on the Board object.
This includes testing direct modifications on a test board, as well as static Board helper functions.

Our testing goal is to maximize branch coverage.
 */
public class BoardTest {

    private Board board;

    @BeforeEach
    void initializeBoard() {
        board = new Board();
    }

    // Testing the initial Board configuration
    // The first 12 pieces should be black
    // Followed by 8 empty squares
    // Followed by 12 white
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

    // The next 5 tests utilize equivalence partitioning of the input ID for the find method
    // 1) ID: Board.BLACK_CHECKER
    // 2) ID: Board.WHITE_CHECKER
    // 3) ID: Board.EMPTY
    // 4) ID: Board.INVALID
    // 5) ID: Random integer that is not any of the above
    // These 5 tests were followed with 2 tests that tested the find method using Integer.MIN_VALUE and Integer.MAX_VALUE
    // This represents our error guessing
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
    void testFindPiecesWithIntegerMinIDInitialBoardConfiguration() {
        List<Point> list = board.find(Integer.MIN_VALUE);
        assertEquals(0, list.size());
    }

    @Test
    void testFindPiecesWithIntegerMaxInitialBoardConfiguration() {
        List<Point> list = board.find(Integer.MAX_VALUE);
        assertEquals(0, list.size());
    }

    // The board.set method is overloaded to allow setting with an index or a (x, y) coordinate
    // First, we did equivalence partitioning of the input index for the set method
    // 1) Index: 0 >= i >= 31
    // 1a) Index: i = 0 (boundary analysis)
    // 1b) Index: i = 31 (boundary analysis)
    // 2) Index: i < 0
    // 3) Index: i > 31
    // We followed this with 2 tests that tested an index of Integer.MIN_VALUE and Integer.MAX_VALUE (error guessing)
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

    // Since we are testing the Boards ability to convert between indices and points later, we did not test set with x, y as extensively
    // Still, we ran 5 tests as follows:
    // 1) Both x and y in range (between 0 and 7 inclusive)
    // 2) Both x and y above range (less than 0)
    // 3) Both x and y below range (greater than 7)
    // 4, 5) Integer.MIN and Integer.MAX
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

    // The Board class contains 2 static functions that convert between indices and points
    // First, we will test the Board.toPoint
    // Equivalence partition the index input
    // 1) Index: 0 >= i >= 31
    // 1a) Index: i = 0 (boundary analysis)
    // 1b) Index: i = 31 (boundary analysis)
    // 2) Index: i < 0 -> should return a point = (-1, -1)
    // 3) Index: i > 31  -> should return a point = (-1, -1)
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
        assertEquals(new Point(6, 7), Board.toPoint(31));
    }

    @Test
    void testToPointConvertsToInvalidPointBlackTileIndexToPointIndexLessThanRange() {
        assertEquals(new Point(-1, -1), Board.toPoint(-1));
    }

    @Test
    void testToPointConvertsToInvalidPointBlackTileIndexToPointIndexGreaterThanRange() {
        assertEquals(new Point(-1, -1), Board.toPoint(32));
    }

    // Next we will test the Board.toIndex method
    // Here, we will equivalence partition on the validitiy of the input Point
    // 1) Valid point
    // 2) Invalid point
    // 3) Null point
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

    // We will now look at the isValidIndex helper function, which determines whether an index corresponds to a black tile on the board
    // Equivalence partition the input index
    // 1) Index: 0 >= i >= 31
    // 2) Index: i < 0
    // 3) Index: i > 31
    @Test
    void testIsValidIndexInRange() {
        assertTrue(Board.isValidIndex(15));
    }

    @Test
    void testIsValidIndexBelowRange() {
        assertFalse(Board.isValidIndex(-1));
    }

    @Test
    void testIsValidIndexAboveRange() {
        assertFalse(Board.isValidIndex(32));
    }

    // The isValidPoint is a little more involved
    // We will equivalence partition based on the location of the point
    // 1) Point on black tile
    // 2) Point on white tile
    // 3) Point not on board
    // 4) Point null
    @Test
    void testIsValidPointOnBlackTile() {
        assertTrue(Board.isValidPoint(new Point(3, 0)));
    }

    @Test
    void testIsValidPointOnWhiteTile() {
        assertFalse(Board.isValidPoint(new Point(2, 0)));
    }

    @Test
    void testIsValidPointNotOnBoard() {
        assertFalse(Board.isValidPoint(new Point(10, 10)));
    }

    @Test
    void testIsValidPointNull() {
        assertFalse(Board.isValidPoint(null));
    }

    // Very simply, test the toString at initialization
    @Test
    void testToString() {
        assertEquals("Checkers.model.Board[6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4]", board.toString());
    }


}
