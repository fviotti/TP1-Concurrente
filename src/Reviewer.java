public class Reviewer extends Master implements Runnable {
    private int cantDatos;
    private Data data = null;
    private boolean stop = false;


    ArrayList<Data> iOwnBuffer;
    ArrayList<Data> vOwnBuffer;
    ReadWriteLock lock;

    public Reviewer(int minT, int maxT, ArrayList<Data> initialBuffer,  ArrayList<Data> finalBuffer){
        super(minT, maxT);
        iOwnBuffer = initialBuffer;
        vOwnBuffer = finalBuffer;
        lock = new ReentrantReadWriteLock();
    }


    public int getCantDatos(){
        return cantDatos;
    }

    public void run(){
        while(!stop){
            review();
        }

    }
}
