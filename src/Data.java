public class Data {
    private int reviews = 0;
    private int id;

    public Data(){}
    
    public Data(int id){
        this.id = id;
    }

    public synchronized void review(){
        reviews++;
    }

    public int getReviews(){
        return reviews;
    }

    public int getID(){
        return id;
    }
    
    public void setID(int id){
        this.id = id;
    }
}
