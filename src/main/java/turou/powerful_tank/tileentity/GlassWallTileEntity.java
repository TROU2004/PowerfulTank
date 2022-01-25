package turou.powerful_tank.tileentity;

import it.zerono.mods.zerocore.lib.multiblock.cuboid.AbstractCuboidMultiblockPart;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import turou.powerful_tank.PowerfulTank;
import turou.powerful_tank.multiblock.MultiblockTank;

import javax.annotation.Nonnull;

public class GlassWallTileEntity extends AbstractCuboidMultiblockPart<MultiblockTank> {
    public GlassWallTileEntity() {
        super(PowerfulTank.RegistryEvents.TILE_ENTITY_TYPE_WALL);
    }

    @Override
    public boolean isGoodForPosition(@Nonnull PartPosition partPosition, @Nonnull IMultiblockValidator iMultiblockValidator) {
        return partPosition != PartPosition.Interior;
    }

    @Nonnull
    @Override
    public MultiblockTank createController() {
        return new MultiblockTank(getCurrentWorld());
    }

    @Nonnull
    @Override
    public Class<MultiblockTank> getControllerType() {
        return MultiblockTank.class;
    }

    @Override
    public void onMachineActivated() {

    }

    @Override
    public void onMachineDeactivated() {

    }
}
