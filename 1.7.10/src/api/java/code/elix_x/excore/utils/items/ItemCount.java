package code.elix_x.excore.utils.items;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class ItemCount {
	
	public Item item;
	public int count;
	
	public ItemCount(Item i, int c) {
		item = i;
		count = c;
	}
	
	public ItemCount(Block block, int c){
		this(Item.getItemFromBlock(block), c);
	}
	
	@Override
	public String toString() {
		return item.getUnlocalizedName() + " x " + count;
	}

}
