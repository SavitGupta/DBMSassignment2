import javafx.util.Pair;

import java.util.Comparator;

public class LockableCompator_id implements Comparator<Pair<Integer, Lockables>>
{
	@Override
	public int compare(Pair<Integer, Lockables> o1, Pair<Integer, Lockables> o2)
	{
		return o2.getValue().id.compareTo(o1.getValue().id);
	}
}