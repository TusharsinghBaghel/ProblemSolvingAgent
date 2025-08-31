import search.*;
import search.puzzles.eightpuzzle.*;

public class main {
    public static void main(String[] args) {
        // Initial state (0 = blank)
        int[] startTiles = {8, 6, 7,
                            2, 5, 4,
                            3, 0, 1};

        int[] goalTiles = {1, 2, 3,
                           4, 5, 6,
                           7, 8, 0};

        State start = new State(startTiles);
        State goal = new State(goalTiles);

        Problem<State, Action> problem = new EightPuzzleProblem(start, goal);

        if (!((EightPuzzleProblem) problem).isSolvable()) {
            System.out.println("No solution possible for this initial state.");
            return;
        }

        // Choose search algorithm
        SearchAlgorithm<State, Action> searchAlgorithm = new IDS<>();
        searchAlgorithm.search(problem).ifPresentOrElse(
                node -> {
                    System.out.println("Solution found with " + node.pathCost + " moves.");
                    System.out.println("Moves: " + node.getSolution());
                    System.out.println("Final state:\n" + node.state);
                },
                () -> System.out.println("No solution found.")
        );
    }
}
