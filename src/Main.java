
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int userOption = 0;
        int userType = 0;
        Shop shop = new Shop();

        do {
            while ((userOption>4) || (userOption<=0)) {

                try {
                    System.out.println("What do you want to do? ");
                    System.out.println("1 - Create account");
                    System.out.println("2 - Login to your account");
                    System.out.println("3 - Exit");
                    userOption = scanner.nextInt();


                } catch (Exception e) {
                    System.out.println("Please try again");
                    scanner.nextLine();
                }
            }

            while ((userType<=0 || userType>=3) && (userOption!=3)) {
                try {
                    System.out.println("Which account would you like to create? (Worker/Customer)");
                    System.out.println("1 - Worker");
                    System.out.println("2 - Customer");
                    userType = scanner.nextInt();
                } catch (Exception e) {
                    System.out.println("Please try again");
                    scanner.nextLine();
                }
            }

            switch (userOption) {

                    case 1:
                       shop.createUser(userType);

                        break;

                    case 2:
                        scanner.nextLine();
                        System.out.println("Enter your username");
                        String userName = scanner.nextLine();
                        System.out.println("Enter your password");
                        String password = scanner.nextLine();
                        User user = shop.checkUserExist(userName, password);
                        if (user == null) {
                            System.out.println("The user is not exist");
                        } else {
                            shop.enterIntoShop(user);
                        }
                        break;

                    case 3:
                        System.out.println("Good bye, hope to see you soon =) ");
                        break;

            }
            if (userOption != 3) {
                userOption = 0;
                userType = 0;
            }

        } while (userOption != 3);
    }
}

