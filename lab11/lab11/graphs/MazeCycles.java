package lab11.graphs;
import edu.princeton.cs.algs4.Stack;

/**
 *  @author Josh Hug
 */

public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int[] cycle;
    private int cycleEnd = -1;
    private int s;
    private boolean cycleFound = false;

    public MazeCycles(Maze m) {
        super(m);
        s = maze.xyTo1D(1, 1);
        cycle = new int[maze.V()];
        cycle[s] = s;
    }



    @Override
    public void solve() {
        findCycle(s);
        if (cycleEnd == -1) {
            return;
        }
    }

    private void findCycle(int v) {
        marked[v] = true;
        announce();
        for (int w: maze.adj(v)) {
            if (!marked[w]) {
                cycle[w] = v;
                findCycle(w);
            } else if (marked[w] && w != cycle[v]) {
                edgeTo[w] = v;
                announce();
                cycleFound = true;
                cycleEnd = w;
                int lastVertex = edgeTo[cycleEnd];
                while (lastVertex != cycleEnd) {
                    edgeTo[lastVertex] = cycle[lastVertex];
                    announce();
                    lastVertex = edgeTo[lastVertex];
                }
                break;
            } else {
                continue;
            }
            if (cycleFound) {
                return;
            }
        }
    }


}



