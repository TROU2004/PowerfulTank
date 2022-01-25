package turou.powerful_tank.multiblock;

import it.zerono.mods.zerocore.lib.energy.IWideEnergyProvider;
import it.zerono.mods.zerocore.lib.multiblock.IMultiblockController;
import it.zerono.mods.zerocore.lib.multiblock.IMultiblockPart;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.AbstractCuboidMultiblockController;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.AbstractCuboidMultiblockPart;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import turou.powerful_tank.tileentity.GlassEnergyPortTileEntity;
import turou.powerful_tank.tileentity.GlassFluidPortTileEntity;

import javax.annotation.Nonnull;

public class MultiblockTank extends AbstractCuboidMultiblockController<MultiblockTank> {

    private EnergyStorage energyStorage;
    private FluidTank fluidTank;

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
        findPorts();
    }

    @Override
    protected void onMachineAssembled() {
        findPorts();
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
        if (energyStorage != null && fluidTank != null) {
            if (energyStorage.getEnergyStored() > 0) energyStorage.extractEnergy(getCost(), false);
            if (energyStorage.getEnergyStored() == 0 && fluidTank.getFluidAmount() != 0) {
                fluidTank.setFluid(FluidStack.EMPTY);
            }
            return true;
        }
        return false;
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
    protected boolean isMachineWhole(IMultiblockValidator validatorCallback) {
        findPorts();
        if (energyStorage != null && fluidTank != null)
            return super.isMachineWhole(validatorCallback);
        else
            return false;
    }

    @Override
    protected boolean isBlockGoodForInterior(@Nonnull World world, int i, int i1, int i2, @Nonnull IMultiblockValidator iMultiblockValidator) {
        return world.getBlockState(new BlockPos(i, i1, i2)).getMaterial() == Material.AIR;
    }

    public int getCost() {
        return (int) Math.pow(fluidTank.getFluidAmount() / 1000.0, 0.8);
    }

    private void findPorts() {
        for (IMultiblockPart<MultiblockTank> connectedPart : _connectedParts) {
            if (connectedPart instanceof GlassEnergyPortTileEntity) energyStorage = ((GlassEnergyPortTileEntity) connectedPart).energyStorage;
            if (connectedPart instanceof GlassFluidPortTileEntity) fluidTank = ((GlassFluidPortTileEntity) connectedPart).fluidTank;
        }
    }
}
