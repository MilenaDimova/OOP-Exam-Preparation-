package aquarium;

import org.junit.Assert;
import org.junit.Test;

public class AquariumTests {

    @Test
    public void testGetName() {
        Aquarium aquarium = new Aquarium("WaterFish", 20);
        Assert.assertEquals("WaterFish", aquarium.getName());
    }

    @Test(expected = NullPointerException.class)
    public void testSetNameThrowNull() {
        Aquarium aquarium = new Aquarium(null, 20);
    }

    @Test(expected = NullPointerException.class)
    public void testSetNameWithEmptyName() {
        Aquarium aquarium = new Aquarium("", 20);
    }

    @Test
    public void testGetCapacity() {
        Aquarium aquarium = new Aquarium("WaterFish", 20);
        Assert.assertEquals(20, aquarium.getCapacity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetCapacityWithInvalidCapacity() {
        Aquarium aquarium = new Aquarium("WaterFish", -10);
    }

    @Test
    public void testGetCount() {
        Aquarium aquarium = new Aquarium("WaterFish", 10);
        Fish fish = new Fish("Nemo");
        Fish fish1 = new Fish("Ruby");
        Fish fish2 = new Fish("Oscar");
        aquarium.add(fish);
        aquarium.add(fish1);
        aquarium.add(fish2);
        Assert.assertEquals(3, aquarium.getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTryToAddFishInFullAquarium() {
        Aquarium aquarium = new Aquarium("WaterFish", 1);
        Fish fish = new Fish("Nemo");
        Fish fish1 = new Fish("Ruby");
        aquarium.add(fish);
        aquarium.add(fish1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTryToRemoveNoneExistingFish() {
        Aquarium aquarium = new Aquarium("WaterFish", 10);
        Fish fish = new Fish("Nemo");
        Fish fish1 = new Fish("Ruby");
        aquarium.add(fish);
        aquarium.add(fish1);
        aquarium.remove("Dori");
    }

    @Test
    public void testRemoveFishByGivenName() {
        Aquarium aquarium = new Aquarium("WaterFish", 10);
        Fish fish = new Fish("Nemo");
        Fish fish1 = new Fish("Ruby");
        aquarium.add(fish);
        aquarium.add(fish1);
        Assert.assertEquals(2, aquarium.getCount());
        aquarium.remove("Nemo");
        Assert.assertEquals(1, aquarium.getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTryToSellNullFish() {
        Aquarium aquarium = new Aquarium("WaterFish", 10);
        aquarium.sellFish("Ruby");
    }

    @Test
    public void testSellFishByGivenName() {
        Aquarium aquarium = new Aquarium("WaterFish", 10);
        Fish fish = new Fish("Nemo");
        Fish fish1 = new Fish("Ruby");
        aquarium.add(fish);
        aquarium.add(fish1);
        Fish fishToSell = aquarium.sellFish("Ruby");
        Assert.assertEquals("Ruby", fishToSell.getName());
        Assert.assertFalse(fishToSell.isAvailable());
    }

    @Test
    public void testReport() {
        Aquarium aquarium = new Aquarium("WaterFish", 10);
        Fish fish = new Fish("Nemo");
        Fish fish1 = new Fish("Ruby");
        aquarium.add(fish);
        aquarium.add(fish1);
        Assert.assertEquals("Fish available at WaterFish: Nemo, Ruby",
                aquarium.report());
    }
}

