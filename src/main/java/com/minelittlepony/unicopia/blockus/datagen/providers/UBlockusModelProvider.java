package com.minelittlepony.unicopia.blockus.datagen.providers;

import java.util.List;
import java.util.Optional;

import com.brand.blockus.data.providers.BlockusModelProvider;
import com.minelittlepony.unicopia.blockus.UBlockusBlocks;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.BlockStateVariant;
import net.minecraft.data.client.BlockStateVariantMap;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TextureKey;
import net.minecraft.data.client.TextureMap;
import net.minecraft.data.client.VariantSettings;
import net.minecraft.data.client.VariantsBlockStateSupplier;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

public class UBlockusModelProvider extends BlockusModelProvider {
    private final TextureKey LOG_TOP = TextureKey.of("log_top");
    private final TextureKey LOG = TextureKey.of("log");
    private final TextureKey LEAVES = TextureKey.of("leaves");
    private final TextureKey SOIL = TextureKey.of("soil");
    private final Model TREE_POT_MODEL = new Model(Optional.of(new Identifier("blockus:block/tinted_large_tree_pot")), Optional.empty(), LOG_TOP, LOG, LEAVES, SOIL);

    public UBlockusModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator modelGenerator) {
        UBlockusBlocks.WOOD_SETS.forEach(woodset -> {
            offerBSSModels(woodset.mosaics(), modelGenerator);
            offerBSSModels(woodset.mossy(), modelGenerator);
            registerAxisRotatedCubeColumn(modelGenerator, woodset.smallLogs());
            modelGenerator.registerSimpleCubeAll(woodset.herringbonePlanks());
            registerSmallHedge(modelGenerator, woodset.smallHedge(), woodset.leaves());
            modelGenerator.registerSimpleCubeAll(woodset.timberFrames().get(0));
            registerDiagonalTimberFrame(modelGenerator, woodset.timberFrames().get(1));
            modelGenerator.registerSimpleCubeAll(woodset.timberFrames().get(2));
            registerPottedPlant(modelGenerator, woodset.smallLogs(), woodset.leaves(), woodset.flowerPot());
        });
    }

    @Override
    public void generateItemModels(ItemModelGenerator modelGenerator) {
    }

    private void offerBSSModels(List<Block> trio, BlockStateModelGenerator modelGenerator) {
        modelGenerator.registerCubeAllModelTexturePool(trio.get(0)).slab(trio.get(1)).stairs(trio.get(2));
    }

    private void registerDiagonalTimberFrame(BlockStateModelGenerator modelGenerator, Block block) {
        Identifier left = TextureMap.getSubId(block, "_right");
        Identifier right = TextureMap.getSubId(block, "_left");
        Identifier modelId = Models.CUBE.upload(block, new TextureMap()
                .put(TextureKey.PARTICLE, left)
                .put(TextureKey.NORTH, left).put(TextureKey.SOUTH, left)
                .put(TextureKey.EAST, left).put(TextureKey.WEST, right)
                .put(TextureKey.DOWN, right).put(TextureKey.UP, right), modelGenerator.modelCollector);
        modelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING)
            .register(Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, modelId))
            .register(Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.Y, VariantSettings.Rotation.R180))
            .register(Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.Y, VariantSettings.Rotation.R270))
            .register(Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.Y, VariantSettings.Rotation.R90))));
        modelGenerator.registerParentedItemModel(block, modelId);
    }

    private void registerPottedPlant(BlockStateModelGenerator modelGenerator, Block logs, Block leaves, Block block) {
        modelGenerator.registerSingleton(block, new TextureMap()
                .put(LOG_TOP, TextureMap.getSubId(logs, "_top"))
                .put(LOG, TextureMap.getId(logs))
                .put(LEAVES, TextureMap.getId(leaves))
                .put(SOIL, TextureMap.getId(Blocks.DIRT)), TREE_POT_MODEL);
    }
}
