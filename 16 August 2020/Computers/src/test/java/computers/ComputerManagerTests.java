package computers;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ComputerManagerTests {

    @Test
    public void testGetCountAndAddComputer() {
        ComputerManager computerManager = new ComputerManager();
        Computer computer1 = new Computer("Asus", "ROG", 1250);
        Computer computer2 = new Computer("Samsung", "EVO", 800);
        Computer computer3 = new Computer("Intel", "Xeon", 1600);
        computerManager.addComputer(computer1);
        computerManager.addComputer(computer2);
        computerManager.addComputer(computer3);
        Assert.assertEquals(3, computerManager.getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTryToAddExistingComputer() {
        ComputerManager computerManager = new ComputerManager();
        Computer computer1 = new Computer("Asus", "ROG", 1250);
        computerManager.addComputer(computer1);
        computerManager.addComputer(computer1);
    }

    @Test
    public void testRemoveComputer() {
        ComputerManager computerManager = new ComputerManager();
        Computer computer1 = new Computer("Asus", "ROG", 1250);
        computerManager.addComputer(computer1);
        computerManager.removeComputer("Asus", "ROG");
        Assert.assertEquals(0, computerManager.getCount());
    }

    @Test
    public void testTryToGetComputerWithNullManufacturer() {
        ComputerManager computerManager = new ComputerManager();
        Computer computer1 = new Computer(null, "ROG", 1250);
    }

    @Test
    public void testTryToGetComputerWithNullModel() {
        ComputerManager computerManager = new ComputerManager();
        Computer computer1 = new Computer("Asus", null, 1250);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTryToGetNullComputer() {
        ComputerManager computerManager = new ComputerManager();
        Computer computer1 = new Computer("Asus", "ROG", 1250);
        Computer computer2 = new Computer("Samsung", "EVO", 800);
        computerManager.addComputer(computer1);
        computerManager.addComputer(computer2);
        computerManager.getComputer("Intel", "Xeon");
    }

    @Test
    public void testGetComputersByManufacturer() {
        ComputerManager computerManager = new ComputerManager();
        Computer computer1 = new Computer("Asus", "ROG", 1250);
        Computer computer2 = new Computer("Samsung", "EVO", 800);
        computerManager.addComputer(computer1);
        computerManager.addComputer(computer2);
        List<Computer> returnedComputers = computerManager.getComputersByManufacturer("Asus");
        Assert.assertEquals(1, returnedComputers.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateNullValue() {
        ComputerManager computerManager = new ComputerManager();
        computerManager.addComputer(null);
    }
}