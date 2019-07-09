package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Queue;
import java.util.ArrayDeque;


public class Solver {
    private MinPQ<searchNode> pq = new MinPQ<>();
    private searchNode start;
    private searchNode goal;

    public Solver(WorldState initial) {
        goal = null;
        start = new searchNode(initial, 0, null);
        pq.insert(start);
        while (!pq.isEmpty()) {
            searchNode v = pq.delMin();
            if (v.current.isGoal()) {
                goal = v;
                break;
            }
            for (WorldState next : v.current.neighbors()) {
                if (v.prev == null || !next.equals(v.prev.current)) {
                    pq.insert(new searchNode(next, v.M + 1, v));
                }
            }
        }
    }

    public int moves() {
        if (goal == start) {
            return 0;
        }
        return goal.M;
    }

    public Iterable<WorldState> solution() {
        Queue<WorldState> solution = new ArrayDeque<>();
        while (goal != null) {
            solution.add(goal.current);
            goal = goal.prev;
        }
        return solution;
    }

    public class searchNode implements Comparable<searchNode> {
        private int priority;
        private int E;
        private int M;
        private searchNode prev;
        private WorldState current;

        private searchNode(WorldState c, int moves, searchNode p) {
            current = c;
            M = moves;
            E = current.estimatedDistanceToGoal();
            priority = E + M;
            prev = p;
        }

        private int priority() {
            return priority;
        }


        @Override
        public int compareTo(searchNode o) {
            return this.priority - o.priority;
        }
    }
}


