package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    public MazeCycles(Maze m) {
        super(m);
    }


    @Override
    public void solve() {
        int s = maze.xyTo1D(1, 1);
        distTo[s] = 0;
        edgeTo[s] = s;
        dfs(s);
    }

    // help method
    private void dfs(int v) {
        marked[v] = true;
        announce();

        for (int u : maze.adj(v)) {
            if (!marked[u]) {
                edgeTo[u] = v;
                announce();
                distTo[u] = distTo[v] + 1;
                dfs(u);
            } else {
                if (edgeTo[v] != u) {
                    announce();
                    return;
                }
            }
        }
    }
}

