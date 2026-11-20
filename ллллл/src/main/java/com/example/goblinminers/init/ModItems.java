package com.example.goblinminers.init;

import com.example.goblinminers.GoblinMinersMod;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModItems {
    public static final Item SOUL_SHARD = new Item(new Item.Properties());
    public static final Item SOUL_FRAGMENT = new Item(new Item.Properties());
    public static final Map<String, Item> SPAWN_EGGS = new LinkedHashMap<>();

    public static void registerItems() {
        registerItem("soul_shard", SOUL_SHARD);
        registerItem("soul_fragment", SOUL_FRAGMENT);

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(entries -> {
            entries.accept(SOUL_SHARD);
            entries.accept(SOUL_FRAGMENT);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register(entries ->
            SPAWN_EGGS.values().forEach(entries::accept)
        );
    }

    public static void registerSpawnEgg(String name, SpawnEggItem egg) {
        SPAWN_EGGS.put(name, egg);
        registerItem(name + "_goblin_spawn_egg", egg);
    }

    private static void registerItem(String path, Item item) {
        Registry.register(
                BuiltInRegistries.ITEM,
                new ResourceLocation(GoblinMinersMod.MOD_ID, path),
                item
        );
    }
}
