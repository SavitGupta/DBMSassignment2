import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainClass
{
	public static void main(String[] args) throws InterruptedException
	{
		Database db = new Database();
		ConcurrencyController ccm = new ConcurrencyController();
		Flight f1 = new Flight("f1", 2, db);
		Flight f2 = new Flight("f2", 2, db);
		Flight f3 = new Flight("f3", 2, db);
		Flight f4 = new Flight("f4", 2, db);
		Flight f5 = new Flight("f5", 2, db);
		Flight f6 = new Flight("f6", 2, db);
		Passenger p1 = new Passenger("P1", db);
		Passenger p2 = new Passenger("P2", db);
		Passenger p3 = new Passenger("P3", db);
		Passenger p4 = new Passenger("P4", db);
		ccm.add(db);
		ExecutorService exec = Executors.newFixedThreadPool(10);
		Reserve_transaction RT1 = new Reserve_transaction(f1.id, p1.id, db, ccm);
		CancelTransaction CT2 = new CancelTransaction(f2.id, p1.id, db, ccm);
		Reserve_transaction RT3 = new Reserve_transaction(f3.id, p1.id, db, ccm);
		CancelTransaction CT4 = new CancelTransaction(f4.id, p1.id, db, ccm);
		Reserve_transaction RT5 = new Reserve_transaction(f5.id, p1.id, db, ccm);
		CancelTransaction CT6 = new CancelTransaction(f6.id, p1.id, db, ccm);
		exec.execute(RT1);
		exec.execute(CT2);
		exec.execute(RT3);
		exec.execute(CT4);
		exec.execute(RT5);
		exec.execute(CT6);
		if (!exec.isTerminated())
		{
			exec.shutdown();
			exec.awaitTermination(5L, TimeUnit.SECONDS);
		}
	}
}