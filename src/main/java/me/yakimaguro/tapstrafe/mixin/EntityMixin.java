package me.yakimaguro.tapstrafe.mixin;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow
    public void setNoGravity(boolean noGravity) {

    }

    @Shadow
    protected boolean onGround;

}
