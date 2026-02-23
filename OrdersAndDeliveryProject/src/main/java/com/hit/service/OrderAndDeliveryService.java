/**
 * Project: Order and Delivery Application
 * * Submitters:
 * 1. נור עזיזי (מספר ת.ז.: 316068410)
 * 2. טאי שופר (מספר ת.ז.: 211697107)
 */

package main.java.com.hit.service;

import main.java.com.hit.ShortestPath.Graph;
import main.java.com.hit.ShortestPath.IShortestPath;
import main.java.com.hit.dm.Order;
import main.java.com.hit.dm.Delivery;
import main.java.com.hit.dm.Customer;
import main.java.com.hit.dm.Product;
import main.java.com.hit.dao.*;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class OrderAndDeliveryService
{
    private IShortestPath<String> shortestPathAlgo;
    private final IDao<Order> orderDao;
    private final IDao<Delivery> deliveryDao;
    private final IDao<Customer> customerDao;
    private final IDao<Product> productDao;

    public void setShortestPathAlgo(IShortestPath<String> shortestPathAlgo)
    {
        this.shortestPathAlgo = shortestPathAlgo;
    }

    public OrderAndDeliveryService(IShortestPath<String> algo, String filePath)
    {
        this.shortestPathAlgo = algo;
        this.customerDao= new CustomerDao(filePath,"customer.txt");
        this.deliveryDao= new DeliveryDao(filePath,"delivery.txt");
        this.productDao= new ProductDao(filePath,"product.txt");
        this.orderDao= new OrderDao(filePath,"order.txt");
    }

    /*Order*/
    /*saves the order*/
    public void saveOrder(Order order)
    {
        try
        {
            orderDao.save(order);
        }
        catch(IOException e)
        {
            System.out.println("Error saving the order: " + e.getMessage());
        }
    }

    /*return all the orders that we have*/
    public List<Order> getAllOrders()
    {
        return orderDao.getAll();
    }

    /*Customer*/
    /*saves the customer*/
    public void saveCustomer(Customer customer)
    {
        try
        {
            customerDao.save(customer);
        }
        catch(IOException e)
        {
            System.out.println("Error saving customer: " + e.getMessage());
        }
    }

    /*return all the customers that we have*/
    public List<Customer> getAllCustomers()
    {
        return customerDao.getAll();
    }

    /*Product*/
    /*saves the product*/
    public void saveProduct(Product product)
    {
        try
        {
            productDao.save(product);
        }
        catch(IOException e)
        {
            System.out.println("Error saving product: " + e.getMessage());
        }
    }

    /*return all the products that we have*/
    public List<Product> getAllProducts()
    {
        return productDao.getAll();
    }

    /*Delivery*/
    /*finds the shortest and the fastest path and then saves it*/
    public void findDeliveryPath(Delivery delivery, Graph<String> city)
    {
       String startPoint = delivery.getStoresAddress();
       String endPoint = delivery.getDeliveryOrder().getDeliveryAddress();

       List<String> deliveryShortestPath = shortestPathAlgo.findShortestPath(city,startPoint,endPoint);
       delivery.setPath(deliveryShortestPath);

       if(deliveryShortestPath!=null && !deliveryShortestPath.isEmpty())
       {
           int timePerRoad = 10; /*driving time between different cities in average*/
           int totalExpectedTime = (deliveryShortestPath.size() - 1) * timePerRoad;

           delivery.setExpectedTime(Math.max(totalExpectedTime, 0));
       }

       saveDelivery(delivery);
    }

    /*saves the delivery*/
    public void saveDelivery(Delivery delivery)
    {
        try
        {
            deliveryDao.save(delivery);
        }
        catch(IOException e)
        {
            System.out.println("Error saving delivery: " + e.getMessage());
        }
    }

    /*return all the deliveries that we have*/
    public List<Delivery> getAllDeliveries()
    {
        return deliveryDao.getAll();
    }
}
