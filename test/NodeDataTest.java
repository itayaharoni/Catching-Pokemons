package test;

import api.GeoLocation;
import api.NodeData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class NodeDataTest {

    private NodeData n=new NodeData(1);

    @Test
    void getKey() {
        assertEquals(1,n.getKey());
    }

    @Test
    void getLocation() {
        assertEquals(new GeoLocation(),n.getLocation());
    }

    @Test
    void setLocation() {
        n.setLocation(new GeoLocation());
        assertEquals(new GeoLocation(0,0,0),n.getLocation());
    }

    @Test
    void getWeight() {
        assertEquals(0,n.getWeight());
    }

    @Test
    void setWeight() {
        n.setWeight(1.5);
        assertEquals(1.5,n.getWeight());
    }

    @Test
    void getInfo() {
        assertEquals("",n.getInfo());
    }

    @Test
    void setInfo() {
        n.setInfo("Test");
        assertEquals("Test",n.getInfo());
    }

    @Test
    void getTag() {
        assertEquals(-1,n.getTag());
    }

    @Test
    void setTag() {
        n.setTag(1);
        assertEquals(1,n.getTag());
    }
}
