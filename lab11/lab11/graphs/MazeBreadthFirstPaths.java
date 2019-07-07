package lab11.graphs;
import java.util.Queue;
import java.util.ArrayDeque;



/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private Maze maze;
    private boolean targetFound = false;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        edgeTo[s] = s;
        distTo[s] = 0;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        Queue<Integer> q = new ArrayDeque<>();
        marked[s] = true;
        q.add(s);
        announce();
        while (!q.isEmpty()) {
            int v = q.remove();
            if (v == t) {
                targetFound = true;
                return;
            }
            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    q.add(w);
                    marked[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

