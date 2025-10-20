package online.bteisrael.satellite.world;

import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.generator.GenerationUnit;
import net.minestom.server.instance.generator.Generator;

import java.util.ArrayList;

public class FlatworldGenerator implements Generator {

    private static final ArrayList<Block> DEFAULT_WORLD = new ArrayList<>();
    static {
        DEFAULT_WORLD.addFirst(Block.GRASS_BLOCK);
        DEFAULT_WORLD.addFirst(Block.DIRT);
        DEFAULT_WORLD.addFirst(Block.DIRT);
        DEFAULT_WORLD.addFirst(Block.DIRT);
        DEFAULT_WORLD.addFirst(Block.BEDROCK);
    }

    @Override
    public void generate(GenerationUnit unit) {
        for (int i = 0; i < DEFAULT_WORLD.size(); i++) {
            unit.modifier().fillHeight(i, i + 1, DEFAULT_WORLD.get(i));
        }

    }
}
