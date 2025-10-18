package online.bteisrael.satellite.command;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.generator.GeneratorImpl;

public class RegenerateChunkCommand extends Command {
    public RegenerateChunkCommand() {
        super("regen", "regenerate");

        addSyntax((sender, context) -> {
            if (!(sender instanceof Player player)) return;
            MinecraftServer.getInstanceManager().getInstances().stream().findFirst().get()
                    .getChunkAt(player.getPosition().x(), player.getPosition().z()).reset();
        });
    }
}
