package search;

import java.util.*;

public class IDS<State, Action> implements SearchAlgorithm<State, Action> {

    int nodesExpanded = 0;
    @Override
    public Optional<Node<State, Action>> search(Problem<State, Action> problem) {
        int depth = 0;
        while (true) {
            Optional<Node<State, Action>> result = depthLimitedSearch(problem, depth);
            if (result.isPresent()) return result;
            depth++;
        }
    }

    private Optional<Node<State, Action>> depthLimitedSearch(Problem<State, Action> problem, int limit) {
        Stack<Node<State, Action>> nodeStack = new Stack<>();
        Set<State> explored = new HashSet<>(); // Local explored set for this iteration
        nodeStack.push(new Node<>(problem.getInitialState(), null, null, 0));
        explored.add(problem.getInitialState());
        while (!nodeStack.isEmpty()) {
            Node<State, Action> node = nodeStack.pop();
            if (problem.isGoal(node.state)) {
                System.out.println("Nodes expanded for IDS: " + nodesExpanded);
                return Optional.of(node);

            }
            nodesExpanded++;
            if (node.depth < limit) {
                for (Action action : problem.getActions(node.state)) {
                    State childState = problem.getResult(node.state, action);
                    if (!explored.contains(childState)) {
                        Node<State, Action> child = new Node<>(childState, node, action,
                                node.pathCost + problem.getStepCost(node.state, action));
                        nodeStack.push(child);
                        explored.add(childState);
                    }
                }
            }
        }
        return Optional.empty();
    }
}
