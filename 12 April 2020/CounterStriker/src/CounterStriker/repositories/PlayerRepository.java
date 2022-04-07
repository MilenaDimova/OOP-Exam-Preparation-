package CounterStriker.repositories;

import CounterStriker.models.players.Player;

import java.util.Collection;

public class PlayerRepository implements Repository<Player> {
    private Collection<Player> models;

    @Override
    public Collection<Player> getModels() {
        return null;
    }

    @Override
    public void add(Player model) {

    }

    @Override
    public boolean remove(Player model) {
        return false;
    }

    @Override
    public Player findByName(String name) {
        return null;
    }
}
