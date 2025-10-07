package search.puzzles.tsp;

import search.AStar;
import search.Problem;
import java.util.*;

/**
 * MST-based admissible heuristic for TSP using Prim's algorithm (dense O(k^2)).
 * h(n) = (if not all visited) min(dist(current, U)) + MST(U) + min(dist(U, start))
 * where U is the set of unvisited cities. If all visited but not at start, h = dist(current,start).
 */
public class MSTHeuristic implements AStar.Heuristic<TSPState, TSPAction> {

    @Override
    public double estimate(TSPState state, Problem<TSPState, TSPAction> problem) {
        TSPProblem tsp = (TSPProblem) problem; // safe cast for this specific heuristic
        double[][] d = tsp.getDistances();
        int start = tsp.getStartCity();
        int n = d.length;

        // Gather unvisited cities
        List<Integer> unvisited = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!state.isVisited(i)) unvisited.add(i);
        }

        // Goal state heuristic = 0
        if (unvisited.isEmpty()) {
            if (state.currentCity == start) return 0; // already complete tour
            return d[state.currentCity][start]; // just cost to return home
        }

        // Part 1: min edge from current city to any unvisited city
        double minFromCurrent = Double.POSITIVE_INFINITY;
        for (int city : unvisited) {
            minFromCurrent = Math.min(minFromCurrent, d[state.currentCity][city]);
        }

        // Part 2: min edge from any unvisited city back to start (to close tour)
        double minToStart = Double.POSITIVE_INFINITY;
        for (int city : unvisited) {
            minToStart = Math.min(minToStart, d[city][start]);
        }

        // Part 3: MST over unvisited cities (Prim now)
        double mstCost = computeMST(unvisited, d);

        return minFromCurrent + mstCost + minToStart;
    }

    private double computeMST(List<Integer> nodes, double[][] d) {
        int k = nodes.size();
        if (k <= 1) return 0; // no edges needed

        boolean[] inTree = new boolean[k];
        double[] best = new double[k];
        Arrays.fill(best, Double.POSITIVE_INFINITY);
        best[0] = 0.0; // start from first node in the subset

        double total = 0.0;
        for (int iter = 0; iter < k; iter++) {
            int sel = -1;
            double selVal = Double.POSITIVE_INFINITY;
            for (int i = 0; i < k; i++) {
                if (!inTree[i] && best[i] < selVal) {
                    selVal = best[i];
                    sel = i;
                }
            }
            inTree[sel] = true;
            if (iter > 0) total += selVal; // skip adding the initial 0 key

            int uCity = nodes.get(sel);
            for (int j = 0; j < k; j++) {
                if (inTree[j]) continue;
                int vCity = nodes.get(j);
                double w = d[uCity][vCity];
                if (w < best[j]) best[j] = w;
            }
        }
        return total;
    }
}
