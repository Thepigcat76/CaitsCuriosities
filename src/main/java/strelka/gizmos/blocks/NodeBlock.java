package strelka.gizmos.blocks;

import strelka.gizmos.blockentities.NodeBE;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NodeBlock extends BaseEntityBlock {
    public static final DirectionProperty ATTACHED_FACE = DirectionProperty.create("attached_face");
    public static final EnumProperty<NodeType> TYPE = EnumProperty.create("type", NodeType.class);

    protected static final VoxelShape NORTH_ATTACHED_AABB = Block.box(3, 3, 11, 13, 13, 16);
    protected static final VoxelShape SOUTH_ATTACHED_AABB = Block.box(3, 3, 0, 13, 13, 5);
    protected static final VoxelShape EAST_ATTACHED_AABB = Block.box(0, 3, 3, 5, 13, 13);
    protected static final VoxelShape WEST_ATTACHED_AABB = Block.box(11, 3, 3, 16, 13, 13);
    protected static final VoxelShape DOWN_ATTACHED_AABB = Block.box(3, 11, 3, 13, 16, 13);
    protected static final VoxelShape UP_ATTACHED_AABB = Block.box(3, 0, 3, 13, 5, 13);

    public NodeBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(ATTACHED_FACE, Direction.DOWN)
                .setValue(TYPE, NodeType.PULL));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(NodeBlock::new);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack pStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand pHand, BlockHitResult pHitResult) {
        if (player.isCrouching() || !pStack.isEmpty()) {
            return ItemInteractionResult.CONSUME;
        } else {
            if (state.getValue(TYPE) == NodeType.PULL)
                level.setBlock(pos, state.setValue(TYPE, NodeType.PUSH), 3);
            else
                level.setBlock(pos, state.setValue(TYPE, NodeType.PULL), 3);

            level.updateNeighborsAt(pos, this);
            level.playSound(player, pos, SoundEvents.STONE_BUTTON_CLICK_ON, SoundSource.BLOCKS, 0.3F, 0.6F);
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        NodeType type;
        if (context.getPlayer().isCrouching())
            type = NodeType.PUSH;
        else
            type = NodeType.PULL;

        return this.defaultBlockState().setValue(ATTACHED_FACE, context.getClickedFace()).setValue(TYPE, type);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        switch (state.getValue(ATTACHED_FACE)) {
            case NORTH -> {
                return NORTH_ATTACHED_AABB;
            }
            case SOUTH -> {
                return SOUTH_ATTACHED_AABB;
            }
            case EAST -> {
                return EAST_ATTACHED_AABB;
            }
            case WEST -> {
                return WEST_ATTACHED_AABB;
            }
            case DOWN -> {
                return DOWN_ATTACHED_AABB;
            }
            default -> {
                return UP_ATTACHED_AABB;
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(ATTACHED_FACE, TYPE);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new NodeBE(pPos, pState);
    }

}
