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
	
	public boolean transfer(Flight flight1, Flight flight2)
	{
		if (!this.flights.contains(flight1) || flight2.availablitiy == 0)
		{
			return false;
		}
		flight1.availablitiy += 1;
		flight1.passgeners.remove(this);
		this.flights.remove(flight1);
		flight2.availablitiy -= 1;
		flight2.passgeners.add(this);
		this.flights.add(flight2);
		return true;
	}
	
	public ArrayList<Flight> get_flights()
	{
		return this.flights;
	}
	
	public void reset()
	{
		this.flights = new ArrayList<Flight>();
	}
}