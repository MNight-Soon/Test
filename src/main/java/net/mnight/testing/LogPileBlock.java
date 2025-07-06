package net.mnight.testing;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

public class LogPileBlock extends Block {
    public static final IntegerProperty AMOUNT = IntegerProperty.create("amount", 1,8);


    public LogPileBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(AMOUNT, 1));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(AMOUNT);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide && player.isShiftKeyDown()) {
            ItemStack held = player.getItemInHand(hand);
            if (held.getItem() == Items.OAK_LOG) {
                int current = state.getValue(AMOUNT);
                if (current < 8) {
                    level.setBlock(pos, state.setValue(AMOUNT, current + 1), 3);
                    if (!player.isCreative()) held.shrink(1);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            int amount = state.getValue(AMOUNT);
            popResource(level, pos, new ItemStack(Items.OAK_LOG, amount));
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

}
