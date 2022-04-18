package zoo.entities.areas;

public class WaterArea extends BaseArea{
    private static final int DEFAULT_CAPACITY = 10;

    public WaterArea(String name) {
        super(name, DEFAULT_CAPACITY);
    }
}
