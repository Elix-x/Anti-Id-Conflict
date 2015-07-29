package code.elix_x.coremods.antiidconflict.core;

import code.elix_x.coremods.antiidconflict.managers.BiomesManager;
import code.elix_x.coremods.antiidconflict.managers.PotionsManager;

public class AsmHooks {

	public static int getLimitation() {
		return BiomesManager.getLimitation();
	}

	public static int getBiomeID(int id, boolean register) {
		return BiomesManager.getBiomeID(id, register);
	}
	
	public static int getPotionID(int id){
		return PotionsManager.getPotionID(id);
	}
	
}
