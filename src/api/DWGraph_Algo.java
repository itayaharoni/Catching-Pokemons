package api;

import com.google.gson.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.*;
/**
 * This class represents a Directed (positive) Weighted Graph Theory Algorithms including:
 * 0. clone(); (copy)
 * 1. init(graph);
 * 2. isConnected(); // strongly (all ordered pais connected)
 * 3. double shortestPathDist(int src, int dest);
 * 4.shortestPath(int src, int dest);
 * 5. Save(file); // JSON file
 * 6. Load(file); // JSON file
 *
 * @author Itay.Aharoni
 */
public class DWGraph_Algo implements dw_graph_algorithms {
    private directed_weighted_graph graph;
    private HashMap<Integer, nodeData_Algo> vertices;

    public DWGraph_Algo(){
        graph=new DWGraph_DS();
        vertices=new HashMap<Integer,nodeData_Algo>();
    }
    /**
     * Init the graph on which this set of algorithms operates on
     * to the graph that is received by the method.
     * @param g
     */
    @Override
    public void init(directed_weighted_graph g) {
        graph = g;
    }
    /**
     * Return the underlying graph of which this class works on.
     * @return graph
     */
    @Override
    public directed_weighted_graph getGraph() {
        return graph;
    }
    /**
     * Computes a deep copy of this weighted graph.
     * @return copyGraph - a copy of this graph.
     */
    @Override
    public directed_weighted_graph copy() {
        directed_weighted_graph copyGraph = new DWGraph_DS();
        for (node_data v : graph.getV()) {
            copyGraph.addNode(new NodeData(v));
        }
        for (node_data copyV : copyGraph.getV()) {
            for (edge_data e : graph.getE(copyV.getKey())) {
                copyGraph.connect(e.getSrc(), e.getDest(), e.getWeight());
            }
        }
        return copyGraph;
    }
    /**
     * Computes a deep copy of this weighted graph with reversed edges.
     * @return reverse - an identical graph to this graph, but the edges are reversed.
     */
    private directed_weighted_graph reverseGraph() {
        directed_weighted_graph reverse = new DWGraph_DS();
        for (node_data v : graph.getV()) {
            reverse.addNode(new NodeData(v));
        }
        for (node_data reverseV : reverse.getV()) {
            for (edge_data e : graph.getE(reverseV.getKey())) {
                reverse.connect(e.getDest(), e.getSrc(), e.getWeight());
            }
        }
        return reverse;
    }
    /**
     * Returns true if and only if (iff) there is a valid path from each node to each
     * other node.
     * @return true if and only if (iff) the graph is strongly connected
     */
    @Override
    public boolean isConnected() {
        if (graph == null) return true;
        if (graph.nodeSize() == 0 || graph.nodeSize() == 1) return true;
        for (node_data v : graph.getV()) {
            v.setTag(-1);
        }
        Iterator<node_data> itr = graph.getV().iterator();
        node_data randomVertex = itr.next();
        ArrayDeque<node_data> queue = new ArrayDeque<node_data>();
        randomVertex.setTag(0);
        queue.add(randomVertex);
        while (!queue.isEmpty()) {
            Iterator<edge_data> eItr = graph.getE(queue.getFirst().getKey()).iterator();
            while (eItr.hasNext()) {
                edge_data e = eItr.next();
                if (graph.getNode(e.getDest()).getTag() == -1) {
                    graph.getNode(e.getDest()).setTag(0);
                    queue.addLast(graph.getNode(e.getDest()));
                }
            }
            if (!queue.isEmpty()) queue.pollFirst();
        }
        for (node_data v : graph.getV()) {
            if (v.getTag() == -1) return false;
        }
        directed_weighted_graph reverseGraph = reverseGraph();
        for (node_data reverseV : reverseGraph.getV()) {
            reverseV.setTag(-1);
        }
        randomVertex.setTag(0);
        queue.add(randomVertex);
        while (!queue.isEmpty()) {
            Iterator<edge_data> itrE = reverseGraph.getE(queue.getFirst().getKey()).iterator();
            while (itrE.hasNext()) {
                edge_data reverseE = itrE.next();
                if (reverseGraph.getNode(reverseE.getDest()).getTag() == -1) {
                    reverseGraph.getNode(reverseE.getDest()).setTag(0);
                    queue.addLast(reverseGraph.getNode(reverseE.getDest()));
                }
            }
            if (!queue.isEmpty()) queue.pollFirst();
        }
        for (node_data reverseV : reverseGraph.getV()) {
            if (reverseV.getTag() == -1) return false;
        }
        return true;
    }
    /**
     * This method returns the length of the shortest path between src to dest.
     * If no such path --> returns -1
     * @param src - start node
     * @param dest - end (target) node
     * @return the length of the shortest path between src to dest.
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (graph.getNode(src) == null || graph.getNode(dest) == null) return -1;
        if (src == dest) return 0;
        vertices = new HashMap<Integer, nodeData_Algo>();
        for (node_data v : graph.getV()) {
            vertices.put(v.getKey(), new nodeData_Algo(v));
        }
        vertices.get(src).setTag(0);
        PriorityQueue<nodeData_Algo> queue = new PriorityQueue<nodeData_Algo>(new nodeDataAlgoComparator());
        queue.add(vertices.get(src));
        while (!queue.isEmpty()) {
            Iterator<edge_data> itrE = graph.getE(queue.peek().getN().getKey()).iterator();
            while (itrE.hasNext()) {
                edge_data e = itrE.next();
                if (vertices.get(e.getDest()).getTag() > e.getWeight() + vertices.get(e.getSrc()).getTag()) {
                    vertices.get(e.getDest()).setTag(e.getWeight() + vertices.get(e.getSrc()).getTag());
                    queue.add(vertices.get(e.getDest()));
                }
            }
            if (!queue.isEmpty()) queue.poll();
        }
        if (vertices.get(dest).getTag() == Double.MAX_VALUE) return -1;
        return vertices.get(dest).getTag();
    }
    /**
     * This method returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * If no such path --> returns null;
     * @param src - start node
     * @param dest - end (target) node
     * @return a list of the shortest path between src to dest in order.
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {
        if (graph.getNode(src) == null || graph.getNode(dest) == null) return null;
        LinkedList<node_data> path = new LinkedList<node_data>();
        if (src == dest) {
            path.add(graph.getNode(src));
            return path;
        }
        if (shortestPathDist(src, dest) == -1) return null;
        path.add(graph.getNode(dest));
        directed_weighted_graph reverseGraph = reverseGraph();
        while (path.getFirst().getKey() != src) {
            Iterator<edge_data> itrE = reverseGraph.getE(path.getFirst().getKey()).iterator();
            while (itrE.hasNext()) {
                edge_data reverseE = itrE.next();
                if (vertices.get(reverseE.getSrc()).getTag() == reverseE.getWeight() + vertices.get(reverseE.getDest()).getTag()) {
                    path.addFirst(graph.getNode(reverseE.getDest()));
                }
            }
        }
        return path;
    }
    /**
     * Saves this weighted (directed) graph to the given
     * file name - in JSON format.
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        Gson gson = new Gson();
        String json = gson.toJson(graph);
        try {
            PrintWriter pw = new PrintWriter(new File(file));
            pw.write(json);
            pw.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }
    /**
     * This class represents a JSON deserializer to the DWGraph_DS graph.
     */
    private class DWGraphDeserializer implements JsonDeserializer<directed_weighted_graph> {
        /**
         * This method receives a JSON object representing a weighted graph and
         * transforms the JSON object into a DWGraph_DS object.
         * @param jsonElement
         * @param type
         * @param jsonDeserializationContext
         * @return g - the graph which is an instance of the DWGraph_DS with the values of the JSON object
         */
        @Override
        public directed_weighted_graph deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            directed_weighted_graph g = new DWGraph_DS();
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray nodes = jsonObject.get("Nodes").getAsJsonArray();
            JsonArray edges = jsonObject.get("Edges").getAsJsonArray();
            for (int i = 0; i < nodes.size(); i++) {
                JsonObject vertex = nodes.get(i).getAsJsonObject();
                g.addNode(new NodeData(vertex.get("id").getAsInt()));
                String s[] = vertex.get("pos").getAsString().split(",");
                g.getNode(vertex.get("id").getAsInt()).setLocation(new GeoLocation(Double.parseDouble(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2])));
            }
            for (int i = 0; i < edges.size(); i++) {
                JsonObject edge = edges.get(i).getAsJsonObject();
                g.connect(edge.get("src").getAsInt(), edge.get("dest").getAsInt(), edge.get("w").getAsDouble());
            }
            return g;
        }
    }
    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name of JSON file
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        directed_weighted_graph copy = graph;
        GsonBuilder gsonBuilder = new GsonBuilder();
        DWGraphDeserializer dwgDS = new DWGraphDeserializer();
        gsonBuilder.registerTypeAdapter(directed_weighted_graph.class, dwgDS);
        Gson customGson = gsonBuilder.create();
        try {
            FileReader reader = new FileReader(file);
            graph = customGson.fromJson(reader, directed_weighted_graph.class);
            return true;
        } catch (FileNotFoundException e) {
            graph = copy;
            return false;
        }
    }
    /**
     * This method load a graph represented as an JSON object to this graph algorithm.
     * if the JSON object was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - the JSON object
     * @return true - iff the graph was successfully loaded.
     */
    public boolean load2(String file) {
        directed_weighted_graph copy = graph;
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            DWGraphDeserializer dwgDS = new DWGraphDeserializer();
            gsonBuilder.registerTypeAdapter(directed_weighted_graph.class, dwgDS);
            Gson customGson = gsonBuilder.create();
            graph = customGson.fromJson(file, directed_weighted_graph.class);
            return true;
        } catch (Exception e) {
            graph = copy;
            return false;
        }
    }
    /**
     * This method receives a node and returns the connectivity component the
     * node is part of in this graph.
     * @param n
     * @return realComponent - the connectivity component the node is part of - represented as a list of nodes.
     */
    private List<node_data> searchComponent(node_data n) {
        List<node_data> component = new ArrayList<node_data>();
        for (node_data v : graph.getV()) {
            v.setTag(-1);
        }
        n.setTag(0);
        LinkedList<node_data> ls = new LinkedList<node_data>();
        ls.addFirst(n);
        component.add(n);
        while (!ls.isEmpty()) {
            Iterator<edge_data> itr = graph.getE(ls.getFirst().getKey()).iterator();
            while (itr.hasNext()) {
                edge_data e = itr.next();
                node_data neighbor = graph.getNode(e.getDest());
                if (neighbor.getTag() == -1) {
                    neighbor.setTag(0);
                    ls.addLast(neighbor);
                    component.add(neighbor);
                }
            }
            if (!ls.isEmpty()) ls.pollFirst();
        }
        directed_weighted_graph reverseG = reverseGraph();
        List<node_data> component2 = new ArrayList<node_data>();
        for (node_data v : reverseG.getV()) {
            v.setTag(-1);
        }
        node_data n2 = reverseG.getNode(n.getKey());
        n2.setTag(0);
        ls.add(n2);
        component2.add(n2);
        while (!ls.isEmpty()) {
            Iterator<edge_data> itr2 = reverseG.getE(ls.getFirst().getKey()).iterator();
            while (itr2.hasNext()) {
                edge_data e2 = itr2.next();
                node_data neighbor2 = reverseG.getNode(e2.getDest());
                if (neighbor2.getTag() == -1) {
                    neighbor2.setTag(0);
                    ls.addLast(neighbor2);
                    component2.add(neighbor2);
                }
            }
            if (!ls.isEmpty()) ls.pollFirst();
        }
        List<node_data> realComponent = new ArrayList<node_data>();
        boolean contains = false;
        for (node_data node : component) {
            for (node_data node2 : component2) {
                if (node.getKey() == node2.getKey()) {
                    contains = true;
                    break;
                }
            }
            if (contains) realComponent.add(node);
            contains = false;
        }
        return realComponent;
    }
    /**
     * This method receives a list of connectivity components of this graph and a
     * node belongs to this graph. The method returns true iff (if and only if) the
     * node is contained in any of the connectivity components.
     * @param n
     * @param Components
     * @return true iff the node is contained in any of the Components list of nodes.
     */
    private boolean isContained(List<List<node_data>> Components, node_data n) {
        for (int i = 0; i < Components.size(); i++) {
            for (int j = 0; j < Components.get(i).size(); j++) {
                if (n.getKey() == Components.get(i).get(j).getKey()) return true;
            }
        }
        return false;
    }
    /**
     * This method returns all of the connectivity components of this graph
     * in a form of List of lists, each list of nodes represents a connectivity
     * component of this graph.
     * @return Components - all of the connectivity components of this graph.
     */
    public List<List<node_data>> GraphComponents() {
        List<List<node_data>> Components = new ArrayList<List<node_data>>();
        Iterator<node_data> itr = graph.getV().iterator();
        while (itr.hasNext()) {
            node_data n = itr.next();
            if (!isContained(Components, n)) {
                Components.add(searchComponent(n));
            }
        }
        return Components;
    }
    /**
     * This class is a nodeDataAlgo comparator.
     */
    private class nodeDataAlgoComparator implements Comparator<nodeData_Algo> {
        /**
         * This method compares two nodeDataAlgo according to their tag parameter.
         * @param o1
         * @param o2
         * @return -1 iff first node's tag is smaller 1 if bigger and 0 if equals to the other node's tag.
         */
        @Override
        public int compare(nodeData_Algo o1, nodeData_Algo o2) {
            if (o1.getTag() < o2.getTag()) return -1;
            if (o1.getTag() > o2.getTag()) return 1;
            return 0;
        }
    }
    /**
     * This class represents a vertex in a weighted directional graph
     * with another tag parameter that contains real numbers. The second tag
     * parameter is used for calculating algorithms on the DWGraph_Algo class.
     */
    private class nodeData_Algo {
        private node_data n;
        private double tag;
        /**
         * Constructor for nodeData_Algo sets this node_data as n and initializes the tag
         * parameter to max value.
         * @param n
         */
        public nodeData_Algo(node_data n) {
            this.n = n;
            this.tag = Double.MAX_VALUE;
        }
        /**
         * This method returns the node_data of this class.
         * @return n
         */
        public node_data getN() {
            return n;
        }
        /**
         * This method returns the tag of this class.
         * @return tag
         */
        public double getTag() {
            return tag;
        }
        /**
         * This method allows changing the node_data of this class
         * to the received node_data.
         * @param n
         */
        public void setN(node_data n) {
            this.n = n;
        }
        /**
         * This method allows changing the tag of
         * this class to the received tag.
         * @param tag
         */
        public void setTag(double tag) {
            this.tag = tag;
        }
    }
    /**
     * This method returns a string which describes the graph's
     * values and parameters in a json format.
     * @return a string that represents the graph's values in a json format.
     */
    @Override
    public String toString() {
        Gson gson=new Gson();
        return gson.toJson(this);
    }
}
