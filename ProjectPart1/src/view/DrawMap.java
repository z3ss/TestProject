/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import krakloader.NodeData;
import model.Quadtree;
import model.Road;

/**
 *
 * @author KristianMohr
 */
public class DrawMap extends JPanel {

    private ArrayList<Road> roads;
    private double scale = 1;
    private double xmax, ymax;
    private Quadtree qt;
    private Rectangle2D view;

    public DrawMap(ArrayList<Road> roads, double xmax, double ymax) {
        this.roads = roads;
        this.xmax = xmax;
        this.ymax = ymax;
    }
    
    public void setQT(Quadtree qt){
        this.qt = qt;
    }

    private void drawMap(Graphics2D g2) {
        scale = view.getWidth() / (double) this.getWidth();
        for (Road r : roads) {
            double x1, x2, y1, y2;
            NodeData n1 = r.getFn();
            NodeData n2 = r.getTn();
            x1 = (n1.getX_COORD()-view.getMinX()) / scale;
            y1 = (n1.getY_COORD()-view.getMinY()) / scale;
            x2 = (n2.getX_COORD()-view.getMinX()) / scale;
            y2 = (n2.getY_COORD()-view.getMinY()) / scale;

            Line2D road = new Line2D.Double(x1, y1, x2, y2);

            //Road colering:
            switch (r.getEd().TYP) {
                case 1:
                    g2.setColor(Color.red); //Highway
                    break;
                case 3:
                    g2.setColor(Color.blue); //Main roads
                    break;
                case 8:
                    g2.setColor(Color.green); //Path
                    break;
                default:
                    g2.setColor(Color.gray); //Other
                    break;
            }
            g2.draw(road);
        }
    }

    public void setView(Rectangle2D view) {
        this.view = view;
        roads.clear();
        qt.getRoads(view, roads);
        System.out.println(view.getWidth()+" "+view.getHeight());
        System.out.println(""+roads.size());
        repaint();
    }

    public double getScale() {
        return scale;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        drawMap(g2);
    }
}
