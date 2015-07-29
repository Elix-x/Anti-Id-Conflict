package code.elix_x.excore.energy;

public class EnergyLoaded {
	
	public static boolean isRfLoaded() {
		try{
			Class.forName("cofh.api.energy.IEnergyStorage");
			return true;
		} catch(ClassNotFoundException e){
			return false;
		}
	}

	public static boolean isIC2Loaded() {
		try{
			Class.forName("ic2.api.energy.IEnergyNet");
			return true;
		} catch(ClassNotFoundException e){
			return false;
		}
	}

}
