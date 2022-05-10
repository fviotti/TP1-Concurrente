import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import observer.EventListener;

public class EventManager {
    private int dataProcessed, dataWritten;
    private ArrayList<Data> initialBuffer;
    private ArrayList<Data> validatedBuffer;
    List<EventListener> listeners;
    // private ReadWriteLock lockDataProcessed, lockInitialB, lockValidatedB;

    public EventManager() {
        initialBuffer = new ArrayList<>();   // Buffer inicial
        validatedBuffer = new ArrayList<>(); //Buffer de validados
        dataProcessed = 0;
        dataWritten = 0;
        listeners = new LinkedList<>();
        // lockDataProcessed = new ReentrantReadWriteLock();
        // lockInitialB = new ReentrantReadWriteLock(true);
        // lockValidatedB = new ReentrantReadWriteLock();
    }

    public void subscribe(EventListener listener) {
        listeners.add(listener);
    }

   /*
        Aumenta la data procesada
    */
    public void increment(){
        // lockDataProcessed.writeLock().lock();
        dataProcessed++;
        notify(dataProcessed);
        // lockDataProcessed.writeLock().unlock();
    }

    private void notify(int dataProcessed) {
        for (EventListener listener : listeners) {
            listener.update(dataProcessed); //Llama el método update de cada elemento del arreglo listener
        }
    }

    //////////// Fin metodos del observer /////////////

    public int getProcessedData(){
        //lockDataProcessed.readLock().lock();
        int dataP = dataProcessed;
        //lockDataProcessed.readLock().unlock();
        return dataP;
    }
    public int getWrittenData(){
        //lockDataProcessed.readLock().lock();
        int dataW = dataWritten;
        //lockDataProcessed.readLock().unlock();
        return dataW;
    }

    // Manejo del buffer inicial
    public ArrayList<Data> getInitialBuffer(){
        ArrayList<Data> datita;
        //lockInitialB.readLock().lock();
        datita = initialBuffer;
        //lockInitialB.readLock().unlock();
        return datita;
    }
    public void setDataOnInitialBuffer(Data data){
        // lockInitialB.writeLock().lock();
        if(initialBuffer.size() < Constants.LIMIT_BUFFER.get()){
            initialBuffer.add(data);
            dataWritten++;
        }
        // lockInitialB.writeLock().unlock();
    }
    public void updateDataOnInitialBuffer(Data data){
        //lockInitialB.writeLock().lock();
        initialBuffer.set(initialBuffer.indexOf(data), data);
        //lockInitialB.writeLock().unlock();
    }
    public void removeDataOnInitialBuffer(Data data){
        // lockInitialB.writeLock().lock();
        initialBuffer.remove(data);
        // lockInitialB.writeLock().unlock();
    }

    public ArrayList<Data> getValidatedBuffer(){
        ArrayList<Data> datita;
        //lockValidatedB.readLock().lock();
        datita = validatedBuffer;
        //lockValidatedB.readLock().unlock();
        return datita;
    }
    public void setDataOnValidatedBuffer(Data data){
        // lockValidatedB.writeLock().lock();
            if(validatedBuffer.size() < Constants.LIMIT_BUFFER.get()){
                validatedBuffer.add(data);
            }
        // lockValidatedB.writeLock().unlock();
    }
    public void removeDataOnValidatedBuffer(Data data){
        // lockValidatedB.writeLock().lock();
        validatedBuffer.remove(data);
        // lockValidatedB.writeLock().unlock();
    }
    public Data getDataOnValidatedBuffer(){
        Data data;
        //lockValidatedB.writeLock().lock();
        if(!validatedBuffer.isEmpty())
            data = validatedBuffer.get(0);
        else 
            data = null;
        //lockValidatedB.writeLock().unlock();
        return data;
    }
}
