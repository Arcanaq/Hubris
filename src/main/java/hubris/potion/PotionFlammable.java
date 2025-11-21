package hubris.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PotionFlammable extends PotionBase {

    public static final PotionFlammable INSTANCE = new PotionFlammable();

    public PotionFlammable() {
        super("flammable", true, 0xff8000);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier){
        World world = entity.world;
        BlockPos pos = entity.getPosition();
        if (world.isAirBlock(new BlockPos(pos.getX(), pos.getY()-1, pos.getZ()))) return;
        if (entity instanceof EntityPlayer) world.playSound((EntityPlayer) entity, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, 0.8F);
        world.setBlockState(pos, Blocks.FIRE.getDefaultState(), 11);
    }
}