package com.example.goblinminers;

import com.example.goblinminers.client.model.MinerGoblinModel;
import com.example.goblinminers.entity.MinerGoblinEntity;
import com.example.goblinminers.init.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GoblinMinersClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(MinerGoblinModel.LAYER_LOCATION, MinerGoblinModel::createBodyLayer);

        ModEntities.getGoblinEntries().forEach(entry ->
            EntityRendererRegistry.register(entry.entityType(), ctx ->
                new MobRenderer<MinerGoblinEntity, MinerGoblinModel>(
                    ctx,
                    new MinerGoblinModel(ctx.bakeLayer(MinerGoblinModel.LAYER_LOCATION)),
                    0.35F
                ) {
                    @Override
                    public ResourceLocation getTextureLocation(MinerGoblinEntity entity) {
                        return new ResourceLocation(GoblinMinersMod.MOD_ID, "textures/entity/" + entry.name() + "_goblin.png");
                    }
                }
            )
        );
    }
}
