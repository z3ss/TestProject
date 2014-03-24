/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 *
 * @author KristianMohr
 */
public class Quadtree {
    public Quadtree[] nodes = new Quadtree[4];
    private final int capacity = 5000;
    private final int maxlevel = 10;
    public final Rectangle2D.Double bounds;
    private ArrayList<Road> objects = new ArrayList<>();
    private final int level;
    
    public Quadtree(int level, Rectangle2D.Double bounds){
        this.bounds = bounds;
        this.level = level;
    }
    
    public boolean insert(Road r){
        if(!bounds.contains(r.midX, r.midY)) return false;
        
        if(nodes[0] == null && (objects.size()<capacity || level >= maxlevel)){
            objects.add(r);
            return true;
        }
        
        if(nodes[0] == null && divide()){
            int i = objects.size()-1;
            while (i > 0){
                Road ro = objects.get(i);
                if(nodes[0].insert(ro));
                else if(nodes[1].insert(ro));
                else if(nodes[2].insert(ro));
                else if(nodes[3].insert(ro));
                objects.remove(i);
                i--;
            }
        }
        
        if(nodes[0].insert(r)) return true;
        else if(nodes[1].insert(r)) return true;
        else if(nodes[2].insert(r)) return true;
        else if(nodes[3].insert(r)) return true;
        
        
        return false;
    }
    
    public void getRoads(Rectangle2D area, ArrayList<Road> roads){
        if(nodes[0] == null){
            roads.addAll(objects);
        }else{
            roads.addAll(objects);
            if(nodes[0].testArea(area)) nodes[0].getRoads(area, roads);
            if(nodes[1].testArea(area)) nodes[1].getRoads(area, roads);
            if(nodes[2].testArea(area)) nodes[2].getRoads(area, roads);
            if(nodes[3].testArea(area)) nodes[3].getRoads(area, roads);
        }
    }
    
    private boolean divide(){
        double subWidth = bounds.getWidth() / 2;
        double subHeight = bounds.getHeight() / 2;
        double x = bounds.getX();
        double y = bounds.getY();

        nodes[0] = new Quadtree(level+1, new Rectangle2D.Double(x, y, subWidth, subHeight));
        nodes[1] = new Quadtree(level+1, new Rectangle2D.Double(x + subWidth, y, subWidth, subHeight));
        nodes[2] = new Quadtree(level+1, new Rectangle2D.Double(x, y + subHeight, subWidth, subHeight));
        nodes[3] = new Quadtree(level+1, new Rectangle2D.Double(x + subWidth, y + subHeight, subWidth, subHeight));
        return true;
    }
    
    private boolean testArea(Rectangle2D area){
        return bounds.contains(area) || area.contains(bounds) || bounds.intersects(area);
    }
}
