package com.minelittlepony.unicopia.blockus;

import java.util.List;
import java.util.stream.Stream;

import com.brand.blockus.registry.content.BlockusBlocks;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.minelittlepony.unicopia.block.UBlocks;
import com.minelittlepony.unicopia.block.zap.ZapBlock;
import com.minelittlepony.unicopia.block.zap.ZapSlabBlock;
import com.minelittlepony.unicopia.block.zap.ZapStairsBlock;
import com.minelittlepony.unicopia.blockus.block.ZapOrientedBlock;
import com.minelittlepony.unicopia.blockus.block.ZapPillarBlock;
import com.minelittlepony.unicopia.blockus.block.ZapPostBlock;
import com.minelittlepony.unicopia.blockus.block.ZapSmallHedgeBlock;
import com.minelittlepony.unicopia.server.world.UTreeGen;

import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public interface UBlockusBlocks {
    BlockFactories ZAP_FACTORIES = new BlockFactories(
            BlockFactory.of(ZapBlock::new),
            BlockFactory.of(ZapSlabBlock::new),
            BlockFactory.of(ZapStairsBlock::new),
            BlockFactory.of(ZapPillarBlock::new),
            (base, settings) -> key -> new ZapSmallHedgeBlock(settings.allowsSpawning(BlockFactories::canSpawnOnLeaves).suffocates(BlockFactories::never).blockVision(BlockFactories::never)),
            BlockFactory.of(ZapOrientedBlock::new),
            BlockFactory.of(ZapPostBlock::new),
            BlockFactories.DEFAULT.largeFlowerPot()
    );
    Registrar REGISTRAR = new Registrar() {
        private final Multimap<String, String> aliases = HashMultimap.create();

        @Override
        public Block register(String name, KeyedFactory<? extends Block> value, boolean addItem) {
            RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, Main.id(name));
            Block block = value.apply(key);
            Registry.register(Registries.BLOCK, key, block);
            if (addItem) {
                Registry.register(Registries.ITEM, Main.id(name), new BlockItem(block, new Item.Settings()));
            }
            aliases.get(name).forEach(alias -> {
                Registries.BLOCK.addAlias(Main.id(alias), Main.id(name));
                if (addItem) {
                    Registries.ITEM.addAlias(Main.id(alias), Main.id(name));
                }
            });
            return block;
        }

        @Override
        public Registrar alias(String oldName, String newName) {
            aliases.put(newName, oldName);
            return this;
        }

    };

    List<BlockusWoodset> WOOD_SETS = List.of(
        new BlockusWoodset("palm", UBlocks.PALM_PLANKS, UBlocks.PALM_SLAB, UBlocks.PALM_LOG, UBlocks.STRIPPED_PALM_LOG, BlockFactories.DEFAULT, REGISTRAR),
        new BlockusWoodset("zap", UBlocks.ZAP_PLANKS, UBlocks.ZAP_SLAB, UBlocks.ZAP_LOG, UBlocks.STRIPPED_ZAP_LOG, ZAP_FACTORIES, REGISTRAR,
            new BlockusWoodset("waxed_zap", UBlocks.WAXED_ZAP_PLANKS, UBlocks.WAXED_ZAP_SLAB, UBlocks.WAXED_ZAP_LOG, UBlocks.WAXED_STRIPPED_ZAP_LOG, BlockFactories.DEFAULT, REGISTRAR)
        ),
        new BlockusWoodset("golden_oak", UBlocks.GOLDEN_OAK_PLANKS, UBlocks.GOLDEN_OAK_SLAB, UBlocks.GOLDEN_OAK_LOG, UBlocks.STRIPPED_GOLDEN_OAK_LOG, BlockFactories.DEFAULT, REGISTRAR)
    );
    List<BlockusPlantset> PLANT_SETS = List.of(
        new BlockusPlantset("palm", UBlocks.PALM_LEAVES, UTreeGen.BANANA_TREE.sapling().get(), WOOD_SETS.get(0).smallLogs(), BlockFactories.DEFAULT, REGISTRAR),
        new BlockusPlantset("zap", UBlocks.ZAP_LEAVES, UTreeGen.ZAP_APPLE_TREE.sapling().get(), WOOD_SETS.get(1).smallLogs(), ZAP_FACTORIES, REGISTRAR),
        new BlockusPlantset("golden_oak", UBlocks.GOLDEN_OAK_LEAVES, UTreeGen.GOLDEN_OAK_TREE.sapling().get(), WOOD_SETS.get(2).smallLogs(), BlockFactories.DEFAULT, REGISTRAR),
        new BlockusPlantset("green_apple", UBlocks.GREEN_APPLE_LEAVES, UTreeGen.GREEN_APPLE_TREE.sapling().get(), BlockusBlocks.SMALL_LOGS.get("oak"), BlockFactories.DEFAULT, REGISTRAR),
        new BlockusPlantset("mango", UBlocks.MANGO_LEAVES, UTreeGen.MANGO_TREE.sapling().get(), BlockusBlocks.SMALL_LOGS.get("oak"), BlockFactories.DEFAULT, REGISTRAR),
        new BlockusPlantset("sour_apple", UBlocks.SOUR_APPLE_LEAVES, UTreeGen.SOUR_APPLE_TREE.sapling().get(), BlockusBlocks.SMALL_LOGS.get("oak"), BlockFactories.DEFAULT, REGISTRAR),
        new BlockusPlantset("sweet_apple", UBlocks.SWEET_APPLE_LEAVES, UTreeGen.SWEET_APPLE_TREE.sapling().get(), BlockusBlocks.SMALL_LOGS.get("oak"), BlockFactories.DEFAULT, REGISTRAR)
    );

    static Stream<BlockusWoodset> woodsets() {
        return WOOD_SETS.stream().flatMap(set -> Stream.concat(Stream.of(set), set.waxedSet().stream()));
    }

    static void bootstrap() {
        WOOD_SETS.forEach(woodset -> {
            woodset.waxablePairs().forEach(pair -> {
                OxidizableBlocksRegistry.registerWaxableBlockPair(pair.getLeft(), pair.getRight());
            });
            woodset.strippablePairs().forEach(pair -> {
                StrippableBlockRegistry.register(pair.getLeft(), pair.getRight());
            });
        });
    }
}
