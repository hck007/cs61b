package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;



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
        Stack<WorldState> solution = new Stack<>();
        while (goal != null) {
            solution.push(goal.current);
            goal = goal.prev;
        }
        return solution;
    }
}


