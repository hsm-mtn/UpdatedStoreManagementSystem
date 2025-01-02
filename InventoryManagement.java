import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class Product{
    private String id;
    private String name;
    private int quantity;
    private double price;
    public Product(String id, String name, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    @Override
    public String toString() {
        return "[id :" + id + ", name :" + name + ", quantity :" + quantity + ", price :" + price + "$]";
    }

}

public class InventoryManagement {

    

    public static void main(String[] args)  {

        List<Product> products = new ArrayList<>();

        Scanner sc = new Scanner(System.in);

        try {
            loadProduct(products);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){
            System.out.println("Welcome to the main menu of our store. How ca we help you today");
            System.out.println("1.Add a product");
            System.out.println("2.View all products");
            System.out.println("3.Search for a product");
            System.out.println("4.Update a product");
            System.out.println("5.Delete a product");
            System.out.println("6.Save and exite");
            System.out.println("Please select an option ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addProducts(sc, products);
                    break;
                case 2 :
                    viewAllProducts(products);
                    break;
                case 3 :
                    searchProduct(sc, products);
                    break;
                case 4 :
                    updateProduct(sc, products);
                    break;
                case 5:
                    deleteProduct(sc, products);
                    break;
                case 6 :
                    System.out.println("Saving and exiting");
                    saveProduct(products);
                    return;
                default:
                    System.out.println("Invalid choice !");;
            }

        }

        
    }

    public static void addProducts(Scanner sc, List<Product> products){
        try{
        System.out.println("Please enter the product info");
        System.out.println("1.Enter the Id:");
        String getId = sc.nextLine();
            if(getId.isEmpty()){
                System.out.println("Id cannot be empty !");
                return;
            }
        System.out.println("Enter the name:");
        String getName = sc.nextLine();
            if(getName.isEmpty()){
                System.out.println("Name cannot be empty !");
                return;
            }
        System.out.println("Enter the quantity:");
        int getQuantity = sc.nextInt();
        sc.nextLine();
            if(getQuantity < 0){
                System.out.println("quantity cannot be negative !");
                return;
            }
        System.out.println("enter the price of the product:");
        double getPrice = sc.nextDouble();
        sc.nextLine();
            if(getPrice < 0){
                System.out.println("price  cannot be negative !");
                return;
        }

        products.add(new Product(getId, getName, getQuantity, getPrice));
        System.out.println("the product added successfully");}
        catch(InputMismatchException e){
            System.out.println("please enter a valid input");
        }
        catch(Exception e){
            System.out.println("Something went wrong !");
        }

    }

    public static void viewAllProducts(List<Product> products){
        if(products.isEmpty()){
            System.out.println("the products list is empty");
        }else {
            for(Product pro : products){
                System.out.println("Id :" + pro.getId() + "Name :" + pro.getName() + "Quantity :" + pro.getQuantity() + "Price :" + pro.getPrice());
            }
        }
    }

    public static void searchProduct(Scanner sc, List<Product> products){
        try {System.out.println("Please enter the name or the id of a product");
        String serach = sc.nextLine();
        for(Product pro : products){
                if(pro.getId().equals(serach) || pro.getName().equals(serach)){
                    System.out.println(pro);
                    return;
                }
               
        }
        System.out.println("Invalid name or id !");}
        catch(Exception e ){
            System.out.println("something went wrong !");
        }
    }

    public static void updateProduct(Scanner sc, List<Product> products){
        System.out.println("Please enter the name or the id of the product you want to update");
        String search = sc.nextLine();
        for(Product pro : products){
            if(pro.getId().equals(search) || pro.getName().equals(search)){
                System.out.println("enter the new name of the product :");
                String name = sc.nextLine(); 
                System.out.println("enter the new quantity :");
                int quantity = sc.nextInt();
                sc.nextLine();
                System.out.println("enter the new price of the product :");
                double price = sc.nextDouble();
                sc.nextLine();

                pro.setName(name);
                pro.setQuantity(quantity);
                pro.setPrice(price);

                System.out.println("product updated successfully");
                return;
                
            }
        }
        System.out.println("Invalid name or id !");
    }

    public static void deleteProduct(Scanner sc, List<Product> products){
        System.out.println("Please enter a product id or name to delete");
        String search = sc.nextLine();
        for(Product pro : products){
            if(pro.getId().equals(search) || pro.getName().equals(search)){
                System.out.println("are you sure you want to delete the choosen product (yes/no)");
                System.out.println(pro);
                String answer = sc.nextLine();
                    if(answer.equalsIgnoreCase("yes")){
                        products.remove(pro);
                        System.out.println("product deleted successfylly");
                    }else{
                        System.out.println("deletion canceled");
                    }
                    return;
            }
        }
        System.out.println("Invalid name or id!");

    }
    public static void saveProduct(List<Product> products){
        try {
            FileWriter writer = new FileWriter("Prodect.txt");
            for(Product pro : products){
                writer.write(pro.getId() + "," + pro.getName() + "," + pro.getQuantity() + "," + pro.getPrice()+ "\n");
                
            }
            writer.close();
        } catch (IOException e) {
           
            System.out.println("Error saving !");
        }
    }
    public static void loadProduct(List<Product> products) throws IOException {
        
            try (BufferedReader reader = new BufferedReader(new FileReader("product.txt"))) {
                String line;
                while((line = reader.readLine()) != null ){
                    String[] parts = line.split(",");
                    products.add(new Product(parts[0], parts[1], Integer.parseInt(parts[2]), Double.parseDouble(parts[3])));
                    
                }
            } catch (NumberFormatException e) {
                
                e.printStackTrace();
            }
            catch(IOException e){
                System.out.println("Error loading !");
            }
        
    }
}