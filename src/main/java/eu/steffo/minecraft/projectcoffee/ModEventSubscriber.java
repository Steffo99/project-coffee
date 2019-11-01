package eu.steffo.minecraft.projectcoffee;

import com.google.common.base.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

@EventBusSubscriber(modid=ProjectCoffee.MODID, bus=EventBusSubscriber.Bus.MOD)
public final class ModEventSubscriber {
    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();

        registry.register(
            setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "test")
        );

        for (final Block block : ForgeRegistries.BLOCKS.getValues()) {

            final ResourceLocation blockRegistryName = block.getRegistryName();
            // An extra safe-guard against badly registered blocks
            Preconditions.checkNotNull(blockRegistryName, "Registry Name of Block \"" + block + "\" of class \"" + block.getClass().getName() + "\"is null! This is not allowed!");

            // Check that the blocks is from our mod, if not, continue to the next block
            if (!blockRegistryName.getNamespace().equals(ProjectCoffee.MODID)) {
                continue;
            }

            // Make the properties, and make it so that the item will be on our ItemGroup (CreativeTab)
            final Item.Properties properties = new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP);
            // Create the new BlockItem with the block and it's properties
            final BlockItem blockItem = new BlockItem(block, properties);
            // Setup the new BlockItem with the block's registry name and register it
            registry.register(setup(blockItem, blockRegistryName));
        }
    }

    @SubscribeEvent
    public static void onRegisterBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
            setup(new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.0F)), "test_block"),
            setup(new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(Float.POSITIVE_INFINITY)), "full_black_block"),
            setup(new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(Float.POSITIVE_INFINITY).lightValue(15)), "lime_neon_left_block"),
            setup(new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(Float.POSITIVE_INFINITY).lightValue(15)), "lime_neon_right_block")
        );
    }

    private static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final String name) {
        return setup(entry, new ResourceLocation(ProjectCoffee.MODID, name));
    }

    private static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final ResourceLocation registryName) {
        entry.setRegistryName(registryName);
        return entry;
    }

}
