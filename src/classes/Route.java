package classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

// route - маршрут
@AllArgsConstructor
@NoArgsConstructor
public class Route implements Comparable<Route>{
    private String Id; // уникальный идентификатор
    private Double Distance; // видимо длинная маршрута в единицах измерения
    private Integer Popularity; // кол-во запросов к этому маршруту

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public boolean isFavorite() {
        return IsFavorite;
    }

    public void setFavorite(boolean favorite) {
        IsFavorite = favorite;
    }

    public List<String> getLocationPoints() {
        return LocationPoints;
    }

    public void setLocationPoints(List<String> locationPoints) {
        LocationPoints = locationPoints;
        this.setId(String.valueOf(locationPoints.hashCode()));
    }

    private boolean IsFavorite; // является ли она избранной для данного пользователя

    public Double getDistance() {
        return Distance;
    }

    public void setDistance(Double distance) {
        Distance = distance;
    }

    public Integer getPopularity() {
        return Popularity;
    }

    public void setPopularity(Integer popularity) {
        Popularity = popularity;
    }

    private List<String> LocationPoints; // список строк (Точка, для упрощения, просто названия городов);

    public void addPopularity(){
        setPopularity(++Popularity);
    }
    public String getStartPoint(){
        return LocationPoints.get(0);
    }
    public String getEndPoint(){
        int len = LocationPoints.size();
        return LocationPoints.get(--len);
    }
    public int getCountOFPoints(){
        return LocationPoints.size();
    }

    @Override
    public int compareTo(Route o) {
        return this.Id.compareTo(o.Id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        int len = LocationPoints.size();
        return (Double.compare(Distance, route.Distance) == 0 &&
                Popularity == route.Popularity && IsFavorite == route.IsFavorite &&
                Objects.equals(Id, route.Id) &&
                Objects.equals(LocationPoints, route.LocationPoints)) ||

                (Objects.equals(getStartPoint(), route.getStartPoint()) &&
                        getCountOFPoints() == route.getCountOFPoints() &&
                        Objects.equals(getEndPoint(), route.getEndPoint()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(LocationPoints.get(0) + LocationPoints.get(LocationPoints.size() - 1));
    }

    @Override
    public String toString() {
        return "Route{" +
                "Id='" + Id + '\'' +
                ", Distance=" + Distance +
                ", Popularity=" + Popularity +
                ", IsFavorite=" + IsFavorite +
                ", LocationPoints=" + LocationPoints.toString() +
                '}';
    }

    //  Обязательно реализуйте equals и hashCode для Navigator.Route.
}

class RouteDistanceComparator implements Comparator<Route> {

    @Override
    public int compare(Route o1, Route o2) {
        return o1.getDistance().compareTo(o2.getDistance());
    }
}

class RoutePopularityComparator implements Comparator<Route> {

    @Override
    public int compare(Route o1, Route o2) {
        return o1.getPopularity().compareTo(o2.getPopularity());
    }
}

