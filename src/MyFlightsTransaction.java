import java.util.ArrayList;

import javafx.util.Pair;

public class MyFlightsTransaction implements Runnable
{
	String passengerId;
	Database db;
	ConcurrencyController CCM;
	static Integer cnt = 1;
	int mycnt;
	int type;
	
	public MyFlightsTransaction(String passenger, Database db, ConcurrencyController CCM, int type)
	{
		passengerId = passenger;
		this.db = db;
		this.CCM = CCM;
		mycnt = cnt++;
		this.type = type;
	}
	
	public void run()
	{
		System.out.println("started my_flight transaction " + mycnt);
		Passenger passenger = (Passenger) db.getbyId(passengerId);
		ArrayList<Pair<Integer, Lockables>> varsNeeded = new ArrayList<>();
		varsNeeded.add(new Pair<>(1, passenger));
		if (this.type == 1)
		{
			CCM.lock_acquire(varsNeeded);
		}
		else
		{
			CCM.lockDatabase(1);
		}
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
		if (this.type == 1)
		{
			CCM.release(varsNeeded);
		}
		else
		{
			CCM.releaeDatabase();
		}
		System.out.println("ended my_flight transaction " + mycnt);
	}
}