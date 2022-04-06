package viceCity.repositories;

import viceCity.models.guns.Gun;
import viceCity.repositories.interfaces.Repository;

import java.util.*;

public class GunRepository implements Repository<Gun> {
    private List<Gun> guns;

    public GunRepository() {
        this.guns = new ArrayList<>();
    }

    @Override
    public Collection<Gun> getModels() {
        return Collections.unmodifiableCollection(guns);
    }

    @Override
    public void add(Gun model) {
        this.guns.add(model);
    }

    @Override
    public boolean remove(Gun model) {
        return guns.remove(model);
    }

    @Override
    public Gun find(String name) {
        return this.guns.stream().filter(g -> g.getName().equals(name)).findAny().get();
    }
}
