//package treeGrow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ForkJoinPool;

public class TreeGrow {
	static long startTime = 0;
	static int frameX;
	static int frameY;
	static ForestPanel fp;
	static JFrame frame;
	//static JPanel g;
	//static SunData sundata;
	private boolean running = true ;
	private int yearCounter = 0;
//	static BorderLayout bd;
	TreeGrow() {
	//	sundata = new SunData();
	}
	// start timer
	private static void tick(){
		startTime = System.currentTimeMillis();
	}
	
	// stop timer, return time elapsed in seconds
	private static float tock(){
		return (System.currentTimeMillis() - startTime) / 1000.0f; 
	}
	
	public static void setupGUI(int frameX,int frameY,Tree [] trees) {
		TreeGrow treeGrow = new TreeGrow();
		Dimension fsize = new Dimension(800, 700);
		// Frame init and dimensions
		frame = new JFrame("Photosynthesis");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setPreferredSize(fsize);
    	//bd = new BorderLayout();
		//frame.setLayout(bd);
    	frame.setSize(800, 800);
    	
         JPanel	g = new JPanel();
      	//g.setSize(800,600);
        g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS)); 
     	g.setPreferredSize(fsize);
 
		fp = new ForestPanel(trees);
		fp.setPreferredSize(new Dimension(frameX,frameY));
		JScrollPane scrollFrame = new JScrollPane(fp);
		fp.setAutoscrolls(true);
		scrollFrame.setPreferredSize(fsize);
	    g.add(scrollFrame);

	    JPanel buttonpanel = new JPanel();
	    buttonpanel.setLayout(new FlowLayout());
	    //buttonpanel.setBackground(Color.red);
		Label yearCounter = new Label("years");
	    Button Exit = new Button("Exit");
	    Exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);}
		});
	    Button play = new Button("Play");
	    play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Play");
				treeGrow.setRunning(true);

			}
		});
	    Button pause = new Button("Pause");
	    pause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("pause");
				treeGrow.setRunning(false);
			}
		});
	    Button Reset = new Button("Reset");
	    Reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			 System.out.println("jihuyfdsarsdtfyg");
			}
		});
	    buttonpanel.add(yearCounter);
	    buttonpanel.add(Reset);
	    buttonpanel.add(play);
	    buttonpanel.add(pause);
	    buttonpanel.add(Exit);
	    g.add(buttonpanel);

      	frame.setLocationRelativeTo(null);// Center window on screen.
		frame.add(g); //add contents to window
		frame.setContentPane(g);
		//frame.add(buttonpanel, bd.SOUTH);
        frame.setVisible(true);
        Thread fpt = new Thread(fp);
        fpt.start();
	}

	private static final ForkJoinPool fjPool = new ForkJoinPool();
	private static ArrayList<Tree> sum(ArrayList<float[]> arr,SunData sunData){
		return fjPool.invoke(new Tread(arr,0,arr.size(),sunData));
	}
		
	public static void main(String[] args) {
		SunData sundata = new SunData();
		TreeGrow treeGrow = new TreeGrow();
		// read in forest and landscape information from file supplied as argument
		sundata.readData("sample_input.txt");
		System.out.println("Data loaded");

		frameX = sundata.sunmap.getDimX();
		frameY = sundata.sunmap.getDimY();

		setupGUI(frameX, frameY, sundata.inTrees);


		treeGrow.play(sundata);
		// create and start simulation loop here as separate thread
//		int lower = 18;
//		int higher = 20;
//		Tree[] result = new Tree[sundata.trees.length];
//		Arrays.fill(result,new Tree(-1,-1,-1));
//		ArrayList<Tree> newLister = new ArrayList<>();
//		ArrayList<float[]> tempArr = new ArrayList<>();
//
//
//		for (int i = 0; i < 10;i++) {
//		//	try {
////		BufferedWriter writer = new BufferedWriter(new FileWriter("test1.txt"));
//				for (Tree t : sundata.trees) {
//					float ext = t.getExt();
//					float x = t.getX();
//					float y = t.getY();
////					writer.write(String.valueOf(t.getX()));
////					writer.write(" ");
////					writer.write(String.valueOf(t.getY()));
////					writer.write(" ");
////					writer.write(String.valueOf(ext));
////					writer.newLine();
//					if ( ext >= lower && ext < higher) {
//
//
//						float ex = t.getExt();
//						float[] some = {x, y, ex};
//						tempArr.add(some);
//					}
//
//				}
//				if (lower > 0){
//					lower -= 2;
//					higher -= 2;}
//				if (tempArr.size() > 0)	{
//					ArrayList<Tree> gtg = sum(tempArr,sundata);
//					newLister.addAll(gtg);
////				/*Tree[] kaka = gtg.toArray(new Tree[gtg.size()]);
////				System.arraycopy(kaka,counter,result,counter,kaka.length-1);
////				counter = kaka.length;*/
//					tempArr.clear();}
////		writer.flush();
////		writer.close();
////	} catch (IOException e) {
////     e.printStackTrace();
////		}
//
//		}
//		Collections.reverse(newLister);
//		result = newLister.toArray(new Tree[newLister.size()]);
		//setupGUI(frameX, frameY, result);


//		try {
//			BufferedWriter writer = new BufferedWriter(new FileWriter("test2.txt"));
//			for (Tree tree : result){
//
//				writer.write(String.valueOf(tree.getX()));
//				writer.write(" ");
//				writer.write(String.valueOf(tree.getY()));
//				writer.write(" ");
//				writer.write(String.valueOf(tree.getExt()));
//				writer.newLine();
//			}
//			writer.flush();
//			writer.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println(result[result.length/2].getExt());
		//upDate(result);
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public boolean isRunning() {
		return running;
	}

	public void play(SunData sundata){
		while (isRunning()) {

			//Tree[] result = new Tree[sundata.trees.length];
		//	Arrays.fill(result, new Tree(-1, -1, -1));
			ArrayList<Tree> newLister = new ArrayList<>();
			ArrayList<float[]> tempArr = new ArrayList<>();

			int lower = 18;
			int higher = 20;
			for (int i = 0; i < 10; i++) {
				for (Tree t : sundata.trees) {
					float ext = t.getExt();
					float x = t.getX();
					float y = t.getY();
					if (ext >= lower && ext < higher) {

						float[] some = {x, y, ext};
						tempArr.add(some);
					}

				}
				if (lower > 0) {
					lower -= 2;
					higher -= 2;
				}
				if (tempArr.size() > 0) {
					ArrayList<Tree> gtg = sum(tempArr, sundata);
					newLister.addAll(gtg);
//				/*Tree[] kaka = gtg.toArray(new Tree[gtg.size()]);
//				System.arraycopy(kaka,counter,result,counter,kaka.length-1);
//				counter = kaka.length;*/
					tempArr.clear();
				}

			}
			yearCounter++;
			sundata.sunmap.resetShade();
			//Collections.reverse(newLister);
			//result = newLister.toArray(new Tree[newLister.size()]);
		}
	}

//	public Tree[] Reset(Tree[] trees){
//		for (Tree tree : trees){
//			tree.setExt((float) 0.4);
//		}
//		return  trees;
//	}
}