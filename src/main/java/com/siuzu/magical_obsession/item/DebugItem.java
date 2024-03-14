package com.siuzu.magical_obsession.item;

import com.siuzu.magical_obsession.MagicalObsession;
import com.siuzu.magical_obsession.block.tile.MagicalCatallyzatorBlockEntity;
import com.siuzu.magical_obsession.render.item.MyBEWLR;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.extensions.IForgeBakedModel;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class DebugItem extends Item {

    public DebugItem(Properties properties){
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (pContext.getLevel().isClientSide){
            if (pContext.getLevel().getBlockEntity(pContext.getClickedPos()) instanceof MagicalCatallyzatorBlockEntity entity) {
                pContext.getPlayer().sendSystemMessage(Component.literal(Integer.toString(entity.getSphere()) + " SPHERE"));
                pContext.getPlayer().sendSystemMessage(Component.literal(Integer.toString(entity.getProgress()) + " PROGRESS"));
                for (int i = 0; i < 10000; i++)
                    System.out.println("YES2");
            }
        }
        return super.useOn(pContext);
    }
}
