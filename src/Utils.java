/**
 * Utils
 */
public class Utils {
    static long randomGen(int num){
        return (long) (Math.random() * num) + 1;
    }
    
    static void mimir(int miliseconds){
        try{
            Thread.sleep(Utils.randomGen(miliseconds));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
