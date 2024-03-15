package com.siuzu.magical_obsession.client.render.item;

import codechicken.lib.model.PerspectiveModelState;
import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.util.TransformUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.siuzu.magical_obsession.init.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class MobSoulRenderer implements IItemRenderer {


    public MobSoulRenderer()
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
            mStack.mulPose(Vector3f.XP.rotationDegrees(75));
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
        mStack.translate(0.5, 0.05, 0.5);
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