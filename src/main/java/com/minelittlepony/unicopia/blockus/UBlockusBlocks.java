package com.minelittlepony.unicopia.blockus;

import java.util.List;
import com.brand.blockus.content.BlockusBlocks;
import com.minelittlepony.unicopia.block.UBlocks;
import com.minelittlepony.unicopia.block.zap.ZapBlock;
import com.minelittlepony.unicopia.block.zap.ZapSlabBlock;
import com.minelittlepony.unicopia.block.zap.ZapStairsBlock;
import com.minelittlepony.unicopia.blockus.block.ZapOrientedBlock;
import com.minelittlepony.unicopia.blockus.block.ZapPillarBlock;
import com.minelittlepony.unicopia.blockus.block.ZapSmallHedgeBlock;
import com.minelittlepony.unicopia.server.world.UTreeGen;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface UBlockusBlocks {
    BlockFactories ZAP_FACTORIES = new BlockFactories(
            BlockFactory.of(ZapBlock::new),
            BlockFactory.of(ZapSlabBlock::new),
            ZapStairsBlock::new,
            BlockFactory.of(ZapPillarBlock::new),
            (base, settings) -> new ZapSmallHedgeBlock(settings.allowsSpawning(BlockFactories::canSpawnOnLeaves).suffocates(BlockFactories::never).blockVision(BlockFactories::never)),
            BlockFactory.of(ZapOrientedBlock::new)
    );
    Registrar REGISTRAR = (String name, Block block, boolean addItem) -> {
        Registry.register(Registries.BLOCK, Main.id(name), block);
        if (addItem) {
            Registry.register(Registries.ITEM, Main.id(name), new BlockItem(block, new Item.Settings()));
        }
        return block;
    };

    List<BlockusWoodset> WOOD_SETS = List.of(
        new BlockusWoodset("palm", UBlocks.PALM_PLANKS, UBlocks.PALM_SLAB, UBlocks.PALM_LOG, BlockFactories.DEFAULT, REGISTRAR),
        new BlockusWoodset("zap", UBlocks.ZAP_PLANKS, UBlocks.ZAP_SLAB, UBlocks.ZAP_LOG, ZAP_FACTORIES, REGISTRAR)
    );
    Block GOLDEN_OAK_SMALL_LOGS = REGISTRAR.register("golden_oak_small_logs", BlockFactories.DEFAULT.pillar().create(UBlocks.GOLDEN_OAK_LOG));
    List<BlockusPlantset> PLANT_SETS = List.of(
        new BlockusPlantset("palm", UBlocks.PALM_LEAVES, UTreeGen.BANANA_TREE.sapling().get(), WOOD_SETS.get(0).smallLogs(), BlockFactories.DEFAULT, REGISTRAR),
        new BlockusPlantset("zap", UBlocks.ZAP_LEAVES, UTreeGen.ZAP_APPLE_TREE.sapling().get(), WOOD_SETS.get(1).smallLogs(), ZAP_FACTORIES, REGISTRAR),
        new BlockusPlantset("golden_oak", UBlocks.GOLDEN_OAK_LEAVES, UTreeGen.GOLDEN_APPLE_TREE.sapling().get(), GOLDEN_OAK_SMALL_LOGS, BlockFactories.DEFAULT, REGISTRAR),
        new BlockusPlantset("green_apple", UBlocks.GREEN_APPLE_LEAVES, UTreeGen.GREEN_APPLE_TREE.sapling().get(), BlockusBlocks.OAK_SMALL_LOGS, BlockFactories.DEFAULT, REGISTRAR),
        new BlockusPlantset("mango", UBlocks.MANGO_LEAVES, UTreeGen.MANGO_TREE.sapling().get(), BlockusBlocks.OAK_SMALL_LOGS, BlockFactories.DEFAULT, REGISTRAR),
        new BlockusPlantset("sour_apple", UBlocks.SOUR_APPLE_LEAVES, UTreeGen.SOUR_APPLE_TREE.sapling().get(), BlockusBlocks.OAK_SMALL_LOGS, BlockFactories.DEFAULT, REGISTRAR),
        new BlockusPlantset("sweet_apple", UBlocks.SWEET_APPLE_LEAVES, UTreeGen.SWEET_APPLE_TREE.sapling().get(), BlockusBlocks.OAK_SMALL_LOGS, BlockFactories.DEFAULT, REGISTRAR)
    );

    static void bootstrap() { }
}
