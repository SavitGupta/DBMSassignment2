import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

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
	
	public void lock_acquire(ArrayList<Lockables> toLock)
	{
		// re-order
		toLock.sort(new LockableCompator_id());
		for (Lockables item : toLock)
		{
			lockTable.get(item).acquire();
		}
	}
	
	public void release(ArrayList<Lockables> toRelease)
	{
		for (Lockables item : toRelease)
		{
			lockTable.get(item).release();
		}
	}
}