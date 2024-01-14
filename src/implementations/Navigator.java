package implementations;

import classes.KeyValue;
import classes.Map;
import classes.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Navigator implements interfaces.Navigator {
    Map<Integer, Route> map = new Map<>();

    public Navigator() {}

    @Override
    public void addRoute(Route route) {
        this.map.add(route.hashCode(), route);
    }

    @Override
    public void removeRoute(String routeId) {
        for (KeyValue<Integer, Route> k : this.map) {
            if (Objects.equals(k.getValue().getId(), routeId)) {
                this.map.remove(k.getKey());
                return;
            }
        }
        System.out.println("There's no routes with this id.");
    }

    @Override
    public boolean contains(Route route) {
        for (KeyValue<Integer, Route> k : this.map) {
            if (Objects.equals(k.getValue().getId(), route.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        int count = 0;
        for (KeyValue<Integer, Route> k : this.map) {
            count++;
        }
        return count;
    }

    @Override
    public Route getRoute(String routeId) {
        for (KeyValue<Integer, Route> k : this.map) {
            if (Objects.equals(k.getValue().getId(), routeId)) {
                return k.getValue();
            }
        }
        System.out.println("There's no routes with this id.");
        return null;
    }

    @Override
    public void chooseRoute(String routeId) {
        for (KeyValue<Integer, Route> k : this.map) {
            if (Objects.equals(k.getValue().getId(), routeId)) {
                k.getValue().addPopularity();
                return;
            }
        }
        System.out.println("There's no routes with this id.");
    }

    @Override
    public Iterable<Route> searchRoutes(String startPoint, String endPoint) {
        List<Route> routes = new ArrayList<>();
        for (KeyValue<Integer, Route> k : this.map) {
            if (Objects.equals(k.getValue().getLocationPoints().get(0), startPoint) &&
                    Objects.equals(k.getValue().getLocationPoints().get(
                            k.getValue().getLocationPoints().size() - 1), endPoint)) {
                routes.add(k.getValue());
            }
        }
        return routes;
    }

    @Override
    public Iterable<Route> getFavoriteRoutes(String destinationPoint) {
        List<Route> routes = new ArrayList<>();
        for (KeyValue<Integer, Route> k : this.map) {
            if (k.getValue().isFavorite()) {
                routes.add(k.getValue());
            }
        }
        return routes;
    }

    @Override
    public Iterable<Route> getTop5Routes() {
        List<Route> routes = new ArrayList<>();
        List<Route> routesTop = new ArrayList<>();
        for (KeyValue<Integer, Route> k : this.map) {
            routes.add(k.getValue());
        }
        routes.sort((id, element) -> (element.getPopularity()));
        for (int i = 0; i < 5; i++) {
            routesTop.add(routes.get(i));
        }
        return routesTop;
    }
}
