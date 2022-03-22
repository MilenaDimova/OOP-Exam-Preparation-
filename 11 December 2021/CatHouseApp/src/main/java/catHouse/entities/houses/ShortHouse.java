package catHouse.entities.houses;

import static catHouse.common.Constants.SHORT_HOUSE_CAPACITY;

public class ShortHouse extends BaseHouse {

    public ShortHouse(String name) {
        super(name, SHORT_HOUSE_CAPACITY);
    }
}
