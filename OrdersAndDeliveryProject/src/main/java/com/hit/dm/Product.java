package main.java.com.hit.dm;

import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable {
    private final String productName;
    private final String productBrand;
    private final Integer productCode;
    private double productPrice;
    private double productWeight;
    private boolean inStock;

    public Product(String productName, String productBrand, Integer productCode, double productPrice, double productWeight) {
        this.productName = productName;
        this.productBrand = productBrand;
        this.productCode = productCode;
        this.productPrice = productPrice;
        this.productWeight = productWeight;
        this.inStock = true;
    }

    public void setProductPrice(double newProductPrice) { productPrice = newProductPrice; }
    public void setInStock(boolean newInStock) { inStock = newInStock; }
    public void setProductWeight(double newProductWeight) { productWeight = newProductWeight; }

    public String getProductName() { return productName; }
    public String getProductBrand() { return productBrand; }
    public Integer getProductCode() { return productCode; }
    public double getProductPrice() { return productPrice; }
    public double getProductWeight() { return productWeight; }
    public boolean isInStock() { return inStock; }

    // תיקון הבאג שהיה לכן פה! עכשיו זה משווה נכון
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productCode, product.productCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productCode);
    }

    @Override
    public String toString() {
        String stockStatus = inStock ? "Yes" : "No";
        return "📦 Product Code: " + productCode + "\n" +
                "   Name: " + productName + " (" + productBrand + ")\n" +
                "   Price: ₪" + productPrice + " | Weight: " + productWeight + "kg\n" +
                "   In Stock: " + stockStatus;
    }
}