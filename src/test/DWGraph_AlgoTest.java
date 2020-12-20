package test;

import api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_AlgoTest {
    DWGraph_DS graph;
    DWGraph_Algo graph_algo;
    @BeforeEach
    void graph_Creator(){
        graph=new DWGraph_DS();
        graph_algo=new DWGraph_Algo();
        graph_algo.init(graph);
        node_data n1=new NodeData(1);
        node_data n2=new NodeData(2);
        node_data n3=new NodeData(3);
        node_data n4=new NodeData(4);
        node_data n5=new NodeData(5);
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);
        graph.addNode(n4);
        graph.addNode(n5);
        graph.connect(1,2,2);
        graph.connect(2,3,3);
        graph.connect(3,4,4);
        graph.connect(4,5,5);
        graph.connect(5,1,1);
    }
    @Test
    void init() {
        graph_algo.init(graph);
        assertEquals(graph,graph_algo.getGraph());
        DWGraph_DS graph2=new DWGraph_DS();
        graph_algo.init(graph2);
        assertEquals(graph2,graph_algo.getGraph());
    }

    @Test
    void getGraph() {
        assertTrue(graph_algo.getGraph() instanceof directed_weighted_graph);
        assertEquals(5,graph_algo.getGraph().getV().size());
        assertEquals(5,graph_algo.getGraph().edgeSize());
        assertEquals(10,graph_algo.getGraph().getMC());
        assertEquals(graph,graph_algo.getGraph());
    }

    @Test
    void copy() {
        directed_weighted_graph copyGraph=new DWGraph_DS();
        assertNotEquals(graph_algo.getGraph(),copyGraph);
        copyGraph=graph_algo.copy();
        assertEquals(graph_algo.getGraph(),copyGraph);
        graph_algo.getGraph().removeNode(1);
        assertNotEquals(graph_algo.getGraph(),copyGraph);
    }

    @Test
    void isConnected() {
        assertTrue(graph_algo.isConnected());
        graph_algo.getGraph().removeEdge(1,2);
        assertFalse(graph_algo.isConnected());
        graph_algo.getGraph().connect(1,2,2);
        assertTrue(graph_algo.isConnected());
        graph_algo.getGraph().removeEdge(3,4);
        assertFalse(graph_algo.isConnected());
    }

    @Test
    void shortestPathDist() {
        assertEquals(2,graph_algo.shortestPathDist(1,2));
        assertEquals(9,graph_algo.shortestPathDist(1,4));
        graph_algo.getGraph().connect(1,4,1);
        assertEquals(1,graph_algo.shortestPathDist(1,4));
        graph_algo.getGraph().removeEdge(1,4);
        graph_algo.getGraph().removeEdge(1,2);
        assertEquals(-1,graph_algo.shortestPathDist(1,2));
    }

    @Test
    void shortestPath() {
        assertEquals(5,graph_algo.shortestPath(1,5).size());
        List<node_data> path=graph_algo.shortestPath(1,3);
        assertEquals(1,path.get(0).getKey());
        assertEquals(2,path.get(1).getKey());
        assertEquals(3,path.get(2).getKey());
        graph_algo.getGraph().connect(1,3,1);
        path=graph_algo.shortestPath(1,3);
        assertEquals(2,path.size());
        assertEquals(1,path.get(0).getKey());
        assertEquals(3,path.get(1).getKey());
        assertNull(graph_algo.shortestPath(1,7));
        graph_algo.getGraph().removeEdge(1,2);
        graph_algo.getGraph().removeEdge(1,3);
        path=graph_algo.shortestPath(1,3);
        assertNull(path);
    }

    @Test
    void save() {
        assertTrue(graph_algo.save("SaveTest"));
    }

    @Test
    void load() {
        assertTrue(graph_algo.load("data/A0"));
        assertTrue(graph_algo.load("data/A1"));
        assertTrue(graph_algo.load("data/A2"));
        assertTrue(graph_algo.load("data/A3"));
        assertTrue(graph_algo.load("data/A4"));
        assertTrue(graph_algo.load("data/A5"));
    }

    @Test
    void load2() {
        try {
            String s = new String(Files.readAllBytes(Path.of("data/A0")));
            assertTrue(graph_algo.load2(s));
            s=new String(Files.readAllBytes(Path.of("data/A1")));
            assertTrue(graph_algo.load2(s));
            s=new String(Files.readAllBytes(Path.of("data/A2")));
            assertTrue(graph_algo.load2(s));
            s=new String(Files.readAllBytes(Path.of("data/A3")));
            assertTrue(graph_algo.load2(s));
            s=new String(Files.readAllBytes(Path.of("data/A4")));
            assertTrue(graph_algo.load2(s));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    void graphComponents() {
        assertEquals(1,graph_algo.GraphComponents().size());
        assertEquals(graph_algo.getGraph().nodeSize(),graph_algo.GraphComponents().get(0).size());
        List<node_data> ls=graph_algo.GraphComponents().get(0);
        for (node_data v: graph_algo.getGraph().getV()) {
            assertTrue(ls.contains(v));
        }
        graph_algo.getGraph().removeEdge(1,2);
        assertEquals(5,graph_algo.GraphComponents().size());
        graph_algo.getGraph().connect(5,2,1);
        assertEquals(2,graph_algo.GraphComponents().size());
        graph_algo.getGraph().removeEdge(3,4);
        assertEquals(5,graph_algo.GraphComponents().size());
    }
}