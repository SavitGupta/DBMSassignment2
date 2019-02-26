public class MainClass
{
    static Database db;
    ConcurrencyController ccm;

    public static void main(String[] args){
        db = new Database();
        Flight f1 = new Flight("f1", 2, db);
        Flight f2 = new Flight("f2", 2, db);
        Flight f3 = new Flight("f3", 2, db);
        Passenger p1 = new Passenger("P1", db);

    }
}
