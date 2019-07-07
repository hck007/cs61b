package lab11.graphs;
import edu.princeton.cs.algs4.Stack;

import java.util.Iterator;


/**
 *  @author Josh Hug
 */
public class MazeDepthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;


    public MazeDepthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    private void dfs(int v) {
        marked[v] = true;
        announce();

        if (v == t) {
            targetFound = true;
        }

        if (targetFound) {
            return;
        }

        for (int w : maze.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                announce();
                distTo[w] = distTo[v] + 1;
                dfs(w);
                if (targetFound) {
                    return;
                }
            }
        }
    }


/*
    iterative version of DFS
    public void dfs(int s) {
        Stack<Integer> stack = new Stack<>();
        stack.push(s);
        marked[s] = true;
        announce();
        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (v == t) {
                targetFound = true;
                return;
            }
            for (int w: maze.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    announce();
                    distTo[w] = distTo[v] + 1;
                    stack.push(w);
                }
            }
        }
    }
*/

    @Override
    public void solve() {
        dfs(s);
    }
}

