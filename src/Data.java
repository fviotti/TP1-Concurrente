public class Data {
    private int reviews = 0;
    public int id;

    public Data(int id){
        this.id = id;
    }

    public void review(){
        reviews++;
    }

    public int getReviews(){
        return reviews;
    }
}
