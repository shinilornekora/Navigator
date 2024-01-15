import classes.KeyValue;
import classes.Route;
import implementations.Navigator;

import java.util.*;

public class Main {
    public static final Navigator navigator = new Navigator();
    public static void main(String[] args) {
        addRouts();
        Scanner scanner = new Scanner(System.in);
        Boolean exitFlag = false;
        while (true) {
            if (exitFlag) {
                break;
            }
            System.out.println("""
                    Choose an action:
                    1) Add the route
                    2) Delete the route
                    3) Get the number of the routes
                    4) Get the route by id
                    5) Get all routes between two locations
                    6) Get all favourite routes
                    7) Get top five routes
                    8) Exit the program"""
            );
            int decision = scanner.nextInt();

            switch (decision) {
                case 1:
                    Route route = new Route();
                    List<String> listPoints = new ArrayList<>();

                    System.out.println("""
                            Write down all location points - every on the new line.
                            Write 'END' to finish route line."""
                    );

                    while (true) {
                        String lineInput = scanner.next();
                        if (Objects.equals(lineInput, "END")) {
                            break;
                        }
                        listPoints.add(lineInput);
                    }

                    System.out.println("Write a distance for this route:");
                    route.setDistance(scanner.nextDouble());
                    route.setLocationPoints(listPoints);
                    navigator.addRoute(route);
                    break;
                case 2:
                    System.out.println("Write route id to delete it:");
                    String routeToDelete = scanner.next();
                    navigator.removeRoute(routeToDelete);
                    System.out.println("Route was deleted successfully");
                    break;
                case 3:
                    System.out.println("Navigator has " + navigator.size() + " routes.");
                    break;
                case 4:
                    System.out.println("Input the id:");
                    String routeToSearch = scanner.next();
                    System.out.println("Route:\n" + navigator.getRoute(routeToSearch));
                    break;
                case 5:
                    System.out.println("Input start and end location at new line each:");
                    String startPoint = scanner.next();
                    String endPoint = scanner.next();
                    List<Route> routes = (List<Route>) navigator.searchRoutes(startPoint, endPoint);
                    System.out.println("Here are the routes:\n");
                    for (Route r:routes)
                        System.out.println(r);
                    break;
                case 6:
                    System.out.println("input destination Point:");
                    String destinationPoint = scanner.next();
                    for (Route r:navigator.getFavoriteRoutes(destinationPoint))
                        System.out.println(r);
                    break;
                case 7:
                    for (Route r:navigator.getTop5Routes())
                        System.out.println(r);
                    break;
                case 8:
                    System.out.println("Shutting down the navigator...");
                    exitFlag = true;
                    break;
                case 9:
                    printNavigator();
                    break;
                default:
                    System.out.println("Command not found. Try again.");
            }
        }
        System.out.println("Program was shut down.");
    }
    public static void addRouts(){
        navigator.addRoute(new Route("1", 23.9, 0, false, Arrays.asList("A", "B", "C")));
        navigator.addRoute(new Route("2", 25.9, 2, false,
                Arrays.asList("A", "B", "G", "C")));
        navigator.addRoute(new Route("3", 13.6, 0, false,
                Arrays.asList("A", "U", "F", "G", "C")));
        navigator.addRoute(new Route("4", 76.3, 4, true,
                Arrays.asList("A", "S", "X", "E", "R", "C")));
        navigator.addRoute(new Route("5", 64.3, 0, false,
                Arrays.asList("A", "H", "R", "V", "N", "I", "C")));
        navigator.addRoute(new Route("6", 28.8, 7, true,
                Arrays.asList("A", "K", "F", "O", "P", "Q", "R", "S", "C")));
        navigator.addRoute(new Route("7", 35.9, 8, false, Arrays.asList("G", "F", "C")));
        navigator.addRoute(new Route("8", 67.3, 12, true, Arrays.asList("Q", "B", "C")));
        navigator.addRoute(new Route("9", 88.6, 54, false, Arrays.asList("J", "T", "C")));
        navigator.addRoute(new Route("10", 72.2, 2, true, Arrays.asList("T", "B", "E")));
        navigator.addRoute(new Route("11", 13.4, 0, true, Arrays.asList("O", "B", "E")));
        navigator.addRoute(new Route("12", 56.8, 0, false, Arrays.asList("A", "B", "F")));
        navigator.addRoute(new Route("13", 43.1, 0, true, Arrays.asList("C", "B", "D")));
    }
    public static void printNavigator(){
        navigator.printNavigator();
    }
}
