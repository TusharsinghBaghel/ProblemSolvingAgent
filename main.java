import search.*;
import search.puzzles.eightpuzzle.*;
import search.puzzles.tsp.*;

public class main {
    public static void main(String[] args) {
        // 1) Eight Puzzle Demo (unchanged)
        int[] startTiles = {8, 6, 7,
                3, 4, 5,
                2, 0, 1};

        int[] goalTiles = {1, 2, 3,
                4, 5, 6,
                7, 8, 0};

        State start = new State(startTiles);
        State goal = new State(goalTiles);

        Problem<State, Action> puzzleProblem = new EightPuzzleProblem(start, goal);

        if (!((EightPuzzleProblem) puzzleProblem).isSolvable()) {
            System.out.println("No solution possible for this initial 8-puzzle state. Skipping to TSP demo.\n");
        } else {
            SearchAlgorithm<State, Action> searchAlgorithm = new Bidirectional<>();
            searchAlgorithm.search(puzzleProblem).ifPresentOrElse(
                    node -> {
                        System.out.println("8-Puzzle solution found with " + node.pathCost + " moves.");
                        System.out.println("Moves: " + node.getSolution());
                        System.out.println("Final state:\n" + node.state);
                    },
                    () -> System.out.println("No 8-puzzle solution found."));
        }

        // 2) Travelling Salesman Problem Demo solved via A* + MST (Kruskal) heuristic
        // Distance matrix (symmetric) for 5-city instance
        double[][] distances = {
                {0, 2, 9, 10, 7},
                {2, 0, 6, 4, 3},
                {9, 6, 0, 8, 5},
                {10, 4, 8, 0, 6},
                {7, 3, 5, 6, 0}
        };
        int startCity = 0;
        TSPProblem tspProblem = new TSPProblem(distances, startCity);

        AStar.Heuristic<TSPState, TSPAction> mstHeuristic = new MSTHeuristic();
        SearchAlgorithm<TSPState, TSPAction> tspSearch = new AStar<>(mstHeuristic);

        System.out.println("\nRunning A* on TSP with MST heuristic (Kruskal)...");
        long begin = System.currentTimeMillis();
        tspSearch.search(tspProblem).ifPresentOrElse(
                node -> {
                    long elapsed = System.currentTimeMillis() - begin;
                    System.out.println("TSP tour cost: " + node.pathCost);
                    System.out.println("Action sequence: " + node.getSolution());
                    System.out.println("Goal state reached: " + node.state);
                    System.out.println("Elapsed ms: " + elapsed);
                },
                () -> System.out.println("No TSP solution found."));
    }
}
