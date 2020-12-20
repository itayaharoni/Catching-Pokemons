package test;

import api.GeoLocation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeoLocationTest {
    private GeoLocation geo=new GeoLocation();
    @Test
    void x() {
        assertEquals(0,geo.x());
    }

    @Test
    void y() {
        assertEquals(0,geo.y());
    }

    @Test
    void z() {
        assertEquals(0,geo.z());
    }

    @Test
    void distance() {
        GeoLocation geo2=new GeoLocation(1,0,0);
        assertEquals(1,geo.distance(geo2));
    }

    @Test
    void testEquals() {
        GeoLocation geo2=new GeoLocation(0,0,0);
        assertTrue(geo.equals(geo2));
        GeoLocation geo3=new GeoLocation(1,1,1);
        assertFalse(geo.equals(geo3));
    }
}