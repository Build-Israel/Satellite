package online.bteisrael.satellite;

import lombok.Getter;
import net.minestom.server.Auth;
import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.registry.RegistryKey;
import net.minestom.server.world.DimensionType;
import online.bteisrael.satellite.command.*;
import online.bteisrael.satellite.event.SatelliteEvents;
import online.bteisrael.satellite.util.Namespace;
import online.bteisrael.satellite.world.TerraGenerator;
import org.jetbrains.annotations.NotNull;
import online.bteisrael.satellite.SatelliteConstants;

@Getter
public class SatelliteServer {

    public static final String SATELLITE_NAMESPACE = "satellite";
    public static final String SATELLITE_VERSION = SatelliteConstants.SATELLITE_VERSION;
    public static final String MINESTOM_VERSION = SatelliteConstants.MINESTOM_VERSION;
    @Getter
    private static SatelliteServer server;

    static void main(String[] args) {
        server = new SatelliteServer();
    }

    public SatelliteServer() {
        MinecraftServer server = MinecraftServer.init(new Auth.Online());

        DimensionType dt = DimensionType.builder()
                .coordinateScale(0.000010165068)
                .height(4000)
                .minY(-2000)
                .build();
        
        RegistryKey<@NotNull DimensionType> key = MinecraftServer.getDimensionTypeRegistry().register(Namespace.TERRA_WORLD.asKey(), dt);

        InstanceContainer ic = MinecraftServer.getInstanceManager().createInstanceContainer(key);
        ic.setChunkSupplier(LightingChunk::new);
        ic.setGenerator(new TerraGenerator());
        ic.viewDistance(16);

        SatelliteEvents.hook(MinecraftServer.getGlobalEventHandler());

        MinecraftServer.getCommandManager().register(new TeleportCommand());
        MinecraftServer.getCommandManager().register(new SetBlockCommand());
        MinecraftServer.getCommandManager().register(new FlyCommand());
        MinecraftServer.getCommandManager().register(new VersionCommand());
        MinecraftServer.getCommandManager().register(new StopCommand());

        server.start("127.0.0.1", 25565);
    }

}
