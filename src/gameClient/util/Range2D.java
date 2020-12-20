package gameClient.util;
import api.geo_location;

/**
 * This class represents a 2D Range, composed from two 1D Ranges.
 */
public class Range2D {
	private Range _y_range;
	private Range _x_range;
	/**
	 * Constructor to this class, initializes this x_range and y_range parameters to the received
	 * x and y parameters accordingly.
	 * @param x
	 * @param y
	 */
	public Range2D(Range x, Range y) {
		_x_range = new Range(x);
		_y_range = new Range(y);
	}
	/**
	 * Constructor to this class, initializes this x_range and y_range parameters to the received
	 * x and y parameters of the w Range2D instance accordingly.
	 * @param w
	 */
	public Range2D(Range2D w) {
		_x_range = new Range(w._x_range);
		_y_range = new Range(w._y_range);
	}
	/**
	 * This method returns a 3D point with x and y values of this instance
	 * x and y getPortion according to the received p point x and y values accordingly. And sets the z value to 0.
	 * @param p
	 * @return a 3D point with x and y values of this instance x and y getPortion according to the received p point x and y values accordingly.
	 */
	public Point3D getPortion(geo_location p) {
		double x = _x_range.getPortion(p.x());
		double y = _y_range.getPortion(p.y());
		return new Point3D(x,y,0);
	}
	/**
	 * This method returns a 3D point with x and y values of this instance
	 * x and y fromPortion according to the received p point x and y values accordingly. And sets the z value to 0.
	 * @param p
	 * @return a 3D point with x and y values of this instance x and y fromPortion according to the received p point x and y values accordingly.
	 */
	public Point3D fromPortion(geo_location p) {
		double x = _x_range.fromPortion(p.x());
		double y = _y_range.fromPortion(p.y());
		return new Point3D(x,y,0);
	}	
}
