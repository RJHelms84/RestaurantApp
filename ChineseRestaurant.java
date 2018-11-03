
package RestaurantReviewApp;


public class ChineseRestaurant extends Restaurant 
{
    
    public ChineseRestaurant(int dID, String dName, 
            String dDescription, String dImageUrl)
    {
        super(dID, dName, dDescription, dImageUrl, "Chinese");
    }

    @Override
    public String DisplayName() 
    {
        return getName();
    }

    @Override
    public String DisplayCategory() 
    {
        return getCategory();
    }

    @Override
    public String DisplayDescription() 
    {
        return getDescription();
    }
    
}
