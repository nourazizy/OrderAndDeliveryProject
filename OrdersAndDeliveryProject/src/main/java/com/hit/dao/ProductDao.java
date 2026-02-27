package main.java.com.hit.dao;

import main.java.com.hit.dm.Product;
import java.io.IOException;
import java.util.List;

public class ProductDao extends AbstractDao<Product>
{
    public ProductDao(String filePath, String fileName) { super(filePath, fileName); }

    @Override
    public Product find(long id) throws IOException {
        for (Product product : getAll()) {
            if (product.getProductCode() == id) return product;
        }
        return null;
    }

    @Override
    protected Product findToRemove(List<Product> list, Product item) {
        for (Product p : list) {
            if (p.getProductCode().equals(item.getProductCode())) return p;
        }
        return null;
    }
}