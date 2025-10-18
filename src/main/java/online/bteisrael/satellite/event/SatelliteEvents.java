package online.bteisrael.satellite.event;

import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import net.minestom.server.extras.query.event.FullQueryEvent;
import online.bteisrael.satellite.event.listener.PlayerSetupListener;
import online.bteisrael.satellite.event.listener.QueryListener;
import org.jetbrains.annotations.NotNull;

public class SatelliteEvents {
    public static void hook(EventNode<@NotNull Event> handler) {
        handler.addListener(FullQueryEvent.class, QueryListener::handleFullQuery);
        handler.addChild(PlayerSetupListener.getEventNode());
    }
}
