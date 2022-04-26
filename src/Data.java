public class Data {
    private int reviews = 0;
    private int id;
    private int cantReviewers;

    public Data(){}
    
    public Data(int id, int cantReviewers){
        this.id = id;
        this.cantReviewers = cantReviewers;
    }

    //public synchronized void review(){
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
