import edu.princeton.cs.algs4.Picture;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Stack;

public class SeamCarver {
    private Picture picture;
    private int[] edgeTo;
    private double[] energyMap;
    private double[] transposedEnergyMap;

    public SeamCarver(Picture picture) {
        this.picture = picture;
        edgeTo = new int[picture.width() * picture.height()];
        energyMap = new double[picture.width() * picture.height()];
        transposedEnergyMap = new double[picture.width() * picture.height()];
        for (int row = 0; row < picture.height(); row++) {
            for (int column = 0; column < picture.width(); column++) {
                int id = xyTo1D(column, row, picture);
                energyMap[id] = energy(column, row);
            }
        }
    }

    private IndexMinPQ<Double> getFringe(Picture p, double[] e) {
        IndexMinPQ<Double> fringe = new IndexMinPQ<>(p.width() * p.height());
        for (int i = 0; i < p.width(); i++) {
            fringe.insert(i, e[i]);
        }
        for (int i = p.width(); i < p.width() * p.height(); i++) {
            fringe.insert(i, Double.POSITIVE_INFINITY);
        }
        return fringe;
    }

    private double[] getDistance(Picture p, double[] e) {
        double[] disTo = new double[p.width() * p.height()];
        for (int i = 0; i < p.width(); i++) {
            disTo[i] = e[i];
        }
        for (int i = p.width(); i < p.width() * p.height(); i++) {
            disTo[i] = Double.POSITIVE_INFINITY;
        }
        return disTo;
    }

    public Picture picture() {
        return new Picture(picture);
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
        edgeTo = new int[picture.width() * picture.height()];
        Picture p = transpose();
        int[] ans = new int[p.height()];
        Stack<Integer> track = new Stack<>();
        double[] e = transposedEnergyMap;
        int destination = shortestPath(p, e);
        for (int i = 0; i < p.height(); i++) {
            track.push(destination);
            destination = edgeTo[destination];
        }
        int i = 0;
        while (!track.isEmpty()) {
            ans[i] = getColumn(track.pop(), p);
            i++;
        }
        return ans;
    }

    public int[] findVerticalSeam() {
        Picture p = picture;
        int[] ans = new int[p.height()];
        Stack<Integer> track = new Stack<>();
        double[] e = energyMap;
        int destination = shortestPath(p, e);
        for (int i = 0; i < p.height(); i++) {
            track.push(destination);
            destination = edgeTo[destination];
        }
        int i = 0;
        while (!track.isEmpty()) {
            ans[i] = getColumn(track.pop(), p);
            i++;
        }
        return ans;
    }

    public void removeHorizontalSeam(int[] seam) {
        SeamRemover.removeHorizontalSeam(picture, seam);
    }

    public void removeVerticalSeam(int[] seam) {
        /*
        if (seam == null) {
            throw new NullPointerException("no input");
        } else if (picture.height() == 1) {
            throw new IllegalArgumentException("image height is 1");
        } else if (seam.length != picture.height()) {
            throw new IllegalArgumentException("seam length does not match image height");
        } else {
            for (int i = 1; i < picture.height(); i++) {
                if (Math.abs(seam[i] - seam[i - 1]) > 1) {
                    throw new IllegalArgumentException("invalid seam, two consecutive seams");
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
        picture = newPicture;*/
        SeamRemover.removeVerticalSeam(picture, seam);
    }

    private int[][] getXNeighbors(int x, int y) {
        int[][] neighbors = new int[2][2];
        int[] left = new int[2];
        int[] right = new int[2];
        int xLeft = (x - 1 + picture.width()) % picture.width();
        int xRight = (x + 1 + picture.width()) % picture.width();
        left[0] = xLeft;
        left[1] = y;
        right[0] = xRight;
        right[1] = y;
        neighbors[0] = left;
        neighbors[1] = right;
        return neighbors;
    }

    private int[][] getYNeighbors(int x, int y) {
        int[][] neighbors = new int[2][2];
        int[] top = new int[2];
        int[] bottom = new int[2];
        int yTop = (y - 1 + picture.height()) % picture.height();
        int yBottom = (y + 1 + picture.height()) % picture.height();
        top[0] = x;
        top[1] = yTop;
        bottom[0] = x;
        bottom[1] = yBottom;
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

    private List<List<Integer>> getAdj(Picture p) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < p.width() * (p.height() - 1); i++) {
            if (i % p.width() == 0) {
                List<Integer> neighbors = new ArrayList<>();
                neighbors.add(i + p.width());
                neighbors.add(i + 1 + p.width());
                adj.add(i, neighbors);
            } else if ((i + 1) % p.width() == 0) {
                List<Integer> neighbors = new ArrayList<>();
                neighbors.add(i + p.width());
                neighbors.add(i + p.width() - 1);
                adj.add(i, neighbors);
            } else {
                List<Integer> neighbors = new ArrayList<>();
                neighbors.add(i + p.width() - 1);
                neighbors.add(i + p.width());
                neighbors.add(i + 1 + p.width());
                adj.add(i, neighbors);
            }
        }
        for (int i = p.width() * (p.height() - 1); i < p.width() * p.height(); i++) {
            List<Integer> neighbors = new ArrayList<>();
            neighbors.add(null);
            adj.add(i, neighbors);
        }
        return adj;
    }

    private int shortestPath(Picture p, double[] e) {
        int destination = -1;
        List<List<Integer>> adj = getAdj(p);
        IndexMinPQ<Double> fringe = getFringe(p, e);
        double[] disTo = getDistance(p, e);
        while (!fringe.isEmpty()) {
            int v = fringe.delMin();
            if (v < p.height() * p.width() && v >= (p.height() - 1) * p.width()) {
                destination = v;
                break;
            } else {
                for (int w : adj.get(v)) {
                    relax(v, w, fringe, disTo, e);
                }
            }
        }
        return destination;
    }

    private void relax(int v, int w, IndexMinPQ<Double> fringe, double[] disTo, double[] e) {
        if (disTo[w] > disTo[v] + e[w]) {
            disTo[w] = disTo[v] + e[w];
            edgeTo[w] = v;
            fringe.decreaseKey(w, disTo[w]);
        }
    }

    private int xyTo1D(int x, int y, Picture p) {
        return x + y * p.width();
    }

    private int getColumn(int x, Picture p) {
        return x % p.width();
    }

    /*
    private int getRow(int x, Picture p) {
        return x / p.width();
    }*/

    private Picture transpose() {
        Picture p = picture();
        Picture transposedP = new Picture(p.height(), p.width());
        for (int row = 0; row < transposedP.height(); row++) {
            for (int column = 0; column < transposedP.width(); column++) {
                Color c = p.get(row, column);
                transposedP.set(column, row, c);
                transposedEnergyMap[xyTo1D(column, row, transposedP)]
                        = energyMap[xyTo1D(row, column, p)];
            }
        }
        return transposedP;
    }
}
