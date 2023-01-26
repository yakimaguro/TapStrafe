package me.yakimaguro.tapstrafe.mixin;

import me.yakimaguro.tapstrafe.util.MC;
import me.yakimaguro.tapstrafe.util.PlayerUtil;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin implements MC {

	@Inject(at = @At("HEAD"), method = "onMouseScroll")
	private void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
		if (mc.player != null && !mc.player.getAbilities().flying) {
			if (vertical == 1.0) {
				if (mc.player.isOnGround()) {
					mc.player.jump();
				}
			}
			if (vertical == -1.0) {
				if (!mc.player.isOnGround() && !mc.player.input.pressingForward && !mc.player.input.pressingRight && !mc.player.input.pressingLeft) {
					PlayerUtil.setSpeed((float) PlayerUtil.getNormalSpeed(), 0);
				}
				if (!mc.player.isOnGround() && !mc.player.input.pressingForward && mc.player.input.pressingRight) {
					PlayerUtil.setSpeed((float) PlayerUtil.getNormalSpeed() * (2 + PlayerUtil.inertia),-80);
				}
				if (!mc.player.isOnGround() && !mc.player.input.pressingForward && mc.player.input.pressingLeft) {
					PlayerUtil.setSpeed((float) PlayerUtil.getNormalSpeed() * (2 + PlayerUtil.inertia), 80);
				}
			}
		}
	}
}
