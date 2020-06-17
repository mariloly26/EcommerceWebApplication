package tracking;

public class Product {
    Integer id;
    String name;
    String imageUrl;
    Product(Integer id, String name, String url) {
        this.id = id;
        this.name = name;
        this.imageUrl = url;
    }

    public String getName() { return name; }
    public String getUrl() { return imageUrl; }
    public Integer getId() { return id; }
}