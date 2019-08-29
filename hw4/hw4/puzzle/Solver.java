package hw4.puzzle;
import java.util.ArrayList;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    private ArrayList<WorldState> solution;

    /* Constructor which solves the puzzle, computing everything necessary for moves() and solution() to
     not have to solve the problem again. Solves the puzzle using the A* algorithm. Assumes a solution exists. **/
    public Solver(WorldState initial) {
        solution = new ArrayList<>();

        // create a priority queue of search nodes
        MinPQ<SNode> pq = new MinPQ<>();

        // insert an “initial search node” into the priority queue
        SNode start = new SNode(initial, 0, null);
        pq.insert(start);

        // If the search node with minimum priority is the goal node, then we’re done.
        while (!pq.min().ws.isGoal()) {

            // Remove the search node with minimum priority.
            SNode X = pq.delMin();

            // for each neighbor of X’s world state, create a new search node and insert it into the priority queue.
            for (WorldState nb: X.ws.neighbors()) {

                // critical optimization
                if (X.prev == null || !(nb.equals(X.prev.ws))) {
                    SNode nbNode = new SNode(nb, X.numberOfMove + 1, X);
                    pq.insert(nbNode);
                }
            }
        }

        // generate the solution
        SNode goal = pq.min();
        while (goal != null) {
            solution.add(0, goal.ws);
            goal = goal.prev;
        }
    }


    /* define a search node of the puzzle. Each SearchNode represents one “move sequence” as defined in
    the conceptual description of Best-First Search. **/
    private class SNode implements Comparable<SNode> {
        private WorldState ws;
        private int numberOfMove;
        private SNode prev;

        SNode(WorldState ws, int numberOfMove, SNode prev) {
            this.ws = ws;
            this.numberOfMove = numberOfMove;
            this.prev = prev;
        }

        @Override
        public int compareTo(SNode o) {
            return (this.numberOfMove + this.ws.estimatedDistanceToGoal()) - (o.numberOfMove + o.ws.estimatedDistanceToGoal());
        }
    }

    /* Returns the minimum number of moves to solve the puzzle starting at the initial WorldState.**/
    public int moves() {
        return solution.size() - 1;
    }

    /* Returns a sequence of WorldStates from the initial WorldState to the solution. **/
    public Iterable<WorldState> solution() {
        return solution;
    }
}
