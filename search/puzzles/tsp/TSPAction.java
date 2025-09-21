package search.puzzles.tsp;

import java.util.Objects;

public class TSPAction {
    public final int nextCity;

    public TSPAction(int nextCity) {
        this.nextCity = nextCity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TSPAction)) return false;
        TSPAction that = (TSPAction) o;
        return nextCity == that.nextCity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nextCity);
    }

    @Override
    public String toString() {
        return "->" + nextCity;
    }
}

