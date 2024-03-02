package com.minelittlepony.unicopia.blockus.datagen.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(targets = "net.minecraft.registry.RegistryBuilder$Registries")
abstract class MixinRegistryBuilder_Registries {
    @Overwrite
    public void checkUnreferencedKeys() {
        System.out.println("Skipping validate references");
    }
}
