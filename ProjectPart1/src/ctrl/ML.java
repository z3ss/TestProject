/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrl;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import model.Quadtree;
import model.Road;
import view.DrawMap;
//import view.Canvas;

/**
 *
 * @author z3ss
 */
public class ML implements MouseListener, MouseMotionListener {

    private Point mouseStart;
    private boolean mousePressed;
    private Point mouseEnd;
    private Point currentMouse;
    private boolean mouseDragged;
    private final DrawMap dm;
    private final Quadtree qt;

    public ML(DrawMap dm, Quadtree qt) {
        this.dm = dm;
        this.qt = qt;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        //s
    }

    @Override
    public void mousePressed(MouseEvent me) {
        mouseStart = me.getPoint();
        mousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        if (me.getButton() == MouseEvent.BUTTON1) {
            mouseEnd = me.getPoint();
            mousePressed = false;
            calcView();
        } else if (me.getButton() == MouseEvent.BUTTON3) {
            dm.zoomView(new Rectangle2D.Double(0, 0, dm.xmax, dm.ymax));
        }
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        //s
    }

    @Override
    public void mouseExited(MouseEvent me) {
        //s
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currentMouse = e.getPoint();
        mouseDragged = true;
        e.consume();//Stops the event when not in use, makes program run faster
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        currentMouse = e.getPoint();
        mouseDragged = false;
        getRoad();
        e.consume();//Stops the event when not in use, makes program run faster
    }

    private void calcView() {
        double scale = dm.getScale();
        double x = mouseStart.getX();
        double y = mouseStart.getY();
        double width = (mouseEnd.getX() - mouseStart.getX()) * scale;
        double height = (mouseEnd.getY() - mouseStart.getY()) * scale;
        double x1 = (x * scale) + dm.getOldX();
        double y1 = (y * scale) + dm.getOldY();
        dm.zoomView(new Rectangle2D.Double(x1, y1, width, height));
    }

    private void getRoad() {
        double scale = dm.getScale();
        double x1 = (currentMouse.getX() + dm.getOldX()) * scale;
        double y1 = (currentMouse.getY() + dm.getOldY()) * scale;
        ArrayList<Road> roads = new ArrayList<>();
        qt.getRoads(new Rectangle2D.Double(x1, y1, y1, y1), roads);
        double distance;
        Road road = roads.get(0);
        distance = Math.sqrt(Math.pow((road.midX - x1), 2) + Math.pow(road.midY - y1, 2));
        for (Road r : roads) {
            if (r.getEd().VEJNAVN != null) {
                double temp = Math.sqrt(Math.pow((r.midX - x1), 2) + Math.pow(r.midY - y1, 2));
                if (temp < distance) {
                    distance = temp;
                    road = r;

                }
                if (distance < 1000) {
                    System.out.println("V: " + r.getEd().VEJNAVN);
                    return;
                }
            }
        }
        System.out.println("V: " + road.getEd().VEJNAVN);
    }
}
