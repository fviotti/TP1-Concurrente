import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Reviewer extends Master implements Runnable {
    //private int cantDatos;
    private Data dataReview = null;
    private boolean stop = false;
    private String name;
    Buffer iOwnBuffer;
    Buffer vOwnBuffer;
    ReadWriteLock lock;

    public Reviewer(String name, int timeReview, Buffer initialBuffer, Buffer finalBuffer) {
        super(timeReview, timeReview);
        this.name = name;
        this.iOwnBuffer = initialBuffer;
        this.vOwnBuffer = finalBuffer;
        lock = new ReentrantReadWriteLock();
    }

    /*public int getCantDatos() {
        return this.cantDatos;
    }*/

//    synchronized public void review() {
    public void review() {
        try {
            lock.writeLock().lock();
            if (dataReview == null && !this.iOwnBuffer.isEmpty()) {
                dataReview = iOwnBuffer.getFirst();
            }
            if(dataReview != null){
                dataReview.review();
                processedData++;
                if(dataReview.isVerified() && !vOwnBuffer.contains(dataReview)){
//                System.out.println(name+" agrego el dato "+dataReview.getID()+" agregado a Buffer final");
                    vOwnBuffer.setData(dataReview);
                    loadedData++;

//                System.out.println("Buffer final size: "+vOwnBuffer.size());
                }
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally{
            lock.writeLock().unlock();
        }
        if (iOwnBuffer.hasNext(dataReview)) {
            dataReview = iOwnBuffer.next(dataReview);
            if(dataReview == null) this.stop = true;
        } else {
            this.stop = true;
        }

    }

    public void run() {
        while(!this.stop) {
            this.review();
        }
    }

    static public int getProcessedData(){
        return processedData;
    }
    static public int getLoadedData(){
        return processedData;
    }
}