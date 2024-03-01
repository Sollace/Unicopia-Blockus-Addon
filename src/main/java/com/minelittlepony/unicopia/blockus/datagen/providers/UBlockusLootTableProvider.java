package com.minelittlepony.unicopia.blockus.datagen.providers;

import java.util.List;

import com.brand.blockus.blocks.base.LargeFlowerPotBlock;
import com.brand.blockus.content.BlockusBlocks;
import com.minelittlepony.unicopia.blockus.UBlockusBlocks;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;

public class UBlockusLootTableProvider extends FabricBlockLootTableProvider {
    public UBlockusLootTableProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate() {
        UBlockusBlocks.WOOD_SETS.forEach(woodset -> {
            addBlockStairsandSlabDrops(woodset.mosaics());
            addBlockStairsandSlabDrops(woodset.mossy());
            woodset.timberFrames().forEach(this::addDrop);
            addDrop(woodset.herringbonePlanks());
            addDrop(woodset.smallLogs());
            addDrop(woodset.smallHedge(), this::stickDrops);
            addPottedLargePlantDrop(woodset.flowerPot());
        });
    }

    public LootTable.Builder stickDrops(Block block) {
        return dropsWithSilkTouchOrShears(block, addSurvivesExplosionCondition(block, ItemEntry.builder(Items.STICK)));
    }

    private void addBlockStairsandSlabDrops(List<Block> trio) {
        addDrop(trio.get(0));
        addDrop(trio.get(1), this::slabDrops);
        addDrop(trio.get(2));
    }

    private LootTable.Builder pottedLargePlantDrops(ItemConvertible plant) {
        return LootTable.builder().pool(addSurvivesExplosionCondition(BlockusBlocks.LARGE_FLOWER_POT, LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1.0F)).with(ItemEntry.builder(BlockusBlocks.LARGE_FLOWER_POT))))
                .pool(addSurvivesExplosionCondition(plant, LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0F))
                        .with(ItemEntry.builder(plant))));
    }

    private void addPottedLargePlantDrop(Block block) {
        addDrop(block, (flowerPot) -> pottedLargePlantDrops(((LargeFlowerPotBlock) flowerPot).getContent()));
    }
}
