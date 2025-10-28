package online.bteisrael.satellite.world;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import lombok.Getter;
import net.buildtheearth.terraminusminus.generator.CachedChunkData;
import net.buildtheearth.terraminusminus.generator.ChunkDataLoader;
import net.buildtheearth.terraminusminus.generator.EarthGeneratorSettings;
import net.buildtheearth.terraminusminus.projection.GeographicProjection;
import net.buildtheearth.terraminusminus.projection.transform.OffsetProjectionTransform;
import net.buildtheearth.terraminusminus.substitutes.ChunkPos;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.generator.GenerationUnit;
import net.minestom.server.instance.generator.Generator;
import online.bteisrael.satellite.util.tmm.TerraMinestom;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * The Terra World Generator, used to generate a model of the Real World.
 */
public class TerraGenerator implements Generator {

    private final EarthGeneratorSettings generatorSettings;
    @Getter
    private final GeographicProjection projection;
    private final LoadingCache<@NotNull ChunkPos, @NotNull CompletableFuture<CachedChunkData>> cache;

    public TerraGenerator() {
        EarthGeneratorSettings generatorSettings = EarthGeneratorSettings.parse(EarthGeneratorSettings.BTE_DEFAULT_SETTINGS);
        this.projection = new OffsetProjectionTransform(
                generatorSettings.projection(),
                0, // TODO: Make configurable
                0 // TODO: Make configurable
        );
        this.generatorSettings = generatorSettings
                .withProjection(projection);
        // TODO: Make Biomes configurable
        this.cache = CacheBuilder.newBuilder()
                .expireAfterAccess(5, TimeUnit.MINUTES)
                .softValues()
                .build(new ChunkDataLoader(this.generatorSettings));
    }

    @Override
    public void generate(@NotNull GenerationUnit unit) {
        CachedChunkData terraData = this.getChunkData(unit.absoluteStart().chunkX(), unit.absoluteStart().chunkZ());
        if (terraData == null) return;
        final Point chunkStart = unit.absoluteStart();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int groundY = terraData.groundHeight(x, z);
                int waterY = terraData.waterHeight(x, z);
                boolean placeWater = waterY > groundY;

                Pos ground = new Pos(x + chunkStart.blockX(), groundY, z + chunkStart.blockZ());

                // Adding 1, 0, 1 is needed to add for some reason.
                // My assumption would be because it uses >/<  instead of >=/<= in the loop.
                // Will submit a PR to Minestom to fix this behavior - NotAMojangDev
                if (placeWater) unit.modifier().fill(ground, ground.withY(waterY).add(1,0,1), Block.WATER);
                unit.modifier().fill(ground.withY(unit.absoluteStart().blockY()), ground.add(1, 0, 1), Block.STONE);
                unit.modifier().setBlock(ground.asBlockVec(), placeWater ? Block.DIRT : this.getSurfaceBlock(terraData, x, z));
                unit.modifier().fillHeight(unit.absoluteStart().blockY(), unit.absoluteStart().blockY() + 1, Block.BEDROCK);
            }
        }
    }

    /**
     * get the {@link CachedChunkData} of the chunk corresponding to (chunkX, chunkZ)
     * @param chunkX the x coordinate of the chunk
     * @param chunkZ the z coordinate of the chunk
     * @return the Corresponding {@link CachedChunkData} data
     */
    public @Nullable CachedChunkData getChunkData(int chunkX, int chunkZ) {
        try {
            return this.cache.get(new ChunkPos(chunkX, chunkZ)).get();
        } catch (ExecutionException | InterruptedException e) {
            return null;
        }
    }

    /**
     * Turn x and z coordinates to a Minestom surface block as a Minestom block <br>
     * TODO: Add configuration
     * @param data The data to reference
     * @param x The (chunk relative) x coordinate of the block
     * @param z The (chunk relative) z coordinate of the block
     * @return the Minestom {@link Block} corresponding to the Surface block at {x, z}
     */
    private Block getSurfaceBlock(@NotNull CachedChunkData data, int x, int z) {
        Block block = TerraMinestom.toMinestomBlock(data.surfaceBlock(x, z));
        return block == null ? Block.MOSS_BLOCK : block;
    }

}
