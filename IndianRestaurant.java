
package RestaurantReviewApp;

public class IndianRestaurant extends Restaurant 
{
    public IndianRestaurant(int dID, String dName, 
            String dDescription, String dImageUrl)
    {
        super(dID, dName, dDescription, dImageUrl, "Indian");
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