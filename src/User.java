import observer.EventListener;

public class User implements Runnable, EventListener {    
    private EventManager eventManager;
    private boolean isEnd;
 
    public User( EventManager eventManager ) {
        this.eventManager = eventManager;
        isEnd = false;
    }

    public void run() {
        while(!isEnd){ 
            synchronized (eventManager){
                Data data = eventManager.getDataOnValidatedBuffer();
                if(data != null){
                    if(!isEnd){
                            eventManager.removeDataOnInitialBuffer(data);
                            eventManager.removeDataOnValidatedBuffer(data);
                            eventManager.increment();
                            //tiempo en eliminar el contenido
                            Utils.mimir(Constants.TIME_USERS.get());
                    }
                }
            }
            Utils.mimir(1);
        }
        System.out.println("Finalizo hilo User "+Thread.currentThread().getName());
    }

    @Override
    public void update(int dataProcessed) {
        isEnd = dataProcessed >= Constants.MAX_DATA_PROCESSED.get();   
    }
 
}