package online.bteisrael.satellite.command;

import net.kyori.adventure.text.Component;
import net.minestom.server.command.CommandSender;
import net.minestom.server.utils.callback.CommandCallback;
import org.jetbrains.annotations.NotNull;

/**
 * Handles unknown (non-existent) commands
 */
public class UnknownCommand implements CommandCallback {
    @Override
    public void apply(CommandSender sender, @NotNull String command) {
        sender.sendMessage(Component.text("The command \"" + command + "\" is not recognized."));
    }
}
