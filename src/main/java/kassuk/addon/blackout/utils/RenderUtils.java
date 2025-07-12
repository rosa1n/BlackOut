package kassuk.addon.blackout.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.*;
import net.minecraft.client.util.BufferAllocator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ColorHelper;
import org.joml.Matrix4f;

import static meteordevelopment.meteorclient.MeteorClient.mc;

/**
 * @author OLEPOSSU
 */

public class RenderUtils {
    private static final VertexConsumerProvider.Immediate vertex = VertexConsumerProvider.immediate(new BufferAllocator(2048));

    public static void corner(float x, float y, float radius, int angle, float p, float r, float g, float b, float a, BufferBuilder bufferBuilder, Matrix4f matrix4f) {
        for (float i = angle; i > angle - 90; i -= 90 / p) {
            bufferBuilder.vertex(matrix4f, (float) (x + Math.cos(Math.toRadians(i)) * radius), (float) (y + Math.sin(Math.toRadians(i)) * radius), 0).color(r, g, b, a);
        }
    }

    public static void text(String text, MatrixStack stack, float x, float y, int color) {
        mc.textRenderer.draw(text, x, y, color, false, stack.peek().getPositionMatrix(), vertex, TextRenderer.TextLayerType.NORMAL, 0, 15728880);
        vertex.draw();
    }
}
