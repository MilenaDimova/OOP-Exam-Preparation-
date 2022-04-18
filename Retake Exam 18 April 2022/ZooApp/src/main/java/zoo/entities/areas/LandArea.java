package zoo.entities.areas;

public class LandArea extends BaseArea{
    private static final int DEFAULT_CAPACITY = 25;

    public LandArea(String name) {
        super(name, DEFAULT_CAPACITY);
    }
}
