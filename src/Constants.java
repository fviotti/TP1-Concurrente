public enum Constants {
    WRITERS(4),
    REVIEWERS(2),
    USERS(2),
    TIME_WRITERS(35),
    TIME_REVIEWERS(2),
    TIME_USERS(3),
    LIMIT_BUFFER(100),
    MAX_DATA_PROCESSED(1000);

    private int cant;
    private Constants(int cant){
        this.cant = cant;
    }
    public int get(){ return cant; }
}
