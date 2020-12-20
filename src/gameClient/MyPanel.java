package gameClient;

import api.*;
import gameClient.util.*;
import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents the panel on which the pokemon game
 * is being displayed on.
 */
public class MyPanel extends JPanel {
    private MyArena ar;
    private Range2Range w2f;
    private game_service game;

    /**
     * Default constructor for the class. Initializes the class which this class extends.
     */
    public MyPanel() {
        super();
    }

    /**
     * This method updates the adapter (w2f) of this class
     * to match to the current size of the game screen.
     */
    public void updateFrame() {
        Range rx = new Range(100, this.getWidth() - 20);
        Range ry = new Range(this.getHeight() - 100, 100);
        Range2D frame = new Range2D(rx, ry);
        directed_weighted_graph g = ar.getGraph();
        w2f = MyArena.w2f(g, frame);
    }

    /**
     * This method allows changing the parameters MyArena
     * and game_service of this class.
     *
     * @param ar   - MyArena parameter
     * @param game - game_service parameter
     */
    public void update(MyArena ar, game_service game) {
        this.ar = ar;
        this.game = game;
        updateFrame();
    }

    /**
     * This method calls for the update adapter memthod and
     * the rest of the methods of this class which are
     * responsible to drawing the game screen.
     *
     * @param g
     */
    public void paint(Graphics g) {
        int w = this.getWidth();
        int h = this.getHeight();
        g.clearRect(0, 0, w, h);
        updateFrame();
        drawPokemons(g);
        drawGraph(g);
        drawAgents(g);
        drawInfo(g);
    }

    /**
     * This method draws the agents id, speed and current values on the game screen.
     *
     * @param g
     */
    private void drawInfo(Graphics g) {
        int count = 50;
        g.setColor(Color.BLUE);
        String time = "" + game.timeToEnd();
        if (time.length() > 3) {
            if (time.length() == 5)
                time = time.substring(0, 2) + "." + time.substring(2);
            if (time.length() == 4)
                time = time.substring(0, 1) + "." + time.substring(2);
        } else {
            time = 0 + "." + time;
        }
        time = "Remaining time: " + time;
        g.drawString(time, 100, 20);
        g.setColor(Color.BLACK);
        for (MyAgent a : ar.getAgents()) {
            String s = "Agent: " + a.getID() + " Value: " + a.getValue() + " Speed: " + a.getSpeed();
            g.drawString(s, 100, count);
            count += 20;
        }

    }

    /**
     * This method draws the nodes of the graph which represents
     * the arena of the game on the game screen.
     *
     * @param g
     */
    private void drawGraph(Graphics g) {
        directed_weighted_graph gg = ar.getGraph();
        Iterator<node_data> iter = gg.getV().iterator();
        while (iter.hasNext()) {
            node_data n = iter.next();
            g.setColor(Color.blue);
            drawNode(n, 5, g);
            Iterator<edge_data> itr = gg.getE(n.getKey()).iterator();
            while (itr.hasNext()) {
                edge_data e = itr.next();
                g.setColor(Color.gray);
                drawEdge(e, g);
            }
        }
    }

    /**
     * This method draws the pokemons of the game according to their locations on the game screen.
     *
     * @param g
     */
    private void drawPokemons(Graphics g) {
        MyPokemon[] ls = ar.getPokemons().toArray(new MyPokemon[0]);
        for (int i = 0; i < ls.length; i++) {
            if (ls[i] != null) {
                Point3D c = ls[i].getLocation();
                int r = 10;
                g.setColor(Color.GREEN);
                if (ls[i].getType() < 0) g.setColor(Color.ORANGE);
                if (c != null) {
                    geo_location fp = this.w2f.world2frame(c);
                    g.fillOval((int) fp.x() - r, (int) fp.y() - r, 2 * r, 2 * r);
                }
            }
        }
    }

    /**
     * This method draws the agents according to their locations on the game screen.
     *
     * @param g
     */
    private void drawAgents(Graphics g) {
        List<MyAgent> rs = ar.getAgents();
        g.setColor(Color.red);
        int i = 0;
        while (rs != null && i < rs.size()) {
            geo_location c = rs.get(i).getLocation();
            int r = 8;
            i++;
            if (c != null) {

                geo_location fp = this.w2f.world2frame(c);
                g.fillOval((int) fp.x() - r, (int) fp.y() - r, 2 * r, 2 * r);
            }
        }
    }

    /**
     * This method draws a single node on the game screen.
     *
     * @param g
     */
    private void drawNode(node_data n, int r, Graphics g) {
        geo_location pos = n.getLocation();
        geo_location fp = this.w2f.world2frame(pos);
        g.fillOval((int) fp.x() - r, (int) fp.y() - r, 2 * r, 2 * r);
        g.drawString("" + n.getKey(), (int) fp.x(), (int) fp.y() - 4 * r);
    }

    /**
     * This method draws a single edge on the game screen.
     *
     * @param g
     */
    private void drawEdge(edge_data e, Graphics g) {
        directed_weighted_graph gg = ar.getGraph();
        geo_location s = gg.getNode(e.getSrc()).getLocation();
        geo_location d = gg.getNode(e.getDest()).getLocation();
        geo_location s0 = this.w2f.world2frame(s);
        geo_location d0 = this.w2f.world2frame(d);
        g.drawLine((int) s0.x(), (int) s0.y(), (int) d0.x(), (int) d0.y());
    }

}

