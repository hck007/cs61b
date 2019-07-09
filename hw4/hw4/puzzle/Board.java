package hw4.puzzle;
import edu.princeton.cs.algs4.Queue;


public class Board implements WorldState{
    private int[][] board;
    private int N;
    private int[][] goal;

    public Board(int[][] tiles) {
        N = tiles.length;
        board = new int[N][N];
        goal = new int[N][N];
        goal[N - 1][N - 1] = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] > N * N - 1 || tiles[i][j] < 0) {
                    throw new IndexOutOfBoundsException();
                }
                board[i][j] = tiles[i][j];
                goal[i][j] = i * N + j + 1;
            }
        }
    }

    public int tileAt(int i, int j) {
        if (i < 0 || i > N - 1 || j < 0 || j > N - 1) {
            throw new IllegalArgumentException();
        }
        return board[i][j];
    }

    public int size() {
        return N;
    }

    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == 0) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = 0;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = 0;
                }
            }
        }
        return neighbors;
    }

    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] != goal[i][j] && board[i][j] != 0) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] != 0) {
                    int expectedR = (board[i][j] - 1) / size();
                    int expectedC = (board[i][j] - 1) % size();
                    manhattan += (Math.abs(expectedR - i) + Math.abs(expectedC - j));
                }
            }
        }
        return manhattan;
    }

    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (this.getClass() != y.getClass()) {
            return false;
        }
        if (y == null) {
            return false;
        }
        Board other = (Board) y;
        if (this.N != ((Board) y).N) {
            return false;
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.board[i][j] != other.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = result * 31 + goal.hashCode();
        return result;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }



}
