import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Passenger {


    ReentrantLock read_lock;
    ReentrantLock write_lock;

    String id;
    List<Passenger> passgeners;
}
