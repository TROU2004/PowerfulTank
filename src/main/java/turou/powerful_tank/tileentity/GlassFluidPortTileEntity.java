package turou.powerful_tank.tileentity;

import it.zerono.mods.zerocore.lib.multiblock.cuboid.AbstractCuboidMultiblockPart;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import turou.powerful_tank.PowerfulTank;
import turou.powerful_tank.multiblock.MultiblockTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class GlassFluidPortTileEntity extends AbstractCuboidMultiblockPart<MultiblockTank> {

    public GlassFluidPortTileEntity() {
        super(PowerfulTank.RegistryEvents.TILE_ENTITY_TYPE_FLUID_PORT);
    }

    @Override
    public boolean isGoodForPosition(@Nonnull PartPosition partPosition, @Nonnull IMultiblockValidator iMultiblockValidator) {
        return partPosition.getType() != PartPosition.Type.Interior;
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
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && getMultiblockController().isPresent() && isMachineAssembled()) {
            return LazyOptional.of(() -> getMultiblockController().get().fluidTank).cast();
        }
        return super.getCapability(cap, side);
    }

    public String getFluidText() {
        FluidStack fluidStack = FluidStack.EMPTY;
        int cost = 0;
        if (getMultiblockController().isPresent() && isMachineAssembled()) {
            fluidStack = getMultiblockController().get().fluidTank.getFluid();
            cost = getMultiblockController().get().getCost();
        }
        return String.format("Fluid(%s): %d / %s mb, Cost: %d FE/t", Objects.requireNonNull(fluidStack.getFluid().getRegistryName()).toString(), fluidStack.getAmount(), "INFINITE", cost);
    }
}
