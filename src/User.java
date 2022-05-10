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
            Data data = null;
            synchronized (eventManager.getValidatedBuffer()){
                data = eventManager.getDataOnValidatedBuffer();
                if(data != null && !isEnd){
                    isIn = true;
                    eventManager.removeDataOnValidatedBuffer(data);
                    
                }
            }
            if (isIn){
                synchronized (eventManager.getInitialBuffer()){
                    eventManager.removeDataOnInitialBuffer(data);
                    eventManager.increment();
                    dataUsed++;
                }
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