package alternate.current.fix;

import alternate.current.util.BlockPos;
import alternate.current.util.BlockState;
import alternate.current.wire.WireHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.world.World;
import net.tclproject.mysteriumlib.asm.annotations.EnumReturnSetting;
import net.tclproject.mysteriumlib.asm.annotations.Fix;

public class FixesBlockRedstoneWire {
    @Fix(returnSetting = EnumReturnSetting.ALWAYS)
    public static void func_150177_e(BlockRedstoneWire c, World world, int x, int y, int z) {
    }

    @Fix
    public static void onNeighborBlockChange(BlockRedstoneWire c, World worldIn, int x, int y, int z, Block neighbor) {
        if (!worldIn.isRemote)
            ((WireHandler) FixesDimensionManager.wireHandlers.get(worldIn)).onWireUpdated(new BlockPos(x, y, z));
    }

    @Fix
    public static void onBlockAdded(BlockRedstoneWire c, World worldIn, int x, int y, int z) {
        if (!worldIn.isRemote)
            ((WireHandler) FixesDimensionManager.wireHandlers.get(worldIn)).onWireAdded(new BlockPos(x, y, z));
    }

    @Fix
    public static void breakBlock(BlockRedstoneWire c, World worldIn, int x, int y, int z, Block blockBroken, int meta) {
        if (!worldIn.isRemote)
            ((WireHandler) FixesDimensionManager.wireHandlers.get(worldIn)).onWireRemoved(new BlockPos(x, y, z), new BlockState(blockBroken, meta));
    }
}
