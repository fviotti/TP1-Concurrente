import java.util.ArrayList;

import observer.EventListener;

public class Reviewer implements Runnable, EventListener {
    ArrayList<Data> nextReview; 
    private boolean isEnd, isIn;
    private EventManager eventManager;

    public Reviewer( EventManager eventManager ) {
        nextReview = new ArrayList<>();
        isEnd = isIn = false;
        this.eventManager = eventManager;

    }

    public void run() {
        while(!isEnd){
            Data dataToReview = null;
            synchronized(eventManager.getInitialBuffer()){
                ArrayList<Data> bufferI = (eventManager.getInitialBuffer());

                if(!bufferI.isEmpty()){
                    for (Data data : bufferI) {
                        if(data != null && !isChecked(data)){
                            data.review();
                            eventManager.updateDataOnInitialBuffer(data);
                            isIn = true;
                            if(data.isReady()){
                                dataToReview = data;
                            }
                            nextReview.add(data);
                        }
                    }
                }
            }
            if(dataToReview != null){
                synchronized(eventManager.getValidatedBuffer()){
                    eventManager.setDataOnValidatedBuffer(dataToReview);
                }
            }
            if (isIn){
                isIn = false;
                Utils.mimir(Constants.TIME_REVIEWERS.get());
            } else{
                Utils.mimir(1);
            }

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