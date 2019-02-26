import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Passenger extends Lockables{
    String id;
    ArrayList<Flight> flights;
    public Passenger(String id, Database db) {
        super("P", id);
        this.id = id;
        this.flights = new ArrayList<>();
        db.add(this);
    }

    public void add_flight(Flight flight){
        this.flights.add(flight);

    }
}
