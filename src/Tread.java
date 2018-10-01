
import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

public class Tread extends RecursiveTask<ArrayList<Tree>> {
    private int lo;
    private int hi;
    private ArrayList<float[]> arr;
    private ArrayList<Tree> tree;
    private SunData sunData;
    private static final int SEQUENTIAL_CUTOFF = 500;

   public Tread(ArrayList<float[]> a, int l, int h,SunData sunData) {
        lo=l; hi=h; arr=a;
        this.sunData = sunData;
        tree = new ArrayList<>();
    }

    protected ArrayList<Tree> compute(){

        if((hi-lo) < SEQUENTIAL_CUTOFF) {
            //float[][] data = sunData.sunmap.getLand();
            int count = 0;
            for(int i=lo; i < hi; i++){
                int xOfTree = (int) arr.get(count)[0];
                int yOfTree = (int) arr.get(count)[1];
                int lengthOfTree = (int) arr.get(count)[2];

                int x1 = xOfTree;
                int y2 = yOfTree;
                int intialLength = lengthOfTree;
                int xr = xOfTree - lengthOfTree;
                int yr = yOfTree - lengthOfTree;
                lengthOfTree = (lengthOfTree + lengthOfTree + 1);
                count++;

                float totalForOneTree = 0;
                int numberOfCells = 0;
                for (int z = 0 ; z < lengthOfTree; z++) {
                    try {
                        for (int l = 0; l < lengthOfTree ;l++){
                           // float sss = data[xOfTree][yOfTree];
                            float sss = sunData.sunmap.getShade(xOfTree,yOfTree);
                            numberOfCells++;
                            totalForOneTree += sss;
                            yOfTree++;
                            sunData.sunmap.setShade(xOfTree,yOfTree,sss);
                         //   data[xOfTree][yOfTree] = sss * (10/100);
                             }
                    }catch (ArrayIndexOutOfBoundsException e){}

                    yOfTree = yr;
                    xOfTree = xr + 1;
                    xr++;
                }
                float s = totalForOneTree/numberOfCells;
                float newLengthOfTree = intialLength + s/1000;

                for (Tree t : sunData.trees){

                if (t.getX() == x1 && t.getY() == y2)
                    t.setExt(newLengthOfTree);
                   // System.out.println("leg");
                }

                tree.add(new Tree(x1,y2,newLengthOfTree));
            }
            return tree;
        }
        else {
           Tread left = new Tread(arr,lo,(hi+lo)/2,sunData);
           Tread right= new Tread(arr,(hi+lo)/2,hi,sunData);

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
