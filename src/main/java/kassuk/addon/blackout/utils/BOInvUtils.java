package kassuk.addon.blackout.utils;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import kassuk.addon.blackout.managers.Managers;
import meteordevelopment.meteorclient.mixininterface.IClientPlayerInteractionManager;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.player.SlotUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.ClickType;

import static meteordevelopment.meteorclient.MeteorClient.mc;

/**
 * @author OLEPOSSU
 */

@SuppressWarnings("DataFlowIssue")
public class BOInvUtils {
    private static int[] slots;
    private static int lastSlot = -1;

    public static void silentSwap(int slot) {
        if (slot > 9 || slot < 0) {
            return;
        }

        if (lastSlot != -1) {
            silentSwapBack();
        }

        lastSlot = mc.player.getInventory().getSelectedSlot();
        mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(slot));
        // mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, slot, 0, SlotActionType.PICKUP_ALL, mc.player);
        // mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, slot, 1, SlotActionType.PICKUP_ALL, mc.player);
        lastSlot = -1;
    }

    public static void swap(int slot) {
        if (slot > 9 || slot < 0) {
            return;
        }

        if (mc.player.getInventory().getSelectedSlot() != slot) {
            mc.player.getInventory().setSelectedSlot(slot);
            mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(slot));
        }
    }

    public static void silentSwapBack() {
        if (lastSlot != -1) {
            mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(lastSlot));
            lastSlot = -1;
        }
    }
}
