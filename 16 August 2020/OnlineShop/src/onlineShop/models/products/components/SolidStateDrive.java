package onlineShop.models.products.components;

public class SolidStateDrive extends BaseComponent {
    private static final double DEFAULT_UNIT_MULTIPLIER = 1.20;

    public SolidStateDrive(int id, String manufacturer, String model, double price,
                       double overallPerformance, int generation) {
        super(id, manufacturer, model, price, overallPerformance, generation);
    }

    @Override
    public void setOverallPerformance(double overallPerformance) {
        super.setOverallPerformance(this.getOverallPerformance() * DEFAULT_UNIT_MULTIPLIER);
    }
}
