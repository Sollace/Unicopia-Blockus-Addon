package com.minelittlepony.unicopia.data.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(targets = "net.minecraft.registry.RegistryBuilder$Registries")
abstract class MixinRegistryBuilder_Registries {
    @Overwrite
    public void validateReferences() {
        System.out.println("Skipping validate references");
    }
}
