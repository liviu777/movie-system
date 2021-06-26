import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Service service = new Service();
        ArrayList<Product> extractProducts;
        extractProducts = service.readProducts();
//        System.out.println(extractProducts);
//        System.out.println(service.readUsers());
        System.out.println("First 2 products with higher rating are :" +service.findProductsWithHighestRatingUsingMax());
        System.out.println("First 2 products with higher rating are :" +service.findProductsWithHighestRatingUsingSort());
        System.out.println(service.readUserSession());

    }


}


