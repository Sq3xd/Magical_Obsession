package com.siuzu.magical_obsession.item;

import com.siuzu.magical_obsession.block.tile.MagicalCatallyzatorBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

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
