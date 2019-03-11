import java.util.ArrayList;

import javafx.util.Pair;

public class CancelTransaction implements Runnable
{
	String flightId;
	String passengerId;
	Database db;
	ConcurrencyController CCM;
	static Integer cnt = 1;
	int mycnt;
	int type;
	
	public CancelTransaction(String flight, String passenger, Database db, ConcurrencyController CCM, int type)
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
		System.out.println("started cancel transaction " + mycnt);
		ArrayList<Pair<Integer, Lockables>> varsNeeded = new ArrayList<>();
		Flight flight = (Flight) db.getbyId(flightId);
		Passenger passenger = (Passenger) db.getbyId(passengerId);
		varsNeeded.add(new Pair<>(2, flight));
		varsNeeded.add(new Pair<>(2, passenger));
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
		System.out.println("lock acquired for cancel transaction " + mycnt);
		flight.cancel_flight(passenger);
		System.out.println("flight canceled " + mycnt);
		if (this.type == 1)
		{
			CCM.release(varsNeeded);
		}
		else
		{
			CCM.releaseDatabase();
		}
		System.out.println("ended cancel transaction " + mycnt);
	}
}