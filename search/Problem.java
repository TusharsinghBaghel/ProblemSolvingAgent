package search;

import java.util.List;

public interface Problem<State, Action> {
    State getInitialState();
    boolean isGoal(State state);
    List<Action> getActions(State state);
    State getResult(State state, Action action);
    double getStepCost(State state, Action action);
    State getGoalState();
}
