package me.yakimaguro.tapstrafe.mixin;

import me.yakimaguro.tapstrafe.Direction;
import me.yakimaguro.tapstrafe.TapStrafeMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class PlayerEntityMixin {

	MinecraftClient mc = MinecraftClient.getInstance();

	@Inject(at = @At("HEAD"), method = "onMouseScroll")
	private void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
		if (mc.player != null) {
			if (vertical == 1.0) {
				if (mc.player.isOnGround()) {
					mc.player.jump();
				}
			}
			if (vertical == -1.0) {
				if (!mc.player.isOnGround() && !mc.player.input.pressingForward && !mc.player.input.pressingRight && !mc.player.input.pressingLeft) {
					setSpeed((float) getNormalSpeed(), Direction.FORWARD);
				}
				if (!mc.player.isOnGround() && !mc.player.input.pressingForward && mc.player.input.pressingRight) {
					setSpeed((float) getNormalSpeed() * 2, Direction.RIGHT);
				}
				if (!mc.player.isOnGround() && !mc.player.input.pressingForward && mc.player.input.pressingLeft) {
					setSpeed((float) getNormalSpeed() * 2, Direction.LEFT);
				}
			}
		}
	}

	public void setSpeed(float f, Direction direction) {
		if (mc.player != null && (mc.player.input.movementForward != 0.0F || mc.player.airStrafingSpeed != 0.0F)) {
			float rotationYaw = mc.player.getYaw();

			float yaw = (float) Math.toRadians(rotationYaw);
			if (direction == Direction.RIGHT) {
				yaw -= 80;
			}
			if (direction == Direction.LEFT) {
				yaw += 80;
			}
			mc.player.setVelocity(-Math.sin(yaw) * f, mc.player.getVelocity().getY(), Math.cos(yaw) * f);
		}
	}

	public double getNormalSpeed() {
		double speed = 0.2875D;
		if (mc.player.hasStatusEffect(StatusEffects.SPEED)) {
			speed *= 1.0D + 0.2D * (double) (mc.player.getStatusEffect(StatusEffects.SPEED).getAmplifier() + 1);
		}
		return speed;
	}

}
