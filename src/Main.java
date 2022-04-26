import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        //Recusos compartidos
        Buffer bufferI = new Buffer();
        Buffer bufferV = new Buffer();
        int cantReviewers = 2;

        //Agentes
        Writer W1 = new Writer(10,30, bufferI,10, cantReviewers);
        Writer W2 = new Writer(12,24, bufferI,87, cantReviewers);
        Writer W3 = new Writer(11,32, bufferI,32, cantReviewers);
        Writer W4 = new Writer(8,17, bufferI,65, cantReviewers);

        Reviewer R1 = new Reviewer("pepe",31, bufferI, bufferV); //TODO: Ver si meto el lock en el master o donde
        Reviewer R2 = new Reviewer("rick",25,  bufferI, bufferV);//TODO : tiene que estar compartido en los 3 actores

        User U1 = new User(7, bufferI, bufferV);
        User U2 = new User(18, bufferI, bufferV);

        //Creacion de hilos
        Thread tW1 = new Thread(W1);
        Thread tW2 = new Thread(W2);
        Thread tW3 = new Thread(W3);
        Thread tW4 = new Thread(W4);

        Thread tR1 = new Thread(R1);
        Thread tR2 = new Thread(R2);

        Thread tU1 = new Thread(U1);
        Thread tU2 = new Thread(U2);

        //Lanzamiento de hilos
        tW1.start();
        tW2.start();
        tW3.start();
        tW4.start();
        tR1.start();
        tR2.start();
        tU1.start();

        try (FileWriter file = new FileWriter("./data/log.txt");
             PrintWriter pw = new PrintWriter(file)) {
            boolean finish = false;
            while (!finish) {
                pw.printf("--------> ESTADO ACTUAL DE LA DATA <------- \n");
                pw.printf("Ocupación del “Buffer Inicial” %d:  \n", bufferI.size());
                pw.printf("Ocupación del “Buffer Final” %d:  \n", bufferV.size());
                pw.printf("Cantidad de datos procesados : %d \n",Reviewer.getLoadedData());
                pw.printf("**********************************************\n");

                pw.printf("\n");
                finish = (tR1.getState()==Thread.State.TERMINATED
                        && tR2.getState()==Thread.State.TERMINATED
                );
                TimeUnit.SECONDS.sleep(2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}