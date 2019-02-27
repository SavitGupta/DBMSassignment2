import java.util.ArrayList;
import java.util.Map.Entry;

import javafx.util.Pair;

public class TotalReservationTransaction implements Runnable
{
	ArrayList<Flight> flights;
	Database db;
	ConcurrencyController CCM;
	static Integer cnt = 1;
	int mycnt;
	
	public TotalReservationTransaction(Database db, ConcurrencyController CCM)
	{
		this.db = db;
		this.CCM = CCM;
		mycnt = cnt++;
		this.flights = new ArrayList<Flight>();
		for (Entry<String, Lockables> item : db.items.entrySet())
		{
			if (item.getKey().charAt(0) == 'F')
			{
				flights.add((Flight) item.getValue());
			}
		}
	}
	
	public void run()
	{
		System.out.println("started total reservations transaction " + mycnt);
		ArrayList<Pair<Integer, Lockables>> varsNeeded = new ArrayList<>();
		for (int i = 0; i < flights.size(); i++)
		{
			varsNeeded.add(new Pair<>(1, flights.get(i)));
		}
		CCM.lock_acquire(varsNeeded);
		System.out.println("lock acquired for total reservations transaction " + mycnt);
		int total_Res = 0;
		for (int i = 0; i < flights.size(); i++)
		{
			total_Res += (flights.get(i).capacity - flights.get(i).availablitiy);
		}
		System.out.println("Total reservations are " + total_Res);
		CCM.release(varsNeeded);
		System.out.println("ended total reservations transaction " + mycnt);
	}
}