package search;

import java.util.*;

public class DFS<State, Action> implements SearchAlgorithm<State, Action> {
    int nodesExpanded = 0;
    @Override
    public Optional<Node<State, Action>> search(Problem<State, Action> problem) {
        Stack<Node<State, Action>> nodeStack = new Stack<>();
        Set<State> explored = new HashSet<>();

        nodeStack.push(new Node<>(problem.getInitialState(), null, null, 0));

        while (!nodeStack.isEmpty()) {
            Node<State, Action> node = nodeStack.pop();
            if (problem.isGoal(node.state)) return Optional.of(node);

            explored.add(node.state);
            nodesExpanded++;

            for (Action action : problem.getActions(node.state)) {
                State childState = problem.getResult(node.state, action);
                Node<State, Action> child = new Node<>(childState, node, action,
                        node.pathCost + problem.getStepCost(node.state, action));

                if (!explored.contains(childState) &&
                        nodeStack.stream().noneMatch(n -> n.state.equals(childState))) {
                    nodeStack.push(child);
                }
            }
        }
        return Optional.empty();
    }
}
