package turou.powerful_tank.tileentity;

import it.zerono.mods.zerocore.lib.multiblock.cuboid.AbstractCuboidMultiblockPart;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import turou.powerful_tank.PowerfulTank;
import turou.powerful_tank.multiblock.MultiblockTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GlassEnergyPortTileEntity extends AbstractCuboidMultiblockPart<MultiblockTank> {

    public final EnergyStorage energyStorage = new EnergyStorage(2_500_000, 2_500_000);

    public GlassEnergyPortTileEntity() {
        super(PowerfulTank.RegistryEvents.TILE_ENTITY_TYPE_ENERGY_PORT);
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
        if (cap == CapabilityEnergy.ENERGY) {
            return LazyOptional.of(() -> energyStorage).cast();
        }
        return super.getCapability(cap, side);
    }

    public String getEnergyText() {
        int cost = 0;
        if (getMultiblockController().isPresent() && isMachineAssembled()) {
            cost = getMultiblockController().get().getCost();
        }
        return String.format("Energy: %d / %d FE, Cost: %d FE/t", energyStorage.getEnergyStored(), energyStorage.getMaxEnergyStored(), cost);
    }

    @Override
    public void syncDataFrom(@Nonnull CompoundNBT data, @Nonnull SyncReason syncReason) {
        super.syncDataFrom(data, syncReason);
        energyStorage.receiveEnergy(data.getInt("energy"), false);
    }

    @Nonnull
    @Override
    public CompoundNBT syncDataTo(CompoundNBT data, @Nonnull SyncReason syncReason) {
        data.putInt("energy", energyStorage.getEnergyStored());
        return super.syncDataTo(data, syncReason);
    }
}
