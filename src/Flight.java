import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Flight {
    String id;
    ArrayList<Passenger> passgeners;
    ArrayList<Seat> seats;
    int capacity;
    int availablitiy;

    public Flight(String id, int capacity){
        passgeners = new ArrayList<>();
        this.capacity = capacity;
        this.id = id;
        this.availablitiy = capacity;
        seats = new ArrayList<>(capacity);
        for(int i = 0; i < capacity; i++){
            Seat temp = new Seat(i + 1, true);
            seats.set(i, temp);
        }

    }






}

