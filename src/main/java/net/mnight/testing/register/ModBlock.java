package net.mnight.testing.register;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mnight.testing.LogPileBlock;
import net.mnight.testing.Testing;

import java.util.function.Supplier;

public class ModBlock {
    public static final DeferredRegister<Block> BLOCKS =
        DeferredRegister.create(ForgeRegistries.BLOCKS, Testing.MOD_ID);

    public static final RegistryObject<Block> LOG_PILE = BLOCKS.register("log_pile",
            () -> new LogPileBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));

    private static <T extends Block> RegistryObject<T> registryObject(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registryBlockItem(String name, Supplier<T> block){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
