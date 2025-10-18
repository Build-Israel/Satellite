package online.bteisrael.satellite.event.listener;

import net.minestom.server.extras.query.event.FullQueryEvent;
import net.minestom.server.extras.query.response.FullQueryResponse;

public class QueryListener {
    public static void handleFullQuery(FullQueryEvent event) {
        FullQueryResponse fqr = event.getQueryResponse();

        // TODO: Make configurable

        event.setQueryResponse(fqr);
    }
}
