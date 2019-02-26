import java.util.ArrayList;

public class reserve_transaction implements Runnable{
    Flight flight;
    Passenger passenger;
    Database db;
    ConcurrencyController CCM;

    static Integer cnt = 0;
    int mycnt;

    public reserve_transaction(String flight, String passenger, Database db, ConcurrencyController CCM){
        this.flight = (Flight)db.getbyId(flight);
        this.passenger = (Passenger)db.getbyId(passenger);
        this.db = db;
        this.CCM = CCM;
        mycnt = cnt ++;

    }
    public void run() {
        ArrayList<Lockables> varsNeeded = new ArrayList<>();
        varsNeeded.add(flight);
        varsNeeded.add(passenger);
        CCM.lock_acquire(varsNeeded);
        flight.book_flight(passenger);
        CCM.release(varsNeeded);
    }





}
