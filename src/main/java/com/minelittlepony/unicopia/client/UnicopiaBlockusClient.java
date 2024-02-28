package com.minelittlepony.unicopia.client;

import com.minelittlepony.unicopia.UBlockusBlocks;
import com.minelittlepony.unicopia.block.UBlocks;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.client.render.RenderLayer;

public class UnicopiaBlockusClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        registerBlockColor(UBlockusBlocks.PALM_SMALL_HEDGE, UBlocks.PALM_LEAVES);
        registerBlockColor(UBlockusBlocks.POTTED_PALM.block, UBlocks.PALM_LEAVES);

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(),
            UBlockusBlocks.PALM_SMALL_HEDGE,
            UBlockusBlocks.POTTED_PALM.block
        );
    }

    public void registerBlockColor(Block block, Block templateBlock) {
        ColorProviderRegistry.BLOCK.register((block1, pos, world, layer) -> {
            BlockColorProvider provider = ColorProviderRegistry.BLOCK.get(templateBlock);
            return provider == null ? -1 : provider.getColor(block1, pos, world, layer);
        }, block);

        ColorProviderRegistry.ITEM.register((item, layer) -> {
            ItemColorProvider provider = ColorProviderRegistry.ITEM.get(templateBlock);
            return provider == null ? -1 : provider.getColor(item, layer);
        }, block.asItem());
    }
}
