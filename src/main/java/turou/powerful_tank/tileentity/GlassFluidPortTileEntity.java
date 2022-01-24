package turou.powerful_tank.tileentity;

import it.zerono.mods.zerocore.lib.multiblock.cuboid.AbstractCuboidMultiblockPart;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import turou.powerful_tank.PowerfulTank;
import turou.powerful_tank.multiblock.MultiblockTank;

import javax.annotation.Nonnull;
import java.util.Objects;

public class GlassFluidPortTileEntity extends AbstractCuboidMultiblockPart<MultiblockTank> {

    public GlassFluidPortTileEntity() {
        super(PowerfulTank.RegistryEvents.TILE_ENTITY_TYPE_FLUID_PORT);
    }

    @Override
    public boolean isGoodForPosition(@Nonnull PartPosition partPosition, @Nonnull IMultiblockValidator iMultiblockValidator) {
        return true;
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

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        boolean isEnergy = Objects.equals(cap, CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);
        return isEnergy ? LazyOptional.of(() -> getMultiblockController().get().fluidTank).cast() : super.getCapability(cap);
    }
}
