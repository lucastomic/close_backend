package com.close.close.location.space_partitioning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryResult<T extends IPosition> {
    public final List<T> RESULTS;
    public final List<T> POTENTIAL_RESULTS;
    public long COMPARISONS;

    public QueryResult() {
        this.RESULTS = new ArrayList<>();
        this.POTENTIAL_RESULTS = new ArrayList<>();
        this.COMPARISONS = 0L;
    }
    public QueryResult(List<T> results, List<T> potentialResults, long comparisons) {
        this.RESULTS = results;
        this.POTENTIAL_RESULTS = potentialResults;
        this.COMPARISONS = comparisons;
    }

    public void add(QueryResult<T> other){
        this.RESULTS.addAll(other.RESULTS);
        this.POTENTIAL_RESULTS.addAll(other.POTENTIAL_RESULTS);
        this.COMPARISONS += other.COMPARISONS;
    }

    @Override
    public String toString() {
        StringBuilder results = new StringBuilder();
        results.append("Results: \n");
        for (T position : RESULTS) results.append(position).append("\n");
        results.append("PotentialResults: \n");
        for (T position : POTENTIAL_RESULTS) results.append(position).append("\n");
        results.append("Comparisons: ").append(COMPARISONS);
        return results.toString();
    }
}
