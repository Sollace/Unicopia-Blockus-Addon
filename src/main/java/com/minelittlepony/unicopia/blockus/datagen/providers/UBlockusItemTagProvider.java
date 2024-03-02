package com.minelittlepony.unicopia.blockus.datagen.providers;

import java.util.concurrent.CompletableFuture;

import com.brand.blockus.utils.tags.BlockusBlockTags;
import com.brand.blockus.utils.tags.BlockusItemTags;
import com.minelittlepony.unicopia.Unicopia;
import com.minelittlepony.unicopia.blockus.UBlockusBlocks;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class UBlockusItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public UBlockusItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture, BlockTagProvider blockTagProvider) {
        super(output, registriesFuture, blockTagProvider);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries) {
        UBlockusBlocks.woodsets().forEach(woodset -> {
            getOrCreateTagBuilder(BlockusItemTags.PLANKS_THAT_BURN).add(woodset.planks().asItem());
            getOrCreateTagBuilder(BlockusItemTags.HERRINGBONE_PLANKS_THAT_BURN).add(woodset.herringbonePlanks().asItem());
            getOrCreateTagBuilder(BlockusItemTags.WOODEN_MOSAIC_THAT_BURN).add(woodset.mosaics().get(0).asItem());
            copy(TagKey.of(RegistryKeys.BLOCK, new Identifier("c", "planks_that_burn")), TagKey.of(RegistryKeys.ITEM, new Identifier("c", "planks_that_burn")));
            copy(TagKey.of(RegistryKeys.BLOCK, Unicopia.id(woodset.id() + "_logs")), TagKey.of(RegistryKeys.ITEM, Unicopia.id(woodset.id() + "_logs")));
            getOrCreateTagBuilder(ItemTags.NON_FLAMMABLE_WOOD).add(woodset.mossy().stream().map(Block::asItem).toArray(Item[]::new));
        });
        copy(BlockusBlockTags.SMALL_HEDGES, BlockusItemTags.SMALL_HEDGES);
        copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
        copy(BlockTags.PLANKS, ItemTags.PLANKS);
        copy(BlockTags.STAIRS, ItemTags.STAIRS);
        copy(BlockTags.SLABS, ItemTags.SLABS);
    }
}
