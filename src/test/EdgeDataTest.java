package test;

import api.EdgeData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class EdgeDataTest {
    private EdgeData e=new EdgeData(1,2,3);
    @Test
    void getSrc() {
        assertEquals(1,e.getSrc());
    }

    @Test
    void getDest() {
        assertEquals(2,e.getDest());
    }

    @Test
    void getWeight() {
        assertEquals(3,e.getWeight());
    }

    @Test
    void getInfo() {
        assertEquals("",e.getInfo());
    }

    @Test
    void setInfo() {
        e.setInfo("Test");
        assertEquals("Test",e.getInfo());
    }

    @Test
    void getTag() {
        assertEquals(-1,e.getTag());
    }

    @Test
    void setTag() {
        e.setTag(1);
        assertEquals(1,e.getTag());
    }
}