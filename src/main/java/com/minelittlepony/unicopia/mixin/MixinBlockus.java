package com.minelittlepony.unicopia.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.brand.blockus.Blockus;
import com.minelittlepony.unicopia.UnicopiaBlockus;

import net.minecraft.util.Identifier;

@Mixin(Blockus.class)
public class MixinBlockus {
    @Inject(method = "id", at = @At("HEAD"), cancellable = true)
    private static void onId(String name, CallbackInfoReturnable<Identifier> info) {
        if (UnicopiaBlockus.registering) {
            info.setReturnValue(UnicopiaBlockus.id(name));
        }
    }

}
