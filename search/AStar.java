package search;

import java.util.*;

public class AStar<State, Action> implements SearchAlgorithm<State, Action> {
    public interface Heuristic<State, Action> {
        double estimate(State state, Problem<State, Action> problem);
    }

    private final Heuristic<State, Action> heuristic;
    int nodesExpanded = 0;

    public AStar(Heuristic<State, Action> heuristic) {
        this.heuristic = heuristic;
    }

    @Override
    public Optional<Node<State, Action>> search(Problem<State, Action> problem) {
        Comparator<Node<State, Action>> cmp = Comparator.comparingDouble(n -> n.pathCost + heuristic.estimate(n.state, problem));
        PriorityQueue<Node<State, Action>> open = new PriorityQueue<>(cmp);
        Map<State, Double> bestG = new HashMap<>();

        Node<State, Action> start = new Node<>(problem.getInitialState(), null, null, 0);
        open.add(start);
        bestG.put(start.state, 0.0);

        while (!open.isEmpty()) {
            Node<State, Action> current = open.poll();
            double fCurrent = current.pathCost + heuristic.estimate(current.state, problem);

            if (problem.isGoal(current.state)) {
                System.out.println("Nodes expanded for A*: " + nodesExpanded);
                return Optional.of(current);
            }

            nodesExpanded++;

            for (Action action : problem.getActions(current.state)) {
                State childState = problem.getResult(current.state, action);
                if (childState == null) continue;
                double g = current.pathCost + problem.getStepCost(current.state, action);

                Double prevBest = bestG.get(childState);
                if (prevBest == null || g < prevBest) {
                    Node<State, Action> child = new Node<>(childState, current, action, g);
                    bestG.put(childState, g);
                    open.add(child);
                }
            }
        }
        return Optional.empty();
    }
}

