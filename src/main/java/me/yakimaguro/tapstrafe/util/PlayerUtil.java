package me.yakimaguro.tapstrafe.util;

import net.minecraft.entity.effect.StatusEffects;

public class PlayerUtil implements MC {

    public static float inertia = 0f;
    public static float speed = 2.0f;

    public static void setSpeed(float f, int addYaw) {
        if (mc.player != null && (mc.player.input.movementForward != 0.0F || mc.player.airStrafingSpeed != 0.0F)) {
            float rotationYaw = mc.player.getYaw();

            float yaw = (float) Math.toRadians(rotationYaw) + addYaw;
            mc.player.setVelocity(-Math.sin(yaw) * f, mc.player.getVelocity().getY(), Math.cos(yaw) * f);
        }
    }

    public static double getNormalSpeed() {
        double speed = 0.2875D;
        if (mc.player.hasStatusEffect(StatusEffects.SPEED)) {
            speed *= 1.0D + 0.2D * (double) (mc.player.getStatusEffect(StatusEffects.SPEED).getAmplifier() + 1);
        }
        return speed;
    }

}
