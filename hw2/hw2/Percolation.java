
package hw2;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size;
    private int topNode;
    private int bottomNode;
    private WeightedQuickUnionUF top;
    private WeightedQuickUnionUF bottom;
    private boolean[] siteStatus;
    private int openSites;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        topNode = -1;
        bottomNode = N * N;
        top = new WeightedQuickUnionUF(N * N + 2); //create top disjoint set
        bottom = new WeightedQuickUnionUF(N + 1); //create bottom disjoint set
        openSites = 0;
        size = N;
        siteStatus = new boolean[N * N];
        for (int i = 0; i < siteStatus.length; i++) {
            siteStatus[i] = false;
        }
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        //skip open method if the site is already open
        if (isOpen(row, col)) {
            return;
        }
        //open a blocked site
        if (!isOpen(row, col)) {
            siteStatus[xyTo1D(row, col)] = true;
            openSites++;
        }
        //connect a first-row site to the virtual topNode
        if (row == 0) {
            top.union(topNode, xyTo1D(row, col));
        }
        //connect a last-row site to the virtual bottomNode
        if (row == size - 1) {
            bottom.union(bottomNode, xyTo1D(row, col));
        }
        //if an open site is adjacent to another open site, connect them
        if (isOpen(row, col) == isOpen(row - 1, col)) {
            top.union(xyTo1D(row, col), xyTo1D(row - 1, col));
        } else if (isOpen(row, col) == isOpen(row + 1, col)) {
            top.union(xyTo1D(row, col), xyTo1D(row + 1, col));
        } else if (isOpen(row, col) == isOpen(row, col - 1)) {
            top.union(xyTo1D(row, col), xyTo1D(row, col - 1));
        } else if (isOpen(row, col) == isOpen(row, col + 1)) {
            top.union(xyTo1D(row, col), xyTo1D(row, col + 1));
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int id = xyTo1D(row, col);
        boolean status = siteStatus[id];
        return status;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return top.connected(topNode, xyTo1D(row, col));
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (top.connected(topNode, bottomNode)) {
            return true;
        }
        return false;
    }

    private int xyTo1D(int row, int col) {
        int id = row * size + col;
        return id;
    }

    // use for unit testing (not required, but keep this here for the autograder)
    public static void main(String[] args) {

    }


}

