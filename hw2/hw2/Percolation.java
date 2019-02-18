package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.List;
import java.util.ArrayList;

public class Percolation {
    private boolean[][] grid;
    private WeightedQuickUnionUF wquWithHT;
    private WeightedQuickUnionUF wquWithH;
    private int numberOfOpen;
    private int head;
    private int tail;

    /** create N-by-N grid, with all sites initially blocked */
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException(
                    "the input must greater than 0!");
        }

        numberOfOpen = 0;

        grid = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = false;
            }
        }

        wquWithHT = new WeightedQuickUnionUF(N * N + 2);
        wquWithH = new WeightedQuickUnionUF(N * N + 1);
        head = N * N;
        tail = N * N + 1;
    }

    /** convert 2 dimentional array into 1 dimentional array */
    private int xyTo1D(int r, int c) {
        return r * grid.length + c;
    }

    /** check whether there are open neighbor, if yes, union with it */
    private void checkUnion(int row, int col) {
        int here = xyTo1D(row, col);
        List<Integer> neighbors = new ArrayList<>();

        if (row > 0 && grid[row - 1][col] == true) {
            neighbors.add(xyTo1D(row - 1, col));
        }
        if (row < grid.length - 1 && grid[row + 1][col] == true) {
            neighbors.add(xyTo1D(row + 1, col));
        }
        if (col > 0 && grid[row][col - 1] == true) {
            neighbors.add(xyTo1D(row, col - 1));
        }
        if (col < grid.length - 1 && grid[row][col + 1] == true) {
            neighbors.add(xyTo1D(row, col + 1));
        }

        for (int i : neighbors) {
            wquWithHT.union(here, i);
            wquWithH.union(here, i);
        }
    }

    /** check for row, if at top of grid, union with head;
     * if at bottom of grid, union with tail */
    private void checkHeadTail(int row, int col) {
        if (row == 0) {
            wquWithHT.union(xyTo1D(row, col), head);
            wquWithH.union(xyTo1D(row, col), head);
        }
        if (row == grid.length - 1) {
            wquWithHT.union(xyTo1D(row, col), tail);
        }
    }

    /** check whether the inout row and col is inside the grid range */
    private void checkInput(int row, int col) {
        if (!(row >= 0 && row <= grid.length - 1
                && col >= 0 && col <= grid.length - 1)) {
            throw new java.lang.IndexOutOfBoundsException(
                    "the input row or column is not between 0 and n-1!");
        }
    }

    /** is the site (row, col) open? */
    public boolean isOpen(int row, int col) {
        checkInput(row, col);
        return grid[row][col];
    }

    /** open the site (row, col) if it is not open already */
    public void open(int row, int col) {
        checkInput(row, col);
        if (!isOpen(row, col)) {
            grid[row][col] = true;
            numberOfOpen += 1;
            checkUnion(row, col);
            checkHeadTail(row, col);
        }
    }

    /** is the site (row, col) full? */
    public boolean isFull(int row, int col) {
        checkInput(row, col);
        return wquWithH.connected(xyTo1D(row, col), head);
    }

    /** number of open sites */
    public int numberOfOpenSites() {
        return numberOfOpen;
    }

    /** does the system percolate? */
    public boolean percolates() {
        return wquWithHT.connected(head, tail);
    }

    public static void main(String[] args) {
//        Percolation test = new Percolation(5);
//        test.open(3, 4);
//        test.open(2, 4);
//        System.out.println(test.wquWithHT.connected(14, 19));
//        test.open(2, 2);
//        test.open(2, 3);
//        test.open(0, 2);
//        System.out.println(test.isFull(0, 2));
//        test.open(1, 2);
//        System.out.println(test.isFull(2, 2));
//        test.open(4, 4);
//        System.out.println(test.percolates());
//        System.out.println(test.numberOfOpenSites());
    }

}
