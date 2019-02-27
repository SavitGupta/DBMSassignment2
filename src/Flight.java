import java.util.ArrayList;

public class Flight extends Lockables
{
	String id;
	ArrayList<Passenger> passgeners;
	int capacity;
	int availablitiy;
	
	public Flight(String id, int capacity, Database db)
	{
		super("F", id);
		this.passgeners = new ArrayList<>();
		this.capacity = capacity;
		this.id = id;
		this.availablitiy = capacity;
		db.add(this);
	}
	
	public boolean book_flight(Passenger pas)
	{
		if (availablitiy <= 0)
		{
			return false;
		}
		availablitiy -= 1;
		passgeners.add(pas);
		pas.add_flight(this);
		return true;
	}
	
	public boolean cancel_flight(Passenger pas)
	{
		if (!pas.flights.contains(this))
		{
			return false;
		}
		else
		{
			pas.cancel_flight(this);
			availablitiy++;
			return true;
		}
	}
	
	public void reset()
	{
		this.passgeners = new ArrayList<Passenger>();
		this.availablitiy = 0;
	}
}