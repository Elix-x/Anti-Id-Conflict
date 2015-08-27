package code.elix_x.coremods.antiidconflict;

import java.io.File;

import code.elix_x.coremods.antiidconflict.managers.BiomesManager;
import code.elix_x.coremods.antiidconflict.managers.DimensionsManager;
import code.elix_x.coremods.antiidconflict.managers.EnchantementsManager;
import code.elix_x.coremods.antiidconflict.managers.EntitiesManager;
import code.elix_x.coremods.antiidconflict.managers.PotionsManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "antiidconflict", name = "Anti Id Conflict", version = "1.3.4")
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
