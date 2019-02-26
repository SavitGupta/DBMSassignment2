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
		return items.get(id);
	}
}