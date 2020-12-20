package test;

import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Range2RangeTest {
    Range2Range r2r = new Range2Range(new Range2D(new Range(0, 10), new Range(0, 10)), new Range2D(new Range(0, 100), new Range(0, 100)));

    @Test
    void world2frame() {
        Assertions.assertEquals(new Point3D(10, 10, 0), r2r.world2frame(new Point3D(1, 1, 0)));
        assertEquals(new Point3D(35, 78, 0), r2r.world2frame(new Point3D(3.5, 7.8, 0)));
        assertEquals(new Point3D(93.5, 41.1, 0), r2r.world2frame(new Point3D(9.35, 4.11, 0)));
    }
}