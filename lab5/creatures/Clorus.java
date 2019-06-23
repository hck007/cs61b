package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static huglife.HugLifeUtils.randomEntry;

public class Clorus extends Creature {
    /**
     * red color
     */
    private int r;
    /**
    *green color
     */
    private int g;
    /**
     * blue color
     */
    private int b;

    /**
     * creates Clorus with energy e
     */
    public Clorus(double e) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    /**
     * creates a clorus with default energy 1
     */
    public Clorus() {
        this(1);
    }

    /**
     *should return the color red = 34, green = 0, blue = 231
     */
    @Override
    public Color color() {
        r = 34;
        g = 0;
        b = 231;
        return color(r, g, b);
    }

    /**
     * when attacking, clorus should gain that creature's energy
     */
    @Override
    public void attack(Creature c) {
        energy += c.energy();
    }

    /**
     * cloruses should lose 0.03 units of energy on a move action
     */
    @Override
    public void move() {
        energy -= 0.03;
    }

    /**
     * clorus should lose 0.01 units of energy on a stay action
     */
    @Override
    public void stay() {
        energy -= 0.01;
    }

    /**
     * when a clorus replicates, it keeps 50% of energy and the other goes to its offspring
     */
    @Override
    public Clorus replicate() {
        double babyEnergy = energy * 0.5;
        energy *= 0.5;
        return new Clorus(babyEnergy);
    }

    /**
     * Cloruses should obey exactly the following behavioral rules:
     * 1. If there are no empty squares, the Clorus will STAY
     * (even if there are Plips nearby they could attack since plip squares do not count as empty squares).
     * 2. Otherwise, if any Plips are seen, the Clorus will ATTACK one of them randomly.
     * 3. Otherwise, if the Clorus has energy greater than or equal to one,
     * it will REPLICATE to a random empty square.
     * 4. Otherwise, the Clorus will MOVE to a random empty square.
     */
    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        //recon
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> preys = new ArrayDeque<>();
        boolean anyPlips = false;
        for (Direction d: neighbors.keySet()) {
            if (neighbors.get(d).name().equals("empty")) {
                emptyNeighbors.addFirst(d);
            } else if (neighbors.get(d).name().equals("plip")) {
                anyPlips = true;
                preys.addFirst(d);
            }
        }
        //Rule 1
        if (emptyNeighbors.size() == 0) {
            return new Action(Action.ActionType.STAY);
            //Rule 2
        } else if (anyPlips) {
            return new Action(Action.ActionType.ATTACK, randomEntry(preys));
            //Rule 3
        } else if (energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, randomEntry(emptyNeighbors));
        }
        //Rule 4
        return new Action(Action.ActionType.MOVE, randomEntry(emptyNeighbors));
    }
}
