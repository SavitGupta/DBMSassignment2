import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainClass
{
	public static int RandInt(int a, int b)
	{
		Random rand = new Random();
		int randomNum = rand.nextInt(b + 1 - a) + a;
		return randomNum;
	}
	
	public static void generate_Database(int items, Database db, ArrayList<Flight> flights, ArrayList<Passenger> passengers)
	{
		for (int i = 0; i < items / 3; i++)
		{
			flights.add(new Flight("F" + (i + 1), RandInt(items / 10, (items / 10) + 10), db));
		}
		for (int i = 0; i < (2 * (items)) / 3; i++)
		{
			passengers.add(new Passenger("P" + (i + 1), db));
		}
	}
	
	public static void generate_Transactions(int num, Database db, ArrayList<Flight> flights, ArrayList<Passenger> passengers, ArrayList<Runnable> transactions, ConcurrencyController ccm)
	{
		for (int i = 0; i < num; i++)
		{
			int pos = RandInt(1, 5);
			if (pos == 1)
			{
				transactions.add(new TransferTransaction(flights.get(RandInt(0, flights.size() - 1)).id, flights.get(RandInt(0, flights.size() - 1)).id,
						passengers.get(RandInt(0, passengers.size() - 1)).id, db, ccm));
			}
			else if (pos == 2)
			{
				transactions.add(new ReserveTransaction(flights.get(RandInt(0, flights.size() - 1)).id, passengers.get(RandInt(0, passengers.size() - 1)).id, db, ccm));
			}
			else if (pos == 2)
			{
				transactions.add(new MyFlightsTransaction(passengers.get(RandInt(0, passengers.size() - 1)).id, db, ccm));
			}
			else if (pos == 2)
			{
				transactions.add(new CancelTransaction(flights.get(RandInt(0, flights.size() - 1)).id, passengers.get(RandInt(0, passengers.size() - 1)).id, db, ccm));
			}
			else
			{
				transactions.add(new TotalReservationTransaction(db, ccm));
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		Database db = new Database();
		ConcurrencyController ccm = new ConcurrencyController();
		ArrayList<Flight> flights = new ArrayList<Flight>();
		ArrayList<Passenger> passengers = new ArrayList<Passenger>();
		ArrayList<Runnable> transactions = new ArrayList<Runnable>();
		int num_items = 60;
		int num_trans = 1000;
		generate_Database(num_items, db, flights, passengers);
		ccm.add(db);
		generate_Transactions(num_trans, db, flights, passengers, transactions, ccm);
		ExecutorService exec = Executors.newFixedThreadPool(num_trans + 5);
		for (int i = 0; i < num_trans; i++)
		{
			exec.execute(transactions.get(i));
		}
		if (!exec.isTerminated())
		{
			exec.shutdown();
			exec.awaitTermination(5L, TimeUnit.SECONDS);
		}
		// Flight f1 = new Flight("F1", 4, db);
		// Flight f2 = new Flight("F2", 4, db);
		// Flight f3 = new Flight("F3", 4, db);
		// Flight f4 = new Flight("F4", 4, db);
		// Flight f5 = new Flight("F5", 4, db);
		// Passenger p1 = new Passenger("P1", db);
		// Passenger p2 = new Passenger("P2", db);
		// Passenger p3 = new Passenger("P3", db);
		// Passenger p4 = new Passenger("P4", db);
		// ReserveTransaction RT1 = new ReserveTransaction(f1.id, p1.id, db, ccm);
		// ReserveTransaction RT2 = new ReserveTransaction(f2.id, p1.id, db, ccm);
		// ReserveTransaction RT3 = new ReserveTransaction(f3.id, p1.id, db, ccm);
		// ReserveTransaction RT4 = new ReserveTransaction(f1.id, p3.id, db, ccm);
		// ReserveTransaction RT5 = new ReserveTransaction(f2.id, p3.id, db, ccm);
		// MyFlightsTransaction FT1 = new MyFlightsTransaction(p3.id, db, ccm);
		// TotalReservationTransaction TR1 = new TotalReservationTransaction(db, ccm);
		// TransferTransaction TT1 = new TransferTransaction(f1.id, f3.id, p3.id, db, ccm);
		// MyFlightsTransaction FT2 = new MyFlightsTransaction(p3.id, db, ccm);
		// TotalReservationTransaction TR2 = new TotalReservationTransaction(db, ccm);
		// CancelTransaction CT1 = new CancelTransaction(f1.id, p3.id, db, ccm);
		// MyFlightsTransaction FT3 = new MyFlightsTransaction(p3.id, db, ccm);
		// CancelTransaction CT2 = new CancelTransaction(f2.id, p3.id, db, ccm);
		// MyFlightsTransaction FT4 = new MyFlightsTransaction(p3.id, db, ccm);
		// exec.execute(RT1);
		// exec.execute(RT2);
		// exec.execute(RT3);
		// exec.execute(RT4);
		// exec.execute(RT5);
		// exec.execute(FT1);
		// exec.execute(TR1);
		// exec.execute(TT1);
		// exec.execute(FT2);
		// exec.execute(TR2);
		// exec.execute(CT1);
		// exec.execute(FT3);
		// exec.execute(CT2);
		// exec.execute(FT4);
	}
}