import java.util.HashMap;
import java.util.LinkedList;

public class ConcurrencyController {
    HashMap<Object , Lock> lockTable;

    public void add(Object obj){
        Lock l1 = new Lock();
        lockTable.put(obj, l1);
    }

    public void lock_acquire(Object obj, Transactions t1){
        //re-order

        Lock required = lockTable.get(obj);
        required.acquire();


    }




}
