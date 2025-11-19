package hubris.misfortunes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MisfortuneBedExplosion extends Misfortune {
    public static final MisfortuneBedExplosion INSTANCE = new MisfortuneBedExplosion();

    public MisfortuneBedExplosion() {
        super("misfortune_bed_explosion");
    }

    @Override
    public void Apply(EntityPlayer plr){
        super.Apply(plr);
        World world = plr.world;
        BlockPos bedPos = plr.getBedLocation();
        world.createExplosion(null, bedPos.getX(), bedPos.getY(), bedPos.getZ(), 10, true);
    }
}