package com.siuzu.magical_obsession.render.item;

import codechicken.lib.model.PerspectiveModelState;
import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.util.TransformUtils;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.siuzu.magical_obsession.MagicalObsession;
import com.siuzu.magical_obsession.block.tile.MagicalCatallyzatorBlockEntity;
import com.siuzu.magical_obsession.init.ModBlocks;
import com.siuzu.magical_obsession.init.ModItems;
import com.siuzu.magical_obsession.item.SoulJarItem;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Rotations;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.ForgeRenderTypes;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms;

public class MyBEWLR implements IItemRenderer {


    public MyBEWLR()
    {
    }

    @Override
    public void renderItem(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack mStack, MultiBufferSource source, int plight, int coverlay) {
        Minecraft minecraft = Minecraft.getInstance();

        Entity entity;

        if (stack.getTag() != null) {
            ResourceLocation rl = new ResourceLocation(stack.getTag().getString("id"));
            EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(rl);
            entity = entityType.create(minecraft.level);
        } else {
            entity = EntityType.PIG.create(minecraft.level);
        }

        if (transformType != ItemTransforms.TransformType.GROUND && transformType != ItemTransforms.TransformType.FIXED) {
            mStack.pushPose();
            mStack.translate(0.5, 0.25, 0.5);
            mStack.scale(0.75f, 0.75f, 0.75f);
            //stack.mulPose(instance.gameRenderer.getMainCamera().rotation());
            mStack.mulPose(Vector3f.YN.rotationDegrees(45));
            TextureAtlasSprite sprite = minecraft.getBlockRenderer().getBlockModelShaper().getParticleIcon(ModBlocks.MAGICAL_PENTAGRAM.get().defaultBlockState());
            VertexConsumer buffer1 = source.getBuffer(RenderType.cutout());
            buffer1.vertex(mStack.last().pose(), -1F, -1F, 0.35F).color(1f, 1f, 1f, 1f).uv(sprite.getU1(), sprite.getV1()).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(plight).normal(mStack.last().normal(), 0, 1, 0).endVertex();
            buffer1.vertex(mStack.last().pose(), -1F, 1F, 0.35F).color(1f, 1f, 1f, 1f).uv(sprite.getU1(), sprite.getV0()).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(plight).normal(mStack.last().normal(), 0, 1, 0).endVertex();
            buffer1.vertex(mStack.last().pose(), 1F, 1F, 0.35F).color(1f, 1f, 1f, 1f).uv(sprite.getU0(), sprite.getV0()).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(plight).normal(mStack.last().normal(), 0, 1, 0).endVertex();
            buffer1.vertex(mStack.last().pose(), 1F, -1F, 0.35F).color(1f, 1f, 1f, 1f).uv(sprite.getU0(), sprite.getV1()).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(plight).normal(mStack.last().normal(), 0, 1, 0).endVertex();
            mStack.popPose();
        }

        float scale = 0.75F / Math.max(entity.getBbWidth(), entity.getBbHeight());
        //float scale = entity.getBbWidth() / entity.getBbHeight()
        mStack.translate(0.5, 0, 0.5);
        mStack.scale(scale, scale, scale);

        //mStack.mulPose(Vector3f.XP.rotation(12));
        mStack.mulPose(Vector3f.YP.rotation(minecraft.player.tickCount / 20f % 360));
        //mStack.mulPose(new Quaternion(new Vector3f(1, 0, -0.5F), (float) Math.sin((1+ Minecraft.getInstance().getFrameTime()) / 50F) * 15F, true));
        //mStack.mulPose(new Quaternion(new Vector3f(0, 1, 0), (1 + Minecraft.getInstance().getFrameTime()) * 3, true));

        EntityRenderDispatcher manager = Minecraft.getInstance().getEntityRenderDispatcher();
        manager.render(entity, 0, 0, 0, 0, 0, mStack, source, plight);
    }

    @Override
    public @Nullable PerspectiveModelState getModelState() {
        return TransformUtils.DEFAULT_BLOCK;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return true;
    }

    @Override
    public boolean usesBlockLight() {
        return true;
    }
}