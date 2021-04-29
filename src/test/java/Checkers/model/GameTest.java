import static org.junit.jupiter.api.Assertions.*;


import Checkers.model.Board;
import Checkers.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
/*
Initially to test this program we started with approaching it as a blackbox testing challenge. Since we rougly knew
how the game of checkers worked as well were provided with adequate documentation this was a suitable task to take on.
Aditionally, since the game was calling many other functions and was tightly coupled to the board as well as individual
pieces, we treated it overall as an integration blackbox test where we applied various techniques like equivalence
partioning, boundary analysis, and error guessing. Aditionally, due to the tight coupling and the presence of many
void functions, we had to test many functions with the assumption the getGameState was working correctly.

After implmenenting all the tests we found that there were no faults.
 */

public class GameTest {

    private Game game;
    private final String initialState = "666666666666000000004444444444441-1";

    @BeforeEach
    void initalizeGame() {
        game = new Game();
    }

    /*
    First we attempted to test the constructors. Since we were using
    the other construcotrs, we started with the alternate constructor. A board was passed in
    as both null and not null for equivalence partioining.
     */
    @Test
    public void testGameAlternateConstructor() {
        Game cur = new Game(game.getBoard(), true, -1);
        assertEquals(initialState, cur.getGameState());
    }

    @Test
    public void testGameAltConstructorBoardNull() {
        Game cur = new Game(null, true, -1);
        assertEquals(initialState, cur.getGameState());
    }

    //Testing the copy function. Since this does not take in any inputs we had to be creative how to
    //test this properly. The two partitions were either a copy of the game in the beginning (to ensure no error happens)
    //and a copy midway through the game.
    @Test
    public void testCopyInitial() {
        Game c = game.copy();
        assertEquals(game.getGameState(), c.getGameState());
    }

    @Test
    public void testCopyNonInitial() {
        String temp = "666666666666000000004444444444441-1";
        Game inProg = new Game(temp);
        Game c = inProg.copy();
        assertEquals(temp, c.getGameState());
    }

    //Testing the restart function. Since this does not take in any inputs we had to be creative how to
    //test this properly. The two partitions were either a restart of the game in the beginning (to ensure no error happens)
    //and a restart midway through the game.
    @Test
    public void testRestartInProg() {
        String temp = "666666666666000000004444444444441-1";
        Game inProg = new Game(temp);
        inProg.restart();
        assertEquals(initialState, inProg.getGameState());
    }

    @Test
    public void testRestartAtBeginning() {
        game.restart();
        assertEquals(initialState, game.getGameState());
    }

    /*
    Move was one of the more complicated functions to test. First we tested
    the move function that took in a Point. In order to do partioining we tested
    with both a null start/end and a non-null.

    From there move was tested. The difficulty in move was that we had to correclty simulate a board game
    to test it with the blackbox testing. There were a couple situations that we thought of.
    1) Valid move
    2) Invalid move
    3) Move that creates a King for either white or black
    4) Invalid indices

    These were the four cases that were initially done. Then when we ran jacococ to check coverage we came back
    to add tests that related to verifying the actual state of the board as well as looking at the case where the skipIndex
    was not -1. This increased coverage.
     */
    @Test
    public void testMovePointStartNull() {
        Point p = new Point(1,0);
        assertFalse(game.move(null, p));
    }

    @Test
    public void testMovePointEndNull() {
        Point p = new Point(1,0);
        assertFalse(game.move(p, null));
    }

    @Test
    public void testMovePointValid() {
        Point start = new Point(1,2);
        Point end = new Point(2,3);
        assertTrue(game.move(start,end));
    }

    @Test
    public void testMoveInvalidMove() {
        assertFalse(game.move(0,0));
    }

    @Test
    public void testMoveNegativeStart() {
        assertFalse(game.move(-1,0));
    }

    @Test
    public void testMoveNegativeEnd() {
        assertFalse(game.move(0,-5));
    }


    @Test
    public void testMoveKingBlack() {
        Game cur = new Game("666606000040400000004060004440001-1");
        assertTrue(cur.move(22,31));
    }

    @Test
    public void testMoveKingWhite() {
        Game cur = new Game("006666060406604000006400004004440-1");
        assertTrue(cur.move(9,0));
    }

    @Test
    public void testMoveAltersBoard() {
        game.move(9,14);
        assertEquals("666666666066006000004444444444440-1", game.getGameState());
    }

    @Test
    public void testMoveWithSkipValid() {
        Game cur = new Game("660660006060406004604000040440041-1");
        assertTrue(cur.move(14,21));
    }

    @Test
    public void testMoveKingTakePiece() {
        Game cur = new Game("500606060006006000000060400440741-1");
        assertTrue(cur.move(14,18));
        assertEquals("500606060006000000600060400440740-1", cur.getGameState());
    }

    @Test
    public void testWhiteKingReachesStart() {
        Game cur = new Game("000600060006000000670050460440040-1");
        assertTrue(cur.move(22,29));
        assertEquals("000600060006000000670000400445041-1", cur.getGameState());
    }

