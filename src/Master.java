public abstract class Master {
    private int id;
    protected int minT;
    protected int maxT;

    public Master(){

    }
    public Master(int minT, int maxT){
        this.minT = minT;
        this.maxT = maxT;
    }



    protected int getID(){
        return id;
    }
}
