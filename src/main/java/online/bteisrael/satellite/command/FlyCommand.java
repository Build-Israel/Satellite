package online.bteisrael.satellite.command;

import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;

public class FlyCommand extends Command {
    public FlyCommand() {
        super("fly");

        addSyntax((sender, context) -> {
            if (!(sender instanceof Player player)) return;

            player.setFlying(false);
            player.setAllowFlying(!player.isAllowFlying());
        });
    }
}
