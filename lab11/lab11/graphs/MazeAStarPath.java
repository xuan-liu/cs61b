package lab11.graphs;

import edu.princeton.cs.algs4.MinPQ;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(v) - maze.toX(this.t)) + Math.abs(maze.toY(v) - maze.toY(this.t));
    }

    /** define a node of the maze. Each Node represents one “move sequence” */
    private class Node implements Comparable<Node> {
        int v;
        int dist;
        int h;

        Node(int v, int d, int h) {
            this.v = v;
            this.dist = d;
            this.h = h;
        }

        @Override
        public int compareTo(Node o) {
            return (this.dist + this.h) - (o.dist + o.h);
        }
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        marked[s] = true;
        announce();

        MinPQ<Node> pq = new MinPQ<>();

        Node start = new Node(s, distTo[s], h(s));
        pq.insert(start);

        while (!pq.isEmpty()) {
            if (pq.min().v == t) {
                return;
            }
            // Remove the node with minimum priority.
            Node X = pq.delMin();

            // for each neighbor of X, create a new search node and
            // insert it into the priority queue.
            for (int nb: maze.adj(X.v)) {
                if (X.v == s || (nb != edgeTo[X.v])) {
                    edgeTo[nb] = X.v;
                    marked[nb] = true;
                    announce();

                    distTo[nb] = distTo[X.v] + 1;
                    Node nbNode = new Node(nb, distTo[nb], h(nb));
                    pq.insert(nbNode);
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }
}

