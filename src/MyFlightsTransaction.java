import java.util.ArrayList;

import javafx.util.Pair;

public class MyFlightsTransaction implements Runnable
{
	Passenger passenger;
	Database db;
	ConcurrencyController CCM;
	static Integer cnt = 1;
	int mycnt;
	
	public MyFlightsTransaction(String passenger, Database db, ConcurrencyController CCM)
	{
		this.passenger = (Passenger) db.getbyId(passenger);
		this.db = db;
		this.CCM = CCM;
		mycnt = cnt++;
	}
	
	public void run()
	{
		System.out.println("started my_flight transaction " + mycnt);
		ArrayList<Pair<Integer, Lockables>> varsNeeded = new ArrayList<>();
		varsNeeded.add(new Pair<>(1, passenger));
		CCM.lock_acquire(varsNeeded);
		System.out.println("lock acquired for my_flight transaction " + mycnt);
		ArrayList<Flight> flights = passenger.get_flights();
		if (flights.size() != 0)
		{
			System.out.print("Passenger has following flights: ");
			for (int i = 0; i < flights.size() - 1; i++)
			{
				System.out.print(flights.get(i).id + " ");
			}
			System.out.println(flights.get(flights.size() - 1).id);
		}
		else
		{
			System.out.println("Passenger has no flights ");
		}
		CCM.release(varsNeeded);
		System.out.println("ended my_flight transaction " + mycnt);
	}
}