    @Test
    public void testBlackKingReachesStart() {
        Game cur = new Game("000600760006040000600000000445041-1");
        assertTrue(cur.move(6, 1));
        assertEquals("070600060006040000600000000445040-1", cur.getGameState());
    }

    //This was a simple test that we verified returned the board. Since specifications said the board was a copy
    //we wanted to ensure a change to the original board was not reflected in the returned object
    @Test
    public void testGetBoard() {
        Board c = game.getBoard();
        Point start = new Point(1,2);
        Point end = new Point(2,3);
        game.move(start,end);
        Board d = game.getBoard();
        assertNotEquals(c.toString(), d.toString());
    }


    /*
    For testing game over there were a couple scenarios that were discovered in blackbox testing.
    We wanted to test
    1) GameNotOver
    2) Game over becaause no more black/white pieces
    3) game over because no valid moves
     */
    @Test
    public void testIsGameOverInitialBoard() {
        assertFalse(game.isGameOver());
    }

    @Test
    public void testIsGameOverNoBlack() {
        game.setGameState("000000000000000000004444444444441-1");
        assertTrue(game.isGameOver());
    }

    @Test
    public void testIsGameOverNoWhite() {
        game.setGameState("666666666666000000000000000000001-1");
        assertTrue(game.isGameOver());
    }

    @Test
    public void testIsGameOverNoMovesBlack() {
        game.setGameState("000060004000440000000000000000001-1");
        assertTrue(game.isGameOver());
    }

    @Test
    public void testIsGameOverNoMovesWhite() {
        game.setGameState("666640000000000000000000000000000-1");
        assertTrue(game.isGameOver());
    }

    @Test
    public void testIsGameOverRandomBoard() {
        game.setGameState("006060060460400040000000000446041-1");
        assertFalse(game.isGameOver());
    }

    /*
    The following tests related to the three methods that were simple getters and setters
    These were trivial to test
     */
    @Test
    public void testisP1TurnInitialTrue() {
        assertTrue(game.isP1Turn());
    }

    @Test
    public void testIsP1TurnInitialFalse() {
        game.setGameState("666666666666000000004444444444440-1");
        assertFalse(game.isP1Turn());
    }

    @Test
    public void testSetP1TurnTrue() {
        game.setP1Turn(true);
        assertTrue(game.isP1Turn());
    }

    @Test
    public void testSetP1TurnFalse() {
        game.setP1Turn(false);
        assertFalse(game.isP1Turn());
    }

    @Test
    public void testGetSkipIndexInitial() {
        assertEquals(-1, game.getSkipIndex());
    }

    @Test
    public void testGetSkipIndexNonInitial() {
        game.setGameState("6666666666660000000044444444444419");
        assertEquals(9, game.getSkipIndex());
    }

    /*
    Due to blackbox testing it was hard to tell what eactly was happening here. We just wanted to make sure
    that the string that was returned was similar to the form stated elsewhere. Because of this we just had a test to look
    at the state.
     */
    @Test
    public void testGetGameStateInitial() {
        assertEquals(initialState, game.getGameState());
    }

    /*
    In order to test this funciton we had to rely on the getGameState to ensure changes were happenign properly. Without
    looking at the code we wanted to pass in a state that was empty, null, and in progress. These were
    the initial cases we looked at.

    After lookin gat jacoco we passed in a game state with non numerical characters, and altered
    the conditions around the p1 and skip values.
     */
    @Test
    public void testSetGameStateNull() {
        assertDoesNotThrow(() -> game.setGameState(null));
    }

    @Test
    public void testSetGameStateEmptyString() {
        assertDoesNotThrow(() -> game.setGameState(""));
    }

    //blackbox then change it to throws the number format
    //whitebox then the board is OG
    @Test
    public void testSetGameStateStringWithNonNumbers() {
        String board = "hello";
        game.setGameState(board);
        assertEquals(initialState,game.getGameState());
    }

    @Test
    public void testSetGameStateStringP1Is1() {
        String board = "666666666666000000004444444444441-1";
        game.setGameState(board);
        assertEquals(board,game.getGameState());
    }

    @Test
    public void testSetGameStateStringP1Is0() {
        String board = "666666666666000000004444444444440-1";
        game.setGameState(board);
        assertEquals(board,game.getGameState());
    }

    @Test
    public void testSetGameStateStringP1IsAlpha() {
        String board = "66666666666600000000444444444444a-1";
        game.setGameState(board);
        assertEquals("666666666666000000004444444444440-1",game.getGameState());
    }

    @Test
    public void testSetGameStateStringSkipAlpha() {
        String board = "666666666666000000004444444444441a";
        game.setGameState(board);
        assertEquals(initialState, game.getGameState());
    }

    @Test
    public void testSetGameStateStringNormalSkip() {
        String board = "666666666666000000004444444444441100";
        game.setGameState(board);
        assertEquals(board, game.getGameState());
    }

    @Test
    public void testSetGameStateStringSkipSomeAlpha() {
        String board = "6666666666660000000044444444444411a1";
        game.setGameState(board);
        assertEquals(initialState, game.getGameState());

    }

}

