package online.bteisrael.satellite.util.tmm;

import lombok.experimental.UtilityClass;
import net.buildtheearth.terraminusminus.substitutes.*;
import net.buildtheearth.terraminusminus.substitutes.exceptions.TranslateToSubstituteException;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.block.Block;
import net.minestom.server.registry.RegistryKey;
import net.minestom.server.world.biome.Biome;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.kyori.adventure.key.Key;

import java.util.Map;

/**
 * Compatibility methods to translate between Terra-- internal Minecraft objects into Minestom objects.
 * <br>
 * Originally written for Bukkit by <a href="https://github.com/SmylerMC">Smyler</a>
 * <p></p>
 *
 * Adapted to Minestom by <a href="https://github.com/NotAMojangDev">NotAMojngDev</a>
 */
@UtilityClass
public class TerraMinestom {

    /**
     * Translates internal Terra-- {@link Identifier} to (Kyori) Adventure's {@link Key}.
     * Conserves nullness.
     * <br>
     * This method has no form of caching and may create a new instance of {@link Key} for each call.
     *
     * @param identifier the Terra-- {@link Identifier}
     * @return the (Kyori) Adventure {@link Key}
     */
    @Contract("!null -> !null; null -> null")
    public static @Nullable Key toMinestomNamespace(@Nullable Identifier identifier) {
        if (identifier == null) return null;
        return Key.key(identifier.namespace(), identifier.path());
    }

    /**
     * Translate (Kyori) Adventure's {@link Key} to Terra-- internal {@link Identifier}.
     * Conserves nullness.
     *
     * @param key the (Kyori) Adventure {@link Key}
     * @return the Terra-- {@link Identifier}
     */
    @Contract("!null -> !null; null -> null")
    public static @Nullable Identifier fromMinestomNamespace(@Nullable Key key) {
        if (key == null) return null;
        return new Identifier(key.namespace(), key.value());
    }

    /**
     * Translates an internal Terra-- {@link BlockPos} to Minestom's {@link Pos}.
     * Conserves nullness.
     * <br>
     * The {@link Pos} yaw and pitch are 0.
     *
     * @param blockPos the Terra-- {@link BlockPos}
     * @return the Minestom {@link Pos}
     */
    @Contract("!null -> new; null -> null")
    public static @Nullable Pos toMinestomPos(@Nullable BlockPos blockPos) {
        if (blockPos == null) return null;
        return new Pos(blockPos.x, blockPos.y, blockPos.z, 0f ,0f);
    }

    /**
     * Translate a Minestom {@link Pos} to an internal Terra-- {@link BlockPos}.
     * Conserves nullness.
     *
     * @param pos the Minestom {@link Pos}
     * @return the internal Terra-- {@link BlockPos}
     *
     * @throws TranslateToSubstituteException if one of the {@link Pos pos}'s x, y or z components is not a finite double
     */
    @Contract("!null -> new; null -> null")
    public static @Nullable BlockPos fromMinestomPos(@Nullable Pos pos)
            throws TranslateToSubstituteException {
        if (pos == null) return null;
        if (!Double.isFinite(pos.x()) ||
            !Double.isFinite(pos.y()) ||
            !Double.isFinite(pos.z())) {
            throw new TranslateToSubstituteException(pos, pos.getClass(), "One of the x, y, or z is not finite");
        }
        return new BlockPos(pos.blockX(), pos.blockY(), pos.blockZ());
    }

    /**
     * Translates Terra-- internal {@link net.buildtheearth.terraminusminus.substitutes.Biome} into a Minestom {@link Biome}.
     * Conserves nullness.
     *
     * @param biome the Terra-- {@link net.buildtheearth.terraminusminus.substitutes.Biome}
     * @return the Minestom {@link Biome}
     */
    @Contract("!null -> !null; null -> null")
    public static @Nullable Biome toMinestomBiome(@Nullable net.buildtheearth.terraminusminus.substitutes.Biome biome) {
        if (biome == null) return null;
        return MinecraftServer.getBiomeRegistry().get(toMinestomNamespace(biome.identifier()));
    }

    /**
     * Translates Minestom {@link Biome} into Terra-- internal {@link net.buildtheearth.terraminusminus.substitutes.Biome}.
     * Conserves nullness.
     *
     * @param biome the Minestom {@link Biome}
     * @return the Terra-- {@link net.buildtheearth.terraminusminus.substitutes.Biome}
     */
    @Contract("!null -> !null; null -> null")
    public static net.buildtheearth.terraminusminus.substitutes.Biome fromMinestomBiome(Biome biome) {
        if (biome == null) return null;
        RegistryKey<@NotNull Biome> msRegKey = MinecraftServer.getBiomeRegistry().getKey(biome);
        if (msRegKey == null) {
            return null;
        }
        return net.buildtheearth.terraminusminus.substitutes.Biome
                .byId(fromMinestomNamespace(msRegKey.key()));
    }

    @Contract("!null -> !null; null -> null")
    public static @Nullable Block toMinestomBlock(BlockState blockState) {
        if (blockState == null) return null;
        Identifier blockIdentifier = blockState.getBlock();
        Block block = Block.fromKey(blockIdentifier.namespace + ":" + blockIdentifier.path);
        if (block == null) return null;
        for (Map.Entry<String, BlockPropertyValue> property : blockState.getProperties().entrySet()) {
            block = block.withProperty(property.getKey(), String.valueOf(property.getValue().getAsBoolean()));
        }

        return block;
    }

    @Contract("!null -> !null; null -> null")
    public static @Nullable BlockState fromMinestomBlock(Block block) {
        if (block == null) return null;
        BlockStateBuilder blockState = new BlockStateBuilder()
                .setBlock(fromMinestomNamespace(block.key()));
        for (Map.Entry<String, String> property : block.properties().entrySet()) {
            switch (TerraMinestom.parseValue(property.getValue())) {
                case Integer i -> blockState.setProperty(property.getKey(), i);
                case Boolean b -> blockState.setProperty(property.getKey(), b);
                case String s -> blockState.setProperty(property.getKey(), s);
                case null, default -> {/* ignore */}
            }
        }
        return blockState.build();
    }

    @Contract("!null -> !null; null -> null")
    private static Object parseValue(String value) {
        if (value == null) return null;

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) { /* ignored */ }

        if (value.matches("(true)|(false)")) return Boolean.parseBoolean(value);
        return value;
    }
}
