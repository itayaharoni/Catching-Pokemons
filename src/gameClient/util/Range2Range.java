package gameClient.util;

import api.geo_location;

/**
 * This class represents a simple world 2 frame conversion (both ways).
 */

public class Range2Range {
	private Range2D _world, _frame;
	/**
	 * Constructor to this class, initializes this world and frame parameters to the received
	 * w and f parameters accordingly.
	 * @param f
	 * @param w
	 */
	public Range2Range(Range2D w, Range2D f) {
		_world = new Range2D(w);
		_frame = new Range2D(f);
	}
	/**
	 * This method takes a point p in space and converts it to a point in the frame.
	 * @param p
	 * @return a 3D point that represents the location of p in the frame.
	 */
	public geo_location world2frame(geo_location p) {
		Point3D d = _world.getPortion(p);
		Point3D ans = _frame.fromPortion(d);
		return ans;
	}
}
