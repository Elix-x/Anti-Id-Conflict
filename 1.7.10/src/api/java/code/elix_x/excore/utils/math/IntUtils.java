package code.elix_x.excore.utils.math;

public class IntUtils {

	public static String translateIntToRoman(int i) {
		if (i < 1 || i > 3999){
	        return "Invalid Roman Number Value";
		}
	    String s = "";
	    while (i >= 1000) {
	        s += "M";
	        i -= 1000;        }
	    while (i >= 900) {
	        s += "CM";
	        i -= 900;
	    }
	    while (i >= 500) {
	        s += "D";
	        i -= 500;
	    }
	    while (i >= 400) {
	        s += "CD";
	        i -= 400;
	    }
	    while (i >= 100) {
	        s += "C";
	        i -= 100;
	    }
	    while (i >= 90) {
	        s += "XC";
	        i -= 90;
	    }
	    while (i >= 50) {
	        s += "L";
	        i -= 50;
	    }
	    while (i >= 40) {
	        s += "XL";
	        i -= 40;
	    }
	    while (i >= 10) {
	        s += "X";
	        i -= 10;
	    }
	    while (i >= 9) {
	        s += "IX";
	        i -= 9;
	    }
	    while (i >= 5) {
	        s += "V";
	        i -= 5;
	    }
	    while (i >= 4) {
	        s += "IV";
	        i -= 4;
	    }
	    while (i >= 1) {
	        s += "I";
	        i -= 1;
	    }    
	    return s;
	}

}
