import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {

    private double totalItems;
    private Map<Item,Integer> shoppingCartList;

    public ShoppingCart() {
        this.totalItems = 0;
        this.shoppingCartList = new HashMap<>();
    }

    public void addProductToCart(List<Item> itemList, int option , int amount){
        for (Item item : itemList){
            if (item.getItemCN() == option){
                if (this.shoppingCartList.containsKey(item)){
                    this.shoppingCartList.put(item, this.shoppingCartList.get(item) + amount);
                }else
                    this.shoppingCartList.put(item,amount);
                break;
            }
        }
        calculateTotalCart();
    }

    public void printCart() {
        System.out.println("Your shopping cart list for this purchase: \n");
        for (Item item : this.shoppingCartList.keySet()) {
            System.out.println("Item name: " + item.getName() + "," + "Amount: " + this.shoppingCartList.get(item));
        }
        System.out.println("Your total purchase is: " + this.totalItems + "\n");
    }

    public void calculateTotalCart() {
        this.totalItems = 0;
        for(Item item : this.shoppingCartList.keySet()) {
            this.totalItems += ((this.shoppingCartList.get(item)) * (item.getPrice()));
        }
    }

    public double getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(double totalItems) {
        this.totalItems = totalItems;
    }

    public Map<Item, Integer> getShoppingCartList() {
        return shoppingCartList;
    }

}
