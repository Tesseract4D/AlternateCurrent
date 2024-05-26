package com.myname.mymodid;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {
    public static String greeting;

    public static void sync(File f) {
        Configuration configuration = new Configuration(f);
        greeting = configuration.getString("greeting", Configuration.CATEGORY_GENERAL, "Hello World", "How shall I greet?");
        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
