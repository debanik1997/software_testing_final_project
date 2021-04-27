import Checkers.model.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveTest {

    private Move move;
    private Move movePt;

    @BeforeEach
    public void initializeMove() {
        move = new Move(0, 1);
        movePt = new Move(new Point(0,0), new Point(1,1));
    }

    @Test
    public void testGetStartIndex() {
        assertEquals(0,move.getStartIndex());
    }

    @Test
    public void testSetStartIndex() {
        move.setStartIndex(2);
        assertEquals(2, move.getStartIndex());
    }

    @Test
    public void testGetEndIndex() {
        assertEquals(1,move.getEndIndex());
    }

    @Test
    public void testSetEndIndex() {
        move.setEndIndex(2);
        assertEquals(2,move.getEndIndex());
    }

    @Test
    public void testGetStartPoint() {
        Point s = new Point(1,0);
        assertEquals(s, move.getStart());
    }

    @Test
    public void testSetStartPoint() {
        Point s = new Point(0,0);
        Point a = new Point(-1,-1);
        move.setStart(s);
        assertEquals(a, move.getStart());
    }

    @Test
    public void testGetEndPoint() {
        Point s = new Point(3,0);
        assertEquals(s, move.getEnd());
    }

    @Test
    public void testSetEndPoint() {
        Point s = new Point(0,0);
        Point a = new Point(-1,-1);
        move.setEnd(s);
        assertEquals(a, move.getEnd());
    }

    @Test
    public void testGetWeightInitial() {
        assertEquals(0, move.getWeight());
    }

    @Test
    public void testGetWeightAfterSet() {
        move.setWeight(5.0);
        assertEquals(5.0, move.getWeight());
    }

    @Test
    public void testDeltaWeight() {
        move.setWeight(5.0);
        assertEquals(5.0, move.getWeight());
        move.changeWeight(2.5);
        assertEquals(7.5, move.getWeight());
    }

    @Test
    public void testToString() {
        assertEquals("Move[startIndex=0, endIndex=1, weight=0.0]", move.toString());
    }

    @Test
    public void testPointStartNull() {
        move.setStart(null);
        assertEquals(new Point(-1,-1), move.getStart());
    }

    @Test
    public void testPointEndNull() {
        move.setEnd(null);
        assertEquals(new Point(-1,-1), move.getEnd());
    }
}
