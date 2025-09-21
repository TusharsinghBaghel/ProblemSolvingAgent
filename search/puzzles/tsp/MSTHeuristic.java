package search.puzzles.tsp;

import search.AStar;
import search.Problem;
import java.util.*;

/**
 * MST-based admissible heuristic for TSP using Kruskal's algorithm.
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

        // Part 3: MST over unvisited cities (Kruskal)
        double mstCost = computeMST(unvisited, d);

        return minFromCurrent + mstCost + minToStart;
    }

    private double computeMST(List<Integer> nodes, double[][] d) {
        int k = nodes.size();
        if (k <= 1) return 0; // 0 or 1 node => no edges

        // Map city id to index in MST set for union-find
        Map<Integer, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < k; i++) indexMap.put(nodes.get(i), i);

        class Edge { int u, v; double w; }
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            for (int j = i + 1; j < k; j++) {
                Edge e = new Edge();
                e.u = i;
                e.v = j;
                e.w = d[nodes.get(i)][nodes.get(j)];
                edges.add(e);
            }
        }
        edges.sort(Comparator.comparingDouble(e -> e.w));

        // Union-Find
        int[] parent = new int[k];
        int[] rank = new int[k];
        for (int i = 0; i < k; i++) parent[i] = i;

        java.util.function.IntUnaryOperator find = new java.util.function.IntUnaryOperator() {
            @Override public int applyAsInt(int x) { return parent[x] == x ? x : (parent[x] = applyAsInt(parent[x])); }
        };
        java.util.function.BiConsumer<Integer,Integer> union = (a,b) -> {
            int ra = find.applyAsInt(a);
            int rb = find.applyAsInt(b);
            if (ra == rb) return;
            if (rank[ra] < rank[rb]) parent[ra] = rb; else if (rank[ra] > rank[rb]) parent[rb] = ra; else { parent[rb] = ra; rank[ra]++; }
        };

        double total = 0;
        int edgesUsed = 0;
        for (Edge e : edges) {
            int ru = find.applyAsInt(e.u);
            int rv = find.applyAsInt(e.v);
            if (ru != rv) {
                union.accept(ru, rv);
                total += e.w;
                edgesUsed++;
                if (edgesUsed == k - 1) break;
            }
        }
        return total;
    }
}

