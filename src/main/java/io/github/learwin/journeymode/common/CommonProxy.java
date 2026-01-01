package io.github.learwin.journeymode.common;

import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import io.github.learwin.journeymode.Config;
import io.github.learwin.journeymode.common.event.ItemPickupHandler;
import io.github.learwin.journeymode.common.event.PlayerLoginHandler;
import io.github.learwin.journeymode.common.event.PlayerTickHandler;
import io.github.learwin.journeymode.common.network.PacketHandler;

public class CommonProxy {

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        Config.synchronizeConfiguration(event.getSuggestedConfigurationFile());
        PacketHandler.init();
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ItemPickupHandler());
        FMLCommonHandler.instance()
            .bus()
            .register(new PlayerLoginHandler());
        FMLCommonHandler.instance()
            .bus()
            .register(new PlayerTickHandler());
    }

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {}

    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {}
}
