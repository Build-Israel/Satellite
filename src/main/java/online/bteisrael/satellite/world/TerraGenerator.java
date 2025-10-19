package online.bteisrael.satellite.world;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class TerraGenerator implements Generator {

    private final EarthGeneratorSettings generatorSettings;
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
        this.cache = CacheBuilder.newBuilder().
                expireAfterAccess(5, TimeUnit.MINUTES)
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

                Pos ground = new Pos(x + chunkStart.blockX(), groundY, z + chunkStart.blockZ());

                if (waterY > groundY) {
                    for (int i = groundY; i <= waterY; ++i) {
                        unit.modifier().setBlock(ground.withY(i), Block.WATER);
                    }
                }
                unit.modifier().setBlock(ground.asBlockVec(), waterY > groundY ? Block.DIRT : Block.MOSS_BLOCK);
            }
        }
    }

    public @Nullable CachedChunkData getChunkData(int chunkX, int chunkZ) {
        try {
            return this.cache.get(new ChunkPos(chunkX, chunkZ)).get();
        } catch (ExecutionException | InterruptedException e) {
            return null;
        }
    }

}
