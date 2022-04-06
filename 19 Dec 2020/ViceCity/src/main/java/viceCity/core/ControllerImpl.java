package viceCity.core;

import viceCity.common.ConstantMessages;
import viceCity.core.interfaces.Controller;
import viceCity.models.guns.Gun;
import viceCity.models.guns.Pistol;
import viceCity.models.guns.Rifle;
import viceCity.models.neighbourhood.GangNeighbourhood;
import viceCity.models.players.CivilPlayer;
import viceCity.models.players.MainPlayer;
import viceCity.models.players.Player;

import java.util.ArrayList;
import java.util.List;

public class ControllerImpl implements Controller {
    private MainPlayer mainPlayer;
    private List<Player> civilPlayers;
    private List<Gun> guns;
    private List<Player> deadPlayers;
    private GangNeighbourhood gangNeighbourhood;

    private static final String MAIN_PLAYER = "Vercetti";
    private static final String MAIN_PLAYER_FULL_NAME = "Tommy Vercetti";

    public ControllerImpl() {
        mainPlayer = new MainPlayer();
        civilPlayers = new ArrayList<>();
        guns = new ArrayList<>();
        deadPlayers = new ArrayList<>();
        gangNeighbourhood = new GangNeighbourhood();
    }

    @Override
    public String addPlayer(String name) {
        CivilPlayer civilPlayer = new CivilPlayer(name);
        civilPlayers.add(civilPlayer);
        return String.format(ConstantMessages.PLAYER_ADDED, name);
    }

    @Override
    public String addGun(String type, String name) {
        String result;
        Gun gun;
        switch (type) {
            case "Pistol":
                gun = new Pistol(name);
                guns.add(gun);
                result = String.format(ConstantMessages.GUN_ADDED, name, type);
                break;
            case "Rifle":
                gun = new Rifle(name);
                guns.add(gun);
                result = String.format(ConstantMessages.GUN_ADDED, name, type);
                break;
            default:
                result = ConstantMessages.GUN_TYPE_INVALID;
        }
        return result;
    }

    @Override
    public String addGunToPlayer(String name) {
        String result = "";
        if (guns.isEmpty()) {
            result = ConstantMessages.GUN_QUEUE_IS_EMPTY;
        } else if (name.equals(MAIN_PLAYER)) {
            result = addGunToPlayer(mainPlayer, ConstantMessages.GUN_ADDED_TO_MAIN_PLAYER, MAIN_PLAYER_FULL_NAME);
        } else {
            Player playerToAddGun = civilPlayers.stream().filter(p -> p.getName().equals(name))
                .findAny().orElse(null);

            if (playerToAddGun != null) {
                result = addGunToPlayer(playerToAddGun, ConstantMessages.GUN_ADDED_TO_CIVIL_PLAYER, playerToAddGun.getName());
            } else {
                result = ConstantMessages.CIVIL_PLAYER_DOES_NOT_EXIST;
            }
        }

        return result;
    }

    private String addGunToPlayer(Player playerToAddGun, String gunAddedToCivilPlayer, String playerName) {
        String result;
        playerToAddGun.getGunRepository().add(guns.get(0));
        result = String.format(gunAddedToCivilPlayer, guns.get(0).getName(), playerName);
        guns.remove(0);
        return result;
    }

    @Override
    public String fight() {
        this.gangNeighbourhood.action(mainPlayer, civilPlayers);
        StringBuilder builder = new StringBuilder();
        if (mainPlayer.getLifePoints() != 100 || civilPlayers.stream().anyMatch(p -> p.getLifePoints() < 50)) {
            builder.append(ConstantMessages.FIGHT_HAPPENED).append(System.lineSeparator());
            builder.append(String.format(ConstantMessages.MAIN_PLAYER_LIVE_POINTS_MESSAGE, mainPlayer.getLifePoints()));
            builder.append(System.lineSeparator());
            builder.append(String.format(ConstantMessages.MAIN_PLAYER_KILLED_CIVIL_PLAYERS_MESSAGE,
                    civilPlayers.stream().filter(p -> !p.isAlive()).count())).append(System.lineSeparator());
            builder.append(String.format(ConstantMessages.CIVIL_PLAYERS_LEFT_MESSAGE,
                    civilPlayers.stream().filter(p -> p.isAlive()).count()));
        } else {
            builder.append(ConstantMessages.FIGHT_HOT_HAPPENED);
        }

        return builder.toString().trim();
    }
}
