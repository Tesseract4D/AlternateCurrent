package alternate.current;

import cn.tesseract.mycelium.asm.MiscHelper;
import cn.tesseract.mycelium.asm.minecraft.HookLoader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.block.BlockRedstoneWire;
import org.apache.logging.log4j.Logger;

@Mod(modid = "alternatecurrent", acceptedMinecraftVersions = "[1.7.10]")
public class AlternateCurrentMod extends HookLoader {
    public static Logger LOGGER;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        LOGGER = e.getModLog();
        MiscHelper.printMethodDescriptors(BlockRedstoneWire.class);
    }

    @Override
    protected void registerHooks() {
        registerHookContainer("alternate.current.hook.AlternateCurrentHook");
    }
}
