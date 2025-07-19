package me.thecuddlybear.hodgepodge.entity.ai.brain.task;

import com.mojang.datafixers.util.Pair;
import me.thecuddlybear.hodgepodge.entity.SittableAnimal;
import me.thecuddlybear.hodgepodge.entity.ai.HodgepodgeMemoryTypes;
import me.thecuddlybear.hodgepodge.entity.ai.brain.TeleportTarget;
import me.thecuddlybear.hodgepodge.util.MemoryList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtil;

import java.util.List;
import java.util.function.Function;

public class FollowOwnerTask<T extends TamableAnimal & SittableAnimal> extends ExtendedBehaviour<T> {

    private static final MemoryList MEMORIES = MemoryList.create(1)
            .registered(
                    HodgepodgeMemoryTypes.TELEPORT_TARGET.get(),
                    MemoryModuleType.LOOK_TARGET,
                    MemoryModuleType.WALK_TARGET
            );

    private LivingEntity owner;
    private int updateCountdownTicks;
    protected Function<LivingEntity, Float> speedModifier = (entity) -> 1.0F;
    protected UniformInt range = UniformInt.of(5, 10);

    public FollowOwnerTask speedModifier(float modifier) {
        return this.speedModifier(entity -> modifier);
    }

    public FollowOwnerTask speedModifier(Function<LivingEntity, Float> function) {
        this.speedModifier = function;

        return this;
    }

    public FollowOwnerTask range(int min, int max) {
        this.range = UniformInt.of(min, max);

        return this;
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORIES;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, T entity) {
        LivingEntity owner = entity.getOwner();
        if(owner == null) {
            return false;
        }
        if(owner.isSpectator()) {
            return false;
        }
        if(entity.IsSitting()) {
            return false;
        }
        if(entity.distanceToSqr(owner) < this.range.getMinValue() * this.range.getMinValue()) {
            return false;
        }
        this.owner = owner;
        return super.checkExtraStartConditions(level, entity);
    }

    @Override
    protected boolean shouldKeepRunning(T entity) {
        if(entity.IsSitting() || this.owner == null) {
            return false;
        }

        double distance = entity.distanceToSqr(this.owner);
        return distance > (this.range.getMinValue() * this.range.getMinValue());
    }

    @Override
    protected void tick(T entity) {
        Brain<?> brain = entity.getBrain();
        BrainUtil.setMemory(brain, MemoryModuleType.LOOK_TARGET, new EntityTracker(this.owner, true));

        if(--this.updateCountdownTicks <= 0) {
            this.updateCountdownTicks = 10;
            if(!entity.isLeashed() && !entity.isPassenger()) {
                double distance = entity.distanceToSqr(this.owner);

                if(distance >= 144.0) {
                    BrainUtil.setMemory(brain, HodgepodgeMemoryTypes.TELEPORT_TARGET.get(), new TeleportTarget(this.owner));
                    BrainUtil.clearMemory(brain, MemoryModuleType.WALK_TARGET);
                } else {
                    BrainUtil.clearMemory(brain, HodgepodgeMemoryTypes.TELEPORT_TARGET.get());
                    BrainUtil.setMemory(brain, MemoryModuleType.WALK_TARGET,
                            new WalkTarget(this.owner, this.speedModifier.apply(entity), 2));
                }

            }
        }

    }
}
