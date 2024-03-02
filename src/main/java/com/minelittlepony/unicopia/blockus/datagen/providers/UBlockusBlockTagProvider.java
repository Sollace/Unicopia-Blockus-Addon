package com.minelittlepony.unicopia.blockus.datagen.providers;

import java.util.concurrent.CompletableFuture;

import com.brand.blockus.utils.tags.BlockusBlockTags;
import com.minelittlepony.unicopia.Unicopia;
import com.minelittlepony.unicopia.blockus.UBlockusBlocks;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class UBlockusBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public UBlockusBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries) {
        UBlockusBlocks.woodsets().forEach(woodset -> {
            getOrCreateTagBuilder(BlockTags.STAIRS).add(woodset.mosaics().get(2));
            getOrCreateTagBuilder(BlockTags.SLABS).add(woodset.mosaics().get(1));
            getOrCreateTagBuilder(BlockTags.STAIRS).add(woodset.mossy().get(2));
            getOrCreateTagBuilder(BlockTags.SLABS).add(woodset.mossy().get(1));
            getOrCreateTagBuilder(BlockusBlockTags.TIMBER_FRAMES).add(woodset.timberFrames().toArray(Block[]::new));
            getOrCreateTagBuilder(BlockusBlockTags.ALL_WOODEN_MOSAICS).add(woodset.mosaics().toArray(Block[]::new));
            getOrCreateTagBuilder(BlockusBlockTags.ALL_MOSSY_PLANKS).add(woodset.mossy().toArray(Block[]::new));
            getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("c", "planks_that_burn")))
                .add(woodset.herringbonePlanks())
                .add(woodset.mosaics().get(0))
                .add(woodset.mossy().get(0));
            getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Unicopia.id(woodset.id() + "_logs"))).add(woodset.smallLogs());
            getOrCreateTagBuilder(BlockTags.PLANKS).add(woodset.herringbonePlanks());
        });
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Unicopia.id("golden_oak_logs"))).add(UBlockusBlocks.GOLDEN_OAK_SMALL_LOGS);
        UBlockusBlocks.PLANT_SETS.forEach(plantset -> {
            getOrCreateTagBuilder(BlockusBlockTags.LARGE_FLOWER_POTS).add(plantset.flowerPot());
            getOrCreateTagBuilder(BlockusBlockTags.SMALL_HEDGES).add(plantset.smallHedge());
        });
    }
}
