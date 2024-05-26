package alternate.current.wire;

import alternate.current.util.BlockPos;
import alternate.current.util.BlockState;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class WorldHelper {

	private static final int Y_MIN = 0;
	private static final int Y_MAX = 256;

	static BlockState getBlockState(WorldServer world, BlockPos pos) {
		int y = pos.y;

		if (y < Y_MIN || y >= Y_MAX) {
			return BlockState.AIR;
		}

		int x = pos.x;
		int z = pos.z;

		Chunk chunk = world.getChunkFromChunkCoords(x >> 4, z >> 4);
		ExtendedBlockStorage section = chunk.getBlockStorageArray()[y >> 4];

		if (section == null) {
			return BlockState.AIR; // we should never get here
		}

		x &= 15;
		y &= 15;
		z &= 15;

		Block block = section.getBlockByExtId(x, y, z);

		if (block == Blocks.air) {
			return BlockState.AIR;
		}

		int metadata = section.getExtBlockMetadata(x, y, z);

		return new BlockState(block, metadata);
	}

	/**
	 * An optimized version of {@link net.minecraft.world.World#setBlockState
	 * World.setBlockState}. Since this method is only used to update redstone wire
	 * block states, lighting checks, height map updates, and block entity updates
	 * are omitted.
	 */
	static boolean setWireState(WorldServer world, BlockPos pos, BlockState state) {
		int y = pos.y;

		if (y < Y_MIN || y >= Y_MAX) {
			return false;
		}

		int x = pos.x;
		int z = pos.z;

		Chunk chunk = world.getChunkFromChunkCoords(x >> 4, z >> 4);
		ExtendedBlockStorage section = chunk.getBlockStorageArray()[y >> 4];

		if (section == null) {
			return false; // we should never get here
		}

		x &= 15;
		y &= 15;
		z &= 15;

		Block block = state.getBlock();
		Block prevBlock = section.getBlockByExtId(x, y, z);

		if (block != prevBlock) {
			return false;
		}

		int metadata = state.get();
		int prevMetadata = section.getExtBlockMetadata(x, y, z);

		if (metadata == prevMetadata) {
			return false;
		}

		section.setExtBlockMetadata(x, y, z, metadata);

		// notify clients of the BlockState change
		world.getPlayerManager().markBlockForUpdate(pos.x, pos.y, pos.z);
		// mark the chunk for saving
		chunk.setChunkModified();

		return true;
	}
}
