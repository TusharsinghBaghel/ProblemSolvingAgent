package search;

import java.util.*;

public class Node<State, Action> {
    public State state;
    public Node<State, Action> parent;
    public Action action;
    public double pathCost;
    public int depth;

    public Node(State state, Node<State, Action> parent, Action action, double pathCost) {
        this.state = state;
        this.parent = parent;
        this.action = action;
        this.pathCost = pathCost;
        this.depth = (parent == null) ? 0 : parent.depth + 1;
    }

    // Reconstruct path from root to this node
    public List<Action> getSolution() {
        List<Action> actions = new ArrayList<>();
        Node<State, Action> current = this;
        while (current.parent != null) {
            actions.add(current.action);
            current = current.parent;
        }
        Collections.reverse(actions);
        return actions;
    }
}
