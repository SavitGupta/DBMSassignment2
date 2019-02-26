import java.util.ArrayList;

public class Passenger extends Lockables
{
	String id;
	ArrayList<Flight> flights;
	
	public Passenger(String id, Database db)
	{
		super("P", id);
		this.id = id;
		this.flights = new ArrayList<>();
		db.add(this);
	}

	public void add_flight(Flight flight)
	{
		this.flights.add(flight);
	}
	public void cancel_flight(Flight flight)
	{
		this.flights.remove(flight);
	}
}