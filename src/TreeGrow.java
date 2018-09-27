//package treeGrow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

public class TreeGrow {
	static long startTime = 0;
	static int frameX;
	static int frameY;
	static ForestPanel fp;

	// start timer
	private static void tick(){
		startTime = System.currentTimeMillis();
	}
	
	// stop timer, return time elapsed in seconds
	private static float tock(){
		return (System.currentTimeMillis() - startTime) / 1000.0f; 
	}
	
	public static void setupGUI(int frameX,int frameY,Tree [] trees) {
		Dimension fsize = new Dimension(800, 700);
		// Frame init and dimensions
    	JFrame frame = new JFrame("Photosynthesis"); 
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	//frame.setPreferredSize(fsize);
		BorderLayout bd = new BorderLayout();
		frame.setLayout(bd);
    	frame.setSize(800, 800);
    	
      	JPanel g = new JPanel();
      //	g.setSize(800,600);
        g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS)); 
     	g.setPreferredSize(fsize);
 
		fp = new ForestPanel(trees);
		fp.setPreferredSize(new Dimension(frameX,frameY));
		JScrollPane scrollFrame = new JScrollPane(fp);
		fp.setAutoscrolls(true);
		scrollFrame.setPreferredSize(fsize);
	    g.add(scrollFrame);

	    JPanel buttonpanel = new JPanel();
	    buttonpanel.setLayout(new GridLayout(1,4));
	    buttonpanel.setBackground(Color.red);
		Label yearCounter = new Label("years");
	    Button Exit = new Button("Exit");
	    Button play = new Button("Play");
	    Button pouse = new Button("Pouse");
	    buttonpanel.add(yearCounter);
	    buttonpanel.add(play);
	    buttonpanel.add(pouse);
	    buttonpanel.add(Exit);


      	frame.setLocationRelativeTo(null);// Center window on screen.


        frame.add(g ,bd.CENTER); //add contents to window
		frame.setContentPane(g);
		frame.add(buttonpanel, bd.SOUTH);
        frame.setVisible(true);
        Thread fpt = new Thread(fp);
        fpt.start();
	}
	private static final ForkJoinPool fjPool = new ForkJoinPool();
	private static double sum(ArrayList<int[]> arr){
		return fjPool.invoke(new Tread(arr,0,arr.size()));
	}
		
	public static void main(String[] args) {
		SunData sundata = new SunData();

		// read in forest and landscape information from file supplied as argument
		sundata.readData("sample_input.txt");
		System.out.println("Data loaded");

		frameX = sundata.sunmap.getDimX();
		frameY = sundata.sunmap.getDimY();
		setupGUI(frameX, frameY, sundata.trees);
		
		// create and start simulation loop here as separate thread
		int lower = 18;
		int higher = 20;
		ArrayList<int[]> tempArr = new ArrayList<>();
		for (int i = 0; i < 10;i++) {

			for (Tree t : sundata.trees) {
				if (t.getExt() >= lower && t.getExt() < higher) {

					int x = t.getX();
					int y = t.getY();
					int ex = t.getExt();
					int[] some = {x, y, ex};
					tempArr.add(some);
				}

			}
			if (lower > 0){
				lower -= 2;
				higher -= 2;}
			sum(tempArr);
			tempArr.clear();
			//		System.out.println();
		}
	}
}