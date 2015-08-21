package code.elix_x.coremods.antiidconflict.managers;

import java.io.File;
import java.io.PrintWriter;

import code.elix_x.coremods.antiidconflict.AntiIdConflictBase;
import net.minecraft.entity.EntityList;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class EntitiesManager {

	public static int idToCheckUntil = 2048;
	
	public static String avaibleIDs = "";
	public static String occupiedIDs = "";
	public static String conflictedIDs = "";
	
	public static void preinit(FMLPreInitializationEvent event) throws Exception
	{ 
		AntiIdConflictBase.entitiesFolder = new File(AntiIdConflictBase.mainFolder, "\\entities");
		AntiIdConflictBase.entitiesFolder.mkdir();
		
		setUpEntitiesFolder();
	}

	public static void init(FMLInitializationEvent event)
	{ 

	}

	public static void postinit(FMLPostInitializationEvent event) throws Exception
	{ 
		updateEntitiesFolder();
	}
	
	public static void setUpEntitiesFolder() throws Exception {
		File conf = new File(AntiIdConflictBase.entitiesFolder, "\\main.cfg");
		conf.createNewFile();
		Configuration config = new Configuration(conf);
		config.load();
		idToCheckUntil = config.getInt("idToChekUntil", "Scanning settings", 256, 256, Integer.MAX_VALUE, "The value until which, scanner will check ids, to be free/occupied...");
		config.save();
	}

	public static void updateEntitiesFolder() throws Exception {
		{
			for(int i = 1; i < idToCheckUntil + 1; i++){
				Class clas = EntityList.getClassFromID(i);
				if(clas != null){
					occupiedIDs += i + " : " + EntityList.getStringFromID(i) + " (" + clas.getName() + ")\n";
				} else {
					avaibleIDs += i + "\n";
				}
			}
		}
		{
			File freeIds = new File(AntiIdConflictBase.entitiesFolder, "\\avaibleIDs.txt");
			if(freeIds.exists()){
				freeIds.delete();
			}
			freeIds.createNewFile();
			PrintWriter writer = new PrintWriter(freeIds);
			writer.println("List of avaible entities ids:");
			for(String s : avaibleIDs.split("\n")){
				writer.println(s);
			}
			writer.close();
		}
		{
			File occupiedIds = new File(AntiIdConflictBase.entitiesFolder, "\\occupiedIDs.txt");
			if(occupiedIds.exists()){
				occupiedIds.delete();
			}
			occupiedIds.createNewFile();
			PrintWriter writer = new PrintWriter(occupiedIds);
			writer.println("Table of occupied entities ids and their owners\nid:name(class)");
			for(String s : occupiedIDs.split("\n")){
				writer.println(s);
			}
			writer.close();
		}
		{
			File all = new File(AntiIdConflictBase.entitiesFolder, "\\AllIDs.txt");
			PrintWriter writer = new PrintWriter(all);

			for(int i = 0; i < idToCheckUntil + 1; i++){
				Class clas = EntityList.getClassFromID(i);
				if(clas != null){
					writer.println(i + " is Occupied by " + EntityList.getStringFromID(i) + "(" + clas.getName() + ")");
				} else {
					writer.println(i + " is Avaible");
				}
			}

			writer.close();
		}
	}
	
	
}
