package fairyShop.models;

public class Sleepy extends BaseHelper{
    private static final int DEFAULT_ENERGY = 50;

    public Sleepy(String name) {
        super(name, DEFAULT_ENERGY);
    }

    @Override
    public void work() {
        this.setEnergy(Math.max(0, this.getEnergy() - 5));
    }
}
