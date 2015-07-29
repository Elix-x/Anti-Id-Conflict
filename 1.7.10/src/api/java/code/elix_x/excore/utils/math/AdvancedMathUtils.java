package code.elix_x.excore.utils.math;

public class AdvancedMathUtils {

	public static double average(double d, double... ds){
		for(double dd : ds){
			d += dd;
		}
		return d / ds.length + 1;
	}
	
}
