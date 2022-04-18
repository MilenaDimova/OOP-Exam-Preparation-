package zoo.entities.foods;

public class Vegetable extends BaseFood{
    private static final int DEFAULT_CALORIES = 50;
    private static final double DEFAULT_PRICE = 5;

    public Vegetable() {
        super(DEFAULT_CALORIES, DEFAULT_PRICE);
    }
}
