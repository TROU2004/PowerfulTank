package turou.powerful_tank.multiblock;

import it.zerono.mods.zerocore.lib.multiblock.IMultiblockController;
import it.zerono.mods.zerocore.lib.multiblock.IMultiblockPart;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.AbstractCuboidMultiblockController;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;

public class MultiblockTank extends AbstractCuboidMultiblockController<MultiblockTank> {
    public static final int ENERGY_CAPACITY = 2_500_000;

    public FluidTank fluidTank = new FluidTank(Integer.MAX_VALUE);
    public EnergyStorage energyStorage = new EnergyStorage(ENERGY_CAPACITY, 2_500_000, 0);

    public MultiblockTank(World world) {
        super(world);
    }

    @Override
    protected void onPartAdded(@Nonnull IMultiblockPart<MultiblockTank> iMultiblockPart) {

    }

    @Override
    protected void onPartRemoved(@Nonnull IMultiblockPart<MultiblockTank> iMultiblockPart) {

    }

    @Override
    protected void onMachineRestored() {

    }

    @Override
    protected void onMachinePaused() {

    }

    @Override
    protected void onMachineDisassembled() {

    }

    @Override
    protected int getMinimumNumberOfPartsForAssembledMachine() {
        return 26;
    }

    @Override
    protected int getMaximumXSize() {
        return 3;
    }

    @Override
    protected int getMinimumXSize() {
        return 3;
    }

    @Override
    protected int getMaximumZSize() {
        return 3;
    }

    @Override
    protected int getMinimumZSize() {
        return 3;
    }

    @Override
    protected int getMaximumYSize() {
        return 3;
    }

    @Override
    protected int getMinimumYSize() {
        return 3;
    }

    @Override
    protected void onAssimilate(@Nonnull IMultiblockController<MultiblockTank> iMultiblockController) {

    }

    @Override
    protected void onAssimilated(@Nonnull IMultiblockController<MultiblockTank> iMultiblockController) {

    }

    @Override
    protected boolean updateServer() {
        energyStorage.extractEnergy(getCost(), false);
        if (energyStorage.getEnergyStored() == 0 && fluidTank.getFluidAmount() != 0) {
            fluidTank.setFluid(FluidStack.EMPTY);
        }
        return true;
    }

    @Override
    protected void updateClient() {

    }

    @Override
    protected boolean isBlockGoodForFrame(@Nonnull World world, int i, int i1, int i2, @Nonnull IMultiblockValidator iMultiblockValidator) {
        return false;
    }

    @Override
    protected boolean isBlockGoodForTop(@Nonnull World world, int i, int i1, int i2, @Nonnull IMultiblockValidator iMultiblockValidator) {
        return false;
    }

    @Override
    protected boolean isBlockGoodForBottom(@Nonnull World world, int i, int i1, int i2, @Nonnull IMultiblockValidator iMultiblockValidator) {
        return false;
    }

    @Override
    protected boolean isBlockGoodForSides(@Nonnull World world, int i, int i1, int i2, @Nonnull IMultiblockValidator iMultiblockValidator) {
        return false;
    }

    @Override
    protected boolean isBlockGoodForInterior(@Nonnull World world, int i, int i1, int i2, @Nonnull IMultiblockValidator iMultiblockValidator) {
        return false;
    }

    public int getCost() {
        return (int) Math.pow(fluidTank.getFluidAmount() / 1000.0, 0.8);
    }
}
