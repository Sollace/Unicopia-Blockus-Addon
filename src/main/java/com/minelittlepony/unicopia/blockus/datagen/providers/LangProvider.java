package com.minelittlepony.unicopia.blockus.datagen.providers;

import java.nio.file.Path;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

import com.google.gson.JsonObject;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public abstract class LangProvider implements DataProvider {
    private static final Pattern SNAKE_CASE_PATTERN = Pattern.compile("(^|_)(.)");

    private final FabricDataOutput output;
    private final DataOutput.PathResolver langPathResolver;

    public LangProvider(FabricDataOutput output) {
        this.output = output;
        langPathResolver = output.getResolver(DataOutput.OutputType.RESOURCE_PACK, "lang");
    }

    protected String getDefaultNamespace() {
        return output.getModId();
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        Path path = langPathResolver.resolveJson(Identifier.of(getDefaultNamespace(), "en_us"));
        JsonObject json = new JsonObject();
        generate(json::addProperty);
        return DataProvider.writeToPath(writer, json, path);
    }

    protected abstract void generate(TranslationGenerator generator);

    @Override
    public String getName() {
        return "Default English Translations";
    }

    public static String formatToTitleCase(String string) {
        return SNAKE_CASE_PATTERN.matcher(string).replaceAll(result -> (result.group(1).isBlank() ? "" : " ") + result.group(2).toUpperCase(Locale.ROOT));
    }

    interface TranslationGenerator {
        void addTranslation(String translation, String text);

        default void addTranslation(Block block) {
            addTranslation(block.getTranslationKey(), formatToTitleCase(Registries.BLOCK.getId(block).getPath()));
        }

        default void addTranslation(Block...rest) {
            for (Block block : rest) {
                addTranslation(block);
            }
        }
    }
}
