package turou.powerful_tank.block;

import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import turou.powerful_tank.tileentity.GlassWallTileEntity;

import javax.annotation.Nullable;

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
}
