import observer.EventListener;

public class Writer implements Runnable, EventListener {    
    private EventManager eventManager;
    private boolean isEnd;
    
    public Writer(EventManager eventManager) {
        this.eventManager = eventManager;
        isEnd = false;
    }

    public void run() {
        while(!isEnd){ 
            synchronized (eventManager){
                eventManager.setDataOnInitialBuffer(new Data());
            }
            Utils.mimir(Constants.TIME_WRITERS.get()); 
        }
        System.out.println("Finalizo hilo Writer"+Thread.currentThread().getName());
    }

    @Override
    public void update(int dataProcessed) {
        isEnd = dataProcessed >= Constants.MAX_DATA_PROCESSED.get();   
    }
}

