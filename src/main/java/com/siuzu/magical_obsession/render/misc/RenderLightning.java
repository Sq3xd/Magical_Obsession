package com.siuzu.magical_obsession.render.misc;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.opengl.GL11;

public class RenderLightning {
    public static void render(BlockPos pos, PoseStack stack, int coverlay, int plight) {
        Vec3 camera = Vec3.atCenterOf(pos);

        Matrix4f matrix = stack.last().pose();

        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.disableBlend();

        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_NORMAL);

// Front face
        bufferbuilder.vertex(matrix, -0.5F, -0.5F, 0.5F).color(1.0F, 0.0F, 0.0F, 1.0F).normal(0.0F, 0.0F, 1.0F).endVertex(); // BOTTOM LEFT
        bufferbuilder.vertex(matrix, 0.5F, -0.5F, 0.5F).color(0.0F, 1.0F, 0.0F, 1.0F).normal(0.0F, 0.0F, 1.0F).endVertex(); // BOTTOM RIGHT
        bufferbuilder.vertex(matrix, 0.5F, 0.5F, 0.5F).color(0.0F, 0.0F, 1.0F, 1.0F).normal(0.0F, 0.0F, 1.0F).endVertex(); // TOP RIGHT
        bufferbuilder.vertex(matrix, -0.5F, 0.5F, 0.5F).color(1.0F, 0.0F, 1.0F, 1.0F).normal(0.0F, 0.0F, 1.0F).endVertex(); // TOP LEFT

// Back face
        bufferbuilder.vertex(matrix, 0.5F, -0.5F, -0.5F).color(1.0F, 0.0F, 0.0F, 1.0F).normal(0.0F, 0.0F, -1.0F).endVertex(); // BOTTOM RIGHT
        bufferbuilder.vertex(matrix, -0.5F, -0.5F, -0.5F).color(0.0F, 1.0F, 0.0F, 1.0F).normal(0.0F, 0.0F, -1.0F).endVertex(); // BOTTOM LEFT
        bufferbuilder.vertex(matrix, -0.5F, 0.5F, -0.5F).color(0.0F, 0.0F, 1.0F, 1.0F).normal(0.0F, 0.0F, -1.0F).endVertex(); // TOP LEFT
        bufferbuilder.vertex(matrix, 0.5F, 0.5F, -0.5F).color(1.0F, 0.0F, 1.0F, 1.0F).normal(0.0F, 0.0F, -1.0F).endVertex(); // TOP RIGHT

// Other faces (similarly for other faces)

        BufferUploader.drawWithShader(bufferbuilder.end());
    }
}
