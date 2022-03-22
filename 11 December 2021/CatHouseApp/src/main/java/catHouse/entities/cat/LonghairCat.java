package catHouse.entities.cat;

import catHouse.common.Constants;
import catHouse.common.HouseType;

public class LonghairCat extends BaseCat {

    public LonghairCat(String name, String breed, double price) {
        super(name, breed, price);
        this.setKilograms(Constants.LONG_HAIR_CAT_KILOGRAMS);
        this.setCanLiveInHouse(HouseType.LongHouse);
    }

    @Override
    public void eating() {
        this.setKilograms(this.getKilograms() + 3);
    }
}
