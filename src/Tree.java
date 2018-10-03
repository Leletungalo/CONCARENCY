//package treeGrow;

// Trees define a canopy which covers a square area of the landscape
public class Tree{
	
private int xpos;	// x-coordinate of center of tree canopy
	    int ypos;	// y-coorindate of center of tree canopy
	    float ext;	// extent of canopy out in vertical and horizontal from center
	
	static float growfactor = 1000.0f; // divide average sun exposure by this amount to get growth in extent
	
public	
	Tree(int x, int y, float e){
		xpos=x; ypos=y; ext=e;
	}
	
	int getX() {
		return xpos;
	}
	
	int getY() {
		return ypos;
	}
	
	float getExt() {
		return ext;
	}
	
	synchronized void setExt(float e) {
		ext = e;
	}

	// return the average sunlight for the cells covered by the tree
	float sunExposure(Land land){
		int xOfTree = getX();
		int yOfTree = getY();
		int lengthOfTree = (int) getExt();

		int x1 = xOfTree;
		int y2 = yOfTree;
		int intialLength = lengthOfTree;
		int xr = xOfTree - lengthOfTree;
		int yr = yOfTree - lengthOfTree;

		if (xr < 0){
			xr = 0;
		}

		if (yr < 0){
			yr = 0;
		}

		lengthOfTree = (lengthOfTree + lengthOfTree + 1);
		//count++;

		float totalForOneTree = 0;
		int numberOfCells = 0;
		for (int z = xr ; z < lengthOfTree; z++) {
			try {
				for (int l = yr; l < lengthOfTree ;l++){
					float sss = land.getShade(xr,yr);
					numberOfCells++;
					totalForOneTree += sss;
					yOfTree++;
				}
			}catch (ArrayIndexOutOfBoundsException e){}

			yOfTree = yr;
			xOfTree = xr + 1;
			xr++;
		}
		return totalForOneTree/numberOfCells;
	}
	
	// is the tree extent within the provided range [minr, maxr)
	boolean inrange(float minr, float maxr) {
		return (ext >= minr && ext < maxr);
	}
	
	// grow a tree according to its sun exposure
	void sungrow(Land land) {
	    float size = sunExposure(land);
	    float newext = getExt() + size/1000;
		setExt(newext);
		//setExt(land.getShade(this.xpos,this.ypos));
	}
}