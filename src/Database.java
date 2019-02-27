import java.util.HashMap;

public class Database
{
	HashMap<String, Lockables> items;
	
	public Database()
	{
		this.items = new HashMap<>();
	}
	
	public void add(Lockables item)
	{
		items.put(item.id, item);
	}
	
	public Lockables getbyId(String id)
	{
		try
		{
			Thread.sleep(3);
		}
		catch (InterruptedException e)
		{
			// System.out.println("Got interrupt");
		}
		return items.get(id);
	}
}