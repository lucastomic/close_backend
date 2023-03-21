package com.close.close.space_partitioning;

import org.jfree.chart.annotations.XYShapeAnnotation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class QuadTreeGraphics<T extends Location> {

    public void showQueryRangeResult (V2 origin, double range, V2 rangeX, V2 rangeY, QuadTree<T> quadTree, QueryResult<T> result) {
        XYSeries me = new XYSeries("Me");
        XYSeries all = new XYSeries("All");
        XYSeries results = new XYSeries("Results");
        XYSeries potentialResults = new XYSeries("Potential Results");

        me.add(origin.getX(), origin.getY());
        for (T location : result.RESULTS) results.add(location.getPosition().getX(), location.getPosition().getY());
        for (T location : result.POTENTIAL_RESULTS) potentialResults.add(location.getPosition().getX(), location.getPosition().getY());
        for (T location : quadTree.getLocations())
            if (!result.RESULTS.contains(location) && !result.POTENTIAL_RESULTS.contains(location))
                all.add(location.getPosition().getX(), location.getPosition().getY());

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(me);
        dataset.addSeries(all);
        dataset.addSeries(results);
        dataset.addSeries(potentialResults);

        SwingUtilities.invokeLater( () -> {
            ScatterPlot sp = new ScatterPlot(dataset, rangeX, rangeY);
            sp.getPlot().addAnnotation(
                    new XYShapeAnnotation(
                            new Ellipse2D.Double(
                                    origin.getX() - range,
                                    origin.getY() - range,
                                    range*2, range*2
                            )
                    )
            );
            drawBranch(sp.getPlot(), quadTree.ROOT);
            sp.setSize(1240, 1240);
            sp.setLocationRelativeTo(null);
            sp.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            sp.setVisible(true);
            sp.requestFocusInWindow();
        });
    }

    private void drawBranch(XYPlot plot, QuadTreeBranch<T> branch) {

        plot.addAnnotation(
                new XYShapeAnnotation(
                        new Rectangle2D.Double(
                                branch.getPosition().getX() - branch.getBox().getX(),
                                branch.getPosition().getY() - branch.getBox().getY(),
                                branch.getBox().getX()*2, branch.getBox().getY()*2
                        )
                )
        );

        for (QuadTreeBranch<T> child : branch.getChild())
            drawBranch(plot, child);
    }
}
