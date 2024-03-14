package com.siuzu.magical_obsession.render.block;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import com.siuzu.magical_obsession.MagicalObsession;
import com.siuzu.magical_obsession.block.tile.MagicalCatallyzatorBlockEntity;
import com.siuzu.magical_obsession.block.tile.MagicalFieldShieldBlockEntity;
import com.siuzu.magical_obsession.block.tile.MagicalPentagramBlockEntity;
import com.siuzu.magical_obsession.init.ModBlocks;
import com.siuzu.magical_obsession.init.ModItems;
import com.siuzu.magical_obsession.render.misc.RenderLightning;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class MagicalCatallyzatorRenderer implements BlockEntityRenderer<MagicalCatallyzatorBlockEntity> {
    private final BlockEntityRendererProvider.Context context;

    public MagicalCatallyzatorRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    private static void vertex(VertexConsumer vc, Vector4f vec4, float r, float g, float b, float a, float u, float v, Vector3f vec3) {
        vc.vertex(vec4.x(), vec4.y(), vec4.z(), r, g, b, a, u, v, OverlayTexture.NO_OVERLAY, 0xf000f0, vec3.x(), vec3.y(), vec3.z());
    }

    private static void drawPart(VertexConsumer vc, Matrix4f matrix, Matrix3f normal, float width, float distance, float v1, float v2, float x, float y, float z, InteractionHand hand, float r, float g, float b, float a) {
        Vector3f vec = new Vector3f(0, 1, 0);
        Vector4f vec1 = new Vector4f(x, -width + y, z, 1);
        Vector4f vec2 = new Vector4f(0, -width, distance, 1);
        Vector4f vec3 = new Vector4f(0, width, distance, 1);
        Vector4f vec4 = new Vector4f(x, width + y, z, 1);
        if (hand == InteractionHand.MAIN_HAND) {
            vertex(vc, vec4, r, g, b, a, 0, v1, vec);
            vertex(vc, vec3, r, g, b, a, 0, v2, vec);
            vertex(vc, vec2, r, g, b, a, 1, v2, vec);
            vertex(vc, vec1, r, g, b, a, 1, v1, vec);
            //Rendering again to allow you to see both sides in multiplayer, shouldn't be necessary with culling disabled but here we are...
            vertex(vc, vec1, r, g, b, a, 1, v1, vec);
            vertex(vc, vec2, r, g, b, a, 1, v2, vec);
            vertex(vc, vec3, r, g, b, a, 0, v2, vec);
            vertex(vc, vec4, r, g, b, a, 0, v1, vec);
        } else {
            vertex(vc, vec1, r, g, b, a, 1, v1, vec);
            vertex(vc, vec2, r, g, b, a, 1, v2, vec);
            vertex(vc, vec3, r, g, b, a, 0, v2, vec);
            vertex(vc, vec4, r, g, b, a, 0, v1, vec);
            //Rendering again to allow you to see both sides in multiplayer, shouldn't be necessary with culling disabled but here we are...
            vertex(vc, vec4, r, g, b, a, 0, v1, vec);
            vertex(vc, vec3, r, g, b, a, 0, v2, vec);
            vertex(vc, vec2, r, g, b, a, 1, v2, vec);
            vertex(vc, vec1, r, g, b, a, 1, v1, vec);
        }
    }

    @Override
    public void render(MagicalCatallyzatorBlockEntity entity, float pticks, PoseStack stack, MultiBufferSource buffer, int coverlay, int plight) {
        final BlockRenderDispatcher block_renderer = this.context.getBlockRenderDispatcher();
        Minecraft instance = Minecraft.getInstance();

        //ItemStack item = Minecraft.getInstance().player.getMainHandItem();
        final ItemRenderer item_renderer = this.context.getItemRenderer();

        BlockPos pos = entity.getBlockPos();
        BlockState state = entity.getBlockState();

        // Render Sphere

        if (entity.getSphere() >= 12000) {
            stack.pushPose();
            stack.translate(3.125d, 3.125d, 3.125d);
            stack.scale(15, 15, 15);
            stack.mulPose(Vector3f.YN.rotationDegrees(0));
            item_renderer.renderStatic(Minecraft.getInstance().player, ModItems.SPHERE_ITEM.get().getDefaultInstance(), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);
            stack.popPose();
        }

        // Render Item inside

        int degress;

        switch (state.getValue(HorizontalDirectionalBlock.FACING)) {
            case NORTH -> degress = 0;
            case SOUTH -> degress = 180;
            case EAST -> degress = 270;
            case WEST -> degress = 90;
            default -> degress = 90;
        }

        stack.pushPose();
        stack.translate(0.5d, 1.2d, 0.5d);
        stack.scale(0.5f, 0.5f, 0.5f);

        if (degress == 270) {
            stack.mulPose(Vector3f.YN.rotationDegrees(degress + 270));
        } else if (degress == 180) {
            stack.mulPose(Vector3f.YN.rotationDegrees(degress + 180));
        } else if (degress == 90) {
            stack.mulPose(Vector3f.YN.rotationDegrees(degress + 90));
        } else {
            stack.mulPose(Vector3f.YN.rotationDegrees(degress));
        }

        stack.mulPose(Vector3f.YN.rotationDegrees(degress + entity.getProgress() / 2));

        item_renderer.renderStatic(Minecraft.getInstance().player, entity.inventory.getStackInSlot(0), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                Minecraft.getInstance().level, coverlay, plight, plight);
        stack.popPose();

        RenderLightning.render(pos, stack, coverlay, plight);

        // Render pentagram
        if (entity.getProgress() >= 1) {
            stack.pushPose();
            stack.translate(0.5, 0.15, 0.5);
            stack.scale(5f, 0.01f, 5f);
            //stack.mulPose(instance.gameRenderer.getMainCamera().rotation());
            stack.mulPose(Vector3f.YN.rotationDegrees(degress - entity.getProgress() / 2));
            stack.mulPose(Vector3f.XN.rotationDegrees(270));
            TextureAtlasSprite sprite = instance.getBlockRenderer().getBlockModelShaper().getParticleIcon(ModBlocks.MAGICAL_PENTAGRAM.get().defaultBlockState());
            VertexConsumer buffer1 = buffer.getBuffer(RenderType.cutout());
            buffer1.vertex(stack.last().pose(), -1F, -1F, 0.35F).color(1f, 1f, 1f, 1f).uv(sprite.getU1(), sprite.getV1()).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(plight).normal(stack.last().normal(), 0, 1, 0).endVertex();
            buffer1.vertex(stack.last().pose(), -1F, 1F, 0.35F).color(1f, 1f, 1f, 1f).uv(sprite.getU1(), sprite.getV0()).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(plight).normal(stack.last().normal(), 0, 1, 0).endVertex();
            buffer1.vertex(stack.last().pose(), 1F, 1F, 0.35F).color(1f, 1f, 1f, 1f).uv(sprite.getU0(), sprite.getV0()).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(plight).normal(stack.last().normal(), 0, 1, 0).endVertex();
            buffer1.vertex(stack.last().pose(), 1F, -1F, 0.35F).color(1f, 1f, 1f, 1f).uv(sprite.getU0(), sprite.getV1()).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(plight).normal(stack.last().normal(), 0, 1, 0).endVertex();
            stack.popPose();


            stack.pushPose();
            stack.translate(0.5, 0.25, 0.5);
            stack.scale(5.5f, 0.01f, 5.5f);
            //stack.mulPose(instance.gameRenderer.getMainCamera().rotation());
            stack.mulPose(Vector3f.YN.rotationDegrees(degress + entity.getProgress() / 2));
            stack.mulPose(Vector3f.XN.rotationDegrees(270));
            buffer1.vertex(stack.last().pose(), -1F, -1F, 0.35F).color(1f, 1f, 1f, 1f).uv(sprite.getU1(), sprite.getV1()).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(plight).normal(stack.last().normal(), 0, 1, 0).endVertex();
            buffer1.vertex(stack.last().pose(), -1F, 1F, 0.35F).color(1f, 1f, 1f, 1f).uv(sprite.getU1(), sprite.getV0()).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(plight).normal(stack.last().normal(), 0, 1, 0).endVertex();
            buffer1.vertex(stack.last().pose(), 1F, 1F, 0.35F).color(1f, 1f, 1f, 1f).uv(sprite.getU0(), sprite.getV0()).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(plight).normal(stack.last().normal(), 0, 1, 0).endVertex();
            buffer1.vertex(stack.last().pose(), 1F, -1F, 0.35F).color(1f, 1f, 1f, 1f).uv(sprite.getU0(), sprite.getV1()).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(plight).normal(stack.last().normal(), 0, 1, 0).endVertex();
            stack.popPose();
        }
    }

    @Override
    public boolean shouldRender(MagicalCatallyzatorBlockEntity entity, Vec3 pCameraPos) {
        return true;
    }

    @Override
    public boolean shouldRenderOffScreen(MagicalCatallyzatorBlockEntity entity) {
        return true;
    }

    @Override
    public int getViewDistance() {
        return 12;
    }
}
