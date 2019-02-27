import java.util.ArrayList;

import javafx.util.Pair;

public class ReserveTransaction implements Runnable
{
	String flightId;
	String passengerId;
	Database db;
	ConcurrencyController CCM;
	static Integer cnt = 1;
	int mycnt;
	int type;
	
	public ReserveTransaction(String flight, String passenger, Database db, ConcurrencyController CCM, int type)
	{
		flightId = flight;
		passengerId = passenger;
		this.db = db;
		this.CCM = CCM;
		mycnt = cnt++;
		this.type = type;
	}
	
	public void run()
	{
		// System.out.println("started reserve transaction " + mycnt);
		ArrayList<Pair<Integer, Lockables>> varsNeeded = new ArrayList<>();
		Flight flight = (Flight) db.getbyId(flightId);
		Passenger passenger = (Passenger) db.getbyId(passengerId);
		varsNeeded.add(new Pair<>(2, flight));
		varsNeeded.add(new Pair<>(2, passenger));
		// System.out.println("variables added " + mycnt);
		if (this.type == 1)
		{
			CCM.lock_acquire(varsNeeded);
		}
		else
		{
			CCM.lockDatabase(2);
		}
		flight = (Flight) db.getbyId(flightId);
		passenger = (Passenger) db.getbyId(passengerId);
		// System.out.println("lock acquired for reserve transaction " + mycnt);
		flight.book_flight(passenger);
		// System.out.println("flight booked " + mycnt);
		if (this.type == 1)
		{
			CCM.release(varsNeeded);
		}
		else
		{
			CCM.releaseDatabase();
		}
		// System.out.println("ended reserve transaction " + mycnt);
	}
}