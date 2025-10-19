package online.bteisrael.satellite.command;

import net.buildtheearth.terraminusminus.TerraConstants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import online.bteisrael.satellite.SatelliteServer;

public class VersionCommand extends Command {
    public VersionCommand() {
        super("version", "ver", "pl", "v",
                "plugins", "icanhasbukkit", "icanhassatellite",
                "bukkit:ver", "bukkit:version", "bukkit:pl",
                "bukkit:plugins");

        addSyntax((sender, context) -> {
            switch (context.getCommandName()) {
                case "version", "ver", "v",
                     "pl", "plugins" -> {
                    sender.sendMessage("This server is running on Satellite, a Minestom-based Minecraft Server " +
                            "built specifically for BTE.\n" +
                            "This server is running on Satellite Version: " + SatelliteServer.SATELLITE_VERSION + "\n" +
                            "Minestom version: " + SatelliteServer.MINESTOM_VERSION + "\n" +
                            "Terra Minus Minus: " + TerraConstants.LIB_VERSION + "\n" +
                            "and Minecraft: " + MinecraftServer.VERSION_NAME
                    );
                }

                case "icanhasbukkit" -> {
                    sender.sendMessage(Component.text(
                            "No you can't not not has Bukkit, " +
                            "Since Satellite is not a Bukkit-derivative project, " +
                            "and you cannot have Satellite, either, unless you run and host it yourself").append(
                                    Component.text("[CLICK HERE]")
                                            .color(NamedTextColor.AQUA)
                                            .clickEvent(ClickEvent.openUrl("https://github.com/Build-Israel/Satellite"))
                            ).append(
                                    Component.text(". but you can run /version to see what " +
                                            "version of Satellite and Minestom this server is running on")
                            )
                    );
                }
                case "icanhassatellite" -> {
                    sender.sendMessage("I don't know if you can has Satellite, " +
                            "but you would need to host it yourself, " +
                            "unless you're a server admin of this server, " +
                            "but if you're not the owner, please request " +
                            "permission from the owner before changing stuff. " +
                            "You can find the Source Code and compiled Executables (Java Archives (JAR)), " +
                            "at the github link: https://github.com/Build-Israel/Satellite ");
                }
                default -> {
                    sender.sendMessage(Component.text("Did you think you Could Outsmart me (NotAMojangDev)?\n" +
                            "I mean Maybe... but these commands didn't exist before I implemented them.\n" +
                            "Trying the \"bukkit:\" prefix won't work here because Satellite " +
                            "is not a Bukkit-derivative project, It's a Minestom-based project." +
                            "Now try running \"/" + context.getCommandName() + "\" without \"bukkit:\". And " +
                                    "You'll see what you wanted")
                            .color(NamedTextColor.RED));
                }
            }
        });
    }
}
