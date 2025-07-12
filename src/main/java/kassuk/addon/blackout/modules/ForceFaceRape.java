package kassuk.addon.blackout.modules;

import kassuk.addon.blackout.BlackOut;
import kassuk.addon.blackout.BlackOutModule;
import kassuk.addon.blackout.utils.BOInvUtils;
import kassuk.addon.blackout.utils.HoleUtils;
import kassuk.addon.blackout.utils.meteor.BOEntityUtils;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.friends.Friends;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.combat.Offhand;
import meteordevelopment.meteorclient.utils.network.PacketUtils;
import meteordevelopment.meteorclient.utils.player.FindItemResult;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import org.apache.http.util.EntityUtils;

import java.util.List;

public class ForceFaceRape extends BlackOutModule {
    public SettingGroup sgDefault = settings.getDefaultGroup();
    public final Setting<Integer> msDelay =
        sgDefault.add(
            new IntSetting.Builder()
                .name("MS Delay")
                .description(".")
                .defaultValue(15)
                .sliderRange(0, 250)
                .build());
    public final Setting<Integer> maxActions =
        sgDefault.add(
            new IntSetting.Builder()
                .name("Max Actions")
                .description(".")
                .defaultValue(3)
                .sliderRange(1, 8)
                .build());
    public final Setting<Double> placeDist =
        sgDefault.add(
            new DoubleSetting.Builder()
                .name("Place Dist")
                .description(".")
                .defaultValue(4.5)
                .sliderRange(3, 6)
                .decimalPlaces(1)
                .build());


    private Direction[] directions = new Direction[]{Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH};
    public double lastUpdate = 0;

    public ForceFaceRape() {
        super(BlackOut.BLACKOUT, "Force Face Rape", "Let's you obliterate peoples faces.");
    }

    @EventHandler
    public void onRender3D(Render3DEvent event) {
        if (System.currentTimeMillis() - lastUpdate >= 0) {
            lastUpdate = System.currentTimeMillis() + msDelay.get();

            int actions = 0;

            for (AbstractClientPlayerEntity player : mc.player.clientWorld.getPlayers()) {
                if (actions > maxActions.get()) {
                    break;
                }

                if (player.getHealth() > 0 && player.isLiving() && !Friends.get().isFriend(player)) {
                    if (mc.player.getEyePos().distanceTo(player.getEyePos()) < 8) {
                            BlockPos.Mutable mutable = new BlockPos.Mutable();

                            for (Direction direction : directions) {
                                mutable.set(player.getBlockX() + direction.getOffsetX(), player.getBlockY(), player.getBlockZ() + direction.getOffsetZ());

                                BlockState state = mc.world.getBlockState(mutable);

                                if (state.getBlock() != Blocks.OBSIDIAN && state.getBlock() != Blocks.OBSIDIAN) {
                                    continue;
                                }

                                if (mutable.toCenterPos().distanceTo(mc.player.getEyePos()) >= placeDist.get()) {
                                    continue;
                                }

                                BlockPos upperBlock = mutable.offset(Direction.UP);
                                BlockState upperState = mc.world.getBlockState(upperBlock);

                                EndCrystalEntity crystalEntity = crystalAt(upperBlock);

                                if (crystalEntity != null) {
                                    mc.player.networkHandler.sendPacket(PlayerInteractEntityC2SPacket.attack(crystalEntity, mc.player.isSneaking()));
                                    actions++;
                                }

                                if (actions > maxActions.get()) {
                                    break;
                                }

                                if (!upperState.isAir()) {
                                    continue;
                                }

                                FindItemResult result = InvUtils.findInHotbar(Items.END_CRYSTAL);

                                if (!result.found()) {
                                    break;
                                }

                                BOInvUtils.swap(result.slot());

                                actions++;

                                BlackOut.LOG.info("Placing crystal at {}", mutable.toShortString());

                                mc.player.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, new BlockHitResult(mutable.toCenterPos(), Direction.UP, mutable, mc.player.isInsideWall()), 0));
                            }
                        }
                }
            }
        }
    }

    private EndCrystalEntity crystalAt(BlockPos pos) {
        for (Entity entity : mc.world.getEntities()) {
            if (entity instanceof EndCrystalEntity crystal && entity.getBlockPos().equals(pos)) {
                return crystal;
            }
        }
        return null;
    }
}
