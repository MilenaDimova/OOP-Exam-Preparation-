package catHouse.entities.cat;

import catHouse.common.Constants;
import catHouse.common.HouseType;

public class ShorthairCat extends BaseCat {

    public ShorthairCat(String name, String breed, double price) {
        super(name, breed, price);
        this.setKilograms(Constants.SHORT_HAIR_CAT_KILOGRAMS);
        this.setCanLiveInHouse(HouseType.ShortHouse);
    }

    @Override
    public void eating() {
       this.setKilograms(this.getKilograms() + 1);
    }


}
