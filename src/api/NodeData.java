package api;

import com.google.gson.Gson;

/**
 * This class represents the set of operations applicable on a
 * node (vertex) in a (directional) weighted graph.
 * @author Itay.Aharoni
 *
 */
public class NodeData implements node_data {
    private int key;
    private geo_location pos;
    private double weight;
    private String info;
    private int tag;
    /**
     * Constructor for NodeData, initializes the node key to the
     * received key and gives default values to the other parameters.
     */
    public NodeData(int key){
        this.key=key;
        pos=new GeoLocation();
        weight=0;
        info="";
        tag=-1;
    }
    /**
     * Constructor for NodeData, receives a node and initializes this
     * node's parameters according to the received node's parameters.
     */
    public NodeData(node_data n){
        key=n.getKey();
        if(n.getLocation()!=null) {
            pos = new GeoLocation(n.getLocation());
        }else {
            pos=null;
        }
        weight=n.getWeight();
        tag=n.getTag();
        info=n.getInfo();
    }
    /**
     * Returns the key (id) associated with this node.
     * @return key
     */
    @Override
    public int getKey() {
        return key;
    }
    /**
     * Returns the location of this node,
     * if none returns null.
     * @return pos
     */
    @Override
    public geo_location getLocation() {
        return pos;
    }
    /**
     * Changes this node's location.
     * @param p - new new location  (position) of this node.
     */
    @Override
    public void setLocation(geo_location p) {
        pos=new GeoLocation(p);
    }
    /**
     * Returns the weight associated with this node.
     * @return weight
     */
    @Override
    public double getWeight() {
        return weight;
    }
    /**
     * Changes this node's weight.
     * @param w - the new weight
     */
    @Override
    public void setWeight(double w) {
        weight=w;
    }
    /**
     * Returns the remark (meta data) associated with this node.
     * @return info
     */
    @Override
    public String getInfo() {
        return info;
    }
    /**
     * Changes the remark (meta data) associated with this node.
     * @param s
     */
    @Override
    public void setInfo(String s) {
        info=s;
    }
    /**
     * Returns the node's tag - temporal data
     * (aka color: e,g, white, gray, black) which can be used be algorithms
     * @return tag
     */
    @Override
    public int getTag() {
        return tag;
    }
    /**
     * Changes the "tag" value for temporal marking an node - common
     * practice for marking by algorithms.
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        tag=t;
    }
    /**
     * This method returns a string which describes the node's
     * values and parameters in a json format.
     * @return a string that represents the node's values in a json format.
     */
    @Override
    public String toString() {
        Gson gson=new Gson();
        return gson.toJson(this);
    }
    /**
     * This method returns true iff the object o is equals to this node.
     * @return true iff the object o equals to this node
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeData nodeData = (NodeData) o;
        if(nodeData.getKey()!=key) return false;
        if(!nodeData.getLocation().equals(pos)) return false;
        if (!nodeData.getInfo().equals(info)) return false;
        if(nodeData.getTag()!=tag) return false;
        if(nodeData.getWeight()!=weight) return false;
        return true;
    }
}
