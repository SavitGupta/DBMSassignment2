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
		for (int i = 0; i < 5; i++)
		{
			flights.add(new Flight("F" + (i + 1), RandInt(items / 5, items / 3), db));
		}
		for (int i = 0; i < items - 5; i++)
		{
			passengers.add(new Passenger("P" + (i + 1), db));
		}
	}
	
	public static void generate_Transactions(int num, Database db, ArrayList<Flight> flights, ArrayList<Passenger> passengers, ArrayList<Runnable> transactions, ConcurrencyController ccm, int type)
	{
		for (int i = 0; i < num; i++)
		{
			int pos = RandInt(0, 4);
			if (pos == 0)
			{
				int guess1 = RandInt(0, flights.size() - 1);
				int guess2 = RandInt(0, flights.size() - 1);
				while (guess1 == guess2)
				{
					guess2 = RandInt(0, flights.size() - 1);
				}
				transactions.add(new TransferTransaction(flights.get(guess1).id, flights.get(guess2).id, passengers.get(RandInt(0, passengers.size() - 1)).id, db, ccm, type));
			}
			if (pos == 1)
			{
				transactions.add(new ReserveTransaction(flights.get(RandInt(0, flights.size() - 1)).id, passengers.get(RandInt(0, passengers.size() - 1)).id, db, ccm, type));
			}
			else if (pos == 2)
			{
				transactions.add(new MyFlightsTransaction(passengers.get(RandInt(0, passengers.size() - 1)).id, db, ccm, type));
			}
			else if (pos == 3)
			{
				transactions.add(new CancelTransaction(flights.get(RandInt(0, flights.size() - 1)).id, passengers.get(RandInt(0, passengers.size() - 1)).id, db, ccm, type));
			}
			else
			{
				transactions.add(new TotalReservationTransaction(db, ccm, type));
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		Database db = new Database();
		ConcurrencyController ccm = new ConcurrencyController();
		ArrayList<Runnable> transactions = new ArrayList<Runnable>();
		ArrayList<Flight> flights = new ArrayList<Flight>();
		ArrayList<Passenger> passengers = new ArrayList<Passenger>();
		int num_items = 105;
		int num_trans = 10;
		generate_Database(num_items, db, flights, passengers);
		generate_Transactions(num_trans, db, flights, passengers, transactions, ccm, 1);
		for (int i = 0; i < 5; i++)
		{
			float tooktime = 0;
			float throughput = 0;
			int a = 1;
			for (Flight fl : flights)
			{
				fl.reset();
			}
			for (Passenger pa : passengers)
			{
				pa.reset();
			}
			ccm.add(db);
			ExecutorService exec = Executors.newFixedThreadPool(i * 5 + a);
			float startTime = System.nanoTime();
			for (int j = 0; j < num_trans; j++)
			{
				exec.execute(transactions.get(j));
			}
			if (!exec.isTerminated())
			{
				exec.shutdown();
				exec.awaitTermination(5L, TimeUnit.SECONDS);
			}
			float endTime = 0;
			System.out.println(exec.isTerminated());
			endTime = System.nanoTime();
			tooktime = (endTime - startTime);
			throughput = 500 * 1000000000 / tooktime;
			System.out.println("Throughput for " + (i * 5 + a) + " threads is " + throughput + " 2PL transactions");
		}
		System.out.println();
		generate_Transactions(num_trans, db, flights, passengers, transactions, ccm, 2);
		for (int i = 0; i < 5; i++)
		{
			float tooktime = 0;
			float throughput = 0;
			int a = 1;
			for (Flight fl : flights)
			{
				fl.reset();
			}
			for (Passenger pa : passengers)
			{
				pa.reset();
			}
			ccm.add(db);
			ExecutorService exec = Executors.newFixedThreadPool(i * 5 + a);
			float startTime = System.nanoTime();
			for (int j = 0; j < num_trans; j++)
			{
				exec.execute(transactions.get(j));
			}
			if (!exec.isTerminated())
			{
				exec.shutdown();
				exec.awaitTermination(5L, TimeUnit.SECONDS);
			}
			float endTime = 0;
			if (exec.isTerminated())
			{
				endTime = System.nanoTime();
			}
			tooktime = (endTime - startTime);
			throughput = 500 * 1000000000 / tooktime;
			System.out.println("Throughput for " + (i * 5 + a) + " threads is " + throughput + " serial transactions");
		}
	}
}