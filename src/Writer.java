import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.Random;

import static java.lang.Math.abs;

public class Writer extends Master implements Runnable{

    ArrayList<Data> ownBuffer = new ArrayList<Data>();
    private final ReadWriteLock lock;
    int seed;


    public Writer(int minT, int maxT, ArrayList<Data> bufferI, int seed){
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
            Data data = new Data(abs((new Random(seed+i)).nextInt()));
            lock.writeLock().lock();
            try {
                if (ownBuffer.size() < 100) {
                    //System.out.printf("%s esta agregando datos al buffer durante %d segundos \n", Thread.currentThread().getName(), randomDuration);
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


}
