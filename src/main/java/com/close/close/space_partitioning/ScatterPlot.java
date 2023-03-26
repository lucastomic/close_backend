package com.close.close.space_partitioning;

import org.jetbrains.annotations.NotNull;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;

public class ScatterPlot extends JFrame {
    private final XYPlot plot;


    public ScatterPlot(XYDataset dataset, @NotNull Vector2D rangeX, @NotNull Vector2D rangeY) {

        JFreeChart chart = ChartFactory.createScatterPlot(
                "Locations",
                "X-Axis",
                "Y-Axis",
                dataset);


        //Changes background color
        this.plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(255,228,196));

        ValueAxis domainAxis = plot.getDomainAxis();
        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setRange(rangeX.getX(), rangeX.getY());
        domainAxis.setRange(rangeX.getX(), rangeY.getY());

        // Create Panel
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
        requestFocus();
        requestFocusInWindow();
    }


    public XYPlot getPlot() {
        return plot;
    }

}
