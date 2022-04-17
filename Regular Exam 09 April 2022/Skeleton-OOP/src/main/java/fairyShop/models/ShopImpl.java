package fairyShop.models;

public class ShopImpl implements Shop{

    @Override
    public void craft(Present present, Helper helper) {
        while (!present.isDone()) {
            Instrument instrument = helper.getInstruments().stream()
                    .filter(i -> !i.isBroken()).findFirst().orElse(null);
            if (instrument == null) {
                break;
            }
            if (!helper.canWork()) {
                break;
            }
            helper.work();
            instrument.use();
            present.getCrafted();
        }
    }
}

