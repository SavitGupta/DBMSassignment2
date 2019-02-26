import java.util.LinkedList;

public class Lock
{
	volatile boolean isLocked;
	volatile int heldby = 0;
	String id_holder;
	LinkedList<Thread> waiters;
	
	public Lock()
	{
		waiters = new LinkedList<>();
		isLocked = false;
		id_holder = "";
	}
	
	void acquire()
	{
		Thread t1 = Thread.currentThread();
		synchronized (this)
		{
			if (!isLocked)
			{
				isLocked = true;
				return;
			}
			// System.out.println("had to wait ");
			// boolean flag = false;
			waiters.add(t1);
		}
		boolean flag = false;
		// System.out.println("had to wait");
		while (!flag)
		{
			// System.out.println("while loop of wait");
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				// System.out.println("interrupted inside wait");
				synchronized (this)
				{
					if (t1 == waiters.getFirst() && !isLocked)
					{
						isLocked = true;
						waiters.removeFirst();
						flag = true;
					}
				}
			}
		}
	}
	
	synchronized void release()
	{
		// System.out.println("just inside release");
		isLocked = false;
		if (waiters.size() > 0)
		{
			// System.out.println("inside while in release");
			Thread t1 = waiters.getFirst();
			t1.interrupt();
		}
	}
	// public static void main(String[] args) throws InterruptedException{
	// Lock l1 = new Lock();
	// reserve_transaction T1 = new reserve_transaction(l1);
	// reserve_transaction T2 = new reserve_transaction(l1);
	// reserve_transaction T3 = new reserve_transaction(l1);
	// reserve_transaction T4 = new reserve_transaction(l1);
	//
	// Thread t1 = new Thread(T1);
	// Thread t2 = new Thread(T2);
	// Thread t3 = new Thread(T3);
	// Thread t4 = new Thread(T4);
	//
	// t1.start();
	// t2.start();
	// t3.start();
	// t4.start();
	//
	// t1.join();
	// t2.join();
	// t3.join();
	// t4.join();
	//
	// }
}