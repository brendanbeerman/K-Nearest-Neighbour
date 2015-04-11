

public class node {
	
	// globals
	
	private String value; // value of any give value in the data source
	private int x; // the x and y coordinates
	private int y;
	
	// setters
	
	public void setValue (String v) {
		value = v;
	}
	public void setCoordinates (int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	// getters

	public String getValue () {
		return value;
	}
	public int getX () {
		return x;
	}
	public int getY () {
		return y;
	}
	
	// constructor
	node () {}
	
	public String toString () {
		return "Value: " + value + " (" + x + ", " + y + ")\n";
	}
}
