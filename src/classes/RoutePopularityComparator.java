package classes;

import java.util.Comparator;

public class RoutePopularityComparator implements Comparator<Route> {
    @Override
    public int compare(Route o1, Route o2) {
        return -o1.getPopularity().compareTo(o2.getPopularity());
    }
}
