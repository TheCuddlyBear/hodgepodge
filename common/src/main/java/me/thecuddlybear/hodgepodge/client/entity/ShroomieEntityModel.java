package me.thecuddlybear.hodgepodge.client.entity;

import me.thecuddlybear.hodgepodge.Hodgepodge;
import me.thecuddlybear.hodgepodge.entity.ShroomieEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.dataticket.DataTicket;
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
}
