package online.bteisrael.satellite.config;

import lombok.Getter;
import lombok.Setter;
import online.bteisrael.satellite.SatelliteConstants;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@ConfigSerializable
@Getter
@Setter
public class SatelliteConfig {
    @Comment("Please go through this configuration along with " +
            "this guide: https://go.namd.dev/satellite-cfg ; " +
            "This config came from a server which was built at: " + SatelliteConstants.BUILD_DATE)
    private @Nullable String buildTeamName = "BTE";
    private @Nullable String brandname = "Satellite";
    private short port = 25565;
    private String address = "127.0.0.1";
    private @Nullable String proxySecret = "";
}
