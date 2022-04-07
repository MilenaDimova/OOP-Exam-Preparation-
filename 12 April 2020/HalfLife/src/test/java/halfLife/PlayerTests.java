package halfLife;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PlayerTests {

    @Test
    public void testGetUsername() {
        Player player = new Player("Diego", 20);
        Assert.assertEquals("Diego", player.getUsername());
    }

    @Test(expected = NullPointerException.class)
    public void testSetUsernameWithNull() {
        Player player = new Player(null, 20);
    }

    @Test(expected = NullPointerException.class)
    public void testSetUsernameWithEmptyName() {
        Player player = new Player("", 20);
    }

    @Test
    public void testGetHealth() {
        Player player = new Player("Diego", 20);
        Assert.assertEquals(20, player.getHealth());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetHealthUnderZero() {
        Player player = new Player("Diego", -5);
    }

    @Test(expected = IllegalStateException.class)
    public void testTakeDamageOnHealthUnderOrEqualsToZero() {
        Player player = new Player("Diego", 0);
        player.takeDamage(5);
    }

    @Test
    public void testSetHealthTakeDamage() {
        Player player = new Player("Diego", 7);
        player.takeDamage(10);
        Assert.assertEquals(0, player.getHealth());
    }

    @Test
    public void testGetGuns() {
        Player player = new Player("Diego", 15);
        Gun pistol = new Gun("Colt", 10);
        Gun rifle = new Gun("SniperRifle", 100);
        player.addGun(pistol);
        player.addGun(rifle);
        List<Gun> returnedGuns = player.getGuns();
        Assert.assertEquals(2, returnedGuns.size());
        Assert.assertEquals("Colt", pistol.getName());
        Assert.assertEquals("SniperRifle", rifle.getName());
    }

    @Test
    public void testTakeDamage() {
        Player player = new Player("Diego", 15);
        player.takeDamage(10);
        Assert.assertEquals(5, player.getHealth());
    }

    @Test(expected = NullPointerException.class)
    public void testAddNullGun() {
        Player player = new Player("Diego", 15);
        player.addGun(null);
    }

    @Test
    public void testAddGun() {
        Player player = new Player("Diego", 15);
        Gun gun = new Gun("Colt", 10);
        player.addGun(gun);
    }

    @Test
    public void testRemoveGun() {
        Player player = new Player("Diego", 15);
        Gun gun = new Gun("Colt", 10);
        player.addGun(gun);
        Assert.assertTrue(player.removeGun(gun));
    }

    @Test
    public void testGetGun() {
        Player player = new Player("Diego", 15);
        Gun gun = new Gun("Colt", 10);
        player.addGun(gun);
        Gun returnedGun = player.getGun("Colt");
        Assert.assertEquals("Colt", returnedGun.getName());
    }

}
