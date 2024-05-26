package com.myname.mymodid;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "mymodid", acceptedMinecraftVersions = "[1.7.10]")
public class MyMod {
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        Config.sync(e.getSuggestedConfigurationFile());
    }
}
