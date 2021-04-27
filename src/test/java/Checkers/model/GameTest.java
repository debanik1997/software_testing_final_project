import static org.junit.jupiter.api.Assertions.*;


import Checkers.model.Board;
import Checkers.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class GameTest {

    private Game game;
    private final String initialState = "666666666666000000004444444444441-1";

    @BeforeEach
    void initalizeGame() {
        game = new Game();
    }

    //test constructors

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

//    @Test
//    public void testMoveKingWhite() {
//
//    }
//
//    @Test
//    public void testMoveKingBlack() {
//        Game cur = new Game("006060060460400040000000000446041-1");
//        assertTrue(cur.move(25,29));
//    }


    @Test
    public void testGetBoard() {
        Board c = game.getBoard();
        Point start = new Point(1,2);
        Point end = new Point(2,3);
        game.move(start,end);
        Board d = game.getBoard();
        assertNotEquals(c.toString(), d.toString());
    }

}

