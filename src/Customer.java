
public class Customer extends User implements Admit {

    private boolean isVIP;
    private ShoppingCart cart;
    private int countPurchases;
    private double sumPurchases;

    public Customer(String firstName, String lastName, String userName, String password, int userType, boolean isVIP) {
        super(firstName, lastName, userName, password, userType);
        this.isVIP = isVIP;
        this.cart = new ShoppingCart();
        this.countPurchases = 0;
        this.sumPurchases = 0;
    }

    public void endPurchase() {
        this.countPurchases++;
        this.sumPurchases += this.cart.getTotalItems();
        this.cart.getShoppingCartList().clear();
        this.cart.setTotalItems(0);
    }

    public boolean isVIP() {
        return isVIP;
    }


    public ShoppingCart getCart() {
        return cart;
    }


    public String admit(){
        StringBuilder message = new StringBuilder();
        message.append("Hello ").append(this.getFirstName()).append(" ").append(this.getLastName());
        if (isVIP)
            message.append(" VIP!");
        else
            message.append(" !");
        return message.toString();
    }

    public int getCountPurchases() {
        return countPurchases;
    }


    public double getSumPurchases() {
        return sumPurchases;
    }


}
