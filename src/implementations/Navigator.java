package implementations;

import classes.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Navigator implements interfaces.Navigator {
    Map<Integer, Route> map = new Map<>();

    public Navigator() {}

    @Override
    public void addRoute(Route route) {
        if (!contains(route)) {
            System.out.println("add element in navigator.class " + route);
            this.map.add(route.hashCode(), route);
        }
        else
            System.out.println("this route already exist!");
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
            int currentRouteLen = k.getValue().getLocationPoints().size();
            int newRouteLen = route.getLocationPoints().size();
            if (Objects.equals(k.getValue().getId(), route.getId())||
                    (Objects.equals(currentRouteLen, newRouteLen)&&
                    Objects.equals(k.getValue().getLocationPoints().get(0), route.getLocationPoints().get(0))&&
                    Objects.equals(k.getValue().getLocationPoints().get(currentRouteLen-1),
                            route.getLocationPoints().get(newRouteLen-1)))) {
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
        routes.sort(Comparator.comparing(Route::getDistance));
        routes.sort(new RoutePopularityComparator());
        routes.sort(new RouteFavoriteComparator());
        return routes;
    }

    @Override
    public Iterable<Route> getFavoriteRoutes(String destinationPoint) {
        List<Route> routes = new ArrayList<>();
        for (KeyValue<Integer, Route> k : this.map) {
            int currentRouteLen = k.getValue().getLocationPoints().size();
            if (k.getValue().isFavorite() &&
                    k.getValue().getLocationPoints().get(currentRouteLen-1).equals(destinationPoint)) {
                routes.add(k.getValue());
            }
        }
        routes.sort(Comparator.comparing(Route::getDistance));
        routes.sort(new RoutePopularityComparator());
        return routes;
    }

    @Override
    public Iterable<Route> getTop5Routes() {
        List<Route> routes = new ArrayList<>();
        List<Route> routesTop = new ArrayList<>();
        for (KeyValue<Integer, Route> k : this.map) {
            routes.add(k.getValue());
        }
        routes.sort(new RoutePopularityComparator());
//        routes.sort(new RouteFavoriteComparator());
        for (int i = 0; i < 5; i++) {
            routesTop.add(routes.get(i));
        }
        routesTop.sort(Comparator.comparing(Route::getDistance));
        routesTop.sort(Comparator.comparing((Route route)->route.getLocationPoints().size()));
        return routesTop;
    }
    public void printNavigator(){
        for (KeyValue<Integer, Route> k:this.map)
            System.out.println(k);
    }
}
