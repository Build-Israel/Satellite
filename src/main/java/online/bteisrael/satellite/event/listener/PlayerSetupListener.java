package online.bteisrael.satellite.event.listener;

import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerLoadedEvent;
import net.minestom.server.network.packet.server.play.TeamsPacket;
import net.minestom.server.scoreboard.Team;
import net.minestom.server.scoreboard.TeamBuilder;
import org.jetbrains.annotations.NotNull;

public class PlayerSetupListener {

    private static final Team visitor = new TeamBuilder("Visitor", MinecraftServer.getTeamManager())
            .collisionRule(TeamsPacket.CollisionRule.NEVER)
            .teamColor(NamedTextColor.GRAY)
            .build();

    public static EventNode<? extends @NotNull Event> getEventNode() {
        EventNode<@NotNull Event> listenerRoot = EventNode.all("SetupNode");

        listenerRoot.addListener(AsyncPlayerConfigurationEvent.class, PlayerSetupListener::handleConfiguration);
        listenerRoot.addListener(PlayerLoadedEvent.class, PlayerSetupListener::handleLoaded);

        return listenerRoot;
    }

    public static void handleConfiguration(AsyncPlayerConfigurationEvent event) {
        // TODO: Make Spawning Instance (World) Configurable
        event.setSpawningInstance(MinecraftServer.getInstanceManager().getInstances().stream().findFirst().get());
        // TODO: Make spawn location Configurable
        event.getPlayer().setRespawnPoint(new Pos(5223258.5, 18, -2656580.5));
        // Spawning Location was set to around Hostage Square
        // Because it was easiest for me to look at
        // TODO: Add permission Handler
        event.getPlayer().setGameMode(GameMode.ADVENTURE);
        event.getPlayer().setPermissionLevel(2);
    }

    public static void handleLoaded(PlayerLoadedEvent event) {
        Player player = event.getPlayer();

        player.setAllowFlying(true);
        player.setCanPickupItem(false);
        player.setInvulnerable(true);
        player.setGlowing(true);
        player.setTeam(visitor);
    }
}
