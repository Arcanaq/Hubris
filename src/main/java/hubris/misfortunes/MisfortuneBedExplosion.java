package hubris.misfortunes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MisfortuneBedExplosion extends MisfortuneBase {
    public MisfortuneBedExplosion() {

    }

    public static void apply(EntityPlayer plr){
        World world = plr.world;
        BlockPos pos = plr.getPosition();
        BlockPos bedPos = plr.getBedLocation();
        if (bedPos != null) {
            world.createExplosion(null, bedPos.getX(), bedPos.getY(), bedPos.getZ(), 10, true);
        }
    }
}