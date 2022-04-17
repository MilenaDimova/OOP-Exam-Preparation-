package gifts;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

public class GiftFactoryTests {

    @Test
    public void testGetCount() {
        GiftFactory giftFactory = new GiftFactory();
        Gift gift = new Gift("present", 20);
        Gift gift1 = new Gift("ball", 5);
        giftFactory.createGift(gift);
        giftFactory.createGift(gift1);
        Assert.assertEquals(2, giftFactory.getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTryToCreateExistingGift() {
        GiftFactory giftFactory = new GiftFactory();
        Gift gift = new Gift("present", 20);
        giftFactory.createGift(gift);
        giftFactory.createGift(gift);
    }

    @Test
    public void testCreateGift() {
        GiftFactory giftFactory = new GiftFactory();
        Gift gift = new Gift("present", 20);
        giftFactory.createGift(gift);
    }

    @Test(expected = NullPointerException.class)
    public void testTryToRemoveNullNameGift() {
        GiftFactory giftFactory = new GiftFactory();
        giftFactory.removeGift(null);
    }

    @Test(expected = NullPointerException.class)
    public void testTryToRemoveEmptyNameGift() {
        GiftFactory giftFactory = new GiftFactory();
        giftFactory.removeGift("");
    }

    @Test
    public void testRemoveGift() {
        GiftFactory giftFactory = new GiftFactory();
        Gift gift = new Gift("present", 20);
        Gift gift1 = new Gift("ball", 5);
        giftFactory.createGift(gift);
        giftFactory.createGift(gift1);
        giftFactory.removeGift("ball");
        Assert.assertEquals(1, giftFactory.getCount());
    }

    @Test
    public void testGetPresentWithLeastMagic() {
        GiftFactory giftFactory = new GiftFactory();
        Gift gift = new Gift("present", 20);
        Gift gift1 = new Gift("ball", 5);
        giftFactory.createGift(gift);
        giftFactory.createGift(gift1);
        Assert.assertEquals(gift1, giftFactory.getPresentWithLeastMagic());
    }

    @Test
    public void testGetPresentWithLeastMagicWithNull() {
        GiftFactory giftFactory = new GiftFactory();
        Assert.assertNull(giftFactory.getPresentWithLeastMagic());
    }

    @Test
    public void testGetPresent() {
        GiftFactory giftFactory = new GiftFactory();
        Gift gift = new Gift("present", 20);
        Gift gift1 = new Gift("ball", 5);
        giftFactory.createGift(gift);
        giftFactory.createGift(gift1);
        Assert.assertEquals(gift.getType(), giftFactory.getPresent("present").getType());
    }

    @Test
    public void testGetNullPresent() {
        GiftFactory giftFactory = new GiftFactory();
        Assert.assertNull(giftFactory.getPresent("null"));
    }

    @Test
    public void testGetPresents() {
        GiftFactory giftFactory = new GiftFactory();
        Gift gift = new Gift("present", 20);
        Gift gift1 = new Gift("ball", 5);
        giftFactory.createGift(gift);
        giftFactory.createGift(gift1);
        Collection<Gift> presents = giftFactory.getPresents();
        Assert.assertEquals(presents.size(), giftFactory.getCount());
    }
}
