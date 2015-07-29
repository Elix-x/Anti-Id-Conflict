package code.elix_x.excore.utils.arrays;

import java.util.ArrayList;
import java.util.List;

public class SpecialArrayUtils {
	
	public static double min(double... doubles) {
		boolean init = false;
		double i = 0;
		for(double j : doubles){
			if(!init){
				init = true;
				i = j;
			} else {
				i = Math.min(i, j);
			}
		}
		return i;
	}
	
	public static double max(double... doubles) {
		boolean init = false;
		double i = 0;
		for(double j : doubles){
			if(!init){
				init = true;
				i = j;
			} else {
				i = Math.max(i, j);
			}
		}
		return i;
	}

	public static int min(int... ints) {
		boolean init = false;
		int i = 0;
		for(int j : ints){
			if(!init){
				init = true;
				i = j;
			} else {
				i = Math.min(i, j);
			}
		}
		return i;
	}
	
	public static int max(int... ints) {
		boolean init = false;
		int i = 0;
		for(int j : ints){
			if(!init){
				init = true;
				i = j;
			} else {
				i = Math.max(i, j);
			}
		}
		return i;
	}
}
