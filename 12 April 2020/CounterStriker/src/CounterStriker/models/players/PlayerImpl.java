package CounterStriker.models.players;

import CounterStriker.models.guns.Gun;

import static CounterStriker.common.ExceptionMessages.*;

public abstract class PlayerImpl implements Player{
    private String username;
    private int health;
    private int armor;
    private boolean isAlive;
    private Gun gun;

    protected PlayerImpl(String username, int health, int armor, Gun gun) {
        this.setUsername(username);
        this.setHealth(health);
        this.setArmor(armor);
        this.setGun(gun);
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    private void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new NullPointerException(INVALID_PLAYER_NAME);
        }
        this.username = username;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    private void setHealth(int health) {
        if (health < 0) {
            throw new IllegalArgumentException(INVALID_PLAYER_HEALTH);
        }
        this.health = health;
    }

    @Override
    public int getArmor() {
        return this.armor;
    }

    private void setArmor(int armor) {
        if (armor < 0) {
            throw new IllegalArgumentException(INVALID_PLAYER_ARMOR);
        }
        this.armor = armor;
    }

    @Override
    public Gun getGun() {
        return this.gun;
    }

    private void setGun(Gun gun) {
        if (gun == null) {
            throw new NullPointerException(INVALID_GUN);
        }
        this.gun = gun;
    }

    @Override
    public boolean isAlive() {
        return this.health > 0;
    }

    @Override
    public void takeDamage(int points) {
        if (this.armor > 0) {
            int armorAfterDmg = Math.max(this.getArmor() - points, 0);
            this.setArmor(armorAfterDmg);
        }

        if (this.armor == 0) {
            int healthAfterDmg = Math.max(this.getHealth() - points, 0);
            this.setHealth(healthAfterDmg);
        }

        if (this.health <= 0) {
            this.isAlive = false;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s: %s", this.getClass().getSimpleName(), this.username));
        builder.append(System.lineSeparator());
        builder.append(String.format("--Health: %d", this.health));
        builder.append(System.lineSeparator());
        builder.append(String.format("--Armor: %d", this.armor));
        builder.append(System.lineSeparator());
        builder.append(String.format("--Gun: %s", this.gun.getName()));
        return builder.toString();
    }
}
