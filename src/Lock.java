import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class Lock {
    LinkedList<Transactions> waiters;
    ReentrantLock lock;
    public void acquire(Transactions t1){
        if(lock.isLocked()) {
            waiters.add(t1);
            
            lock.wait();

        }




    }



}
