package test;

import gameClient.util.Range;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RangeTest {
    Range range=new Range(0,10);
    @Test
    void isIn() {
        assertTrue(range.isIn(2));
        assertFalse(range.isIn(11));
    }

    @Test
    void isEmpty() {
        assertFalse(range.isEmpty());
        assertTrue(new Range(10,1).isEmpty());
    }

    @Test
    void get_max() {
        assertEquals(10,range.get_max());
    }

    @Test
    void get_length() {
        assertEquals(10,range.get_length());
    }

    @Test
    void get_min() {
        assertEquals(0,range.get_min());
    }

    @Test
    void getPortion() {
        assertEquals(0.5,range.getPortion(5));
        assertEquals(0.7,range.getPortion(7));
    }

    @Test
    void fromPortion() {
        assertEquals(20,range.fromPortion(2));
        assertEquals(75,range.fromPortion(7.5));
        assertEquals(5,range.fromPortion(0.5));
    }
}