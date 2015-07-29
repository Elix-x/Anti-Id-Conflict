package code.elix_x.excore.utils.color;

import net.minecraft.nbt.NBTTagCompound;

import org.lwjgl.util.Color;

public class RGBA {

	public int r;
	public int g;
	public int b;
	public int a;

	private RGBA() {

	}

	public RGBA(int r, int g, int b, int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("r", r);
		nbt.setInteger("g", g);
		nbt.setInteger("b", b);
		nbt.setInteger("a", a);
		return nbt;
	}

	public void readFromNBT(NBTTagCompound nbt) {
		r = nbt.getInteger("r");
		g = nbt.getInteger("g");
		b = nbt.getInteger("b");
		a = nbt.getInteger("a");
	}

	public static RGBA createFromNBT(NBTTagCompound nbt){
		RGBA rgba = new RGBA();
		rgba.readFromNBT(nbt);
		return rgba;
	}

	public Color getColor(){
		return new Color(r, g, b, a);
	}

	public int getHex(){
		//return toHex(getColor());
		String as = pad(Integer.toHexString(a));
		String rs = pad(Integer.toHexString(r));
		String gs = pad(Integer.toHexString(g));
		String bs = pad(Integer.toHexString(b));
		String hex = /*"0x" + */as + rs + gs + bs;
		return Integer.parseInt(hex, 16);
	}

	/*public static int toHex(Color col) {
	    String as = pad(Integer.toHexString(col.getAlpha()));
	    String rs = pad(Integer.toHexString(col.getRed()));
	    String gs = pad(Integer.toHexString(col.getGreen()));
	    String bs = pad(Integer.toHexString(col.getBlue()));
	    String hex = as + rs + gs + bs;
	    return Integer.parseInt(hex, 16);
	}*/

	private static final String pad(String s) {
		return (s.length() == 1) ? "0" + s : s;
	}

	@Override
	public String toString() {
		return "RGBA{ " + r + ", " + g + ", " + b + ", " + a + "}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + b;
		result = prime * result + g;
		result = prime * result + a;
		result = prime * result + r;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RGBA other = (RGBA) obj;
		if (b != other.b)
			return false;
		if (g != other.g)
			return false;
		if (a != other.a)
			return false;
		if (r != other.r)
			return false;
		return true;
	}

}
