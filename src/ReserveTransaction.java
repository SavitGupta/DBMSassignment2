import java.util.ArrayList;

import javafx.util.Pair;

public class ReserveTransaction implements Runnable
{
	Flight flight;
	Passenger passenger;
	Database db;
	ConcurrencyController CCM;
	static Integer cnt = 1;
	int mycnt;
	
	public ReserveTransaction(String flight, String passenger, Database db, ConcurrencyController CCM)
	{
		this.flight = (Flight) db.getbyId(flight);
		this.passenger = (Passenger) db.getbyId(passenger);
		this.db = db;
		this.CCM = CCM;
		mycnt = cnt++;
	}
	
	public void run()
	{
		System.out.println("started reserve transaction " + mycnt);
		ArrayList<Pair<Integer, Lockables>> varsNeeded = new ArrayList<>();
		varsNeeded.add(new Pair<>(2, flight));
		varsNeeded.add(new Pair<>(2, passenger));
		// System.out.println("variables added " + mycnt);
		CCM.lock_acquire(varsNeeded);
		System.out.println("lock acquired for reserve transaction " + mycnt);
		flight.book_flight(passenger);
		System.out.println("flight booked " + mycnt);
		CCM.release(varsNeeded);
		System.out.println("ended reserve transaction " + mycnt);
	}
}