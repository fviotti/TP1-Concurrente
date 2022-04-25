/*
import java.util.Queue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
//import java.util.Random;

public class Writer extends Master implements Runnable{

    Queue<Data> ownBuffer;
    private final ReadWriteLock lock;


    public Writer(int minT, int maxT, Queue<Data> bufferI){
        super(minT, maxT);
        ownBuffer=bufferI;
        lock = new ReentrantReadWriteLock();
    }


    @Override
    public void run(){
        for(int i=0; i<250; i++){
            //int numeroRandom = ThreadLocalRandom.current().nextInt(min, max);
            int randomDuration = (int) (Math.random()*(this.minT-this.maxT)) + this.minT;
            Data data = new Data();
            lock.writeLock().lock();
            try {
                if (ownBuffer.size() <= 100) {
                    System.out.printf("%s esta agregando datos al buffer durante %d segundos", Thread.currentThread().getName(), randomDuration);
                    ownBuffer.add(data);
                    Thread.sleep(randomDuration);
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

//    public Queue<Data> addQueue(Queue<Data> bufferI, Data data){
//        bufferI.add(data);
//    }

    */
/*public void setData(Queue<Data> bufferI){
        ownBuffer = bufferI;
    }*//*

}
*/
