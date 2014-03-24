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
import view.DrawMap;
//import view.Canvas;

/**
 *
 * @author z3ss
 */
public class ML implements MouseListener, MouseMotionListener{
    
    private Point mouseStart;
    private boolean mousePressed;
    private Point mouseEnd;
    private Point currentMouse;
    private boolean mouseDragged;
    private final DrawMap dm;
    
    public ML(DrawMap dm){
        this.dm = dm;
    }
    
      @Override
    public void mouseClicked(MouseEvent me)
    {
        //s
    }


    @Override
    public void mousePressed(MouseEvent me)
    {
        mouseStart = me.getPoint();
        mousePressed = true;
    }


    @Override
    public void mouseReleased(MouseEvent me)
    {
        mouseEnd = me.getPoint();
        mousePressed = false;
        double scale = dm.getScale();
        dm.setView(new Rectangle2D.Double(mouseStart.x*scale, mouseStart.y*scale,
            (mouseEnd.x-mouseStart.x)*scale, (mouseEnd.y - mouseStart.y)*scale));
    }

    @Override
    public void mouseEntered(MouseEvent me)
    {
        //s
    }

    @Override
    public void mouseExited(MouseEvent me)
    {
        //s
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        currentMouse = e.getPoint();
        mouseDragged = true;
        e.consume();//Stops the event when not in use, makes program run faster
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        currentMouse = e.getPoint();
        mouseDragged = false;
        e.consume();//Stops the event when not in use, makes program run faster
    }
}
