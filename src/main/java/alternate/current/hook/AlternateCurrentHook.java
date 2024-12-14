package alternate.current.hook;

import alternate.current.util.BlockPos;
import alternate.current.util.BlockState;
import alternate.current.wire.WireHandler;
import cn.tesseract.mycelium.asm.Hook;
import cn.tesseract.mycelium.asm.ReturnCondition;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

import java.util.HashMap;

public class AlternateCurrentHook {
    public static HashMap<WorldServer, WireHandler> wireHandlers = new HashMap<>();

    @Hook
    public static void setWorld(DimensionManager c, int id, WorldServer world) {
        if (world != null) {
            wireHandlers.put(world, new WireHandler(world));
        } else {
            wireHandlers.remove(DimensionManager.getWorld(id));
        }
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void func_150177_e(BlockRedstoneWire c, World world, int x, int y, int z) {
        System.out.println(7777);
    }

    @Hook(injectOnInvoke = "Lnet/minecraft/block/BlockRedstoneWire;func_150177_e(Lnet/minecraft/world/World;III)V", redirect = true)
    public static void onNeighborBlockChange(BlockRedstoneWire c, World worldIn, int x, int y, int z, Block neighbor) {
        wireHandlers.get(worldIn).onWireUpdated(new BlockPos(x, y, z));
    }

    @Hook(injectOnInvoke = "Lnet/minecraft/block/BlockRedstoneWire;func_150177_e(Lnet/minecraft/world/World;III)V", redirect = true)
    public static void onBlockAdded(BlockRedstoneWire c, World worldIn, int x, int y, int z) {
        wireHandlers.get(worldIn).onWireAdded(new BlockPos(x, y, z));
    }

    @Hook(injectOnInvoke = "Lnet/minecraft/block/BlockRedstoneWire;func_150177_e(Lnet/minecraft/world/World;III)V", redirect = true)
    public static void breakBlock(BlockRedstoneWire c, World worldIn, int x, int y, int z, Block blockBroken, int meta) {
        wireHandlers.get(worldIn).onWireRemoved(new BlockPos(x, y, z), new BlockState(blockBroken, meta));
    }
}
