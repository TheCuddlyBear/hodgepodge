package me.thecuddlybear.hodgepodge.client.entity;

import me.thecuddlybear.hodgepodge.Hodgepodge;
import me.thecuddlybear.hodgepodge.entity.ShroomieEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Pose;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.processing.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.loading.json.raw.Bone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class ShroomieEntityModel extends GeoModel<ShroomieEntity> {

    public static DataTicket<ResourceLocation> TEXTURE = DataTicket.create("texture", ResourceLocation.class);

    @Override
    public ResourceLocation getModelResource(GeoRenderState renderState) {
        return ResourceLocation.fromNamespaceAndPath(Hodgepodge.MOD_ID, "shroomie");
    }

    @Override
    public ResourceLocation getTextureResource(GeoRenderState renderState) {
        return renderState.getGeckolibData(TEXTURE);
    }

    @Override
    public void addAdditionalStateData(ShroomieEntity animatable, GeoRenderState renderState) {
        super.addAdditionalStateData(animatable, renderState);
        ResourceLocation text = animatable.getTexture();
        renderState.addGeckolibData(TEXTURE, text);
    }

    @Override
    public ResourceLocation getAnimationResource(ShroomieEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(Hodgepodge.MOD_ID, "shroomie");
    }

    @Override
    public void setCustomAnimations(AnimationState<ShroomieEntity> animationState) {
        super.setCustomAnimations(animationState);

        GeoBone head = getAnimationProcessor().getBone("upper_body"); // or whatever the actual name is
        if (head == null) {
            // Add some debug logging to see what bones are available
            System.out.println("Head bone not found! Available bones:");
            getAnimationProcessor().getRegisteredBones().forEach((bone) ->
                    System.out.println("  - " + bone.getName()));
            return;
        }


        Float pitch = animationState.getData(DataTickets.ENTITY_PITCH);
        //Float yaw = animationState.getData(DataTickets.ENTITY_YAW);

        if(pitch == null) return;

        head.setRotX(Math.min(Math.max(0, pitch * ((float) Math.PI / 130F)), 20 * ((float) Math.PI / 130F)));
        //head.setRotY(yaw * ((float) Math.PI / 180F));
    }


}
