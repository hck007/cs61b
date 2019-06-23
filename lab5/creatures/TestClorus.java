package creatures;
import static huglife.HugLifeUtils.randomEntry;
import static org.junit.Assert.*;

import huglife.*;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

public class TestClorus {
    @Test
    public void TestReplicate() {
        Clorus c = new Clorus(2);
        Clorus babyClorus = c.replicate();
        Clorus expected = new Clorus(1);
        assertEquals(expected.energy(), babyClorus.energy(), 0.01);
    }
    @Test
    public void TestChoose() {
        Clorus c = new Clorus(2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());
        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);
        assertEquals(expected, actual);

        //All empty
        /**
        Clorus c1 = new Clorus(2);
        HashMap<Direction, Occupant> allEmpty = new HashMap<Direction, Occupant>();
        allEmpty.put(Direction.TOP, new Empty());
        allEmpty.put(Direction.BOTTOM, new Empty());
        allEmpty.put(Direction.LEFT, new Empty());
        allEmpty.put(Direction.RIGHT, new Empty());
        Deque<Direction> list = new ArrayDeque<>();
        for (Direction d: allEmpty.keySet()) {
            list.addFirst(d);
        }
        Action actual1 = c1.chooseAction(allEmpty);
        Action expected1 = new Action(Action.ActionType.REPLICATE, randomEntry(list));
        assertEquals(expected1, actual1);
        */
        //TopPlip
        Clorus c2 = new Clorus(2);
        HashMap<Direction, Occupant> topPlip = new HashMap<Direction, Occupant>();
        topPlip.put(Direction.TOP, new Plip(2));
        topPlip.put(Direction.BOTTOM, new Empty());
        topPlip.put(Direction.RIGHT, new Empty());
        topPlip.put(Direction.LEFT, new Empty());
        Action actual2 = c2.chooseAction(topPlip);
        Action expected2 = new Action(Action.ActionType.ATTACK, Direction.TOP);
        assertEquals(expected2, actual2);
    }
}
