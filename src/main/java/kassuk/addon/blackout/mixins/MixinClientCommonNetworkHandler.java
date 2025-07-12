package kassuk.addon.blackout.mixins;

import kassuk.addon.blackout.managers.Managers;
import kassuk.addon.blackout.modules.PingSpoof;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.network.packet.s2c.common.CommonPingS2CPacket;
import net.minecraft.network.packet.s2c.common.KeepAliveS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientCommonNetworkHandler.class)
public abstract class MixinClientCommonNetworkHandler {
}
