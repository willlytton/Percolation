import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    /*
  create a boolean array to initialize the array (n * n)
   */
    private boolean[] grid;
    private final int size; // reference to constructor parameter that takes in n size of grid
    private  int openSites;
    // create virtual top
    private final int top;
    // creates virtual bottom
    private final int bottom;
    // initializes a union find data structure
    private WeightedQuickUnionUF uf;
    private int currentIndex;

    /*
    initializes n-by-n grid array
    sets constructor parameter n to size
    uses size var to set size of grid
    sets grid to boolean n by n
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("argument is outside its prescribed range");
        }
        size = n;
        grid = new boolean[size * size];
        top = size * size + 1;
        bottom = size * size + 2;
        // calls union find data structure
        uf = new WeightedQuickUnionUF(size * size + 2);


    }

//    /*
//    should validate indices of site it opens
//    mark site as open
//    should perform some sequence of weightedquickunionUF operations that links the site in question to its open neighbors
//     */
    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        // checks if site is open and if not it opens site on grid
        if (!isOpen(row, col)) {
            int currentIndex = indexOfArray(row, col);
            grid[currentIndex] = true;
            openSites++;
        }
            /* checks if open site is in top row and indexes it
            supposed to connect top row to virtual top root and bottom to bottom root
             */
        if (row == 1) {
            uf.union(currentIndex, top);
        }

        if (row == size) {
            uf.union(currentIndex, bottom);
        }

        // checks if site (row - 1) is above current one and if open does union operation
        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(currentIndex, indexOfArray(row - 1, col));
        }

        // checks if (row site + 1) below is open and if so does union operation
        if (row < size && isOpen(row + 1, col)) {
            uf.union(currentIndex, indexOfArray(row + 1, col));
        }

        // checks adjacent (left cell - 1 from current) is open and does union op
        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(currentIndex , indexOfArray(row, col - 1));
        }

        // checks adjacent (right + 1) cell is open
        if (col < size && isOpen(row, col + 1)) {
            uf.union(currentIndex , indexOfArray(row, col + 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkValid(row, col);
        return grid[indexOfArray(row, col)];
    }

    public void checkValid(int row, int col) {
        if (row < 0 && col < 0 && row > size && col > size) {
            throw new IndexOutOfBoundsException("site coordinate is out of bounds");
        }
    }
    // translates int ID for 2D coordinates into 1D index
    private int indexOfArray(int row, int col) {
        // checks if site coordinate is valid
        checkValid(row, col);
        // returns index of array
        return size * (row - 1) + (col - 1);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return (uf.find(currentIndex) == uf.find(top));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return (uf.find(top) == uf.find(bottom));
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation grid = new Percolation(5);
        grid.open(1, 1);
        grid.open(1,2);

        System.out.println(grid.indexOfArray(2,2));

    }
}
