package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import com.google.gson.Gson;
import gameClient.util.Point3D;
import org.json.JSONObject;
import java.util.List;
/**
 * This class represents an agent in the game.
 */
public class MyAgent {
    private int _id;
    private geo_location _pos;
    private double _speed;
    private edge_data _curr_edge;
    private node_data _curr_node;
    private directed_weighted_graph _gg;
    private MyPokemon _curr_fruit;
    private long _sg_dt;
    private double _value;
    private int src;
    private int dest;
    private List<node_data> path;
    private long timeForNext;
    /**
     * This method returns a list of nodes of the shortest path between this
     * agent the pokemon he's chasing.
     * @return path
     */
    public List<node_data> getPath() {
        return path;
    }
    /**
     * This method returns the time in milliseconds the until this agent
     * reaches it's next destination node.
     * @return timeForNext
     */
    public long getTimeForNext() {
        return timeForNext;
    }
    /**
     * This method allows changing this instance timeForNext object to the
     * received long object.
     * @param timeForNext
     */
    public void setTimeForNext(long timeForNext) {
        this.timeForNext = timeForNext;
    }
    /**
     * This method allows changing this instance path object to the
     * received path.
     * @param path
     */
    public void setPath(List<node_data> path) {
        this.path = path;
    }
    /**
     * This method allows changing this instance _pos object to
     * the received _pos object.
     * @param _pos
     */
    public void set_pos(geo_location _pos) {
        this._pos = _pos;
    }
    /**
     * This method allows changing this instance _curr_edge object to
     * the received edge object.
     * @param _curr_edge
     */
    public void set_curr_edge(edge_data _curr_edge) {
        this._curr_edge = _curr_edge;
    }
    /**
     * This method allows changing this instance _gg object to
     * the received graph object.
     * @param _gg
     */
    public void set_gg(directed_weighted_graph _gg) {
        this._gg = _gg;
    }
    /**
     * This method allows changing this instance _value object
     * to the received value object.
     * @param _value
     */
    public void set_value(double _value) {
        this._value = _value;
    }
    /**
     * This method allows changing this instance src object to
     * the received src object.
     * @param src
     */
    public void setSrc(int src) {
        this.src = src;
    }
    /**
     * This method allows changing this instance dest object to
     * the received dest object.
     * @param dest
     */
    public void setDest(int dest) {
        this.dest = dest;
    }
    /**
     * This method returns the agent's current source node key.
     * @return  src
     */
    public int getSrc() {
        return src;
    }
    /**
     * This method returns the agent's current destination node key.
     * @return  dest
     */
    public int getDest() {
        return dest;
    }
    /**
     * Constructor for this class, initializes this graph and start node. Initializes the
     * rest of the parameters to default values.
     * @param   g
     * @param start_node
     */
    public MyAgent(directed_weighted_graph g, int start_node) {
        src=-1;
        dest=-1;
        _gg = g;
        setMoney(0);
        this._curr_node = _gg.getNode(start_node);
        _pos = _curr_node.getLocation();
        _id = -1;
        setSpeed(0);
    }
    /**
     * This method allows updating all of this instance parameters
     * according to another agent values represented as a json String.
     * @param   json
     */
    public void update(String json) {
        JSONObject line;
        try {
            line = new JSONObject(json);
            JSONObject ttt = line.getJSONObject("Agent");
            int id = ttt.getInt("id");
            if(id==this.getID() || this.getID() == -1) {
                if(this.getID() == -1) {_id = id;}
                double speed = ttt.getDouble("speed");
                String p = ttt.getString("pos");
                Point3D pp = new Point3D(p);
                int src = ttt.getInt("src");
                int dest = ttt.getInt("dest");
                double value = ttt.getDouble("value");
                this._pos = pp;
                this.setCurrNode(src);
                this.setSpeed(speed);
                this.setNextNode(dest);
                this.setMoney(value);
                this.setSrc(src);
                this.setDest(dest);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * This method allows changing this value parameter to the
     * received v parameter.
     * @param   v
     */
    private void setMoney(double v) {_value = v;}
    /**
     * This method returns true iff there is an edge between current this src node to the received dest in this graph.
     * @return  true iff there is an edge between current this src node to the received dest in this graph
     */
    public boolean setNextNode(int dest) {
        boolean ans = false;
        int src = this._curr_node.getKey();
        this._curr_edge = _gg.getEdge(src, dest);
        if(_curr_edge!=null) {
            ans=true;
        }
        else {_curr_edge = null;}
        return ans;
    }
    /**
     * This method allows changing this currNode to the received src node.
     * @param src
     */
    public void setCurrNode(int src) {
        this._curr_node = _gg.getNode(src);
    }
    /**
     * This method returns a string representing this agent instance in a json format.
     * @return  s
     */
    public String toString() {
        Gson gson=new Gson();
        return gson.toJson(this);
    }
    /**
     * This method returns this agent's ID.
     * @return  _id
     */
    public int getID() {
        return this._id;
    }
    /**
     * This method returns this agent's location.
     * @return  _pos
     */
    public geo_location getLocation() {
        return _pos;
    }
    /**
     * This method returns this agent's current value.
     * @return  _value
     */
    public double getValue() {
        return this._value;
    }
    /**
     * This method returns this agent's current speed.
     * @return  _speed
     */
    public double getSpeed() {
        return this._speed;
    }
    /**
     * This method allows changing this speed object to the received v value.
     * @param   v
     */
    public void setSpeed(double v) {
        this._speed = v;
    }
    /**
     * This method returns the current pokemon this agent is chasing.
     * @return _curr_fruit
     */
    public MyPokemon get_curr_fruit() {
        return _curr_fruit;
    }
    /**
     * This method allows changing the pokemon this agent is currently chasing
     * to the received curr_fruit.
     * @param  curr_fruit
     */
    public void set_curr_fruit(MyPokemon curr_fruit) {
        this._curr_fruit = curr_fruit;
    }
    /**
     * This method returns the edge on which this agent is currently chasing located on.
     * @return _curr_edge
     */
    public edge_data get_curr_edge() {
        return this._curr_edge;
    }
    /**
     * This method returns the time in milliseconds until this agent reaches
     * the pokemon he's chasing.
     * @return _sg_dt
     */
    public long get_sg_dt() {
        return _sg_dt;
    }
    /**
     * This method allows changing this agent's _sg_dt parameter
     * to the received _sg_dt value.
     * @param  _sg_dt
     */
    public void set_sg_dt(long _sg_dt) {
        this._sg_dt = _sg_dt;
    }
}
