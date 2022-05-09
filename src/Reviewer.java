import java.util.ArrayList;

import observer.EventListener;

public class Reviewer implements Runnable, EventListener {
    ArrayList<Data> nextReview; 
    private boolean isEnd;
    private EventManager eventManager;

    public Reviewer( EventManager eventManager ) {
        nextReview = new ArrayList<>();
        isEnd = false;
        this.eventManager = eventManager;
    }

    public void run() {
        while(!isEnd){
            synchronized(eventManager){
            ArrayList<Data> bufferI = (eventManager.getInitialBuffer());

            if(!bufferI.isEmpty()){
                for (Data data : bufferI) {
                    if(data != null && !isChecked(data)){
                        data.review();
                        eventManager.updateDataOnInitialBuffer(data);
                        
                        if(data.isReady()){
                            eventManager.setDataOnValidatedBuffer(data);
                        }
                        nextReview.add(data);
                        Utils.mimir(Constants.REVIEWERS.get());
                    }
                }
            }
            }
            Utils.mimir(1);
        }
        System.out.println("\nFinalizo hilo Reviewer " 
                            + Thread.currentThread().getName() 
                            + " con " + nextReview.size() + " Revisiones");
    }

    private boolean isChecked(Data data){
        for (Data dataCheck : nextReview) {
            if(data == dataCheck) return true;
        }
        return false;
    }

    @Override
    public void update(int dataProcessed) {
        isEnd = dataProcessed >= Constants.MAX_DATA_PROCESSED.get();
    }
 
}