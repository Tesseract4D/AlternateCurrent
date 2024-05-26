package alternate.current.util;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BlockState {

    public static final BlockState AIR = new BlockState(Blocks.air, 0);

    private final Block block;
    private final int metadata;

    public BlockState(Block block, int metadata) {
        this.block = block;
        this.metadata = metadata;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BlockState) {
            BlockState state = (BlockState) obj;
            return state.block == block && state.metadata == metadata;
        }

        return false;
    }

    public Block getBlock() {
        return block;
    }

    public boolean is(Block block) {
        return this.block == block;
    }

    public int get() {
        return metadata;
    }

    public BlockState with(int metadata) {
        return new BlockState(block, metadata);
    }

    public boolean isAir() {
        return this == AIR;
    }

    public boolean isConductor() {
        return block.isOpaqueCube();
    }

    public boolean isSignalSource() {
        return block.canProvidePower();
    }

    public int getSignal(World world, BlockPos pos, Direction dir) {
        return block.isProvidingWeakPower(world, pos.x, pos.y, pos.z, dir.index);
    }

    public int getDirectSignal(World world, BlockPos pos, Direction dir) {
        return block.isProvidingStrongPower(world, pos.x, pos.y, pos.z, dir.index);
    }

    public boolean canSurviveAt(World world, BlockPos pos) {
        return block.canPlaceBlockAt(world, pos.x, pos.y, pos.z);
    }

    public void dropAsItem(World world, BlockPos pos) {
        block.dropBlockAsItem(world, pos.x, pos.y, pos.z, 0, 0);
    }

    public void neighborChanged(World world, BlockPos pos, Block neighborBlock) {
        block.onNeighborBlockChange(world, pos.x, pos.y, pos.z, neighborBlock);
    }
}
