package code.elix_x.excore.utils.pos;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class DimBlockPos extends BlockPos{

	public int dimId;

	public DimBlockPos(int x, int y, int z, int dimId) {
		super(x, y, z);
		this.dimId = dimId;
	}
	
	public DimBlockPos(TileEntity te) {
		this(te.xCoord, te.yCoord, te.zCoord, te.getWorldObj().provider.dimensionId);
	}

	public int getDimId() {
		return dimId;
	}

	public void setDimId(int dimId) {
		this.dimId = dimId;
	}
	
	public Block getBlock(){
		return MinecraftServer.getServer().worldServerForDimension(dimId).getBlock(x, y, z);
	}
	
	public int getMetadata(){
		return MinecraftServer.getServer().worldServerForDimension(dimId).getBlockMetadata(x, y, z);
	}
	
	public TileEntity getTileEntity(){
		return MinecraftServer.getServer().worldServerForDimension(dimId).getTileEntity(x, y, z);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt = super.writeToNBT(nbt);
		nbt.setInteger("dimId", dimId);
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		dimId = nbt.getInteger("dimId");
	}
	
	public static DimBlockPos createFromNBT(NBTTagCompound nbt){
		return new DimBlockPos(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"), nbt.getInteger("dimId"));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + dimId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DimBlockPos other = (DimBlockPos) obj;
		if (dimId != other.dimId)
			return false;
		return true;
	}
	
	
	
}
