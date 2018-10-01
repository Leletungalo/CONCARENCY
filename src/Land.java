//package treeGrow;

public class Land{
	
	private int xOfLand, yOfLand;
	private float[][] land;
	private float[][] intialLand;
	// sun exposure data here

	static float shadefraction = 0.1f; // only this fraction of light is transmitted by a tree

	Land(int dx, int dy) {
		this.xOfLand = dx;
		this.yOfLand = dy;
		land = new float[dx][dy];
		intialLand = new float[dx][dy];
	}

	int getDimX() {
		return xOfLand;
	}
	
	int getDimY() {
		return yOfLand;
	}
	
	// Reset the shaded landscape to the same as the initial sun exposed landscape
	// Needs to be done after each growth pass of the simulator
	void resetShade() {
		land = intialLand;

	}
	
	float getFull(int x, int y) {
		// to do
		return 0.0f; // incorrect value
	}
	
	void setFull(int x, int y, String val) {
		land[x][y] = Float.parseFloat(val);
		intialLand[x][y] = Float.parseFloat(val);
	}
	
	float getShade(int x, int y) {
		return land[x][y];
	}
	
	synchronized void setShade(int x, int y, float val){
		land[x][y] = val * 10/100;
	}

	public synchronized float[][] getLand() {
		return land;
	}

	// reduce the
	void shadow(Tree tree){
		// to do
	}
}