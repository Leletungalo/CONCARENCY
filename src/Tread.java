import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

public class Tread extends RecursiveTask<ArrayList<Tree>> {
    private int lo;
    private int hi;
    private ArrayList<float[]> arr;
    private ArrayList<Tree> tree;
    private static final int SEQUENTIAL_CUTOFF = 500;

   public Tread(ArrayList<float[]> a, int l, int h) {
        lo=l; hi=h; arr=a;
        tree = new ArrayList<>();
    }

    protected ArrayList<Tree> compute(){

        if((hi-lo) < SEQUENTIAL_CUTOFF) {
            float[][] dataaa = Land.getLand();
            int count = 0;
            for(int i=lo; i < hi; i++){
                int xOfTree = (int) arr.get(count)[0];
                int yOfTree = (int) arr.get(count)[1];
                int lengthOfTree = (int) arr.get(count)[2];
               // System.out.println(lengthOfTree);
                int x1 = xOfTree;
                int y2 = yOfTree;
                int xr = xOfTree;//- lengthOfTree;
                int yr = yOfTree;//- lengthOfTree;
               // lengthOfTree = lengthOfTree + 1;
                count++;

                float totalForOneTree = 0;

                for (int z = 0 ; z < lengthOfTree; z++) {
                    try {
                        for (int l = 0; l < lengthOfTree ;l++){
                            float sss = dataaa[xOfTree][yOfTree];
                            totalForOneTree += sss;
                            yOfTree++;
                            dataaa[xOfTree][yOfTree] = sss * (10/100);
                             }
                    }catch (ArrayIndexOutOfBoundsException e){}

                    yOfTree = yr;
                    xOfTree = xr + 1;
                    xr++;
                }
                  lengthOfTree = Math.round( lengthOfTree + totalForOneTree/1000);

                tree.add(new Tree(x1,y2,lengthOfTree));
            }
            return tree;
        }
        else {
           Tread left = new Tread(arr,lo,(hi+lo)/2);
           Tread right= new Tread(arr,(hi+lo)/2,hi);

            left.fork();
            right.fork();
            ArrayList<Tree> rightAns = right.join();
            ArrayList<Tree> leftAns  = left.join();
            ArrayList<Tree> newlist = new ArrayList<Tree>();
            newlist.addAll(rightAns);
            newlist.addAll(leftAns);
            return newlist ;
        }
    }

}
