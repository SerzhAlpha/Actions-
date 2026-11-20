package com.example.goblinminers.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class MinerGoblinEntity extends PathfinderMob {
    private final Block requiredPlatformBlock;
    private final Item outputItem;
    private final int outputCount;
    private int miningTimer = 0;

    // 1200 ticks = 60 seconds (1 minute)
    private static final int TICKS_PER_MINUTE = 1200;

    public MinerGoblinEntity(
            EntityType<? extends PathfinderMob> type,
            Level level,
            Block platformBlock,
            Item outputItem,
            int outputCount
    ) {
        super(type, level);
        this.requiredPlatformBlock = platformBlock;
        this.outputItem = outputItem;
        this.outputCount = outputCount;
    }

    public static AttributeSupplier.Builder createGoblinAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.20D)
                .add(Attributes.ARMOR, 2.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 6.0F));
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            if (isValidPlatform()) {
                miningTimer++;
                if (miningTimer >= TICKS_PER_MINUTE) {
                    this.spawnAtLocation(new ItemStack(this.outputItem, this.outputCount));
                    miningTimer = 0;
                }
            } else {
                miningTimer = 0;
            }
        }
    }

    private boolean isValidPlatform() {
        BlockPos center = this.blockPosition().below();
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (!this.level().getBlockState(center.offset(x, 0, z)).is(this.requiredPlatformBlock)) {
                    return false;
                }
            }
        }
        return true;
    }
}
