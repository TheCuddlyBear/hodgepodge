package me.thecuddlybear.hodgepodge.entity;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import me.thecuddlybear.hodgepodge.Hodgepodge;

public class HodgepodgeEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Hodgepodge.MOD_ID, Registries.ENTITY_TYPE);

    public static final ResourceKey<EntityType<?>> SHROOMIE_KEY = ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Hodgepodge.MOD_ID, "shroomie"));

    public static final RegistrySupplier<EntityType<ShroomieEntity>> SHROOMIE = ENTITIES.register("shroomie", () ->
        EntityType.Builder.of(ShroomieEntity::new, MobCategory.CREATURE)
            .sized(0.6F, 1.8F) // Adjust size as needed
            .build(SHROOMIE_KEY));

    public static void init() {
        ENTITIES.register();
    }
}