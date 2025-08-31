package search.puzzles.eightpuzzle;

import search.Problem;
import java.util.*;

public class EightPuzzleProblem implements Problem<State, Action> {
    private final State initialState;
    private final State goalState;
    private final boolean isSolvable;

    public EightPuzzleProblem(State initialState, State goalState) {
        this.initialState = initialState;
        this.goalState = goalState;
        this.isSolvable = isSolvable(initialState);
        if (!this.isSolvable) {
            System.out.println("This initial state cannot be solved (parity test failed). Problem will not be solved.");
        }
    }

    public boolean isSolvable() {
        return isSolvable;
    }

    // Parity test for 8-tile puzzle
    private boolean isSolvable(State state) {
        int[] tiles = state.getTiles();
        int inversions = 0;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == 0) continue; // skip blank
            for (int j = i + 1; j < tiles.length; j++) {
                if (tiles[j] == 0) continue;
                if (tiles[i] > tiles[j]) inversions++;
            }
        }
        return inversions % 2 == 0;
    }

    @Override
    public State getInitialState() {
        return initialState;
    }

    @Override
    public boolean isGoal(State state) {
        return state.equals(goalState);
    }

    @Override
    public List<Action> getActions(State state) {
        List<Action> actions = new ArrayList<>();
        int row = state.getRow();
        int col = state.getCol();

        if (row > 0) actions.add(Action.UP);
        if (row < 2) actions.add(Action.DOWN);
        if (col > 0) actions.add(Action.LEFT);
        if (col < 2) actions.add(Action.RIGHT);

        return actions;
    }

    public State getGoalState() {
        return goalState;
    }

    @Override
    public State getResult(State state, Action action) {
        return state.move(action);
    }

    @Override
    public double getStepCost(State state, Action action) {
        return 1; // every move costs 1
    }
}
