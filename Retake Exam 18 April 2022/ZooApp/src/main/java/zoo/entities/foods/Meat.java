package zoo.entities.foods;

public class Meat extends BaseFood{
    private static final int DEFAULT_CALORIES = 70;
    private static final double DEFAULT_PRICE = 10;

    public Meat() {
        super(DEFAULT_CALORIES, DEFAULT_PRICE);
    }
}
