package mkl.com.miniprojekt;

import java.io.Serializable;

/**
 * Created by MattiasKristenLyngdr on 26-09-2017.
 */

public class Product implements Serializable{

    private String Name;
    private double Price;
    private double SalePrice;
    private int ShopId;
//    private double Volume;
//    private int Amount;

    public Product(String name, double price, double salePrice, int shopId){
        this.Name=name;
        this.Price=price;
        this.SalePrice=salePrice;
        this.ShopId=shopId;

    }

    public String getName() {
        return Name;
    }

    public double getPrice() {
        return Price;
    }

    public double getSalePrice() {
        return SalePrice;
    }

    public int getShopId() {
        return ShopId;
    }
}
