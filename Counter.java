package os;

public class Counter {
    private int count;

    public int Value(){
        return count;
    }
    public Counter(){
        count = 0;
    }
    public synchronized void Increment(){
        count += 1;
    }
}
