package com.close.close.space_partitioning;

import java.util.ArrayList;

public class QueryResult<T extends Location> {
    public final ArrayList<T> RESULTS;
    public final ArrayList<T> POTENTIAL_RESULTS;
    public final long COMPARISONS;

    public QueryResult(ArrayList<T> results, ArrayList<T> potentialResults, long comparisons) {
        this.RESULTS = results;
        this.POTENTIAL_RESULTS = potentialResults;
        this.COMPARISONS = comparisons;
    }

    @Override
    public String toString() {
        StringBuilder results = new StringBuilder();
        results.append("Results: \n");
        for (Location location : RESULTS)
            results.append(location).append("\n");
        results.append("PotentialResults: \n");
        for (Location location : POTENTIAL_RESULTS)
            results.append(location).append("\n");
        results.append("Comparisons: ").append(COMPARISONS);
        return results.toString();
    }
}
