package main.java.com.hit.dm;

import java.io.Serializable;
import java.util.Objects;

/*holds tha main and important information about the Product that will help the buyer and the seller*/
public class Product implements Serializable
{
    private final String productName; /*the name of the product to help the customer know what is it*/
    private final String productBrand; /*the product brand's name that will help when we have more than one brand from the same product*/
    private final Integer productCode; /*the products code to know about which product where are talking if we have more than one brand*/
    private double productPrice; /*how much it will cost the customer*/
    private double productWeight; /*how much weight there is in this package*/
    private boolean inStock; /*if the product is still in stock or not*/

    public Product(String productName, String productBrand, Integer productCode, double productPrice, double productWeight)
    {
        this.productName = productName;
        this.productBrand = productBrand;
        this.productCode = productCode;
        this.productPrice = productPrice;
        this.productWeight = productWeight;
        this.inStock = true;
    }

    /*Setters*/
    public void setProductPrice(double newProductPrice)
    {
        productPrice = newProductPrice;
    }

    public void setInStock(boolean newInStock)
    {
        inStock = newInStock;
    }

    public void setProductWeight(double newProductWeight)
    {
        productWeight = newProductWeight;
    }

    /*getters*/
    public String getProductName()
    {
        return productName;
    }

    public String getProductBrand()
    {
        return productBrand;
    }

    public Integer getProductCode()
    {
        return productCode;
    }

    public double getProductPrice()
    {
        return productPrice;
    }

    public double getProductWeight()
    {
        return productWeight;
    }

    public boolean isInStock()
    {
        return inStock;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;

        return product.productCode.equals(product.productCode);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(productCode);
    }

    /*helps us when we want to print the information of a specific Product*/
    @Override
    public String toString()
    {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", productBrand='" + productBrand + '\'' +
                ", productCode=" + productCode +
                ", productPrice=" + productPrice +
                ", productWeight=" + productWeight +
                ", inStock=" + inStock + '}';
    }
}

