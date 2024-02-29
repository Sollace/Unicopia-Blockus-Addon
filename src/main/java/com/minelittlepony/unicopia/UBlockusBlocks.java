package com.minelittlepony.unicopia;

import java.util.List;

import com.brand.blockus.blocks.base.OrientableBlockBase;
import com.brand.blockus.blocks.base.SmallHedgeBlock;
import com.minelittlepony.unicopia.block.UBlocks;
import com.minelittlepony.unicopia.block.zap.ZapBlock;
import com.minelittlepony.unicopia.block.zap.ZapSlabBlock;
import com.minelittlepony.unicopia.block.zap.ZapStairsBlock;
import com.minelittlepony.unicopia.server.world.UTreeGen;

import net.minecraft.block.Block;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface UBlockusBlocks {
    BlockFactories ELECTRIFIED_BLOCK_FACTORIES = new BlockFactories(
            BlockFactory.of(ZapBlock::new),
            BlockFactory.of(ZapSlabBlock::new),
            ZapStairsBlock::new,
            BlockFactory.of(PillarBlock::new),
            (base, settings) -> new SmallHedgeBlock(settings.allowsSpawning(BlockFactories::canSpawnOnLeaves).suffocates(BlockFactories::never).blockVision(BlockFactories::never)),
            BlockFactory.of(OrientableBlockBase::new)
    );

    List<BlockusWoodset> WOOD_SETS = List.of(
        new BlockusWoodset("palm", UBlocks.PALM_PLANKS, UBlocks.PALM_SLAB, UBlocks.PALM_LOG, UBlocks.PALM_LEAVES, UTreeGen.BANANA_TREE.sapling().get(), BlockFactories.DEFAULT, UBlockusBlocks::register)
    );

    static void bootstrap() { }

    static Block register(String name, Block block, boolean addItem) {

        Registry.register(Registries.BLOCK, UnicopiaBlockus.id(name), block);
        if (addItem) {
            Registry.register(Registries.ITEM, UnicopiaBlockus.id(name), new BlockItem(block, new Item.Settings()));
        }

        return block;
    }
}
