package com.minelittlepony.unicopia.blockus.datagen.providers;

import java.util.List;
import java.util.Optional;

import com.brand.blockus.datagen.providers.BlockusModelProvider;
import com.minelittlepony.unicopia.block.UBlocks;
import com.minelittlepony.unicopia.blockus.UBlockusBlocks;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.BlockStateVariant;
import net.minecraft.data.client.BlockStateVariantMap;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.ModelIds;
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
    private final Model TREE_POT_MODEL = new Model(Optional.of(Identifier.of("blockus:block/tinted_large_tree_pot")), Optional.empty(), LOG_TOP, LOG, LEAVES, SOIL);

    public UBlockusModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator modelGenerator) {
        UBlockusBlocks.WOOD_SETS.forEach(woodset -> {
            registerBSSModels(woodset.mosaics(), modelGenerator);
            registerBSSModels(woodset.mossy(), modelGenerator);
            registerAxisRotatedCubeColumn(modelGenerator, woodset.smallLogs());
            modelGenerator.registerSimpleCubeAll(woodset.herringbonePlanks());

            registerPost(modelGenerator, woodset.posts().get(0), woodset.log());
            registerPost(modelGenerator, woodset.posts().get(1), woodset.strippedLog());

            modelGenerator.registerSimpleCubeAll(woodset.timberFrames().get(0));
            registerDiagonalTimberFrame(modelGenerator, woodset.timberFrames().get(1));
            modelGenerator.registerSimpleCubeAll(woodset.timberFrames().get(2));

            woodset.waxedSet().ifPresent(waxedset -> {
                registerParentedBSSModels(woodset.mosaics(), waxedset.mosaics(), modelGenerator);
                registerParentedBSSModels(woodset.mossy(), waxedset.mossy(), modelGenerator);

                registerPost(modelGenerator, waxedset.posts().get(0), woodset.log());
                registerPost(modelGenerator, waxedset.posts().get(1), woodset.strippedLog());

                registerParentedAxisRotatedCubeColumn(modelGenerator, woodset.smallLogs(), waxedset.smallLogs());
                modelGenerator.registerParented(woodset.herringbonePlanks(), waxedset.herringbonePlanks());

                modelGenerator.registerParented(woodset.timberFrames().get(0), waxedset.timberFrames().get(0));
                registerParentedDiagonalTimberFrame(modelGenerator, woodset.timberFrames().get(1), waxedset.timberFrames().get(1));
                modelGenerator.registerParented(woodset.timberFrames().get(2), waxedset.timberFrames().get(2));
            });
        });
        registerAxisRotatedCubeColumn(modelGenerator, UBlockusBlocks.GOLDEN_OAK_SMALL_LOGS);
        UBlockusBlocks.PLANT_SETS.forEach(plantset -> {
            Block leaves = plantset.leaves() == UBlocks.MANGO_LEAVES ? Blocks.JUNGLE_LEAVES : plantset.leaves();
            registerPottedPlant(modelGenerator, plantset.smallLogs(), leaves, plantset.flowerPot());
            registerSmallHedge(modelGenerator, plantset.smallHedge(), leaves);
        });
    }

    @Override
    public void generateItemModels(ItemModelGenerator modelGenerator) {
    }

    private void registerBSSModels(List<Block> trio, BlockStateModelGenerator modelGenerator) {
        modelGenerator.registerCubeAllModelTexturePool(trio.get(0)).slab(trio.get(1)).stairs(trio.get(2));
    }

    private void registerParentedBSSModels(List<Block> modelSource, List<Block> child, BlockStateModelGenerator modelGenerator) {
        modelGenerator.registerParented(modelSource.get(0), child.get(0));

        Identifier full = ModelIds.getBlockModelId(modelSource.get(0));
        Identifier slab = ModelIds.getBlockModelId(modelSource.get(1));
        Identifier slabTop = ModelIds.getBlockSubModelId(modelSource.get(1), "_top");
        modelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(child.get(1), slab, slabTop, full));
        modelGenerator.registerParentedItemModel(child.get(1), slab);

        Identifier inner = ModelIds.getBlockSubModelId(modelSource.get(2), "_inner");
        Identifier straight = ModelIds.getBlockModelId(modelSource.get(2));
        Identifier outer = ModelIds.getBlockSubModelId(modelSource.get(2), "_outer");
        modelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createStairsBlockState(child.get(2), inner, straight, outer));
        modelGenerator.registerParentedItemModel(child.get(2), straight);
    }

    public void registerParentedAxisRotatedCubeColumn(BlockStateModelGenerator modelGenerator, Block modelSource, Block child) {
        Identifier vertical = ModelIds.getBlockModelId(modelSource);
        Identifier horizontal = ModelIds.getBlockSubModelId(modelSource, "_horizontal");
        modelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(child, vertical, horizontal));
        modelGenerator.registerParentedItemModel(child, vertical);
    }

    private void registerParentedDiagonalTimberFrame(BlockStateModelGenerator modelGenerator, Block modelSource, Block child) {
        Identifier modelId = ModelIds.getBlockModelId(modelSource);
        modelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(child).coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING)
                .register(Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, modelId))
                .register(Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                .register(Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                .register(Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.Y, VariantSettings.Rotation.R90))));
        modelGenerator.registerParentedItemModel(child, modelId);
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
