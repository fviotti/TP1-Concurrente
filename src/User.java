import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class User extends Master implements Runnable {
    Buffer vBuffer;
    Buffer iBuffer;
    Data usedData = null;
    ReadWriteLock lock;
    boolean stop = false;
    private static int erasedData = 0;


    public User(int time, Buffer vBuffer, Buffer iBuffer){
        super(time,time);
        this.iBuffer = iBuffer;
        this.vBuffer = vBuffer;
        lock = new ReentrantReadWriteLock();
    }
    public void cycle(){
        lock.writeLock().lock();
        if(usedData == null && !vBuffer.isEmpty()){
            usedData = vBuffer.getFirst();
        }
        try{
            if(vBuffer.contains(usedData) ) {
                vBuffer.deleteData(usedData);
                iBuffer.deleteData(usedData);
                erasedData++;
            }
            Thread.sleep(500);

            if (vBuffer.hasNext(usedData)) {
                usedData = vBuffer.next(usedData);
                if (usedData == null) this.stop = true;
            } else {
                this.stop = true;
            }
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        finally {
            lock.writeLock().unlock();
        }



    }


    @Override
    public void run(){
        while(!this.stop){cycle();}
    }

    public static int getErasedData(){
        return erasedData;
    }
}