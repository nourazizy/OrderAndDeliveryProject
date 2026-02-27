package main.java.com.hit.controller;

import main.java.com.hit.dm.Product;
import main.java.com.hit.service.OrderAndDeliveryService;
import main.java.com.hit.ShortestPath.DijkstraAlgoShortestPathImpl;
import java.util.List;

public class ProductController {

    private OrderAndDeliveryService service;

    public ProductController() {
        String path = "src/main/resources";
        this.service = new OrderAndDeliveryService(new DijkstraAlgoShortestPathImpl<>(), path);
    }

    public void saveProduct(Product product) {
        service.saveProduct(product);
    }

    public void deleteProduct(Product product) {
        service.deleteProduct(product);
    }

    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

    public Product getProduct(long id) { return service.getProduct(id); }
}