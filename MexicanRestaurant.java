
package RestaurantReviewApp;

public class MexicanRestaurant extends Restaurant 
{
    public MexicanRestaurant(int dID, String dName, 
            String dDescription, String dImageUrl)
    {
        super(dID, dName, dDescription, dImageUrl, "Mexican");
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
