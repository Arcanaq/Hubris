package hubris.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PotionClipping extends PotionBase {

    public static final PotionClipping INSTANCE = new PotionClipping();

    public PotionClipping() {
        super("clipping", true, 0xf4c6ff);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier){
        World world = entity.world;
        BlockPos pos = entity.getPosition();
        if (
                !entity.getActivePotionEffect(INSTANCE).getIsPotionDurationMax()
                && world.isAirBlock(pos)
                && world.isAirBlock(new BlockPos(pos.getX(), pos.getY()-1, pos.getZ()))
                )
        {
            entity.removePotionEffect(INSTANCE);
            return;
        }
        entity.noClip = true;
    }
}