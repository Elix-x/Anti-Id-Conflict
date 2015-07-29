package code.elix_x.excore.utils.vecs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import code.elix_x.excore.utils.pos.BlockPos;

public class PositionnedRotatedRangedVec3 {

	public static Logger logger = LogManager.getLogger("Pyramidal Vec3");

	private World worldObj;

	private double posX;
	private double posY;
	private double posZ;

	private float rotYaw;
	private float rotPitch;
	private float rotR;

	private double range;

	public PositionnedRotatedRangedVec3(World worldObj, double posX, double posY, double posZ, float rotationYaw, float rotationPitch, float rotR, double range) {
		setData(worldObj, posX, posY, posZ, rotationYaw, rotationPitch, rotR, range);
	}

	public void setData(World worldObj, double posX, double posY, double posZ, float rotationYaw, float rotationPitch, float rotR, double range){
		this.worldObj = worldObj;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.rotYaw = rotationYaw;
		this.rotPitch = rotationPitch;
		this.rotR = rotR;
		this.range = range;

		/*Vec3 vec = Vec3Utils.getLookVec(rotationYaw + rotR, rotationPitch + rotR);
		adjustX = vec.xCoord;
		adjustY = vec.yCoord;
		adjustZ = vec.zCoord;*/
		//		logger.info("Created new pyramid: " + this);
	}

	public Vec3[] getMainVecs() {
		return new Vec3[]{Vec3Utils.getLookVec(rotYaw, rotPitch), Vec3Utils.getLookVec(rotYaw + rotR, rotPitch + rotR), Vec3Utils.getLookVec(rotYaw - rotR, rotPitch + rotR), Vec3Utils.getLookVec(rotYaw + rotR, rotPitch - rotR), Vec3Utils.getLookVec(rotYaw - rotR, rotPitch - rotR)};
	}
	
	public List<AxisAlignedBB> getMainBoxes() {
		List<AxisAlignedBB> list = new ArrayList<AxisAlignedBB>();

		Vec3[] vecs = new Vec3[]{Vec3.createVectorHelper(posX, posY, posZ), Vec3.createVectorHelper(posX, posY, posZ), Vec3.createVectorHelper(posX, posY, posZ), Vec3.createVectorHelper(posX, posY, posZ), Vec3.createVectorHelper(posX, posY, posZ)};
		Vec3[] vec2s = getMainVecs();
		
		boolean arrived = false;

		while(!arrived){
			double minX = 0;
			double minY = 0;
			double minZ = 0;
			double maxX = 0;
			double maxY = 0;
			double maxZ = 0;

			for(int i = 0; i < 5; i++){
				Vec3 vec = vecs[i];

				Vec3 vec2 = vec2s[i];
				vec = vec.addVector(vec2.xCoord, vec2.yCoord, vec2.zCoord);

				if(minX == 0){
					minX = vec.xCoord;
				} else {
					minX = Math.min(minX, vec.xCoord);
				}
				if(minY == 0){
					minY = vec.yCoord;
				} else {
					minY = Math.min(minY, vec.yCoord);
				}
				if(minZ == 0){
					minZ = vec.zCoord;
				} else {
					minZ = Math.min(minZ, vec.zCoord);
				}

				if(maxX == 0){
					maxX = vec.xCoord;
				} else {
					maxX = Math.max(maxX, vec.xCoord);
				}
				if(maxY == 0){
					maxY = vec.yCoord;
				} else {
					maxY = Math.max(maxY, vec.yCoord);
				}
				if(maxZ == 0){
					maxZ = vec.zCoord;
				} else {
					maxZ = Math.max(maxZ, vec.zCoord);
				}


				vecs[i] = vec;
			}

			list.add(AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ));

			double dist = vecs[0].distanceTo(Vec3.createVectorHelper(posX, posY, posZ));
			if(dist >= range){
				arrived = true;
				break;
			}
		}

		for(int i = 0; i < 5; i++){
			vecs[i] = null;
		}
		vecs = null;
		
