package com.siuzu.magical_obsession.render.block;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.siuzu.magical_obsession.block.tile.MagicalPentagramBlockEntity;
import com.siuzu.magical_obsession.init.ModBlocks;
import com.siuzu.magical_obsession.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class MagicalPentagramRenderer implements BlockEntityRenderer<MagicalPentagramBlockEntity> {
    private final BlockEntityRendererProvider.Context context;
    public MagicalPentagramRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(MagicalPentagramBlockEntity entity, float pticks, PoseStack stack, MultiBufferSource buffer, int coverlay, int plight) {
        final BlockRenderDispatcher block_renderer = this.context.getBlockRenderDispatcher();

        //ItemStack item = Minecraft.getInstance().player.getMainHandItem();
        final ItemRenderer item_renderer = this.context.getItemRenderer();

        BlockPos pos = entity.getBlockPos();
        BlockState state = entity.getBlockState();

        if (state.getValue(HorizontalDirectionalBlock.FACING).equals(Direction.NORTH)) {
            stack.pushPose();
            stack.translate(0.5d, 0.35d, 0.5d);
            stack.scale(0.59f, 0.59f, 0.59f);
            stack.mulPose(Vector3f.YN.rotationDegrees(180 - entity.getProgress() / 2));
            stack.mulPose(Vector3f.XN.rotationDegrees(90));

            //ResourceLocation texture = new ResourceLocation("magical_obsession", "textures/block/magical_pentagram.png");
            item_renderer.renderStatic(Minecraft.getInstance().player, entity.itemStackHandler.getStackInSlot(0), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);
            stack.popPose();


            /*ResourceLocation WHITE = null;
            ResourceLocation BLANK = null;
            Minecraft instance = Minecraft.getInstance();
            stack.pushPose();
            stack.translate(0.5, 0.5, 0.5);
            stack.scale(1.25f, 1.25f, 1.25f);
            stack.mulPose(instance.gameRenderer.getMainCamera().rotation());
            stack.mulPose(Vector3f.ZP.rotation(instance.player.tickCount / 10f % 360));
            ResourceLocation sprite = texture;
            VertexConsumer buffer1 = buffer.getBuffer(RenderType.translucent());
            buffer1.vertex(stack.last().pose(), -1F, -1F, 0F).color(1f, 1f, 1f, 1f).uv(1, 16).overlayCoords(OverlayTexture.RED_OVERLAY_V).uv2(plight).normal(stack.last().normal(), 0, 1, 0).endVertex();
            buffer1.vertex(stack.last().pose(), -1F, 1F, 0F).color(1f, 1f, 1f, 1f).uv(1, 1).overlayCoords(OverlayTexture.RED_OVERLAY_V).uv2(plight).normal(stack.last().normal(), 0, 1, 0).endVertex();
            buffer1.vertex(stack.last().pose(), 1F, 1F, 0F).color(1f, 1f, 1f, 1f).uv(1, 16).overlayCoords(OverlayTexture.RED_OVERLAY_V).uv2(plight).normal(stack.last().normal(), 0, 1, 0).endVertex();
            buffer1.vertex(stack.last().pose(), 1F, -1F, 0F).color(1f, 1f, 1f, 1f).uv(1, 16).overlayCoords(OverlayTexture.RED_OVERLAY_V).uv2(plight).normal(stack.last().normal(), 0, 1, 0).endVertex();
            stack.popPose();*/

            Minecraft instance = Minecraft.getInstance();
            stack.pushPose();
            stack.translate(0.5, 0.5, 0.5);
            stack.scale(1.25f, 1.25f, 1.25f);
            stack.mulPose(instance.gameRenderer.getMainCamera().rotation());
            //stack.mulPose(Vector3f.ZP.rotation(instance.player.tickCount / 10f % 360));
            TextureAtlasSprite sprite = instance.getBlockRenderer().getBlockModelShaper().getParticleIcon(entity.getBlockState());
            VertexConsumer buffer1 = buffer.getBuffer(RenderType.translucent());
            buffer1.vertex(stack.last().pose(), -1F, -1F, 0F).color(1f, 1f, 1f, 1f).uv(sprite.getU1(), sprite.getV1()).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(plight).normal(stack.last().normal(), 0, 1, 0).endVertex();
            buffer1.vertex(stack.last().pose(), -1F, 1F, 0F).color(1f, 1f, 1f, 1f).uv(sprite.getU1(), sprite.getV0()).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(plight).normal(stack.last().normal(), 0, 1, 0).endVertex();
            buffer1.vertex(stack.last().pose(), 1F, 1F, 0F).color(1f, 1f, 1f, 1f).uv(sprite.getU0(), sprite.getV0()).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(plight).normal(stack.last().normal(), 0, 1, 0).endVertex();
            buffer1.vertex(stack.last().pose(), 1F, -1F, 0F).color(1f, 1f, 1f, 1f).uv(sprite.getU0(), sprite.getV1()).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(plight).normal(stack.last().normal(), 0, 1, 0).endVertex();
            stack.popPose();


            /*ResourceLocation texture = new ResourceLocation("magical_obsession", "textures/block/magical_pentagram.png");

            Vec3 camera = Vec3.atCenterOf(pos);

            float x = 0.01000322f;
            float y = 0.46387255f;
            float z = -3.8817964f;

            Matrix4f matrix = stack.last().pose();

// Enable texturing
            RenderSystem.enableTexture();
            RenderSystem.setShader(GameRenderer::getPositionTexShader); // Use positionTexShader for textures

// Bind the texture to the active texture unit
            Minecraft.getInstance().getTextureManager().bindForSetup(texture); // Use bind instead of bindForSetup

            BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX); // Use POSITION_TEX format for textures

// Define vertices with texture coordinates (assuming your texture is square)
            bufferbuilder.vertex(matrix, -0.5F, 0.5F, -0.5F).uv(0f, 1f).endVertex(); // Bottom-left corner (texture X: 0, Y: 1)
            bufferbuilder.vertex(matrix, 0.5F, 0.5F, -0.5F).uv(1f, 1f).endVertex(); // Bottom-right corner (texture X: 1, Y: 1)
            bufferbuilder.vertex(matrix, 0.5F, 1.5F, -0.5F).uv(1f, 0f).endVertex(); // Top-right corner (texture X: 1, Y: 0)
            bufferbuilder.vertex(matrix, -0.5F, 1.5F, -0.5F).uv(0f, 0f).endVertex(); // Top-left corner (texture X: 0, Y: 0)

            BufferUploader.drawWithShader(bufferbuilder.end());

// Disable texturing (optional, good practice)
            RenderSystem.disableTexture();*/


            stack.pushPose();
            stack.translate(0.5d, 0.1d + entity.getProgress() / 2100f, 0.5d);
            stack.scale(1.19f, 0.00000001f, 1.19f);
            stack.mulPose(Vector3f.XP.rotationDegrees(90));
            stack.mulPose(Vector3f.ZN.rotationDegrees(0 - entity.getProgress() / 3.15f));

            item_renderer.renderStatic(Minecraft.getInstance().player, ModBlocks.MAGICAL_PENTAGRAM.get().asItem().getDefaultInstance(), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);
            stack.popPose();
        } else if (state.getValue(HorizontalDirectionalBlock.FACING).equals(Direction.SOUTH)) {
            stack.pushPose();
            stack.translate(0.5d, 0.35d, 0.5d);
            stack.scale(0.59f, 0.59f, 0.59f);
            stack.mulPose(Vector3f.YN.rotationDegrees(0 - entity.getProgress() / 2));
            stack.mulPose(Vector3f.XN.rotationDegrees(90));

            item_renderer.renderStatic(Minecraft.getInstance().player, entity.itemStackHandler.getStackInSlot(0), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);
            stack.popPose();

            stack.pushPose();
            stack.translate(0.5d, 0.1d + entity.getProgress() / 2100f, 0.5d);
            stack.scale(1.19f, 0.00000001f, 1.19f);
            stack.mulPose(Vector3f.XP.rotationDegrees(90));
            stack.mulPose(Vector3f.ZN.rotationDegrees(180 - entity.getProgress() / 3.15f));

            item_renderer.renderStatic(Minecraft.getInstance().player, ModBlocks.MAGICAL_PENTAGRAM.get().asItem().getDefaultInstance(), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);
            stack.popPose();
        } else if (state.getValue(HorizontalDirectionalBlock.FACING).equals(Direction.EAST)) {
            stack.pushPose();
            stack.translate(0.5d, 0.35d, 0.5d);
            stack.scale(0.59f, 0.59f, 0.59f);
            stack.mulPose(Vector3f.YN.rotationDegrees(270 - entity.getProgress() / 2));
            stack.mulPose(Vector3f.XN.rotationDegrees(90));

            item_renderer.renderStatic(Minecraft.getInstance().player, entity.itemStackHandler.getStackInSlot(0), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);
            stack.popPose();

            stack.pushPose();
            stack.translate(0.5d, 0.1d + entity.getProgress() / 2100f, 0.5d);
            stack.scale(1.19f, 0.00000001f, 1.19f);
            stack.mulPose(Vector3f.XP.rotationDegrees(90));
            stack.mulPose(Vector3f.ZN.rotationDegrees(270 - entity.getProgress() / 3.15f));

            item_renderer.renderStatic(Minecraft.getInstance().player, ModBlocks.MAGICAL_PENTAGRAM.get().asItem().getDefaultInstance(), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);
            stack.popPose();
        } else if (state.getValue(HorizontalDirectionalBlock.FACING).equals(Direction.WEST)) {
            stack.pushPose();
            stack.translate(0.5d, 0.35d, 0.5d);
            stack.scale(0.59f, 0.59f, 0.59f);
            stack.mulPose(Vector3f.YN.rotationDegrees(90 - entity.getProgress() / 2));
            stack.mulPose(Vector3f.XN.rotationDegrees(90));

            item_renderer.renderStatic(Minecraft.getInstance().player, entity.itemStackHandler.getStackInSlot(0), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);
            stack.popPose();

            stack.pushPose();
            stack.translate(0.5d, 0.1d + entity.getProgress() / 2100f, 0.5d);
            stack.scale(1.19f, 0.00000001f, 1.19f);
            stack.mulPose(Vector3f.XP.rotationDegrees(90));
            stack.mulPose(Vector3f.ZN.rotationDegrees(90 - entity.getProgress() / 3.15f));

            item_renderer.renderStatic(Minecraft.getInstance().player, ModBlocks.MAGICAL_PENTAGRAM.get().asItem().getDefaultInstance(), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);
            stack.popPose();
        }
    }

        // Render Lines

//        stack.pushPose();
//
//        MultiBufferSource.BufferSource buffer1 = Minecraft.getInstance().renderBuffers().bufferSource();
//        VertexConsumer builder = buffer1.getBuffer(RenderType.LINES);
//
//        stack.translate(0d, 0d, 0d);
//
//        Matrix4f mat = stack.last().pose();
//        Matrix3f mat1 = stack.last().normal();
//        builder.vertex(mat, 0.5f, 0.5f, 0.5f).color(201, 135, 227, 200).normal(mat1, 0.5f, 0.5f, 0.5f).endVertex();
//        builder.vertex(mat, -entity.getCords(0) + 0.5f, 0.75f, -entity.getCords(2) + 0.5f).color(255, 255, 255, 200)
//                .normal(mat1, -entity.getCords(0) + 0.5f, 0.75f, -entity.getCords(2) + 0.5f).endVertex();
//        stack.popPose();
//
//        buffer1.endBatch();
}
