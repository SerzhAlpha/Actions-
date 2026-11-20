package com.example.goblinminers.init;

import com.example.goblinminers.GoblinMinersMod;
import com.example.goblinminers.entity.MinerGoblinEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class ModEntities {
    public record GoblinEntry(
            String name,
            EntityType<MinerGoblinEntity> entityType,
            Block platformBlock,
            Item outputItem,
            int outputCount,
            int primaryColor,
            int secondaryColor
    ) {}

    private static final List<GoblinEntry> ENTRIES = new ArrayList<>();

    public static void registerEntities() {
        registerGoblin("coal", Blocks.COAL_BLOCK, Items.COAL, 4, 0x2B2B2B, 0x111111);
        registerGoblin("iron", Blocks.IRON_BLOCK, Items.RAW_IRON, 4, 0xD8AF93, 0x8A6D5B);
        registerGoblin("copper", Blocks.COPPER_BLOCK, Items.RAW_COPPER, 4, 0xE0734D, 0x8F4931);
        registerGoblin("gold", Blocks.GOLD_BLOCK, Items.RAW_GOLD, 4, 0xFCEE4B, 0xC49A1A);
        registerGoblin("redstone", Blocks.REDSTONE_BLOCK, Items.REDSTONE, 4, 0xDB0000, 0x780000);
        registerGoblin("lapis", Blocks.LAPIS_BLOCK, Items.LAPIS_LAZULI, 4, 0x2A54C4, 0x162E78);
        registerGoblin("diamond", Blocks.DIAMOND_BLOCK, Items.DIAMOND, 4, 0x4AEDD9, 0x249E91);
        registerGoblin("emerald", Blocks.EMERALD_BLOCK, Items.EMERALD, 4, 0x17DD62, 0x0B8A3B);
        registerGoblin("quartz", Blocks.QUARTZ_BLOCK, Items.QUARTZ, 4, 0xEAE5DE, 0xB5AFA6);
        registerGoblin("netherite", Blocks.NETHERITE_BLOCK, Items.NETHERITE_SCRAP, 4, 0x4C4447, 0x292426);
    }

    private static void registerGoblin(
            String name,
            Block platformBlock,
            Item outputItem,
            int outputCount,
            int primaryColor,
            int secondaryColor
    ) {
        EntityType<MinerGoblinEntity> type = FabricEntityTypeBuilder
                .<MinerGoblinEntity>create(MobCategory.CREATURE, (entityType, level) ->
                        new MinerGoblinEntity(entityType, level, platformBlock, outputItem, outputCount))
                .dimensions(EntityDimensions.fixed(0.5F, 0.95F))
                .build();

        EntityType<MinerGoblinEntity> registeredType = Registry.register(
                BuiltInRegistries.ENTITY_TYPE,
                new ResourceLocation(GoblinMinersMod.MOD_ID, name + "_goblin"),
                type
        );

        SpawnEggItem egg = new SpawnEggItem(
                registeredType,
                primaryColor,
                secondaryColor,
                new Item.Properties()
        );
        ModItems.registerSpawnEgg(name, egg);

        ENTRIES.add(new GoblinEntry(name, registeredType, platformBlock, outputItem, outputCount, primaryColor, secondaryColor));
    }

    public static List<GoblinEntry> getGoblinEntries() {
        return ENTRIES;
    }

    public static List<EntityType<MinerGoblinEntity>> getAllGoblinTypes() {
        return ENTRIES.stream().map(GoblinEntry::entityType).toList();
    }
}
