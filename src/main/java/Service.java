import javax.jws.soap.SOAPBinding;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Service {

    public ArrayList<Product> readProducts() throws FileNotFoundException {
        String file = "src/main/resources/movie-data/Products.txt";
        Scanner scanner = new Scanner(new File(file));
        scanner.useDelimiter("\n");
        ArrayList<Product> products = new ArrayList<Product>();
        while (scanner.hasNext()) {
            String line = scanner.next();
            String attributes[] = line.split(",");
            Product product = new Product();
            product.setId(Integer.parseInt(attributes[0]));
            product.setName(attributes[1]);
            product.setYear(Integer.parseInt(attributes[2]));
            ArrayList<String> keywords = new ArrayList<String>();
            for (int i = 3; i < 8; i++) {
                keywords.add(attributes[i].replace(" ", ""));
            }
            product.setKeywords(keywords);
            product.setRating(Double.parseDouble(attributes[8]));
            product.setPrice(Double.parseDouble(attributes[9]));
            products.add(product);
        }
        return products;
    }

    public List<User> readUsers() throws FileNotFoundException {
        String file = "src/main/resources/movie-data/Users.txt";
        Scanner scanner = new Scanner(new File(file));
        ArrayList<User> users = new ArrayList<User>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String attributes[] = line.split(",");
            User user = new User();
            user.setId(Integer.parseInt(attributes[0]));
            user.setName(attributes[1]);
            List<Integer> viewedProductsId = new ArrayList<Integer>();
            String viewedProducts[] = attributes[2].split(";");
            for (int i = 0; i < viewedProducts.length; i++) {
                viewedProductsId.add(Integer.parseInt(viewedProducts[i].replace(" ", "")));
            }
            List<Integer> purchasedProductsId = new ArrayList<Integer>();
            user.setPurchasedProductsId(purchasedProductsId);
            String purchasedProducts[] = attributes[3].split(";");
            for (int i = 0; i < purchasedProducts.length; i++) {
                purchasedProductsId.add(Integer.parseInt(purchasedProducts[i].replace(" ", "")));
            }
            user.setPurchasedProductsId(purchasedProductsId);
            users.add(user);
        }
        return users;
    }

    public List<Product> findProductsWithHighestRatingUsingSort(List<Product> products) throws FileNotFoundException {
        products.sort(Comparator.comparing(Product::getRating));
        List<Product> productList = new ArrayList<>();
        productList.add(products.get(products.size() - 1));
        productList.add(products.get(products.size() - 2));
        return productList;
    }

    public List<Product> findProductsWithHighestRatingUsingMax() throws FileNotFoundException {
        List<Product> products = readProducts();
        Product product1 = products.get(0);
        Product product2 = products.get(0);
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getRating() > product1.getRating()) {
                product2 = product1;
                product1 = products.get(i);
            } else if (products.get(i).getRating() > product2.getRating()) {
                product2 = products.get(i);
            }
        }
        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        return productList;
    }

    public HashMap<Integer, Integer> readUserSession() throws FileNotFoundException {
        String file = "src/main/resources/movie-data/CurrentUserSession.txt";
        Scanner scanner = new Scanner(new File(file));
        HashMap<Integer, Integer> usersSession = new HashMap<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String attributes[] = line.split(",");
            usersSession.put(Integer.parseInt(attributes[0].replace(" ", "")), Integer.parseInt(attributes[1].replace(" ", "")));

        }
        return usersSession;
    }

    public User findUserById(Integer userId) throws FileNotFoundException {
        List<User> userList = readUsers();
        for (User user : userList) {
            if (userId == user.getId()) {
                return user;
            }
        }
        return null;
    }

    public Product findProductById(Integer productId) throws FileNotFoundException {
        ArrayList<Product> productList = readProducts();
        for (Product product : productList) {
            if (productId == product.getId()) {
                return product;
            }
        }
        return null;
    }

    public List<Product> findRecommendedProductsByProduct(Product product) throws FileNotFoundException {
        //campare every genre of the product received as parameter and compare with every genre of every product from list
        //build an frequency array or an hashmap that contains the product  (Product) as key and the value is the number of genre (Intger)
        // that matches with the product's genre
        ArrayList<Product> productList = readProducts();
        HashMap<Product, Integer> matchedGenres = new HashMap<>();
        for (Product prod : productList) {
            int noOfMatchedGenres = 0;
            if(prod.getId() == product.getId()){
                continue;
            }
            for (String genre : prod.getKeywords()) {
                if (product.getKeywords().contains(genre)) {
                    noOfMatchedGenres++;
                }
            }
            matchedGenres.put(prod, noOfMatchedGenres);
        }
        int maxNoOfMatchedGenres = matchedGenres.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get().getValue();
        List<Product> recommendedProducts = matchedGenres.entrySet().stream().filter(e -> e.getValue().equals(maxNoOfMatchedGenres)).map(Map.Entry::getKey).collect(Collectors.toList());
        return recommendedProducts;
    }


    public HashMap<User, List<Product>> findRecommendedProducts(List<Product> products) throws FileNotFoundException {
        HashMap<User, List<Product>> userRecommendedProducts = new HashMap<>();
        HashMap<Integer, Integer> userSession = readUserSession();
        for (Map.Entry session : userSession.entrySet()) {
            //extract user id from session
            Integer userId = (Integer) session.getKey();
            //find user by id => return user
            User user = findUserById(userId);

            //extract product id from session
            Integer productId = (Integer) session.getValue();
            //find product by id => return product
            Product product = findProductById(productId);
            //find recommended  products by product => return list de recommended products
            List<Product> recommendedProducts = findRecommendedProductsByProduct(product);
            userRecommendedProducts.put(user, recommendedProducts);
        }

        return userRecommendedProducts;
    }
}
