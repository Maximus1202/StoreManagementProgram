import java.util.LinkedList;
import java.util.Scanner;


public class Shop {

    public static final int FINISH_BUY = -1;
    public static final int CUSTOMER_USER  = 1;
    public static final int WORKER_USER  = 2;

    private LinkedList<Customer> users;
    private  LinkedList<Item> items;

    public Shop() {
        this.users = new LinkedList<>();
        this.items = new LinkedList<>();
    }
    public void createUser(int userType) {
        Scanner scanner = new Scanner(System.in);
        String firstName;
        String lastName;
        String userName;
        String password;
        do {
            System.out.println("Enter your first name:");
            firstName = scanner.nextLine();
        }while (this.isValidChar(firstName));
        System.out.println("Your first name is OK!");
        do {
            System.out.println("Enter your last name:");
            lastName = scanner.nextLine();
        } while (this.isValidChar(lastName));
        System.out.println("Your last name is OK!");
        do {
            System.out.println("Enter username:");
            userName = scanner.nextLine();
        } while (this.usernameExists(userName));
        System.out.println("Your username is OK!");
        do {
            System.out.println("Enter a password:");
            password = scanner.nextLine();
        } while (!this.isStrongPassword(password));
        System.out.println("Are you a VIP?");
        System.out.println("1 - YES");
        System.out.println("2 - NO");
        int vip = scanner.nextInt();
        boolean isMember = (vip == 1);
        Integer rank = null;
        if (userType == 2) {
            do {
                System.out.println("What is your rank?");
                System.out.println("Choose between 1-3!");
                int i = 1;
                for(WorkerType type : WorkerType.values()) {
                    System.out.println(i + "- " + type);
                    i++;
            }
                rank = scanner.nextInt();
            } while (rank <= 0 || rank >=4);
        }
        if (rank == null) {
            this.users.add(new Customer(firstName, lastName, userName, password,userType,isMember));
        }else {
            this.users.add(new Worker(firstName,lastName,userName,password,userType,isMember,rank));
        }
        System.out.println("You are register successfully");
    }

    public void enterIntoShop(User user) {
        if (user.getUserType() == CUSTOMER_USER) {
            Customer currentCustomer = (Customer) user;
            customerMenu(currentCustomer);
        } else if (user.getUserType() == WORKER_USER) {
            Worker currentWorker = (Worker) user;
            workerMenu(currentWorker);
        }
    }

    private void workerMenu(Worker worker)  {
        Scanner scanner = new Scanner(System.in);
        System.out.println(worker.admit());
        Integer workerOptions = null;
        do {
            System.out.println("1. Print all customers");
            System.out.println("2. Print only VIP members");
            System.out.println("3. Print all customers with at least one purchase");
            System.out.println("4. Print the customer with the highest total purchases");
            System.out.println("5. Add new item to the store");
            System.out.println("6. Change inventory status to the item");
            System.out.println("7. Make a purchase");
            System.out.println("8. Back to the main menu");
            workerOptions = scanner.nextInt();
            Customer customer = null;
            switch (workerOptions) {
                case 1:
                    for(User user : this.users) {
                        System.out.println(user.toString());
                    }
                    break;

                case 2:
                    for (User user : this.users) {
                        customer = (Customer) user;
                        if ((customer!=null) && (customer.isVIP())) {
                            System.out.println(customer + " VIP");
                        }
                    }

                    break;

                case 3:
                    for (User user : this.users) {
                        customer = (Customer) user;
                        if((customer!=null) && (customer.getCountPurchases() >= 1)) {
                            System.out.println(customer);
                        }
                    }

                    break;

                case 4:
                    double maximum = 0;
                    Customer highestBuyer = null;
                    for (User user : this.users) {
                        customer = (Customer) user;
                        double maxUser = customer.getSumPurchases();
                        if ((customer!=null) && (maxUser > maximum)) {
                            maximum = maxUser;
                            highestBuyer = customer;
                        }
                    }
                    if (highestBuyer!=null) {
                        System.out.println(highestBuyer + " payed: " + highestBuyer.getSumPurchases());
                    }

                    break;

                case 5:
                    System.out.println("Enter item name:");
                    String itemName = scanner.nextLine();
                    System.out.println("Enter item price:");
                    float itemPrice = scanner.nextFloat();
                    System.out.println("Enter item amount:");
                    int itemAmount = scanner.nextInt();
                    System.out.println("Enter item discount for VIP customers");
                    int itemDiscount = scanner.nextInt();
                    this.getItems().add(new Item(getItems().size(), itemPrice, itemAmount, itemDiscount, itemName));


                    break;

                case 6:
                    this.showAllItems();
                    System.out.println("Choose item to change status");
                    int changeStatus = scanner.nextInt();
                    System.out.println("Which item status do you want to change?");
                    System.out.println("1 - In stock");
                    System.out.println("2 - Out of stock");
                    int status = scanner.nextInt();
                    if (status == 1) {
                        System.out.println("Enter amount of this item");
                        int amount = scanner.nextInt();
                        this.getItems().get(changeStatus).setAmount(amount);
                    } else if (status == 2) {
                        this.getItems().get(changeStatus).setAmount(0);
                    }

                    break;

                case 7:
                    customerMenu(worker);
                    break;

                case 8:
                    break;
                default:
                    System.out.println("Wrong number, choose again between 1-8!");
                    scanner.nextLine();
            }

        } while (workerOptions != 8);
    }

