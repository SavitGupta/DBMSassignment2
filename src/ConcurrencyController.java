import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javafx.util.Pair;

public class ConcurrencyController
{
	HashMap<Lockables, Lock> lockTable;
	
	public ConcurrencyController()
	{
		this.lockTable = new HashMap<Lockables, Lock>();
	}
	
	public void add(Database db)
	{
		for (Entry<String, Lockables> item : db.items.entrySet())
		{
			Lock l1 = new Lock();
			Lockables l5 = item.getValue();
			lockTable.put(l5, l1);
		}
	}
	
	public void lock_acquire(ArrayList<Pair<Integer, Lockables>> toLock)
	{
		// re-order
		toLock.sort(new LockableCompator_id());
		for (Pair<Integer, Lockables> item : toLock)
		{
			if (item.getKey() == 1)
			{
				lockTable.get(item.getValue()).acquire_shared();
			}
			else
			{
				lockTable.get(item.getValue()).acquire_exclusive();
			}
		}
	}
	
	public void lockDatabase(int type)
	{
		for (Entry<Lockables, Lock> item : lockTable.entrySet())
		{
			// try {
			// Thread.sleep(10000);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			item.getValue().acquire_exclusive();
		}
	}
	
	public void releaseDatabase()
	{
		for (Entry<Lockables, Lock> item : lockTable.entrySet())
		{
			item.getValue().release();
		}
	}
	
	public void release(ArrayList<Pair<Integer, Lockables>> toRelease)
	{
		for (Pair<Integer, Lockables> item : toRelease)
		{
			lockTable.get(item.getValue()).release();
		}
	}
}