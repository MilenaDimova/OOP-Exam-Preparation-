package onlineShop.models.products.components;

public class Motherboard extends BaseComponent {
    private static final double DEFAULT_UNIT_MULTIPLIER = 1.25;

    public Motherboard(int id, String manufacturer, String model, double price,
                          double overallPerformance, int generation) {
        super(id, manufacturer, model, price, overallPerformance, generation);
    }

    @Override
    public void setOverallPerformance(double overallPerformance) {
        super.setOverallPerformance(this.getOverallPerformance() * DEFAULT_UNIT_MULTIPLIER);
    }
}
