/**
 * Project: Order and Delivery Application
 * * Submitters:
 * 1. נור עזיזי (מספר ת.ז.: 316068410)
 * 2. טאי שופר (מספר ת.ז.: 211697107)
 */

package main.test.com.hit.service;

import static org.junit.jupiter.api.Assertions.*;
import main.java.com.hit.dm.*;
import main.java.com.hit.service.OrderAndDeliveryService;
import main.java.com.hit.ShortestPath.Graph;
import main.java.com.hit.ShortestPath.DijkstraAlgoShortestPathImpl;
import main.java.com.hit.ShortestPath.IShortestPath;
import main.java.com.hit.ShortestPath.BellmanFordAlgoShortestPathImpl;

import java.util.List;

class OrderAndDeliveryServiceTest
{
    public static void main(String[] args)
    {
        IShortestPath<String> firstAlgo = new DijkstraAlgoShortestPathImpl<>();
        IShortestPath<String> secondAlgo = new BellmanFordAlgoShortestPathImpl<>();

        /*the files path*/
        String path = "src/main/resources";
        /*first algorithm - Dijkstra*/
        OrderAndDeliveryService service = new OrderAndDeliveryService(firstAlgo, path);

        Graph<String> countryGraph = new Graph<>();
        countryGraph.addNode("Tel-Aviv");
        countryGraph.addNode("Haifa");
        countryGraph.addNode("Jerusalem");
        countryGraph.addNode("Eilat");
        countryGraph.addNode("Beer-Sheva");

        countryGraph.addEdge("Tel-Aviv", "Haifa", 90);
        countryGraph.addEdge("Tel-Aviv", "Jerusalem", 60);
        countryGraph.addEdge("Jerusalem", "Beer-Sheva", 80);
        countryGraph.addEdge("Beer-Sheva", "Eilat", 220);
        countryGraph.addEdge("Haifa", "Tel-Aviv", 90);

        /*data*/
        Customer newCustomer = new Customer(123, "Dan", "0523456788", "Eilat", "01/12/1992");
        service.saveCustomer(newCustomer);
        System.out.println("Customer saved successfully. Customers Name:" + newCustomer.getCustomerName());

        Product newProduct = new Product("Laptop", "Asus", 5678, 2500, 1.2);
        service.saveProduct(newProduct);
        System.out.println("Product saved successfully. Products Name:" + newProduct.getProductName());

        Order newOrder = new Order(4356, newCustomer);
        newOrder.addProductToList(newProduct);
        service.saveOrder(newOrder);
        System.out.println("order saved successfully. Order serial number:" + newOrder.getOrderId());

        Delivery newDelivery = new Delivery("Jerusalem", newOrder);

        System.out.println("****** FIRST ALGORITHM - TEST RUN ******");
        service.findDeliveryPath(newDelivery,countryGraph);
        printResult(newDelivery);

        System.out.println("****** SECOND ALGORITHM - TEST RUN ******");
        service.setShortestPathAlgo(secondAlgo);

        newDelivery.setPath(null);
        newDelivery.setExpectedTime(0);

        service.findDeliveryPath(newDelivery,countryGraph);
        printResult(newDelivery);

        /*checks if the data was saved in the files*/
        System.out.println("****** CHECKING FILES - TEST RUN ******");
        List<Delivery> fileDeliveries = service.getAllDeliveries();
        boolean found = false;
        for (Delivery delivery : fileDeliveries)
        {
            if(delivery.getDeliveryOrder().getOrderId() == 4356)
            {
                found = true;
                break;
            }
        }

        if(found)
        {
            System.out.println("Delivery that was added before was successfully found in the file deliveries.txt - PASS THE TEST");
        }
        else
        {
            System.out.println("Delivery was not found in the file that means there is an error - FAIL THE TEST");
        }
    }

    private static void printResult(Delivery newDelivery)
    {
        List<String> deliveryPath = newDelivery.getPath();

        if(deliveryPath != null && !deliveryPath.isEmpty())
        {
            System.out.println("The path we need had been founded. The path is: " + deliveryPath + " - PASS THE TEST");
            System.out.println("Expected time to deliver: " + newDelivery.getExpectedTime());
        }
        else
        {
            System.out.println("The path was not founded - FAILED THE TEST");
        }
    }
}