package alternate.current.wire;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class WorldHelper {

	private static final int Y_MIN = 0;
	private static final int Y_MAX = 256;

	/**
	 * An optimized version of {@link net.minecraft.world.World#setBlockState
	 * World.setBlockState}. Since this method is only used to update redstone wire
	 * block states, lighting checks, height map updates, and block entity updates
	 * are omitted.
	 */
	static boolean setWireState(WorldServer world, BlockPos pos, IBlockState state) {
		int y = pos.getY();

		if (y < Y_MIN || y >= Y_MAX) {
			return false;
		}

		int x = pos.getX();
		int z = pos.getZ();

		Chunk chunk = world.getChunk(x >> 4, z >> 4);
		ExtendedBlockStorage section = chunk.getBlockStorageArray()[y >> 4];

		if (section == null) {
			return false; // we should never get here
		}

		x &= 15;
		y &= 15;
		z &= 15;

		IBlockState prevState = section.get(x, y, z);

		if (state == prevState) {
			return false;
		}

		section.set(x, y, z, state);

		// notify clients of the BlockState change
		world.getPlayerChunkMap().markBlockForUpdate(pos);
		// mark the chunk for saving
		chunk.setModified(true);

		return true;
	}
}
