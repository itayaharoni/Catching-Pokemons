package gameClient.util;
/**
 * This class represents a simple 1D range of shape [min,max]
 */
public class Range {
	private double _min, _max;
	/**
	 * Constructor to this class, initializes the min and max parameters to the received
	 * min and max parameters accordingly.
	 * @param min
	 * @param max
	 */
	public Range(double min, double max) {
		set_min(min);
		set_max(max);
	}
	/**
	 * Constructor to this class, initializes the min and max parameters to the received
	 * min and max parameters of the x -Range instance accordingly.
	 * @param x
	 */
	public Range(Range x) {
		this(x._min, x._max);
	}
	/**
	 * This method return true iff the value d is on the line of min and max.
	 * @param d
	 * @return true iff d is between min to max.
	 */
	public boolean isIn(double d) {
		boolean inSide = false;
		if(d>=this.get_min() && d<=this.get_max()) {inSide=true;}
		return inSide;
	}
	/**
	 * This method return a string representing this Range instance.
	 * @return a string representing this Range instance
	 */
	public String toString() {
		String ans = "["+this.get_min()+","+this.get_max()+"]";
		if(this.isEmpty()) {ans = "Empty Range";}
		return ans;
	}
	/**
	 * This method return iff the min value is bigger then the max value.
	 * @return true iff the min value is bigger then the max value of this Range instance.
	 */
	public boolean isEmpty() {
		return this.get_min()>this.get_max();
	}
	/**
	 * This method returns the parameter max of this instance.
	 * @return _max
	 */
	public double get_max() {
		return _max;
	}
	/**
	 * This method returns the distance between max and min parameters of this instance.
	 * @return the distance between max and min.
	 */
	public double get_length() {
		return _max-_min;
	}
	/**
	 * This method allows setting the max parameter of this instance.
	 * @param _max
	 */
	private void set_max(double _max) {
		this._max = _max;
	}
	/**
	 * This method returns the parameter min of this instance.
	 * @return _min
	 */
	public double get_min() {
		return _min;
	}
	/**
	 * This method allows setting the max parameter of this instance.
	 * @param _min
	 */
	private void set_min(double _min) {
		this._min = _min;
	}
	/**
	 * This method returns the ratio of the line between the max and min
	 * parameters of this instance according to the d received.
	 * @param d
	 * @return the ratio of the distance between max and min according to the d value.
	 */
	public double getPortion(double d) {
		double d1 = d-_min;
		double ans = d1/get_length();
		return ans;
	}
	/**
	 * This method returns value of min plus the distance between min and value multiply by d.
	 * @param p
	 * @return value of min plus the distance between min and value multiply by d
	 */
	public double fromPortion(double p) {
		return _min+p* get_length();
	}
}
