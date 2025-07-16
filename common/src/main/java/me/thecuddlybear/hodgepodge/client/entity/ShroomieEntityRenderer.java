package me.thecuddlybear.hodgepodge.client.entity;

import me.thecuddlybear.hodgepodge.entity.ShroomieEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class ShroomieEntityRenderer <R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<ShroomieEntity, R> {

    public ShroomieEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new ShroomieEntityModel());
    }

}
