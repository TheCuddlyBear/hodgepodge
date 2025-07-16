package me.thecuddlybear.hodgepodge.entity.ai;

import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.DeferredRegister;
import me.thecuddlybear.hodgepodge.Hodgepodge;
import me.thecuddlybear.hodgepodge.entity.ai.brain.TeleportTarget;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import java.util.Optional;
import java.util.function.Supplier;

public class ModMemoryTypes {

    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_TYPES = DeferredRegister.create(Hodgepodge.MOD_ID, Registries.MEMORY_MODULE_TYPE);

    public static final Supplier<MemoryModuleType<TeleportTarget>> TELEPORT_TARGET = register("teleport_target");

    private static <U> Supplier<MemoryModuleType<U>> register(String id, Codec<U> codec) {
        return MEMORY_TYPES.register(id, () -> new MemoryModuleType<>(Optional.of(codec)));
    }

    private static <U> Supplier<MemoryModuleType<U>> register(String id) {
        return MEMORY_TYPES.register(id, () -> new MemoryModuleType<>(Optional.empty()));
    }

}
