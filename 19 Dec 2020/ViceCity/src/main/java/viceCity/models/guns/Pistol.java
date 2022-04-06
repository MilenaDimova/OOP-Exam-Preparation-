package viceCity.models.guns;

public class Pistol extends BaseGun{
    private static final int DEFAULT_BULLETS_PER_BARREL = 10;
    private static final int DEFAULT_TOTAL_BULLETS = 100;
    private static final int DEFAULT_BULLETS_PER_SHOT = 1;

    public Pistol(String name) {
        super(name, DEFAULT_BULLETS_PER_BARREL, DEFAULT_TOTAL_BULLETS);
    }

    @Override
    public int fire() {
        if (this.canFire()) {
            this.setBulletsPerBarrel(this.getBulletsPerBarrel() - DEFAULT_BULLETS_PER_SHOT);
        } else if (this.getBulletsPerBarrel() <= 0) {
            reload(DEFAULT_BULLETS_PER_BARREL);
        }
        return DEFAULT_BULLETS_PER_SHOT;
    }

    @Override
    public int reload(int bulletsPerBarrel) {
        super.reload(DEFAULT_BULLETS_PER_BARREL);
        return getBulletsPerBarrel();
    }
}
