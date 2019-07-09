package hw4.puzzle;

public class searchNode implements Comparable<searchNode> {
    public int priority;
    public int E;
    public int M;
    public searchNode prev;
    public WorldState current;

    public searchNode(WorldState c, int moves, searchNode p) {
        current = c;
        M = moves;
        E = current.estimatedDistanceToGoal();
        priority = E + M;
        prev = p;
    }

    public int priority() {
        return priority;
    }


    @Override
    public int compareTo(searchNode o) {
        return this.priority - o.priority;
    }
}