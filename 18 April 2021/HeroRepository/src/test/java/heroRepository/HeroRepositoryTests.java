package heroRepository;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class HeroRepositoryTests {


    @Test
    public void testGetCount() {
        HeroRepository heroRepository = new HeroRepository();
        Hero hero = new Hero("Eli", 20);
        Hero hero1 = new Hero("Jasen", 15);
        heroRepository.create(hero);
        heroRepository.create(hero1);
        Assert.assertEquals(2, heroRepository.getCount());
    }

    @Test(expected = NullPointerException.class)
    public void testCreateHeroWithNull() {
        HeroRepository heroRepository = new HeroRepository();
        heroRepository.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateExistingHero() {
        HeroRepository heroRepository = new HeroRepository();
        Hero hero = new Hero("Eli", 20);
        heroRepository.create(hero);
        heroRepository.create(hero);
    }

    @Test(expected = NullPointerException.class)
    public void testTryToRemoveNullHero() {
        HeroRepository heroRepository = new HeroRepository();
        heroRepository.remove(null);
    }

    @Test(expected = NullPointerException.class)
    public void testTryToRemoveHeroWithEmptyName() {
        HeroRepository heroRepository = new HeroRepository();
        heroRepository.remove("");
    }

    @Test
    public void testRemoveHero() {
        HeroRepository heroRepository = new HeroRepository();
        Hero hero = new Hero("Eli", 20);
        heroRepository.create(hero);
        heroRepository.remove("Eli");
    }

    @Test
    public void testGetHeroWithHighestLevel() {
        HeroRepository heroRepository = new HeroRepository();
        Hero hero = new Hero("Eli", 20);
        Hero hero1 = new Hero("Jasen", 15);
        heroRepository.create(hero);
        heroRepository.create(hero1);
        Hero highestLevelHero = heroRepository.getHeroWithHighestLevel();
        Assert.assertEquals(20, highestLevelHero.getLevel());
    }

    @Test
    public void testGetHero() {
        HeroRepository heroRepository = new HeroRepository();
        Hero hero = new Hero("Eli", 20);
        Hero hero1 = new Hero("Jasen", 15);
        heroRepository.create(hero);
        heroRepository.create(hero1);
        Hero heroBYName = heroRepository.getHero("Eli");
        Assert.assertEquals("Eli", heroBYName.getName());
    }

    @Test
    public void testGetHeroes() {
        HeroRepository heroRepository = new HeroRepository();
        Hero hero = new Hero("Eli", 20);
        Hero hero1 = new Hero("Jasen", 15);
        heroRepository.create(hero);
        heroRepository.create(hero1);
        Collection<Hero> heroes = heroRepository.getHeroes();
        Assert.assertEquals(2, heroes.size());
    }
}
