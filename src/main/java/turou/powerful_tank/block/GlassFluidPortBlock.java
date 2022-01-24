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
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import turou.powerful_tank.tileentity.GlassFluidPortTileEntity;

import javax.annotation.Nullable;
import java.util.UUID;

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

    @Override
    public ActionResultType use(BlockState pState, World pLevel, BlockPos pPos, PlayerEntity pPlayer, Hand pHand, BlockRayTraceResult pHit) {
        if (!pLevel.isClientSide && pHand == Hand.MAIN_HAND) {
            TileEntity te = pLevel.getBlockEntity(pPos);
            if (te instanceof GlassFluidPortTileEntity) {
                GlassFluidPortTileEntity port = (GlassFluidPortTileEntity) te;
                if (port.getMultiblockController().isPresent()) {
                    pPlayer.sendMessage(port.getMultiblockController().get().getLastError().get().getChatMessage(), UUID.randomUUID());
                }

            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}
