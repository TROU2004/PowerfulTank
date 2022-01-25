package turou.powerful_tank.block;


import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import turou.powerful_tank.PowerfulTank;
import turou.powerful_tank.tileentity.GlassFluidPortTileEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GlassFluidPortBlock extends AbstractGlassBlock {

    public GlassFluidPortBlock() {
        super(Properties.of(Material.GLASS, MaterialColor.NONE).noOcclusion());
        this.setRegistryName("powerful_tank", "fluid_port_block");
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new GlassFluidPortTileEntity();
    }

    @Nonnull
    @Override
    public ActionResultType use(@Nonnull BlockState pState, World pLevel, @Nonnull BlockPos pPos, @Nonnull PlayerEntity pPlayer, @Nonnull Hand pHand, @Nonnull BlockRayTraceResult pHit) {
        if (!pLevel.isClientSide && pHand == Hand.MAIN_HAND) {
            GlassFluidPortTileEntity te = (GlassFluidPortTileEntity) pLevel.getBlockEntity(pPos);
            if (te != null && te.isMachineAssembled()) {
                pPlayer.sendMessage(new StringTextComponent(te.getFluidText()), PowerfulTank.POWERFUL_TANK_UUID);
                return ActionResultType.SUCCESS;
            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}
