/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrl;

import java.awt.BorderLayout;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import krakloader.EdgeData;
import krakloader.KrakLoader;
import krakloader.NodeData;
import model.Quadtree;
import model.Road;
import view.DrawMap;

/**
 *
 * @author KristianMohr
 */
public class StartMap {

    private JFrame frame;
    private Quadtree qt;
    private double xmax, ymax;

    public StartMap() throws IOException {
        setupData();
        setupFrame();
    }

    private void setupData() throws IOException {
        final ArrayList<NodeData> nodes = new ArrayList<>();
        final ArrayList<EdgeData> edges = new ArrayList<>();
        final ArrayList<Road> roads = new ArrayList<>();
        String dir = "./data/";
        KrakLoader kl = new KrakLoader() {
            @Override
            public void processNode(NodeData nd) {
                nodes.add(nd);
            }

            @Override
            public void processEdge(EdgeData ed) {
                edges.add(ed);
            }
        };
        kl.load(dir + "kdv_node_unload.txt", dir + "kdv_unload.txt");
        xmax = kl.xmax - kl.xmin;
        ymax = (-kl.ymin)+kl.ymax;
        for (NodeData n : nodes) {
            n.recalc(kl.ymax, kl.xmin);
        }
        for (EdgeData e : edges) {
            Road rd = new Road(e, nodes.get(e.FNODE - 1), nodes.get(e.TNODE - 1));
            roads.add(rd);
        }
        
        qt = new Quadtree(0, new Rectangle2D.Double(0, 0, xmax, ymax));
        int i = 0;
        for (Road r : roads) {
            qt.insert(r);
        }
    }

    private void setupFrame() {
        frame = new JFrame("Map Of Denmark");
        frame.setSize(700, 630);
        ArrayList<Road> roads = new ArrayList<>();
        Rectangle2D area = new Rectangle2D.Double(0, 0, xmax, ymax);
        qt.getRoads(area, roads);
        DrawMap dm = new DrawMap(roads, xmax, ymax);
        dm.setQT(qt);
        ML ml = new ML(dm, qt);
        dm.addMouseListener(ml);
        dm.addMouseMotionListener(ml);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(dm, BorderLayout.CENTER);
        JPanel south = new JPanel();
        south.add(new JLabel("Not implemented"));
        frame.getContentPane().add(south, BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws IOException {
        StartMap sm = new StartMap();
    }
}
