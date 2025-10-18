package online.bteisrael.satellite.command;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentBlockState;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.block.Block;

public class SetBlockCommand extends Command {
    public SetBlockCommand() {
        super("setblock", "sb");

        ArgumentBlockState argBlockState = new ArgumentBlockState("block");

        addSyntax((sender, context) -> {
            Block bs = context.get(argBlockState);
            if (!(sender instanceof Player player)) { return; }
            player.getInstance().setBlock(player.getPosition(), bs);
        }, argBlockState);
    }
}
