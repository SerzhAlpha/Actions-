package com.example.goblinminers;

import com.example.goblinminers.entity.MinerGoblinEntity;
import com.example.goblinminers.init.ModEntities;
import com.example.goblinminers.init.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoblinMinersMod implements ModInitializer {
    public static final String MOD_ID = "goblinminers";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModItems.registerItems();
        ModEntities.registerEntities();

        ModEntities.getAllGoblinTypes().forEach(type ->
            FabricDefaultAttributeRegistry.register(type, MinerGoblinEntity.createGoblinAttributes())
        );

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            if (entity instanceof Monster && !entity.level().isClientSide) {
                if (entity.getRandom().nextFloat() < 0.20F) {
                    entity.spawnAtLocation(new ItemStack(ModItems.SOUL_SHARD));
                }
            }
        });

        LOGGER.info("Goblin Miners initialized for Fabric 1.20.1!");
    }
}
