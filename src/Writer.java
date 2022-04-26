import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.Random;

import static java.lang.Math.abs;

public class Writer extends Master implements Runnable{

    //ArrayList<Data> ownBuffer = new ArrayList<Data>();
    Buffer ownBuffer;
    private final ReadWriteLock lock;
    int seed;
    int cantReviewers = 9; //TODO:CAMBIAR CANT REVIWERS. ADAPTAR WRITER AL MAIN
    private static int createdData = 0;


    public Writer(int minT, int maxT, Buffer bufferI, int seed){
        super(minT, maxT);
        ownBuffer=bufferI;
        lock = new ReentrantReadWriteLock();
        this.seed = seed;
    }

    //TODO: encontrar metodo que tire numeros random entre dos constantes y que no se repita


    @Override
    public void run(){
        for(int i=0; i<250; i++){
            //int numeroRandom = ThreadLocalRandom.current().nextInt(min, max);
            int randomDuration = abs((int) (Math.random()*(this.minT-this.maxT)) + this.minT);
            Data data = new Data(abs((new Random(seed+i)).nextInt()), cantReviewers);
            lock.writeLock().lock();
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


}
