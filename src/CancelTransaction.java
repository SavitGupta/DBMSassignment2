import java.util.ArrayList;

import javafx.util.Pair;

public class CancelTransaction implements Runnable
{
	Flight flight;
	Passenger passenger;
	Database db;
	ConcurrencyController CCM;
	static Integer cnt = 1;
	int mycnt;
	
	public CancelTransaction(String flight, String passenger, Database db, ConcurrencyController CCM)
	{
		this.flight = (Flight) db.getbyId(flight);
		this.passenger = (Passenger) db.getbyId(passenger);
		this.db = db;
		this.CCM = CCM;
		mycnt = cnt++;
	}
	
	public void run()
	{
		System.out.println("started cancel transaction " + mycnt);
		ArrayList<Pair<Integer, Lockables>> varsNeeded = new ArrayList<>();
		varsNeeded.add(new Pair<>(2, flight));
		varsNeeded.add(new Pair<>(2, passenger));
		CCM.lock_acquire(varsNeeded);
		System.out.println("lock acquired for cancel transaction " + mycnt);
		flight.cancel_flight(passenger);
		System.out.println("flight canceled " + mycnt);
		CCM.release(varsNeeded);
		System.out.println("ended cancel transaction " + mycnt);
	}
}