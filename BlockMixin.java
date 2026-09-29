package com.caleon.client.mixin;

import com.caleon.client.module.Xray;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Xray hook. Minecraft changed the shouldDrawSide signature between versions, so both shapes are
 * targeted by full descriptor with require = 0: whichever one doesn't exist is skipped silently.
 */
@Mixin(Block.class)
public class BlockMixin {
    @Inject(method = "shouldDrawSide(Lnet/minecraft/block/BlockState;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;)Z",
            at = @At("HEAD"), cancellable = true, require = 0)
    private static void caleon$sideA(BlockState state, BlockState other, Direction side, CallbackInfoReturnable<Boolean> cir) {
        if (Xray.active) cir.setReturnValue(Xray.isVisible(state));
    }

    @Inject(method = "shouldDrawSide(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;Lnet/minecraft/util/math/BlockPos;)Z",
            at = @At("HEAD"), cancellable = true, require = 0)
    private static void caleon$sideB(BlockState state, BlockView world, BlockPos pos, Direction side, BlockPos other, CallbackInfoReturnable<Boolean> cir) {
        if (Xray.active) cir.setReturnValue(Xray.isVisible(state));
    }
}
