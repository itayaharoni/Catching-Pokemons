package api;

import com.google.gson.Gson;

/**
 * This class represents a geo location (x,y,z), aka Point3D
 */
public class GeoLocation implements geo_location {
    private double x;
    private double y;
    private double z;
    /**
     * Default constructor to the class, initializes all parameters to 0.
     */
    public GeoLocation(){
        x=y=z=0;
    }
    /**
     * Constructor to the class, initializes all of the
     * parameters of this GeoLocation to the received GeoLocation - g.
     * @param g
     */
    public GeoLocation(geo_location g){
        x=g.x();
        y=g.y();
        z=g.z();
    }
    /**
     * Constructor to the class, initializes all of the
     * parameters of this GeoLocation to the received values - x,y,z accordingly.
     * @param x
     * @param y
     * @param z
     */
    public GeoLocation(double x,double y, double z){
        this.x=x;
        this.y=y;
        this.z=z;
    }
    /**
     * This method returns the x coordinate of this geo location.
     * @return x
     */
    @Override
    public double x() {
        return x;
    }
    /**
     * This method returns the y coordinate of this geo location.
     * @return y
     */
    @Override
    public double y() {
        return y;
    }
    /**
     * This method returns the z coordinate of this geo location.
     * @return z
     */
    @Override
    public double z() {
        return z;
    }
    /**
     * This method returns the distance between this GeoLocation to
     * the received GeoLocation g.
     * @param g
     * @return the distance between this GeoLocation and the GeoLocation g.
     */
    @Override
    public double distance(geo_location g) {
        return Math.sqrt(Math.pow(x-g.x(),2)+Math.pow(y-g.y(),2)+Math.pow(z-g.z(),2));
    }
    /**
     * This method returns a string representing this GeoLocation in a json format.
     * @return a string representing this GeoLocation in a json format
     */
    @Override
    public String toString() {
        Gson gson=new Gson();
        return gson.toJson(this);
    }
    /**
     * This method returns true iff the object o is equals to this GeoLocation.
     * @return true iff the object o equals to this GeoLocation
     */
    @Override
    public boolean equals(Object obj) {
        if (this ==obj) return true;
        if(!(obj instanceof GeoLocation)) return false;
        GeoLocation g=(GeoLocation) obj;
        return (x==g.x())&&(y==g.y())&&(z==g.z());
    }
}
