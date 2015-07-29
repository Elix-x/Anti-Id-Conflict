package code.elix_x.excore.utils.pos;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPos {
	
	public int x;
	public int y;
	public int z;
	
	public BlockPos(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public BlockPos(TileEntity te) {
		this(te.xCoord, te.yCoord, te.zCoord);
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public BlockPos offsetX(int xx){
		x += xx;
		return this;
	}
	
	public BlockPos offsetY(int yy){
		y += yy;
		return this;
	}
	
	public BlockPos offsetZ(int zz){
		z += zz;
		return this;
	}
	
	public BlockPos offsetXNew(int xx){
		return new BlockPos(x + xx, y, z);
	}
	
	public BlockPos offsetYNew(int yy){
		return new BlockPos(x, y + yy, z);
	}
	
	public BlockPos offsetZNew(int zz){
		return new BlockPos(x, y, z + zz);
	}
	
	public Block getBlock(World world){
		return world.getBlock(x, y, z);
	}
	
	public int getMetadata(World world){
		return world.getBlockMetadata(x, y, z);
	}
	
	public TileEntity getTileEntity(World world){
		return world.getTileEntity(x, y, z);
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt){
		nbt.setInteger("x", x);
		nbt.setInteger("y", y);
		nbt.setInteger("z", z);
		return nbt;
	}
	
	public void readFromNBT(NBTTagCompound nbt){
		x = nbt.getInteger("x");
		y = nbt.getInteger("y");
		z = nbt.getInteger("z");
	}
	
	public static BlockPos createFromNBT(NBTTagCompound nbt){
		return new BlockPos(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
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
		BlockPos other = (BlockPos) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BlockPos [" + x + ", " + y + ", " + z + "]";
	}
	
}
