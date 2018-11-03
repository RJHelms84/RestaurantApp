
package RestaurantReviewApp;

public class ItalianRestaurant extends Restaurant 
{
    public ItalianRestaurant(int dID, String dName, 
            String dDescription, String dImageUrl)
    {
        super(dID, dName, dDescription, dImageUrl, "Italian");
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