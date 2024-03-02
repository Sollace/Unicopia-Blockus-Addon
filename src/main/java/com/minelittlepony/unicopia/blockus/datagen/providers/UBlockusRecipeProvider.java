package com.minelittlepony.unicopia.blockus.datagen.providers;

import java.util.List;
import java.util.function.Consumer;

import com.brand.blockus.data.providers.BlockusRecipeProvider;
import com.minelittlepony.unicopia.block.UBlocks;
import com.minelittlepony.unicopia.blockus.UBlockusBlocks;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.recipe.book.RecipeCategory;

public class UBlockusRecipeProvider extends FabricRecipeProvider {
    public UBlockusRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        UBlockusBlocks.woodsets().forEach(woodset -> {
            offerBSSRecipes(woodset.mosaics(), exporter);
            offerBSSRecipes(woodset.mossy(), exporter);
            BlockusRecipeProvider.offerSmallLogsRecipe(exporter, woodset.smallLogs(), woodset.log());
            BlockusRecipeProvider.offerMosaicRecipe(exporter, RecipeCategory.DECORATIONS, woodset.mosaics().get(0), woodset.slab());
            BlockusRecipeProvider.offerHerringBoneRecipe(exporter, woodset.herringbonePlanks(), woodset.planks());
            BlockusRecipeProvider.offerMossyRecipe(exporter, woodset.mossy().get(0), woodset.planks());
            BlockusRecipeProvider.createTimberFramesRecipes(exporter, woodset.planks(), woodset.timberFrames().get(0), woodset.timberFrames().get(1), woodset.timberFrames().get(2));
        });
        BlockusRecipeProvider.offerSmallLogsRecipe(exporter, UBlockusBlocks.GOLDEN_OAK_SMALL_LOGS, UBlocks.GOLDEN_OAK_LOG);
        UBlockusBlocks.PLANT_SETS.forEach(plantset -> {
            BlockusRecipeProvider.offerSmallHedgesRecipe(exporter, plantset.smallHedge(), plantset.leaves());
        });
    }

    private static void offerBSSRecipes(List<Block> trio, Consumer<RecipeJsonProvider> exporter) {
        RecipeProvider.offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, trio.get(1), trio.get(0));
        BlockusRecipeProvider.offerStairsRecipe(exporter, trio.get(2), trio.get(0));
    }
}
