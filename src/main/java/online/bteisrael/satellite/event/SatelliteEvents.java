package online.bteisrael.satellite.event;

import net.minestom.server.event.Event;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerLoadedEvent;
import net.minestom.server.extras.query.event.FullQueryEvent;
import online.bteisrael.satellite.event.listener.PlayerSetupListener;
import online.bteisrael.satellite.event.listener.QueryListener;
import org.jetbrains.annotations.NotNull;

public class SatelliteEvents {
    public static void hook(EventNode<@NotNull Event> handler) {
        handler.addListener(
                EventListener.builder(FullQueryEvent.class)
                        .ignoreCancelled(true)
                        .handler(QueryListener::handleFullQuery)
                        .build());
        handler.addListener(
                EventListener.builder(AsyncPlayerConfigurationEvent.class)
                        .handler(PlayerSetupListener::handleConfiguration)
                        .ignoreCancelled(true)
                        .build());

        handler.addListener(
                EventListener.builder(PlayerLoadedEvent.class)
                        .handler(PlayerSetupListener::handleLoaded)
                        .ignoreCancelled(true)
                        .build());
    }
}
