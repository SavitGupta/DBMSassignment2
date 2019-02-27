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
	
	public TransferTransaction(String flight1, String flight2, String passenger, Database db, ConcurrencyController CCM)
	{
		this.passenger = (Passenger) db.getbyId(passenger);
		this.flight1 = (Flight) db.getbyId(flight1);
		this.flight2 = (Flight) db.getbyId(flight2);
		this.db = db;
		this.CCM = CCM;
		mycnt = cnt++;
	}
	
	public void run()
	{
		System.out.println("started transfer transaction " + mycnt);
		ArrayList<Pair<Integer, Lockables>> varsNeeded = new ArrayList<>();
		varsNeeded.add(new Pair<>(2, passenger));
		varsNeeded.add(new Pair<>(2, flight1));
		varsNeeded.add(new Pair<>(2, flight2));
		CCM.lock_acquire(varsNeeded);
		System.out.println("lock acquired for transfer transaction " + mycnt);
		passenger.transfer(flight1, flight2);
		System.out.println("Transfer completed");
		CCM.release(varsNeeded);
		System.out.println("ended transfer transaction " + mycnt);
	}
}