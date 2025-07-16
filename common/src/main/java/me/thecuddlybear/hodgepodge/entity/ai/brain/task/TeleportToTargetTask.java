package me.thecuddlybear.hodgepodge.entity.ai.brain.task;

import com.mojang.datafixers.util.Pair;
import me.thecuddlybear.hodgepodge.entity.ai.HodgepodgeMemoryTypes;
import me.thecuddlybear.hodgepodge.util.MemoryList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtil;

import java.util.List;

public class TeleportToTargetTask extends ExtendedBehaviour<TamableAnimal> {

    private static final MemoryList MEMORIES = MemoryList.create(1)
        .present(HodgepodgeMemoryTypes.TELEPORT_TARGET.get());

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORIES;
    }

    @Override
    protected boolean shouldKeepRunning(TamableAnimal entity) {
        return BrainUtil.hasMemory(entity, HodgepodgeMemoryTypes.TELEPORT_TARGET.get());
    }

    @Override
    protected void tick(TamableAnimal entity) {
        Brain<?> brain = entity.getBrain();
        if(this.tryTeleport(entity, brain)) {
            BrainUtil.clearMemory(brain, HodgepodgeMemoryTypes.TELEPORT_TARGET.get());
        }
    }

    private boolean tryTeleport(TamableAnimal entity, Brain<?> brain) {
        if(!BrainUtil.hasMemory(entity, HodgepodgeMemoryTypes.TELEPORT_TARGET.get())) {
            return false;
        }

        Entity target = BrainUtil.getMemory(brain, HodgepodgeMemoryTypes.TELEPORT_TARGET.get()).entity();
        BlockPos pos = target.blockPosition();

        for(int i = 0; i < 10; i++) {
            int j = entity.getRandom().nextIntBetweenInclusive(-3, 3);
            int k = entity.getRandom().nextIntBetweenInclusive(-3, 3);
            if(Math.abs(j) >= 2 || Math.abs(k) >= 2) {
                int l = entity.getRandom().nextIntBetweenInclusive(-1, 1);
                if(this.tryTeleportTo(entity, pos.getX() + j, pos.getY() + l, pos.getZ() + k)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean tryTeleportTo(TamableAnimal entity, int x, int y, int z) {
        if(!this.canTeleportTo(entity, new BlockPos(x, y, z))) {
            return false;
        }

        entity.snapTo(x + 0.5, y, z + 0.5, entity.getYRot(), entity.getXRot());
        entity.getNavigation().stop();
        return true;
    }

    private boolean canTeleportTo(TamableAnimal entity, BlockPos pos) {
        PathType pathNodeType = WalkNodeEvaluator.getPathTypeStatic(entity, pos.mutable());
        if(pathNodeType != PathType.WALKABLE) {
            return false;
        }
        BlockPos distance = pos.subtract(entity.blockPosition());
        return entity.level().noCollision(entity, entity.getBoundingBox().move(distance));
    }

}
