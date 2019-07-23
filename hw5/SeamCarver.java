import edu.princeton.cs.algs4.Picture;
import java.util.ArrayList;
import java.util.List;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Stack;

public class SeamCarver {
    private Picture picture;
    private IndexMinPQ<Double> fringe;
    private double[] disTo;
    private int[] edgeTo;

    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
        fringe = new IndexMinPQ<>(picture.width() * picture.height());
        disTo = new double[picture.width() * picture.height()];
        edgeTo = new int[picture.width() * picture.height()];
        for (int i = 0; i < picture.width(); i++) {
            fringe.insert(i, energy(i, 0));
            disTo[i] = energy(i, 0);
        }
        for (int i = picture.width(); i < picture.width() * picture.height(); i++) {
            fringe.insert(i, Double.POSITIVE_INFINITY);
            disTo[i] = Double.POSITIVE_INFINITY;
        }
    }

    public Picture picture() {
        return picture;
    }

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
    }

    public double energy(int x, int y) {
        validateInput(x, y);
        return getXGradient(x, y) + getYGradient(x, y);
    }

    public int[] findHorizontalSeam() {
        return null;
    }

    public int[] findVerticalSeam() {
        int[] ans = new int[picture.height()];
        Stack<Integer> track = new Stack<>();
        int destination = shortestPath();
        for (int i = 0; i < picture.height(); i++) {
            track.push(destination);
            destination = edgeTo[destination];
        }
        int i = 0;
        while (!track.isEmpty()) {
            ans[i] = getColumn(track.pop());
            i++;
        }
        return ans;
    }

    public void removeHorizontalSeam(int[] seam) {

    }

    public void removeVerticalSeam(int[] seam) {
        if (seam == null) {
            throw new NullPointerException("no input");
        } else if (picture.height() == 1) {
            throw new IllegalArgumentException("image height is 1");
        } else if (seam.length != picture.height()) {
            throw new IllegalArgumentException("seam length does not match image height");
        } else {
            for (int i = 1; i < picture.height(); i++) {
                if (Math.abs(seam[i] - seam[i - 1]) > 1) {
                    throw new IllegalArgumentException("invalid seam, two consecutive seams are 1 column apart");
                }
            }
        }
        Picture newPicture = new Picture(picture.width() - 1, picture.height());
        for (int row = 0; row < picture.height(); row++) {
            int column;
            for (column = 0; column < seam[row]; column++) {
                newPicture.set(column, row, picture.get(column, row));
            }
            for (column = seam[row] + 1; column < picture.width(); column++) {
                newPicture.set(column, row, picture.get(column, row));
            }
        }
        picture = newPicture;
    }

    private int[][] getXNeighbors(int x, int y) {
        int[][] neighbors = new int[2][2];
        int[] left = new int[2];
        int[] right = new int[2];
        int x_left = (x - 1 + picture.width()) % picture.width();
        int x_right = (x + 1 + picture.width()) % picture.width();
        left[0] = x_left;
        left[1] = y;
        right[0] = x_right;
        right[1] = y;
        neighbors[0] = left;
        neighbors[1] = right;
        return neighbors;
    }

    private int[][] getYNeighbors(int x, int y) {
        int[][] neighbors = new int[2][2];
        int[] top = new int[2];
        int[] bottom = new int[2];
        int y_top = (y - 1 + picture.height()) % picture.height();
        int y_bottom = (y + 1 + picture.height()) % picture.height();
        top[0] = x;
        top[1] = y_top;
        bottom[0] = x;
        bottom[1] = y_bottom;
        neighbors[0] = top;
        neighbors[1] = bottom;
        return neighbors;
    }

    private double getXGradient(int x, int y) {
        int[][] neighbors = getXNeighbors(x, y);
        double r = (double) picture.get(neighbors[0][0], neighbors[0][1]).getRed()
                - (double) picture.get(neighbors[1][0], neighbors[1][1]).getRed();
        double b = (double) picture.get(neighbors[0][0], neighbors[0][1]).getBlue()
                - (double) picture.get(neighbors[1][0], neighbors[1][1]).getBlue();
        double g = (double) picture.get(neighbors[0][0], neighbors[0][1]).getGreen()
                - (double) picture.get(neighbors[1][0], neighbors[1][1]).getGreen();
        return r * r + b * b + g * g;
    }

    private double getYGradient(int x, int y) {
        int[][] neighbors = getYNeighbors(x, y);
        double r = (double) picture.get(neighbors[0][0], neighbors[0][1]).getRed()
                - (double) picture.get(neighbors[1][0], neighbors[1][1]).getRed();
        double b = (double) picture.get(neighbors[0][0], neighbors[0][1]).getBlue()
                - (double) picture.get(neighbors[1][0], neighbors[1][1]).getBlue();
        double g = (double) picture.get(neighbors[0][0], neighbors[0][1]).getGreen()
                - (double) picture.get(neighbors[1][0], neighbors[1][1]).getGreen();
        return r * r + b * b + g * g;
    }

    private void validateInput(int x, int y) {
        if (x < 0 || x >= picture.width() || y < 0 || y > picture.height()) {
            throw new IndexOutOfBoundsException("invalid input");
        }
    }

    private List<List<Integer>> getAdj() {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < picture.width() * (picture.height() - 1); i++) {
            if (i % picture.width() == 0) {
                List<Integer> neighbors = new ArrayList<>();
                neighbors.add(i + picture.width());
                neighbors.add(i + 1 + picture.width());
                adj.add(i, neighbors);
            } else if ((i + 1) % picture.width() == 0) {
                List<Integer> neighbors = new ArrayList<>();
                neighbors.add(i + picture.width());
                neighbors.add(i + picture.width() - 1);
                adj.add(i, neighbors);
            } else {
                List<Integer> neighbors = new ArrayList<>();
                neighbors.add(i + picture.width() - 1);
                neighbors.add(i + picture.width());
                neighbors.add(i + 1 + picture.width());
                adj.add(i, neighbors);
            }
        }
        for (int i = picture.width() * (picture.height() - 1); i < picture.width() * picture.height(); i++) {
            List<Integer> neighbors = new ArrayList<>();
            neighbors.add(null);
            adj.add(i, neighbors);
        }
        return adj;
    }

    private int shortestPath() {
        int destination = -1;
        List<List<Integer>> adj = getAdj();
        while (!fringe.isEmpty()) {
            int v = fringe.delMin();
            if (v < picture.height() * picture.width() && v >= (picture.height() - 1) * picture.width()) {
                destination = v;
                break;
            } else {
                for (int w : adj.get(v)) {
                    relax(v, w);
                }
            }
        }
        return destination;
    }

    private void relax(int v, int w) {
        if (disTo[w] > disTo[v] + energy(getColumn(w), getRow(w))) {
            disTo[w] = disTo[v] + energy(getColumn(w), getRow(w));
            edgeTo[w] = v;
            fringe.decreaseKey(w, disTo[w]);
        }
    }

    private int xyTo1D(int x, int y) {
        return x + y * picture.width();
    }

    private int getColumn(int x) {
        return x % picture.width();
    }

    private int getRow(int x) {
        return x / picture.width();
    }
}
