package turou.powerful_tank.block;

import it.zerono.mods.zerocore.lib.multiblock.validation.ValidationError;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import turou.powerful_tank.PowerfulTank;
import turou.powerful_tank.tileentity.GlassWallTileEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class GlassWallBlock extends AbstractGlassBlock {
    public GlassWallBlock() {
        super(Properties.of(Material.GLASS, MaterialColor.NONE).sound(SoundType.GLASS).noOcclusion());
        this.setRegistryName("powerful_tank", "wall_block");
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new GlassWallTileEntity();
    }

    @Nonnull
    @Override
    public ActionResultType use(@Nonnull BlockState pState, World pLevel, @Nonnull BlockPos pPos, @Nonnull PlayerEntity pPlayer, @Nonnull Hand pHand, @Nonnull BlockRayTraceResult pHit) {
        if (!pLevel.isClientSide && pHand == Hand.MAIN_HAND) {
            GlassWallTileEntity te = (GlassWallTileEntity) pLevel.getBlockEntity(pPos);
            if (te != null && te.getMultiblockController().isPresent() && !te.isMachineAssembled() && pPlayer.isShiftKeyDown()) {
                Optional<ValidationError> errorOptional = te.getMultiblockController().get().getLastError();
                errorOptional.ifPresent(validationError -> pPlayer.sendMessage(validationError.getChatMessage(), PowerfulTank.POWERFUL_TANK_UUID));
                return ActionResultType.SUCCESS;
            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}
