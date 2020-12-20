package test;

import gameClient.util.Point3D;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Point3DTest {
    Point3D p=new Point3D(1,2,0);
    @Test
    void x() {
        assertEquals(1,p.x());
    }

    @Test
    void y() {
        assertEquals(2,p.y());

    }

    @Test
    void z() {
        assertEquals(0,p.z());

    }

    @Test
    void distance() {
        assertEquals(3,p.distance(new Point3D(1,2,3)));
        assertEquals(1,p.distance(new Point3D(2,2,0)));
        assertNotEquals(1,p.distance(new Point3D(2,2,3)));
        assertEquals(0,p.distance(new Point3D(1,2,0)));
    }

    @Test
    void testEquals() {
        assertEquals(new Point3D(1,2,0),p);
        assertEquals(new Point3D(1,2,3),new Point3D(1,2,3));
    }

}