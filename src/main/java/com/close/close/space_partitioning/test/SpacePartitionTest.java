package com.close.close.space_partitioning.test;

import com.close.close.space_partitioning.QuadTree;
import com.close.close.space_partitioning.V2;
import com.close.close.space_partitioning.QueryResult;
import com.close.close.space_partitioning.QuadTreeGraphics;

import java.util.Random;
import java.util.Scanner;

/**
 * Simple QuadTree test with fake users and fake locations
 */
public class SpacePartitionTest {
    private static final long MADRID_POPULATION = 100000;
    private static final double SIDE_M = 30000;
    private static final V2 box = new V2(SIDE_M/2, SIDE_M/2);

    public static void main(String[] args) {
        QuadTree<DummyLocation> quadTree = new QuadTree<>(10, 4,box);

        System.out.println("Loading QuadTree...");
        Random random = new Random();
        long treePopulationStart = System.currentTimeMillis();
        for (int i = 0; i < MADRID_POPULATION; i++) {
            double x = random.nextDouble() * (box.getX() + box.getX()) - box.getX();
            double y = random.nextDouble() * (box.getY() + box.getY()) - box.getY();
            quadTree.insert(new DummyLocation(new Dummy(), new V2(x, y)));
        }
        System.out.println("QuadTree Loaded :: Duration: " + (System.currentTimeMillis() - treePopulationStart)/1000f + "s");

        //quadTree.show();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println("=====================================");
            System.out.print("Query Position: " );
            V2 pos = new V2(scanner.nextDouble(), scanner.nextDouble());
            System.out.print("Query Range: ");
            double range = scanner.nextDouble();

            long treeQueryStart = System.currentTimeMillis();
            QueryResult<DummyLocation> qr = quadTree.query(pos, range);

            System.out.println("-------------------------------------");
            System.out.println("Query Completed :: Duration: " + (System.currentTimeMillis() - treeQueryStart)/1000f + "s");

            QuadTreeGraphics<DummyLocation> graphics = new QuadTreeGraphics<>();
            graphics.showQueryRangeResult(
                    pos, range,
                    new V2(-box.getX(), box.getX()),
                    new V2(- box.getY(), box.getY()),
                    quadTree,
                    qr
            );

            System.out.println("Comparisons: " + qr.COMPARISONS);
            System.out.println("Results: " + qr.RESULTS.size());
            System.out.println("Potential Results: " + qr.POTENTIAL_RESULTS.size());
        }
    }
}