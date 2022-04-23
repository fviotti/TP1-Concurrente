public class Reviewer extends Master implements Runnable {
    private int cantDatos;
    private Data data;

    public Reviewer(int minT, int maxT){
        super(minT, maxT);
    }

    public void setData(Data data){

    }

    public Data getData(){
        return data;
    }

    public int getCantDatos(){
        return cantDatos;
    }

    public void run(){

    }
}
