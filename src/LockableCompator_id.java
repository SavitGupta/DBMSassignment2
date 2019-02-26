import java.util.Comparator;

public class LockableCompator_id implements Comparator<Lockables> {

    @Override
    public int compare(Lockables o1, Lockables o2) {
        return o2.id.compareTo(o1.id);
    }
}
