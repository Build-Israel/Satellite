package online.bteisrael.satellite.command;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
import net.minestom.server.command.builder.arguments.number.ArgumentDouble;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.entity.EntityFinder;

public class TeleportCommand extends Command {
    public TeleportCommand() {
        super("teleport", "tp");

        ArgumentDouble argX = new ArgumentDouble("x");
        ArgumentDouble argY = new ArgumentDouble("y");
        ArgumentDouble argZ = new ArgumentDouble("z");

        ArgumentEntity destination = new ArgumentEntity("destination").singleEntity(true);
        ArgumentEntity entities = new ArgumentEntity("entities");

        addSyntax((sender, context) -> {
            double x = context.get(argX);
            double y = context.get(argY);
            double z = context.get(argZ);

            if (!(sender instanceof Player player)) { return; }
            player.teleport(new Pos(x, y, z, player.getPosition().yaw(), player.getPosition().pitch()));
        }, argX, argY, argZ);

        addSyntax((sender, context) -> {
            EntityFinder finder = context.get(entities);
            double x = context.get(argX);
            double y = context.get(argY);
            double z = context.get(argZ);

            finder.find(sender).forEach(e -> e.teleport(new Pos(x, y, z)));
        }, entities, argX, argY, argZ);

        addSyntax((sender, context) -> {
            EntityFinder finder = context.get(entities);
            EntityFinder destinationFinder = context.get(destination);

            Entity desti = destinationFinder.find(sender).getFirst();

            finder.find(sender).forEach(e -> e.teleport(desti.getPosition()));
        }, entities, destination);
    }
}
