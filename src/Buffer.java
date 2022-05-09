import java.util.ArrayList;

public class Buffer {
    private ArrayList<Data> buffer;

    public Buffer(){
        buffer = new ArrayList<>();
    }

    public int size() {return buffer.size();}

    //public synchronized void setData(Data data){
    public void setData(Data data){
        if(buffer.size() < 1000){
            buffer.add(data);
        }
    }

    private Data getDataByIndex(int index){
        return buffer.get(index);
    }

    public Data getIndexOf(Data data){
        return getDataByIndex(buffer.indexOf(data));
    }

    public boolean isEmpty(){
        return buffer.isEmpty();
    }

    public boolean hasNext(Data data){
        return (buffer.indexOf(data)+1) < buffer.size();
    }

    public Data next(Data data){
        if(hasNext(data)){
            return getDataByIndex(buffer.indexOf(data)+1);
        }
        return null;
    }

    public Data getFirst(){
        return buffer.get(0);
    }

    public ArrayList<Data> getAllBuffer(){
        return buffer;
    }

    public boolean contains(Data data){
        return buffer.contains(data);
    }
    public synchronized void deleteData(Data data){
        buffer.remove(data);
    }
}
