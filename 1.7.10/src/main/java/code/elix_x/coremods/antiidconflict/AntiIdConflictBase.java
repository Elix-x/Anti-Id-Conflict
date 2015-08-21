package code.elix_x.coremods.antiidconflict;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import code.elix_x.coremods.antiidconflict.managers.BiomesManager;
import code.elix_x.coremods.antiidconflict.managers.DimensionsManager;
import code.elix_x.coremods.antiidconflict.managers.EnchantementsManager;
import code.elix_x.coremods.antiidconflict.managers.EntitiesManager;
import code.elix_x.coremods.antiidconflict.managers.PotionsManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.MetadataCollection;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModContainer.Disableable;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = "antiidconflict", name = "Anti Id Conflict", version = "1.3")
public class AntiIdConflictBase {

	public static File mainFolder;
	public static File biomesFolder;
	public static File entitiesFolder;
	public static File enchantementsFolder;
	public static File potionsFolder;
	public static File dimensionsFolder;

	@EventHandler
	public static void preinit(FMLPreInitializationEvent event) throws Exception
	{ 
		mainFolder = new File(event.getModConfigurationDirectory().getPath() + "\\AntiIDConflict");
		mainFolder.mkdir();

		BiomesManager.preinit(event);
		EntitiesManager.preinit(event);
		EnchantementsManager.preinit(event);
		PotionsManager.preinit(event);
		DimensionsManager.preinit(event);
	}

	@EventHandler
	public static void init(FMLInitializationEvent event)
	{ 
		BiomesManager.init(event);
		EntitiesManager.init(event);
		EnchantementsManager.init(event);
		PotionsManager.init(event);
		DimensionsManager.init(event);
	}

	@EventHandler
	public static void postinit(FMLPostInitializationEvent event) throws Exception
	{ 
		BiomesManager.postinit(event);
		EntitiesManager.postinit(event);
		EnchantementsManager.postinit(event);
		PotionsManager.postinit(event);
		DimensionsManager.postinit(event);
	}

}
