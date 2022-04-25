import java.util.ArrayList;
/*
import java.util.LinkedList;
import java.util.Queue;
*/

public class Main {
    public static void main(String[] args) {

        //Recusos compartidos
        int processData = 0;
        ArrayList<Data> bufferI = new ArrayList<>();
        ArrayList<Data> bufferV = new ArrayList<>();
        bufferI.add(new Data(0));
        bufferI.add(new Data(1));
        bufferI.add(new Data(2));
        bufferI.add(new Data(3));



        //Agentes
        /*Writer lisanDROSS = new Writer(10,30, bufferI);
        Writer laPepaPug = new Writer(12,24, bufferI);
        Writer marcelitoComunica = new Writer(11,32, bufferI);
        Writer vinchaDeLaLuisa = new Writer(8,17, bufferI);*/

        Reviewer afip = new Reviewer(13,16, bufferI, bufferV);
        Reviewer controlParental = new Reviewer(9,11, bufferI, bufferV);

        User stevenFranklin = new User(7,14);
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
        tU1.start();
        tU2.start();
        //tLog.start();
        //

    }
}
