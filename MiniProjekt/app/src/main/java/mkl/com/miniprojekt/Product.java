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
    private int Quantity;

    public Product(String name, double price, double salePrice, int Quantity, int shopId){
        this.Name=name;
        this.Price=price;
        this.SalePrice=salePrice;
        this.ShopId=shopId;
        this.Quantity=Quantity;
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

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}
