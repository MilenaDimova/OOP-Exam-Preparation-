package petStore;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PetStoreTests {

    @Test
    public void testGetCount() {
        PetStore petStore = new PetStore();
        Animal animal = new Animal("cat", 10, 250.50);
        Animal animal1 = new Animal("dog", 25, 170.80);
        petStore.addAnimal(animal);
        petStore.addAnimal(animal1);
        Assert.assertEquals(2, petStore.getCount());
    }

    @Test
    public void testGetAnimals() {
        PetStore petStore = new PetStore();
        Animal animal = new Animal("cat", 10, 250.50);
        Animal animal1 = new Animal("dog", 25, 170.80);
        petStore.addAnimal(animal);
        petStore.addAnimal(animal1);
        Assert.assertEquals(2, petStore.getCount());
        Assert.assertEquals(animal, petStore.getAnimals().get(0));
        Assert.assertEquals(animal1, petStore.getAnimals().get(1));
    }

    @Test
    public void testAddAnimal() {
        PetStore petStore = new PetStore();
        Animal animal = new Animal("cat", 10, 250.50);
        petStore.addAnimal(animal);
        Assert.assertEquals(1, petStore.getCount());
    }

    @Test
    public void testFindAllAnimalsWithMaxKilograms() {
        PetStore petStore = new PetStore();
        Animal animal = new Animal("cat", 10, 250.50);
        Animal animal1 = new Animal("dog", 25, 170.80);
        Animal animal2 = new Animal("snake", 12, 200.60);
        petStore.addAnimal(animal);
        petStore.addAnimal(animal1);
        petStore.addAnimal(animal2);
        List<Animal> animals = petStore.findAllAnimalsWithMaxKilograms(24);
        Assert.assertEquals(1, animals.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTryToAddNullAnimal() {
        PetStore petStore = new PetStore();
        petStore.addAnimal(null);
    }

    @Test
    public void testGetTheMostExpensiveAnimal() {
        PetStore petStore = new PetStore();
        Animal animal = new Animal("cat", 10, 250.50);
        Animal animal1 = new Animal("dog", 25, 170.80);
        Animal animal2 = new Animal("snake", 12, 200.60);
        petStore.addAnimal(animal);
        petStore.addAnimal(animal1);
        petStore.addAnimal(animal2);
        Assert.assertEquals(animal.getPrice(), petStore.getTheMostExpensiveAnimal().getPrice(), 0.00);
    }

    @Test
    public void testGetTheMostExpensiveAnimalThrowNull() {
        PetStore petStore = new PetStore();
        Assert.assertNull(petStore.getTheMostExpensiveAnimal());
    }


    @Test
    public void testFindAllAnimalBySpecie() {
        PetStore petStore = new PetStore();
        Animal animal = new Animal("cat", 10, 250.50);
        Animal animal1 = new Animal("dog", 25, 170.80);
        Animal animal2 = new Animal("snake", 12, 200.60);
        petStore.addAnimal(animal);
        petStore.addAnimal(animal1);
        petStore.addAnimal(animal2);
        Assert.assertEquals(1, petStore.findAllAnimalBySpecie("cat").size());
    }
}

