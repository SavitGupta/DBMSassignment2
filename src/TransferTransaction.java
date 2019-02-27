import java.util.ArrayList;

import javafx.util.Pair;

public class TransferTransaction implements Runnable
{
	Passenger passenger;
	Flight flight1;
	Flight flight2;
	Database db;
	ConcurrencyController CCM;
	static Integer cnt = 1;
	int mycnt;
	int type;
	
	public TransferTransaction(String flight1, String flight2, String passenger, Database db, ConcurrencyController CCM, int type)
	{
		this.passenger = (Passenger) db.getbyId(passenger);
		this.flight1 = (Flight) db.getbyId(flight1);
		this.flight2 = (Flight) db.getbyId(flight2);
		this.db = db;
		this.CCM = CCM;
		mycnt = cnt++;
		this.type = type;
	}
	
	public void run()
	{
		System.out.println("started transfer transaction " + mycnt);
		ArrayList<Pair<Integer, Lockables>> varsNeeded = new ArrayList<>();
		varsNeeded.add(new Pair<>(2, passenger));
		varsNeeded.add(new Pair<>(2, flight1));
		varsNeeded.add(new Pair<>(2, flight2));
		if (this.type == 1)
		{
			CCM.lock_acquire(varsNeeded);
		}
		else
		{
			CCM.lockDatabase(2);
		}
		System.out.println("lock acquired for transfer transaction " + mycnt);
		passenger.transfer(flight1, flight2);
		System.out.println("Transfer completed");
		if (this.type == 1)
		{
			CCM.release(varsNeeded);
		}
		else
		{
			CCM.releaeDatabase();
		}
		System.out.println("ended transfer transaction " + mycnt);
	}
}