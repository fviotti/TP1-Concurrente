import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.Random;

import static java.lang.Math.abs;

public class Writer extends Master implements Runnable{


    Buffer ownBuffer;
    private final ReadWriteLock lock;
    int seed;
    int cantReviewers;
    private static int createdData = 0;


    public Writer(int minT, int maxT, Buffer bufferI, int seed, int cantReviewers){
        super(minT, maxT);
        ownBuffer=bufferI;
        lock = new ReentrantReadWriteLock();
        this.seed = seed;
        this.cantReviewers = cantReviewers;
    }




    @Override
    public void run(){
        for(int i=0; i<250; i++){
            //int numeroRandom = ThreadLocalRandom.current().nextInt(min, max);
            int randomDuration = abs((int) (Math.random()*(this.minT-this.maxT)) + this.minT);
            Data data = new Data(abs((new Random(seed+i)).nextInt()), cantReviewers);
            lock.writeLock().lock();
            setData();
            try {
                if (ownBuffer.size() < 100) {
                    //System.out.printf("%s esta agregando datos al buffer durante %d segundos \n", Thread.currentThread().getName(), randomDuration);
                    ownBuffer.setData(data);
                    Thread.sleep(50);
                }
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
            finally {
                lock.writeLock().unlock();
            }
        }
    }
    public static int getCreatedData(){
        return createdData;
    }

    public synchronized void setData(){
        createdData++;
    }
}
