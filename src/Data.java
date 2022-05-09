import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Data {
    private int reviews;
    private ReadWriteLock lock;

    public Data(){
        reviews = 0;
        lock = new ReentrantReadWriteLock();
    }
    
    void review() {
        lock.writeLock().lock();
        reviews++;
        lock.writeLock().unlock();
    }

    /*int reviews(){
        lock.readLock().lock();
        int revs = reviews;
        lock.readLock().unlock();
        return revs;
    }*/


    boolean isReady(){
        lock.readLock().lock();
        boolean isReady = Constants.REVIEWERS.get() == reviews;
        lock.readLock().unlock();
        return isReady;
    }
}
