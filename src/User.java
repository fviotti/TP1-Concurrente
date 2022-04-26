public class User extends Master implements Runnable {


    public User(int time, Buffer vBuffer, Buffer iBuffer){
        super(time,time);
        this.iBuffer = iBuffer;
        this.vBuffer = vBuffer;
        lock = new ReentrantReadWriteLock();
    }

    public void run(){

    }
}