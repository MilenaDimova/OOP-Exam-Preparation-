package onlineShop.models.products.components;

public class VideoCard extends BaseComponent {
    private static final double DEFAULT_UNIT_MULTIPLIER = 1.15;

    public VideoCard(int id, String manufacturer, String model, double price,
                       double overallPerformance, int generation) {
        super(id, manufacturer, model, price, overallPerformance, generation);
    }

    @Override
    public void setOverallPerformance(double overallPerformance) {
        super.setOverallPerformance(overallPerformance * DEFAULT_UNIT_MULTIPLIER);
    }
}
