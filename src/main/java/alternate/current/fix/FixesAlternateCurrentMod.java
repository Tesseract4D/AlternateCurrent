package alternate.current.fix;

import alternate.current.util.BlockPos;
import alternate.current.util.BlockState;
import alternate.current.wire.WireHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.tclproject.mysteriumlib.asm.annotations.EnumReturnSetting;
import net.tclproject.mysteriumlib.asm.annotations.Fix;

import java.util.Hashtable;

public class FixesAlternateCurrentMod {
    public static Hashtable<WorldServer, WireHandler> wireHandlers = new Hashtable<>();

    @Fix
    public static void setWorld(DimensionManager c, int id, WorldServer world) {
        if (world != null) {
            wireHandlers.put(world, new WireHandler(world));
        } else {
            wireHandlers.remove(DimensionManager.getWorld(id));
        }
    }

    @Fix(returnSetting = EnumReturnSetting.ALWAYS)
    public static void func_150177_e(BlockRedstoneWire c, World world, int x, int y, int z) {
    }

    @Fix
    public static void onNeighborBlockChange(BlockRedstoneWire c, World worldIn, int x, int y, int z, Block neighbor) {
        if (!worldIn.isRemote)
            wireHandlers.get(worldIn).onWireUpdated(new BlockPos(x, y, z));
    }

    @Fix
    public static void onBlockAdded(BlockRedstoneWire c, World worldIn, int x, int y, int z) {
        if (!worldIn.isRemote)
            wireHandlers.get(worldIn).onWireAdded(new BlockPos(x, y, z));
    }

    @Fix
    public static void breakBlock(BlockRedstoneWire c, World worldIn, int x, int y, int z, Block blockBroken, int meta) {
        if (!worldIn.isRemote)
            wireHandlers.get(worldIn).onWireRemoved(new BlockPos(x, y, z), new BlockState(blockBroken, meta));
    }
}
