public class SimulationClass extends  Thread{
    SunData object;

    public SimulationClass(SunData sun){

        object = sun;

    }

    public void run(){
        while(true){
            float maxh = 20.0f;
            float minh = 18.0f;
            for(int layer = 0; layer <= 10; layer++) {
                for(int i = 0; i < object.trees.length; i++){

                    object.trees[i].sungrow(object.sunmap);

                }
                maxh = minh;  // next band of trees
                minh -= 2.0f;
            }
        }
    }

}
