package search.puzzles.tsp;

import java.util.Objects;

public class TSPState {
    public final int currentCity;
    public final int visitedMask; // bit i set => city i visited

    public TSPState(int currentCity, int visitedMask) {
        this.currentCity = currentCity;
        this.visitedMask = visitedMask;
    }

    public boolean isVisited(int city) {
        return (visitedMask & (1 << city)) != 0;
    }

    public TSPState visit(int city) {
        int newMask = visitedMask | (1 << city);
        return new TSPState(city, newMask);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TSPState)) return false;
        TSPState that = (TSPState) o;
        return currentCity == that.currentCity && visitedMask == that.visitedMask;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentCity, visitedMask);
    }

    @Override
    public String toString() {
        return "City=" + currentCity + ", visitedMask=" + Integer.toBinaryString(visitedMask);
    }
}

