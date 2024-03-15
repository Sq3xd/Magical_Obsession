package com.siuzu.magical_obsession.client.render.block;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
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
        Minecraft instance = Minecraft.getInstance();
        final ItemRenderer item_renderer = this.context.getItemRenderer();

        BlockPos pos = entity.getBlockPos();
        BlockState state = entity.getBlockState();

        int degress;

        switch (state.getValue(HorizontalDirectionalBlock.FACING)) {
            case NORTH -> degress = 0;
            case SOUTH -> degress = 180;
            case EAST -> degress = 90;
            case WEST -> degress = 270;
            default -> degress = 90;
        }

        stack.pushPose();
        stack.translate(0.5d, 0.25d, 0.5d);
        stack.scale(0.5f, 0.5f, 0.5f);

        if (degress==270) {
            stack.mulPose(Vector3f.YN.rotationDegrees(degress+90));
        } else if (degress==180) {
            stack.mulPose(Vector3f.YN.rotationDegrees(degress+180));
            stack.mulPose(Vector3f.XN.rotationDegrees(270));
        }
        else if (degress==90) {
            stack.mulPose(Vector3f.YN.rotationDegrees(degress+90));
            stack.mulPose(Vector3f.XN.rotationDegrees(180));
        }
        else {
            stack.mulPose(Vector3f.YN.rotationDegrees(degress+360));
            stack.mulPose(Vector3f.XN.rotationDegrees(270));
        };

        //stack.mulPose(Vector3f.ZN.rotationDegrees(degress));
        stack.mulPose(Vector3f.YN.rotationDegrees(degress + entity.getProgress() / 2));
        stack.mulPose(Vector3f.XN.rotationDegrees(degress - entity.getProgress() / 2));

        item_renderer.renderStatic(Minecraft.getInstance().player, entity.inventory.getStackInSlot(0), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                Minecraft.getInstance().level, coverlay, plight, plight);
        stack.popPose();



        stack.pushPose();
        stack.translate(0.5, 0.5, 0.5);
        stack.scale(1.25f, 1.25f, 1.25f);
        //stack.mulPose(instance.gameRenderer.getMainCamera().rotation());
        stack.mulPose(Vector3f.YN.rotationDegrees(degress - entity.getProgress() / 2));
        stack.mulPose(Vector3f.XN.rotationDegrees(270));
        TextureAtlasSprite sprite = instance.getBlockRenderer().getBlockModelShaper().getParticleIcon(entity.getBlockState());
        VertexConsumer buffer1 = buffer.getBuffer(RenderType.cutout());
        buffer1.vertex(stack.last().pose(), -1F, -1F, 0.35F).color(1f, 1f, 1f, 1f).uv(sprite.getU1(), sprite.getV1()).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(plight).normal(stack.last().normal(), 0, 1, 0).endVertex();
        buffer1.vertex(stack.last().pose(), -1F, 1F, 0.35F).color(1f, 1f, 1f, 1f).uv(sprite.getU1(), sprite.getV0()).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(plight).normal(stack.last().normal(), 0, 1, 0).endVertex();
        buffer1.vertex(stack.last().pose(), 1F, 1F, 0.35F).color(1f, 1f, 1f, 1f).uv(sprite.getU0(), sprite.getV0()).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(plight).normal(stack.last().normal(), 0, 1, 0).endVertex();
        buffer1.vertex(stack.last().pose(), 1F, -1F, 0.35F).color(1f, 1f, 1f, 1f).uv(sprite.getU0(), sprite.getV1()).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(plight).normal(stack.last().normal(), 0, 1, 0).endVertex();
        stack.popPose();
    }
}
