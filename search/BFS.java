package search;

import java.util.*;

public class BFS<State, Action> implements SearchAlgorithm<State, Action> {
    int nodesExpanded = 0;
    @Override
    public Optional<Node<State, Action>> search(Problem<State, Action> problem) {
        Queue<Node<State, Action>> nodesQueue = new LinkedList<>();
        Set<State> explored = new HashSet<>();
        Set<State> queueHash = new HashSet<>(); // For fast membership check

        Node<State, Action> initialNode = new Node<>(problem.getInitialState(), null, null, 0);
        nodesQueue.add(initialNode);
        queueHash.add(initialNode.state);

        while (!nodesQueue.isEmpty()) {
            Node<State, Action> node = nodesQueue.poll();
            queueHash.remove(node.state);
            if (problem.isGoal(node.state)) {
                System.out.println("Nodes expanded for BFS: " + nodesExpanded);
                return Optional.of(node);
            }

            explored.add(node.state);
            nodesExpanded++;

            for (Action action : problem.getActions(node.state)) {
                State childState = problem.getResult(node.state, action);
                Node<State, Action> child = new Node<>(childState, node, action,
                        node.pathCost + problem.getStepCost(node.state, action));

                if (!explored.contains(childState) && !queueHash.contains(childState)) {
                    nodesQueue.add(child);
                    queueHash.add(childState);
                }
            }
        }
        return Optional.empty();
    }
}