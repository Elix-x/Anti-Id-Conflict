package code.elix_x.coremods.antiidconflict;

public class ByteCodeTester {

	private int[] blockBiomeArray;
	
	public void init(){
		this.blockBiomeArray = new int[256];
	}
	
	/*
	 * BYTE[]:
	 * ALOAD 0
	 * SIPUSH 256
	 * NEWARRAY T_BYTE
	 * PUTFIELD code/elix_x/coremods/antiidconflict/ByteCodeTester.blockBiomeArray : [B
	 *  
	 * INT[]:
	 * ALOAD 0
	 * SIPUSH 256
	 * NEWARRAY T_INT
	 * PUTFIELD code/elix_x/coremods/antiidconflict/ByteCodeTester.blockBiomeArray : [I
	 */
}
