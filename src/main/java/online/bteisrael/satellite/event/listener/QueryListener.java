package online.bteisrael.satellite.event.listener;

import net.kyori.adventure.text.Component;
import net.minestom.server.event.server.ServerListPingEvent;
import net.minestom.server.extras.query.event.FullQueryEvent;
import net.minestom.server.extras.query.response.FullQueryResponse;
import net.minestom.server.ping.Status;

public class QueryListener {
    public static void handleFullQuery(FullQueryEvent event) {
        FullQueryResponse fqr = event.getQueryResponse();

        // TODO: Make configurable

        event.setQueryResponse(fqr);
    }
    public static void handleServerListPing(ServerListPingEvent event) {
        event.setStatus(Status.builder()
                .enforcesSecureChat(false)
                .description(Component.text("A Satellite Server"))
                .build()
        );
    }

}
