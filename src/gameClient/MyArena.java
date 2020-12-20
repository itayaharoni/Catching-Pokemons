package gameClient;

import api.*;
import com.google.gson.Gson;
import gameClient.util.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a multi Agents Arena which move on a graph.
 */
public class MyArena {
    public static final double EPS1 = 0.001, EPS2=EPS1*EPS1;
    private  directed_weighted_graph _gg;
    private List<MyAgent> _agents;
    private List<MyPokemon> _pokemons;
    private List<String> _info;
    /**
     * Default Constructor for this class, initializes all parameters.
     */
    public MyArena() {
        _info = new ArrayList<String>();
        _gg=new DWGraph_DS();
        _agents=new ArrayList<MyAgent>();
        _pokemons=new ArrayList<MyPokemon>();
    }
    /**
     * This method allows changing this instance pokemons list to the received list f.
     * @param f
     */
    public void setPokemons(List<MyPokemon> f) {
        this._pokemons = f;
    }
    /**
     * This method allows changing this instance agents list to the received list f.
     * @param f
     */
    public void setAgents(List<MyAgent> f) {
        this._agents = f;
    }
    /**
     * This method allows changing this instance graph to the received graph g.
     * @param g
     */
    public void setGraph(directed_weighted_graph g) {this._gg =g;}
    /**
     * This method returns this instance list of agents.
     * @return  _agents
     */
    public List<MyAgent> getAgents() {return _agents;}
    /**
     * This method returns this instance list of pokemons.
     * @return  _pokemons
     */
    public List<MyPokemon> getPokemons() {return _pokemons;}
    /**
     * This method sets the received agent's timeForNext parameter to
     * the time in millisecond until the agent reaches it's next destination node.
     * @param a
     */
    public void updateTime(MyAgent a){
        a.setTimeForNext(((long)((a.get_curr_edge().getWeight()/a.getSpeed())*1000)));
    }
    /**
     * This method sets all of this agents list _sg_dt parameter to the time
     * in millisecond until they reach the pokemon they are chasing, according to the time
     * received.
     * @param time
     */
    public void updateTime(long time){
        for (MyAgent a: _agents) {
            if(a.getSrc()!=a.getDest() && a.getSrc()!=-1 && a.getDest()!=-1){
                geo_location pDest=_gg.getNode(a.getDest()).getLocation();
                geo_location pSrc=_gg.getNode(a.getSrc()).getLocation();
                double dist=pDest.distance(pSrc);
                double distA=a.getLocation().distance(pDest);
                double ratio=distA/dist;
                double DigiDist=_gg.getEdge(a.getSrc(),a.getDest()).getWeight()*ratio;
                long dt=(long)((DigiDist/a.getSpeed())*1000);
                a.setTimeForNext(time-dt);
            }
        }
    }
    /**
     * This method returns the minimum timeForNext value among this instance list of agents.
     * @return min
     */
    public long getMinTime(){
        long min=Long.MAX_VALUE;
        for (MyAgent a:_agents) {
            if(a.getTimeForNext()<min) min=a.getTimeForNext();
        }
        return min;
    }
    /**
     * This method returns the graph of the arena.
     * @return _gg
     */
    public directed_weighted_graph getGraph() {
        return _gg;
    }
    /**
     * This method receives a string representing a list of agents in a json format
     * and a graph. The method returns a list of agents with values according to the
     * string values and locations according to the graph.
     * @param aa
     * @param gg
     * @return ans
     */
    public static List<MyAgent> getAgents(String aa, directed_weighted_graph gg) {
        ArrayList<MyAgent> ans = new ArrayList<MyAgent>();
        try {
            JSONObject ttt = new JSONObject(aa);
            JSONArray ags = ttt.getJSONArray("Agents");
            for(int i=0;i<ags.length();i++) {
                MyAgent c = new MyAgent(gg,0);
                c.update(ags.get(i).toString());
                ans.add(c);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ans;
    }
    /**
     * This method removes a pokemon that has the same key value to the received
     * key from this pokemon list.
     * @param  key
     */
    public void removePoke(int key){
        for (MyPokemon p: _pokemons) {
            if(p.getKey()==key){
                _pokemons.remove(p);
                break;
            }
        }
    }
    /**
     * This method receives a string representing a list of pokemons in a json format
     * and updates this list of pokemons according to the received string.
     * @param s
     */
    public void updatePokemon(String s){
        try {
            JSONObject ttt = new JSONObject(s);
            JSONArray ags = ttt.getJSONArray("Pokemons");
            for (int i = 0; i < ags.length(); i++) {
                JSONObject pp = ags.getJSONObject(i);
                JSONObject pk = pp.getJSONObject("Pokemon");
                int t = pk.getInt("type");
                double v = pk.getDouble("value");
                String p = pk.getString("pos");
                MyPokemon f = new MyPokemon(new Point3D(p), t, v, null);
                    if (!_pokemons.contains(f)) {
                        _pokemons.add(f);
                        updateEdge(f, _gg);
                    }
                }
            boolean flag=true;
            for (int i = 0; i < _pokemons.size(); i++) {
                geo_location pos=_pokemons.get(i).get_pos();
                for (int j = 0; j < ags.length(); j++) {
                    JSONObject pp2 = ags.getJSONObject(j);
                    JSONObject pk2 = pp2.getJSONObject("Pokemon");
                    Point3D posVerify=new Point3D(pk2.getString("pos"));
                    if(posVerify.equals(pos)) flag=false;
                }
                if(flag) replace(_pokemons.remove(i));
                flag=true;

            }
        }
        catch (JSONException e) {e.printStackTrace();}
    }
    /**
     * This method receives a pokemon and checks if any agent chasing it. If
     * an agent is chasing the received pokemon p then replace it to another pokemon.
     * @param p
     */
    public void replace(MyPokemon p){
        for (MyAgent a:_agents) {
            if(a.get_curr_fruit()==p){
                chooseNextPoke(a);
            }
        }
    }
    /**
     * This method receives an agent, and replaces the pokemon he's chasing.
     * @param agent
     */
    private void chooseNextPoke(MyAgent agent) {
        DWGraph_Algo ga = new DWGraph_Algo();
        MyPokemon chosenOne = null;
        double minDist = Double.MAX_VALUE;
        double currDist = 0;
        ga.init(getGraph());
        for (MyPokemon p : getPokemons()) {
            if (p.getChase() == -1) {
                currDist = ga.shortestPathDist(agent.getSrc(), p.get_edge().getSrc());
                if (currDist != -1) {
                    if (currDist < minDist) {
                        minDist = currDist;
                        chosenOne = p;
                    }
                }
            }
        }
        agent.setCurrNode(agent.getSrc());
        agent.set_curr_fruit(chosenOne);
        agent.set_gg(ga.getGraph());
        agent.set_curr_edge(chosenOne.get_edge());
        chosenOne.setChase(0);
        agent.setPath(ga.shortestPath(agent.getSrc(), chosenOne.get_edge().getSrc()));
    }
    /**
     * This method receives a string representing a list of agents in a json format
     * and updates this instance list of agents according to the string.
     * @param s
     */
    public  void updateAgents(String s){
        try {
            JSONObject jsonObject=new JSONObject(s);
            JSONArray agents_arr= jsonObject.getJSONArray("Agents");
            for (int i = 0; i < agents_arr.length(); i++) {
             String agent_string=agents_arr.get(i).toString();
             JSONObject line = new JSONObject(agent_string);
             JSONObject agent = line.getJSONObject("Agent");
             int id = agent.getInt("id");
                for (MyAgent a: _agents) {
                    if(a.getID()==id){
                        a.set_value(agent.getDouble("value"));
                        a.setSrc(agent.getInt("src"));
                        a.setSpeed(agent.getDouble("speed"));
                        a.set_pos(new Point3D(agent.getString("pos")));
                        a.setDest(agent.getInt("dest"));
                        break;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     * This method receives a string representing a list of pokemons in a json format
     * and returns a list of pokemons containing the same values according to the string's pokemons.
     * @param fs
     * @return ans
     */
    public static ArrayList<MyPokemon> json2Pokemons(String fs) {
        ArrayList<MyPokemon> ans = new  ArrayList<MyPokemon>();
        try {
            JSONObject ttt = new JSONObject(fs);
            JSONArray ags = ttt.getJSONArray("Pokemons");
            for(int i=0;i<ags.length();i++) {
                JSONObject pp = ags.getJSONObject(i);
                JSONObject pk = pp.getJSONObject("Pokemon");
                int t = pk.getInt("type");
                double v = pk.getDouble("value");
                String p = pk.getString("pos");
                MyPokemon f = new MyPokemon(new Point3D(p), t, v, null);
                ans.add(f);
            }
        }
        catch (JSONException e) {e.printStackTrace();}
        return ans;
    }
    /**
     * This method receives a pokemon and a graph and updates the pokemon
     * edge parameter according to it's location on the graph.
     * @param g
     * @param fr
     */
    public static void updateEdge(MyPokemon fr, directed_weighted_graph g) {
        Iterator<node_data> itr = g.getV().iterator();
        while(itr.hasNext()) {
            node_data v = itr.next();
            Iterator<edge_data> iter = g.getE(v.getKey()).iterator();
            while(iter.hasNext()) {
                edge_data e = iter.next();
                boolean f = isOnEdge(fr.getLocation(), e,fr.getType(), g);
                if(f) {fr.set_edge(e);}
            }
        }
    }
    /**
     * This method receives three 3D Points representing locations of 2 nodes in
     * a graph and a pokemon location. Returns true iff the pokemon is located on
     * the edge between them.
     * @param src
     * @param p
     * @param dest
     * @return true iff the pokemon is located on the edge between src and dest nodes.
     */
    private static boolean isOnEdge(geo_location p, geo_location src, geo_location dest ) {

        boolean ans = false;
        double dist = src.distance(dest);
        double d1 = src.distance(p) + p.distance(dest);
        if(dist>d1-(EPS2*EPS2)) {ans = true;}
        return ans;
    }
    /**
     * This method receives a 3D point representing a pokemon's location and
     * a src and dest values representing nodes with identical key values in the graph g.
     * Returns true iff the pokemon is located on the edge between the nodes src and dest.
     * @param s
     * @param d
     * @param g
     * @return true iff the pokemon is located on the edge between the nodes src and dest.
     */
    private static boolean isOnEdge(geo_location p, int s, int d, directed_weighted_graph g) {
        geo_location src = g.getNode(s).getLocation();
        geo_location dest = g.getNode(d).getLocation();
        return isOnEdge(p,src,dest);
    }
    /**
     * This method receives a 3D point representing a pokemon's location and
     * type, an edge and a graph. The method return true iff the pokemon is located
     * on the edge e in the graph g.
     * @param e
     * @param type
     * @param g
     * @return true iff the pokemon is located on the edge e in the graph g.
     */
    private static boolean isOnEdge(geo_location p, edge_data e, int type, directed_weighted_graph g) {
        int src = g.getNode(e.getSrc()).getKey();
        int dest = g.getNode(e.getDest()).getKey();
        if(type<0 && dest>src) {return false;}
        if(type>0 && src>dest) {return false;}
        return isOnEdge(p,src, dest, g);
    }
    /**
     * This method receives a graph and returns a Range2D object representing
     * the graph dimensions - width and height.
     * @param g
     * @return range
     */
    private static Range2D GraphRange(directed_weighted_graph g) {
        Iterator<node_data> itr = g.getV().iterator();
        double x0=0,x1=0,y0=0,y1=0;
        boolean first = true;
        while(itr.hasNext()) {
            geo_location p = itr.next().getLocation();
            if(first) {
                x0=p.x(); x1=x0;
                y0=p.y(); y1=y0;
                first = false;
            }
            else {
                if(p.x()<x0) {x0=p.x();}
                if(p.x()>x1) {x1=p.x();}
                if(p.y()<y0) {y0=p.y();}
                if(p.y()>y1) {y1=p.y();}
            }
        }
        Range xr = new Range(x0,x1);
        Range yr = new Range(y0,y1);
        Range2D range=new Range2D(xr,yr);
        return range;
    }
    /**
     * This method receives a graph and Range2D object, and returns
     * an Range2Range object according to the graph and frame.
     * @param g
     * @param frame
     * @return ans
     */
    public static Range2Range w2f(directed_weighted_graph g, Range2D frame) {
        Range2D world = GraphRange(g);
        Range2Range ans = new Range2Range(world, frame);
        return ans;
    }
    /**
     * This method return a string represents this instance
     * in a json format.
     * @return a string describing this instance in json format.
     */
    @Override
    public String toString() {
        Gson gson=new Gson();
        return gson.toJson(this);
    }
}
