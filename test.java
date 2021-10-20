package os;
import java.lang.Thread;
import os.Counter;

public class test{
    public static int NumThreads = 100;
    public static int NumIters = 1000000;
    public static void main(String[] args) {
        Counter c = new Counter();
        Thread[] t = new Thread[NumThreads];
        for(int i = 0; i < NumThreads;i++){
            t[i] = new Thread(new DoSomething(c));
            t[i].start();
        }
        for(int i = 0; i < NumThreads; i++){
            try {
                t[i].join(0);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println(c.Value());
    }
    
}

class DoSomething implements Runnable{
    public void run(){
        for(int i = 0; i < test.NumIters; i++){
            c.Increment();
        }
    }
    public DoSomething(Object o){
        this.c = (Counter)o;
    }
    public Counter c;
}
