package alternate.current;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.tclproject.mysteriumlib.asm.common.CustomLoadingPlugin;
import net.tclproject.mysteriumlib.asm.common.FirstClassTransformer;
import org.apache.logging.log4j.Logger;

@Mod(modid = "alternatecurrent", acceptedMinecraftVersions = "[1.7.10]", dependencies = "required-after:MysteriumLib")
public class AlternateCurrentMod extends CustomLoadingPlugin {
    public static Logger LOGGER;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        LOGGER = e.getModLog();
    }

    public String[] getASMTransformerClass() {
        return new String[]{FirstClassTransformer.class.getName()};
    }

    public void registerFixes() {
        registerClassWithFixes("alternate.current.fix.FixesDimensionManager");
        registerClassWithFixes("alternate.current.fix.FixesBlockRedstoneWire");
    }
}
