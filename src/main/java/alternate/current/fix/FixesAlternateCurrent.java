package alternate.current.fix;

import alternate.current.wire.WireHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.tclproject.mysteriumlib.asm.annotations.EnumReturnSetting;
import net.tclproject.mysteriumlib.asm.annotations.Fix;

import java.util.Hashtable;

public class FixesAlternateCurrent {
    public static Hashtable<WorldServer, WireHandler> wireHandlers = new Hashtable<>();

    @Fix
    public static void setWorld(DimensionManager c, int id, WorldServer world, MinecraftServer server) {
        if (world != null) {
            wireHandlers.put(world, new WireHandler(world));
        } else {
            wireHandlers.remove(DimensionManager.getWorld(id));
        }
    }

    @Fix(returnSetting = EnumReturnSetting.ALWAYS)
    public static IBlockState updateSurroundingRedstone(BlockRedstoneWire c, World worldIn, BlockPos pos, IBlockState state) {
        return state;
    }

    @Fix(insertOnInvoke = "net/minecraft/block/BlockRedstoneWire;updateSurroundingRedstone(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/block/state/IBlockState;")
    public static void neighborChanged(BlockRedstoneWire c, IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        wireHandlers.get(worldIn).onWireUpdated(pos);
    }

    @Fix(insertOnInvoke = "net/minecraft/block/BlockRedstoneWire;updateSurroundingRedstone(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/block/state/IBlockState;")
    public static void onBlockAdded(BlockRedstoneWire c, World worldIn, BlockPos pos, IBlockState state) {
        wireHandlers.get(worldIn).onWireAdded(pos);
    }

    @Fix(insertOnInvoke = "net/minecraft/block/BlockRedstoneWire;updateSurroundingRedstone(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/block/state/IBlockState;")
    public static void breakBlock(BlockRedstoneWire c, World worldIn, BlockPos pos, IBlockState state) {
        wireHandlers.get(worldIn).onWireRemoved(pos, state);
    }
}
