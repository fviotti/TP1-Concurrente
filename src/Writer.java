import observer.EventListener;

public class Writer implements Runnable, EventListener {    
    private final EventManager eventManager;
    private boolean isEnd;
    int dataCreated = 0;

    
    public Writer(EventManager eventManager) {
        this.eventManager = eventManager;
        isEnd = false;
    }

    public void run() {
        while(!isEnd){ 
            synchronized (eventManager){
                eventManager.setDataOnInitialBuffer(new Data());
                dataCreated++;
            }
            Utils.mimir(Constants.TIME_WRITERS.get()); 
        }
        System.out.println("Finalizo hilo Writer "+Thread.currentThread().getName()+" con "+dataCreated+" datos creados");
    }

    @Override
    public void update(int dataProcessed) {
        isEnd = dataProcessed >= Constants.MAX_DATA_PROCESSED.get();   
    }
}