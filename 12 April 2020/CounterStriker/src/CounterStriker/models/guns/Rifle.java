package CounterStriker.models.guns;

public class Rifle extends GunImpl{
    private static final int DEFAULT_SHOT_BULLET = 10;

    public Rifle(String name, int bulletsCount) {
        super(name, bulletsCount);
    }

    @Override
    public int fire() {
        if (this.getBulletsCount() > 0) {
            this.setBulletsCount(this.getBulletsCount() - DEFAULT_SHOT_BULLET);
            return DEFAULT_SHOT_BULLET;
        } else {
            this.setBulletsCount(0);
            return 0;
        }
    }
}
