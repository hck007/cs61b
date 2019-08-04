import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int width;
    private int height;
    private char[] board;
    private List<List<Integer>> adj = new ArrayList<>();

    public Board(String file) {
        In in = new In(file);
        width = new In(file).readLine().length();
        height = new In(file).readAllLines().length;
        board = new char[width * height];
        for (int i = 0; i < height; i++) {
            String s = in.readLine();
            for (int j = 0; j < s.length(); j++) {
                board[i * width + j] = s.charAt(j);
            }
        }
        neighbors();
    }

    //get the adjacency list
    public void neighbors() {
        //first row
        for (int i = 0; i < width; i++) {
            List<Integer> neighbors = new ArrayList<>();
            if (i % width == 0) {
                neighbors.add(i + 1);
                neighbors.add(i + width);
                neighbors.add(i + width + 1);
            } else if ((i + 1) % width == 0) {
                neighbors.add(i - 1);
                neighbors.add(i + width);
                neighbors.add(i + width - 1);
            } else {
                neighbors.add(i - 1);
                neighbors.add(i + 1);
                neighbors.add(i + width);
                neighbors.add(i + width + 1);
                neighbors.add(i + width - 1);
            }
            adj.add(i, neighbors);
        }
        //second row to last second row
        for (int j = width; j < width * (height - 1); j++) {
            List<Integer> neighbors = new ArrayList<>();
            if (j % width == 0) {
                neighbors.add(j - width);
                neighbors.add(j + width);
                neighbors.add(j - width + 1);
                neighbors.add(j + width + 1);
                neighbors.add(j + 1);
            } else if ((j + 1) % width == 0){
                neighbors.add(j - width);
                neighbors.add(j + width);
                neighbors.add(j - width - 1);
                neighbors.add(j + width - 1);
                neighbors.add(j - 1);
            } else {
                neighbors.add(j - width);
                neighbors.add(j + width);
                neighbors.add(j - width - 1);
                neighbors.add(j - width + 1);
                neighbors.add(j + width - 1);
                neighbors.add(j + width + 1);
                neighbors.add(j - 1);
                neighbors.add(j + 1);
            }
            adj.add(neighbors);
        }
        //last row
        for (int k = width * (height - 1); k < width * height; k++) {
            List<Integer> neighbors = new ArrayList<>();
            if (k % width == 0) {
                neighbors.add(k + 1);
                neighbors.add(k - width);
                neighbors.add(k - width + 1);
            } else if ((k + 1) % width == 0) {
                neighbors.add(k - 1);
                neighbors.add(k - width);
                neighbors.add(k - width - 1);
            } else {
                neighbors.add(k + 1);
                neighbors.add(k - 1);
                neighbors.add(k - width);
                neighbors.add(k - width + 1);
                neighbors.add(k - width - 1);
            }
            adj.add(neighbors);
        }
    }

    public List<List<Integer>> getAdj() {
        return adj;
    }

    public char getChar(int i) {
        return board[i];
    }


    public void Print() {
        System.out.println(width);
        System.out.println(height);
        System.out.print(board);
    }
}
