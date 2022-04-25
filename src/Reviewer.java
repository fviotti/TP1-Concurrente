//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Reviewer extends Master implements Runnable {
    private int cantDatos;
    private Data data = null;
    private boolean stop = false;
    ArrayList<Data> iOwnBuffer;
    ArrayList<Data> vOwnBuffer;
    ReadWriteLock lock;

    public Reviewer(int minT, int maxT, ArrayList<Data> initialBuffer, ArrayList<Data> finalBuffer) {
        super(minT, maxT);
        this.iOwnBuffer = initialBuffer;
        this.vOwnBuffer = finalBuffer;
        this.lock = new ReentrantReadWriteLock();
    }

    public int getCantDatos() {
        return this.cantDatos;
    }

    public void review() {
        if (this.data == null && !this.iOwnBuffer.isEmpty()) {
            this.data = (Data)this.iOwnBuffer.get(0);
        }

        Data tempData = (Data)this.iOwnBuffer.get(this.iOwnBuffer.indexOf(this.data));
        tempData.review();
        int var10001 = tempData.getID();
        System.out.printf("ID" + var10001 + "-> " + ((Data)this.iOwnBuffer.get(this.iOwnBuffer.indexOf(this.data))).getReviews() + "\n");
        if (this.iOwnBuffer.indexOf(this.data) + 1 < this.iOwnBuffer.size()) {
            this.data = (Data)this.iOwnBuffer.get(this.iOwnBuffer.indexOf(this.data) + 1);
        } else {
            this.stop = true;
        }

    }

    public void run() {
        while(!this.stop) {
            this.review();
        }

    }
}
