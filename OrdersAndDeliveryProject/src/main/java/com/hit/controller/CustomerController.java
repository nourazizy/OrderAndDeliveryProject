package main.java.com.hit.controller;

import main.java.com.hit.dm.Customer;
import main.java.com.hit.service.OrderAndDeliveryService;
import main.java.com.hit.ShortestPath.DijkstraAlgoShortestPathImpl;
import java.util.List;

public class CustomerController {

    private OrderAndDeliveryService service;

    public CustomerController() {
        String path = "src/main/resources";
        this.service = new OrderAndDeliveryService(new DijkstraAlgoShortestPathImpl<>(), path);
    }

    public void saveCustomer(Customer customer) {
        service.saveCustomer(customer);
    }

    public void deleteCustomer(Customer customer) {
        service.deleteCustomer(customer);
    }

    public List<Customer> getAllCustomers() {
        return service.getAllCustomers();
    }

    public Customer getCustomer(long id) {
        return service.getCustomer(id);
    }
}
