import java.util.ArrayList;
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
        
        for (int i = 0; i < 100; i++) {
            bufferI.setData(new Data(i));
        }


        //Agentes
        /*Writer lisanDROSS = new Writer(10,30, bufferI);
        Writer laPepaPug = new Writer(12,24, bufferI);
        Writer marcelitoComunica = new Writer(11,32, bufferI);
        Writer vinchaDeLaLuisa = new Writer(8,17, bufferI);*/

        Reviewer afip = new Reviewer("pepe",31, bufferI, bufferV);
        Reviewer controlParental = new Reviewer("rick",25,  bufferI, bufferV);
        Reviewer controlParental0 = new Reviewer("tutu",22,  bufferI, bufferV);
        Reviewer controlParental1 = new Reviewer("kaka",19,  bufferI, bufferV);
        Reviewer controlParental2 = new Reviewer("pito",32,  bufferI, bufferV);
        Reviewer controlParental3 = new Reviewer("puti",18,  bufferI, bufferV);
        Reviewer controlParental4 = new Reviewer("nest",14,  bufferI, bufferV);
        Reviewer controlParental5 = new Reviewer("jose",13,  bufferI, bufferV);
        Reviewer controlParental6 = new Reviewer("lola",51,  bufferI, bufferV);

        User stevenFranklin = new User(7, 14);
        User elBrayatan = new User(10, 18);

        //Logger logger = new Logger();
        //

        //Creacion de hilos
        /*Thread tW1 = new Thread(lisanDROSS);
        Thread tW2 = new Thread(laPepaPug);
        Thread tW3 = new Thread(marcelitoComunica);
        Thread tW4 = new Thread(vinchaDeLaLuisa);*/

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

        //Thread tLog = new Thread(logger);
        //

        //Lanzamiento de hilos
        /*tW1.start();
        tW2.start();
        tW3.start();
        tW4.start();*/
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
        //tLog.start();
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
            ){}
        try {
            Thread.sleep(1000);
            tR1.join();
            tR2.join();
            tR3.join();
            tR4.join();
            tR5.join();
            tR6.join();
            tR7.join();
            tR8.join();
            tR9.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Data data : bufferI.getAllBuffer()) {
            System.out.println("El dato |"+data.getID() + "| fue revisada ["+data.getReviews()+"] veces");
        }
    }
}