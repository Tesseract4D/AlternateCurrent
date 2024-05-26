package alternate.current.fix;

import alternate.current.wire.WireHandler;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.tclproject.mysteriumlib.asm.annotations.Fix;

import java.util.Hashtable;

public class FixesDimensionManager {
    public static Hashtable wireHandlers = new Hashtable<WorldServer, WireHandler>();

    @Fix
    public static void setWorld(DimensionManager c, int id, WorldServer world) {
        if (world != null) {
            wireHandlers.put(world, new WireHandler(world));
        } else {
            wireHandlers.remove(DimensionManager.getWorld(id));
        }
    }
}