    private void customerMenu(Customer customer) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println(customer.admit());
            Integer customerOption = null;
            if (this.getItems().size() > 0) {
                do {
                    int itemAmount = 0;
                    this.showAllItems();
                    System.out.println("Select item from the list, and if you want to end the purchase type -1");
                    customerOption = scanner.nextInt();
                    boolean validItem = checkItemValid(customerOption);
                    boolean purchase = true;
                    if (validItem) {
                        do {
                            System.out.println("How much items do you want from this product in your cart? (choose at least one)");
                            itemAmount = scanner.nextInt();
                            boolean availableItem = checkAvailableItem(customerOption, itemAmount);
                            if (!availableItem) {
                                System.out.println("You entered too much from this item amount in store");
                                System.out.println("Please choose again!");
                                purchase = false;
                            }
                        } while (itemAmount <= 0);
                        if (purchase) {
                            customer.getCart().addProductToCart(this.getItems(), customerOption, itemAmount);
                            customer.getCart().printCart();
                            this.updateInventoryItem(customerOption, itemAmount);
                        }
                    }
                } while (customerOption != FINISH_BUY);

                if (customer.getUserType() == WORKER_USER) {
                    Worker worker = (Worker) customer;
                    double currentTotal = worker.getCart().getTotalItems();
                    switch (worker.getWorkerType()) {
                        case REGULAR_WORKER:
                            worker.getCart().setTotalItems(calculateDiscount(currentTotal, WorkerType.REGULAR_WORKER.discount));
                            break;
                        case MANAGER:
                            worker.getCart().setTotalItems(calculateDiscount(currentTotal, WorkerType.MANAGER.discount));
                            break;
                        case MANAGEMENT_TEAM:
                            worker.getCart().setTotalItems(calculateDiscount(currentTotal, WorkerType.MANAGEMENT_TEAM.discount));
                            break;
                    }
                    System.out.println("The total price after discount is:  " + worker.getCart().getTotalItems());
                    worker.endPurchase();
                } else {
                    System.out.println("The total price is: " + customer.getCart().getTotalItems());
                    customer.endPurchase();
                }
            } else {
                System.out.println("The store is empty.");
            }
        } catch (Exception e) {
            System.out.println("Wrong number, please try again!");
        }
    }



    private boolean isValidChar (String name) {
        boolean valid = false;
        boolean onlyLetters = false;

        for (int i = 0; i < name.length(); i++) {
            char currentChar = name.charAt(i);
            if (Character.isDigit(currentChar)) {
                onlyLetters = true;
                break;
            }
        }
        if (onlyLetters) {
            valid = true;
        }
        return valid;
    }

    private boolean isStrongPassword (String password) {
        if (password.length() > 5){
            return true;

        }else {
            System.out.println("Need at least 6 characters");
            return false;
        }
    }

    private boolean usernameExists (String username) {
        boolean exists = false;
        for (int i = 0; i < this.users.size(); i++) {
            User currentUser = this.users.get(i);
            if (currentUser != null) {
                if (currentUser.getUserName().equals(username)) {
                    exists = true;
                    break;
                }
            }
        }
        return exists;

    }

    public void showAllItems() {
        System.out.println("Choose a number of items in this list:");
        for (Item item : this.items) {
            System.out.println(item.getItemCN() + " - " + item.getName() + " - " + item.getPrice() + "₪");
            if (item.getAmount() == 0) {
                System.out.println("the item is out of stock!");
            }
            System.out.println();
        }
    }

    private boolean checkItemValid(int customerOption) {
        boolean existItem = false;
        for (Item item : this.getItems()) {
            if (item.getItemCN() == customerOption) {
                existItem = true;
                break;
            }
        }
        return existItem;
    }

    private boolean checkAvailableItem(int customerOption, int itemAmount) {
        return this.getItems().get(customerOption).getAmount() >= itemAmount;
    }

    public void updateInventoryItem (int customerOption, int itemAmount) {
        for (Item item : this.items) {
            if (customerOption == item.getItemCN()) {
                item.setAmount(item.getAmount() - itemAmount);
                break;
            }
        }
    }

    public User checkUserExist (String userName, String password) {
        for (User user : this.users) {
            if ((user.getUserName().equals(userName)) && (user.getPassword().equals(password))) {
                return user;
            }
        }
        return null;
    }


    public LinkedList<Item> getItems() {
        return items;
    }

    private double calculateDiscount(double currentTotal, int discount) {
        double calculateTotal = currentTotal/100;
        calculateTotal *= discount;
        return (currentTotal-calculateTotal);
    }




}
