package turou.powerful_tank.tileentity;

import it.zerono.mods.zerocore.lib.multiblock.cuboid.AbstractCuboidMultiblockPart;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import turou.powerful_tank.PowerfulTank;
import turou.powerful_tank.multiblock.MultiblockTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class GlassFluidPortTileEntity extends AbstractCuboidMultiblockPart<MultiblockTank> {

    public final FluidTank fluidTank = new FluidTank(Integer.MAX_VALUE){
        @Override
        public int fill(FluidStack resource, FluidAction action) {
            return super.fill(GlassFluidPortTileEntity.this.isMachineAssembled() ? resource : FluidStack.EMPTY, action);
        }

        @Nonnull
        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            return super.drain(GlassFluidPortTileEntity.this.isMachineAssembled() ? maxDrain : 0, action);
        }
    };

    public GlassFluidPortTileEntity() {
        super(PowerfulTank.RegistryEvents.TILE_ENTITY_TYPE_FLUID_PORT);
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

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return LazyOptional.of(() -> fluidTank).cast();
        }
        return super.getCapability(cap, side);
    }

    public String getFluidText() {
        FluidStack fluidStack = fluidTank.getFluid();
        int cost = 0;
        if (getMultiblockController().isPresent() && isMachineAssembled()) {
            cost = getMultiblockController().get().getCost();
        }

        return String.format("Fluid(%s): %d / %s mb, Cost: %d FE/t", I18n.get(fluidStack.getTranslationKey()), fluidStack.getAmount(), "INFINITE", cost);
    }

    @Override
    public void syncDataFrom(@Nonnull CompoundNBT data, @Nonnull SyncReason syncReason) {
        super.syncDataFrom(data, syncReason);
        fluidTank.readFromNBT(data);
    }

    @Nonnull
    @Override
    public CompoundNBT syncDataTo(@Nonnull CompoundNBT data, @Nonnull SyncReason syncReason) {
        fluidTank.writeToNBT(data);
        return super.syncDataTo(data, syncReason);
    }
}
