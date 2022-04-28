
public class Item {
    private int itemCN; // CN = Catalog Number
    private double price;
    private int amount;
    private int discountType;
    private String name;

    public Item(int itemCN, double price, int amount, int discountType, String name) {
        this.itemCN = itemCN;
        this.price = price;
        this.amount = amount;
        this.discountType = discountType;
        this.name = name;
    }

    public int getItemCN() {
        return itemCN;
    }


    public double getPrice() {
        return price;
    }


    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getDiscountType() {
        return discountType;
    }

    public void setDiscountType(int discountType) {
        this.discountType = discountType;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemCN='" + itemCN + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }


}
