import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

public class Tread extends RecursiveTask<Double> {
    private int lo;
    private int hi;
    private int[][] arr;
    private static final int SEQUENTIAL_CUTOFF = 500;

   public Tread(int[][] a, int l, int h) {
        lo=l; hi=h; arr=a;
    }

    protected Double compute(){
        if((hi-lo) < SEQUENTIAL_CUTOFF) {
            float[][] dataaa = Land.getLand();
            for(int i=lo; i < hi; i++){
                int xOfTree = arr[i][0];
                int yOfTree = arr[i][1];
                int lengthOfTree = arr[i][2];

                int xr = xOfTree - lengthOfTree;
                int yr = yOfTree - lengthOfTree;
                float totalForOneTree = 0;

                for (int z = 0 ; z < lengthOfTree; z++) {
                    try {
                        for (int l = 0; l < lengthOfTree ;l++){
                            float sss = dataaa[xOfTree][yOfTree];
                            totalForOneTree += sss;
                            yOfTree++;}
                    }catch (ArrayIndexOutOfBoundsException e){}

                    yOfTree = yr;
                    xOfTree = xr + 1;
                    xr++;
                }
                  lengthOfTree = Math.round( lengthOfTree + totalForOneTree/1000);
                  Tree.setExt(lengthOfTree);
            }
            return 0.0;
        }
        else {
           Tread left = new Tread(arr,lo,(hi+lo)/2);
           Tread right= new Tread(arr,(hi+lo)/2,hi);

            left.fork();
           double rightAns = right.compute();
           double leftAns  = left.join();
            return 0.0;
        }
    }
}
