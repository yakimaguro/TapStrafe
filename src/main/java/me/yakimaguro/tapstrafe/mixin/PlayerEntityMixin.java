package me.yakimaguro.tapstrafe.mixin;

import me.yakimaguro.tapstrafe.TapStrafeMod;
import me.yakimaguro.tapstrafe.util.PlayerUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin extends EntityMixin {

    MinecraftClient mc = MinecraftClient.getInstance();

    private int yaw = 0;

    private int tick = 0;

    private boolean climbing = false;

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo ci) {
        if (mc.player != null && !mc.player.getAbilities().flying) {
            if (mc.player.horizontalCollision) {
                if (mc.player.input.pressingForward) {
                    climbing = true;
                    mc.player.setVelocity(mc.player.getVelocity().getX(), (float) PlayerUtil.getNormalSpeed() * 0.6f, mc.player.getVelocity().getZ());
                }
            } else {
                if (climbing) {
                    tick++;
                    if (mc.player.input.sneaking) {
                        mc.player.jump();
                        PlayerUtil.setSpeed((float) PlayerUtil.getNormalSpeed() * 4.0f, 0);
                    }
                    if (tick <= 40) {
                        climbing = false;
                    }
                }
            }
            if (mc.player.isOnGround() && mc.player.input.sneaking && mc.player.isSprinting()) {
                if (mc.player.input.pressingForward) {
                    yaw = 0;
                }
                if (mc.player.input.pressingRight) {
                    yaw += 45;
                }
                if (mc.player.input.pressingLeft) {
                    yaw -= 45;
                }
                if (mc.player.input.pressingBack) {
                    yaw += 180;
                }
                PlayerUtil.setSpeed((float) PlayerUtil.getNormalSpeed() * PlayerUtil.speed, yaw);
                if (PlayerUtil.speed >= 0) {
                    PlayerUtil.speed -= 0.02f;
                }
            } else if (PlayerUtil.speed <= 2.0f) {
                PlayerUtil.speed += 0.01f;
            }
        }
    }
}
