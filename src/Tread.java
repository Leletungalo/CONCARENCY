import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

public class Tread extends RecursiveTask<Double> {
    private int lo;
    private int hi;
    private ArrayList<int[]> arr;
    private static final int SEQUENTIAL_CUTOFF = 500;

   public Tread(ArrayList<int[]> a, int l, int h) {
        lo=l; hi=h; arr=a;
    }

    protected Double compute(){

        if((hi-lo) < SEQUENTIAL_CUTOFF) {
            float[][] dataaa = Land.getLand();
            int count = 0;
            for(int i=lo; i < hi; i++){
                int xOfTree = arr.get(count)[0];
                int yOfTree = arr.get(count)[1];
                int lengthOfTree = arr.get(count)[2];
               // System.out.println(lengthOfTree);
                int xr = xOfTree - lengthOfTree;
                int yr = yOfTree - lengthOfTree;
                lengthOfTree = lengthOfTree + 1;
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

                  Tree.setExt(lengthOfTree);
               //   System.out.println("-------------------------");
               //   System.out.println(lengthOfTree);
            }
            return 0.0;
        }
        else {
           Tread left = new Tread(arr,lo,(hi+lo)/2);
           Tread right= new Tread(arr,(hi+lo)/2,hi);

            left.fork();
            right.fork();
           double rightAns = right.join();
           double leftAns  = left.join();
            return 0.0;
        }
    }
}
