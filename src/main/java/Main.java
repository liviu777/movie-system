import java.io.FileNotFoundException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Service service = new Service();
        List<Product> products= service.readProducts();
        System.out.println("The products with the highest rate are:");
        System.out.println(service.findProductsWithHighestRatingUsingSort(products));

        System.out.println();
        System.out.println("The recommended products for every user from session are:");
        service.findRecommendedProducts(products).entrySet().stream().forEach(e -> {
            System.out.print(e.getKey());
            System.out.print(" : ");
            System.out.println(e.getValue());
        });

    }


}


