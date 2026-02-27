package main.java.com.hit.dao;

import main.java.com.hit.dm.Customer;
import java.io.IOException;
import java.util.List;

public class CustomerDao extends AbstractDao<Customer>
{
    public CustomerDao(String filePath, String fileName) { super(filePath, fileName); }

    @Override
    public Customer find(long id) throws IOException {
        for (Customer customer : getAll()) {
            if (customer.getCustomerId() == id) return customer;
        }
        return null;
    }

    @Override
    protected Customer findToRemove(List<Customer> list, Customer item) {
        for (Customer c : list) {
            if (c.getCustomerId() == item.getCustomerId()) return c;
        }
        return null;
    }
}