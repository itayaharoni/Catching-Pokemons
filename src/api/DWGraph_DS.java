package api;

import com.google.gson.Gson;

import java.util.Collection;
import java.util.HashMap;
/**
 * This class represents a directional weighted graph.
 * The class has a road-system or communication network in mind -
 * and supports a large number of nodes (over 100,000).
 */
public class DWGraph_DS implements directed_weighted_graph {
    private HashMap<Integer,node_data> graph;
    private HashMap<Integer,HashMap<Integer,edge_data>> Edges;
    private int mc;
    private int EdgeSize;
    public DWGraph_DS(){
        graph=new HashMap<Integer, node_data>();
        Edges=new HashMap<Integer, HashMap<Integer, edge_data>>();
        EdgeSize=0;
        mc=0;
    }
    /**
     * Returns the node_data by the node_id,
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_data getNode(int key) {
        return graph.get(key);
    }
    /**
     * Returns the data of the edge between the source node src and the
     * destination node dest, null if none.
     * @param src
     * @param dest
     * @return the data of the edge that connects between src to dest.
     */
    @Override
    public edge_data getEdge(int src, int dest) {
        if(graph.get(src)==null || graph.get(dest)==null) return null;
        return Edges.get(src).get(dest);
    }
    /**
     * Adds a new node to the graph with the given node_data.
     * @param n
     */
    @Override
    public void addNode(node_data n) {
        if(graph.get(n.getKey())==null) {
            node_data Vertex = new NodeData(n);
            graph.put(Vertex.getKey(), Vertex);
            HashMap<Integer, edge_data> edgeV = new HashMap<Integer, edge_data>();
            Edges.put(Vertex.getKey(), edgeV);
            mc++;
        }
    }
    /**
     * Connects an edge with weight w between node src to node dest.
     * Does nothing if the weight is a not a positive number or if
     * the edge already exists with the same weight value.
     * @param src - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w) {
        if (src == dest || w <= 0) return;
        if (graph.get(src) == null || graph.get(dest) == null) return;
        if (Edges.get(src).get(dest) == null) {
            Edges.get(src).put(dest, new EdgeData(src, dest, w));
            EdgeSize++;
            mc++;
        }else {
            if (Edges.get(src).get(dest).getWeight()==w) return;
            Edges.get(src).put(dest, new EdgeData(src, dest, w));
            EdgeSize++;
            mc++;
        }
    }
    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the nodes in the graph.
     * This method returns null if none.
     * @return Collection of this graph's vertices.
     */
    @Override
    public Collection<node_data> getV() {
        if(graph==null) return null;
        return graph.values();
    }
    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the edges getting out of
     * the given node (all the edges starting (source) at the given node).
     * @return Collection of the edges which their source is the given node_id
     */
    @Override
    public Collection<edge_data> getE(int node_id) {
        if (graph==null) return null;
        if (graph.get(node_id)==null) return null;
        return Edges.get(node_id).values();
    }
    /**
     * Deletes the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * @return the data of the removed node (null if none).
     * @param key
     */
    @Override
    public node_data removeNode(int key) {
        if(graph.get(key)==null) return null;
        if(Edges.get(key).size()==0){
            mc++;
            Edges.remove(key);
            return graph.remove(key);
        }
        for (node_data v: getV()) {
            for (edge_data e: getE(v.getKey())) {
                if(e.getDest()==key){
                    Edges.get(v.getKey()).remove(key);
                    EdgeSize--;
                }
            }
        }
        EdgeSize-=Edges.get(key).size();
        Edges.remove(key);
        mc++;
        return graph.remove(key);
    }
    /**
     * Deletes the edge from the graph,
     * @param src
     * @param dest
     * @return the data of the removed edge (null if none).
     */
    @Override
    public edge_data removeEdge(int src, int dest) {
        if(graph.get(src)==null || graph.get(dest)==null) return null;
        if(Edges.get(src).get(dest)==null) return null;
        EdgeSize--;
        mc++;
        return Edges.get(src).remove(dest);
    }
    /** Returns the number of vertices (nodes) in the graph.
     * @return graph.size()
     */
    @Override
    public int nodeSize() {
        return graph.size();
    }
    /**
     * Returns the number of edges of the directional graph.
     * @return EdgeSize
     */
    @Override
    public int edgeSize() {
        return EdgeSize;
    }
    /**
     * Returns the Mode Count - for testing changes in the graph.
     * @return mc
     */
    @Override
    public int getMC() {
        return mc;
    }
    /**
     * This method returns a string representing this graph parameters and values in a json format.
     * @return a string representing this graph parameters and values in a json format.
     */
    @Override
    public String toString() {
        Gson gson=new Gson();
        return gson.toJson(this);
    }
    /**
     * This method returns true iff the object o is equals to this graph.
     * @return true iff the object o equals to this graph
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DWGraph_DS that = (DWGraph_DS) o;
        if(that.nodeSize()!=nodeSize()) return false;
        if (EdgeSize != that.EdgeSize) return false;
        for (node_data v:getV()) {
            if(that.getNode(v.getKey())==null) return false;
            if(that.getE(v.getKey()).size()!=getE(v.getKey()).size()) return false;
            if(!(v.getLocation().equals(that.getNode(v.getKey()).getLocation()))) return false;
            if(!(v.getInfo().equals(that.getNode(v.getKey()).getInfo()))) return false;
            if(v.getTag()!=that.getNode(v.getKey()).getTag()) return false;
            if(v.getWeight()!=that.getNode(v.getKey()).getWeight()) return false;
            for (edge_data e:getE(v.getKey())) {
                if(that.getEdge(e.getSrc(),e.getDest())==null) return false;
                if(that.getEdge(e.getSrc(),e.getDest()).getWeight()!=e.getWeight()) return false;
                if(e.getTag()!=that.getEdge(e.getSrc(),e.getDest()).getTag()) return false;
                if(!(e.getInfo().equals(that.getEdge(e.getSrc(),e.getDest()).getInfo()))) return false;
            }
        }
        return true;
    }

}
