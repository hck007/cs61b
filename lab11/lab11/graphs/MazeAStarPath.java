package lab11.graphs;
import edu.princeton.cs.algs4.IndexMinPQ;

import java.util.ArrayDeque;
import java.util.Queue;


/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int sourceX = maze.toX(v);
        int sourceY = maze.toY(v);
        int targetX = maze.toX(t);
        int targetY = maze.toY(t);
        return Math.abs(sourceX - targetX) + Math.abs(sourceY - targetY);
    }

    /** Finds vertex estimated to be closest to target. */
    private void findMinimumUnmarked(int v, int w, IndexMinPQ pq) {
        if (distTo[w] > distTo[v] + 1) {
            distTo[w] = distTo[v] + 1;
            edgeTo[w] = v;
            announce();
            if (pq.contains(w)) {
                pq.decreaseKey(w, distTo[w] + h(w));
            } else {
                pq.insert(w, distTo[w] + h(w));
        }
        }
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        IndexMinPQ<Integer> pq = new IndexMinPQ<>(maze.V());
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            if (v == t) {
                targetFound = true;
                break;
            }
            marked[v] = true;
            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    findMinimumUnmarked(v, w, pq);
                }
            }
        }
    }




    @Override
    public void solve() {
        astar(s);
    }

}

