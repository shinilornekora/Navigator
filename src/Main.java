import classes.Route;
import com.sun.security.jgss.GSSUtil;
import implementations.Navigator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Navigator navigator = new Navigator();
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
                        String lineInput = scanner.nextLine();
                        if (Objects.equals(lineInput, "END")) {
                            break;
                        }
                        listPoints.add(lineInput);
                    }

                    System.out.println("Write a distance for this route:");
                    route.setDistance(scanner.nextDouble());
                    route.setLocationPoints(listPoints);
                    navigator.addRoute(route);
                    System.out.println("Route was added successfully.\n" + route);
                    break;
                case 2:
                    System.out.println("Write route id to delete it:");
                    String routeToDelete = scanner.nextLine();
                    navigator.removeRoute(routeToDelete);
                    System.out.println("Route was deleted successfully");
                    break;
                case 3:
                    System.out.println("Navigator has " + navigator.size() + " routes.");
                    break;
                case 4:
                    System.out.println("Input the id:");
                    String routeToSearch = scanner.nextLine();
                    System.out.println("Route:\n" + navigator.getRoute(routeToSearch));
                    break;
                case 5:
                    System.out.println("Input start and end location at new line each:");
                    String startPoint = scanner.nextLine();
                    String endPoint = scanner.nextLine();
                    List<Route> routes = (List<Route>) navigator.searchRoutes(startPoint, endPoint);
                    System.out.println("Here are the routes:\n" + routes.toString());
                    break;
                case 6:
                    String destinationPoint = scanner.nextLine();
                    System.out.println(navigator.getFavoriteRoutes(destinationPoint));
                case 7:
                    System.out.println(navigator.getTop5Routes());
                case 8:
                    System.out.println("Shutting down the navigator...");
                    exitFlag = true;
                    break;
                default:
                    System.out.println("Command not found. Try again.");
            }
        }
        System.out.println("Program was shut down.");
    }
}
