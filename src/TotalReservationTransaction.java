import java.util.ArrayList;
import java.util.Map.Entry;

import javafx.util.Pair;

public class TotalReservationTransaction implements Runnable
{
	Database db;
	ConcurrencyController CCM;
	static Integer cnt = 1;
	int mycnt;
	int type;
	
	public TotalReservationTransaction(Database db, ConcurrencyController CCM, int type)
	{
		this.db = db;
		this.CCM = CCM;
		mycnt = cnt++;
		this.type = type;
	}
	
	public void run()
	{
		// System.out.println("started total reservations transaction " + mycnt);
		ArrayList<Pair<Integer, Lockables>> varsNeeded = new ArrayList<>();
		ArrayList<Flight> flights = new ArrayList<Flight>();
		for (Entry<String, Lockables> item : db.items.entrySet())
		{
			if (item.getKey().charAt(0) == 'F')
			{
				flights.add((Flight) item.getValue());
			}
		}
		for (int i = 0; i < flights.size(); i++)
		{
			varsNeeded.add(new Pair<>(1, flights.get(i)));
		}
		if (this.type == 1)
		{
			CCM.lock_acquire(varsNeeded);
		}
		else
		{
			CCM.lockDatabase(1);
		}
		// System.out.println("lock acquired for total reservations transaction " + mycnt);
		int total_Res = 0;
		for (int i = 0; i < flights.size(); i++)
		{
			total_Res += (flights.get(i).capacity - flights.get(i).availablitiy);
		}
		// System.out.println("Total reservations are " + total_Res);
		if (this.type == 1)
		{
			CCM.release(varsNeeded);
		}
		else
		{
			CCM.releaseDatabase();
		}
		System.out.println("ended total reservations transaction " + mycnt);
	}
}