		return list;
	}

	public Set<BlockPos> getAffectedBlocks(){
		Set<BlockPos> blocks = new HashSet<BlockPos>();

		Vec3[] vecs = new Vec3[]{Vec3.createVectorHelper(posX, posY, posZ), Vec3.createVectorHelper(posX, posY, posZ), Vec3.createVectorHelper(posX, posY, posZ), Vec3.createVectorHelper(posX, posY, posZ), Vec3.createVectorHelper(posX, posY, posZ)};
		//		Vec3[] vec2s = new Vec3[]{Vec3Utils.getLookVec(rotYaw, rotPitch), Vec3Utils.getLookVec(rotYaw + rotR, rotPitch + rotR), Vec3Utils.getLookVec(rotYaw - rotR, rotPitch + rotR), Vec3Utils.getLookVec(rotYaw + rotR, rotPitch - rotR), Vec3Utils.getLookVec(rotYaw - rotR, rotPitch - rotR)};
		Vec3[] vec2s = getMainVecs();

		boolean arrived = false;

		while(!arrived){
			double minX = 0;
			double minY = 0;
			double minZ = 0;
			double maxX = 0;
			double maxY = 0;
			double maxZ = 0;

			for(int i = 0; i < 5; i++){
				Vec3 vec = vecs[i];

				/*if(i == 0){
					Vec3 vec2 = Vec3Utils.getLookVec(rotYaw, rotPitch);
					Vec3 vec2 = vec2s[i];
					vec = vec.addVector(vec2.xCoord, vec2.yCoord, vec2.zCoord);
				}
				if(i == 1){
					Vec3 vec2 = Vec3Utils.getLookVec(rotYaw + rotR, rotPitch + rotR);
					vec = vec.addVector(vec2.xCoord, vec2.yCoord, vec2.zCoord);
				}
				if(i == 2){
					Vec3 vec2 = Vec3Utils.getLookVec(rotYaw - rotR, rotPitch + rotR);
					vec = vec.addVector(vec2.xCoord, vec2.yCoord, vec2.zCoord);
				}
				if(i == 3){
					Vec3 vec2 = Vec3Utils.getLookVec(rotYaw + rotR, rotPitch - rotR);
					vec = vec.addVector(vec2.xCoord, vec2.yCoord, vec2.zCoord);
				}
				if(i == 4){
					Vec3 vec2 = Vec3Utils.getLookVec(rotYaw - rotR, rotPitch - rotR);
					vec = vec.addVector(vec2.xCoord, vec2.yCoord, vec2.zCoord);
				}*/

				Vec3 vec2 = vec2s[i];
				vec = vec.addVector(vec2.xCoord, vec2.yCoord, vec2.zCoord);

				if(minX == 0){
					minX = vec.xCoord;
				} else {
					minX = Math.min(minX, vec.xCoord);
				}
				if(minY == 0){
					minY = vec.yCoord;
				} else {
					minY = Math.min(minY, vec.yCoord);
				}
				if(minZ == 0){
					minZ = vec.zCoord;
				} else {
					minZ = Math.min(minZ, vec.zCoord);
				}

				if(maxX == 0){
					maxX = vec.xCoord;
				} else {
					maxX = Math.max(maxX, vec.xCoord);
				}
				if(maxY == 0){
					maxY = vec.yCoord;
				} else {
					maxY = Math.max(maxY, vec.yCoord);
				}
				if(maxZ == 0){
					maxZ = vec.zCoord;
				} else {
					maxZ = Math.max(maxZ, vec.zCoord);
				}


				vecs[i] = vec;
			}

			for(int i = (int) minX; i <= maxX; i++){
				for(int j = (int) minY; j <= maxY; j++){
					for(int k = (int) minZ; k <= maxZ; k++){
						blocks.add(new BlockPos(i, j, k));
					}
				}
			}

			double dist = vecs[0].distanceTo(Vec3.createVectorHelper(posX, posY, posZ));
			if(dist >= range){
				arrived = true;
				break;
			}
		}

		for(int i = 0; i < 5; i++){
			vecs[i] = null;
		}
		vecs = null;

		return blocks;
	}

	public Set<Entity> getAffectedEntities(){
		Set<Entity> entities = new HashSet<Entity>();

		List<Entity> list = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(posX - range, posY - range, posZ - range, posX + range, posY + range, posZ + range));

		Vec3[] vecs = new Vec3[]{Vec3.createVectorHelper(posX, posY, posZ), Vec3.createVectorHelper(posX, posY, posZ), Vec3.createVectorHelper(posX, posY, posZ), Vec3.createVectorHelper(posX, posY, posZ), Vec3.createVectorHelper(posX, posY, posZ)};
