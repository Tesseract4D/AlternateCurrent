package alternate.current;

import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.tclproject.mysteriumlib.asm.common.CustomLoadingPlugin;
import net.tclproject.mysteriumlib.asm.common.FirstClassTransformer;
import net.tclproject.mysteriumlib.asm.core.MiscUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = AlternateCurrentMod.MOD_ID, name = AlternateCurrentMod.MOD_NAME, version = AlternateCurrentMod.MOD_VERSION)
public class AlternateCurrentMod extends CustomLoadingPlugin {

    public static final String MOD_ID = "alternatecurrent";
    public static final String MOD_NAME = "Alternate Current";
    public static final String MOD_VERSION = "1.4.2";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    public String[] getASMTransformerClass() {
        return new String[]{FirstClassTransformer.class.getName()};
    }

    public void registerFixes() {
        registerClassWithFixes("alternate.current.fix.FixesAlternateCurrent");
    }
}
