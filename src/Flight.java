import java.util.ArrayList;

public class Flight extends Lockables
{
	String id;
	ArrayList<Passenger> passgeners;
	ArrayList<Seat> seats;
	int capacity;
	int availablitiy;
	
	public Flight(String id, int capacity, Database db)
	{
		super("F", id);
		this.passgeners = new ArrayList<>();
		this.seats = new ArrayList<Seat>(capacity);
		this.capacity = capacity;
		this.id = id;
		this.availablitiy = capacity;
		// seats = new ArrayList<>(capacity);
		// for (int i = 0; i < capacity; i++)
		// {
		// Seat temp = new Seat(i + 1, true);
		// seats.set(i, temp);
		// }
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
}