//		Vec3[] vec2s = new Vec3[]{Vec3Utils.getLookVec(rotYaw, rotPitch), Vec3Utils.getLookVec(rotYaw + rotR, rotPitch + rotR), Vec3Utils.getLookVec(rotYaw - rotR, rotPitch + rotR), Vec3Utils.getLookVec(rotYaw + rotR, rotPitch - rotR), Vec3Utils.getLookVec(rotYaw - rotR, rotPitch - rotR)};
		Vec3[] vec2s = getMainVecs();
		
		boolean arrived = false;

		while(!arrived){
			double minX = 0;
			double minY = 0;
			double minZ = 0;
			double maxX = 0;
			double maxY = 0;
			double maxZ = 0;

			for(int i = 0; i < 5; i++){
				Vec3 vec = vecs[i];

				/*if(i == 0){
					Vec3 vec2 = Vec3Utils.getLookVec(rotYaw, rotPitch);
					vec = vec.addVector(vec2.xCoord, vec2.yCoord, vec2.zCoord);
				}
				if(i == 1){
					Vec3 vec2 = Vec3Utils.getLookVec(rotYaw + rotR, rotPitch + rotR);
					vec = vec.addVector(vec2.xCoord, vec2.yCoord, vec2.zCoord);
				}
				if(i == 2){
					Vec3 vec2 = Vec3Utils.getLookVec(rotYaw - rotR, rotPitch + rotR);
					vec = vec.addVector(vec2.xCoord, vec2.yCoord, vec2.zCoord);
				}
				if(i == 3){
					Vec3 vec2 = Vec3Utils.getLookVec(rotYaw + rotR, rotPitch - rotR);
					vec = vec.addVector(vec2.xCoord, vec2.yCoord, vec2.zCoord);
				}
				if(i == 4){
					Vec3 vec2 = Vec3Utils.getLookVec(rotYaw - rotR, rotPitch - rotR);
					vec = vec.addVector(vec2.xCoord, vec2.yCoord, vec2.zCoord);
				}*/

				Vec3 vec2 = vec2s[i];
				vec = vec.addVector(vec2.xCoord, vec2.yCoord, vec2.zCoord);

				if(minX == 0){
					minX = vec.xCoord;
				} else {
					minX = Math.min(minX, vec.xCoord);
				}
				if(minY == 0){
					minY = vec.yCoord;
				} else {
					minY = Math.min(minY, vec.yCoord);
				}
				if(minZ == 0){
					minZ = vec.zCoord;
				} else {
					minZ = Math.min(minZ, vec.zCoord);
				}

				if(maxX == 0){
					maxX = vec.xCoord;
				} else {
					maxX = Math.max(maxX, vec.xCoord);
				}
				if(maxY == 0){
					maxY = vec.yCoord;
				} else {
					maxY = Math.max(maxY, vec.yCoord);
				}
				if(maxZ == 0){
					maxZ = vec.zCoord;
				} else {
					maxZ = Math.max(maxZ, vec.zCoord);
				}


				vecs[i] = vec;
			}

			/*for(int i = (int) minX; i <= maxX; i++){
				for(int j = (int) minY; j <= maxY; j++){
					for(int k = (int) minZ; k <= maxZ; k++){
						blocks.add(new BlockPos(i, j, k));
					}
				}
			}*/

			AxisAlignedBB box = AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
			for(Entity e : list){
				if(box.intersectsWith(e.boundingBox)){
					entities.add(e);
				}
			}

			double dist = vecs[0].distanceTo(Vec3.createVectorHelper(posX, posY, posZ));
			if(dist >= range){
				arrived = true;
				break;
			}
		}

		for(int i = 0; i < 5; i++){
			vecs[i] = null;
		}
		vecs = null;


		/*
		 * AxisAlignedBB box = AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
			for(Entity e : list){
				if(box.intersectsWith(e.boundingBox)){
					entities.add(e);
				}
			}
		 */

		return entities;
	}

	@Override
	public String toString() {
		return "PyramidalVec3{Pos:{" + posX + ", " + posY + ", " + posZ + "}, Rot:{" + rotYaw + ", " + rotPitch + "}, Borders degree: " + rotR + ", Height: " + range + "}";
	}

}
