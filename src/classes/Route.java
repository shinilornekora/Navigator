package classes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

// route - маршрут
@AllArgsConstructor
@NoArgsConstructor
public class Route implements Comparable<Route>{
    private String Id; // уникальный идентификатор
    private Double Distance; // видимо длинная маршрута в единицах измерения
    private Integer Popularity = 0; // кол-во запросов к этому маршруту
    private boolean IsFavorite; // является ли она избранной для данного пользователя
    private List<String> LocationPoints; // список строк (Точка, для упрощения, просто названия городов);

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
        this.setId(String.valueOf(Math.abs(locationPoints.hashCode())));
    }

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

    public void addPopularity(){
        setPopularity(++Popularity);
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
        return (Double.compare(Distance, route.Distance) == 0 &&
                Popularity == route.Popularity && IsFavorite == route.IsFavorite &&
                Objects.equals(Id, route.Id) &&
                Objects.equals(LocationPoints, route.LocationPoints));
//                ||(Objects.equals(getStartPoint(), route.getStartPoint()) &&
//                        getCountOFPoints() == route.getCountOFPoints() &&
//                        Objects.equals(getEndPoint(), route.getEndPoint()));
    }

    @Override
    public int hashCode() {
        return Math.abs(Objects.hash(
                LocationPoints.get(0) + LocationPoints.get(LocationPoints.size() - 1)+LocationPoints.size()));
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

