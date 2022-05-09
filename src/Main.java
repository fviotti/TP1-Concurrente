import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;
/*
import java.util.LinkedList;
import java.util.Queue;
*/

public class Main {
    public static void main(String[] args) {

        //Recusos compartidos
        int processData = 0;
        // ArrayList<Data> bufferI = new ArrayList<>();
        // ArrayList<Data> bufferV = new ArrayList<>();
        Buffer bufferI = new Buffer(), bufferV = new Buffer();
        int cantReviewers = 9;
        //ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        
//        for (int i = 0; i < 1000; i++) {
//            bufferI.setData(new Data(i,cantReviewers));
//        }

        //Agentes
        Writer lisanDROSS = new Writer(10,30, bufferI,10);
        Writer laPepaPug = new Writer(12,24, bufferI,87);
        Writer marcelitoComunica = new Writer(11,32, bufferI,32);
        Writer vinchaDeLaLuisa = new Writer(8,17, bufferI,65);

        Reviewer afip = new Reviewer("pepe",31, bufferI, bufferV); //TODO: Ver si meto el lock en el master o donde
        Reviewer controlParental = new Reviewer("rick",25,  bufferI, bufferV);//TODO : tiene que estar compartido en los 3 actores
        Reviewer controlParental0 = new Reviewer("tutu",22,  bufferI, bufferV);
        Reviewer controlParental1 = new Reviewer("kaka",19,  bufferI, bufferV);
        Reviewer controlParental2 = new Reviewer("pito",32,  bufferI, bufferV);
        Reviewer controlParental3 = new Reviewer("puti",18,  bufferI, bufferV);
        Reviewer controlParental4 = new Reviewer("nest",14,  bufferI, bufferV);
        Reviewer controlParental5 = new Reviewer("jose",13,  bufferI, bufferV);
        Reviewer controlParental6 = new Reviewer("lola",51,  bufferI, bufferV);

        User stevenFranklin = new User(7, bufferI, bufferV);
        User elBrayatan = new User(18, bufferI, bufferV);

        Logger logger = new Logger();
        //

        //Creacion de hilos
        Thread tW1 = new Thread(lisanDROSS);
        Thread tW2 = new Thread(laPepaPug);
        Thread tW3 = new Thread(marcelitoComunica);
        Thread tW4 = new Thread(vinchaDeLaLuisa);

        Thread tR1 = new Thread(afip);
        Thread tR2 = new Thread(controlParental);
        Thread tR3 = new Thread(controlParental0);
        Thread tR4 = new Thread(controlParental1);
        Thread tR5 = new Thread(controlParental2);
        Thread tR6 = new Thread(controlParental3);
        Thread tR7 = new Thread(controlParental4);
        Thread tR8 = new Thread(controlParental5);
        Thread tR9 = new Thread(controlParental6);

        Thread tU1 = new Thread(stevenFranklin);
        Thread tU2 = new Thread(elBrayatan);

        Thread tLog = new Thread(logger);
        tLog.setPriority(Thread.MAX_PRIORITY);
        //

        //Lanzamiento de hilos
        tW1.start();
        tW2.start();
        tW3.start();
        tW4.start();
        tR1.start();
        tR2.start();
        tR3.start();
        tR4.start();
        tR5.start();
        tR6.start();
        tR7.start();
        tR8.start();
        tR9.start();
        tU1.start();
        tU2.start();
        tLog.start();
        //

        while(  !(tR1.getState() == Thread.State.TERMINATED)
                && !(tR2.getState() == Thread.State.TERMINATED)
                && !(tR3.getState() == Thread.State.TERMINATED)
                && !(tR4.getState() == Thread.State.TERMINATED)
                && !(tR5.getState() == Thread.State.TERMINATED)
                && !(tR6.getState() == Thread.State.TERMINATED)
                && !(tR7.getState() == Thread.State.TERMINATED)
                && !(tR8.getState() == Thread.State.TERMINATED)
                && !(tR9.getState() == Thread.State.TERMINATED)
                && !(tU1.getState() == Thread.State.TERMINATED)
                && !(tU2.getState() == Thread.State.TERMINATED)
                && !(tW1.getState() == Thread.State.TERMINATED)
                && !(tW2.getState() == Thread.State.TERMINATED)
                && !(tW3.getState() == Thread.State.TERMINATED)
                && !(tW4.getState() == Thread.State.TERMINATED)
            ){}
        try {
            Thread.sleep(1000); //TODO: PROBAR SIN JOIN Y SIN WHILE
            tR1.join();
            tR2.join();
            tR3.join();
            tR4.join();
            tR5.join();
            tR6.join();
            tR7.join();
            tR8.join();
            tR9.join();
            tW1.join();
            tW2.join();
            tW3.join();
            tW4.join();
            tU1.join();
            tU2.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Data data : bufferV.getAllBuffer()) {
            System.out.println("El dato |"+data.getID() + "| fue cargado y tiene ["+data.getReviews()+"] reviews ");
        }
        System.out.println(bufferV.size()+" verified buffer size");
        System.out.println(bufferI.size()+" initial buffer size");
        System.out.println(Reviewer.getProcessedData() + " Data proccessed");
        System.out.println(Writer.getCreatedData() + " Data created");
        System.out.println(User.getErasedData() + " Data erased");
        System.out.println(Reviewer.getLoadedData() + " Data loaded");
    }
}