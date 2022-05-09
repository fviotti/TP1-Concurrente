import observer.EventListener;

public class User implements Runnable, EventListener {    
    private final EventManager eventManager;
    private boolean isEnd;
    private boolean isIn;
    private int dataUsed;
 
    public User( EventManager eventManager ) {
        this.eventManager = eventManager;
        isEnd = false;
        isIn  = false;
    }

    public void run() {
        while(!isEnd){ 
            synchronized (eventManager){
                Data data = eventManager.getDataOnValidatedBuffer();
                if(data != null){
                    if(!isEnd){
                            isIn = true;
                            eventManager.removeDataOnInitialBuffer(data);
                            eventManager.removeDataOnValidatedBuffer(data);
                            eventManager.increment();
                            dataUsed++;
                            //Utils.mimir(Constants.TIME_USERS.get());
                    }
                }
            }
            if (isIn){
                isIn = false;
                Utils.mimir(Constants.TIME_USERS.get());
            }else{
                Utils.mimir(1);
            }
        }
        System.out.println("Finalizo hilo User "+Thread.currentThread().getName()+" con "+dataUsed+" datos consumidos." );
    }

    @Override
    public void update(int dataProcessed) {
        isEnd = dataProcessed >= Constants.MAX_DATA_PROCESSED.get();   
    }
 
}