package turou.powerful_tank;

import com.mojang.datafixers.DSL;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import turou.powerful_tank.block.GlassEnergyPortBlock;
import turou.powerful_tank.block.GlassFluidPortBlock;
import turou.powerful_tank.block.GlassWallBlock;
import turou.powerful_tank.tileentity.GlassEnergyPortTileEntity;
import turou.powerful_tank.tileentity.GlassFluidPortTileEntity;
import turou.powerful_tank.tileentity.GlassWallTileEntity;

import java.util.UUID;

@Mod("powerful_tank")
public class PowerfulTank {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final UUID POWERFUL_TANK_UUID = UUID.fromString("10D4F60E-61F0-3E41-5BAF-56CE4B68145C");


    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        public static final GlassEnergyPortBlock BLOCK_ENERGY_PORT = new GlassEnergyPortBlock();
        public static final GlassFluidPortBlock BLOCK_FLUID_PORT = new GlassFluidPortBlock();
        public static final GlassWallBlock BLOCK_WALL = new GlassWallBlock();
        public static final TileEntityType<GlassEnergyPortTileEntity> TILE_ENTITY_TYPE_ENERGY_PORT = TileEntityType.Builder.of(GlassEnergyPortTileEntity::new, BLOCK_ENERGY_PORT).build(DSL.remainderType());
        public static final TileEntityType<GlassFluidPortTileEntity> TILE_ENTITY_TYPE_FLUID_PORT = TileEntityType.Builder.of(GlassFluidPortTileEntity::new, BLOCK_FLUID_PORT).build(DSL.remainderType());
        public static final TileEntityType<GlassWallTileEntity> TILE_ENTITY_TYPE_WALL = TileEntityType.Builder.of(GlassWallTileEntity::new, BLOCK_WALL).build(DSL.remainderType());

        @SubscribeEvent
        public static void onBlocksRegistry(RegistryEvent.Register<Block> event) {
            event.getRegistry().registerAll(BLOCK_ENERGY_PORT, BLOCK_FLUID_PORT, BLOCK_WALL);
        }

        @SubscribeEvent
        public static void onItemsRegistry(RegistryEvent.Register<Item> event) {
            event.getRegistry().register(new BlockItem(BLOCK_ENERGY_PORT, new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)).setRegistryName("powerful_tank", "energy_port_block"));
            event.getRegistry().register(new BlockItem(BLOCK_FLUID_PORT, new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)).setRegistryName("powerful_tank", "fluid_port_block"));
            event.getRegistry().register(new BlockItem(BLOCK_WALL, new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)).setRegistryName("powerful_tank", "wall_block"));
        }

        @SubscribeEvent
        public static void onTileEntitiesRegistry(RegistryEvent.Register<TileEntityType<?>> event) {
            event.getRegistry().register(TILE_ENTITY_TYPE_ENERGY_PORT.setRegistryName("powerful_tank:energy_port"));
            event.getRegistry().register(TILE_ENTITY_TYPE_FLUID_PORT.setRegistryName("powerful_tank:fluid_port"));
            event.getRegistry().register(TILE_ENTITY_TYPE_WALL.setRegistryName("powerful_tank:wall"));
        }

        @SubscribeEvent
        public static void onRenderTypeRegistry(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                RenderTypeLookup.setRenderLayer(BLOCK_ENERGY_PORT, RenderType.cutoutMipped());
                RenderTypeLookup.setRenderLayer(BLOCK_FLUID_PORT, RenderType.cutoutMipped());
                RenderTypeLookup.setRenderLayer(BLOCK_WALL, RenderType.cutoutMipped());
            });
        }
    }
}
