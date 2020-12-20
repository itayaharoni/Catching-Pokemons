package api;

import com.google.gson.Gson;

/**
 * This interface represents the set of operations applicable on a
 * directional edge(src,dest) in a (directional) weighted graph.
 * @author Itay.Aharoni
 *
 */
public class EdgeData implements edge_data {
    private int src;
    private int dest;
    private double weight;
    private String info;
    private int tag;
    /**
     * Constructor for EdgeData, receives a source and destination node id
     * and a weight for the edge between them and initializes the parameters
     * according to the values the constructor gets. Initializes the rest of
     * the parameters to default values.
     */
    public EdgeData(int src, int dest, double weight){
        this.src=src;
        this.dest=dest;
        this.weight=weight;
        info="";
        tag=-1;
    }
    /**
     * Returns the id of the source node of this edge.
     * @return src
     */
    @Override
    public int getSrc() {
        return src;
    }
    /**
     * Returns the id of the destination node of this edge
     * @return dest
     */
    @Override
    public int getDest() {
        return dest;
    }
    /**
     * Returns the weight of this edge. Must be a positive number.
     * @return weight
     */
    @Override
    public double getWeight() {
        return weight;
    }
    /**
     * Returns the remark (meta data) associated with this edge.
     * @return info
     */
    @Override
    public String getInfo() {
        return info;
    }
    /**
     * Changes the remark (meta data) associated with this edge,
     * to the received string.
     * @param s
     */
    @Override
    public void setInfo(String s) {
        info=s;
    }
    /**
     * Returns the tag - temporal data (aka color: e,g, white, gray, black)
     * which can be used be algorithms
     * @return tag
     */
    @Override
    public int getTag() {
        return tag;
    }
    /**
     * This method allows setting the "tag" value for temporal marking an edge - common
     * practice for marking by algorithms.
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        tag=t;
    }
    /**
     * This method returns a string which describes the edge's
     * values and parameters in a json format.
     * @return a string that represents the edge's values in a json format.
     */
    @Override
    public String toString() {
        Gson gson=new Gson();
        return gson.toJson(this);
    }
    /**
     * This method returns true iff the object o is equals to this edge.
     * @return true iff the object o equals to this edge
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EdgeData edgeData = (EdgeData) o;

        if (src != edgeData.getSrc()) return false;
        if (dest != edgeData.getDest()) return false;
        if (tag != edgeData.getTag()) return false;
        if(!edgeData.getInfo().equals(info)) return false;
        if (edgeData.getWeight()!=weight) return false;
        return true;
    }
}
