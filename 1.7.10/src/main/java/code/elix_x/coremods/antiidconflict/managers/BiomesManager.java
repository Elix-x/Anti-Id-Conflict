package code.elix_x.coremods.antiidconflict.managers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.config.Configuration;
import code.elix_x.coremods.antiidconflict.AntiIdConflictBase;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class BiomesManager {


	public static String freeBiomeIDs = "";
	public static String occupiedBiomeIDs = "";
	public static String conflictedIds = "";

	public static boolean crashIfConflict;
	public static boolean ignoreRegistry;
	public static boolean debug;

	public static int freeIds = 0;
	public static int occupiedIds = 0;
	public static int IconflictedIds = 0;

	public static ConflictingBiomes[] conflicts = new ConflictingBiomes[256];

	public static void preinit(FMLPreInitializationEvent event) throws Exception
	{ 
		AntiIdConflictBase.biomesFolder = new File(AntiIdConflictBase.mainFolder, "\\biomes");
		AntiIdConflictBase.biomesFolder.mkdir();

		setUpBiomesFolder();
	}

	public static void init(FMLInitializationEvent event)
	{ 

	}

	public static void postinit(FMLPostInitializationEvent event) throws Exception
	{ 
		updateConflictedArrays();
		updateBiomesFolder();
		crash();
	}

	public static void setUpBiomesFolder() throws Exception{
		{
			File conf = new File(AntiIdConflictBase.biomesFolder,"\\main.cfg");	
			conf.createNewFile();
			Configuration config = new Configuration(conf);
			config.load();
			crashIfConflict = config.getBoolean("crashIfConflict", "Settings of reaction to repeated ids", true, "If mod detects biome trying to ovverride old one, force Minecraft to crash.\nRecommendation: do not tuch, may lead to worlds problems");
			ignoreRegistry = config.getBoolean("ignoreRegistry", "Settings of reaction to repeated ids", true, "If mod detects biome trying to ovverride old one, force Minecraft to crash EVEN IF BIOME ISN'T REGISTERED.\nRecommendation: do not tuch, may lead to many many many problems");
			debug = config.getBoolean("debug", "Settings of console output", false, "Enable debugging messages in console");
			config.save();
		}	
	}

	public static void updateBiomesFolder() throws Exception{
		{
			for(int i = 0; i < BiomeGenBase.getBiomeGenArray().length; i++){
				if(BiomeGenBase.getBiomeGenArray()[i] == null){
					freeBiomeIDs += i + "\n";
					freeIds++;
				}
			}
		}
		System.out.println("Found tottally " + freeIds + " free biome ids");

		{
			for(BiomeGenBase biome : BiomeGenBase.getBiomeGenArray()){
				if(biome != null){
					occupiedBiomeIDs += biome.biomeID + " : " + biome.biomeName + " (" + biome.getBiomeClass() + ")\n";
					occupiedIds++;
				}
			}
		}
		System.out.println("Found tottally " + occupiedIds + " occupied biome ids");

		{
			for(ConflictingBiomes conflict : conflicts){
				if(conflict != null){
					if(debug){
						System.out.println("Found biomes id conflict for " + conflict.ID + " : " + conflict.getCrashMessage());
					}
					conflictedIds += conflict.getCrashMessage() + "\n";
					IconflictedIds++;
				}
			}
		}
		System.out.println("Found tottally " + IconflictedIds + " conflicted biome ids");

		{
			File freeIds = new File(AntiIdConflictBase.biomesFolder, "\\avaibleIDs.txt");
			if(freeIds.exists()){
				freeIds.delete();
			}
			freeIds.createNewFile();
			PrintWriter writer = new PrintWriter(freeIds);
			writer.println("Total amount of free biome ids: " + freeIds);
			writer.println("List of free biome ids:");
			for(String s : freeBiomeIDs.split("\n")){
				writer.println(s);
			}
			writer.close();
		}
		{
			File occupiedIds = new File(AntiIdConflictBase.biomesFolder, "\\occupiedIDs.txt");
			if(occupiedIds.exists()){
				occupiedIds.delete();
			}
			occupiedIds.createNewFile();
			PrintWriter writer = new PrintWriter(occupiedIds);
			writer.println("Total amount of occupied biome ids: " + BiomesManager.occupiedIds);
			writer.println("Table of occupied biome ids and their owners");
			writer.println("id:name(class)");
			for(String s : occupiedBiomeIDs.split("\n")){
				writer.println(s);
			}
			writer.close();
		}
		{
			File file = new File(AntiIdConflictBase.biomesFolder, "\\conflictedIDs.txt");
			if(file.exists()){
				file.delete();
			}
			file.createNewFile();
			PrintWriter writer = new PrintWriter(file);
			writer.println("Total amount of conflicted biome ids: " + IconflictedIds);
			writer.println("IDs in conflict:\n");
			for(String s : conflictedIds.split("\n")){
				writer.println(s);
			}
			writer.close();
		}
		{
			File all = new File(AntiIdConflictBase.biomesFolder, "\\AllIDs.txt");
			PrintWriter writer = new PrintWriter(all);
			writer.println("Total amount of free biome ids: " + freeIds);
			writer.println("Total amount of occupied biome ids: " + BiomesManager.occupiedIds);
			writer.println("Total amount of conflicted biome ids: " + IconflictedIds);
			writer.println("All ids and their position:");
			for(int i = 0; i < BiomeGenBase.getBiomeGenArray().length; i++){
				if(!ArrayUtils.isEmpty(conflicts) && conflicts[i] != null){
					writer.println(conflicts[i].getCrashMessage());
				} else if(BiomeGenBase.getBiomeGenArray()[i] != null){
					writer.println(i + " is Occupied by " + BiomeGenBase.getBiomeGenArray()[i].biomeName + " (" + BiomeGenBase.getBiomeGenArray()[i].getBiomeClass().getName() + ")");
				} else {
					writer.println(i + " is Avaible");
				}
			}

			writer.close();
		}
	}

	public static int getBiomeID(int id, boolean register) {
		if(!register && ignoreRegistry){
			return id;
		}
		if(BiomeGenBase.getBiomeGenArray()[id] != null){
			if(debug){
				System.out.println("Detected biomes conflict for id " + id + (crashIfConflict ? " Game will crash!" : ""));
			}
			BiomeGenBase biome = BiomeGenBase.getBiomeGenArray()[id];
			ConflictingBiomes conflict = conflicts[id];
			if(conflict == null){
				conflict = new ConflictingBiomes(id, biome);
			}
			conflicts[id] = conflict;
		}
		updateConflictedArrays();
		return id;
	}

	public static void updateConflictedArrays() {
		for(ConflictingBiomes conflict : conflicts){
			if(conflict != null){
				conflict.updateArray();
			}
		}
	}

	private static void crash() throws BiomesIDConflictException {
		if(ArrayUtils.isEmpty(conflicts)){
			if(debug){
				System.out.println("No conflicts found");
			}
			return;
		} else if(crashIfConflict){
			for(ConflictingBiomes conflict : conflicts){
				if(conflict != null){
					String report = "Conflict between biomes with same id caused game to crash.\nAffected ids:\n" + conflictedIds;
					if(debug){
						System.out.println(report);
					}
					CrashReport crash = new CrashReport("Conflicting biomes forced game to crash: ", new BiomesIDConflictException(report));
					Minecraft.getMinecraft().crashed(crash);
				}
			}
			if(debug){
				System.out.println("No conflicts found");
			}
		}
	}

	public static class ConflictingBiomes{

		public BiomeGenBase[] biomes = new BiomeGenBase[256];

		public int ID;

		public ConflictingBiomes(int id, BiomeGenBase biome){
			ID = id;
			biomes[0] = biome;
		}

		public void updateArray(){
			BiomeGenBase biome = BiomeGenBase.getBiomeGenArray()[ID];
			if(biome != null){
				boolean p = ArrayUtils.contains(biomes, biome);
				if(!p) {
					for(BiomeGenBase b : biomes){
						if(b != null){
							if(null != b.biomeName && null != biome.biomeName) {
								p |= (b.equals(biome) || (b.biomeName.equals(biome.biomeName) && b.getClass().getName().equals(biome.getClass().getName())));
							} else {
								String message = "Encountered biome without biomeName with biomeID ";
								if(null == b.biomeName) {	
									message = message.concat(Integer.toString(b.biomeID));
								}
								else {
									message = message.concat(Integer.toString(biome.biomeID));
								}
								System.out.println(message);
							}
						}
					}
				}
				if(!p){
					biomes = ArrayUtils.add(biomes, BiomeGenBase.getBiomeGenArray()[ID]);
				}
			}
		}

		public String getCrashMessage(){
			String s = "";
			s += ID + " is asked by: ";

			boolean flag = false;
			for(BiomeGenBase biome : biomes){
				if(biome != null){
					if(flag){
						s += ", ";
					} else {
						flag = true;
					}
					s += biome.biomeName;
					s += " (";
					s += biome.getBiomeClass().getName();
					s += ")";
				}
			}

			return s;
		}
	}

	public static class BiomesIDConflictException extends Exception{

		public BiomesIDConflictException(String s) {
			super(s);
		}

	}
}
