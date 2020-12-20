package gameClient.util;

import api.geo_location;
import com.google.gson.Gson;

import java.io.Serializable;
/**
 * This class represents a 3D point in space.
 */
public class Point3D implements geo_location, Serializable{
    private double _x,_y,_z;
    /**
     * Constructor for this class initializes this point x,y,z to the received x,y,z accordingly.
     * @param x
     * @param y
     * @param z
     */
    public Point3D(double x, double y, double z) {
        _x=x;
        _y=y;
        _z=z;
    }
    /**
     * Constructor for this class initializes this point x,y,z to the
     * received point p x,y,z parameters accordingly.
     * @param p
     */
    public Point3D(Point3D p) {
       this(p.x(), p.y(), p.z());
    }
    /**
     * Constructor for this class initializes this point x,y to the received x,y accordingly.
     * And initializes the z parameter of this point to 0.
     * @param x
     * @param y
     */
    public Point3D(double x, double y) {this(x,y,0);}
    /**
     * Constructor for this class initializes this point x,y,z to the received x,y,z in the string s accordingly.
     * @param s
     */
    public Point3D(String s) { try {
            String[] a = s.split(",");
            _x = Double.parseDouble(a[0]);
            _y = Double.parseDouble(a[1]);
            _z = Double.parseDouble(a[2]);
        }
        catch(IllegalArgumentException e) {
            System.err.println("ERR: got wrong format string for Point3D init, got:"+s+"  should be of format: x,y,x");
        }
    }
    /**
     * This method returns the x parameter of this point.
     * @return x
     */
    @Override
    public double x() {return _x;}
    /**
     * This method returns the y parameter of this point.
     * @return y
     */
    @Override
    public double y() {return _y;}
    /**
     * This method returns the z parameter of this point.
     * @return z
     */
    @Override
    public double z() {return _z;}
    /**
     * This method returns the distance between this point to p2 point.
     * @param p2
     * @return distance between this point and the point p2
     */
    @Override
    public double distance(geo_location p2) {
        double dx = this.x() - p2.x();
        double dy = this.y() - p2.y();
        double dz = this.z() - p2.z();
        double t = (dx*dx+dy*dy+dz*dz);
        return Math.sqrt(t);
    }
    /**
     * This method returns true iff this point x,y,z parameters equals to
     * the point p x,y,z parameters accordingly.
     * @param p
     * @return true iff this point equals to the point p
     */
    public boolean equals(Object p) {
        if(p==null || !(p instanceof geo_location)) {return false;}
        Point3D p2 = (Point3D)p;
        return ( (_x==p2._x) && (_y==p2._y) && (_z==p2._z) );
    }
    /**
     * This method returns a string representing this point.
     * @return a string represting this point.
     */
    public String toString(){
        return _x+","+_y+","+_z;
    }
}

