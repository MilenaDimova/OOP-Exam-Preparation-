package CounterStriker.models.guns;

public class Pistol extends GunImpl{
    private static final int DEFAULT_SHOT_BULLET = 1;

    public Pistol(String name, int bulletsCount) {
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
