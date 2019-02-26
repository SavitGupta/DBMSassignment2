import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class ConcurrencyController {
    HashMap<Lockables , Lock> lockTable;

    public void add(Database db){
        for (Lockables item: db.items.entrySet()) {
            Lock l1 = new Lock();
            lockTable.put(item, l1);
        }
    }




    public void lock_acquire(ArrayList<Lockables> toLock){
        //re-order
        toLock.sort(new LockableCompator_id());
        for (Lockables item: toLock) {
            lockTable.get(item).acquire();
        }
    }

    public void release(ArrayList<Lockables> toRelease){
        for (Lockables item: toRelease) {
            lockTable.get(item).release();
        }
    }



}
