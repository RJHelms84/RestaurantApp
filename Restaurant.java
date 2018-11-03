
package RestaurantReviewApp;


public abstract class Restaurant 
{
    private int id;
    private String name;
    private String description;
    private String imageUrl;
    private String category;
    
    public Restaurant(int dID, String dName, 
            String dDescription, String dImageUrl, String dCategory)
    {
        id = dID;
        name = dName;
        description = dDescription;
        imageUrl = dImageUrl;
        category = dCategory;
    }
    
    public abstract String DisplayName();
    public abstract String DisplayCategory();
    public abstract String DisplayDescription();
    
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
}
