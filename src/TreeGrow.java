//package treeGrow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;

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
	   // buttonpanel.setSize(700,200);
	    Button Exit = new Button("Exit");
	    Button play = new Button("Play");
	    Button pouse = new Button("Pouse");
	    buttonpanel.add(play);
	    buttonpanel.add(pouse);
	    buttonpanel.add(Exit);

    	
      	frame.setLocationRelativeTo(null);  // Center window on screen.
     	frame.add(g ,bd.CENTER); //add contents to window
		frame.add(buttonpanel, bd.SOUTH);
      //  frame.setContentPane(g);
        frame.setVisible(true);
        Thread fpt = new Thread(fp);
        fpt.start();
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
	}
}