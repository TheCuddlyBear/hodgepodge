package me.thecuddlybear.hodgepodge.entity;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.thecuddlybear.hodgepodge.Hodgepodge;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.InWaterSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyAdultSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animatable.processing.AnimationTest;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class ShroomieEntity extends TamableAnimal implements GeoEntity, SmartBrainOwner<ShroomieEntity> {
    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("animation.shroomie.idle");
    protected static final RawAnimation SIT_ANIM = RawAnimation.begin().thenLoop("animation.shroomie.sitting");
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public static final Predicate<LivingEntity> FOLLOW_TAMED_PREDICATE;
    public static final int RED_TYPE = 0;
    public static final int BLUE_TYPE = 1;
    public static final int GREEN_TYPE = 2;
    public static final int PURPLE_TYPE = 3;
    public static final int PINK_TYPE = 4;
    public static final Map<Integer, ResourceLocation> TEXTURES;
    private static final EntityDataAccessor<Integer> TYPE;

    public ShroomieEntity(EntityType<? extends TamableAnimal> entityType, Level level){
        super(entityType, level);
        this.setTame(false , false);
    }

    //AI
    @Override
    public List<? extends ExtendedSensor<? extends ShroomieEntity>> getSensors() {
        return ObjectArrayList.of(
          new NearbyLivingEntitySensor<>(),
          new NearbyPlayersSensor<>(),
          new NearbyAdultSensor<>(),
          new InWaterSensor<>()
        );
    }

    // TODO: core tasks
    // TODO: idle tasks
    // TODO: activity priorities

    @Override
    protected Brain.@NotNull Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    public int getShroomieType() {
        return this.entityData.get(TYPE);
    }

    public void setShroomieType(int type) {
        if (type < 0 || type >= 5){
            type = this.random.nextInt(5);
        }

        this.entityData.set(TYPE, type);
    }

    public ResourceLocation getTexture() {
        return TEXTURES.getOrDefault(this.getShroomieType(), TEXTURES.get(0));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData spawnGroupData) {
        spawnGroupData = super.finalizeSpawn(level, difficulty, spawnReason, spawnGroupData);
        if(level.getMoonBrightness() > 0.9F) {
            this.setShroomieType(this.random.nextInt(5));
        }else {
            this.setShroomieType(this.random.nextInt(4));
        }

        return spawnGroupData;
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("ShroomieType", this.getShroomieType());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.setShroomieType(input.getIntOr("ShroomieType", 0));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(TYPE, 0);
    }

    public static AttributeSupplier.Builder createShroomieAttributes() {
        return TamableAnimal.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    @Override
    public Vec3 getLeashOffset() {
        return new Vec3(0.0D, 0.6F * this.getEyeHeight(), this.getBbWidth() * 0.4F);
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.HAPPY_GHAST_DEATH;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.HAPPY_GHAST_HURT;
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.HAPPY_GHAST_AMBIENT;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        ShroomieEntity shroomieEntity = HodgepodgeEntities.SHROOMIE.get().create(level, EntitySpawnReason.BREEDING);
        LivingEntity owner = this.getOwner();
        if (owner != null){
            shroomieEntity.setOwner(owner);
            shroomieEntity.setTame(true, true);
        }

        if(otherParent instanceof ShroomieEntity){
            if (this.random.nextBoolean()) {
                shroomieEntity.setShroomieType(this.getShroomieType());
            } else {
                shroomieEntity.setShroomieType(((ShroomieEntity)otherParent).getShroomieType());
            }
        }

        return shroomieEntity;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if(this.level().isClientSide){
            boolean bl = this.isOwnedBy(player) || this.isTame() || itemstack.is(Items.RED_MUSHROOM) || itemstack.is(Items.BROWN_MUSHROOM) && !this.isTame();
            return bl ? InteractionResult.CONSUME : InteractionResult.PASS;
        } else {
            if(this.isTame()){
                if(this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()){
                    if(!player.getAbilities().instabuild){
                        itemstack.shrink(1);
                    }

                    this.heal(1);
                    this.gameEvent(GameEvent.ENTITY_INTERACT);
                    return InteractionResult.CONSUME;
                } else{
                    this.setOrderedToSit(!this.isOrderedToSit());
                    this.gameEvent(GameEvent.ENTITY_INTERACT);
                    return InteractionResult.SUCCESS;
                }
            } else if(itemstack.is(Items.RED_MUSHROOM) || itemstack.is(Items.BROWN_MUSHROOM)){
                if(!player.getAbilities().instabuild){
                    itemstack.shrink(1);
                }

                if(this.random.nextInt(3) == 0) {
                    this.setOwner(player);
                    this.navigation.stop();
                    this.setTarget((LivingEntity)null);
                    this.setTame(true, true);
                    this.setOrderedToSit(true);
                    this.gameEvent(GameEvent.ENTITY_INTERACT);
                    this.level().broadcastEntityEvent(this, (byte)7);
                }else {
                    this.level().broadcastEntityEvent(this, (byte)6);
                }

                return InteractionResult.SUCCESS;
            }
            return super.mobInteract(player, hand);
        }
    }

    @Override
    public void setTame(boolean tame, boolean applyTamingSideEffects) {
        super.setTame(tame, applyTamingSideEffects);
        if (tame) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
            this.setHealth(20.0F);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
        }

        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }

    protected <E extends ShroomieEntity> PlayState predicate(final AnimationTest<E> animTest) {
        if(this.isInSittingPose()){
            return animTest.setAndContinue(SIT_ANIM);
        }
        if(animTest.isMoving()){
            return animTest.setAndContinue(IDLE_ANIM);
        }

        return animTest.setAndContinue(IDLE_ANIM);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<ShroomieEntity>("Predicate", 5, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    @Override
    protected void customServerAiStep(ServerLevel level) {
        this.tickBrain(this);
        if(this.getServer() == null) {
            return;
        }

        if(!this.isTame()) {
            return;
        }
    }

    static {
        FOLLOW_TAMED_PREDICATE = (entity) -> {
            EntityType<?> entityType = entity.getType();
            return entityType == EntityType.SHEEP || entityType == EntityType.MOOSHROOM;
        };
        TYPE = SynchedEntityData.defineId(ShroomieEntity.class, EntityDataSerializers.INT);

        TEXTURES = new HashMap<>();

        TEXTURES.put(0, ResourceLocation.fromNamespaceAndPath(Hodgepodge.MOD_ID, "textures/entity/shroomie/shroomie_red.png"));
        TEXTURES.put(1, ResourceLocation.fromNamespaceAndPath(Hodgepodge.MOD_ID, "textures/entity/shroomie/shroomie_blue.png"));
        TEXTURES.put(2, ResourceLocation.fromNamespaceAndPath(Hodgepodge.MOD_ID, "textures/entity/shroomie/shroomie_green.png"));
        TEXTURES.put(3, ResourceLocation.fromNamespaceAndPath(Hodgepodge.MOD_ID, "textures/entity/shroomie/shroomie_purple.png"));
        TEXTURES.put(4, ResourceLocation.fromNamespaceAndPath(Hodgepodge.MOD_ID, "textures/entity/shroomie/shroomie_pink.png"));

    }

}
