package landmaster.plustic.item;

import landmaster.plustic.block.IMetaBlockName;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ItemBlockMeta extends ItemBlock {

    public <T extends Block & IMetaBlockName> ItemBlockMeta(T block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Nonnull
    @Override
    public String getUnlocalizedName(@Nonnull ItemStack stack) {
        return super.getUnlocalizedName(stack) + "." + ((IMetaBlockName) block).getSpecialName(stack);
    }
}
