import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class App{
    public static void main(String[] args)   {
        EventManager eventManager = new EventManager();
        long inicio = System.currentTimeMillis();
        long fin = 0;

        //Creamos los arreglos que almacenan los creadores de contenido (size = 4)
        Writer writers[] = new Writer[Constants.WRITERS.get()];
        Thread threadWs[] = new Thread[Constants.WRITERS.get()];

        //Inicializmos los hilos correspondientes a los creadores de contenido
        for (int i = 0; i < Constants.WRITERS.get(); i++) {
            writers[i] = new Writer(eventManager);
            eventManager.subscribe(writers[i]); //nos suscribimos al observer
            threadWs[i] = new Thread(writers[i]);
            threadWs[i].start();
        }

        //Creamos los revisores de contenido
        Reviewer reviewers[] = new Reviewer[Constants.REVIEWERS.get()];
        Thread threadRs[] = new Thread[Constants.REVIEWERS.get()];
        
        for (int i = 0; i < Constants.REVIEWERS.get(); i++) {
            reviewers[i] = new Reviewer(eventManager);
            eventManager.subscribe(reviewers[i]); //nos suscribimos al observer
            threadRs[i] = new Thread(reviewers[i]);
            threadRs[i].start();
        }

         //Creamos los usuarios de contenido
         User users[] = new User[Constants.USERS.get()];
         Thread threadUs[] = new Thread[Constants.USERS.get()];
         
         for (int i = 0; i < Constants.USERS.get(); i++) {
            users[i] = new User(eventManager);
             eventManager.subscribe(users[i]); //nos suscribimos al observer
             threadUs[i] = new Thread(users[i]);
             threadUs[i].start();
         }

         //Logger
         try (FileWriter file = new FileWriter("log.txt");
                PrintWriter pw = new PrintWriter(file)) {
                boolean scan = true;
                while (scan) {
                    pw.printf("--------> ESTADO ACTUAL DE LA DATA <------- \n");
                    pw.printf("Ocupacion del Buffer Inicial :%d  \n", eventManager.getInitialBuffer().size());
                    pw.printf("Ocupacion del Buffer Final   :%d  \n",  eventManager.getValidatedBuffer().size());
                    pw.printf("Cantidad de datos procesados : %d \n",eventManager.getProcessedData());
                    pw.printf("**********************************************\n");

                    pw.printf("\n");
                    /*if (threadUs[0].getState() == Thread.State.TERMINATED && threadUs[1].getState() == Thread.State.TERMINATED){
                        scan = false;
                        fin = System.currentTimeMillis();
                    }*/
                    for (int i = 0; i < threadRs.length; i++) {
                        scan = scan && !(threadRs[i].getState() == Thread.State.TERMINATED);
                    }
                    fin = System.currentTimeMillis();
                    TimeUnit.MILLISECONDS.sleep(100);
            }
                pw.printf("TIEMPO TOTAL DE EJECUCIÓN "+(fin-inicio));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Esperamos que todo termine
        for (int i = 0; i < threadWs.length; i++) {
            try {
                threadWs[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < threadRs.length; i++) {
            try {
                threadRs[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < threadUs.length; i++) {
            try {
                threadUs[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            Thread.sleep(100);
        } catch (Exception e) {
            System.out.println("Error : "+e);
        }
        
        System.out.println("initialBuffer: " + eventManager.getInitialBuffer().size());
        System.out.println("validatedBuffer: " + eventManager.getValidatedBuffer().size());
        System.out.println("proccessedData: " + eventManager.getProcessedData());
        System.out.println("writtenData: " + eventManager.getWrittenData());
    }

}
