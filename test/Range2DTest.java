package test;

import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Range2DTest {
    Range rangeX=new Range(0,10);
    Range rangeY=new Range(0,10);
    Range2D range2D=new Range2D(rangeX,rangeY);
    @Test
    void getPortion() {
        Assertions.assertEquals(new Point3D(0.1,0.1,0),range2D.getPortion(new Point3D(1,1,0)));
        assertEquals(new Point3D(1,1,0),range2D.getPortion(new Point3D(10,10,0)));
    }

    @Test
    void fromPortion() {
        assertEquals(new Point3D(10,10,0),range2D.fromPortion(new Point3D(1,1,0)));
        assertEquals(new Point3D(10,50,0),range2D.fromPortion(new Point3D(1,5,0)));
        assertEquals(new Point3D(70,35,0),range2D.fromPortion(new Point3D(7,3.5,0)));
        assertEquals(new Point3D(100,100,0),range2D.fromPortion(new Point3D(10,10,0)));
    }
}