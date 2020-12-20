package test;

import api.DWGraph_DS;
import api.NodeData;
import api.edge_data;
import api.node_data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_DSTest {
    private DWGraph_DS graph;

    @BeforeEach
    void graph_Creator() {
        graph = new DWGraph_DS();
        node_data n = new NodeData(1);
        node_data n2 = new NodeData(2);
        graph.addNode(n);
        graph.addNode(n2);
        graph.connect(1, 2, 3);
    }

    @Test
    void getNode() {
        assertNotNull(graph.getNode(1));
        assertTrue(graph.getNode(1) instanceof node_data);
        assertNull(graph.getNode(0));
    }

    @Test
    void getEdge() {
        assertNotNull(graph.getEdge(1, 2));
        assertNull(graph.getEdge(2, 1));
        assertTrue(graph.getEdge(1, 2) instanceof edge_data);
        assertEquals(3, graph.getEdge(1, 2).getWeight());
        assertEquals(1, graph.getEdge(1, 2).getSrc());
        assertEquals(2, graph.getEdge(1, 2).getDest());
        assertNotEquals(2, graph.getEdge(1, 2).getSrc());
        assertNotEquals(1, graph.getEdge(1, 2).getDest());
    }

    @Test
    void addNode() {
        assertNull(graph.getNode(3));
        NodeData tempNode = new NodeData(3);
        graph.addNode(tempNode);
        assertNotNull(graph.getNode(3));
        assertTrue(graph.getNode(3) instanceof node_data);
    }

    @Test
    void connect() {
        assertNull(graph.getEdge(2, 1));
        graph.connect(2, 1, 3);
        assertNotNull(graph.getEdge(2, 1));
        assertTrue(graph.getEdge(2, 1) instanceof edge_data);
    }

    @Test
    void getV() {
        assertEquals(2, graph.getV().size());
        assertTrue(graph.getV().contains(graph.getNode(1)));
        assertTrue(graph.getV().contains(graph.getNode(2)));
        assertFalse(graph.getV().contains(graph.getNode(3)));
        NodeData tempNode = new NodeData(3);
        graph.addNode(tempNode);
        assertTrue(graph.getV().contains(graph.getNode(3)));
    }

    @Test
    void getE() {
        assertEquals(1, graph.getE(1).size());
        assertEquals(0, graph.getE(2).size());
        graph.connect(2, 1, 3);
        assertNotNull(graph.getE(2));
        assertTrue(graph.getE(2).contains(graph.getEdge(2, 1)));
    }

    @Test
    void removeNode() {
        graph.connect(2, 1, 3);
        assertNotNull(graph.getEdge(2, 1));
        assertNotNull(graph.getNode(1));
        assertNotNull(graph.getEdge(1, 2));
        graph.removeNode(1);
        assertNull(graph.getNode(1));
        assertNull(graph.getEdge(1, 2));
        assertNull(graph.getEdge(2, 1));
    }

    @Test
    void removeEdge() {
        assertNotNull(graph.getEdge(1, 2));
        graph.removeEdge(1, 2);
        assertNull(graph.getEdge(1,2));
    }

    @Test
    void nodeSize() {
        assertEquals(2,graph.nodeSize());
        graph.removeNode(1);
        assertEquals(1,graph.nodeSize());
        node_data v=new NodeData(3);
        graph.addNode(v);
        assertEquals(2,graph.nodeSize());
    }

    @Test
    void edgeSize() {
        assertEquals(1,graph.edgeSize());
        graph.connect(2,1,3);
        assertEquals(2,graph.edgeSize());
        graph.removeEdge(1,2);
        assertEquals(1,graph.edgeSize());
    }

    @Test
    void getMC() {
        assertEquals(3,graph.getMC());
        graph.connect(2,1,3);
        assertEquals(4,graph.getMC());
        NodeData v=new NodeData(3);
        graph.addNode(v);
        assertEquals(5,graph.getMC());
        graph.removeEdge(1,2);
        assertEquals(6,graph.getMC());
        graph.connect(2,1,3);
        assertEquals(6,graph.getMC());
        graph.removeNode(3);
        assertEquals(7,graph.getMC());
    }

}