package code.elix_x.coremods.antiidconflict.managers;

import java.io.File;
import java.io.PrintWriter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.crash.CrashReport;
import net.minecraft.potion.Potion;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.config.Configuration;

import org.apache.commons.lang3.ArrayUtils;

import code.elix_x.coremods.antiidconflict.AntiIdConflictBase;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class PotionsManager {

	public static String freePotionIDs = "";
	public static String occupiedPotionIDs = "";
	public static String conflictedIds = "";

	public static boolean crashIfConflict;
	public static boolean debug;

	public static boolean translate;

	public static int freeIds = 0;
	public static int occupiedIds = 0;
	public static int IconflictedIds = 0;

	public static ConflictingPotions[] conflicts = new ConflictingPotions[/*Potion.potionTypes.length*/ 1028];

	public static void preinit(FMLPreInitializationEvent event) throws Exception
	{ 
		AntiIdConflictBase.potionsFolder = new File(AntiIdConflictBase.mainFolder, "\\potions");
		AntiIdConflictBase.potionsFolder.mkdir();

		setUpPotionFolder();
	}

	public static void init(FMLInitializationEvent event)
	{ 

	}

	public static void postinit(FMLPostInitializationEvent event) throws Exception
	{ 
		updateConflictedArrays();
		updatePotionFolder();
		crash();
	}

	public static void setUpPotionFolder() throws Exception{
		{
			File conf = new File(AntiIdConflictBase.potionsFolder,"\\main.cfg");	
			conf.createNewFile();
			Configuration config = new Configuration(conf);
			config.load();
			crashIfConflict = config.getBoolean("crashIfConflict", "Settings of reaction to repeated ids", true, "If mod detects potion trying to override old one, force Minecraft to crash.\nRecommendation: do not tuch, may lead to worlds problems");
			debug = config.getBoolean("debug", "Settings of console output", false, "Enable debugging messages in console");
			config.save();
		}	
	}

	public static void updatePotionFolder() throws Exception{
		{
			for(int i = 0; i < Potion.potionTypes.length; i++){
				if(Potion.potionTypes[i] == null){
					freePotionIDs += i + "\n";
					freeIds++;
				}
			}
		}

		{
			for(Potion potion : Potion.potionTypes){
				if(potion != null){
					occupiedPotionIDs += potion.id + " : " + potion.getName() + " (" + potion.getClass().getName() + ")\n";
					occupiedIds++;
				}
			}
		}

		{
			for(ConflictingPotions conflict : conflicts){
				if(conflict != null){
					if(debug){
						System.out.println("Found potions id conflict for " + conflict.ID + " : " + conflict.getCrashMessage());
					}
					conflictedIds += conflict.getCrashMessage() + "\n";
					IconflictedIds++;
				}
			}
		}

		{
			File freeIds = new File(AntiIdConflictBase.potionsFolder, "\\avaibleIDs.txt");
			if(freeIds.exists()){
				freeIds.delete();
			}
			freeIds.createNewFile();
			PrintWriter writer = new PrintWriter(freeIds);
			writer.println("Total amount of free potion ids: " + freeIds);
			writer.println("List of free potion ids:");
			for(String s : freePotionIDs.split("\n")){
				writer.println(s);
			}
			writer.close();
		}
		{
			int i = occupiedIds;

			File occupiedIds = new File(AntiIdConflictBase.potionsFolder, "\\occupiedIDs.txt");
			if(occupiedIds.exists()){
				occupiedIds.delete();
			}
			occupiedIds.createNewFile();
			PrintWriter writer = new PrintWriter(occupiedIds);
			writer.println("Total amount of occupied potion ids: " + i);
			writer.println("Table of occupied potion ids and their owners");
			writer.println("id:name(class)");
			for(String s : occupiedPotionIDs.split("\n")){
				writer.println(s);
			}
			writer.close();
		}
		{
			File file = new File(AntiIdConflictBase.potionsFolder, "\\conflictedIDs.txt");
			if(file.exists()){
				file.delete();
			}
			file.createNewFile();
			PrintWriter writer = new PrintWriter(file);
			writer.println("Total amount of conflicted potion ids: " + IconflictedIds);
			writer.println("IDs in conflict:\n");
			for(String s : conflictedIds.split("\n")){
				writer.println(s);
			}
			writer.close();
		}
		{
			int j = occupiedIds;

			File all = new File(AntiIdConflictBase.potionsFolder, "\\AllIDs.txt");
			PrintWriter writer = new PrintWriter(all);
			writer.println("Total amount of free potion ids: " + freeIds);
			writer.println("Total amount of occupied potion ids: " + j);
			writer.println("Total amount of conflicted potion ids: " + IconflictedIds);
			writer.println("All ids and their position:");
			try{
				for(int i = 0; i < Potion.potionTypes.length; i++){
					try{
						if(!ArrayUtils.isEmpty(conflicts) && conflicts[i] != null){
							writer.println(conflicts[i].getCrashMessage());
						} else if(Potion.potionTypes[i] != null){
							writer.println(i + " is Occupied by " + Potion.potionTypes[i].getName() + " (" + Potion.potionTypes.getClass().getName() + ")");
						} else {
							writer.println(i + " is Avaible");
						}
					} catch(Exception e){
						if(debug){
							e.printStackTrace();
						}
					}
				}
			} catch(Exception e){
				if(debug){
					e.printStackTrace();
				}
			}

			writer.close();
		}
	}

	public static int getPotionID(int id) {
		if(Potion.potionTypes[id] != null){
			if(debug){
				System.out.println("Detected potion conflict for id " + id + (crashIfConflict ? " Game will crash!" : ""));
			}
			Potion potion = Potion.potionTypes[id];
			ConflictingPotions conflict = conflicts[id];
			if(conflict == null){
				conflict = new ConflictingPotions(id, potion);
			}
			conflicts[id] = conflict;
		}
		updateConflictedArrays();
		return id;
	}

	public static void updateConflictedArrays() {
		for(ConflictingPotions conflict : conflicts){
			if(conflict != null){
				conflict.updateArray();
			}
		}
	}

	private static void crash() throws PotionsIDConflictException {
		if(ArrayUtils.isEmpty(conflicts)){
			if(debug){
				System.out.println("No conflicts found");
			}
			return;
		} else if(crashIfConflict){
			for(ConflictingPotions conflict : conflicts){
				if(conflict != null){
					String report = "Conflict between potions with same id caused game to crash.\nAffected ids:\n" + conflictedIds;
					if(debug){
						System.out.println(report);
					}
					CrashReport crash = new CrashReport("Conflicting potions forced game to crash: ", new PotionsIDConflictException(report));
					Minecraft.getMinecraft().crashed(crash);
				}
			}
		}
	}

	public static class ConflictingPotions{

		public Potion[] potions = new Potion[Potion.potionTypes.length];

		public int ID;

		public ConflictingPotions(int id, Potion potion){
			ID = id;
			potions[0] = potion;
		}

		public void updateArray(){
			Potion potion = Potion.potionTypes[ID];
			if(potion != null){
				boolean p = ArrayUtils.contains(potions, potion);
				for(Potion b : potions){
					if(b != null){
						p |= (b.equals(potion) || (b.getName().equals(potion.getName()) && b.getClass().getName().equals(potion.getClass().getName())));
					}
				}
				if(!p){
					potions = ArrayUtils.add(potions, Potion.potionTypes[ID]);
				}
			}
		}

		public String getCrashMessage(){
			String s = "";
			s += ID + " is asked by: ";

			boolean flag = false;
			for(Potion potion : potions){
				if(potion != null){
					if(flag){
						s += ", ";
					} else {
						flag = true;
					}
					s += (translate ? I18n.format(potion.getName()) : potion.getName());
					s += " (";
					s += potion.getClass().getName();
					s += ")";
				}
			}

			return s;
		}
	}

	public static class PotionsIDConflictException extends Exception{

		public PotionsIDConflictException(String s) {
			super(s);
		}

	}
}
