import java.util.List;

public class User {
    private int id;
    private String name;
    private List<Integer> viewedProductsId;
    private List<Integer> purchasedProductsId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getViewedProductsId() {
        return viewedProductsId;
    }

    public void setViewedProductsId(List<Integer> viewedProductsId) {
        this.viewedProductsId = viewedProductsId;
    }

    public List<Integer> getPurchasedProductsId() {
        return purchasedProductsId;
    }

    public void setPurchasedProductsId(List<Integer> purchasedProductsId) {
        this.purchasedProductsId = purchasedProductsId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
