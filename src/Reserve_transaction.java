import java.util.ArrayList;

public class Reserve_transaction implements Runnable
{
	Flight flight;
	Passenger passenger;
	Database db;
	ConcurrencyController CCM;
	static Integer cnt = 0;
	int mycnt;
	
	public Reserve_transaction(String flight, String passenger, Database db, ConcurrencyController CCM)
	{
		this.flight = (Flight) db.getbyId(flight);
		this.passenger = (Passenger) db.getbyId(passenger);
		this.db = db;
		this.CCM = CCM;
		mycnt = cnt++;
	}
	
	public void run()
	{
		System.out.println("started " + mycnt);
		ArrayList<Lockables> varsNeeded = new ArrayList<>();
		varsNeeded.add(flight);
		varsNeeded.add(passenger);
		System.out.println("variables added " + mycnt);
		CCM.lock_acquire(varsNeeded);
		System.out.println("lock acquired " + mycnt);
		flight.book_flight(passenger);
		System.out.println("flight booked " + mycnt);
		CCM.release(varsNeeded);
		System.out.println("ended " + mycnt);
	}
}
