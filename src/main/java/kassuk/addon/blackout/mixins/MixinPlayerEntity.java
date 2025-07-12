package kassuk.addon.blackout.mixins;

import kassuk.addon.blackout.modules.SoundModifier;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity {

    @Unique
    Entity attackEntity = null;

    @Inject(method = "attack", at = @At(value = "HEAD"))
    private void inject(Entity target, CallbackInfo ci) {
        attackEntity = target;
    }
}
