package classes;

import java.util.Comparator;

public class RouteFavoriteComparator implements Comparator<Route> {
    @Override
    public int compare(Route o1, Route o2) {
        if (o1.isFavorite() && o2.isFavorite())
            return 0;
        return 1;
    }
}
