package search;

import java.util.Optional;

public interface SearchAlgorithm<State, Action> {
    Optional<Node<State, Action>> search(Problem<State, Action> problem);
}
