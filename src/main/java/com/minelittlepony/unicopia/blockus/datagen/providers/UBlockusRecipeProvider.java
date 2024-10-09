package com.minelittlepony.unicopia.blockus.datagen.providers;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.brand.blockus.datagen.providers.BlockusRecipeProvider;
import com.minelittlepony.unicopia.block.UBlocks;
import com.minelittlepony.unicopia.blockus.UBlockusBlocks;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

public class UBlockusRecipeProvider extends FabricRecipeProvider {
    public UBlockusRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        UBlockusBlocks.woodsets().forEach(woodset -> {
            offerBSSRecipes(woodset.mosaics(), exporter);
            offerBSSRecipes(woodset.mossy(), exporter);
            offerWoodenPostRecipe(exporter, woodset.log(), woodset.posts().get(0), woodset.planks());
            offerWoodenPostRecipe(exporter, woodset.strippedLog(), woodset.posts().get(1), woodset.planks());
            BlockusRecipeProvider.offerSmallLogsRecipe(exporter, woodset.smallLogs(), woodset.log());
            offerMosaicRecipe(exporter, RecipeCategory.DECORATIONS, woodset.mosaics().get(0), woodset.slab());
            BlockusRecipeProvider.offerHerringBoneRecipe(exporter, woodset.herringbonePlanks(), woodset.planks());
            BlockusRecipeProvider.offerMossyRecipe(exporter, woodset.mossy().get(0), woodset.planks());
            BlockusRecipeProvider.createTimberFramesRecipes(exporter, woodset.planks(), woodset.timberFrames().get(0), woodset.timberFrames().get(1), woodset.timberFrames().get(2));
        });
        BlockusRecipeProvider.offerSmallLogsRecipe(exporter, UBlockusBlocks.GOLDEN_OAK_SMALL_LOGS, UBlocks.GOLDEN_OAK_LOG);
        UBlockusBlocks.PLANT_SETS.forEach(plantset -> {
            BlockusRecipeProvider.offerSmallHedgesRecipe(exporter, plantset.smallHedge(), plantset.leaves());
        });
    }

    private static void offerBSSRecipes(List<Block> trio, RecipeExporter exporter) {
        RecipeProvider.offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, trio.get(1), trio.get(0));
        BlockusRecipeProvider.offerStairsRecipe(exporter, trio.get(2), trio.get(0));
    }

    public static void offerWoodenPostRecipe(RecipeExporter exporter, ItemConvertible wood, ItemConvertible post, ItemConvertible planks) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, post, 6)
            .input('#', wood)
            .pattern("#")
            .pattern("#")
            .pattern("#").group("wooden_posts")
            .criterion("has_woods", conditionsFromItem(wood))
            .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, planks, 2)
            .input(post).group("planks")
            .criterion("has_wooden_post", conditionsFromItem(post))
            .offerTo(exporter, convertBetween(planks, post));
    }
}
