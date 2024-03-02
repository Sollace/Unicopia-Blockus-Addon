package com.minelittlepony.unicopia.blockus.client;

import com.minelittlepony.unicopia.blockus.UBlockusBlocks;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.client.render.RenderLayer;

public class Main implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        UBlockusBlocks.PLANT_SETS.forEach(plantSet -> {
            registerLeafyBlock(plantSet.smallHedge(), plantSet.leaves());
            registerLeafyBlock(plantSet.flowerPot(), plantSet.leaves());
        });
    }

    private static void registerLeafyBlock(Block block, Block base) {
        ColorProviderRegistry.BLOCK.register((block1, pos, world, layer) -> {
            BlockColorProvider provider = ColorProviderRegistry.BLOCK.get(base);
            return provider == null ? -1 : provider.getColor(base.getDefaultState(), pos, world, layer);
        }, block);

        ColorProviderRegistry.ITEM.register((item, layer) -> {
            ItemColorProvider provider = ColorProviderRegistry.ITEM.get(base);
            return provider == null ? -1 : provider.getColor(base.asItem().getDefaultStack(), layer);
        }, block.asItem());
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(), block);
    }
}
