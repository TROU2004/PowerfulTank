package turou.powerful_tank.tileentity;

import it.zerono.mods.zerocore.lib.multiblock.cuboid.AbstractCuboidMultiblockPart;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import turou.powerful_tank.PowerfulTank;
import turou.powerful_tank.multiblock.MultiblockTank;

import javax.annotation.Nonnull;
import java.util.Objects;

public class GlassEnergyPortTileEntity extends AbstractCuboidMultiblockPart<MultiblockTank> {

    public GlassEnergyPortTileEntity() {
        super(PowerfulTank.RegistryEvents.TILE_ENTITY_TYPE_ENERGY_PORT);
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
        boolean isEnergy = Objects.equals(cap, CapabilityEnergy.ENERGY);
        return isEnergy ? LazyOptional.of(() -> getMultiblockController().get().energyStorage).cast() : super.getCapability(cap);
    }
}
