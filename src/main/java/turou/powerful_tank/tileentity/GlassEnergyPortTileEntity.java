package turou.powerful_tank.tileentity;

import it.zerono.mods.zerocore.lib.multiblock.cuboid.AbstractCuboidMultiblockPart;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import turou.powerful_tank.PowerfulTank;
import turou.powerful_tank.multiblock.MultiblockTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GlassEnergyPortTileEntity extends AbstractCuboidMultiblockPart<MultiblockTank> {

    public GlassEnergyPortTileEntity() {
        super(PowerfulTank.RegistryEvents.TILE_ENTITY_TYPE_ENERGY_PORT);
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
        if (cap == CapabilityEnergy.ENERGY && getMultiblockController().isPresent() && isMachineAssembled()) {
            return LazyOptional.of(() -> getMultiblockController().get().energyStorage).cast();
        }
        return super.getCapability(cap, side);
    }

    public String getEnergyText() {
        int energy = 0;
        int cost = 0;
        if (getMultiblockController().isPresent() && isMachineAssembled()) {
            energy = getMultiblockController().get().energyStorage.getEnergyStored();
            cost = getMultiblockController().get().getCost();
        }
        return String.format("Energy: %d / %d FE, Cost: %d FE/t", energy, MultiblockTank.ENERGY_CAPACITY, cost);
    }
}
