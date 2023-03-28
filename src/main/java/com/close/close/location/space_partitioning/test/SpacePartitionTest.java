package com.close.close.location.space_partitioning.test;

import com.close.close.location.space_partitioning.*;

import java.util.Random;
import java.util.Scanner;

/**
 * Simple QuadTree test with fake users and fake locations
 */
public class SpacePartitionTest {
    private static final long MADRID_POPULATION = 1000;
    private static final double SIDE_M = 6000;
    private static final Vector2D box = new Vector2D(SIDE_M/2, SIDE_M/2);

    public static void main(String[] args) {
        long maxLevel = QuadTree.findMaxLevel(SIDE_M, 10);
        QuadTree<DummyLocation> quadTree = new QuadTree<>(maxLevel, 4, new Rectangle(Vector2D.ZERO, box));

        System.out.println("Loading QuadTree...");
        Random random = new Random();
        long treePopulationStart = System.currentTimeMillis();
        for (int i = 0; i < MADRID_POPULATION; i++) {
            double x = random.nextDouble() * Math.cos((float) random.nextDouble()) * (box.getX() + box.getX()) - box.getX();
            double y = random.nextDouble() * Math.sin((float) random.nextDouble()) * Math.cos((float) random.nextDouble()) * (box.getY() + box.getY()) - box.getY();
            quadTree.insert(new DummyLocation(new Dummy(), new Vector2D(x, y)));
        }
        System.out.println("QuadTree Loaded :: Duration: " + (System.currentTimeMillis() - treePopulationStart)/1000f + "s");

        //quadTree.show();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println("=====================================");
            System.out.print("Query Position: " );
            Vector2D pos = new Vector2D(scanner.nextDouble(), scanner.nextDouble());
            System.out.print("Query Range: ");
            double range = scanner.nextDouble();

            long treeQueryStart = System.currentTimeMillis();
            QueryResult<DummyLocation> qr = quadTree.query(pos, range);

            System.out.println("-------------------------------------");
            System.out.println("Query Completed :: Duration: " + (System.currentTimeMillis() - treeQueryStart)/1000f + "s");

            QuadTreeGraphics<DummyLocation> graphics = new QuadTreeGraphics<>();
            graphics.showQueryRangeResult(
                    pos, range,
                    new Vector2D(-box.getX(), box.getX()),
                    new Vector2D(- box.getY(), box.getY()),
                    quadTree,
                    qr
            );

            System.out.println("Comparisons: " + qr.COMPARISONS);
            System.out.println("Results: " + qr.RESULTS.size());
            System.out.println("Potential Results: " + qr.POTENTIAL_RESULTS.size());
        }
    }
}