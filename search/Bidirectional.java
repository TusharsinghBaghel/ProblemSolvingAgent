package search;
import java.util.*;

public class Bidirectional<State, Action> implements SearchAlgorithm<State, Action> {
    int nodesExpanded = 0;

    @Override
    public Optional<Node<State, Action>> search(Problem<State, Action> problem) {
        State start = problem.getInitialState();
        State goal = problem.getGoalState();

        if (start.equals(goal)) {
            System.out.println("Nodes expanded for Bidirectional: " + nodesExpanded);
            return Optional.of(new Node<>(start, null, null, 0));
        }

        // Frontiers
        Queue<Node<State, Action>> forwardQueue = new LinkedList<>();
        Queue<Node<State, Action>> backwardQueue = new LinkedList<>();

        Map<State, Node<State, Action>> forwardParents = new HashMap<>();
        Map<State, Node<State, Action>> backwardParents = new HashMap<>();

        Node<State, Action> startNode = new Node<>(start, null, null, 0);
        Node<State, Action> goalNode = new Node<>(goal, null, null, 0);

        forwardQueue.add(startNode);
        forwardParents.put(start, startNode);

        backwardQueue.add(goalNode);
        backwardParents.put(goal, goalNode);

        while (!forwardQueue.isEmpty() && !backwardQueue.isEmpty()) {
            Node<State, Action> meet = expandLayer(problem, forwardQueue, forwardParents, backwardParents, true);
            if (meet != null) {
                System.out.println("Nodes expanded for Bidirectional: " + nodesExpanded);
                return Optional.of(meet);
            }

            meet = expandLayer(problem, backwardQueue, backwardParents, forwardParents, false);
            if (meet != null) {
                System.out.println("Nodes expanded for Bidirectional: " + nodesExpanded);
                return Optional.of(meet);
            }
        }

        return Optional.empty();
    }

    private Node<State, Action> expandLayer(
            Problem<State, Action> problem,
            Queue<Node<State, Action>> queue,
            Map<State, Node<State, Action>> thisParents,
            Map<State, Node<State, Action>> otherParents,
            boolean forward) {
        if (queue.isEmpty()) return null;

        Node<State, Action> node = queue.poll();
        State current = node.state;
        nodesExpanded++;

        for (Action action : problem.getActions(current)) {
            State next = problem.getResult(current, action);
            if (next == null) continue;

            if (!thisParents.containsKey(next)) {
                Node<State, Action> child = new Node<>(next, node, action,
                        node.pathCost + problem.getStepCost(current, action));
                thisParents.put(next, child);
                queue.add(child);

                if (otherParents.containsKey(next)) {
                    Node<State, Action> fromForward = child;
                    Node<State, Action> fromBackward = otherParents.get(next);
                    if (!forward) {
                        fromForward = otherParents.get(next);
                        fromBackward = child;
                    }
                    return constructNodePath(problem, fromForward, fromBackward);
                }
            }
        }
        return null;
    }

    // single Node chain from start -> meet -> goal
    private Node<State, Action> constructNodePath(Problem<State, Action> problem,
                                                  Node<State, Action> meetForward,
                                                  Node<State, Action> meetBackward) {
        Node<State, Action> tail = meetForward;
        Node<State, Action> cursor = meetBackward;
        while (cursor.parent != null) {
            Node<State, Action> nextTowardsGoal = cursor.parent;
            // Find the action that takes from current tail.state to nextTowardsGoal.state
            Action forwardAction = deriveAction(problem, tail.state, nextTowardsGoal.state);
            if (forwardAction == null) {
                break;
            }
            tail = new Node<>(nextTowardsGoal.state, tail, forwardAction,
                    tail.pathCost + problem.getStepCost(tail.state, forwardAction));
            cursor = nextTowardsGoal;
        }
        return tail;
    }

    // Try each action from "fromState" and pick the one that leads to "toState"
    private Action deriveAction(Problem<State, Action> problem, State fromState, State toState) {
        for (Action a : problem.getActions(fromState)) {
            State r = problem.getResult(fromState, a);
            if (toState.equals(r)) return a;
        }
        return null;
    }
}
