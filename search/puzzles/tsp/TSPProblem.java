package search.puzzles.tsp;

import search.Problem;
import java.util.*;

public class TSPProblem implements Problem<TSPState, TSPAction> {
    private final double[][] distances; // symmetric matrix assumed
    private final int n;
    private final int startCity;
    private final TSPState initialState;
    private final int allVisitedMask;

    public TSPProblem(double[][] distances, int startCity) {
        this.distances = distances;
        this.n = distances.length;
        this.startCity = startCity;
        this.allVisitedMask = (1 << n) - 1;
        this.initialState = new TSPState(startCity, 1 << startCity);
    }

    public double[][] getDistances() { return distances; }
    public int getStartCity() { return startCity; }

    @Override
    public TSPState getInitialState() { return initialState; }

    @Override
    public boolean isGoal(TSPState state) {
        return state.visitedMask == allVisitedMask && state.currentCity == startCity;
    }

    @Override
    public List<TSPAction> getActions(TSPState state) {
        List<TSPAction> actions = new ArrayList<>();
        // If all visited and not yet back to start, only action is return to start
        if (state.visitedMask == allVisitedMask) {
            if (state.currentCity != startCity) actions.add(new TSPAction(startCity));
            return actions;
        }
        for (int city = 0; city < n; city++) {
            if (!state.isVisited(city)) {
                actions.add(new TSPAction(city));
            }
        }
        return actions;
    }

    @Override
    public TSPState getResult(TSPState state, TSPAction action) {
        int next = action.nextCity;
        if (next == startCity && state.visitedMask == allVisitedMask) {
            return new TSPState(startCity, state.visitedMask); // closing tour
        }
        return state.visit(next);
    }

    @Override
    public double getStepCost(TSPState state, TSPAction action) {
        return distances[state.currentCity][action.nextCity];
    }

    @Override
    public TSPState getGoalState() {
        return new TSPState(startCity, allVisitedMask);
    }
}

